/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.apache.log4j.Logger;

/*
 * Renamed from MW
 */
public class mw_1 {
    private final jn_0 byw;
    private final AtomicInteger byx = new AtomicInteger(0);
    private static final ano_0 byy = new ano_0();
    private static final ReentrantReadWriteLock aNo = new ReentrantReadWriteLock();
    private static final Lock aNp = aNo.readLock();
    private static final Lock aNq = aNo.writeLock();
    private static final Logger a = Logger.getLogger(agy_0.class);

    private mw_1(jn_0 jn_02) {
        this.byw = jn_02;
    }

    public void ZV() {
        this.byx.incrementAndGet();
    }

    public void ZW() {
        this.byx.decrementAndGet();
    }

    public jn_0 ZX() {
        return this.byw;
    }

    public int ZY() {
        return this.byx.intValue();
    }

    public static mw_1 b(jn_0 jn_02) {
        mw_1 mw_12 = mw_1.c(jn_02);
        if (mw_12 == null) {
            mw_12 = new mw_1(jn_02);
            aNq.lock();
            byy.put(jn_02, mw_12);
            aNq.unlock();
        }
        return mw_12;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static mw_1 c(jn_0 jn_02) {
        aNp.lock();
        mw_1 mw_12 = null;
        try {
            mw_12 = (mw_1)byy.get(jn_02);
        }
        catch (Exception exception) {
            a.error((Object)"Exception", (Throwable)exception);
        }
        finally {
            aNp.unlock();
        }
        return mw_12;
    }
}

