/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import org.apache.log4j.Logger;

public class bA
extends ka_2 {
    private static Logger a = Logger.getLogger(bA.class);
    public static Logger gO = Logger.getLogger((String)"Traceur");
    protected int gP = 500;
    protected int gQ = Integer.MAX_VALUE;

    public bA(RE rE) {
        super(rE);
        this.boy = new alt_2(new jp_0(this));
        this.bp("listener");
    }

    public void dc() {
    }

    public void z(int n2) {
        this.gP = n2;
    }

    public void A(int n2) {
        this.gQ = n2;
    }

    public synchronized mu_0 c(String string, int n2) {
        mu_0 mu_02 = (mu_0)this.WR();
        if (mu_02 != null) {
            mu_02.setHost(string);
            mu_02.setPort(n2);
            this.boG.offer(new akp_0(vc_2.atn, mu_02));
            this.box.wakeup();
        }
        return mu_02;
    }

    protected void a(mu_0 mu_02) {
        block8: {
            if (mu_02 != null) {
                SocketChannel socketChannel = this.dd();
                if (socketChannel != null) {
                    this.b((ait_0)mu_02);
                    mu_02.A(this.gQ);
                    mu_02.z(this.gP);
                    try {
                        InetSocketAddress inetSocketAddress = new InetSocketAddress(mu_02.getHost(), mu_02.getPort());
                        boolean bl2 = socketChannel.connect(inetSocketAddress);
                        mu_02.b(socketChannel);
                        if (bl2) {
                            mu_02.ayb();
                            mu_02.bQ(true);
                            mu_02.bP(false);
                            if (!this.boz.a(this, mu_02)) {
                                a.error((Object)"onNewConnection failed");
                                mu_02.Yw();
                            }
                            break block8;
                        }
                        mu_02.aya();
                    }
                    catch (ConnectException connectException) {
                        a.error((Object)("Une exception dans l'ouverture de la connection a ete levee (" + mu_02 + ")"), (Throwable)connectException);
                        this.boz.c(this, mu_02);
                        mu_02.Yw();
                    }
                    catch (Exception exception) {
                        this.boz.c(this, mu_02);
                        a.error((Object)("Une exception dans l'ouverture de la connection a ete levee (" + mu_02 + ")"), (Throwable)exception);
                    }
                }
            } else {
                a.error((Object)"Unable to create a connection");
            }
        }
    }

    SocketChannel dd() {
        SocketChannel socketChannel;
        try {
            socketChannel = SocketChannel.open();
        }
        catch (IOException iOException) {
            a.error((Object)"Impossible de g\u00e9n\u00e9rer un nouveau SocketChannel.");
            return null;
        }
        try {
            socketChannel.configureBlocking(false);
            Socket socket = socketChannel.socket();
            socket.setReuseAddress(true);
            socket.setKeepAlive(true);
            socket.setTcpNoDelay(true);
            socket.setTrafficClass(128);
            return socketChannel;
        }
        catch (Throwable throwable) {
            a.error((Object)"generateNewSocketChannel() a lev\u00e9 une exception :", throwable);
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void run() {
        a.info((Object)("Client ConnectionHandler started: " + this.toString()));
        block10: while (true) {
            if (!this.cX) {
                a.info((Object)"ConnectorConnectionHandler stopped");
                return;
            }
            try {
                block11: while (true) {
                    if (!this.cX) continue block10;
                    this.Us();
                    int n2 = 0;
                    try {
                        n2 = this.box.select(bow);
                    }
                    catch (Throwable throwable) {
                        a.error((Object)"select() exception : ", throwable);
                    }
                    if (n2 <= 0) continue;
                    try {
                        Set<SelectionKey> set = this.box.selectedKeys();
                        Iterator<SelectionKey> iterator = set.iterator();
                        while (true) {
                            mu_0 mu_02;
                            if (!iterator.hasNext()) continue block11;
                            SelectionKey selectionKey = iterator.next();
                            iterator.remove();
                            try {
                                Object object;
                                block19: {
                                    block20: {
                                        boolean bl2;
                                        boolean bl3;
                                        if (!selectionKey.isValid() || !selectionKey.isConnectable()) break block19;
                                        object = (SocketChannel)selectionKey.channel();
                                        if (!((SocketChannel)object).isConnectionPending()) {
                                            a.error((Object)"Le s\u00e9lecteur a retourn\u00e9 une clef connectable qui n'est pas connection_pending.");
                                            continue;
                                        }
                                        mu_02 = (mu_0)selectionKey.attachment();
                                        try {
                                            bl3 = ((SocketChannel)object).finishConnect();
                                        }
                                        catch (ConnectException connectException) {
                                            bl3 = false;
                                        }
                                        if (!bl3) break block20;
                                        boolean bl4 = mu_02.Yu();
                                        boolean bl5 = mu_02.YD();
                                        mu_02.bP(false);
                                        mu_02.bQ(true);
                                        mu_02.ayb();
                                        this.WO();
                                        boolean bl6 = bl2 = !bl4 || !bl5;
                                        if (bl2) {
                                            System.out.println("Connect\u00e9 \u00e0 l'h\u00f4te " + mu_02.getHost() + ":" + mu_02.getPort());
                                            if (!this.boz.a(this, mu_02)) {
                                                mu_02.close();
                                                this.WP();
                                                mu_02.release();
                                            }
                                            break block19;
                                        } else {
                                            System.out.println("Reconnect\u00e9 \u00e0 l'h\u00f4te " + mu_02.getHost() + ":" + mu_02.getPort());
                                            if (!this.boz.e(this, mu_02)) {
                                                mu_02.close();
                                                this.WP();
                                                mu_02.release();
                                            }
                                        }
                                        break block19;
                                    }
                                    mu_02.Yw();
                                }
                                object = (ait_0)selectionKey.attachment();
                                if (selectionKey.isValid() && selectionKey.isWritable() && !((ait_0)object).ayj() || !selectionKey.isValid() || !selectionKey.isReadable() || this.boz.b(this, (ait_0)object)) continue;
                                ((ait_0)object).close();
                                this.WP();
                                ((ait_0)object).release();
                            }
                            catch (Throwable throwable) {
                                a.error((Object)"Exception en traitant une clef dans le ConnectorConnectionHandler : ", throwable);
                                mu_02 = (mu_0)selectionKey.attachment();
                                this.boz.c(this, mu_02);
                                mu_02.Yw();
                            }
                        }
                    }
                    catch (Throwable throwable) {
                        a.error((Object)"Exception dans la loop interne du ConnectorConnectionHandler : ", throwable);
                        continue;
                    }
                    break;
                }
            }
            catch (Throwable throwable) {
                a.error((Object)"Exception dans la loop externe du ConnectorConnectionHandler : ", throwable);
                continue;
            }
            break;
        }
    }
}

