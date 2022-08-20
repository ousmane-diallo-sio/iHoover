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
import androidx.core.content.ContextCompat;

import com.yanport.R;
import com.yanport.technique.MutablePair;

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
    private boolean isActive = true;

    public Vacuum(TableLayout gridContainer, Pair<Integer, Integer> gridDimension, MutablePair<Integer, Integer> vacuumPos, ImageView vacuumImg, TextView tvPosition, TextView tvOrientation){
        this.gridContainer = gridContainer;
        this.coordinates = new Coordinates(gridDimension, vacuumPos);
        this.vacuumImg = vacuumImg;
        this.tvPosition = tvPosition;
        this.tvOrientation = tvOrientation;

        this.setupImg(this.vacuumImg);
        for(int i=0; i<this.gridContainer.getChildCount(); i++){
            this.gridRows.add((LinearLayout) this.gridContainer.getChildAt(i));
        }

        this.currentBox = (LinearLayout) this.gridRows.get(vacuumPos.second).getChildAt(vacuumPos.first);
        this.currentBox.addView(this.vacuumImg);

        this.tvPosition.setText(String.format("(%s,%s)", this.coordinates.getPositionX(), this.coordinates.getPositionY()));
        this.tvOrientation.setText(this.coordinates.getOrientation().toString());

        Log.i(logTag, String.format("Nombre de lignes : %s", this.gridRows.size()));

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public Coordinates move(List<Commands> commands){
        for(int i = 0; i < commands.size(); i++){
            if(isActive){
                Handler handler = new Handler();
                int finalI = i;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        LinearLayout previousBox = null;
                        try{

                            if(Vacuum.this.coordinates.isMovementPossible() == false){
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
                                previousBox = Vacuum.this.updateCurrentBox();
                                if(Vacuum.this.isActive){
                                    Vacuum.this.currentBox.addView(img);
                                    Vacuum.this.tvPosition.setText(String.format("(%s,%s)", Vacuum.this.coordinates.getPositionX(), Vacuum.this.coordinates.getPositionY()));
                                }
                            }
                            else {
                                ImageView temp = finalI == 0 ? Vacuum.this.vacuumImg : img;
                                Vacuum.this.coordinates.rotate(commands.get(finalI), temp);
                                Vacuum.this.updateCurrentBox();
                                Vacuum.this.currentBox.addView(img);
                                Vacuum.this.tvOrientation.setText(Vacuum.this.coordinates.getOrientation().toString());
                            }

                            if(!isActive){
                                Log.i(logTag, "oui test");
                                img.setBackgroundResource(R.drawable.stop);
                                previousBox.addView(img);

                            }
                        } catch (IndexOutOfBoundsException e){
                            Log.e(logTag, e.toString());
                        }
                    }
                }, i *1000);
            } else{
                break;
            }
        }

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

    public LinearLayout updateCurrentBox(){
        LinearLayout previousBox = this.currentBox;
        this.currentBox = (LinearLayout) this.gridRows.get(this.coordinates.getPositionY()).getChildAt(this.coordinates.getPositionX());
        if (currentBox == null){
            this.isActive = false;
        } else{
            Vacuum.this.currentBox.setBackgroundResource(R.color.steel_blue);
        }
        return previousBox;
    }

    public Coordinates getCoordinates(){
        return this.coordinates;
    }

}
