package com.example.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.helloworld.R;


public class ProductAdapter extends ArrayAdapter<String> {
    private  final Activity context;
    private final  String[] maintitle;
    private final  String[] subtatol;
    private final  Integer[] imgid;

    public ProductAdapter(Activity context,String[] maintitle,String[] subtatol,Integer[] imgid){
        super(context, R.layout.product,maintitle);
        this.context = context;
        this.maintitle = maintitle;
        this.subtatol = subtatol;
        this.imgid = imgid;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();

        View row = inflater.inflate(R.layout.product, null, true);
        TextView title = (TextView) row.findViewById(R.id.product_title);
        TextView sub_title = (TextView) row.findViewById(R.id.product_subtotal);
        ImageView img = (ImageView) row.findViewById(R.id.product_img);

        title.setText(maintitle[position]);
        sub_title.setText(subtatol[position]);
        img.setImageResource(imgid[position]);
        return row;
    }
}
