package com.example.deprem_proje.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
        auth = new Auth();
        fireStore = new FireStore();

        kayitOlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                createUserWithEmailAndPassword(email, password);
                onAuthStateChanged(savedInstanceState);
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
        fireStore.setUserInfos(userUid,nameEditText.getText().toString(), phoneEditText.getText().toString(),emailEditText.getText().toString(), passwordEditText.getText().toString(), kanGrubuEditText.getText().toString(), false);
    }
    private  void onAuthStateChanged(Bundle options){
        FirebaseAuth.AuthStateListener mAuthStateListener = auth.mAuthStateListener(KayitOl.this, Kullanici.class, options);
        mAuthStateListener.onAuthStateChanged(auth._firebaseAuth);
    }
}