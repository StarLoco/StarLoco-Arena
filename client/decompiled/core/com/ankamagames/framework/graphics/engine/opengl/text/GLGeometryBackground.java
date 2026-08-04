/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.framework.graphics.engine.opengl.text;

import com.ankamagames.framework.graphics.engine.VertexBufferPCT;
import com.ankamagames.framework.graphics.engine.text.GeometryBackground;
import javax.media.opengl.GL;

public class GLGeometryBackground
extends GeometryBackground {
    public void a(db_2 db_22) {
        assert (db_22.vg() == arX.cQT);
        wq_1.Dn().a(this);
        this.cm();
        vo_1.aik().a(this.dA, this.dB);
        vo_1.aik().n(db_22);
        qp_2 qp_22 = (qp_2)db_22;
        qp_22.adV.nO(13);
        GL gL = (GL)qp_22.LV();
        this.a(gL, 7, this.aA, this.az);
        float f = this.getBorderWidth();
        if (f > 0.0f) {
            gL.glLineWidth(f);
            this.a(gL, 3, this.eZ, this.fa);
        }
        this.clean();
    }

    protected void af() {
    }

    protected void ag() {
        this.delete();
    }

    private void a(GL gL, int n2, VertexBufferPCT vertexBufferPCT, ams_1 ams_12) {
        gL.glVertexPointer(2, 5126, 0, vertexBufferPCT.ys());
        gL.glColorPointer(4, 5126, 0, vertexBufferPCT.yt());
        gL.glTexCoordPointer(2, 5126, 0, vertexBufferPCT.yu());
        gL.glDrawElements(n2, ams_12.aWY(), 5123, ams_12.aWZ());
    }
}

