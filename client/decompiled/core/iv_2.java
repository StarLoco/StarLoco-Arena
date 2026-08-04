/*
 * Decompiled with CFR 0.152.
 */
import java.io.FilterReader;
import java.io.Reader;
import java.io.Writer;

/*
 * Renamed from iv
 */
public class iv_2
extends FilterReader {
    private final Writer out;
    private final boolean yr;

    public iv_2(Reader reader, Writer writer, boolean bl2) {
        super(reader);
        this.out = writer;
        this.yr = bl2;
    }

    public void close() {
        this.in.close();
        this.out.close();
    }

    public int read() {
        int n2 = this.in.read();
        if (n2 == -1) {
            if (this.yr) {
                this.out.close();
            } else {
                this.out.flush();
            }
        } else {
            this.out.write(n2);
        }
        return n2;
    }

    public int read(char[] cArray, int n2, int n3) {
        int n4 = this.in.read(cArray, n2, n3);
        if (n4 == -1) {
            if (this.yr) {
                this.out.close();
            } else {
                this.out.flush();
            }
        } else {
            this.out.write(cArray, n2, n4);
        }
        return n4;
    }
}

