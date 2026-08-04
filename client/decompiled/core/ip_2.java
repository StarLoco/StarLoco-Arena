/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.log4j.Logger;

/*
 * Renamed from Ip
 */
public class ip_2 {
    protected static final Logger a = Logger.getLogger(ip_2.class);
    private static final ip_2 bgR = new ip_2();
    private AtomicInteger bgS = new AtomicInteger(0);
    private AtomicInteger bgT = new AtomicInteger(0);
    private long bgU = 0L;
    private final acl_0 bgV = new ade_1(new do(this), 100);
    private SK bgW;
    private final ConcurrentLinkedQueue bgX = new ConcurrentLinkedQueue();
    private final ArrayList bgY = new ArrayList();
    private final ArrayList bgZ = new ArrayList();

    public static ip_2 Un() {
        return bgR;
    }

    private ip_2() {
    }

    void a(SK sK) {
        this.bgW = sK;
    }

    private long currentTimeMillis() {
        if (this.bgW == null) {
            return System.currentTimeMillis();
        }
        return this.bgW.afn();
    }

    public void a(Runnable runnable) {
        this.a(runnable, 1L, 1);
    }

    public void a(Runnable runnable, long l2) {
        this.a(runnable, l2, -1);
    }

    private adl_1 Uo() {
        try {
            return (adl_1)this.bgV.adr();
        }
        catch (Exception exception) {
            a.error((Object)"Exception lev\u00e9e lors d'un checkOut d'op\u00e9ration", (Throwable)exception);
            return null;
        }
    }

    private void a(adl_1 adl_12) {
        try {
            this.bgV.af(adl_12);
        }
        catch (Exception exception) {
            a.error((Object)"Exception lev\u00e9e lors du retour au pool d'un process", (Throwable)exception);
        }
    }

    public void a(Runnable runnable, long l2, int n2) {
        if (runnable == null) {
            a.error((Object)"Tentative d'insertion d'un Runnable null");
            return;
        }
        yw_0 yw_02 = yw_0.amB();
        yw_02.c(runnable);
        yw_02.jf(n2);
        yw_02.dn(l2);
        adl_1 adl_12 = this.Uo();
        adl_12.cmo = EY.aUp;
        adl_12.cmp = yw_02;
        this.bgX.offer(adl_12);
        this.bgT.incrementAndGet();
        acu_1.ara().We();
    }

    public void b(Runnable runnable, long l2) {
        this.a(runnable);
        this.a(runnable, l2);
    }

    public void b(Runnable runnable) {
        adl_1 adl_12 = this.Uo();
        adl_12.cmo = EY.aUq;
        adl_12.runnable = runnable;
        this.bgX.offer(adl_12);
        this.bgT.incrementAndGet();
    }

    public void Up() {
        this.bgY.clear();
        this.bgX.clear();
        this.bgT.set(0);
        this.bgS.set(0);
    }

    private void a(yw_0 yw_02, long l2) {
        boolean bl2 = false;
        yw_02.dm(l2 + yw_02.amD());
        for (int j = 0; j < this.bgY.size(); ++j) {
            yw_0 yw_03 = (yw_0)this.bgY.get(j);
            if (yw_02.amC() >= yw_03.amC()) continue;
            this.bgY.add(j, yw_02);
            bl2 = true;
            break;
        }
        if (!bl2) {
            this.bgY.add(yw_02);
        }
    }

    long Uq() {
        if (this.bgY.isEmpty()) {
            return 30L;
        }
        long l2 = ((yw_0)this.bgY.get(0)).amC() - this.currentTimeMillis();
        return Math.max(0L, l2);
    }

    long Ur() {
        if (this.bgY.isEmpty() && this.bgX.peek() == null) {
            return this.Uq();
        }
        this.Us();
        return this.Uq();
    }

    private void Us() {
        adl_1 adl_12;
        long l2 = this.currentTimeMillis();
        while ((adl_12 = (adl_1)this.bgX.poll()) != null) {
            block0 : switch (adl_12.cmo) {
                case aUq: {
                    Iterator iterator = this.bgY.iterator();
                    while (iterator.hasNext()) {
                        yw_0 yw_02 = (yw_0)iterator.next();
                        if (yw_02.amE() != adl_12.runnable) continue;
                        this.bgS.decrementAndGet();
                        iterator.remove();
                        yw_02.release();
                        break block0;
                    }
                    break;
                }
                case aUp: {
                    this.bgS.incrementAndGet();
                    this.a(adl_12.cmp, l2);
                    break;
                }
                default: {
                    a.error((Object)("Undefined operation ! : " + adl_12));
                }
            }
            this.bgT.decrementAndGet();
            this.a(adl_12);
        }
    }

    void update() {
        if (this.bgY.isEmpty() && this.bgX.peek() == null) {
            return;
        }
        long l2 = this.currentTimeMillis();
        this.Us();
        Iterator iterator = this.bgY.iterator();
        while (iterator.hasNext()) {
            long l3;
            int n2;
            yw_0 yw_02;
            block13: {
                yw_02 = (yw_0)iterator.next();
                long l4 = l2 - yw_02.amC();
                if (l4 < 0L) continue;
                iterator.remove();
                this.bgS.decrementAndGet();
                n2 = yw_02.amF();
                if (n2 == 0) continue;
                if (n2 > 0) {
                    --n2;
                }
                yw_02.jf(n2);
                l3 = System.nanoTime();
                Runnable runnable = null;
                try {
                    runnable = yw_02.amE();
                    if (runnable != null) {
                        if (acu_1.cjU > 0 && acu_1.cjV > 0) {
                            try {
                                int n3 = acu_1.cjU + (int)(Math.random() * (double)(acu_1.cjV - acu_1.cjU));
                                Thread.sleep(n3);
                            }
                            catch (InterruptedException interruptedException) {
                                a.info((Object)"Inner latency generation interrupted");
                            }
                        }
                        runnable.run();
                        break block13;
                    }
                    a.error((Object)"(Paranoia) Process null ?!");
                }
                catch (Throwable throwable) {
                    if (runnable != null) {
                        a.error((Object)("ProcessScheduler exception (" + runnable.getClass().getName() + "): "), throwable);
                    }
                    a.error((Object)"ProcessScheduler exception (null process): ", throwable);
                }
            }
            double d = (double)(System.nanoTime() - l3) / 1000000.0;
            if (n2 != 0) {
                this.bgZ.add(yw_02);
                continue;
            }
            if (n2 != 0) continue;
            yw_02.release();
        }
        if (!this.bgZ.isEmpty()) {
            l2 = this.currentTimeMillis();
            int n4 = this.bgZ.size();
            for (int j = 0; j < n4; ++j) {
                this.a((yw_0)this.bgZ.get(j), l2);
            }
            this.bgZ.clear();
        }
        this.Us();
    }
}

