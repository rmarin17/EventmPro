package unicauca.movil.eventmpro.beacons;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import io.reactivex.subjects.PublishSubject;


public class BeaconReceiver extends BroadcastReceiver {

    private int resultmajor,resultmajor2,resultminor,resultminor2 =0;

    public static final String ACTION_BEACON = "com.example.francisco.mallsbeaconslocation.ACTION_BEACONS";
    public static final String EXTRA_MAJOR = "major";
    public static final String EXTRA_MINOR = "minor";
    public static final String EXTRA_MAJOR2 = "major2";
    public static final String EXTRA_MINOR2 = "minor2";


    PublishSubject<Integer[]> subject;
    Integer[] data = null;

    public BeaconReceiver() {

        subject = PublishSubject.create();
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle extras =  intent.getExtras();
        int major = extras.getInt(EXTRA_MAJOR);
        int minor = extras.getInt(EXTRA_MINOR);
        int major2 = extras.getInt(EXTRA_MAJOR2);
        int minor2 = extras.getInt(EXTRA_MINOR2);

        Log.e("Reactive", "major: "+ major);
        Log.e("Reactive", "major: "+ major2);

        subject.onNext(new Integer[]{major, major2});
        //subject.onNext(new Integer[]{major});


/************************************************************************************************************************************/
    }


    public PublishSubject<Integer[]> getBeaconNotify(){
        return subject;
    }

}
