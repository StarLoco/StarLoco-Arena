/*
 * Decompiled with CFR 0.152.
 */
import java.io.BufferedInputStream;
import java.io.InputStream;

/*
 * Renamed from aAK
 */
class aak_1
extends BufferedInputStream {
    public aak_1(InputStream inputStream) {
        super(inputStream);
    }

    public aak_1(InputStream inputStream, int n2) {
        super(inputStream, n2);
    }

    public void b(InputStream inputStream) {
        this.in = inputStream;
        this.pos = 0;
        this.count = 0;
        this.marklimit = 0;
        this.markpos = -1;
        this.buf = new byte[8192];
    }

    public void close() {
        super.close();
    }
}

