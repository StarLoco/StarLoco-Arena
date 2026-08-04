/*
 * Decompiled with CFR 0.152.
 */
import java.util.HashMap;
import java.util.Map;

/*
 * Renamed from xo
 */
class xo_2
extends ThreadLocal {
    private xo_2() {
    }

    public Object initialValue() {
        return new HashMap();
    }

    Map DI() {
        return (Map)this.get();
    }

    xo_2(si_1 si_12) {
        this();
    }
}

