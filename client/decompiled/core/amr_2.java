/*
 * Decompiled with CFR 0.152.
 */
import java.io.PrintWriter;

/*
 * Renamed from aMR
 */
public class amr_2
extends aGB {
    private static final String dYL = "doc";
    private boolean dYM;

    public amr_2(k_0 k_02, air_1 air_12, String string, String string2, amr_2 amr_22, boolean bl2) {
        super(k_02, air_12, string, string2, amr_22, bl2);
        this.dYM = bl2;
    }

    protected void init(boolean bl2) {
        if (bl2) {
            this.a(new aKI(DS.class, dYL, "parser", true));
            this.a(new aza(null, "push", "elementMaps", "currentElementMap"));
        }
        this.a(new aKI(aji_1.class, "elementMap", "elementMaps.peek()"));
    }

    public String yg() {
        return dYL;
    }

    public void b(air_1 air_12, String string) {
        this.cjQ = air_12;
        this.dzj = string;
        if (this.cjQ != null) {
            this.dJi = this.L(air_12);
        }
        if (this.cjQ != null && this.dzj != null) {
            this.h(this.dzj, this.cjQ);
        }
    }

    public void a(PrintWriter printWriter) {
        this.dzo.clear();
        if (this.dYM) {
            printWriter.println("\tpublic void " + this.getMethodName() + "(ElementMap currentElementMap, DocumentParser parser, Widget " + this.dzj + ") {");
        } else {
            printWriter.println("\tpublic BasicElement " + this.getMethodName() + "(BasicElement " + this.dzj + ") {");
        }
        for (oy_0 oy_02 : this.dzm) {
            printWriter.println("\t\t" + oy_02.a(this));
        }
        if (!this.dYM) {
            printWriter.println("\t\treturn " + this.dJk + ";");
        }
        printWriter.println("\t}");
    }
}

