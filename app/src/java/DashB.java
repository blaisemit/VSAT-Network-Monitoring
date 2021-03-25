package telecoms.bccres.monitoring;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

public class DashB extends AppCompatActivity {
    CardView npmview, globalview;
    Activity activity = (Activity) this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashb);

        npmview = findViewById(R.id.npm);
        npmview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Handler handler = new Handler();
                handler.postDelayed(() -> {


                    Intent intent = new Intent(DashB.this, MainActivity.class);
                    startActivity(intent);

                    activity.overridePendingTransition(R.anim.activity_in, R.anim.activity_out);

                }, 300);

            }
        });


        globalview = findViewById(R.id.sum);
        globalview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Handler handler = new Handler();
                handler.postDelayed(() -> {


                    Intent intent = new Intent(DashB.this, GlobalV.class);
                    startActivity(intent);

                    activity.overridePendingTransition(R.anim.activity_in, R.anim.activity_out);

                }, 300);


            }
        });


    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(DashB.this);
        builder.setMessage("Are you sure you want to exit?");
        builder.setCancelable(false);
        builder.setIcon(R.drawable.launcher);
        builder.setTitle("Exit!");
        builder.setPositiveButton("Yes", (dialog, which) -> {
            DashB.this.finish();
            ActivityCompat.finishAffinity(DashB.this);

        });

        builder.setNegativeButton("No", (dialog, which) -> dialog.cancel());

        AlertDialog alert = builder.create();
        alert.show();

    }


}

