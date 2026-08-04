/*
 * Decompiled with CFR 0.152.
 */
import java.util.regex.Pattern;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public class aex {
    private String rE;
    private int coJ;
    private int coK;
    private Pattern coL = null;
    private String coM = null;
    private boolean coN = false;
    private boolean coO = false;
    private boolean coP = false;
    private boolean coQ = false;
    private boolean coR = false;
    private hz_0 coS = null;
    private boolean OD = true;
    private String jv;
    private String coT;
    private int coU;
    private String coV;

    public aex(String string, int n2, String string2, boolean bl2, boolean bl3, boolean bl4, boolean bl5, boolean bl6, String string3) {
        this.rE = string;
        this.coJ = n2;
        this.coM = string2;
        this.coN = bl2;
        this.coO = bl3;
        this.coP = bl4;
        this.coR = bl5;
        this.coQ = bl6;
        this.coV = string3;
        this.aud();
    }

    public aex(String string, Pattern pattern, String string2, boolean bl2, boolean bl3, boolean bl4, boolean bl5, boolean bl6, String string3) {
        this.rE = null;
        this.coL = pattern;
        this.coM = string2;
        this.coN = bl2;
        this.coO = bl3;
        this.coP = bl4;
        this.coR = bl5;
        this.coQ = bl6;
        this.coV = string3;
        this.aud();
    }

    public aex(String string, String string2, String string3, boolean bl2, boolean bl3, boolean bl4, boolean bl5, boolean bl6, String string4) {
        String[] stringArray = string2.split("-");
        if (stringArray.length == 1) {
            this.coJ = this.coK = Integer.parseInt(stringArray[0]);
        } else if (stringArray.length == 2) {
            this.coJ = Integer.parseInt(stringArray[0]);
            this.coK = Integer.parseInt(stringArray[1]);
        }
        this.rE = string;
        this.coM = string3;
        this.coN = bl2;
        this.coO = bl3;
        this.coP = bl4;
        this.coR = bl5;
        this.coQ = bl6;
        this.coV = string4;
        this.aud();
    }

    private void aud() {
        if (this.coN) {
            this.coU |= 0x80;
        }
        if (this.coO) {
            this.coU |= 0x200;
        }
        if (this.coP) {
            this.coU |= 0x40;
        }
    }

    public String aue() {
        return this.coM;
    }

    public int auf() {
        return this.coJ;
    }

    public void dl(boolean bl2) {
        this.coN = bl2;
    }

    public void dm(boolean bl2) {
        this.coO = bl2;
    }

    public void dn(boolean bl2) {
        this.coP = bl2;
    }

    public boolean aug() {
        return this.coN;
    }

    public boolean auh() {
        return this.coO;
    }

    public boolean aui() {
        return this.coP;
    }

    public boolean auj() {
        return this.coQ;
    }

    public void do(boolean bl2) {
        this.coQ = bl2;
    }

    public String getId() {
        return this.rE;
    }

    public void setId(String string) {
        this.rE = string;
    }

    public boolean a(int n2, char c) {
        if (this.coL != null && this.coL.matcher(Character.toString(c)).matches()) {
            return true;
        }
        return n2 >= this.coJ && n2 <= this.coK;
    }

    public boolean isEnabled() {
        return this.OD;
    }

    public void setEnabled(boolean bl2) {
        this.OD = bl2;
    }

    public void a(hz_0 hz_02) {
        this.coS = hz_02;
    }

    public hz_0 auk() {
        return this.coS;
    }

    public String CL() {
        return this.jv;
    }

    public void C(String string) {
        this.jv = string;
    }

    public String getCategory() {
        return this.coT;
    }

    public void setCategory(String string) {
        this.coT = string;
    }

    public void ke(int n2) {
        this.coJ = n2;
    }

    public void kf(int n2) {
        this.coK = n2;
    }

    public int aul() {
        return this.coU;
    }

    public void kg(int n2) {
        this.coU = n2;
        this.dn((this.coU & 0x40) == 64);
        this.dm((this.coU & 0x200) == 512);
        this.dl((this.coU & 0x80) == 128);
    }

    public void hF(String string) {
        this.coM = string;
    }

    public String aum() {
        return this.coV;
    }

    public void hG(String string) {
        this.coV = string;
    }

    public boolean aun() {
        return this.coR;
    }

    public aex auo() {
        aex aex2 = this.coJ == -1 ? new aex(this.rE, this.coJ, this.coM, this.coN, this.coO, this.coP, this.coR, this.coQ, this.coV) : new aex(this.rE, String.valueOf(this.coJ), this.coM, this.coN, this.coO, this.coP, this.coR, this.coQ, this.coV);
        aex2.C(this.jv);
        aex2.setCategory(this.coT);
        return aex2;
    }
}

