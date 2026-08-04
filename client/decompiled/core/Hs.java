/*
 * Decompiled with CFR 0.152.
 */
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.StringCharacterIterator;
import java.util.Locale;

public final class Hs {
    private static final int bej = 4;
    private static final int bek = 15;
    private static final int bel = 128;
    private static final int BYTE_SIZE = 256;
    private static final int WORD = 16;
    private static final int SPACE = 32;
    private static final int bem = 127;
    public static final String URI_ENCODING = "UTF-8";
    private static boolean[] ben = new boolean[128];
    private static char[] beo = new char[128];
    private static char[] bep = new char[128];
    private static char[] beq = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    public static final String ber = "Can only handle valid file: URIs, not ";
    static Class bes;
    static Class avl;
    static Class bbt;

    private Hs() {
    }

    public static File e(Class clazz) {
        String string = clazz.getName().replace('.', '/') + ".class";
        return Hs.a(clazz.getClassLoader(), string);
    }

    public static File a(ClassLoader classLoader, String string) {
        if (classLoader == null) {
            classLoader = (bes == null ? (bes = Hs.a("Hs")) : bes).getClassLoader();
        }
        URL uRL = null;
        uRL = classLoader == null ? ClassLoader.getSystemResource(string) : classLoader.getResource(string);
        if (uRL != null) {
            String string2 = uRL.toString();
            try {
                if (string2.startsWith("jar:file:")) {
                    int n2 = string2.indexOf("!");
                    String string3 = string2.substring("jar:".length(), n2);
                    return new File(Hs.ec(string3));
                }
                if (string2.startsWith("file:")) {
                    int n3 = string2.indexOf(string);
                    String string4 = string2.substring(0, n3);
                    return new File(Hs.ec(string4));
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                return null;
            }
        }
        return null;
    }

    public static String ec(String string) {
        String string2 = null;
        if (string2 == null) {
            string2 = Hs.ey(string);
        }
        return string2;
    }

    private static String ex(String string) {
        Class<?> clazz = null;
        try {
            clazz = Class.forName("java.net.URI");
        }
        catch (ClassNotFoundException classNotFoundException) {
            return null;
        }
        if (clazz != null && string.startsWith("file:/")) {
            try {
                Method method = clazz.getMethod("create", avl == null ? (avl = Hs.a("java.lang.String")) : avl);
                Object object = method.invoke(null, Hs.eA(string));
                Constructor constructor = (bbt == null ? (bbt = Hs.a("java.io.File")) : bbt).getConstructor(clazz);
                File file = (File)constructor.newInstance(object);
                return Hs.ez(file.getAbsolutePath());
            }
            catch (InvocationTargetException invocationTargetException) {
                Throwable throwable = invocationTargetException.getTargetException();
                if (throwable instanceof IllegalArgumentException) {
                    throwable.printStackTrace();
                } else {
                    throwable.printStackTrace();
                }
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

    static String ey(String string) {
        String string2;
        int n2;
        URL uRL = null;
        try {
            uRL = new URL(string);
        }
        catch (MalformedURLException malformedURLException) {
            // empty catch block
        }
        if (uRL == null || !"file".equals(uRL.getProtocol())) {
            throw new IllegalArgumentException(ber + string);
        }
        StringBuffer stringBuffer = new StringBuffer(uRL.getHost());
        if (stringBuffer.length() > 0) {
            stringBuffer.insert(0, File.separatorChar).insert(0, File.separatorChar);
        }
        stringBuffer.append((n2 = (string2 = uRL.getFile()).indexOf(63)) < 0 ? string2 : string2.substring(0, n2));
        string = stringBuffer.toString().replace('/', File.separatorChar);
        if (File.pathSeparatorChar == ';' && string.startsWith("\\") && string.length() > 2 && Character.isLetter(string.charAt(1)) && string.lastIndexOf(58) > -1) {
            string = string.substring(1);
        }
        String string3 = null;
        try {
            string3 = Hs.ez(string);
            String string4 = System.getProperty("user.dir");
            int n3 = string4.indexOf(":");
            if (n3 > 0 && string3.startsWith(File.separator)) {
                string3 = string4.substring(0, n3 + 1) + string3;
            }
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new IllegalStateException("Could not convert URI " + string + " to path: " + unsupportedEncodingException.getMessage());
        }
        return string3;
    }

    public static String ez(String string) {
        if (string.indexOf(37) == -1) {
            return string;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(string.length());
        StringCharacterIterator stringCharacterIterator = new StringCharacterIterator(string);
        char c = stringCharacterIterator.first();
        while (c != '\uffff') {
            if (c == '%') {
                char c2 = stringCharacterIterator.next();
                if (c2 != '\uffff') {
                    int n2 = Character.digit(c2, 16);
                    char c3 = stringCharacterIterator.next();
                    if (c3 != '\uffff') {
                        int n3 = Character.digit(c3, 16);
                        byteArrayOutputStream.write((char)((n2 << 4) + n3));
                    }
                }
            } else {
                byteArrayOutputStream.write(c);
            }
            c = stringCharacterIterator.next();
        }
        return byteArrayOutputStream.toString(URI_ENCODING);
    }

    public static String eA(String string) {
        int n2;
        int n3 = string.length();
        int n4 = 0;
        StringBuffer stringBuffer = null;
        for (n2 = 0; n2 < n3 && (n4 = string.charAt(n2)) < 128; ++n2) {
            if (ben[n4]) {
                if (stringBuffer == null) {
                    stringBuffer = new StringBuffer(string.substring(0, n2));
                }
                stringBuffer.append('%');
                stringBuffer.append(beo[n4]);
                stringBuffer.append(bep[n4]);
                continue;
            }
            if (stringBuffer == null) continue;
            stringBuffer.append((char)n4);
        }
        if (n2 < n3) {
            if (stringBuffer == null) {
                stringBuffer = new StringBuffer(string.substring(0, n2));
            }
            byte[] byArray = null;
            byArray = string.substring(n2).getBytes(URI_ENCODING);
            n3 = byArray.length;
            for (n2 = 0; n2 < n3; ++n2) {
                byte by = byArray[n2];
                if (by < 0) {
                    n4 = by + 256;
                    stringBuffer.append('%');
                    stringBuffer.append(beq[n4 >> 4]);
                    stringBuffer.append(beq[n4 & 0xF]);
                    continue;
                }
                if (ben[by]) {
                    stringBuffer.append('%');
                    stringBuffer.append(beo[by]);
                    stringBuffer.append(bep[by]);
                    continue;
                }
                stringBuffer.append((char)by);
            }
        }
        return stringBuffer == null ? string : stringBuffer.toString();
    }

    public static URL t(File file) {
        try {
            return new URL(Hs.eA(file.toURL().toString()));
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new MalformedURLException(unsupportedEncodingException.toString());
        }
    }

    public static File SS() {
        boolean bl2 = false;
        try {
            Class.forName("com.sun.tools.javac.Main");
            bl2 = true;
        }
        catch (Exception exception) {
            try {
                Class.forName("sun.tools.javac.Main");
                bl2 = true;
            }
            catch (Exception exception2) {
                // empty catch block
            }
        }
        if (bl2) {
            return null;
        }
        String string = File.separator + "lib" + File.separator + "tools.jar";
        String string2 = System.getProperty("java.home");
        File file = new File(string2 + string);
        if (file.exists()) {
            return file;
        }
        if (string2.toLowerCase(Locale.US).endsWith(File.separator + "jre")) {
            string2 = string2.substring(0, string2.length() - "/jre".length());
            file = new File(string2 + string);
        }
        if (!file.exists()) {
            System.out.println("Unable to locate tools.jar. Expected to find it in " + file.getPath());
            return null;
        }
        return file;
    }

    public static URL[] u(File file) {
        return Hs.a(file, new String[]{".jar"});
    }

    public static URL[] a(File file, String[] stringArray) {
        URL[] uRLArray = new URL[]{};
        if (!file.exists()) {
            return uRLArray;
        }
        if (!file.isDirectory()) {
            uRLArray = new URL[1];
            String string = file.getPath();
            String string2 = string.toLowerCase(Locale.US);
            for (int j = 0; j < stringArray.length; ++j) {
                if (!string2.endsWith(stringArray[j])) continue;
                uRLArray[0] = Hs.t(file);
                break;
            }
            return uRLArray;
        }
        File[] fileArray = file.listFiles(new fx_0(stringArray));
        uRLArray = new URL[fileArray.length];
        for (int j = 0; j < fileArray.length; ++j) {
            uRLArray[j] = Hs.t(fileArray[j]);
        }
        return uRLArray;
    }

    static Class a(String string) {
        try {
            return Class.forName(string);
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw new NoClassDefFoundError(classNotFoundException.getMessage());
        }
    }

    static {
        for (int j = 0; j < 32; ++j) {
            Hs.ben[j] = true;
            Hs.beo[j] = beq[j >> 4];
            Hs.bep[j] = beq[j & 0xF];
        }
        Hs.ben[127] = true;
        Hs.beo[127] = 55;
        Hs.bep[127] = 70;
        char[] cArray = new char[]{' ', '<', '>', '#', '%', '\"', '{', '}', '|', '\\', '^', '~', '[', ']', '`'};
        int n2 = cArray.length;
        for (int j = 0; j < n2; ++j) {
            char c = cArray[j];
            Hs.ben[c] = true;
            Hs.beo[c] = beq[c >> 4];
            Hs.bep[c] = beq[c & 0xF];
        }
    }
}

