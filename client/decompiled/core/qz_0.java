/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;

/*
 * Renamed from Qz
 */
public class qz_0
extends Thread {
    private boolean bGH = true;
    private Qa qJ;
    private BlockingQueue bGI = new LinkedBlockingQueue();
    private static final qz_0 bGJ = new qz_0();
    private static final Logger a = Logger.getLogger(qz_0.class);

    private qz_0() {
        this.setName("AsyncLoader");
    }

    public static qz_0 adf() {
        return bGJ;
    }

    public final void run() {
        while (this.bGH) {
            if (this.qJ == null) {
                try {
                    this.qJ = (Qa)this.bGI.poll(1L, TimeUnit.SECONDS);
                }
                catch (InterruptedException interruptedException) {
                    continue;
                }
                if (this.qJ == null) continue;
            }
            try {
                this.qJ.acH();
                if (this.qJ.is()) {
                    this.qJ = null;
                }
            }
            catch (IOException iOException) {
                a.error((Object)("An error occurs while streaming the url " + this.qJ.getURL().getPath()), (Throwable)iOException);
                this.qJ = null;
            }
            Thread.yield();
        }
    }

    public final Qa e(URL uRL) {
        Qa qa = new Qa(uRL);
        this.bGI.add(qa);
        return qa;
    }

    public final void kill() {
        this.bGH = false;
    }
}

