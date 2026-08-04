/*
 * Decompiled with CFR 0.152.
 */
import java.util.HashMap;
import java.util.Iterator;

public abstract class xX
implements Iterable {
    private final HashMap azZ = new HashMap();

    protected xX(gp_0 ... gp_0Array) {
        for (gp_0 gp_02 : gp_0Array) {
            if (this.azZ.containsKey(gp_02.ko())) {
                throw new RuntimeException("D\u00e9finition des listes de param\u00e8tres impossibles : liste \u00e0 " + gp_02.ko() + " param\u00e8tres d\u00e9j\u00e0 d\u00e9finie");
            }
            this.azZ.put(gp_02.ko(), gp_02);
        }
    }

    public gp_0 eq(int n2) {
        return (gp_0)this.azZ.get(n2);
    }

    public final Iterator iterator() {
        return this.azZ.values().iterator();
    }

    public final boolean er(int n2) {
        if (n2 > 0) {
            return this.azZ.containsKey(n2);
        }
        return this.azZ.isEmpty() || this.azZ.containsKey(0);
    }

    public int size() {
        return this.azZ.size();
    }

    public abstract boolean aY(int var1);
}

