package com.example.cameraxdemo;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.common.util.concurrent.ListenableFuture;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    PreviewView previewView;
    ImageCapture imageCapture;
    
    Executor executor = Executors.newSingleThreadExecutor();
    final int REQUEST_CODE_PERMISSIONS = 1011;
    final String[] REQUIRED_PERMISSIONS = {"android.permission.CAMERA","android.permission.RECORD_AUDIO"};
    
    ImageView imageView;
    EditText inputPictureUri;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        imageView = findViewById(R.id.imageView);
        previewView = findViewById(R.id.previewView);
        inputPictureUri = findViewById(R.id.inputPictureUri);
        Button button = findViewById(R.id.button);

        if(!allPermissionGranted()){
            ActivityCompat.requestPermissions(this,REQUIRED_PERMISSIONS,REQUEST_CODE_PERMISSIONS);
        }
        startCamera();
        button.setOnClickListener(view->{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            String[] items = {"Take picture","Select picture from Gallery","View a picture from an Uri"};
            builder.setItems(items,(dialog,item)->{
               if ( items[item]=="Take picture"){
                   takePicture();
               }else if(items[item]=="Select picture from Gallery"){
                  selectPictureFromGallery();
               }else if(items[item]=="View a picture from an Uri"){
                   String uri = inputPictureUri.getText().toString();
                   Bitmap bitmap = BitmapFactory.decodeFile(new File(uri).getAbsolutePath());
                   imageView.setImageBitmap(bitmap);
                   dialog.dismiss();
               }
            });
            builder.show();
        });
        
    }

    private void selectPictureFromGallery() {
        Intent intent =new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        selectPicture.launch(intent);
    }
    ActivityResultLauncher<Intent> selectPicture = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getResultCode()== Activity.RESULT_OK){
                    Intent data = result.getData();
                    Uri fileUri = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),fileUri);
                        imageView.setImageBitmap(bitmap);

                        //create a copy of the file in the gallery
                        String filePath = getApplicationContext().getFilesDir().getAbsolutePath()
                                + File.separator + String.valueOf(System.currentTimeMillis());
                        File saveFile = bitmapToFile(bitmap,filePath);
                        inputPictureUri.setText(saveFile.getAbsolutePath());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
    );
    public static File bitmapToFile(Bitmap bitmap, String fileNameToSave) {
        //create a file to write bitmap data
        File file = null;
        try {
            file = new File(fileNameToSave);
            file.createNewFile();

            //Convert bitmap to byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0 , bos); // YOU can also save it in JPEG
            byte[] bitmapdata = bos.toByteArray();

            //write the bytes in file
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
            return file;
        }catch (Exception e){
            e.printStackTrace();
            return file; // it will return null
        }
    }


    private void takePicture() {
        long timestamp = System.currentTimeMillis();

        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, timestamp);
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
            contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image");
        }

        //option 1: store in phone's gallery
        ImageCapture.OutputFileOptions options1 =new ImageCapture.OutputFileOptions.Builder(
                getContentResolver(),
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
        ).build();

        ImageCapture.OutputFileOptions option2 = new ImageCapture.OutputFileOptions.Builder(
                new File(getApplicationContext().getFilesDir(),String.valueOf(timestamp))
        ).build();
        imageCapture.takePicture(option2, executor,
                new ImageCapture.OnImageSavedCallback() {
                    @Override
                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                        Uri selectedImg = outputFileResults.getSavedUri();
                        runOnUiThread(()->{
                            try {
                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),selectedImg);
                                inputPictureUri.setText(selectedImg.getPath());
                                imageView.setImageBitmap(bitmap);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                    }
                    @Override
                    public void onError(@NonNull ImageCaptureException exception) {

                    }
                });
    }

    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderListenableFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderListenableFuture.addListener(()->{
            try{
                ProcessCameraProvider cameraProvider = cameraProviderListenableFuture.get();
                Preview preview = new Preview.Builder().build();
                preview.setSurfaceProvider(previewView.getSurfaceProvider());

                CameraSelector cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA;
                imageCapture = new ImageCapture.Builder().build();

                cameraProvider.unbindAll();
                cameraProvider.bindToLifecycle(this,cameraSelector,preview,imageCapture);

            }catch (Exception ex){
                ex.printStackTrace();
            }
        },ContextCompat.getMainExecutor(this));
    }

    boolean allPermissionGranted(){
        for (String permission: REQUIRED_PERMISSIONS){
            if(ContextCompat.checkSelfPermission(this,permission) != PackageManager.PERMISSION_GRANTED){
                return  false;
            }
        }
        return  true;
    }
}