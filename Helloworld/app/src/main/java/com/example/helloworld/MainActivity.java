package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView txtNoiDung;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ánh xạ
        txtNoiDung = (TextView) findViewById(R.id.textView);
        btn = (Button) findViewById(R.id.button);
        // viết code
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtNoiDung.setText("Chào mừng đến với lập trình android ");
            }
        });
    }
}
