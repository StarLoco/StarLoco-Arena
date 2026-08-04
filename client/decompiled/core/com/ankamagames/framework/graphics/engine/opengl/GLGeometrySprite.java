/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.framework.graphics.engine.opengl;

import com.ankamagames.framework.graphics.engine.geometry.GeometrySprite;
import javax.media.opengl.GL;

public class GLGeometrySprite
extends GeometrySprite {
    private static final int qL = GLGeometrySprite.L(GLGeometrySprite.class);

    public void a(db_2 db_22) {
        assert (db_22.vg() == arX.cQT);
        wq_1.Dn().a(this);
        vo_1 vo_12 = vo_1.aik();
        vo_12.b(this.aC);
        vo_12.cw(this.aD);
        vo_12.a(this.dA, this.dB);
        vo_12.n(db_22);
        qp_2 qp_22 = (qp_2)db_22;
        qp_22.adV.nO(13);
        GL gL = (GL)qp_22.LV();
        gL.glVertexPointer(2, 5126, 0, this.aA.ys());
        gL.glColorPointer(4, 5126, 0, this.aA.yt());
        gL.glTexCoordPointer(2, 5126, 0, this.aA.yu());
        gL.glDrawElements(7, 4, 5123, this.az.aWZ());
    }

    public static int it() {
        return qL;
    }

    protected void af() {
        super.af();
    }

    protected void ag() {
        super.ag();
    }
}

