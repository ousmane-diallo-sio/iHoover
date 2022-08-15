package com.yanport.entites;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.opengl.Visibility;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.yanport.R;

import java.util.ArrayList;
import java.util.List;

public class Vacuum {

    public final static String logTag = "Vacuum";

    private TableLayout gridContainer;
    private List<LinearLayout> gridRows = new ArrayList<>();
    private LinearLayout currentBox;
    private Coordinates coordinates;
    private ImageView vacuumImg;
    private List<ImageView> vacuumImgs = new ArrayList<>();
    private TextView tvPosition;
    private TextView tvOrientation;

    public Vacuum(TableLayout gridContainer, Pair<Integer, Integer> gridDimension, ImageView vacuumImg, TextView tvPosition, TextView tvOrientation){
        this.gridContainer = gridContainer;
        this.coordinates = new Coordinates(gridDimension);
        this.vacuumImg = vacuumImg;
        this.tvPosition = tvPosition;
        this.tvOrientation = tvOrientation;

        this.setupImg(this.vacuumImg);
        for(int i=0; i<this.gridContainer.getChildCount(); i++){
            this.gridRows.add((LinearLayout) this.gridContainer.getChildAt(i));
        }

        this.currentBox = (LinearLayout) this.gridRows.get(5).getChildAt(5);
        this.currentBox.addView(this.vacuumImg);

        this.tvPosition.setText(String.format("(%s,%s)", this.coordinates.getPositionX(), this.coordinates.getPositionY()));
        this.tvOrientation.setText(this.coordinates.getOrientation().toString());

        Log.i(logTag, String.format("Nombre de lignes : %s", this.gridRows.size()));

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public Coordinates move(List<Commands> commands){
        for(int i = 0; i < commands.size(); i++){
            if(commands.get(i) != Commands.A || this.coordinates.isMovementPossible()){
                Handler handler = new Handler();
                int finalI = i;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            //Log.i(logTag, String.format("\n\nNombre d'élément à l'intérieur du container (1) : %s", Vacuum.this.currentBox.getChildCount()));

                            if(Vacuum.this.coordinates.isMovementPossible() == false){
                                if(Vacuum.this.vacuumImgs.size() > 1){
                                    Vacuum.this.vacuumImgs.get(Vacuum.this.vacuumImgs.size()).setColorFilter(R.color.red);
                                }
                                Log.e(logTag, "erreur");
                                throw new IndexOutOfBoundsException();
                            }

                            Vacuum.this.currentBox.removeView(Vacuum.this.vacuumImg);
                            ImageView img = new ImageView(Vacuum.this.vacuumImg.getContext());
                            Vacuum.this.setupImg(img);
                            Vacuum.this.vacuumImgs.add(img);

                            if(Vacuum.this.vacuumImgs.size() > 1 && finalI != commands.size()){
                                ImageView previousImg = Vacuum.this.vacuumImgs.get(Vacuum.this.vacuumImgs.indexOf(img) -1);
                                ViewGroup parent = (ViewGroup) previousImg.getParent();
                                parent.removeView(previousImg);
                            }

                            if(commands.get(finalI) == Commands.A){
                                //Log.i(logTag, String.format("Nombre d'élément à l'intérieur du container (2) : %s", Vacuum.this.currentBox.getChildCount()));
                                Vacuum.this.coordinates.transform();
                                Vacuum.this.updateCurrentBox();
                                Vacuum.this.currentBox.addView(img);
                                Vacuum.this.tvPosition.setText(String.format("(%s,%s)", Vacuum.this.coordinates.getPositionX(), Vacuum.this.coordinates.getPositionY()));
                            }
                            else {
                                ImageView temp = finalI == 0 ? Vacuum.this.vacuumImg : img;
                                Vacuum.this.coordinates.rotate(commands.get(finalI), temp);
                                Vacuum.this.updateCurrentBox();
                                Vacuum.this.currentBox.addView(img);
                                Vacuum.this.tvOrientation.setText(Vacuum.this.coordinates.getOrientation().toString());
                            }
                            Vacuum.this.currentBox.setBackgroundResource(R.color.blue);
                        } catch (IndexOutOfBoundsException e){
                            Log.e(logTag, e.getMessage());
                        }
                    }
                }, i *1000);
            } else{
                break;
            }
        }

        Log.i(logTag, String.format("Nombre d'élément à l'intérieur du container (3) : %s", this.currentBox.getChildCount()));

        return this.coordinates;
    }

    public void setupImg(ImageView vacuumImg){
        vacuumImg.setBackgroundResource(R.drawable.vacuum);
        vacuumImg.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
        if(this.vacuumImgs.size() > 1){
            vacuumImg.setRotation(this.vacuumImgs.get(this.vacuumImgs.size() -1).getRotation());
        } else {
            vacuumImg.setRotation(this.vacuumImg.getRotation());
        }
    }

    public void updateCurrentBox(){
        this.currentBox = (LinearLayout) this.gridRows.get(this.coordinates.getPositionY()).getChildAt(this.coordinates.getPositionX());
    }

    public Coordinates getCoordinates(){
        return this.coordinates;
    }

}
