/*
 * Decompiled with CFR 0.152.
 */
import java.util.Collection;
import java.util.HashMap;

/*
 * Renamed from jt
 */
public class jt_0 {
    private static final jt_0 Ay = new jt_0();
    private final HashMap Az = new HashMap();

    public static jt_0 mn() {
        return Ay;
    }

    public boolean a(ko_0 ko_02) {
        int n2 = ko_02.getId();
        if (this.Az.containsKey(n2)) {
            return false;
        }
        this.Az.put(n2, ko_02);
        return true;
    }

    public ko_0 aZ(int n2) {
        return (ko_0)this.Az.get(n2);
    }

    public Collection mo() {
        return this.Az.values();
    }
}

