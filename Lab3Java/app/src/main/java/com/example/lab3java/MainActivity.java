package com.example.lab3java;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button count_button;
    Button toast_button;
    TextView textCount ;
    int counter;

    private void reset(TextView txt){
        counter = 0;
        txt.setText(String.valueOf(counter));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        count_button = findViewById(R.id.count_button);
        toast_button = findViewById(R.id.button_toast);
        textCount = findViewById(R.id.count_txt);

        counter=0;


        toast_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset(textCount);
                Toast.makeText(MainActivity.this, "Counter reseted", Toast.LENGTH_LONG).show();
            }
        });

        count_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter=counter+1;
                textCount.setText(String.valueOf(counter));
            }
        });
    }
}