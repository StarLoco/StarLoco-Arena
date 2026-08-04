/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from iB
 */
public abstract class ib_2 {
    protected static final Logger a = Logger.getLogger(ib_2.class);
    protected final lb_0 yw = new lb_0();
    protected final lb_0 yx = new lb_0();
    protected final afj_0 yy = new afj_0();

    public void a(ks_2 ks_22) {
        this.yx.c(ks_22.getId(), ks_22);
        if (ks_22.ct() == axi.ct()) {
            this.yy.b(ks_22.cu(), ks_22);
        }
    }

    public void aU(int n2) {
        this.yy.clear();
        Object[] objectArray = this.yx.getValues();
        for (int j = 0; j < objectArray.length; ++j) {
            ks_2 ks_22 = (ks_2)objectArray[j];
            if (ks_22.ct() != n2) continue;
            this.yy.b(ks_22.cu(), ks_22);
        }
    }

    public void a(int n2, ajM ajM2) {
        ((ks_2)this.yx.get(n2)).a(ajM2);
        this.yw.c(ajM2.getId(), ajM2);
    }

    public ajM aV(int n2) {
        return (ajM)this.yw.get(n2);
    }

    public ks_2 aW(int n2) {
        ks_2 ks_22 = (ks_2)this.yx.get(n2);
        if (ks_22 != null && ks_22.Xn().isEmpty()) {
            return this.a(ks_22.ct() - 1, ks_22.cu());
        }
        return ks_22;
    }

    public ks_2 a(int n2, byte by) {
        ks_2 ks_22 = this.b(n2, by);
        if (ks_22 == null || n2 < 0) {
            return null;
        }
        if (ks_22.Xn().isEmpty()) {
            return this.a(n2 - 1, by);
        }
        return ks_22;
    }

    public ks_2 b(int n2, byte by) {
        Object[] objectArray = this.yx.getValues();
        for (int j = 0; j < objectArray.length; ++j) {
            ks_2 ks_22 = (ks_2)objectArray[j];
            if (n2 <= 0 || ks_22.cu() != by || ks_22.ct() != n2) continue;
            return ks_22;
        }
        return null;
    }

    public int aX(int n2) {
        int n3 = -1;
        ks_2 ks_22 = (ks_2)this.yx.get(n2);
        if (ks_22 == ks_2.bpG) {
            a.error((Object)("Obtention du sphereboard d'id " + n2 + " imposssible : SphereBoard \u00e9gal \u00e0 " + ks_2.bpG + "."));
        } else {
            n3 = ks_22.ct();
        }
        return n3;
    }

    public lb_0 lB() {
        return this.yx;
    }
}

