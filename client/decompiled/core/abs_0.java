/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

/*
 * Renamed from aBS
 */
public class abs_0
implements ajh_0,
axm_0,
hb_1 {
    private static final boolean dtd = xk_1.cO("openvms");
    protected static final String[] dte = new String[]{"**/*~", "**/#*#", "**/.#*", "**/%*%", "**/._*", "**/CVS", "**/CVS/**", "**/.cvsignore", "**/SCCS", "**/SCCS/**", "**/vssver.scc", "**/.svn", "**/.svn/**", "**/.DS_Store"};
    private static final ga_2 xa = ga_2.Qo();
    private static final boolean[] dtf = new boolean[]{true};
    private static final boolean[] dtg = new boolean[]{true, false};
    private static Vector dth = new Vector();
    protected File bFR;
    protected String[] dti;
    protected String[] dtj;
    protected R[] dtk = null;
    protected Vector dtl;
    protected Vector dtm;
    protected Vector dtn;
    protected Vector dto;
    protected Vector dtp;
    protected Vector dtq;
    protected Vector dtr;
    protected Vector dts;
    protected boolean dtt = false;
    protected boolean dtu = true;
    protected boolean cWk = true;
    private boolean cWj = true;
    protected boolean dtv = true;
    private Map dtw = new HashMap();
    private Set dtx = new HashSet();
    private Set dty = new HashSet();
    private Set dtz = new HashSet();
    private String[] dtA;
    private String[] dtB;
    private boolean dtC = false;
    private boolean dtD = false;
    private Object dtE = new Object();
    private boolean dtF = false;
    private Object dtG = new Object();
    private IllegalStateException dtH = null;

    protected static boolean N(String string, String string2) {
        return zr_1.N(string, string2);
    }

    protected static boolean d(String string, String string2, boolean bl2) {
        return zr_1.d(string, string2, bl2);
    }

    protected static boolean O(String string, String string2) {
        return zr_1.O(string, string2);
    }

    protected static boolean e(String string, String string2, boolean bl2) {
        return zr_1.e(string, string2, bl2);
    }

    public static boolean P(String string, String string2) {
        return zr_1.P(string, string2);
    }

    protected static boolean f(String string, String string2, boolean bl2) {
        return zr_1.f(string, string2, bl2);
    }

    public static String[] aNZ() {
        return dth.toArray(new String[dth.size()]);
    }

    public static boolean kn(String string) {
        if (dth.indexOf(string) == -1) {
            dth.add(string);
            return true;
        }
        return false;
    }

    public static boolean ko(String string) {
        return dth.remove(string);
    }

    public static void aOa() {
        dth = new Vector();
        for (int j = 0; j < dte.length; ++j) {
            dth.add(dte[j]);
        }
    }

    public void ar(String string) {
        this.f(string == null ? (File)null : new File(string.replace('/', File.separatorChar).replace('\\', File.separatorChar)));
    }

    public synchronized void f(File file) {
        this.bFR = file;
    }

    public synchronized File kt() {
        return this.bFR;
    }

    public synchronized boolean aHy() {
        return this.dtu;
    }

    public synchronized void setCaseSensitive(boolean bl2) {
        this.dtu = bl2;
    }

    public void ej(boolean bl2) {
        this.cWk = bl2;
    }

    public synchronized boolean aHz() {
        return this.cWj;
    }

    public synchronized void ei(boolean bl2) {
        this.cWj = bl2;
    }

    public synchronized void d(String[] stringArray) {
        if (stringArray == null) {
            this.dti = null;
        } else {
            this.dti = new String[stringArray.length];
            for (int j = 0; j < stringArray.length; ++j) {
                this.dti[j] = abs_0.kp(stringArray[j]);
            }
        }
    }

    public synchronized void c(String[] stringArray) {
        if (stringArray == null) {
            this.dtj = null;
        } else {
            this.dtj = new String[stringArray.length];
            for (int j = 0; j < stringArray.length; ++j) {
                this.dtj[j] = abs_0.kp(stringArray[j]);
            }
        }
    }

    public synchronized void x(String[] stringArray) {
        if (stringArray != null && stringArray.length > 0) {
            if (this.dtj != null && this.dtj.length > 0) {
                String[] stringArray2 = new String[stringArray.length + this.dtj.length];
                System.arraycopy(this.dtj, 0, stringArray2, 0, this.dtj.length);
                for (int j = 0; j < stringArray.length; ++j) {
                    stringArray2[this.dtj.length + j] = abs_0.kp(stringArray[j]);
                }
                this.dtj = stringArray2;
            } else {
                this.c(stringArray);
            }
        }
    }

    private static String kp(String string) {
        String string2 = string.replace('/', File.separatorChar).replace('\\', File.separatorChar);
        if (string2.endsWith(File.separator)) {
            string2 = string2 + "**";
        }
        return string2;
    }

    public synchronized void a(R[] rArray) {
        this.dtk = rArray;
    }

    public synchronized boolean aOb() {
        return this.dtv;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Unable to fully structure code
     */
    public void kA() {
        block42: {
            var1_1 = this.dtE;
            synchronized (var1_1) {
                if (this.dtD) {
                    while (this.dtD) {
                        try {
                            this.dtE.wait();
                        }
                        catch (InterruptedException var2_2) {}
                    }
                    if (this.dtH != null) {
                        throw this.dtH;
                    }
                    return;
                }
                this.dtD = true;
            }
            var1_1 = this;
            synchronized (var1_1) {
                this.dtH = null;
                this.aOd();
                v0 = var2_3 = this.dti == null;
                if (var2_3) {
                    v1 = new String[1];
                    v2 = v1;
                    v1[0] = "**";
                } else {
                    v2 = this.dti;
                }
                this.dti = v2;
                var3_5 = this.dtj == null;
                v3 = this.dtj = var3_5 != false ? new String[]{} : this.dtj;
                if (this.bFR != null) ** break block40
                if (!var2_3) ** GOTO lbl69
                // MONITOREXIT @DISABLED, blocks:[2, 3, 12] lbl39 : MonitorExitStatement: MONITOREXIT : var1_1
                var4_6 = this.dtE;
            }
            synchronized (var4_6) {
                this.dtD = false;
                this.dtE.notifyAll();
            }
            return;
            {
                if (this.bFR.exists()) ** break block41
                if (this.cWk) {
                    this.dtH = new IllegalStateException("basedir " + this.bFR + " does not exist");
                    ** break block41
                }
                // MONITOREXIT @DISABLED, blocks:[5, 12] lbl56 : MonitorExitStatement: MONITOREXIT : var1_1
                var4_7 = this.dtE;
            }
            synchronized (var4_7) {
                this.dtD = false;
                this.dtE.notifyAll();
            }
            return;
lbl-1000:
            // 2 sources

            {
                if (!this.bFR.isDirectory()) {
                    this.dtH = new IllegalStateException("basedir " + this.bFR + " is not a directory");
                }
                if (this.dtH != null) {
                    throw this.dtH;
                }
lbl69:
                // 3 sources

                if (this.kq("")) {
                    if (!this.kt("")) {
                        if (this.c("", this.bFR)) {
                            this.dto.addElement("");
                        } else {
                            this.dts.addElement("");
                        }
                    } else {
                        this.dtq.addElement("");
                    }
                } else {
                    this.dtp.addElement("");
                }
                this.aOc();
                this.clearCaches();
                this.dti = var2_3 != false ? null : this.dti;
                this.dtj = var3_5 != false ? null : this.dtj;
                break block42;
                {
                    catch (Throwable var7_10) {
                        throw var7_10;
                    }
                }
            }
            {
                finally {
                    var1_1 = this.dtE;
                    synchronized (var1_1) {
                        this.dtD = false;
                        this.dtE.notifyAll();
                    }
                }
            }
        }
    }

    private void aOc() {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        for (int j = 0; j < this.dti.length; ++j) {
            if (ga_2.isAbsolutePath(this.dti[j]) ? this.bFR != null && !zr_1.d(this.dti[j], this.bFR.getAbsolutePath(), this.aHy()) : this.bFR == null) continue;
            hashMap.put(zr_1.ha(this.dti[j]), this.dti[j]);
        }
        if (hashMap.containsKey("") && this.bFR != null) {
            this.a(this.bFR, "", true);
        } else {
            Iterator iterator = hashMap.entrySet().iterator();
            File file = null;
            if (this.bFR != null) {
                try {
                    file = this.bFR.getCanonicalFile();
                }
                catch (IOException iOException) {
                    throw new eq_2(iOException);
                }
            }
            while (iterator.hasNext()) {
                Object object;
                Map.Entry entry = iterator.next();
                String string = (String)entry.getKey();
                if (this.bFR == null && !ga_2.isAbsolutePath(string)) continue;
                String string2 = (String)entry.getValue();
                Object object2 = new File(this.bFR, string);
                if (((File)object2).exists()) {
                    try {
                        Object object3 = object = this.bFR == null ? ((File)object2).getCanonicalPath() : xa.c(file, ((File)object2).getCanonicalFile());
                        if ((!((String)object).equals(string) || dtd) && (object2 = this.b(this.bFR, string, true)) != null && this.bFR != null) {
                            string = xa.c(this.bFR, (File)object2);
                        }
                    }
                    catch (IOException iOException) {
                        throw new eq_2(iOException);
                    }
                }
                if (!(object2 != null && ((File)object2).exists() || this.aHy() || (object = this.b(this.bFR, string, false)) == null || !((File)object).exists())) {
                    string = this.bFR == null ? ((File)object).getAbsolutePath() : xa.c(this.bFR, (File)object);
                    object2 = object;
                }
                if (object2 == null || !((File)object2).exists() || !this.cWj && this.g(this.bFR, string)) continue;
                if (((File)object2).isDirectory()) {
                    if (this.kq(string) && string.length() > 0) {
                        this.a(string, (File)object2, true);
                        continue;
                    }
                    if (string.length() > 0 && string.charAt(string.length() - 1) != File.separatorChar) {
                        string = string + File.separatorChar;
                    }
                    this.a((File)object2, string, true);
                    continue;
                }
                boolean bl2 = this.aHy() ? string2.equals(string) : string2.equalsIgnoreCase(string);
                if (!bl2) continue;
                this.b(string, (File)object2);
            }
        }
    }

    protected synchronized void aOd() {
        this.dtl = new Vector();
        this.dtm = new Vector();
        this.dtn = new Vector();
        this.dtr = new Vector();
        this.dto = new Vector();
        this.dtp = new Vector();
        this.dtq = new Vector();
        this.dts = new Vector();
        this.dtv = this.bFR != null;
        this.dtx.clear();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void aOe() {
        Object object = this.dtG;
        synchronized (object) {
            if (this.dtt) {
                return;
            }
            if (this.dtF) {
                while (this.dtF) {
                    try {
                        this.dtG.wait();
                    }
                    catch (InterruptedException interruptedException) {}
                }
                return;
            }
            this.dtF = true;
        }
        try {
            object = this;
            synchronized (object) {
                String[] stringArray;
                boolean bl2;
                boolean bl3 = bl2 = this.dti == null;
                if (bl2) {
                    String[] stringArray2 = new String[1];
                    stringArray = stringArray2;
                    stringArray2[0] = "**";
                } else {
                    stringArray = this.dti;
                }
                this.dti = stringArray;
                boolean bl4 = this.dtj == null;
                this.dtj = bl4 ? new String[]{} : this.dtj;
                Object[] objectArray = new String[this.dtq.size()];
                this.dtq.copyInto(objectArray);
                Object[] objectArray2 = new String[this.dtp.size()];
                this.dtp.copyInto(objectArray2);
                this.y((String[])objectArray);
                this.y((String[])objectArray2);
                this.clearCaches();
                this.dti = bl2 ? null : this.dti;
                this.dtj = bl4 ? null : this.dtj;
            }
        }
        finally {
            object = this.dtG;
            synchronized (object) {
                this.dtt = true;
                this.dtF = false;
                this.dtG.notifyAll();
            }
        }
    }

    private void y(String[] stringArray) {
        for (int j = 0; j < stringArray.length; ++j) {
            if (this.kr(stringArray[j])) continue;
            this.a(new File(this.bFR, stringArray[j]), stringArray[j] + File.separator, false);
        }
    }

    protected void a(File file, String string, boolean bl2) {
        if (file == null) {
            throw new eq_2("dir must not be null.");
        }
        String[] stringArray = file.list();
        if (stringArray == null) {
            if (!file.exists()) {
                throw new eq_2(file + " doesn't exist.");
            }
            if (!file.isDirectory()) {
                throw new eq_2(file + " is not a directory.");
            }
            throw new eq_2("IO error scanning directory '" + file.getAbsolutePath() + "'");
        }
        this.a(file, string, bl2, stringArray);
    }

    private void a(File file, String string, boolean bl2, String[] stringArray) {
        Object object;
        Object object2;
        if (bl2 && this.ku(string)) {
            return;
        }
        if (!this.cWj) {
            Vector<String> vector = new Vector<String>();
            for (int j = 0; j < stringArray.length; ++j) {
                try {
                    if (xa.e(file, stringArray[j])) {
                        object2 = string + stringArray[j];
                        object = new File(file, stringArray[j]);
                        (((File)object).isDirectory() ? this.dtq : this.dtn).addElement(object2);
                        continue;
                    }
                    vector.addElement(stringArray[j]);
                    continue;
                }
                catch (IOException iOException) {
                    object = "IOException caught while checking for links, couldn't get canonical path!";
                    System.err.println((String)object);
                    vector.addElement(stringArray[j]);
                }
            }
            stringArray = vector.toArray(new String[vector.size()]);
        }
        for (int j = 0; j < stringArray.length; ++j) {
            String string2 = string + stringArray[j];
            object2 = new File(file, stringArray[j]);
            object = ((File)object2).list();
            if (object == null) {
                if (this.kq(string2)) {
                    this.b(string2, (File)object2);
                    continue;
                }
                this.dtv = false;
                this.dtm.addElement(string2);
                continue;
            }
            if (this.kq(string2)) {
                this.a(string2, (File)object2, bl2, (String[])object);
            } else {
                this.dtv = false;
                this.dtp.addElement(string2);
                if (bl2 && this.kr(string2)) {
                    this.a((File)object2, string2 + File.separator, bl2, (String[])object);
                }
            }
            if (bl2) continue;
            this.a((File)object2, string2 + File.separator, bl2, (String[])object);
        }
    }

    private void b(String string, File file) {
        this.a(string, file, this.dtl, this.dtn, this.dtr);
    }

    private void a(String string, File file, boolean bl2) {
        this.a(string, file, this.dto, this.dtq, this.dts);
        if (bl2 && this.kr(string) && !this.ks(string)) {
            this.a(file, string + File.separator, bl2);
        }
    }

    private void a(String string, File file, boolean bl2, String[] stringArray) {
        this.a(string, file, this.dto, this.dtq, this.dts);
        if (bl2 && this.kr(string) && !this.ks(string)) {
            this.a(file, string + File.separator, bl2, stringArray);
        }
    }

    private void a(String string, File file, Vector vector, Vector vector2, Vector vector3) {
        if (vector.contains(string) || vector2.contains(string) || vector3.contains(string)) {
            return;
        }
        boolean bl2 = false;
        if (this.kt(string)) {
            vector2.add(string);
        } else if (this.c(string, file)) {
            bl2 = true;
            vector.add(string);
        } else {
            vector3.add(string);
        }
        this.dtv &= bl2;
    }

    protected boolean kq(String string) {
        this.aOk();
        if (this.aHy() ? this.dty.contains(string) : this.dty.contains(string.toUpperCase())) {
            return true;
        }
        for (int j = 0; j < this.dtA.length; ++j) {
            if (!abs_0.e(this.dtA[j], string, this.aHy())) continue;
            return true;
        }
        return false;
    }

    protected boolean kr(String string) {
        for (int j = 0; j < this.dti.length; ++j) {
            if (!abs_0.d(this.dti[j], string, this.aHy()) || !this.an(string, this.dti[j]) || !this.am(this.dti[j], string)) continue;
            return true;
        }
        return false;
    }

    private boolean am(String string, String string2) {
        Vector vector = zr_1.gW(string);
        Vector vector2 = zr_1.gW(string2);
        return vector.contains("**") || vector.size() > vector2.size();
    }

    private boolean an(String string, String string2) {
        String string3 = string + File.separator + "**";
        for (int j = 0; j < this.dtj.length; ++j) {
            if (!this.dtj[j].equals(string3)) continue;
            return false;
        }
        return true;
    }

    private boolean ks(String string) {
        string = string.endsWith(File.separator) ? string : string + File.separator;
        for (int j = 0; j < this.dtj.length; ++j) {
            String string2 = this.dtj[j];
            if (!string2.endsWith("**") || !zr_1.e(string2.substring(0, string2.length() - 2), string, this.aHy())) continue;
            return true;
        }
        return false;
    }

    protected boolean kt(String string) {
        this.aOk();
        if (this.aHy() ? this.dtz.contains(string) : this.dtz.contains(string.toUpperCase())) {
            return true;
        }
        for (int j = 0; j < this.dtB.length; ++j) {
            if (!abs_0.e(this.dtB[j], string, this.aHy())) continue;
            return true;
        }
        return false;
    }

    protected boolean c(String string, File file) {
        if (this.dtk != null) {
            for (int j = 0; j < this.dtk.length; ++j) {
                if (this.dtk[j].a(this.bFR, string, file)) continue;
                return false;
            }
        }
        return true;
    }

    public synchronized String[] kx() {
        if (this.dtl == null) {
            throw new IllegalStateException("Must call scan() first");
        }
        Object[] objectArray = new String[this.dtl.size()];
        this.dtl.copyInto(objectArray);
        Arrays.sort(objectArray);
        return objectArray;
    }

    public synchronized int aOf() {
        if (this.dtl == null) {
            throw new IllegalStateException("Must call scan() first");
        }
        return this.dtl.size();
    }

    public synchronized String[] kz() {
        this.aOe();
        Object[] objectArray = new String[this.dtm.size()];
        this.dtm.copyInto(objectArray);
        return objectArray;
    }

    public synchronized String[] kv() {
        this.aOe();
        Object[] objectArray = new String[this.dtn.size()];
        this.dtn.copyInto(objectArray);
        return objectArray;
    }

    public synchronized String[] aOg() {
        this.aOe();
        Object[] objectArray = new String[this.dtr.size()];
        this.dtr.copyInto(objectArray);
        return objectArray;
    }

    public synchronized String[] kw() {
        if (this.dto == null) {
            throw new IllegalStateException("Must call scan() first");
        }
        Object[] objectArray = new String[this.dto.size()];
        this.dto.copyInto(objectArray);
        Arrays.sort(objectArray);
        return objectArray;
    }

    public synchronized int aOh() {
        if (this.dto == null) {
            throw new IllegalStateException("Must call scan() first");
        }
        return this.dto.size();
    }

    public synchronized String[] ky() {
        this.aOe();
        Object[] objectArray = new String[this.dtp.size()];
        this.dtp.copyInto(objectArray);
        return objectArray;
    }

    public synchronized String[] ku() {
        this.aOe();
        Object[] objectArray = new String[this.dtq.size()];
        this.dtq.copyInto(objectArray);
        return objectArray;
    }

    public synchronized String[] aOi() {
        this.aOe();
        Object[] objectArray = new String[this.dts.size()];
        this.dts.copyInto(objectArray);
        return objectArray;
    }

    public synchronized void ks() {
        int n2 = this.dtj == null ? 0 : this.dtj.length;
        String[] stringArray = new String[n2 + dth.size()];
        if (n2 > 0) {
            System.arraycopy(this.dtj, 0, stringArray, 0, n2);
        }
        String[] stringArray2 = abs_0.aNZ();
        for (int j = 0; j < stringArray2.length; ++j) {
            stringArray[j + n2] = stringArray2[j].replace('/', File.separatorChar).replace('\\', File.separatorChar);
        }
        this.dtj = stringArray;
    }

    public synchronized iv_1 gj(String string) {
        return new ash_0(this.bFR, string);
    }

    private String[] list(File file) {
        String[] stringArray = (String[])this.dtw.get(file);
        if (stringArray == null && (stringArray = file.list()) != null) {
            this.dtw.put(file, stringArray);
        }
        return stringArray;
    }

    private File b(File file, String string, boolean bl2) {
        if (ga_2.isAbsolutePath(string)) {
            if (file == null) {
                String[] stringArray = xa.ea(string);
                file = new File(stringArray[0]);
                string = stringArray[1];
            } else {
                File file2 = xa.dZ(string);
                String string2 = xa.c(file, file2);
                if (string2.equals(file2.getAbsolutePath())) {
                    return null;
                }
                string = string2;
            }
        }
        return this.a(file, zr_1.gW(string), bl2);
    }

    private File a(File file, Vector vector, boolean bl2) {
        if (vector.size() == 0) {
            return file;
        }
        String string = (String)vector.remove(0);
        if (file == null) {
            return this.a(new File(string), vector, bl2);
        }
        if (!file.isDirectory()) {
            return null;
        }
        String[] stringArray = this.list(file);
        if (stringArray == null) {
            throw new eq_2("IO error scanning directory " + file.getAbsolutePath());
        }
        boolean[] blArray = bl2 ? dtf : dtg;
        for (int j = 0; j < blArray.length; ++j) {
            for (int i2 = 0; i2 < stringArray.length; ++i2) {
                if (!(blArray[j] ? stringArray[i2].equals(string) : stringArray[i2].equalsIgnoreCase(string))) continue;
                return this.a(new File(file, stringArray[i2]), vector, bl2);
            }
        }
        return null;
    }

    private boolean g(File file, String string) {
        return this.a(file, zr_1.gW(string));
    }

    private boolean a(File file, Vector vector) {
        if (vector.size() > 0) {
            String string = (String)vector.remove(0);
            try {
                return xa.e(file, string) || this.a(new File(file, string), vector);
            }
            catch (IOException iOException) {
                String string2 = "IOException caught while checking for links, couldn't get canonical path!";
                System.err.println(string2);
            }
        }
        return false;
    }

    private boolean ku(String string) {
        return !this.dtx.add(string);
    }

    Set aOj() {
        return this.dtx;
    }

    private synchronized void clearCaches() {
        this.dtw.clear();
        this.dty.clear();
        this.dtz.clear();
        this.dtA = null;
        this.dtB = null;
        this.dtC = false;
    }

    private synchronized void aOk() {
        if (!this.dtC) {
            this.dtA = this.a(this.dty, this.dti);
            this.dtB = this.a(this.dtz, this.dtj);
            this.dtC = true;
        }
    }

    private String[] a(Set set, String[] stringArray) {
        ArrayList<String> arrayList = new ArrayList<String>(stringArray.length);
        for (int j = 0; j < stringArray.length; ++j) {
            if (!zr_1.gZ(stringArray[j])) {
                set.add(this.aHy() ? stringArray[j] : stringArray[j].toUpperCase());
                continue;
            }
            arrayList.add(stringArray[j]);
        }
        return set.size() == 0 ? stringArray : arrayList.toArray(new String[arrayList.size()]);
    }

    static {
        abs_0.aOa();
    }
}

