package papka.pahan.testproject.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.concurrent.TimeUnit;

/**
 * Created by admin on 16.06.2017.
 */

public class FireBaseService extends Service {

    final String LOG_TAG = "myLogs";

    public void onCreate() {
        super.onCreate();
        Log.d(LOG_TAG, "MyService onCreate");
    }

    void someTask() {
        new Thread(new Runnable() {
            public void run() {
                for (int i = 1; i<=50; i++) {
                    Log.d(LOG_TAG, "i = " + i);
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                stopSelf();
            }
        }).start();
    }

    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.d(LOG_TAG, "MyService onRebind");
    }

    public boolean onUnbind(Intent intent) {
        Log.d(LOG_TAG, "MyService onUnbind");
        return true;
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "MyService onDestroy");
        stopSelf();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        someTask();
        Log.d(LOG_TAG, "MyService onBind");
        return new Binder();
    }
}
