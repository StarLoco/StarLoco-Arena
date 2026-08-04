/*
 * Decompiled with CFR 0.152.
 */
import java.io.PrintWriter;

public class aGB
extends adq_0 {
    protected air_1 cjQ;
    protected String dJi;
    protected String dJj;
    protected String dJk = null;

    public aGB(k_0 k_02, air_1 air_12, String string, String string2, aGB aGB2, boolean bl2) {
        super(string, string2, aGB2, bl2);
        this.init(bl2);
        this.dJj = this.L(k_02);
        this.b(air_12, string);
        this.dJk = null;
    }

    protected void init(boolean bl2) {
        if (bl2) {
            this.a(new aKI(afq_1.class, "env", "environment", true));
            this.a(new aza(null, "push", "elementMaps", "currentElementMap"));
        }
        this.a(new aKI(aji_1.class, "elementMap", "elementMaps.peek()"));
    }

    String L(Object object) {
        String string = super.L(object);
        if (this.dJk == null && object instanceof na_1) {
            this.dJk = string;
        }
        return string;
    }

    public air_1 aSN() {
        return this.cjQ;
    }

    public String ahU() {
        return this.dJi;
    }

    public String ahV() {
        return this.dJj;
    }

    public String aSO() {
        return this.dJk;
    }

    public void h(String string, Object object) {
        super.h(string, object);
        if (this.dJk == null && object instanceof na_1) {
            this.dJk = string;
        }
    }

    public void a(air_1 air_12, String string, boolean bl2) {
        this.cjQ = air_12;
        this.dzj = string;
        if (this.cjQ != null) {
            this.dJi = this.L(air_12);
        }
        if (bl2) {
            this.dJi = string;
        }
        if (this.cjQ != null && this.dzj != null) {
            this.h(this.dzj, this.cjQ);
        }
    }

    public void b(air_1 air_12, String string) {
        this.a(air_12, string, false);
    }

    public void a(PrintWriter printWriter) {
        this.dzo.clear();
        if (this.dzj == null && this.cjQ == null) {
            printWriter.println("\tpublic BasicElement " + this.getMethodName() + "(Environment environment, ElementMap currentElementMap) {");
        } else {
            printWriter.println("\tpublic BasicElement " + this.getMethodName() + "(BasicElement " + this.dzj + ") {");
        }
        for (oy_0 oy_02 : this.dzm) {
            printWriter.println("\t\t" + oy_02.a(this));
        }
        printWriter.println("\t\treturn " + this.dJk + ";");
        printWriter.println("\t}");
    }
}

