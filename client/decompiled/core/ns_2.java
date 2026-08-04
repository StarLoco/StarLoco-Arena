/*
 * Decompiled with CFR 0.152.
 */
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/*
 * Renamed from NS
 */
public class ns_2
extends mk {
    private final ZipFile bAK;

    public ns_2(ZipFile zipFile) {
        this.bAK = zipFile;
    }

    public final String toString() {
        return "zip:" + this.bAK.getName();
    }

    public final any_2 aU(String string) {
        ZipEntry zipEntry = this.bAK.getEntry(string);
        if (zipEntry == null) {
            return null;
        }
        return new amm_0(this, zipEntry, string);
    }

    static ZipFile a(ns_2 ns_22) {
        return ns_22.bAK;
    }
}

