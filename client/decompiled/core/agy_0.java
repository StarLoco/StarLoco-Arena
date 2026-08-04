/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.apache.log4j.Logger;

/*
 * Renamed from aGY
 */
public class agy_0 {
    private final ka_2 cys;
    private final AtomicLong dJT = new AtomicLong(0L);
    private final AtomicLong dJU = new AtomicLong(0L);
    private final AtomicInteger dJV = new AtomicInteger(0);
    private static final ano_0 byy = new ano_0();
    private static final ReentrantReadWriteLock aNo = new ReentrantReadWriteLock();
    private static final Lock aNp = aNo.readLock();
    private static final Lock aNq = aNo.writeLock();
    private static final Logger a = Logger.getLogger(agy_0.class);

    agy_0(ka_2 ka_22) {
        this.cys = ka_22;
    }

    public ka_2 rd() {
        return this.cys;
    }

    public long aTd() {
        return this.dJU.get();
    }

    public void oB(int n2) {
        this.dJU.addAndGet(n2);
    }

    public long aTe() {
        return this.dJT.get();
    }

    public void gD(int n2) {
        this.dJT.addAndGet(n2);
    }

    public int re() {
        return this.dJV.get();
    }

    public void WO() {
        this.dJV.incrementAndGet();
    }

    public void WP() {
        this.dJV.decrementAndGet();
    }

    public static agy_0 e(ka_2 ka_22) {
        agy_0 agy_02 = agy_0.f(ka_22);
        if (agy_02 == null) {
            agy_02 = new agy_0(ka_22);
            aNq.lock();
            byy.put(ka_22, agy_02);
            aNq.unlock();
        }
        return agy_02;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static agy_0 f(ka_2 ka_22) {
        aNp.lock();
        agy_0 agy_02 = null;
        try {
            agy_02 = (agy_0)byy.get(ka_22);
        }
        catch (Exception exception) {
            a.error((Object)"Exception", (Throwable)exception);
        }
        finally {
            aNp.unlock();
        }
        return agy_02;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static ArrayList aTf() {
        ArrayList arrayList = new ArrayList(20);
        aNp.lock();
        try {
            if (!byy.isEmpty()) {
                byy.a(new hm(arrayList));
            }
        }
        catch (Exception exception) {
            a.error((Object)"Exception", (Throwable)exception);
        }
        finally {
            aNp.unlock();
        }
        return arrayList;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static ArrayList aTg() {
        ArrayList arrayList = new ArrayList(20);
        aNp.lock();
        try {
            if (!byy.isEmpty()) {
                byy.a(new hi(arrayList));
            }
        }
        catch (Exception exception) {
            a.error((Object)"Exception", (Throwable)exception);
        }
        finally {
            aNp.unlock();
        }
        return arrayList;
    }

    static /* synthetic */ ka_2 b(agy_0 agy_02) {
        return agy_02.cys;
    }
}

