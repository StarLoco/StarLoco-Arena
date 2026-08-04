/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.io.IOException;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import org.apache.log4j.Logger;

/*
 * Renamed from agA
 */
public class aga_1
extends ka_2 {
    private static final int cuq = 1024;
    private static final int cur = 5;
    private static Logger a = Logger.getLogger(aga_1.class);
    protected ServerSocketChannel cus;
    protected int cut = 0;
    protected String cuu = "";
    protected int cuv = 0;
    protected aer_1[] cuw = new aer_1[5];

    public aga_1(RE rE) {
        super(rE);
        this.bp("listener");
        this.boy = new ym_0(new axz_0(this));
        try {
            this.cus = ServerSocketChannel.open();
        }
        catch (Exception exception) {
            a.error((Object)exception);
            this.boz.a(this);
        }
    }

    public void u(String string, int n2) {
        this.cuu = string;
        this.cut = n2;
        this.setName(this.getName() + "-listener-port-" + string + ":" + n2);
        try {
            this.cus.socket().setReuseAddress(true);
            this.cus.socket().bind(new InetSocketAddress(this.cuu, this.cut), 1024);
            this.cus.configureBlocking(false);
            this.cus.register(this.box, 16);
            for (int j = 0; j < 5; ++j) {
                this.cuw[j] = new aer_1(this.boz);
                this.cuw[j].setName(this.getName() + "-slave-" + j);
                this.cuw[j].start();
            }
        }
        catch (BindException bindException) {
            a.error((Object)("Ouverture de socket impossible sur " + this.cuu + ":" + this.cut + ". Port probablement d\u00e9j\u00e0 utilis\u00e9."));
            this.boz.b(this);
        }
        catch (IOException iOException) {
            a.error((Object)iOException);
            this.boz.b(this);
        }
        a.info((Object)(this.getName() + " initialized: server mode."));
    }

    public int awv() {
        return this.cut;
    }

    public String aww() {
        return this.cuu;
    }

    private aer_1 awx() {
        if (this.cuv >= this.cuw.length) {
            this.cuv = 0;
        }
        return this.cuw[this.cuv++];
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void run() {
        a.info((Object)("Server ConnectionHandler started : bindAddress=" + this.cuu + ", bindPort=" + this.cut + ", " + this.toString()));
        block10: while (true) {
            if (!this.cX) {
                a.info((Object)"ListenerConnectionHandler stopped");
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
                            if (!iterator.hasNext()) continue block11;
                            SelectionKey selectionKey = iterator.next();
                            iterator.remove();
                            try {
                                if (!selectionKey.isValid() || !selectionKey.isAcceptable()) continue;
                                try {
                                    SocketChannel socketChannel = this.cus.accept();
                                    if (socketChannel == null) continue;
                                    aer_1 aer_12 = this.awx();
                                    aer_12.a(socketChannel);
                                }
                                catch (Throwable throwable) {
                                    a.error((Object)"accept() exception : ", throwable);
                                }
                            }
                            catch (Throwable throwable) {
                                a.error((Object)"Exception en traitant une clef dans le ListenerConnectionHandler : ", throwable);
                                ait_0 ait_02 = (ait_0)selectionKey.attachment();
                                this.boz.c(this, ait_02);
                                ait_02.close();
                                ait_02.release();
                            }
                        }
                    }
                    catch (Throwable throwable) {
                        a.error((Object)"Exception dans la loop interne du ListenerConnectionHandler : ", throwable);
                        continue;
                    }
                    break;
                }
            }
            catch (Throwable throwable) {
                a.error((Object)"Exception dans la loop externe du ListenerConnectionHandler : ", throwable);
                continue;
            }
            break;
        }
    }
}

