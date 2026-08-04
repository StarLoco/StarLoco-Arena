/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from abY
 */
public class aby_2 {
    private int aG;
    private int aH;
    private int bgG;

    public aby_2() {
        this(0, 0, 0);
    }

    public aby_2(aby_2 aby_22) {
        this(aby_22.aG, aby_22.aH, aby_22.bgG);
    }

    public aby_2(int[] nArray) {
        this(nArray[0], nArray[1], nArray[2]);
    }

    public aby_2(ry ry2, ry ry3) {
        this.aG = ry3.getX() - ry2.getX();
        this.aH = ry3.getY() - ry2.getY();
        this.bgG = ry3.wk() - ry2.wk();
    }

    public aby_2(int n2, int n3, int n4) {
        this.aG = n2;
        this.aH = n3;
        this.bgG = n4;
    }

    public aby_2(int n2, int n3, int n4, int n5, int n6, int n7) {
        this.aG = n5 - n2;
        this.aH = n6 - n3;
        this.bgG = n7 - n4;
    }

    public void l(int[] nArray) {
        this.aG = nArray[0];
        this.aH = nArray[1];
        this.bgG = nArray[2];
    }

    public int getX() {
        return this.aG;
    }

    public void setX(int n2) {
        this.aG = n2;
    }

    public int getY() {
        return this.aH;
    }

    public void setY(int n2) {
        this.aH = n2;
    }

    public int Ui() {
        return this.bgG;
    }

    public void jB(int n2) {
        this.bgG = n2;
    }

    public aby_2 a(aby_2 aby_22) {
        return new aby_2(aby_22.aG + this.aG, aby_22.aH + this.aH, aby_22.bgG + this.bgG);
    }

    public aby_2 b(aby_2 aby_22) {
        return new aby_2(this.aG - aby_22.aG, this.aH - aby_22.aH, this.bgG - aby_22.bgG);
    }

    public aby_2 c(aby_2 aby_22) {
        return new aby_2(this.aG * aby_22.aG + this.aG * aby_22.aH + this.aG * aby_22.bgG, this.aH * aby_22.aG + this.aH * aby_22.aH + this.aH * aby_22.bgG, this.bgG * aby_22.aG + this.bgG * aby_22.aH + this.bgG * aby_22.bgG);
    }

    public aby_2 jC(int n2) {
        return new aby_2(n2 * this.aG, n2 * this.aH, n2 * this.bgG);
    }

    public aby_2 aL(float f) {
        return new aby_2((int)(f * (float)this.aG), (int)(f * (float)this.aH), (int)(f * (float)this.bgG));
    }

    public float d(aby_2 aby_22) {
        return this.aG * aby_22.aH + this.aH * aby_22.bgG + this.bgG * aby_22.aG - aby_22.aG * this.aH - aby_22.aH * this.bgG - aby_22.bgG * this.aG;
    }

    public float e(aby_2 aby_22) {
        return this.aG * aby_22.aG + this.aH * aby_22.aH + this.bgG * aby_22.bgG;
    }

    public int aqy() {
        return this.aG * this.aG + this.aH * this.aH + this.bgG * this.bgG;
    }

    public int length() {
        int n2 = this.aG * this.aG + this.aH * this.aH + this.bgG * this.bgG;
        return ej_0.ap(n2);
    }

    public aby_2 aqz() {
        int n2 = this.length();
        return this.jC(1 / n2);
    }

    public boolean equals(Object object) {
        if (object == null || !(object instanceof aby_2)) {
            return false;
        }
        aby_2 aby_22 = (aby_2)object;
        return aby_22.aG == this.aG && aby_22.aH == this.aH && aby_22.bgG == this.bgG;
    }

    public String toString() {
        return "[" + this.aG + " ; " + this.aH + " ; " + this.bgG + "]";
    }

    public static qc_0 D(float f, float f2) {
        return agv_0.D(f, f2);
    }

    public qc_0 aqA() {
        return agv_0.D(this.aG, this.aH);
    }

    public static qc_0 E(float f, float f2) {
        return agv_0.E(f, f2);
    }

    public qc_0 aqB() {
        return agv_0.E(this.aG, this.aH);
    }

    public qc_0 e(ye_0 ye_02) {
        int n2 = this.aG + this.aH;
        int n3 = this.aG - this.aH;
        int[] nArray = ye_02.acJ();
        if (n2 == 0) {
            n2 = nArray[0] + nArray[1];
        }
        if (n3 == 0) {
            n3 = nArray[0] - nArray[1];
        }
        if (n2 > 0) {
            if (n3 > 0) {
                return qc_0.bEK;
            }
            return qc_0.bEM;
        }
        if (n3 > 0) {
            return qc_0.bEQ;
        }
        return qc_0.bEO;
    }

    public int hashCode() {
        assert (false) : "Il n'est pas pr\u00e9vu que ces objets comparables servent de clef dans une HashTable/HashMap.";
        return super.hashCode();
    }
}

