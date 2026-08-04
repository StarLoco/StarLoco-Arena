/*
 * Decompiled with CFR 0.152.
 */
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;

/*
 * Renamed from afi
 */
class afi_1
extends Thread {
    boolean cqA = false;
    final /* synthetic */ lr_0 cqB;

    afi_1(lr_0 lr_02) {
        this.cqB = lr_02;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void run() {
        while (!this.cqA) {
            try {
                afi_1.sleep(this.cqB.bsv);
                this.cqB.ee("Attempting connection to " + this.cqB.address.getHostName());
                Socket socket = new Socket(this.cqB.address, this.cqB.port);
                afi_1 afi_12 = this;
                synchronized (afi_12) {
                    this.cqB.bsu = new ObjectOutputStream(socket.getOutputStream());
                    lr_0.a(this.cqB, null);
                    this.cqB.ee("Connection established. Exiting connector thread.");
                    break;
                }
            }
            catch (InterruptedException interruptedException) {
                this.cqB.ee("Connector interrupted. Leaving loop.");
                return;
            }
            catch (ConnectException connectException) {
                this.cqB.ee("Remote host " + this.cqB.address.getHostName() + " refused connection.");
            }
            catch (IOException iOException) {
                this.cqB.ee("Could not connect to " + this.cqB.address.getHostName() + ". Exception is " + iOException);
            }
        }
    }
}

