/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aau
 */
public class aau_1 {
    private final short fL;
    private final short bIY;
    private final short bIZ;
    private final short Gp;
    private final short cfQ;
    private final short bJc;
    private final int it;
    private final aGz cfR;
    private final jg_0 bJe;
    private final boolean bJf;
    private final int bJg;
    private final boolean bJh;

    public aau_1(short s, short s2, short s3, short s4, short s5, short s6, aGz aGz2, jg_0 jg_02, boolean bl2, int n2, int n3, boolean bl3) {
        this.fL = s;
        this.bIY = s2;
        this.bIZ = s3;
        this.Gp = s4;
        this.cfQ = s5;
        this.bJc = s6;
        this.cfR = aGz2;
        this.bJe = jg_02;
        this.bJf = bl2;
        this.bJg = n2;
        this.it = n3;
        this.bJh = bl3;
    }

    public boolean a(aGz aGz2, asc asc2) {
        if (!this.a(aGz2)) {
            return false;
        }
        return this.a(asc2);
    }

    public boolean a(aGz aGz2) {
        short[] sArray = this.cfR.Gj();
        for (int j = 0; j < sArray.length; ++j) {
            if (aGz2.cp(sArray[j]) >= this.cfR.cp(sArray[j])) continue;
            return false;
        }
        return true;
    }

    public boolean a(asc asc2) {
        jg_0 jg_02 = this.bJe;
        for (int j = 0; j < jg_02.size(); ++j) {
            if (asc2.bY(jg_02.bu(j))) continue;
            return false;
        }
        return true;
    }

    public short tI() {
        return this.fL;
    }

    public short adQ() {
        return this.bIY;
    }

    public short getType() {
        return this.Gp;
    }

    public aGz aoW() {
        return this.cfR;
    }

    public short aoX() {
        return this.cfQ;
    }

    public short adU() {
        return this.bIZ;
    }

    public short adV() {
        return this.bJc;
    }

    public boolean adW() {
        return this.bJf;
    }

    public jg_0 adX() {
        return this.bJe;
    }

    public int adY() {
        return this.bJg;
    }

    public int eA() {
        return this.it;
    }

    public boolean isHidden() {
        return this.bJh;
    }
}

