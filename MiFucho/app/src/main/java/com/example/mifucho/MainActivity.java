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
        
        if (balon.getY() < 0){
            balon.setY(0);
        }else if((balon.getY()+ balon.getWidth()) > height && height != 0){
            balon.setY(height- balon.getWidth());
        }else {
            balon.setY(balon.getY()+(rotationSensorReading[1]*10));
        }

        if (balon.getX() + balon.getWidth() -15 >= port.getX() && balon.getX() - 15 <= port.getX() + port.getWidth()){
            if (balon.getY() + 15 >= port.getY() && balon.getY() + 15 <= port.getY() + port.getHeight()) {
                goles2.setText(((Integer.parseInt(goles2.getText().toString()))+1)+"");
                balon.setX(width/2);
                balon.setY(height/2);


            }
        }
        if (balon.getX() + balon.getWidth() - 15 >= port2.getX() && balon.getX() - 15 <= port2.getX() + port2.getWidth()){
            if (balon.getY() + balon.getHeight() - 15 >= port2.getY() && balon.getY() - 15 <= port2.getY() + port2.getHeight()) {
                goles.setText(((Integer.parseInt(goles.getText().toString()))+1)+"");
                balon.setX(width/2);
                balon.setY(height/2);


            }
        }    


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
