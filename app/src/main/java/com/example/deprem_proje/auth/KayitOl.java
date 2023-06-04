package com.example.deprem_proje.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.example.deprem_proje.Firabase.Auth;
import com.example.deprem_proje.Firabase.FireStore;
import com.example.deprem_proje.Kullanici.Kullanici;
import com.example.deprem_proje.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.auth.User;

public class KayitOl extends AppCompatActivity {
    private EditText emailEditText, passwordEditText, nameEditText, phoneEditText, kanGrubuEditText;
    private Button kayitOlButton;
    private FireStore fireStore;

    private Auth auth;
    private Switch isUserSwitch;
    private boolean isUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit_ol);

        emailEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);
        kayitOlButton = findViewById(R.id.buttonRegister);
        nameEditText = findViewById(R.id.editTextKullaniciAdi);
        phoneEditText = findViewById(R.id.editTextTelefonNo);
        kanGrubuEditText = findViewById(R.id.editTextKanGrubu);
        isUserSwitch = findViewById(R.id.isUser);
        auth = new Auth();
        fireStore = new FireStore();
        isUser = true;

        kayitOlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                createUserWithEmailAndPassword(email, password);
                onAuthStateChanged(savedInstanceState);
            }
        });
        isUserSwitch.setChecked(isUser);
        isUserSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isUser = true;
                    System.out.println(isUser);

                } else {
                    isUser = false;
                    System.out.println(isUser);
                }
            }
        });

    }


    private void createUserWithEmailAndPassword(String email, String password){
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if(user != null){
                    String uid = user.getUid();
                    setUserInfos(uid);
                }else{
                    System.out.println("user yok");
                }
            }else{
                System.out.println("başarısız");
            }
        });
    }

    private  void setUserInfos(String userUid){
        fireStore.setUserInfos(userUid,nameEditText.getText().toString(), phoneEditText.getText().toString(),emailEditText.getText().toString(), passwordEditText.getText().toString(), kanGrubuEditText.getText().toString(), isUser);
    }
    private  void onAuthStateChanged(Bundle options){
        FirebaseAuth.AuthStateListener mAuthStateListener = auth.mAuthStateListener(KayitOl.this, Kullanici.class, options);
        mAuthStateListener.onAuthStateChanged(auth._firebaseAuth);
    }
}