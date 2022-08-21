package com.yanport;

import static com.yanport.R.color.white;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yanport.entites.Commands;
import com.yanport.entites.Vacuum;
import com.yanport.technique.MutablePair;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class GridActivity extends AppCompatActivity {

    String logTag = "GridActivity";

    int gridHeight;
    int gridLength;

    int vacuumPosX = 0;
    int vacuumPosY = 0;

    TableLayout gridContainer;
    Button btnStart;
    TextView tvPosition;
    TextView tvOrientation;
    EditText etPosX;
    EditText etPosY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);

        this.gridContainer = findViewById(R.id.gridContainer);
        this.btnStart = findViewById(R.id.btnStart);
        this.tvPosition = findViewById(R.id.tvPosition);
        this.tvOrientation = findViewById(R.id.tvOrientation);
        this.etPosX =  findViewById(R.id.etPosX);
        this.etPosY = findViewById(R.id.etPosY);

        Bundle bundle = new Bundle();
        bundle = getIntent().getExtras();

        this.gridHeight = bundle.getInt("gridHeight");
        this.gridLength = bundle.getInt("gridLength");

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


        etPosX.setHint(Integer.toString(vacuumPosX));
        etPosY.setHint(Integer.toString(vacuumPosY));

        etPosX.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                int value = charSequence.length() != 0 ? Integer.parseInt(charSequence.toString()) : -1;
                if(value > 0 && value <= gridLength -1){
                    vacuumPosX = value;
                }
                else if(value == -1){}
                else {
                    Toast.makeText(GridActivity.this, "La saisie est invalide", Toast.LENGTH_SHORT).show();
                    etPosX.getText().clear();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        etPosY.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int value = charSequence.length() != 0 ? Integer.parseInt(charSequence.toString()) : -1;
                if(value > 0 && value <= gridHeight -1){
                    vacuumPosY = value;
                }
                else if(value == -1){}
                else {
                    Toast.makeText(GridActivity.this, "La saisie est invalide", Toast.LENGTH_SHORT).show();
                    etPosY.getText().clear();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                Vacuum vacuum = new Vacuum(
                        gridContainer,
                        new Pair<>(gridLength, gridHeight),
                        new MutablePair<>(vacuumPosX, vacuumPosY),
                        new ImageView(GridActivity.this),
                        tvPosition,
                        tvOrientation
                );
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
                vacuum.move(commandList);

                etPosX.setEnabled(false);
                etPosY.setEnabled(false);
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