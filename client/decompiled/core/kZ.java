/*
 * Decompiled with CFR 0.152.
 */
import java.io.PrintWriter;

public class kZ
extends adq_0 {
    private DS Gb;

    public kZ(DS dS, String string, String string2, kZ kZ2, boolean bl2) {
        super(string, string2, kZ2, bl2);
        this.Gb = dS;
    }

    public void a(PrintWriter printWriter) {
        this.dzo.clear();
        printWriter.println("\tpublic void " + this.getMethodName() + "(DocumentParser " + this.dzj + ") {");
        for (oy_0 oy_02 : this.dzm) {
            printWriter.println("\t\t" + oy_02.a(this));
        }
        printWriter.println("\t}");
    }
}

