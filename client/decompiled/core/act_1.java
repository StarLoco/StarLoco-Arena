/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from acT
 */
public class act_1
extends yw_2 {
    private static vP cle = new vP();

    public act_1(byte by, byte by2, byte by3, byte by4, byte by5, byte by6, byte by7, byte by8, boolean bl2) {
        super(by, by2, by3, by4, by5, by6, by7, by8, bl2);
    }

    public boolean b(byte by, byte by2, byte by3, byte by4) {
        return by2 == 0 && by3 == 0;
    }

    public void d(byte[] byArray, int n2) {
        cle.c(byArray[n2], (byte)-1, (byte)-1, (byte)-1);
        float f = cle.Ct();
        cle.c(this.aCA, this.aCB, this.aCC, this.aCD);
        cle.V(f);
        byArray[n2] = cle.Ci();
        byArray[n2 + 1] = cle.Cj();
        byArray[n2 + 2] = cle.Ck();
        if (this.aCE) {
            int n3 = n2 + 3;
            byArray[n3] = (byte)((float)byArray[n3] * cle.getAlpha());
        }
    }
}

