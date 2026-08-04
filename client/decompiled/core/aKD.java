/*
 * Decompiled with CFR 0.152.
 */
import java.io.ByteArrayOutputStream;
import java.io.IOException;

class aKD
implements uz_1 {
    final /* synthetic */ ByteArrayOutputStream dTJ;
    final /* synthetic */ zg_0 dTE;

    aKD(zg_0 zg_02, ByteArrayOutputStream byteArrayOutputStream) {
        this.dTE = zg_02;
        this.dTJ = byteArrayOutputStream;
    }

    public void z(byte[] byArray) {
        try {
            this.dTJ.write(byArray);
        }
        catch (IOException iOException) {
            ajz.Dm().error((Object)"", (Throwable)iOException);
        }
    }
}

