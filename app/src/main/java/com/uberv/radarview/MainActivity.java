package com.uberv.radarview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(new RadarSurface(this));

        List<RadarVector> radarVectors=generateRadarData(10,0,90,30,100);
        Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
    }

    private List<RadarVector> generateRadarData(int count,float fromDeg, float toDeg, float minLength, float maxLength){
        List<RadarVector> radarVectors = new ArrayList<>(count);
        Random rng = new Random();
        for(int i=0;i<count;i++){
            RadarVector radarVector = new RadarVector();
            float length= rng.nextFloat()*(maxLength-minLength)+minLength;
            radarVector.setLength(length);
            float degree = rng.nextFloat()*(toDeg-fromDeg)+fromDeg;
            radarVector.setDegree(degree);
            radarVectors.add(radarVector);
        }
        return radarVectors;
    }

}
