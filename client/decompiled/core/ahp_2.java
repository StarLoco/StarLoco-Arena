/*
 * Decompiled with CFR 0.152.
 */
import javax.media.opengl.GL;

/*
 * Renamed from ahp
 */
class ahp_2
implements ap_1 {
    private static ahp_2 cvr = new ahp_2();

    protected ahp_2() {
    }

    public static ahp_2 awV() {
        return cvr;
    }

    public void a(GL gL) {
        gL.glEnable(2848);
        gL.glEnable(2852);
        gL.glLineStipple(4, (short)-21846);
    }
}

