/*
 * Decompiled with CFR 0.152.
 */
import java.io.PrintWriter;

/*
 * Renamed from Go
 */
public class go_0
extends adq_0 {
    public go_0(String string, go_0 go_02) {
        super(null, string, go_02, false);
    }

    public void a(PrintWriter printWriter) {
        this.dzo.clear();
        printWriter.println("\tpublic void " + this.getMethodName() + "() {");
        for (oy_0 oy_02 : this.dzm) {
            printWriter.println("\t\t" + oy_02.a(this));
        }
        printWriter.println("\t}");
    }
}

