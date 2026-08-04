/*
 * Decompiled with CFR 0.152.
 */
import java.io.IOException;

public class asL
extends cj_2 {
    public asL() {
        this.im = null;
    }

    public final aon_2 bz() {
        assert (this.im != null) : "Buffer == null. Did you forget to call Read ?";
        try {
            int n2;
            acf acf2 = acf.T(this.im);
            acf2.setOffset(4);
            awi_0 awi_02 = new awi_0();
            awi_02.p(acf2);
            pw pw2 = new pw(awi_02.dim.cWB);
            if (pw2.getID() == pw.bu("DXT1")) {
                n2 = 8;
            } else if (pw2.getID() == pw.bu("DXT2") || pw2.getID() == pw.bu("DXT3") || pw2.getID() == pw.bu("DXT4") || pw2.getID() == pw.bu("DXT5")) {
                n2 = 16;
            } else {
                a.error((Object)("Unsupported format " + pw2.getString()));
                return null;
            }
            kf_0[] kf_0Array = new kf_0[awi_02.dik];
            int n3 = awi_02.fb;
            int n4 = awi_02.fc;
            for (int j = 0; j < awi_02.dik; ++j) {
                int n5 = (n3 + 3) / 4 * ((n4 + 3) / 4) * n2;
                kf_0Array[j] = new kf_0(n3, n4, 32, null, this.im, acf2.getOffset(), n5);
                acf2.setOffset(acf2.getOffset() + n5);
                n4 /= 2;
                if ((n3 /= 2) == 0) {
                    n3 = 1;
                }
                if (n4 != 0) continue;
                n4 = 1;
            }
            return new aon_2(pw2, kf_0Array);
        }
        catch (IOException iOException) {
            a.error((Object)"Exception, e");
            return null;
        }
    }

    protected aon_2 a(acf acf2) {
        try {
            int n2;
            acf2.jD(4);
            awi_0 awi_02 = new awi_0();
            awi_02.p(acf2);
            pw pw2 = new pw(awi_02.dim.cWB);
            if (pw2.getID() == pw.bu("DXT1")) {
                n2 = 8;
            } else if (pw2.getID() == pw.bu("DXT2") || pw2.getID() == pw.bu("DXT3") || pw2.getID() == pw.bu("DXT4") || pw2.getID() == pw.bu("DXT5")) {
                n2 = 16;
            } else {
                a.error((Object)("Unsupported format " + pw2.getString()));
                return null;
            }
            kf_0[] kf_0Array = new kf_0[awi_02.dik];
            int n3 = awi_02.fb;
            int n4 = awi_02.fc;
            for (int j = 0; j < awi_02.dik; ++j) {
                int n5 = (n3 + 3) / 4 * ((n4 + 3) / 4) * n2;
                byte[] byArray = new byte[n5];
                acf2.U(byArray);
                kf_0Array[j] = new kf_0(n3, n4, 32, null, byArray);
                n4 /= 2;
                if ((n3 /= 2) == 0) {
                    n3 = 1;
                }
                if (n4 != 0) continue;
                n4 = 1;
            }
            return new aon_2(pw2, kf_0Array);
        }
        catch (IOException iOException) {
            a.error((Object)"Exception", (Throwable)iOException);
            return null;
        }
    }

    protected final boolean bA() {
        if (this.im[0] == 68 && this.im[1] == 68 && this.im[2] == 83) {
            return true;
        }
        a.error((Object)"This is not a valid DDS file");
        return false;
    }
}

