/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from aLy
 */
public class aly_2
extends abd_1 {
    private static Logger a = Logger.getLogger(aly_2.class);
    private kn_1 Is = null;
    private Object It = null;
    private Object dE = null;
    private static final acl_0 uG = new ym_0(new agc_2());

    public aly_2() {
    }

    public aly_2(kn_1 kn_12, Object object) {
        this.setDragNDropable(kn_12);
        this.DK = kn_12;
        this.dE = object;
    }

    public static aly_2 a(abd_1 abd_12, na_1 na_12, Object object) {
        aly_2 aly_22;
        try {
            aly_22 = (aly_2)uG.adr();
            aly_22.DG = uG;
        }
        catch (Exception exception) {
            a.error((Object)"Probl\u00e8me au borrowObject.");
            aly_22 = new aly_2();
            aly_22.b();
        }
        aly_22.ng(abd_12.bTl);
        aly_22.nh(abd_12.bTm);
        aly_22.setModifiers(abd_12.jH);
        aly_22.ai(abd_12.oI);
        aly_22.aj(abd_12.oJ);
        aly_22.e(na_12);
        aly_22.a(qe_1.bFc);
        aly_22.setDragNDropable((kn_1)na_12);
        aly_22.dE = object;
        return aly_22;
    }

    public kn_1 getDragNDropable() {
        return this.Is;
    }

    public void setDragNDropable(kn_1 kn_12) {
        qa_1 qa_12;
        if (kn_12 instanceof na_1) {
            this.Is = kn_12;
        }
        if (kn_12 != null && (qa_12 = kn_12.getRenderableParent()) != null) {
            this.It = qa_12.getItemValue();
        }
    }

    public Object qJ() {
        return this.It;
    }

    public void x(Object object) {
        this.It = object;
    }

    public Object getValue() {
        return this.dE;
    }

    public void setValue(Object object) {
        this.dE = object;
    }

    public qe_1 aV() {
        return qe_1.bFc;
    }
}

