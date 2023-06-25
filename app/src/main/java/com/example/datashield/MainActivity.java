package com.example.datashield;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ImageView shield;
    TextView welcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shield = (ImageView) findViewById(R.id.shield);
        welcome = (TextView) findViewById(R.id.welcome);

        shield.animate().alpha(0f).setDuration(0);
        welcome.animate().alpha(0f).setDuration(0);

        shield.animate().alpha(1f).setDuration(1000).setListener(new AnimatorListenerAdapter() {
            /**
             * {@inheritDoc}
             *
             * @param animation
             */
            @Override
            public void onAnimationEnd(Animator animation) {
                welcome.animate().alpha(1f).setDuration(950);

            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                Intent intent = new Intent(MainActivity.this, login.class);
                startActivity(intent);
                finish();
            }


        }, 3000);

    }
}


