/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class uW
extends os_0 {
    public static uW c(mx_2 mx_22) {
        return mx_22 instanceof uW ? (uW)mx_22 : new uW(mx_22);
    }

    public uW() {
    }

    public uW(mx_2 mx_22) {
        this.a(mx_22);
    }

    public String[] list() {
        if (this.aId()) {
            return ((uW)this.aIg()).list();
        }
        Collection collection = this.aL(true);
        return collection.toArray(new String[collection.size()]);
    }

    public iv_1[] AZ() {
        if (this.aId()) {
            return ((uW)this.aIg()).AZ();
        }
        Collection collection = this.getCollection();
        return collection.toArray(new iv_1[collection.size()]);
    }

    protected Collection getCollection() {
        return this.aL(false);
    }

    protected Collection aL(boolean bl2) {
        List list = this.abv();
        if (list.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        ArrayList arrayList = new ArrayList(list.size() * 2);
        HashSet hashSet = new HashSet(list.size() * 2);
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            Iterator iterator2 = uW.b(iterator).iterator();
            while (iterator2.hasNext()) {
                Object object = iterator2.next();
                if (bl2) {
                    object = object.toString();
                }
                if (!hashSet.add(object)) continue;
                arrayList.add(object);
            }
        }
        return arrayList;
    }

    private static mx_2 b(Iterator iterator) {
        return (mx_2)iterator.next();
    }
}

