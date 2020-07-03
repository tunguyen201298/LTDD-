package com.example.cuahangsach;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SachAdapter2 extends ArrayAdapter<SachOnThi> {
    Activity context;
    int resource;
    int position=-1;
    public SachAdapter2(Activity context, int resource) {
        super(context, resource);
        this.context=context;
        this.resource=resource;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View customView=this.context.getLayoutInflater().inflate(this.resource,null);

        ImageView imgHinhSach1=(ImageView)customView.findViewById(R.id.imgHinhAnhSach);
        TextView txtTenSach1=(TextView)customView.findViewById(R.id.txtTenSach);
        TextView txtGiaSach1=(TextView)customView.findViewById(R.id.txtGiaSach);
        TextView txtNhaXuatBan1=(TextView)customView.findViewById(R.id.txtNhaXuatBan);

        SachOnThi sot=getItem(position);
        imgHinhSach1.setImageResource(sot.getHinhAnhSach());
        txtTenSach1.setText(sot.getTenSach());
        txtGiaSach1.setText(sot.getGiaSach()+"VND");
        txtNhaXuatBan1.setText(sot.getNhaXuatBan());
        return customView;
    }
}
