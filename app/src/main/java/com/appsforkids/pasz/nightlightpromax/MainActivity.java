package com.appsforkids.pasz.nightlightpromax;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.appsforkids.pasz.nightlightpromax.Fragments.MainFragment;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        Realm.init(this);

        getSupportFragmentManager().beginTransaction().add(R.id.my_container, new MainFragment()).commit();
    }
}