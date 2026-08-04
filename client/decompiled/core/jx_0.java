/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Jx
 */
public class jx_0 {
    public static final jx_0 blQ = new jx_0(0, 0, 0, 0, 0, 0);
    public static final jx_0 blR = new jx_0(0, 0, 0, 1, 0, 0);
    public static final jx_0 blS = new jx_0(0, 0, 1, 0, 0, 0);
    public static final jx_0 blT = new jx_0(0, 0, 7, 0, 0, 0);
    public static final jx_0 blU = new jx_0(0, 1, 0, 0, 0, 0);
    public static final jx_0 blV = new jx_0(1, 0, 0, 0, 0, 0);
    private int blW;
    private int blX;
    private int acP;
    private int acO;
    private int acN;
    private int acM;
    private long ahF;

    public jx_0(jx_0 jx_02) {
        this.blW = jx_02.blW;
        this.blX = jx_02.blX;
        this.acP = jx_02.acP;
        this.acO = jx_02.acO;
        this.acN = jx_02.acN;
        this.acM = jx_02.acM;
        this.VZ();
    }

    public jx_0(sl_0 sl_02) {
        this.blW = 0;
        this.blX = 0;
        this.acP = sl_02.getDays();
        this.acO = sl_02.getHours();
        this.acN = sl_02.getMinutes();
        this.acM = sl_02.getSeconds();
        this.VZ();
    }

    public jx_0(int n2, int n3, int n4, int n5, int n6, int n7) {
        this.blW = n2;
        this.blX = n3;
        this.acP = n4;
        this.acO = n5;
        this.acN = n6;
        this.acM = n7;
        this.VZ();
    }

    private void VZ() {
        this.ahF = (long)(this.acM | this.acN << 8 | this.acO << 16 | this.acP << 24) | (long)this.blX << 32 | (long)this.blW << 40;
    }

    public int getYears() {
        return this.blW;
    }

    public int getMonths() {
        return this.blX;
    }

    public int getDays() {
        return this.acP;
    }

    public int getHours() {
        return this.acO;
    }

    public int getMinutes() {
        return this.acN;
    }

    public int getSeconds() {
        return this.acM;
    }

    public long uJ() {
        return this.ahF;
    }

    public static jx_0 bT(long l2) {
        long l3 = l2;
        int n2 = (int)(l3 & 0xFFL);
        int n3 = (int)((l3 >>= 8) & 0xFFL);
        int n4 = (int)((l3 >>= 8) & 0xFFL);
        int n5 = (int)((l3 >>= 8) & 0xFFL);
        int n6 = (int)((l3 >>= 8) & 0xFFL);
        int n7 = (int)((l3 >>= 8) & 0xFFFFL);
        return new jx_0(n7, n6, n5, n4, n3, n2);
    }

    public String toString() {
        return (this.blW != 0 ? this.blW + " ans " : " ") + (this.blX != 0 ? this.blX + " mois " : "") + (this.acP != 0 ? this.acP + " jours " : "") + (this.acO != 0 ? this.acO + " heures " : "") + (this.acN != 0 ? this.acN + " minutes " : "") + (this.acM != 0 ? this.acM + " secondes " : "");
    }
}

