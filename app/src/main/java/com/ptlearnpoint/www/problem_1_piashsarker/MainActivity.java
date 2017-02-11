package com.ptlearnpoint.www.problem_1_piashsarker;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PackageManager manager = this.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(this.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Toast.makeText(this,
                "PackageName = " + info.packageName + "\nVersionCode = "
                        + info.versionCode + "\nVersionName = "
                        + info.versionName + "\nPermissions = " + info.permissions, Toast.LENGTH_SHORT).show();

        WifiManager wm = (WifiManager) getSystemService(WIFI_SERVICE);
        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
        TelephonyManager mngr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String imei = mngr.getDeviceId();
        String countryCode = mngr.getSimCountryIso();
        Toast.makeText(this, ip +" "+imei +""+ Build.ID +" "+countryCode +" "+Build.MANUFACTURER +" "+Build.BRAND+" "+ Build.MODEL,Toast.LENGTH_LONG).show();

        if(isNetworkConnected()){
            final ProgressDialog dialog ;
            dialog = new ProgressDialog(this);
            dialog.setTitle("Wait!");
            dialog.setMessage("Loading......");
            dialog.show();

            ApiService api = RetroClient.getApiService();

            Call<Response> call = api.postPhoneInfo(info.packageName,info.versionName,String.valueOf(info.versionCode),ip,imei,Build.ID,countryCode,Build.MANUFACTURER,Build.BRAND,Build.MODEL,"pt_sarker");
            call.enqueue(new Callback<Response>() {
                @Override
                public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                  dialog.hide();
                    if(response.isSuccess()){
                        Toast.makeText(MainActivity.this, "Posting Successfull", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Response> call, Throwable t) {
                dialog.hide();
                    Toast.makeText(MainActivity.this, "Server Problem", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }

    }




    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
}
