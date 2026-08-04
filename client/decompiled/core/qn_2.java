/*
 * Decompiled with CFR 0.152.
 */
import java.io.IOException;

/*
 * Renamed from qn
 */
public class qn_2
extends cj_2 {
    public qn_2() {
        this.im = null;
    }

    public aon_2 bz() {
        assert (this.im != null) : "Buffer == null. Did you forget to call Read ?";
        acf acf2 = acf.T(this.im);
        try {
            int n2;
            acf2.setOffset(4);
            short s = acf2.readShort();
            short s2 = acf2.readShort();
            int n3 = acf2.readInt();
            int n4 = acf2.readInt();
            int n5 = acf2.readInt();
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
            assert (awi_02.dik == 1) : "DDSM file can't have mipmaps";
            kf_0[] kf_0Array = new kf_0[1];
            int n6 = awi_02.fb;
            int n7 = awi_02.fc;
            int n8 = (n6 + 3) / 4 * ((n7 + 3) / 4) * n2;
            kf_0Array[0] = new kf_0((int)s, (int)s2, 32, null, this.im, acf2.getOffset(), n8);
            kf_0Array[0].a(this.im, acf2.getOffset() + n8, n4);
            return new aon_2(pw2, kf_0Array);
        }
        catch (IOException iOException) {
            a.error((Object)"Exception", (Throwable)iOException);
            return null;
        }
    }

    protected aon_2 a(acf acf2) {
        try {
            int n2;
            acf2.jD(4);
            short s = acf2.readShort();
            short s2 = acf2.readShort();
            int n3 = acf2.readInt();
            int n4 = acf2.readInt();
            int n5 = acf2.readInt();
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
            assert (awi_02.dik == 1) : "DDSM file can't have mipmaps";
            kf_0[] kf_0Array = new kf_0[1];
            int n6 = awi_02.fb;
            int n7 = awi_02.fc;
            int n8 = (n6 + 3) / 4 * ((n7 + 3) / 4) * n2;
            byte[] byArray = new byte[n8];
            acf2.U(byArray);
            kf_0Array[0] = new kf_0(s, s2, 32, null, byArray);
            byte[] byArray2 = new byte[n4];
            acf2.U(byArray2);
            kf_0Array[0].a(byArray2, 0, n4);
            return new aon_2(pw2, kf_0Array);
        }
        catch (IOException iOException) {
            a.error((Object)"Exception", (Throwable)iOException);
            return null;
        }
    }

    protected boolean bA() {
        if (this.im[0] == 77 && this.im[1] == 83 && this.im[2] == 68 && this.im[3] == 68) {
            return true;
        }
        a.error((Object)"This is not a valid DDSM file");
        return false;
    }
}

