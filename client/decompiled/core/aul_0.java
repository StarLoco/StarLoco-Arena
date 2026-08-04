/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;
import java.io.FilenameFilter;

/*
 * Renamed from aul
 */
class aul_0
implements FilenameFilter {
    final /* synthetic */ fh_1 cVZ;

    aul_0(fh_1 fh_12) {
        this.cVZ = fh_12;
    }

    public boolean accept(File file, String string) {
        return string.endsWith(this.cVZ.getExtension());
    }
}

