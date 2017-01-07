package com.example.yinksb23.thebioquiz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class BioQuiz extends AppCompatActivity {

    private static final String TAG = "BioQuizActivity";
    private static final String KEY_INDEX = "index";

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mCheatButton;
    private Button mQ1Button;
    private Button mQ2Button;
    private Button mQ3Button;
    private Button mQ4Button;
    private Button mQ5Button;
    private TextView mQuestionTextView;
    private Button mSaveButton;
    private Button mShowScoreButton;
    private Button mSaveScoreButton;
    private Button mPassButton; //mpMode button; pressed by Player2 to begin a new quiz

    private Question[] mQuestionBank = new Question[]{

            new Question(R.string.question_1, true, 1),
            new Question(R.string.question_2, false,2),
            new Question(R.string.question_3, false, 3),
            new Question(R.string.question_4, false, 4),
            new Question(R.string.question_5, true, 5),
    };

    private int mCurrentIndex = 0;
    private int nCounter = 0; //keeps track of the number of times the user skips a question
    private int cCounter = 0;
    public int correctCounter = 0;
    public int falseCounter = 0;
    public int totalCounter = 0;
    public int realTotalCounter;
    private static boolean cqPlayer2; //mpMode; this boolean tracks whether P2 has taken their turn  on the quiz

    private void updateQuestion(){
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue){
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

        int messageResId = 0;

        if (userPressedTrue == answerIsTrue) {
            messageResId = R.string.correct_toast;
            correctCounter++;
        } else {
            messageResId = R.string.incorrect_toast;
            falseCounter++;
        }
        totalCounter = correctCounter + falseCounter;

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bio_quiz);

        final DatabaseHelper db = new DatabaseHelper(this);

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        //mQuestionTextView.setVisibility(View.INVISIBLE);

        final TextView tvFinalScore = (TextView)findViewById(R.id.tvFinalScore); //Manipulates final score field

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                checkAnswer(true);
            }
        });

        mFalseButton= (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                checkAnswer(false);
            }
        });

        mQ1Button = (Button)findViewById(R.id.q1_button);
        mQ1Button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mCurrentIndex = 0;
                int question1 = mQuestionBank[mCurrentIndex].getTextResId();
                mQuestionTextView.setText(question1);
                mQ1Button.setVisibility(View.GONE);
            }
        });

        mQ2Button = (Button)findViewById(R.id.q2_button);
        mQ2Button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mCurrentIndex = 1;
                int question2 = mQuestionBank[mCurrentIndex].getTextResId();
                mQuestionTextView.setText(question2);
                mQ2Button.setVisibility(View.GONE);
            }
        });

        mQ3Button = (Button)findViewById(R.id.q3_button);
        mQ3Button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mCurrentIndex = 2;
                int question3 = mQuestionBank[mCurrentIndex].getTextResId();
                mQuestionTextView.setText(question3);
                mQ3Button.setVisibility(View.GONE);
            }
        });

        mQ4Button = (Button)findViewById(R.id.q4_button);
        mQ4Button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mCurrentIndex = 3;
                int question4 = mQuestionBank[mCurrentIndex].getTextResId();
                mQuestionTextView.setText(question4);
                mQ4Button.setVisibility(View.GONE);
            }
        });

        mQ5Button = (Button)findViewById(R.id.q5_button);
        mQ5Button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mCurrentIndex = 4;
                int question5 = mQuestionBank[mCurrentIndex].getTextResId();
                mQuestionTextView.setText(question5);
                mQ5Button.setVisibility(View.GONE);
            }
        });

        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){

                    int lastQuestion = mQuestionBank[mCurrentIndex].getQuestionId();
                    switch (lastQuestion)
                    {
                        case 1:
                            mQ1Button.setVisibility(View.GONE);
                            break;
                        case 2:
                            mQ2Button.setVisibility(View.GONE);
                            break;
                        case 3:
                            mQ3Button.setVisibility(View.GONE);
                            break;
                        case 4:
                            mQ4Button.setVisibility(View.GONE);
                            break;
                        case 5:
                            mQ5Button.setVisibility(View.GONE);
                            break;
                    }
                    mCurrentIndex = (mCurrentIndex+1) % mQuestionBank.length;
                    updateQuestion();
                    nCounter++;

            }

        });

        mCheatButton = (Button)findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //start CheatActivity
                //Intent i = new Intent(BioQuiz.this, CheatActivity.class);
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent i = CheatActivity.newIntent(BioQuiz.this, answerIsTrue);
                startActivity(i);
                cCounter++;
            }
        });

        mShowScoreButton = (Button)findViewById(R.id.showscore_button);
        mShowScoreButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final TextView tvFinalScore = (TextView)findViewById(R.id.tvFinalScore); //Manipulates final score field
                realTotalCounter = totalCounter + nCounter + cCounter;

                if (realTotalCounter >= 5)
                {
                    //Intent scoreIntent = new Intent(BioQuiz.this, RegisterActivity.class);
                    //BioQuiz.this.startActivity(scoreIntent);
                    tvFinalScore.setText(correctCounter + "");
                    //Need to add code here which inputs the Final Score into
                    mNextButton.setVisibility(View.GONE);
                    mFalseButton.setVisibility(View.GONE);
                    mTrueButton.setVisibility(View.GONE);
                    mCheatButton.setVisibility(View.GONE);
                    mSaveScoreButton.setVisibility(View.VISIBLE);
                    mQ5Button.setVisibility(View.GONE);
                    mQuestionTextView.setVisibility(View.GONE);
                }
                else
                {
                    int amessageResId = 0;
                    amessageResId = R.string.answertotal_toast;
                    Toast.makeText(BioQuiz.this, amessageResId, Toast.LENGTH_SHORT)
                            .show();
                }
            }

        });

        mSaveScoreButton = (Button)findViewById(R.id.fSave_button);
        mSaveScoreButton.setVisibility(View.INVISIBLE);
        mSaveScoreButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                //here we are accessing our saved boolean mpMode2; 1P = false and 2P = true;
                SharedPreferences sharedPref3 = getSharedPreferences("userInfo3", Context.MODE_PRIVATE);
                boolean mpMode5 = sharedPref3.getBoolean("mpMode2", false);

                    if (mpMode5) {//Our user chose mpMode
                        if(cqPlayer2)//Has P2 completed their turn on the quiz? If yes, the code below is executed
                        {
                            Log.d(TAG, "Player 2 has completed the quiz..."); //confirms P2 has completed the quiz
                            SharedPreferences sharedPref4 = getSharedPreferences("userInfo3", Context.MODE_PRIVATE);
                            int score1 = sharedPref4.getInt("Score1", 0); //we retrieve the score for player1
                            int score2 = correctCounter; //we save the current score as this is for player 2

                            String userOne = "Player1 scored " + score1; //create a string for P1
                            String userTwo = "Player2 scored " + score2; //create a string for P2

                            Log.d(TAG, userOne); //print to console
                            Log.d(TAG, userTwo); //print to console

                        }
                        else {//Our user has selected mpMode but P2 hasn't had their turn; it is time for P2 to begin the quiz
                            Log.d(TAG, "Welcome to multiplayer mode...");
                            mSaveScoreButton.setVisibility(View.GONE);
                            mShowScoreButton.setVisibility(View.GONE);
                            mPassButton.setVisibility(View.VISIBLE); //This button is described below

                            SharedPreferences sharedPref4 = getSharedPreferences("userInfo3", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref4.edit();
                            editor.putInt("Score1", correctCounter); //P1 has just finished our quiz; their score is stored here
                            editor.apply();
                        }

                    } else {//our user has chosen 1P mode; their score will be added to the database and their leaderboard

                        //IT WORKS; I'm using the sharedpreferences fxn to access the username of the active user
                        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                        String spUname = sharedPref.getString("username", "");

                        //IT WORKS; using the username stored in spUname I can the pull up the specific record associated with said user
                        Contact retrievedUser = db.getContact(spUname);
                        Log.d(TAG, "Inserting attempts...");
                        //IT WORKS; using that record I've pulled up, I'm going to pass the unique email and score into the Scores class
                        //IT WORKS; I'll then take that info and add it into the 'scores' database - I'm saving the score for this attempt
                        db.addScore(new Scores(retrievedUser.getEmail(), correctCounter));

                        //IT WORKS; I'm using the sharedpreferences fxn to access the email of the active user; then store it in a string
                        SharedPreferences sharedPref1 = getSharedPreferences("userInfo1", Context.MODE_PRIVATE);
                        String spEmail = sharedPref1.getString("email", "");

                        //Being tested; IT FUCKING WORKS!
                        Log.d(TAG, "Returning Ayo's scores...");
                        //IT WORKS; I'm then passing the active user's email and uname into the publishLeaderboard method
                        String ayo2 = db.publishLeaderboard(spEmail, spUname);
                        Log.d(TAG, ayo2);
                    }
                }
        });

        //This button appears only if the user has selected multiplayer and P1 has completed their turn on the quiz
        mPassButton = (Button)findViewById(R.id.multiplayer_button);
        mPassButton.setVisibility(View.INVISIBLE); //unless told, this button should be invisible
        mPassButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){

                    //We are now restarting the quiz and P2 is in  control
                    Log.d(TAG, "Welcome to the pass button...");
                    Intent uWelcomeIntent = new Intent(BioQuiz.this, UserWelcomeActivity.class);
                    uWelcomeIntent.putExtra("Username", "Player2 aka the Challenger");
                    BioQuiz.this.startActivity((uWelcomeIntent));
                    cqPlayer2 = true; //this Boolean is set to true; it tells us that Player 2 is in charge now
            }

        });

        if (savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }

        updateQuestion();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
    }

}
