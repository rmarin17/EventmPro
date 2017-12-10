package unicauca.movil.eventmpro.beacons;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import com.estimote.coresdk.observation.region.beacon.BeaconRegion;
import com.estimote.coresdk.recognition.packets.Beacon;
import com.estimote.coresdk.service.BeaconManager;

import java.util.List;
import java.util.UUID;

/**
 * Created by jhovy on 5/11/2017.
 */

public class BeaconLocationService extends Service {
    private Looper mServiceLooper;
    private Handler mServiceHandler;

    /********************** Utilizado por ESTIMOTE *******************************************/

    int current_location=0, current_location2=0;

    private BeaconManager beaconManager;
    private BeaconRegion region;
    /*****************************************************************/

    private final class ServiceHandler extends Handler {
        private ServiceHandler(Looper looper){
            super(looper);
        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent2, int flags, int startId) {
        Log.e("Reactive", "Entro al servicio");
        final Intent intent =new Intent(BeaconReceiver.ACTION_BEACON);
        //region = new Region("ranged region", UUID.fromString("b9407f30-f5f8-466e-aff9-25556b57fe6d"), null, null);

        beaconManager = new BeaconManager(this);
        beaconManager.setRangingListener(new BeaconManager.BeaconRangingListener() {
            @Override
            public void onBeaconsDiscovered(BeaconRegion beaconRegion, List<Beacon> list) {
               // Log.e("Reactive", "beaconRegion: "+beaconRegion);
                Log.e("Reactive", "list: "+list);
                Log.e("Reactive", "Entro a onBeaconsDiscovered");
                if (!list.isEmpty()) {
                    Beacon nearestBeacon = list.get(0);
                    Log.e("Reactive", "size "+list.size());
                    switch (list.size()){

                        default:
                            Log.e("Reactive", "case 1");
                            intent.putExtra(BeaconReceiver.EXTRA_MAJOR, nearestBeacon.getMajor());
                            intent.putExtra(BeaconReceiver.EXTRA_MINOR, nearestBeacon.getMinor());
                            break;
                        /*default:
                            Log.e("Reactive", "case 2");
                            Beacon nearestBeacon2 = list.get(1);
                            intent.putExtra(BeaconReceiver.EXTRA_MAJOR, nearestBeacon.getMajor());
                            intent.putExtra(BeaconReceiver.EXTRA_MINOR, nearestBeacon.getMinor());
*//**************************************************************************************************************************//*
                            intent.putExtra(BeaconReceiver.EXTRA_MAJOR2, nearestBeacon2.getMajor());
                            intent.putExtra(BeaconReceiver.EXTRA_MINOR2, nearestBeacon2.getMinor());
                            break;*/



                    }
                    sendBroadcast(intent);

                } else {
                    Log.d("Reactive", "empty");
                    /**Como no se detectan Beacons, el indicador retorna a "0" **/
                    current_location = 0;
                    current_location2 = 0;

                }

            }

        });
        //region = new BeaconRegion("ranged region", UUID.fromString("fda50693-a4e2-4fb1-afcf-c6eb07647825"), null, null);
        //region = new BeaconRegion("ranged region", UUID.fromString("b9407f30-f5f8-466e-aff9-25556b57fe6d"), null, null);
        region = new BeaconRegion("ranged region", UUID.fromString("E2C56DB5-DFFB-48D2-B060-D0F5A71096E0"), null, null);



        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                Log.d("reactive","onServiceReady startRanging");
                Log.d("reactive","onServiceReady startRanging "+region);
                beaconManager.startRanging(region);
            }
        });





        return super.onStartCommand(intent2, flags, startId);
    }


    @Override
    public void onDestroy() {
        beaconManager.stopRanging(region);
        beaconManager.disconnect();
    }
}

