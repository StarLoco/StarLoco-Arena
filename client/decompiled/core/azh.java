/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;

public class azh {
    private static final Logger a = Logger.getLogger(azh.class);
    private static final adz_1 dno = new adz_1(0, 0);
    private List dnp;
    private static final azh dnq = new azh();

    private azh() {
        int n2 = 2;
        this.dnp = new ArrayList(2);
        for (int j = 0; j < 2; ++j) {
            this.dnp.add(new ahz_0(azh.mV(j), mx_0.Kz[j]));
        }
    }

    public static azh aLL() {
        return dnq;
    }

    public void mU(int n2) {
        Om om = aku_0.cv((short)n2);
        if (om == null) {
            a.error((Object)("pas de map de combat pour l'instance " + n2));
            return;
        }
        this.a(om, (byte)0);
        this.a(om, (byte)1);
    }

    private void a(Om om, byte by) {
        ahz_0 ahz_02 = (ahz_0)this.dnp.get(by);
        Iterator iterator = om.af(by);
        while (iterator.hasNext()) {
            ry ry2 = (ry)iterator.next();
            ahz_02.y(ry2.getX(), ry2.getY(), ry2.wk());
        }
    }

    public void aLM() {
        for (ahz_0 ahz_02 : this.dnp) {
            ahz_02.clear();
        }
    }

    public boolean a(byte by, ry ry2) {
        if (this.dnp.size() > by && by >= 0) {
            return ((ahz_0)this.dnp.get(by)).i(ry2);
        }
        a.trace((Object)("teamId invalid " + by));
        return false;
    }

    private static String mV(int n2) {
        return "startPoint" + n2;
    }
}

