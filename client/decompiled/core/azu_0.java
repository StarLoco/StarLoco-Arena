/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;
import java.io.FilenameFilter;

/*
 * Renamed from azu
 */
class azu_0
implements FilenameFilter {
    final /* synthetic */ apl_1 Ge;

    azu_0(apl_1 apl_12) {
        this.Ge = apl_12;
    }

    public boolean accept(File file, String string) {
        int n2 = string.lastIndexOf(".");
        if (n2 == -1) {
            return false;
        }
        String string2 = string.substring(n2);
        if (".bdat".equalsIgnoreCase(string2)) {
            return true;
        }
        return ".bdat".equalsIgnoreCase(string2);
    }
}

