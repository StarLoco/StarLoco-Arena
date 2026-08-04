/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;

/*
 * Renamed from hd
 */
public class hd_2
extends cr_2 {
    private String pattern = null;
    private boolean uX = true;
    private boolean uY = false;
    public static final String uZ = "name";
    public static final String va = "casesensitive";
    public static final String vb = "negate";

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("{filenameselector name: ");
        stringBuffer.append(this.pattern);
        stringBuffer.append(" negate: ");
        if (this.uY) {
            stringBuffer.append("true");
        } else {
            stringBuffer.append("false");
        }
        stringBuffer.append(" casesensitive: ");
        if (this.uX) {
            stringBuffer.append("true");
        } else {
            stringBuffer.append("false");
        }
        stringBuffer.append("}");
        return stringBuffer.toString();
    }

    public void setName(String string) {
        if ((string = string.replace('/', File.separatorChar).replace('\\', File.separatorChar)).endsWith(File.separator)) {
            string = string + "**";
        }
        this.pattern = string;
    }

    public void K(boolean bl2) {
        this.uX = bl2;
    }

    public void L(boolean bl2) {
        this.uY = bl2;
    }

    public void a(vj_0[] vj_0Array) {
        super.a(vj_0Array);
        if (vj_0Array != null) {
            for (int j = 0; j < vj_0Array.length; ++j) {
                String string = vj_0Array[j].getName();
                if (uZ.equalsIgnoreCase(string)) {
                    this.setName(vj_0Array[j].getValue());
                    continue;
                }
                if (va.equalsIgnoreCase(string)) {
                    this.K(UI.gh(vj_0Array[j].getValue()));
                    continue;
                }
                if (vb.equalsIgnoreCase(string)) {
                    this.L(UI.gh(vj_0Array[j].getValue()));
                    continue;
                }
                this.eC("Invalid parameter " + string);
            }
        }
    }

    public void dQ() {
        if (this.pattern == null) {
            this.eC("The name attribute is required");
        }
    }

    public boolean a(File file, String string, File file2) {
        this.validate();
        return zr_1.e(this.pattern, string, this.uX) == !this.uY;
    }
}

