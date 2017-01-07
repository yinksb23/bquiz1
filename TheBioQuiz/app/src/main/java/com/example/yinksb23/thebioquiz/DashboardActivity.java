package com.example.yinksb23.thebioquiz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DashboardActivity extends AppCompatActivity {

    DatabaseHelper helper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        final Button b1Player = (Button)findViewById(R.id.oneplayer_button); //my 1P button
        final Button b2Player = (Button)findViewById(R.id.twoplayer_button); //my 2P button

        b1Player.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                //retrieves the username stored using sharedpreferences
                SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                String spUname3 = sharedPref.getString("username", "");

                //makes the username of the user available to WelcomeActivity
                Intent uWelcomeIntent = new Intent(DashboardActivity.this, UserWelcomeActivity.class);
                uWelcomeIntent.putExtra("Username", spUname3);
                DashboardActivity.this.startActivity((uWelcomeIntent));

                //By choosing the 1P button the user has confirmed their decision to not enter multiplayer; stored as a boolean
                final boolean mpMode = false;
                SharedPreferences sharedPref3 = getSharedPreferences("userInfo3", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref3.edit();
                editor.putBoolean("mpMode2", mpMode);
                editor.apply();
            }
        });

        b2Player.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                //retrieves the username stored using sharedpreferences
                SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                String spUname3 = sharedPref.getString("username", "");

                //makes the username of the user available to WelcomeActivity
                Intent uWelcomeIntent = new Intent(DashboardActivity.this, UserWelcomeActivity.class);
                uWelcomeIntent.putExtra("Username", spUname3);
                DashboardActivity.this.startActivity((uWelcomeIntent)); //makes the username of the user available to WelcomeActivity

                //By choosing the 2P button the user has confirmed their decision to enter multiplayer; stored as a boolean
                final boolean mpMode = true;
                SharedPreferences sharedPref3 = getSharedPreferences("userInfo3", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref3.edit();
                editor.putBoolean("mpMode2", mpMode);
                editor.apply();

            }
        });



    }
}
