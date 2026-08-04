/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import org.apache.log4j.Logger;

/*
 * Renamed from aer
 */
class aer_1
extends aga_1 {
    protected static final Logger a = Logger.getLogger(aer_1.class);
    private volatile boolean cX = false;
    private RE boz;

    aer_1(RE rE) {
        super(rE);
        this.boz = rE;
    }

    public void f(boolean bl2) {
        this.cX = bl2;
        this.box.wakeup();
    }

    public synchronized void start() {
        if (!this.cX) {
            this.cX = true;
            super.start();
        }
    }

    void a(SocketChannel socketChannel) {
        try {
            socketChannel.configureBlocking(false);
            socketChannel.socket().setTcpNoDelay(true);
            socketChannel.socket().setPerformancePreferences(0, 2, 1);
            socketChannel.socket().setTrafficClass(128);
            ait_0 ait_02 = this.WR();
            if (ait_02 != null) {
                this.b(ait_02);
                ait_02.b(socketChannel);
                this.k(ait_02);
            }
        }
        catch (IOException iOException) {
            a.error((Object)"Exception", (Throwable)iOException);
        }
    }

    private void k(ait_0 ait_02) {
        this.WO();
        ait_02.d(this);
        this.boG.offer(new akp_0(vc_2.atj, ait_02));
        this.box.wakeup();
    }

    protected void a(akp_0 akp_02) {
        ait_0 ait_02 = akp_02.cDp;
        switch (akp_02.cDo) {
            case atj: {
                this.e(ait_02);
                if (this.boz.a(this, ait_02)) break;
                ait_02.close();
                this.WP();
                ait_02.release();
                break;
            }
            default: {
                super.a(akp_02);
            }
        }
    }

    public void run() {
        while (this.cX) {
            this.Us();
            int n2 = 0;
            try {
                n2 = this.box.select(bow);
            }
            catch (IOException iOException) {
                a.error((Object)"Exception", (Throwable)iOException);
            }
            if (n2 <= 0) continue;
            Iterator<SelectionKey> iterator = null;
            iterator = this.box.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                iterator.remove();
                ait_0 ait_02 = (ait_0)selectionKey.attachment();
                if (selectionKey.isValid() && selectionKey.isWritable() && !ait_02.ayj() || !selectionKey.isValid() || !selectionKey.isReadable() || this.boz.b(this, ait_02)) continue;
                ait_02.close();
                this.WP();
                ait_02.release();
            }
        }
    }
}

