package com.uberv.radarview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import static android.graphics.Color.BLACK;


public class RadarView extends View {
    public static final String LOG_TAG = RadarView.class.getSimpleName();

    float width, height;

    RadarVector currentVector;
    Paint radarPaint = new Paint();

    public RadarView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

//        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.ChartView,0,0);
//        int resId=typedArray.getResourceId(R.styleable.ChartView_data,0);
//        typedArray.recycle();


        setBackgroundColor(BLACK);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(LOG_TAG, "onDraw()");

        width = canvas.getWidth();
        height = canvas.getHeight();
        // bottom center
        float radarCenterX = width / 2;
        float radarCenterY = width;

        radarPaint.setColor(Color.GREEN);
        radarPaint.setStrokeWidth(10);

//        canvas.drawLine(radarCenterX, radarCenterY, width, height, radarPaint);
        DrawThread drawThread = new DrawThread(canvas);
        drawThread.setRunning(true);
        drawThread.start();
    }

    class DrawThread extends Thread {

        private boolean running = false;
        private Canvas canvas;

        public DrawThread(Canvas canvas) {
            this.canvas=canvas;
        }

        public void setRunning(boolean running) {
            this.running = running;
        }

        @Override
        public void run() {
            int width = canvas.getWidth();
            int height = canvas.getHeight();
            // bottom center
            float radarCenterX = width / 2;
            float radarCenterY = width;
            while(running){
                canvas.drawLine(radarCenterX, radarCenterY, width, height, radarPaint);
            }
        }
    }

}
