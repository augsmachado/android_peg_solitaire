package com.augsmachado.pegsolitaire;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    // Initialize constants
    public static final int MAX_MILLISECONDS = 1000;
    public static final int MAX_MINUTES = 2;
    public static final int MAX_RANDOM = 1000;

    // Initialize variables
    private TextView mTimer;
    private TextView mResults;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();

        // Initialize timer duration
        long duration = TimeUnit.MINUTES.toMillis(MAX_MINUTES);


        // Generate random number
        int randomNumber = generateRandomNumber();
        mResults.setText(String.valueOf(randomNumber));

        // Initialize countdown timer
        new CountDownTimer(duration, MAX_MILLISECONDS) {
            @Override
            public void onTick(long millisUntilFinished) {
                // When tick, convert millisecond to minute and second
                String sDuration = String.format(Locale.ENGLISH, " %02d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));

                // Set converted string on text view
                mTimer.setText(sDuration);
            }

            @Override
            public void onFinish() {
                // When finish, hide text view
                mTimer.setVisibility(View.GONE);

                // Display toast
                Toast.makeText(getApplicationContext(), "Countdown timer has ended", Toast.LENGTH_SHORT).show();
            }
        }.start();

    }


    private void bindViews(){
        // Initialize the components in view
        mTimer = findViewById(R.id.timer);

        mResults = findViewById(R.id.results);


    }

    private int generateRandomNumber() {
        return ThreadLocalRandom.current().nextInt(MAX_RANDOM);
    }
}