package telecoms.bccres.monitoring;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;


public class SiteDetail extends AppCompatActivity {


    SwipeRefreshLayout swipeRef;

    TextView titleCdm, ping, powLev, ebNo, freq, bucCur, bucVolt, lnbCur, lnbVolt;
    TextView slot, rFreq, ber, esNo, circuitID, demodcdd, barTitle, contact;


    Handler handler = new Handler();
    int apiDelayed = 65 * 1000;
    Runnable runnable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.site_detail);
        swipeRef = findViewById(R.id.swipRefresh);


        Toolbar titlebar = findViewById(R.id.toolbar);
        barTitle = titlebar.findViewById(R.id.barTitle);
        setSupportActionBar(titlebar);
        barTitle.setText(getIntent().getStringExtra("site") + " (NODE DETAILS)");

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        titleCdm = findViewById(R.id.titleCdm);
        ping = findViewById(R.id.ping);
        powLev = findViewById(R.id.powLev);
        ebNo = findViewById(R.id.ebNo);
        freq = findViewById(R.id.freq);
        bucCur = findViewById(R.id.bucCur);
        bucVolt = findViewById(R.id.bucVolt);
        lnbCur = findViewById(R.id.lnbCur);
        lnbVolt = findViewById(R.id.lnbVolt);

        slot = findViewById(R.id.slot);
        rFreq = findViewById(R.id.rFreq);
        ber = findViewById(R.id.ber);
        esNo = findViewById(R.id.esNo);
        circuitID = findViewById(R.id.circuitID);
        demodcdd = findViewById(R.id.demodcdd);

        Button call = findViewById(R.id.call);
        contact = findViewById(R.id.siteCont);


        titleCdm.setText(getIntent().getStringExtra("modem"));
        powLev.setText(getIntent().getStringExtra("PowLev"));

        ebNo.setText(getIntent().getStringExtra("Eb_No"));
        freq.setText(getIntent().getStringExtra("Freq"));
        bucCur.setText(getIntent().getStringExtra("Buc_Cur"));
        bucVolt.setText(getIntent().getStringExtra("Buc_Volt"));
        lnbCur.setText(getIntent().getStringExtra("Lnb_Cur"));
        lnbVolt.setText(getIntent().getStringExtra("Lnb_Volt"));
        ping.setText(getIntent().getStringExtra("Ping"));

        demodcdd.setText(getIntent().getStringExtra("CDD"));
        slot.setText(getIntent().getStringExtra("Slot"));
        rFreq.setText(getIntent().getStringExtra("Rx_Freq"));
        esNo.setText(getIntent().getStringExtra("EsNo"));
        ber.setText(getIntent().getStringExtra("BER"));
        circuitID.setText(getIntent().getStringExtra("CircuitID"));
        contact.setText(getIntent().getStringExtra("Contact"));


        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (checkCallPermission()) {

                    Intent call = new Intent(Intent.ACTION_DIAL);
                    String tel = contact.getText().toString();

                    call.setData(Uri.parse("tel:" + tel));

                    startActivity(call);

                }

            }
        });

        swipeRef.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {


            @Override
            public void onRefresh() {

                doUpdate();
                new LongOperation().execute();

            }

            private void doUpdate() {

                swipeRef.setRefreshing(false);
            }
        });
    }


    protected void onResume() {

        super.onResume();

        handler.postDelayed(runnable = () -> {
            new LongOperation().execute();
            handler.postDelayed(runnable, apiDelayed);
        }, apiDelayed);
    }

    @Override
    protected void onPause() {

        super.onPause();
        handler.removeCallbacks(runnable);
    }


    private boolean checkCallPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(SiteDetail.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(SiteDetail.this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        1);
                return false;
            }
            return true;
        }
        return true;
    }

    private class LongOperation extends AsyncTask<String, Void, Void> {

        // Required initialization


        private String Content;
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(SiteDetail.this);
        String data = "";
        int sizeData = 0;


        protected void onPreExecute() {
            // NOTE: You can call UI Element here.

            //Start Progress Dialog (Message)

            Dialog.setMessage("Please wait..");
            Dialog.show();
            Dialog.setCancelable(false);
            Dialog.setCanceledOnTouchOutside(false);

            try {
                // Set Request parameter
                data += "&" + URLEncoder.encode("site", "UTF-8") + "=" + getIntent().getStringExtra("site").toString();


            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        // Call after onPreExecute method
        protected Void doInBackground(String... urls) {

            /************ Make Post Call To Web Server ***********/
            BufferedReader reader = null;

            // Send data
            try {

                // Defined URL  where to send data
                URL url = new URL("http://172.16.3.102/select.php");

                // Send POST data request

                URLConnection conn = url.openConnection();

                conn.setConnectTimeout(15000);//define connection timeout
                conn.setReadTimeout(15000);//define read timeout
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data);
                wr.flush();

                // Get the server response

                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;


                // Read Server Response
                while ((line = reader.readLine()) != null) {
                    // Append server response in string
                    sb.append(line + " ");
                }

                // Append Server Response To Content String
                Content = sb.toString();


            } catch (Exception ex) {
                Error = ex.getMessage();
            } finally {
                try {

                    reader.close();
                } catch (Exception ex) {
                }
            }


            return null;
        }

        protected void onPostExecute(Void unused) {
            // NOTE: You can call UI Element here.

            // Close progress dialog
            Dialog.dismiss();

            if (Error != null) {

                Toast.makeText(getApplicationContext(), "Error encountered", Toast.LENGTH_LONG).show();

            } else {


                try {

                    JSONArray jArray = new JSONArray(Content);

                    // Extract data from json and store into ArrayList as class objects
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject json_data = jArray.getJSONObject(i);

                        ping.setText(json_data.getString("Ping") + " ms");
                        powLev.setText(json_data.getString("PowLev"));
                        ebNo.setText(json_data.getString("Eb_No") + " (dB)");
                        freq.setText(json_data.getString("Freq") + " MHz");
                        bucCur.setText(json_data.getString("Buc_Cur") + " mA");
                        bucVolt.setText(json_data.getString("Buc_Volt") + " Volt");
                        lnbCur.setText(json_data.getString("Lnb_Cur") + " mA");
                        lnbVolt.setText(json_data.getString("Lnb_Volt") + " Volt");
                        slot.setText(json_data.getString("Slot"));
                        rFreq.setText(json_data.getString("Rx_Freq") + " MHz");
                        ber.setText(json_data.getString("BER"));
                        esNo.setText(json_data.getString("EsNo") + " (dB)");
                        circuitID.setText(json_data.getString("CircuitID"));
                        demodcdd.setText(json_data.getString("CDD"));

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }
    }
}













