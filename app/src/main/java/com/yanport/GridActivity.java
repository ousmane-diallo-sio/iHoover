package com.yanport;

import static com.yanport.R.color.white;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.yanport.entites.Commands;
import com.yanport.entites.Vacuum;

import java.util.ArrayList;
import java.util.List;

public class GridActivity extends AppCompatActivity {

    String logTag = "GridActivity";

    int gridHeight;
    int gridLength;

    TableLayout gridContainer;
    Button btnStart;
    TextView tvPosition;
    TextView tvOrientation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);

        this.gridContainer = findViewById(R.id.gridContainer);
        this.btnStart = findViewById(R.id.btnStart);
        this.tvPosition = findViewById(R.id.tvPosition);
        this.tvOrientation = findViewById(R.id.tvOrientation);

        Bundle bundle = new Bundle();
        bundle = getIntent().getExtras();

        gridHeight = bundle.getInt("gridHeight");
        gridLength = bundle.getInt("gridLength");

        Log.i(this.logTag, String.format("Grid height : %s, Grid Length : %s", gridHeight, gridLength));

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        for(int i = 0; i < this.gridHeight; i++){
            LinearLayout row = (LinearLayout) inflater.inflate(R.layout.grid_row, null);
            for(int j = 0; j < this.gridLength; j++){
                LinearLayout box = (LinearLayout) inflater.inflate(R.layout.grid_box, row);
                box.setId(View.generateViewId());
            }

            gridContainer.addView(row);
        }

        Vacuum vacuum = new Vacuum(gridContainer, new Pair<>(gridLength, gridHeight), new ImageView(this), tvPosition, tvOrientation);
        List<Commands> commandList = new ArrayList();
        commandList.add(Commands.D);
        commandList.add(Commands.A);
        commandList.add(Commands.D);
        commandList.add(Commands.A);
        commandList.add(Commands.D);
        commandList.add(Commands.A);
        commandList.add(Commands.D);
        commandList.add(Commands.A);
        commandList.add(Commands.A);


        btnStart.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                vacuum.move(commandList);
                btnStart.setEnabled(false);
            }
        });


        /*LinearLayout layout = (LinearLayout) this.gridContainer.getChildAt(0);
        layout.setBackgroundResource(R.color.green);
        LinearLayout case1 = (LinearLayout) layout.getChildAt(0);
        case1.setBackgroundResource(R.color.blue);
        TextView tv = new TextView(this);
        tv.setText("Test");*/


    }

    public void quitActivity(View view) {
        Intent intent = new Intent(GridActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void reloadActivity(View view) {
        finish();
        startActivity(getIntent());
    }
}