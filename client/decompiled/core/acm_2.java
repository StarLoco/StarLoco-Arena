/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aCM
 */
public class acm_2
extends yw_2 {
    private static vP cle = new vP();

    public acm_2(boolean bl2) {
        super((byte)0, (byte)0, (byte)0, (byte)0, (byte)0, (byte)0, (byte)0, (byte)0, bl2);
    }

    public boolean b(byte by, byte by2, byte by3, byte by4) {
        return by == by2 && by2 == by3;
    }

    public void d(byte[] byArray, int n2) {
        cle.c(byArray[n2], byArray[n2], byArray[n2], (byte)-1);
        float f = cle.Ct();
        cle.c(this.aCA, this.aCB, this.aCC, this.aCD);
        cle.V(ej_0.b(f *= cle.Cs(), 0.0f, 1.0f));
        byArray[n2] = cle.Ci();
        byArray[n2 + 1] = cle.Cj();
        byArray[n2 + 2] = cle.Ck();
        if (this.aCE) {
            int n3 = n2 + 3;
            byArray[n3] = (byte)((float)byArray[n3] * cle.getAlpha());
        }
    }
}

