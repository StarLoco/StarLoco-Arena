/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from afz
 */
public class afz_1
extends aNZ
implements cn_1 {
    private static Logger a = Logger.getLogger(afz_1.class);
    public static final String TAG = "Property";
    private String m_name = null;
    private String aPH = null;
    private String crg = null;
    private boolean crh = false;
    private afl_0 cpT = null;
    private jn_2 cri = null;
    private static final acl_0 uG = new ym_0(new cv_1());
    public static final int crj = "local".hashCode();
    public static final int aru = "name".hashCode();
    public static final int crk = "attribute".hashCode();
    public static final int aPL = "field".hashCode();

    public static afz_1 checkOut() {
        afz_1 afz_12;
        try {
            afz_12 = (afz_1)uG.adr();
            afz_12.DG = uG;
        }
        catch (Exception exception) {
            a.error((Object)"Probl\u00e8me au borrowObject.");
            afz_12 = new afz_1();
            afz_12.b();
        }
        return afz_12;
    }

    public void a(na_1 na_12) {
        if (na_12 instanceof jn_2) {
            this.cri = (jn_2)((Object)na_12);
        }
        super.a(na_12);
    }

    public String getTag() {
        return TAG;
    }

    public String getName() {
        return this.m_name;
    }

    public void setName(String string) {
        this.m_name = string;
    }

    public String getField() {
        return this.aPH;
    }

    public void setField(String string) {
        this.aPH = string;
    }

    public String getAttribute() {
        return this.crg;
    }

    public void setAttribute(String string) {
        this.crg = string;
    }

    public boolean getLocal() {
        return this.crh;
    }

    public void setLocal(boolean bl2) {
        this.crh = bl2;
    }

    public afl_0 getProperty() {
        return this.cpT;
    }

    public void h(air_1 air_12) {
        if (this.cpT != null && air_12 != null) {
            this.cpT.b(new ahb_0(air_12, ye_2.amJ().ij(air_12.getTag()), this.crg, this.aPH, this.cri), false);
        }
    }

    public void ava() {
        if (this.cpT == null) {
            Ur[] urArray;
            afq_1 afq_12;
            this.cpT = azs_0.aLV().l(this.m_name, this.crh ? this.blb : null);
            if (this.cpT == null) {
                this.cpT = new afl_0(this.m_name, this.crh ? this.blb : null);
                azs_0.aLV().b(this.cpT);
            }
            if ((afq_12 = this.blb.azj()) == null) {
                afq_12 = add_1.aOG().azj();
            }
            if ((urArray = afq_12.aRS()) != null) {
                for (Ur ur : urArray) {
                    ur.b(this.cpT);
                }
            }
            ahb_0 ahb_02 = new ahb_0(this.czf, ye_2.amJ().ij(this.czf.getTag()), this.crg, this.aPH, this.cri);
            this.cpT.b(ahb_02, false);
        }
    }

    public void Aj() {
        super.Aj();
        this.ava();
    }

    public void aaf() {
        super.aaf();
        this.ava();
    }

    public void j() {
        super.j();
        this.m_name = null;
        this.aPH = null;
        this.crg = null;
        this.cri = null;
        this.cpT = null;
    }

    public void b() {
        this.crh = false;
        super.b();
    }

    public void a(air_1 air_12) {
        afz_1 afz_12 = (afz_1)air_12;
        super.a((air_1)afz_12);
        afz_12.crg = this.crg;
        afz_12.aPH = this.aPH;
        afz_12.m_name = this.m_name;
        afz_12.crh = this.crh;
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 == crj) {
            this.setLocal(Gr.getBoolean(string));
        } else if (n2 == aru) {
            this.setName(if_12.eM(string));
        } else if (n2 == crk) {
            this.setAttribute(if_12.eM(string));
        } else if (n2 == aPL) {
            this.setField(if_12.eM(string));
        } else {
            return super.setXMLAttribute(n2, string, if_12);
        }
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 == crj) {
            this.setLocal(Gr.getBoolean(object));
        } else if (n2 == aru) {
            this.setName(String.valueOf(object));
        } else if (n2 == crk) {
            this.setAttribute(String.valueOf(object));
        } else if (n2 == aPL) {
            this.setField(String.valueOf(object));
        } else {
            return super.setPropertyAttribute(n2, object);
        }
        return true;
    }
}

