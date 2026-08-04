/*
 * Decompiled with CFR 0.152.
 */
import java.io.BufferedOutputStream;
import java.io.OutputStream;

/*
 * Renamed from ajA
 */
class aja_2
extends BufferedOutputStream {
    public aja_2(OutputStream outputStream) {
        super(outputStream);
    }

    public aja_2(OutputStream outputStream, int n2) {
        super(outputStream, n2);
    }

    public void c(OutputStream outputStream) {
        this.out = outputStream;
        this.count = 0;
    }
}

