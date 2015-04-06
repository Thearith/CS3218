package com.example.android.cs3218thearith.cs3218thearith.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.android.cs3218thearith.cs3218thearith.R;
import com.example.android.cs3218thearith.cs3218thearith.SurfaceView.CSurfaceViewGraph;


public class GraphActivity extends Activity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private String   trigo;
    private double   sineAmplitude;
    private double   cosineAmplitude;
    private double   sineFrequency;
    private double   cosineFrequency;
    private double   sinePhase;
    private double   cosinePhase;
    private TextView displayFunction;

    public CSurfaceViewGraph surfaceView;
    public  static short[]  buffer;
    public  static int      bufferSize;     // in bytes

    private static final String[][] buttonTexts = {
            {"Sine", "Cosine"},
            {"A+", "A-", "P+", "P-"},
            {"F+", "F-"},
            {"Back to Main"}
    };

    public void goToMainActivity() {

        try
        {
            CSurfaceViewGraph.drawFlag = Boolean.valueOf(false);
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
        setContentView(R.layout.activity_graphing);
        displayFunction = (TextView)findViewById(R.id.displayFunction);
        trigo = "sine";
        sineAmplitude=0.0;
        cosineAmplitude=0.0;
        sineFrequency=0.0;
        cosineFrequency=0.0;
        sinePhase = 0.0;
        cosinePhase=0.0;
        bufferSize = 1024;
        buffer = new short[bufferSize];
        surfaceView = (CSurfaceViewGraph)findViewById(R.id.surfaceView);

        //create table layout and buttons
        TableLayout table = (TableLayout) findViewById(R.id.table);

        for(int i=0; i<buttonTexts.length; i++) {
            TableRow row = new TableRow(this);

            for(int j=0; j<buttonTexts[i].length; j++) {
                Button button = new Button(this);
                button.setText(buttonTexts[i][j]);
                TableRow.LayoutParams lp = new TableRow.LayoutParams();
                if(buttonTexts[i].length > 1) {
                    lp.weight= (float)1/buttonTexts[i].length;
                } else {
                    lp.weight = 0.75f;
                }

                button.setLayoutParams(lp);
                button.setOnClickListener(this);
                row.addView(button);
            }

            table.addView(row, i);
        }
    }

    public void onClick(View view) {
        String text = ((Button) view).getText().toString();

        switch(text){

            case "Sine":
                sinePressed();
                break;

            case "Cosine":
                cosinePressed();
                break;

            case "A+":
                AplusPressed();
                break;

            case "A-":
                AminusPressed();
                break;

            case "P+":
                PplusPressed();
                break;

            case "P-":
                PminusPressed();
                break;

            case "F+":
                FplusPressed();
                break;

            case "F-":
                FminusPressed();
                break;

            case "Back to Main":
                goToMainActivity();
                break;

            default:
                Log.e(TAG, "button not recognized");
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void sinePressed() {
        trigo = "sine";
    }

    private void cosinePressed() {
        trigo = "cosine";
    }

    private void AplusPressed(){

        if (trigo.equalsIgnoreCase("sine")) {
            sineAmplitude += 1.0;

        } else if (trigo.equalsIgnoreCase("cosine")) {
            cosineAmplitude += 1.0;
        }

        composeSignal();

    }

    private void AminusPressed(){

        if (trigo.equalsIgnoreCase("sine")) {
            sineAmplitude -= 1.0;

        } else if (trigo.equalsIgnoreCase("cosine")) {
            cosineAmplitude -= 1.0;
        }

        composeSignal();


    }

    private void FplusPressed(){

        if (trigo.equalsIgnoreCase("sine")) {
            sineFrequency += 1.0;

        } else if (trigo.equalsIgnoreCase("cosine")) {
            cosineFrequency += 1.0;
        }

        composeSignal();

    }

    private void FminusPressed(){

        if (trigo.equalsIgnoreCase("sine")) {
            sineFrequency -= 1.0;

        } else if (trigo.equalsIgnoreCase("cosine")) {
            cosineFrequency -= 1.0;
        }

        composeSignal();

    }

    private void PplusPressed(){

        if (trigo.equalsIgnoreCase("sine")) {
            sinePhase += 1.0;

        } else if (trigo.equalsIgnoreCase("cosine")) {
            cosinePhase += 1.0;
        }

        composeSignal();

    }

    private void PminusPressed(){

        if (trigo.equalsIgnoreCase("sine")) {
            sinePhase -= 1.0;

        } else if (trigo.equalsIgnoreCase("cosine")) {
            cosinePhase -= 1.0;
        }

        composeSignal();

    }


    private void composeSignal() {

        displayFunction.setText(String.valueOf(sineAmplitude) + " sin( 2π" +
                String.valueOf(sineFrequency) + " + " + String.valueOf(sinePhase) + " )" + " + " +
                String.valueOf(cosineAmplitude) + " cos( 2π" +
                String.valueOf(cosineFrequency) + " + " + String.valueOf(cosinePhase) + " )");

        // plot 1 second duration of signal
        double radStepSine    = 2*Math.PI*sineFrequency   / (double)bufferSize;
        double radStepCosine  = 2*Math.PI*cosineFrequency / (double)bufferSize;
        double freqSine       = 0.0;
        double freqCosine     = 0.0;
        double sinePhaseRad   = sinePhase*Math.PI/180;
        double cosinePhaseRad = cosinePhase*Math.PI/180;
        for (int i=0; i<bufferSize; i++) {
            buffer[i] = (short)(sineAmplitude*Math.sin(freqSine + sinePhaseRad) + cosineAmplitude*Math.cos(freqCosine + cosinePhaseRad));
            freqSine   += radStepSine;
            freqCosine += radStepCosine;
        }
        surfaceView.drawThread.setBuffer(buffer);
    }


}
