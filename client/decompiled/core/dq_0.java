/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.HashMap;
import org.apache.log4j.Logger;

/*
 * Renamed from dQ
 */
public class dq_0
implements Runnable {
    private static Logger a = Logger.getLogger(dq_0.class);
    private final HashMap nn = new HashMap();
    private final Object no = new Object();
    private boolean np = false;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void f(String string, int n2) {
        cz_2 cz_22 = (cz_2)this.nn.get(string);
        if (cz_22 != null) {
            return;
        }
        if (n2 < 1) {
            n2 = 1;
        }
        try {
            cz_22 = new cz_2(string, n2);
            Object object = this.nn;
            synchronized (object) {
                this.nn.put(string, cz_22);
            }
            object = this.no;
            synchronized (object) {
                this.no.notify();
            }
            this.np = true;
        }
        catch (Exception exception) {
            a.error((Object)("Resource invalide : " + string));
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void Q(String string) {
        cz_2 cz_22 = null;
        HashMap hashMap = this.nn;
        synchronized (hashMap) {
            cz_22 = (cz_2)this.nn.remove(string);
        }
        if (cz_22 != null) {
            cz_22.clean();
        }
    }

    public void gE() {
        for (cz_2 cz_22 : this.nn.values()) {
            cz_22.clean();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public na_1 R(String string) {
        cz_2 cz_22;
        Object object = this.nn;
        synchronized (object) {
            cz_22 = (cz_2)this.nn.get(string);
        }
        if (cz_22 != null) {
            object = this.no;
            synchronized (object) {
                this.no.notify();
            }
            this.np = true;
            return cz_22.Lp();
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void run() {
        while (true) {
            Object object;
            try {
                object = this.no;
                synchronized (object) {
                    if (!this.np) {
                        this.no.wait();
                    }
                }
            }
            catch (InterruptedException interruptedException) {
                a.warn((Object)"Interrupted Exception");
            }
            object = this.nn;
            synchronized (object) {
                for (cz_2 cz_22 : this.nn.values()) {
                    if (!cz_22.Lq()) continue;
                    cz_22.Lr();
                }
            }
            this.np = false;
        }
    }
}

