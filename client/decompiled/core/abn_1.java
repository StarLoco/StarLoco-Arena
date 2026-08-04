/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Renamed from abN
 */
public class abn_1
extends jz {
    public static final Pattern ciG = Pattern.compile("(<(\\p{Alpha}+?)( ([^<>]*))*>(.*?)</(\\2)>)|([^<>]+)", 32);

    public String mJ() {
        StringBuilder stringBuilder = new StringBuilder();
        for (yb_0 yb_02 : this) {
            stringBuilder.append(yb_02.Fk());
        }
        return stringBuilder.toString();
    }

    public void aD(String string) {
        this.mr();
        this.mK();
        this.aE(string);
    }

    public void aE(String string) {
        Matcher matcher = ciG.matcher(string);
        matcher.reset();
        ArrayList arrayList = new ArrayList();
        while (matcher.find()) {
            yb_0 yb_02 = this.a(matcher, null, true);
            yb_02.a(matcher, arrayList);
        }
        int n2 = arrayList.size();
        for (int j = 0; j < n2; ++j) {
            this.a((yb_0)arrayList.get(j));
        }
    }

    public yb_0 a(Matcher matcher, yb_0 yb_02, boolean bl2) {
        di_0 di_02 = yb_0.db(matcher.group(2));
        switch (di_02) {
            case mt: {
                return new aoz_2(this, yb_02);
            }
        }
        return new adv_0(this, yb_02, false);
    }

    public static String ht(String string) {
        Matcher matcher = ciG.matcher(string);
        matcher.reset();
        String string2 = "";
        while (matcher.find()) {
            String string3 = matcher.group(0);
            String string4 = matcher.group(5);
            if (string3 != null && string3.length() > 0 && string4 != null && string4.length() > 0) {
                string2 = string2 + string4;
                continue;
            }
            if (string3 == null || string3.length() <= 0) continue;
            string2 = string2 + string3;
        }
        return string2;
    }
}

