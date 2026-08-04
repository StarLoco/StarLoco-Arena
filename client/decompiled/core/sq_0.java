/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.entity.Entity;
import com.sun.opengl.cg.CGpass;
import com.sun.opengl.cg.CgGL;

/*
 * Renamed from sq
 */
public class sq_0
extends ars_0 {
    private CGpass aja;
    private static CGpass ajb = null;

    public sq_0(CGpass cGpass) {
        this.aja = cGpass;
    }

    public final void a(db_2 db_22, Entity entity) {
        if (ajb != this.aja) {
            this.reset();
            CgGL.cgSetPassState(this.aja);
            ajb = this.aja;
        }
        entity.d(db_22);
    }

    public final void reset() {
        if (ajb == null) {
            return;
        }
        CgGL.cgResetPassState(ajb);
        vo_1.aik().reset();
        ajb = null;
    }
}

