/*
 * Decompiled with CFR 0.152.
 */
import java.io.PrintWriter;

/*
 * Renamed from sf
 */
public class sf_1
extends zp_1 {
    static final String aiR = "doc";
    private final DS Gb;

    public sf_1(PrintWriter printWriter, String string, String string2, DS dS) {
        super(printWriter, string, string2);
        this.aGk = new kZ(dS, aiR, "initTheme", null, true);
        this.aGj.add(this.aGk);
        this.j(afn_2.class);
        this.j(DS.class);
        this.j(vP.class);
        this.Qc = printWriter;
        this.Gb = dS;
    }

    public String yg() {
        return aiR;
    }

    public DS yh() {
        return this.Gb;
    }

    public void b(DS dS) {
        this.a(new kZ(dS, aiR, "method" + this.aGl++, (kZ)this.aGk, false));
    }

    public void yi() {
        aza aza2 = new aza(null, ((kZ)this.aGk).getMethodName(), null, ((kZ)this.aGk).aPJ());
        super.yi();
        ((kZ)this.aGk).a(aza2);
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
        this.Qc.println("public class " + this.m_name + " implements ThemeLoader {");
        this.Qc.println();
        for (Object object : this.aGj) {
            ((kZ)object).a(this.Qc);
            this.Qc.println();
        }
        this.Qc.println("}");
        this.Qc.flush();
    }
}

