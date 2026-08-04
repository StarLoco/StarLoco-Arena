/*
 * Decompiled with CFR 0.152.
 */
import javax.media.opengl.GL;

/*
 * Renamed from auX
 */
public class aux__0
implements ap_1 {
    private static aux__0 cXT = new aux__0();

    protected aux__0() {
    }

    public static aux__0 aHL() {
        return cXT;
    }

    public void a(GL gL) {
        db_2 db_22 = arX.cQT.iE();
        vo_1 vo_12 = vo_1.aik();
        vo_12.cr(false);
        gL.glDisable(2929);
        gL.glDepthMask(false);
        gL.glDisable(34037);
        vo_12.cu(false);
        gL.glDisableClientState(32884);
        gL.glDisableClientState(32886);
        gL.glDisableClientState(32888);
        vo_12.n(db_22);
    }
}

