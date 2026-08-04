/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Iterator;
import java.util.Locale;
import java.util.Stack;
import java.util.Vector;

/*
 * Renamed from bK
 */
public class bk_2
extends avg
implements mx_2,
Cloneable {
    public static bk_2 hl = new bk_2(null, System.getProperty("java.class.path"));
    public static bk_2 hm = new bk_2(null, System.getProperty("sun.boot.class.path"));
    private static final Iterator hn = Collections.EMPTY_SET.iterator();
    private Boolean ho;
    private uW hp = null;
    static Class hq;

    public bk_2(UI uI, String string) {
        this(uI);
        this.dA().setPath(string);
    }

    public bk_2(UI uI) {
        this.l(uI);
    }

    public void d(File file) {
        this.aIl();
        this.dA().d(file);
    }

    public void setPath(String string) {
        this.aIl();
        this.dA().setPath(string);
    }

    public void a(awq_0 awq_02) {
        if (this.hp != null) {
            throw this.aIh();
        }
        super.a(awq_02);
    }

    public aod_1 dA() {
        if (this.aId()) {
            throw this.aIi();
        }
        aod_1 aod_12 = new aod_1(this);
        this.a(aod_12);
        return aod_12;
    }

    public void a(be_0 be_02) {
        if (be_02.TP() == null) {
            be_02.l(this.TP());
        }
        this.a((mx_2)be_02);
    }

    public void a(ND nD) {
        if (nD.TP() == null) {
            nD.l(this.TP());
        }
        this.a((mx_2)nD);
    }

    public void a(zv_2 zv_22) {
        if (zv_22.TP() == null) {
            zv_22.l(this.TP());
        }
        this.a((mx_2)zv_22);
    }

    public void a(bk_2 bk_22) {
        if (bk_22 == this) {
            throw this.aIj();
        }
        if (bk_22.TP() == null) {
            bk_22.l(this.TP());
        }
        this.a((mx_2)bk_22);
    }

    public void a(mx_2 mx_22) {
        this.aIm();
        if (mx_22 == null) {
            return;
        }
        if (this.hp == null) {
            this.hp = new uW();
            this.hp.l(this.TP());
            this.hp.bY(false);
        }
        this.hp.a(mx_22);
        this.setChecked(false);
    }

    public bk_2 dB() {
        bk_2 bk_22 = new bk_2(this.TP());
        this.a(bk_22);
        return bk_22;
    }

    public void b(bk_2 bk_22) {
        if (bk_22 == null) {
            return;
        }
        this.a(bk_22);
    }

    public void c(bk_2 bk_22) {
        this.a(bk_22, false);
    }

    public void a(bk_2 bk_22, boolean bl2) {
        String[] stringArray = bk_22.list();
        File file = bl2 ? new File(System.getProperty("user.dir")) : null;
        for (int j = 0; j < stringArray.length; ++j) {
            File file2 = bk_2.b(this.TP(), stringArray[j]);
            if (bl2 && !file2.exists()) {
                file2 = new File(file, stringArray[j]);
            }
            if (file2.exists()) {
                this.d(file2);
                continue;
            }
            this.l("dropping " + file2 + " from path as it doesn't exist", 3);
        }
    }

    public String[] list() {
        if (this.aId()) {
            return ((bk_2)this.aIg()).list();
        }
        return this.b(this.hp) == null ? new String[]{} : this.hp.list();
    }

    public String toString() {
        return this.aId() ? this.aIg().toString() : (this.hp == null ? "" : this.hp.toString());
    }

    public static String[] a(UI uI, String string) {
        Object[] objectArray;
        Vector<String> vector = new Vector<String>();
        if (string == null) {
            return new String[0];
        }
        aqA aqA2 = new aqA(string);
        StringBuffer stringBuffer = new StringBuffer();
        while (aqA2.hasMoreTokens()) {
            objectArray = aqA2.nextToken();
            try {
                stringBuffer.append(bk_2.b(uI, (String)objectArray).getPath());
            }
            catch (eq_2 eq_22) {
                uI.l("Dropping path element " + (String)objectArray + " as it is not valid relative to the project", 3);
            }
            for (int j = 0; j < stringBuffer.length(); ++j) {
                bk_2.a(stringBuffer, j);
            }
            vector.addElement(stringBuffer.toString());
            stringBuffer = new StringBuffer();
        }
        objectArray = new String[vector.size()];
        vector.copyInto(objectArray);
        return objectArray;
    }

    public static String r(String string) {
        if (string == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer(string);
        for (int j = 0; j < stringBuffer.length(); ++j) {
            bk_2.a(stringBuffer, j);
        }
        return stringBuffer.toString();
    }

    protected static boolean a(StringBuffer stringBuffer, int n2) {
        if (stringBuffer.charAt(n2) == '/' || stringBuffer.charAt(n2) == '\\') {
            stringBuffer.setCharAt(n2, File.separatorChar);
            return true;
        }
        return false;
    }

    public synchronized int size() {
        if (this.aId()) {
            return ((bk_2)this.aIg()).size();
        }
        this.aIf();
        return this.hp == null ? 0 : this.b(this.hp).size();
    }

    public Object clone() {
        try {
            bk_2 bk_22 = (bk_2)super.clone();
            bk_22.hp = this.hp == null ? this.hp : (uW)this.hp.clone();
            return bk_22;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new eq_2(cloneNotSupportedException);
        }
    }

    protected synchronized void a(Stack stack, UI uI) {
        if (this.isChecked()) {
            return;
        }
        if (this.aId()) {
            super.a(stack, uI);
        } else {
            if (this.hp != null) {
                stack.push(this.hp);
                bk_2.a(this.hp, stack, uI);
                stack.pop();
            }
            this.setChecked(true);
        }
    }

    private static File b(UI uI, String string) {
        return ga_2.Qo().d(uI == null ? null : uI.ahg(), string);
    }

    public bk_2 dC() {
        return this.s("last");
    }

    public bk_2 s(String string) {
        return this.a(string, hl);
    }

    public bk_2 t(String string) {
        return this.a(string, hm);
    }

    private bk_2 a(String string, bk_2 bk_22) {
        String string2;
        bk_2 bk_23 = new bk_2(this.TP());
        String string3 = string;
        if (this.TP() != null && (string2 = this.TP().getProperty("build.sysclasspath")) != null) {
            string3 = string2;
        }
        if (string3.equals("only")) {
            bk_23.a(bk_22, true);
        } else if (string3.equals("first")) {
            bk_23.a(bk_22, true);
            bk_23.c(this);
        } else if (string3.equals("ignore")) {
            bk_23.c(this);
        } else {
            if (!string3.equals("last")) {
                this.l("invalid value for build.sysclasspath: " + string3, 1);
            }
            bk_23.c(this);
            bk_23.a(bk_22, true);
        }
        return bk_23;
    }

    public void dD() {
        Object object;
        if (ako_1.aAa()) {
            object = new File(System.getProperty("java.home") + File.separator + "share" + File.separator + "kaffe");
            if (object.isDirectory()) {
                be_0 be_02 = new be_0();
                be_02.x((File)object);
                be_02.fT("*.jar");
                this.a(be_02);
            }
        } else if ("GNU libgcj".equals(System.getProperty("java.vm.name"))) {
            this.c(hm);
        }
        if (System.getProperty("java.vendor").toLowerCase(Locale.US).indexOf("microsoft") >= 0) {
            object = new be_0();
            object.x(new File(System.getProperty("java.home") + File.separator + "Packages"));
            object.fT("*.ZIP");
            this.a((be_0)object);
        } else {
            this.c(new bk_2(null, System.getProperty("java.home") + File.separator + "lib" + File.separator + "rt.jar"));
            this.c(new bk_2(null, System.getProperty("java.home") + File.separator + "jre" + File.separator + "lib" + File.separator + "rt.jar"));
            object = new String[]{"jce", "jsse"};
            for (int j = 0; j < ((String[])object).length; ++j) {
                this.c(new bk_2(null, System.getProperty("java.home") + File.separator + "lib" + File.separator + object[j] + ".jar"));
                this.c(new bk_2(null, System.getProperty("java.home") + File.separator + ".." + File.separator + "Classes" + File.separator + object[j] + ".jar"));
            }
            String[] stringArray = new String[]{"core", "graphics", "security", "server", "xml"};
            for (int j = 0; j < stringArray.length; ++j) {
                this.c(new bk_2(null, System.getProperty("java.home") + File.separator + "lib" + File.separator + stringArray[j] + ".jar"));
            }
            this.c(new bk_2(null, System.getProperty("java.home") + File.separator + ".." + File.separator + "Classes" + File.separator + "classes.jar"));
            this.c(new bk_2(null, System.getProperty("java.home") + File.separator + ".." + File.separator + "Classes" + File.separator + "ui.jar"));
        }
    }

    public void d(bk_2 bk_22) {
        String[] stringArray;
        if (bk_22 == null) {
            stringArray = System.getProperty("java.ext.dirs");
            if (stringArray != null) {
                bk_22 = new bk_2(this.TP(), (String)stringArray);
            } else {
                return;
            }
        }
        stringArray = bk_22.list();
        for (int j = 0; j < stringArray.length; ++j) {
            File file = bk_2.b(this.TP(), stringArray[j]);
            if (!file.exists() || !file.isDirectory()) continue;
            be_0 be_02 = new be_0();
            be_02.x(file);
            be_02.fT("*");
            this.a(be_02);
        }
    }

    public final synchronized Iterator iterator() {
        if (this.aId()) {
            return ((bk_2)this.aIg()).iterator();
        }
        this.aIf();
        if (this.dG()) {
            return new qf_0(null, this.list());
        }
        return this.hp == null ? hn : this.b(this.hp).iterator();
    }

    public synchronized boolean dE() {
        if (this.aId()) {
            return ((bk_2)this.aIg()).dE();
        }
        this.aIf();
        this.b(this.hp);
        return true;
    }

    protected mx_2 b(mx_2 mx_22) {
        if (mx_22 != null && !mx_22.dE()) {
            throw new eq_2(this.aIe() + " allows only filesystem resources.");
        }
        return mx_22;
    }

    protected boolean dF() {
        if (this.getClass().equals(hq == null ? (hq = bk_2.a("bK")) : hq)) {
            return false;
        }
        try {
            Method method = this.getClass().getMethod("list", null);
            return !method.getDeclaringClass().equals(hq == null ? (hq = bk_2.a("bK")) : hq);
        }
        catch (Exception exception) {
            return false;
        }
    }

    private synchronized boolean dG() {
        if (this.ho == null) {
            this.ho = this.dF() ? Boolean.TRUE : Boolean.FALSE;
        }
        return this.ho;
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

