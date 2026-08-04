/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

public class OE
extends yt_1 {
    private static Logger a = Logger.getLogger(OE.class);
    public static final String TAG = "Label";

    public String getTag() {
        return TAG;
    }

    public void b() {
        super.b();
        auL auL2 = auL.checkOut();
        auL2.setWidget(this);
        this.a(auL2);
        this.setTextBuilder(new ch_2(new ajx_1()));
        this.getTextBuilder().a(this);
        this.setMultiline(false);
    }
}

