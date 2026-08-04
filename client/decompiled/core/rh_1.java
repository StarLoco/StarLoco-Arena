/*
 * Decompiled with CFR 0.152.
 */
import javax.media.opengl.GL;

/*
 * Renamed from RH
 */
public abstract class rh_1 {
    protected boolean bKr = true;

    protected rh_1() {
    }

    public final boolean hasChanged() {
        return this.bKr;
    }

    final void cm(boolean bl2) {
        this.bKr = bl2;
    }

    public abstract void k(GL var1);

    public abstract gf_1 aeq();
}

