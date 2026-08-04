/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/*
 * Renamed from Kh
 */
public final class kh_1
implements aim_2 {
    public String bnj;
    public azm_0 bnk = null;
    public final List bnl = new ArrayList();
    public final List bnm = new ArrayList();

    public kh_1(String string) {
        this.bnj = string;
    }

    public aim_2 Dw() {
        throw new aHY("A compilation unit has no enclosing scope");
    }

    public void a(azm_0 azm_02) {
        if (this.bnk != null) {
            throw new aHY("Re-setting package declaration");
        }
        this.bnk = azm_02;
    }

    public void a(DV dV) {
        this.bnl.add(dV);
    }

    public void b(pn_1 pn_12) {
        this.bnm.add(pn_12);
        pn_12.a(this);
    }

    public pn_1[] WA() {
        return this.bnm.toArray(new pn_1[this.bnm.size()]);
    }

    public pn_1 eX(String string) {
        Iterator iterator = this.bnm.iterator();
        while (iterator.hasNext()) {
            pn_1 pn_12 = (pn_1)iterator.next();
            if (!pn_12.getName().equals(string)) continue;
            return pn_12;
        }
        return null;
    }
}

