/*
 * Decompiled with CFR 0.152.
 */
import java.io.Reader;

/*
 * Renamed from EF
 */
public class ef_0
extends aqk_0
implements gx_2 {
    private StringBuffer aTz = new StringBuffer();

    public ef_0() {
    }

    public ef_0(Reader reader) {
        super(reader);
    }

    public final int read() {
        if (!this.aCg()) {
            this.initialize();
            this.bk(true);
        }
        int n2 = -1;
        if (this.aTz.length() == 0) {
            char c;
            n2 = this.in.read();
            if (n2 != -1 && (c = (char)n2) >= '\u0080') {
                this.aTz = new StringBuffer("u0000");
                String string = Integer.toHexString(n2);
                for (int j = 0; j < string.length(); ++j) {
                    this.aTz.setCharAt(this.aTz.length() - string.length() + j, string.charAt(j));
                }
                n2 = 92;
            }
        } else {
            n2 = this.aTz.charAt(0);
            this.aTz.deleteCharAt(0);
        }
        return n2;
    }

    public final Reader b(Reader reader) {
        ef_0 ef_02 = new ef_0(reader);
        ef_02.bk(true);
        return ef_02;
    }

    private void initialize() {
    }
}

