/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import org.apache.log4j.Logger;

/*
 * Renamed from TG
 */
public class tg_2
extends Thread {
    protected static Logger a = Logger.getLogger(tg_2.class);
    public static final tg_2 bOB = new tg_2();
    public static final long cV = 10L;
    private volatile boolean cX = false;
    private final ArrayList bOC = new ArrayList();
    private long bOD;
    private final ArrayList bOE = new ArrayList();

    private tg_2() {
    }

    public final synchronized void start() {
        this.setName("VideoManager");
        super.start();
        this.bOD = System.currentTimeMillis();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void run() {
        a.info((Object)"VideoManager running");
        while (true) {
            try {
                while (true) {
                    Thread.sleep(10L);
                    if (!this.cX) {
                        continue;
                    }
                    break;
                }
            }
            catch (InterruptedException interruptedException) {
                a.error((Object)"Exception", (Throwable)interruptedException);
            }
            long l2 = System.currentTimeMillis();
            long l3 = l2 - this.bOD;
            ArrayList arrayList = this.bOC;
            synchronized (arrayList) {
                this.aD(l3);
            }
        }
    }

    public final void aD(long l2) {
        if (!this.cX) {
            return;
        }
        int n2 = this.bOC.size();
        for (int j = 0; j < n2; ++j) {
            rr_0 rr_02 = (rr_0)this.bOC.get(j);
            rr_02.aD(l2);
            if (!rr_02.wv()) continue;
            this.bOE.add(rr_02);
        }
        this.bOC.removeAll(this.bOE);
        if (this.bOC.size() == 0) {
            this.setPaused(true);
        }
        this.bOE.clear();
    }

    public void setPaused(boolean bl2) {
        if (bl2 && this.cX) {
            a.info((Object)"Suspending VideoManager thread...");
            this.cX = false;
        } else if (!bl2 && !this.cX) {
            a.info((Object)"Resuming VideoManager thread...");
            this.cX = true;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final synchronized void b(rr_0 rr_02) {
        this.setPaused(false);
        ArrayList arrayList = this.bOC;
        synchronized (arrayList) {
            if (!this.bOC.contains(rr_02)) {
                this.bOC.add(rr_02);
            }
        }
    }
}

