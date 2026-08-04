/*
 * Decompiled with CFR 0.152.
 */
import java.security.PrivilegedAction;
import java.util.Map;

/*
 * Renamed from JK
 */
class jk_0
implements PrivilegedAction {
    private final Map bmf;
    private final avj_0 bmg;

    jk_0(avj_0 avj_02, Map map) {
        this.bmg = avj_02;
        this.bmf = map;
    }

    public Object run() {
        return new aed_0(this.bmf, avj_0.b(this.bmg));
    }
}

