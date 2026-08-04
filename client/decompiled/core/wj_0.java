/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.zip.ZipFile;

/*
 * Renamed from Wj
 */
public class wj_0
extends se_1 {
    public wj_0(File[] fileArray) {
        super(wj_0.e(Arrays.asList(fileArray).iterator()));
    }

    public wj_0(Iterator iterator) {
        super(iterator);
    }

    public wj_0(String string) {
        this(wj_0.gB(string));
    }

    private static Iterator e(Iterator iterator) {
        return new ajh_1(iterator);
    }

    public static File[] gB(String string) {
        int n2 = 0;
        ArrayList<File> arrayList = new ArrayList<File>();
        while (true) {
            int n3;
            if ((n3 = string.indexOf(File.pathSeparatorChar, n2)) == -1) {
                if (n2 == string.length()) break;
                arrayList.add(new File(string.substring(n2)));
                break;
            }
            if (n3 != n2) {
                arrayList.add(new File(string.substring(n2, n3)));
            }
            n2 = n3 + 1;
        }
        return arrayList.toArray(new File[arrayList.size()]);
    }

    private static mk D(File file) {
        if ((file.getName().endsWith(".jar") || file.getName().endsWith(".zip")) && file.isFile()) {
            try {
                return new ns_2(new ZipFile(file));
            }
            catch (IOException iOException) {
                return mk.Ji;
            }
        }
        if (file.isDirectory()) {
            return new uv_0(file);
        }
        return mk.Ji;
    }

    static mk E(File file) {
        return wj_0.D(file);
    }
}

