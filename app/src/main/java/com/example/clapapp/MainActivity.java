package com.example.clapapp;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity  implements SensorEventListener {

    private SensorManager sensormanager;
    private Sensor sensor;
    private MediaPlayer makesound;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("onCreate", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensormanager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensormanager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        makesound = MediaPlayer.create(this, R.raw.chime);
        makesound.start();
        makesound.setLooping(true);
        ToggleButton toggleButton = (ToggleButton) findViewById(R.id.toggleButton);
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (makesound.isPlaying()) {
                    makesound.pause();
                } else {
                    makesound.start();
                    makesound.setVolume(1.0f, 1.0f);
                }
            }
        });
    }


    @Override
    protected void onResume() {

        super.onResume();

        sensormanager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {

        super.onPause();
        sensormanager.unregisterListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (makesound.isPlaying()) {
            makesound.stop();
        }
    }


    @Override
    public final void onSensorChanged(SensorEvent event) {
        if (makesound.isPlaying()) {
            float distance = event.values[0];
            float volume = 0.0f;
            if (distance > 0) {
                volume = 1.0f;
            }
            makesound.setVolume(volume, volume);
        }
        }


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public void onClickButton(View view ) {
        if (makesound.isPlaying()) {
            makesound.stop();
        } else {
            makesound.start();
        }
    }
}

