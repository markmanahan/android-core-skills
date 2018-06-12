// Mark Manahan

package cs4720.cs.virginia.edu.coreskills;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

/**

 Assignment Notes: Add code here to make the accelerometer data on the screen update
 as the device is rotated.  Check the example code at the following sites for ideas:

 http://joerichard.net/android/android-shake-detector/
 https://github.com/marksherriff/SensorExample

 More references:
 https://code.tutsplus.com/tutorials/using-the-accelerometer-on-android--mobile-22125


 */

public class AccelerometerActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager sensorManager;
    private Sensor mAccelerometer;

    TextView xTextView;
    TextView yTextView;
    TextView zTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer);

        xTextView = (TextView) findViewById(R.id.xTextView);
        yTextView = (TextView) findViewById(R.id.yTextView);
        zTextView = (TextView) findViewById(R.id.zTextView);

        // Initialize your sensorManager and listeners here
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    public void onResume() {

        // Add a line to register the Session Manager Listener
        super.onResume();
        sensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    public void onPause() {

        // Add the following line to unregister the Sensor Manager
        super.onPause();
        sensorManager.unregisterListener(this);

    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // No edits needed here.
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        // Add code here to update the screen
        float x = event.values[0]; // get x accel
        float y = event.values[1]; // get y accel
        float z = event.values[2]; // get z accel

        xTextView.setText(Float.toString(x)); // update x accel displayed on screen
        yTextView.setText(Float.toString(y)); // update y accel displayed on screen
        zTextView.setText(Float.toString(z)); // update z accel displayed on screen

    }
}
