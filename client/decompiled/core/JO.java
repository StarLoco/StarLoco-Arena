/*
 * Decompiled with CFR 0.152.
 */
import java.io.PrintWriter;
import java.util.HashMap;

public class JO
extends zp_1 {
    private int bmF = 0;

    public JO(PrintWriter printWriter, String string, String string2) {
        super(printWriter, string, string2);
        this.j(lw_0.class);
        this.j(vf_2.class);
        this.j(HashMap.class);
        this.aGk = new go_0("init", null);
        this.aGj.add(this.aGk);
    }

    public void a(oy_0 oy_02) {
        if (((go_0)this.aGk).isFull()) {
            this.Wk();
            ++this.bmF;
        }
        super.a(oy_02);
    }

    public void Wk() {
        this.a(new go_0("init" + this.aGl++, (go_0)this.aGk));
    }

    public void yi() {
        aza aza2 = new aza(null, ((go_0)this.aGk).getMethodName(), null);
        super.yi();
        ((go_0)this.aGk).a(aza2);
    }

    public void yj() {
        for (int j = this.bmF - 1; j >= 0; --j) {
            this.yi();
        }
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
        this.Qc.println("public class " + this.m_name + " implements StyleProvider {");
        this.Qc.println();
        this.Qc.println("\tprivate HashMap<String, StyleSetter> m_setters = new HashMap<String, StyleSetter>();");
        this.Qc.println("\tpublic " + this.m_name + "() {");
        this.Qc.println("\t\tinit();");
        this.Qc.println("\t}");
        this.Qc.println("\tpublic StyleSetter getStyleSetter(String style) {");
        this.Qc.println("\t\treturn m_setters.get(style);");
        this.Qc.println("\t}");
        this.Qc.println();
        for (Object object : this.aGj) {
            ((go_0)object).a(this.Qc);
            this.Qc.println();
        }
        this.Qc.println("}");
        this.Qc.flush();
    }
}

