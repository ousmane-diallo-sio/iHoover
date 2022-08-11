package com.yanport.entites;

import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Vacuum {

    private Coordinates coordinates;

    public Vacuum(Pair<Integer, Integer> gridDimension){
        this.coordinates = new Coordinates(gridDimension);
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

}
