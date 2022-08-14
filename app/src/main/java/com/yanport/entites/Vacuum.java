package com.yanport.entites;


import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import com.yanport.R;

import java.util.ArrayList;
import java.util.List;

public class Vacuum {

    private final static String logTag = "Vacuum";

    private TableLayout gridContainer;
    private List<LinearLayout> gridRows = new ArrayList<>();
    private LinearLayout currentBox;
    private Coordinates coordinates;
    private ImageView vacuumImg;

    public Vacuum(TableLayout gridContainer, Pair<Integer, Integer> gridDimension, ImageView vacuumImg){
        this.gridContainer = gridContainer;
        this.coordinates = new Coordinates(gridDimension);
        this.vacuumImg = vacuumImg;

        this.vacuumImg.setBackgroundResource(R.drawable.vacuum);
        this.vacuumImg.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));

        for(int i=0; i<this.gridContainer.getChildCount(); i++){
            this.gridRows.add((LinearLayout) this.gridContainer.getChildAt(i));
        }

        this.currentBox = (LinearLayout) this.gridRows.get(5).getChildAt(5);
        this.currentBox.addView(vacuumImg);

        Log.i(logTag, String.format("Nombre de lignes : %s", this.gridRows.size()));

    }


    public Coordinates move(List<Commands> commands){

        for(Commands command : commands){
            Log.i(logTag, String.format("\n\nNombre d'élément à l'intérieur du container (1) : %s", this.currentBox.getChildCount()));
            this.currentBox.removeAllViewsInLayout();
            if(command == Commands.A){
                Log.i(logTag, String.format("Nombre d'élément à l'intérieur du container (2) : %s", this.currentBox.getChildCount()));

                this.coordinates.transform();
                this.updateCurrentBox();
                this.currentBox.addView(this.vacuumImg);
            }
            else {
                this.coordinates.rotate(command, this.vacuumImg);
                this.updateCurrentBox();
                //this.currentBox.addView(this.vacuumImg);
            }
        }
        Log.i(logTag, String.format("Nombre d'élément à l'intérieur du container (3) : %s", this.currentBox.getChildCount()));

        return this.coordinates;
    }

    public void updateCurrentBox(){
        this.currentBox = (LinearLayout) this.gridRows.get(this.coordinates.getPositionY()).getChildAt(this.coordinates.getPositionX());
    }

    public Coordinates getCoordinates(){
        return this.coordinates;
    }

}
