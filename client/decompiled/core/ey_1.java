/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;
import java.io.FilenameFilter;

/*
 * Renamed from Ey
 */
final class ey_1
implements FilenameFilter {
    ey_1() {
    }

    public boolean accept(File file, String string) {
        return string.endsWith(".jar");
    }
}

