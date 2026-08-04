/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Pattern;

class azt
implements FilenameFilter {
    final /* synthetic */ Pattern dnE;
    final /* synthetic */ apl_1 Ge;

    azt(apl_1 apl_12, Pattern pattern) {
        this.Ge = apl_12;
        this.dnE = pattern;
    }

    public boolean accept(File file, String string) {
        return this.dnE.matcher(string).matches();
    }
}

