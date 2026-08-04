/*
 * Decompiled with CFR 0.152.
 */
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class uM
implements uz_1 {
    final /* synthetic */ aMO aqR;

    uM(aMO aMO2) {
        this.aqR = aMO2;
    }

    public void z(byte[] byArray) {
        byte[] byArray2 = null;
        try {
            byArray2 = MessageDigest.getInstance("MD5").digest(byArray);
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            zz_0.Dm().error((Object)"", (Throwable)noSuchAlgorithmException);
        }
        this.aqR.a(byArray, byArray2);
    }
}

