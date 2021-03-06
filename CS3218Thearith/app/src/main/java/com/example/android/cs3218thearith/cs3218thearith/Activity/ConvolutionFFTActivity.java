package com.example.android.cs3218thearith.cs3218thearith.Activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.android.cs3218thearith.cs3218thearith.R;
import com.example.android.cs3218thearith.cs3218thearith.SurfaceView.CSurfaceViewConvolution;
import com.example.android.cs3218thearith.cs3218thearith.SurfaceView.CSurfaceViewConvolutionFFT;


public class ConvolutionFFTActivity extends ActionBarActivity {

    private CSurfaceViewConvolutionFFT surfaceView;

    public void goToMainActivity(View view) {

        try
        {
            CSurfaceViewConvolutionFFT.drawFlag = Boolean.valueOf(false);
            surfaceView.drawThread.join();
        }
        catch (InterruptedException localInterruptedException)
        {
        }

        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convolution_fft);

        surfaceView = (CSurfaceViewConvolutionFFT) findViewById(R.id.surfaceView);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_convolution_fft, menu);
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
