package com.yin.hy.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;

import android.net.NetworkInfo;
import android.widget.Toast;
//网络状态监听
public class NetStateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        Network[] network = connectivityManager.getAllNetworks();
        if(network == null){
            return;
        }
        NetworkInfo networkInfo;
        for(Network net:network){
             networkInfo = connectivityManager.getNetworkInfo(net);
            if (networkInfo.isAvailable()){
                return;
            }
        }
        Toast.makeText(context, "网络不可用!", Toast.LENGTH_LONG).show();
    }
}
