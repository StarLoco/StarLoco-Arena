/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;
import java.io.FilenameFilter;

/*
 * Renamed from Uw
 */
class uw_1
implements FilenameFilter {
    private final String bPV;
    private final ga_2 bPL;

    uw_1(ga_2 ga_22, String string) {
        this.bPL = ga_22;
        this.bPV = string;
    }

    public boolean accept(File file, String string) {
        return string.equalsIgnoreCase(this.bPV) && !string.equals(this.bPV);
    }
}

