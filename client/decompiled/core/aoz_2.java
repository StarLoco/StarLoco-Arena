/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Renamed from aOz
 */
public class aoz_2
extends yb_0 {
    private static final String cnB = "image";
    private static final String emb = "width";
    private static final String emc = "height";
    private static final String emd = "pixmap";
    private static final Pattern eme = Pattern.compile("width=\"([0-9]+)\"");
    private static final Pattern emf = Pattern.compile("height=\"([0-9]+)\"");
    private static final Pattern emg = Pattern.compile("pixmap=\"([a-zA-Z0-9-/!:.]+)\"");
    private String emh;
    private akq_1 arn;
    private int fb = 0;
    private int fc = 0;
    private boolean emi = false;

    public aoz_2(jz jz2, yb_0 yb_02) {
        super(jz2, yb_02, true);
        this.a(di_0.mt);
    }

    public akq_1 getPixmap() {
        return this.arn;
    }

    public void setPixmap(akq_1 akq_12) {
        this.arn = akq_12;
    }

    public int getWidth() {
        if (this.emi) {
            return this.fb;
        }
        if (this.arn != null) {
            return this.arn.getWidth();
        }
        return 0;
    }

    public void setWidth(int n2) {
        this.fb = n2;
        this.emi = true;
    }

    public int getHeight() {
        if (this.emi) {
            return this.fc;
        }
        if (this.arn != null) {
            return this.arn.getHeight();
        }
        return 0;
    }

    public void setHeight(int n2) {
        this.fc = n2;
        this.emi = true;
    }

    public int Fj() {
        return 1;
    }

    protected void i(ArrayList arrayList) {
        StringBuilder stringBuilder;
        super.i(arrayList);
        if (this.fb != 0) {
            stringBuilder = new StringBuilder(emb);
            stringBuilder.append("=\"").append(this.fb).append("\"");
            arrayList.add(stringBuilder.toString());
        }
        if (this.fc != 0) {
            stringBuilder = new StringBuilder(emc);
            stringBuilder.append("=\"").append(this.fc).append("\"");
            arrayList.add(stringBuilder.toString());
        }
        if (this.emh != null) {
            stringBuilder = new StringBuilder(emd);
            stringBuilder.append("=\"").append(this.emh).append("\"");
            arrayList.add(stringBuilder.toString());
        }
    }

    protected void r(String string, String string2) {
        super.r(string, string2);
        if (string2 != null) {
            Matcher matcher;
            Matcher matcher2;
            Matcher matcher3 = eme.matcher(string2);
            if (matcher3.find()) {
                this.setWidth(Integer.valueOf(matcher3.group(1)));
            }
            if ((matcher2 = emf.matcher(string2)).find()) {
                this.setHeight(Integer.valueOf(matcher3.group(1)));
            }
            if ((matcher = emg.matcher(string2)).find()) {
                String string3 = matcher.group(1);
                ef_1 ef_12 = agx_2.aTc().lo(string3);
                if (ef_12 != null) {
                    this.setPixmap(new akq_1(ef_12));
                    this.emh = string3;
                }
            }
        }
    }

    public boolean ex(int n2) {
        return true;
    }

    public boolean ae(int n2, int n3) {
        return true;
    }

    public boolean ey(int n2) {
        return true;
    }

    public boolean a(Matcher matcher, ArrayList arrayList) {
        int n2 = arrayList.size();
        boolean bl2 = super.a(matcher, arrayList);
        if (this.emh == null) {
            return false;
        }
        arrayList.add(n2, this);
        return bl2;
    }

    protected String getTypeName() {
        return cnB;
    }

    public String getData() {
        return "";
    }
}

