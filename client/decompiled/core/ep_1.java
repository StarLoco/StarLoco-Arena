/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;
import java.io.FileInputStream;

/*
 * Renamed from EP
 */
public class ep_1
implements er_0 {
    public boolean isValid() {
        return true;
    }

    public String b(File file) {
        try {
            if (!file.canRead()) {
                return null;
            }
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] byArray = new byte[fileInputStream.available()];
            fileInputStream.read(byArray);
            fileInputStream.close();
            String string = new String(byArray);
            int n2 = string.hashCode();
            return Integer.toString(n2);
        }
        catch (Exception exception) {
            return null;
        }
    }

    public String toString() {
        return "HashvalueAlgorithm";
    }
}

