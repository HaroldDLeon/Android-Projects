package com.harolddleon.tipcalculator;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import android.widget.SeekBar.OnSeekBarChangeListener;

import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnSeekBarChangeListener, TextWatcher, RadioGroup.OnCheckedChangeListener, NumberPicker.OnValueChangeListener, AlertDialog.OnClickListener {

    private static final NumberFormat currencyFormat =
            NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat =
            NumberFormat.getPercentInstance();

    private EditText editTextBillAmount;
    private TextView tvPercentage;
    private TextView tvTip;
    private TextView tvTotal;
    private TextView tvPerPerson;
    private RadioGroup radioGroup;
    private AlertDialog.Builder builder;
    private Intent intent;

    private int guestAmount = 1;
    private int currentRadioButton;
    private double billAmount = 0.0;
    private double totalAmount = 0.0;
    private double percent = 0.15;
    private double tipAmount = 0.0;
    private double perPersonAmount = 0.00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar main_toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(main_toolbar);

        SeekBar seekBar = (findViewById(R.id.SeekBarView));
        seekBar.setOnSeekBarChangeListener((SeekBar.OnSeekBarChangeListener) this);

        editTextBillAmount = findViewById(R.id.billAmount);
        editTextBillAmount.addTextChangedListener((TextWatcher) this);

        tvPercentage = findViewById(R.id.tipSliderAmount);
        tvTip = findViewById(R.id.tipAmount);
        tvTotal = findViewById(R.id.totalAmount);

        tvPerPerson = findViewById(R.id.totalPerPersonAmount);

        radioGroup = findViewById(R.id.radioGroup_rounding);
        radioGroup.setOnCheckedChangeListener(this);
        radioGroup.check(R.id.radioButton_none);
        currentRadioButton = radioGroup.getCheckedRadioButtonId();

        NumberPicker numberPicker = (NumberPicker) findViewById(R.id.numberPicker_guests);
        numberPicker.setOnValueChangedListener(this);
        initializeNumberPicker(numberPicker);
    }

    private void initializeNumberPicker(NumberPicker np) {
        np.setMinValue(1);
        np.setMaxValue(10);
        np.setWrapSelectorWheel(false);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_info:
                showPhoneInput();
                return true;
            case R.id.action_share:
                shareIntent();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        try {
            billAmount = Double.parseDouble(charSequence.toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            billAmount = (double) 0;
        }
        calculate();
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    private void showPhoneInput() {
        builder = new AlertDialog.Builder(this);
        String info_message = getString(R.string.mainInfo);
        builder.setTitle("Info sample");
        builder.setMessage(info_message);
        builder.setPositiveButton("Got it", this);
        builder.show();
    }

    private void shareIntent() {
        String message = String.format(Locale.US, "Hey, the bill comes out at %s, tip of %s, total of %s, which is %s per person.",
                billAmount, tvTip.getText().toString(), tvTotal.getText().toString(), currencyFormat.format(perPersonAmount)
        );
        intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        intent.setType("text/plain");
        startActivity(Intent.createChooser(intent, "Use an app"));
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        percent = ((double) progress / 100);
        tvPercentage.setText(percentFormat.format(percent));
        calculate();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        int c_progress = seekBar.getProgress();

        if (c_progress > 40) {
            Toast.makeText(this, "Feeling a little generous? \uD83D\uDC40", Toast.LENGTH_SHORT).show();
        } else if (c_progress < 10) {
            Toast.makeText(this, "That's a little too... little, isn't it? \ud83d\ude12", Toast.LENGTH_SHORT).show();
        }

    }

    private void roundValue(int buttonID) {
        tipAmount = billAmount * percent;
        totalAmount = billAmount + tipAmount;
        switch (buttonID) {
            case R.id.radioButton_tip:
                tipAmount = Math.ceil(tipAmount);
                totalAmount = billAmount + tipAmount;
                break;
            case R.id.radioButton_total:
                totalAmount = Math.ceil(totalAmount);
                break;
        }
        perPersonAmount = totalAmount / guestAmount;
        updateValues();
    }

    private void updateValues() {
        tvTip.setText(currencyFormat.format(tipAmount));
        tvTotal.setText(currencyFormat.format(totalAmount));
        tvPerPerson.setText(currencyFormat.format(perPersonAmount));
    }

    private void calculate() {
        roundValue(currentRadioButton);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        roundValue(checkedId);
        currentRadioButton = radioGroup.getCheckedRadioButtonId();
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        guestAmount = newVal;
        calculate();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
                dialog.cancel();
                break;
        }
    }
}
