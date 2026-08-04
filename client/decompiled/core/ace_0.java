/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.log4j.Logger;

/*
 * Renamed from acE
 */
public abstract class ace_0
extends Thread {
    private static final boolean cR = false;
    protected static final Logger a = Logger.getLogger(apl_1.class);
    protected final ArrayList ckD = new ArrayList();
    private final Queue ckE = new ConcurrentLinkedQueue();
    private volatile boolean cX;
    private int ckF;
    private final Lock bmw = new ReentrantLock();
    private final Condition ckG = this.bmw.newCondition();
    protected static final String ckH = "id";
    protected static final int ckI = "id".hashCode();
    private final acl_0 bgV = new abk_2(new aKt(this));

    private iu_2 arq() {
        try {
            return (iu_2)this.bgV.adr();
        }
        catch (Exception exception) {
            a.error((Object)"Exception lev\u00e9e lors d'un checkOut d'op\u00e9ration", (Throwable)exception);
            return null;
        }
    }

    private void a(iu_2 iu_22) {
        try {
            this.bgV.af(iu_22);
        }
        catch (Exception exception) {
            a.error((Object)"Exception lev\u00e9e lors du retour au pool d'un process", (Throwable)exception);
        }
    }

    public void a(tp_2 tp_22) {
        if (!this.ckD.contains(tp_22)) {
            this.ckD.add(tp_22);
        }
    }

    public void b(tp_2 tp_22) {
        this.ckD.remove(tp_22);
    }

    public synchronized void start() {
        if (!this.cX) {
            this.cX = true;
            super.start();
        }
    }

    public void shutdown() {
        this.a(W.bs, null);
    }

    public boolean isRunning() {
        return this.cX;
    }

    public void f(boolean bl2) {
        this.cX = bl2;
        this.We();
    }

    private void We() {
        this.bmw.lock();
        this.ckG.signal();
        this.bmw.unlock();
    }

    public void a(W w, Object object) {
        iu_2 iu_22 = this.arq();
        if (iu_22 != null) {
            iu_22.yp = w;
            iu_22.yq = object;
            this.ckE.offer(iu_22);
            this.We();
        }
    }

    public int arr() {
        return this.ckE.size();
    }

    public int ars() {
        return this.ckF;
    }

    public void jI(int n2) {
        this.ckF = n2;
    }

    public abstract lJ a(int var1, lJ var2);

    public abstract lJ[] a(lJ var1);

    public abstract lJ[] a(String var1, Object var2, lJ var3);

    protected abstract boolean art();

    protected abstract void b(lJ var1);

    protected abstract void c(lJ var1);

    protected abstract String aru();

    protected abstract void arv();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void run() {
        a.info((Object)("BinaryStorage started " + this));
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        this.cX = true;
        while (this.cX) {
            iu_2 iu_22;
            while ((iu_22 = (iu_2)this.ckE.poll()) != null) {
                W w = iu_22.yp;
                switch (w) {
                    case bq: {
                        ++n4;
                        Object object = (lJ)iu_22.yq;
                        this.b((lJ)object);
                        for (tp_2 tp_22 : this.ckD) {
                            tp_22.b(this, (lJ)object);
                        }
                        break;
                    }
                    case br: {
                        ++n3;
                        Object object = (lJ)iu_22.yq;
                        this.c((lJ)object);
                        for (tp_2 tp_22 : this.ckD) {
                            tp_22.a(this, (lJ)object);
                        }
                        break;
                    }
                    case bs: {
                        this.f(false);
                        for (Object object : this.ckD) {
                            object.c(this);
                        }
                        break;
                    }
                }
                ++n2;
                this.a(iu_22);
            }
            if (!this.cX || !this.bmw.tryLock()) continue;
            try {
                this.ckG.await();
            }
            catch (InterruptedException interruptedException) {
                a.warn((Object)"Interrupt", (Throwable)interruptedException);
            }
            finally {
                this.bmw.unlock();
            }
        }
        a.info((Object)("BinaryStorage stopped : " + n2 + " operations, " + n3 + " saved, " + n4 + " destroyed"));
    }
}

