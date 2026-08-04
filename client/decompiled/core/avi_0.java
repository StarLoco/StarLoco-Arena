/*
 * Decompiled with CFR 0.152.
 */
import java.net.InetAddress;

/*
 * Renamed from avI
 */
public class avi_0
extends lr_0 {
    boolean dfN = false;

    public avi_0() {
    }

    public avi_0(InetAddress inetAddress, int n2) {
        this.address = inetAddress;
        this.bst = inetAddress.getHostName();
        this.port = n2;
    }

    public avi_0(String string, int n2) {
        this.port = n2;
        this.address = avi_0.fh(string);
        this.bst = string;
    }

    protected void i(tz_0 tz_02) {
        if (this.dfN) {
            tz_02.agv();
        }
    }

    public void ew(boolean bl2) {
        this.dfN = bl2;
    }
}

