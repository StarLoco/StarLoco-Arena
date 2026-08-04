/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.framework.graphics.engine.entity;

import com.ankamagames.framework.graphics.engine.entity.Entity;
import com.ankamagames.framework.graphics.engine.geometry.Geometry;
import java.util.ArrayList;

public class Entity3D
extends Entity {
    public static final int TYPE = 1;
    protected ArrayList cSl;
    private static final int qL = Entity3D.L(Entity3D.class);

    public Entity3D() {
        this.rv = 1;
        this.cSl = new ArrayList(2);
    }

    public void clear() {
        int n2 = this.cSl.size();
        for (int j = 0; j < n2; ++j) {
            ((agb_0)this.cSl.get(j)).destroy();
        }
        this.cSl.clear();
    }

    public final int aFz() {
        return this.cSl.size();
    }

    public final int JZ() {
        int n2 = 0;
        for (agb_0 agb_02 : this.cSl) {
            if (agb_02.jI() == null) continue;
            ++n2;
        }
        return n2;
    }

    public final int b(Geometry geometry) {
        this.cSl.add(new agb_0(this, geometry, null, null));
        return this.cSl.size() - 1;
    }

    public final int f(ef_1 ef_12) {
        this.cSl.add(new agb_0(this, null, ef_12, null));
        return this.cSl.size() - 1;
    }

    public final int a(Geometry geometry, ef_1 ef_12, aPb aPb2) {
        this.cSl.add(new agb_0(this, geometry, ef_12, aPb2));
        return this.cSl.size() - 1;
    }

    public final void c(Geometry geometry) {
        int n2 = this.cSl.size();
        for (int j = 0; j < n2; ++j) {
            agb_0 agb_02 = (agb_0)this.cSl.get(j);
            if (agb_02.aSk() != geometry) continue;
            agb_02.destroy();
            this.cSl.remove(agb_02);
            break;
        }
    }

    public final void a(int n2, Geometry geometry) {
        assert (n2 < this.cSl.size());
        ((agb_0)this.cSl.get(n2)).d(geometry);
    }

    public final Geometry ma(int n2) {
        return ((agb_0)this.cSl.get(n2)).aSk();
    }

    public final void a(int n2, ef_1 ef_12) {
        assert (n2 < this.cSl.size());
        ((agb_0)this.cSl.get(n2)).setTexture(ef_12);
    }

    public final ef_1 ln(int n2) {
        return ((agb_0)this.cSl.get(n2)).jI();
    }

    public final void a(int n2, aPb aPb2) {
        assert (n2 < this.cSl.size());
        ((agb_0)this.cSl.get(n2)).setMaterial(aPb2);
    }

    public final void c(aPb aPb2) {
        int n2 = this.cSl.size();
        for (int j = 0; j < n2; ++j) {
            agb_0 agb_02 = (agb_0)this.cSl.get(j);
            if (agb_02.getMaterial() != aPb2) continue;
            agb_02.bj(true);
        }
    }

    public final aPb mb(int n2) {
        return ((agb_0)this.cSl.get(n2)).getMaterial();
    }

    public final void setColor(float f, float f2, float f3, float f4) {
        for (agb_0 agb_02 : this.cSl) {
            Geometry geometry = agb_02.aSk();
            if (geometry == null) continue;
            geometry.setColor(f, f2, f3, f4);
        }
    }

    public void a(float f) {
    }

    public void d(db_2 db_22) {
        if (this.aFz() == 0) {
            return;
        }
        db_22.b(this.aUM().ki());
        boolean bl2 = this.aFO();
        this.cws.b(db_22);
        int n2 = this.cSl.size();
        for (int j = 0; j < n2; ++j) {
            ((agb_0)this.cSl.get(j)).a(db_22, bl2);
        }
        this.cwt.b(db_22);
    }

    public static int it() {
        return qL;
    }

    protected void delete() {
        super.delete();
        this.clear();
        this.cSl = null;
    }

    public void af() {
        super.af();
    }

    protected void ag() {
        super.ag();
        this.clear();
        this.cSl.trimToSize();
    }
}

