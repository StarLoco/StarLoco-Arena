/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

public class Iw {
    public static final String bhp = "ant.home";
    public static final String bhq = "ant.library.dir";
    public static final String bhr = ".ant";
    public static final String bhs = "lib";
    public static final String bht = ".ant" + File.separatorChar + "lib";
    public static final String bhu = "org.apache.tools.ant.Main";
    public static final String bhv = "user.home";
    private static final String bhw = "java.class.path";
    protected static final int bhx = 2;

    public static void main(String[] stringArray) {
        int n2;
        try {
            Iw iw = new Iw();
            n2 = iw.m(stringArray);
        }
        catch (at_1 at_12) {
            n2 = 2;
            System.err.println(at_12.getMessage());
        }
        catch (Throwable throwable) {
            n2 = 2;
            throwable.printStackTrace(System.err);
        }
        if (n2 != 0) {
            System.exit(n2);
        }
    }

    private void a(String string, boolean bl2, List list) {
        StringTokenizer stringTokenizer = new StringTokenizer(string, File.pathSeparator);
        while (stringTokenizer.hasMoreElements()) {
            String string2 = stringTokenizer.nextToken();
            File file = new File(string2);
            if (string2.indexOf("%") != -1 && !file.exists()) continue;
            if (bl2 && file.isDirectory()) {
                URL[] uRLArray = Hs.u(file);
                for (int j = 0; j < uRLArray.length; ++j) {
                    list.add(uRLArray[j]);
                }
            }
            list.add(Hs.t(file));
        }
    }

    private int m(String[] stringArray) {
        String string = System.getProperty(bhp);
        File file = null;
        File file2 = Hs.e(this.getClass());
        File file3 = file2.getParentFile();
        String string2 = bhu;
        if (string != null) {
            file = new File(string);
        }
        if (file == null || !file.exists()) {
            file = file3.getParentFile();
            System.setProperty(bhp, file.getAbsolutePath());
        }
        if (!file.exists()) {
            throw new at_1("Ant home is set incorrectly or ant could not be located");
        }
        ArrayList<String> arrayList = new ArrayList<String>();
        String string3 = null;
        ArrayList<String> arrayList2 = new ArrayList<String>();
        boolean bl2 = false;
        boolean bl3 = false;
        for (int j = 0; j < stringArray.length; ++j) {
            if (stringArray[j].equals("-lib")) {
                if (j == stringArray.length - 1) {
                    throw new at_1("The -lib argument must be followed by a library location");
                }
                arrayList.add(stringArray[++j]);
                continue;
            }
            if (stringArray[j].equals("-cp")) {
                if (j == stringArray.length - 1) {
                    throw new at_1("The -cp argument must be followed by a classpath expression");
                }
                if (string3 != null) {
                    throw new at_1("The -cp argument must not be repeated");
                }
                string3 = stringArray[++j];
                continue;
            }
            if (stringArray[j].equals("--nouserlib") || stringArray[j].equals("-nouserlib")) {
                bl2 = true;
                continue;
            }
            if (stringArray[j].equals("--noclasspath") || stringArray[j].equals("-noclasspath")) {
                bl3 = true;
                continue;
            }
            if (stringArray[j].equals("-main")) {
                if (j == stringArray.length - 1) {
                    throw new at_1("The -main argument must be followed by a library location");
                }
                string2 = stringArray[++j];
                continue;
            }
            arrayList2.add(stringArray[j]);
        }
        String[] stringArray2 = arrayList2.size() == stringArray.length ? stringArray : arrayList2.toArray(new String[arrayList2.size()]);
        URL[] uRLArray = this.a(bl3 ? null : string3, arrayList);
        URL[] uRLArray2 = this.v(file3);
        URL[] uRLArray3 = bl2 ? new URL[]{} : this.Ux();
        URL[] uRLArray4 = this.a(uRLArray, uRLArray3, uRLArray2, Hs.SS());
        StringBuffer stringBuffer = new StringBuffer(System.getProperty(bhw));
        if (stringBuffer.charAt(stringBuffer.length() - 1) == File.pathSeparatorChar) {
            stringBuffer.setLength(stringBuffer.length() - 1);
        }
        for (int j = 0; j < uRLArray4.length; ++j) {
            stringBuffer.append(File.pathSeparatorChar);
            stringBuffer.append(Hs.ec(uRLArray4[j].toString()));
        }
        System.setProperty(bhw, stringBuffer.toString());
        URLClassLoader uRLClassLoader = new URLClassLoader(uRLArray4);
        Thread.currentThread().setContextClassLoader(uRLClassLoader);
        Class<?> clazz = null;
        int n2 = 0;
        try {
            clazz = uRLClassLoader.loadClass(string2);
            ain_0 ain_02 = (ain_0)clazz.newInstance();
            ain_02.b(stringArray2, null, null);
        }
        catch (InstantiationException instantiationException) {
            System.err.println("Incompatible version of " + string2 + " detected");
            File file4 = Hs.e(clazz);
            System.err.println("Location of this class " + file4);
            n2 = 2;
        }
        catch (Throwable throwable) {
            throwable.printStackTrace(System.err);
            n2 = 2;
        }
        return n2;
    }

    private URL[] a(String string, List list) {
        ArrayList arrayList = new ArrayList();
        if (string != null) {
            this.a(string, false, arrayList);
        }
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            String string2 = (String)iterator.next();
            this.a(string2, true, arrayList);
        }
        return arrayList.toArray(new URL[arrayList.size()]);
    }

    private URL[] v(File file) {
        File file2 = null;
        String string = System.getProperty(bhq);
        if (string != null) {
            file2 = new File(string);
        }
        if (file2 == null || !file2.exists()) {
            file2 = file;
            System.setProperty(bhq, file2.getAbsolutePath());
        }
        return Hs.u(file2);
    }

    private URL[] Ux() {
        File file = new File(System.getProperty(bhv), bht);
        return Hs.u(file);
    }

    private URL[] a(URL[] uRLArray, URL[] uRLArray2, URL[] uRLArray3, File file) {
        int n2 = uRLArray.length + uRLArray2.length + uRLArray3.length;
        if (file != null) {
            ++n2;
        }
        URL[] uRLArray4 = new URL[n2];
        System.arraycopy(uRLArray, 0, uRLArray4, 0, uRLArray.length);
        System.arraycopy(uRLArray2, 0, uRLArray4, uRLArray.length, uRLArray2.length);
        System.arraycopy(uRLArray3, 0, uRLArray4, uRLArray2.length + uRLArray.length, uRLArray3.length);
        if (file != null) {
            uRLArray4[uRLArray4.length - 1] = Hs.t(file);
        }
        return uRLArray4;
    }
}

