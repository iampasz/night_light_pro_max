package com.appsforkids.pasz.nightlightpromax;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.appsforkids.pasz.nightlightpromax.Fragments.MainFragment;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Realm.init(this);

        getSupportFragmentManager().beginTransaction().add(R.id.my_container, new MainFragment()).commit();
    }
}