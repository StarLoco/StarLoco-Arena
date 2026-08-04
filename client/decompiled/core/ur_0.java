/*
 * Decompiled with CFR 0.152.
 */
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

/*
 * Renamed from UR
 */
public class ur_0
extends zp_1 {
    private k_0 bRM;

    public ur_0(PrintWriter printWriter, String string, String string2, k_0 k_02) {
        super(printWriter, string, string2);
        this.p(k_02);
        this.j(air_1.class);
        this.j(na_1.class);
        this.j(aGm.class);
        this.j(k_0.class);
        this.j(Stack.class);
    }

    protected void p(k_0 k_02) {
        this.aGk = new aGB(k_02, null, null, "getElement", null, true);
        this.aGj.add(this.aGk);
    }

    public String ahU() {
        return ((aGB)this.aGj.get(0)).ahU();
    }

    public String ahV() {
        return ((aGB)this.aGj.get(0)).ahV();
    }

    public void a(k_0 k_02, air_1 air_12, String string) {
        this.a(new aGB(k_02, air_12, string, "method" + this.aGl++, (aGB)this.aGk, false));
    }

    public void yi() {
        aza aza2 = new aza(null, this.aGk.getMethodName(), null, this.aGk.aPJ());
        super.yi();
        this.aGk.a(aza2);
    }

    public void yj() {
        Object object;
        int n2 = this.aGj.size();
        for (int j = 0; j < n2; ++j) {
            aGB aGB2 = (aGB)this.aGj.get(j);
            object = aGB2.aPL();
            int n3 = ((ArrayList)object).size();
            for (int i2 = 0; i2 < n3; ++i2) {
                Class clazz = ((oy_0)((ArrayList)object).get(i2)).abM();
                if (clazz == null) continue;
                this.j(clazz);
            }
        }
        if (this.aGh != null) {
            this.Qc.println("package " + this.aGh + ";");
        }
        this.Qc.println();
        Iterator iterator = this.aGi.iterator();
        while (iterator.hasNext()) {
            this.Qc.println("import " + ((Class)iterator.next()).getCanonicalName() + ";");
        }
        this.Qc.println();
        this.Qc.println("public class " + this.m_name + " implements BasicElementFactory {");
        this.Qc.println();
        this.Qc.println("private Stack<ElementMap> elementMaps = new Stack<ElementMap>();");
        this.Qc.println("private Environment env;");
        this.Qc.println();
        int n4 = this.aGj.size();
        for (n2 = 0; n2 < n4; ++n2) {
            object = (aGB)this.aGj.get(n2);
            ((aGB)object).a(this.Qc);
            this.Qc.println();
        }
        this.Qc.println("}");
        this.Qc.flush();
    }
}

