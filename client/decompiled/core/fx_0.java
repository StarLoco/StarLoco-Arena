/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;
import java.io.FilenameFilter;
import java.util.Locale;

/*
 * Renamed from fX
 */
final class fx_0
implements FilenameFilter {
    private final String[] sb;

    fx_0(String[] stringArray) {
        this.sb = stringArray;
    }

    public boolean accept(File file, String string) {
        String string2 = string.toLowerCase(Locale.US);
        for (int j = 0; j < this.sb.length; ++j) {
            if (!string2.endsWith(this.sb[j])) continue;
            return true;
        }
        return false;
    }
}

