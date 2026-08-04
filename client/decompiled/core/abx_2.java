/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from abX
 */
public abstract class abx_2
extends Ts {
    Object cjc;

    protected abx_2(aqw aqw2) {
        super(aqw2);
    }

    protected abx_2(aos aos2) {
        super(aos2);
    }

    protected abstract Object getValue();

    public final Object get() {
        if (this.cjc == null) {
            this.cjc = this.getValue();
        }
        return this.cjc;
    }

    public void update(int n2) {
        this.cjc = null;
    }
}

