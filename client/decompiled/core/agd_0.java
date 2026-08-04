/*
 * Decompiled with CFR 0.152.
 */
import javax.media.opengl.GL;

/*
 * Renamed from agd
 */
public class agd_0
extends ub_0 {
    public static agd_0 ctE = new agd_0();

    public void b(db_2 db_22) {
        qp_2 qp_22 = (qp_2)db_22;
        GL gL = (GL)qp_22.LV();
        vo_1 vo_12 = vo_1.aik();
        vo_12.ir(0);
        vo_12.cx(true);
        gL.glStencilFunc(517, 1, 1);
        gL.glStencilOp(7680, 7680, 7680);
        vo_12.n(db_22);
        gL.glFlush();
    }

    private agd_0() {
    }
}

