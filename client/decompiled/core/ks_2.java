/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import org.apache.log4j.Logger;

/*
 * Renamed from KS
 */
public abstract class ks_2
implements ul_0 {
    protected static final Logger a = Logger.getLogger(ks_2.class);
    public static final ks_2 bpG = null;
    protected int aW;
    protected byte fp;
    protected int fo;
    protected final ArrayList bpH = new ArrayList();
    private jg_0 bpI = new jg_0();
    private int bpJ;
    private short fs;
    private short ft;

    public ks_2(int n2, byte by, int n3, jg_0 jg_02, int n4, short s, short s2) {
        this.aW = n2;
        this.fp = by;
        this.fo = n3;
        this.bpI = jg_02;
        this.bpJ = n4;
        this.fs = s;
        this.ft = s2;
    }

    public boolean a(ajM ajM2) {
        ajM2.b(this);
        int n2 = this.bpH.size();
        for (int j = 0; j < n2; ++j) {
            ajM ajM3 = (ajM)this.bpH.get(j);
            if (ajM3.aut() == ajM2.aut()) {
                if (ajM3.auu() == ajM2.auu()) {
                    a.error((Object)("Tentative d'ajouter une sph\u00e8re \u00e0 la m\u00eame position qu'une autre : " + this.getId() + " x : " + ajM3.aut() + " y : " + ajM3.auu()));
                    return false;
                }
                if (ajM3.auu() == ajM2.auu() + 1) {
                    ajM3.e(ajM2);
                    continue;
                }
                if (ajM3.auu() != ajM2.auu() - 1) continue;
                ajM3.b(ajM2);
                continue;
            }
            if (ajM3.auu() != ajM2.auu()) continue;
            if (ajM3.aut() == ajM2.aut() + 1) {
                ajM3.c(ajM2);
                continue;
            }
            if (ajM3.aut() != ajM2.aut() - 1) continue;
            ajM3.d(ajM2);
        }
        this.bpH.add(ajM2);
        return true;
    }

    public ajM X(int n2, int n3) {
        for (int j = 0; j < this.bpH.size(); ++j) {
            if (((ajM)this.bpH.get(j)).aut() != n2 || ((ajM)this.bpH.get(j)).auu() != n3) continue;
            return (ajM)this.bpH.get(j);
        }
        return null;
    }

    public ArrayList a(ajM ajM2, ajM ajM3) {
        ArrayList arrayList = ajM3.a(ajM2, true);
        for (int j = 0; j < this.bpH.size(); ++j) {
            ((ajM)this.bpH.get(j)).dD(false);
        }
        return arrayList;
    }

    public ajM Xm() {
        return this.X(this.fs, this.ft);
    }

    public int getId() {
        return this.aW;
    }

    public byte cu() {
        return this.fp;
    }

    public ArrayList Xn() {
        return this.bpH;
    }

    protected int ct() {
        return this.fo;
    }

    public jg_0 Xo() {
        return this.bpI;
    }

    public int Xp() {
        return this.bpJ;
    }

    public short cx() {
        return this.fs;
    }

    public short cy() {
        return this.ft;
    }
}

