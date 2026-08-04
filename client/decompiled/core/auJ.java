/*
 * Decompiled with CFR 0.152.
 */
import javax.media.opengl.GL;

public class auJ
extends ub_0 {
    public static auJ cWH = new auJ();

    public void b(db_2 db_22) {
        qp_2 qp_22 = (qp_2)db_22;
        GL gL = (GL)qp_22.LV();
        vo_1 vo_12 = vo_1.aik();
        vo_12.cx(false);
        vo_12.n(db_22);
        gL.glColorMask(true, true, true, true);
    }

    private auJ() {
    }
}

