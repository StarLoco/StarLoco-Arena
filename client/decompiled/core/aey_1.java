/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aEY
 */
class aey_1
implements Wv,
Comparable {
    protected Wv acH;
    protected Wv acG;
    protected int djV = 0;
    protected long agL;
    protected long dEM;
    protected long dEN;
    protected int dEO;
    protected int dEP = 0;
    private boolean dEQ = false;
    protected alx_0 dER;

    public void mH(int n2) {
        this.djV = n2;
    }

    public int aKE() {
        return this.djV;
    }

    public Wv uw() {
        return this.acH;
    }

    public void a(Wv wv) {
        this.acH = wv;
    }

    public Wv ux() {
        return this.acG;
    }

    public void b(Wv wv) {
        this.acG = wv;
    }

    public long aKD() {
        return this.agL;
    }

    void ek(long l2) {
        this.agL = l2;
    }

    public int aRi() {
        return this.dEO;
    }

    public void nP(int n2) {
        this.dEO = n2;
    }

    public void es(long l2) {
        this.dEM = l2;
    }

    public long aRj() {
        return this.dEM;
    }

    public long aRk() {
        return this.dEN;
    }

    public boolean aRl() {
        return this.dEO == -1 || this.dEP <= this.dEO;
    }

    public alx_0 aRm() {
        return this.dER;
    }

    public void c(alx_0 alx_02) {
        this.dER = alx_02;
    }

    void et(long l2) {
        ++this.dEP;
        this.dEN = l2 + this.dEM;
    }

    public boolean aRn() {
        return this.dEQ;
    }

    public void aRo() {
        this.dEQ = true;
    }

    public int compareTo(Object object) {
        aey_1 aey_12 = (aey_1)object;
        if (aey_12 == null) {
            throw new UnsupportedOperationException("Comparaison d'un listener avec null.");
        }
        if (this.dEN < aey_12.dEN) {
            return -1;
        }
        if (this.dEN > aey_12.dEN) {
            return 1;
        }
        return 0;
    }

    public String toString() {
        return this.dER.getClass().getName() + ", nextTime : " + this.dEN;
    }
}

