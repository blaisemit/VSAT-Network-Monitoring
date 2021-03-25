package telecoms.bccres.monitoring;

import android.animation.ObjectAnimator;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableLayout;
import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.Utils;

import java.util.Collections;
import java.util.List;

public class AdapterSite extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    List<DataSite> data = Collections.emptyList();
    List<DataSite> dat;

    private SparseBooleanArray expandState = new SparseBooleanArray();

    private final static int TYPE_SITE = 1, TYPE_DETA = 2;

    @Override
    public int getItemCount() {

        return data.size();
    }


    // create constructor to initialize context and data sent from MainActivity
    public AdapterSite(Context context, List<DataSite> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;

        for (int i = 0; i < data.size(); i++) {
            expandState.append(i, false);
        }

    }

    public int getItemViewType(int currentPos) {

        if (data.get(currentPos) instanceof DataSite) {

            return TYPE_SITE;

        } else if (data.get(currentPos) instanceof DataSite) {

            return TYPE_DETA;

        }
        return -1;

    }


    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        int layout = 0;
        RecyclerView.ViewHolder viewHolder;


        switch (viewType) {

            case TYPE_SITE:
                layout = R.layout.container_site;
                View mainView = LayoutInflater
                        .from(parent.getContext())
                        .inflate(layout, parent, false);

                viewHolder = new SiteViewHolder(mainView);


                break;

            case TYPE_DETA:

                layout = R.layout.container_site;
                View detaView = LayoutInflater
                        .from(parent.getContext())
                        .inflate(layout, parent, false);
                viewHolder = new DetaViewHolder(detaView);


                break;
            default:
                viewHolder = null;
                break;
        }


        return viewHolder;

    }


    // Bind data
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        // Get current position of item in recyclerview to bind data and assign values from list

        int viewType = holder.getItemViewType();

        switch (viewType) {

            case TYPE_SITE:

                DataSite sitedata = data.get(position);
                ((SiteViewHolder) holder).showMainData(sitedata);
                holder.setIsRecyclable(false);
                ((SiteViewHolder) holder).expandableLayout.setInterpolator(sitedata.interpolator);
                ((SiteViewHolder) holder).expandableLayout.setExpanded(expandState.get(position));
                ((SiteViewHolder) holder).expandableLayout.collapse();
                ((SiteViewHolder) holder).expandableLayout.setListener(new ExpandableLayoutListenerAdapter() {

                    @Override
                    public void onPreOpen() {

                        createRotateAnimator(((SiteViewHolder) holder).buttonLayout, 0f, 180f).start();
                        expandState.put(position, true);
                        //super.onPreOpen();
                    }

                    @Override
                    public void onPreClose() {
                        createRotateAnimator(((SiteViewHolder) holder).buttonLayout, 180f, 0f).start();
                        expandState.put(position, false);
                        //super.onPreClose();
                    }
                });
                ((SiteViewHolder) holder).buttonLayout.setRotation(expandState.get(position) ? 180f : 0f);
                ((SiteViewHolder) holder).buttonLayout.setOnClickListener(v -> onClickButton(((SiteViewHolder) holder).expandableLayout));


                break;

            case TYPE_DETA:

                DataSite sitedeta = data.get(position);
                ((DetaViewHolder) holder).showDetaData(sitedeta);

                break;
        }
    }


    public class SiteViewHolder extends RecyclerView.ViewHolder {

        private TextView textName, txtPing, textRouter, textModem, txtrPing;
        ExpandableLayout expandableLayout;
        RelativeLayout buttonLayout;
        LinearLayout modemLayout, nameSite;

        ImageView details;

        TextView txtPowLev, txtFreq, txtEbNo, txtBucCur, txtBucVolt, txtLnbCur, txtLnbVolt, titleping;
        TextView txtSlot, txtEsNo, txtBer, txtRxFreq, txtCircuitID, txtCont, titledemod;

        Activity activity = (Activity) context;

        @SuppressLint("ClickableViewAccessibility")
        public SiteViewHolder(View itemView) {

            super(itemView);

            textName = itemView.findViewById(R.id.textName);
            nameSite = itemView.findViewById(R.id.nameSite);

            nameSite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    expandableLayout.toggle();

                }
            });

            textName.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    expandableLayout.toggle();

                    return false;
                }
            });

            textModem = itemView.findViewById(R.id.modem);
            textRouter = itemView.findViewById(R.id.txtRouter);
            expandableLayout = itemView.findViewById(R.id.expandableLayout);
            buttonLayout = itemView.findViewById(R.id.button);
            txtPing = itemView.findViewById(R.id.txtPing);
            txtrPing = itemView.findViewById(R.id.txtrPing);
            details = itemView.findViewById(R.id.details);
            modemLayout = itemView.findViewById(R.id.modemLayout);


            titledemod = itemView.findViewById(R.id.txtDemodcdd);
            titleping = itemView.findViewById(R.id.txtPing);
            txtPowLev = itemView.findViewById(R.id.txtPowLev);
            txtFreq = itemView.findViewById(R.id.txtFreq);
            txtEbNo = itemView.findViewById(R.id.txtEbNo);
            txtBucCur = itemView.findViewById(R.id.txtBucCur);
            txtBucVolt = itemView.findViewById(R.id.txtBucVolt);
            txtLnbCur = itemView.findViewById(R.id.txtLnbCur);
            txtLnbVolt = itemView.findViewById(R.id.txtLnbVolt);

            txtSlot = itemView.findViewById(R.id.txtSlot);
            txtEsNo = itemView.findViewById(R.id.txtEsNo);
            txtBer = itemView.findViewById(R.id.txtBer);
            txtRxFreq = itemView.findViewById(R.id.txtRxFreq);
            txtCircuitID = itemView.findViewById(R.id.txtCircuitID);
            txtCont = itemView.findViewById(R.id.txtContct);

            modemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final Handler handler = new Handler();
                    handler.postDelayed(() -> {

                        Intent i = new Intent(context, SiteDetail.class);

                        i.putExtra("site", textName.getText());
                        i.putExtra("modem", textModem.getText());
                        i.putExtra("PowLev", txtPowLev.getText());
                        i.putExtra("Eb_No", txtEbNo.getText());
                        i.putExtra("Freq", txtFreq.getText());
                        i.putExtra("Buc_Cur", txtBucCur.getText());
                        i.putExtra("Buc_Volt", txtBucVolt.getText());
                        i.putExtra("Lnb_Cur", txtLnbCur.getText());
                        i.putExtra("Lnb_Volt", txtLnbVolt.getText());
                        i.putExtra("CDD", titledemod.getText());
                        i.putExtra("Slot", txtSlot.getText());
                        i.putExtra("EsNo", txtEsNo.getText());
                        i.putExtra("Rx_Freq", txtRxFreq.getText());
                        i.putExtra("BER", txtBer.getText());
                        i.putExtra("CircuitID", txtCircuitID.getText());
                        i.putExtra("Ping", txtPing.getText());
                        i.putExtra("Contact", txtCont.getText());

                        context.startActivity(i);

                        activity.overridePendingTransition(R.anim.activity_in, R.anim.activity_out);


                    }, 300);

                }
            });

        }

        public void showMainData(DataSite data) {

            textName.setText(data.name);
            textModem.setText("CDM-840_" + data.modem);
            textRouter.setText("GW_" + data.router);
            txtPowLev.setText(data.powLev);
            txtFreq.setText(data.freq + " MHz");
            txtEbNo.setText(data.ebNo + " (dB)");
            txtBucCur.setText(data.bucCur + " mA");
            txtBucVolt.setText(data.bucVolt + " Volt");
            txtLnbCur.setText(data.lnbCur + " mA");
            txtLnbVolt.setText(data.lnbVolt + " Volt");
            txtSlot.setText(data.slot);
            txtEsNo.setText(data.esNo + " (dB)");
            txtBer.setText(data.ber);
            txtRxFreq.setText(data.rxFreq + " MHz");
            titledemod.setText("CDD-880_" + data.demodcdd);
            txtCircuitID.setText(data.circuitID);
            txtCont.setText(data.contact);

            int pingInt = Integer.parseInt(data.ping);
            txtPing.setText(pingInt + " ms");

            if (pingInt == -1) {
                txtPing.setTextColor(Color.RED);
                textName.setTextColor(Color.RED);

            } else if (pingInt < 750) {
                txtPing.setTextColor(Color.parseColor("#175732"));
                textName.setTextColor(Color.parseColor("#175732"));

            } else if (pingInt > 750 && pingInt < 999) {
                txtPing.setTextColor(Color.parseColor("#ffbf43"));
                textName.setTextColor(Color.parseColor("#ffbf43"));
            } else if (pingInt > 999) {
                txtPing.setTextColor(Color.RED);
                textName.setTextColor(Color.RED);
            }

            int rPingInt = Integer.parseInt(data.rPing);
            txtrPing.setText(rPingInt + " ms");


            if (rPingInt == -1) {
                txtrPing.setTextColor(Color.RED);
                textRouter.setTextColor(Color.RED);


            } else if (rPingInt == -1 && pingInt < 999) {

                textName.setTextColor(Color.parseColor("#ffbf43"));
            } else if (rPingInt < 750) {

                txtrPing.setTextColor(Color.parseColor("#175732"));
                textRouter.setTextColor(Color.parseColor("#175732"));

            } else if (rPingInt > 750 && rPingInt < 999) {
                txtrPing.setTextColor(Color.parseColor("#ffbf43"));
                textRouter.setTextColor(Color.parseColor("#ffbf43"));
            } else if (rPingInt > 999) {
                txtrPing.setTextColor(Color.RED);
                textRouter.setTextColor(Color.RED);

            } else if (rPingInt > 999 && pingInt < 999) {

                textName.setTextColor(Color.parseColor("#ffbf43"));

            }

        }

    }

    public class DetaViewHolder extends RecyclerView.ViewHolder {

        public DetaViewHolder(View itemView) {

            super(itemView);

        }

        public void showDetaData(DataSite data) {
        }
    }

    private void onClickButton(final ExpandableLayout expandableLayout) {
        expandableLayout.toggle();
    }

    public ObjectAnimator createRotateAnimator(final View target, final float from, final float to) {

        ObjectAnimator animator = ObjectAnimator.ofFloat(target, "rotation", from, to);
        animator.setDuration(300);
        animator.setInterpolator(Utils.createInterpolator(Utils.LINEAR_INTERPOLATOR));
        return animator;
    }
}
