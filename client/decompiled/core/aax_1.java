/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aAX
 */
public class aax_1
extends js_2 {
    private String afO;
    private String afP;

    public void setFrom(String string) {
        this.afO = string;
    }

    public void setTo(String string) {
        this.afP = string;
    }

    public String dV(String string) {
        if (this.afO == null) {
            throw new eq_2("Missing from in stringreplace");
        }
        StringBuffer stringBuffer = new StringBuffer();
        int n2 = 0;
        int n3 = string.indexOf(this.afO);
        while (n3 >= 0) {
            if (n3 > n2) {
                stringBuffer.append(string.substring(n2, n3));
            }
            if (this.afP != null) {
                stringBuffer.append(this.afP);
            }
            n2 = n3 + this.afO.length();
            n3 = string.indexOf(this.afO, n2);
        }
        if (string.length() > n2) {
            stringBuffer.append(string.substring(n2, string.length()));
        }
        return stringBuffer.toString();
    }
}

