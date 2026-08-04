/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

public class aGJ
extends abd_1 {
    private static Logger a = Logger.getLogger(aGJ.class);
    private Object dJq = null;
    private static final acl_0 uG = new ym_0(new aiV());
    private static int dqY = 0;
    private static int dqZ = 0;

    public static aGJ a(na_1 na_12, qe_1 qe_12, Object object) {
        aGJ aGJ2;
        ++dqY;
        try {
            aGJ2 = (aGJ)uG.adr();
            aGJ2.DG = uG;
        }
        catch (Exception exception) {
            a.error((Object)"Probl\u00e8me au borrowObject.");
            aGJ2 = new aGJ();
            aGJ2.b();
        }
        aGJ2.e(na_12);
        aGJ2.a(qe_12);
        aGJ2.setItemValue(object);
        return aGJ2;
    }

    public static aGJ a(abd_1 abd_12, na_1 na_12, qe_1 qe_12, Object object) {
        aGJ aGJ2;
        ++dqY;
        try {
            aGJ2 = (aGJ)uG.adr();
            aGJ2.DG = uG;
        }
        catch (Exception exception) {
            a.error((Object)"Probl\u00e8me au borrowObject.");
            aGJ2 = new aGJ();
            aGJ2.b();
        }
        aGJ2.ng(abd_12.bTl);
        aGJ2.nh(abd_12.bTm);
        aGJ2.setModifiers(abd_12.jH);
        aGJ2.ai(abd_12.oI);
        aGJ2.aj(abd_12.oJ);
        aGJ2.X(abd_12.oH());
        aGJ2.e(na_12);
        aGJ2.a(qe_12);
        aGJ2.setItemValue(object);
        return aGJ2;
    }

    public void release() {
        super.release();
        ++dqZ;
    }

    public Object getItemValue() {
        return this.dJq;
    }

    public void setItemValue(Object object) {
        this.dJq = object;
    }

    public void j() {
        super.j();
        this.dJq = null;
    }
}

