package com.harolddleon.myapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends Activity implements TextWatcher, OnSeekBarChangeListener{

    //declare your variables for the widgets
    private EditText editTextBillAmount;
    private TextView textViewPercentageAmount;
    private TextView textViewTipAmount;
    private TextView textViewTotalAmount;

    //declare the variables for the calculations
    private double billAmount = 0.0;
    private double totalAmount = 0.0;
    private double percent = 0.15;
    private double tip = 0.0;


    //set the number formats to be used for the $ amounts , and % amounts
    private static final NumberFormat currencyFormat =
            NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat =
            NumberFormat.getPercentInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SeekBar seekBar = (findViewById(R.id.SeekBarView));
        seekBar.setOnSeekBarChangeListener(this);

        //add Listeners to Widgets
        editTextBillAmount = findViewById(R.id.editText_BillAmount);
        editTextBillAmount.addTextChangedListener(this);


        textViewPercentageAmount = findViewById(R.id.textView_TipSliderAmount);
        textViewTipAmount = findViewById(R.id.textView_TipAmount);
        textViewTotalAmount = findViewById(R.id.textView_TotalAmount);


    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    /*
    Note:   int i, int i1, and int i2
            represent start, before, count respectively
            The charSequence is converted to a String and parsed to a double for you
     */
    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        Log.d("MainActivity", "inside onTextChanged method: charSequence= "+charSequence);
        //surround risky calculations with try catch (what if billAmount is 0 ?
        //charSequence is converted to a String and parsed to a double for you

        try {
            billAmount = Double.parseDouble(charSequence.toString());
            Log.d("MainActivity", "Bill Amount = " + billAmount);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            billAmount = (double) 0;
        }
        //perform tip and total calculation and update UI by calling calculate
        calculate();
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        percent = ((double) progress/100);
        textViewPercentageAmount.setText(percentFormat.format(percent));
        calculate();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        int c_progress = seekBar.getProgress();

        if(c_progress > 40){
            Toast.makeText(this, "Feeling a little generous? \uD83D\uDC40", Toast.LENGTH_SHORT).show();
        }
        else if(c_progress < 10) {
            Toast.makeText(this, "That's a little too... little, isn't it? \ud83d\ude12", Toast.LENGTH_SHORT).show();
        }

    }


    // calculate and display tip and total amounts
    private void calculate() {
        Log.d("MainActivity", "inside calculate method");

        // calculate the tip and total
        tip = billAmount * percent;

        //  display tip and total formatted as currency
        // user currencyFormat instead of percentFormat to set the textViewTip
        textViewTipAmount.setText(currencyFormat.format(tip));

        totalAmount = billAmount + tip;
        textViewTotalAmount.setText(currencyFormat.format(totalAmount));

    }
}
