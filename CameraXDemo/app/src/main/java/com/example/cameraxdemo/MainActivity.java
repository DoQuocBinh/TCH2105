package com.example.cameraxdemo;

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

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
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
                   Toast.makeText(this, "From Gallery", Toast.LENGTH_SHORT).show();
               }else if(items[item]=="View a picture from an Uri"){
                   Toast.makeText(this, "From an Uri", Toast.LENGTH_SHORT).show();
                   dialog.dismiss();
               }
            });
            builder.show();
        });
        
    }

    private void takePicture() {
        long timestamp = System.currentTimeMillis();
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