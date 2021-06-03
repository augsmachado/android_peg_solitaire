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

    private Button mFirstButton, mSecondButton, mThirdButton, mFourthButton, mFifthButton;
    private  Button mSixthButton, mSeventhButton, mEighthButton, mNinthButton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();

        // Initialize timer duration
        long duration = TimeUnit.MINUTES.toMillis(MAX_MINUTES);


        // Generate random number
        int randomNumber = generateRandomNumber(MAX_RANDOM);

        if (randomNumber == 1 || randomNumber == 0) randomNumber += 2;

        mResults.setText(String.valueOf(randomNumber));

        // Generate game board
        generateBoardGame(randomNumber);

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

        mFirstButton = findViewById(R.id.line1Button1);
        mSecondButton = findViewById(R.id.line1Button2);
        mThirdButton = findViewById(R.id.line1Button3);
        mFourthButton = findViewById(R.id.line2Button1);
        mFifthButton = findViewById(R.id.line2Button2);
        mSixthButton = findViewById(R.id.line2Button3);
        mSeventhButton = findViewById(R.id.line3Button1);
        mEighthButton = findViewById(R.id.line3Button2);
        mNinthButton = findViewById(R.id.line3Button3);

    }

    private int generateRandomNumber(int value) {
        return ThreadLocalRandom.current().nextInt(value);
    }

    private void generateBoardGame (int randomNumber) {
        int[] array;

        // Successive divisions of the random number
        array = successiveDivisions(randomNumber);

        // Difference between summation and random number
        array = diffSummationRandomNumber(array, randomNumber);


        mFirstButton.setText(String.valueOf(array[0]));
        mSecondButton.setText(String.valueOf(array[1]));
        mThirdButton.setText(String.valueOf(array[2]));
        mFourthButton.setText(String.valueOf(array[3]));
        mFifthButton.setText(String.valueOf(array[4]));
        mSixthButton.setText(String.valueOf(array[5]));
        mSeventhButton.setText(String.valueOf(array[6]));
        mEighthButton.setText(String.valueOf(array[7]));
        mNinthButton.setText(String.valueOf(array[8]));
    }

    // Successive divisions of the random number
    private int[] successiveDivisions (int randomNumber) {
        int[] array = new int[9];
        array[0] = randomNumber / 2;

        for(int i = 1; i < 8; i++) {
            array[i] = array[i-1]/ 2;
        }

        return array;
    }

    // Difference between summation and random number
    private int[] diffSummationRandomNumber(int[] array, int randomNumber) {
        int sum = 0;

        // Summation of values on array
        for(int i = 0; i < 9; i++) {
            sum = sum + array[i];
        }

        // Difference between summation and random number
        int diff = randomNumber - sum - 1;

        // Test the positions is not zero
        sum = 0;
        for(int i = 0; i < 9; i++) {
            if (array[i] != 0) sum++;
        }

        // Add diff in random position of array, if value different of zero
        int i = generateRandomNumber(sum);
        array[i] = array[i] + diff;
        
        return array;
    }
}