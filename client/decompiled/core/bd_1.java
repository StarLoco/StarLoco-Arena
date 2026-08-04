/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.log4j.Logger;

/*
 * Renamed from BD
 */
public class bd_1
extends yu_2
implements aht_2 {
    protected static final Logger a = Logger.getLogger(bd_1.class);
    private static final bd_1 aIY = new bd_1();
    protected final ConcurrentHashMap aIZ;
    protected final List aJa;
    private final List aJb = new ArrayList();
    private final ArrayList aJc;
    private final ArrayList aJd;
    private final ArrayList aJe;
    private final ArrayList aJf;

    private bd_1() {
        this.aIZ = new ConcurrentHashMap();
        this.aJa = new ArrayList();
        this.aJc = new ArrayList();
        this.aJd = new ArrayList();
        this.aJe = new ArrayList();
        this.aJf = new ArrayList();
    }

    public static bd_1 Is() {
        return aIY;
    }

    protected Collection It() {
        return this.aIZ.values();
    }

    public void g(mT mT2) {
        ArrayList arrayList = mT2.rH();
        if (arrayList != null) {
            int n2 = arrayList.size();
            for (int j = 0; j < n2; ++j) {
                mT mT3 = (mT)arrayList.get(j);
                if (!mT3.rA()) continue;
                this.g(mT3);
            }
        }
        this.aIZ.put(mT2.getId(), mT2);
        this.aJa.remove(mT2);
        this.h(mT2);
    }

    private void h(mT mT2) {
        int n2 = this.aJe.size();
        for (int j = 0; j < n2; ++j) {
            ((aoh_1)this.aJe.get(j)).a(mT2);
        }
    }

    public mT ba(long l2) {
        mT mT2 = (mT)this.aIZ.remove(l2);
        if (mT2 != null) {
            this.aJa.add(mT2);
            this.i(mT2);
        }
        return mT2;
    }

    private void i(mT mT2) {
        int n2 = this.aJf.size();
        for (int j = 0; j < n2; ++j) {
            ((adx_1)this.aJf.get(j)).n(mT2);
        }
    }

    public mT j(mT mT2) {
        return this.ba(mT2.getId());
    }

    public mT k(mT mT2) {
        ArrayList arrayList = mT2.rH();
        if (arrayList != null) {
            int n2 = arrayList.size();
            for (int j = 0; j < n2; ++j) {
                mT mT3 = (mT)arrayList.get(j);
                if (!mT3.rA()) continue;
                this.k(mT3);
            }
        }
        mT mT4 = (mT)this.aIZ.remove(mT2.getId());
        this.i(mT2);
        return mT4;
    }

    public void Iu() {
        for (mT mT2 : this.aIZ.values()) {
            this.i(mT2);
        }
        for (mT mT2 : this.aJa) {
            this.i(mT2);
        }
        for (mT mT2 : this.aIZ.values()) {
            mT2.dispose();
            mT2.getMaterial().release();
        }
        this.aIZ.clear();
        for (mT mT2 : this.aJa) {
            mT2.dispose();
            mT2.getMaterial().release();
        }
        this.aJa.clear();
        this.atf.clear();
        this.aJb.clear();
        this.aJe.clear();
        this.aJe.addAll(this.aJc);
        this.aJf.clear();
        this.aJf.addAll(this.aJd);
    }

    public mT bb(long l2) {
        if (this.aIZ.containsKey(l2)) {
            return (mT)this.aIZ.get(l2);
        }
        return null;
    }

    public Collection Iv() {
        return this.aIZ.values();
    }

    public int Iw() {
        return this.aIZ.size();
    }

    public void Ix() {
        this.aJa.addAll(this.aIZ.values());
        for (int j = this.aJa.size(); j <= 0; --j) {
            ((mT)this.aJa.get(j)).aTt();
        }
    }

    public void a(qs_2 qs_22, int n2) {
        int n3;
        int n4;
        int n5 = this.aJa.size();
        for (n4 = 0; n4 < n5; ++n4) {
            mT mT2 = (mT)this.aJa.get(n4);
            if (mT2.rD()) {
                mT2.rC().rF();
            }
            if (mT2.rE()) {
                mT2.rF();
            }
            if (mT2.rH() != null) {
                mT2.rG();
            }
            if (mT2.rI() != null) {
                mT2.rI().f(mT2);
            }
            if (this.atf.contains(mT2)) {
                this.atf.remove(mT2);
            }
            mT2.dispose();
            mT2.getMaterial().release();
        }
        this.aJa.clear();
        this.Iy();
        n4 = this.aJb.size();
        for (n3 = 0; n3 < n4; ++n3) {
            ((mT)this.aJb.get(n3)).a(qs_22, n2);
        }
        for (n3 = 0; n3 < n4; ++n3) {
            mT mT3 = (mT)this.aJb.get(n3);
            mT3.b(qs_22, n2);
        }
    }

    private List Iy() {
        Collection collection = this.aIZ.values();
        this.aJb.clear();
        for (mT mT2 : collection) {
            int n2 = -1;
            mT mT3 = mT2.rC();
            if (mT3 != null && (n2 = this.aJb.indexOf(mT3)) != -1) {
                this.aJb.add(n2 + 1, mT2);
                continue;
            }
            mT mT4 = mT2.rB();
            if (mT4 != null && (n2 = this.aJb.indexOf(mT4)) != -1) {
                this.aJb.add(n2, mT2);
                continue;
            }
            mT mT5 = mT2.rI();
            if (mT5 != null && !this.aJb.contains(mT2) && (n2 = this.aJb.indexOf(mT5)) != -1) {
                this.aJb.add(n2 + 1, mT2);
                continue;
            }
            this.aJb.add(mT2);
            ArrayList arrayList = mT2.rH();
            if (arrayList == null) continue;
            int n3 = arrayList.size();
            for (int j = 0; j < n3; ++j) {
                mT mT6 = (mT)arrayList.get(j);
                if (!this.aJb.contains(mT6)) continue;
                this.aJb.remove(mT6);
                this.aJb.add(mT6);
            }
        }
        return this.aJb;
    }

    public void a(qs_2 qs_22, float f, float f2) {
        this.atf.clear();
        for (int j = 0; j < this.aJb.size(); ++j) {
            mT mT2 = (mT)this.aJb.get(j);
            if (mT2.a(qs_22)) {
                mT2.eY(false);
                this.a(mT2, null, qs_22, f, f2);
                this.atf.add(mT2);
                continue;
            }
            mT2.eY(true);
        }
    }

    public void a(aoh_1 aoh_12) {
        if (!this.aJc.contains(aoh_12)) {
            this.aJc.add(aoh_12);
        }
    }

    public void a(adx_1 adx_12) {
        if (!this.aJd.contains(adx_12)) {
            this.aJd.add(adx_12);
        }
    }

    public void b(aoh_1 aoh_12) {
        if (!this.aJe.contains(aoh_12)) {
            this.aJe.add(aoh_12);
        }
    }

    public void c(aoh_1 aoh_12) {
        this.aJe.remove(aoh_12);
    }

    public void b(adx_1 adx_12) {
        if (!this.aJf.contains(adx_12)) {
            this.aJf.add(adx_12);
        }
    }

    public void c(adx_1 adx_12) {
        this.aJf.remove(adx_12);
    }
}

