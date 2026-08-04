/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.entity.Entity;
import java.awt.Dimension;
import java.awt.Insets;

/*
 * Renamed from apf
 */
public abstract class apf_0
extends bv_1
implements py_1 {
    protected boolean apf;

    public boolean isDirty() {
        return this.apf;
    }

    public abstract xL getMesh();

    public abstract Entity getEntity();

    public void b(Dimension dimension, Insets insets, Insets insets2, Insets insets3) {
        this.getMesh().a(dimension, insets, insets2, insets3);
    }

    public void b() {
        super.b();
        this.apf = false;
    }
}

