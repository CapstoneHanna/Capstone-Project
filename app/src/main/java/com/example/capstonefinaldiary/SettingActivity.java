package com.example.capstonefinaldiary;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


// 환경설정(메뉴) -> 로그아웃 기능(구글)
public class SettingActivity extends AppCompatActivity {

    private GoogleSignInClient mSignInClient;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser currentUser;
    private TextView logout;
    private MenuActivity menuActivity;

    ImageView ivProfile;
    TextView tv_Username;
    TextView profile;
    ImageView profile_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);      // activity_setting.xml과 연동

        menuActivity = new MenuActivity(this) ; // MenuActivity 인스턴스 생성

        // Firebase 인증 및 현재 사용자 가져오기
        mFirebaseAuth = FirebaseAuth.getInstance();
        currentUser = mFirebaseAuth.getCurrentUser();

        ivProfile = findViewById(R.id.iv_Profile);       // 프로필 이미지 뷰
        tv_Username = findViewById(R.id.tv_Username);    // 사용자 이름
        logout = findViewById(R.id.logout);              // 로그아웃

        if (currentUser != null) {                       // 사용자가 있다면
            String userName = currentUser.getDisplayName();    // 현재 사용자의 이름 가져와서 userName에 할당
            Uri profilePicUri = currentUser.getPhotoUrl();     // PhotoUrl 또한 ..

            if (userName != null) {
                // 유저 이름 설정
                tv_Username.setText("UserName: " + userName);
            }

            if (profilePicUri != null) {
                // 프로필 이미지 설정
                Glide.with(this)
                        .load(profilePicUri)
                        .circleCrop()
                        .into(ivProfile);                    // 원형 이미지(Glide -> build.gradle에 설정)
            }
        }

        profile = findViewById(R.id.profile);
        profile_img = findViewById(R.id.profile_img);

        profile.setOnClickListener(new View.OnClickListener() {           // 프로필을 클릭하면
            @Override
            public void onClick(View view) {
                if (currentUser != null) {                             // 현재 사용자의 HomeActivity로 이동하기
                    Intent intent1 = new Intent(SettingActivity.this, HomeActivity.class);
                    startActivity(intent1);
                }
            }
        });


        // logout 버튼 클릭 시 초기화
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // GoogleSignInClient 초기화
                if (mSignInClient == null) {
                    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestIdToken(getString(R.string.default_web_client_id))
                            .requestEmail()
                            .build();
                    mSignInClient = GoogleSignIn.getClient(SettingActivity.this, gso);
                }
                // 로그아웃 처리
                if (currentUser != null) {
                    mSignInClient.signOut().addOnCompleteListener(SettingActivity.this,
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                // Firebase 로그아웃
                                mFirebaseAuth.signOut();
                                // MainActivity로 이동(로그인 맨 처음 화면)
                                startActivity(new Intent(SettingActivity.this, MainActivity.class));
                                finish();
                            }
                    });
                }

            }
        });

    }

}