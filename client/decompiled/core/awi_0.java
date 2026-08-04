/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from awI
 */
public class awi_0 {
    public int m_size;
    public int cWA;
    public int fc;
    public int fb;
    public int dij;
    public int bmF;
    public int dik;
    public int[] dil = new int[11];
    public auI dim = new auI(this);
    public aeU din = new aeU(this);
    public int cpY;

    public void p(acf acf2) {
        this.m_size = acf2.readInt();
        this.cWA = acf2.readInt();
        this.fc = acf2.readInt();
        this.fb = acf2.readInt();
        this.dij = acf2.readInt();
        this.bmF = acf2.readInt();
        this.dik = acf2.readInt();
        if (this.dik == 0) {
            this.dik = 1;
        }
        acf2.setOffset(acf2.getOffset() + 44);
        this.dim.p(acf2);
        this.din.p(acf2);
        this.cpY = acf2.readInt();
    }
}

