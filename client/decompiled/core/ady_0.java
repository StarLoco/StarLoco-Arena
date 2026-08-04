/*
 * Decompiled with CFR 0.152.
 */
import java.io.InputStream;

/*
 * Renamed from aDy
 */
public class ady_0
extends InputStream {
    private UI hL;

    public ady_0(UI uI) {
        this.hL = uI;
    }

    public int read() {
        byte[] byArray = new byte[1];
        if (this.hL.e(byArray, 0, 1) == -1) {
            return -1;
        }
        return byArray[0];
    }

    public int read(byte[] byArray, int n2, int n3) {
        return this.hL.e(byArray, n2, n3);
    }
}

