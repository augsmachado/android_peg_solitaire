package com.augsmachado.pegsolitaire;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    // Initialize constants
    public static final int MAX_MILLISECONDS = 1000;
    public static final int MAX_MINUTES = 2;
    public static final int MAX_RANDOM = 1000;
    public static final int MAX_HAS_ZEROS = 50;
    public static final int BOARD = 9;

    // Initialize variables
    private TextView mTimer;
    private TextView mResults;
    private TextView mScores;

    // Initialize buttons
    private Button mFirstButton, mSecondButton, mThirdButton, mFourthButton, mFifthButton;
    private Button mSixthButton, mSeventhButton, mEighthButton, mNinthButton;
    private Button mStart, mRestart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();






        // Initialize mStart button
        mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newGame();
                showButtons();

                mStart.setVisibility(View.INVISIBLE);
                mTimer.setVisibility(View.VISIBLE);

                // Initialize timer duration
                long duration = TimeUnit.MINUTES.toMillis(MAX_MINUTES);

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

                        hideButtons();
                        mStart.setVisibility(View.VISIBLE);
                        mRestart.setVisibility(View.INVISIBLE);

                        // Display toast
                        Toast.makeText(getApplicationContext(), "Countdown timer has ended", Toast.LENGTH_SHORT).show();
                    }
                }.start();
            }
        });

        // Restart with new game
        mRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newGame();
                showButtons();

                mRestart.setVisibility(View.INVISIBLE);
            }
        });


        // Set behavior for each button displayed with numbers
        // When button clicked, subtract corresponding clicked number
        mFirstButton.setOnClickListener(v -> {
            String str = (String) mFirstButton.getText();
            subtractNumber(str);

            mFirstButton.setClickable(false);
            mFirstButton.setVisibility(View.INVISIBLE);
        });

        mSecondButton.setOnClickListener(v -> {
            String str = (String) mSecondButton.getText();
            subtractNumber(str);

            mSecondButton.setClickable(false);
            mSecondButton.setVisibility(View.INVISIBLE);
        });

        mThirdButton.setOnClickListener(v -> {
            String str = (String) mThirdButton.getText();
            subtractNumber(str);

            mThirdButton.setClickable(false);
            mThirdButton.setVisibility(View.INVISIBLE);
        });

        mFourthButton.setOnClickListener(v -> {
            String str = (String) mFourthButton.getText();
            subtractNumber(str);

            mFourthButton.setClickable(false);
            mFourthButton.setVisibility(View.INVISIBLE);
        });

        mFifthButton.setOnClickListener(v -> {
            String str = (String) mFifthButton.getText();
            subtractNumber(str);

            mFifthButton.setClickable(false);
            mFifthButton.setVisibility(View.INVISIBLE);
        });

        mSixthButton.setOnClickListener(v -> {
            String str = (String) mSixthButton.getText();
            subtractNumber(str);

            mSixthButton.setClickable(false);
            mSixthButton.setVisibility(View.INVISIBLE);
        });

        mSeventhButton.setOnClickListener(v -> {
            String str = (String) mSeventhButton.getText();
            subtractNumber(str);

            mSeventhButton.setClickable(false);
            mSeventhButton.setVisibility(View.INVISIBLE);
        });

        mEighthButton.setOnClickListener(v -> {
            String str = (String) mEighthButton.getText();
            subtractNumber(str);

            mEighthButton.setClickable(false);
            mEighthButton.setVisibility(View.INVISIBLE);
        });

        mNinthButton.setOnClickListener(v -> {
            String str = (String) mNinthButton.getText();
            subtractNumber(str);

            mNinthButton.setClickable(false);
            mNinthButton.setVisibility(View.INVISIBLE);
        });
    }

    private void bindViews() {
        // Initialize the components in view
        mTimer = findViewById(R.id.timer);

        mResults = findViewById(R.id.results);
        mScores = findViewById(R.id.scores);

        mFirstButton = findViewById(R.id.line1Button1);
        mSecondButton = findViewById(R.id.line1Button2);
        mThirdButton = findViewById(R.id.line1Button3);
        mFourthButton = findViewById(R.id.line2Button1);
        mFifthButton = findViewById(R.id.line2Button2);
        mSixthButton = findViewById(R.id.line2Button3);
        mSeventhButton = findViewById(R.id.line3Button1);
        mEighthButton = findViewById(R.id.line3Button2);
        mNinthButton = findViewById(R.id.line3Button3);

        mStart = findViewById(R.id.startNewGame);
        mRestart = findViewById(R.id.restart);

    }

    // Create a new game
    private void newGame() {
        // Generate random number above 2
        int randomNumber = generateRandomNumber(MAX_RANDOM);

        if (randomNumber < 2) randomNumber = 2;

        // Generate game board and set mResults view
        generateBoardGame(randomNumber);

        mResults.setText(String.valueOf(randomNumber));
    }

    // Generate random number above 2
    private int generateRandomNumber(int value) {
        return ThreadLocalRandom.current().nextInt(value);
    }

    // Generate game board
    private int[] generateBoardGame (int randomNumber) {
        int[] array;

        // Successive divisions of the random number
        array = successiveDivisions(randomNumber);

        // Difference between summation and random number
        array = diffSummationRandomNumber(array, randomNumber);

        // Verify if the board has positions with zero values
        array = hasZeros(array, randomNumber);

        // Shuffle the values of array
        array = shuffleArray(array);

        mFirstButton.setText(String.valueOf(array[0]));
        mSecondButton.setText(String.valueOf(array[1]));
        mThirdButton.setText(String.valueOf(array[2]));
        mFourthButton.setText(String.valueOf(array[3]));
        mFifthButton.setText(String.valueOf(array[4]));
        mSixthButton.setText(String.valueOf(array[5]));
        mSeventhButton.setText(String.valueOf(array[6]));
        mEighthButton.setText(String.valueOf(array[7]));
        mNinthButton.setText(String.valueOf(array[8]));

        return array;
    }

    // Successive divisions of the random number
    private int[] successiveDivisions (int randomNumber) {
        int[] array = new int[BOARD];
        array[0] = randomNumber / 2;

        for(int i = 1; i < array.length - 1; i++) {
            int div =  (array[i-1]/ 2);

            if ( div != 1) array[i] = div;
        }

        return array;
    }

    // When difference between summation and random number is sup 1 unity
    private int[] diffSummationRandomNumber(int[] array, int randomNumber) {
        int sum = 0;

        // Summation of values on array
        for (int i : array) {
            sum = sum + i;
        }

        // Difference between summation and random number
        int diff = randomNumber - sum - 1;

        // Test and return the last position where is not zero
        // Add diff in random position of array, if value different of zero
        int i = generateRandomNumber(notZero(array));
        array[i] = array[i] + diff;

        return array;
    }

    // Return the last position is not zero
    private int notZero(int[] array) {
        int notZero = 0;
        for (int i : array) {
            if (i != 0) notZero++;
        }
        return notZero;
    }

    // When the board has zeros
    private int[] hasZeros(int[] array, int randomNumber) {
        for (int i = notZero(array); i < array.length; i++) {

            // Verify if the position really have zero
            if (array[i] == 0) {

                // Generate number to position
                array[i] = (randomNumber + generateRandomNumber(MAX_HAS_ZEROS) + 1) / 2;
            }
        }

        return array;
    }

    // Shuffle the values of array
    private int[] shuffleArray (int[] array) {
        Random r = new Random();

        for (int i = 0; i < array.length; i++) {
            int randomPosition = r.nextInt(array.length);
            int temp = array[i];
            array[i] = array[randomPosition];
            array[randomPosition] = temp;
        }

        return array;
    }

    // When button clicked, subtract corresponding clicked number
    private void subtractNumber(String str) {
        int buttonValue = Integer.parseInt(str);

        String sResult = (String) mResults.getText();
        int results = Integer.parseInt(sResult);

        results = results - buttonValue;

        addScores(results);

        mResults.setText(String.valueOf(results));
    }

    // When results = 1 and timer > 0, add 1 in score
    private void addScores(int results) {
        String sScore = (String) mScores.getText();
        String sTimer = (String) mTimer.getText();

        int scores = Integer.parseInt(sScore);

        boolean matches = sTimer.matches(" 00:00");

        if (results == 1) {
            scores = scores + 1;

            mScores.setText(String.valueOf(scores));

            mRestart.setVisibility(View.VISIBLE);
        }

        if (results < 1 || matches == true) {
            hideButtons();
            mRestart.setVisibility(View.VISIBLE);
        }
    }

    // When results < 1 or time = 0, hide buttons
    private void hideButtons() {
        mFirstButton.setClickable(false);
        mSecondButton.setClickable(false);
        mThirdButton.setClickable(false);
        mFourthButton.setClickable(false);
        mFifthButton.setClickable(false);
        mSixthButton.setClickable(false);
        mSeventhButton.setClickable(false);
        mEighthButton.setClickable(false);
        mNinthButton.setClickable(false);

        mFirstButton.setVisibility(View.INVISIBLE);
        mSecondButton.setVisibility(View.INVISIBLE);
        mThirdButton.setVisibility(View.INVISIBLE);
        mFourthButton.setVisibility(View.INVISIBLE);
        mFifthButton.setVisibility(View.INVISIBLE);
        mSixthButton.setVisibility(View.INVISIBLE);
        mSeventhButton.setVisibility(View.INVISIBLE);
        mEighthButton.setVisibility(View.INVISIBLE);
        mNinthButton.setVisibility(View.INVISIBLE);
    }

    // When start or restart game, show buttons
    // Initialize the behavior of button
    private void showButtons() {
        mFirstButton.setClickable(true);
        mSecondButton.setClickable(true);
        mThirdButton.setClickable(true);
        mFourthButton.setClickable(true);
        mFifthButton.setClickable(true);
        mSixthButton.setClickable(true);
        mSeventhButton.setClickable(true);
        mEighthButton.setClickable(true);
        mNinthButton.setClickable(true);

        mFirstButton.setVisibility(View.VISIBLE);
        mSecondButton.setVisibility(View.VISIBLE);
        mThirdButton.setVisibility(View.VISIBLE);
        mFourthButton.setVisibility(View.VISIBLE);
        mFifthButton.setVisibility(View.VISIBLE);
        mSixthButton.setVisibility(View.VISIBLE);
        mSeventhButton.setVisibility(View.VISIBLE);
        mEighthButton.setVisibility(View.VISIBLE);
        mNinthButton.setVisibility(View.VISIBLE);

        mStart.setVisibility(View.INVISIBLE);
    }
}