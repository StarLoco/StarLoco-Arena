/*
 * Decompiled with CFR 0.152.
 */
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Reader;
import java.util.Hashtable;
import java.util.Properties;

/*
 * Renamed from acQ
 */
public final class acq_1
extends aqk_0
implements gx_2 {
    private static final char ckU = '@';
    private static final char ckV = '@';
    private String OA = null;
    private String ckW = null;
    private int ckX = -1;
    private int ckY = -1;
    private Hashtable ckZ = new Hashtable();
    private char cla = (char)64;
    private char clb = (char)64;

    public acq_1() {
    }

    public acq_1(Reader reader) {
        super(reader);
    }

    private int arD() {
        if (this.ckY != -1) {
            char c = this.OA.charAt(this.ckY++);
            if (this.ckY >= this.OA.length()) {
                this.ckY = -1;
            }
            return c;
        }
        return this.in.read();
    }

    public int read() {
        if (!this.aCg()) {
            this.initialize();
            this.bk(true);
        }
        if (this.ckX != -1) {
            char c = this.ckW.charAt(this.ckX++);
            if (this.ckX >= this.ckW.length()) {
                this.ckX = -1;
            }
            return c;
        }
        int n2 = this.arD();
        if (n2 == this.cla) {
            StringBuffer stringBuffer = new StringBuffer("");
            while ((n2 = this.arD()) != -1) {
                stringBuffer.append((char)n2);
                if (n2 != this.clb) continue;
            }
            if (n2 == -1) {
                this.OA = this.OA == null || this.ckY == -1 ? stringBuffer.toString() : stringBuffer.toString() + this.OA.substring(this.ckY);
                this.ckY = 0;
                return this.cla;
            }
            stringBuffer.setLength(stringBuffer.length() - 1);
            String string = (String)this.ckZ.get(stringBuffer.toString());
            if (string != null) {
                if (string.length() > 0) {
                    this.ckW = string;
                    this.ckX = 0;
                }
                return this.read();
            }
            String string2 = stringBuffer.toString() + this.clb;
            this.OA = this.OA == null || this.ckY == -1 ? string2 : string2 + this.OA.substring(this.ckY);
            this.ckY = 0;
            return this.cla;
        }
        return n2;
    }

    public void e(char c) {
        this.cla = c;
    }

    private char arE() {
        return this.cla;
    }

    public void f(char c) {
        this.clb = c;
    }

    private char arF() {
        return this.clb;
    }

    public void a(hi_0 hi_02) {
        this.ckZ.put(hi_02.getKey(), hi_02.getValue());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Properties hu(String string) {
        FileInputStream fileInputStream = null;
        Properties properties = new Properties();
        try {
            fileInputStream = new FileInputStream(string);
            properties.load(fileInputStream);
        }
        catch (IOException iOException) {
            try {
                iOException.printStackTrace();
            }
            catch (Throwable throwable) {
                ga_2.h(fileInputStream);
                throw throwable;
            }
            ga_2.h(fileInputStream);
        }
        ga_2.h(fileInputStream);
        return properties;
    }

    private void a(Hashtable hashtable) {
        this.ckZ = hashtable;
    }

    private Hashtable arG() {
        return this.ckZ;
    }

    public Reader b(Reader reader) {
        acq_1 acq_12 = new acq_1(reader);
        acq_12.e(this.arE());
        acq_12.f(this.arF());
        acq_12.a(this.arG());
        acq_12.bk(true);
        return acq_12;
    }

    private void initialize() {
        vj_0[] vj_0Array = this.JT();
        if (vj_0Array != null) {
            for (int j = 0; j < vj_0Array.length; ++j) {
                Object object;
                Object object2;
                if (vj_0Array[j] == null) continue;
                String string = vj_0Array[j].getType();
                if ("tokenchar".equals(string)) {
                    object2 = vj_0Array[j].getName();
                    object = vj_0Array[j].getValue();
                    if ("begintoken".equals(object2)) {
                        if (((String)object).length() == 0) {
                            throw new eq_2("Begin token cannot be empty");
                        }
                        this.cla = vj_0Array[j].getValue().charAt(0);
                        continue;
                    }
                    if (!"endtoken".equals(object2)) continue;
                    if (((String)object).length() == 0) {
                        throw new eq_2("End token cannot be empty");
                    }
                    this.clb = vj_0Array[j].getValue().charAt(0);
                    continue;
                }
                if ("token".equals(string)) {
                    object2 = vj_0Array[j].getName();
                    object = vj_0Array[j].getValue();
                    this.ckZ.put(object2, object);
                    continue;
                }
                if (!"propertiesfile".equals(string)) continue;
                object2 = this.hu(vj_0Array[j].getValue());
                object = ((Hashtable)object2).keys();
                while (object.hasMoreElements()) {
                    String string2 = (String)object.nextElement();
                    String string3 = ((Properties)object2).getProperty(string2);
                    this.ckZ.put(string2, string3);
                }
            }
        }
    }
}

