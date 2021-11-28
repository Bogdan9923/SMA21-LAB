package com.upt.cti.smartwallet;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AppState {

    public void updateLocalBackup(Context context, Payment payment, boolean toAdd) {
        String fileName = payment.timestamp;

        if (toAdd) {
            // save to file
        } else {
            context.deleteFile(fileName);
        }
    }

    public boolean hasLocalStorage(Context context) {
        return context.getFilesDir().listFiles().length > 0;
    }

    public List<Payment> loadFromLocalBackup(Context context, String month) {

            List<Payment> payments = new ArrayList<>();

            for (File file : context.getFilesDir().listFiles()) {

                }

            return payments;

    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
