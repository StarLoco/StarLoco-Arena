/*
 * Decompiled with CFR 0.152.
 */
import java.lang.reflect.Method;

/*
 * Renamed from ahb
 */
public class ahb_0 {
    private static final String cvf = "content";
    private Object cvg;
    private Method cvh;
    private Method cvi;
    private Method cvj;
    private aLH cvk;
    private String crg;
    private int cvl;
    private String cvm = null;
    private jn_2 cri = null;
    private boolean cvn = false;
    private aiw cvo = null;

    public ahb_0(Object object, aLH aLH2, String string, String string2, jn_2 jn_22) {
        this.cvg = object;
        this.cvk = aLH2;
        this.crg = string;
        this.cvl = string != null ? string.hashCode() : 0;
        this.cvh = aLH2.iX(string);
        this.cvi = aLH2.iZ(string);
        this.cvj = aLH2.ja(string);
        this.cvm = string2;
        this.cri = jn_22;
    }

    public ahb_0(Object object, aLH aLH2, String string, jn_2 jn_22) {
        this.cvg = object;
        this.cvk = aLH2;
        this.crg = string;
        this.cvl = string != null ? string.hashCode() : 0;
        this.cri = jn_22;
    }

    public int getAttributeHash() {
        return this.cvl;
    }

    public String getAttribute() {
        return this.crg;
    }

    public void setAttribute(String string) {
        this.crg = string;
        this.cvl = this.crg != null ? string.hashCode() : 0;
    }

    public Object getElement() {
        return this.cvg;
    }

    public void az(Object object) {
        this.cvg = object;
    }

    public aLH awM() {
        return this.cvk;
    }

    public void b(aLH aLH2) {
        this.cvk = aLH2;
    }

    public String getFieldName() {
        return this.cvm;
    }

    public void id(String string) {
        this.cvm = string;
    }

    public jn_2 getResultProvider() {
        return this.cri;
    }

    public void b(jn_2 jn_22) {
        this.cri = jn_22;
    }

    public boolean awN() {
        return this.cvg instanceof Fc && cvf.equalsIgnoreCase(this.crg);
    }

    public Method awO() {
        return this.cvh;
    }

    public Method awP() {
        return this.cvi;
    }

    public Method awQ() {
        return this.cvj;
    }

    public boolean awR() {
        return this.cvn;
    }

    public void dw(boolean bl2) {
        this.cvn = bl2;
    }

    aiw awS() {
        return this.cvo;
    }

    void a(aiw aiw2) {
        this.cvo = aiw2;
    }

    public String toString() {
        return "(PropertyClientData Element:" + this.cvg + " attribute=" + this.crg + " field=" + this.cvm + ")";
    }
}

