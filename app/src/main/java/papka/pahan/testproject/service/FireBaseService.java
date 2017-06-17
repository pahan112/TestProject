package papka.pahan.testproject.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by admin on 16.06.2017.
 */

public class FireBaseService extends Service {


    final String LOG_TAG = "myLogs";

    private SensorManager mSensorManager;
    private Sensor mOrientation;
    private float xy_angle;
    private float xz_angle;
    private float zy_angle;

    DatabaseReference mDatabaseReference;

    SensorEventListener mListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            xy_angle = event.values[0];
            xz_angle = event.values[1];
            zy_angle = event.values[2];

            Log.d(LOG_TAG, "x = " + xy_angle);
            Log.d(LOG_TAG, "z = " + xz_angle);
            Log.d(LOG_TAG, "y = " + zy_angle);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    public void onCreate() {
        super.onCreate();
        Log.d(LOG_TAG, "MyService onCreate");
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("fdsfsd");
        mDatabaseReference.child("xdsfsdf").setValue("fsdfsdfsdf");

    }

    public void onDestroy() {
        Log.d(LOG_TAG, "MyService onDestroy");
        mSensorManager.unregisterListener(mListener);
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(mListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),200000);
        Log.d(LOG_TAG, "MyService sdfsdgsdgsdgsdg");


        return START_STICKY;
    }
}
