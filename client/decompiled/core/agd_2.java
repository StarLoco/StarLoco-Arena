/*
 * Decompiled with CFR 0.152.
 */
import java.util.Enumeration;
import java.util.Vector;

/*
 * Renamed from aGD
 */
public class agd_2 {
    private Vector dJl = new Vector();

    public agd_2() {
    }

    public agd_2(Zq zq) {
        this.c(zq);
    }

    public void c(Zq zq) {
        this.dJl.addElement(zq);
    }

    public String gT(String string) {
        String string2 = string;
        Enumeration enumeration = this.dJl.elements();
        while (enumeration.hasMoreElements()) {
            Zq zq = (Zq)enumeration.nextElement();
            string2 = zq.gT(string2);
        }
        return string2;
    }

    public boolean anv() {
        Enumeration enumeration = this.dJl.elements();
        while (enumeration.hasMoreElements()) {
            Zq zq = (Zq)enumeration.nextElement();
            if (!zq.anv()) continue;
            return true;
        }
        return false;
    }
}

