/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import java.util.BitSet;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import org.apache.log4j.Logger;

/*
 * Renamed from alf
 */
public class alf_1
implements Iterable,
m_0 {
    protected static final Logger a = Logger.getLogger(alf_1.class);
    protected final cp_2 cEI = new cp_2();
    private boolean cEJ = true;
    public static final byte cEK = 1;
    public static final byte cEL = 10;
    public static final byte cEM = 2;
    public static final byte cEN = 20;
    public static final byte cEO = 3;
    public static final byte cEP = 30;
    public static final byte cEQ = 4;
    public static final byte cER = 40;
    public static final byte cES = 5;
    public static final byte cET = 6;
    public static final byte cEU = 60;

    public void clear() {
        for (long l2 : this.cEI.eJ()) {
            xb_2 xb_22 = (xb_2)this.cEI.t(l2);
            if (xb_22 == null) continue;
            try {
                xb_22.aK();
            }
            catch (Exception exception) {
                a.error((Object)"Exception levee lors de la d\u00e9spplication d'un effet", (Throwable)exception);
            }
        }
        this.cEI.clear();
    }

    public void pG() {
        akz_0 akz_02 = this.cEI.eI();
        while (akz_02.hasNext()) {
            akz_02.fK();
            ((xb_2)akz_02.value()).release();
        }
        this.cEI.clear();
    }

    public void aAt() {
        this.cEJ = false;
    }

    public void aAu() {
        this.cEJ = true;
    }

    public xb_2 dK(long l2) {
        return (xb_2)this.cEI.t(l2);
    }

    public void o(xb_2 xb_22) {
        if (!this.cEI.v(xb_22.je())) {
            this.cEI.a(xb_22.je(), xb_22);
            xb_22.a(this);
        }
    }

    public boolean p(xb_2 xb_22) {
        return this.dL(xb_22.je());
    }

    public void a(xb_2 ... xb_2Array) {
        if (xb_2Array != null && xb_2Array.length > 0) {
            for (xb_2 xb_22 : xb_2Array) {
                this.p(xb_22);
            }
        }
    }

    public void q(xb_2 xb_22) {
        xb_22.aK();
    }

    public boolean dL(long l2) {
        xb_2 xb_22 = (xb_2)this.cEI.u(l2);
        if (xb_22 != null) {
            this.q(xb_22);
            return true;
        }
        return false;
    }

    public Iterator r(xb_2 xb_22) {
        return tn_2.a(this, this.cEI.eI(), xb_22);
    }

    public Iterable lm(int n2) {
        ArrayList arrayList = new ArrayList();
        if (this.cEI != null && !this.cEI.isEmpty()) {
            this.cEI.a(new adx_2(this, n2, arrayList));
        }
        return arrayList;
    }

    protected age k(kc_2 kc_22) {
        return age.a(this, this.cEI.eI(), kc_22, false);
    }

    public age l(kc_2 kc_22) {
        return age.a(this, this.cEI.eI(), kc_22, true);
    }

    protected cm_0 b(Pi pi) {
        return cm_0.a(this, this.cEI.eI(), pi);
    }

    public void s(xb_2 xb_22) {
        try {
            akz_0 akz_02 = this.cEI.eI();
            while (akz_02.hasNext()) {
                akz_02.fK();
                xb_2 xb_23 = (xb_2)akz_02.value();
                if (xb_23.ajZ() != xb_22) continue;
                akz_02.remove();
                xb_23.aK();
            }
        }
        catch (ConcurrentModificationException concurrentModificationException) {
            this.s(xb_22);
        }
    }

    public void m(kc_2 kc_22) {
        try {
            akz_0 akz_02 = this.cEI.eI();
            while (akz_02.hasNext()) {
                akz_02.fK();
                xb_2 xb_22 = (xb_2)akz_02.value();
                if (xb_22.ajQ() != kc_22 && xb_22.ajR() != kc_22) continue;
                akz_02.remove();
                xb_22.aK();
            }
        }
        catch (ConcurrentModificationException concurrentModificationException) {
            this.m(kc_22);
        }
    }

    public void a(Pi pi, boolean bl2) {
        try {
            akz_0 akz_02 = this.cEI.eI();
            while (akz_02.hasNext()) {
                akz_02.fK();
                xb_2 xb_22 = (xb_2)akz_02.value();
                if (xb_22.mi() != pi) continue;
                akz_02.remove();
                if (!bl2) continue;
                xb_22.aK();
            }
        }
        catch (ConcurrentModificationException concurrentModificationException) {
            this.a(pi, bl2);
        }
    }

    public void h(int n2, boolean bl2) {
        try {
            akz_0 akz_02 = this.cEI.eI();
            while (akz_02.hasNext()) {
                akz_02.fK();
                xb_2 xb_22 = (xb_2)akz_02.value();
                if (xb_22.mi() == null || xb_22.mi().iP() != n2) continue;
                akz_02.remove();
                if (!bl2) continue;
                xb_22.aK();
            }
        }
        catch (ConcurrentModificationException concurrentModificationException) {
            this.h(n2, bl2);
        }
    }

    public void c(XV xV) {
        try {
            akz_0 akz_02 = this.cEI.eI();
            while (akz_02.hasNext()) {
                akz_02.fK();
                xb_2 xb_22 = (xb_2)akz_02.value();
                if (xb_22.ajO() != xV) continue;
                akz_02.remove();
                xb_22.aK();
            }
        }
        catch (ConcurrentModificationException concurrentModificationException) {
            this.c(xV);
        }
    }

    public Iterator iterator() {
        return new agw_2(this.cEI);
    }

    public boolean a(BitSet bitSet, xb_2 xb_22, byte by) {
        xb_2 xb_232;
        if (!this.cEJ) {
            return false;
        }
        boolean bl2 = false;
        ArrayList<xb_2> arrayList = new ArrayList<xb_2>();
        ArrayList<xb_2> arrayList2 = new ArrayList<xb_2>();
        Object object = this.cEI.eI();
        while (((aiz_1)object).hasNext()) {
            ((akz_0)object).fK();
            xb_232 = (xb_2)((akz_0)object).value();
            if (xb_22 == xb_232 || xb_22 != null && xb_22.ajZ() == xb_232) continue;
            switch (by) {
                case 10: {
                    if (!xb_232.ako() || xb_232.akh() == null || !xb_232.akh().intersects(bitSet)) break;
                    arrayList.add(xb_232);
                    bl2 = true;
                    break;
                }
                case 20: {
                    if (!xb_232.ako() || xb_232.aki() == null || !xb_232.aki().intersects(bitSet)) break;
                    arrayList.add(xb_232);
                    bl2 = true;
                    break;
                }
                case 30: {
                    if (!xb_232.ako() || xb_232.akj() == null || !xb_232.akj().intersects(bitSet)) break;
                    arrayList.add(xb_232);
                    bl2 = true;
                    break;
                }
                case 1: {
                    if (xb_232.ako() || xb_232.akh() == null || !xb_232.akh().intersects(bitSet)) break;
                    arrayList.add(xb_232);
                    bl2 = true;
                    break;
                }
                case 2: {
                    if (xb_232.ako() || xb_232.aki() == null || !xb_232.aki().intersects(bitSet)) break;
                    arrayList.add(xb_232);
                    bl2 = true;
                    break;
                }
                case 3: {
                    if (xb_232.ako() || xb_232.akj() == null || !xb_232.akj().intersects(bitSet)) break;
                    arrayList.add(xb_232);
                    bl2 = true;
                    break;
                }
                case 5: {
                    if (xb_232.ako() || xb_232.akk() == null || !xb_232.akk().intersects(bitSet)) break;
                    arrayList.add(xb_232);
                    bl2 = true;
                    break;
                }
                case 6: {
                    if (xb_232.ako() || xb_232.akl() == null || !xb_232.akl().intersects(bitSet)) break;
                    arrayList.add(xb_232);
                    bl2 = true;
                    break;
                }
                case 60: {
                    if (!xb_232.ako() || xb_232.akl() == null || !xb_232.akl().intersects(bitSet)) break;
                    arrayList.add(xb_232);
                    bl2 = true;
                    break;
                }
                case 4: {
                    if (xb_232.ako() || xb_232.akm() == null || !xb_232.akm().intersects(bitSet)) break;
                    arrayList2.add(xb_232);
                    bl2 = true;
                    break;
                }
                case 40: {
                    if (!xb_232.ako() || xb_232.akm() == null || !xb_232.akm().intersects(bitSet)) break;
                    arrayList2.add(xb_232);
                    bl2 = true;
                }
            }
        }
        if (bl2) {
            for (xb_2 xb_232 : arrayList2) {
                xb_232.aky();
            }
            if (by == 10 || by == 1) {
                object = null;
                xb_232 = null;
                for (xb_2 xb_24 : arrayList) {
                    if (object != null && ((nc_2)object).a(xb_24.akC()) >= 0) continue;
                    object = xb_24.akC();
                    xb_232 = xb_24;
                }
                xb_232.i(xb_22);
            } else {
                for (xb_2 xb_232 : arrayList) {
                    xb_232.i(xb_22);
                }
            }
        }
        return bl2;
    }

    public boolean isEmpty() {
        return this.cEI.isEmpty();
    }

    public int size() {
        return this.cEI.size();
    }
}

