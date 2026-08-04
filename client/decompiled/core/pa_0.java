/*
 * Decompiled with CFR 0.152.
 */
import java.io.DataOutputStream;
import java.io.OutputStream;

/*
 * Renamed from Pa
 */
final class pa_0
extends DataOutputStream {
    private final aja_2 bDd = new aja_2(null);

    public pa_0() {
        super(null);
        this.out = this.bDd;
    }

    public final void c(OutputStream outputStream) {
        this.bDd.c(outputStream);
        this.written = 0;
    }

    public void close() {
        this.bDd.close();
    }
}

