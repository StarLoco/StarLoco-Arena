/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.log4j.Logger;

class YY
extends Thread {
    static final YY cbS = new YY();
    protected static final Logger a;
    private final Queue cbT = new ConcurrentLinkedQueue();
    private volatile boolean cX = true;
    private final Lock bmw = new ReentrantLock();
    private final Condition bmx = this.bmw.newCondition();
    private static final SimpleDateFormat cbU;
    private BufferedWriter cbV;
    private BufferedWriter cbW;

    YY() {
    }

    public void g(nm_2 nm_22) {
        this.cbT.offer(nm_22);
        this.We();
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

    private void a(String string, String string2, String string3, int n2, Object object, long l2) {
        this.gP(string);
        StringBuffer stringBuffer = new StringBuffer().append(string2).append("|").append(string3).append("|").append(n2).append("|").append(l2).append("|").append(object);
        if (this.cbV != null) {
            try {
                this.cbV.write(stringBuffer.toString());
                this.cbV.newLine();
            }
            catch (IOException iOException) {
                a.error((Object)"Erreur durant l'\u00e9criture des donn\u00e9es");
            }
        }
        if (this.cbW != null) {
            try {
                this.cbW.write(stringBuffer.toString());
                this.cbW.newLine();
            }
            catch (IOException iOException) {
                a.error((Object)"Erreur durant l'\u00e9criture des donn\u00e9es");
            }
        }
    }

    private void gO(String string) {
        if (this.cbW == null) {
            String string2 = string + "/stats-instant.log";
            File file = new File(string);
            if (!file.exists()) {
                file.mkdirs();
            }
            file = new File(string2);
            try {
                this.cbW = new BufferedWriter(new FileWriter(file, false), 1024);
            }
            catch (IOException iOException) {
                a.error((Object)("Impossible de cr\u00e9\u00e9r le fichier " + string2));
            }
        }
    }

    private void gP(String string) {
        if (this.cbV == null) {
            String string2 = string + "/stats-" + cbU.format(new Date()) + ".log";
            File file = new File(string);
            if (!file.exists()) {
                file.mkdirs();
            }
            file = new File(string2);
            try {
                this.cbV = new BufferedWriter(new FileWriter(file, true), 1024);
            }
            catch (IOException iOException) {
                a.error((Object)("Impossible de cr\u00e9\u00e9r le fichier " + string2));
            }
        }
    }

    private void anb() {
        if (this.cbW != null) {
            try {
                this.cbW.flush();
                this.cbW.close();
            }
            catch (IOException iOException) {
                a.error((Object)"Erreur durant la fermeture du log : ", (Throwable)iOException);
            }
            this.cbW = null;
        }
    }

    private void anc() {
        if (this.cbV != null) {
            try {
                this.cbV.flush();
                this.cbV.close();
            }
            catch (IOException iOException) {
                a.error((Object)"Erreur durant la fermeture du log : ", (Throwable)iOException);
            }
            this.cbV = null;
        }
    }

    public void gQ(String string) {
        this.cbT.offer(new nm_2(string, null, null, 0, null, -1L));
    }

    public void and() {
        this.cbT.offer(new nm_2(null, null, null, 0, null, -2L));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void run() {
        a.info((Object)"MonitoredPropertyWriter started");
        boolean bl2 = false;
        while (!bl2) {
            nm_2 nm_22;
            block10: while ((nm_22 = (nm_2)this.cbT.poll()) != null) {
                switch ((int)nm_2.a(nm_22)) {
                    case -1: {
                        this.gO(nm_2.b(nm_22));
                        continue block10;
                    }
                    case -2: {
                        this.anb();
                        continue block10;
                    }
                }
                this.a(nm_2.b(nm_22), nm_2.c(nm_22), nm_2.d(nm_22), nm_2.e(nm_22), nm_2.f(nm_22), nm_2.a(nm_22));
            }
            if (this.cbT.isEmpty()) {
                this.anc();
                if (this.cbT.isEmpty() && this.bmw.tryLock()) {
                    try {
                        this.bmx.await();
                    }
                    catch (InterruptedException interruptedException) {
                        a.warn((Object)"Interrupted");
                    }
                    finally {
                        this.bmw.unlock();
                    }
                }
            }
            bl2 = !this.cX && this.cbT.isEmpty();
        }
        this.anb();
        a.info((Object)"MonitoredPropertyWriter terminated");
    }

    static {
        cbS.setName("MonitoredPropertyWriter");
        cbS.start();
        a = Logger.getLogger(YY.class);
        cbU = new SimpleDateFormat("dd-MM-yyyy");
    }
}

