package com.example.repeatcall;


import android.os.Bundle;
import io.flutter.app.FlutterActivity;
import io.flutter.plugins.GeneratedPluginRegistrant;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.BatteryManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.telecom.TelecomManager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;

import android.widget.Toast;
import android.net.Uri;
import android.telecom.ConnectionRequest;
import android.telecom.ConnectionService;
import android.telecom.PhoneAccount;
import android.telecom.PhoneAccountHandle;
import android.telecom.Connection;
import java.lang.Object;
import android.content.ComponentName;
//import com.example.repeatcall.MyConnectionService;
import android.widget.EditText;
import android.telecom.Call;


public class MainActivity extends FlutterActivity {
  private static final String CHANNEL = "samples.flutter.io/battery";
  private static final int PERMISSION_REQUEST_READ_PHONE_STATE = 1;
  private static Call call;
  /*
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    GeneratedPluginRegistrant.registerWith(this);
    new MethodChannel(getFlutterView(), CHANNEL).setMethodCallHandler(
                new MethodCallHandler() {
                    @Override
                    public void onMethodCall(MethodCall call, Result result) {
                      if (call.method.equals("getBatteryLevel")) {
                        int batteryLevel = getBatteryLevel();

                        if (batteryLevel != -1) {
                          result.success(batteryLevel);
                        } else {
                          result.error("UNAVAILABLE", "Battery level not available.", null);
                        }
                      } else {
                        result.notImplemented();
                      }   // TODO
                    }
                });
  }//onCreate
  */
  
  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GeneratedPluginRegistrant.registerWith(this);
        
        new App();
        Toast.makeText(MainActivity.this, "Started the MainActivity.app", Toast.LENGTH_SHORT).show();
        CallService CallService = new CallService();
        //CallService.onCallAdded();

        new MethodChannel(getFlutterView(), CHANNEL).setMethodCallHandler(
          new MethodCallHandler() {
                    @Override
                    public void onMethodCall(MethodCall call, Result result) {//TODO
                    Toast.makeText(MainActivity.this, "Started theMethodChannel ", Toast.LENGTH_SHORT).show();
                      if (call.method.equals("getBatteryLevel")) {
                        int batteryLevel = getBatteryLevel();

                        if (batteryLevel != -1) {
                          result.success(batteryLevel);
                        } else {
                          result.error("UNAVAILABLE", "Battery level not available.", null);
                        }
                      } else {
                        result.notImplemented();
                      }   // TODO
                    }
                }
          );
    }
   

    







    
  private int getBatteryLevel() {
    Toast ts = Toast.makeText(MainActivity.this, "getBatteryLeval",Toast.LENGTH_SHORT);
     ts.show();


    int batteryLevel = -1;
    if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
      BatteryManager batteryManager = (BatteryManager) getSystemService(BATTERY_SERVICE);
      batteryLevel = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
    } else {
      Intent bintent = new ContextWrapper(getApplicationContext()).
        registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
      batteryLevel = (bintent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) * 100) /
        bintent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
    }
      return batteryLevel;
  }


  public void performDial(){
    
     String mEditText_number = "099123456789";
     if(mEditText_number!=null){
       try {
         startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mEditText_number)));
         Toast.makeText(this, "Started the performDial", Toast.LENGTH_SHORT).show();
       } catch (Exception e) {
         e.printStackTrace();
       }
     }
   }


   

}
