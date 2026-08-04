/*
 * Decompiled with CFR 0.152.
 */
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.zip.CRC32;

/*
 * Renamed from Vq
 */
public class vq_2 {
    private static final boolean bSy = true;

    public static URL getURL(String string) {
        try {
            return new URL(string);
        }
        catch (MalformedURLException malformedURLException) {
            File file = new File(string);
            return file.toURI().toURL();
        }
    }

    public static byte[] readFile(String string) {
        InputStream inputStream = vq_2.gm(string);
        byte[] byArray = vq_2.l(inputStream);
        inputStream.close();
        return byArray;
    }

    public static InputStream gm(String string) {
        BufferedInputStream bufferedInputStream;
        try {
            bufferedInputStream = new BufferedInputStream(new URL(string).openStream());
        }
        catch (Exception exception) {
            String string2 = "file://";
            int n2 = string.indexOf("file://");
            String string3 = string;
            if (n2 >= 0) {
                string3 = string.substring(n2 + "file://".length());
            }
            File file = new File(string3);
            bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
        }
        return bufferedInputStream;
    }

    public static int a(InputStream inputStream, byte[] byArray, int n2, int n3) {
        int n4 = 0;
        int n5 = 0;
        while ((n4 = inputStream.read(byArray, n2 + n5, n3 - n5)) != -1 && (n5 += n4) != n3) {
        }
        return n5;
    }

    public static byte[] l(InputStream inputStream) {
        int n2;
        byte[] byArray = new byte[inputStream.available()];
        int n3 = 0;
        while ((n2 = inputStream.read(byArray, n3, byArray.length - n3)) != 0 && (n3 += n2) != byArray.length) {
        }
        assert (n3 == byArray.length);
        return byArray;
    }

    public static boolean gn(String string) {
        File file = new File(string);
        return file.exists();
    }

    public static void go(String string) {
        File file = new File(string);
        if (!file.exists()) {
            return;
        }
        if (!file.isFile()) {
            return;
        }
        file.delete();
    }

    public static void gp(String string) {
        vq_2.p(string, false);
    }

    public static void p(String string, boolean bl2) {
        File file = new File(string);
        if (!file.exists()) {
            return;
        }
        File[] fileArray = file.listFiles();
        if (fileArray == null) {
            return;
        }
        for (File file2 : fileArray) {
            if (file2.isDirectory()) {
                vq_2.gp(file2.getPath());
                continue;
            }
            file2.delete();
        }
        if (!bl2) {
            file.delete();
        }
    }

    public static String aim() {
        CRC32 cRC32 = new CRC32();
        cRC32.update(("" + System.nanoTime()).getBytes());
        return "" + cRC32.getValue();
    }

    public static void t(String string, String string2) {
        byte[] byArray = vq_2.readFile(string);
        FileOutputStream fileOutputStream = vq_2.gw(string2);
        fileOutputStream.write(byArray);
        fileOutputStream.close();
    }

    public static void a(aij_1 aij_12, boolean bl2) {
        if (bl2) {
            aij_12.writeByte((byte)1);
        } else {
            aij_12.writeByte((byte)0);
        }
    }

    public static String getName(String string) {
        int n2 = vq_2.gv(string);
        return string.substring(n2 + 1);
    }

    public static String getPath(String string) {
        int n2 = vq_2.gv(string);
        return string.substring(0, n2);
    }

    public static String gq(String string) {
        int n2 = vq_2.gv(string);
        return string.substring(0, n2 + 1);
    }

    public static String gr(String string) {
        int n2 = string.lastIndexOf(46);
        if (n2 == -1) {
            return "";
        }
        return string.substring(n2 + 1);
    }

    public static String gs(String string) {
        int n2 = vq_2.gv(string) + 1;
        int n3 = string.lastIndexOf(46);
        if (n3 < 0) {
            return string.substring(n2);
        }
        return string.substring(n2, n3);
    }

    public static String gt(String string) {
        String string2 = string.replace('\\', '/');
        int n2 = string2.lastIndexOf(47);
        if (n2 == string2.length() - 1) {
            n2 = string2.substring(0, n2).lastIndexOf(47);
        }
        return string2.substring(0, n2);
    }

    public static String gu(String string) {
        String string2 = string.replace('\\', '/');
        int n2 = string2.lastIndexOf(47);
        if (n2 == string2.length() - 1) {
            n2 = string2.substring(0, n2).lastIndexOf(47);
            return string2.substring(n2 + 1, string2.length() - 1);
        }
        return string2.substring(n2 + 1, string2.length());
    }

    private static int gv(String string) {
        int n2 = string.lastIndexOf(47);
        if (n2 >= 0) {
            return n2;
        }
        return string.lastIndexOf(92);
    }

    public static FileOutputStream gw(String string) {
        File file = new File(string);
        File file2 = file.getParentFile();
        if (file2 != null) {
            file2.mkdirs();
        }
        return new FileOutputStream(file);
    }

    public static void a(String string, byte[] byArray) {
        if (string == null) {
            throw new IllegalArgumentException("Argument 0 for @NotNull parameter of com/ankamagames/framework/fileFormat/io/FileHelper.write must not be null");
        }
        if (byArray == null) {
            throw new IllegalArgumentException("Argument 1 for @NotNull parameter of com/ankamagames/framework/fileFormat/io/FileHelper.write must not be null");
        }
        FileOutputStream fileOutputStream = vq_2.gw(string);
        fileOutputStream.write(byArray);
        fileOutputStream.close();
    }

    public static boolean C(File file) {
        if (!file.exists()) {
            return false;
        }
        File[] fileArray = file.listFiles();
        if (fileArray == null) {
            return false;
        }
        for (File file2 : fileArray) {
            if (file2.isDirectory()) {
                vq_2.C(file2);
                continue;
            }
            file2.delete();
        }
        return file.delete();
    }
}

