/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aoW
 */
public final class aow_0
extends jy_2 {
    public final Object value;

    public aow_0(lc_0 lc_02, Object object) {
        super(lc_02);
        if (!(object instanceof Integer || object instanceof Long || object instanceof Float || object instanceof Double || object instanceof String || object instanceof Character || object instanceof Boolean || object instanceof Short || object instanceof Byte || object == null)) {
            throw new IllegalArgumentException(object.getClass().getName());
        }
        this.value = object;
    }

    public String toString() {
        return ahr_1.aA(this.value);
    }

    public void a(Ax ax) {
        ax.e(this);
    }

    public void a(EO eO) {
        eO.e(this);
    }
}

