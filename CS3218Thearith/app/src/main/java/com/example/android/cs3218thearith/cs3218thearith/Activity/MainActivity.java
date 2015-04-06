package com.example.android.cs3218thearith.cs3218thearith.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.android.cs3218thearith.cs3218thearith.R;


public class MainActivity extends ActionBarActivity
    implements View.OnClickListener{

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int CALCULATOR_INDEX = 0;
    private static final int GRAPHING_INDEX = CALCULATOR_INDEX + 1;
    private static final int SOUND_INDEX = GRAPHING_INDEX + 1;
    private static final int CALCULUS_INDEX = SOUND_INDEX + 1;
    private static final int FFT_INDEX = CALCULUS_INDEX + 1;
    private static final int LIVE_FFT_INDEX = FFT_INDEX + 1;
    private static final int SPECTROGRAM_INDEX = LIVE_FFT_INDEX + 1;
    private static final int CONVOLUTION_INDEX = SPECTROGRAM_INDEX + 1;
    private static final int CONVOLUTION_FFT_INDEX = CONVOLUTION_INDEX + 1;
    private static final int CORRELATION_INDEX = CONVOLUTION_FFT_INDEX + 1;
    private static final int CORRELATION_FFT_INDEX = CORRELATION_INDEX + 1;


    private Class[] activities = {CalculatorActivity.class, GraphActivity.class,
            SoundActivity.class, CalculusActivity.class,
            FFTActivity.class, LiveFFTActivity.class, SpectrogramActivity.class,
            ConvolutionActivity.class, ConvolutionFFTActivity.class,
            CorrelationActivity.class, CorrelationFFTActivity.class};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button calculatorButton = (Button) findViewById(R.id.goToCalculator);
        calculatorButton.setOnClickListener(this);

        Button graphingButton = (Button) findViewById(R.id.goToGraphing);
        graphingButton.setOnClickListener(this);

        Button soundButton = (Button) findViewById(R.id.goToSound);
        soundButton.setOnClickListener(this);

        Button calculusButton = (Button) findViewById(R.id.goToCalculus);
        calculusButton.setOnClickListener(this);

        Button fftButton = (Button) findViewById(R.id.goToFFT);
        fftButton.setOnClickListener(this);

        Button liveFFTButton = (Button) findViewById(R.id.goToLiveFFT);
        liveFFTButton.setOnClickListener(this);

        Button spectrogramButton = (Button) findViewById(R.id.goToSpectrogram);
        spectrogramButton.setOnClickListener(this);

        Button convolutionButton = (Button) findViewById(R.id.goToConvolution);
        convolutionButton.setOnClickListener(this);

        Button convolutionFFTButton = (Button) findViewById(R.id.goToConvolutionFFT);
        convolutionFFTButton.setOnClickListener(this);

        Button correlationButton = (Button) findViewById(R.id.goToCorrelation);
        correlationButton.setOnClickListener(this);

        Button correlationFFTButton = (Button) findViewById(R.id.goToCorrelationFFT);
        correlationFFTButton.setOnClickListener(this);
    }

    public void onClick(View v) {
        int index = CALCULATOR_INDEX;
        switch(v.getId()) {
            case R.id.goToCalculator:
                index = CALCULATOR_INDEX;
                break;
            case R.id.goToGraphing:
                index = GRAPHING_INDEX;
                break;
            case R.id.goToSound:
                index = SOUND_INDEX;
                break;
            case R.id.goToCalculus:
                index = CALCULUS_INDEX;
                break;
            case R.id.goToFFT:
                index = FFT_INDEX;
                break;
            case R.id.goToLiveFFT:
                index = LIVE_FFT_INDEX;
                break;
            case R.id.goToSpectrogram:
                index = SPECTROGRAM_INDEX;
                break;
            case R.id.goToConvolution:
                index = CONVOLUTION_INDEX;
                break;
            case R.id.goToConvolutionFFT:
                index = CONVOLUTION_FFT_INDEX;
                break;
            case R.id.goToCorrelation:
                index = CORRELATION_INDEX;
                break;
            case R.id.goToCorrelationFFT:
                index = CORRELATION_FFT_INDEX;
                break;
            default:
                Log.e(TAG, "button not recognized");
                break;
        }

        //go to activity
        Intent intent = new Intent(this, activities[index]);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
