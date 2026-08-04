/*
 * Decompiled with CFR 0.152.
 */
import java.util.Collection;
import java.util.Iterator;

/*
 * Renamed from aEg
 */
public class aeg_0
extends mk {
    private final Collection dzE;

    public aeg_0(Collection collection) {
        this.dzE = collection;
    }

    public final any_2 aU(String string) {
        Iterator iterator = this.dzE.iterator();
        while (iterator.hasNext()) {
            mk mk2 = (mk)iterator.next();
            any_2 any_22 = mk2.aU(string);
            if (any_22 == null) continue;
            return any_22;
        }
        return null;
    }
}

