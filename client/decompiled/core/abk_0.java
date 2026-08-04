/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;
import org.apache.log4j.Logger;

/*
 * Renamed from abk
 */
public class abk_0 {
    protected static final Logger a = Logger.getLogger(abk_0.class);
    public static final boolean chq = false;
    public static final double chr = 0.0;
    public static final float chs = 0.0f;
    public static final int cht = 0;
    public static final long chu = 0L;
    public static final String chv = "";
    private final Properties nV;
    private final Properties chw;
    private boolean apf = false;
    private String eA;
    private boolean chx = false;
    private boolean chy = false;
    private final ArrayList chz = new ArrayList();
    private final ArrayList chA = new ArrayList();

    public abk_0() {
        this.chw = new Properties();
        this.nV = new Properties(this.chw);
    }

    public abk_0(String string) {
        this();
        this.setFileName(string);
    }

    public String getFileName() {
        return this.eA;
    }

    public void setFileName(String string) {
        this.eA = string;
    }

    public boolean apK() {
        return this.chx;
    }

    public void cS(boolean bl2) {
        this.chx = bl2;
    }

    public boolean apL() {
        return this.chy;
    }

    public void cT(boolean bl2) {
        this.chy = bl2;
    }

    public void load() {
        if (this.eA != null) {
            FileInputStream fileInputStream = new FileInputStream(this.eA);
            this.load(fileInputStream);
            fileInputStream.close();
        }
    }

    public void load(InputStream inputStream) {
        this.nV.load(inputStream);
        this.apf = false;
        this.apM();
    }

    private void apM() {
        if (this.chA.size() > 0) {
            for (int j = 0; j < this.chA.size(); ++j) {
                HV hV = (HV)this.chA.get(j);
                hV.TB();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void save() {
        if (this.eA == null) {
            throw new IOException("File name not specified");
        }
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(this.eA);
            this.save(fileOutputStream);
        }
        finally {
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
        }
    }

    public void save(OutputStream outputStream) {
        this.nV.store(outputStream, null);
        this.apf = false;
    }

    public String[] apN() {
        ArrayList arrayList = new ArrayList();
        Enumeration<?> enumeration = this.chw.propertyNames();
        while (enumeration.hasMoreElements()) {
            arrayList.add(enumeration.nextElement());
        }
        enumeration = this.nV.propertyNames();
        while (enumeration.hasMoreElements()) {
            Object obj = enumeration.nextElement();
            if (arrayList.contains(obj)) continue;
            arrayList.add(obj);
        }
        return arrayList.toArray(new String[arrayList.size()]);
    }

    public void zd() {
        Enumeration<?> enumeration = this.nV.propertyNames();
        this.nV.clear();
        while (enumeration.hasMoreElements()) {
            Object obj = enumeration.nextElement();
            this.nV.setProperty(String.valueOf(obj), String.valueOf(this.chw.getProperty(String.valueOf(obj))));
        }
        try {
            this.save();
        }
        catch (IOException iOException) {
            a.error((Object)"Exception", (Throwable)iOException);
        }
    }

    public boolean apO() {
        return this.apf;
    }

    public void a(anf_2 anf_22) {
        this.chz.add(anf_22);
    }

    public void b(anf_2 anf_22) {
        this.chz.remove(anf_22);
    }

    public void a(HV hV) {
        this.chA.add(hV);
    }

    public void b(HV hV) {
        this.chA.remove(hV);
    }

    public void apP() {
        this.chz.clear();
    }

    public boolean contains(String string) {
        return this.nV.containsKey(string) || this.chw.containsKey(string);
    }

    public Enumeration apQ() {
        return this.nV.keys();
    }

    public void hh(String string) {
        Object v = this.nV.remove(string);
        if (v != null) {
            this.f(string, v, null);
            if (this.chx) {
                try {
                    this.save();
                }
                catch (IOException iOException) {
                    // empty catch block
                }
            }
        }
    }

    public boolean hi(String string) {
        return this.a(this.chw, string);
    }

    public double hj(String string) {
        return this.b(this.chw, string);
    }

    public float hk(String string) {
        return this.c(this.chw, string);
    }

    public int hl(String string) {
        return this.d(this.chw, string);
    }

    public long hm(String string) {
        return this.e(this.chw, string);
    }

    public String hn(String string) {
        return this.f(this.chw, string);
    }

    public boolean ho(String string) {
        return !this.nV.containsKey(string) && this.chw.containsKey(string);
    }

    public String getValue(String string) {
        if (this.nV.containsKey(string)) {
            return this.nV.getProperty(string);
        }
        if (this.chw.containsKey(string)) {
            return this.chw.getProperty(string);
        }
        return null;
    }

    public boolean getBoolean(String string) {
        return this.a(null, string);
    }

    public double getDouble(String string) {
        return this.b(null, string);
    }

    public float getFloat(String string) {
        return this.c(null, string);
    }

    public int getInt(String string) {
        return this.d(null, string);
    }

    public long getLong(String string) {
        return this.e(null, string);
    }

    public String getString(String string) {
        return this.f(null, string);
    }

    public void s(String string, boolean bl2) {
        this.a(this.chw, string, bl2);
    }

    public void b(String string, double d) {
        this.a(this.chw, string, d);
    }

    public void a(String string, float f) {
        this.a(this.chw, string, f);
    }

    public void q(String string, int n2) {
        this.a(this.chw, string, n2);
    }

    public void f(String string, long l2) {
        this.a(this.chw, string, l2);
    }

    public void S(String string, String string2) {
        this.a(this.chw, string, string2);
    }

    public void t(String string, boolean bl2) {
        boolean bl3 = this.getBoolean(string);
        if (bl3 != bl2 || !this.contains(string)) {
            if (this.chy && !this.chw.containsKey(string)) {
                return;
            }
            this.a(this.nV, string, bl2);
            this.apf = true;
            this.f(string, bl3, bl2);
            if (this.chx) {
                try {
                    this.save();
                }
                catch (IOException iOException) {
                    // empty catch block
                }
            }
        }
    }

    public void c(String string, double d) {
        double d2 = this.getDouble(string);
        if (d2 != d || !this.contains(string)) {
            if (this.chy && !this.chw.containsKey(string)) {
                return;
            }
            this.a(this.nV, string, d);
            this.apf = true;
            this.f(string, d2, d);
            if (this.chx) {
                try {
                    this.save();
                }
                catch (IOException iOException) {
                    // empty catch block
                }
            }
        }
    }

    public void b(String string, float f) {
        float f2 = this.getFloat(string);
        if (f2 != f || !this.contains(string)) {
            if (this.chy && !this.chw.containsKey(string)) {
                return;
            }
            this.a(this.nV, string, f);
            this.apf = true;
            this.f(string, Float.valueOf(f2), Float.valueOf(f));
            if (this.chx) {
                try {
                    this.save();
                }
                catch (IOException iOException) {
                    // empty catch block
                }
            }
        }
    }

    public void r(String string, int n2) {
        int n3 = this.getInt(string);
        if (n3 != n2 || !this.contains(string)) {
            if (this.chy && !this.chw.containsKey(string)) {
                return;
            }
            this.a(this.nV, string, n2);
            this.apf = true;
            this.f(string, n3, n2);
            if (this.chx) {
                try {
                    this.save();
                }
                catch (IOException iOException) {
                    // empty catch block
                }
            }
        }
    }

    public void g(String string, long l2) {
        long l3 = this.getLong(string);
        if (l3 != l2 || !this.contains(string)) {
            if (this.chy && !this.chw.containsKey(string)) {
                return;
            }
            this.a(this.nV, string, l2);
            this.apf = true;
            this.f(string, l3, l2);
            if (this.chx) {
                try {
                    this.save();
                }
                catch (IOException iOException) {
                    // empty catch block
                }
            }
        }
    }

    public void T(String string, String string2) {
        String string3 = this.getString(string);
        if (string3 == null || !string3.equals(string2) || !this.contains(string)) {
            if (this.chy && !this.chw.containsKey(string)) {
                return;
            }
            this.a(this.nV, string, string2);
            this.apf = true;
            this.f(string, string3, string2);
            if (this.chx) {
                try {
                    this.save();
                }
                catch (IOException iOException) {
                    // empty catch block
                }
            }
        }
    }

    public void f(String string, Object object, Object object2) {
        if (!(this.chz.size() <= 0 || object != null && object.equals(object2))) {
            ajw ajw2 = new ajw(this, string, object, object2);
            for (int j = 0; j < this.chz.size(); ++j) {
                anf_2 anf_22 = (anf_2)this.chz.get(j);
                anf_22.a(ajw2);
            }
        }
    }

    private boolean a(Properties properties, String string) {
        String string2;
        String string3 = string2 = properties != null ? properties.getProperty(string) : this.getValue(string);
        if (string2 == null) {
            return false;
        }
        return Boolean.valueOf(string2);
    }

    private double b(Properties properties, String string) {
        String string2;
        String string3 = string2 = properties != null ? properties.getProperty(string) : this.getValue(string);
        if (string2 == null) {
            return 0.0;
        }
        double d = 0.0;
        try {
            d = Double.valueOf(string2);
        }
        catch (NumberFormatException numberFormatException) {
            // empty catch block
        }
        return d;
    }

    private float c(Properties properties, String string) {
        String string2;
        String string3 = string2 = properties != null ? properties.getProperty(string) : this.getValue(string);
        if (string2 == null) {
            return 0.0f;
        }
        float f = 0.0f;
        try {
            f = Float.valueOf(string2).floatValue();
        }
        catch (NumberFormatException numberFormatException) {
            // empty catch block
        }
        return f;
    }

    private int d(Properties properties, String string) {
        String string2;
        String string3 = string2 = properties != null ? properties.getProperty(string) : this.getValue(string);
        if (string2 == null) {
            return 0;
        }
        int n2 = 0;
        try {
            n2 = Integer.valueOf(string2);
        }
        catch (NumberFormatException numberFormatException) {
            // empty catch block
        }
        return n2;
    }

    private long e(Properties properties, String string) {
        String string2;
        String string3 = string2 = properties != null ? properties.getProperty(string) : this.getValue(string);
        if (string2 == null) {
            return 0L;
        }
        long l2 = 0L;
        try {
            l2 = Long.valueOf(string2);
        }
        catch (NumberFormatException numberFormatException) {
            // empty catch block
        }
        return l2;
    }

    private String f(Properties properties, String string) {
        String string2;
        String string3 = string2 = properties != null ? properties.getProperty(string) : this.getValue(string);
        if (string2 == null) {
            return chv;
        }
        return string2;
    }

    private void a(Properties properties, String string, boolean bl2) {
        if (properties != null) {
            properties.put(string, Boolean.toString(bl2));
        }
    }

    private void a(Properties properties, String string, double d) {
        if (properties != null) {
            properties.put(string, Double.toString(d));
        }
    }

    private void a(Properties properties, String string, float f) {
        if (properties != null) {
            properties.put(string, Float.toString(f));
        }
    }

    private void a(Properties properties, String string, int n2) {
        if (properties != null) {
            properties.put(string, Integer.toString(n2));
        }
    }

    private void a(Properties properties, String string, long l2) {
        if (properties != null) {
            properties.put(string, Long.toString(l2));
        }
    }

    private void a(Properties properties, String string, String string2) {
        if (properties != null) {
            properties.put(string, string2);
        }
    }
}

