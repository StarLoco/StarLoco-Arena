/*
 * Decompiled with CFR 0.152.
 */
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 * Renamed from zP
 */
public abstract class zp_1 {
    protected String m_name;
    protected String aGh;
    protected final HashSet aGi = new HashSet();
    protected final ArrayList aGj = new ArrayList();
    protected adq_0 aGk = null;
    protected PrintWriter Qc;
    protected int aGl = 0;

    public zp_1(PrintWriter printWriter, String string, String string2) {
        this.m_name = string == null || string.length() == 0 ? "Class0" : string;
        this.aGh = string2;
        this.Qc = printWriter;
    }

    protected void init() {
    }

    public void j(Class clazz) {
        if (!clazz.isPrimitive()) {
            this.aGi.add(clazz);
        }
    }

    public void a(oy_0 oy_02) {
        this.aGk.a(oy_02);
    }

    public String L(Object object) {
        return this.aGk.L(object);
    }

    public Object dn(String string) {
        return this.aGk.dn(string);
    }

    public String GQ() {
        return this.aGk.GQ();
    }

    public void h(String string, Object object) {
        this.aGk.h(string, object);
    }

    public boolean do(String string) {
        return this.aGk.do(string);
    }

    public void dp(String string) {
        this.aGk.dp(string);
    }

    public String getClassName() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.m_name.substring(0, 1).toUpperCase());
        if (this.m_name.length() > 1) {
            stringBuilder.append(this.m_name.substring(0, this.m_name.length()));
        }
        return stringBuilder.toString();
    }

    public boolean GR() {
        return this.aGk.isFull();
    }

    public void mark() {
        this.aGk.mark();
    }

    public void resetMark() {
        this.aGk.resetMark();
    }

    public void GS() {
        this.aGk.GS();
    }

    protected void a(adq_0 adq_02) {
        this.aGk.b(adq_02);
        this.aGk = adq_02;
        this.aGj.add(this.aGk);
    }

    public void yi() {
        this.aGk = this.aGk.aPK();
    }

    public void yj() {
        for (Object object : this.aGj) {
            for (oy_0 oy_02 : ((adq_0)object).aPL()) {
                Class clazz = oy_02.abM();
                if (clazz == null) continue;
                this.j(clazz);
            }
        }
        if (this.aGh != null) {
            this.Qc.println("package " + this.aGh + ";");
        }
        this.Qc.println();
        for (Object object : this.aGi) {
            this.Qc.println("import " + ((Class)object).getCanonicalName() + ";");
        }
        this.Qc.println();
        this.Qc.println("public class " + this.m_name + " implements BasicElementFactory {");
        this.Qc.println();
        this.Qc.println("private Stack<ElementMap> elementMaps = new Stack<ElementMap>();");
        this.Qc.println("private Environment env;");
        this.Qc.println();
        for (Object object : this.aGj) {
            ((adq_0)object).a(this.Qc);
            this.Qc.println();
        }
        this.Qc.println("}");
        this.Qc.flush();
    }
}

