package com.yanport.entites;

import android.content.Context;
import android.text.Layout;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.yanport.GridActivity;
import com.yanport.R;

import java.util.ArrayList;
import java.util.List;

public class Vacuum {

    private Coordinates coordinates;
    private ImageView vacuumImg;

    public Vacuum(Pair<Integer, Integer> gridDimension, ImageView vacuumImg){
        this.coordinates = new Coordinates(gridDimension);
        this.vacuumImg = vacuumImg;
        this.vacuumImg.setBackgroundResource(R.drawable.vacuum);
    }

    public Coordinates move(List<Commands> commands){
        for(Commands command : commands){
            if(command == Commands.A){
                this.coordinates.transform();
            } else {
                this.coordinates.rotate(command);
            }
        }
        return this.coordinates;
    }

    public Coordinates getCoordinates(){
        return this.coordinates;
    }

    public ImageView getVacuumImg() {
        return vacuumImg;
    }
}
