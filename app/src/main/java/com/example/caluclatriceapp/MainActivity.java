package com.example.caluclatriceapp;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private HistoryAdapter adapter;
    private TextView tvOperation;
    private TextView tvResult;
    private ListView listView;
    private String mOperator;
    private String mNumberOne;
    private String mNumberTwo;
    private boolean isOperatorSelected;
    private boolean isCleared;
    private DatabaseHandler dbManager ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbManager = new DatabaseHandler(this);

        ArrayList<HashMap<String, String>> data = new ArrayList<>();
        data = dbManager.getOperations();
        adapter = new HistoryAdapter(this, data);
        listView = findViewById(R.id.list_view_history);
        tvOperation = findViewById(R.id.text_view_operation);
        tvResult = findViewById(R.id.text_view_result);
        listView.setAdapter(adapter);
        tvOperation.setText("");

    }

    public void handleClick(View view) {
        Button button = (Button) view;
        String buttonText = button.getText().toString();
        // You can perform different actions based on the button's text
        switch (buttonText) {
            case "0":
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
            case "6":
            case "7":
            case "8":
            case "9":

                handleNumberInput(buttonText);
//                tvOperation.append(buttonText);
                break;
            case ".":
                handleDecimalInput();
                break;
            case "+":
                handleOperatorInput("+");
                break;
            case "-":
                handleOperatorInput("-");
                break;
            case "X":
                handleOperatorInput("*");
                break;
            case "/":
                handleOperatorInput("/");
                break;
            case "=":
                handleEquals();
                break;
            case "AC":
                handleClear();
                break;
            case "BACK":
                handleBack();
                break;
        }

    }

    private void handleNumberInput(String number) {
        if(isCleared)
        {
            isCleared=false;
            tvOperation.setText("");
            tvResult.setText("");
        }
        if (isOperatorSelected) {
            mNumberTwo = mNumberTwo == null ? number : mNumberTwo + number;
            tvOperation.append(number);
        } else {
            mNumberOne = mNumberOne == null ? number : mNumberOne + number;
            tvOperation.setText(mNumberOne);
        }
    }

    private void handleDecimalInput() {
        if (isOperatorSelected) {
            if (mNumberTwo == null) {
                mNumberTwo = "0.";
            } else if (!mNumberTwo.contains(".")) {
                mNumberTwo += ".";
            }
            tvOperation.append(mNumberTwo);
        } else {
            if (mNumberOne == null) {
                mNumberOne = "0.";
            } else if (!mNumberOne.contains(".")) {
                mNumberOne += ".";
            }
            tvOperation.append(mNumberOne);
        }
    }

    private void handleOperatorInput(String operator) {
        if (mNumberOne != null && !isOperatorSelected) {
            mOperator = operator;
            isOperatorSelected = true;
            tvOperation.setText(mNumberOne + " " + mOperator);
        }
    }

    private void handleEquals() {
        if (mNumberOne != null && mNumberTwo != null && mOperator != null) {
            double num1 = Double.parseDouble(mNumberOne);
            double num2 = Double.parseDouble(mNumberTwo);
            double result = 0.0;
            String opertaion = num1 + mOperator + num2;
            switch (mOperator) {
                case "+":
                    result = num1 + num2;
                    break;
                case "-":
                    result = num1 - num2;
                    break;
                case "*":
                    result = num1 * num2;
                    break;
                case "/":
                    if (num2 != 0) {
                        result = num1 / num2;
                    } else {
                        tvOperation.setText("Error");
                        return;
                    }
                    break;
            }

            tvResult.setText(String.valueOf(result));
            dbManager.addOperation(opertaion, String.valueOf(result));
            adapter.addItem(dbManager.getOperations());
            listView.setSelection(listView.getCount() - 1);
            mNumberOne = null;
            mNumberTwo = null;
            mOperator = null;
            isOperatorSelected = false;
            isCleared=true;
        }
    }

    private void handleBack() {
        char lastChar = tvOperation.getText().charAt(tvOperation.getText().length() - 1);
        if(isCleared)
        {
            isCleared=false;
            tvOperation.setText("");
            tvResult.setText("");
        }

        if(tvOperation.getText().length()>0)
            if(tvOperation.getText().length()==1)
                tvOperation.setText("0");
              else
            {    if (lastChar == '+' || lastChar == '=' || lastChar == '/' || lastChar == 'X')
                isOperatorSelected = false ;
                if(isOperatorSelected){
                if (mNumberTwo != null && mNumberTwo.length() > 1) {
                mNumberTwo = mNumberTwo.substring(0, mNumberTwo.length() - 1);
            } else {
                mNumberTwo = null;
            } }else {
            if (mNumberOne != null && mNumberOne.length() > 1) {
                mNumberOne = mNumberOne.substring(0, mNumberOne.length() - 1);
            } else {
                mNumberOne = null;
            } }
              tvOperation.setText(tvOperation.getText().subSequence(0, tvOperation.getText().length() - 1));   }
    }

    private void handleClear() {
        if(isCleared)
        {
            isCleared=false;
            tvOperation.setText("");
            tvResult.setText("");
        }
        mNumberOne = null;
        mNumberTwo = null;
        mOperator = null;
        isOperatorSelected = false;
        tvOperation.setText("0");
        tvResult = null;
    }


}
