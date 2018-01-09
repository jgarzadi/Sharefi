package com.sharefi.srcit.sharefi.app;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

/**
 * Created by juan.garza on 28/08/2017.
 */

public class Connectivity {

    Context mContext;

    public Connectivity(Context context) {
        this.mContext = context;
    }

    public WifiInfo ConnWifiInfo() {
        WifiManager wfManager = (WifiManager) this.mContext.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wfInfo = wfManager.getConnectionInfo();
        return wfInfo;
    }

    public String getConnName() {
        String wfName = "";

        wfName = ConnWifiInfo().getSSID();
        wfName = wfName.replace("\"", "");

        return wfName;
    }

    public Integer getConnSpeed() {
        Integer linkSpeed = ConnWifiInfo().getLinkSpeed();

        return linkSpeed;
    }

    public Integer getConnFrequency() {
        Integer wfFrequency = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            wfFrequency = ConnWifiInfo().getFrequency();
        }
        return wfFrequency;
    }

    public boolean checkConnectivity() {
        boolean isWiFi = false;

        ConnectivityManager connManager = (ConnectivityManager) this.mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connManager.getActiveNetworkInfo();

        if (activeNetwork != null && activeNetwork.isConnected()) {
            isWiFi = activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
        }

        return isWiFi;
    }
}
