/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.framework.graphics.engine.entity;

import com.ankamagames.framework.graphics.engine.entity.Entity;
import com.ankamagames.framework.graphics.engine.geometry.GeometrySprite;
import java.util.HashMap;
import javax.media.opengl.GL;

public final class EntitySprite
extends Entity {
    public static final int TYPE = 2;
    private GeometrySprite aIb;
    private ef_1 tl;
    public ef_1 aIc;
    public ef_1 aId;
    private aPb tJ;
    private boolean GD;
    private static final int qL = EntitySprite.L(EntitySprite.class);
    private static final boolean DEBUG = false;
    private int aIe = 0;
    private final HashMap aIf = new HashMap();
    private final HashMap aIg = new HashMap();

    public EntitySprite() {
        this.tJ = aPb.aYI();
        this.tJ.d(aPb.enf);
        this.tJ.H(0.0f, 0.0f, 0.0f, 0.0f);
        this.rv = 2;
        this.GD = false;
    }

    public void a(GeometrySprite geometrySprite) {
        geometrySprite.HE();
        if (this.aIb != null) {
            this.aIb.HF();
        }
        this.aIb = geometrySprite;
    }

    public final GeometrySprite Hu() {
        return this.aIb;
    }

    public final void k(float f, float f2, float f3, float f4) {
        this.aIb.k(f, f2, f3, f4);
    }

    public final void setTexture(ef_1 ef_12) {
        if (ef_12 != null) {
            ef_12.HE();
        }
        if (this.tl != null) {
            this.tl.HF();
        }
        this.tl = ef_12;
    }

    public final ef_1 jI() {
        return this.tl;
    }

    public final aPb getMaterial() {
        return this.tJ;
    }

    public final void setMaterial(aPb aPb2) {
        if (this.tJ != null && this.tJ.e(aPb2)) {
            return;
        }
        if (this.tJ != null) {
            this.tJ.release();
        }
        this.tJ = aPb2;
        this.GD = true;
    }

    public final void Hv() {
        this.GD = true;
    }

    public final void x(float f, float f2) {
        this.aIb.x(f, f2);
    }

    public final void setSize(int n2, int n3) {
        this.aIb.setSize(n2, n3);
    }

    public final void setColor(float f, float f2, float f3, float f4) {
        this.aIb.setColor(f, f2, f3, f4);
    }

    public final void a(un un2, float f, float f2, float f3, float f4) {
        assert (this.exists()) : "Using an item with a reference counter < 0 is forbidden";
        this.aIb.a(un2, f, f2, f3, f4);
    }

    public final void setColor(vP vP2) {
        this.aIb.setColor(vP2.Cp(), vP2.Cq(), vP2.Cr(), vP2.getAlpha());
    }

    public final void a(un un2, vP vP2) {
        assert (this.exists()) : "Using an item with a reference counter < 0 is forbidden";
        this.aIb.a(un2, vP2.Cp(), vP2.Cq(), vP2.Cr(), vP2.getAlpha());
    }

    public final float Hw() {
        return this.aIb.Hw();
    }

    public final float Hx() {
        return this.aIb.Hx();
    }

    public final float Hy() {
        return this.aIb.Hy();
    }

    public final float Hz() {
        return this.aIb.Hz();
    }

    public final float HA() {
        return this.aIb.HA();
    }

    public final float HB() {
        return this.aIb.HB();
    }

    public final int getWidth() {
        return this.aIb.getWidth();
    }

    public final int getHeight() {
        return this.aIb.getHeight();
    }

    public final float HC() {
        return this.aIb.HC();
    }

    public final float HD() {
        return this.aIb.HD();
    }

    public void a(float f) {
    }

    public void d(db_2 db_22) {
        GL gL;
        vo_1 vo_12 = vo_1.aik();
        db_22.b(this.aUM().ki());
        if (this.GD && this.aFO()) {
            this.GD = false;
            this.aIb.a(this.tJ);
        }
        this.cws.b(db_22);
        if (this.tl != null) {
            gL = (GL)((qp_2)db_22).LV();
            vo_12.cu(true);
            vo_12.n(db_22);
            gL.glClientActiveTexture(33984);
            gL.glActiveTexture(33984);
            this.tl.f(db_22);
        } else {
            vo_12.cu(false);
        }
        if (this.aIc != null) {
            gL = (GL)((qp_2)db_22).LV();
            gL.glClientActiveTexture(33985);
            gL.glActiveTexture(33985);
            vo_12.cu(true);
            vo_12.n(db_22);
            this.aIc.f(db_22);
            gL.glClientActiveTexture(33984);
            gL.glActiveTexture(33984);
        }
        if (this.aId != null) {
            gL = (GL)((qp_2)db_22).LV();
            gL.glClientActiveTexture(33986);
            gL.glActiveTexture(33986);
            vo_12.cu(true);
            vo_12.n(db_22);
            this.aId.f(db_22);
            gL.glClientActiveTexture(33984);
            gL.glActiveTexture(33984);
        }
        if (this.tJ != null) {
            db_22.a(this.tJ);
        }
        this.aIb.a(db_22);
        if (this.aIc != null) {
            gL = (GL)((qp_2)db_22).LV();
            gL.glClientActiveTexture(33985);
            gL.glActiveTexture(33985);
            vo_12.cu(false);
            vo_12.n(db_22);
            gL.glBindTexture(3553, 0);
            gL.glClientActiveTexture(33984);
            gL.glActiveTexture(33984);
        }
        if (this.aId != null) {
            gL = (GL)((qp_2)db_22).LV();
            gL.glClientActiveTexture(33986);
            gL.glActiveTexture(33986);
            vo_12.cu(false);
            vo_12.n(db_22);
            gL.glBindTexture(3553, 0);
            gL.glClientActiveTexture(33984);
            gL.glActiveTexture(33984);
        }
        this.cwt.b(db_22);
    }

    public static int it() {
        return qL;
    }

    protected void delete() {
        super.delete();
        this.tJ.release();
        if (this.tl != null) {
            this.tl.HF();
            this.tl = null;
        }
        if (this.aIb != null) {
            this.aIb.HF();
            this.aIb = null;
        }
    }

    protected void af() {
        super.af();
        this.tJ = aPb.aYI();
        this.tJ.d(aPb.enf);
        this.tJ.H(0.0f, 0.0f, 0.0f, 0.0f);
        this.GD = false;
    }

    protected void ag() {
        super.ag();
        if (this.tJ != null) {
            this.tJ.release();
        }
        if (this.tl != null) {
            this.tl.HF();
            this.tl = null;
        }
        if (this.aIb != null) {
            this.aIb.HF();
            this.aIb = null;
        }
    }

    public void HE() {
        super.HE();
    }

    public void HF() {
        super.HF();
    }

    private void a(StackTraceElement[] stackTraceElementArray, HashMap hashMap) {
        StringBuilder stringBuilder = new StringBuilder();
        for (StackTraceElement stackTraceElement : stackTraceElementArray) {
            stringBuilder.append("@").append(stackTraceElement.toString()).append("\n");
        }
        String string = stringBuilder.toString().intern();
        Integer n2 = (Integer)hashMap.get(string);
        if (n2 == null) {
            hashMap.put(string, 1);
        } else {
            hashMap.put(string, n2 + 1);
        }
    }
}

