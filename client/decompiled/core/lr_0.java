/*
 * Decompiled with CFR 0.152.
 */
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/*
 * Renamed from LR
 */
public abstract class lr_0
extends ahT {
    static final int DEFAULT_PORT = 4560;
    static final int bss = 30000;
    protected String bst;
    protected InetAddress address;
    protected int port = 4560;
    protected ObjectOutputStream bsu;
    protected int bsv = 30000;
    private afi_1 bsw;
    protected int counter = 0;

    public void start() {
        int n2 = 0;
        if (this.port == 0) {
            ++n2;
            this.eg("No port was configured for appender" + this.name + " For more information, please visit http://logback.qos.ch/codes.html#socket_no_port");
        }
        if (this.address == null) {
            ++n2;
            this.eg("No remote address was configured for appender" + this.name + " For more information, please visit http://logback.qos.ch/codes.html#socket_no_host");
        }
        this.connect(this.address, this.port);
        if (n2 == 0) {
            this.bgs = true;
        }
    }

    public void stop() {
        if (!this.isStarted()) {
            return;
        }
        this.bgs = false;
        this.cleanUp();
    }

    public void cleanUp() {
        if (this.bsu != null) {
            try {
                this.bsu.close();
            }
            catch (IOException iOException) {
                this.e("Could not close oos.", iOException);
            }
            this.bsu = null;
        }
        if (this.bsw != null) {
            this.ee("Interrupting the connector.");
            this.bsw.cqA = true;
            this.bsw = null;
        }
    }

    void connect(InetAddress inetAddress, int n2) {
        if (this.address == null) {
            return;
        }
        try {
            this.cleanUp();
            this.bsu = new ObjectOutputStream(new Socket(inetAddress, n2).getOutputStream());
        }
        catch (IOException iOException) {
            String string = "Could not connect to remote logback server at [" + inetAddress.getHostName() + "].";
            if (this.bsv > 0) {
                string = string + " We will try again later.";
                this.Ye();
            }
            this.e(string, iOException);
        }
    }

    protected void z(Object object) {
        block9: {
            if (object == null) {
                return;
            }
            if (this.address == null) {
                this.eg("No remote host is set for SocketAppender named \"" + this.name + "\". For more information, please visit http://logback.qos.ch/codes.html#socket_no_host");
                return;
            }
            if (this.bsu != null) {
                try {
                    this.Z(object);
                    this.bsu.writeObject(object);
                    this.bsu.flush();
                    if (++this.counter >= 70) {
                        this.counter = 0;
                        this.bsu.reset();
                    }
                }
                catch (IOException iOException) {
                    if (this.bsu != null) {
                        try {
                            this.bsu.close();
                        }
                        catch (IOException iOException2) {
                            // empty catch block
                        }
                    }
                    this.bsu = null;
                    this.ef("Detected problem with connection: " + iOException);
                    if (this.bsv <= 0) break block9;
                    this.Ye();
                }
            }
        }
    }

    protected abstract void Z(Object var1);

    void Ye() {
        if (this.bsw == null) {
            this.ee("Starting a new connector thread.");
            this.bsw = new afi_1(this);
            this.bsw.setDaemon(true);
            this.bsw.setPriority(1);
            this.bsw.start();
        }
    }

    protected static InetAddress fh(String string) {
        try {
            return InetAddress.getByName(string);
        }
        catch (Exception exception) {
            return null;
        }
    }

    public void setRemoteHost(String string) {
        this.address = lr_0.fh(string);
        this.bst = string;
    }

    public String getRemoteHost() {
        return this.bst;
    }

    public void setPort(int n2) {
        this.port = n2;
    }

    public int getPort() {
        return this.port;
    }

    public void setReconnectionDelay(int n2) {
        this.bsv = n2;
    }

    public int getReconnectionDelay() {
        return this.bsv;
    }

    static /* synthetic */ afi_1 a(lr_0 lr_02, afi_1 afi_12) {
        lr_02.bsw = afi_12;
        return lr_02.bsw;
    }
}

