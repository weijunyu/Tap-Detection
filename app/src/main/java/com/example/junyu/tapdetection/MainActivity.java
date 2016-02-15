package com.example.junyu.tapdetection;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    private final String LOG_TAG = "Main activity";

    private SensorManager sensorManager;
    private SensorEventListener sensorListener;

    private TextView linAccText;
    private TextView gyroText;

//    private double[][] linAccSample = new double[15][3];
//    private double[][] gyroSample = new double[15][3];

    private LinkedList<double[]> linAccSamples = new LinkedList<>();
    private LinkedList<double[]> gyroSamples = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar appBar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(appBar);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (sensorListener != null) {
            sensorManager.unregisterListener(sensorListener);
        }
    }

    public void startDetecting(View view) {
        startListening();
    }

    protected void startListening() {
        // Initialise sensors
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor linearAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        final Sensor gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        sensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                Sensor sensor = event.sensor;
                if (sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
                    double xValue = event.values[0];
                    double yValue = event.values[1];
                    double zValue = event.values[2];
                    // Use LinkedList
                    if (linAccSamples.size() >= 15) {
                        linAccSamples.removeFirst();
                        linAccSamples.addLast(new double[] {xValue, yValue, zValue});
                    } else {
                        linAccSamples.addLast(new double[] {xValue, yValue, zValue});
                    }
                    Log.d(LOG_TAG, "The size of the linacc array is " + linAccSamples.size());
                } else if (sensor.getType() == Sensor.TYPE_GYROSCOPE) {
                    double xValue = event.values[0];
                    double yValue = event.values[1];
                    double zValue = event.values[2];
                    // Use LinkedList
                    if (gyroSamples.size() >= 15) {
                        gyroSamples.removeFirst();
                        gyroSamples.addLast(new double[]{xValue, yValue, zValue});
                    } else {
                        gyroSamples.addLast(new double[] {xValue, yValue, zValue});
                    }
                    Log.d(LOG_TAG, "The size of the gyro array is " + gyroSamples.size());

                    // Old array based method
//                    System.arraycopy(gyroSample, 0, gyroSample, 1, 14);
//                    gyroSample[0] = new double[] {xValue, yValue, zValue};
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        // delay_game is 20ms (50hz), delay_normal is 200ms (5hz), delay_ui is 60ms (16.7hz)
        sensorManager.registerListener(sensorListener, linearAccelerometer, SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(sensorListener, gyroscope, SensorManager.SENSOR_DELAY_GAME);
    }
}
