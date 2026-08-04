/*
 * Decompiled with CFR 0.152.
 */
public abstract class aFA {
    private String bnj;
    private short brL;
    private short brM;
    private lc_0 bX;
    private final ahr_1 yo;

    private aFA(ahr_1 ahr_12) {
        this.yo = ahr_12;
        this.bX = null;
        this.bnj = ahr_1.c(ahr_12);
        this.brL = ahr_1.d(ahr_12);
        this.brM = ahr_1.e(ahr_12);
    }

    public lc_0 aP() {
        if (this.bX == null) {
            this.bX = new lc_0(this.bnj, this.brL, this.brM);
        }
        return this.bX;
    }

    public boolean NU() {
        return false;
    }

    public boolean dN(String string) {
        return false;
    }

    public boolean i(String[] stringArray) {
        return false;
    }

    public String NV() {
        throw new ajy_2("Not a keyword token", this.yo.RR());
    }

    public boolean lz() {
        return false;
    }

    public boolean aA(String string) {
        return false;
    }

    public String getIdentifier() {
        throw new ajy_2("Not an identifier token", this.yo.RR());
    }

    public boolean isLiteral() {
        return false;
    }

    public Object sA() {
        throw new ajy_2("Not a literal token", this.yo.RR());
    }

    public boolean aub() {
        return false;
    }

    public boolean hE(String string) {
        return false;
    }

    public boolean r(String[] stringArray) {
        return false;
    }

    public String auc() {
        throw new ajy_2("Not an operator token", this.yo.RR());
    }

    public boolean isEOF() {
        return false;
    }

    aFA(ahr_1 ahr_12, re_2 re_22) {
        this(ahr_12);
    }
}

