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

 Assignment Notes: For this activity, every time you shake the device, the counter
 on the screen should go up.  Note that there is no specific "shake listener,"
 unless you build your own.  I chose to add a SensorEventListener to this Activity
 to listen for accelerometer movements.  You will have to determine what's hard enough
 a movement to warrant a "shake."

 Reference:
 http://joerichard.net/android/android-shake-detector/

 More references:
 https://stackoverflow.com/questions/5271448/how-to-detect-shake-event-with-android
 http://jasonmcreynolds.com/?p=388
 https://stackoverflow.com/questions/2317428/android-i-want-to-shake-it

 */

public class ShakeActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager sensorManager;

    private float mAccel; // acceleration without gravity accounted for
    private float mAccelCurrent; // acceleration with gravity accounted for
    private float mAccelLast; // last acceleration with gravity accounted for

    private int mShakeCount = 0;
    TextView shakeCountTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake);

        // Add code to intialize the sensorManager and accelerometer
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

        // initialize acceleration metrics to default
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;

        // get shake count text view
        shakeCountTextView = (TextView) findViewById(R.id.shakeCountTextView);

    }

    @Override
    public void onResume() {

        // Add a line to register the Session Manager Listener
        super.onResume();
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    public void onPause() {

        // Add a line to unregister the Sensor Manager
        super.onPause();
        sensorManager.unregisterListener(this);

    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Nothing needs to be added here.
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        // Add code here to handle what happens when a sensor event occurs.
        float x = event.values[0]; // get x accel
        float y = event.values[1]; // get y accel
        float z = event.values[2]; // get z accel
        mAccelLast = mAccelCurrent; // save current accel
        mAccelCurrent = (float) Math.sqrt((double) (x*x + y*y + z*z)); // get new current accel
        float delta = mAccelCurrent - mAccelLast; // get difference
        mAccel = mAccel * 0.9f + delta; // perform low-cut filter

        if (mAccel > 12) { // shake threshold

            mShakeCount++; // if shake is greater than threshold, increment shake count

        }

        shakeCountTextView.setText("Shake count: " + mShakeCount); // update shake count displayed

    }

}
