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

public class adq {
    private String aNj;
    private final int[] aNk = new int[4];
    private final int[] cmt = new int[4];
    private final int[] cmu = new int[4];
    private int cmv;
    private int cmw;
    private static final ano_0 cmx = new ano_0();
    private static final Logger a = Logger.getLogger(adq.class);
    private static final ReentrantReadWriteLock aNo = new ReentrantReadWriteLock();
    private static final Lock aNp = aNo.readLock();
    private static final Lock aNq = aNo.writeLock();

    public adq(String string) {
        this.reset();
        this.aNj = string;
    }

    public void jN(int n2) {
        if (n2 < this.cmu[0]) {
            this.cmu[0] = n2;
        }
        if (n2 > this.cmu[1]) {
            this.cmu[1] = n2;
        }
        this.cmu[2] = this.cmu[2] + n2;
        this.cmu[3] = this.cmu[3] + 1;
    }

    public void jO(int n2) {
        if (n2 < this.cmt[0]) {
            this.cmt[0] = n2;
        }
        if (n2 > this.cmt[1]) {
            this.cmt[1] = n2;
        }
        this.cmt[2] = this.cmt[2] + n2;
        this.cmt[3] = this.cmt[3] + 1;
    }

    public void db(boolean bl2) {
        if (bl2) {
            ++this.cmv;
        } else {
            ++this.cmw;
        }
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
        this.cmt[0] = Integer.MAX_VALUE;
        this.cmt[1] = 0;
        this.cmu[0] = Integer.MAX_VALUE;
        this.cmu[1] = 0;
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

    public int jP(int n2) {
        int n3 = this.cmt[n2];
        if (n2 == 0) {
            this.cmt[0] = Integer.MAX_VALUE;
        } else if (n2 == 1) {
            this.cmt[1] = 0;
        }
        return n3;
    }

    public int jQ(int n2) {
        int n3 = this.cmu[n2];
        if (n2 == 0) {
            this.cmu[0] = Integer.MAX_VALUE;
        } else if (n2 == 1) {
            this.cmu[1] = 0;
        }
        return n3;
    }

    public int asn() {
        return this.cmv;
    }

    public int aso() {
        return this.cmw;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static ArrayList Lz() {
        ArrayList arrayList = new ArrayList();
        aNp.lock();
        try {
            if (!cmx.isEmpty()) {
                cmx.b(new go_2(arrayList));
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
    public static adq u(String string, boolean bl2) {
        adq adq2 = null;
        aNp.lock();
        try {
            adq2 = (adq)cmx.get(string);
        }
        catch (Exception exception) {
            a.error((Object)"Exception", (Throwable)exception);
        }
        finally {
            aNp.unlock();
        }
        if (adq2 == null && bl2) {
            adq2 = new adq(string);
            aNq.lock();
            try {
                cmx.put(string, adq2);
            }
            catch (Exception exception) {
                a.error((Object)"Exception", (Throwable)exception);
            }
            finally {
                aNq.unlock();
            }
        }
        return adq2;
    }
}

