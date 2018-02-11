package phg.com.automotiveoctoengine.controllers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import phg.com.automotiveoctoengine.Interfaces.NetworkStateListener;

public class NetworkStateReceiver extends BroadcastReceiver {

    private NetworkStateListener networkStateListener;

    public NetworkStateReceiver(NetworkStateListener networkStateListener) {
        this.networkStateListener = networkStateListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getExtras() != null) {
            NetworkInfo ni = (NetworkInfo) intent.getExtras().get(ConnectivityManager.EXTRA_NETWORK_INFO);
            if (ni != null && ni.getState() == NetworkInfo.State.CONNECTED) {
                Log.i("app", "Network " + ni.getTypeName() + " connected");
                networkStateListener.onNetworkAvailable();
            }
        }
        if (intent.getExtras().getBoolean(ConnectivityManager.EXTRA_NO_CONNECTIVITY, Boolean.FALSE)) {
            Log.d("app", "There's no network connectivity");
            networkStateListener.onNetworkUnavailable();
        }
    }
}
