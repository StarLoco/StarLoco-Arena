/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;

public class vi
extends cr_2 {
    private String type = null;
    public static final String asv = "type";

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("{typeselector type: ");
        stringBuffer.append(this.type);
        stringBuffer.append("}");
        return stringBuffer.toString();
    }

    public void a(yf_2 yf_22) {
        this.type = yf_22.getValue();
    }

    public void a(vj_0[] vj_0Array) {
        super.a(vj_0Array);
        if (vj_0Array != null) {
            for (int j = 0; j < vj_0Array.length; ++j) {
                String string = vj_0Array[j].getName();
                if (asv.equalsIgnoreCase(string)) {
                    yf_2 yf_22 = new yf_2();
                    yf_22.setValue(vj_0Array[j].getValue());
                    this.a(yf_22);
                    continue;
                }
                this.eC("Invalid parameter " + string);
            }
        }
    }

    public void dQ() {
        if (this.type == null) {
            this.eC("The type attribute is required");
        }
    }

    public boolean a(File file, String string, File file2) {
        this.validate();
        if (file2.isDirectory()) {
            return this.type.equals("dir");
        }
        return this.type.equals("file");
    }
}

