/*
 * Decompiled with CFR 0.152.
 */
import javax.media.opengl.GL;

/*
 * Renamed from bu
 */
public class bu_0
implements ap_1 {
    private static bu_0 ga = new bu_0();

    protected bu_0() {
    }

    public static bu_0 cO() {
        return ga;
    }

    public void a(GL gL) {
        db_2 db_22 = arX.cQT.iE();
        vo_1 vo_12 = vo_1.aik();
        vo_12.a(air.cya, air.cye);
        vo_12.cr(true);
        vo_12.n(db_22);
        gL.glDisable(2929);
        gL.glDepthMask(false);
        gL.glActiveTexture(33984);
        gL.glTexEnvf(8960, 8704, 34160.0f);
        gL.glTexEnvf(8960, 34161, 8448.0f);
        gL.glTexEnvf(8960, 34176, 33984.0f);
        gL.glTexEnvf(8960, 34163, 2.0f);
        gL.glEnableClientState(32884);
        gL.glEnableClientState(32886);
        gL.glEnableClientState(32888);
        vo_12.cu(true);
        vo_12.n(db_22);
    }
}

