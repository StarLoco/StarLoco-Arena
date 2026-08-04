/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import org.apache.log4j.Logger;

/*
 * Renamed from Mu
 */
public class mu_0
extends ait_0 {
    private static final Logger a = Logger.getLogger(mu_0.class);
    private static final boolean cR = false;
    protected String btW;
    protected int btX;
    protected int btY = 0;
    protected int gQ = Integer.MAX_VALUE;
    protected int gP = 500;
    protected boolean btZ;
    protected boolean bua;
    protected boolean bub;
    private final Runnable buc = new ajn_0(this);

    protected void Yt() {
        super.Yt();
        this.btZ = false;
        this.btY = 0;
        this.gQ = Integer.MAX_VALUE;
        this.gP = 500;
        this.bub = false;
        this.bua = false;
        this.btW = "";
        this.btX = 0;
    }

    public synchronized boolean Yu() {
        return this.btZ;
    }

    void bP(boolean bl2) {
        this.btZ = bl2;
    }

    public synchronized boolean Yv() {
        return true;
    }

    void Yw() {
        if (this.cys == null) {
            a.error((Object)"Impossible de lancer la reconnexion, aucun ConnectionHandler n'est d\u00e9fini.");
            return;
        }
        if (this.btY >= this.gQ) {
            if (!this.bua) {
                a.warn((Object)"Limite de reconnexion d\u00e9pass\u00e9e.");
                this.bua = true;
                this.close();
                this.cys.WP();
                this.release();
            }
            return;
        }
        if (!this.Yu()) {
            a.info((Object)("Lancement de la proc\u00e9dure de reconnexion pour " + this));
            this.btZ = true;
            this.btY = 0;
            this.cyr.f(this.cys, this);
        }
        try {
            if (this.cyp != null) {
                this.aye();
            }
            this.b(null);
            this.cys.b(this);
        }
        catch (Throwable throwable) {
            a.error((Object)("Exception lors de la reconnexion " + this.toString() + " :"), throwable);
        }
    }

    void Yx() {
        ++this.btY;
        try {
            a.info((Object)("Tentative de reconnexion #" + this.btY + "/" + this.gQ + " \u00e0 l'h\u00f4te " + this.btW + ":" + this.btX));
            SocketChannel socketChannel = ((bA)this.cys).dd();
            if (socketChannel != null) {
                if (this.cyp != null) {
                    this.aye();
                }
                boolean bl2 = socketChannel.connect(new InetSocketAddress(this.btW, this.btX));
                this.b(socketChannel);
                if (bl2) {
                    boolean bl3;
                    a.info((Object)"(Re)connexion instantan\u00e9e.");
                    this.bub = true;
                    this.btZ = false;
                    this.ayb();
                    boolean bl4 = bl3 = !this.bub || !this.btZ;
                    if (bl3) {
                        if (!this.cyr.a(this.cys, this)) {
                            this.Yw();
                        }
                    } else if (!this.cyr.e(this.cys, this)) {
                        this.Yw();
                    }
                } else {
                    this.aya();
                }
            } else {
                a.warn((Object)("Impossible de cr\u00e9er un nouveau SocketChannel " + this.toString()));
                this.Yw();
            }
        }
        catch (IOException iOException) {
            a.warn((Object)("Exception lev\u00e9e " + this.toString() + " lors de la tentative de reconnexion : "), (Throwable)iOException);
            this.Yw();
        }
    }

    protected ByteBuffer Yy() {
        super.Yy();
        this.Yw();
        this.cyt.flip();
        return this.cyt;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void Yz() {
        Object object = this.cyv;
        synchronized (object) {
            this.cyu.clear();
        }
        this.Yw();
    }

    public synchronized String getHost() {
        return this.btW;
    }

    void setHost(String string) {
        this.btW = string.intern();
    }

    public synchronized int getPort() {
        return this.btX;
    }

    void setPort(int n2) {
        this.btX = n2;
    }

    public synchronized int YA() {
        return this.btY;
    }

    public synchronized int YB() {
        return this.gQ;
    }

    public synchronized void A(int n2) {
        this.gQ = n2;
    }

    public synchronized int YC() {
        return this.gP;
    }

    public synchronized void z(int n2) {
        this.gP = n2;
    }

    public String toString() {
        return "(persistant [" + this.btW + ":" + this.btX + "] connected=" + this.isConnected() + ")";
    }

    public synchronized boolean YD() {
        return this.bub;
    }

    void bQ(boolean bl2) {
        this.bub = bl2;
    }

    public synchronized boolean YE() {
        return this.bua;
    }
}

