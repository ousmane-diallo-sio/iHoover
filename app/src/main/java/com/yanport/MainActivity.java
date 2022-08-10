package com.yanport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String logTag = "MainActivity";
    Spinner spHeight;
    Spinner spLength;
    List possibleValues = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.spHeight = findViewById(R.id.spHeight);
        this.spLength = findViewById(R.id.spLength);

        for(int i=2; i<11; i++){
            possibleValues.add(i);
            Log.i(this.logTag, String.format("Ajout de l'élement n°%s à la liste", i));
        }

        ArrayAdapter<Integer> spsAdapter = new ArrayAdapter<Integer>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                possibleValues
        );

        spHeight.setAdapter(spsAdapter);
        spLength.setAdapter(spsAdapter);

        spHeight.setSelection(spsAdapter.getCount() -1);
        spLength.setSelection(spsAdapter.getCount() -1);

    }

    public void createGrid(View view) {
        Intent intent = new Intent(MainActivity.this, GridActivity.class);
        intent.putExtra("gridHeight", (Integer) spHeight.getSelectedItem());
        intent.putExtra("gridLength", (Integer) this.spLength.getSelectedItem());

        startActivity(intent);
    }
}