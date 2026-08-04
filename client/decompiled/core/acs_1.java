/*
 * Decompiled with CFR 0.152.
 */
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

/*
 * Renamed from acs
 */
public class acs_1
extends ur_0 {
    private static final String cjP = "doc";
    private air_1 cjQ;

    public acs_1(PrintWriter printWriter, String string, String string2, k_0 k_02, air_1 air_12, DS dS) {
        super(printWriter, string, string2, k_02);
        this.j(vf_2.class);
        ((amr_2)this.aGk).a(air_12, "element", true);
    }

    protected void p(k_0 k_02) {
        this.aGk = new amr_2(k_02, null, null, "applyStyle", null, true);
        this.aGj.add(this.aGk);
    }

    public void a(k_0 k_02, air_1 air_12, String string) {
        this.a(new amr_2(k_02, air_12, string, "method" + this.aGl++, (amr_2)this.aGk, false));
    }

    public void yi() {
        aza aza2 = new aza(null, this.aGk.getMethodName(), null, this.aGk.aPJ());
        this.aGk = this.aGk.aPK();
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
        this.Qc.println("public class " + this.m_name + " implements StyleSetter {");
        this.Qc.println();
        this.Qc.println("private DocumentParser doc;");
        this.Qc.println("private Stack<ElementMap> elementMaps = new Stack<ElementMap>();");
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

