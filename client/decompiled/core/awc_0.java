/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from awc
 */
public class awc_0
extends awh_0 {
    public static final String TAG = "margin";
    private static final Logger a = Logger.getLogger(awc_0.class);
    private static final acl_0 uG = new ym_0(new aoc_1());

    public String getTag() {
        return TAG;
    }

    public static awc_0 checkOut() {
        awc_0 awc_02;
        try {
            awc_02 = (awc_0)uG.adr();
            awc_02.DG = uG;
        }
        catch (Exception exception) {
            a.error((Object)"Probl\u00e8me au borrowObject.");
            awc_02 = new awc_0();
            awc_02.b();
        }
        return awc_02;
    }
}

