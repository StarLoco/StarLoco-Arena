/*
 * Decompiled with CFR 0.152.
 */
import java.io.Reader;

/*
 * Renamed from aDY
 */
class ady_1
extends and_1 {
    private final gi_1 dzr;

    ady_1(gi_1 gi_12, Reader reader) {
        super(reader);
        this.dzr = gi_12;
    }

    public int read() {
        int n2;
        do {
            if ((n2 = this.in.read()) != -1) continue;
            return n2;
        } while (gi_1.a(this.dzr, (char)n2));
        return n2;
    }
}

