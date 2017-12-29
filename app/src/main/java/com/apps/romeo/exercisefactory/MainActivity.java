package com.apps.romeo.exercisefactory;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private final String TAG = MainActivity.class.getSimpleName();

    private TextView[] exerciseTextViews = new TextView[5];
    private EditText[] resultEditTexts = new EditText[5];
    private ExerciseGenerator generator;
    private Button checkButton;
    private int maxNumSize;
    private boolean isOnGen = true;
    private byte rightAnswers = 0;
    private byte wrongAnswers = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initCompanents();
        ((Button) findViewById(R.id.button_generate)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isOnGen = true;
                try {
                    maxNumSize = Integer.parseInt(((EditText)findViewById(R.id.editText_number_size)).getText().toString());
                }catch (NumberFormatException e){
                    return;
                }
                generator = new ExerciseGenerator(maxNumSize);
                for(TextView exTV : exerciseTextViews){
                    exTV.setText(nextEx());
                }
                isOnGen = false;
                showCompanents(true);
            }
        });
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isOnGen)
                {
                    for(int i = 0; i < 5 ; i++){
                        String exTxt = exerciseTextViews[i].getText().toString();
                        String resultTxt = resultEditTexts[i].getText().toString();
                        if(!(exTxt.equals("") || resultTxt.equals("")))
                        {
                            checkEx(exTxt, resultTxt);
                        }
                    }
                    showResultsDialog(prepareResultsReportTxt(), "Checking Results");
                }
            }
            private String prepareResultsReportTxt() {
                return "Right answers: " + rightAnswers + "\n" +
                        "Wrong answers: " + wrongAnswers + "\n" +
                        "Unanswered: " + (5 - (rightAnswers + wrongAnswers));
            }
            private void checkEx(String exTxt, String resultTxt) {
                String[] exCompanents = exTxt.split(" ");
                Log.d(TAG, exCompanents[0] + " " + exCompanents[1] + " " + exCompanents[2]);
                int num1 = Integer.parseInt(exCompanents[0]);
                int num2 = Integer.parseInt(exCompanents[2]);
                int actualAnswer = 0;
                switch (exCompanents[1]){
                    case "+":
                        actualAnswer = num1 + num2;
                        break;
                    case "-":
                        actualAnswer = num1 - num2;
                        break;
                    case "*":
                        actualAnswer = num1 * num2;
                        break;
                    case "/":
                        actualAnswer = num1 / num2;
                }
                if(actualAnswer == Integer.parseInt(resultTxt))
                {
                    rightAnswers++;
                }
                else
                {
                    wrongAnswers++;
                }
            }
        });
    }

    private void showResultsDialog(String message, String title) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setCancelable(true)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
                resetAll();

    }

    private String nextEx(){
        try {
            String[] exCompanents = generator.genExercise();
            StringBuilder txtBuilder = new StringBuilder();
            for (String exCompanent : exCompanents){
                txtBuilder.append(exCompanent + " ");
            }
            txtBuilder.append("=");
            return txtBuilder.toString();
        } catch (Exception e) {
            generator = new ExerciseGenerator(this.maxNumSize);
            return nextEx();
        }

    }
    private void initCompanents(){
        resultEditTexts[0] = (EditText) findViewById(R.id.editText_1);
        resultEditTexts[1] = (EditText) findViewById(R.id.editText_2);
        resultEditTexts[2] = (EditText) findViewById(R.id.editText_3);
        resultEditTexts[3] = (EditText) findViewById(R.id.editText_4);
        resultEditTexts[4] = (EditText) findViewById(R.id.editText_5);

        exerciseTextViews[0] = (TextView) findViewById(R.id.textView_1);
        exerciseTextViews[1] = (TextView) findViewById(R.id.textView_2);
        exerciseTextViews[2] = (TextView) findViewById(R.id.textView_3);
        exerciseTextViews[3] = (TextView) findViewById(R.id.textView_4);
        exerciseTextViews[4] = (TextView) findViewById(R.id.textView_5);

        checkButton = (Button) findViewById(R.id.button_check);
    }
    private void showCompanents(boolean isVisible){
        int state = isVisible ? Button.VISIBLE : Button.INVISIBLE;
        for (int i = 0 ; i < 5 ; i++){
            resultEditTexts[i].setVisibility(state);
        }
        checkButton.setVisibility(state);

    }
    private void resetAll(){
        showCompanents(false);
        for (EditText resultET : resultEditTexts){
            resultET.setText("");
        }
        rightAnswers = 0;
        wrongAnswers = 0;
    }
}
