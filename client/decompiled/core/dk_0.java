/*
 * Decompiled with CFR 0.152.
 */
import javax.media.opengl.GL;

/*
 * Renamed from Dk
 */
public class dk_0
extends ub_0 {
    public static dk_0 aNu = new dk_0();

    public void b(db_2 db_22) {
        qp_2 qp_22 = (qp_2)db_22;
        GL gL = (GL)qp_22.LV();
        vo_1 vo_12 = vo_1.aik();
        gL.glColorMask(false, false, false, false);
        vo_12.ir(1);
        vo_12.cx(true);
        gL.glStencilOp(7681, 7680, 7680);
        gL.glStencilFunc(512, 1, -1);
        vo_12.n(db_22);
        gL.glFlush();
    }

    private dk_0() {
    }
}

