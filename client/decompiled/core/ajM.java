/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.Iterator;

public abstract class ajM
implements Pi {
    protected int aW;
    protected ks_2 cAS;
    protected int cpn;
    protected short cpo;
    protected short cpp;
    protected int ir;
    protected final ArrayList iM;
    protected final jg_0 cpq;
    protected final int cAT;
    protected final boolean cpr;
    protected final short cps;
    protected final short cpt;
    protected ajM cAU;
    protected ajM cAV;
    protected ajM cAW;
    protected ajM cAX;
    protected boolean cAY = false;

    public ajM(int n2, int n3, short s, short s2, int n4, ArrayList arrayList, jg_0 jg_02, int n5, boolean bl2, short s3, short s4) {
        this.aW = n2;
        this.cpn = n3;
        this.cpo = s;
        this.cpp = s2;
        this.ir = n4;
        this.iM = arrayList;
        this.cpq = jg_02;
        this.cAT = n5;
        this.cpr = bl2;
        this.cps = s3;
        this.cpt = s4;
    }

    public ArrayList a(ajM ajM2, boolean bl2) {
        ArrayList arrayList;
        this.cAY = true;
        if (this.cpr) {
            return null;
        }
        if (ajM2.getId() == this.aW || ajM2.aux() == this.cpo && ajM2.auy() == this.cpp) {
            ArrayList<ajM> arrayList2 = new ArrayList<ajM>();
            arrayList2.add(ajM2);
            return arrayList2;
        }
        if (!bl2 && this.azm()) {
            return null;
        }
        if (this.cAU != null && !this.cAU.azn() && (arrayList = this.cAU.a(ajM2, false)) != null) {
            arrayList.add(this);
            return arrayList;
        }
        if (this.cAX != null && !this.cAX.azn() && (arrayList = this.cAX.a(ajM2, false)) != null) {
            arrayList.add(this);
            return arrayList;
        }
        if (this.cAW != null && !this.cAW.azn() && (arrayList = this.cAW.a(ajM2, false)) != null) {
            arrayList.add(this);
            return arrayList;
        }
        if (this.cAV != null && !this.cAV.azn() && (arrayList = this.cAV.a(ajM2, false)) != null) {
            arrayList.add(this);
            return arrayList;
        }
        return null;
    }

    public boolean azm() {
        return this.ir != 0 || !this.iM.isEmpty() || this.cpq.size() > 0 || this.cAT != 0 || this.cps != 0 || this.auw();
    }

    public boolean azn() {
        return this.cAY;
    }

    public void dD(boolean bl2) {
        this.cAY = bl2;
    }

    public void b(ks_2 ks_22) {
        this.cAS = ks_22;
    }

    public int getId() {
        return this.aW;
    }

    public ks_2 azo() {
        return this.cAS;
    }

    public int aus() {
        return this.cpn;
    }

    public short aut() {
        return this.cpo;
    }

    public short auu() {
        return this.cpp;
    }

    public int el() {
        return this.ir;
    }

    public ArrayList eC() {
        return this.iM;
    }

    public ajM azp() {
        return this.cAU;
    }

    public void b(ajM ajM2) {
        this.cAU = ajM2;
        ajM2.cAX = this;
    }

    public ajM azq() {
        return this.cAV;
    }

    public void c(ajM ajM2) {
        this.cAV = ajM2;
        ajM2.cAW = this;
    }

    public ajM azr() {
        return this.cAW;
    }

    public void d(ajM ajM2) {
        this.cAW = ajM2;
        ajM2.cAV = this;
    }

    public ajM azs() {
        return this.cAX;
    }

    public void e(ajM ajM2) {
        this.cAX = ajM2;
        ajM2.cAU = this;
    }

    public int iP() {
        return 15;
    }

    public long iO() {
        return this.aW;
    }

    public Iterator iterator() {
        return this.iM.iterator();
    }

    public jg_0 auv() {
        return this.cpq;
    }

    public int azt() {
        return this.cAT;
    }

    public boolean auw() {
        return this.cpr;
    }

    public short aux() {
        return this.cps;
    }

    public short auy() {
        return this.cpt;
    }
}

