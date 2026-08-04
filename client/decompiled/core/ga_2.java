/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.Method;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.Vector;

/*
 * Renamed from GA
 */
public class ga_2 {
    private static final int bbg = 50;
    private static final ga_2 bbh = new ga_2();
    private static Random bbi = new Random(System.currentTimeMillis() + Runtime.getRuntime().freeMemory());
    private static final boolean bbj = xk_1.cO("netware");
    private static final boolean bbk = xk_1.cO("dos");
    private static final boolean bbl = xk_1.cO("win9x");
    private static final boolean bbm = xk_1.cO("windows");
    static final int BUF_SIZE = 8192;
    public static final long bbn = 2000L;
    public static final long bbo = 1000L;
    public static final long bbp = 1L;
    private Object bbq = new Object();
    private String bbr = null;
    private String bbs = null;
    static Class bbt;

    public static ga_2 Qn() {
        return new ga_2();
    }

    public static ga_2 Qo() {
        return bbh;
    }

    protected ga_2() {
    }

    public URL o(File file) {
        return new URL(this.eb(file.getAbsolutePath()));
    }

    public void t(String string, String string2) {
        this.a(new File(string), new File(string2), null, false, false);
    }

    public void a(String string, String string2, agd_2 agd_22) {
        this.a(new File(string), new File(string2), agd_22, false, false);
    }

    public void a(String string, String string2, agd_2 agd_22, boolean bl2) {
        this.a(new File(string), new File(string2), agd_22, bl2, false);
    }

    public void a(String string, String string2, agd_2 agd_22, boolean bl2, boolean bl3) {
        this.a(new File(string), new File(string2), agd_22, bl2, bl3);
    }

    public void a(String string, String string2, agd_2 agd_22, boolean bl2, boolean bl3, String string3) {
        this.a(new File(string), new File(string2), agd_22, bl2, bl3, string3);
    }

    public void a(String string, String string2, agd_2 agd_22, Vector vector, boolean bl2, boolean bl3, String string3, UI uI) {
        this.a(new File(string), new File(string2), agd_22, vector, bl2, bl3, string3, uI);
    }

    public void a(String string, String string2, agd_2 agd_22, Vector vector, boolean bl2, boolean bl3, String string3, String string4, UI uI) {
        this.a(new File(string), new File(string2), agd_22, vector, bl2, bl3, string3, string4, uI);
    }

    public void a(File file, File file2) {
        this.a(file, file2, null, false, false);
    }

    public void a(File file, File file2, agd_2 agd_22) {
        this.a(file, file2, agd_22, false, false);
    }

    public void a(File file, File file2, agd_2 agd_22, boolean bl2) {
        this.a(file, file2, agd_22, bl2, false);
    }

    public void a(File file, File file2, agd_2 agd_22, boolean bl2, boolean bl3) {
        this.a(file, file2, agd_22, bl2, bl3, null);
    }

    public void a(File file, File file2, agd_2 agd_22, boolean bl2, boolean bl3, String string) {
        this.a(file, file2, agd_22, null, bl2, bl3, string, null);
    }

    public void a(File file, File file2, agd_2 agd_22, Vector vector, boolean bl2, boolean bl3, String string, UI uI) {
        this.a(file, file2, agd_22, vector, bl2, bl3, string, string, uI);
    }

    public void a(File file, File file2, agd_2 agd_22, Vector vector, boolean bl2, boolean bl3, String string, String string2, UI uI) {
        ahu_1.a(new ash_0(file), new ash_0(file2), agd_22, vector, bl2, bl3, string, string2, uI);
    }

    public void a(File file, long l2) {
        ahu_1.a(new ash_0(file), l2);
    }

    public File d(File file, String string) {
        if (!ga_2.isAbsolutePath(string)) {
            char c = File.separatorChar;
            if (ga_2.dX(string = string.replace('/', c).replace('\\', c))) {
                file = null;
                String string2 = System.getProperty("user.dir");
                if (string.charAt(0) == c && string2.charAt(0) == c) {
                    string = this.ea(string2)[0] + string.substring(1);
                }
            }
            string = new File(file, string).getAbsolutePath();
        }
        return this.dZ(string);
    }

    public static boolean dX(String string) {
        if (!bbk && !bbj || string.length() == 0) {
            return false;
        }
        char c = File.separatorChar;
        string = string.replace('/', c).replace('\\', c);
        char c2 = string.charAt(0);
        int n2 = string.length();
        return c2 == c && (n2 == 1 || string.charAt(1) != c) || Character.isLetter(c2) && n2 > 1 && string.indexOf(58) == 1 && (n2 == 2 || string.charAt(2) != c);
    }

    public static boolean isAbsolutePath(String string) {
        int n2 = string.length();
        if (n2 == 0) {
            return false;
        }
        char c = File.separatorChar;
        string = string.replace('/', c).replace('\\', c);
        char c2 = string.charAt(0);
        if (!bbk && !bbj) {
            return c2 == c;
        }
        if (c2 == c) {
            if (!bbk || n2 <= 4 || string.charAt(1) != c) {
                return false;
            }
            int n3 = string.indexOf(c, 2);
            return n3 > 2 && n3 + 1 < n2;
        }
        int n4 = string.indexOf(58);
        return Character.isLetter(c2) && n4 == 1 && string.length() > 2 && string.charAt(2) == c || bbj && n4 > 0;
    }

    public static String dY(String string) {
        if (string == null || string.length() == 0) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer(string.length() + 50);
        aqA aqA2 = new aqA(string);
        while (aqA2.hasMoreTokens()) {
            String string2 = aqA2.nextToken();
            string2 = string2.replace('/', File.separatorChar);
            string2 = string2.replace('\\', File.separatorChar);
            if (stringBuffer.length() != 0) {
                stringBuffer.append(File.pathSeparatorChar);
            }
            stringBuffer.append(string2);
        }
        return stringBuffer.toString();
    }

    public File dZ(String string) {
        CharSequence charSequence;
        Stack<CharSequence> stack = new Stack<CharSequence>();
        String[] stringArray = this.ea(string);
        stack.push(stringArray[0]);
        StringTokenizer stringTokenizer = new StringTokenizer(stringArray[1], File.separator);
        while (stringTokenizer.hasMoreTokens()) {
            charSequence = stringTokenizer.nextToken();
            if (".".equals(charSequence)) continue;
            if ("..".equals(charSequence)) {
                if (stack.size() < 2) {
                    return new File(string);
                }
                stack.pop();
                continue;
            }
            stack.push(charSequence);
        }
        charSequence = new StringBuffer();
        for (int j = 0; j < stack.size(); ++j) {
            if (j > 1) {
                ((StringBuffer)charSequence).append(File.separatorChar);
            }
            ((StringBuffer)charSequence).append(stack.elementAt(j));
        }
        return new File(((StringBuffer)charSequence).toString());
    }

    public String[] ea(String string) {
        char c = File.separatorChar;
        if (!ga_2.isAbsolutePath(string = string.replace('/', c).replace('\\', c))) {
            throw new eq_2(string + " is not an absolute path");
        }
        String string2 = null;
        int n2 = string.indexOf(58);
        if (n2 > 0 && (bbk || bbj)) {
            int n3 = n2 + 1;
            string2 = string.substring(0, n3);
            char[] cArray = string.toCharArray();
            string2 = string2 + c;
            n3 = cArray[n3] == c ? n3 + 1 : n3;
            StringBuffer stringBuffer = new StringBuffer();
            for (int j = n3; j < cArray.length; ++j) {
                if (cArray[j] == c && cArray[j - 1] == c) continue;
                stringBuffer.append(cArray[j]);
            }
            string = stringBuffer.toString();
        } else if (string.length() > 1 && string.charAt(1) == c) {
            int n4 = string.indexOf(c, 2);
            string2 = (n4 = string.indexOf(c, n4 + 1)) > 2 ? string.substring(0, n4 + 1) : string;
            string = string.substring(string2.length());
        } else {
            string2 = File.separator;
            string = string.substring(1);
        }
        return new String[]{string2, string};
    }

    public String p(File file) {
        String string = this.dZ(file.getAbsolutePath()).getPath();
        String string2 = file.getName();
        boolean bl2 = string.charAt(0) == File.separatorChar;
        boolean bl3 = file.isDirectory() && !string2.regionMatches(true, string2.length() - 4, ".DIR", 0, 4);
        String string3 = null;
        StringBuffer stringBuffer = null;
        String string4 = null;
        int n2 = 0;
        if (bl2) {
            n2 = string.indexOf(File.separatorChar, 1);
            if (n2 == -1) {
                return string.substring(1) + ":[000000]";
            }
            string3 = string.substring(1, n2++);
        }
        if (bl3) {
            stringBuffer = new StringBuffer(string.substring(n2).replace(File.separatorChar, '.'));
        } else {
            int n3 = string.lastIndexOf(File.separatorChar, string.length());
            if (n3 == -1 || n3 < n2) {
                string4 = string.substring(n2);
            } else {
                stringBuffer = new StringBuffer(string.substring(n2, n3).replace(File.separatorChar, '.'));
                n2 = n3 + 1;
                if (string.length() > n2) {
                    string4 = string.substring(n2);
                }
            }
        }
        if (!bl2 && stringBuffer != null) {
            stringBuffer.insert(0, '.');
        }
        String string5 = (string3 != null ? string3 + ":" : "") + (stringBuffer != null ? "[" + stringBuffer + "]" : "") + (string4 != null ? string4 : "");
        return string5;
    }

    public File createTempFile(String string, String string2, File file) {
        return this.a(string, string2, file, false);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public File a(String string, String string2, File file, boolean bl2, boolean bl3) {
        String string3;
        File file2 = null;
        String string4 = string3 = file == null ? System.getProperty("java.io.tmpdir") : file.getPath();
        if (bl3) {
            try {
                file2 = File.createTempFile(string, string2, new File(string3));
            }
            catch (IOException iOException) {
                throw new eq_2("Could not create tempfile in " + string3, iOException);
            }
        }
        DecimalFormat decimalFormat = new DecimalFormat("#####");
        Random random = bbi;
        synchronized (random) {
            while ((file2 = new File(string3, string + decimalFormat.format(Math.abs(bbi.nextInt())) + string2)).exists()) {
            }
        }
        if (bl2) {
            file2.deleteOnExit();
        }
        return file2;
    }

    public File a(String string, String string2, File file, boolean bl2) {
        return this.a(string, string2, file, bl2, false);
    }

    public boolean b(File file, File file2) {
        return this.a(file, file2, false);
    }

    public boolean a(File file, File file2, boolean bl2) {
        return ahu_1.a((iv_1)new ash_0(file), (iv_1)new ash_0(file2), bl2);
    }

    public File a(File file) {
        return file == null ? null : file.getParentFile();
    }

    public static String c(Reader reader) {
        return ga_2.a(reader, 8192);
    }

    public static String a(Reader reader, int n2) {
        if (n2 <= 0) {
            throw new IllegalArgumentException("Buffer size must be greater than 0");
        }
        char[] cArray = new char[n2];
        int n3 = 0;
        StringBuffer stringBuffer = null;
        while (n3 != -1) {
            n3 = reader.read(cArray);
            if (n3 <= 0) continue;
            stringBuffer = stringBuffer == null ? new StringBuffer() : stringBuffer;
            stringBuffer.append(new String(cArray, 0, n3));
        }
        return stringBuffer == null ? null : stringBuffer.toString();
    }

    public static String d(Reader reader) {
        String string = ga_2.c(reader);
        return string == null ? "" : string;
    }

    public boolean q(File file) {
        return file.createNewFile();
    }

    public boolean a(File file, boolean bl2) {
        File file2 = file.getParentFile();
        if (bl2 && !file2.exists()) {
            file2.mkdirs();
        }
        return file.createNewFile();
    }

    public boolean e(File file, String string) {
        File file2;
        if (file == null) {
            file2 = new File(string);
            file = file2.getParentFile();
            string = file2.getName();
        }
        return !(file2 = new File(file.getCanonicalPath(), string)).getAbsolutePath().equals(file2.getCanonicalPath());
    }

    public String c(File file, File file2) {
        String string;
        String string2 = this.dZ(file.getAbsolutePath()).getAbsolutePath();
        if (string2.equals(string = this.dZ(file2.getAbsolutePath()).getAbsolutePath())) {
            return "";
        }
        if (!string2.endsWith(File.separator)) {
            string2 = string2 + File.separator;
        }
        return string.startsWith(string2) ? string.substring(string2.length()) : string;
    }

    public boolean d(File file, File file2) {
        String string;
        String string2 = this.dZ(file.getAbsolutePath()).getAbsolutePath();
        if (string2.equals(string = this.dZ(file2.getAbsolutePath()).getAbsolutePath())) {
            return true;
        }
        if (!string2.endsWith(File.separator)) {
            string2 = string2 + File.separator;
        }
        return string.startsWith(string2);
    }

    public String eb(String string) {
        Class<?> clazz = null;
        try {
            clazz = Class.forName("java.net.URI");
        }
        catch (ClassNotFoundException classNotFoundException) {
            // empty catch block
        }
        if (clazz != null) {
            try {
                File file = new File(string).getAbsoluteFile();
                Method method = (bbt == null ? (bbt = ga_2.a("java.io.File")) : bbt).getMethod("toURI", new Class[0]);
                Object object = method.invoke(file, new Object[0]);
                Method method2 = clazz.getMethod("toASCIIString", new Class[0]);
                return (String)method2.invoke(object, new Object[0]);
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        boolean bl2 = new File(string).isDirectory();
        StringBuffer stringBuffer = new StringBuffer("file:");
        string = this.d(null, string).getPath();
        stringBuffer.append("//");
        if (!string.startsWith(File.separator)) {
            stringBuffer.append("/");
        }
        string = string.replace('\\', '/');
        try {
            stringBuffer.append(Hs.eA(string));
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new eq_2(unsupportedEncodingException);
        }
        if (bl2 && !string.endsWith("/")) {
            stringBuffer.append('/');
        }
        return stringBuffer.toString();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String ec(String string) {
        Object object = this.bbq;
        synchronized (object) {
            if (string.equals(this.bbr)) {
                return this.bbs;
            }
            String string2 = Hs.ec(string);
            String string3 = ga_2.isAbsolutePath(string2) ? this.dZ(string2).getAbsolutePath() : string2;
            this.bbr = string;
            this.bbs = string3;
            return string3;
        }
    }

    public boolean e(File file, File file2) {
        return this.dZ(file.getAbsolutePath()).getAbsolutePath().equals(this.dZ(file2.getAbsolutePath()).getAbsolutePath());
    }

    public void f(File file, File file2) {
        file = this.dZ(file.getAbsolutePath()).getCanonicalFile();
        file2 = this.dZ(file2.getAbsolutePath());
        if (!file.exists()) {
            System.err.println("Cannot rename nonexistent file " + file);
            return;
        }
        if (file.equals(file2)) {
            System.err.println("Rename of " + file + " to " + file2 + " is a no-op.");
            return;
        }
        if (file2.exists() && !file.equals(file2.getCanonicalFile()) && !file2.delete()) {
            throw new IOException("Failed to delete " + file2 + " while trying to rename " + file);
        }
        File file3 = file2.getParentFile();
        if (file3 != null && !file3.exists() && !file3.mkdirs()) {
            throw new IOException("Failed to create directory " + file3 + " while trying to rename " + file);
        }
        if (!file.renameTo(file2)) {
            this.a(file, file2);
            if (!file.delete()) {
                throw new IOException("Failed to delete " + file + " while trying to rename it.");
            }
        }
    }

    public long Qp() {
        if (bbl) {
            return 2000L;
        }
        if (bbm) {
            return 1L;
        }
        if (bbk) {
            return 2000L;
        }
        return 1000L;
    }

    public boolean r(File file) {
        if (!(file = this.dZ(file.getAbsolutePath())).exists()) {
            return false;
        }
        String string = file.getName();
        uw_1 uw_12 = new uw_1(this, string);
        String[] stringArray = file.getParentFile().list(uw_12);
        return stringArray != null && stringArray.length == 1;
    }

    public boolean a(File file, File file2, long l2) {
        if (!file2.exists()) {
            return false;
        }
        long l3 = file.lastModified();
        long l4 = file2.lastModified();
        return this.d(l3, l4, l2);
    }

    public boolean g(File file, File file2) {
        return this.a(file, file2, this.Qp());
    }

    public boolean d(long l2, long l3, long l4) {
        return l3 != -1L && l3 >= l2 + l4;
    }

    public boolean h(long l2, long l3) {
        return this.d(l2, l3, this.Qp());
    }

    public static void a(Writer writer) {
        if (null != writer) {
            try {
                writer.close();
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
    }

    public static void e(Reader reader) {
        if (null != reader) {
            try {
                reader.close();
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
    }

    public static void a(OutputStream outputStream) {
        if (null != outputStream) {
            try {
                outputStream.close();
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
    }

    public static void h(InputStream inputStream) {
        if (null != inputStream) {
            try {
                inputStream.close();
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
    }

    public static void s(File file) {
        if (file != null) {
            file.delete();
        }
    }

    public static String h(File file, File file2) {
        int n2;
        String string = file.getCanonicalPath();
        String string2 = file2.getCanonicalPath();
        String[] stringArray = ga_2.ed(string);
        String[] stringArray2 = ga_2.ed(string2);
        if (0 < stringArray2.length && 0 < stringArray.length) {
            if (!stringArray[0].equals(stringArray2[0])) {
                return ga_2.f(Arrays.asList(stringArray2));
            }
        } else {
            return ga_2.f(Arrays.asList(stringArray2));
        }
        int n3 = Math.min(stringArray.length, stringArray2.length);
        for (int j = 1; j < n3 && stringArray[j].equals(stringArray2[j]); ++j) {
        }
        ArrayList<String> arrayList = new ArrayList<String>();
        for (n2 = j; n2 < stringArray.length; ++n2) {
            arrayList.add("..");
        }
        for (n2 = j; n2 < stringArray2.length; ++n2) {
            arrayList.add(stringArray2[n2]);
        }
        return ga_2.f(arrayList);
    }

    public static String[] ed(String string) {
        String string2 = string.replace(File.separatorChar, '/');
        Object[] objectArray = ayM.E(string2, 47).toArray();
        String[] stringArray = new String[objectArray.length];
        System.arraycopy(objectArray, 0, stringArray, 0, objectArray.length);
        return stringArray;
    }

    public static String f(List list) {
        return ga_2.a(list, '/');
    }

    public static String a(List list, char c) {
        StringBuffer stringBuffer = new StringBuffer();
        Iterator iterator = list.iterator();
        if (iterator.hasNext()) {
            stringBuffer.append(iterator.next());
        }
        while (iterator.hasNext()) {
            stringBuffer.append(c);
            stringBuffer.append(iterator.next());
        }
        return stringBuffer.toString();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String Qq() {
        InputStreamReader inputStreamReader = new InputStreamReader(new up_2(this));
        try {
            String string = inputStreamReader.getEncoding();
            return string;
        }
        finally {
            ga_2.e(inputStreamReader);
        }
    }

    static Class a(String string) {
        try {
            return Class.forName(string);
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw new NoClassDefFoundError(classNotFoundException.getMessage());
        }
    }
}

