/*
 * Decompiled with CFR 0.152.
 */
import java.io.DataInputStream;
import java.io.InputStream;

/*
 * Renamed from cs
 */
final class cs_1
extends DataInputStream {
    private final aak_1 iW = new aak_1(null);

    public cs_1() {
        super(null);
        this.in = this.iW;
    }

    public final void b(InputStream inputStream) {
        this.iW.b(inputStream);
    }

    public void close() {
        this.iW.close();
    }
}

