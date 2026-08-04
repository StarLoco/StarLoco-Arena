/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import java.util.Collections;
import org.apache.log4j.Logger;

/*
 * Renamed from wh
 */
abstract class wh_1 {
    private static final Logger a = Logger.getLogger(yk_2.class);
    private final ArrayList aul = new ArrayList();
    private final hq_1 aum;
    protected final asz aun = new asz();

    protected wh_1(hq_1 hq_12) {
        this.aum = hq_12;
    }

    public final Object ea(int n2) {
        return this.aun.get(n2);
    }

    public final void put(int n2, Object object) {
        this.aun.put(n2, object);
    }

    public void clear() {
        this.aun.clear();
    }

    public final void a(float f, float f2, int n2, ArrayList arrayList) {
        Object object;
        int n3;
        int n4;
        int n5 = this.aun.size() - n2;
        if (n5 <= 0) {
            return;
        }
        this.aum.h(f, f2);
        this.aul.clear();
        for (n4 = this.aun.size() - 1; n4 >= 0; --n4) {
            n3 = this.aun.hL(n4);
            object = this.aun.jx(n4);
            this.aul.add(new aph_0(n3, object, null));
        }
        Collections.sort(this.aul, this.aum);
        for (n4 = 0; n4 < n5; ++n4) {
            n3 = ((aph_0)this.aul.get((int)n4)).cSq;
            object = ((aph_0)this.aul.get((int)n4)).eoH;
            assert (object == this.aun.get(n3));
            if (arrayList.contains(object)) continue;
            this.aun.remove(n3);
            this.K(object);
        }
    }

    protected abstract void K(Object var1);
}

