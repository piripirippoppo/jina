package com.example.tutorpal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button points = (Button) findViewById(R.id.redeemPoints);
        final Button tutor = (Button) findViewById(R.id.findStudentButton);
        final Button students = (Button) findViewById(R.id.findTutor);

        //final Button log_out = (Button) findViewById(R.id.log_out2);

        points.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(getApplicationContext(), PointsActivity.class);
                Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
                startActivity(intent);
            }
        });

        tutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FindStudentsActivity.class);
                startActivity(intent);
            }
        });

        students.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FindTutorActivity.class);
                startActivity(intent);
            }
        });

//        log_out.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), StartActivity.class);
//                startActivity(intent);
//            }
//        });
    }
}
