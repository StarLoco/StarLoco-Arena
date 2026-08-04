/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/*
 * Renamed from yY
 */
public class yy_2
extends asn {
    private static final boolean DEBUG = false;
    private final nw_2 aEo;
    private final apm_0 avk;
    private final short aEp;
    private final Map aEq = new HashMap();
    private final Map aEr = new HashMap();
    private final Map aEs = new HashMap();

    public yy_2(nw_2 nw_22, apm_0 apm_02) {
        this.aEo = nw_22;
        this.avk = apm_02;
        this.aEp = nw_22.aEp;
    }

    protected adi_0[] lK() {
        ArrayList<ms_2> arrayList = new ArrayList<ms_2>();
        Iterator iterator = this.aEo.bzR.iterator();
        while (iterator.hasNext()) {
            ms_2 ms_22;
            adz adz2 = (adz)iterator.next();
            try {
                ms_22 = this.a(adz2);
            }
            catch (ClassNotFoundException classNotFoundException) {
                throw new aHY(classNotFoundException.getMessage());
            }
            if (!(ms_22 instanceof adi_0)) continue;
            arrayList.add(ms_22);
        }
        return arrayList.toArray(new adi_0[arrayList.size()]);
    }

    protected ff_2[] lL() {
        ArrayList<ms_2> arrayList = new ArrayList<ms_2>();
        Iterator iterator = this.aEo.bzR.iterator();
        while (iterator.hasNext()) {
            ms_2 ms_22;
            adz adz2 = (adz)iterator.next();
            if ((adz2.asX() & 0x1000) != 0) continue;
            try {
                ms_22 = this.a(adz2);
            }
            catch (ClassNotFoundException classNotFoundException) {
                throw new aHY(classNotFoundException.getMessage());
            }
            if (!(ms_22 instanceof ff_2)) continue;
            arrayList.add(ms_22);
        }
        return arrayList.toArray(new ff_2[arrayList.size()]);
    }

    protected jz_0[] lM() {
        jz_0[] jz_0Array = new jz_0[this.aEo.bzQ.size()];
        for (int j = 0; j < this.aEo.bzQ.size(); ++j) {
            try {
                jz_0Array[j] = this.a((axo_0)this.aEo.bzQ.get(j));
                continue;
            }
            catch (ClassNotFoundException classNotFoundException) {
                throw new aHY(classNotFoundException.getMessage());
            }
        }
        return jz_0Array;
    }

    protected asn[] lN() {
        aig_2 aig_22 = this.aEo.aaA();
        if (aig_22 == null) {
            return new asn[0];
        }
        List list = aig_22.ayu();
        ArrayList<asn> arrayList = new ArrayList<asn>();
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            aqw_0 aqw_02 = (aqw_0)iterator.next();
            if (aqw_02.cOY != this.aEo.thisClass) continue;
            try {
                arrayList.add(this.aj(aqw_02.cOX));
            }
            catch (ClassNotFoundException classNotFoundException) {
                throw new ajy_2(classNotFoundException.getMessage(), null);
            }
        }
        return arrayList.toArray(new asn[arrayList.size()]);
    }

    protected asn lO() {
        aig_2 aig_22 = this.aEo.aaA();
        if (aig_22 == null) {
            return null;
        }
        List list = aig_22.ayu();
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            aqw_0 aqw_02 = (aqw_0)iterator.next();
            if (aqw_02.cOX != this.aEo.thisClass) continue;
            if (aqw_02.cOY == 0) {
                return null;
            }
            try {
                return this.aj(aqw_02.cOY);
            }
            catch (ClassNotFoundException classNotFoundException) {
                throw new ajy_2(classNotFoundException.getMessage(), null);
            }
        }
        return null;
    }

    protected asn lP() {
        aig_2 aig_22 = this.aEo.aaA();
        if (aig_22 == null) {
            return null;
        }
        List list = aig_22.ayu();
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            aqw_0 aqw_02 = (aqw_0)iterator.next();
            if (aqw_02.cOX != this.aEo.thisClass) continue;
            if (aqw_02.cOY == 0) {
                return null;
            }
            if ((aqw_02.cPa & 8) != 0) {
                return null;
            }
            try {
                return this.aj(aqw_02.cOY);
            }
            catch (ClassNotFoundException classNotFoundException) {
                throw new ajy_2(classNotFoundException.getMessage(), null);
            }
        }
        return null;
    }

    protected asn lQ() {
        if (this.aEo.bzO == 0) {
            return null;
        }
        try {
            return this.aj(this.aEo.bzO);
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw new ajy_2(classNotFoundException.getMessage(), null);
        }
    }

    public amf ib() {
        return yy_2.ak(this.aEp);
    }

    public boolean isFinal() {
        return (this.aEp & 0x10) != 0;
    }

    protected asn[] lR() {
        return this.h(this.aEo.bzP);
    }

    public boolean isAbstract() {
        return (this.aEp & 0x400) != 0;
    }

    protected String lS() {
        return sA.cb(this.aEo.aaB());
    }

    public boolean isInterface() {
        return (this.aEp & 0x200) != 0;
    }

    public boolean isArray() {
        return false;
    }

    public boolean isPrimitive() {
        return false;
    }

    public boolean lT() {
        return false;
    }

    protected asn lU() {
        return null;
    }

    public void FW() {
        int n2;
        this.aj(this.aEo.bzO);
        for (n2 = 0; n2 < this.aEo.bzP.length; ++n2) {
            this.aj(this.aEo.bzP[n2]);
        }
        for (n2 = 0; n2 < this.aEo.bzR.size(); ++n2) {
            this.a((adz)this.aEo.bzR.get(n2));
        }
        for (n2 = 0; n2 < this.aEo.bzQ.size(); ++n2) {
            this.a((axo_0)this.aEo.bzQ.get(n2));
        }
    }

    public void FX() {
        for (short s = 0; s < this.aEo.bzN.size(); s = (short)(s + 1)) {
            anv anv2 = this.aEo.aN(s);
            if (anv2 instanceof acp_2) {
                this.aj(s);
                continue;
            }
            if (!(anv2 instanceof ow_0)) continue;
            short s2 = ((ow_0)anv2).abx();
            String string = this.aEo.aP(s2);
            if (string.charAt(0) == '(') {
                cc_2 cc_22 = new cc_2(string);
                this.dd(cc_22.if);
                for (int j = 0; j < cc_22.ie.length; ++j) {
                    this.dd(cc_22.ie[j]);
                }
                continue;
            }
            this.dd(string);
        }
    }

    private asn aj(short s) {
        return this.dd(sA.cc(this.aEo.aO(s)));
    }

    private asn dd(String string) {
        asn asn2 = (asn)this.aEr.get(string);
        if (asn2 != null) {
            return asn2;
        }
        asn2 = this.avk.lT(string);
        if (asn2 == null) {
            throw new ClassNotFoundException(string);
        }
        this.aEr.put(string, asn2);
        return asn2;
    }

    private asn[] h(short[] sArray) {
        asn[] asnArray = new asn[sArray.length];
        for (int j = 0; j < asnArray.length; ++j) {
            try {
                asnArray[j] = this.aj(sArray[j]);
                continue;
            }
            catch (ClassNotFoundException classNotFoundException) {
                throw new ajy_2(classNotFoundException.getMessage(), null);
            }
        }
        return asnArray;
    }

    private ms_2 a(adz adz2) {
        Object object;
        ms_2 ms_22 = (ms_2)this.aEs.get(adz2);
        if (ms_22 != null) {
            return ms_22;
        }
        String string = this.aEo.aP(adz2.asY());
        cc_2 cc_22 = new cc_2(this.aEo.aP(adz2.abx()));
        asn asn2 = this.dd(cc_22.if);
        asn[] asnArray = new asn[cc_22.ie.length];
        for (int j = 0; j < asnArray.length; ++j) {
            asnArray[j] = this.dd(cc_22.ie[j]);
        }
        asn[] asnArray2 = null;
        ov_2[] ov_2Array = adz2.asZ();
        for (int j = 0; j < ov_2Array.length; ++j) {
            object = ov_2Array[j];
            if (!(object instanceof im_1)) continue;
            short[] sArray = ((im_1)object).lw();
            asnArray2 = new asn[sArray.length];
            for (int i2 = 0; i2 < sArray.length; ++i2) {
                asnArray2[i2] = this.aj(sArray[i2]);
            }
        }
        asn[] asnArray3 = asnArray2 == null ? new asn[]{} : asnArray2;
        object = yy_2.ak(adz2.asX());
        ms_22 = string.equals("<init>") ? new om_1(this, asnArray, asnArray3, (amf)object) : new ol_1(this, string, asn2, adz2, asnArray, asnArray3, (amf)object);
        this.aEs.put(adz2, ms_22);
        return ms_22;
    }

    private jz_0 a(axo_0 axo_02) {
        Object object;
        jz_0 jz_02 = (jz_0)this.aEq.get(axo_02);
        if (jz_02 != null) {
            return jz_02;
        }
        String string = this.aEo.aP(axo_02.asY());
        String string2 = this.aEo.aP(axo_02.abx());
        asn asn2 = this.dd(string2);
        oY oY2 = null;
        ov_2[] ov_2Array = axo_02.asZ();
        for (int j = 0; j < ov_2Array.length; ++j) {
            object = ov_2Array[j];
            if (!(object instanceof oY)) continue;
            oY2 = (oY)object;
            break;
        }
        Object object2 = null;
        if (oY2 != null) {
            object = this.aEo.aN(oY2.tS());
            if (object instanceof za_1) {
                object2 = ((za_1)object).a(this.aEo);
            } else {
                throw new aHY("Unexpected constant pool info type \"" + object.getClass().getName() + "\"");
            }
        }
        object = object2;
        amf amf2 = yy_2.ak(axo_02.asX());
        jz_02 = new ok_2(this, object, string, asn2, axo_02, amf2);
        this.aEq.put(axo_02, jz_02);
        return jz_02;
    }

    private static amf ak(short s) {
        return (s & 1) != 0 ? amf.cGt : ((s & 4) != 0 ? amf.cGr : ((s & 2) != 0 ? amf.cGq : amf.cGs));
    }
}

