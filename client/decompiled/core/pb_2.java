/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from pb
 */
public class pb_2
extends abd_1 {
    private static final Logger a = Logger.getLogger(pb_2.class);
    private static final acl_0 uG = new ym_0(new rq_0());
    private float abc;
    private float abd;
    private Object dE;

    public static pb_2 tT() {
        pb_2 pb_22;
        try {
            pb_22 = (pb_2)uG.adr();
            pb_22.DG = uG;
        }
        catch (Exception exception) {
            a.error((Object)"Probl\u00e8me au borrowObject.");
            pb_22 = new pb_2();
            pb_22.b();
        }
        return pb_22;
    }

    public static pb_2 a(abd_1 abd_12, float f, float f2, Object object) {
        pb_2 pb_22 = pb_2.tT();
        pb_22.ng(abd_12.bTl);
        pb_22.nh(abd_12.bTm);
        pb_22.setModifiers(abd_12.jH);
        pb_22.ai(abd_12.oI);
        pb_22.aj(abd_12.oJ);
        pb_22.e(abd_12.oF());
        pb_22.z(f);
        pb_22.A(f2);
        pb_22.setValue(object);
        return pb_22;
    }

    public float tU() {
        return this.abc;
    }

    public void z(float f) {
        this.abc = f;
    }

    public float tV() {
        return this.abd;
    }

    public void A(float f) {
        this.abd = f;
    }

    public Object getValue() {
        return this.dE;
    }

    public void setValue(Object object) {
        this.dE = object;
    }
}

