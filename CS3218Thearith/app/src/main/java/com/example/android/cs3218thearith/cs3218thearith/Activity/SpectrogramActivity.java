package com.example.android.cs3218thearith.cs3218thearith.Activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.android.cs3218thearith.cs3218thearith.R;
import com.example.android.cs3218thearith.cs3218thearith.Sampler.SoundSamplerSpectrogram;
import com.example.android.cs3218thearith.cs3218thearith.SurfaceView.CSurfaceViewSpectrogram;


public class SpectrogramActivity extends ActionBarActivity {

    public static CSurfaceViewSpectrogram surfaceView;
    public SoundSamplerSpectrogram soundSampler;
    public static int bufferSize;
    public static short[] buffer;

    public void goToMainActivity (View v) {

        try {
            CSurfaceViewSpectrogram.drawFlag = false;
            surfaceView.drawThread.join();
            soundSampler.stop();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        finish();

    }

    public void captureSoundSpectrogram (View v) {
        if(surfaceView.drawThread.soundCapture) {
               surfaceView.drawThread.soundCapture = Boolean.valueOf(false);
            surfaceView.drawThread.segmentIndex = -1;
            ((Button) v).setText("Capture Spectrogram");
        } else {
            surfaceView.drawThread.soundCapture = Boolean.valueOf(true);
            ((Button) v).setText("Pause Spectrogram");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spectrogram);

        try {
            soundSampler = new SoundSamplerSpectrogram(this);
        } catch(Exception e) {
            Log.e("LiveFFTActivity", "SoundSamplerLiveFFT cannot be instantiated");
        }

        try {
            soundSampler.init();
        } catch(Exception e) {
            Log.e("LiveFFTActivity", "SoundSamplerLiveFFT cannot be initialized");
        }

        surfaceView = (CSurfaceViewSpectrogram) findViewById(R.id.surfaceView);
        surfaceView.drawThread.setBuffer(buffer);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_spectrogram, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
