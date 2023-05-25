package com.example.deprem_proje.Kullanici;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import com.example.deprem_proje.Firabase.FireStore;
import com.example.deprem_proje.Kullanici.Fragments.ChatFragment;
import com.example.deprem_proje.Kullanici.Fragments.HomeFragment;
import com.example.deprem_proje.Location.GetLocation;
import com.example.deprem_proje.R;
import com.example.deprem_proje.Firabase.Auth;
import com.example.deprem_proje.databinding.ActivityKullaniciBinding;
import com.google.firebase.auth.FirebaseAuth;

public class Kullanici extends AppCompatActivity {

    private ActivityKullaniciBinding binding;
    private Auth auth;
    private  boolean isSafe;
    private GetLocation getLocation;
    private FireStore fireStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityKullaniciBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());
        auth = new Auth();
        fireStore = new FireStore();
        getLocation = new GetLocation(this, this);
        binding.bottomNavigationView.setBackground(null);
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                replaceFragment(new HomeFragment());
            } else if (itemId == R.id.chat) {
                replaceFragment(new ChatFragment());
            }
            return true;
        });
        isSafe = true;



    }

    private void signOut() {
        auth.signOut();
    }

    private void onAuthStateChanged(Bundle options) {
        FirebaseAuth.AuthStateListener mAuthStateListener = auth.mAuthStateListener(Kullanici.this, Kullanici.class, options);
        mAuthStateListener.onAuthStateChanged(auth._firebaseAuth);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    public void guvendeyim(View view) {
       isSafe = true;
       fireStore.removeUserLocation(auth.getUser().getUid(), isSafe, "asd");
    }

    public void guvendeDegilim(View view){
        isSafe = false;
        getLocation.getLocation(auth.getUser().getUid(), isSafe);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 200 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getLocation.getLocation(auth.getUser().getUid(), isSafe);
        }
    }


}


