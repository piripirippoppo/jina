package com.example.jina.dinasour;

import android.content.*;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class StartActivity extends AppCompatActivity {

    private ImageView anky;
    private Button button_next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        anky = findViewById(R.id.anky);
        button_next = findViewById(R.id.button_next);


        button_next.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent nextIntent = new Intent(StartActivity.this, SecondActivity.class);
                StartActivity.this.startActivity(nextIntent);
            }
        });

    }
}
