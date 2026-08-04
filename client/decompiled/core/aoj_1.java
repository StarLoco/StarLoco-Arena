/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.lang.reflect.Array;
import java.util.ArrayList;
import org.apache.log4j.Logger;

/*
 * Renamed from aOj
 */
public final class aoj_1 {
    private lb_0 ebF = null;
    private ArrayList[] ebG = null;
    private static final aoj_1 ebH = new aoj_1();
    private static final Logger a = Logger.getLogger(aoj_1.class);
    private float[] ebI = null;

    private aoj_1() {
    }

    public static aoj_1 aXZ() {
        return ebH;
    }

    public void E(ArrayList arrayList) {
        assert (this.ebF == null) : "The DirectBufferPoolManager is already initialised and can't be initialised twice";
        int n2 = 0;
        for (cq_1 cq_12 : arrayList) {
            assert (cq_12.Lk() >= 0) : "bufferCounts can't be less than zero";
            if (cq_12.Lk() == 0) continue;
            ++n2;
        }
        assert (n2 > 0) : "No group created, all buffer counts are null";
        int n3 = rf.values().length;
        this.ebF = new lb_0(n2 * 2);
        this.ebG = (ArrayList[])Array.newInstance(ArrayList.class, n3);
        for (int j = 0; j < n3; ++j) {
            this.ebG[j] = j == rf.afJ.ordinal() ? null : new ArrayList(n2);
        }
        for (cq_1 cq_13 : arrayList) {
            if (cq_13.Lk() == 0) continue;
            rf rf2 = cq_13.Ll();
            if (rf2 == rf.afJ) {
                this.ebF.c(cq_13.getBufferSize(), new XC(cq_13));
                continue;
            }
            this.ebG[rf2.ordinal()].add(new XC(cq_13));
        }
        a.info((Object)("DirectBufferPoolManager allocates " + this.ali() / 1024 + "KB"));
    }

    public uo_2 pG(int n2) {
        Object object;
        assert (this.ebF != null) : "DirectBufferPoolManager is not initialized";
        XC xC = (XC)this.ebF.get(n2);
        if (xC == null) {
            a.error((Object)("Creating byte buffer pool of size " + n2));
            object = new cq_1();
            ((cq_1)object).fc(1);
            ((cq_1)object).setBufferSize(n2);
            ((cq_1)object).a(rf.afJ);
            this.ebF.c(((cq_1)object).getBufferSize(), new XC((cq_1)object));
            xC = (XC)this.ebF.get(n2);
        }
        object = (uo_2)xC.alg();
        assert (object != null) : "No more free ByteBuffer pools of size " + n2;
        return object;
    }

    public qx pH(int n2) {
        assert (this.ebG != null) : "DirectBufferPoolManager is not initialized";
        assert (this.ebG[rf.afK.ordinal()] != null) : "No ShortBufferPool initialized";
        return (qx)this.a(rf.afK, n2);
    }

    public final zf_1 pI(int n2) {
        assert (this.ebG != null) : "DirectBufferPoolManager is not initialized";
        assert (this.ebG[rf.afL.ordinal()] != null) : "No FloatBufferPool initialized";
        return (zf_1)this.a(rf.afL, n2);
    }

    public final int ali() {
        assert (this.ebF != null || this.ebG != null) : "DirectBufferPoolManager is not initialized";
        int n2 = 0;
        if (this.ebF != null) {
            ArrayList[] arrayListArray = this.ebF.pK();
            while (arrayListArray.hasNext()) {
                arrayListArray.fK();
                n2 += ((XC)arrayListArray.value()).ali();
            }
        }
        if (this.ebG != null) {
            for (ArrayList arrayList : this.ebG) {
                if (arrayList == null) continue;
                for (XC xC : arrayList) {
                    n2 += xC.ali();
                }
            }
        }
        return n2;
    }

    public final void aYa() {
    }

    private aew_0 a(rf rf2, int n2) {
        ArrayList arrayList = null;
        try {
            arrayList = this.ebG[rf2.ordinal()];
        }
        catch (Exception exception) {
            a.error((Object)"DEBUG \u00e9cran noir au lancement", (Throwable)new Exception("stacktrace"));
            return null;
        }
        int n3 = arrayList.size();
        for (int j = 0; j < n3; ++j) {
            XC xC = (XC)arrayList.get(j);
            if (xC.getSize() < n2) continue;
            aew_0 aew_02 = xC.alg();
            if (aew_02 != null) {
                return aew_02;
            }
            a.trace((Object)("No enough FloatBuffer pool of size " + n2));
        }
        assert (false) : "No more free " + (Object)((Object)rf2) + "pools of size " + n2;
        return null;
    }

    public void all() {
        a.warn((Object)"#######################################################");
        a.warn((Object)"##### Pool stats");
        a.warn((Object)"#######################################################");
        ll_0 ll_02 = this.ebF.pK();
        while (ll_02.hasNext()) {
            ll_02.fK();
            ((XC)ll_02.value()).all();
        }
        for (int j = 0; j < this.ebG.length; ++j) {
            if (this.ebG[j] == null) continue;
            for (XC xC : this.ebG[j]) {
                xC.all();
            }
        }
    }
}

