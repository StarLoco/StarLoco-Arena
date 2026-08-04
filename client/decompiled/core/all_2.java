/*
 * Decompiled with CFR 0.152.
 */
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

/*
 * Renamed from all
 */
public class all_2
extends aIz {
    public String[] bT(String string) {
        HashSet<String> hashSet = new HashSet<String>();
        yx_2 yx_22 = null;
        Iterator iterator = this.aVf().iterator();
        while (iterator.hasNext()) {
            String[] stringArray;
            yx_22 = (yx_2)iterator.next();
            if (yx_22 == null || (stringArray = yx_22.bT(string)) == null) continue;
            hashSet.addAll(Arrays.asList(stringArray));
        }
        return hashSet.size() == 0 ? null : hashSet.toArray(new String[hashSet.size()]);
    }
}

