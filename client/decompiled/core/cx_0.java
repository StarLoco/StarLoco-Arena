/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.log4j.Logger;

/*
 * Renamed from Cx
 */
public final class cx_0 {
    protected static final Logger a = Logger.getLogger(cx_0.class);
    private static cx_0 aLC = new cx_0();
    private static final int aLD = 131072;
    private final cp_2 aLE;
    private final HashMap aLF;
    private final ArrayList aLG;
    private final ArrayList aLH = new ArrayList(64);
    private float aLI;
    private float aLJ = 131072.0f;
    private final Lock aLK = new ReentrantLock();

    private cx_0() {
        this.aLE = new cp_2(1024);
        this.aLG = new ArrayList(128);
        this.aLF = new HashMap(128);
    }

    public static cx_0 JY() {
        return aLC;
    }

    public ef_1 a(db_2 db_22, long l2, String string, boolean bl2, boolean bl3) {
        ef_1 ef_12 = this.bt(l2);
        if (ef_12 == null) {
            ef_12 = db_22.a(l2, string, bl2);
            ef_12.bh(bl3);
            this.aLK.lock();
            this.aLE.a(l2, ef_12);
            this.aLK.unlock();
        }
        return ef_12;
    }

    public ef_1 a(db_2 db_22, long l2, String string, boolean bl2) {
        return this.a(db_22, l2, string, bl2, true);
    }

    public ef_1 a(db_2 db_22, long l2, aon_2 aon_22, boolean bl2) {
        ef_1 ef_12 = this.bt(l2);
        if (ef_12 == null) {
            ef_12 = db_22.a(l2, aon_22, bl2);
            this.aLK.lock();
            this.aLE.a(l2, ef_12);
            this.aLK.unlock();
        }
        return ef_12;
    }

    public ef_1 a(db_2 db_22, long l2, aon_2 aon_22, adz_1 adz_12, boolean bl2) {
        ef_1 ef_12 = this.a(l2, adz_12);
        if (ef_12 != null) {
            return ef_12;
        }
        return this.b(db_22, l2, aon_22, adz_12, bl2);
    }

    public ef_1 a(db_2 db_22, long l2, String string, adz_1 adz_12, boolean bl2) {
        ef_1 ef_12 = this.a(l2, adz_12);
        if (ef_12 != null) {
            return ef_12;
        }
        aon_2 aon_22 = new aon_2();
        if (!aon_22.iO(string)) {
            return null;
        }
        ef_12 = this.b(db_22, l2, aon_22, adz_12, bl2);
        ef_12.eA = string;
        return ef_12;
    }

    public ef_1 a(db_2 db_22, long l2, String string, String string2, adz_1 adz_12, boolean bl2) {
        ef_1 ef_12 = this.a(l2, adz_12);
        if (ef_12 != null) {
            return ef_12;
        }
        aon_2 aon_22 = new aon_2();
        if (!aon_22.iO(string2 + string)) {
            return null;
        }
        return this.b(db_22, l2, aon_22, adz_12, bl2);
    }

    public ef_1 a(db_2 db_22, long l2, int n2, int n3, boolean bl2) {
        return db_22.a(l2, n2, n3, bl2);
    }

    public adz_1 c(ef_1 ef_12) {
        return (adz_1)this.aLF.get(ef_12);
    }

    public final int JZ() {
        return this.aLE.size();
    }

    public final ef_1 bt(long l2) {
        this.aLK.lock();
        ef_1 ef_12 = (ef_1)this.aLE.t(l2);
        this.aLK.unlock();
        return ef_12;
    }

    public final ef_1 d(ef_1 ef_12) {
        return this.bu(ef_12.MI());
    }

    public ef_1 bu(long l2) {
        return (ef_1)this.aLE.u(l2);
    }

    public void reset() {
        akz_0 akz_02 = this.aLE.eI();
        while (akz_02.hasNext()) {
            akz_02.fK();
            this.d((ef_1)akz_02.value());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int dA(String string) {
        assert (string != null);
        this.aLK.lock();
        int n2 = 0;
        try {
            try {
                n2 = 0;
                akz_0 akz_02 = this.aLE.eI();
                while (akz_02.hasNext()) {
                    akz_02.fK();
                    ef_1 ef_12 = (ef_1)akz_02.value();
                    if (ef_12.is() || !ef_12.isEmpty() || !ef_12.load(string)) continue;
                    ++n2;
                }
                Object var6_6 = null;
                this.aLK.unlock();
            }
            catch (Exception exception) {
                a.error((Object)"Exception raised while loading textures : ", (Throwable)exception);
                Object var6_7 = null;
                this.aLK.unlock();
            }
        }
        catch (Throwable throwable) {
            Object var6_8 = null;
            this.aLK.unlock();
            throw throwable;
        }
        return n2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int a(db_2 db_22, String string) {
        long l2 = System.nanoTime();
        this.aLK.lock();
        int n2 = 0;
        try {
            try {
                akz_0 akz_02 = this.aLE.eI();
                while (akz_02.hasNext()) {
                    float f;
                    akz_02.fK();
                    ef_1 ef_12 = (ef_1)akz_02.value();
                    if (ef_12.is() || ef_12.isEmpty() && string != null && !ef_12.load(string)) continue;
                    if (ef_12.e(db_22)) {
                        this.aLI += ef_12.MM();
                        ++n2;
                    }
                    if (!((f = (float)(System.nanoTime() - l2) / 1000000.0f) > 1.0f)) continue;
                    break;
                }
                Object var10_9 = null;
                this.aLK.unlock();
            }
            catch (Exception exception) {
                a.error((Object)"Exception raised while preparing textures : ", (Throwable)exception);
                Object var10_10 = null;
                this.aLK.unlock();
            }
        }
        catch (Throwable throwable) {
            Object var10_11 = null;
            this.aLK.unlock();
            throw throwable;
        }
        return n2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void Ka() {
        float f = this.aLI - this.aLJ;
        if (f <= 0.0f) {
            return;
        }
        this.aLH.clear();
        float f2 = 0.0f;
        this.aLK.lock();
        try {
            try {
                akz_0 akz_02 = this.aLE.eI();
                while (akz_02.hasNext()) {
                    akz_02.fK();
                    ef_1 ef_12 = (ef_1)akz_02.value();
                    if (ef_12 == null) continue;
                    ef_12.avd();
                    if (ef_12.avb() != 0 || ef_12.avc() > 0) continue;
                    this.aLH.add(ef_12);
                    f2 += ef_12.MM();
                }
                if (f2 > f) {
                    int n2;
                    int n3 = this.aLH.size();
                    for (n2 = 0; n2 < n3; ++n2) {
                        for (int j = n2 + 1; j < n3; ++j) {
                            ef_1 ef_13 = (ef_1)this.aLH.get(n2);
                            ef_1 ef_14 = (ef_1)this.aLH.get(j);
                            if (ef_14.MN() <= ef_13.MN()) continue;
                            this.aLH.set(n2, ef_14);
                            this.aLH.set(j, ef_13);
                        }
                    }
                    for (n2 = 0; n2 < n3; ++n2) {
                        ef_1 ef_15 = (ef_1)this.aLH.get(n2);
                        f -= ef_15.MM();
                        this.aLG.add(ef_15);
                        this.aLE.u(ef_15.MI());
                        this.aLI -= ef_15.MM();
                        this.aLF.remove(ef_15);
                        if (!(f < 0.0f)) {
                            continue;
                        }
                        break;
                    }
                } else {
                    int n4 = this.aLH.size();
                    for (int j = 0; j < n4; ++j) {
                        ef_1 ef_16 = (ef_1)this.aLH.get(j);
                        this.aLE.u(ef_16.MI());
                        this.aLG.add(ef_16);
                        this.aLI -= ef_16.MM();
                        this.aLF.remove(ef_16);
                    }
                }
                Object var10_16 = null;
                this.aLK.unlock();
            }
            catch (Exception exception) {
                a.error((Object)"Exception raised while releasing textures : ", (Throwable)exception);
                Object var10_17 = null;
                this.aLK.unlock();
            }
        }
        catch (Throwable throwable) {
            Object var10_18 = null;
            this.aLK.unlock();
            throw throwable;
        }
    }

    public void Kb() {
        for (int j = 0; j < this.aLG.size(); ++j) {
            ((ef_1)this.aLG.get(j)).HF();
        }
        this.aLG.clear();
    }

    public float Kc() {
        return this.aLI / 1024.0f;
    }

    public void ae(float f) {
        this.aLJ = Math.max(131072.0f, f);
    }

    private ef_1 a(long l2, adz_1 adz_12) {
        ef_1 ef_12 = this.bt(l2);
        if (ef_12 != null) {
            adz_12.b((adz_1)this.aLF.get(ef_12));
        }
        return ef_12;
    }

    private ef_1 b(db_2 db_22, long l2, aon_2 aon_22, adz_1 adz_12, boolean bl2) {
        adz_1 adz_13 = aon_22.lC(0);
        adz_12.b(adz_13);
        int n2 = aon_22.aCH();
        for (int j = 0; j < n2; ++j) {
            kf_0 kf_02 = aon_22.lB(j);
            kf_0 kf_03 = aon_2.b(kf_02.getData(), kf_02.getWidth(), kf_02.getHeight(), kf_02.getBitDepth());
            kf_03.g(kf_02.cx(), kf_02.cy());
            aon_22.a(j, kf_03);
        }
        ef_1 ef_12 = cx_0.JY().a(db_22, l2, aon_22, bl2);
        aon_22.HF();
        this.aLK.lock();
        this.aLF.put(ef_12, adz_12);
        this.aLE.a(l2, ef_12);
        this.aLK.unlock();
        return ef_12;
    }
}

