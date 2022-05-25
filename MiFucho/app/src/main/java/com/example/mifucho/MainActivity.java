package com.example.mifucho;


import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor sensor;
    private final float[] rotationSensorReading = new float[3];
    ImageView balon;
    ImageView port;
    ImageView port2;
    TextView goles;
    TextView goles2;
    ConstraintLayout cancha;
    int width;
    int height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cancha = findViewById(R.id.cancha);
        balon = findViewById(R.id.balon);
        port = findViewById(R.id.port);
        port2 = findViewById(R.id.port2);
        goles = findViewById(R.id.marcador);
        goles2 = findViewById(R.id.marcador2);

        sensorManager = (SensorManager) getSystemService(this.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Sensor acelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (acelerometer != null) {
            sensorManager.registerListener(this, acelerometer,
                    SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            System.arraycopy(event.values, 0, rotationSensorReading,
                    0, rotationSensorReading.length);
        }

        width = cancha.getWidth();
        height = cancha.getHeight();
        if(balon.getX() < 0){
            balon.setX(0);
        }else if((balon.getX()+ balon.getHeight()) > width && width != 0){
            balon.setX(width- balon.getHeight());
        }else {
            balon.setX(balon.getX()-(rotationSensorReading[0]*10));
        }



       



    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}