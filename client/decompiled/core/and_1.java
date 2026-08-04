/*
 * Decompiled with CFR 0.152.
 */
import java.io.FilterReader;
import java.io.Reader;
import java.io.StringReader;

/*
 * Renamed from and
 */
public abstract class and_1
extends FilterReader {
    private static final int cq = 8192;
    private boolean initialized = false;
    private UI hL = null;

    public and_1() {
        super(new StringReader(""));
        ga_2.e(this);
    }

    public and_1(Reader reader) {
        super(reader);
    }

    public final int read(char[] cArray, int n2, int n3) {
        for (int j = 0; j < n3; ++j) {
            int n4 = this.read();
            if (n4 == -1) {
                if (j == 0) {
                    return -1;
                }
                return j;
            }
            cArray[n2 + j] = (char)n4;
        }
        return n3;
    }

    public final long skip(long l2) {
        if (l2 < 0L) {
            throw new IllegalArgumentException("skip value is negative");
        }
        for (long j = 0L; j < l2; ++j) {
            if (this.read() != -1) continue;
            return j;
        }
        return l2;
    }

    protected final void bk(boolean bl2) {
        this.initialized = bl2;
    }

    protected final boolean aCg() {
        return this.initialized;
    }

    public final void l(UI uI) {
        this.hL = uI;
    }

    protected final UI TP() {
        return this.hL;
    }

    protected final String readLine() {
        int n2 = this.in.read();
        if (n2 == -1) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        while (n2 != -1) {
            stringBuffer.append((char)n2);
            if (n2 == 10) break;
            n2 = this.in.read();
        }
        return stringBuffer.toString();
    }

    protected final String aCh() {
        return ga_2.a(this.in, 8192);
    }
}

