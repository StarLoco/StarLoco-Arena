/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.framework.graphics.engine.opengl;

import com.ankamagames.framework.graphics.engine.geometry.GeometryMesh;
import javax.media.opengl.GL;

public class GLGeometryMesh
extends GeometryMesh {
    private static final int qL = GLGeometryMesh.L(GLGeometryMesh.class);
    private float tP;
    private float tR;
    private final agw_1 dWm = new agw_1();
    private final agw_1 dWn = new agw_1();

    public void a(db_2 db_22) {
        assert (db_22.vg() == arX.cQT);
        wq_1.Dn().a(this);
        vo_1.aik().b(this.aC);
        vo_1.aik().cw(this.aD);
        vo_1.aik().a(this.dA, this.dB);
        vo_1.aik().n(db_22);
        qp_2 qp_22 = (qp_2)db_22;
        qp_22.adV.nO(13);
        GL gL = (GL)qp_22.LV();
        gL.glVertexPointer(2, 5126, 0, this.aA.ys());
        gL.glColorPointer(4, 5126, 0, this.aA.yt());
        gL.glTexCoordPointer(2, 5126, 0, this.aA.yu());
        jB jB2 = this.ad();
        switch (jB2) {
            case AX: {
                gL.glDrawElements(4, this.az.aWY(), 5123, this.az.aWZ());
                break;
            }
            case AY: {
                gL.glDrawElements(5, this.aA.fq(), 5123, this.az.aWZ());
                break;
            }
            case Ba: {
                gL.glDrawElements(7, this.aA.fq(), 5123, this.az.aWZ());
                break;
            }
            case AW: {
                gL.glDrawElements(3, this.aA.fq(), 5123, this.az.aWZ());
                break;
            }
            case AV: {
                gL.glDrawElements(1, this.aA.fq(), 5123, this.az.aWZ());
                break;
            }
            case AU: {
                gL.glDrawElements(0, this.aA.fq(), 5123, this.az.aWZ());
                break;
            }
            default: {
                assert (false) : "Unimplemented mesh type";
                break;
            }
        }
    }

    public static int it() {
        return qL;
    }

    public void HF() {
        super.HF();
    }

    protected void af() {
        super.af();
    }

    protected void ag() {
        super.ag();
    }

    public void a(float f, float f2, agw_1 agw_12, agw_1 agw_13) {
        this.tP = f;
        this.tR = f2;
        this.dWm.k(agw_12.getX(), agw_12.getY());
        this.dWn.k(agw_13.getX(), agw_13.getY());
    }

    public void E(float f, float f2, float f3, float f4) {
        this.tP = f;
        this.tR = f2;
        this.dWm.k(f3, 0.0f);
        this.dWn.k(0.0f, f4);
    }

    public void c(ak_2 ak_22) {
        this.tP = ak_22.bB();
        this.tR = ak_22.bD();
        this.dWm.k(ak_22.bC() - this.tP, 0.0f);
        this.dWn.k(0.0f, ak_22.bE() - this.tR);
    }

    public float getMinX() {
        return this.tP;
    }

    public float getMinY() {
        return this.tR;
    }

    public agw_1 aWC() {
        return this.dWm;
    }

    public agw_1 aWD() {
        return this.dWn;
    }
}

