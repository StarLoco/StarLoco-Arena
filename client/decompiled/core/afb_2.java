/*
 * Decompiled with CFR 0.152.
 */
import javax.media.opengl.GL;

/*
 * Renamed from aFb
 */
class afb_2
implements ap_1 {
    private static afb_2 dEU = new afb_2();

    protected afb_2() {
    }

    public static afb_2 aRp() {
        return dEU;
    }

    public void a(GL gL) {
        db_2 db_22 = arX.cQT.iE();
        vo_1 vo_12 = vo_1.aik();
        gL.glDisableClientState(32884);
        gL.glDisableClientState(32886);
        gL.glDisableClientState(32888);
        vo_12.cu(false);
        vo_12.n(db_22);
    }
}

