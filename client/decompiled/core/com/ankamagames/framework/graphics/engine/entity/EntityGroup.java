/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.framework.graphics.engine.entity;

import com.ankamagames.framework.graphics.engine.entity.Entity;

public class EntityGroup
extends Entity {
    private static final int qL = EntityGroup.L(EntityGroup.class);

    public void a(float f) {
        int n2 = this.dPG.size();
        for (int j = 0; j < n2; ++j) {
            ((Entity)this.dPG.get(j)).a(f);
        }
    }

    public void a(db_2 db_22) {
        if (!dPD || !this.isVisible() || this.aUJ()) {
            return;
        }
        this.d(db_22);
    }

    public void d(db_2 db_22) {
        db_22.b(this.aUM().ki());
        this.cws.b(db_22);
        int n2 = this.dPG.size();
        for (int j = 0; j < n2; ++j) {
            ((Entity)this.dPG.get(j)).a(db_22);
        }
        this.cwt.b(db_22);
    }

    public static int it() {
        return qL;
    }

    protected void delete() {
        super.delete();
    }

    protected void af() {
        super.af();
    }

    protected void ag() {
        super.ag();
    }
}

