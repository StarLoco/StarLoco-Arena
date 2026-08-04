/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

/*
 * Renamed from hJ
 */
class hj_2
extends Hashtable {
    private static final long serialVersionUID = -3060442320477772028L;
    private UI hL;

    hj_2(UI uI) {
        this.hL = uI;
    }

    alv_2 at(String string) {
        return (alv_2)super.get(string);
    }

    public Object get(Object object) {
        return this.av((String)object);
    }

    Object au(String string) {
        alv_2 alv_22 = this.at(string);
        return alv_22 == null ? null : alv_22.j(this.hL);
    }

    Class av(String string) {
        alv_2 alv_22 = this.at(string);
        return alv_22 == null ? null : alv_22.g(this.hL);
    }

    Class aw(String string) {
        alv_2 alv_22 = this.at(string);
        return alv_22 == null ? null : alv_22.f(this.hL);
    }

    public boolean contains(Object object) {
        boolean bl2 = false;
        if (object instanceof Class) {
            Iterator iterator = this.values().iterator();
            while (iterator.hasNext() && !bl2) {
                bl2 = ((alv_2)iterator.next()).f(this.hL) == object;
            }
        }
        return bl2;
    }

    public boolean containsValue(Object object) {
        return this.contains(object);
    }

    public List ax(String string) {
        ArrayList<alv_2> arrayList = new ArrayList<alv_2>();
        Iterator iterator = this.values().iterator();
        while (iterator.hasNext()) {
            alv_2 alv_22 = (alv_2)iterator.next();
            if (!alv_22.getName().startsWith(string)) continue;
            arrayList.add(alv_22);
        }
        return arrayList;
    }
}

