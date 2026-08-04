/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.apache.log4j.Logger;

/*
 * Renamed from KA
 */
public abstract class ka_2
extends Thread
implements axx_0 {
    protected static final int bob = 0;
    protected static final int boc = 40;
    protected static final int bod = 72;
    protected static final int boe = 104;
    protected static final int bof = 136;
    protected static final int bog = 48;
    protected static final int boh = 80;
    protected static final int boi = 112;
    protected static final int boj = 144;
    protected static final int bok = 56;
    protected static final int bol = 88;
    protected static final int bom = 120;
    protected static final int bon = 152;
    protected static final int boo = 184;
    protected static final int bop = 32;
    protected static final int boq = 64;
    protected static final int bor = 96;
    protected static final int bos = 128;
    protected static final int bot = 160;
    protected static final int bou = 192;
    protected static final int bov = 128;
    private static Logger a = Logger.getLogger(ka_2.class);
    protected static final boolean cR = false;
    public static int bow = 10;
    protected Selector box;
    protected acl_0 boy;
    protected RE boz;
    protected int aW;
    protected static int boA = 0;
    protected int boB;
    protected int bmy;
    protected boolean cX;
    protected boolean act;
    protected final List boC;
    protected int Qx;
    protected String Qy;
    protected int boD;
    private final Xi boE = new Xi();
    private long boF = Long.MAX_VALUE;
    protected final Queue boG = new ConcurrentLinkedQueue();
    protected final agy_0 boH;

    public ka_2(RE rE) {
        this.aW = ++boA;
        super.setName("ConnectionHandler-" + this.aW);
        this.boH = agy_0.e(this);
        this.boz = rE;
        this.boB = 0;
        this.bmy = 0;
        this.boC = Collections.synchronizedList(new ArrayList());
        if (this.boz == null) {
            throw new IllegalArgumentException("L'argument 'eventsHandler' ne doit pas \u00c3\u00aatre nul");
        }
        try {
            this.box = Selector.open();
            this.cX = false;
            this.act = false;
        }
        catch (Exception exception) {
            a.error((Object)exception);
            this.boz.a(this);
        }
        this.boE.setName("ConnectionWriter-" + this.getName());
    }

    void gD(int n2) {
        this.boH.gD(n2);
    }

    void gE(int n2) {
        this.boH.oB(n2);
    }

    void WO() {
        this.boH.WO();
    }

    void WP() {
        this.boH.WP();
    }

    public synchronized void start() {
        if (!this.cX) {
            this.cX = true;
            super.start();
        }
    }

    public int getID() {
        return this.aW;
    }

    public boolean isRunning() {
        return this.cX;
    }

    public void UA() {
        this.cX = false;
    }

    public boolean uq() {
        return this.act;
    }

    public void ai(boolean bl2) {
        this.act = bl2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void d(Throwable throwable) {
        String string = bl_0.b(throwable);
        a.error((Object)"Exception raised : ", throwable);
        ++this.bmy;
        List list = this.boC;
        synchronized (list) {
            if (this.bmy >= 10) {
                this.boC.remove(0);
            }
            this.boC.add(string);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Object[] WQ() {
        List list = this.boC;
        synchronized (list) {
            return this.boC.toArray();
        }
    }

    protected ait_0 WR() {
        ait_0 ait_02 = null;
        try {
            ait_02 = (ait_0)this.boy.adr();
            ait_02.a(this.boy);
        }
        catch (Throwable throwable) {
            this.boz.c(this);
            a.error((Object)"createConnection exception : ", throwable);
        }
        return ait_02;
    }

    boolean b(ait_0 ait_02) {
        boolean bl2 = false;
        if (ait_02 != null) {
            ++this.boB;
            ait_02.d(this);
            ait_02.d(this.boz);
        }
        return bl2;
    }

    /*
     * Loose catch block
     */
    protected boolean a(SelectionKey selectionKey) {
        try {
            ait_0 ait_02 = (ait_0)selectionKey.attachment();
            if (ait_02 == null) {
                return true;
            }
            if (ait_02.ayh()) {
                return true;
            }
            if (!ait_02.isRegistered()) {
                return true;
            }
            return this.boz.b(this, ait_02);
        }
        catch (Throwable throwable) {
            a.error((Object)"read exception : ", throwable);
            return false;
            {
                catch (Throwable throwable2) {
                    a.error((Object)"key exception : ", throwable2);
                    return false;
                }
            }
        }
    }

    protected final void Us() {
        akp_0 akp_02;
        if (this.boD > 0) {
            --this.boD;
            return;
        }
        while ((akp_02 = (akp_0)this.boG.poll()) != null) {
            this.a(akp_02);
        }
    }

    protected void a(akp_0 akp_02) {
        switch (akp_02.cDo) {
            case atp: {
                this.boD = akp_02.cDq;
                break;
            }
            case atn: {
                this.a((mu_0)akp_02.cDp);
                break;
            }
            case atm: {
                ((mu_0)akp_02.cDp).Yx();
                break;
            }
            case ato: {
                this.WP();
                akp_02.cDp.release();
                break;
            }
            case atk: {
                this.f(akp_02.cDp);
                break;
            }
            case atl: {
                this.e(akp_02.cDp);
                break;
            }
            default: {
                a.warn((Object)("Unhandled operation ! " + akp_02));
            }
        }
    }

    protected void a(mu_0 mu_02) {
        throw new UnsupportedOperationException("Non impl\u00c3\u00a9ment\u00c3\u00a9 dans " + this.getClass().getSimpleName());
    }

    void b(mu_0 mu_02) {
        akp_0 akp_02 = new akp_0(vc_2.atp, mu_02);
        akp_02.cDq = 250;
        this.boG.offer(akp_02);
        this.boG.offer(new akp_0(vc_2.atm, mu_02));
        this.box.wakeup();
    }

    void c(ait_0 ait_02) {
        this.boG.offer(new akp_0(vc_2.atk, ait_02));
        this.box.wakeup();
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

    public long WS() {
        return this.boF;
    }

    public void bX(long l2) {
        a.info((Object)("Configuration du timeout d'\u00c3\u00a9criture fix\u00c3\u00a9 \u00c3\u00a0 : " + l2 + " ms"));
        this.boF = l2;
    }

    protected synchronized void d(ait_0 ait_02) {
        SocketChannel socketChannel = ait_02.getSocketChannel();
        if (socketChannel != null) {
            if (!socketChannel.isConnected()) {
                try {
                    socketChannel.register(this.box, 8, ait_02);
                }
                catch (ClosedChannelException closedChannelException) {
                    a.error((Object)"ClosedChannelException", (Throwable)closedChannelException);
                }
            } else {
                try {
                    socketChannel.register(this.box, 1, ait_02);
                }
                catch (ClosedChannelException closedChannelException) {
                    a.error((Object)"ClosedChannelException", (Throwable)closedChannelException);
                }
            }
            this.box.wakeup();
        } else {
            a.error((Object)"Channel invalide = null");
        }
    }

    protected synchronized void e(ait_0 ait_02) {
        SocketChannel socketChannel = ait_02.getSocketChannel();
        if (socketChannel != null && socketChannel.isConnected()) {
            try {
                socketChannel.register(this.box, 1, ait_02);
                this.box.wakeup();
            }
            catch (ClosedChannelException closedChannelException) {
                a.error((Object)"ClosedChannelException", (Throwable)closedChannelException);
            }
        }
    }

    protected synchronized void f(ait_0 ait_02) {
        SocketChannel socketChannel = ait_02.getSocketChannel();
        if (socketChannel != null && socketChannel.isConnected()) {
            try {
                socketChannel.register(this.box, 5, ait_02);
                this.box.wakeup();
            }
            catch (ClosedChannelException closedChannelException) {
                a.error((Object)"ClosedChannelException", (Throwable)closedChannelException);
            }
        }
    }

    protected synchronized void g(ait_0 ait_02) {
        this.e(ait_02);
    }

    protected synchronized void h(ait_0 ait_02) {
        SocketChannel socketChannel = ait_02.getSocketChannel();
        if (socketChannel != null) {
            SelectionKey selectionKey = socketChannel.keyFor(this.box);
            if (selectionKey != null) {
                selectionKey.attach(null);
                selectionKey.cancel();
            }
            try {
                socketChannel.close();
            }
            catch (IOException iOException) {
                a.error((Object)"Exception lev\u00c3\u00a9e lors de la fermeture du channel", (Throwable)iOException);
            }
        } else {
            a.error((Object)"Channel is null");
        }
    }
}

