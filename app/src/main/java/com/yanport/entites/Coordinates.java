package com.yanport.entites;

import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;


import android.util.Pair;

import com.yanport.technique.MutablePair;

import java.util.Arrays;
import java.util.List;

public class Coordinates {

    // Position de l'aspirateur, la 1ère valeur correspond à x et la 2ème à y.
    private MutablePair<Integer, Integer> position;
    private Orientation orientation = Orientation.N;
    // Dimension de la grille, la 1ère valeur correspond à sa hauteur et la 2ème à sa largeur.
    private Pair<Integer, Integer> gridDimension;

    public Coordinates(Pair<Integer, Integer> gridDimension, MutablePair<Integer, Integer> position){
        this.gridDimension = gridDimension;
        this.position = position;
    }

    public Coordinates(Pair<Integer, Integer> gridDimension, MutablePair<Integer, Integer> position, Orientation orientation){
        this.gridDimension = gridDimension;
        this.position = position;
        this.orientation = orientation;
    }

    public boolean isMovementPossible(){
        boolean isPossible = false;

        switch (this.orientation){
            case N:
                if (this.getPositionY() +1 <= this.getGridDimensionY()){
                    isPossible = true;
                } else{
                    isPossible = false;
                }
                break;
            case E:
                if (this.getPositionX() +1 <= this.getGridDimensionX()){
                    isPossible = true;
                } else{
                    isPossible = false;
                }
                break;
            case S:
                if (this.getPositionY() -1 >= 0){
                    isPossible = true;
                } else{
                    isPossible = false;
                }
                break;
            case W:
                if (this.getPositionX() -1 >= 0){
                    isPossible = true;
                } else{
                    isPossible = false;
                }
                break;

        }

        return isPossible;

    }

    public boolean transform(){
        if (isMovementPossible()){
            switch (orientation){
                case N:
                    this.increasePositionY();
                    break;
                case E:
                    this.increasePositionX();
                    break;
                case S:
                    this.decreasePositionY();
                    break;
                case W:
                    this.decreasePositionX();
                    break;
            }
        } else {
            Log.e(Vacuum.logTag, "Mouvement impossible");
            return false;
        }
        Log.i(Vacuum.logTag, String.format("Position actuelle : [%s, %s]", this.position.first, this.position.second));
        return true;
    }

    public Orientation rotate(Commands command, ImageView vacuumImg){
        List<Orientation> orientations = Arrays.asList(Orientation.values());
        if(command == Commands.D){
            if(this.orientation == orientations.get(orientations.size() -1)){
                this.orientation = orientations.get(0);
            } else {
                this.orientation = orientations.get(orientations.indexOf(this.orientation) +1);
            }
            vacuumImg.setRotation(vacuumImg.getRotation() +90);
        }
        else if(command == Commands.G){
            if(this.orientation == orientations.get(0)){
                this.orientation = orientations.get(orientations.size() -1);
            } else {
                this.orientation = orientations.get(orientations.indexOf(this.orientation) -1);
            }
            vacuumImg.setRotation(vacuumImg.getRotation() -90);
        }
        Log.i(Vacuum.logTag, String.format("Orientation actuelle : %s", this.orientation));
        return this.orientation;
    }

    public Integer getPositionX(){
        return this.position.first;
    }

    public Integer getPositionY(){
        return this.position.second;
    }

    public Enum getOrientation(){ return this.orientation; }

    public void increasePositionX(){
        this.position.first = this.position.first + 1;
    }

    public void decreasePositionX(){
        this.position.first = this.position.first - 1;
    }

    public void increasePositionY(){
        this.position.second = this.position.second + 1;
    }

    public void decreasePositionY(){
        this.position.second = this.position.second - 1;
    }

    public Integer getGridDimensionX(){
        return this.gridDimension.first;
    }

    public Integer getGridDimensionY(){
        return this.gridDimension.second;
    }

}
