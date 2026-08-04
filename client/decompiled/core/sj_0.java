/*
 * Decompiled with CFR 0.152.
 */
import javax.media.opengl.GL;

/*
 * Renamed from sj
 */
public class sj_0
extends ub_0 {
    public int fb;
    public int fc;

    public void b(db_2 db_22) {
        qp_2 qp_22 = (qp_2)db_22;
        GL gL = (GL)qp_22.LV();
        vo_1 vo_12 = vo_1.aik();
        vo_12.cu(false);
        gL.glColorMask(false, false, false, false);
        vo_12.ir(-1);
        vo_12.cx(true);
        gL.glClear(1024);
        gL.glStencilOp(7681, 7681, 7681);
        gL.glStencilFunc(512, 1, -1);
        gL.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        vo_12.n(db_22);
        int n2 = Math.min(this.fb, this.fc) / 2;
        alj_0.aWw().P(this.fb / 2, this.fc / 2, n2);
        gL.glColorMask(true, true, true, true);
        gL.glStencilFunc(514, 1, -1);
        gL.glStencilOp(7680, 7680, 7680);
    }
}

