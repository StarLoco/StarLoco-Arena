/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.log4j.Logger;

class Xi
extends Thread {
    protected static final Logger a = Logger.getLogger(Xi.class);
    protected boolean cX;
    protected final Lock bXc = new ReentrantLock();
    protected final Condition bXd = this.bXc.newCondition();
    private final List bXe = new ArrayList(64);
    private final Queue bXf = new ConcurrentLinkedQueue();

    public Xi() {
        super.setName("ConnectionWriter");
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

    public void We() {
        this.bXc.lock();
        this.bXd.signal();
        this.bXc.unlock();
    }

    public void f(boolean bl2) {
        this.cX = bl2;
        this.We();
    }

    public void i(ait_0 ait_02) {
        if (ait_02 != null) {
            this.bXf.remove(ait_02);
        }
    }

    public void c(ait_0 ait_02) {
        if (ait_02 != null) {
            this.bXf.offer(ait_02);
        }
        this.We();
    }

    public void m(List list) {
        int n2 = list.size();
        if (n2 > 0) {
            for (int j = 0; j < n2; ++j) {
                ait_0 ait_02 = (ait_0)list.get(j);
                if (ait_02 == null) continue;
                this.bXf.offer(ait_02);
            }
            this.We();
        }
    }

    void j(ait_0 ait_02) {
        this.bXe.add(ait_02);
    }

    void Kv() {
        this.m(this.bXe);
        this.bXe.clear();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void run() {
        a.info((Object)"ConnectionWriter running");
        while (this.cX) {
            while (!this.bXf.isEmpty()) {
                ait_0 ait_02 = (ait_0)this.bXf.poll();
                try {
                    if (ait_02.ayj()) continue;
                    a.warn((Object)"Des donn\u00e9es n'ont pas pues \u00eatre envoy\u00e9es au destinataire : on abandonne.");
                }
                catch (Exception exception) {
                    a.error((Object)"Exception lev\u00e9e lors de l'\u00e9criture des donn\u00e9es", (Throwable)exception);
                }
            }
            if (!this.bXc.tryLock()) continue;
            try {
                this.bXd.await();
            }
            catch (InterruptedException interruptedException) {
                a.warn((Object)"Interrupted");
            }
            finally {
                this.bXc.unlock();
            }
        }
        a.info((Object)"ConnectionWriter stopped");
    }
}

