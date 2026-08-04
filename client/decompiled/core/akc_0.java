/*
 * Decompiled with CFR 0.152.
 */
import java.io.IOException;

/*
 * Renamed from aKC
 */
class akc_0
implements uz_1 {
    final /* synthetic */ String dTI;
    final /* synthetic */ zg_0 dTE;

    akc_0(zg_0 zg_02, String string) {
        this.dTE = zg_02;
        this.dTI = string;
    }

    public void z(byte[] byArray) {
        try {
            vq_2.a(this.dTI, byArray);
        }
        catch (IOException iOException) {
            ajz.Dm().error((Object)"", (Throwable)iOException);
        }
    }
}

