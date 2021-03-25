package telecoms.bccres.monitoring;


import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class AdapterGl extends RecyclerView.Adapter {
    List<DataSite> datasite;

    public AdapterGl(List<DataSite> datasite) {
        this.datasite = datasite;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.glview_cont, parent, false);

        return new RowViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RowViewHolder rowViewHolder = (RowViewHolder) holder;

        int rowPos = rowViewHolder.getAdapterPosition();

        if (rowPos == 0) {
            // Header Cells. Main Headings appear here
            rowViewHolder.glsite.setBackgroundResource(R.drawable.table_header_cell_bg);
            rowViewHolder.glstatus.setBackgroundResource(R.drawable.table_header_cell_bg);
            rowViewHolder.glpowLev.setBackgroundResource(R.drawable.table_header_cell_bg);
            rowViewHolder.glebNo.setBackgroundResource(R.drawable.table_header_cell_bg);
            rowViewHolder.glesNo.setBackgroundResource(R.drawable.table_header_cell_bg);
            rowViewHolder.glber.setBackgroundResource(R.drawable.table_header_cell_bg);
            rowViewHolder.glping.setBackgroundResource(R.drawable.table_header_cell_bg);

            rowViewHolder.glFreq.setBackgroundResource(R.drawable.table_header_cell_bg);
            rowViewHolder.gldatar.setBackgroundResource(R.drawable.table_header_cell_bg);
            rowViewHolder.glModCod.setBackgroundResource(R.drawable.table_header_cell_bg);
            rowViewHolder.glCdd.setBackgroundResource(R.drawable.table_header_cell_bg);
            rowViewHolder.glBucV.setBackgroundResource(R.drawable.table_header_cell_bg);
            rowViewHolder.glBucC.setBackgroundResource(R.drawable.table_header_cell_bg);
            rowViewHolder.glLnbV.setBackgroundResource(R.drawable.table_header_cell_bg);
            rowViewHolder.glLnbC.setBackgroundResource(R.drawable.table_header_cell_bg);


            rowViewHolder.glsite.setText("Site");
            rowViewHolder.glpowLev.setText("PowLev");
            rowViewHolder.glebNo.setText("Eb/No");
            rowViewHolder.glesNo.setText("Es/No");
            rowViewHolder.glber.setText("BER");
            rowViewHolder.glstatus.setText("Status");
            rowViewHolder.glping.setText("Ping (mA)");

            rowViewHolder.glFreq.setText("Freq (MHz)");
            rowViewHolder.gldatar.setText("Data (kbps)");
            rowViewHolder.glModCod.setText("ModCode");
            rowViewHolder.glCdd.setText("CDD IP Add");
            rowViewHolder.glBucV.setText("Buc (V)");
            rowViewHolder.glBucC.setText("Buc (mA)");
            rowViewHolder.glLnbV.setText("Lnb (V)");
            rowViewHolder.glLnbC.setText("Lnb (mA)");


        } else {
            DataSite data = datasite.get(rowPos - 1);

            // Content Cells. Content appear here
            rowViewHolder.glsite.setBackgroundResource(R.drawable.table_content_cell_bg);
            rowViewHolder.glstatus.setBackgroundResource(R.drawable.table_content_cell_bg);
            rowViewHolder.glpowLev.setBackgroundResource(R.drawable.table_content_cell_bg);
            rowViewHolder.glebNo.setBackgroundResource(R.drawable.table_content_cell_bg);
            rowViewHolder.glesNo.setBackgroundResource(R.drawable.table_content_cell_bg);
            rowViewHolder.glber.setBackgroundResource(R.drawable.table_content_cell_bg);
            rowViewHolder.glping.setBackgroundResource(R.drawable.table_content_cell_bg);


            rowViewHolder.glFreq.setBackgroundResource(R.drawable.table_content_cell_bg);
            rowViewHolder.gldatar.setBackgroundResource(R.drawable.table_content_cell_bg);
            rowViewHolder.glModCod.setBackgroundResource(R.drawable.table_content_cell_bg);
            rowViewHolder.glCdd.setBackgroundResource(R.drawable.table_content_cell_bg);
            rowViewHolder.glBucV.setBackgroundResource(R.drawable.table_content_cell_bg);
            rowViewHolder.glBucC.setBackgroundResource(R.drawable.table_content_cell_bg);
            rowViewHolder.glLnbV.setBackgroundResource(R.drawable.table_content_cell_bg);
            rowViewHolder.glLnbC.setBackgroundResource(R.drawable.table_content_cell_bg);

            rowViewHolder.glsite.setText(data.name);
            rowViewHolder.glpowLev.setText(data.powLev);
            rowViewHolder.glebNo.setText(data.ebNo);
            rowViewHolder.glesNo.setText(data.esNo);
            rowViewHolder.glber.setText(data.ber);

            rowViewHolder.glFreq.setText(data.freq);
            rowViewHolder.glCdd.setText(data.demodcdd);
            rowViewHolder.glBucV.setText(data.bucVolt);
            rowViewHolder.glBucC.setText(data.bucCur);
            rowViewHolder.glLnbV.setText(data.lnbVolt);
            rowViewHolder.glLnbC.setText(data.lnbCur);


            int pingInt = Integer.parseInt(data.ping);
            rowViewHolder.glping.setText(data.ping);

            if (pingInt == -1) {
                rowViewHolder.glping.setTextColor(Color.RED);

            } else if (pingInt < 750) {
                rowViewHolder.glping.setTextColor(Color.parseColor("#175732"));

            } else if (pingInt > 750 && pingInt < 999) {
                rowViewHolder.glping.setTextColor(Color.parseColor("#ffbf43"));
            } else if (pingInt > 999) {
                rowViewHolder.glping.setTextColor(Color.RED);
            }


            if (pingInt == -1) {
                rowViewHolder.glstatus.setText("DOWN");
                rowViewHolder.glstatus.setBackgroundColor(Color.RED);

            } else if (pingInt != -1) {
                rowViewHolder.glstatus.setText("UP");
                rowViewHolder.glstatus.setBackgroundColor(Color.GREEN);

            }

        }
    }

    @Override
    public int getItemCount() {
        return datasite.size() + 1; // one more to add header row
    }

    public class RowViewHolder extends RecyclerView.ViewHolder {
        protected TextView glsite, glstatus, glpowLev, glesNo, glber, glping, glebNo, glFreq;
        protected TextView gldatar, glModCod, glCdd, glBucV, glBucC, glLnbV, glLnbC;

        public RowViewHolder(View itemView) {
            super(itemView);

            glsite = itemView.findViewById(R.id.glsite);
            glstatus = itemView.findViewById(R.id.glstatus);
            glping = itemView.findViewById(R.id.glPing);
            glpowLev = itemView.findViewById(R.id.glPowLev);
            glebNo = itemView.findViewById(R.id.glEbNo);
            glber = itemView.findViewById(R.id.glBER);
            glesNo = itemView.findViewById(R.id.glEsNo);

            glFreq = itemView.findViewById(R.id.glFreq);
            gldatar = itemView.findViewById(R.id.gldatar);
            glModCod = itemView.findViewById(R.id.glModCod);
            glCdd = itemView.findViewById(R.id.glCdd);
            glBucV = itemView.findViewById(R.id.glBucV);
            glBucC = itemView.findViewById(R.id.glBucC);
            glLnbV = itemView.findViewById(R.id.glLnbV);
            glLnbC = itemView.findViewById(R.id.glLnbC);


        }
    }
}
