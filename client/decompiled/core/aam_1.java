/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.log4j.Logger;

/*
 * Renamed from aAm
 */
public class aam_1
extends Thread {
    private static final long dpa = 3L;
    private static final Logger a = Logger.getLogger(aam_1.class);
    protected static aam_1 dpb = new aam_1();
    protected boolean cX = false;
    protected final acM dpc = new acM();
    protected final Queue dpd = new ConcurrentLinkedQueue();
    protected final Lock cjY = new ReentrantLock();
    protected final Condition bXd = this.cjY.newCondition();
    protected long dpe = 0L;

    protected aam_1() {
        super("MessageScheduler");
    }

    public static aam_1 aMF() {
        return dpb;
    }

    static void a(aam_1 aam_12) {
        dpb = aam_12;
    }

    private void We() {
        this.cjY.lock();
        this.bXd.signalAll();
        this.cjY.unlock();
    }

    public long a(alx_0 alx_02, long l2, int n2) {
        return this.a(alx_02, l2, n2, -1);
    }

    public long a(alx_0 alx_02, long l2, int n2, int n3) {
        aey_1 aey_12 = new aey_1();
        aey_12.c(alx_02);
        aey_12.es(l2);
        aey_12.mH(n2);
        aey_12.nP(n3);
        aey_12.et(System.currentTimeMillis());
        ++this.dpe;
        aey_12.ek(this.dpe);
        t_0 t_02 = new t_0(null);
        t_02.ad = Gb.bax;
        t_02.ae = aey_12;
        this.dpd.offer(t_02);
        this.We();
        return aey_12.aKD();
    }

    public void en(long l2) {
        t_0 t_02 = new t_0(null);
        t_02.ad = Gb.bat;
        t_02.af = l2;
        this.dpd.offer(t_02);
        this.We();
    }

    public void aMG() {
        t_0 t_02 = new t_0(null);
        t_02.ad = Gb.baw;
        this.dpd.offer(t_02);
        this.We();
        this.We();
    }

    public void b(alx_0 alx_02) {
        t_0 t_02 = new t_0(null);
        t_02.ad = Gb.bau;
        t_02.ag = alx_02;
        this.dpd.offer(t_02);
        this.We();
    }

    public void a(alx_0 alx_02, int n2) {
        t_0 t_02 = new t_0(null);
        t_02.ad = Gb.bav;
        t_02.ag = alx_02;
        t_02.ah = n2;
        this.dpd.offer(t_02);
        this.We();
    }

    public void start() {
        if (!this.cX) {
            this.cX = true;
            super.start();
        }
    }

    public boolean isRunning() {
        return this.cX;
    }

    public void f(boolean bl2) {
        this.cX = bl2;
        this.We();
    }

    private void a(aey_1 aey_12) {
        boolean bl2 = false;
        long l2 = aey_12.aRk();
        for (aey_1 aey_13 : this.dpc) {
            if (aey_13.aRk() <= l2) continue;
            this.dpc.a(aey_13, (Wv)aey_12);
            bl2 = true;
            break;
        }
        if (!bl2) {
            this.dpc.c(aey_12);
        }
    }

    private void a(aey_1 aey_12, long l2) {
        try {
            axe_0 axe_02 = axe_0.aKC();
            axe_02.a(aey_12.aRm());
            axe_02.ek(aey_12.aKD());
            axe_02.mH(aey_12.aKE());
            axe_02.setTimeStamp(l2);
            acu_1.ara().c(axe_02);
        }
        catch (Exception exception) {
            a.error((Object)("Unable to push ClockMessage, exception raised : " + exception.getMessage()));
        }
    }

    private void aMH() {
        while (!this.dpd.isEmpty()) {
            t_0 t_02 = (t_0)this.dpd.poll();
            block0 : switch (t_02.ad) {
                case bax: {
                    this.a(t_02.ae);
                    break;
                }
                case bat: {
                    aey_1 aey_12;
                    Iterator iterator = this.dpc.iterator();
                    while (iterator.hasNext()) {
                        aey_12 = (aey_1)iterator.next();
                        if (aey_12.aKD() != t_02.af) continue;
                        aey_12.aRo();
                        iterator.remove();
                        break block0;
                    }
                    break;
                }
                case bau: {
                    aey_1 aey_12;
                    Iterator iterator = this.dpc.iterator();
                    while (iterator.hasNext()) {
                        aey_12 = (aey_1)iterator.next();
                        if (aey_12.aRm() != t_02.ag) continue;
                        iterator.remove();
                    }
                    break;
                }
                case bav: {
                    aey_1 aey_12;
                    Iterator iterator = this.dpc.iterator();
                    while (iterator.hasNext()) {
                        aey_12 = (aey_1)iterator.next();
                        if (aey_12.aRm() != t_02.ag || aey_12.aKE() != t_02.ah) continue;
                        iterator.remove();
                    }
                    break;
                }
                case baw: {
                    this.dpc.clear();
                }
            }
        }
    }

    public void run() {
        ArrayList<aey_1> arrayList = new ArrayList<aey_1>();
        a.info((Object)"MessageScheduler running");
        while (this.cX) {
            try {
                try {
                    if (!this.dpc.isEmpty()) {
                        long l2 = System.currentTimeMillis();
                        arrayList.clear();
                        Iterator iterator = this.dpc.iterator();
                        while (iterator.hasNext()) {
                            aey_1 aey_12 = (aey_1)iterator.next();
                            if (aey_12.aRk() <= l2 + 3L) {
                                if (!aey_12.aRn()) {
                                    this.a(aey_12, l2);
                                    aey_12.et(l2);
                                    iterator.remove();
                                    if (!aey_12.aRl()) continue;
                                    arrayList.add(aey_12);
                                    continue;
                                }
                                iterator.remove();
                                continue;
                            }
                            if (!arrayList.isEmpty()) break;
                            this.cjY.lock();
                            this.bXd.await(Math.max(1L, aey_12.aRk() - l2), TimeUnit.MILLISECONDS);
                            this.bXd.signalAll();
                            this.cjY.unlock();
                            break;
                        }
                        if (!arrayList.isEmpty()) {
                            for (aey_1 aey_13 : arrayList) {
                                this.a(aey_13);
                            }
                        }
                        this.aMH();
                        continue;
                    }
                    if (this.cjY.tryLock()) {
                        this.bXd.await();
                        this.cjY.unlock();
                    }
                    this.aMH();
                }
                catch (Exception exception) {
                    a.error((Object)"Exception lev\u00e9e : ", (Throwable)exception);
                }
            }
            catch (Exception exception) {
                a.error((Object)"Exception", (Throwable)exception);
            }
        }
        a.info((Object)"Message Scheduler stopped");
    }

    public final String aMI() {
        long l2 = System.currentTimeMillis();
        Iterator iterator = this.dpc.iterator();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Found ").append(this.dpc.size()).append(" clocks:\n");
        while (iterator.hasNext()) {
            aey_1 aey_12 = (aey_1)iterator.next();
            stringBuilder.append(aey_12.aRm().getClass().getSimpleName()).append(" : ").append(aey_12.aRj()).append(" ms ");
            if (aey_12.aRl()) {
                stringBuilder.append("repeatable ").append(aey_12.aRi()).append(" times ");
            }
            stringBuilder.append("next tick in ").append(aey_12.aRk() - l2).append(" ms\n");
        }
        return stringBuilder.toString();
    }
}

