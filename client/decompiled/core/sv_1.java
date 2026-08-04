/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import org.apache.log4j.Logger;

/*
 * Renamed from Sv
 */
public class sv_1
extends ArrayList {
    protected static final Logger a = Logger.getLogger(sv_1.class);
    private static final int bLp = 10;
    public static final Comparator bLq = new qz_2();
    public static final Comparator bLr = new qy_0();
    public static final Comparator bLs = new qx_0();
    public static final Comparator bLt = new qw_1();
    public static final Comparator bLu = new rg_1();
    private final avA bLv;

    public sv_1(int n2, avA avA2) {
        super(n2);
        this.bLv = avA2;
    }

    public sv_1(int n2, Comparator comparator) {
        super(n2);
        this.bLv = new aon_1(this, comparator, null);
    }

    public sv_1(avA avA2) {
        this(10, avA2);
    }

    public sv_1(Comparator comparator) {
        this(10, comparator);
    }

    public boolean add(Object object) {
        int n2 = this.indexOf(object);
        if (n2 < 0) {
            super.add(-(n2 + 1), object);
        } else {
            super.add(n2, object);
        }
        return true;
    }

    public void add(int n2, Object object) {
        a.warn((Object)"Impossible d'inserer un \u00e9l\u00e9ment \u00e0 un index donn\u00e9.");
        this.add(object);
    }

    public boolean addAll(Collection collection) {
        if (collection instanceof sv_1 && this.size() == 0) {
            super.addAll(collection);
        } else {
            for (Object e : collection) {
                this.add(e);
            }
        }
        return 0 < collection.size();
    }

    public boolean addAll(int n2, Collection collection) {
        a.warn((Object)"Impossible d'inserer des \u00e9l\u00e9ments \u00e0 un index donn\u00e9.");
        return this.addAll(collection);
    }

    private void b(Collection collection) {
        super.addAll(collection);
    }

    public boolean ak(Object object) {
        boolean bl2;
        boolean bl3 = bl2 = this.size() == 0 || this.compare(object, this.get(0)) <= 0;
        if (bl2) {
            super.add(0, object);
        }
        return bl2;
    }

    public boolean al(Object object) {
        boolean bl2;
        int n2 = this.size();
        boolean bl3 = bl2 = n2 == 0 || this.compare(this.get(n2 - 1), object) <= 0;
        if (bl2) {
            super.add(n2, object);
        }
        return bl2;
    }

    public Object clone() {
        sv_1 sv_12 = new sv_1(this.size(), this.bLv);
        sv_12.b(this);
        return sv_12;
    }

    protected final int compare(Object object, Object object2) {
        return this.bLv.compare(object, object2);
    }

    public boolean contains(Object object) {
        return 0 <= this.indexOf(object);
    }

    public boolean containsAll(Collection collection) {
        Iterator iterator = collection.iterator();
        while (iterator.hasNext()) {
            if (this.contains(iterator.next())) continue;
            return false;
        }
        return true;
    }

    public Object getFirst() {
        return this.size() == 0 ? null : this.get(0);
    }

    public Object getLast() {
        int n2 = this.size();
        return n2 == 0 ? null : this.get(--n2);
    }

    public int indexOf(Object object) {
        Object object2 = object;
        int n2 = this.search(object2);
        if (n2 < 0 || this.get(n2).equals(object)) {
            return n2;
        }
        int n3 = n2;
        while (0 <= --n3 && this.compare(this.get(n3), object2) == 0 && !this.get(n3).equals(object)) {
        }
        if (0 <= n3 && this.compare(this.get(n3), object2) == 0 && this.get(n3).equals(object)) {
            return n3;
        }
        int n4 = Math.max(0, n3 + 1);
        n3 = n2;
        int n5 = this.size();
        while (++n3 < n5 && this.compare(this.get(n3), object2) == 0 && !this.get(n3).equals(object)) {
        }
        return n3 < n5 && this.compare(this.get(n3), object2) == 0 && this.get(n3).equals(object) ? n3 : -(n4 + 1);
    }

    public int am(Object object) {
        int n2 = this.indexOf(object);
        if (n2 < 0) {
            return -1;
        }
        Object object2 = object;
        int n3 = this.size();
        while (0 <= --n2 && this.compare(this.get(n2), object2) == 0) {
        }
        while (++n2 < n3 && this.compare(this.get(n2), object2) == 0 && !this.get(n2).equals(object)) {
        }
        return n2 < n3 && this.compare(this.get(n2), object2) == 0 && this.get(n2).equals(object) ? n2 : -1;
    }

    public int lastIndexOf(Object object) {
        int n2 = this.indexOf(object);
        if (n2 < 0) {
            return -1;
        }
        Object object2 = object;
        int n3 = this.size();
        while (++n2 < n3 && this.compare(this.get(n2), object2) == 0) {
        }
        while (0 <= --n2 && this.compare(this.get(n2), object2) == 0 && !this.get(n2).equals(object)) {
        }
        return 0 <= n2 && this.compare(this.get(n2), object2) == 0 && this.get(n2).equals(object) ? n2 : -1;
    }

    public boolean an(Object object) {
        boolean bl2;
        int n2 = super.lastIndexOf(object);
        boolean bl3 = bl2 = 0 <= n2;
        if (bl2) {
            int n3;
            for (n3 = n2 - 1; 0 <= n3 && this.get(n3).equals(object); --n3) {
            }
            super.removeRange(++n3, ++n2);
            int n4 = n2 - n3;
            if (n4 == 1) {
                super.add(-(this.indexOf(object) + 1), object);
            } else {
                ArrayList<Object> arrayList = new ArrayList<Object>(n4 + 1);
                for (int j = 0; j < n4; ++j) {
                    arrayList.add(j, object);
                }
                super.addAll(-(this.indexOf(object) + 1), arrayList);
            }
        }
        return bl2;
    }

    public boolean remove(Object object) {
        boolean bl2;
        int n2 = this.indexOf(object);
        boolean bl3 = bl2 = 0 <= n2;
        if (bl2) {
            super.remove(n2);
        }
        return bl2;
    }

    public boolean removeAll(Collection collection) {
        int n2 = this.size();
        for (Object e : collection) {
            int n3 = this.am(e);
            if (0 > n3) continue;
            super.removeRange(n3, this.lastIndexOf(e) + 1);
        }
        return this.size() < n2;
    }

    public boolean retainAll(Collection collection) {
        int n2 = this.size();
        int n3 = n2 - 1;
        while (0 <= n3) {
            if (collection.contains(this.get(n3))) {
                --n3;
                continue;
            }
            int n4 = n3;
            while (0 <= --n3 && !collection.contains(this.get(n3))) {
            }
            super.removeRange(n3 + 1, n4 + 1);
        }
        return this.size() < n2;
    }

    protected final int search(Object object) {
        return this.bLv.a(this, object);
    }

    public Object set(int n2, Object object) {
        Object e = super.remove(n2);
        super.add(-(this.indexOf(object) + 1), object);
        return e;
    }
}

