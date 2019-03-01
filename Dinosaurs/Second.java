package com.example.jina.dinasour;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class SecondActivity extends AppCompatActivity {

    private ImageView apato;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        apato = findViewById(R.id.apato);

        Intent mainIntent = new Intent(SecondActivity.this, ThirdActivity.class);
        startActivity(mainIntent);
        finish();
    }
}
