package com.example.android.cs3218thearith.cs3218thearith.Activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.android.cs3218thearith.cs3218thearith.R;


public class CalculatorActivity extends ActionBarActivity
        implements View.OnClickListener{

    private static final String[][] buttonTexts = {
            {"AC", "C", "\u221a", "+"},
            {"7", "8", "9", "-"},
            {"4", "5", "6", "x"},
            {"1", "2", "3", "/"},
            {"0", ".", "="},
            {"sin", "cos", "tan", "cot"}
    };

    private static final int EQUAL_ROW = buttonTexts.length-2;
    private static final int EQUAL_COL = buttonTexts[EQUAL_ROW].length-1;

    private float result;
    private String currentOperator;
    private float currentNumber;
    private boolean decimalPressed;
    private int decimalPlace;
    private int maxDecimalPlaces = 6;

    private TextView outputTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        outputTextView = (TextView) findViewById(R.id.outputTextView);
        TableLayout buttonsTableLayout = (TableLayout) findViewById(R.id.buttonsTableLayout);
        createTableLayout(buttonsTableLayout);

        result = 0;
        currentOperator = "";
        currentNumber = 0;

        decimalPressed = false;
        decimalPlace = 0;
    }

    private void createTableLayout(TableLayout buttonsTableLayout) {
        for(int i=0; i<buttonTexts.length; i++) {
            TableRow row = new TableRow(this);
            TableLayout.LayoutParams tableLP = new TableLayout.LayoutParams();
            tableLP.leftMargin = 30;
            tableLP.rightMargin = 30;
            tableLP.bottomMargin = 20;
            tableLP.rightMargin = 20;
            row.setLayoutParams(tableLP);
            for(int j=0; j<buttonTexts[i].length; j++) {
                Button button = new Button(this);
                button.setText(buttonTexts[i][j]);
                TableRow.LayoutParams lp = new TableRow.LayoutParams();
                lp.weight = (i==EQUAL_ROW && j==EQUAL_COL) ? 0.50f : 0.25f;
                button.setLayoutParams(lp);
                button.setOnClickListener(this);
                row.addView(button);
            }

            buttonsTableLayout.addView(row, i);
        }
    }

    private void dotOnClick(){
        decimalPressed = true;
        decimalPlace = 0;
    }

    private void digitsOnClick(View view){
        float numberPressed = Float.parseFloat(((Button)
                view).getText().toString());
        if (decimalPressed) {
            decimalPlace++;
            if (decimalPlace < maxDecimalPlaces) {
                float decimals = (float) (numberPressed / Math.pow(10.0,
                        decimalPlace));
                currentNumber = currentNumber + decimals;
                String displayStr = String.format("%." + decimalPlace +
                        "f",currentNumber);
                outputTextView.setText(displayStr);
            }
        } else {
            currentNumber = currentNumber * 10 + numberPressed;
            outputTextView.setText(String.valueOf(currentNumber));
        }
        if(currentOperator.equals("=")) result = currentNumber;
    }

    private void operatorOnClick(View view){
        if(currentOperator == ""){
            result = currentNumber;
        }else{
            if(currentOperator.equals("+")) result += currentNumber;
            else if(currentOperator.equals("-")) result -= currentNumber;
            else if(currentOperator.equals("x")) result *= currentNumber;
            else if(currentOperator.equals("/")) result /= currentNumber;
        }
        System.out.printf("result:%f \n",result);
        currentOperator = ((Button) view).getText().toString();
        if (currentOperator.equals("=")) {
            outputTextView.setText(String.valueOf(result));
        } else {
            String displayStr = String.format("%." + decimalPlace + "f", result);
            outputTextView.setText(displayStr);
        }
        currentNumber = 0;
        decimalPressed = false;
        decimalPlace = 0;
    }

    private void mathOpOnClick(View v) {
        String op = ((Button)v).getText().toString();
        switch(op) {
            case "√":
                result = (float)Math.sqrt(currentNumber);
                break;

            case "sin":
                result = (float)Math.sin(Math.toRadians(currentNumber));
                break;

            case "cos":
                result = (float)Math.cos(Math.toRadians(currentNumber));
                break;

            case "tan":
                result = (float)Math.tan(Math.toRadians(currentNumber));
                break;

            case "cot":
                result = 1/(float)Math.tan(Math.toRadians(currentNumber));
                break;

            default:
                break;
        }

        outputTextView.setText(String.valueOf(result));
        currentNumber = result;
        decimalPressed = false;
        decimalPlace = 0;
    }

    private void clear(){
        decimalPressed = false;
        decimalPlace = 0;
        currentNumber = 0;
        outputTextView.setText(String.valueOf(currentNumber));
    }

    private void allClear(){
        decimalPressed = false;
        decimalPlace = 0;
        currentNumber = 0;
        currentOperator = "";
        result = 0;
        outputTextView.setText(String.valueOf(result));
    }

    public void onClick(View v) {
        String text = ((Button)v).getText().toString();
        switch(text) {

            case "AC":
                allClear();
                break;

            case "C":
                clear();
                break;

            case "sin":
            case "cos":
            case "tan":
            case "cot":
            case "√":
                mathOpOnClick(v);
                break;

            case ".":
                dotOnClick();
                break;

            case "+":
            case "-":
            case "x":
            case "/":
            case "=":
                operatorOnClick(v);
                break;

            default:
                digitsOnClick(v);
                break;
        }
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
