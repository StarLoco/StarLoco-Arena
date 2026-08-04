/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/*
 * Renamed from ayq
 */
public class ayq_0
implements any_2 {
    private final File ll;

    public ayq_0(File file) {
        this.ll = file;
    }

    public final String getFileName() {
        return this.ll.toString();
    }

    public final InputStream aBH() {
        return new FileInputStream(this.ll);
    }

    public final long lastModified() {
        return this.ll.lastModified();
    }

    public final File getFile() {
        return this.ll;
    }

    public final String toString() {
        return this.getFileName();
    }
}

