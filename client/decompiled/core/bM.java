/*
 * Decompiled with CFR 0.152.
 */
public class bM {
    private String name;
    private String hw;
    private String hx;
    private final TP hy;

    public bM(TP tP) {
        this.hy = tP;
    }

    public void setName(String string) {
        this.name = string;
    }

    public void w(String string) {
        this.hw = string;
    }

    public void x(String string) {
        this.hx = string;
    }

    public String getName() {
        return this.name;
    }

    public String d(UI uI) {
        return this.e(uI) ? this.name : null;
    }

    private boolean e(UI uI) {
        if (this.hw != null && uI.getProperty(this.hw) == null) {
            return false;
        }
        return this.hx == null || uI.getProperty(this.hx) == null;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        if (this.name == null) {
            stringBuffer.append("noname");
        } else {
            stringBuffer.append(this.name);
        }
        if (this.hw != null || this.hx != null) {
            stringBuffer.append(":");
            String string = "";
            if (this.hw != null) {
                stringBuffer.append("if->");
                stringBuffer.append(this.hw);
                string = ";";
            }
            if (this.hx != null) {
                stringBuffer.append(string);
                stringBuffer.append("unless->");
                stringBuffer.append(this.hx);
            }
        }
        return stringBuffer.toString();
    }
}

