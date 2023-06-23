package hu.unideb.mobilsz14.jump;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private MediaPlayer mediaPlayer;
    private Sensor accSensor;
    private SensorManager sensorManager;
    private TextView speedMeter;
    private float speed = 0;
    long measureTime = 300;
    long currenttime = System.currentTimeMillis();
    double prevMagnitude = 0;
    private int jumpCounter;
    


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        speedMeter = findViewById(R.id.CounttextView);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accSensor !=null){

            sensorManager.registerListener(this,accSensor,SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (mediaPlayer==null){
            mediaPlayer = MediaPlayer.create(this,R.raw.jump);

        }

    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        float x = sensorEvent.values[0];
        float y = sensorEvent.values[1];
        float z = sensorEvent.values[2];


        long elapsed = System.currentTimeMillis()-currenttime;
        //double magnitude = z-SensorManager.GRAVITY_EARTH;
        double magnitude = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
        if(500<elapsed) {

            if(magnitude<4.5){
                //speedMeter.setText("you jumped wth" + Double.toString(magnitude));
                jumpCounter++;
                speedMeter.setText(Integer.toString(jumpCounter));

                mediaPlayer.start();
            }
            currenttime = System.currentTimeMillis();
           }

            /*
        if (magnitude<=0.0){
            speedMeter.setText("we got a jump");
        }

        if(500<elapsed) {
            if (Math.abs(magnitude-prevMagnitude)>1.0){
                speed += magnitude * measureTime / 1000;
                String str = speedMeter.getText().toString() + "\n";
                speedMeter.setText(str +  Double.toString(speed));
            }
            prevMagnitude = magnitude;
            //speedMeter.setText(Float.toString(speed));
            //String str = speedMeter.getText().toString() + "\n";
            //speedMeter.setText(str +  Double.toString(speed));
            currenttime = System.currentTimeMillis();
        }


            //System.out.println(speed);
            //elapsed = (System.currentTimeMillis()-curr_time)/1000;
            //System.out.println(elapsed);
            // curr_time = System.currentTimeMillis();

/*
        if (magnitude >= 15.0) {

            if (mediaPlayer==null){
                mediaPlayer = MediaPlayer.create(this,R.raw.jump);

            }
            mediaPlayer.start();
        }
         */

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}