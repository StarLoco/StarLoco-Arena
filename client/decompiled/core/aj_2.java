/*
 * Decompiled with CFR 0.152.
 */
import java.io.IOException;

/*
 * Renamed from aJ
 */
public class aj_2
extends cj_2 {
    public aon_2 bz() {
        assert (this.im != null) : "Buffer == null. Did you forget to call Read ?";
        try {
            acf acf2 = acf.T(this.im);
            acf2.setOffset(4);
            short s = acf2.readShort();
            short s2 = acf2.readShort();
            int n2 = acf2.readInt();
            int n3 = acf2.readInt();
            kf_0 kf_02 = new kf_0((int)s, (int)s2, 32, null, this.im, acf2.getOffset(), n2);
            acf2.setOffset(acf2.getOffset() + n2);
            kf_02.a(this.im, acf2.getOffset(), n3);
            return new aon_2(pw.acd, kf_02);
        }
        catch (IOException iOException) {
            a.error((Object)"Exception", (Throwable)iOException);
            return null;
        }
    }

    protected aon_2 a(acf acf2) {
        acf2.jD(4);
        short s = acf2.readShort();
        short s2 = acf2.readShort();
        int n2 = acf2.readInt();
        int n3 = acf2.readInt();
        byte[] byArray = new byte[n2];
        acf2.U(byArray);
        kf_0 kf_02 = new kf_0(s, s2, 32, null, byArray);
        byte[] byArray2 = new byte[n3];
        acf2.U(byArray2);
        kf_02.a(byArray2, 0, n3);
        acf2.close();
        return new aon_2(pw.acd, kf_02);
    }

    protected boolean bA() {
        if (this.im[0] == 77 && this.im[1] == 65 && this.im[2] == 71 && this.im[3] == 84) {
            return true;
        }
        a.error((Object)"This is not a valid TGAM file");
        return false;
    }
}

