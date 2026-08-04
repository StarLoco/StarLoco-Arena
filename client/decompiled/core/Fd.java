/*
 * Decompiled with CFR 0.152.
 */
import java.io.IOException;

public class Fd
extends cj_2 {
    public aon_2 bz() {
        assert (this.im != null) : "Buffer == null. Did you forget to call Read ?";
        try {
            acf acf2 = acf.T(this.im);
            Xe xe = new Xe();
            xe.n(acf2);
            eu_1 eu_12 = null;
            if (xe.bWU != 0) {
                int n2;
                int n3;
                if (xe.bWW != 4 && xe.bWW != 8) {
                    n3 = xe.bWV + 7 >> 3;
                    n2 = xe.bWU * n3;
                    acf2.setOffset(acf2.getOffset() + n2);
                } else {
                    n3 = acf2.getOffset();
                    eu_12 = new eu_1(xe.bWU);
                    for (n2 = 0; n2 < xe.bWU; ++n2) {
                        eu_12.b(new vP(-1, this.im[n3 + 2], this.im[n3 + 1], this.im[n3]));
                        n3 += 3;
                    }
                    acf2.setOffset(acf2.getOffset() + xe.bWU * 3);
                }
            }
            byte[] byArray = this.c(xe.adE, xe.adF, xe.bWW, acf2.getOffset());
            acf2.close();
            aon_2.h(byArray, xe.bWW);
            kf_0[] kf_0Array = new kf_0[]{new kf_0(xe.adE, xe.adF, xe.fs, xe.ft, xe.bWW, eu_12, byArray, 0, byArray.length)};
            return new aon_2(pw.acc, kf_0Array);
        }
        catch (IOException iOException) {
            a.error((Object)"Exception", (Throwable)iOException);
            return null;
        }
    }

    protected aon_2 a(acf acf2) {
        try {
            Xe xe = new Xe();
            xe.n(acf2);
            eu_1 eu_12 = null;
            if (xe.bWU != 0) {
                int n2;
                int n3;
                if (xe.bWW != 4 && xe.bWW != 8) {
                    n3 = xe.bWV + 7 >> 3;
                    n2 = xe.bWU * n3;
                    acf2.jD(n2);
                } else {
                    n3 = acf2.getOffset();
                    eu_12 = new eu_1(xe.bWU);
                    for (n2 = 0; n2 < xe.bWU; ++n2) {
                        byte by = acf2.readByte();
                        byte by2 = acf2.readByte();
                        byte by3 = acf2.readByte();
                        eu_12.b(new vP(-1, (int)by3, (int)by2, (int)by));
                        n3 += 3;
                    }
                }
            }
            byte[] byArray = this.b(acf2, (int)xe.adE, (int)xe.adF, (int)xe.bWW);
            acf2.close();
            aon_2.h(byArray, xe.bWW);
            kf_0 kf_02 = new kf_0(xe.adE, xe.adF, xe.fs, xe.ft, xe.bWW, eu_12, byArray);
            return new aon_2(pw.acc, kf_02);
        }
        catch (IOException iOException) {
            a.error((Object)"Exception", (Throwable)iOException);
            return null;
        }
    }

    protected boolean bA() {
        return true;
    }
}

