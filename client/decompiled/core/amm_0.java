/*
 * Decompiled with CFR 0.152.
 */
import java.io.InputStream;
import java.util.zip.ZipEntry;

/*
 * Renamed from amm
 */
class amm_0
implements any_2 {
    private final ZipEntry cGE;
    private final String cGF;
    private final ns_2 cGG;

    amm_0(ns_2 ns_22, ZipEntry zipEntry, String string) {
        this.cGG = ns_22;
        this.cGE = zipEntry;
        this.cGF = string;
    }

    public InputStream aBH() {
        return ns_2.a(this.cGG).getInputStream(this.cGE);
    }

    public String getFileName() {
        return ns_2.a(this.cGG).getName() + ':' + this.cGF;
    }

    public long lastModified() {
        long l2 = this.cGE.getTime();
        return l2 == -1L ? 0L : l2;
    }

    public String toString() {
        return this.getFileName();
    }
}

