/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

/*
 * Renamed from zh
 */
public class zh_2 {
    private static final boolean DEBUG = false;
    private static final int aEw = 3;
    private static final RuntimeException aEx = new RuntimeException("SNO: This exception should have been caught and processed");
    private final Map aEy;
    private static final HashMap aEz = new HashMap();
    private static final HashMap aEA;
    private avo_0 aEB;
    private ayT aEC;
    private int aED;
    private aeo_1 aEE;
    public final kh_1 aEF;
    private final apm_0 avk;
    private final boolean aEG;
    private List aEH;
    private boolean aEI;
    private boolean aEJ;
    private boolean aEK;
    private final Map aEL;
    private final Collection aEM;
    private final Map aEN;
    private final Collection aEO;

    public zh_2(kh_1 kh_12, apm_0 apm_02) {
        Object object;
        block8: {
            this.aEy = new HashMap();
            this.aEB = null;
            this.aEC = null;
            this.aED = 0;
            this.aEE = null;
            this.aEL = new HashMap();
            this.aEN = new HashMap();
            this.aEO = new ArrayList();
            this.aEF = kh_12;
            this.avk = apm_02;
            try {
                boolean bl2 = true;
                object = System.getProperty("Janino.TargetVersion");
                if (object != null && Double.parseDouble((String)object) <= 1.4) {
                    bl2 = false;
                }
                if (apm_02.lT("Ljava/lang/StringBuilder;") != null && bl2) {
                    this.aEG = true;
                    break block8;
                }
                if (apm_02.lT("Ljava/lang/StringBuffer;") != null) {
                    this.aEG = false;
                    break block8;
                }
                throw new aHY("SNO: Could neither load \"StringBuffer\" nor \"StringBuilder\"");
            }
            catch (ClassNotFoundException classNotFoundException) {
                throw new aHY("SNO: Error loading \"StringBuffer\" or \"StringBuilder\": " + classNotFoundException.getMessage());
            }
        }
        this.aEM = new ArrayList();
        this.aEM.add(new String[]{"java", "lang"});
        Iterator iterator = this.aEF.bnl.iterator();
        while (iterator.hasNext()) {
            object = (DV)iterator.next();
            try {
                ((DV)object).a(new kt_0(this));
            }
            catch (anr_1 anr_12) {
                throw anr_12.rV;
            }
        }
    }

    private void a(ahb_2 ahb_22) {
        asn asn2;
        Object[] objectArray = ahb_22.rb;
        String string = zh_2.g((String[])objectArray);
        Object[] objectArray2 = this.aEL.put(string, objectArray);
        if (objectArray2 != null && !Arrays.equals(objectArray2, objectArray)) {
            this.c("Class \"" + string + "\" was previously imported as " + "\"" + jf_1.a(objectArray2, ".") + "\", now as \"" + jf_1.a(objectArray, ".") + "\"", ahb_22.aP());
        }
        if ((asn2 = this.f((String[])objectArray)) == null) {
            this.c("Imported class \"" + jf_1.a(objectArray, ".") + "\" could not be loaded", ahb_22.aP());
        }
    }

    private void a(gb_1 gb_12) {
        this.aEM.add(gb_12.rb);
    }

    private void a(xv_0 xv_02) {
        Object[] objectArray;
        String string = zh_2.g(xv_02.rb);
        ArrayList<Object> arrayList = (ArrayList<Object>)this.aEN.get(string);
        if (arrayList == null) {
            arrayList = new ArrayList<Object>();
            this.aEN.put(string, arrayList);
        }
        if ((objectArray = this.f(xv_02.rb)) != null) {
            arrayList.add(objectArray);
            return;
        }
        objectArray = zh_2.h(xv_02.rb);
        asn asn2 = this.f((String[])objectArray);
        if (asn2 == null) {
            this.c("Could not load \"" + jf_1.a(objectArray, ".") + "\"", xv_02.aP());
            return;
        }
        jz_0 jz_02 = asn2.jv(string);
        if (jz_02 != null) {
            if (!jz_02.isStatic()) {
                this.c("Field \"" + string + "\" of \"" + jf_1.a(objectArray, ".") + "\" must be static", xv_02.aP());
            }
            arrayList.add(jz_02);
            return;
        }
        ff_2[] ff_2Array = asn2.ju(string);
        if (ff_2Array.length > 0) {
            arrayList.addAll(Arrays.asList(ff_2Array));
            return;
        }
        this.c("\"" + jf_1.a(objectArray, ".") + "\" has no static member \"" + string + "\"", xv_02.aP());
    }

    private void a(Xh xh) {
        asn asn2 = this.f(xh.rb);
        if (asn2 == null) {
            this.c("Could not load \"" + jf_1.a(xh.rb, ".") + "\"", xh.aP());
            return;
        }
        this.aEO.add(asn2);
    }

    public nw_2[] b(boolean bl2, boolean bl3, boolean bl4) {
        this.aEI = bl2;
        this.aEJ = bl3;
        this.aEK = bl4;
        Object object = this.aEF.bnl.iterator();
        while (object.hasNext()) {
            DV dV = (DV)object.next();
            try {
                dV.a(new KW(this));
            }
            catch (gH gH2) {
                throw gH2.rV;
            }
        }
        this.aEH = new ArrayList();
        object = this.aEF.bnm.iterator();
        while (object.hasNext()) {
            this.b((pn_1)object.next());
        }
        if (this.aED > 0) {
            throw new ajy_2(this.aED + " error(s) while compiling unit \"" + this.aEF.bnj + "\"", null);
        }
        object = this.aEH;
        return object.toArray(new nw_2[object.size()]);
    }

    private void b(el_1 el_12) {
        kq_0 kq_02 = new kq_0(this);
        try {
            el_12.a(kq_02);
        }
        catch (oi_0 oi_02) {
            throw oi_02.rV;
        }
    }

    public void a(pn_1 pn_12) {
        kh_1 kh_12 = pn_12.ue();
        Object[] objectArray = this.df(pn_12.getName());
        if (objectArray != null) {
            this.c("Package member type declaration \"" + pn_12.getName() + "\" conflicts with single-type-import \"" + jf_1.a(objectArray, ".") + "\"", pn_12.aP());
        }
        if ((objectArray = kh_12.eX(pn_12.getName())) != pn_12) {
            this.c("Redeclaration of type \"" + pn_12.getName() + "\", previously declared in " + objectArray.aP(), pn_12.aP());
        }
        if (pn_12 instanceof gk_0) {
            this.a((gk_0)((Object)pn_12));
        } else if (pn_12 instanceof cg_2) {
            this.a((cg_2)((Object)pn_12));
        } else {
            throw new aHY("PMTD of unexpected type " + pn_12.getClass().getName());
        }
    }

    public void a(azV azV2) {
        short s;
        Object object;
        asn asn2 = this.c(azV2);
        if ((azV2.hQ() & 0x400) == 0) {
            object = asn2.aFk();
            for (s = 0; s < ((ff_2[])object).length; ++s) {
                ff_2 ff_22;
                ff_2 ff_23 = object[s];
                if (!ff_23.isAbstract() || (ff_22 = asn2.b(ff_23.getName(), ff_23.iy())) != null && !ff_22.isAbstract() && ff_23.ix().g(ff_22.ix())) continue;
                this.c("Non-abstract class \"" + asn2 + "\" must implement method \"" + ff_23 + "\"", azV2.aP());
            }
        }
        object = new nw_2((short)(azV2.hQ() | 0x20), asn2.getDescriptor(), asn2.aFq().getDescriptor(), asn.a(asn2.aFr()));
        if (!(azV2.Dw() instanceof kh_1)) {
            if (azV2.Dw() instanceof lo_2) {
                s = ((nw_2)object).fs(asn2.getDescriptor());
                short s2 = this instanceof aao_0 ? ((nw_2)object).ft(((aao_0)((Object)this)).getName()) : (short)0;
                ((nw_2)object).a(new aqw_0(s, 0, s2, azV2.hQ()));
            } else if (azV2.Dw() instanceof el_1) {
                s = ((nw_2)object).fs(asn2.getDescriptor());
                short s3 = ((nw_2)object).fs(this.c((el_1)azV2.Dw()).getDescriptor());
                short s4 = ((nw_2)object).ft(((rp_1)((Object)azV2)).getName());
                ((nw_2)object).a(new aqw_0(s, s3, s4, azV2.hQ()));
            }
        }
        if (this.aEI) {
            String string = azV2.aP().getFileName();
            String string2 = string != null ? new File(string).getName() : (azV2 instanceof aao_0 ? ((aao_0)((Object)azV2)).getName() + ".java" : "ANONYMOUS.java");
            ((nw_2)object).fr(string2);
        }
        if (azV2 instanceof alW && ((alW)((Object)azV2)).jw()) {
            ((nw_2)object).aaz();
        }
        ArrayList<TK> arrayList = new ArrayList<TK>();
        Iterator iterator = azV2.doQ.iterator();
        while (iterator.hasNext()) {
            aR aR2 = (aR)iterator.next();
            if (!aR2.isStatic()) continue;
            arrayList.add((TK)((Object)aR2));
        }
        this.a(azV2, (nw_2)object, arrayList);
        this.a(azV2, (nw_2)object);
        int n2 = azV2.hS().size();
        int n3 = azV2.doR.size();
        acc_0[] acc_0Array = azV2.aMx();
        for (int j = 0; j < acc_0Array.length; ++j) {
            this.a(acc_0Array[j], (nw_2)object);
            if (n3 == azV2.doR.size()) continue;
            throw new aHY("SNO: Compilation of constructor \"" + acc_0Array[j] + "\" (" + acc_0Array[j].aP() + ") added synthetic fields!?");
        }
        this.a((el_1)azV2, (nw_2)object);
        this.a((el_1)azV2, (nw_2)object, n2);
        Object object2 = asn2.aFk();
        for (int j = 0; j < ((ff_2[])object2).length; ++j) {
            ff_2 ff_24;
            ff_2 ff_25 = object2[j];
            if (ff_25.isStatic() || (ff_24 = asn2.b(ff_25.getName(), ff_25.iy())) == null || ff_25.ix().equals(ff_24.ix())) continue;
            this.a((nw_2)object, ff_25, ff_24);
        }
        object2 = azV2.doQ.iterator();
        while (object2.hasNext()) {
            aR aR3 = (aR)object2.next();
            if (!(aR3 instanceof aBi)) continue;
            this.a((aBi)aR3, (nw_2)object);
        }
        object2 = azV2.doR.values().iterator();
        while (object2.hasNext()) {
            jz_0 jz_02 = (jz_0)object2.next();
            ((nw_2)object).a((short)0, jz_02.getName(), jz_02.tF().getDescriptor(), null);
        }
        this.aEH.add(object);
    }

    private void a(aBi aBi2, nw_2 nw_22) {
        for (int j = 0; j < aBi2.HE.length; ++j) {
            jk_2 jk_22 = aBi2.HE[j];
            atu_0 atu_02 = aBi2.HD;
            for (int i2 = 0; i2 < jk_22.BN; ++i2) {
                atu_02 = new ahe_1(atu_02);
            }
            Object object = null;
            if ((aBi2.HC & 0x10) != 0 && jk_22.BO != null) {
                if (jk_22.BO instanceof jy_2) {
                    object = this.j((jy_2)jk_22.BO);
                }
                if (object == jy_2.Dk) {
                    object = null;
                }
            }
            axo_0 axo_02 = pp_0.O(aBi2.HC) ? nw_22.a(pp_0.h(aBi2.HC, (short)0), jk_22.name, this.a(atu_02).getDescriptor(), object) : nw_22.a(aBi2.HC, jk_22.name, this.a(atu_02).getDescriptor(), object);
            if (!aBi2.jw()) continue;
            axo_02.b(new ts_0(nw_22.ft("Deprecated")));
        }
    }

    public void b(uy_1 uy_12) {
        this.a(uy_12);
    }

    public void a(abh_1 abh_12) {
        this.a((eb_0)abh_12);
    }

    public void a(eb_0 eb_02) {
        Object object = zh_2.d(eb_02);
        int n2 = object.size();
        if (n2 >= 2) {
            eb_02.a(new aji(this.c(eb_02), "this$" + (n2 - 2), this.c((el_1)object.get(1))));
        }
        if (eb_02 instanceof uy_1 || eb_02 instanceof abh_1) {
            object = (azV)((Object)eb_02);
            for (n2 = 0; n2 < ((azV)object).doQ.size(); ++n2) {
                aR aR2 = (aR)((azV)object).doQ.get(n2);
                if (!(aR2 instanceof aBi)) continue;
                aBi aBi2 = (aBi)aR2;
                for (int j = 0; j < aBi2.HE.length; ++j) {
                    jk_2 jk_22 = aBi2.HE[j];
                    if (jk_22.BO == null) continue;
                    this.a(jk_22.BO);
                }
            }
        }
        this.a((azV)((Object)eb_02));
    }

    public void b(hg_2 hg_22) {
        this.a(hg_22);
    }

    public void a(cg_2 cg_22) {
        Object object;
        Object object2;
        asn asn2 = this.c(cg_22);
        cg_22.aKJ = new asn[cg_22.aKH.length];
        String[] stringArray = new String[cg_22.aKJ.length];
        for (int j = 0; j < cg_22.aKH.length; ++j) {
            cg_22.aKJ[j] = this.a(cg_22.aKH[j]);
            stringArray[j] = cg_22.aKJ[j].getDescriptor();
        }
        nw_2 nw_22 = new nw_2((short)(cg_22.hQ() | 0x20 | 0x200 | 0x400), asn2.getDescriptor(), "Ljava/lang/Object;", stringArray);
        if (this.aEI) {
            object2 = cg_22.aP().getFileName();
            object = object2 != null ? new File((String)object2).getName() : cg_22.getName() + ".java";
            nw_22.fr((String)object);
        }
        if (cg_22.jw()) {
            nw_22.aaz();
        }
        if (!cg_22.aKI.isEmpty()) {
            object = new ArrayList();
            object.addAll(cg_22.aKI);
            this.a(cg_22, nw_22, (List)object);
        }
        this.a(cg_22, nw_22);
        for (int j = 0; j < cg_22.aKI.size(); ++j) {
            object2 = (TK)cg_22.aKI.get(j);
            if (!(object2 instanceof aBi)) continue;
            this.a((aBi)object2, nw_22);
        }
        this.a((el_1)cg_22, nw_22);
        this.aEH.add(nw_22);
    }

    private void a(DM dM, nw_2 nw_22, List list) {
        if (this.e(list)) {
            kc_0 kc_02 = new kc_0(dM.aP(), null, 9, new gw_1(dM.aP(), 0), "<clinit>", new anb_1[0], new ft[0], list);
            kc_02.a(dM);
            this.a(kc_02, nw_22);
        }
    }

    private void a(el_1 el_12, nw_2 nw_22) {
        Iterator iterator = el_12.hR().iterator();
        while (iterator.hasNext()) {
            rp_1 rp_12 = (rp_1)iterator.next();
            this.b(rp_12);
            short s = nw_22.fs(this.c(rp_12).getDescriptor());
            short s2 = nw_22.fs(this.c(el_12).getDescriptor());
            short s3 = nw_22.ft(rp_12.getName());
            nw_22.a(new aqw_0(s, s2, s3, rp_12.hQ()));
        }
    }

    private void a(DM dM, nw_2 nw_22) {
        this.a((el_1)dM, nw_22, 0);
    }

    private void a(el_1 el_12, nw_2 nw_22, int n2) {
        for (int j = n2; j < el_12.hS().size(); ++j) {
            this.a((kc_0)el_12.hS().get(j), nw_22);
        }
    }

    private void a(nw_2 nw_22, ff_2 ff_22, ff_2 ff_23) {
        int n2;
        Object object;
        adz adz2 = nw_22.a((short)4097, ff_22.getName(), ff_22.getDescriptor());
        asn[] asnArray = ff_22.iz();
        if (asnArray.length > 0) {
            short s = nw_22.ft("Exceptions");
            object = new short[asnArray.length];
            for (int j = 0; j < asnArray.length; ++j) {
                object[j] = nw_22.fs(asnArray[j].getDescriptor());
            }
            adz2.b(new im_1(s, (short[])object));
        }
        avo_0 avo_02 = new avo_0(adz2.asW());
        object = this.a(avo_02);
        avo_02.aIr();
        avo_02.a((short)1, "this", ff_23.ic());
        asn[] asnArray2 = ff_23.iy();
        xl_2[] xl_2Array = new xl_2[asnArray2.length];
        for (n2 = 0; n2 < asnArray2.length; ++n2) {
            xl_2Array[n2] = avo_02.a(sA.bY(asnArray2[n2].getDescriptor()), "param" + n2, asnArray2[n2]);
        }
        this.b((lz_1)aj_1.bW, 42);
        for (n2 = 0; n2 < xl_2Array.length; ++n2) {
            this.a((lz_1)aj_1.bW, xl_2Array[n2].tF(), (int)xl_2Array[n2].jl());
        }
        this.b((lz_1)aj_1.bW, -74);
        this.c(ff_23.ic().getDescriptor(), ff_23.getName(), ff_23.getDescriptor());
        this.b((lz_1)aj_1.bW, -80);
        this.a((avo_0)object);
        avo_02.jQ(ff_23.getName());
        adz2.b(new KR(this, nw_22.ft("Code"), avo_02));
    }

    private boolean b(TK tK) {
        boolean[] blArray = new boolean[1];
        Le le = new Le(this, blArray);
        try {
            tK.a(le);
            return blArray[0];
        }
        catch (wv_0 wv_02) {
            throw wv_02.rV;
        }
    }

    private boolean b(ra_0 ra_02) {
        return this.b((TK)ra_02.bIo);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean a(lo_2 lo_22) {
        this.aEB.aIr();
        try {
            boolean bl2 = this.d(lo_22.bsj);
            return bl2;
        }
        finally {
            this.aEB.aIs();
        }
    }

    private boolean d(List list) {
        boolean bl2 = true;
        for (int j = 0; j < list.size(); ++j) {
            TK tK = (TK)list.get(j);
            if (!bl2 && this.c(tK)) {
                this.c("Statement is unreachable", tK.aP());
                break;
            }
            bl2 = this.b(tK);
        }
        return bl2;
    }

    private boolean a(tb_1 tb_12) {
        Object object = this.j(tb_12.bMM);
        if (object != null) {
            if (Boolean.TRUE.equals(object)) {
                this.a("DSTC", "Condition of DO statement is always TRUE; the proper way of declaring an unconditional loop is \"for (;;)\"", tb_12.aP());
                return this.a(tb_12, tb_12.Pj, null);
            }
            this.a("DSNR", "DO statement never repeats", tb_12.aP());
        }
        avo_0 avo_02 = this.aEB;
        avo_02.getClass();
        tb_12.bTU = new va_2(avo_02);
        tb_12.bTV = false;
        va_2 va_22 = this.aEB.aIv();
        if (!this.b(tb_12.Pj) && !tb_12.bTV) {
            this.a("DSNTC", "\"do\" statement never tests its condition", tb_12.aP());
            if (tb_12.ama == null) {
                return false;
            }
            tb_12.ama.set();
            return true;
        }
        tb_12.bTU.set();
        this.a(tb_12.bMM, va_22, true);
        if (tb_12.ama != null) {
            tb_12.ama.set();
        }
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean a(no_1 no_12) {
        this.aEB.aIr();
        try {
            if (no_12.Pg != null) {
                this.b(no_12.Pg);
            }
            if (no_12.Ph == null) {
                boolean bl2 = this.a(no_12, no_12.Pj, no_12.Pi);
                return bl2;
            }
            Object object = this.j(no_12.Ph);
            if (object != null) {
                if (Boolean.TRUE.equals(object)) {
                    this.a("FSTC", "Condition of FOR statement is always TRUE; the proper way of declaring an unconditional loop is \"for (;;)\"", no_12.aP());
                    boolean bl3 = this.a(no_12, no_12.Pj, no_12.Pi);
                    return bl3;
                }
                this.a("FSNR", "FOR statement never repeats", no_12.aP());
            }
            avo_0 avo_02 = this.aEB;
            avo_02.getClass();
            object = new va_2(avo_02);
            this.a((lz_1)no_12, -89, (va_2)object);
            avo_0 avo_03 = this.aEB;
            avo_03.getClass();
            no_12.bTU = new va_2(avo_03);
            no_12.bTV = false;
            va_2 va_22 = this.aEB.aIv();
            boolean bl4 = this.b(no_12.Pj);
            no_12.bTU.set();
            if (no_12.Pi != null) {
                if (!bl4 && !no_12.bTV) {
                    this.a("FUUR", "For update is unreachable", no_12.aP());
                } else {
                    for (int j = 0; j < no_12.Pi.length; ++j) {
                        this.d(no_12.Pi[j]);
                    }
                }
            }
            ((va_2)object).set();
            this.a(no_12.Ph, va_22, true);
        }
        finally {
            this.aEB.aIs();
        }
        if (no_12.ama != null) {
            no_12.ama.set();
        }
        return true;
    }

    private boolean a(adh_0 adh_02) {
        Object object = this.j(adh_02.bMM);
        if (object != null) {
            if (Boolean.TRUE.equals(object)) {
                this.a("WSTC", "Condition of WHILE statement is always TRUE; the proper way of declaring an unconditional loop is \"for (;;)\"", adh_02.aP());
                return this.a(adh_02, adh_02.Pj, null);
            }
            this.a("WSNR", "WHILE statement never repeats", adh_02.aP());
        }
        avo_0 avo_02 = this.aEB;
        avo_02.getClass();
        adh_02.bTU = new va_2(avo_02);
        adh_02.bTV = false;
        this.a((lz_1)adh_02, -89, adh_02.bTU);
        va_2 va_22 = this.aEB.aIv();
        this.b(adh_02.Pj);
        adh_02.bTU.set();
        this.a(adh_02.bMM, va_22, true);
        if (adh_02.ama != null) {
            adh_02.ama.set();
        }
        return true;
    }

    private boolean a(wl_0 wl_02, TK tK, jy_2[] jy_2Array) {
        if (jy_2Array != null) {
            return this.b(wl_02, tK, jy_2Array);
        }
        wl_02.bTU = this.aEB.aIv();
        wl_02.bTV = false;
        if (this.b(tK)) {
            this.a((lz_1)wl_02, -89, wl_02.bTU);
        }
        if (wl_02.ama == null) {
            return false;
        }
        wl_02.ama.set();
        return true;
    }

    private boolean b(wl_0 wl_02, TK tK, jy_2[] jy_2Array) {
        avo_0 avo_02 = this.aEB;
        avo_02.getClass();
        wl_02.bTU = new va_2(avo_02);
        wl_02.bTV = false;
        va_2 va_22 = this.aEB.aIv();
        boolean bl2 = this.b(tK);
        wl_02.bTU.set();
        if (!bl2 && !wl_02.bTV) {
            this.a("LUUR", "Loop update is unreachable", jy_2Array[0].aP());
        } else {
            for (int j = 0; j < jy_2Array.length; ++j) {
                this.d(jy_2Array[j]);
            }
            this.a((lz_1)wl_02, -89, va_22);
        }
        if (wl_02.ama == null) {
            return false;
        }
        wl_02.ama.set();
        return true;
    }

    private boolean a(akj_0 akj_02) {
        boolean bl2 = this.b(akj_02.cDK);
        if (akj_02.ama != null) {
            akj_02.ama.set();
            bl2 = true;
        }
        return bl2;
    }

    private boolean a(asD asD2) {
        Object object;
        Object object2;
        int n2;
        Object object3;
        asn asn2 = this.i(asD2.bMM);
        this.a((lz_1)asD2, asn2, asn.cRB, null);
        TreeMap<Integer, va_2> treeMap = new TreeMap<Integer, va_2>();
        va_2 va_22 = null;
        va_2[] va_2Array = new va_2[asD2.cSp.size()];
        for (int j = 0; j < asD2.cSp.size(); ++j) {
            object3 = (jt_1)asD2.cSp.get(j);
            avo_0 avo_02 = this.aEB;
            avo_02.getClass();
            va_2Array[j] = new va_2(avo_02);
            for (n2 = 0; n2 < ((jt_1)object3).blm.size(); ++n2) {
                Integer n3;
                object2 = (jy_2)((jt_1)object3).blm.get(n2);
                Object object4 = this.j((jy_2)object2);
                if (object4 == null) {
                    this.c("Value of \"case\" label does not pose a constant value", ((aj_1)object2).aP());
                    object4 = new Integer(99);
                }
                object = this.a((alb_0)object2);
                this.a((lz_1)asD2, (asn)object, asn2, object4);
                if (object4 instanceof Integer) {
                    n3 = (Integer)object4;
                } else if (object4 instanceof Number) {
                    n3 = new Integer(((Number)object4).intValue());
                } else if (object4 instanceof Character) {
                    n3 = new Integer(((Character)object4).charValue());
                } else {
                    this.c("Value of case label must be a char, byte, short or int constant", ((aj_1)object2).aP());
                    n3 = new Integer(99);
                }
                if (treeMap.containsKey(n3)) {
                    this.c("Duplicate \"case\" switch label value", ((aj_1)object2).aP());
                }
                treeMap.put(n3, va_2Array[j]);
            }
            if (!((jt_1)object3).bln) continue;
            if (va_22 != null) {
                this.c("Duplicate \"default\" switch label", ((aj_1)object3).aP());
            }
            va_22 = va_2Array[j];
        }
        if (va_22 == null) {
            va_22 = this.a((so_1)asD2);
        }
        va_2 va_23 = this.aEB.aIv();
        if (!treeMap.isEmpty()) {
            if ((Integer)treeMap.firstKey() + treeMap.size() >= (Integer)treeMap.lastKey() - treeMap.size()) {
                int n4 = (Integer)treeMap.firstKey();
                n2 = (Integer)treeMap.lastKey();
                this.b((lz_1)asD2, -86);
                new aui_0(this.aEB).set();
                this.a(va_23, va_22);
                this.writeInt(n4);
                this.writeInt(n2);
                object2 = treeMap.entrySet().iterator();
                int n5 = n4;
                while (object2.hasNext()) {
                    object = (Map.Entry)object2.next();
                    int n6 = (Integer)object.getKey();
                    while (n5 < n6) {
                        this.a(va_23, va_22);
                        ++n5;
                    }
                    this.a(va_23, (va_2)object.getValue());
                    ++n5;
                }
            } else {
                this.b((lz_1)asD2, -85);
                new aui_0(this.aEB).set();
                this.a(va_23, va_22);
                this.writeInt(treeMap.size());
                object3 = treeMap.entrySet().iterator();
                while (object3.hasNext()) {
                    Map.Entry entry = (Map.Entry)object3.next();
                    this.writeInt((Integer)entry.getKey());
                    this.a(va_23, (va_2)entry.getValue());
                }
            }
        }
        boolean bl2 = true;
        block5: for (int j = 0; j < asD2.cSp.size(); ++j) {
            object2 = (jt_1)asD2.cSp.get(j);
            va_2Array[j].set();
            bl2 = true;
            for (int i2 = 0; i2 < ((jt_1)object2).blo.size(); ++i2) {
                object = (TK)((jt_1)object2).blo.get(i2);
                if (!bl2) {
                    this.c("Statement is unreachable", object.aP());
                    continue block5;
                }
                bl2 = this.b((TK)object);
            }
        }
        if (asD2.ama != null) {
            asD2.ama.set();
            bl2 = true;
        }
        return bl2;
    }

    private boolean a(gl_1 gl_12) {
        so_1 so_12 = null;
        if (gl_12.bco == null) {
            aim_2 aim_22 = gl_12.Dw();
            while (aim_22 instanceof akE || aim_22 instanceof xp_1) {
                if (aim_22 instanceof so_1) {
                    so_12 = (so_1)aim_22;
                    break;
                }
                aim_22 = aim_22.Dw();
            }
            if (so_12 == null) {
                this.c("\"break\" statement is not enclosed by a breakable statement", gl_12.aP());
                return false;
            }
        } else {
            aim_2 aim_23 = gl_12.Dw();
            while (aim_23 instanceof akE || aim_23 instanceof xp_1) {
                if (aim_23 instanceof akj_0) {
                    akj_0 akj_02 = (akj_0)aim_23;
                    if (akj_02.cDJ.equals(gl_12.bco)) {
                        so_12 = akj_02;
                        break;
                    }
                }
                aim_23 = aim_23.Dw();
            }
            if (so_12 == null) {
                this.c("Statement \"break " + gl_12.bco + "\" is not enclosed by a breakable statement with label \"" + gl_12.bco + "\"", gl_12.aP());
                return false;
            }
        }
        this.a(gl_12.Dw(), so_12.Dw(), null);
        this.a((lz_1)gl_12, -89, this.a(so_12));
        return false;
    }

    private boolean a(Ms ms) {
        wl_0 wl_02 = null;
        if (ms.bco == null) {
            aim_2 aim_22 = ms.Dw();
            while (aim_22 instanceof akE || aim_22 instanceof xp_1) {
                if (aim_22 instanceof wl_0) {
                    wl_02 = (wl_0)aim_22;
                    break;
                }
                aim_22 = aim_22.Dw();
            }
            if (wl_02 == null) {
                this.c("\"continue\" statement is not enclosed by a continuable statement", ms.aP());
                return false;
            }
        } else {
            aim_2 aim_23 = ms.Dw();
            while (aim_23 instanceof akE || aim_23 instanceof xp_1) {
                if (aim_23 instanceof akj_0) {
                    akj_0 akj_02 = (akj_0)aim_23;
                    if (akj_02.cDJ.equals(ms.bco)) {
                        akE akE2 = akj_02.cDK;
                        while (akE2 instanceof akj_0) {
                            akE2 = ((akj_0)akE2).cDK;
                        }
                        if (!(akE2 instanceof wl_0)) {
                            this.c("Labeled statement is not continuable", akE2.aP());
                            return false;
                        }
                        wl_02 = (wl_0)akE2;
                        break;
                    }
                }
                aim_23 = aim_23.Dw();
            }
            if (wl_02 == null) {
                this.c("Statement \"continue " + ms.bco + "\" is not enclosed by a continuable statement with label \"" + ms.bco + "\"", ms.aP());
                return false;
            }
        }
        wl_02.bTV = true;
        this.a(ms.Dw(), wl_02.Dw(), null);
        this.a((lz_1)ms, -89, wl_02.bTU);
        return false;
    }

    private boolean a(ek_0 ek_02) {
        return true;
    }

    private boolean a(cr cr2) {
        this.d(cr2.ij);
        return true;
    }

    private boolean b(aBi aBi2) {
        for (int j = 0; j < aBi2.HE.length; ++j) {
            jk_2 jk_22 = aBi2.HE[j];
            fd_2 fd_22 = this.a(aBi2, jk_22);
            if (fd_22 == null) continue;
            if ((aBi2.HC & 8) == 0) {
                this.b((lz_1)aBi2, 42);
            }
            asn asn2 = this.a(aBi2.HD);
            if (fd_22 instanceof jy_2) {
                jy_2 jy_22 = (jy_2)fd_22;
                asn asn3 = this.i(jy_22);
                asn2 = asn2.a(jk_22.BN, this.avk.eoQ);
                this.a((lz_1)aBi2, asn3, asn2, this.j(jy_22));
            } else if (fd_22 instanceof ln_2) {
                this.a((ln_2)fd_22, asn2);
            } else {
                throw new aHY("Unexpected array initializer or rvalue class " + fd_22.getClass().getName());
            }
            if ((aBi2.HC & 8) != 0) {
                this.b((lz_1)aBi2, -77);
            } else {
                this.b((lz_1)aBi2, -75);
            }
            this.b(this.c(aBi2.bV()).getDescriptor(), jk_22.name, asn2.getDescriptor());
        }
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean a(aia_1 aia_12) {
        TK tK;
        Object object = this.j(aia_12.bMM);
        TK tK2 = tK = aia_12.dOq != null ? aia_12.dOq : new ek_0(aia_12.dOp.aP());
        if (object instanceof Boolean) {
            TK tK3;
            TK tK4;
            this.c(aia_12.bMM);
            if (((Boolean)object).booleanValue()) {
                tK4 = aia_12.dOp;
                tK3 = tK;
            } else {
                tK4 = tK;
                tK3 = aia_12.dOp;
            }
            aNc aNc2 = this.aEB.aIw();
            boolean bl2 = this.b(tK4);
            if (bl2) {
                return true;
            }
            aim_2 aim_22 = aia_12.Dw();
            while (aim_22 instanceof lo_2) {
                aim_22 = aim_22.Dw();
            }
            if (aim_22 instanceof xN) {
                throw aEx;
            }
            va_2 va_22 = this.aEB.aIv();
            this.aEB.a(aNc2);
            try {
                this.a((lz_1)aia_12, new Integer(0));
                this.a((lz_1)aia_12, -102, va_22);
            }
            finally {
                this.aEB.aIy();
            }
            return this.b(tK3);
        }
        if (this.c(aia_12.dOp)) {
            if (this.c(tK)) {
                avo_0 avo_02 = this.aEB;
                avo_02.getClass();
                va_2 va_23 = new va_2(avo_02);
                avo_0 avo_03 = this.aEB;
                avo_03.getClass();
                va_2 va_24 = new va_2(avo_03);
                this.a(aia_12.bMM, va_23, false);
                boolean bl3 = this.b(aia_12.dOp);
                if (bl3) {
                    this.a((lz_1)aia_12, -89, va_24);
                }
                va_23.set();
                boolean bl4 = this.b(tK);
                va_24.set();
                return bl3 || bl4;
            }
            avo_0 avo_04 = this.aEB;
            avo_04.getClass();
            va_2 va_25 = new va_2(avo_04);
            this.a(aia_12.bMM, va_25, false);
            this.b(aia_12.dOp);
            va_25.set();
            return true;
        }
        if (this.c(tK)) {
            avo_0 avo_05 = this.aEB;
            avo_05.getClass();
            va_2 va_26 = new va_2(avo_05);
            this.a(aia_12.bMM, va_26, true);
            this.b(tK);
            va_26.set();
            return true;
        }
        asn asn2 = this.i(aia_12.bMM);
        if (asn2 != asn.cRE) {
            this.c("Not a boolean expression", aia_12.aP());
        }
        this.e(aia_12, asn2);
        return true;
    }

    private boolean a(ail_1 ail_12) {
        abh_1 abh_12 = this.a((aim_2)ail_12, ail_12.dPK.name);
        if (abh_12 != ail_12.dPK) {
            this.di("Redeclaration of local class \"" + ail_12.dPK.name + "\"; previously declared in " + abh_12.aP());
        }
        this.b(ail_12.dPK);
        return true;
    }

    private abh_1 a(aim_2 aim_22, String string) {
        aim_2 aim_23;
        while (!((aim_23 = aim_22.Dw()) instanceof kh_1)) {
            if (aim_22 instanceof TK && (aim_23 instanceof lo_2 || aim_23 instanceof xN)) {
                TK tK = (TK)aim_22;
                List list = aim_23 instanceof TK ? ((lo_2)aim_23).bsj : ((xN)aim_23).azB;
                Iterator iterator = list.iterator();
                while (iterator.hasNext()) {
                    TK tK2 = (TK)iterator.next();
                    if (tK2 instanceof ail_1) {
                        ail_1 ail_12 = (ail_1)tK2;
                        if (ail_12.dPK.name.equals(string)) {
                            return ail_12.dPK;
                        }
                    }
                    if (tK2 != tK) continue;
                    break;
                }
            }
            aim_22 = aim_23;
        }
        return null;
    }

    private boolean a(lG lG2) {
        if ((lG2.HC & 0xFFFFFFEF) != 0) {
            this.c("The only allowed modifier in local variable declarations is \"final\"", lG2.aP());
        }
        for (int j = 0; j < lG2.HE.length; ++j) {
            jk_2 jk_22 = lG2.HE[j];
            fb_2 fb_22 = this.a(lG2, jk_22);
            fb_22.a(this.aEB.a(sA.bY(fb_22.rC.getDescriptor()), jk_22.name, fb_22.rC));
            if (jk_22.BO == null) continue;
            if (jk_22.BO instanceof jy_2) {
                jy_2 jy_22 = (jy_2)jk_22.BO;
                this.a((lz_1)lG2, this.i(jy_22), fb_22.rC, this.j(jy_22));
            } else if (jk_22.BO instanceof ln_2) {
                this.a((ln_2)jk_22.BO, fb_22.rC);
            } else {
                throw new aHY("Unexpected rvalue or array initialized class " + jk_22.BO.getClass().getName());
            }
            this.a((lz_1)lG2, fb_22.rC, fb_22);
        }
        return true;
    }

    public fb_2 a(lG lG2, jk_2 jk_22) {
        if (jk_22.BP == null) {
            atu_0 atu_02 = lG2.HD;
            for (int j = 0; j < jk_22.BN; ++j) {
                atu_02 = new ahe_1(atu_02);
            }
            jk_22.BP = new fb_2((lG2.HC & 0x10) != 0, this.a(atu_02));
        }
        return jk_22.BP;
    }

    private boolean a(jr_1 jr_12) {
        xN xN2 = null;
        Object object = jr_12.Dw();
        while (object instanceof akE || object instanceof xp_1) {
            object = object.Dw();
        }
        xN2 = (xN)object;
        if ((object = this.b(xN2)) == asn.cRw) {
            if (jr_12.bmK != null) {
                this.c("Method must not return a value", jr_12.aP());
            }
            this.a(jr_12.Dw(), (aim_2)xN2, null);
            this.b((lz_1)jr_12, -79);
            return false;
        }
        if (jr_12.bmK == null) {
            this.c("Method must return a value", jr_12.aP());
            return false;
        }
        asn asn2 = this.i(jr_12.bmK);
        this.a((lz_1)jr_12, asn2, (asn)object, this.j(jr_12.bmK));
        this.a(jr_12.Dw(), (aim_2)xN2, (asn)object);
        this.b((lz_1)jr_12, -84 + this.e((asn)object));
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean a(vu_2 vu_22) {
        if (!this.avk.eoQ.g(this.i(vu_22.al))) {
            this.c("Monitor object of \"synchronized\" statement is not a subclass of \"Object\"", vu_22.aP());
        }
        this.aEB.aIr();
        boolean bl2 = false;
        try {
            vu_22.asR = this.aEB.cb((short)1);
            this.b((lz_1)vu_22, 89);
            this.a((lz_1)vu_22, this.avk.eoQ, vu_22.asR);
            this.b((lz_1)vu_22, -62);
            avo_0 avo_02 = this.aEB;
            avo_02.getClass();
            va_2 va_22 = new va_2(avo_02);
            va_2 va_23 = this.aEB.aIv();
            bl2 = this.b(vu_22.Pj);
            if (bl2) {
                this.a((lz_1)vu_22, -89, va_22);
            }
            va_2 va_24 = this.aEB.aIv();
            this.aEB.a(va_23, va_24, va_24, null);
            this.a((TK)vu_22, this.avk.eoT);
            this.b((lz_1)vu_22, -65);
            if (bl2) {
                va_22.set();
                this.a((TK)vu_22, (asn)null);
            }
        }
        finally {
            this.aEB.aIs();
        }
        return bl2;
    }

    private boolean a(v_0 v_02) {
        asn asn2 = this.i(v_02.al);
        this.a((lz_1)v_02, asn2, v_02.Dw());
        this.b((lz_1)v_02, -65);
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean b(aqt aqt2) {
        if (aqt2.cOd != null) {
            avo_0 avo_02 = this.aEB;
            avo_02.getClass();
            aqt2.cOe = new va_2(avo_02);
        }
        va_2 va_22 = this.aEB.aIv();
        avo_0 avo_03 = this.aEB;
        avo_03.getClass();
        va_2 va_23 = new va_2(avo_03);
        this.aEB.aIr();
        try {
            int n2;
            short s = aqt2.cOd != null ? this.aEB.cb((short)1) : (short)0;
            int n3 = this.b(aqt2.Pj);
            va_2 va_24 = this.aEB.aIv();
            if (n3 != 0) {
                this.a((lz_1)aqt2, -89, va_23);
            }
            if (va_22.offset != va_24.offset) {
                this.aEB.aIr();
                try {
                    for (n2 = 0; n2 < aqt2.cOc.size(); ++n2) {
                        try {
                            this.aEB.aIr();
                            xp_1 xp_12 = (xp_1)aqt2.cOc.get(n2);
                            asn asn2 = this.a(xp_12.azD.HD);
                            xl_2 xl_22 = this.aEB.a((short)1, xp_12.azD.name, asn2);
                            short s2 = xl_22.jl();
                            this.a(xp_12.azD).a(xl_22);
                            this.aEB.a(va_22, va_24, this.aEB.aIv(), asn2.getDescriptor());
                            this.a((lz_1)xp_12, asn2, s2);
                            if (!this.b((TK)xp_12.azE)) continue;
                            n3 = 1;
                            if (n2 >= aqt2.cOc.size() - 1 && aqt2.cOd == null) continue;
                            this.a((lz_1)xp_12, -89, va_23);
                            continue;
                        }
                        finally {
                            this.aEB.aIs();
                        }
                    }
                }
                finally {
                    this.aEB.aIs();
                }
            }
            if (aqt2.cOd != null) {
                va_2 va_25 = this.aEB.aIv();
                this.aEB.a(va_22, va_25, va_25, null);
                this.aEB.aIr();
                try {
                    short s3 = this.aEB.cb((short)1);
                    this.a((lz_1)aqt2.cOd, this.avk.eoQ, s3);
                    this.a((lz_1)aqt2.cOd, -88, aqt2.cOe);
                    this.a((lz_1)aqt2.cOd, this.avk.eoQ, (int)s3);
                    this.b((lz_1)aqt2.cOd, -65);
                    aqt2.cOe.set();
                    this.a((lz_1)aqt2.cOd, this.avk.eoQ, s);
                    if (this.b((TK)aqt2.cOd)) {
                        if (s > 255) {
                            this.b((lz_1)aqt2.cOd, -60);
                            this.b((lz_1)aqt2.cOd, -87);
                            this.writeShort(s);
                        } else {
                            this.b((lz_1)aqt2.cOd, -87);
                            this.writeByte(s);
                        }
                    }
                }
                finally {
                    this.aEB.aIs();
                }
            }
            va_23.set();
            if (n3 != 0) {
                this.a((TK)aqt2, (asn)null);
            }
            n2 = n3;
            return n2 != 0;
        }
        finally {
            this.aEB.aIs();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void a(xN xN2, nw_2 nw_22) {
        short s;
        avo_0 avo_02;
        adz adz2;
        block26: {
            Object object;
            adz2 = pp_0.O(xN2.HC) ? (xN2 instanceof kc_0 && !xN2.isStatic() ? nw_22.a((short)(pp_0.h(xN2.HC, (short)0) | 8), xN2.name + '$', cc_2.d(this.b((kc_0)xN2).getDescriptor(), this.c(xN2.bV()).getDescriptor())) : nw_22.a(pp_0.h(xN2.HC, (short)0), xN2.name, this.c(xN2).getDescriptor())) : nw_22.a(xN2.HC, xN2.name, this.c(xN2).getDescriptor());
            if (xN2.azA.length > 0) {
                short s2 = nw_22.ft("Exceptions");
                object = new short[xN2.azA.length];
                for (int j = 0; j < xN2.azA.length; ++j) {
                    object[j] = nw_22.fs(this.a(xN2.azA[j]).getDescriptor());
                }
                adz2.b(new im_1(s2, (short[])object));
            }
            if (xN2.jw()) {
                adz2.b(new ts_0(nw_22.ft("Deprecated")));
            }
            if ((xN2.HC & 0x500) != 0) {
                return;
            }
            avo_02 = new avo_0(adz2.asW());
            object = this.a(avo_02);
            try {
                block25: {
                    Object object2;
                    Object object3;
                    Object object4;
                    this.aEB.aIr();
                    if ((xN2.HC & 8) == 0) {
                        this.aEB.a((short)1, "this", this.c(xN2.bV()));
                    }
                    if (xN2 instanceof acc_0) {
                        acc_0 acc_02 = (acc_0)xN2;
                        object4 = acc_02.aro().doR.values().iterator();
                        while (object4.hasNext()) {
                            object3 = (jz_0)object4.next();
                            object2 = new fb_2(true, ((jz_0)object3).tF());
                            ((fb_2)object2).a(this.aEB.a(sA.bY(((jz_0)object3).getDescriptor()), (String)null, (asn)null));
                            acc_02.cky.put(((jz_0)object3).getName(), object2);
                        }
                    }
                    this.a(xN2);
                    if (xN2 instanceof acc_0) {
                        acc_0 acc_03 = (acc_0)xN2;
                        if (acc_03.ckx != null) {
                            this.b(acc_03.ckx);
                            if (acc_03.ckx instanceof akl_0) {
                                this.b(acc_03);
                                this.c(acc_03);
                            }
                        } else {
                            object4 = null;
                            object3 = this.c(acc_03.aro()).aFq().aFp();
                            if (object3 != null) {
                                object4 = new xj_1(acc_03.aP(), new vq_1(acc_03.aP(), (asn)object3));
                            }
                            object2 = new akl_0(acc_03.aP(), (jy_2)object4, new jy_2[0]);
                            ((xa)object2).a(xN2);
                            this.b((TK)object2);
                            this.b(acc_03);
                            this.c(acc_03);
                        }
                    }
                    try {
                        if (xN2.azB != null) break block25;
                        this.c("Method must have a body", xN2.aP());
                        return;
                    }
                    catch (RuntimeException runtimeException) {
                        if (runtimeException != aEx) {
                            throw runtimeException;
                        }
                        break block26;
                    }
                }
                if (this.d(xN2.azB)) {
                    if (this.b(xN2) != asn.cRw) {
                        this.c("Method must return a value", xN2.aP());
                    }
                    this.b((lz_1)xN2, -79);
                }
            }
            finally {
                this.aEB.aIs();
                this.a((avo_0)object);
            }
        }
        if (this.aED > 0) {
            return;
        }
        avo_02.aIt();
        avo_02.jQ(xN2.toString());
        short s3 = this.aEJ ? nw_22.ft("LineNumberTable") : (short)0;
        if (this.aEK) {
            this.a(avo_02, adz2);
            s = nw_22.ft("LocalVariableTable");
        } else {
            s = 0;
        }
        adz2.b(new Lb(this, nw_22.ft("Code"), avo_02, s3, s));
    }

    private void a(avo_0 avo_02, adz adz2) {
        nw_2 nw_22 = adz2.asW();
        Iterator iterator = avo_02.aIz().iterator();
        nw_22.ft("LocalVariableTable");
        while (iterator.hasNext()) {
            xl_2 xl_22 = (xl_2)iterator.next();
            if (xl_22.getName() == null) continue;
            String string = xl_22.tF().getDescriptor();
            nw_22.ft(string);
            nw_22.ft(xl_22.getName());
        }
    }

    private void a(xN xN2) {
        lz_1 lz_12;
        Map<String, fb_2> map = new HashMap<String, fb_2>();
        for (int j = 0; j < xN2.azz.length; ++j) {
            lz_12 = xN2.azz[j];
            fb_2 fb_22 = this.a((anb_1)lz_12);
            fb_22.a(this.aEB.a(sA.bY(fb_22.rC.getDescriptor()), lz_12.name, this.a(lz_12.HD)));
            if (map.put(lz_12.name, fb_22) == null) continue;
            this.c("Redefinition of parameter \"" + lz_12.name + "\"", xN2.aP());
        }
        xN2.avW = map;
        if (xN2 instanceof acc_0) {
            acc_0 acc_02 = (acc_0)xN2;
            if (acc_02.ckx != null) {
                this.a(acc_02.ckx, map);
            }
        }
        if (xN2.azB != null) {
            Iterator iterator = xN2.azB.iterator();
            while (iterator.hasNext()) {
                lz_12 = (TK)iterator.next();
                map = this.a((TK)lz_12, map);
            }
        }
    }

    private Map a(TK tK, Map map) {
        Map[] mapArray = new Map[]{map};
        kz_0 kz_02 = new kz_0(this, map, mapArray);
        try {
            tK.a(kz_02);
        }
        catch (rS rS2) {
            throw rS2.rV;
        }
        return mapArray[0];
    }

    private Map a(akE akE2, Map map) {
        akE2.avW = map;
        return akE2.avW;
    }

    private Map a(xa xa2, Map map) {
        xa2.avW = map;
        return xa2.avW;
    }

    private void a(lo_2 lo_22, Map map) {
        lo_22.avW = map;
        Iterator iterator = lo_22.bsj.iterator();
        while (iterator.hasNext()) {
            TK tK = (TK)iterator.next();
            map = this.a(tK, map);
        }
    }

    private void a(tb_1 tb_12, Map map) {
        tb_12.avW = map;
        this.a(tb_12.Pj, map);
    }

    private void a(no_1 no_12, Map map) {
        Map map2 = map;
        if (no_12.Pg != null) {
            map2 = this.a(no_12.Pg, map);
        }
        no_12.avW = map2;
        this.a(no_12.Pj, map2);
    }

    private void a(aia_1 aia_12, Map map) {
        aia_12.avW = map;
        this.a(aia_12.dOp, map);
        if (aia_12.dOq != null) {
            this.a(aia_12.dOq, map);
        }
    }

    private void a(ra_0 ra_02, Map map) {
        this.a(ra_02.bIo, map);
    }

    private void a(asD asD2, Map map) {
        asD2.avW = map;
        Map map2 = map;
        Iterator iterator = asD2.cSp.iterator();
        while (iterator.hasNext()) {
            jt_1 jt_12 = (jt_1)iterator.next();
            Iterator iterator2 = jt_12.blo.iterator();
            while (iterator2.hasNext()) {
                TK tK = (TK)iterator2.next();
                map2 = this.a(tK, map2);
            }
        }
    }

    private void a(vu_2 vu_22, Map map) {
        vu_22.avW = map;
        this.a(vu_22.Pj, map);
    }

    private void a(aqt aqt2, Map map) {
        aqt2.avW = map;
        this.a(aqt2.Pj, map);
        Iterator iterator = aqt2.cOc.iterator();
        while (iterator.hasNext()) {
            xp_1 xp_12 = (xp_1)iterator.next();
            this.a(xp_12, map);
        }
        if (aqt2.cOd != null) {
            this.a(aqt2.cOd, map);
        }
    }

    private void a(adh_0 adh_02, Map map) {
        adh_02.avW = map;
        this.a(adh_02.Pj, map);
    }

    private Map a(akj_0 akj_02, Map map) {
        akj_02.avW = map;
        return this.a((TK)akj_02.cDK, map);
    }

    private Map a(lG lG2, Map map) {
        HashMap<String, fb_2> hashMap = new HashMap<String, fb_2>();
        hashMap.putAll(map);
        for (int j = 0; j < lG2.HE.length; ++j) {
            jk_2 jk_22 = lG2.HE[j];
            fb_2 fb_22 = this.a(lG2, jk_22);
            if (hashMap.put(jk_22.name, fb_22) == null) continue;
            this.c("Redefinition of local variable \"" + jk_22.name + "\" ", jk_22.aP());
        }
        lG2.avW = hashMap;
        return hashMap;
    }

    protected void a(xp_1 xp_12, Map map) {
        HashMap<String, fb_2> hashMap = new HashMap<String, fb_2>();
        hashMap.putAll(map);
        fb_2 fb_22 = this.a(xp_12.azD);
        hashMap.put(xp_12.azD.name, fb_22);
        this.a(xp_12.azE, hashMap);
    }

    public fb_2 a(anb_1 anb_12) {
        if (anb_12.BP == null) {
            anb_12.BP = new fb_2(anb_12.rB, this.a(anb_12.HD));
        }
        return anb_12.BP;
    }

    private void a(fd_2 fd_22) {
        aj_1 aj_12;
        if (fd_22 instanceof jy_2) {
            aj_12 = (jy_2)fd_22;
            this.c((jy_2)aj_12);
        }
        if (fd_22 instanceof ln_2) {
            aj_12 = (ln_2)fd_22;
            for (int j = 0; j < ((ln_2)aj_12).bsi.length; ++j) {
                this.a(((ln_2)aj_12).bsi[j]);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void c(jy_2 jy_22) {
        avo_0 avo_02 = this.a(this.Gc());
        try {
            this.f(jy_22);
            this.h(jy_22);
        }
        finally {
            this.a(avo_02);
        }
    }

    private void d(jy_2 jy_22) {
        ky_0 ky_02 = new ky_0(this);
        try {
            jy_22.a(ky_02);
        }
        catch (yr_2 yr_22) {
            throw yr_22.rV;
        }
    }

    private void e(jy_2 jy_22) {
        this.e(jy_22, this.i(jy_22));
    }

    private void a(ayN ayN2) {
        if (ayN2.coI == "=") {
            this.f(ayN2.dmN);
            this.a((lz_1)ayN2, this.i(ayN2.ail), this.a((alb_0)ayN2.dmN), this.j(ayN2.ail));
            this.a(ayN2.dmN);
            return;
        }
        int n2 = this.f(ayN2.dmN);
        this.a((lz_1)ayN2, n2);
        asn asn2 = this.h(ayN2.dmN);
        asn asn3 = this.a((lz_1)ayN2, asn2, ayN2.coI.substring(0, ayN2.coI.length() - 1).intern(), ayN2.ail);
        if (!this.b(asn3, asn2) && !this.e(ayN2, asn3, asn2)) {
            throw new aHY("SNO: \"" + ayN2.coI + "\" reconversion failed");
        }
        this.a(ayN2.dmN);
    }

    private void a(afa_1 afa_12) {
        fb_2 fb_22 = this.d(afa_12);
        if (fb_22 != null) {
            this.a(afa_12, fb_22);
            return;
        }
        int n2 = this.f(afa_12.dET);
        this.a((lz_1)afa_12, n2);
        asn asn2 = this.h(afa_12.dET);
        asn asn3 = this.b((lz_1)afa_12, asn2);
        this.b((lz_1)afa_12, zh_2.a(asn3, 4, 10, 12, 15));
        if (afa_12.coI == "++") {
            this.b((lz_1)afa_12, 96 + zh_2.d(asn3));
        } else if (afa_12.coI == "--") {
            this.b((lz_1)afa_12, 100 + zh_2.d(asn3));
        } else {
            this.c("Unexpected operator \"" + afa_12.coI + "\"", afa_12.aP());
        }
        this.a((lz_1)afa_12, asn3, asn2);
        this.a(afa_12.dET);
    }

    private void a(zS zS2) {
        this.d(zS2.aGv);
    }

    private boolean a(yn_1 yn_12) {
        acc_0 acc_02 = (acc_0)yn_12.Dw();
        asn asn2 = this.c(acc_02.aro());
        this.b((lz_1)yn_12, 42);
        if (asn2.aFp() != null) {
            this.b((lz_1)yn_12, 43);
        }
        this.a((lz_1)yn_12, acc_02, (jy_2)null, asn2, yn_12.avU);
        return true;
    }

    private boolean a(akl_0 akl_02) {
        jy_2 jy_22;
        acc_0 acc_02 = (acc_0)akl_02.Dw();
        this.b((lz_1)akl_02, 42);
        azV azV2 = acc_02.aro();
        asn asn2 = this.c(azV2).aFq();
        if (akl_02.bzn != null) {
            jy_22 = akl_02.bzn;
        } else {
            asn asn3 = asn2.aFp();
            if (asn3 == null) {
                jy_22 = null;
            } else {
                jy_22 = new xj_1(akl_02.aP(), new vq_1(akl_02.aP(), asn3));
                jy_22.a(akl_02);
            }
        }
        this.a((lz_1)akl_02, acc_02, jy_22, asn2, akl_02.avU);
        return true;
    }

    private void a(jy_2 jy_22, va_2 va_22, boolean bl2) {
        lg_1 lg_12 = new lg_1(this, va_22, bl2);
        try {
            jy_22.a(lg_12);
        }
        catch (fq_2 fq_22) {
            throw fq_22.rV;
        }
    }

    private void b(jy_2 jy_22, va_2 va_22, boolean bl2) {
        asn asn2 = this.i(jy_22);
        apm_0 apm_02 = this.avk;
        if (asn2 == apm_02.cRE) {
            this.j(jy_22, apm_02.cRE, asn.cRE);
        } else if (asn2 != asn.cRE) {
            this.c("Not a boolean expression", jy_22.aP());
        }
        this.a((lz_1)jy_22, bl2 ? -102 : -103, va_22);
    }

    private void a(afk_2 afk_22, va_2 va_22, boolean bl2) {
        if (afk_22.coI == "!") {
            this.a(afk_22.dGk, va_22, !bl2);
            return;
        }
        this.c("Boolean expression expected", afk_22.aP());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void a(rr_2 rr_22, va_2 va_22, boolean bl2) {
        if (rr_22.aik == "|" || rr_22.aik == "^" || rr_22.aik == "&") {
            this.b(rr_22, va_22, bl2);
            return;
        }
        if (rr_22.aik == "||" || rr_22.aik == "&&") {
            Object object = this.j(rr_22.B);
            if (object instanceof Boolean) {
                if ((Boolean)object ^ rr_22.aik == "||") {
                    this.a(rr_22.ail, va_22, true ^ !bl2);
                } else {
                    this.a(rr_22.B, va_22, true ^ !bl2);
                    this.c(rr_22.ail);
                }
                return;
            }
            Object object2 = this.j(rr_22.ail);
            if (object2 instanceof Boolean) {
                if ((Boolean)object2 ^ rr_22.aik == "||") {
                    this.a(rr_22.B, va_22, true ^ !bl2);
                } else {
                    this.e(rr_22.B, this.i(rr_22.B));
                    this.a(rr_22.ail, va_22, true ^ !bl2);
                }
                return;
            }
            if (rr_22.aik == "||" ^ !bl2) {
                this.a(rr_22.B, va_22, true ^ !bl2);
                this.a(rr_22.ail, va_22, true ^ !bl2);
            } else {
                avo_0 avo_02 = this.aEB;
                avo_02.getClass();
                va_2 va_23 = new va_2(avo_02);
                this.a(rr_22.B, va_23, false ^ !bl2);
                this.a(rr_22.ail, va_22, true ^ !bl2);
                va_23.set();
            }
            return;
        }
        if (rr_22.aik == "==" || rr_22.aik == "!=" || rr_22.aik == "<=" || rr_22.aik == ">=" || rr_22.aik == "<" || rr_22.aik == ">") {
            boolean bl3;
            int n2;
            int n3 = rr_22.aik == "==" ? 0 : (rr_22.aik == "!=" ? 1 : (rr_22.aik == "<" ? 2 : (rr_22.aik == ">=" ? 3 : (rr_22.aik == ">" ? 4 : (n2 = rr_22.aik == "<=" ? 5 : Integer.MIN_VALUE)))));
            if (!bl2) {
                n2 ^= 1;
            }
            boolean bl4 = this.j(rr_22.B) == jy_2.Dk;
            boolean bl5 = bl3 = this.j(rr_22.ail) == jy_2.Dk;
            if (bl4 || bl3) {
                asn asn2;
                if (rr_22.aik != "==" && rr_22.aik != "!=") {
                    this.c("Operator \"" + rr_22.aik + "\" not allowed on operand \"null\"", rr_22.aP());
                }
                if (!(bl4 && bl3 || !(asn2 = this.i(bl4 ? rr_22.ail : rr_22.B)).isPrimitive())) {
                    this.c("Cannot compare \"null\" with primitive type \"" + asn2.toString() + "\"", rr_22.aP());
                }
                this.a((lz_1)rr_22, -58 + n2, va_22);
                return;
            }
            asn asn3 = this.i(rr_22.B);
            aNc aNc2 = this.aEB.aIw();
            asn asn4 = this.i(rr_22.ail);
            if (this.a(asn3).lT() && this.a(asn4).lT() && (rr_22.aik != "==" && rr_22.aik != "!=" || asn3.isPrimitive() || asn4.isPrimitive())) {
                asn asn5 = this.a((lz_1)rr_22, asn3, aNc2, asn4);
                if (asn5 == asn.cRB) {
                    this.a((lz_1)rr_22, -97 + n2, va_22);
                } else if (asn5 == asn.cRC) {
                    this.b((lz_1)rr_22, -108);
                    this.a((lz_1)rr_22, -103 + n2, va_22);
                } else if (asn5 == asn.cRA) {
                    if (rr_22.aik == ">" || rr_22.aik == ">=") {
                        this.b((lz_1)rr_22, -107);
                    } else {
                        this.b((lz_1)rr_22, -106);
                    }
                    this.a((lz_1)rr_22, -103 + n2, va_22);
                } else if (asn5 == asn.cRz) {
                    if (rr_22.aik == ">" || rr_22.aik == ">=") {
                        this.b((lz_1)rr_22, -105);
                    } else {
                        this.b((lz_1)rr_22, -104);
                    }
                    this.a((lz_1)rr_22, -103 + n2, va_22);
                } else {
                    throw new aHY("Unexpected promoted type \"" + asn5 + "\"");
                }
                return;
            }
            if (asn3 == asn.cRE && this.a(asn4) == asn.cRE || asn4 == asn.cRE && this.a(asn3) == asn.cRE) {
                if (rr_22.aik != "==" && rr_22.aik != "!=") {
                    this.c("Operator \"" + rr_22.aik + "\" not allowed on boolean operands", rr_22.aP());
                }
                apm_0 apm_02 = this.avk;
                if (asn3 == apm_02.cRE) {
                    this.aEB.a(aNc2);
                    try {
                        this.j(rr_22, apm_02.cRE, asn.cRE);
                    }
                    finally {
                        this.aEB.aIy();
                    }
                }
                if (asn4 == apm_02.cRE) {
                    this.j(rr_22, apm_02.cRE, asn.cRE);
                }
                this.a((lz_1)rr_22, -97 + n2, va_22);
                return;
            }
            if (!asn3.isPrimitive() && !asn4.isPrimitive()) {
                if (rr_22.aik != "==" && rr_22.aik != "!=") {
                    this.c("Operator \"" + rr_22.aik + "\" not allowed on reference operands", rr_22.aP());
                }
                this.a((lz_1)rr_22, -91 + n2, va_22);
                return;
            }
            this.c("Cannot compare types \"" + asn3 + "\" and \"" + asn4 + "\"", rr_22.aP());
        }
        this.c("Boolean expression expected", rr_22.aP());
    }

    private void a(zS zS2, va_2 va_22, boolean bl2) {
        this.a(zS2.aGv, va_22, bl2);
    }

    private int f(jy_2 jy_22) {
        int[] nArray = new int[1];
        ane_0 ane_02 = new ane_0(this, nArray);
        try {
            jy_22.a(ane_02);
            return nArray[0];
        }
        catch (tl_1 tl_12) {
            throw tl_12.rV;
        }
    }

    private int g(jy_2 jy_22) {
        return 0;
    }

    private int b(anM anM2) {
        return this.f(this.e(this.h(anM2)));
    }

    private int a(cb_1 cb_12) {
        if (cb_12.aKB.isStatic()) {
            jy_2 jy_22 = cb_12.aKA.oj();
            if (jy_22 != null) {
                this.a("CNSFA", "Left-hand side of static field access should be a type, not an rvalue", cb_12.aKA.aP());
                this.e(cb_12.aKA, this.i(jy_22));
            }
            return 0;
        }
        this.i(this.e(cb_12.aKA));
        return 1;
    }

    private int a(auo auo2) {
        if (!this.i(auo2.B).isArray()) {
            this.c("Cannot determine length of non-array type", auo2.aP());
        }
        return 1;
    }

    private int a(Wh wh) {
        asn asn2;
        asn asn3 = this.i(wh.B);
        if (!asn3.isArray()) {
            this.c("Subscript not allowed on non-array type \"" + asn3.toString() + "\"", wh.aP());
        }
        if (!this.b(asn2 = this.i(wh.bTT), asn.cRB) && !this.d(wh, asn2, asn.cRB)) {
            this.c("Index expression of type \"" + asn2 + "\" cannot be widened to \"int\"", wh.aP());
        }
        return 2;
    }

    private int a(aai_2 aai_22) {
        this.e(aai_22);
        return this.f(aai_22.aGv);
    }

    private int a(GT gT) {
        this.e(gT);
        return this.f(gT.aGv);
    }

    private int b(zS zS2) {
        return this.f(zS2.aGv);
    }

    private asn h(jy_2 jy_22) {
        asn[] asnArray = new asn[1];
        anh_2 anh_22 = new anh_2(this, asnArray);
        try {
            jy_22.a(anh_22);
            return asnArray[0];
        }
        catch (acj_0 acj_02) {
            throw acj_02.rV;
        }
    }

    private asn a(xh_1 xh_12) {
        avo_0 avo_02 = this.aEB;
        avo_02.getClass();
        va_2 va_22 = new va_2(avo_02);
        this.a(xh_12, va_22, true);
        this.b((lz_1)xh_12, 3);
        avo_0 avo_03 = this.aEB;
        avo_03.getClass();
        va_2 va_23 = new va_2(avo_03);
        this.a((lz_1)xh_12, -89, va_23);
        va_22.set();
        this.b((lz_1)xh_12, 4);
        va_23.set();
        return asn.cRE;
    }

    private asn c(anM anM2) {
        return this.h(this.e(this.h(anM2)));
    }

    private asn a(aoa_1 aoa_12) {
        return this.a((lz_1)aoa_12, aoa_12.BP);
    }

    private asn b(cb_1 cb_12) {
        this.a((ff_0)cb_12.aKB, cb_12.oi());
        if (cb_12.aKB.isStatic()) {
            this.b((lz_1)cb_12, -78);
        } else {
            this.b((lz_1)cb_12, -76);
        }
        this.b(cb_12.aKB.ic().getDescriptor(), cb_12.aKB.getName(), cb_12.aKB.tF().getDescriptor());
        return cb_12.aKB.tF();
    }

    private asn b(auo auo2) {
        this.b((lz_1)auo2, -66);
        return asn.cRB;
    }

    private asn a(aLs aLs2) {
        this.a((lz_1)aLs2);
        return this.c(aLs2);
    }

    private asn a(xj_1 xj_12) {
        this.a((lz_1)xj_12, this.e(xj_12), this.d(xj_12), this.c(xj_12));
        return this.c(xj_12);
    }

    private asn a(agx_0 agx_02) {
        lz_1 lz_12;
        String string;
        lc_0 lc_02 = agx_02.aP();
        apm_0 apm_02 = this.avk;
        asn asn2 = this.a(agx_02.HD);
        if (asn2.isPrimitive()) {
            String string2;
            this.b((lz_1)agx_02, -78);
            String string3 = asn2 == asn.cRw ? "Ljava/lang/Void;" : (asn2 == asn.cRx ? "Ljava/lang/Byte;" : (asn2 == asn.cRy ? "Ljava/lang/Character;" : (asn2 == asn.cRz ? "Ljava/lang/Double;" : (asn2 == asn.cRA ? "Ljava/lang/Float;" : (asn2 == asn.cRB ? "Ljava/lang/Integer;" : (asn2 == asn.cRC ? "Ljava/lang/Long;" : (asn2 == asn.cRD ? "Ljava/lang/Short;" : (string2 = asn2 == asn.cRE ? "Ljava/lang/Boolean;" : null))))))));
            if (string2 == null) {
                throw new aHY("SNO: Unidentifiable primitive type \"" + asn2 + "\"");
            }
            this.b(string2, "TYPE", "Ljava/lang/Class;");
            return apm_02.eoS;
        }
        Object object = agx_02.oi();
        while (true) {
            if (object instanceof el_1) break;
            object = object.Dw();
        }
        DM dM = (DM)object;
        if (dM.ac("class$") == null) {
            this.c(agx_02);
        }
        if (dM instanceof azV) {
            object = ((azV)dM).doQ;
        } else if (dM instanceof cg_2) {
            object = ((cg_2)dM).aKI;
        } else {
            throw new aHY("SNO: AbstractTypeDeclaration is neither ClassDeclaration nor InterfaceDeclaration");
        }
        String string4 = sA.toClassName(asn2.getDescriptor());
        if (string4.startsWith("[")) {
            string = "array" + string4.replace('.', '$').replace('[', '$');
            if (string.endsWith(";")) {
                string = string.substring(0, string.length() - 1);
            }
        } else {
            string = "class$" + string4.replace('.', '$');
        }
        boolean bl2 = false;
        Object object2 = object.iterator();
        block1: while (object2.hasNext()) {
            lz_12 = (aR)object2.next();
            if (!lz_12.isStatic() || !(lz_12 instanceof aBi)) continue;
            aBi aBi2 = (aBi)lz_12;
            jz_0[] jz_0Array = this.d(aBi2);
            for (int j = 0; j < jz_0Array.length; ++j) {
                if (!jz_0Array[j].getName().equals(string)) continue;
                bl2 = true;
                break block1;
            }
        }
        if (!bl2) {
            object2 = new vq_1(lc_02, apm_02.eoS);
            lz_12 = new aBi(lc_02, null, 8, (atu_0)object2, new jk_2[]{new jk_2(lc_02, string, 0, null)});
            if (dM instanceof azV) {
                ((azV)dM).a((aR)lz_12);
            } else if (dM instanceof cg_2) {
                ((cg_2)dM).e((aBi)lz_12);
            } else {
                throw new aHY("SNO: AbstractTypeDeclaration is neither ClassDeclaration nor InterfaceDeclaration");
            }
        }
        vq_1 vq_12 = new vq_1(lc_02, this.c(dM));
        object2 = new aai_2(lc_02, vq_12, string);
        lz_12 = new acq_0(lc_02, new rr_2(lc_02, (jy_2)object2, "!=", new aow_0(lc_02, null)), (jy_2)object2, new ayN(lc_02, (anw)object2, "=", new La(lc_02, vq_12, "class$", new jy_2[]{new aow_0(lc_02, string4)})));
        ((jy_2)lz_12).a(agx_02.oi());
        return this.h((jy_2)lz_12);
    }

    private asn b(ayN ayN2) {
        if (ayN2.coI == "=") {
            int n2 = this.f(ayN2.dmN);
            asn asn2 = this.i(ayN2.ail);
            asn asn3 = this.a((alb_0)ayN2.dmN);
            Object object = this.j(ayN2.ail);
            this.a((lz_1)ayN2, asn2, asn3, object);
            this.b((lz_1)ayN2, asn3, n2);
            this.a(ayN2.dmN);
            return asn3;
        }
        int n3 = this.f(ayN2.dmN);
        this.a((lz_1)ayN2, n3);
        asn asn4 = this.h(ayN2.dmN);
        asn asn5 = this.a((lz_1)ayN2, asn4, ayN2.coI.substring(0, ayN2.coI.length() - 1).intern(), ayN2.ail);
        if (!this.b(asn5, asn4) && !this.e(ayN2, asn5, asn4)) {
            throw new aHY("SNO: \"" + ayN2.coI + "\" reconversion failed");
        }
        this.b((lz_1)ayN2, asn4, n3);
        this.a(ayN2.dmN);
        return asn4;
    }

    /*
     * Enabled aggressive block sorting
     */
    private asn a(acq_0 acq_02) {
        Object object;
        aNc aNc2;
        asn asn2;
        aNc aNc3;
        asn asn3;
        avo_0 avo_02 = this.aEB;
        avo_02.getClass();
        va_2 va_22 = new va_2(avo_02);
        Object object2 = this.j(acq_02.B);
        if (object2 instanceof Boolean) {
            if (((Boolean)object2).booleanValue()) {
                asn3 = this.i(acq_02.dun);
                aNc3 = this.aEB.aIw();
                asn2 = this.a(acq_02.ail);
                aNc2 = null;
            } else {
                asn3 = this.a(acq_02.dun);
                aNc3 = null;
                asn2 = this.i(acq_02.ail);
                aNc2 = this.aEB.aIx();
            }
        } else {
            avo_0 avo_03 = this.aEB;
            avo_03.getClass();
            object = new va_2(avo_03);
            this.a(acq_02.B, (va_2)object, false);
            asn3 = this.i(acq_02.dun);
            aNc3 = this.aEB.aIw();
            this.a((lz_1)acq_02, -89, va_22);
            ((va_2)object).set();
            asn2 = this.i(acq_02.ail);
            aNc2 = this.aEB.aIx();
        }
        if (asn3 == asn2) {
            object = asn3;
        } else if (asn3.lT() && asn2.lT()) {
            object = this.a((lz_1)acq_02, asn3, aNc3, asn2, aNc2);
        } else if (this.j(acq_02.dun) == jy_2.Dk && !asn2.isPrimitive()) {
            object = asn2;
        } else if (!asn3.isPrimitive() && this.j(acq_02.ail) == jy_2.Dk) {
            object = asn3;
        } else if (!asn3.isPrimitive() && !asn2.isPrimitive()) {
            if (asn3.g(asn2)) {
                object = asn3;
            } else {
                if (!asn2.g(asn3)) {
                    this.c("Reference types \"" + asn3 + "\" and \"" + asn2 + "\" don't match", acq_02.aP());
                    return this.avk.eoQ;
                }
                object = asn2;
            }
        } else {
            this.c("Incompatible expression types \"" + asn3 + "\" and \"" + asn2 + "\"", acq_02.aP());
            return this.avk.eoQ;
        }
        va_22.set();
        return object;
    }

    private asn b(afa_1 afa_12) {
        fb_2 fb_22 = this.d(afa_12);
        if (fb_22 != null) {
            if (!afa_12.dES) {
                this.a((lz_1)afa_12, fb_22);
            }
            this.a(afa_12, fb_22);
            if (afa_12.dES) {
                this.a((lz_1)afa_12, fb_22);
            }
            return fb_22.rC;
        }
        int n2 = this.f(afa_12.dET);
        this.a((lz_1)afa_12, n2);
        asn asn2 = this.h(afa_12.dET);
        if (!afa_12.dES) {
            this.b((lz_1)afa_12, asn2, n2);
        }
        asn asn3 = this.b((lz_1)afa_12, asn2);
        this.b((lz_1)afa_12, zh_2.a(asn3, 4, 10, 12, 15));
        if (afa_12.coI == "++") {
            this.b((lz_1)afa_12, 96 + zh_2.d(asn3));
        } else if (afa_12.coI == "--") {
            this.b((lz_1)afa_12, 100 + zh_2.d(asn3));
        } else {
            this.c("Unexpected operator \"" + afa_12.coI + "\"", afa_12.aP());
        }
        this.a((lz_1)afa_12, asn3, asn2);
        if (afa_12.dES) {
            this.b((lz_1)afa_12, asn2, n2);
        }
        this.a(afa_12.dET);
        return asn2;
    }

    private void a(afa_1 afa_12, fb_2 fb_22) {
        if (fb_22.jl() > 255) {
            this.b((lz_1)afa_12, -60);
            this.b((lz_1)afa_12, -124);
            this.writeShort(fb_22.jl());
            this.writeShort(afa_12.coI == "++" ? 1 : -1);
        } else {
            this.b((lz_1)afa_12, -124);
            this.writeByte(fb_22.jl());
            this.writeByte(afa_12.coI == "++" ? 1 : -1);
        }
    }

    private asn b(Wh wh) {
        asn asn2 = this.a((alb_0)wh);
        this.b((lz_1)wh, 46 + zh_2.f(asn2));
        return asn2;
    }

    private asn b(aai_2 aai_22) {
        this.e(aai_22);
        return this.h(aai_22.aGv);
    }

    private asn b(GT gT) {
        this.e(gT);
        return this.h(gT.aGv);
    }

    private asn a(afk_2 afk_22) {
        if (afk_22.coI == "!") {
            return this.a((xh_1)afk_22);
        }
        if (afk_22.coI == "+") {
            return this.b((lz_1)afk_22, this.c(afk_22, this.i(afk_22.dGk)));
        }
        if (afk_22.coI == "-") {
            if (afk_22.dGk instanceof aow_0) {
                aow_0 aow_02 = (aow_0)afk_22.dGk;
                this.a((lz_1)afk_22, this.c(aow_02));
                return this.b((lz_1)afk_22, this.d(aow_02));
            }
            asn asn2 = this.b((lz_1)afk_22, this.c(afk_22, this.i(afk_22.dGk)));
            this.b((lz_1)afk_22, 116 + zh_2.d(asn2));
            return asn2;
        }
        if (afk_22.coI == "~") {
            asn asn3 = this.i(afk_22.dGk);
            asn asn4 = this.b((lz_1)afk_22, asn3);
            if (asn4 == asn.cRB) {
                this.b((lz_1)afk_22, 2);
                this.b((lz_1)afk_22, -126);
                return asn.cRB;
            }
            if (asn4 == asn.cRC) {
                this.b((lz_1)afk_22, 20);
                this.aY(-1L);
                this.b((lz_1)afk_22, -125);
                return asn.cRC;
            }
            this.c("Operator \"~\" not applicable to type \"" + asn4 + "\"", afk_22.aP());
        }
        this.c("Unexpected operator \"" + afk_22.coI + "\"", afk_22.aP());
        return this.avk.eoQ;
    }

    private asn a(p_0 p_02) {
        asn asn2 = this.i(p_02.B);
        asn asn3 = this.a(p_02.C);
        if (asn2.isInterface() || asn3.isInterface() || asn2.g(asn3) || asn3.g(asn2)) {
            this.b((lz_1)p_02, -63);
            this.dj(asn3.getDescriptor());
        } else {
            this.c("\"" + asn2 + "\" can never be an instance of \"" + asn3 + "\"", p_02.aP());
        }
        return asn.cRE;
    }

    private asn a(rr_2 rr_22) {
        if (rr_22.aik == "||" || rr_22.aik == "&&" || rr_22.aik == "==" || rr_22.aik == "!=" || rr_22.aik == "<" || rr_22.aik == ">" || rr_22.aik == "<=" || rr_22.aik == ">=") {
            return this.a((xh_1)rr_22);
        }
        return this.a((lz_1)rr_22, null, rr_22.xO(), rr_22.aik);
    }

    private asn a(agz_2 agz_22) {
        asn asn2 = this.a(agz_22.dJW);
        asn asn3 = this.i(agz_22.aGv);
        if (!(this.b(asn3, asn2) || this.d(agz_22, asn3, asn2) || this.e(agz_22, asn3, asn2) || this.e(asn3, asn2) || this.f(agz_22, asn3, asn2) || this.g(agz_22, asn3, asn2) || this.i(agz_22, asn3, asn2))) {
            this.c("Cannot cast \"" + asn3 + "\" to \"" + asn2 + "\"", agz_22.aP());
        }
        return asn2;
    }

    private asn c(zS zS2) {
        return this.h(zS2.aGv);
    }

    private asn a(La la) {
        int n2;
        asn[] asnArray;
        ff_2 ff_22 = this.c(la);
        if (la.bpM == null) {
            aim_2 aim_22 = la.oi();
            while (!(aim_22 instanceof aR)) {
                aim_22 = aim_22.Dw();
            }
            asnArray = (asn[])aim_22;
            if (!(aim_22 instanceof azV)) {
                aim_22 = aim_22.Dw();
            }
            azV azV2 = (azV)aim_22;
            if (ff_22.isStatic()) {
                this.a("IASM", "Implicit access to static method \"" + ff_22.toString() + "\"", la.aP());
            } else {
                this.a("IANSM", "Implicit access to non-static method \"" + ff_22.toString() + "\"", la.aP());
                if (asnArray.isStatic()) {
                    this.c("Instance method \"" + ff_22.toString() + "\" cannot be invoked in static context", la.aP());
                }
                this.a((lz_1)la, azV2, (aR)asnArray, ff_22.ic());
            }
        } else {
            boolean bl2 = this.b(la.bpM);
            if (bl2) {
                this.a(this.d(la.bpM));
            } else {
                this.i(this.e(la.bpM));
            }
            if (ff_22.isStatic()) {
                if (!bl2) {
                    this.e(la.bpM, this.a(la.bpM));
                }
            } else if (bl2) {
                this.c("Instance method \"" + la.methodName + "\" cannot be invoked in static context", la.aP());
            }
        }
        asnArray = ff_22.iy();
        for (n2 = 0; n2 < la.avU.length; ++n2) {
            this.a((lz_1)la, this.i(la.avU[n2]), asnArray[n2], this.j(la.avU[n2]));
        }
        this.a((ff_0)ff_22, la.oi());
        if (ff_22.ic().isInterface()) {
            this.b((lz_1)la, -71);
            this.d(ff_22.ic().getDescriptor(), ff_22.getName(), ff_22.getDescriptor());
            asn[] asnArray2 = ff_22.iy();
            int n3 = 1;
            for (int j = 0; j < asnArray2.length; ++j) {
                n3 += sA.bY(asnArray2[j].getDescriptor());
            }
            this.writeByte(n3);
            this.writeByte(0);
        } else if (!ff_22.isStatic() && ff_22.ib() == amf.cGq) {
            this.b((lz_1)la, -72);
            this.c(ff_22.ic().getDescriptor(), ff_22.getName() + '$', cc_2.d(ff_22.getDescriptor(), ff_22.ic().getDescriptor()));
        } else {
            n2 = ff_22.isStatic() ? -72 : -74;
            this.b((lz_1)la, n2);
            this.c(ff_22.ic().getDescriptor(), ff_22.getName(), ff_22.getDescriptor());
        }
        return ff_22.ix();
    }

    private asn a(ajs_2 ajs_22) {
        xN xN2;
        ff_2 ff_22 = this.c(ajs_22);
        aim_2 aim_22 = ajs_22.oi();
        while (aim_22 instanceof akE || aim_22 instanceof xp_1) {
            aim_22 = aim_22.Dw();
        }
        xN xN3 = xN2 = aim_22 instanceof xN ? (xN)aim_22 : null;
        if (xN2 == null) {
            this.c("Cannot invoke superclass method in non-method scope", ajs_22.aP());
            return asn.cRB;
        }
        if ((xN2.HC & 8) != 0) {
            this.c("Cannot invoke superclass method in static context", ajs_22.aP());
        }
        this.a((lz_1)ajs_22, this.c(xN2.bV()), 0);
        asn[] asnArray = ff_22.iy();
        for (int j = 0; j < ajs_22.avU.length; ++j) {
            this.a((lz_1)ajs_22, this.i(ajs_22.avU[j]), asnArray[j], this.j(ajs_22.avU[j]));
        }
        this.b((lz_1)ajs_22, -73);
        this.c(ff_22.ic().getDescriptor(), ajs_22.methodName, ff_22.getDescriptor());
        return ff_22.ix();
    }

    private asn a(Nl nl) {
        jy_2 jy_22;
        if (nl.asH == null) {
            nl.asH = this.a(nl.HD);
        }
        this.b((lz_1)nl, -69);
        this.dj(nl.asH.getDescriptor());
        this.b((lz_1)nl, 89);
        if (nl.asH.isInterface()) {
            this.c("Cannot instantiate \"" + nl.asH + "\"", nl.aP());
        }
        this.a(nl.asH, nl.oi());
        if (nl.asH.isAbstract()) {
            this.c("Cannot instantiate abstract \"" + nl.asH + "\"", nl.aP());
        }
        if (nl.bzn != null) {
            if (nl.asH.aFp() == null) {
                this.di("Static member class cannot be instantiated with qualified NEW");
            }
            jy_22 = nl.bzn;
        } else {
            aim_2 aim_22 = nl.oi();
            while (!(aim_22 instanceof aR)) {
                aim_22 = aim_22.Dw();
            }
            aR aR2 = (aR)aim_22;
            el_1 el_12 = (el_1)aim_22.Dw();
            if (!(el_12 instanceof azV) || aR2.isStatic()) {
                if (nl.asH.aFp() != null) {
                    this.c("Instantiation of \"" + nl.HD + "\" requires an enclosing instance", nl.aP());
                }
                jy_22 = null;
            } else {
                asn asn2 = nl.asH.ic();
                if (asn2 == null) {
                    jy_22 = null;
                } else {
                    jy_22 = new xj_1(nl.aP(), new vq_1(nl.aP(), asn2));
                    jy_22.a(nl.oi());
                }
            }
        }
        this.a((lz_1)nl, nl.oi(), jy_22, nl.asH, nl.avU);
        return nl.asH;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private asn a(afi_2 afi_22) {
        int n2;
        uy_1 uy_12 = afi_22.dHC;
        asn asn2 = this.c(uy_12).aFq();
        ms_2[] ms_2Array = asn2.aFi();
        if (ms_2Array.length == 0) {
            throw new aHY("SNO: Base class has no constructors");
        }
        adi_0 adi_02 = (adi_0)this.a((lz_1)afi_22, ms_2Array, afi_22.avU, uy_12);
        asn[] asnArray = adi_02.iy();
        lc_0 lc_02 = afi_22.aP();
        asn[] asnArray2 = new ArrayList();
        if (afi_22.bzn != null) {
            asnArray2.add(new anb_1(lc_02, true, new vq_1(lc_02, this.a(afi_22.bzn)), "this$base"));
        }
        for (int j = 0; j < asnArray.length; ++j) {
            asnArray2.add(new anb_1(lc_02, true, new vq_1(lc_02, asnArray[j]), "p" + j));
        }
        anb_1[] anb_1Array = asnArray2.toArray(new anb_1[asnArray2.size()]);
        asnArray2 = adi_02.iz();
        atu_0[] atu_0Array = new atu_0[asnArray2.length];
        for (n2 = 0; n2 < asnArray2.length; ++n2) {
            atu_0Array[n2] = new vq_1(lc_02, asnArray2[n2]);
        }
        n2 = 0;
        ac_1 ac_12 = afi_22.bzn == null ? null : new ac_1(lc_02, anb_1Array[n2++]);
        jy_2[] jy_2Array = new jy_2[asnArray.length];
        for (int j = 0; j < asnArray.length; ++j) {
            jy_2Array[j] = new ac_1(lc_02, anb_1Array[n2++]);
        }
        acc_0 acc_02 = new acc_0(lc_02, null, 0, anb_1Array, atu_0Array, new akl_0(lc_02, ac_12, jy_2Array), Collections.EMPTY_LIST);
        uy_12.f(acc_02);
        try {
            aLs aLs2;
            jy_2[] jy_2Array2;
            this.b((el_1)uy_12);
            this.b((lz_1)afi_22, -69);
            this.dj(this.c(afi_22.dHC).getDescriptor());
            this.b((lz_1)afi_22, 89);
            if (afi_22.bzn == null) {
                jy_2Array2 = afi_22.avU;
            } else {
                jy_2Array2 = new jy_2[afi_22.avU.length + 1];
                jy_2Array2[0] = afi_22.bzn;
                System.arraycopy(afi_22.avU, 0, jy_2Array2, 1, afi_22.avU.length);
            }
            aim_2 aim_22 = afi_22.oi();
            while (!(aim_22 instanceof aR)) {
                aim_22 = aim_22.Dw();
            }
            if (((aR)aim_22).isStatic()) {
                aLs2 = null;
            } else {
                aLs2 = new aLs(lc_02);
                aLs2.a(afi_22.oi());
            }
            this.a((lz_1)afi_22, afi_22.oi(), aLs2, this.c(afi_22.dHC), jy_2Array2);
        }
        finally {
            uy_12.doP.remove(uy_12.doP.size() - 1);
        }
        return this.c(afi_22.dHC);
    }

    private asn a(ac_1 ac_12) {
        fb_2 fb_22 = this.a(ac_12.bD);
        this.a((lz_1)ac_12, fb_22);
        return fb_22.rC;
    }

    private asn a(zj zj2) {
        for (int j = 0; j < zj2.aER.length; ++j) {
            asn asn2 = this.i(zj2.aER[j]);
            if (asn2 == asn.cRB || this.b((lz_1)zj2, asn2) == asn.cRB) continue;
            this.c("Invalid array size expression type", zj2.aP());
        }
        return this.a((lz_1)zj2, zj2.aER.length, zj2.aES, this.a(zj2.HD));
    }

    private asn a(aFz aFz2) {
        asn asn2 = this.a(aFz2.dHl);
        this.a(aFz2.dHm, asn2);
        return asn2;
    }

    private void a(ln_2 ln_22, asn asn2) {
        if (!asn2.isArray()) {
            this.di("Array initializer not allowed for non-array type \"" + asn2.toString() + "\"");
        }
        asn asn3 = asn2.aFs();
        this.a((lz_1)ln_22, new Integer(ln_22.bsi.length));
        this.a((lz_1)ln_22, 1, 0, asn3);
        for (int j = 0; j < ln_22.bsi.length; ++j) {
            this.b((lz_1)ln_22, 89);
            this.a((lz_1)ln_22, new Integer(j));
            fd_2 fd_22 = ln_22.bsi[j];
            if (fd_22 instanceof jy_2) {
                jy_2 jy_22 = (jy_2)fd_22;
                this.a((lz_1)ln_22, this.i(jy_22), asn3, this.j(jy_22));
            } else if (fd_22 instanceof ln_2) {
                this.a((ln_2)fd_22, asn3);
            } else {
                throw new aHY("Unexpected array initializer or rvalue class " + fd_22.getClass().getName());
            }
            this.b((lz_1)ln_22, 79 + zh_2.f(asn3));
        }
    }

    private asn a(aow_0 aow_02) {
        if (aow_02.value == ahr_1.cvt || aow_02.value == ahr_1.cvu) {
            this.c("This literal value may only appear in a negated context", aow_02.aP());
        }
        return this.a((lz_1)aow_02, aow_02.value == null ? jy_2.Dk : aow_02.value);
    }

    private asn i(jy_2 jy_22) {
        Object object = this.j(jy_22);
        if (object != null) {
            this.c(jy_22);
            this.a((lz_1)jy_22, object);
            return this.a(jy_22);
        }
        this.f(jy_22);
        return this.h(jy_22);
    }

    public final Object j(jy_2 jy_22) {
        if (jy_22.Dj != jy_2.Di) {
            return jy_22.Dj;
        }
        Object[] objectArray = new Object[1];
        ani_0 ani_02 = new ani_0(this, objectArray);
        try {
            jy_22.a(ani_02);
            jy_22.Dj = objectArray[0];
            return jy_22.Dj;
        }
        catch (awb_0 awb_02) {
            throw awb_02.rV;
        }
    }

    private Object k(jy_2 jy_22) {
        return null;
    }

    private Object d(anM anM2) {
        return this.j(this.e(this.h(anM2)));
    }

    private Object c(cb_1 cb_12) {
        return cb_12.aKB.getConstantValue();
    }

    private Object b(afk_2 afk_22) {
        if (afk_22.coI.equals("+")) {
            return this.j(afk_22.dGk);
        }
        if (afk_22.coI.equals("-")) {
            return this.l(afk_22.dGk);
        }
        if (afk_22.coI.equals("!")) {
            Object object = this.j(afk_22.dGk);
            return object instanceof Boolean ? (((Boolean)object).booleanValue() ? Boolean.FALSE : Boolean.TRUE) : null;
        }
        return null;
    }

    private Object b(acq_0 acq_02) {
        Object object = this.j(acq_02.B);
        if (object instanceof Boolean) {
            return (Boolean)object != false ? this.j(acq_02.dun) : this.j(acq_02.ail);
        }
        return null;
    }

    private Object b(rr_2 rr_22) {
        Object object;
        if ((rr_22.aik == "==" || rr_22.aik == "!=") && this.j(rr_22.B) == jy_2.Dk && this.j(rr_22.ail) == jy_2.Dk) {
            return rr_22.aik == "==" ? Boolean.TRUE : Boolean.FALSE;
        }
        if (rr_22.aik == "|" || rr_22.aik == "^" || rr_22.aik == "&" || rr_22.aik == "*" || rr_22.aik == "/" || rr_22.aik == "%" || rr_22.aik == "+" || rr_22.aik == "-") {
            Object object2;
            ArrayList<Object> arrayList = new ArrayList<Object>();
            Iterator iterator = rr_22.xO();
            while (iterator.hasNext()) {
                object2 = this.j((jy_2)iterator.next());
                if (object2 == null) {
                    return null;
                }
                arrayList.add(object2);
            }
            iterator = arrayList.iterator();
            object2 = iterator.next();
            while (iterator.hasNext()) {
                Object e = iterator.next();
                if (rr_22.aik == "+" && (object2 instanceof String || e instanceof String)) {
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append(object2.toString()).append(e.toString());
                    while (iterator.hasNext()) {
                        stringBuffer.append(iterator.next().toString());
                    }
                    return stringBuffer.toString();
                }
                if (!(object2 instanceof Number) || !(e instanceof Number)) {
                    return null;
                }
                try {
                    if (object2 instanceof Double || e instanceof Double) {
                        double d = ((Number)object2).doubleValue();
                        double d2 = ((Number)e).doubleValue();
                        object2 = rr_22.aik == "*" ? new Double(d * d2) : (rr_22.aik == "/" ? new Double(d / d2) : (rr_22.aik == "%" ? new Double(d % d2) : (rr_22.aik == "+" ? new Double(d + d2) : (rr_22.aik == "-" ? new Double(d - d2) : null))));
                    } else if (object2 instanceof Float || e instanceof Float) {
                        float f = ((Number)object2).floatValue();
                        float f2 = ((Number)e).floatValue();
                        object2 = rr_22.aik == "*" ? new Float(f * f2) : (rr_22.aik == "/" ? new Float(f / f2) : (rr_22.aik == "%" ? new Float(f % f2) : (rr_22.aik == "+" ? new Float(f + f2) : (rr_22.aik == "-" ? new Float(f - f2) : null))));
                    } else if (object2 instanceof Long || e instanceof Long) {
                        long l2 = ((Number)object2).longValue();
                        long l3 = ((Number)e).longValue();
                        object2 = rr_22.aik == "|" ? new Long(l2 | l3) : (rr_22.aik == "^" ? new Long(l2 ^ l3) : (rr_22.aik == "&" ? new Long(l2 & l3) : (rr_22.aik == "*" ? new Long(l2 * l3) : (rr_22.aik == "/" ? new Long(l2 / l3) : (rr_22.aik == "%" ? new Long(l2 % l3) : (rr_22.aik == "+" ? new Long(l2 + l3) : (rr_22.aik == "-" ? new Long(l2 - l3) : null)))))));
                    } else {
                        int n2 = ((Number)object2).intValue();
                        int n3 = ((Number)e).intValue();
                        Object object3 = rr_22.aik == "|" ? new Integer(n2 | n3) : (rr_22.aik == "^" ? new Integer(n2 ^ n3) : (rr_22.aik == "&" ? new Integer(n2 & n3) : (rr_22.aik == "*" ? new Integer(n2 * n3) : (rr_22.aik == "/" ? new Integer(n2 / n3) : (rr_22.aik == "%" ? new Integer(n2 % n3) : (rr_22.aik == "+" ? new Integer(n2 + n3) : (object2 = rr_22.aik == "-" ? new Integer(n2 - n3) : null)))))));
                    }
                    if (object2 != null) continue;
                    return null;
                }
                catch (ArithmeticException arithmeticException) {
                    return null;
                }
            }
            return object2;
        }
        if ((rr_22.aik == "&&" || rr_22.aik == "||") && (object = this.j(rr_22.B)) instanceof Boolean) {
            boolean bl2 = (Boolean)object;
            return rr_22.aik == "&&" ? (bl2 ? this.j(rr_22.ail) : Boolean.FALSE) : (bl2 ? Boolean.TRUE : this.j(rr_22.ail));
        }
        return null;
    }

    private Object b(agz_2 agz_22) {
        Object object = this.j(agz_22.aGv);
        if (object == null) {
            return null;
        }
        if (object instanceof Number) {
            asn asn2 = this.a(agz_22.dJW);
            if (asn2 == asn.cRx) {
                return new Byte(((Number)object).byteValue());
            }
            if (asn2 == asn.cRD) {
                return new Short(((Number)object).shortValue());
            }
            if (asn2 == asn.cRB) {
                return new Integer(((Number)object).intValue());
            }
            if (asn2 == asn.cRC) {
                return new Long(((Number)object).longValue());
            }
            if (asn2 == asn.cRA) {
                return new Float(((Number)object).floatValue());
            }
            if (asn2 == asn.cRz) {
                return new Double(((Number)object).doubleValue());
            }
        }
        return null;
    }

    private Object d(zS zS2) {
        return this.j(zS2.aGv);
    }

    private Object b(aow_0 aow_02) {
        if (aow_02.value == ahr_1.cvt || aow_02.value == ahr_1.cvu) {
            this.c("This literal value may only appear in a negated context", aow_02.aP());
        }
        return aow_02.value == null ? jy_2.Dk : aow_02.value;
    }

    private Object l(jy_2 jy_22) {
        Object[] objectArray = new Object[1];
        ank_0 ank_02 = new ank_0(this, objectArray);
        try {
            jy_22.a(ank_02);
            return objectArray[0];
        }
        catch (xr_1 xr_12) {
            throw xr_12.rV;
        }
    }

    private Object m(jy_2 jy_22) {
        return null;
    }

    private Object c(afk_2 afk_22) {
        return afk_22.coI.equals("+") ? this.l(afk_22.dGk) : (afk_22.coI.equals("-") ? this.j(afk_22.dGk) : null);
    }

    private Object e(zS zS2) {
        return this.l(zS2.aGv);
    }

    private Object c(aow_0 aow_02) {
        if (aow_02.value instanceof Byte) {
            return new Byte(-((Byte)aow_02.value).byteValue());
        }
        if (aow_02.value instanceof Short) {
            return new Short(-((Short)aow_02.value).shortValue());
        }
        if (aow_02.value instanceof Integer) {
            return new Integer(-((Integer)aow_02.value).intValue());
        }
        if (aow_02.value instanceof Long) {
            return new Long(-((Long)aow_02.value).longValue());
        }
        if (aow_02.value instanceof Float) {
            return new Float(-((Float)aow_02.value).floatValue());
        }
        if (aow_02.value instanceof Double) {
            return new Double(-((Double)aow_02.value).doubleValue());
        }
        this.c("Cannot negate this literal", aow_02.aP());
        return null;
    }

    private boolean c(TK tK) {
        boolean[] blArray = new boolean[1];
        anl_1 anl_12 = new anl_1(this, blArray);
        try {
            tK.a(anl_12);
            return blArray[0];
        }
        catch (og og2) {
            throw og2.rV;
        }
    }

    public boolean d(TK tK) {
        return true;
    }

    public boolean b(ek_0 ek_02) {
        return false;
    }

    public boolean b(ail_1 ail_12) {
        return false;
    }

    public boolean c(ra_0 ra_02) {
        return this.c(ra_02.bIo);
    }

    public boolean e(List list) {
        for (int j = 0; j < list.size(); ++j) {
            if (!this.c((TK)list.get(j))) continue;
            return true;
        }
        return false;
    }

    public boolean b(lo_2 lo_22) {
        return this.e(lo_22.bsj);
    }

    public boolean c(aBi aBi2) {
        for (int j = 0; j < aBi2.HE.length; ++j) {
            jk_2 jk_22 = aBi2.HE[j];
            if (this.a(aBi2, jk_22) == null) continue;
            return true;
        }
        return false;
    }

    private void a(TK tK, asn asn2) {
        ann_0 ann_02 = new ann_0(this, asn2);
        tK.a(ann_02);
    }

    public void b(TK tK, asn asn2) {
    }

    public void a(vu_2 vu_22, asn asn2) {
        this.a((lz_1)vu_22, this.avk.eoQ, (int)vu_22.asR);
        this.b((lz_1)vu_22, -61);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void a(aqt aqt2, asn asn2) {
        if (aqt2.cOe != null) {
            this.aEB.aIr();
            try {
                short s = 0;
                if (asn2 != null) {
                    s = this.aEB.cb(sA.bY(asn2.getDescriptor()));
                    this.a((lz_1)aqt2, asn2, s);
                }
                this.a((lz_1)aqt2, -88, aqt2.cOe);
                if (asn2 != null) {
                    this.a((lz_1)aqt2, asn2, (int)s);
                }
            }
            finally {
                this.aEB.aIs();
            }
        }
    }

    private void a(anw anw2) {
        anO anO2 = new anO(this);
        try {
            anw2.a(anO2);
        }
        catch (ke_2 ke_22) {
            throw ke_22.rV;
        }
    }

    private void e(anM anM2) {
        this.a(this.f(this.h(anM2)));
    }

    private void b(aoa_1 aoa_12) {
        this.a((lz_1)aoa_12, aoa_12.BP.rC, aoa_12.BP);
    }

    private void d(cb_1 cb_12) {
        this.a((ff_0)cb_12.aKB, cb_12.oi());
        this.b((lz_1)cb_12, cb_12.aKB.isStatic() ? -77 : -75);
        this.b(cb_12.aKB.ic().getDescriptor(), cb_12.aKB.getName(), cb_12.aKB.getDescriptor());
    }

    private void c(Wh wh) {
        this.b((lz_1)wh, 79 + zh_2.f(this.a((alb_0)wh)));
    }

    private void c(aai_2 aai_22) {
        this.e(aai_22);
        this.a(this.f((alb_0)aai_22.aGv));
    }

    private void c(GT gT) {
        this.e(gT);
        this.a(this.f((alb_0)gT.aGv));
    }

    private void f(zS zS2) {
        this.a(this.f((alb_0)zS2.aGv));
    }

    private asn a(alb_0 alb_02) {
        asn[] asnArray = new asn[1];
        anp_1 anp_12 = new anp_1(this, asnArray);
        try {
            alb_02.a(anp_12);
            return asnArray[0] != null ? asnArray[0] : this.avk.eoQ;
        }
        catch (ba_2 ba_22) {
            throw ba_22.rV;
        }
    }

    private asn b(vq_1 vq_12) {
        return vq_12.asH;
    }

    private asn b(gw_1 gw_12) {
        switch (gw_12.index) {
            case 0: {
                return asn.cRw;
            }
            case 1: {
                return asn.cRx;
            }
            case 2: {
                return asn.cRD;
            }
            case 3: {
                return asn.cRy;
            }
            case 4: {
                return asn.cRB;
            }
            case 5: {
                return asn.cRC;
            }
            case 6: {
                return asn.cRA;
            }
            case 7: {
                return asn.cRz;
            }
            case 8: {
                return asn.cRE;
            }
        }
        throw new aHY("Invalid index " + gw_12.index);
    }

    private asn b(ft ft2) {
        TK tK = null;
        el_1 el_12 = null;
        Object object = ft2.Dw();
        while (true) {
            if (object instanceof TK && tK == null) {
                tK = (TK)object;
            }
            if (object instanceof el_1 && el_12 == null) {
                el_12 = (el_1)object;
            }
            if (object instanceof kh_1) break;
            object = object.Dw();
        }
        kh_1 kh_12 = (kh_1)object;
        if (ft2.rb.length == 1) {
            Object object2;
            Object object3;
            object = ft2.rb[0];
            Object object4 = this.a(ft2.Dw(), (String)object);
            if (object4 != null) {
                return this.c((el_1)object4);
            }
            if (el_12 != null) {
                object4 = el_12;
                while (!(object4 instanceof kh_1)) {
                    if (object4 instanceof el_1 && (object3 = this.b(this.c((DM)object4), (String)object, ft2.aP())) != null) {
                        return object3;
                    }
                    object4 = object4.Dw();
                }
            }
            if ((object4 = this.a((String)object, ft2.aP())) != null) {
                return object4;
            }
            object4 = kh_12.eX((String)object);
            if (object4 != null) {
                return this.c((DM)object4);
            }
            object4 = kh_12.bnk == null ? null : kh_12.bnk.doL;
            object3 = object4 == null ? object : (String)object4 + "." + (String)object;
            Object object5 = this.a(ft2.aP(), (String)object3);
            if (object5 != null) {
                return object5;
            }
            object4 = this.b((String)object, ft2.aP());
            if (object4 != null) {
                return object4;
            }
            object4 = (List)this.aEN.get(object);
            if (object4 != null) {
                object3 = null;
                object5 = object4.iterator();
                while (object5.hasNext()) {
                    asn asn2;
                    object2 = object5.next();
                    if (!(object2 instanceof asn) || !this.a(asn2 = (asn)object2, (aim_2)tK)) continue;
                    if (object3 != null && object3 != asn2) {
                        this.di("Ambiguous static imports: \"" + ((asn)object3).toString() + "\" vs. \"" + asn2.toString() + "\"");
                    }
                    object3 = asn2;
                }
                if (object3 != null) {
                    return object3;
                }
            }
            object4 = null;
            object3 = this.aEO.iterator();
            while (object3.hasNext()) {
                object5 = (asn)object3.next();
                object2 = ((asn)object5).aFo();
                for (int j = 0; j < ((E)object2).length; ++j) {
                    Object e = object2[j];
                    if (!this.a((asn)e, (aim_2)tK) || !((asn)e).getDescriptor().endsWith('$' + (String)object + ';')) continue;
                    if (object4 != null) {
                        this.di("Ambiguous static imports: \"" + ((asn)object4).toString() + "\" vs. \"" + ((asn)e).toString() + "\"");
                    }
                    object4 = e;
                }
            }
            if (object4 != null) {
                return object4;
            }
            this.c("Cannot determine simple type name \"" + (String)object + "\"", ft2.aP());
            return this.avk.eoQ;
        }
        object = this.a(ft2.aP(), ft2.Dw(), ft2.rb, ft2.rb.length - 1);
        if (object instanceof lj_2) {
            String string = jf_1.a(ft2.rb, ".");
            asn asn3 = this.a(ft2.aP(), string);
            if (asn3 != null) {
                return asn3;
            }
            this.c("Class \"" + string + "\" not found", ft2.aP());
            return this.avk.eoQ;
        }
        String string = ft2.rb[ft2.rb.length - 1];
        asn[] asnArray = this.a(this.d((alb_0)object)).jw(string);
        if (asnArray.length == 1) {
            return asnArray[0];
        }
        if (asnArray.length == 0) {
            this.c("\"" + object + "\" declares no member type \"" + string + "\"", ft2.aP());
        } else {
            this.c("\"" + object + "\" and its supertypes declare more than one member type \"" + string + "\"", ft2.aP());
        }
        return this.avk.eoQ;
    }

    private asn b(cg_1 cg_12) {
        asn asn2 = this.a(cg_12.ij);
        asn asn3 = this.b(asn2, cg_12.ik, cg_12.aP());
        if (asn3 == null) {
            this.c("\"" + asn2 + "\" has no member type \"" + cg_12.ik + "\"", cg_12.aP());
        }
        return asn3;
    }

    private asn b(ahe_1 ahe_12) {
        return this.a(ahe_12.dMM).j(this.avk.eoQ);
    }

    private asn f(anM anM2) {
        return this.a(this.h(anM2));
    }

    private asn a(lj_2 lj_22) {
        this.c("Unknown variable or type \"" + lj_22.name + "\"", lj_22.aP());
        return this.avk.eoQ;
    }

    private asn c(aoa_1 aoa_12) {
        return aoa_12.BP.rC;
    }

    private asn e(cb_1 cb_12) {
        return cb_12.aKB.tF();
    }

    private asn c(auo auo2) {
        return asn.cRB;
    }

    private asn b(aLs aLs2) {
        return this.c(aLs2);
    }

    private asn b(xj_1 xj_12) {
        return this.c(xj_12);
    }

    private asn b(agx_0 agx_02) {
        return this.avk.eoS;
    }

    private asn c(ayN ayN2) {
        return this.a((alb_0)ayN2.dmN);
    }

    private asn c(acq_0 acq_02) {
        asn asn2;
        asn asn3 = this.a(acq_02.dun);
        if (asn3 == (asn2 = this.a(acq_02.ail))) {
            return asn3;
        }
        if (asn3.lT() && asn2.lT()) {
            return this.c(acq_02, asn3, asn2);
        }
        if (this.j(acq_02.dun) == jy_2.Dk && !asn2.isPrimitive()) {
            return asn2;
        }
        if (!asn3.isPrimitive() && this.j(acq_02.ail) == jy_2.Dk) {
            return asn3;
        }
        if (!asn3.isPrimitive() && !asn2.isPrimitive()) {
            if (asn3.g(asn2)) {
                return asn3;
            }
            if (asn2.g(asn3)) {
                return asn2;
            }
            this.c("Reference types \"" + asn3 + "\" and \"" + asn2 + "\" don't match", acq_02.aP());
            return this.avk.eoQ;
        }
        this.c("Incompatible expression types \"" + asn3 + "\" and \"" + asn2 + "\"", acq_02.aP());
        return this.avk.eoQ;
    }

    private asn c(afa_1 afa_12) {
        return this.a((alb_0)afa_12.dET);
    }

    private asn d(Wh wh) {
        return this.a(wh.B).aFs();
    }

    private asn d(aai_2 aai_22) {
        this.e(aai_22);
        return this.a(aai_22.aGv);
    }

    private asn d(GT gT) {
        this.e(gT);
        return this.a(gT.aGv);
    }

    private asn d(afk_2 afk_22) {
        if (afk_22.coI == "!") {
            return asn.cRE;
        }
        if (afk_22.coI == "+" || afk_22.coI == "-" || afk_22.coI == "~") {
            return this.d(afk_22, this.a(this.a(afk_22.dGk)));
        }
        this.c("Unexpected operator \"" + afk_22.coI + "\"", afk_22.aP());
        return asn.cRE;
    }

    private asn b(p_0 p_02) {
        return asn.cRE;
    }

    private asn c(rr_2 rr_22) {
        if (rr_22.aik == "||" || rr_22.aik == "&&" || rr_22.aik == "==" || rr_22.aik == "!=" || rr_22.aik == "<" || rr_22.aik == ">" || rr_22.aik == "<=" || rr_22.aik == ">=") {
            return asn.cRE;
        }
        if (rr_22.aik == "|" || rr_22.aik == "^" || rr_22.aik == "&") {
            asn asn2 = this.a(rr_22.B);
            return asn2 == asn.cRE || asn2 == this.avk.cRE ? asn.cRE : this.c(rr_22, asn2, this.a(rr_22.ail));
        }
        if (rr_22.aik == "*" || rr_22.aik == "/" || rr_22.aik == "%" || rr_22.aik == "+" || rr_22.aik == "-") {
            apm_0 apm_02 = this.avk;
            Iterator iterator = rr_22.xO();
            asn asn3 = this.a(this.a((jy_2)iterator.next()));
            if (rr_22.aik == "+" && asn3 == apm_02.eoR) {
                return apm_02.eoR;
            }
            do {
                asn asn4 = this.a(this.a((jy_2)iterator.next()));
                if (rr_22.aik == "+" && asn4 == apm_02.eoR) {
                    return apm_02.eoR;
                }
                asn3 = this.c(rr_22, asn3, asn4);
            } while (iterator.hasNext());
            return asn3;
        }
        if (rr_22.aik == "<<" || rr_22.aik == ">>" || rr_22.aik == ">>>") {
            asn asn5 = this.a(rr_22.B);
            return this.d(rr_22, asn5);
        }
        this.c("Unexpected operator \"" + rr_22.aik + "\"", rr_22.aP());
        return this.avk.eoQ;
    }

    private asn a(asn asn2) {
        asn asn3 = this.c(asn2);
        return asn3 != null ? asn3 : asn2;
    }

    private asn c(agz_2 agz_22) {
        return this.a(agz_22.dJW);
    }

    private asn g(zS zS2) {
        return this.a(zS2.aGv);
    }

    private asn b(La la) {
        if (la.boI == null) {
            la.boI = this.c(la);
        }
        return la.boI.ix();
    }

    private asn b(ajs_2 ajs_22) {
        return this.c(ajs_22).ix();
    }

    private asn b(Nl nl) {
        if (nl.asH == null) {
            nl.asH = this.a(nl.HD);
        }
        return nl.asH;
    }

    private asn b(afi_2 afi_22) {
        return this.c(afi_22.dHC);
    }

    private asn b(ac_1 ac_12) {
        return this.a((anb_1)ac_12.bD).rC;
    }

    private asn b(zj zj2) {
        asn asn2 = this.a(zj2.HD);
        return asn2.a(zj2.aER.length + zj2.aES, this.avk.eoQ);
    }

    private asn b(aFz aFz2) {
        return this.a(aFz2.dHl);
    }

    private asn d(aow_0 aow_02) {
        if (aow_02.value instanceof Short) {
            return asn.cRD;
        }
        if (aow_02.value instanceof Byte) {
            return asn.cRx;
        }
        if (aow_02.value instanceof Integer) {
            return asn.cRB;
        }
        if (aow_02.value instanceof Long) {
            return asn.cRC;
        }
        if (aow_02.value instanceof Float) {
            return asn.cRA;
        }
        if (aow_02.value instanceof Double) {
            return asn.cRz;
        }
        if (aow_02.value instanceof String) {
            return this.avk.eoR;
        }
        if (aow_02.value instanceof Character) {
            return asn.cRy;
        }
        if (aow_02.value instanceof Boolean) {
            return asn.cRE;
        }
        if (aow_02.value == null) {
            return asn.cRw;
        }
        throw new aHY("SNO: Unidentifiable literal type \"" + aow_02.value.getClass().getName() + "\"");
    }

    private boolean b(alb_0 alb_02) {
        boolean[] blArray = new boolean[1];
        anq_1 anq_12 = new anq_1(this, blArray);
        try {
            alb_02.a(anq_12);
            return blArray[0];
        }
        catch (vr_1 vr_12) {
            throw vr_12.rV;
        }
    }

    private boolean c(alb_0 alb_02) {
        return alb_02 instanceof atu_0;
    }

    private boolean g(anM anM2) {
        return this.b(this.h(anM2));
    }

    private boolean a(ff_0 ff_02, aim_2 aim_22) {
        asn asn2 = ff_02.ic();
        boolean bl2 = this.a(asn2, aim_22);
        bl2 = bl2 && this.a(asn2, ff_02.ib(), aim_22);
        return bl2;
    }

    private void a(ff_0 ff_02, TK tK) {
        asn asn2 = ff_02.ic();
        this.a(asn2, tK);
        this.a(asn2, ff_02.ib(), tK);
    }

    private boolean a(asn asn2, amf amf2, aim_2 aim_22) {
        return null == this.b(asn2, amf2, aim_22);
    }

    private void a(asn asn2, amf amf2, TK tK) {
        String string = this.b(asn2, amf2, tK);
        if (string != null) {
            this.c(string, tK.aP());
        }
    }

    private String b(asn asn2, amf amf2, aim_2 aim_22) {
        asn asn3;
        if (amf2 == amf.cGt) {
            return null;
        }
        Object object = aim_22;
        while (true) {
            if (object instanceof el_1) break;
            object = object.Dw();
        }
        asn asn4 = this.c((el_1)object);
        if (asn4 == asn2) {
            return null;
        }
        object = asn2;
        for (asn3 = asn2.ic(); asn3 != null; asn3 = asn3.ic()) {
            object = asn3;
        }
        asn3 = asn4;
        for (asn asn5 = asn4.ic(); asn5 != null; asn5 = asn5.ic()) {
            asn3 = asn5;
        }
        if (object == asn3) {
            return null;
        }
        if (amf2 == amf.cGq) {
            return "Private member cannot be accessed from type \"" + asn4 + "\".";
        }
        if (sA.k(asn2.getDescriptor(), asn4.getDescriptor())) {
            return null;
        }
        if (amf2 == amf.cGs) {
            return "Member with \"" + amf2 + "\" access cannot be accessed from type \"" + asn4 + "\".";
        }
        object = asn4;
        do {
            if (!asn2.g((asn)object)) continue;
            return null;
        } while ((object = ((asn)object).aFp()) != null);
        return "Protected member cannot be accessed from type \"" + asn4 + "\", which is neither declared in the same package as nor is a subclass of \"" + asn2 + "\".";
    }

    private boolean a(asn asn2, aim_2 aim_22) {
        return null == this.b(asn2, aim_22);
    }

    private void a(asn asn2, TK tK) {
        String string = this.b(asn2, tK);
        if (string != null) {
            this.c(string, tK.aP());
        }
    }

    private String b(asn asn2, aim_2 aim_22) {
        asn asn3 = asn2.ic();
        if (asn3 == null) {
            if (asn2.ib() == amf.cGt) {
                return null;
            }
            if (asn2.ib() == amf.cGs) {
                Object object = aim_22;
                while (true) {
                    if (object instanceof el_1) break;
                    object = object.Dw();
                }
                asn asn4 = this.c((el_1)object);
                object = sA.cg(asn2.getDescriptor());
                String string = sA.cg(asn4.getDescriptor());
                if (object == null ? string != null : !((String)object).equals(string)) {
                    return "\"" + asn2 + "\" is inaccessible from this package";
                }
                return null;
            }
            throw new aHY("\"" + asn2 + "\" has unexpected access \"" + asn2.ib() + "\"");
        }
        return this.b(asn3, asn2.ib(), aim_22);
    }

    private atu_0 d(alb_0 alb_02) {
        atu_0 atu_02 = alb_02.aAo();
        if (atu_02 == null) {
            this.c("Expression \"" + alb_02.toString() + "\" is not a type", alb_02.aP());
            return new vq_1(alb_02.aP(), this.avk.eoQ);
        }
        return atu_02;
    }

    private jy_2 e(alb_0 alb_02) {
        jy_2 jy_22 = alb_02.oj();
        if (jy_22 == null) {
            this.c("Expression \"" + alb_02.toString() + "\" is not an rvalue", alb_02.aP());
            return new aow_0(alb_02.aP(), "X");
        }
        return jy_22;
    }

    public final anw f(alb_0 alb_02) {
        anw anw2 = alb_02.aAp();
        if (anw2 == null) {
            this.c("Expression \"" + alb_02.toString() + "\" is not an lvalue", alb_02.aP());
            return new anR(this, alb_02.aP(), alb_02);
        }
        return anw2;
    }

    void b(acc_0 acc_02) {
        Iterator iterator = acc_02.aro().doR.values().iterator();
        while (iterator.hasNext()) {
            jz_0 jz_02 = (jz_0)iterator.next();
            fb_2 fb_22 = (fb_2)acc_02.cky.get(jz_02.getName());
            if (fb_22 == null) {
                throw new aHY("SNO: Synthetic parameter for synthetic field \"" + jz_02.getName() + "\" not found");
            }
            cr cr2 = new cr(new ayN(acc_02.aP(), new cb_1(acc_02.aP(), new aLs(acc_02.aP()), jz_02), "=", new aoa_1(acc_02.aP(), fb_22)));
            cr2.a(acc_02);
            this.b(cr2);
        }
    }

    void c(acc_0 acc_02) {
        for (int j = 0; j < acc_02.aro().doQ.size(); ++j) {
            TK tK;
            aR aR2 = (aR)acc_02.aro().doQ.get(j);
            if (aR2.isStatic() || this.b(tK = (TK)((Object)aR2))) continue;
            this.c("Instance variable declarator or instance initializer does not complete normally", tK.aP());
        }
    }

    private void a(aim_2 aim_22, aim_2 aim_23, asn asn2) {
        for (aim_2 aim_24 = aim_22; aim_24 != aim_23; aim_24 = aim_24.Dw()) {
            if (!(aim_24 instanceof TK)) continue;
            this.a((TK)aim_24, asn2);
        }
    }

    private asn a(lz_1 lz_12, asn asn2, String string, jy_2 jy_22) {
        return this.a(lz_12, asn2, Arrays.asList(jy_22).iterator(), string);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private asn a(lz_1 lz_12, asn object, Iterator iterator, String string) {
        if (string == "|" || string == "^" || string == "&") {
            int n2 = string == "&" ? 126 : (string == "|" ? -128 : (string == "^" ? -126 : Integer.MAX_VALUE));
            do {
                Object object2;
                jy_2 jy_22 = (jy_2)iterator.next();
                if (object == null) {
                    object = this.i(jy_22);
                    continue;
                }
                aNc aNc2 = this.aEB.aIw();
                asn asn2 = this.i(jy_22);
                if (((asn)object).lT() && asn2.lT()) {
                    object2 = this.a(lz_12, (asn)object, aNc2, asn2);
                    if (object2 == asn.cRB) {
                        this.b(lz_12, n2);
                    } else if (object2 == asn.cRC) {
                        this.b(lz_12, n2 + 1);
                    } else {
                        this.c("Operator \"" + string + "\" not defined on types \"" + object + "\" and \"" + asn2 + "\"", lz_12.aP());
                    }
                    object = object2;
                    continue;
                }
                if (object == asn.cRE && this.a(asn2) == asn.cRE || this.a((asn)object) == asn.cRE && asn2 == asn.cRE) {
                    object2 = this.avk;
                    if (object == ((apm_0)object2).cRE) {
                        this.aEB.a(aNc2);
                        try {
                            this.j(lz_12, ((apm_0)object2).cRE, asn.cRE);
                        }
                        finally {
                            this.aEB.aIy();
                        }
                    }
                    if (asn2 == ((apm_0)object2).cRE) {
                        this.j(lz_12, ((apm_0)object2).cRE, asn.cRE);
                    }
                    this.b(lz_12, n2);
                    object = asn.cRE;
                    continue;
                }
                this.c("Operator \"" + string + "\" not defined on types \"" + object + "\" and \"" + asn2 + "\"", lz_12.aP());
                object = asn.cRB;
            } while (iterator.hasNext());
            return object;
        }
        if (string == "*" || string == "/" || string == "%" || string == "+" || string == "-") {
            int n3 = string == "*" ? 104 : (string == "/" ? 108 : (string == "%" ? 112 : (string == "+" ? 96 : (string == "-" ? 100 : Integer.MAX_VALUE))));
            do {
                int n4;
                asn asn3;
                jy_2 jy_23 = (jy_2)iterator.next();
                asn asn4 = this.a(jy_23);
                apm_0 apm_02 = this.avk;
                if (string == "+" && (object == apm_02.eoR || asn4 == apm_02.eoR)) {
                    return this.a(lz_12, (asn)object, jy_23, iterator);
                }
                if (object == null) {
                    object = this.i(jy_23);
                    continue;
                }
                aNc aNc3 = this.aEB.aIw();
                if ((object = this.a(lz_12, (asn)object, aNc3, asn3 = this.i(jy_23))) == asn.cRB) {
                    n4 = n3;
                } else if (object == asn.cRC) {
                    n4 = n3 + 1;
                } else if (object == asn.cRA) {
                    n4 = n3 + 2;
                } else if (object == asn.cRz) {
                    n4 = n3 + 3;
                } else {
                    this.c("Unexpected promoted type \"" + object + "\"", lz_12.aP());
                    n4 = n3;
                }
                this.b(lz_12, n4);
            } while (iterator.hasNext());
            return object;
        }
        if (string == "<<" || string == ">>" || string == ">>>") {
            int n5 = string == "<<" ? 120 : (string == ">>" ? 122 : (string == ">>>" ? 124 : Integer.MAX_VALUE));
            do {
                asn asn5;
                asn asn6;
                jy_2 jy_24 = (jy_2)iterator.next();
                if (object == null) {
                    object = this.i(jy_24);
                    continue;
                }
                aNc aNc4 = this.aEB.aIw();
                asn asn7 = this.i(jy_24);
                this.aEB.a(aNc4);
                try {
                    asn6 = this.b(lz_12, (asn)object);
                }
                finally {
                    this.aEB.aIy();
                }
                if (asn6 != asn.cRB && asn6 != asn.cRC) {
                    this.c("Shift operation not allowed on operand type \"" + object + "\"", lz_12.aP());
                }
                if ((asn5 = this.b(lz_12, asn7)) != asn.cRB && asn5 != asn.cRC) {
                    this.c("Shift distance of type \"" + asn7 + "\" is not allowed", lz_12.aP());
                }
                if (asn5 == asn.cRC) {
                    this.b(lz_12, -120);
                }
                this.b(lz_12, asn6 == asn.cRC ? n5 + 1 : n5);
                object = asn6;
            } while (iterator.hasNext());
            return object;
        }
        throw new aHY("Unexpected operator \"" + string + "\"");
    }

    private asn a(lz_1 lz_12, asn asn2, jy_2 object, Iterator iterator) {
        Object object2;
        Object object3;
        boolean bl2;
        if (asn2 != null) {
            this.a(lz_12, asn2);
            bl2 = true;
        } else {
            bl2 = false;
        }
        ArrayList<zv> arrayList = new ArrayList<zv>();
        do {
            Object object4;
            if ((object3 = this.j((jy_2)object)) == null) {
                object2 = object;
                arrayList.add(new aol_0(this, lz_12, (jy_2)object2));
                object = iterator.hasNext() ? (jy_2)iterator.next() : null;
                continue;
            }
            if (iterator.hasNext()) {
                object = (jy_2)iterator.next();
                object2 = this.j((jy_2)object);
                if (object2 != null) {
                    StringBuffer stringBuffer = new StringBuffer(object3.toString()).append(object2);
                    while (true) {
                        if (!iterator.hasNext()) {
                            object = null;
                            break;
                        }
                        object = (jy_2)iterator.next();
                        object4 = this.j((jy_2)object);
                        if (object4 == null) break;
                        stringBuffer.append(object4);
                    }
                    object3 = stringBuffer.toString();
                }
            } else {
                object = null;
            }
            object2 = zh_2.dg(object3.toString());
            for (int j = 0; j < ((String[])object2).length; ++j) {
                object4 = object2[j];
                arrayList.add(new aoe_0(this, lz_12, (String)object4));
            }
        } while (object != null);
        if (arrayList.size() <= (bl2 ? 2 : 3)) {
            object3 = arrayList.iterator();
            while (object3.hasNext()) {
                object2 = (zv)object3.next();
                object2.Gs();
                if (bl2) {
                    this.b(lz_12, -74);
                    this.c("Ljava/lang/String;", "concat", "(Ljava/lang/String;)Ljava/lang/String;");
                    continue;
                }
                bl2 = true;
            }
            return this.avk.eoR;
        }
        object3 = arrayList.iterator();
        Object object5 = object2 = this.aEG ? "Ljava/lang/StringBuilder;" : "Ljava/lang/StringBuffer;";
        if (bl2) {
            this.b(lz_12, -69);
            this.dj((String)object2);
            this.b(lz_12, 90);
            this.b(lz_12, 95);
        } else {
            this.b(lz_12, -69);
            this.dj((String)object2);
            this.b(lz_12, 89);
            ((zv)object3.next()).Gs();
        }
        this.b(lz_12, -73);
        this.c((String)object2, "<init>", "(Ljava/lang/String;)V");
        while (object3.hasNext()) {
            ((zv)object3.next()).Gs();
            this.b(lz_12, -74);
            this.c((String)object2, "append", "(Ljava/lang/String;)" + (String)object2);
        }
        this.b(lz_12, -74);
        this.c((String)object2, "toString", "()Ljava/lang/String;");
        return this.avk.eoR;
    }

    private void a(lz_1 lz_12, asn asn2) {
        this.b(lz_12, -72);
        this.c("Ljava/lang/String;", "valueOf", "(" + (asn2 == asn.cRE || asn2 == asn.cRy || asn2 == asn.cRC || asn2 == asn.cRA || asn2 == asn.cRz ? asn2.getDescriptor() : (asn2 == asn.cRx || asn2 == asn.cRD || asn2 == asn.cRB ? "I" : "Ljava/lang/Object;")) + ")" + "Ljava/lang/String;");
    }

    private void a(lz_1 lz_12, aim_2 aim_22, jy_2 jy_22, asn asn2, jy_2[] jy_2Array) {
        Object[] objectArray;
        adi_0 adi_02;
        block19: {
            el_1 el_12;
            aim_2 aim_23;
            Object object;
            block18: {
                asn asn3;
                ms_2[] ms_2Array = asn2.aFi();
                if (ms_2Array.length == 0) {
                    throw new aHY("SNO: Target class \"" + asn2.getDescriptor() + "\" has no constructors");
                }
                adi_02 = (adi_0)this.a(lz_12, ms_2Array, jy_2Array, aim_22);
                asn[] asnArray = adi_02.iz();
                for (int j = 0; j < asnArray.length; ++j) {
                    this.a(lz_12, asnArray[j], aim_22);
                }
                if (jy_22 != null && (asn3 = asn2.aFp()) != null && !asn3.g((asn)(object = this.i(jy_22)))) {
                    this.c("Type of enclosing instance (\"" + object + "\") is not assignable to \"" + asn3 + "\"", lz_12.aP());
                }
                objectArray = asn2.aCO();
                aim_23 = aim_22;
                while (!(aim_23 instanceof aR)) {
                    aim_23 = aim_23.Dw();
                }
                object = (aR)aim_23;
                el_12 = object.bV();
                if (el_12 instanceof azV) break block18;
                if (objectArray.length > 0) {
                    throw new aHY("SNO: Target class has synthetic fields");
                }
                break block19;
            }
            aim_23 = (azV)el_12;
            for (int j = 0; j < objectArray.length; ++j) {
                fb_2 fb_22;
                block17: {
                    Object object2;
                    lz_1 lz_13;
                    Object object3;
                    Object object4 = objectArray[j];
                    if (!((jz_0)object4).getName().startsWith("val$")) continue;
                    jz_0 jz_02 = (jz_0)((azV)aim_23).doR.get(((jz_0)object4).getName());
                    if (jz_02 != null) {
                        if (object instanceof kc_0) {
                            this.a(lz_12, this.c((el_1)aim_23), 0);
                            this.b(lz_12, -76);
                            this.b(this.c((el_1)aim_23).getDescriptor(), ((jz_0)object4).getName(), ((jz_0)object4).getDescriptor());
                            continue;
                        }
                        if (object instanceof acc_0) {
                            object3 = (acc_0)object;
                            fb_22 = (fb_2)((acc_0)object3).cky.get(((jz_0)object4).getName());
                            if (fb_22 == null) {
                                this.c("Compiler limitation: Constructor cannot access local variable \"" + ((jz_0)object4).getName().substring(4) + "\" declared in an enclosing block because none of the methods accesses it. " + "As a workaround, declare a dummy method that accesses the local variable.", lz_12.aP());
                                this.b(lz_12, 1);
                                continue;
                            }
                            this.a(lz_12, fb_22);
                            continue;
                        }
                        this.c("Compiler limitation: Initializers cannot access local variables declared in an enclosing block.", lz_12.aP());
                        this.b(lz_12, 1);
                        continue;
                    }
                    object3 = ((jz_0)object4).getName().substring(4);
                    aim_2 aim_24 = aim_22;
                    while (aim_24 instanceof TK) {
                        block22: {
                            TK tK;
                            block21: {
                                aim_2 aim_25;
                                block20: {
                                    lz_13 = (TK)aim_24;
                                    aim_25 = lz_13.Dw();
                                    if (!(aim_25 instanceof lo_2)) break block20;
                                    object2 = ((lo_2)aim_25).bsj;
                                    break block21;
                                }
                                if (!(aim_25 instanceof xN)) break block22;
                                object2 = ((xN)aim_25).azB;
                            }
                            Iterator iterator = object2.iterator();
                            while ((tK = (TK)iterator.next()) != lz_13) {
                                if (!(tK instanceof lG)) continue;
                                lG lG2 = (lG)tK;
                                jk_2[] jk_2Array = lG2.HE;
                                for (int i2 = 0; i2 < jk_2Array.length; ++i2) {
                                    if (!jk_2Array[i2].name.equals(object3)) continue;
                                    fb_22 = this.a(lG2, jk_2Array[i2]);
                                    break block17;
                                }
                            }
                        }
                        aim_24 = aim_24.Dw();
                    }
                    while (!(aim_24 instanceof xN)) {
                        aim_24 = aim_24.Dw();
                    }
                    lz_13 = (xN)aim_24;
                    for (int i3 = 0; i3 < ((xN)lz_13).azz.length; ++i3) {
                        object2 = ((xN)lz_13).azz[i3];
                        if (!((anb_1)object2).name.equals(object3)) continue;
                        fb_22 = this.a((anb_1)object2);
                        break block17;
                    }
                    throw new aHY("SNO: Synthetic field \"" + ((jz_0)object4).getName() + "\" neither maps a synthetic field of an enclosing instance nor a local variable");
                }
                this.a(lz_12, fb_22);
            }
        }
        objectArray = adi_02.iy();
        for (int j = 0; j < jy_2Array.length; ++j) {
            this.a(lz_12, this.i(jy_2Array[j]), (asn)objectArray[j], this.j(jy_2Array[j]));
        }
        this.b(lz_12, -73);
        this.c(asn2.getDescriptor(), "<init>", adi_02.getDescriptor());
    }

    jz_0[] d(aBi aBi2) {
        jz_0[] jz_0Array = new jz_0[aBi2.HE.length];
        for (int j = 0; j < jz_0Array.length; ++j) {
            jk_2 jk_22 = aBi2.HE[j];
            asn asn2 = this.c(aBi2.bV());
            asn2.getClass();
            jz_0Array[j] = new aod(this, asn2, aBi2, jk_22);
        }
        return jz_0Array;
    }

    fd_2 a(aBi aBi2, jk_2 jk_22) {
        if (jk_22.BO == null) {
            return null;
        }
        if ((aBi2.HC & 8) != 0 && (aBi2.HC & 0x10) != 0 && jk_22.BO instanceof jy_2 && this.j((jy_2)jk_22.BO) != null) {
            return null;
        }
        return jk_22.BO;
    }

    private alb_0 h(anM anM2) {
        if (anM2.cJX == null) {
            anM2.cJX = this.a(anM2.aP(), anM2.oi(), anM2.rb, anM2.n);
        }
        return anM2.cJX;
    }

    private alb_0 a(lc_0 lc_02, aim_2 aim_22, String[] stringArray, int n2) {
        if (n2 == 1) {
            return this.a(lc_02, aim_22, stringArray[0]);
        }
        alb_0 alb_02 = this.a(lc_02, aim_22, stringArray, n2 - 1);
        String string = stringArray[n2 - 1];
        if (alb_02 instanceof lj_2) {
            String string2 = ((lj_2)alb_02).name + '.' + string;
            asn asn2 = this.a(lc_02, string2);
            if (asn2 != null) {
                return new vq_1(lc_02, asn2);
            }
            return new lj_2(lc_02, string2);
        }
        if (string.equals("length") && this.a(alb_02).isArray()) {
            auo auo2 = new auo(lc_02, this.e(alb_02));
            if (!(aim_22 instanceof TK)) {
                this.di("\".length\" only allowed in expression context");
                return auo2;
            }
            auo2.a((TK)aim_22);
            return auo2;
        }
        asn asn3 = this.a(alb_02);
        asn[] asnArray = this.a(asn3, string, lc_02);
        if (asnArray != null) {
            cb_1 cb_12 = new cb_1(lc_02, alb_02, (jz_0)asnArray);
            cb_12.a((TK)aim_22);
            return cb_12;
        }
        asnArray = asn3.aFo();
        for (int j = 0; j < asnArray.length; ++j) {
            asn asn4 = asnArray[j];
            String string3 = sA.toClassName(asn4.getDescriptor());
            if (!(string3 = string3.substring(string3.lastIndexOf(36) + 1)).equals(string)) continue;
            return new vq_1(lc_02, asn4);
        }
        this.c("\"" + string + "\" is neither a method, a field, nor a member class of \"" + asn3 + "\"", lc_02);
        return new aoj_0(this, lc_02, stringArray);
    }

    private asn a(lc_0 lc_02, String string) {
        asn asn2 = this.dh(string);
        if (asn2 != null) {
            return asn2;
        }
        try {
            return this.avk.lT(sA.cb(string));
        }
        catch (ClassNotFoundException classNotFoundException) {
            if (classNotFoundException.getException() instanceof ajy_2) {
                throw (ajy_2)classNotFoundException.getException();
            }
            throw new ajy_2(string, lc_02, classNotFoundException);
        }
    }

    private alb_0 a(lc_0 lc_02, aim_2 aim_22, String string) {
        asn asn2;
        Object object;
        Object object2;
        Object object3;
        TK tK = null;
        aR aR2 = null;
        DM dM = null;
        aim_2 aim_23 = aim_22;
        if (aim_23 instanceof TK) {
            tK = (TK)aim_23;
        }
        while ((aim_23 instanceof TK || aim_23 instanceof xp_1) && !(aim_23 instanceof aR)) {
            aim_23 = aim_23.Dw();
        }
        if (aim_23 instanceof aR) {
            aR2 = (aR)aim_23;
            aim_23 = aim_23.Dw();
        }
        if (aim_23 instanceof el_1) {
            dM = (DM)aim_23;
            aim_23 = aim_23.Dw();
        }
        while (!(aim_23 instanceof kh_1)) {
            aim_23 = aim_23.Dw();
        }
        kh_1 kh_12 = (kh_1)aim_23;
        aim_23 = aim_22;
        if (aim_23 instanceof TK) {
            object3 = (TK)aim_23;
            object2 = object3.cL(string);
            if (object2 != null) {
                aoa_1 aoa_12 = new aoa_1(lc_02, (fb_2)object2);
                aoa_12.a((TK)object3);
                return aoa_12;
            }
            aim_23 = aim_23.Dw();
        }
        while (aim_23 instanceof TK || aim_23 instanceof xp_1) {
            aim_23 = aim_23.Dw();
        }
        if (aim_23 instanceof xN) {
            aim_23 = aim_23.Dw();
        }
        if (aim_23 instanceof eb_0) {
            object3 = (eb_0)aim_23;
            if ((aim_23 = aim_23.Dw()) instanceof uy_1) {
                aim_23 = aim_23.Dw();
            }
            while (aim_23 instanceof TK) {
                object2 = ((TK)aim_23).cL(string);
                if (object2 != null) {
                    if (!((fb_2)object2).rB) {
                        this.di("Cannot access non-final local variable \"" + string + "\" from inner class");
                    }
                    asn asn3 = ((fb_2)object2).rC;
                    aji aji2 = new aji(this.c((el_1)object3), "val$" + string, asn3);
                    object3.a(aji2);
                    cb_1 cb_12 = new cb_1(lc_02, new xj_1(lc_02, new vq_1(lc_02, this.c((el_1)object3))), aji2);
                    cb_12.a((TK)aim_22);
                    return cb_12;
                }
                aim_23 = aim_23.Dw();
                while (aim_23 instanceof TK) {
                    aim_23 = aim_23.Dw();
                }
                if (!(aim_23 instanceof xN) || !((aim_23 = aim_23.Dw()) instanceof eb_0)) break;
                object3 = (eb_0)aim_23;
                aim_23 = aim_23.Dw();
            }
        }
        aim_23 = null;
        object3 = aim_22;
        while (!(object3 instanceof kh_1)) {
            if (object3 instanceof TK && aim_23 == null) {
                aim_23 = object3;
            }
            if (object3 instanceof el_1 && (object = this.a(asn2 = this.c((el_1)(object2 = (DM)object3)), string, lc_02)) != null) {
                if (object.isStatic()) {
                    this.a("IASF", "Implicit access to static field \"" + string + "\" of declaring class (better write \"" + object.ic() + '.' + object.getName() + "\")", lc_02);
                } else if (object.ic() == asn2) {
                    this.a("IANSF", "Implicit access to non-static field \"" + string + "\" of declaring class (better write \"this." + object.getName() + "\")", lc_02);
                } else {
                    this.a("IANSFEI", "Implicit access to non-static field \"" + string + "\" of enclosing instance (better write \"" + object.ic() + ".this." + object.getName() + "\")", lc_02);
                }
                vq_1 vq_12 = new vq_1(dM.aP(), asn2);
                alb_0 alb_02 = aR2.isStatic() ? vq_12 : (object.isStatic() ? vq_12 : new xj_1(lc_02, vq_12));
                cb_1 cb_13 = new cb_1(lc_02, alb_02, (jz_0)object);
                cb_13.a((TK)aim_23);
                return cb_13;
            }
            object3 = object3.Dw();
        }
        object3 = (List)this.aEN.get(string);
        if (object3 != null) {
            object2 = object3.iterator();
            while (object2.hasNext()) {
                asn2 = object2.next();
                if (!(asn2 instanceof jz_0)) continue;
                object = new cb_1(lc_02, new vq_1(lc_02, ((jz_0)((Object)asn2)).ic()), (jz_0)((Object)asn2));
                object.a((TK)aim_23);
                return object;
            }
        }
        object3 = null;
        object2 = this.aEO.iterator();
        while (object2.hasNext()) {
            asn2 = (asn)object2.next();
            object = asn2.jv(string);
            if (object == null || !this.a((ff_0)object, aim_23)) continue;
            if (object3 != null) {
                this.di("Ambiguous static field import: \"" + ((jz_0)object3).toString() + "\" vs. \"" + object.toString() + "\"");
            }
            object3 = object;
        }
        if (object3 != null) {
            if (!((jz_0)object3).isStatic()) {
                this.di("Cannot static-import non-static field");
            }
            object2 = new cb_1(lc_02, new vq_1(lc_02, ((jz_0)object3).ic()), (jz_0)object3);
            ((jy_2)object2).a((TK)aim_23);
            return object2;
        }
        if (string.equals("java")) {
            return new lj_2(lc_02, string);
        }
        object3 = this.a(aim_22, string);
        if (object3 != null) {
            return new vq_1(lc_02, this.c((el_1)object3));
        }
        if (dM != null && (object3 = this.b(this.c(dM), string, lc_02)) != null) {
            return new vq_1(lc_02, (asn)object3);
        }
        object3 = this.a(string, lc_02);
        if (object3 != null) {
            return new vq_1(lc_02, (asn)object3);
        }
        object3 = kh_12.eX(string);
        if (object3 != null) {
            return new vq_1(lc_02, this.c((DM)object3));
        }
        object3 = kh_12.bnk == null ? string : kh_12.bnk.doL + '.' + string;
        object2 = this.a(lc_02, (String)object3);
        if (object2 != null) {
            return new vq_1(lc_02, (asn)object2);
        }
        object3 = this.b(string, lc_02);
        if (object3 != null) {
            return new vq_1(lc_02, (asn)object3);
        }
        object3 = (List)this.aEN.get(string);
        if (object3 != null) {
            object2 = object3.iterator();
            while (object2.hasNext()) {
                asn2 = object2.next();
                if (!(asn2 instanceof asn)) continue;
                return new vq_1(null, asn2);
            }
        }
        object3 = null;
        object2 = this.aEO.iterator();
        while (object2.hasNext()) {
            asn2 = (asn)object2.next();
            object = asn2.aFo();
            for (int j = 0; j < ((asn[])object).length; ++j) {
                asn asn4 = object[j];
                if (!this.a(asn4, (aim_2)tK) || !asn4.getDescriptor().endsWith('$' + string + ';')) continue;
                if (object3 != null) {
                    this.di("Ambiguous static type import: \"" + ((asn)object3).toString() + "\" vs. \"" + asn4.toString() + "\"");
                }
                object3 = asn4;
            }
        }
        if (object3 != null) {
            return new vq_1(null, (asn)object3);
        }
        return new lj_2(lc_02, string);
    }

    private void e(aai_2 aai_22) {
        if (aai_22.aGv != null) {
            return;
        }
        asn asn2 = this.a(aai_22.aKA);
        if (aai_22.fieldName.equals("length") && asn2.isArray()) {
            aai_22.aGv = new auo(aai_22.aP(), this.e(aai_22.aKA));
        } else {
            jz_0 jz_02 = this.a(asn2, aai_22.fieldName, aai_22.aP());
            if (jz_02 == null) {
                this.c("\"" + this.a(aai_22.aKA).toString() + "\" has no field \"" + aai_22.fieldName + "\"", aai_22.aP());
                aai_22.aGv = new aoh_0(this, aai_22.aP());
                return;
            }
            aai_22.aGv = new cb_1(aai_22.aP(), aai_22.aKA, jz_02);
        }
        aai_22.aGv.a(aai_22.oi());
    }

    private void e(GT gT) {
        if (gT.aGv != null) {
            return;
        }
        Object object = new aLs(gT.aP());
        ((jy_2)object).a(gT.oi());
        asn asn2 = gT.bcv != null ? this.a(gT.bcv) : this.a((alb_0)object);
        agz_2 agz_22 = new agz_2(gT.aP(), new vq_1(gT.aP(), asn2.aFq()), (jy_2)object);
        object = this.a(this.a((alb_0)agz_22), gT.fieldName, gT.aP());
        if (object == null) {
            this.c("Class has no field \"" + gT.fieldName + "\"", gT.aP());
            gT.aGv = new anz_0(this, gT.aP());
            return;
        }
        gT.aGv = new cb_1(gT.aP(), agz_22, (jz_0)object);
        gT.aGv.a(gT.oi());
    }

    public ff_2 c(La la) {
        ff_2 ff_22;
        block11: {
            Object object;
            Object object2;
            Iterator iterator;
            block14: {
                block13: {
                    block12: {
                        if (la.bpM != null) break block12;
                        iterator = la.oi();
                        while (!(iterator instanceof kh_1)) {
                            if (!(iterator instanceof el_1) || (ff_22 = this.a(this.c((el_1)(object2 = (el_1)((Object)iterator))), la)) == null) {
                                iterator = iterator.Dw();
                                continue;
                            }
                            break block11;
                        }
                        break block13;
                    }
                    ff_22 = this.a(this.a(la.bpM), la);
                    if (ff_22 != null) break block11;
                }
                if ((iterator = (List)this.aEN.get(la.methodName)) == null) break block14;
                ff_22 = null;
                object2 = iterator.iterator();
                while (object2.hasNext()) {
                    asn asn2;
                    ff_2 ff_23;
                    object = object2.next();
                    if (!(object instanceof ff_2) || (ff_23 = this.a(asn2 = ((ff_2)object).ic(), la)) == null) continue;
                    if (ff_22 != null && ff_22 != ff_23) {
                        this.di("Ambiguous static method import: \"" + ff_22.toString() + "\" vs. \"" + ff_23.toString() + "\"");
                    }
                    ff_22 = ff_23;
                }
                if (ff_22 != null) break block11;
            }
            ff_22 = null;
            iterator = this.aEO.iterator();
            while (iterator.hasNext()) {
                object2 = (asn)iterator.next();
                object = this.a((asn)object2, la);
                if (object == null) continue;
                if (ff_22 != null) {
                    this.di("Ambiguous static method import: \"" + ff_22.toString() + "\" vs. \"" + ((ff_2)object).toString() + "\"");
                }
                ff_22 = object;
            }
            if (ff_22 == null) {
                this.c("A method named \"" + la.methodName + "\" is not declared in any enclosing class nor any supertype, nor through a static import", la.aP());
                return this.a(this.avk.eoQ, la.methodName, la.avU);
            }
        }
        this.a(la, ff_22);
        return ff_22;
    }

    private ff_2 a(asn asn2, afN afN2) {
        ArrayList<ff_2> arrayList = new ArrayList<ff_2>();
        this.a(asn2, afN2.methodName, arrayList);
        if (asn2.isInterface()) {
            ff_2[] ff_2Array = this.avk.eoQ.ju(afN2.methodName);
            for (int j = 0; j < ff_2Array.length; ++j) {
                ff_2 ff_22 = ff_2Array[j];
                if (ff_22.isStatic() || ff_22.ib() != amf.cGt) continue;
                arrayList.add(ff_22);
            }
        }
        if (arrayList.size() == 0) {
            return null;
        }
        return (ff_2)this.a((lz_1)afN2, arrayList.toArray(new ff_2[arrayList.size()]), afN2.avU, afN2.oi());
    }

    private ff_2 a(asn asn2, String string, jy_2[] jy_2Array) {
        asn[] asnArray = new asn[jy_2Array.length];
        for (int j = 0; j < jy_2Array.length; ++j) {
            asnArray[j] = this.a(jy_2Array[j]);
        }
        asn asn3 = asn2;
        asn3.getClass();
        return new any_0(this, asn3, string, asnArray);
    }

    public void a(asn asn2, String string, List list) {
        Object object = asn2.ju(string);
        for (int j = 0; j < ((ff_2[])object).length; ++j) {
            list.add(object[j]);
        }
        object = asn2.aFq();
        if (object != null) {
            this.a((asn)object, string, list);
        }
        asn[] asnArray = asn2.aFr();
        for (int j = 0; j < asnArray.length; ++j) {
            this.a(asnArray[j], string, list);
        }
    }

    public ff_2 c(ajs_2 ajs_22) {
        Object object;
        Object object2 = ajs_22.oi();
        while (true) {
            if (object2 instanceof xN) {
                object = (xN)object2;
                if ((((xN)object).HC & 8) != 0) {
                    this.c("Superclass method cannot be invoked in static context", ajs_22.aP());
                }
            }
            if (object2 instanceof azV) break;
            object2 = object2.Dw();
        }
        azV azV2 = (azV)object2;
        object2 = this.c(azV2).aFq();
        object = this.a((asn)object2, ajs_22);
        if (object == null) {
            this.c("Class \"" + object2 + "\" has no method named \"" + ajs_22.methodName + "\"", ajs_22.aP());
            return this.a((asn)object2, ajs_22.methodName, ajs_22.avU);
        }
        this.a(ajs_22, (ff_2)object);
        return object;
    }

    private ms_2 a(lz_1 lz_12, ms_2[] ms_2Array, jy_2[] jy_2Array, aim_2 aim_22) {
        int n2;
        asn[] asnArray = new asn[jy_2Array.length];
        for (int j = 0; j < jy_2Array.length; ++j) {
            asnArray[j] = this.a(jy_2Array[j]);
        }
        ms_2 ms_22 = this.a(lz_12, ms_2Array, asnArray, false, aim_22);
        if (ms_22 != null) {
            return ms_22;
        }
        ms_22 = this.a(lz_12, ms_2Array, asnArray, true, aim_22);
        if (ms_22 != null) {
            return ms_22;
        }
        StringBuffer stringBuffer = new StringBuffer("No applicable constructor/method found for ");
        if (asnArray.length == 0) {
            stringBuffer.append("zero actual parameters");
        } else {
            stringBuffer.append("actual parameters \"").append(asnArray[0]);
            for (n2 = 1; n2 < asnArray.length; ++n2) {
                stringBuffer.append(", ").append(asnArray[n2]);
            }
            stringBuffer.append("\"");
        }
        stringBuffer.append("; candidates are: ").append('\"' + ms_2Array[0].toString() + '\"');
        for (n2 = 1; n2 < ms_2Array.length; ++n2) {
            stringBuffer.append(", ").append('\"' + ms_2Array[n2].toString() + '\"');
        }
        this.c(stringBuffer.toString(), lz_12.aP());
        if (ms_2Array[0] instanceof adi_0) {
            asn asn2 = ms_2Array[0].ic();
            asn2.getClass();
            return new aoc(this, asn2, asnArray);
        }
        if (ms_2Array[0] instanceof ff_2) {
            asn asn3 = ms_2Array[0].ic();
            asn3.getClass();
            return new aob_0(this, asn3, ms_2Array, asnArray);
        }
        return ms_2Array[0];
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public ms_2 a(lz_1 lz_12, ms_2[] ms_2Array, asn[] asnArray, boolean bl2, aim_2 aim_22) {
        int n2;
        ArrayList<Object> arrayList;
        block31: {
            int n3;
            Object object;
            ArrayList<ms_2> arrayList2 = new ArrayList<ms_2>();
            block0: for (int j = 0; j < ms_2Array.length; ++j) {
                ms_2 ms_22 = ms_2Array[j];
                object = ms_22.iy();
                if (((asn[])object).length != asnArray.length || !this.a((ff_0)ms_22, aim_22)) continue;
                for (n3 = 0; n3 < asnArray.length; ++n3) {
                    if (!this.a(asnArray[n3], object[n3], bl2)) continue block0;
                }
                arrayList2.add(ms_22);
            }
            if (arrayList2.size() == 0) {
                return null;
            }
            if (arrayList2.size() == 1) {
                return (ms_2)arrayList2.get(0);
            }
            arrayList = new ArrayList<Object>();
            for (int j = 0; j < arrayList2.size(); ++j) {
                object = (ms_2)arrayList2.get(j);
                n3 = 0;
                int n4 = 0;
                for (int i2 = 0; i2 < arrayList.size(); ++i2) {
                    ms_2 ms_23 = (ms_2)arrayList.get(i2);
                    if (((ms_2)object).a(ms_23)) {
                        ++n3;
                        continue;
                    }
                    if (!((ms_2)object).b(ms_23)) continue;
                    ++n4;
                }
                if (n3 == arrayList.size()) {
                    arrayList.clear();
                    arrayList.add(object);
                    continue;
                }
                if (n4 >= arrayList.size()) continue;
                arrayList.add(object);
            }
            if (arrayList.size() == 1) {
                return (ms_2)arrayList.get(0);
            }
            if (arrayList.size() > 1 && ms_2Array[0] instanceof ff_2) {
                int n5;
                asn[][] asnArrayArray = null;
                object = arrayList.iterator();
                Object object2 = (asn[][])object.next();
                Object object3 = ((ms_2)object2).iy();
                block4: while (true) {
                    asn[] asnArray2;
                    if (!((ff_2)object2).isAbstract()) {
                        if (asnArrayArray == null) {
                            asnArrayArray = object2;
                        } else {
                            asn asn2;
                            asnArray2 = ((ms_2)object2).ic();
                            if (asnArray2 == (asn2 = asnArrayArray.ic())) {
                                if (((ff_2)object2).ix() == asnArrayArray.ix()) {
                                    throw new aHY("Two non-abstract methods '" + object2 + "' have the same parameter types, " + "declaring type and return type");
                                }
                                if (!((ff_2)object2).ix().g(asnArrayArray.ix())) {
                                    if (!asnArrayArray.ix().g(((ff_2)object2).ix())) throw new aHY("Incompatible return types");
                                    asnArrayArray = object2;
                                }
                            } else if (!asnArray2.g(asn2)) {
                                if (!asn2.g((asn)asnArray2)) throw new aHY("SNO: Types declaring '" + asnArrayArray + "' are not assignable");
                                asnArrayArray = object2;
                            }
                        }
                    }
                    if (!object.hasNext()) break;
                    object2 = (ff_2)object.next();
                    asnArray2 = ((ms_2)object2).iy();
                    int n6 = 0;
                    while (true) {
                        if (n6 >= asnArray2.length) continue block4;
                        if (asnArray2[n6] == object3[n6]) {
                            ++n6;
                            continue;
                        }
                        break block31;
                        break;
                    }
                    break;
                }
                if (asnArrayArray != null) {
                    return asnArrayArray;
                }
                object = new HashSet();
                object2 = new asn[arrayList.size()][];
                object3 = arrayList.iterator();
                for (n5 = 0; n5 < ((asn[][])object2).length; ++n5) {
                    object2[n5] = ((ff_2)object3.next()).iz();
                }
                for (n5 = 0; n5 < ((asn[][])object2).length; ++n5) {
                    block8: for (int j = 0; j < object2[n5].length; ++j) {
                        asn asn3 = object2[n5][j];
                        block9: for (int i3 = 0; i3 < ((Object)object2).length; ++i3) {
                            if (i3 == n5) continue;
                            for (int i4 = 0; i4 < ((Object)object2[i3]).length; ++i4) {
                                Object object4 = object2[i3][i4];
                                if (((asn)object4).g(asn3)) continue block9;
                            }
                            continue block8;
                        }
                        object.add(asn3);
                    }
                }
                object2 = (ff_2)arrayList.get(0);
                object3 = object.toArray(new asn[object.size()]);
                asn asn4 = ((ms_2)object2).ic();
                asn4.getClass();
                return new anu_1(this, asn4, (ff_2)object2, (asn[])object3);
            }
        }
        StringBuffer stringBuffer = new StringBuffer("Invocation of constructor/method with actual parameter type(s) \"");
        for (n2 = 0; n2 < asnArray.length; ++n2) {
            if (n2 > 0) {
                stringBuffer.append(", ");
            }
            stringBuffer.append(sA.toString(asnArray[n2].getDescriptor()));
        }
        stringBuffer.append("\" is ambiguous: ");
        for (n2 = 0; n2 < arrayList.size(); ++n2) {
            if (n2 > 0) {
                stringBuffer.append(" vs. ");
            }
            stringBuffer.append("\"" + arrayList.get(n2) + "\"");
        }
        this.c(stringBuffer.toString(), lz_12.aP());
        return (ff_2)ms_2Array[0];
    }

    private boolean a(asn asn2, asn asn3, boolean bl2) {
        asn asn4;
        if (asn2 == asn3) {
            return true;
        }
        if (this.c(asn2, asn3)) {
            return true;
        }
        if (this.d(asn2, asn3)) {
            return true;
        }
        if (bl2 && (asn4 = this.b(asn2)) != null) {
            return this.a(asn4, asn3) || this.d(asn4, asn3);
        }
        if (bl2 && (asn4 = this.c(asn2)) != null) {
            return this.a(asn4, asn3) || this.c(asn4, asn3);
        }
        return false;
    }

    private void a(afN afN2, ff_2 ff_22) {
        asn[] asnArray = ff_22.iz();
        for (int j = 0; j < asnArray.length; ++j) {
            this.a((lz_1)afN2, asnArray[j], (aim_2)afN2.oi());
        }
    }

    private void a(lz_1 lz_12, asn asn2, aim_2 aim_22) {
        if (!this.avk.eoT.g(asn2)) {
            this.c("Thrown object of type \"" + asn2 + "\" is not assignable to \"Throwable\"", lz_12.aP());
        }
        if (this.avk.eoU.g(asn2) || this.avk.eoV.g(asn2)) {
            return;
        }
        while (true) {
            Object object;
            int n2;
            aj_1 aj_12;
            if (aim_22 instanceof aqt) {
                aj_12 = (aqt)aim_22;
                for (n2 = 0; n2 < aj_12.cOc.size(); ++n2) {
                    object = (xp_1)aj_12.cOc.get(n2);
                    asn asn3 = this.a(((xp_1)object).azD.HD);
                    if (!asn3.g(asn2)) continue;
                    return;
                }
            } else {
                if (aim_22 instanceof xN) {
                    aj_12 = (xN)aim_22;
                    for (n2 = 0; n2 < ((xN)aj_12).azA.length; ++n2) {
                        object = this.a(((xN)aj_12).azA[n2]);
                        if (!((asn)object).g(asn2)) continue;
                        return;
                    }
                    break;
                }
                if (aim_22 instanceof aR) break;
            }
            aim_22 = aim_22.Dw();
        }
        this.c("Thrown exception of type \"" + asn2 + "\" is neither caught by a \"try...catch\" block " + "nor declared in the \"throws\" clause of the declaring function", lz_12.aP());
    }

    private asn c(xj_1 xj_12) {
        if (xj_12.bXj == null) {
            xj_12.bXj = this.a(xj_12.bXg);
        }
        return xj_12.bXj;
    }

    fb_2 d(afa_1 afa_12) {
        if (!(afa_12.dET instanceof anM)) {
            return null;
        }
        anM anM2 = (anM)afa_12.dET;
        alb_0 alb_02 = this.h(anM2);
        if (!(alb_02 instanceof aoa_1)) {
            return null;
        }
        aoa_1 aoa_12 = (aoa_1)alb_02;
        fb_2 fb_22 = aoa_12.BP;
        if (fb_22.rB) {
            this.c("Must not increment or decrement \"final\" local variable", aoa_12.aP());
        }
        if (fb_22.rC == asn.cRx || fb_22.rC == asn.cRD || fb_22.rC == asn.cRB || fb_22.rC == asn.cRy) {
            return fb_22;
        }
        return null;
    }

    private asn c(el_1 el_12) {
        DM dM = (DM)el_12;
        if (dM.aOl == null) {
            dM.aOl = new aoe_1(this, dM, el_12);
        }
        return dM.aOl;
    }

    private void a(lz_1 lz_12, azV azV2, aR aR2, asn asn2) {
        int n2;
        Object object;
        Object object2;
        Object object3;
        int n3;
        List list;
        block8: {
            list = zh_2.d(azV2);
            if (aR2.isStatic()) {
                this.c("No current instance available in static context", lz_12.aP());
            }
            for (n3 = 0; n3 < list.size(); ++n3) {
                if (!asn2.g(this.c((DM)list.get(n3)))) {
                    continue;
                }
                break block8;
            }
            this.c("\"" + azV2 + "\" is not enclosed by \"" + asn2 + "\"", lz_12.aP());
        }
        if (aR2 instanceof acc_0) {
            if (n3 == 0) {
                this.b(lz_12, 42);
                return;
            }
            object3 = (acc_0)aR2;
            object2 = "this$" + (list.size() - 2);
            object = (fb_2)((acc_0)object3).cky.get(object2);
            if (object == null) {
                throw new aHY("SNO: Synthetic parameter \"" + (String)object2 + "\" not found");
            }
            this.a(lz_12, (fb_2)object);
            n2 = 1;
        } else {
            this.b(lz_12, 42);
            n2 = 0;
        }
        while (n2 < n3) {
            object3 = "this$" + (list.size() - n2 - 2);
            object2 = (eb_0)list.get(n2);
            object = this.c((DM)object2);
            el_1 el_12 = (el_1)list.get(n2 + 1);
            asn asn3 = this.c((DM)el_12);
            object2.a(new aji((asn)object, (String)object3, asn3));
            this.b(lz_12, -76);
            this.b(((asn)object).getDescriptor(), (String)object3, asn3.getDescriptor());
            ++n2;
        }
    }

    private static List d(el_1 el_12) {
        ArrayList<el_1> arrayList = new ArrayList<el_1>();
        el_1 el_13 = el_12;
        while (el_13 != null) {
            arrayList.add(el_13);
            el_13 = zh_2.e(el_13);
        }
        return arrayList;
    }

    static el_1 e(el_1 el_12) {
        if (el_12 instanceof ayp_0) {
            return null;
        }
        if (el_12 instanceof abh_1) {
            aim_2 aim_22 = el_12.Dw();
            while (!(aim_22 instanceof xN)) {
                aim_22 = aim_22.Dw();
            }
            if (aim_22 instanceof kc_0 && (((xN)aim_22).HC & 8) != 0) {
                return null;
            }
            while (!(aim_22 instanceof el_1)) {
                aim_22 = aim_22.Dw();
            }
            el_1 el_13 = (el_1)aim_22;
            return el_13 instanceof azV ? el_13 : null;
        }
        if (el_12 instanceof hg_2 && (((hg_2)el_12).hQ() & 8) != 0) {
            return null;
        }
        aim_2 aim_23 = el_12;
        while (!(aim_23 instanceof aR)) {
            if (aim_23 instanceof xa) {
                return null;
            }
            if (aim_23 instanceof kh_1) {
                return null;
            }
            aim_23 = aim_23.Dw();
        }
        if (((aR)aim_23).isStatic()) {
            return null;
        }
        return (DM)aim_23.Dw();
    }

    private asn c(aLs aLs2) {
        if (aLs2.asH == null) {
            aim_2 aim_22 = aLs2.oi();
            while (aim_22 instanceof akE || aim_22 instanceof xp_1) {
                aim_22 = aim_22.Dw();
            }
            if (aim_22 instanceof xN) {
                xN xN2 = (xN)aim_22;
                if ((xN2.HC & 8) != 0) {
                    this.c("No current instance available in static method", aLs2.aP());
                }
            }
            while (!(aim_22 instanceof el_1)) {
                aim_22 = aim_22.Dw();
            }
            if (!(aim_22 instanceof azV)) {
                this.c("Only methods of classes can have a current instance", aLs2.aP());
            }
            aLs2.asH = this.c((azV)aim_22);
        }
        return aLs2.asH;
    }

    private asn b(xN xN2) {
        if (xN2.azC == null) {
            xN2.azC = this.a(xN2.HD);
        }
        return xN2.azC;
    }

    adi_0 d(acc_0 acc_02) {
        if (acc_02.ckw != null) {
            return acc_02.ckw;
        }
        asn asn2 = this.c((DM)acc_02.bV());
        asn2.getClass();
        acc_02.ckw = new aod_0(this, asn2, acc_02);
        return acc_02.ckw;
    }

    public ff_2 b(kc_0 kc_02) {
        if (kc_02.boI != null) {
            return kc_02.boI;
        }
        asn asn2 = this.c((DM)kc_02.bV());
        asn2.getClass();
        kc_02.boI = new aob_1(this, asn2, kc_02);
        return kc_02.boI;
    }

    private ms_2 c(xN xN2) {
        if (xN2 instanceof acc_0) {
            return this.d((acc_0)xN2);
        }
        if (xN2 instanceof kc_0) {
            return this.b((kc_0)xN2);
        }
        throw new aHY("FunctionDeclarator is neither ConstructorDeclarator nor MethodDeclarator");
    }

    private asn a(String string, lc_0 lc_02) {
        Object[] objectArray = this.df(string);
        if (objectArray == null) {
            return null;
        }
        asn asn2 = this.f((String[])objectArray);
        if (asn2 == null) {
            this.c("Imported class \"" + jf_1.a(objectArray, ".") + "\" could not be loaded", lc_02);
            return this.avk.eoQ;
        }
        return asn2;
    }

    public String[] df(String string) {
        return (String[])this.aEL.get(string);
    }

    public asn b(String string, lc_0 lc_02) {
        asn asn2 = (asn)this.aEy.get(string);
        if (asn2 != null) {
            return asn2;
        }
        Iterator iterator = this.aEM.iterator();
        while (iterator.hasNext()) {
            String[] stringArray = (String[])iterator.next();
            String[] stringArray2 = zh_2.a(stringArray, string);
            asn asn3 = this.f(stringArray2);
            if (asn3 == null) continue;
            if (asn2 != null && asn2 != asn3) {
                this.c("Ambiguous class name: \"" + asn2 + "\" vs. \"" + asn3 + "\"", lc_02);
            }
            asn2 = asn3;
        }
        if (asn2 == null) {
            return null;
        }
        this.aEy.put(string, asn2);
        return asn2;
    }

    private void c(agx_0 agx_02) {
        asn asn2;
        asn asn3;
        lc_0 lc_02 = agx_02.aP();
        Object object = agx_02.oi();
        while (true) {
            if (object instanceof DM) break;
            object = object.Dw();
        }
        DM dM = (DM)object;
        object = new La(lc_02, new vq_1(lc_02, this.avk.eoS), "forName", new jy_2[]{new anM(lc_02, new String[]{"className"})});
        try {
            asn3 = this.avk.lT("Ljava/lang/ClassNotFoundException;");
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw new aHY("Loading class \"ClassNotFoundException\": " + classNotFoundException.getMessage());
        }
        if (asn3 == null) {
            throw new aHY("SNO: Cannot load \"ClassNotFoundException\"");
        }
        try {
            asn2 = this.avk.lT("Ljava/lang/NoClassDefFoundError;");
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw new aHY("Loading class \"NoClassDefFoundError\": " + classNotFoundException.getMessage());
        }
        if (asn2 == null) {
            throw new aHY("SNO: Cannot load \"NoClassFoundError\"");
        }
        lo_2 lo_22 = new lo_2(lc_02);
        lo_22.e(new v_0(lc_02, new Nl(lc_02, (jy_2)null, new vq_1(lc_02, asn2), new jy_2[]{new La(lc_02, new anM(lc_02, new String[]{"ex"}), "getMessage", new jy_2[0])})));
        ArrayList<xp_1> arrayList = new ArrayList<xp_1>();
        arrayList.add(new xp_1(lc_02, new anb_1(lc_02, true, new vq_1(lc_02, asn3), "ex"), lo_22));
        aqt aqt2 = new aqt(lc_02, new jr_1(lc_02, (jy_2)object), arrayList, null);
        ArrayList<aqt> arrayList2 = new ArrayList<aqt>();
        arrayList2.add(aqt2);
        anb_1 anb_12 = new anb_1(lc_02, false, new vq_1(lc_02, this.avk.eoR), "className");
        kc_0 kc_02 = new kc_0(lc_02, null, 8, new vq_1(lc_02, this.avk.eoS), "class$", new anb_1[]{anb_12}, new atu_0[0], arrayList2);
        dM.c(kc_02);
        dM.Mk();
    }

    private asn a(lz_1 lz_12, Object object) {
        if (object instanceof Integer || object instanceof Short || object instanceof Character || object instanceof Byte) {
            int n2;
            int n3 = n2 = object instanceof Character ? ((Character)object).charValue() : ((Number)object).intValue();
            if (n2 >= -1 && n2 <= 5) {
                this.b(lz_12, 3 + n2);
            } else if (n2 >= -128 && n2 <= 127) {
                this.b(lz_12, 16);
                this.writeByte((byte)n2);
            } else {
                this.a(lz_12, this.eB(n2));
            }
            return asn.cRB;
        }
        if (object instanceof Long) {
            long l2 = (Long)object;
            if (l2 >= 0L && l2 <= 1L) {
                this.b(lz_12, 9 + (int)l2);
            } else {
                this.b(lz_12, 20);
                this.aY(l2);
            }
            return asn.cRC;
        }
        if (object instanceof Float) {
            float f = ((Float)object).floatValue();
            if (Float.floatToIntBits(f) == Float.floatToIntBits(0.0f) || f == 1.0f || f == 2.0f) {
                this.b(lz_12, 11 + (int)f);
            } else {
                this.a(lz_12, this.ac(f));
            }
            return asn.cRA;
        }
        if (object instanceof Double) {
            double d = (Double)object;
            if (Double.doubleToLongBits(d) == Double.doubleToLongBits(0.0) || d == 1.0) {
                this.b(lz_12, 14 + (int)d);
            } else {
                this.b(lz_12, 20);
                this.m(d);
            }
            return asn.cRz;
        }
        if (object instanceof String) {
            String string = (String)object;
            String[] stringArray = zh_2.dg(string);
            this.a(lz_12, this.dk(stringArray[0]));
            for (int j = 1; j < stringArray.length; ++j) {
                this.a(lz_12, this.dk(stringArray[j]));
                this.b(lz_12, -74);
                this.c("Ljava/lang/String;", "concat", "(Ljava/lang/String;)Ljava/lang/String;");
            }
            return this.avk.eoR;
        }
        if (object instanceof Boolean) {
            this.b(lz_12, (Boolean)object != false ? 4 : 3);
            return asn.cRE;
        }
        if (object == jy_2.Dk) {
            this.b(lz_12, 1);
            return asn.cRw;
        }
        throw new aHY("Unknown literal type \"" + object.getClass().getName() + "\"");
    }

    private static String[] dg(String string) {
        if (string.length() < 21845) {
            return new String[]{string};
        }
        int n2 = string.length();
        int n3 = 0;
        int n4 = 0;
        ArrayList<String> arrayList = new ArrayList<String>();
        int n5 = 0;
        while (true) {
            char c;
            if (n5 == n2) {
                arrayList.add(string.substring(n4));
                break;
            }
            if (n3 >= 65532) {
                arrayList.add(string.substring(n4, n5));
                if (n5 + 21845 > n2) {
                    arrayList.add(string.substring(n5));
                    break;
                }
                n4 = n5;
                n3 = 0;
            }
            n3 = (c = string.charAt(n5)) >= '\u0001' && c <= '\u007f' ? ++n3 : (c > '\u07ff' ? (n3 += 3) : (n3 += 2));
            ++n5;
        }
        return arrayList.toArray(new String[arrayList.size()]);
    }

    private void a(lz_1 lz_12, short s) {
        if (0 <= s && s <= 255) {
            this.b(lz_12, 18);
            this.writeByte((byte)s);
        } else {
            this.b(lz_12, 19);
            this.writeShort(s);
        }
    }

    private void a(lz_1 lz_12, asn asn2, asn asn3, Object object) {
        if (this.b(asn2, asn3)) {
            return;
        }
        if (this.d(lz_12, asn2, asn3)) {
            return;
        }
        if (this.d(asn2, asn3)) {
            return;
        }
        asn asn4 = this.b(asn2);
        if (asn4 != null) {
            if (this.b(asn4, asn3)) {
                this.h(lz_12, asn2, asn4);
                return;
            }
            if (this.d(asn4, asn3)) {
                this.h(lz_12, asn2, asn4);
                return;
            }
        }
        if ((asn4 = this.c(asn2)) != null) {
            if (this.b(asn4, asn3)) {
                this.j(lz_12, asn2, asn4);
                return;
            }
            if (this.c(asn4, asn3)) {
                this.j(lz_12, asn2, asn4);
                this.d(lz_12, asn4, asn3);
                return;
            }
        }
        if (object != null && this.b(lz_12, object, asn3)) {
            return;
        }
        this.c("Assignment conversion not possible from type \"" + asn2 + "\" to type \"" + asn3 + "\"", lz_12.aP());
    }

    private Object a(lz_1 lz_12, Object object, asn asn2) {
        Object object2 = null;
        if (asn2 == asn.cRE) {
            if (object instanceof Boolean) {
                object2 = object;
            }
        } else if (asn2 == this.avk.eoR) {
            if (object instanceof String) {
                object2 = object;
            }
        } else if (asn2 == asn.cRx) {
            char c;
            if (object instanceof Byte) {
                object2 = object;
            } else if (object instanceof Short || object instanceof Integer) {
                int n2 = ((Number)object).intValue();
                if (n2 >= -128 && n2 <= 127) {
                    object2 = new Byte((byte)n2);
                }
            } else if (object instanceof Character && (c = ((Character)object).charValue()) >= '\uffffff80' && c <= '\u007f') {
                object2 = new Byte((byte)c);
            }
        } else if (asn2 == asn.cRD) {
            int n3;
            if (object instanceof Byte) {
                object2 = new Short(((Number)object).shortValue());
            } else if (object instanceof Short) {
                object2 = object;
            } else if (object instanceof Character) {
                char c = ((Character)object).charValue();
                if (c >= Short.MIN_VALUE && c <= Short.MAX_VALUE) {
                    object2 = new Short((short)c);
                }
            } else if (object instanceof Integer && (n3 = ((Integer)object).intValue()) >= Short.MIN_VALUE && n3 <= Short.MAX_VALUE) {
                object2 = new Short((short)n3);
            }
        } else if (asn2 == asn.cRy) {
            int n4;
            if (object instanceof Short) {
                object2 = object;
            } else if ((object instanceof Byte || object instanceof Short || object instanceof Integer) && (n4 = ((Number)object).intValue()) >= 0 && n4 <= 65535) {
                object2 = new Character((char)n4);
            }
        } else if (asn2 == asn.cRB) {
            if (object instanceof Integer) {
                object2 = object;
            } else if (object instanceof Byte || object instanceof Short) {
                object2 = new Integer(((Number)object).intValue());
            } else if (object instanceof Character) {
                object2 = new Integer(((Character)object).charValue());
            }
        } else if (asn2 == asn.cRC) {
            if (object instanceof Long) {
                object2 = object;
            } else if (object instanceof Byte || object instanceof Short || object instanceof Integer) {
                object2 = new Long(((Number)object).longValue());
            } else if (object instanceof Character) {
                object2 = new Long(((Character)object).charValue());
            }
        } else if (asn2 == asn.cRA) {
            if (object instanceof Float) {
                object2 = object;
            } else if (object instanceof Byte || object instanceof Short || object instanceof Integer || object instanceof Long) {
                object2 = new Float(((Number)object).floatValue());
            } else if (object instanceof Character) {
                object2 = new Float(((Character)object).charValue());
            }
        } else if (asn2 == asn.cRz) {
            if (object instanceof Double) {
                object2 = object;
            } else if (object instanceof Byte || object instanceof Short || object instanceof Integer || object instanceof Long || object instanceof Float) {
                object2 = new Double(((Number)object).doubleValue());
            } else if (object instanceof Character) {
                object2 = new Double(((Character)object).charValue());
            }
        } else if (object == jy_2.Dk && !asn2.isPrimitive()) {
            object2 = object;
        }
        if (object2 == null) {
            this.c("Cannot convert constant of type \"" + object.getClass().getName() + "\" to type \"" + asn2.toString() + "\"", lz_12.aP());
        }
        return object2;
    }

    private asn b(lz_1 lz_12, asn asn2) {
        asn2 = this.c(lz_12, asn2);
        asn asn3 = this.d(lz_12, asn2);
        this.b(lz_12, asn2, asn3);
        return asn3;
    }

    private void a(lz_1 lz_12, asn asn2, asn asn3) {
        asn asn4;
        asn asn5 = this.c(asn3);
        asn asn6 = asn4 = asn5 != null ? asn5 : asn3;
        if (!this.b(asn2, asn4) && !this.e(lz_12, asn2, asn4)) {
            throw new aHY("SNO: reverse unary numeric promotion failed");
        }
        if (asn5 != null) {
            this.h(lz_12, asn5, asn3);
        }
    }

    private asn c(lz_1 lz_12, asn asn2) {
        if (asn2.lT()) {
            return asn2;
        }
        asn asn3 = this.c(asn2);
        if (asn3 != null) {
            this.j(lz_12, asn2, asn3);
            return asn3;
        }
        this.c("Object of type \"" + asn2.toString() + "\" cannot be converted to a numeric type", lz_12.aP());
        return asn2;
    }

    private void b(lz_1 lz_12, asn asn2, asn asn3) {
        if (!this.b(asn2, asn3) && !this.d(lz_12, asn2, asn3)) {
            throw new aHY("SNO: Conversion failed");
        }
    }

    private asn d(lz_1 lz_12, asn asn2) {
        if (!asn2.lT()) {
            this.c("Unary numeric promotion not possible on non-numeric-primitive type \"" + asn2 + "\"", lz_12.aP());
        }
        return asn2 == asn.cRz ? asn.cRz : (asn2 == asn.cRA ? asn.cRA : (asn2 == asn.cRC ? asn.cRC : asn.cRB));
    }

    private asn a(lz_1 lz_12, asn asn2, aNc aNc2, asn asn3) {
        return this.a(lz_12, asn2, aNc2, asn3, this.aEB.aIx());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private asn a(lz_1 lz_12, asn asn2, aNc aNc2, asn asn3, aNc aNc3) {
        asn asn4 = this.c(asn2);
        asn asn5 = this.c(asn3);
        asn asn6 = this.c(lz_12, asn4 != null ? asn4 : asn2, asn5 != null ? asn5 : asn3);
        if (aNc2 != null) {
            this.aEB.a(aNc2);
            try {
                this.b(lz_12, this.c(lz_12, asn2), asn6);
            }
            finally {
                this.aEB.aIy();
            }
        }
        if (aNc3 != null) {
            this.aEB.a(aNc3);
            try {
                this.b(lz_12, this.c(lz_12, asn3), asn6);
            }
            finally {
                this.aEB.aIy();
            }
        }
        return asn6;
    }

    private asn c(lz_1 lz_12, asn asn2, asn asn3) {
        if (!asn2.lT() || !asn3.lT()) {
            this.c("Binary numeric promotion not possible on types \"" + asn2 + "\" and \"" + asn3 + "\"", lz_12.aP());
        }
        return asn2 == asn.cRz || asn3 == asn.cRz ? asn.cRz : (asn2 == asn.cRA || asn3 == asn.cRA ? asn.cRA : (asn2 == asn.cRC || asn3 == asn.cRC ? asn.cRC : asn.cRB));
    }

    private boolean a(asn asn2, asn asn3) {
        return asn2 == asn3;
    }

    private boolean b(asn asn2, asn asn3) {
        return asn2 == asn3;
    }

    private boolean c(asn asn2, asn asn3) {
        return aEz.get(asn2.getDescriptor() + asn3.getDescriptor()) != null;
    }

    private boolean d(lz_1 lz_12, asn asn2, asn asn3) {
        byte[] byArray = (byte[])aEz.get(asn2.getDescriptor() + asn3.getDescriptor());
        if (byArray != null) {
            this.a(lz_12, byArray);
            return true;
        }
        return false;
    }

    private static void a(Object[] objectArray, HashMap hashMap) {
        byte[] byArray = null;
        for (int j = 0; j < objectArray.length; ++j) {
            Object object = objectArray[j];
            if (object instanceof byte[]) {
                byArray = (byte[])object;
                continue;
            }
            hashMap.put(object, byArray);
        }
    }

    private boolean d(asn asn2, asn asn3) {
        if (asn3.isPrimitive() || asn2 == asn3) {
            return false;
        }
        return asn3.g(asn2);
    }

    private boolean e(asn asn2, asn asn3) {
        if (asn3.isPrimitive() || asn2 == asn3) {
            return false;
        }
        return asn3.g(asn2);
    }

    private boolean f(asn asn2, asn asn3) {
        return aEA.containsKey(asn2.getDescriptor() + asn3.getDescriptor());
    }

    private boolean e(lz_1 lz_12, asn asn2, asn asn3) {
        byte[] byArray = (byte[])aEA.get(asn2.getDescriptor() + asn3.getDescriptor());
        if (byArray != null) {
            this.a(lz_12, byArray);
            return true;
        }
        return false;
    }

    private boolean b(lz_1 lz_12, Object object, asn asn2) {
        int n2;
        if (object instanceof Byte) {
            n2 = ((Byte)object).byteValue();
        } else if (object instanceof Short) {
            n2 = ((Short)object).shortValue();
        } else if (object instanceof Integer) {
            n2 = (Integer)object;
        } else if (object instanceof Character) {
            n2 = ((Character)object).charValue();
        } else {
            return false;
        }
        if (asn2 == asn.cRx) {
            return n2 >= -128 && n2 <= 127;
        }
        if (asn2 == asn.cRD) {
            return n2 >= Short.MIN_VALUE && n2 <= Short.MAX_VALUE;
        }
        if (asn2 == asn.cRy) {
            return n2 >= 0 && n2 <= 65535;
        }
        apm_0 apm_02 = this.avk;
        if (asn2 == apm_02.cRx && n2 >= -128 && n2 <= 127) {
            this.h(lz_12, asn.cRx, asn2);
            return true;
        }
        if (asn2 == apm_02.cRD && n2 >= Short.MIN_VALUE && n2 <= Short.MAX_VALUE) {
            this.h(lz_12, asn.cRD, asn2);
            return true;
        }
        if (asn2 == apm_02.eoY && n2 >= 0 && n2 <= 65535) {
            this.h(lz_12, asn.cRy, asn2);
            return true;
        }
        return false;
    }

    private boolean g(asn asn2, asn asn3) {
        asn asn4;
        asn asn5;
        if (asn2.isPrimitive()) {
            return false;
        }
        if (asn2 == asn3) {
            return false;
        }
        if (asn2.g(asn3)) {
            return true;
        }
        if (asn3.isInterface() && !asn2.isFinal() && !asn3.g(asn2)) {
            return true;
        }
        if (asn2 == this.avk.eoQ && asn3.isArray()) {
            return true;
        }
        if (asn2 == this.avk.eoQ && asn3.isInterface()) {
            return true;
        }
        if (asn2.isInterface() && !asn3.isFinal()) {
            return true;
        }
        if (asn2.isInterface() && asn3.isFinal() && asn2.g(asn3)) {
            return true;
        }
        if (asn2.isInterface() && asn3.isInterface() && !asn3.g(asn2)) {
            return true;
        }
        return asn2.isArray() && asn3.isArray() && (this.f(asn5 = asn2.aFs(), asn4 = asn3.aFs()) || this.g(asn5, asn4));
    }

    private boolean f(lz_1 lz_12, asn asn2, asn asn3) {
        if (!this.g(asn2, asn3)) {
            return false;
        }
        this.b(lz_12, -64);
        this.dj(asn3.getDescriptor());
        return true;
    }

    private asn b(asn asn2) {
        apm_0 apm_02 = this.avk;
        if (asn2 == asn.cRE) {
            return apm_02.cRE;
        }
        if (asn2 == asn.cRx) {
            return apm_02.cRx;
        }
        if (asn2 == asn.cRy) {
            return apm_02.eoY;
        }
        if (asn2 == asn.cRD) {
            return apm_02.cRD;
        }
        if (asn2 == asn.cRB) {
            return apm_02.eoZ;
        }
        if (asn2 == asn.cRC) {
            return apm_02.cRC;
        }
        if (asn2 == asn.cRA) {
            return apm_02.cRA;
        }
        if (asn2 == asn.cRz) {
            return apm_02.cRz;
        }
        return null;
    }

    private boolean g(lz_1 lz_12, asn asn2, asn asn3) {
        if (this.b(asn2) == asn3) {
            this.h(lz_12, asn2, asn3);
            return true;
        }
        return false;
    }

    private void h(lz_1 lz_12, asn asn2, asn asn3) {
        if (asn3.a("valueOf", new asn[]{asn2})) {
            this.b(lz_12, -72);
            this.c(asn3.getDescriptor(), "valueOf", '(' + asn2.getDescriptor() + ')' + asn3.getDescriptor());
            return;
        }
        this.b(lz_12, -69);
        this.dj(asn3.getDescriptor());
        if (sA.ca(asn2.getDescriptor())) {
            this.b(lz_12, 91);
            this.b(lz_12, 91);
            this.b(lz_12, 87);
        } else {
            this.b(lz_12, 90);
            this.b(lz_12, 95);
        }
        this.b(lz_12, -73);
        this.c(asn3.getDescriptor(), "<init>", '(' + asn2.getDescriptor() + ')' + "V");
    }

    private asn c(asn asn2) {
        apm_0 apm_02 = this.avk;
        if (asn2 == apm_02.cRE) {
            return asn.cRE;
        }
        if (asn2 == apm_02.cRx) {
            return asn.cRx;
        }
        if (asn2 == apm_02.eoY) {
            return asn.cRy;
        }
        if (asn2 == apm_02.cRD) {
            return asn.cRD;
        }
        if (asn2 == apm_02.eoZ) {
            return asn.cRB;
        }
        if (asn2 == apm_02.cRC) {
            return asn.cRC;
        }
        if (asn2 == apm_02.cRA) {
            return asn.cRA;
        }
        if (asn2 == apm_02.cRz) {
            return asn.cRz;
        }
        return null;
    }

    private boolean i(lz_1 lz_12, asn asn2, asn asn3) {
        if (this.c(asn2) == asn3) {
            this.j(lz_12, asn2, asn3);
            return true;
        }
        return false;
    }

    private void j(lz_1 lz_12, asn asn2, asn asn3) {
        this.b(lz_12, -74);
        this.c(asn2.getDescriptor(), asn3.toString() + "Value", "()" + asn3.getDescriptor());
    }

    private asn f(String[] stringArray) {
        int[] nArray = new int[stringArray.length - 1];
        StringBuffer stringBuffer = new StringBuffer("L");
        int n2 = 0;
        while (true) {
            stringBuffer.append(stringArray[n2]);
            if (n2 == stringArray.length - 1) break;
            nArray[n2] = stringBuffer.length();
            stringBuffer.append('/');
            ++n2;
        }
        stringBuffer.append(';');
        n2 = nArray.length - 1;
        while (true) {
            asn asn2;
            try {
                asn2 = this.avk.lT(stringBuffer.toString());
            }
            catch (ClassNotFoundException classNotFoundException) {
                if (classNotFoundException.getException() instanceof ajy_2) {
                    throw (ajy_2)classNotFoundException.getException();
                }
                throw new ajy_2(stringBuffer.toString(), null, classNotFoundException);
            }
            if (asn2 != null) {
                return asn2;
            }
            if (n2 < 0) break;
            stringBuffer.setCharAt(nArray[n2], '$');
            --n2;
        }
        return null;
    }

    private asn a(lz_1 lz_12, fb_2 fb_22) {
        this.a(lz_12, fb_22.rC, (int)fb_22.jl());
        return fb_22.rC;
    }

    private void a(lz_1 lz_12, asn asn2, int n2) {
        if (n2 <= 3) {
            this.b(lz_12, 26 + 4 * this.e(asn2) + n2);
        } else if (n2 <= 255) {
            this.b(lz_12, 21 + this.e(asn2));
            this.writeByte(n2);
        } else {
            this.b(lz_12, -60);
            this.b(lz_12, 21 + this.e(asn2));
            this.writeShort(n2);
        }
    }

    private void a(lz_1 lz_12, asn asn2, fb_2 fb_22) {
        this.a(lz_12, fb_22.rC, fb_22.jl());
    }

    private void a(lz_1 lz_12, asn asn2, short s) {
        if (s <= 3) {
            this.b(lz_12, 59 + 4 * this.e(asn2) + s);
        } else if (s <= 255) {
            this.b(lz_12, 54 + this.e(asn2));
            this.writeByte(s);
        } else {
            this.b(lz_12, -60);
            this.b(lz_12, 54 + this.e(asn2));
            this.writeShort(s);
        }
    }

    private void a(lz_1 lz_12, int n2) {
        switch (n2) {
            case 0: {
                break;
            }
            case 1: {
                this.b(lz_12, 89);
                break;
            }
            case 2: {
                this.b(lz_12, 92);
                break;
            }
            default: {
                throw new aHY("dup(" + n2 + ")");
            }
        }
    }

    private void b(lz_1 lz_12, asn asn2, int n2) {
        if (n2 < 0 || n2 > 2) {
            throw new aHY("SNO: x has value " + n2);
        }
        int n3 = 89 + n2;
        int n4 = 92 + n2;
        this.b(lz_12, asn2 == asn.cRC || asn2 == asn.cRz ? n4 : n3);
    }

    private void e(lz_1 lz_12, asn asn2) {
        if (asn2 == asn.cRw) {
            return;
        }
        this.b(lz_12, asn2 == asn.cRC || asn2 == asn.cRz ? 88 : 87);
    }

    static int d(asn asn2) {
        if (asn2 == asn.cRx || asn2 == asn.cRy || asn2 == asn.cRB || asn2 == asn.cRD || asn2 == asn.cRE) {
            return 0;
        }
        if (asn2 == asn.cRC) {
            return 1;
        }
        if (asn2 == asn.cRA) {
            return 2;
        }
        if (asn2 == asn.cRz) {
            return 3;
        }
        throw new aHY("Unexpected type \"" + asn2 + "\"");
    }

    static int a(asn asn2, int n2, int n3, int n4, int n5) {
        if (asn2 == asn.cRx || asn2 == asn.cRy || asn2 == asn.cRB || asn2 == asn.cRD || asn2 == asn.cRE) {
            return n2;
        }
        if (asn2 == asn.cRC) {
            return n3;
        }
        if (asn2 == asn.cRA) {
            return n4;
        }
        if (asn2 == asn.cRz) {
            return n5;
        }
        throw new aHY("Unexpected type \"" + asn2 + "\"");
    }

    private int e(asn asn2) {
        return !asn2.isPrimitive() ? 4 : zh_2.d(asn2);
    }

    static int f(asn asn2) {
        if (asn2 == asn.cRB) {
            return 0;
        }
        if (asn2 == asn.cRC) {
            return 1;
        }
        if (asn2 == asn.cRA) {
            return 2;
        }
        if (asn2 == asn.cRz) {
            return 3;
        }
        if (!asn2.isPrimitive()) {
            return 4;
        }
        if (asn2 == asn.cRE) {
            return 5;
        }
        if (asn2 == asn.cRx) {
            return 5;
        }
        if (asn2 == asn.cRy) {
            return 6;
        }
        if (asn2 == asn.cRD) {
            return 7;
        }
        throw new aHY("Unexpected type \"" + asn2 + "\"");
    }

    private jz_0 a(asn asn2, String string, lc_0 lc_02) {
        jz_0 jz_02 = asn2.jv(string);
        if (jz_02 != null) {
            return jz_02;
        }
        asn[] asnArray = asn2.aFq();
        if (asnArray != null) {
            jz_02 = this.a((asn)asnArray, string, lc_02);
        }
        asnArray = asn2.aFr();
        for (int j = 0; j < asnArray.length; ++j) {
            jz_0 jz_03 = this.a(asnArray[j], string, lc_02);
            if (jz_03 == null) continue;
            if (jz_02 != null) {
                throw new ajy_2("Access to field \"" + string + "\" is ambiguous - both \"" + jz_02.ic() + "\" and \"" + jz_03.ic() + "\" declare it", lc_02);
            }
            jz_02 = jz_03;
        }
        return jz_02;
    }

    private asn b(asn asn2, String string, lc_0 lc_02) {
        asn[] asnArray = asn2.jw(string);
        if (asnArray.length == 0) {
            return null;
        }
        if (asnArray.length == 1) {
            return asnArray[0];
        }
        StringBuffer stringBuffer = new StringBuffer("Type \"" + string + "\" is ambiguous: " + asnArray[0].toString());
        for (int j = 1; j < asnArray.length; ++j) {
            stringBuffer.append(" vs. ").append(asnArray[j].toString());
        }
        this.c(stringBuffer.toString(), lc_02);
        return asnArray[0];
    }

    public asn dh(String string) {
        StringTokenizer stringTokenizer;
        aao_0 aao_02;
        String string2;
        String string3 = string2 = this.aEF.bnk == null ? null : this.aEF.bnk.doL;
        if (string2 != null) {
            if (!string.startsWith(string2 + '.')) {
                return null;
            }
            string = string.substring(string2.length() + 1);
        }
        if ((aao_02 = this.aEF.eX((stringTokenizer = new StringTokenizer(string, "$")).nextToken())) == null) {
            return null;
        }
        while (stringTokenizer.hasMoreTokens()) {
            if ((aao_02 = aao_02.ab(stringTokenizer.nextToken())) != null) continue;
            return null;
        }
        return this.c((DM)((Object)aao_02));
    }

    private void di(String string) {
        this.c(string, null);
    }

    private void c(String string, lc_0 lc_02) {
        ++this.aED;
        if (this.aEC == null) {
            throw new ajy_2(string, lc_02);
        }
        this.aEC.i(string, lc_02);
    }

    private void a(String string, String string2, lc_0 lc_02) {
        if (this.aEE != null) {
            this.aEE.b(string, string2, lc_02);
        }
    }

    public void a(ayT ayT2) {
        this.aEC = ayT2;
    }

    public void a(aeo_1 aeo_12) {
        this.aEE = aeo_12;
    }

    private avo_0 Gb() {
        avo_0 avo_02 = this.aEB;
        if (avo_02 == null) {
            throw new aHY("S.N.O.: Null CodeContext");
        }
        return avo_02;
    }

    private avo_0 a(avo_0 avo_02) {
        avo_0 avo_03 = this.aEB;
        this.aEB = avo_02;
        return avo_03;
    }

    private avo_0 Gc() {
        return new avo_0(this.Gb().asW());
    }

    private void writeByte(int n2) {
        if (n2 > 255) {
            throw new aHY("Byte value out of legal range");
        }
        this.aEB.d((short)-1, (byte)n2);
    }

    private void writeShort(int n2) {
        if (n2 > 65535) {
            throw new aHY("Short value out of legal range");
        }
        this.aEB.b((short)-1, (byte)(n2 >> 8), (byte)n2);
    }

    private void writeInt(int n2) {
        this.aEB.a((short)-1, (byte)(n2 >> 24), (byte)(n2 >> 16), (byte)(n2 >> 8), (byte)n2);
    }

    private void b(lz_1 lz_12, int n2) {
        this.aEB.d(lz_12.aP().XQ(), (byte)n2);
    }

    private void a(lz_1 lz_12, byte[] byArray) {
        this.aEB.a(lz_12.aP().XQ(), byArray);
    }

    private void a(lz_1 lz_12, int n2, va_2 va_22) {
        this.aEB.a(lz_12.aP().XQ(), n2, va_22);
    }

    private void a(va_2 va_22, va_2 va_23) {
        this.aEB.a((short)-1, va_22, va_23);
    }

    private void dj(String string) {
        avo_0 avo_02 = this.aEB;
        avo_02.e((short)-1, avo_02.asW().fs(string));
    }

    private void b(String string, String string2, String string3) {
        avo_0 avo_02 = this.aEB;
        avo_02.e((short)-1, avo_02.asW().e(string, string2, string3));
    }

    private void c(String string, String string2, String string3) {
        avo_0 avo_02 = this.aEB;
        avo_02.e((short)-1, avo_02.asW().f(string, string2, string3));
    }

    private void d(String string, String string2, String string3) {
        avo_0 avo_02 = this.aEB;
        avo_02.e((short)-1, avo_02.asW().g(string, string2, string3));
    }

    private short dk(String string) {
        return this.aEB.asW().dk(string);
    }

    private short eB(int n2) {
        return this.aEB.asW().eB(n2);
    }

    private short ac(float f) {
        return this.aEB.asW().ac(f);
    }

    private void aY(long l2) {
        avo_0 avo_02 = this.aEB;
        avo_02.e((short)-1, avo_02.asW().cf(l2));
    }

    private void m(double d) {
        avo_0 avo_02 = this.aEB;
        avo_02.e((short)-1, avo_02.asW().o(d));
    }

    public va_2 a(so_1 so_12) {
        if (so_12.ama == null) {
            avo_0 avo_02 = this.aEB;
            avo_02.getClass();
            so_12.ama = new va_2(avo_02);
        }
        return so_12.ama;
    }

    private aR d(xj_1 xj_12) {
        if (xj_12.bXi == null) {
            aim_2 aim_22 = xj_12.oi();
            while (!(aim_22 instanceof aR)) {
                aim_22 = aim_22.Dw();
            }
            xj_12.bXi = (aR)aim_22;
            if (xj_12.bXi.isStatic()) {
                this.c("No current instance available in static method", xj_12.aP());
            }
            xj_12.bXh = (azV)xj_12.bXi.bV();
        }
        return xj_12.bXi;
    }

    private azV e(xj_1 xj_12) {
        if (xj_12.bXh == null) {
            this.d(xj_12);
        }
        return xj_12.bXh;
    }

    private void a(lz_1 lz_12) {
        this.b(lz_12, 42);
    }

    private asn a(lz_1 lz_12, int n2, int n3, asn asn2) {
        if (n2 == 1 && n3 == 0 && asn2.isPrimitive()) {
            this.b(lz_12, -68);
            this.writeByte(asn2 == asn.cRE ? 4 : (asn2 == asn.cRy ? 5 : (asn2 == asn.cRA ? 6 : (asn2 == asn.cRz ? 7 : (asn2 == asn.cRx ? 8 : (asn2 == asn.cRD ? 9 : (asn2 == asn.cRB ? 10 : (asn2 == asn.cRC ? 11 : -1))))))));
            return asn2.j(this.avk.eoQ);
        }
        if (n2 == 1) {
            asn asn3 = asn2.a(n3, this.avk.eoQ);
            this.b(lz_12, -67);
            this.dj(asn3.getDescriptor());
            return asn3.j(this.avk.eoQ);
        }
        asn asn4 = asn2.a(n2 + n3, this.avk.eoQ);
        this.b(lz_12, -59);
        this.dj(asn4.getDescriptor());
        this.writeByte(n2);
        return asn4;
    }

    private static amf al(short s) {
        return (s & 1) != 0 ? amf.cGt : ((s & 4) != 0 ? amf.cGr : ((s & 2) != 0 ? amf.cGq : amf.cGs));
    }

    private static String g(String[] stringArray) {
        if (stringArray.length == 0) {
            throw new IllegalArgumentException("SNO: Empty string array");
        }
        return stringArray[stringArray.length - 1];
    }

    private static String[] h(String[] stringArray) {
        if (stringArray.length == 0) {
            throw new IllegalArgumentException("SNO: Empty string array");
        }
        String[] stringArray2 = new String[stringArray.length - 1];
        System.arraycopy(stringArray, 0, stringArray2, 0, stringArray2.length);
        return stringArray2;
    }

    private static String[] a(String[] stringArray, String string) {
        String[] stringArray2 = new String[stringArray.length + 1];
        System.arraycopy(stringArray, 0, stringArray2, 0, stringArray.length);
        stringArray2[stringArray.length] = string;
        return stringArray2;
    }

    static void a(zh_2 zh_22, ahb_2 ahb_22) {
        zh_22.a(ahb_22);
    }

    static void a(zh_2 zh_22, gb_1 gb_12) {
        zh_22.a(gb_12);
    }

    static void a(zh_2 zh_22, xv_0 xv_02) {
        zh_22.a(xv_02);
    }

    static void a(zh_2 zh_22, Xh xh) {
        zh_22.a(xh);
    }

    static boolean a(zh_2 zh_22, ra_0 ra_02) {
        return zh_22.b(ra_02);
    }

    static boolean a(zh_2 zh_22, aBi aBi2) {
        return zh_22.b(aBi2);
    }

    static boolean a(zh_2 zh_22, akj_0 akj_02) {
        return zh_22.a(akj_02);
    }

    static boolean a(zh_2 zh_22, lo_2 lo_22) {
        return zh_22.a(lo_22);
    }

    static boolean a(zh_2 zh_22, cr cr2) {
        return zh_22.a(cr2);
    }

    static boolean a(zh_2 zh_22, aia_1 aia_12) {
        return zh_22.a(aia_12);
    }

    static boolean a(zh_2 zh_22, no_1 no_12) {
        return zh_22.a(no_12);
    }

    static boolean a(zh_2 zh_22, adh_0 adh_02) {
        return zh_22.a(adh_02);
    }

    static boolean a(zh_2 zh_22, aqt aqt2) {
        return zh_22.b(aqt2);
    }

    static boolean a(zh_2 zh_22, asD asD2) {
        return zh_22.a(asD2);
    }

    static boolean a(zh_2 zh_22, vu_2 vu_22) {
        return zh_22.a(vu_22);
    }

    static boolean a(zh_2 zh_22, tb_1 tb_12) {
        return zh_22.a(tb_12);
    }

    static boolean a(zh_2 zh_22, lG lG2) {
        return zh_22.a(lG2);
    }

    static boolean a(zh_2 zh_22, jr_1 jr_12) {
        return zh_22.a(jr_12);
    }

    static boolean a(zh_2 zh_22, v_0 v_02) {
        return zh_22.a(v_02);
    }

    static boolean a(zh_2 zh_22, gl_1 gl_12) {
        return zh_22.a(gl_12);
    }

    static boolean a(zh_2 zh_22, Ms ms) {
        return zh_22.a(ms);
    }

    static boolean a(zh_2 zh_22, ek_0 ek_02) {
        return zh_22.a(ek_02);
    }

    static boolean a(zh_2 zh_22, ail_1 ail_12) {
        return zh_22.a(ail_12);
    }

    static boolean a(zh_2 zh_22, yn_1 yn_12) {
        return zh_22.a(yn_12);
    }

    static boolean a(zh_2 zh_22, akl_0 akl_02) {
        return zh_22.a(akl_02);
    }

    static Map a(zh_2 zh_22, xa xa2, Map map) {
        return zh_22.a(xa2, map);
    }

    static Map a(zh_2 zh_22, akE akE2, Map map) {
        return zh_22.a(akE2, map);
    }

    static void a(zh_2 zh_22, lo_2 lo_22, Map map) {
        zh_22.a(lo_22, map);
    }

    static void a(zh_2 zh_22, tb_1 tb_12, Map map) {
        zh_22.a(tb_12, map);
    }

    static void a(zh_2 zh_22, no_1 no_12, Map map) {
        zh_22.a(no_12, map);
    }

    static void a(zh_2 zh_22, aia_1 aia_12, Map map) {
        zh_22.a(aia_12, map);
    }

    static void a(zh_2 zh_22, ra_0 ra_02, Map map) {
        zh_22.a(ra_02, map);
    }

    static void a(zh_2 zh_22, asD asD2, Map map) {
        zh_22.a(asD2, map);
    }

    static void a(zh_2 zh_22, vu_2 vu_22, Map map) {
        zh_22.a(vu_22, map);
    }

    static void a(zh_2 zh_22, aqt aqt2, Map map) {
        zh_22.a(aqt2, map);
    }

    static void a(zh_2 zh_22, adh_0 adh_02, Map map) {
        zh_22.a(adh_02, map);
    }

    static Map a(zh_2 zh_22, akj_0 akj_02, Map map) {
        return zh_22.a(akj_02, map);
    }

    static Map a(zh_2 zh_22, lG lG2, Map map) {
        return zh_22.a(lG2, map);
    }

    static void a(zh_2 zh_22, jy_2 jy_22) {
        zh_22.e(jy_22);
    }

    static void a(zh_2 zh_22, ayN ayN2) {
        zh_22.a(ayN2);
    }

    static void a(zh_2 zh_22, afa_1 afa_12) {
        zh_22.a(afa_12);
    }

    static void a(zh_2 zh_22, zS zS2) {
        zh_22.a(zS2);
    }

    static void a(zh_2 zh_22, jy_2 jy_22, va_2 va_22, boolean bl2) {
        zh_22.b(jy_22, va_22, bl2);
    }

    static void a(zh_2 zh_22, afk_2 afk_22, va_2 va_22, boolean bl2) {
        zh_22.a(afk_22, va_22, bl2);
    }

    static void a(zh_2 zh_22, rr_2 rr_22, va_2 va_22, boolean bl2) {
        zh_22.a(rr_22, va_22, bl2);
    }

    static void a(zh_2 zh_22, zS zS2, va_2 va_22, boolean bl2) {
        zh_22.a(zS2, va_22, bl2);
    }

    static int a(zh_2 zh_22, auo auo2) {
        return zh_22.a(auo2);
    }

    static int b(zh_2 zh_22, jy_2 jy_22) {
        return zh_22.g(jy_22);
    }

    static int a(zh_2 zh_22, anM anM2) {
        return zh_22.b(anM2);
    }

    static int a(zh_2 zh_22, Wh wh) {
        return zh_22.a(wh);
    }

    static int a(zh_2 zh_22, cb_1 cb_12) {
        return zh_22.a(cb_12);
    }

    static int a(zh_2 zh_22, aai_2 aai_22) {
        return zh_22.a(aai_22);
    }

    static int a(zh_2 zh_22, GT gT) {
        return zh_22.a(gT);
    }

    static int b(zh_2 zh_22, zS zS2) {
        return zh_22.b(zS2);
    }

    static asn b(zh_2 zh_22, auo auo2) {
        return zh_22.b(auo2);
    }

    static asn b(zh_2 zh_22, ayN ayN2) {
        return zh_22.b(ayN2);
    }

    static asn a(zh_2 zh_22, afk_2 afk_22) {
        return zh_22.a(afk_22);
    }

    static asn a(zh_2 zh_22, rr_2 rr_22) {
        return zh_22.a(rr_22);
    }

    static asn a(zh_2 zh_22, agz_2 agz_22) {
        return zh_22.a(agz_22);
    }

    static asn a(zh_2 zh_22, agx_0 agx_02) {
        return zh_22.a(agx_02);
    }

    static asn a(zh_2 zh_22, acq_0 acq_02) {
        return zh_22.a(acq_02);
    }

    static asn b(zh_2 zh_22, afa_1 afa_12) {
        return zh_22.b(afa_12);
    }

    static asn a(zh_2 zh_22, p_0 p_02) {
        return zh_22.a(p_02);
    }

    static asn a(zh_2 zh_22, La la) {
        return zh_22.a(la);
    }

    static asn a(zh_2 zh_22, ajs_2 ajs_22) {
        return zh_22.a(ajs_22);
    }

    static asn a(zh_2 zh_22, aow_0 aow_02) {
        return zh_22.a(aow_02);
    }

    static asn a(zh_2 zh_22, afi_2 afi_22) {
        return zh_22.a(afi_22);
    }

    static asn a(zh_2 zh_22, zj zj2) {
        return zh_22.a(zj2);
    }

    static asn a(zh_2 zh_22, aFz aFz2) {
        return zh_22.a(aFz2);
    }

    static asn a(zh_2 zh_22, Nl nl) {
        return zh_22.a(nl);
    }

    static asn a(zh_2 zh_22, ac_1 ac_12) {
        return zh_22.a(ac_12);
    }

    static asn a(zh_2 zh_22, xj_1 xj_12) {
        return zh_22.a(xj_12);
    }

    static asn a(zh_2 zh_22, aLs aLs2) {
        return zh_22.a(aLs2);
    }

    static asn b(zh_2 zh_22, anM anM2) {
        return zh_22.c(anM2);
    }

    static asn b(zh_2 zh_22, Wh wh) {
        return zh_22.b(wh);
    }

    static asn b(zh_2 zh_22, cb_1 cb_12) {
        return zh_22.b(cb_12);
    }

    static asn b(zh_2 zh_22, aai_2 aai_22) {
        return zh_22.b(aai_22);
    }

    static asn b(zh_2 zh_22, GT gT) {
        return zh_22.b(gT);
    }

    static asn a(zh_2 zh_22, aoa_1 aoa_12) {
        return zh_22.a(aoa_12);
    }

    static asn c(zh_2 zh_22, zS zS2) {
        return zh_22.c(zS2);
    }

    static Object c(zh_2 zh_22, jy_2 jy_22) {
        return zh_22.k(jy_22);
    }

    static Object b(zh_2 zh_22, afk_2 afk_22) {
        return zh_22.b(afk_22);
    }

    static Object b(zh_2 zh_22, rr_2 rr_22) {
        return zh_22.b(rr_22);
    }

    static Object b(zh_2 zh_22, agz_2 agz_22) {
        return zh_22.b(agz_22);
    }

    static Object b(zh_2 zh_22, acq_0 acq_02) {
        return zh_22.b(acq_02);
    }

    static Object b(zh_2 zh_22, aow_0 aow_02) {
        return zh_22.b(aow_02);
    }

    static Object c(zh_2 zh_22, anM anM2) {
        return zh_22.d(anM2);
    }

    static Object c(zh_2 zh_22, cb_1 cb_12) {
        return zh_22.c(cb_12);
    }

    static Object d(zh_2 zh_22, zS zS2) {
        return zh_22.d(zS2);
    }

    static Object d(zh_2 zh_22, jy_2 jy_22) {
        return zh_22.m(jy_22);
    }

    static Object c(zh_2 zh_22, afk_2 afk_22) {
        return zh_22.c(afk_22);
    }

    static Object c(zh_2 zh_22, aow_0 aow_02) {
        return zh_22.c(aow_02);
    }

    static Object e(zh_2 zh_22, zS zS2) {
        return zh_22.e(zS2);
    }

    static void d(zh_2 zh_22, anM anM2) {
        zh_22.e(anM2);
    }

    static void c(zh_2 zh_22, Wh wh) {
        zh_22.c(wh);
    }

    static void d(zh_2 zh_22, cb_1 cb_12) {
        zh_22.d(cb_12);
    }

    static void c(zh_2 zh_22, aai_2 aai_22) {
        zh_22.c(aai_22);
    }

    static void c(zh_2 zh_22, GT gT) {
        zh_22.c(gT);
    }

    static void b(zh_2 zh_22, aoa_1 aoa_12) {
        zh_22.b(aoa_12);
    }

    static void f(zh_2 zh_22, zS zS2) {
        zh_22.f(zS2);
    }

    static asn a(zh_2 zh_22, lj_2 lj_22) {
        return zh_22.a(lj_22);
    }

    static asn a(zh_2 zh_22, ahe_1 ahe_12) {
        return zh_22.b(ahe_12);
    }

    static asn a(zh_2 zh_22, gw_1 gw_12) {
        return zh_22.b(gw_12);
    }

    static asn a(zh_2 zh_22, ft ft2) {
        return zh_22.b(ft2);
    }

    static asn a(zh_2 zh_22, cg_1 cg_12) {
        return zh_22.b(cg_12);
    }

    static asn a(zh_2 zh_22, vq_1 vq_12) {
        return zh_22.b(vq_12);
    }

    static asn c(zh_2 zh_22, auo auo2) {
        return zh_22.c(auo2);
    }

    static asn c(zh_2 zh_22, ayN ayN2) {
        return zh_22.c(ayN2);
    }

    static asn d(zh_2 zh_22, afk_2 afk_22) {
        return zh_22.d(afk_22);
    }

    static asn c(zh_2 zh_22, rr_2 rr_22) {
        return zh_22.c(rr_22);
    }

    static asn c(zh_2 zh_22, agz_2 agz_22) {
        return zh_22.c(agz_22);
    }

    static asn b(zh_2 zh_22, agx_0 agx_02) {
        return zh_22.b(agx_02);
    }

    static asn c(zh_2 zh_22, acq_0 acq_02) {
        return zh_22.c(acq_02);
    }

    static asn c(zh_2 zh_22, afa_1 afa_12) {
        return zh_22.c(afa_12);
    }

    static asn b(zh_2 zh_22, p_0 p_02) {
        return zh_22.b(p_02);
    }

    static asn b(zh_2 zh_22, La la) {
        return zh_22.b(la);
    }

    static asn b(zh_2 zh_22, ajs_2 ajs_22) {
        return zh_22.b(ajs_22);
    }

    static asn d(zh_2 zh_22, aow_0 aow_02) {
        return zh_22.d(aow_02);
    }

    static asn b(zh_2 zh_22, afi_2 afi_22) {
        return zh_22.b(afi_22);
    }

    static asn b(zh_2 zh_22, zj zj2) {
        return zh_22.b(zj2);
    }

    static asn b(zh_2 zh_22, aFz aFz2) {
        return zh_22.b(aFz2);
    }

    static asn b(zh_2 zh_22, Nl nl) {
        return zh_22.b(nl);
    }

    static asn b(zh_2 zh_22, ac_1 ac_12) {
        return zh_22.b(ac_12);
    }

    static asn b(zh_2 zh_22, xj_1 xj_12) {
        return zh_22.b(xj_12);
    }

    static asn b(zh_2 zh_22, aLs aLs2) {
        return zh_22.b(aLs2);
    }

    static asn e(zh_2 zh_22, anM anM2) {
        return zh_22.f(anM2);
    }

    static asn d(zh_2 zh_22, Wh wh) {
        return zh_22.d(wh);
    }

    static asn e(zh_2 zh_22, cb_1 cb_12) {
        return zh_22.e(cb_12);
    }

    static asn d(zh_2 zh_22, aai_2 aai_22) {
        return zh_22.d(aai_22);
    }

    static asn d(zh_2 zh_22, GT gT) {
        return zh_22.d(gT);
    }

    static asn c(zh_2 zh_22, aoa_1 aoa_12) {
        return zh_22.c(aoa_12);
    }

    static asn g(zh_2 zh_22, zS zS2) {
        return zh_22.g(zS2);
    }

    static boolean a(zh_2 zh_22, alb_0 alb_02) {
        return zh_22.c(alb_02);
    }

    static boolean f(zh_2 zh_22, anM anM2) {
        return zh_22.g(anM2);
    }

    static asn e(zh_2 zh_22, jy_2 jy_22) {
        return zh_22.i(jy_22);
    }

    static void a(zh_2 zh_22, lz_1 lz_12, asn asn2) {
        zh_22.a(lz_12, asn2);
    }

    static asn a(zh_2 zh_22, lz_1 lz_12, Object object) {
        return zh_22.a(lz_12, object);
    }

    static apm_0 a(zh_2 zh_22) {
        return zh_22.avk;
    }

    static asn b(zh_2 zh_22, alb_0 alb_02) {
        return zh_22.a(alb_02);
    }

    static Object a(zh_2 zh_22, lz_1 lz_12, Object object, asn asn2) {
        return zh_22.a(lz_12, object, asn2);
    }

    static asn a(zh_2 zh_22, el_1 el_12) {
        return zh_22.c(el_12);
    }

    static void a(zh_2 zh_22, String string, lc_0 lc_02) {
        zh_22.c(string, lc_02);
    }

    static amf am(short s) {
        return zh_2.al(s);
    }

    static asn a(zh_2 zh_22, xN xN2) {
        return zh_22.b(xN2);
    }

    static {
        zh_2.a(new Object[]{new byte[0], "BS", "BI", "SI", "CI", new byte[]{-123}, "BJ", "SJ", "CJ", "IJ", new byte[]{-122}, "BF", "SF", "CF", "IF", new byte[]{-119}, "JF", new byte[]{-121}, "BD", "SD", "CD", "ID", new byte[]{-118}, "JD", new byte[]{-115}, "FD"}, aEz);
        aEA = new HashMap();
        zh_2.a(new Object[]{new byte[0], "BC", "SC", "CS", new byte[]{-111}, "SB", "CB", "IB", new byte[]{-109}, "IS", "IC", new byte[]{-120, -111}, "JB", new byte[]{-120, -109}, "JS", "JC", new byte[]{-120}, "JI", new byte[]{-117, -111}, "FB", new byte[]{-117, -109}, "FS", "FC", new byte[]{-117}, "FI", new byte[]{-116}, "FJ", new byte[]{-114, -111}, "DB", new byte[]{-114, -109}, "DS", "DC", new byte[]{-114}, "DI", new byte[]{-113}, "DJ", new byte[]{-112}, "DF"}, aEA);
    }
}

