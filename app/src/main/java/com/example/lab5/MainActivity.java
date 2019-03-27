package com.example.lab5;


import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    float srednia = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        validate();
        generateOnClickListener();
        generateCreditsListener();

    }

    protected void onActivityResult(int kodZadania, int kodWyniku, Intent dane) {
        super.onActivityResult(kodZadania, kodWyniku, dane);
        if (kodWyniku == RESULT_OK) {
            //pobierz srednia przekazaną z aktywności GradeList
            Bundle bundle = dane.getExtras();
            this.srednia = bundle.getFloat("srednia");
            TextView gradeAvg = (TextView) findViewById(R.id.gradeAverage);
            gradeAvg.setText("Twoja średnia to: " + srednia);
            gradeAvg.setVisibility(View.VISIBLE);
            Button endButton = (Button) findViewById(R.id.grades_button);

            //wyłącz textEdity
            findViewById(R.id.gradeCount_input).setEnabled(false);
            findViewById(R.id.firstName_input).setEnabled(false);
            findViewById(R.id.surname_input).setEnabled(false);

            if (srednia >= 3.0) {
                endButton.setText("Super :)");
                endButton.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                finish();
                                showToast("Gratulacje!");
                            }
                        }

                );
            } else {
                endButton.setText("Tym razem mi nie poszło.");
                endButton.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                finish();
                                showToast("Wysyłam podanie o zaliczenie poprawkowe");
                            }
                        }

                );
            }
        }
    }

    //w razie obrotu urządzenia
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //zachowaj średnią
        outState.putFloat("srednia", this.srednia);
        super.onSaveInstanceState(outState);
    }

    //po obrocie urządzenia
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        //pobierz zachowaną średnią
        super.onRestoreInstanceState(savedInstanceState);
        this.srednia = savedInstanceState.getFloat("srednia");
        validate();
        Button endButton = (Button) findViewById(R.id.grades_button);
        if (srednia != 0) {
            TextView gradeAvg = (TextView) findViewById(R.id.gradeAverage);
            gradeAvg.setText("Twoja średnia to: " + srednia);
            gradeAvg.setVisibility(View.VISIBLE);
            findViewById(R.id.grades_button).setVisibility(View.VISIBLE);
            findViewById(R.id.gradeCount_input).setEnabled(false);
            findViewById(R.id.firstName_input).setEnabled(false);
            findViewById(R.id.surname_input).setEnabled(false);
        }
        if (srednia >= 3.0) {
            endButton.setText("Super :)");
            endButton.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            finish();
                            showToast("Gratulacje!");
                        }
                    }

            );
        } else if (srednia > 0) {
            endButton.setText("Tym razem mi nie poszło.");
            endButton.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            finish();
                            showToast("Wysyłam podanie o zaliczenie poprawkowe");
                        }
                    }

            );
        }
    }

    //wyświetl tosta jedną linią kodu z jednym argumentem - wyświetlanym stringiem
    public void showToast(String toast_message) {
        Toast toast = Toast.makeText(this, toast_message, Toast.LENGTH_SHORT);
        toast.show();
    }

    //dokonaj walidacji
    public void validate() {

        generateNameListeners();
        generateGradeCountListener();

    }

    //czy podane dane są poprawne
    public boolean isDataOK() {
        final EditText gradeCount = (EditText) findViewById(R.id.gradeCount_input);
        final EditText tab[] = {findViewById(R.id.firstName_input), findViewById(R.id.surname_input)};


        if (gradeCount.getText().toString().isEmpty()) {
        } else if (Integer.valueOf(gradeCount.getText().toString()) < 5 || Integer.valueOf(gradeCount.getText().toString()) > 15) {

        } else {
            gradeCount.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
            if (!tab[0].getText().toString().isEmpty() && !tab[1].getText().toString().isEmpty()) {
                findViewById(R.id.grades_button).setVisibility(View.VISIBLE);
                return true;
            }
        }
        return false;
    }

    //wygeneruj listener dla pola z ilością ocen
    public void generateGradeCountListener() {
        final EditText gradeCount = (EditText) findViewById(R.id.gradeCount_input);
        gradeCount.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (gradeCount.getText().toString().isEmpty()) {
                            showToast("Wprowadź liczbę ocen");
                            gradeCount.setBackgroundColor(Color.RED);
                            findViewById(R.id.grades_button).setVisibility(View.INVISIBLE);
                        } else if (Integer.valueOf(gradeCount.getText().toString()) < 5 || Integer.valueOf(gradeCount.getText().toString()) > 15) {
                            showToast("Ilość ocen musi zawierać się w przedziale 5-15");
                            gradeCount.setBackgroundColor(Color.RED);
                            findViewById(R.id.grades_button).setVisibility(View.INVISIBLE);
                        } else {
                            gradeCount.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
                            if (isDataOK()) {
                                findViewById(R.id.grades_button).setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }
        );
    }

    //wygeneruj listenery dla pól z imię oraz nazwisko
    public void generateNameListeners() {
        final EditText tab[] = {findViewById(R.id.firstName_input), findViewById(R.id.surname_input)};
        for (final EditText element : tab) {
            element.addTextChangedListener(
                    new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                            if (element.getText().toString().isEmpty()) {
                                showToast("Pole nie może być puste");
                                element.setBackgroundColor(Color.RED);
                                findViewById(R.id.grades_button).setVisibility(View.INVISIBLE);
                            } else {
                                element.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
                                if (isDataOK())
                                    findViewById(R.id.grades_button).setVisibility(View.VISIBLE);
                            }

                        }
                    }
            );
        }
    }

    //wygeneruj listenera dla przycisku przenoszącego do drugiej aktywności
    public void generateOnClickListener() {
        findViewById(R.id.grades_button).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText gradeCount = (EditText) findViewById(R.id.gradeCount_input);
                        if (!isDataOK()) {
                            showToast("Wprowadź poprawne wartości!");
                        }
                        if (!gradeCount.getText().toString().isEmpty() && isDataOK()) {
                            Intent gradeList = new Intent(MainActivity.this, GradeList.class);
                            int howManyGrades = Integer.valueOf(gradeCount.getText().toString());
                            gradeList.putExtra("howManyGrades", howManyGrades);
                            int requestCode = 0;
                            startActivityForResult(gradeList, requestCode);
                        }
                    }
                }

        );
    }

    //wygeneruj listenera dla TextView Credits
    public void generateCreditsListener()
    {
        findViewById(R.id.creditsLink).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        credits();
                    }
                }
        );
    }

    //stwórz intent przenoszący do aktywności credits
    public void credits()
    {
        Intent credits = new Intent(MainActivity.this, Credits.class);
        startActivityForResult(credits, new Integer(0));
    }
}
