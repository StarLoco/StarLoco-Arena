/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from wu
 */
public class wu_1
extends atb_0 {
    public wu_1() {
    }

    public wu_1(R r) {
        this();
        this.a(r);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        if (this.lp()) {
            stringBuffer.append("{notselect: ");
            stringBuffer.append(super.toString());
            stringBuffer.append("}");
        }
        return stringBuffer.toString();
    }

    public void dQ() {
        if (this.lq() != 1) {
            this.eC("One and only one selector is allowed within the <not> tag");
        }
    }
}

