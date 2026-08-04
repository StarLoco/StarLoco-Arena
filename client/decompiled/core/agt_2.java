/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from agt
 */
public final class agt_2 {
    public static final int cul = 1;
    public static final int NORMAL = 2;
    public static final int cum = 4;
    public static final int cun = 8;
    int NB;
    private float[] aaB = null;
    private int cuo;

    public agt_2(agt_2 agt_22) {
        this.cuo = agt_22.cuo;
        this.aaB = new float[agt_22.aaB.length];
        System.arraycopy(agt_22.aaB, 0, this.aaB, 0, this.aaB.length);
        this.NB = agt_22.NB;
    }

    public agt_2(int n2, int n3) {
        this.cuo = n2;
        this.aaB = new float[n3];
    }

    public agt_2(int n2, float[] fArray) {
        this.cuo = n2;
        this.aaB = new float[fArray.length];
        System.arraycopy(fArray, 0, this.aaB, 0, fArray.length);
    }

    public agt_2(int n2, float[] fArray, int n3, int n4) {
        this.cuo = n2;
        this.aaB = new float[n4];
        System.arraycopy(fArray, n3, this.aaB, 0, n4);
    }

    public agt_2(acf acf2) {
        this.b(acf2);
    }

    public final void a(aij_1 aij_12) {
        aij_12.writeInt(this.cuo);
        aij_12.writeInt(this.NB);
        aij_12.writeInt(this.aaB.length);
        for (float f : this.aaB) {
            aij_12.writeFloat(f);
        }
    }

    public final void b(acf acf2) {
        this.cuo = acf2.readInt();
        this.NB = acf2.readInt();
        int n2 = acf2.readInt();
        this.aaB = new float[n2];
        for (int j = 0; j < n2; ++j) {
            this.aaB[j] = acf2.readFloat();
        }
    }

    public final int getSize() {
        return this.aaB.length;
    }

    public final float[] Pn() {
        return this.aaB;
    }

    public final int aws() {
        return this.cuo;
    }

    public final int getPosition() {
        return this.NB;
    }
}

