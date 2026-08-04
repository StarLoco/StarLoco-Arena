/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.Stack;
import org.apache.log4j.Logger;

public class qr
extends on_1
implements cn_1 {
    private static Logger a = Logger.getLogger(qr.class);
    public static final String TAG = "Font";
    private af_1 adY = null;
    private static final acl_0 uG = new ym_0(new uj_2());
    public static final int adZ = "renderer".hashCode();

    public static qr checkOut() {
        qr qr2;
        try {
            qr2 = (qr)uG.adr();
            qr2.DG = uG;
        }
        catch (Exception exception) {
            a.error((Object)"Probl\u00e8me au borrowObject.");
            qr2 = new qr();
            qr2.b();
        }
        return qr2;
    }

    public String getTag() {
        return TAG;
    }

    public void setRenderer(af_1 af_12) {
        this.adY = af_12;
    }

    public af_1 getRenderer() {
        return this.adY;
    }

    public void setup(and_0 and_02) {
        if (and_02 instanceof wS) {
            ((wS)and_02).setFont(this.adY);
        }
    }

    public void j() {
        super.j();
        this.adY = null;
    }

    public void b() {
        super.b();
    }

    public void a(air_1 air_12) {
        qr qr2 = (qr)air_12;
        super.a((air_1)qr2);
        qr2.adY = this.adY;
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 != adZ) {
            return super.setXMLAttribute(n2, string, if_12);
        }
        this.setRenderer(if_12.eP(string));
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 != adZ) {
            return super.setPropertyAttribute(n2, object);
        }
        this.setRenderer((vg_2)object);
        return true;
    }

    public void a(k_0 k_02, na_1 na_12, Stack stack, afq_1 afq_12) {
        super.a(k_02, na_12, stack, afq_12);
        k_0 k_03 = k_02.f("ref");
        if (k_03 != null) {
            this.setRenderer(add_1.aOG().yh().dL(k_03.getStringValue()));
        }
        k_02.b(k_03);
    }
}

