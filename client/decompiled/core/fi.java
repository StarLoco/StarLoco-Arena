/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;
import java.io.FilenameFilter;

class fi
implements FilenameFilter {
    fi() {
    }

    public boolean accept(File file, String string) {
        return string.endsWith(".ogg");
    }
}

