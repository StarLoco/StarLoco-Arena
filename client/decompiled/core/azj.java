/*
 * Decompiled with CFR 0.152.
 */
import java.io.InputStream;

public class azj
extends InputStream {
    private InputStream dnr;

    public azj(InputStream inputStream) {
        this.dnr = inputStream;
    }

    public int read() {
        int n2 = this.dnr.read();
        if (n2 == -1) {
            return -1;
        }
        return (n2 - 1 + 256) % 256;
    }

    public int read(byte[] byArray) {
        return this.read(byArray, 0, byArray.length);
    }

    public int read(byte[] byArray, int n2, int n3) {
        int n4 = this.dnr.read(byArray, n2, n3);
        int n5 = n2 + n3;
        for (int j = n2; j < n5; ++j) {
            byArray[j] = (byte)((byArray[j] - 1 + 256) % 256);
        }
        return n4;
    }

    public long skip(long l2) {
        return this.dnr.skip(l2);
    }

    public int available() {
        return this.dnr.available();
    }

    public void close() {
        this.dnr.close();
    }

    public void mark(int n2) {
        this.dnr.mark(n2);
    }

    public void reset() {
        this.dnr.reset();
    }

    public boolean markSupported() {
        return this.dnr.markSupported();
    }
}

