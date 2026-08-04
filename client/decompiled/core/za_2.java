/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

/*
 * Renamed from za
 */
public class za_2
implements ayt_0 {
    public ayp de(String string) {
        String[] stringArray;
        ArrayList<aaq_2> arrayList = new ArrayList<aaq_2>();
        String string2 = string.substring(1, string.length() - 1);
        for (String string3 : stringArray = string2.split(",")) {
            arrayList.add(new aaq_2(Integer.parseInt(string3)));
        }
        return new aqf_0(arrayList);
    }
}

