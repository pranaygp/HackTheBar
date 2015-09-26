package com.hackthebar.pranaygp.drinkup;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by pranaygp on 9/25/15.
 */
public class beerTapSocket extends AsyncTask<String, Void, Void> {

    private Context mContext;
    private static boolean getData;
    private NetClient nc;

    public beerTapSocket(Context mContext){
        this.mContext = mContext;
    }

    @Override
    protected Void doInBackground(String... strings) {
        getData = true;
        nc = new NetClient("172.17.111.66", 1338, null);
        while (getData) {

            nc.sendDataWithString(strings[0]);
            Log.d("Data Request", "doInBackground : requestingData");

            String response = nc.receiveDataFromServer();

            Log.i("Data Received: ", response);

            Intent data = new Intent(mContext, MainActivity.class);
            data.putExtra("response_data", response);
            data.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            mContext.startActivity(data);

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Log.d("BTS", "STOPPED READING DATA");
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Log.i("beerTapSocket", "onPostExecute : sent Data");


    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        Log.d("BTS", "cancel command received");
        nc.sendDataWithString("0\n");
        getData = false;
        nc.disConnectWithServer();

    }
}
