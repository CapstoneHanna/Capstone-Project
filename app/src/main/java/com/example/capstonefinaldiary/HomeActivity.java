package com.example.capstonefinaldiary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class HomeActivity extends AppCompatActivity {

    ImageView imageView;
    TextView textView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        imageView = findViewById(R.id.profile_image);
        textView = findViewById(R.id.profile_name);


        String userName = getIntent().getStringExtra("userName");
        String ProfilePic = getIntent().getStringExtra("ProfilePic");

        textView.setText("userName");   //유저 이름이 나타나도록 설정
        Picasso.get().load(ProfilePic).into(imageView);   // 프로필 사진




    }
}