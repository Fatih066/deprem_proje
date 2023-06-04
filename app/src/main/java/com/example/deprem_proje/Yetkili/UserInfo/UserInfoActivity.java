package com.example.deprem_proje.Yetkili.UserInfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.deprem_proje.Firabase.Auth;
import com.example.deprem_proje.Firabase.FireStore;
import com.example.deprem_proje.R;
import com.example.deprem_proje.publicFunctions.PublicFunctions;

public class UserInfoActivity extends AppCompatActivity {

    private TextView txtAdSoyad;
    private TextView txtEmail;
    private TextView txtTelNo;
    private TextView txtKanGrubu;
    private Button btnKurtarildi;
    private Button btnMesajAt;
    private GetUserInfo getUserInfo;
    private Intent intent;
    private FireStore fireStore;
    private PublicFunctions publicFunctions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        setViews();
        String uid =intent.getStringExtra("Uid");
        getUserInfo.getuserInfo(uid,userInfo -> {
            txtAdSoyad.setText(userInfo.get("name").toString());
            txtEmail.setText(userInfo.get("email").toString());
            txtTelNo.setText(userInfo.get("phone").toString());
            txtKanGrubu.setText(userInfo.get("kanGrubu").toString());
        });

        btnKurtarildi.setOnClickListener(v -> {
            fireStore.removeUserLocation(uid, true, publicFunctions.getCurrenDate());
        });
    }


    private void setViews(){
        intent = getIntent();
        getUserInfo = new GetUserInfo();
        fireStore = new FireStore();
        publicFunctions = new PublicFunctions();
        txtAdSoyad = findViewById(R.id.txtAdSoyad);
        txtEmail = findViewById(R.id.txtEmail);
        txtTelNo = findViewById(R.id.txtTelNo);
        txtKanGrubu = findViewById(R.id.txtKanGrubu);
        btnKurtarildi = findViewById(R.id.btnKurtarildi);
        btnMesajAt = findViewById(R.id.btnMesaj);
    }
}