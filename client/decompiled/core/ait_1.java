/*
 * Decompiled with CFR 0.152.
 */
import java.util.HashMap;
import java.util.Map;

/*
 * Renamed from aiT
 */
public final class ait_1 {
    private Map czt = new HashMap();
    private static ait_1 czu = new ait_1();

    public static ait_1 ayz() {
        return czu;
    }

    private ait_1() {
    }

    public final void a(String string, cj_2 cj_22) {
        this.czt.put(string, cj_22);
    }

    public final cj_2 in(String string) {
        return (cj_2)this.czt.get(string);
    }

    public final void reset() {
        this.czt.clear();
    }

    public final boolean isEmpty() {
        return this.czt.isEmpty();
    }
}

