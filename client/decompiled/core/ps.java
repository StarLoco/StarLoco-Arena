/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

public class ps
extends YN {
    private static Logger a = Logger.getLogger(ps.class);
    public static final String TAG = "TextView";
    protected String abY = null;

    public void appendText(String string) {
        if (string == null) {
            string = "";
        }
        if (this.caC != null) {
            this.caC = this.caC + string;
        } else {
            if (this.abY == null) {
                this.abY = "";
            }
            this.abY = this.abY + string;
        }
        this.setNeedsToPreProcess();
    }

    public String getTag() {
        return TAG;
    }

    public void setText(String string) {
        super.setText(string);
        this.abY = null;
    }

    protected void ug() {
        if (this.abY != null) {
            this.getTextBuilder().aE(this.abY);
            this.amz();
            this.abY = null;
        }
    }

    public boolean cc(int n2) {
        this.ug();
        return super.cc(n2);
    }

    public void a(air_1 air_12) {
        ps ps2 = (ps)air_12;
        if (this.abY != null) {
            ps2.abY = this.abY;
        }
        super.a(air_12);
    }

    public void b() {
        super.b();
        auL auL2 = auL.checkOut();
        auL2.setWidget(this);
        this.a(auL2);
        this.setTextBuilder(new ch_2(new abn_1()));
        this.getTextBuilder().a(this);
        this.setMultiline(true);
    }

    public boolean a(int n2, String string, if_1 if_12) {
        if (n2 != caS) {
            return super.a(n2, string, if_12);
        }
        this.appendText(if_12.eM(string));
        return true;
    }

    public boolean d(int n2, Object object) {
        if (n2 != caS) {
            return super.d(n2, object);
        }
        this.appendText(String.valueOf(object));
        return true;
    }
}

