package com.example.deprem_proje.publicFunctions;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.deprem_proje.MainActivity;
import com.example.deprem_proje.auth.KayitOl;

public class PublicFunctions {
    public void GoPage(android.content.Context packageContext,
                       Class<?> cls, Bundle options){
        Intent intent = new Intent(packageContext, cls);
        startActivity(packageContext, intent,options);
    }
}
