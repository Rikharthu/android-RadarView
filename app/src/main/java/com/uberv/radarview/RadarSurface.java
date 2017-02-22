package com.uberv.radarview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import java.util.Random;

import static com.uberv.radarview.RadarView.LOG_TAG;

/**
 * Created by UberV on 2/21/2017.
 */

public class RadarSurface extends SurfaceView implements SurfaceHolder.Callback {

    private DrawThread drawThread;

    public RadarSurface(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        drawThread = new DrawThread(getHolder());
        drawThread.setRunning(true);
        drawThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        drawThread.setRunning(false);
        while (retry) {
            try {
                drawThread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }

    class DrawThread extends Thread {

        private boolean running = false;
        private SurfaceHolder surfaceHolder;

        public DrawThread(SurfaceHolder surfaceHolder) {
            this.surfaceHolder = surfaceHolder;
        }

        public void setRunning(boolean running) {
            this.running = running;
        }

        @Override
        public void run() {
            Canvas canvas;
            float deg = 0;
            int step = 1;
            Paint radarPaint = new Paint();
            radarPaint.setColor(Color.GREEN);
            radarPaint.setStrokeWidth(5);
            Paint textPaint = new Paint();
            textPaint.setTextSize(32);
            textPaint.setColor(Color.RED);
            Paint whitePaint = new Paint();
            whitePaint.setStrokeWidth(10);
            whitePaint.setColor(Color.WHITE);
            whitePaint.setStyle(Paint.Style.STROKE);
            while (running) {
                canvas = null;
                try {
                    canvas = surfaceHolder.lockCanvas(null);
                    if (canvas == null)
                        continue;
                    // можно рисовать
                    int width = canvas.getWidth();
                    int height = canvas.getHeight();
                    int halfWidth = width/2;
                    // bottom center
                    float radarCenterX = width / 2;
                    float radarCenterY = width;

                    canvas.drawColor(Color.BLACK);

                    Random rng = new Random();

                    float toY = 0;
//                    if (deg >= 180 * .3f && deg <= 180 * .8f) {
//                        toY = 200 + rng.nextFloat() * 30;
//                        canvas.drawText("Detected!",0,height-60,textPaint);
//                    }

//                    float length = rng.nextFloat()*50+300;
                    float length = 400;
                    RadarVector radarVector = new RadarVector(deg,length);
                    // draw the radar-vector
                    int vectorX, vectorY;
                    // cos = piek/hip => piek =hip*cos
                    float tmp;
                    // map max length to the smallest dimension
                    if(halfWidth>=height){
                        tmp=height/length;
                    }else{
                        tmp=halfWidth/length;
                    }
                    float k2 = height/400;
                    vectorX = (int) (length*Math.cos(Math.toRadians(deg))*tmp);
                    vectorY = (int) (length*Math.sin(Math.toRadians(deg))*tmp);

                    canvas.drawCircle(radarCenterX,height,length*tmp,whitePaint);
                    canvas.drawCircle(radarCenterX,height,length*tmp/3,whitePaint);
                    canvas.drawCircle(radarCenterX,height,length*tmp/3*2,whitePaint);

                    canvas.drawLine(radarCenterX, height, vectorX+radarCenterX, height-(vectorY), radarPaint);
                    canvas.drawLine(radarCenterX, height, vectorX+radarCenterX, height, radarPaint);
                    canvas.drawLine(vectorX+radarCenterX, height, vectorX+radarCenterX, height-(vectorY), radarPaint);
                    canvas.drawText(String.format("x: %d, y: %d, a: %d, l: %d",(int)vectorX,(int)vectorY,(int)deg,(int)(Math.sqrt(Math.pow(vectorX,2)+Math.pow(vectorY,2)))),
                            0,height-20,textPaint);
                    sleep(20);
                    deg += step;
                    if (deg > 180 || deg < 0) {
                        step = -step;
                        canvas.drawColor(Color.GRAY);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if (canvas != null) {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }
    }
}
