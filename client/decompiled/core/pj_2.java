/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from Pj
 */
public class pj_2
extends dz_2
implements cn_1 {
    private static Logger a = Logger.getLogger(pj_2.class);
    public static final String TAG = "borderLayoutData";
    public static final String aTJ = "bld";
    private ahq_1 bDn = null;
    private static final acl_0 uG = new ym_0(new PJ());
    public static final int bDo = "data".hashCode();

    public static pj_2 checkOut() {
        pj_2 pj_22;
        try {
            pj_22 = (pj_2)uG.adr();
            pj_22.DG = uG;
        }
        catch (Exception exception) {
            a.error((Object)"Probl\u00e8me au borrowObject.");
            pj_22 = new pj_2();
            pj_22.b();
        }
        return pj_22;
    }

    public String getTag() {
        return TAG;
    }

    public ahq_1 getData() {
        return this.bDn;
    }

    public void setData(ahq_1 ahq_12) {
        this.bDn = ahq_12;
    }

    public void a(air_1 air_12) {
        pj_2 pj_22 = (pj_2)air_12;
        super.a((air_1)pj_22);
        pj_22.bDn = this.bDn;
    }

    public void j() {
        super.j();
        this.bDn = null;
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 != bDo) {
            return super.setXMLAttribute(n2, string, if_12);
        }
        this.setData(ahq_1.lu(string));
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        return super.setPropertyAttribute(n2, object);
    }
}

