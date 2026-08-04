/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;
import java.io.FilenameFilter;

/*
 * Renamed from xQ
 */
class xq_1
implements FilenameFilter {
    xq_1() {
    }

    public boolean accept(File file, String string) {
        return string.endsWith(".lua");
    }
}

