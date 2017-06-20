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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import papka.pahan.testproject.data.DataXYZ;

/**
 * Created by admin on 16.06.2017.
 */

public class FireBaseService extends Service {

    final String LOG_TAG = "myLogs";

    private SensorManager mSensorManager;

    private float xy_angle;
    private float xz_angle;
    private float zy_angle;


    DateFormat mDf = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
    String mDate = mDf.format(Calendar.getInstance().getTime());

    final FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    DatabaseReference mRef = mDatabase.getReference(mDate);
    private DatabaseReference mReference;

    SensorEventListener mListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            xy_angle = event.values[0];
            xz_angle = event.values[1];
            zy_angle = event.values[2];
            long actualTime = System.currentTimeMillis() / 1000;

            String timeSec = String.valueOf(actualTime);
            String x = String.valueOf(xy_angle);
            String y = String.valueOf(xz_angle);
            String z = String.valueOf(zy_angle);

            mReference = mRef.child(timeSec);
            mReference.setValue(new DataXYZ(x, y, z));
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    public void onCreate() {
        super.onCreate();
        Log.d(LOG_TAG, "MyService onCreate");
        mDatabase.getReference().removeValue();
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
        mSensorManager.registerListener(mListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        return START_STICKY;
    }
}
