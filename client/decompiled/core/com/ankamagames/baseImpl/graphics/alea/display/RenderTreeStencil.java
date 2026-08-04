/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
package com.ankamagames.baseImpl.graphics.alea.display;

import com.ankamagames.framework.graphics.engine.entity.Entity;
import java.util.ArrayList;
import javax.media.opengl.GL;
import org.apache.log4j.Logger;

public class RenderTreeStencil
extends ams_2
implements Ir {
    protected static Logger a = Logger.getLogger(RenderTreeStencil.class);
    private Entity crU;
    private ArrayList crV = new ArrayList(1);
    private ArrayList crW = new ArrayList(1);
    private ArrayList crX = new ArrayList(1);
    private ArrayList crY = new ArrayList();
    private static short crZ = 1;
    private RenderTreeStencil csa;
    private static int aNV;
    private static int aNW;
    private static final ArrayList csb;
    private static final int qL;

    public void clear() {
        csb.clear();
        this.avv();
    }

    public void ar(int n2, int n3) {
        aNV = n2;
        aNW = n3;
    }

    public void a(Entity entity, int n2) {
        if (this.crU == null) {
            this.crU = entity;
            return;
        }
        RenderTreeStencil renderTreeStencil = (RenderTreeStencil)yW.FL().a(qL, RenderTreeStencil.class);
        renderTreeStencil.crU = entity;
        this.b(renderTreeStencil);
    }

    public void c(Entity entity) {
        csb.add(entity);
    }

    public void a(db_2 db_22) {
        vo_1 vo_12 = vo_1.aik();
        vo_12.cv(false);
        vo_12.n(db_22);
        db_22.vl();
        this.q(db_22);
        int n2 = csb.size();
        for (int j = 0; j < n2; ++j) {
            Entity entity = (Entity)csb.get(j);
            entity.a(db_22);
        }
    }

    public static int it() {
        return qL;
    }

    protected void af() {
    }

    protected void ag() {
        this.clear();
    }

    private void avv() {
        this.crV.clear();
        this.crW.clear();
        this.crX.clear();
        this.crY.clear();
        this.crU = null;
        if (this.csa != null) {
            this.csa.avv();
            this.csa.HF();
            this.csa = null;
        }
        crZ = (short)254;
    }

    private void q(db_2 db_22) {
        RenderTreeStencil renderTreeStencil;
        int n2;
        int n3;
        if (this.crU == null) {
            return;
        }
        vo_1 vo_12 = vo_1.aik();
        qp_2 qp_22 = (qp_2)db_22;
        GL gL = (GL)qp_22.LV();
        int n4 = this.crV.size();
        if (n4 != 0) {
            vo_12.ir(-2);
            vo_12.cx(true);
            vo_12.n(db_22);
            gL.glStencilOp(7681, 7680, 7680);
            gL.glStencilFunc(512, 2, -1);
            gL.glColorMask(false, false, false, false);
            gL.glFlush();
            for (n3 = 0; n3 < n4; ++n3) {
                RenderTreeStencil renderTreeStencil2 = (RenderTreeStencil)this.crV.get(n3);
                renderTreeStencil2.crU.a(db_22);
                n2 = renderTreeStencil2.crX.size();
                for (int j = 0; j < n2; ++j) {
                    renderTreeStencil = (RenderTreeStencil)renderTreeStencil2.crX.get(j);
                    renderTreeStencil.crU.a(db_22);
                }
            }
            vo_12.ir(0);
            vo_12.n(db_22);
            gL.glStencilFunc(517, 2, -1);
            gL.glStencilOp(7680, 7680, 7680);
            gL.glColorMask(true, true, true, true);
            this.crU.a(db_22);
            vo_12.cx(false);
            vo_12.ir(0);
            vo_12.n(db_22);
        } else {
            n3 = this.crW.size();
            if (n3 != 0) {
                this.crU.a(db_22);
                int n5 = this.crX.size();
                vo_12.ir(-2);
                vo_12.cx(true);
                vo_12.n(db_22);
                gL.glStencilOp(7681, 7680, 7680);
                gL.glStencilFunc(512, crZ, -1);
                gL.glColorMask(false, false, false, false);
                gL.glFlush();
                this.crU.a(db_22);
                for (n2 = 0; n2 < n5; ++n2) {
                    ((RenderTreeStencil)this.crX.get((int)n2)).crU.a(db_22);
                }
                gL.glStencilFunc(512, 0, -1);
                for (n2 = 0; n2 < n3; ++n2) {
                    int n6;
                    int n7 = -1;
                    renderTreeStencil = (RenderTreeStencil)this.crW.get(n2);
                    int n8 = renderTreeStencil.crV.size();
                    for (n6 = 0; n6 < n8; ++n6) {
                        if (renderTreeStencil.crV.get(n6) != this) continue;
                        n7 = n6 + 1;
                        break;
                    }
                    if (n7 == -1) continue;
                    for (n6 = n7; n6 < n8; ++n6) {
                        RenderTreeStencil renderTreeStencil3 = (RenderTreeStencil)renderTreeStencil.crV.get(n6);
                        renderTreeStencil3.crU.a(db_22);
                        int n9 = renderTreeStencil3.crX.size();
                        for (int j = 0; j < n9; ++j) {
                            ((RenderTreeStencil)renderTreeStencil3.crX.get((int)j)).crU.a(db_22);
                        }
                    }
                }
                gL.glStencilFunc(514, crZ, -2);
                gL.glStencilOp(7680, 7680, 7680);
                gL.glColorMask(true, true, true, true);
                vo_12.ir(0);
                vo_12.n(db_22);
                for (n2 = 0; n2 < n3; ++n2) {
                    RenderTreeStencil renderTreeStencil4 = (RenderTreeStencil)this.crW.get(n2);
                    renderTreeStencil4.crU.a(db_22);
                }
                if ((crZ = (short)(crZ - 2)) <= 2) {
                    crZ = (short)254;
                }
                vo_12.cx(false);
                vo_12.n(db_22);
            } else {
                this.crU.a(db_22);
            }
        }
        n3 = this.crY.size();
        for (int j = 0; j < n3; ++j) {
            ((Entity)this.crY.get(j)).a(db_22);
        }
        if (this.csa != null) {
            this.csa.q(db_22);
        }
    }

    private void b(RenderTreeStencil renderTreeStencil) {
        Entity entity = renderTreeStencil.crU;
        if (this.crU.cpB > 1.0f && !(entity.cpB > 1.0f) && this.a(entity, this.crU, this.crU.cpB) && this.d(entity)) {
            int n2 = this.crV.size();
            for (int j = 0; j < n2; ++j) {
                RenderTreeStencil renderTreeStencil2 = (RenderTreeStencil)this.crV.get(j);
                if (renderTreeStencil2.crU.dPy != entity.dPy || renderTreeStencil2.crU.dPz != entity.dPz) continue;
                renderTreeStencil.crX.addAll(renderTreeStencil2.crX);
                renderTreeStencil.crX.add(renderTreeStencil2);
                renderTreeStencil2.crX.clear();
                renderTreeStencil2.crW.remove(this);
                this.crV.remove(j);
                break;
            }
            renderTreeStencil.crW.add(this);
            this.crV.add(renderTreeStencil);
        }
        if (this.csa == null) {
            if (renderTreeStencil.crU.cpB > 1.0f || renderTreeStencil.crV.size() != 0 || renderTreeStencil.crW.size() != 0) {
                this.csa = renderTreeStencil;
            } else {
                this.crY.add(renderTreeStencil.crU);
                renderTreeStencil.HF();
            }
        } else {
            this.csa.b(renderTreeStencil);
        }
    }

    private boolean a(Entity entity, Entity entity2, float f) {
        if (Math.abs(entity.dPy - entity2.dPy) >= f) {
            return false;
        }
        return !(Math.abs(entity.dPz - entity2.dPz) >= f);
    }

    private boolean d(Entity entity) {
        if (entity.dPy == this.crU.dPy && entity.dPz == this.crU.dPz) {
            return entity.dPA < this.crU.dPA + this.crU.bsF;
        }
        return entity.dPA + entity.bsF <= this.crU.dPA;
    }

    public Entity getEntity() {
        return this.crU;
    }

    public RenderTreeStencil avw() {
        return this.csa;
    }

    public ArrayList avx() {
        return this.crX;
    }

    public ArrayList avy() {
        return this.crW;
    }

    public ArrayList avz() {
        return this.crV;
    }

    public ArrayList avA() {
        return this.crY;
    }

    static {
        csb = new ArrayList();
        qL = RenderTreeStencil.L(RenderTreeStencil.class);
    }
}

