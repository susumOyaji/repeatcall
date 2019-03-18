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
//import android.support.v7.app.AppCompatActivity;

import android.widget.Toast;
import android.net.Uri;
import android.telecom.ConnectionRequest;
import android.telecom.ConnectionService;
import android.telecom.PhoneAccount;
import android.telecom.PhoneAccountHandle;
import android.telecom.Connection;
import java.lang.Object;
import android.content.ComponentName;
import com.example.repeatcall.MyConnectionService;
import android.widget.EditText;



public class MainActivity extends FlutterActivity {
  private static final String CHANNEL = "samples.flutter.io/battery";
  private static final int PERMISSION_REQUEST_READ_PHONE_STATE = 1;
 
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
        TelecomManager manager = (TelecomManager) getSystemService(TELECOM_SERVICE);
        
       
        //setContentView(R.layout.activity_main);

        Toast.makeText(this, "Started the app", Toast.LENGTH_SHORT).show();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_DENIED || 
                      checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_DENIED) {
                String[] permissions = {Manifest.permission.READ_PHONE_STATE, Manifest.permission.CALL_PHONE};
                requestPermissions(permissions, PERMISSION_REQUEST_READ_PHONE_STATE);
            }
        }
        new MethodChannel(getFlutterView(), CHANNEL).setMethodCallHandler(
                new MethodCallHandler() {
                    @Override
                    public void onMethodCall(MethodCall call, Result result) {//TODO
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_READ_PHONE_STATE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission granted: " + PERMISSION_REQUEST_READ_PHONE_STATE, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission NOT granted: " + PERMISSION_REQUEST_READ_PHONE_STATE, Toast.LENGTH_SHORT).show();
                }

                return;
            }
        }
    }


    







    
  private int getBatteryLevel() {

    //Intent callIntent = new Intent(Intent.ACTION_CALL);
    //callIntent.setData(Uri.parse("tel:"+"1234567890")); 
    //Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "1234567890"));
    //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    //startActivity(intent);
    //Intent intentcall = new Intent();
    //intentcall.setAction(Intent.ACTION_DIAL);
    //intentcall.setData(Uri.parse("tel:" + "12345687890"));
    //startActivity(intentcall);

    //Uri uri = Uri.parse("tel:1111111111");
    //Intent i = new Intent(Intent.ACTION_CALL,uri);
    //startActivity(i);
    //String resule= isInCall();

    //MyConnectionService myConnectionService = new MyConnectionService();
    //myConnectionService.call();

    performDial();
   

    /*
    TelecomManager manager = (TelecomManager) getSystemService(TELECOM_SERVICE);
    PhoneAccountHandle phoneAccountHandle = new PhoneAccountHandle(
            new ComponentName(getPackageName(),MyConnectionService.class.getName()), "myConnectionServiceId");
    Bundle test = new Bundle();
    test.putParcelable(TelecomManager.EXTRA_PHONE_ACCOUNT_HANDLE, phoneAccountHandle);
    manager.placeCall(Uri.parse("tel:" + "1212312312"), test);
    */

    
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
    
     String mEditText_number = "0123456789";
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
