package com.example.deprem_proje.Yetkili;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.deprem_proje.Firabase.Auth;
import com.example.deprem_proje.Kullanici.Fragments.ChatFragment;
import com.example.deprem_proje.Kullanici.Fragments.HomeFragment;
import com.example.deprem_proje.Kullanici.Kullanici;
import com.example.deprem_proje.R;

import com.example.deprem_proje.Yetkili.Fragments.YetkiliHomeFragment;
import com.example.deprem_proje.databinding.ActivityKullaniciBinding;
import com.example.deprem_proje.databinding.ActivityYetkiliBinding;
import com.google.firebase.auth.FirebaseAuth;

public class Yetkili extends AppCompatActivity  {



    private Toolbar toolbar;
    private  Bundle options;
    private Auth auth;
    private ActivityYetkiliBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityYetkiliBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        replaceFragment(new YetkiliHomeFragment());

        binding.yetkiliBottomNavigationView.setBackground(null);
        binding.yetkiliBottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                replaceFragment(new YetkiliHomeFragment());
            } else if (itemId == R.id.chat) {
                replaceFragment(new ChatFragment());
            }
            return true;
        });
        toolbar = findViewById(R.id.appBar);
        setSupportActionBar(toolbar);
        options = savedInstanceState;
        auth = new Auth();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.signOut) {
            signOut();
            onAuthStateChanged(options);
        }
        return true;
    }


    private void signOut() {
        auth.signOut();
    }

    private void onAuthStateChanged(Bundle options) {
        FirebaseAuth.AuthStateListener mAuthStateListener = auth.mAuthStateListener(this, Kullanici.class, options);
        mAuthStateListener.onAuthStateChanged(auth._firebaseAuth);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.yetkili_frame_layout, fragment);
        fragmentTransaction.commit();
    }
}