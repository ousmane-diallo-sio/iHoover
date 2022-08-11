package com.yanport;

import static com.yanport.R.color.white;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

public class GridActivity extends AppCompatActivity {

    String logTag = "GridActivity";

    int gridHeight;
    int gridLength;

    TableLayout gridContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);

        this.gridContainer = findViewById(R.id.gridContainer);

        Bundle bundle = new Bundle();
        bundle = getIntent().getExtras();

        gridHeight = bundle.getInt("gridHeight");
        gridLength = bundle.getInt("gridLength");

        Log.i(this.logTag, String.format("Grid height : %s, Grid Length : %s", gridHeight, gridLength));

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        for(int i = 0; i < this.gridHeight; i++){
            LinearLayout row = (LinearLayout) inflater.inflate(R.layout.grid_row, null);
            for(int j = 0; j < this.gridLength; j++){
                View box = inflater.inflate(R.layout.grid_box, row);
            }

            gridContainer.addView(row);
        }

        /*LinearLayout layout = (LinearLayout) this.gridContainer.getChildAt(5);
        LinearLayout case1 = (LinearLayout) layout.getChildAt(4);
        case1.setBackgroundResource(R.color.blue);
        TextView tv = new TextView(this);
        tv.setText("Test");
        case1.addView(tv);*/







    }

    public void quitActivity(View view) {
        Intent intent = new Intent(GridActivity.this, MainActivity.class);
        startActivity(intent);
    }
}