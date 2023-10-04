package com.example.stopwatchapplication;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    int hours,
            minutes,
            secs,
            ms;

    private int seconds = 0;

    private boolean running;

    int lapCount = 0;

    ImageView playBtn,
            pauseBtn,
            stopBtn,
            timeLapseBtn;
    TextView timeView;
    TextView timeViewms;
    TextView timeLapse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playBtn = (ImageView) findViewById(R.id.playBtn);
        pauseBtn = (ImageView) findViewById(R.id.pauseBtn);
        stopBtn = (ImageView) findViewById(R.id.stopBtn);

        timeLapseBtn = (ImageView) findViewById(R.id.timeLapseBtn);

        timeView = (TextView) findViewById(R.id.time_view);
        timeViewms = (TextView) findViewById(R.id.time_view_ms);
        timeLapse = (TextView) findViewById(R.id.timeLapse);

        playBtn.setOnClickListener(new View.OnClickListener() {@Override
        public void onClick(View view) {

            Toast.makeText(MainActivity.this, "Started", Toast.LENGTH_SHORT).show();

            playBtn.setVisibility(View.GONE);
            stopBtn.setVisibility(View.GONE);

            pauseBtn.setVisibility(View.VISIBLE);
            timeLapseBtn.setVisibility(View.VISIBLE);

            running = true;
        }
        });
        pauseBtn.setOnClickListener(new View.OnClickListener() {@Override
        public void onClick(View view) {

            Toast.makeText(MainActivity.this, "Paused", Toast.LENGTH_SHORT).show();

            playBtn.setVisibility(View.VISIBLE);
            stopBtn.setVisibility(View.VISIBLE);

            timeLapseBtn.setVisibility(View.GONE);
            pauseBtn.setVisibility(View.GONE);

            running = false;
        }
        });

        stopBtn.setOnClickListener(new View.OnClickListener() {@Override
        public void onClick(View view) {

            Toast.makeText(MainActivity.this, "Stoped", Toast.LENGTH_SHORT).show();

            running = false;
            seconds = 0;
            lapCount = 0;

            timeView.setText("00:00:00");
            timeViewms.setText("00");
            timeLapse.setText("");

            playBtn.setVisibility(View.VISIBLE);

            pauseBtn.setVisibility(View.GONE);
            stopBtn.setVisibility(View.GONE);
            timeLapseBtn.setVisibility(View.GONE);

        }
        });

        timeLapseBtn.setOnClickListener(new View.OnClickListener() {@Override
        public void onClick(View view) {
            // calling timeLapse function
            timeLapseFun();
        }
        });

        runTimer();

    }

    private void runTimer() {

        final Handler handlertime = new Handler();

        final Handler handlerMs = new Handler();

        handlertime.post(new Runnable() {@Override

        public void run() {
            hours = seconds / 3600;
            minutes = (seconds % 3600) / 60;
            secs = seconds % 60;

            if (running) {
                String time = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, secs);

                timeView.setText(time);

                seconds++;
            }

            handlertime.postDelayed(this, 1000);
        }
        });

        handlerMs.post(new Runnable() {@Override
        public void run() {

            if (ms >= 99) {
                ms = 0;
            }

            if (running) {
                String msString = String.format(Locale.getDefault(), "%02d", ms);
                timeViewms.setText(msString);

                ms++;
            }
            handlerMs.postDelayed(this, 1);
        }
        });

    }

    void timeLapseFun() {

        lapCount++;

        String laptext = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, secs);
        String msString = String.format(Locale.getDefault(), "%02d", ms);

        laptext = laptext + ":" + msString;

        if (lapCount >= 10) {
            laptext = " Lap " + lapCount + " ------------->       " + laptext + " \n " + timeLapse.getText();
        } else {
            laptext = " Lap " + lapCount + " --------------->       " + laptext + " \n " + timeLapse.getText();

        }

        Toast.makeText(MainActivity.this, "Lap " + lapCount, Toast.LENGTH_SHORT).show();

        timeLapse.setText(laptext);
    }
}