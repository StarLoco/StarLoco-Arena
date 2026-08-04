/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.baseImpl.graphics.alea.display;

import com.ankamagames.framework.graphics.engine.entity.Entity;
import java.util.ArrayList;

public class RenderTree
extends ams_2
implements Ir {
    private ArrayList cHg = new ArrayList(0);
    private ArrayList crY = new ArrayList(0);
    private RenderTree cHh;
    private RenderTree cHi;
    private Entity crU;
    private boolean cHj;
    private float cHk;
    private float cHl;
    private final ArrayList csb = new ArrayList();
    private static final int qL = RenderTree.L(RenderTree.class);
    private static int cHm = 1000;
    private static final boolean DEBUG = true;

    public void clear() {
        this.csb.clear();
        if (this.cHh != null) {
            this.cHh.HF();
            this.cHh = null;
        }
        if (this.cHi != null) {
            this.cHi.HF();
            this.cHi = null;
        }
        this.cHg.clear();
        this.crY.clear();
        this.crU = null;
    }

    public void a(Entity entity, int n2) {
        if (this.crU == null) {
            this.crU = entity;
            this.cHj = this.f(this.crU);
            this.cHk = this.crU.dPy;
            this.cHl = this.crU.dPz;
            this.cHk = Float.MAX_VALUE;
            this.cHl = Float.MAX_VALUE;
            return;
        }
        if (!this.cHj) {
            if (this.cHi == null) {
                if (!this.f(entity)) {
                    this.crY.add(entity);
                } else {
                    if (this.cHi == null) {
                        this.cHi = (RenderTree)yW.FL().a(RenderTree.it(), RenderTree.class);
                    }
                    this.cHi.a(entity, n2 + 1);
                }
            } else {
                this.cHi.a(entity, n2 + 1);
            }
            return;
        }
        if (this.g(entity)) {
            if (this.cHh == null) {
                this.cHh = (RenderTree)yW.FL().a(RenderTree.it(), RenderTree.class);
            }
            this.cHh.a(entity, n2 + 1);
        } else {
            if (this.cHi == null) {
                this.cHi = (RenderTree)yW.FL().a(RenderTree.it(), RenderTree.class);
            }
            this.cHi.a(entity, n2 + 1);
        }
    }

    public final void b(ArrayList arrayList, int n2) {
        int n3;
        for (n3 = 0; n3 < this.cHg.size(); ++n3) {
            arrayList.add(this.cHg.get(n3));
        }
        if (this.cHh != null) {
            this.cHh.b(arrayList, n2);
        }
        if (this.crU != null) {
            arrayList.add(this.crU);
        }
        for (n3 = 0; n3 < this.crY.size(); ++n3) {
            arrayList.add(this.crY.get(n3));
        }
        if (n2 > cHm) {
            return;
        }
        if (this.cHi != null) {
            this.cHi.b(arrayList, n2 + 1);
        }
    }

    public void ar(int n2, int n3) {
    }

    public void c(Entity entity) {
        this.csb.add(entity);
    }

    public void a(db_2 db_22) {
        this.q(db_22);
        int n2 = this.csb.size();
        for (int j = 0; j < n2; ++j) {
            Entity entity = (Entity)this.csb.get(j);
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

    private void q(db_2 db_22) {
        int n2;
        int n3 = this.cHg.size();
        for (n2 = 0; n2 < n3; ++n2) {
            ((Entity)this.cHg.get(n2)).a(db_22);
        }
        if (this.cHh != null) {
            this.cHh.q(db_22);
        }
        if (this.crU != null) {
            this.crU.a(db_22);
        }
        n2 = this.crY.size();
        for (int j = 0; j < n2; ++j) {
            ((Entity)this.crY.get(j)).a(db_22);
        }
        if (this.cHi != null) {
            this.cHi.q(db_22);
        }
    }

    private boolean f(Entity entity) {
        return entity.cpB > 1.0f;
    }

    private boolean g(Entity entity) {
        if (entity.dPy < this.crU.dPy) {
            return true;
        }
        return entity.dPz < this.crU.dPz;
    }

    private boolean a(Entity entity, boolean bl2) {
        float f = entity.dPy;
        float f2 = entity.dPz;
        float f3 = this.crU.dPy;
        float f4 = this.crU.dPz;
        float f5 = this.crU.cpB;
        if (!bl2) {
            float f6;
            if (f == f3 && f2 == f4) {
                float f7 = entity.dPA;
                float f8 = this.crU.dPA;
                if (f7 > f8 + this.crU.bsF) {
                    this.cHk = f - f5;
                    return true;
                }
                return false;
            }
            if (f > f3 - 1.0f && f < this.cHk + f5 && f2 > f4 - f5 && f2 < this.cHl + f5) {
                float f9 = entity.dPA;
                float f10 = f9 + entity.bsF;
                float f11 = this.crU.dPA;
                if (f10 <= f11) {
                    return false;
                }
                if (f9 > f11 + this.crU.bsF) {
                    return true;
                }
                this.cHk = f - f5;
                return true;
            }
            if (f < f3 && f2 + (f6 = f3 - f) < f4) {
                return false;
            }
        }
        return !(f < f3) || !(f2 < f4 + f5);
    }
}

