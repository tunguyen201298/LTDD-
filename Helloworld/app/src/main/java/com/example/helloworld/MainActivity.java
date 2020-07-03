package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.adapter.ProductAdapter;

public class MainActivity extends AppCompatActivity {
    private ListView listProduct;
    String[] maintitle ={
        "Bản Thiết kế vĩ đại","Ngày xưa có một con bò","Ký sự code dạo"
    };
    String[] subtotal ={
            "Trần Thanh Trung","Nguyễn Văn Tú","Phạm Huy Hoàng"
    };
    Integer[] imgid ={
         R.drawable.btkvd,R.drawable.nxcmcb,R.drawable.kscd
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        ///
        ProductAdapter adapter = new ProductAdapter(this, maintitle, subtotal, imgid);
        listProduct = (ListView) findViewById(R.id.listProduct) ;
        listProduct.setAdapter(adapter);

    }
}
