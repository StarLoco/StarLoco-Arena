/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/*
 * Renamed from uv
 */
public class uv_0
extends ya_2 {
    private final File aqz;
    private final Map aqA = new HashMap();

    public uv_0(File file) {
        this.aqz = file;
    }

    public final String toString() {
        return "dir:" + this.aqz;
    }

    protected final File cx(String string) {
        File file;
        int n2 = string.lastIndexOf(47);
        String string2 = n2 == -1 ? null : string.substring(0, n2).replace('/', File.separatorChar);
        Set set = (Set)this.aqA.get(string2);
        if (set == null) {
            file = string2 == null ? this.aqz : new File(this.aqz, string2);
            File[] fileArray = file.listFiles();
            set = fileArray == null ? Collections.EMPTY_SET : new HashSet<File>(Arrays.asList(fileArray));
            this.aqA.put(string2, set);
        }
        if (!set.contains(file = new File(this.aqz, string.replace('/', File.separatorChar)))) {
            return null;
        }
        return file;
    }
}

