/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public abstract class DM
implements el_1 {
    private final lc_0 bX;
    private final short HC;
    private final List aOj = new ArrayList();
    private final List aOk = new ArrayList();
    private aim_2 avV = null;
    asn aOl = null;
    public int aOm = 0;
    public int aOn = 0;

    public DM(lc_0 lc_02, short s) {
        this.bX = lc_02;
        this.HC = s;
    }

    public short hQ() {
        return this.HC;
    }

    public void a(aim_2 aim_22) {
        if (this.avV != null && aim_22 != this.avV) {
            throw new aHY("Enclosing scope is already set for type declaration \"" + this.toString() + "\" at " + this.aP());
        }
        this.avV = aim_22;
    }

    public aim_2 Dw() {
        return this.avV;
    }

    public void Mk() {
        if (this.aOl != null) {
            this.aOl.cRG = null;
            this.aOl.cRH = null;
        }
    }

    public void a(rp_1 rp_12) {
        this.aOk.add(rp_12);
        rp_12.a(this);
    }

    public Collection hR() {
        return this.aOk;
    }

    public rp_1 ab(String string) {
        Iterator iterator = this.aOk.iterator();
        while (iterator.hasNext()) {
            rp_1 rp_12 = (rp_1)iterator.next();
            if (!rp_12.getName().equals(string)) continue;
            return rp_12;
        }
        return null;
    }

    public void c(kc_0 kc_02) {
        this.aOj.add(kc_02);
        kc_02.a(this);
    }

    public kc_0 ac(String string) {
        Iterator iterator = this.aOj.iterator();
        while (iterator.hasNext()) {
            kc_0 kc_02 = (kc_0)iterator.next();
            if (!kc_02.name.equals(string)) continue;
            return kc_02;
        }
        return null;
    }

    public List hS() {
        return this.aOj;
    }

    public String ad(String string) {
        return this.getClassName() + '$' + ++this.aOn + '$' + string;
    }

    public String hT() {
        return this.getClassName() + '$' + ++this.aOm;
    }

    public lc_0 aP() {
        return this.bX;
    }

    public void j(String string) {
        throw new ajy_2(string, this.bX);
    }

    public abstract String toString();

    public abstract void a(qo_1 var1);

    public abstract String getClassName();
}

