/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.ankamagames.framework.kernel.stats.StatisticsCounter
 *  org.apache.log4j.Logger
 */
import com.ankamagames.framework.kernel.stats.StatisticsCounter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.log4j.Logger;

/*
 * Renamed from acu
 */
public final class acu_1
implements axx_0,
Runnable {
    static final boolean cjR = false;
    private static final boolean cjS = false;
    private static final boolean cR = false;
    private static final boolean cjT = false;
    public static int cjU = 0;
    public static int cjV = 0;
    private static final Logger a = Logger.getLogger(acu_1.class);
    protected static final acu_1 cjW = new acu_1();
    protected final ConcurrentLinkedQueue cjX = new ConcurrentLinkedQueue();
    protected final Lock cjY = new ReentrantLock();
    protected final Condition bXd = this.cjY.newCondition();
    private final List cjZ;
    private int Qx;
    private long cka = 0L;
    private int ckb = 0;
    public AtomicInteger ckc = new AtomicInteger(0);
    public int ckd = 0;
    public int cke;
    public int bmy;
    public int ckf;
    public final HashMap ckg = new HashMap();
    public final ArrayList ckh;
    public long cki;
    private nj_0 ckj;
    private ake_2 ckk = new ake_2(this, null);
    private aay_1 ckl;
    long ckm = 0L;
    long ckn = 0L;
    StatisticsCounter[] cko = null;
    int ckp = 0;

    private acu_1() {
        this.cjZ = Collections.synchronizedList(new ArrayList());
        this.ckh = new ArrayList();
    }

    public static acu_1 ara() {
        return cjW;
    }

    public void a(aay_1 aay_12) {
        this.ckl = aay_12;
    }

    public void join() {
        try {
            nj_0 nj_02 = this.ckj;
            if (nj_02 != null) {
                nj_02.join();
            }
        }
        catch (InterruptedException interruptedException) {
            a.error((Object)"Thread interrupted : ", (Throwable)interruptedException);
        }
    }

    public void start() {
        if (this.ckj == null) {
            acu_1 acu_12 = this;
            this.ckj = new mc_0(this, acu_12);
            this.ckj.setPriority(10);
            this.ckj.start();
        } else {
            a.warn((Object)"Le Worker est d\u00e9j\u00e0 en cours de fonctionnement.");
        }
    }

    public void arb() {
        if (this.ckj == null) {
            this.ckj = new lz_0(this);
            this.ckj.start();
        } else {
            a.warn((Object)"Le Worker est d\u00e9j\u00e0 en cours de fonctionnement.");
        }
    }

    public void c(pr_0 pr_02) {
        if (pr_02 != null) {
            pr_02.aw(System.currentTimeMillis());
            this.cjX.offer(pr_02);
            this.ckc.incrementAndGet();
            this.We();
        }
    }

    public void t(ArrayList arrayList) {
        if (arrayList != null && !arrayList.isEmpty()) {
            int n2 = arrayList.size();
            long l2 = System.currentTimeMillis();
            for (int j = 0; j < n2; ++j) {
                pr_0 pr_02 = (pr_0)arrayList.get(j);
                pr_02.aw(l2);
                this.cjX.offer(pr_02);
            }
            this.ckc.addAndGet(n2);
            this.We();
        }
    }

    public int arc() {
        return this.ckc.get();
    }

    public int ard() {
        return 0;
    }

    public void are() {
        this.ckd = 0;
        this.cke = 0;
        this.bmy = 0;
        this.cjZ.clear();
    }

    public int arf() {
        return this.ckd;
    }

    public int arg() {
        return this.cke;
    }

    public int arh() {
        return this.bmy;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Object[] WQ() {
        List list = this.cjZ;
        synchronized (list) {
            return this.cjZ.toArray();
        }
    }

    public boolean isRunning() {
        if (this.ckj != null) {
            return this.ckj.isRunning();
        }
        return false;
    }

    public void f(boolean bl2) {
        if (this.ckj != null) {
            this.ckj.f(bl2);
            this.We();
        }
    }

    public void kill() {
        a.warn((Object)("Worker killed by " + bl_0.dH()));
        this.cjX.clear();
        this.f(false);
    }

    public void interrupt() {
        if (this.ckj != null) {
            this.ckj.interrupt();
            this.We();
        }
    }

    void We() {
        if (this.cjY.tryLock()) {
            this.bXd.signal();
            this.cjY.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void d(pr_0 pr_02) {
        block15: {
            if (pr_02 != null) {
                String string = pr_02.getClass().getSimpleName();
                Dg dg = Dg.g(string, true);
                try {
                    if (pr_02.uz()) {
                        int n2;
                        long l2 = System.nanoTime();
                        if (this.ckl != null) {
                            this.ckl.a(System.currentTimeMillis(), pr_02);
                        }
                        try {
                            if (cjU > 0 && cjV > 0) {
                                try {
                                    n2 = cjU + (int)(Math.random() * (double)(cjV - cjU));
                                    Thread.sleep(n2);
                                }
                                catch (InterruptedException interruptedException) {
                                    a.info((Object)"Inner latency generation interrupted");
                                }
                            }
                            pr_02.execute();
                        }
                        catch (Exception exception) {
                            a.error((Object)("Exception lev\u00e9e lors de l'\u00e9x\u00e9cution d'un message (id=" + pr_02.getId() + "): "), (Throwable)exception);
                        }
                        n2 = (int)((System.nanoTime() - l2) / 1000000L);
                        if (this.ckl != null) {
                            this.ckl.aML();
                        }
                        dg.d(n2, true);
                        ++this.ckd;
                        break block15;
                    }
                    alx_0 alx_02 = pr_02.uy();
                    a.error((Object)("Destinataire invalide pour un message de type " + string + ", destinataire : " + (alx_02 != null ? alx_02.getClass().getSimpleName() : "null")));
                }
                catch (Throwable throwable) {
                    this.d(throwable);
                    a.error((Object)"Exception lev\u00e9e dans le worker : ", throwable);
                    dg.d(0, false);
                }
                finally {
                    acl_0 acl_02;
                    if (!pr_02.uA() && (acl_02 = pr_02.uG) != null) {
                        pr_02.release();
                        ++this.ckf;
                    }
                }
            }
        }
    }

    void ari() {
        while (!this.cjX.isEmpty()) {
            pr_0 pr_02 = (pr_0)this.cjX.poll();
            this.ckc.decrementAndGet();
            this.d(pr_02);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void run() {
        this.ari();
        ip_2.Un().Ur();
        long l2 = ip_2.Un().Uq();
        if (l2 > 0L) {
            if (this.cjY.tryLock()) {
                try {
                    this.bXd.await(l2, TimeUnit.MILLISECONDS);
                    ip_2.Un().update();
                }
                catch (InterruptedException interruptedException) {
                    a.error((Object)"Worker interrupted", (Throwable)interruptedException);
                }
                finally {
                    this.cjY.unlock();
                }
            }
        } else {
            ip_2.Un().update();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void d(Throwable throwable) {
        if (throwable instanceof OutOfMemoryError) {
            try {
                a.error((Object)"Out of memory lev\u00e9 dans le Worker ", throwable);
                throwable.printStackTrace();
            }
            catch (Exception exception) {
                a.error((Object)"Out of Memory !");
                exception.printStackTrace();
            }
            System.exit(1);
        }
        String string = bl_0.b(throwable);
        a.error((Object)("Exception catch\u00e9e dans le Worker : " + string), throwable);
        Throwable throwable2 = throwable.getCause();
        if (throwable2 != null) {
            a.error((Object)"Caused by : ", throwable2);
        }
        ++this.bmy;
        List list = this.cjZ;
        synchronized (list) {
            if (this.bmy >= 10) {
                this.cjZ.remove(0);
            }
            this.cjZ.add(string.toString());
        }
    }

    public String sZ() {
        return "Worker";
    }

    public int ta() {
        return this.Qx;
    }

    public void cv(int n2) {
        this.Qx = n2;
    }

    public HashMap arj() {
        return this.ckg;
    }

    public void clear() {
        this.cjX.clear();
        this.ckc.set(0);
    }

    public final String ark() {
        sa_1 sa_12 = new sa_1();
        for (Serializable serializable : this.cjX) {
            sa_12.a(serializable.getClass().getSimpleName(), 1, 1);
        }
        if (sa_12.size() > 0) {
            Serializable serializable;
            serializable = new StringBuilder();
            if (!sa_12.isEmpty()) {
                sa_12.a(new Ma(this, (StringBuilder)serializable));
            }
            return ((StringBuilder)serializable).toString();
        }
        return "(empty)";
    }

    public static void main(String[] stringArray) {
        acu_1.ara().arb();
        aIN aIN2 = new aIN(null);
        int n2 = 0;
        while (true) {
            Thread.yield();
            fp_2 fp_22 = new fp_2(null);
            fp_2.a(fp_22, n2++);
            fp_22.aj(true);
            fp_22.a(aIN2);
            acu_1.ara().c(fp_22);
        }
    }

    static /* synthetic */ void a(acu_1 acu_12, pr_0 pr_02) {
        acu_12.d(pr_02);
    }

    static /* synthetic */ Logger sP() {
        return a;
    }

    static /* synthetic */ nj_0 a(acu_1 acu_12, nj_0 nj_02) {
        acu_12.ckj = nj_02;
        return acu_12.ckj;
    }

    static /* synthetic */ ake_2 a(acu_1 acu_12) {
        return acu_12.ckk;
    }

    static /* synthetic */ void a(acu_1 acu_12, Throwable throwable) {
        acu_12.d(throwable);
    }
}

