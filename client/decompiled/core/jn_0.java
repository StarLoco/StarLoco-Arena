/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.sql.DataSource;
import org.apache.log4j.Logger;

/*
 * Renamed from JN
 */
public final class jn_0
extends Thread
implements axx_0 {
    protected static final Logger a = Logger.getLogger(jn_0.class);
    private static final int bmp = 10;
    private static final long bmq = 5000L;
    private static final long bmr = 5000L;
    private static final long bms = 100L;
    private static final long bmt = 1000L;
    protected final DataSource Qv;
    protected Connection bmu;
    private final Queue bmv = new ConcurrentLinkedQueue();
    private final Lock bmw = new ReentrantLock();
    private final Condition bmx = this.bmw.newCondition();
    public volatile boolean cX;
    protected int Qx;
    protected String Qy;
    public int bmy;
    private long bmz;
    private long bmA;
    private boolean bmB = false;
    private boolean bmC = true;
    private lb_0 bmD;
    private HashMap QF = new HashMap();
    private final mw_1 bmE;

    public void a(HashMap hashMap) {
        this.QF = hashMap;
    }

    public boolean Wc() {
        return this.bmB;
    }

    public void bJ(boolean bl2) {
        this.bmB = bl2;
    }

    public boolean Wd() {
        return this.bmC;
    }

    public void bK(boolean bl2) {
        this.bmC = bl2;
    }

    public jn_0(DataSource dataSource) {
        super.setName("SqlRequestChannel");
        this.Qv = dataSource;
        this.bmu = null;
        this.cX = false;
        this.bmy = 0;
        this.bmD = new lb_0();
        this.bmE = mw_1.b(this);
        this.bmB = false;
    }

    public void start() {
        this.cX = true;
        super.start();
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
        this.bmx.signal();
        this.bmw.unlock();
    }

    public void c(arr arr2) {
        if (arr2 != null) {
            this.bmv.offer(arr2);
            this.We();
            this.bmE.ZV();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private arr Wf() {
        long l2 = System.currentTimeMillis();
        arr arr2 = null;
        arr2 = (arr)this.bmv.peek();
        if (arr2 == null) {
            try {
                if (this.bmu != null && l2 - this.bmA > 5000L) {
                    if (this.bmB) {
                        this.bmu.commit();
                    }
                    this.bmA = l2;
                }
            }
            catch (SQLException sQLException) {
                a.error((Object)"Exception during autocommit", (Throwable)sQLException);
            }
            if (this.bmw.tryLock()) {
                try {
                    this.bmx.await(1000L, TimeUnit.MILLISECONDS);
                }
                catch (InterruptedException interruptedException) {
                    a.warn((Object)"Interrupted", (Throwable)interruptedException);
                }
                finally {
                    this.bmw.unlock();
                }
            }
        }
        arr2 = (arr)this.bmv.peek();
        return arr2;
    }

    private void Wg() {
        arr arr2 = (arr)this.bmv.poll();
        this.bmE.ZW();
    }

    public int Wh() {
        return this.bmE.ZY();
    }

    public Connection getConnection() {
        return this.bmu;
    }

    public final void a(int n2, PreparedStatement preparedStatement) {
        this.bmD.c(n2, preparedStatement);
    }

    public final PreparedStatement gv(int n2) {
        return (PreparedStatement)this.bmD.get(n2);
    }

    private void Wi() {
        ll_0 ll_02 = this.bmD.pK();
        while (ll_02.hasNext()) {
            ll_02.fK();
            try {
                ((PreparedStatement)ll_02.value()).close();
            }
            catch (SQLException sQLException) {
                a.error((Object)"Exception", (Throwable)sQLException);
            }
        }
        this.bmD.clear();
    }

    public boolean Wj() {
        long l2 = System.currentTimeMillis() + 1000L;
        boolean bl2 = true;
        do {
            try {
                if (this.bmu == null || this.bmu.isClosed()) {
                    if (this.bmu != null) {
                        if (this.bmB) {
                            this.bmu.commit();
                        }
                        this.bmu.close();
                        this.bmA = System.currentTimeMillis();
                    }
                    this.bmu = this.Qv.getConnection();
                    this.bmu.setTypeMap(this.QF);
                    this.Wi();
                    if (this.bmB) {
                        this.bmu.setAutoCommit(false);
                    }
                }
                bl2 = false;
            }
            catch (Exception exception) {
                try {
                    Thread.sleep(100L);
                }
                catch (Exception exception2) {
                    // empty catch block
                }
                a.warn((Object)("[" + this.Qy + "] Connection error : Lost since " + (System.currentTimeMillis() - l2 + 1000L) + " ms."), (Throwable)exception);
            }
        } while (bl2 && System.currentTimeMillis() < l2);
        if (bl2 && l2 <= System.currentTimeMillis()) {
            a.error((Object)("[" + this.Qy + "] Connection error : TimeOut greater than " + 1000L + " ms, connection is lost !"));
        }
        return !bl2;
    }

    public pr_0 b(arr arr2) {
        ob_0 ob_02 = arr2.aEz();
        agc_1 agc_12 = new agc_1(this);
        arr2.a(agc_12);
        this.c(arr2);
        while (!agc_12.asB) {
            Thread.yield();
        }
        arr2.a(ob_02);
        if (agc_12.asB) {
            if (agc_12.asC != null) {
                throw new Exception(agc_12.asC);
            }
            return agc_12.asA;
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void run() {
        a.info((Object)("SqlRequestChannel [" + this.Qy + "] started "));
        int n2 = 0;
        try {
            while (this.cX || !this.bmv.isEmpty()) {
                Thread.yield();
                arr arr2 = this.Wf();
                long l2 = System.currentTimeMillis();
                if (arr2 != null) {
                    this.bmz = l2;
                    String string = arr2.getClass().getSimpleName();
                    UH uH = UH.m(string, true);
                    ob_0 ob_02 = arr2.aEz();
                    try {
                        if (++n2 < 10) {
                            if (this.Wj() && (arr2.aEy() || !arr2.aEx())) {
                                pr_0 pr_02 = null;
                                long l3 = System.nanoTime();
                                pr_02 = arr2.a(this);
                                int n3 = (int)((System.nanoTime() - l3) / 1000000L);
                                uH.ih(n3);
                                if (pr_02 != null) {
                                    pr_02.a(arr2.aEz());
                                    acu_1.ara().c(pr_02);
                                } else if (ob_02 != null) {
                                    ob_02.a(null);
                                }
                                uH.a(gd_1.sc);
                            } else {
                                a.warn((Object)("[" + this.Qy + "] Le destinataire de la r\u00e9ponse \u00e0 cette requete n'est plus valide : requestType = " + arr2.getClass().getSimpleName()));
                                uH.a(gd_1.sd);
                            }
                        } else {
                            a.warn((Object)("[" + this.Qy + "] Request (" + arr2.getClass().getSimpleName() + ") canceled (too much retries) : " + arr2));
                            if (ob_02 != null) {
                                ob_02.a(arr2, new Exception("Request canceled (too much retries)"));
                            }
                            uH.a(gd_1.se);
                        }
                        this.Wg();
                        arr2.release();
                        n2 = 0;
                        continue;
                    }
                    catch (SQLException sQLException) {
                        a.error((Object)("[" + this.Qy + "] SQLException lev\u00e9e lors de l'\u00e9x\u00e9cution d'une requ\u00eate de type : " + arr2.getClass().getSimpleName()), (Throwable)sQLException);
                        ++this.bmy;
                        continue;
                    }
                    catch (Throwable throwable) {
                        a.error((Object)("[" + this.Qy + "] Throwable capt\u00e9 lors de l'\u00e9x\u00e9cution d'une requ\u00eate de type : " + arr2.getClass().getSimpleName()), throwable);
                        ++this.bmy;
                        continue;
                    }
                }
                if (this.bmu == null || this.bmu.isClosed() || l2 - this.bmz < 5000L || !this.bmC || !this.bmv.isEmpty()) continue;
                if (this.bmB) {
                    this.bmu.commit();
                }
                this.bmu.close();
                this.bmu = null;
                this.Wi();
                this.bmA = l2;
            }
            if (this.bmu != null) {
                if (this.bmB) {
                    this.bmu.commit();
                }
                this.bmu.close();
                this.bmu = null;
            }
        }
        catch (Throwable throwable) {
            a.error((Object)("[" + this.Qy + "] Throwable capt\u00e9 lors de la connexion \u00e0 la base"), throwable);
            ++this.bmy;
        }
        a.info((Object)"SqlRequestChannel stopped ");
    }

    public String sZ() {
        return this.Qy;
    }

    public void bp(String string) {
        this.Qy = string;
    }

    public int ta() {
        return this.Qx;
    }

    public void cv(int n2) {
        this.Qx = n2;
    }
}

