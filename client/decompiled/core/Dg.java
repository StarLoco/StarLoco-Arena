/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.apache.log4j.Logger;

public class Dg {
    private String aNj;
    private final int[] aNk = new int[4];
    private final int[] aNl = new int[4];
    private final int[] aNm = new int[5];
    private static final ano_0 aNn = new ano_0();
    private static final Logger a = Logger.getLogger(adq.class);
    private static final ReentrantReadWriteLock aNo = new ReentrantReadWriteLock();
    private static final Lock aNp = aNo.readLock();
    private static final Lock aNq = aNo.writeLock();

    public Dg(String string) {
        this.reset();
        this.aNj = string;
    }

    public void d(int n2, boolean bl2) {
        if (bl2) {
            this.aNm[3] = this.aNm[3] + 1;
        } else {
            this.aNm[4] = this.aNm[4] + 1;
        }
        if (n2 < this.aNm[0]) {
            this.aNm[0] = n2;
        }
        if (n2 > this.aNm[1]) {
            this.aNm[1] = n2;
        }
        this.aNm[2] = this.aNm[2] + n2;
    }

    public void fd(int n2) {
        if (n2 < this.aNl[0]) {
            this.aNl[0] = n2;
        }
        if (n2 > this.aNl[1]) {
            this.aNl[1] = n2;
        }
        this.aNl[2] = this.aNl[2] + n2;
        this.aNl[3] = this.aNl[3] + 1;
    }

    public void fe(int n2) {
        if (n2 < this.aNk[0]) {
            this.aNk[0] = n2;
        }
        if (n2 > this.aNk[1]) {
            this.aNk[1] = n2;
        }
        this.aNk[2] = this.aNk[2] + n2;
        this.aNk[3] = this.aNk[3] + 1;
    }

    void reset() {
        this.aNk[0] = Integer.MAX_VALUE;
        this.aNk[1] = 0;
        this.aNl[0] = Integer.MAX_VALUE;
        this.aNl[1] = 0;
        this.aNm[0] = Integer.MAX_VALUE;
        this.aNm[1] = 0;
    }

    public String Ly() {
        return this.aNj;
    }

    public int ff(int n2) {
        int n3 = this.aNk[n2];
        if (n2 == 0) {
            this.aNk[0] = Integer.MAX_VALUE;
        } else if (n2 == 1) {
            this.aNk[1] = 0;
        }
        return n3;
    }

    public int fg(int n2) {
        int n3 = this.aNl[n2];
        if (n2 == 0) {
            this.aNl[0] = Integer.MAX_VALUE;
        } else if (n2 == 1) {
            this.aNl[1] = 0;
        }
        return n3;
    }

    public int fh(int n2) {
        int n3 = this.aNm[n2];
        if (n2 == 0) {
            this.aNm[0] = Integer.MAX_VALUE;
        } else if (n2 == 1) {
            this.aNm[1] = 0;
        }
        return n3;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static ArrayList Lz() {
        ArrayList arrayList = new ArrayList();
        aNp.lock();
        try {
            block4: {
                try {
                    if (aNn.isEmpty()) break block4;
                    aNn.b(new aMM(arrayList));
                }
                catch (Exception exception) {
                    a.error((Object)"Exception", (Throwable)exception);
                    Object var3_2 = null;
                    aNp.unlock();
                }
            }
            Object var3_1 = null;
            aNp.unlock();
        }
        catch (Throwable throwable) {
            Object var3_3 = null;
            aNp.unlock();
            throw throwable;
        }
        return arrayList;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Dg g(String string, boolean bl2) {
        aNp.lock();
        Dg dg = null;
        try {
            try {
                dg = (Dg)aNn.get(string);
            }
            catch (Exception exception) {
                a.error((Object)"Exception", (Throwable)exception);
                Object var5_4 = null;
                aNp.unlock();
            }
            Object var5_3 = null;
            aNp.unlock();
        }
        catch (Throwable throwable) {
            Object var5_5 = null;
            aNp.unlock();
            throw throwable;
        }
        if (dg == null && bl2) {
            dg = new Dg(string);
            aNq.lock();
            try {
                try {
                    aNn.put(string, dg);
                }
                catch (Exception exception) {
                    a.error((Object)"Exception", (Throwable)exception);
                    Object var7_10 = null;
                    aNq.unlock();
                }
                Object var7_9 = null;
                aNq.unlock();
            }
            catch (Throwable throwable) {
                Object var7_11 = null;
                aNq.unlock();
                throw throwable;
            }
        }
        return dg;
    }
}

