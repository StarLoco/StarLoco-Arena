/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;
import java.io.IOException;

/*
 * Renamed from aJA
 */
public class aja_0
extends sl_2 {
    private static final ga_2 xa = ga_2.Qo();
    private boolean dRy = true;
    private boolean dRz = false;

    public void fg(boolean bl2) {
        this.dRy = bl2;
    }

    public void fh(boolean bl2) {
        this.dRz = bl2;
    }

    protected boolean i(File file, File file2) {
        if (file.exists() != file2.exists()) {
            return true;
        }
        if (file.length() != file2.length()) {
            return true;
        }
        if (!this.dRy) {
            boolean bl2;
            boolean bl3 = bl2 = file2.lastModified() >= file.lastModified() - (long)this.bLh && file2.lastModified() <= file.lastModified() + (long)this.bLh;
            if (!bl2) {
                return true;
            }
        }
        if (!this.dRz) {
            try {
                return !xa.b(file, file2);
            }
            catch (IOException iOException) {
                throw new eq_2("while comparing " + file + " and " + file2, iOException);
            }
        }
        return false;
    }
}

