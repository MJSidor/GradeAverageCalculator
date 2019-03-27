package com.example.lab5;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.List;

public class InteractiveArrayAdapter extends ArrayAdapter<Marks> {

    private List<Marks> gradeList;
    private Activity context;
    int gradeSum = 0;

    public InteractiveArrayAdapter(Activity context, List<Marks> gradeList) {
        super(context, R.layout.marks, gradeList);
        //ustawienie wartości pól
        this.gradeList = gradeList;
        this.context = context;

    }


    @Override
    public View getView(final int numerWiersza, View widokDoRecyklingu, ViewGroup parent) {
        View widok = null;
        //tworzenie nowego wiersza
        if (widokDoRecyklingu == null) {
            //utworzenie layout na podstawie pliku XML

            LayoutInflater pompka = this.context.getLayoutInflater();
            widok = pompka.inflate(R.layout.marks, null);
            widok.setVisibility(View.VISIBLE);

            //wybranie konkretnego przycisku radiowego musi zmieniać dane w modelu
            RadioGroup grupaOceny = (RadioGroup) widok.findViewById(R.id.gradeRadioGroup);
            final View finalWidok = widok;
            grupaOceny.setOnCheckedChangeListener(
                    new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup group, int checkedId) {
                            //1 odczytanie z etykiety który obiekt modelu przechowuje dane o zmienionej ocenie
                            Marks element = (Marks) group.getTag();
                            //2 zapisanie zmienionej oceny
                            RadioButton checked = (RadioButton) finalWidok.findViewById(group.getCheckedRadioButtonId());

                            element.setValue(Integer.valueOf(checked.getText().toString()));
                            //gradeList.set(numerWiersza,element);
                        }
                    }
            );
            //powiązanie grupy przycisków z obiektem w modelu
            grupaOceny.setTag(gradeList.get(numerWiersza));
        }

        //aktualizacja istniejącego wiersza
        else {
            widok = widokDoRecyklingu;
            RadioGroup grupaOceny = (RadioGroup) widok.findViewById(R.id.gradeRadioGroup);
            //powiązanie grupy przycisków z obiektem w modelu
            grupaOceny.setTag(gradeList.get(numerWiersza));
            Marks element = (Marks) grupaOceny.getTag();

        }


        TextView etykieta = (TextView) widok.findViewById(R.id.etykieta);
        //ustawienie tekstu etykiety na podstawie modelu
        etykieta.setText(gradeList.get(numerWiersza).getId());

        RadioGroup grupaOceny = (RadioGroup) widok.findViewById(R.id.gradeRadioGroup);
        //zaznaczenie odpowiedniego przycisku na podstawie modelu
        switch (gradeList.get(numerWiersza).getValue()) {
            case 2:
                grupaOceny.check(R.id.grade2Button);
                break;

            case 3:
                grupaOceny.check(R.id.grade3Button);
                break;

            case 4:
                grupaOceny.check(R.id.grade4Button);
                break;

            case 5:
                grupaOceny.check(R.id.grade5Button);
                break;
        }


        //zwrócenie nowego lub zaktualizowanego wiersza listy
        return widok;

    }
}