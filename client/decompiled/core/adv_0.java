/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.awt.Color;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;

/*
 * Renamed from adV
 */
public class adv_0
extends yb_0 {
    private static Logger a = Logger.getLogger(adv_0.class);
    private static final String cnB = "text";
    private static final String cnC = "font";
    private static final String cnD = "u";
    private static final String cnE = "b";
    private static final String cnF = "i";
    private static final String cnG = "c";
    private static final String cnH = "name";
    private static final String cnI = "(name|face)";
    private static final String cnJ = "color";
    private static final String cnK = "id";
    private static final Pattern cnL = Pattern.compile("(name|face)=\"([a-zA-Z0-9-]+)\"");
    private static final Pattern cnM = Pattern.compile("size=\"([0-9]+)\"");
    private static final Pattern cnN = Pattern.compile("color=\"([0-9A-Fa-f]{6})\"");
    private static final Pattern cnO = Pattern.compile("id=(([^,]+)|([^,]+\\-[^,]+))");
    private String rE = null;
    private af_1 lY;
    private boolean cnP = false;
    private boolean cnQ = false;
    private Color avf;
    private boolean avg = false;
    private boolean avh = false;

    public adv_0(jz jz2, yb_0 yb_02, boolean bl2) {
        super(jz2, yb_02, bl2);
        if (!bl2) {
            this.a(di_0.ms);
        }
    }

    public void setData(String string) {
        super.setData(this.hB(string));
    }

    public void setText(String string) {
        this.setData(string);
    }

    public int s(String string, int n2) {
        String string2 = this.hB(string);
        String string3 = this.getText();
        String string4 = string3 == null ? "" : string3.substring(0, n2);
        String string5 = string3 == null ? "" : string3.substring(n2);
        super.setData(string4 + string2 + string5);
        return string2.length();
    }

    public String getText() {
        return this.getData();
    }

    public String att() {
        if (this.Fh().mu()) {
            return this.getText().replaceAll(".", "*");
        }
        return this.getText();
    }

    public af_1 Da() {
        return this.lY;
    }

    public void b(af_1 af_12) {
        this.lY = af_12;
    }

    public Color getColor() {
        return this.avf;
    }

    public void setColor(Color color) {
        if (color != null) {
            this.avf = color;
        }
    }

    public boolean isUnderline() {
        return this.avg;
    }

    public void setUnderline(boolean bl2) {
        this.avg = bl2;
    }

    public boolean Dd() {
        return this.avh;
    }

    public void aR(boolean bl2) {
        this.avh = bl2;
    }

    public String getId() {
        return this.rE;
    }

    public void setId(String string) {
        this.rE = string;
    }

    protected String getTypeName() {
        return cnB;
    }

    public int Fj() {
        return this.getText().length();
    }

    public boolean a(Matcher matcher, ArrayList arrayList) {
        int n2 = arrayList.size();
        boolean bl2 = super.a(matcher, arrayList);
        if (bl2 && (this.getData() == null || this.getData().length() == 0)) {
            return false;
        }
        arrayList.add(n2, this);
        return bl2;
    }

    public String hB(String string) {
        Pattern pattern = this.Fh().mt();
        if (pattern != null) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int j = 0; j < string.length(); ++j) {
                String string2 = Character.toString(string.charAt(j));
                if (!pattern.matcher(string2).matches()) continue;
                stringBuilder.append(string2);
            }
            return stringBuilder.toString();
        }
        return string;
    }

    protected void i(ArrayList arrayList) {
        CharSequence charSequence;
        super.i(arrayList);
        if (this.lY != null && (charSequence = this.lY.getFontName()) != null) {
            StringBuilder stringBuilder = new StringBuilder(cnH);
            stringBuilder.append("=\"").append((String)charSequence).append("\"");
            arrayList.add(stringBuilder.toString());
        }
        if (this.avf != null) {
            charSequence = new StringBuilder(cnJ);
            ((StringBuilder)charSequence).append("=\"").append(Integer.toHexString(this.avf.getRed() & 0xFF)).append(Integer.toHexString(this.avf.getGreen() >> 8 & 0xFF)).append(Integer.toHexString(this.avf.getBlue() >> 16 & 0xFF)).append("\"");
            arrayList.add(((StringBuilder)charSequence).toString());
        }
        if (this.rE != null) {
            charSequence = new StringBuilder(cnK);
            ((StringBuilder)charSequence).append("=\"").append(this.rE).append("\"");
            arrayList.add(((StringBuilder)charSequence).toString());
        }
        if (this.avg) {
            charSequence = new StringBuilder(cnD);
            ((StringBuilder)charSequence).append('=').append(this.avg);
            arrayList.add(((StringBuilder)charSequence).toString());
        }
        if (this.avh) {
            charSequence = new StringBuilder(cnG);
            ((StringBuilder)charSequence).append('=').append(this.avh);
            arrayList.add(((StringBuilder)charSequence).toString());
        }
    }

    protected void r(String string, String string2) {
        Matcher matcher;
        super.r(string, string2);
        boolean bl2 = false;
        float f = 0.0f;
        boolean bl3 = false;
        int n2 = 0;
        boolean bl4 = false;
        adv_0 adv_02 = (adv_0)this.b(di_0.ms);
        if (adv_02 != null) {
            this.cnP = adv_02.cnP;
            this.cnQ = adv_02.cnQ;
            this.avg = adv_02.avg;
            this.avh = adv_02.avh;
            this.avf = adv_02.avf;
            this.lY = adv_02.lY;
            this.rE = adv_02.rE;
            if (this.cnP) {
                n2 |= 1;
            }
            if (this.cnQ) {
                n2 |= 2;
            }
        }
        ma_1 ma_12 = null;
        if (this.Fh().mH() != null) {
            ma_12 = this.Fh().mH().getFont();
        }
        if (this.lY != null) {
            ma_12 = this.lY.getFont();
        }
        if (string2 != null && (matcher = cnL.matcher(string2)).find()) {
            String string3 = matcher.group(2);
            ma_12 = abw_1.kh(string3);
            bl2 = true;
        }
        if (string2 != null) {
            matcher = cnM.matcher(string2);
            if (ma_12 != null && matcher.find()) {
                f = Float.parseFloat(matcher.group(1));
                bl3 = true;
            }
        }
        if (ma_12 != null) {
            if (string2 != null && (matcher = cnN.matcher(string2)).find()) {
                try {
                    int n3 = Integer.valueOf(matcher.group(1), 16);
                    this.setColor(new Color(n3));
                }
                catch (NumberFormatException numberFormatException) {
                    a.warn((Object)("la couleur " + matcher.group(1) + " est invalide !"));
                }
            }
            n2 = ma_12.getStyle();
            if (cnE.equalsIgnoreCase(string)) {
                this.cnP = true;
                bl4 = true;
            }
            if (cnF.equalsIgnoreCase(string)) {
                this.cnQ = true;
                bl4 = true;
            }
            if (bl4) {
                if (this.cnP) {
                    n2 |= 1;
                }
                if (this.cnQ) {
                    n2 |= 2;
                }
            } else {
                n2 = ma_12.getStyle();
            }
            if (!bl3) {
                f = ma_12.getSize();
            }
            if (bl3 || bl4) {
                ma_12 = ma_12.b(n2, f);
                bl2 = true;
            }
            if (bl2) {
                this.b(aFM.b(ma_12));
            }
        }
        if (cnD.equals(string)) {
            this.setUnderline(true);
        }
        if (cnG.equals(string)) {
            this.aR(true);
        }
        if (string2 != null && (matcher = cnO.matcher(string2)).find()) {
            this.setId(String.valueOf(matcher.group(1)));
        }
    }

    public boolean ex(int n2) {
        String string = this.getText();
        if (string.length() <= n2) {
            return false;
        }
        this.setText(string.substring(0, n2));
        return this.getText().length() == 0;
    }

    public boolean ae(int n2, int n3) {
        String string = this.getText();
        this.setText(string.substring(0, n2) + string.substring(n3));
        return this.getText().length() == 0;
    }

    public boolean ey(int n2) {
        this.setText(this.getText().substring(n2));
        return this.getText().length() == 0;
    }
}

