package com.example.lab5;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class GradeList extends AppCompatActivity {

    ArrayList<Marks> grades = new ArrayList<Marks>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_ocen);


        Bundle bundleIn = getIntent().getExtras();
        final int howManyGrades = bundleIn.getInt("howManyGrades");

        int i;

        for (i = 0; i < howManyGrades; i++) {
            Marks grade = new Marks("ocena " + (i + 1));
            grade.setValue(2);
            grades.add(grade);
        }

        final InteractiveArrayAdapter adapter = new InteractiveArrayAdapter(this, grades);
        ListView listaOcen = (ListView) findViewById(R.id.listaOcen);
        listaOcen.setAdapter(adapter);

        findViewById(R.id.buttonOut).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Bundle bundleOut = new Bundle();
                        bundleOut.putFloat("srednia", getAverage()); //
                        Intent intentOut = new Intent();
                        intentOut.putExtras(bundleOut);
                        setResult(RESULT_OK, intentOut);

                        finish();

                    }
                }

        );


    }

    //przy obróceniu urządzenia
    @Override
    protected void onSaveInstanceState(Bundle outState) {

        //stwórz tablicę typu integer z wartościami ocen z ArrayList z obiektami modelu
        int gradeTab[] = new int[grades.size()];
        for (int i = 0; i < grades.size(); i++) {
            gradeTab[i] = grades.get(i).getValue();
        }
        //zapisz utworzoną tablicę
        outState.putIntArray("grades", gradeTab);
        super.onSaveInstanceState(outState);

    }

    //po obróceniu urządzenia
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);
        //pobierz zapisaną tablicę z ocenami
        int gradeTab[] = savedInstanceState.getIntArray("grades");
        int i;
        //wypełnij ArrayLista z obiektami modelu ocenami z tablicy
        for (i = 0; i < grades.size(); i++) {
            grades.get(i).setValue(gradeTab[i]);
        }

        //wywołaj adapter
        final InteractiveArrayAdapter adapter = new InteractiveArrayAdapter(this, grades);
        ListView listaOcen = (ListView) findViewById(R.id.listaOcen);
        listaOcen.setAdapter(adapter);

    }

    //oblicz średnią z ocen zawartych w ArrayList z obiektami modelu
    protected float getAverage() {
        float avg = 0, gradeSum = 0;
        for (int i = 0; i < grades.size(); i++) {
            gradeSum += grades.get(i).getValue();

        }
        avg = (float) gradeSum / (float) grades.size();
        return avg;
    }


}
