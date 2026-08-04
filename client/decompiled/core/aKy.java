/*
 * Decompiled with CFR 0.152.
 */
import javax.media.opengl.GL;

class aKy
implements zz_1 {
    static final /* synthetic */ boolean bb;
    final /* synthetic */ String dTC;
    final /* synthetic */ uz_1 dTD;
    final /* synthetic */ zg_0 dTE;

    aKy(zg_0 zg_02, String string, uz_1 uz_12) {
        this.dTE = zg_02;
        this.dTC = string;
        this.dTD = uz_12;
    }

    public boolean anY() {
        if (!this.dTE.azh()) {
            return true;
        }
        qp_2 qp_22 = (qp_2)arX.cQT.iE();
        GL gL = (GL)qp_22.LV();
        int n2 = zg_0.a(this.dTE, gL);
        byte[] byArray = zg_0.a(this.dTE, n2, qp_22, this.dTC);
        if (!bb && this.dTD == null) {
            throw new AssertionError();
        }
        this.dTD.z(byArray);
        gL.glDeleteTextures(1, new int[]{n2}, 0);
        return false;
    }

    static {
        bb = !ajz.class.desiredAssertionStatus();
    }
}

