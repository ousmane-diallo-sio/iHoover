package com.yanport.entites;

import android.widget.Toast;


import android.util.Pair;

import com.yanport.technique.MutablePair;

import java.util.Arrays;
import java.util.List;

public class Coordinates {

    // Position de l'aspirateur, la 1ère valeur correspond à x et la 2ème à y.
    private MutablePair<Integer, Integer> position = new MutablePair<>(5,5);
    private Orientation orientation = Orientation.N;
    // Dimension de la grille, la 1ère valeur correspond à sa hauteur et la 2ème à sa largeur.
    private Pair<Integer, Integer> gridDimension;

    public Coordinates(Pair<Integer, Integer> gridDimension){
        this.gridDimension = gridDimension;
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
                if (this.getPositionY() -1 >= this.getGridDimensionY()){
                    isPossible = true;
                } else{
                    isPossible = false;
                }
                break;
            case W:
                if (this.getPositionX() -1 >= this.getGridDimensionX()){
                    isPossible = true;
                } else{
                    isPossible = false;
                }
                break;

        }

        return isPossible;

    }

    public MutablePair<Integer, Integer> transform(){
        if (isMovementPossible()){
            switch (orientation){
                case N:
                    this.increasePositionX();
                    break;
                case E:
                    this.increasePositionY();
                    break;
                case S:
                    this.decreasePositionX();
                case W:
                    this.decreasePositionY();
            }
        }
        return this.position;
    }

    public Orientation rotate(Commands command){
        List<Orientation> orientations = Arrays.asList(Orientation.values());
        if(command == Commands.D){
            if(this.orientation == orientations.get(orientations.size() -1)){
                this.orientation = orientations.get(0);
            } else {
                this.orientation = orientations.get(orientations.indexOf(this.orientation) +1);
            }
        }
        else if(command == Commands.G){
            if(this.orientation == orientations.get(0)){
                this.orientation = orientations.get(orientations.size() -1);
            } else {
                this.orientation = orientations.get(orientations.indexOf(this.orientation) -1);
            }
        }
        return this.orientation;
    }

    public Integer getPositionX(){
        return this.position.first;
    }

    public Integer getPositionY(){
        return this.position.second;
    }

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
