package telecoms.bccres.monitoring;

import android.animation.TimeInterpolator;

public class DataSite {


    public static final int TEXT = 0;
    public String name, modem, router, ping, powLev, ebNo, freq, bucCur, bucVolt, lnbCur, lnbVolt;
    public String slot, esNo, ber, rxFreq, demodcdd, circuitID, rPing, contact;
    public final TimeInterpolator interpolator;

    public DataSite(TimeInterpolator interpolator) {
        this.interpolator = interpolator;

    }

}
