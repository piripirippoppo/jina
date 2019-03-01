package com.example.jina.dinasour;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView t_rex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // t-rex used as a logo for the startup
        t_rex = findViewById(R.id.t_rex);

        Thread welcomeThread = new Thread() {

            @Override
            public void run() {
                try {
                    super.run();
                    sleep(2000);
                } catch (Exception e) {

            } finally {
                Intent mainIntent = new Intent(MainActivity.this, StartActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }
    };
    welcomeThread.start();
    }
}
