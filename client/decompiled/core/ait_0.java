/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.net.InetAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.apache.log4j.Logger;

/*
 * Renamed from ait
 */
public class ait_0
implements JG,
apc_0 {
    private static final boolean cR = false;
    private static final boolean cym = false;
    private static final int cyn = 8192;
    private static final int cyo = 8192;
    private static final Logger a = Logger.getLogger(ait_0.class);
    protected SocketChannel cyp;
    protected adq_1 cyq;
    protected RE cyr;
    protected ka_2 cys;
    protected ByteBuffer cyt;
    protected ByteBuffer cyu;
    protected final Object cyv = new Object();
    protected acl_0 uG;
    protected final Queue cyw = new ConcurrentLinkedQueue();
    protected int cyx = 0;
    private static int cyy = 1;
    protected long nD = 0L;
    protected volatile boolean cyz;
    protected volatile boolean cyA;
    private long cyB;
    private boolean cyC;

    ait_0() {
        this.cyt = ByteBuffer.allocate(8192);
        this.cyt.clear();
        this.cyu = ByteBuffer.allocate(8192);
        this.cyu.clear();
    }

    public void b() {
        this.nD = cyy++;
        this.Yt();
    }

    public void j() {
        this.nD = 0L;
        this.uG = null;
        this.Yt();
    }

    public synchronized boolean axX() {
        return this.cyC;
    }

    public synchronized void dy(boolean bl2) {
        this.cyC = bl2;
    }

    protected void Yt() {
        this.cyz = false;
        this.cyA = false;
        this.cyp = null;
        this.cyq = null;
        this.cyr = null;
        this.cys = null;
        if (this.cyt != null) {
            this.cyt.clear();
        }
        if (this.cyu != null) {
            this.cyu.clear();
        }
        this.cyB = System.currentTimeMillis();
        this.cyC = false;
        this.cyw.clear();
    }

    public synchronized long getId() {
        return this.nD;
    }

    public synchronized void c(long l2) {
        this.nD = l2;
    }

    public synchronized void a(adq_1 adq_12) {
        this.cyq = adq_12;
    }

    public synchronized adq_1 axY() {
        return this.cyq;
    }

    void d(ka_2 ka_22) {
        this.cys = ka_22;
    }

    public synchronized ka_2 rd() {
        return this.cys;
    }

    void d(RE rE) {
        this.cyr = rE;
    }

    public synchronized RE axZ() {
        return this.cyr;
    }

    void b(SocketChannel socketChannel) {
        this.cyp = socketChannel;
    }

    SocketChannel getSocketChannel() {
        return this.cyp;
    }

    public boolean isRegistered() {
        return this.cys != null;
    }

    synchronized void close() {
        this.cyu.clear();
        this.cyt.clear();
        if (this.cyq != null) {
            this.cyq.Kq();
            this.cyq.a(null);
        }
        this.cyq = null;
        if (this.cyr != null && this.cys != null) {
            this.cyr.d(this.cys, this);
        }
        this.aye();
    }

    protected void aya() {
        this.cys.d(this);
    }

    protected void ayb() {
        this.cys.e(this);
    }

    protected void ayc() {
        this.cys.c(this);
    }

    protected void ayd() {
        this.ayb();
    }

    protected void aye() {
        this.cys.h(this);
        this.cyp = null;
    }

    public synchronized void ayf() {
        if (!this.cyz && !this.cyA && this.cys != null) {
            this.cyA = true;
            this.cys.c(this);
        } else {
            a.info((Object)("La connexion a \u00e9t\u00e9 ferm\u00e9e, ou la fermeture est d\u00e9j\u00e0 programm\u00e9e (aboutToClose=" + this.cyz + ", cleanClose=" + this.cyA));
        }
    }

    public boolean ayg() {
        return this.cyA;
    }

    public boolean ayh() {
        return this.cyz;
    }

    void dz(boolean bl2) {
        this.cyz = bl2;
    }

    public synchronized boolean Yv() {
        return false;
    }

    public synchronized boolean isConnected() {
        return this.cyp != null && this.cyp.isConnected() && !this.cyz && !this.Yu();
    }

    public synchronized boolean isConnectionPending() {
        return this.cyp != null && this.cyp.isConnectionPending();
    }

    public ByteBuffer ayi() {
        if (this.cyp == null) {
            a.warn((Object)"Tentative de lecture sur une Connection sans SocketChannel.");
            return null;
        }
        if (!this.cyp.isConnected() || !this.cyp.isOpen()) {
            a.warn((Object)("Tentative de lecture sur une Connection avec un SocketChannel dans l'\u00e9tat connected=" + this.cyp.isConnected() + " open=" + this.cyp.isOpen()));
            ByteBuffer byteBuffer = this.Yy();
            this.cyr.c(this.cys, this);
            return byteBuffer;
        }
        try {
            if (this.cyt.position() == this.cyt.limit()) {
                ByteBuffer byteBuffer = ByteBuffer.allocate(this.cyt.limit() * 2);
                byteBuffer.rewind();
                this.cyt.compact();
                byteBuffer.put(this.cyt);
                this.cyt = byteBuffer;
            }
        }
        catch (Throwable throwable) {
            a.warn((Object)"Impossible d'agrandir le buffer d'entr\u00e9e:", throwable);
            ByteBuffer byteBuffer = this.Yy();
            this.cyr.c(this.cys, this);
            return byteBuffer;
        }
        try {
            int n2 = this.cyp.read(this.cyt);
            if (n2 <= 0) {
                ByteBuffer byteBuffer = this.Yy();
                this.cyr.c(this.cys, this);
                return byteBuffer;
            }
            this.cys.gE(n2);
        }
        catch (Throwable throwable) {
            a.warn((Object)("Impossible de lire : la connexion a \u00e9t\u00e9 perdue. [" + throwable.getMessage() + "]"));
            ByteBuffer byteBuffer = this.Yy();
            this.cyr.c(this.cys, this);
            return byteBuffer;
        }
        this.cyt.flip();
        return this.cyt;
    }

    protected ByteBuffer Yy() {
        return null;
    }

    protected void Yz() {
        this.close();
        this.cys.WP();
        this.release();
    }

    boolean ayj() {
        if (this.cyp == null) {
            a.warn((Object)"Tentative d'\u00e9criture sur une Connection sans SocketChannel.");
            return false;
        }
        if (!this.cyp.isConnected() || !this.cyp.isOpen()) {
            a.warn((Object)("Tentative d'\u00e9criture sur une Connection avec un SocketChannel dans l'\u00e9tat connected=" + this.cyp.isConnected() + " open=" + this.cyp.isOpen()));
            this.cyr.c(this.cys, this);
            this.Yz();
            return false;
        }
        try {
            byte[] byArray;
            while ((byArray = (byte[])this.cyw.poll()) != null) {
                this.Y(byArray);
            }
            this.cyu.flip();
            if (this.cyu.remaining() > 0) {
                int n2 = this.cyp.write(this.cyu);
                if (n2 == 0) {
                    this.cyu.position(this.cyu.limit());
                    this.cyu.limit(this.cyu.capacity());
                    long l2 = this.cys.WS();
                    if (System.currentTimeMillis() - this.cyB >= l2) {
                        a.error((Object)"[WRITE ERROR] Write timeout");
                        this.cyr.c(this.cys, this);
                        this.Yz();
                        return false;
                    }
                } else {
                    if (n2 < 0) {
                        a.error((Object)"[WRITE ERROR] Write error");
                        this.cyr.c(this.cys, this);
                        this.Yz();
                        return false;
                    }
                    this.cys.gD(n2);
                    this.cyB = System.currentTimeMillis();
                    int n3 = this.cyu.remaining();
                    this.cyu.compact();
                    if (n3 == 0) {
                        this.ayd();
                        if (this.cyA) {
                            this.close();
                            this.cys.WP();
                            this.release();
                        }
                    } else {
                        this.cys.c(this);
                    }
                }
            } else {
                this.ayd();
                if (this.cyA) {
                    this.close();
                    this.cys.WP();
                    this.release();
                }
            }
        }
        catch (Exception exception) {
            a.error((Object)("[WRITE ERROR] Connection closed (exception=" + exception.toString() + ")"));
            this.cyr.c(this.cys, this);
            this.Yz();
            return false;
        }
        return true;
    }

    public void X(byte[] byArray) {
        if (byArray != null) {
            this.cyw.offer(byArray);
            this.cys.c(this);
        }
    }

    private void Y(byte[] byArray) {
        if (byArray != null && byArray.length > 0) {
            int n2 = this.cyu.limit();
            int n3 = this.cyu.position() + byArray.length;
            if (n3 > n2) {
                ByteBuffer byteBuffer = ByteBuffer.allocate(n3 * 2);
                if (this.cyu.position() > 0) {
                    this.cyu.flip();
                    byteBuffer.put(this.cyu);
                }
                this.cyu = byteBuffer;
            }
            this.cyu.put(byArray);
        } else {
            a.error((Object)"Donn\u00e9es du message inexistantes ou de longueur nulle.");
        }
    }

    public synchronized void Kv() {
    }

    public synchronized InetAddress getInetAddress() {
        if (this.cyp == null) {
            return null;
        }
        Socket socket = this.cyp.socket();
        if (socket == null) {
            return null;
        }
        return socket.getInetAddress();
    }

    public synchronized int getPort() {
        return 0;
    }

    public synchronized String getHost() {
        return "";
    }

    public synchronized int YB() {
        return 0;
    }

    public synchronized int YA() {
        return 0;
    }

    public synchronized int YC() {
        return 0;
    }

    public synchronized String toString() {
        return "(non-persistant [<unknown>] connected=" + this.isConnected() + ")";
    }

    public synchronized boolean Yu() {
        return false;
    }

    void a(acl_0 acl_02) {
        this.uG = acl_02;
    }

    void release() {
        if (this.uG != null) {
            try {
                this.uG.af(this);
            }
            catch (Exception exception) {
                a.error((Object)"Exception lors du release de la connexion : ", (Throwable)exception);
            }
        }
    }
}

