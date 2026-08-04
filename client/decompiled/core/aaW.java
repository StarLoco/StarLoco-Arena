/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.entity.Entity;

public abstract class aaW
extends ahg_2 {
    protected ars_0[] cgV;
    protected boolean cgW;

    public final int apC() {
        return this.cgV.length;
    }

    public final ars_0 jy(int n2) {
        return this.cgV[n2];
    }

    public final ars_0 hg(String string) {
        for (ars_0 ars_02 : this.cgV) {
            if (!ars_02.getName().equals(string)) continue;
            return ars_02;
        }
        return null;
    }

    public final void a(db_2 db_22, Entity entity) {
        for (ars_0 ars_02 : this.cgV) {
            ars_02.a(db_22, entity);
        }
    }

    public final void reset() {
        for (ars_0 ars_02 : this.cgV) {
            ars_02.reset();
        }
    }

    public final boolean apD() {
        return this.cgW;
    }

    public abstract boolean o(db_2 var1);
}

