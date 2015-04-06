package com.example.android.cs3218thearith.cs3218thearith.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.example.android.cs3218thearith.cs3218thearith.R;
import com.example.android.cs3218thearith.cs3218thearith.Sampler.SoundSampler;
import com.example.android.cs3218thearith.cs3218thearith.SurfaceView.CSurfaceViewSound;

public class SoundActivity extends Activity {
    public  static CSurfaceViewSound surfaceView;
    private SoundSampler soundSampler;
    public  static short[]  buffer;
    public  static int      bufferSize;     // in bytes

    public void goToMainActivity(View view){
        try
        {
            CSurfaceViewSound.drawFlag = Boolean.valueOf(false);
            surfaceView.drawThread.join();
            soundSampler.stop();
        }
        catch (InterruptedException localInterruptedException)
        {
        }

        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound);

        try {
            soundSampler = new SoundSampler(this);

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Cannot instantiate SoundSampler", Toast.LENGTH_LONG).show();
        }

        try {
            soundSampler.init();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),"Cannot initialize SoundSampler.", Toast.LENGTH_LONG).show();
        }

        surfaceView = (CSurfaceViewSound)findViewById(R.id.surfaceView);
        surfaceView.drawThread.setBuffer(buffer);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }


    public void captureSound(View v) {
        if (surfaceView.drawThread.soundCapture) {
            surfaceView.drawThread.soundCapture = Boolean.valueOf(false);
            surfaceView.drawThread.segmentIndex = -1;
        }
        else {
            surfaceView.drawThread.soundCapture = Boolean.valueOf(true);

        }
    }

}
