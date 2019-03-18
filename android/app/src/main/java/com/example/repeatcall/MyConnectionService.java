package com.example.repeatcall;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

//import android.support.annotation.Nullable;
import android.widget.Toast;
import android.util.Log;
import android.telecom.Connection;
import android.telecom.ConnectionService;
import android.telecom.DisconnectCause;
import android.telecom.PhoneAccountHandle;
import android.telecom.ConnectionRequest;
import android.telecom.TelecomManager;
import android.content.ComponentName;
import android.telecom.PhoneAccount;
import android.os.Bundle;
import android.net.Uri;



public class MyConnectionService extends ConnectionService {
public static final String TAG = MyConnectionService.class.getName();
@Override
public int onStartCommand(Intent intent,int flags, int startId) {
    Log.d(TAG, "On Start");
    return super.onStartCommand(intent, flags, startId);
}

@Override//Start Up
public Connection onCreateOutgoingConnection(PhoneAccountHandle connectionManagerPhoneAccount, ConnectionRequest request) {
    Connection connection = super.onCreateOutgoingConnection(connectionManagerPhoneAccount, request);
    Log.d(TAG, connection.getDisconnectCause().getReason());
    return connection;
}

@Override
public void onCreateOutgoingConnectionFailed(PhoneAccountHandle connectionManagerPhoneAccount, ConnectionRequest request) {
    if (request != null) {
        Log.d(TAG, request.toString());
    }
    super.onCreateOutgoingConnectionFailed(connectionManagerPhoneAccount, request);
}

@Override
public Connection onCreateIncomingConnection(PhoneAccountHandle connectionManagerPhoneAccount, ConnectionRequest request) {

    Toast.makeText(getApplicationContext(), "onCreateIncomingConnection called", Toast.LENGTH_SHORT).show();
    Connection incomingCallCannection = createConnection(request);
    incomingCallCannection.setRinging();
    return incomingCallCannection;
}

/*
@Override
public Connection onCreateOutgoingConnection(PhoneAccountHandle connectionManagerPhoneAccount, ConnectionRequest request) {
    Toast.makeText(getApplicationContext(), "onCreateOutgoingConnection called", Toast.LENGTH_SHORT).show();

    Connection outgoingCallConnection = createConnection(request);
    outgoingCallConnection.setDialing();

    return outgoingCallConnection;
}
*/
Connection mConnection;
private Connection createConnection(ConnectionRequest request) {
    mConnection = new Connection() {
        @Override
        public void onStateChanged(int state) {
            super.onStateChanged(state);
        }

        @Override
        public void onDisconnect() {
            super.onDisconnect();
            mConnection.setDisconnected(new DisconnectCause(DisconnectCause.CANCELED));
            //mConnectionsAvailableForConference.clear();
            mConnection.destroy();
        }

        @Override
        public void onSeparate() {
            super.onSeparate();
        }

        @Override
        public void onAbort() {
            super.onAbort();
            mConnection.setDisconnected(new DisconnectCause(DisconnectCause.CANCELED));
            mConnection.destroy();
        }

        @Override
        public void onHold() {
            super.onHold();
        }

        @Override
        public void onAnswer() {
            super.onAnswer();
            mConnection.setActive();
        }

        @Override
        public void onReject() {
            super.onReject();
            mConnection.setDisconnected(new DisconnectCause(DisconnectCause.CANCELED));
            mConnection.destroy();

        }
    };
    mConnection.setAddress(request.getAddress(), TelecomManager.PRESENTATION_ALLOWED);
    mConnection.setExtras(request.getExtras());
    return mConnection;
}





public void register() {
    TelecomManager manager = (TelecomManager) getSystemService(TELECOM_SERVICE);
    PhoneAccountHandle phoneAccountHandle = new PhoneAccountHandle(
            new ComponentName(getPackageName(),
                    MyConnectionService.class.getName()), "MyConnectionServiceId");
    PhoneAccount.Builder builder = PhoneAccount.builder(phoneAccountHandle, "CustomAccount");
    builder.setCapabilities(PhoneAccount.CAPABILITY_CALL_PROVIDER | PhoneAccount.CAPABILITY_CONNECTION_MANAGER);
    PhoneAccount phoneAccount = builder.build();
    manager.registerPhoneAccount(phoneAccount);
}

//呼び出しを開始するcall（）メソッド
public void call() {
    TelecomManager manager = (TelecomManager) getSystemService(TELECOM_SERVICE);
    PhoneAccountHandle phoneAccountHandle = new PhoneAccountHandle(
            new ComponentName(getPackageName(),
                    MyConnectionService.class.getName()), "MyConnectionServiceId");
    Bundle test = new Bundle();
    test.putParcelable(TelecomManager.EXTRA_PHONE_ACCOUNT_HANDLE, phoneAccountHandle);
    manager.placeCall(Uri.parse("tel:" + "1212312312"), test);
}

}




