package telecoms.bccres.monitoring;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class GlobalV extends AppCompatActivity {

    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;

    Handler handler = new Handler();
    private Dialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_glview);

        SwipeRefreshLayout swipeRef = findViewById(R.id.swiprefresh);
        new AsyncFetch().execute();

        swipeRef.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new GlobalV.AsyncFetch().execute();
                doUpdate();

            }

            private void doUpdate() {
                swipeRef.setRefreshing(false);
            }
        });
    }

    private void showDialog(String title, String message) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(GlobalV.this)
                .setTitle(title)
                .setCancelable(false)
                .setIcon(R.drawable.launcher)
                .setMessage(message)
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });

        if (mDialog != null) {

            mDialog.dismiss();
        }

        mDialog = dialogBuilder.show();

    }

    public class AsyncFetch extends AsyncTask<String, String, String> {

        ProgressDialog pdLoading = new ProgressDialog(GlobalV.this);

        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {

                // Enter URL address where your json file resides
                // Even you can make call to php file which returns json data
                url = new URL("http://104.248.38.196/myapi/restapi.php");

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return e.toString();
            }
            try {

                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("GET");

                // setDoOutput to true as we recieve data from json file
                conn.setDoOutput(true);

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return e1.toString();
            }

            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pass data to onPostExecute method
                    return (result.toString());

                } else {

                    return ("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            } finally {
                conn.disconnect();
            }
        }

        @Override
        protected void onPostExecute(String result) {

            //this method will be running on UI thread
            pdLoading.dismiss();
            List<DataSite> data = new ArrayList<>();
            pdLoading.dismiss();

            try {

                JSONArray jArray = new JSONArray(result);

                // Extract data from json and store into ArrayList as class objects
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json_data = jArray.getJSONObject(i);
                    DataSite siteData = new DataSite(null);
                    siteData.name = json_data.getString("site");
                    siteData.modem = json_data.getString("modem");
                    siteData.ping = json_data.getString("Ping");
                    siteData.router = json_data.getString("Router");

                    siteData.powLev = json_data.getString("PowLev");
                    siteData.freq = json_data.getString("Freq");
                    siteData.ebNo = json_data.getString("Eb_No");
                    siteData.bucCur = json_data.getString("Buc_Cur");
                    siteData.bucVolt = json_data.getString("Buc_Volt");
                    siteData.lnbCur = json_data.getString("Lnb_Cur");
                    siteData.lnbVolt = json_data.getString("Lnb_Volt");

                    siteData.slot = json_data.getString("Slot");
                    siteData.esNo = json_data.getString("EsNo");
                    siteData.ber = json_data.getString("BER");
                    siteData.rxFreq = json_data.getString("Rx_Freq");
                    siteData.demodcdd = json_data.getString("CDD");
                    siteData.circuitID = json_data.getString("CircuitID");

                    siteData.rPing = json_data.getString("R_Ping");
                    siteData.contact = json_data.getString("Contact");

                    data.add(siteData);

                }

                RecyclerView recyclerView = findViewById(R.id.siteparam);
                AdapterGl adapter = new AdapterGl(data);
                recyclerView.setLayoutManager(new LinearLayoutManager(GlobalV.this));
                recyclerView.setAdapter(adapter);

            } catch (JSONException e) {

                String title = "Info!";
                String message = "Server Unavailable check your Internet and Swipe down to refresh ...";
                showDialog(title, message);

            }
        }
    }
}
