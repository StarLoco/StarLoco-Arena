/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Renamed from yB
 */
public abstract class yb_0 {
    private static final String aCH = "text";
    private static final String aCI = "selectableText";
    private static final String aCJ = "image";
    private static final String aCK = "type";
    private static final String aCL = "align";
    private static final Pattern aCM = Pattern.compile("(text|image|selectableText)");
    private static final Pattern aCN = Pattern.compile("align=\"(west|center|east)\"");
    private di_0 aCO = di_0.mr;
    private final boolean aCP;
    private final jz aCQ;
    private final yb_0 aCR;
    private BP aCS = null;
    private String aCT = null;

    public yb_0(jz jz2, yb_0 yb_02, boolean bl2) {
        this.aCQ = jz2;
        this.aCP = bl2;
        this.aCR = yb_02;
    }

    protected abstract String getTypeName();

    public di_0 Fg() {
        return this.aCO;
    }

    public jz Fh() {
        return this.aCQ;
    }

    protected void a(di_0 di_02) {
        this.aCO = di_02;
    }

    public String getData() {
        return this.aCT;
    }

    public void setData(String string) {
        this.aCT = string != null ? string.intern() : null;
    }

    public BP Fi() {
        return this.aCS;
    }

    public void a(BP bP) {
        if (bP != null) {
            this.aCS = bP;
        }
    }

    public abstract int Fj();

    public boolean a(Matcher matcher, ArrayList arrayList) {
        String string = matcher.group(5);
        if (string != null) {
            String string2 = matcher.group(2);
            String string3 = matcher.group(4);
            boolean bl2 = false;
            this.r(string2, string3);
            Matcher matcher2 = abn_1.ciG.matcher(string);
            while (matcher2.find()) {
                yb_0 yb_02 = ((abn_1)this.aCQ).a(matcher2, this, bl2);
                if (yb_02 != this) {
                    bl2 = true;
                }
                yb_02.a(matcher2, arrayList);
            }
        } else {
            this.setData(this.da(matcher.group(7)));
            this.r(null, null);
        }
        return true;
    }

    public String Fk() {
        ArrayList arrayList = new ArrayList();
        this.i(arrayList);
        if (!arrayList.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder("<").append(this.getTypeName());
            for (int j = 0; j < arrayList.size(); ++j) {
                stringBuilder.append(' ');
                stringBuilder.append((String)arrayList.get(j));
            }
            stringBuilder.append('>').append(this.Fl()).append("</").append(this.getTypeName()).append('>');
            return stringBuilder.toString();
        }
        return this.getData();
    }

    protected String Fl() {
        String string = this.getData();
        string = string.replace("<", "&lt;");
        string = string.replace(">", "&gt;");
        return string;
    }

    protected String da(String string) {
        String string2 = string;
        string2 = string2.replace("&lt;", "<");
        string2 = string2.replace("&gt;", ">");
        return string2;
    }

    protected void i(ArrayList arrayList) {
        if (this.aCS != null) {
            StringBuilder stringBuilder = new StringBuilder(aCL);
            stringBuilder.append("=\"").append(this.aCS.toString().toLowerCase()).append("\"");
            arrayList.add(stringBuilder.toString());
        }
    }

    protected void r(String string, String string2) {
        BP bP;
        Matcher matcher;
        if (this.aCR != null) {
            this.aCS = this.aCR.aCS;
        }
        if (string2 != null && (matcher = aCN.matcher(string2)).find() && (bP = BP.valueOf(matcher.group(1).toUpperCase())) != null) {
            this.a(bP);
        }
    }

    public static di_0 db(String string) {
        Matcher matcher;
        di_0 di_02 = di_0.ms;
        if (string != null && string.length() != 0 && (matcher = aCM.matcher(string)).find()) {
            di_02 = di_0.valueOf(matcher.group(1).toUpperCase());
        }
        return di_02;
    }

    public abstract boolean ex(int var1);

    public abstract boolean ey(int var1);

    public abstract boolean ae(int var1, int var2);

    public String toString() {
        return this.getClass().getSimpleName() + " data=" + this.getData();
    }

    public yb_0 Fm() {
        return this.aCR;
    }

    public yb_0 b(di_0 di_02) {
        if (this.aCR == null) {
            return null;
        }
        if (this.aCR.Fg() == di_02) {
            return this.aCR;
        }
        return this.aCR.b(di_02);
    }
}

