/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;
import java.io.FilenameFilter;

/*
 * Renamed from gA
 */
class ga_1
implements FilenameFilter {
    private final hd_1 uc;

    ga_1(hd_1 hd_12) {
        this.uc = hd_12;
    }

    public boolean accept(File file, String string) {
        return string.endsWith(".jar");
    }
}

