/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.framework.graphics.engine.fx.FixedPipeline;

import com.ankamagames.framework.graphics.engine.entity.Entity3D;
import com.ankamagames.framework.kernel.core.maths.Matrix44;
import java.util.ArrayList;
import javax.media.opengl.GL;

public class Water
extends Entity3D {
    public static final float dKc = -0.05f;
    private float fG;
    private float IQ;
    private float IR;
    private float IS;
    private boolean dep;
    private Matrix44 uz = new Matrix44();
    private Matrix44 dKd = new Matrix44();
    private Matrix44 dKe = new Matrix44();
    private Matrix44 dKf = new Matrix44();
    private Matrix44 dKg = new Matrix44();
    aBT dKh = new aBT(this);
    aCu dKi = new aCu(this, 30);
    public int dKj = 0;
    private ArrayList dKk = new ArrayList();

    public Water() {
        float[] fArray = this.uz.Pn();
        fArray[0] = -2.1213205f;
        fArray[1] = -1.5f;
        fArray[2] = -1.5f;
        fArray[3] = 0.0f;
        fArray[4] = 2.1213207f;
        fArray[5] = -1.5f;
        fArray[6] = -1.5f;
        fArray[7] = 0.0f;
        fArray[8] = 0.0f;
        fArray[9] = -0.7071069f;
        fArray[10] = 0.7071069f;
        fArray[11] = 0.0f;
        fArray[12] = 0.0f;
        fArray[13] = 0.0f;
        fArray[14] = 0.0f;
        fArray[15] = 1.0f;
        fArray = this.dKd.Pn();
        fArray[0] = 2.0f;
        fArray[1] = 0.0f;
        fArray[2] = 0.0f;
        fArray[3] = 0.0f;
        fArray[4] = 0.0f;
        fArray[5] = 2.0f;
        fArray[6] = 0.0f;
        fArray[7] = 0.0f;
        fArray[8] = 0.0f;
        fArray[9] = 0.0f;
        fArray[10] = 1.0f;
        fArray[11] = 0.0f;
        fArray[12] = 0.0f;
        fArray[13] = 0.0f;
        fArray[14] = 0.0f;
        fArray[15] = 1.0f;
        this.dKh.setTexture(cx_0.JY().bt(-1296775008915292156L));
        this.dKh.c(air.cya, air.cxZ);
        this.dKi.setTexture(cx_0.JY().bt(-1296775008915292153L));
        this.dKi.c(air.cya, air.cye);
        this.S(0.0f, 0.3f);
        this.IQ = 1.0f;
        this.IR = 1.0f;
        this.IS = 1.0f;
        this.dep = false;
    }

    public void S(float f, float f2) {
        this.dKi.S(f, f2);
    }

    public void a(float f) {
        if (!this.dep) {
            return;
        }
        this.fG += f;
        this.dKh.a(f);
        this.dKi.a(f);
    }

    public void a(db_2 db_22) {
        if (!this.dep) {
            return;
        }
        GL gL = (GL)((qp_2)db_22).LV();
        vo_1 vo_12 = vo_1.aik();
        this.dKf.d(db_22.LU());
        this.dKe.d(db_22.LT());
        gL.glGetFloatv(2983, this.dKg.Pn(), 0);
        db_22.c(Matrix44.bEn);
        db_22.b(this.uz);
        vo_12.a(jq_0.bmG);
        vo_12.n(db_22);
        gL.glLoadMatrixf(this.dKd.Pn(), 0);
        super.a(db_22);
        db_22.b(this.dKe);
        db_22.c(this.dKf);
        vo_12.a(jq_0.bmG);
        vo_12.n(db_22);
        gL.glLoadMatrixf(this.dKg.Pn(), 0);
    }

    public void d(db_2 db_22) {
        GL gL = (GL)((qp_2)db_22).LV();
        gL.glDepthMask(true);
        gL.glClearDepth(65000.0);
        gL.glDepthFunc(515);
        gL.glEnable(2929);
        gL.glClear(256);
        gL.glEnable(2884);
        gL.glCullFace(2305);
        this.dKh.a(db_22);
        this.dKi.a(db_22);
        int n2 = this.dKk.size();
        for (int j = 0; j < n2; ++j) {
            bn bn2 = (bn)this.dKk.get(j);
            bn2.a(db_22);
        }
        gL.glDisable(2884);
        gL.glPolygonMode(1032, 6914);
        gL.glDepthMask(false);
    }

    public void enable(boolean bl2) {
        this.dep = bl2;
    }

    public void c(float f, float f2, float f3) {
        this.IQ = f;
        this.IR = f2;
        this.IS = f3;
    }

    private void aTi() {
        this.dKk.add(new bn(this));
    }

    public static /* synthetic */ float a(Water water) {
        return water.IQ;
    }

    public static /* synthetic */ float b(Water water) {
        return water.IR;
    }

    public static /* synthetic */ float c(Water water) {
        return water.IS;
    }
}

