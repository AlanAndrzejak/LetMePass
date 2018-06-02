package com.mygdx.game;


import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class Accelerometer extends Activity implements SensorEventListener {
    private SensorManager sensorManager;
    private final int roll = 6;
    private long lastUpdate;
    private int miliard = 1000000000;
    private String movedTo;
    private int position;
    private boolean stop;

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            getAccelerometer(event);
        }

    }

    private void setObjectPosition(String moved) {
        if (moved.equals("LEFT")) {
            this.position -= 1;
        } else {
            this.position += 1;
        }
    }

    private void getAccelerometer(SensorEvent event) {
        float[] values = event.values;
        float x = values[0];
        float y = values[1];
        float z = values[2];


        float accelationSquareRoot = (x * x + z * z)
                / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);

        long actualTime = event.timestamp;
        if (accelationSquareRoot >= 2) {
            if (actualTime - lastUpdate < miliard * 2) {
                return;
            }
            calculateDirection(x, z);
            setObjectPosition(movedTo);
            lastUpdate = actualTime;
        }
    }

    public int getPosition() {
        return this.position;
    }

    public String getmovedTo() {
        return this.movedTo;
    }

    private void calculateDirection(float x, float z) {
        if (Math.abs(x) < Math.abs(z)) {
            this.stop = true;
        } else if (x < 0) {
            this.movedTo = "LEFT";
        } else {
            this.movedTo = "RIGHT";
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();

        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}