package fpt.grw.reviewandroid;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;

import java.util.List;

import entities.ExamDetail;

public class MyCustomAdapter implements ListAdapter {
    Context context;
    List<ExamDetail> examDetails;

    public MyCustomAdapter(Context context, List<ExamDetail> examDetails) {
        this.context = context;
        this.examDetails = examDetails;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int i) {
        return false;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public int getCount() {
        return examDetails.size();
    }

    @Override
    public Object getItem(int i) {
        return examDetails.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
       if (view == null){
           LayoutInflater layoutInflater = LayoutInflater.from(context);
           view = layoutInflater.inflate(R.layout.list_item, null);

           TextView exam_detailID = view.findViewById(R.id.textView);
           TextView exam_detailName = view.findViewById(R.id.textView2);
           ImageView image = view.findViewById(R.id.imageView2);

           ExamDetail details = examDetails.get(i);
           exam_detailID.setText(String.valueOf(details.getDetail_id()));
           exam_detailName.setText(details.getDetail_question());

           Picasso.with(context)
                   .load(details.getDetail_picture_url())
                   .into(image);
           //register even user click on Listview Item
           view.setOnClickListener(view1 -> {
               Toast.makeText(context, details.getDetail_question(), Toast.LENGTH_SHORT).show();
           });
       }
       return view;
    }

    @Override
    public int getItemViewType(int i) {
        return 1;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
