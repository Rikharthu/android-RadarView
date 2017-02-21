package com.uberv.radarview;


public class RadarVector {

    // servo-motor degree
    float degree;
    // length till obstacle
    float length;

    public RadarVector() {
    }

    public RadarVector(float degree, float length) {
        this.degree = degree;
        this.length = length;
    }

    public float getDegree() {
        return degree;
    }

    public void setDegree(float degree) {
        this.degree = degree;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }
}
