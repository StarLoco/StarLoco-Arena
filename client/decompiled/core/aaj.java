/*
 * Decompiled with CFR 0.152.
 */
public class aaj {
    private float abc;
    private float abd;
    private float ceK;
    private float ceL;
    private float ceM;
    private float ceN;
    private String m_name;
    private boolean aKZ = false;
    private boolean ceO = false;
    private boolean ceP = false;
    private boolean aQv = true;
    private boolean ceQ = false;
    private boolean ceR = false;
    private Object dE = null;
    private String ceS;
    private String ceT;
    private float[] aaV;

    public aaj(float f, float f2, float f3, String string, String string2, float[] fArray) {
        this(f, f2, f3, string, null, string2, fArray);
    }

    public aaj(float f, float f2, float f3, String string, Object object, String string2, float[] fArray) {
        this(f, f2, f3, string, object, string2, fArray, false, false);
    }

    public aaj(float f, float f2, float f3, String string, Object object, String string2, float[] fArray, boolean bl2, boolean bl3) {
        this(f, f2, f3, string, object, string2, null, fArray, bl2, bl3);
    }

    public aaj(float f, float f2, float f3, String string, Object object, String string2, String string3, float[] fArray, boolean bl2, boolean bl3) {
        this.abc = f;
        this.abd = f2;
        this.ceK = f3;
        this.ceL = f;
        this.ceM = f2;
        this.ceN = f3;
        this.m_name = string;
        this.dE = object;
        this.ceS = string2;
        this.ceT = string3;
        this.aaV = fArray;
        this.aKZ = bl2;
        this.ceO = bl3;
    }

    public float tU() {
        return this.abc;
    }

    public void z(float f) {
        this.abc = f;
    }

    public float tV() {
        return this.abd;
    }

    public void A(float f) {
        this.abd = f;
    }

    public float aoJ() {
        return this.ceK;
    }

    public void aD(float f) {
        this.ceK = f;
    }

    public String getName() {
        if (this.m_name != null) {
            return this.m_name;
        }
        return null;
    }

    public void setName(String string) {
        this.m_name = string;
    }

    public String iu() {
        return this.ceS;
    }

    public String aoK() {
        return this.ceT;
    }

    public void he(String string) {
        this.ceS = string;
    }

    public float[] Aa() {
        return this.aaV;
    }

    public void q(float[] fArray) {
        this.aaV = fArray;
    }

    public Object getValue() {
        return this.dE;
    }

    public void setValue(Object object) {
        this.dE = object;
    }

    public float aoL() {
        return this.ceM;
    }

    public float aoM() {
        return this.ceL;
    }

    public float aoN() {
        return this.ceN;
    }

    public void aE(float f) {
        this.ceL = f;
    }

    public void aF(float f) {
        this.ceM = f;
    }

    public void aG(float f) {
        this.ceN = f;
    }

    public boolean isEditable() {
        return this.aKZ;
    }

    public void setEditable(boolean bl2) {
        this.aKZ = bl2;
    }

    public void cJ(boolean bl2) {
        this.ceO = bl2;
    }

    public boolean aoO() {
        return this.ceO;
    }

    public boolean aoP() {
        return this.ceP;
    }

    public void cK(boolean bl2) {
        this.ceP = bl2;
    }

    public boolean aoQ() {
        return this.ceQ;
    }

    public void cL(boolean bl2) {
        this.ceQ = bl2;
    }

    public boolean aoR() {
        return this.ceR;
    }

    public void cM(boolean bl2) {
        this.ceR = bl2;
    }

    public boolean isVisible() {
        return this.aQv;
    }

    public void setVisible(boolean bl2) {
        this.aQv = bl2;
    }

    public boolean aH(float f) {
        return Math.abs(this.ceL - this.abc) >= f || Math.abs(this.ceM - this.abd) >= f;
    }
}

