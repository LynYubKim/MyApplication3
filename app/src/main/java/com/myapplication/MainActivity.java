package com.myapplication;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.SystemRequirementsChecker;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

 private BeaconManager beaconManager;
 private Region region;
 private TextView tvId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvId = (TextView) findViewById(R.id.tvId);
         beaconManager = new BeaconManager(this);
           //add this below:
         beaconManager.setRangingListener(new BeaconManager.RangingListener() {
         @Override
         public void onBeaconsDiscovered(Region region, List<Beacon> list) {
         if (!list.isEmpty()) {
         Beacon nearestBeacon = list.get(0);
         Log.d("Airport", "Nearest places: " + nearestBeacon.getRssi());
         tvId.setText(nearestBeacon.getRssi() + "");
         }
         }
         });


         region = new Region("ranged region",
         UUID.fromString("E2C56DB5-DFFB-48D2-B060-D0F5A71096E0"), 40001,10618);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SystemRequirementsChecker.checkWithDefaultDialogs(this);
         beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
         @Override
                  public void onServiceReady() {
         beaconManager.startRanging(region);
         }
         });
    }

    @Override
    protected void onPause() {
        //beaconManager.stopRanging(region); super.onPause();
        super.onPause();
    }
}

