/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

/*
 * Renamed from cc
 * Illegal identifiers - consider using --renameillegalidents true
 */
public class cc_2 {
    public final String[] ie;
    public final String if;

    public cc_2(String[] stringArray, String string) {
        this.ie = stringArray;
        this.if = string;
    }

    public cc_2(String string) {
        if (string.charAt(0) != '(') {
            throw new aHY();
        }
        int n2 = 1;
        ArrayList<String> arrayList = new ArrayList<String>();
        while (string.charAt(n2) != ')') {
            int n3 = n2;
            while (string.charAt(n3) == '[') {
                ++n3;
            }
            if ("BCDFIJSZ".indexOf(string.charAt(n3)) != -1) {
                ++n3;
            } else if (string.charAt(n3) == 'L') {
                ++n3;
                while (string.charAt(n3) != ';') {
                    ++n3;
                }
                ++n3;
            } else {
                throw new aHY();
            }
            arrayList.add(string.substring(n2, n3));
            n2 = n3;
        }
        this.ie = arrayList.toArray(new String[arrayList.size()]);
        this.if = string.substring(++n2);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("(");
        for (int j = 0; j < this.ie.length; ++j) {
            stringBuffer.append(this.ie[j]);
        }
        return stringBuffer.append(')').append(this.if).toString();
    }

    public static String d(String string, String string2) {
        return '(' + string2 + string.substring(1);
    }
}

