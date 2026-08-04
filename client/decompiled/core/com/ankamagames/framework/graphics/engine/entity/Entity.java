/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.framework.graphics.engine.entity;

import com.ankamagames.framework.graphics.engine.transformer.BatchTransformer;
import com.ankamagames.framework.kernel.core.maths.Matrix44;
import java.util.ArrayList;

public abstract class Entity
extends ams_2 {
    private static final int dPu = 2;
    public static final int dPv = 0;
    public static final int dPw = 1;
    public long dPx;
    public float dPy;
    public float dPz;
    public float dPA;
    public float cpB;
    public float bsF;
    public int dPB;
    public int EN;
    public int EP;
    public int EO;
    public int EQ;
    public final QI dPC;
    public static boolean dPD = true;
    protected int rv;
    protected ub_0 cws;
    protected ub_0 cwt;
    protected asr_0 cwp;
    protected int cwq = -180157682;
    protected final ArrayList cwr;
    protected static asr_0 dPE = null;
    private Entity dPF = null;
    protected final ArrayList dPG;
    private boolean chi = true;
    private boolean coB = false;
    private BatchTransformer dPH = (BatchTransformer)yW.FL().a(BatchTransformer.it(), BatchTransformer.class);
    private static final ub_0 dPI = new ub_0();
    private static final ub_0 dPJ = new ub_0();

    public Entity() {
        this.dPG = new ArrayList(2);
        this.cws = dPI;
        this.cwt = dPJ;
        this.EN = Integer.MAX_VALUE;
        this.EP = Integer.MIN_VALUE;
        this.EO = Integer.MAX_VALUE;
        this.EQ = Integer.MIN_VALUE;
        this.cwr = new ArrayList(4);
        this.dPC = new QI("DEFAULT_EFFECT", mr_0.JP);
        this.cwr.add(this.dPC);
        this.dPB = 0;
        this.dPy = Float.MIN_VALUE;
        this.dPz = Float.MIN_VALUE;
        this.dPA = Float.MIN_VALUE;
    }

    public void G(byte by) {
        this.cpB = by;
    }

    public boolean isVisible() {
        return this.chi;
    }

    public final void setVisible(boolean bl2) {
        this.chi = bl2;
    }

    public final boolean aUJ() {
        return this.coB;
    }

    public final void fd(boolean bl2) {
        this.coB = bl2;
    }

    public final void g(boolean bl2, boolean bl3) {
        this.setVisible(bl2);
        if (!bl3) {
            return;
        }
        for (Entity entity : this.dPG) {
            entity.g(bl2, bl3);
        }
    }

    public final ArrayList aUK() {
        return this.dPG;
    }

    public final void i(Entity entity) {
        this.a(this.dPG.size(), entity);
    }

    public final void a(int n2, Entity entity) {
        assert (entity != null) : "It's forbidden to add a null value as a child";
        entity.k(this);
        this.dPG.add(n2, entity);
        this.dPH.a(n2, entity.aUM());
    }

    public final void j(Entity entity) {
        assert (entity != null) : "null value can't be removed from childList";
        entity.k(null);
        this.dPG.remove(entity);
        this.dPH.b(entity.aUM());
    }

    public final void aC(int n2) {
        assert (n2 >= 0 && n2 < this.dPG.size()) : "Index is out of bound";
        this.j((Entity)this.dPG.get(n2));
    }

    public final void removeAllChildren() {
        for (int j = this.dPG.size() - 1; j >= 0; --j) {
            ((Entity)this.dPG.get(j)).k(null);
        }
        this.dPG.clear();
        this.dPH.removeAllChildren();
    }

    public final Entity aUL() {
        return this.dPF;
    }

    public final BatchTransformer aUM() {
        return this.dPH;
    }

    public final int getType() {
        return this.rv;
    }

    public void a(db_2 db_22) {
        if (!dPD || !this.chi || this.coB) {
            return;
        }
        if (this.avb() < 0) {
            return;
        }
        if (this.cwp != null) {
            dPE = this.cwp;
            this.cwp.mg(this.cwq);
            int n2 = this.cwr.size();
            for (int j = 0; j < n2; ++j) {
                this.cwp.a((QI)this.cwr.get(j));
            }
            this.cwp.a(db_22, this);
        } else {
            if (dPE != null) {
                dPE.reset();
                dPE = null;
            }
            this.d(db_22);
        }
    }

    public abstract void a(float var1);

    public abstract void d(db_2 var1);

    public final Matrix44 ki() {
        return this.aUM().ki();
    }

    public final void a(ub_0 ub_02) {
        this.cws = ub_02;
    }

    public final ub_0 aUN() {
        return this.cws;
    }

    public final void b(ub_0 ub_02) {
        this.cwt = ub_02;
    }

    public final ub_0 aUO() {
        return this.cwt;
    }

    public final asr_0 aUP() {
        return this.cwp;
    }

    public final void b(asr_0 asr_02) {
        this.cwp = asr_02;
        if (asr_02 != null) {
            this.aUS();
        }
    }

    public final ArrayList aUQ() {
        return this.cwr;
    }

    public final void d(QI qI) {
        assert (qI != null) : "It's forbidden to set a null variable";
        this.cwr.add(qI);
    }

    public final int aUR() {
        return this.cwq;
    }

    public final void oM(int n2) {
        this.cwq = n2;
    }

    public final void at(float f) {
        this.dPC.bHy[0] = f;
    }

    protected final boolean aFO() {
        return this.cwp == null || this.cwp.aFO();
    }

    protected void delete() {
        super.delete();
        this.cws = null;
        this.cwt = null;
        if (this.dPF != null) {
            this.dPF.j(this);
            this.dPF = null;
        }
        this.dPG.clear();
        this.cwp = null;
        this.cwq = -180157682;
        this.aUS();
        this.dPH.HF();
    }

    protected void af() {
        this.cws = dPI;
        this.cwt = dPJ;
        this.chi = true;
        this.coB = false;
        this.dPy = Float.MIN_VALUE;
        this.dPz = Float.MIN_VALUE;
        this.dPA = Float.MIN_VALUE;
    }

    protected void ag() {
        if (this.dPF != null) {
            this.dPF.j(this);
            this.dPF = null;
        }
        this.dPH.clear();
        this.dPG.clear();
        this.cwp = null;
        this.cwq = -180157682;
        this.aUS();
        this.cws = null;
        this.cwt = null;
        this.dPB = 0;
    }

    private void aUS() {
        this.cwr.clear();
        this.cwr.add(this.dPC);
        this.dPC.bHy[1] = 0.0f;
    }

    private void k(Entity entity) {
        this.dPF = entity;
    }
}

