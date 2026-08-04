/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

/*
 * Renamed from Rs
 */
public class rs_0
extends dm_1 {
    private String elementName;
    private String bIU = "";
    private String bIV;
    private Object bIW;
    private List aUS = null;
    private boolean bIX = false;

    public rs_0(String string) {
        this.elementName = string;
    }

    public List getChildren() {
        return this.aUS;
    }

    public String getTag() {
        return this.elementName;
    }

    public String getNamespace() {
        return this.bIU;
    }

    public void setNamespace(String string) {
        if (string.equals("ant:current")) {
            abm_1 abm_12 = abm_1.D(this.TP());
            string = abm_12.apU();
        }
        this.bIU = string == null ? "" : string;
    }

    public String DD() {
        return this.bIV;
    }

    public void fL(String string) {
        this.bIV = string;
    }

    public fy_2 LN() {
        return super.LN();
    }

    public void LH() {
        if (this.bIW != null) {
            return;
        }
        this.ah(this.a(this, this.LN()));
    }

    public void ah(Object object) {
        this.bIW = object;
        this.LN().P(this.bIW);
        dm_1 dm_12 = null;
        if (this.bIW instanceof dm_1) {
            dm_12 = (dm_1)this.bIW;
            dm_12.a(this.LN());
            if (this.LN().getId() != null) {
                this.LE().a((dm_1)this, (dm_1)this.bIW);
            }
        }
        if (dm_12 != null) {
            dm_12.LH();
        } else {
            this.LN().m(this.TP());
        }
        this.a(this.bIW, this.LN());
    }

    protected void dF(String string) {
        if (this.bIW instanceof dm_1) {
            ((dm_1)this.bIW).dF(string);
        } else {
            super.dF(string);
        }
    }

    protected int c(byte[] byArray, int n2, int n3) {
        if (this.bIW instanceof dm_1) {
            return ((dm_1)this.bIW).c(byArray, n2, n3);
        }
        return super.c(byArray, n2, n3);
    }

    protected void dG(String string) {
        if (this.bIW instanceof dm_1) {
            ((dm_1)this.bIW).dG(string);
        } else {
            super.dG(string);
        }
    }

    protected void dH(String string) {
        if (this.bIW instanceof dm_1) {
            ((dm_1)this.bIW).dH(string);
        } else {
            super.dH(string);
        }
    }

    protected void dI(String string) {
        if (this.bIW instanceof dm_1) {
            ((dm_1)this.bIW).dH(string);
        } else {
            super.dH(string);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void execute() {
        if (this.bIW == null) {
            throw new eq_2("Could not create task of type: " + this.elementName, this.hW());
        }
        try {
            if (this.bIW instanceof dm_1) {
                ((dm_1)this.bIW).execute();
            }
        }
        finally {
            if (this.LN().getId() == null) {
                this.bIW = null;
                this.LN().P(null);
            }
        }
    }

    public void a(rs_0 rs_02) {
        if (this.aUS == null) {
            this.aUS = new ArrayList();
        }
        this.aUS.add(rs_02);
    }

    protected void a(Object object, fy_2 fy_22) {
        if (object instanceof akm) {
            object = ((akm)object).OV();
        }
        String string = this.getNamespace();
        Class<?> clazz = object.getClass();
        hm_2 hm_22 = hm_2.a(this.TP(), clazz);
        if (this.aUS != null) {
            Iterator iterator = this.aUS.iterator();
            int n2 = 0;
            while (iterator.hasNext()) {
                fy_2 fy_23 = fy_22.fC(n2);
                rs_0 rs_02 = (rs_0)iterator.next();
                try {
                    if (!this.a(string, hm_22, object, rs_02, fy_23)) {
                        if (!(object instanceof cf_1)) {
                            hm_22.c(this.TP(), object, rs_02.getTag());
                        } else {
                            cf_1 cf_12 = (cf_1)object;
                            cf_12.a(rs_02);
                        }
                    }
                }
                catch (anr_2 anr_22) {
                    throw new eq_2(fy_22.Pb() + " doesn't support the nested \"" + anr_22.getElement() + "\" element.", anr_22);
                }
                ++n2;
            }
        }
    }

    protected String adM() {
        return es_2.s(this.getNamespace(), this.getTag());
    }

    public void b(rs_0 rs_02) {
        if (this.bIX) {
            return;
        }
        this.LN().c(rs_02.LN());
        if (rs_02.aUS != null) {
            ArrayList arrayList = new ArrayList();
            arrayList.addAll(rs_02.aUS);
            if (this.aUS != null) {
                arrayList.addAll(this.aUS);
            }
            this.aUS = arrayList;
        }
        this.bIX = true;
    }

    protected Object a(rs_0 rs_02, fy_2 fy_22) {
        abm_1 abm_12 = abm_1.D(this.TP());
        String string = rs_02.adM();
        Object object = abm_12.a(rs_02, rs_02.getNamespace(), string);
        if (object == null) {
            throw this.A("task or type", string);
        }
        if (object instanceof cc_0) {
            cc_0 cc_02 = (cc_0)object;
            if ((object = cc_02.i(rs_02.TP())) == null) {
                throw this.A("preset " + string, cc_02.eU().adM());
            }
            rs_02.b(cc_02.eU());
            if (object instanceof dm_1) {
                dm_1 dm_12 = (dm_1)object;
                dm_12.dE(rs_02.LM());
                dm_12.cW(rs_02.LF());
                dm_12.init();
            }
        }
        if (object instanceof rs_0) {
            object = ((rs_0)object).a((rs_0)object, fy_22);
        }
        if (object instanceof dm_1) {
            ((dm_1)object).a(this.LE());
        }
        if (object instanceof aat_0) {
            ((aat_0)object).a(this.hW());
        }
        return object;
    }

    protected dm_1 b(rs_0 rs_02, fy_2 fy_22) {
        dm_1 dm_12 = this.TP().gd(rs_02.getTag());
        if (dm_12 != null) {
            dm_12.a(this.hW());
            dm_12.a(this.LE());
            dm_12.init();
        }
        return dm_12;
    }

    protected eq_2 A(String string, String string2) {
        abm_1 abm_12 = abm_1.D(this.TP());
        String string3 = abm_12.U(string2, string);
        return new eq_2(string3, this.hW());
    }

    public String LF() {
        return this.bIW == null || !(this.bIW instanceof dm_1) ? super.LF() : ((dm_1)this.bIW).LF();
    }

    public dm_1 adN() {
        if (this.bIW instanceof dm_1) {
            return (dm_1)this.bIW;
        }
        return null;
    }

    public Object adO() {
        return this.bIW;
    }

    public void ai(Object object) {
        this.bIW = object;
    }

    private boolean a(String string, hm_2 hm_22, Object object, rs_0 rs_02, fy_2 fy_22) {
        String string2 = es_2.s(rs_02.getNamespace(), rs_02.getTag());
        if (hm_22.u(string, string2)) {
            Object object2;
            ud_0 ud_02 = hm_22.b(this.TP(), string, object, string2, rs_02);
            ud_02.cy(fy_22.OW());
            Object object3 = ud_02.create();
            if (object3 instanceof cc_0) {
                object2 = (cc_0)object3;
                object3 = ud_02.AI();
                rs_02.b(((cc_0)object2).eU());
            }
            fy_22.a(ud_02);
            fy_22.P(object3);
            if (object3 instanceof dm_1) {
                object2 = (dm_1)object3;
                ((dm_1)object2).a(fy_22);
                ((dm_1)object2).cW(string2);
                ((dm_1)object2).dE(string2);
            }
            if (object3 instanceof aat_0) {
                ((aat_0)object3).a(rs_02.hW());
            }
            fy_22.m(this.TP());
            rs_02.a(object3, fy_22);
            ud_02.store();
            return true;
        }
        return false;
    }

    public boolean aj(Object object) {
        if (object == null) {
            return false;
        }
        if (!this.getClass().getName().equals(object.getClass().getName())) {
            return false;
        }
        rs_0 rs_02 = (rs_0)object;
        if (!rs_0.B(this.elementName, rs_02.elementName)) {
            return false;
        }
        if (!this.bIU.equals(rs_02.bIU)) {
            return false;
        }
        if (!this.bIV.equals(rs_02.bIV)) {
            return false;
        }
        if (!this.LN().OX().equals(rs_02.LN().OX())) {
            return false;
        }
        if (!this.LN().Pa().toString().equals(rs_02.LN().Pa().toString())) {
            return false;
        }
        if (this.aUS == null || this.aUS.size() == 0) {
            return rs_02.aUS == null || rs_02.aUS.size() == 0;
        }
        if (rs_02.aUS == null) {
            return false;
        }
        if (this.aUS.size() != rs_02.aUS.size()) {
            return false;
        }
        for (int j = 0; j < this.aUS.size(); ++j) {
            rs_0 rs_03 = (rs_0)this.aUS.get(j);
            if (rs_03.aj(rs_02.aUS.get(j))) continue;
            return false;
        }
        return true;
    }

    private static boolean B(String string, String string2) {
        return string == null ? string2 == null : string.equals(string2);
    }

    public rs_0 r(UI uI) {
        Object object;
        Object object2;
        rs_0 rs_02 = new rs_0(this.getTag());
        rs_02.setNamespace(this.getNamespace());
        rs_02.l(uI);
        rs_02.fL(this.DD());
        rs_02.dE(this.LM());
        rs_02.cW(this.LF());
        rs_02.a(this.hW());
        if (this.LE() == null) {
            object2 = new id_2();
            ((id_2)object2).l(this.TP());
            rs_02.a((id_2)object2);
        } else {
            rs_02.a(this.LE());
        }
        object2 = new fy_2(rs_02, this.LF());
        ((fy_2)object2).cy(this.LN().OW());
        Hashtable hashtable = this.LN().OX();
        Object object3 = hashtable.entrySet().iterator();
        while (object3.hasNext()) {
            object = object3.next();
            ((fy_2)object2).setAttribute((String)object.getKey(), (String)object.getValue());
        }
        ((fy_2)object2).addText(this.LN().Pa().toString());
        object3 = this.LN().OZ();
        while (object3.hasMoreElements()) {
            object = (fy_2)object3.nextElement();
            rs_0 rs_03 = (rs_0)((fy_2)object).OV();
            rs_0 rs_04 = rs_03.r(uI);
            ((fy_2)object2).b(rs_04.LN());
            rs_02.a(rs_04);
        }
        return rs_02;
    }
}

