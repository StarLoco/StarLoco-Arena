/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from Xs
 */
public class xs_1
extends aNZ {
    public static final String TAG = "Spring";
    private static Logger a = Logger.getLogger(xs_1.class);
    private int r = 0;
    private String bXP;
    private String aGL;
    private String bXQ;
    public static final int bXR = "edge".hashCode();
    public static final int bXS = "referentEdge".hashCode();
    public static final int bXT = "referentId".hashCode();
    public static final int dL = "value".hashCode();

    public String getEdge() {
        return this.aGL;
    }

    public void setEdge(String string) {
        this.aGL = string;
    }

    public String getReferentEdge() {
        return this.bXQ;
    }

    public void setReferentEdge(String string) {
        this.bXQ = string;
    }

    public String getTag() {
        return TAG;
    }

    public int getValue() {
        return this.r;
    }

    public void setValue(int n2) {
        this.r = n2;
    }

    public String toString() {
        return Integer.toString(this.getValue());
    }

    public static xs_1 a(xs_1 xs_12, xs_1 xs_13) {
        return new abi_2(xs_12, xs_13);
    }

    public static xs_1 b(xs_1 xs_12, xs_1 xs_13) {
        return xs_1.a(xs_12, new abf_2(xs_13));
    }

    public static xs_1 iU(int n2) {
        return new k(n2);
    }

    public static xs_1 i(adg_2 adg_22) {
        return new an_1(adg_22);
    }

    public static xs_1 j(adg_2 adg_22) {
        return new adk_2(adg_22);
    }

    public static xs_1 k(adg_2 adg_22) {
        return new acs_2(adg_22);
    }

    public static xs_1 l(adg_2 adg_22) {
        return new zn_0(adg_22);
    }

    public String getReferentId() {
        return this.bXP;
    }

    public void setReferentId(String string) {
        this.bXP = string;
    }

    public void j() {
        super.j();
        this.aGL = null;
        this.bXQ = null;
        this.bXP = null;
        this.r = 0;
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 == bXR) {
            this.setEdge(if_12.eM(string));
        } else if (n2 == bXS) {
            this.setReferentEdge(if_12.eM(string));
        } else if (n2 == bXT) {
            this.setReferentId(if_12.eM(string));
        } else if (n2 == dL) {
            this.setValue(Gr.R(string));
        } else {
            return super.setXMLAttribute(n2, string, if_12);
        }
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        return super.setPropertyAttribute(n2, object);
    }

    static /* synthetic */ Logger Dm() {
        return a;
    }
}

