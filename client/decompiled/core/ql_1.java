/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.net.URL;
import java.util.LinkedList;
import org.apache.log4j.Logger;

/*
 * Renamed from Ql
 */
public class ql_1 {
    private static Logger a = Logger.getLogger(ql_1.class);
    private static final ql_1 bFV = new ql_1();
    private apk_0 bFW;
    private nu_0 bFX;
    private lb_0 bFY;
    private boolean bFZ = true;
    private boolean bGa = false;
    private LinkedList bGb = new LinkedList();
    private LinkedList bGc = new LinkedList();

    public static ql_1 acX() {
        return bFV;
    }

    public void a(MC mC, URL uRL, nu_0 nu_02) {
        this.bFW = new apk_0();
        this.bFW.dP(false);
        this.bFW.dO(false);
        this.bFW.a(mC);
        if (uRL != null) {
            this.bFW.f(uRL);
        } else {
            a.error((Object)"Impossible de charger les commandes de chat !");
        }
        this.bFX = nu_02;
    }

    public apk_0 acY() {
        return this.bFW;
    }

    public void a(int n2, ua ua2) {
        if (this.bFY == null) {
            this.bFY = new lb_0();
        }
        this.bFY.c(n2, ua2);
    }

    public void hj(int n2) {
        if (this.bFY != null) {
            this.bFY.remove(n2);
        }
    }

    public ua hk(int n2) {
        return (ua)this.bFY.get(n2);
    }

    public int fH(String string) {
        ll_0 ll_02 = this.bFY.pK();
        while (ll_02.hasNext()) {
            ll_02.fK();
            if (!((ua)ll_02.value()).zV().equals(string)) continue;
            return ll_02.kR();
        }
        return -1;
    }

    public ua fI(String string) {
        ll_0 ll_02 = this.bFY.pK();
        while (ll_02.hasNext()) {
            ll_02.fK();
            if (!((ua)ll_02.value()).getName().equals(string)) continue;
            return (ua)ll_02.value();
        }
        return null;
    }

    public void p(String string, int n2) {
        zc_0 zc_02 = new zc_0(string);
        zc_02.eD(n2);
        ua ua2 = (ua)this.bFY.get(zc_02.GH());
        ua2.a(zc_02);
    }

    public void a(zc_0 zc_02, String string) {
        ua ua2 = (ua)this.bFY.get(zc_02.GH());
        ua2.a(zc_02, string);
    }

    public void a(zc_0 zc_02) {
        ua ua2 = (ua)this.bFY.get(zc_02.GH());
        ua2.a(zc_02);
    }

    public String cf(boolean bl2) {
        String string;
        if (this.bGb.size() == 0) {
            return null;
        }
        if (bl2) {
            string = (String)this.bGb.removeFirst();
            this.bGb.addLast(string);
        } else {
            string = (String)this.bGb.removeLast();
            this.bGb.addFirst(string);
        }
        return string;
    }

    public void fJ(String string) {
        if (!this.bGb.contains(string)) {
            this.bGb.add(string);
        } else {
            this.bGb.remove(string);
            this.bGb.addLast(string);
        }
        if (!this.bGc.contains(string)) {
            this.bGc.add(string);
        }
        if (this.bGc.size() > 10 || this.bGb.size() > 10) {
            String string2 = (String)this.bGc.removeFirst();
            this.bGb.remove(string2);
        }
    }

    public void clean() {
        this.bGb.clear();
        this.bGc.clear();
        ll_0 ll_02 = this.bFY.pK();
        while (ll_02.hasNext()) {
            ll_02.fK();
            ((ua)ll_02.value()).clean();
        }
    }

    public ll_0 acZ() {
        return this.bFY.pK();
    }

    public nu_0 ada() {
        return this.bFX;
    }
}

