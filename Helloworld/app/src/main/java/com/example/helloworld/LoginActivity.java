package com.example.helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
public class LoginActivity extends AppCompatActivity {
    EditText edtuser,edtpassword;
    Button btndangky,btndangnhap;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Anhxa();

        btndangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = "admin";
                String password = "admin";

                if (edtuser.getText().toString().equals(username) && edtpassword.getText().toString().equals(password)){
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(), "Đăng nhập thất bại", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void Anhxa() {
        edtuser = (EditText)findViewById(R.id.edittextuser);
        edtpassword = (EditText)findViewById(R.id.edittextpassword);
        btndangky = (Button)findViewById(R.id.buttondangky);
        btndangnhap = (Button)findViewById(R.id.buttondangnhap);
    }
}
