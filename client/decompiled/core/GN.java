/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.List;

public class GN {
    private final ahr_1 bcq;
    private static final short[] bcr = new short[]{7};
    private aeo_1 aEE = null;

    public GN(ahr_1 ahr_12) {
        this.bcq = ahr_12;
    }

    public ahr_1 QL() {
        return this.bcq;
    }

    public kh_1 QM() {
        kh_1 kh_12 = new kh_1(this.RR().getFileName());
        if (this.ej("package")) {
            kh_12.a(this.QN());
        }
        while (this.ej("import")) {
            kh_12.a(this.QO());
        }
        while (!this.bcq.awY().isEOF()) {
            if (this.el(";")) {
                this.RS();
                continue;
            }
            kh_12.b(this.QR());
        }
        return kh_12;
    }

    public azm_0 QN() {
        this.ek("package");
        lc_0 lc_02 = this.RR();
        String string = GN.join(this.QQ(), ".");
        this.em(";");
        this.d(string, lc_02);
        return new azm_0(lc_02, string);
    }

    public DV QO() {
        this.ek("import");
        DV dV = this.QP();
        this.em(";");
        return dV;
    }

    public DV QP() {
        boolean bl2;
        lc_0 lc_02 = this.RR();
        if (this.ej("static")) {
            bl2 = true;
            this.RS();
        } else {
            bl2 = false;
        }
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add(this.RW());
        while (true) {
            if (!this.el(".")) {
                String[] stringArray = arrayList.toArray(new String[arrayList.size()]);
                return bl2 ? new xv_0(lc_02, stringArray) : new ahb_2(lc_02, stringArray);
            }
            this.em(".");
            if (this.el("*")) {
                this.RS();
                String[] stringArray = arrayList.toArray(new String[arrayList.size()]);
                return bl2 ? new Xh(lc_02, stringArray) : new gb_1(lc_02, stringArray);
            }
            arrayList.add(this.RW());
        }
    }

    public String[] QQ() {
        if (!this.bcq.awY().lz()) {
            this.j("Identifier expected");
        }
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add(this.RW());
        while (this.el(".") && this.bcq.awZ().lz()) {
            this.RS();
            arrayList.add(this.RW());
        }
        return arrayList.toArray(new String[arrayList.size()]);
    }

    public pn_1 QR() {
        DM dM;
        String string = this.bcq.axa();
        short s = this.QS();
        if (this.ej("class")) {
            if (string == null) {
                this.a("CDCM", "Class doc comment missing", this.RR());
            }
            this.RS();
            dM = (ayp_0)this.a(string, s, ale_2.cFw);
        } else if (this.ej("interface")) {
            if (string == null) {
                this.a("IDCM", "Interface doc comment missing", this.RR());
            }
            this.RS();
            dM = (ang)this.a(string, s, cw_0.aMV);
        } else {
            this.j("Unexpected token \"" + this.bcq.awY() + "\" in class or interface declaration");
            return null;
        }
        return dM;
    }

    public short QS() {
        short s = 0;
        while (this.RT()) {
            int n2;
            String string = this.bcq.awY().NV();
            int n3 = string == "public" ? 1 : (string == "protected" ? 4 : (string == "private" ? 2 : (string == "static" ? 8 : (string == "abstract" ? 1024 : (string == "final" ? 16 : (string == "native" ? 256 : (string == "synchronized" ? 32 : (string == "transient" ? 128 : (string == "volatile" ? 64 : (n2 = string == "strictfp" ? 2048 : -1))))))))));
            if (n2 == -1) break;
            this.RS();
            if ((s & n2) != 0) {
                this.j("Duplicate modifier \"" + string + "\"");
            }
            for (int j = 0; j < bcr.length; ++j) {
                short s2 = bcr[j];
                if ((n2 & s2) == 0 || (s & s2) == 0) continue;
                this.j("Only one of \"" + pp_0.R(s2) + "\" allowed");
            }
            s = (short)(s | n2);
        }
        return s;
    }

    public gk_0 a(String string, short s, ale_2 ale_22) {
        gk_0 gk_02;
        lc_0 lc_02 = this.RR();
        String string2 = this.RW();
        this.e(string2, lc_02);
        ft ft2 = null;
        if (this.ej("extends")) {
            this.RS();
            ft2 = this.Rv();
        }
        atu_0[] atu_0Array = new ft[]{};
        if (this.ej("implements")) {
            this.RS();
            atu_0Array = this.Rw();
        }
        if (ale_22 == ale_2.cFw) {
            gk_02 = new ayp_0(lc_02, string, s, string2, ft2, atu_0Array);
        } else if (ale_22 == ale_2.cFv) {
            gk_02 = new hg_2(lc_02, string, s, string2, ft2, atu_0Array);
        } else if (ale_22 == ale_2.cFu) {
            gk_02 = new abh_1(lc_02, string, s, string2, ft2, atu_0Array);
        } else {
            throw new aHY("SNO: Class declaration in unexpected context " + ale_22);
        }
        this.b(gk_02);
        return gk_02;
    }

    public void b(azV azV2) {
        if (!this.el("{")) {
            this.j("\"{\" expected at start of class body");
        }
        this.RS();
        while (true) {
            if (this.el("}")) {
                this.RS();
                return;
            }
            this.c(azV2);
        }
    }

    public void c(azV azV2) {
        if (this.el(";")) {
            this.RS();
            return;
        }
        String string = this.bcq.axa();
        short s = this.QS();
        if (this.el("{")) {
            if ((s & 0xFFFFFFF7) != 0) {
                this.j("Only modifier \"static\" allowed on initializer");
            }
            ra_0 ra_02 = new ra_0(this.RR(), (s & 8) != 0, this.QZ());
            azV2.a(ra_02);
            return;
        }
        if (this.ej("void")) {
            lc_0 lc_02 = this.RR();
            this.RS();
            if (string == null) {
                this.a("MDCM", "Method doc comment missing", lc_02);
            }
            String string2 = this.RW();
            azV2.c(this.a(string, s, new gw_1(lc_02, 0), string2));
            return;
        }
        if (this.ej("class")) {
            if (string == null) {
                this.a("MCDCM", "Member class doc comment missing", this.RR());
            }
            this.RS();
            azV2.a((rp_1)((Object)this.a(string, s, ale_2.cFv)));
            return;
        }
        if (this.ej("interface")) {
            if (string == null) {
                this.a("MIDCM", "Member interface doc comment missing", this.RR());
            }
            this.RS();
            azV2.a((rp_1)((Object)this.a(string, (short)(s | 8), cw_0.aMU)));
            return;
        }
        if (azV2 instanceof gk_0 && this.bcq.awY().aA(((gk_0)azV2).getName()) && this.bcq.awZ().hE("(")) {
            if (string == null) {
                this.a("CDCM", "Constructor doc comment missing", this.RR());
            }
            azV2.f(this.a(string, s));
            return;
        }
        atu_0 atu_02 = this.Ru();
        lc_0 lc_03 = this.RR();
        String string3 = this.RW();
        if (this.el("(")) {
            if (string == null) {
                this.a("MDCM", "Method doc comment missing", this.RR());
            }
            azV2.c(this.a(string, s, atu_02, string3));
            return;
        }
        if (string == null) {
            this.a("FDCM", "Field doc comment missing", this.RR());
        }
        aBi aBi2 = new aBi(lc_03, string, s, atu_02, this.eh(string3));
        this.em(";");
        azV2.a(aBi2);
    }

    public cg_2 a(String string, short s, cw_0 cw_02) {
        cg_2 cg_22;
        lc_0 lc_02 = this.RR();
        String string2 = this.RW();
        this.e(string2, lc_02);
        atu_0[] atu_0Array = new ft[]{};
        if (this.ej("extends")) {
            this.RS();
            atu_0Array = this.Rw();
        }
        if (cw_02 == cw_0.aMV) {
            cg_22 = new ang(lc_02, string, s, string2, atu_0Array);
        } else if (cw_02 == cw_0.aMU) {
            cg_22 = new ajf_0(lc_02, string, s, string2, atu_0Array);
        } else {
            throw new aHY("SNO: Interface declaration in unexpected context " + cw_02);
        }
        this.b(cg_22);
        return cg_22;
    }

    public void b(cg_2 cg_22) {
        this.em("{");
        while (true) {
            Object object;
            Object object2;
            if (this.el("}")) break;
            if (this.el(";")) {
                this.RS();
                continue;
            }
            String string = this.bcq.axa();
            short s = this.QS();
            if (this.ej("void")) {
                if (string == null) {
                    this.a("MDCM", "Method doc comment missing", this.RR());
                }
                object2 = this.RR();
                this.RS();
                object = this.RW();
                cg_22.c(this.a(string, (short)(s | 0x400 | 1), new gw_1((lc_0)object2, 0), (String)object));
                continue;
            }
            if (this.ej("class")) {
                if (string == null) {
                    this.a("MCDCM", "Member class doc comment missing", this.RR());
                }
                this.RS();
                cg_22.a((rp_1)((Object)this.a(string, (short)(s | 8 | 1), ale_2.cFv)));
                continue;
            }
            if (this.ej("interface")) {
                if (string == null) {
                    this.a("MIDCM", "Member interface doc comment missing", this.RR());
                }
                this.RS();
                cg_22.a((rp_1)((Object)this.a(string, (short)(s | 8 | 1), cw_0.aMU)));
                continue;
            }
            object2 = this.Ru();
            if (!this.bcq.awY().lz()) {
                this.j("Identifier expected in member declaration");
            }
            object = this.RR();
            String string2 = this.RW();
            if (this.el("(")) {
                if (string == null) {
                    this.a("MDCM", "Method doc comment missing", this.RR());
                }
                cg_22.c(this.a(string, (short)(s | 0x400 | 1), (atu_0)object2, string2));
                continue;
            }
            if (string == null) {
                this.a("FDCM", "Field doc comment missing", this.RR());
            }
            aBi aBi2 = new aBi((lc_0)object, string, (short)(s | 1 | 8 | 0x10), (atu_0)object2, this.eh(string2));
            cg_22.e(aBi2);
        }
        this.RS();
    }

    public acc_0 a(String string, short s) {
        atu_0[] atu_0Array;
        lc_0 lc_02 = this.RR();
        this.RW();
        anb_1[] anb_1Array = this.QV();
        if (this.ej("throws")) {
            this.RS();
            atu_0Array = this.Rw();
        } else {
            atu_0Array = new ft[]{};
        }
        lc_02 = this.RR();
        this.em("{");
        xa xa2 = null;
        ArrayList<cr> arrayList = new ArrayList<cr>();
        if (this.j(new String[]{"this", "super", "new", "void", "byte", "char", "short", "int", "long", "float", "double", "boolean"}) || this.bcq.awY().isLiteral() || this.bcq.awY().lz()) {
            alb_0 alb_02 = this.Rx();
            if (alb_02 instanceof xa) {
                this.em(";");
                xa2 = (xa)alb_02;
            } else {
                akE akE2;
                if (this.bcq.awY().lz()) {
                    atu_0 atu_02 = alb_02.aAq();
                    akE2 = new lG(alb_02.aP(), 0, atu_02, this.Rc());
                    this.em(";");
                } else {
                    akE2 = new cr(alb_02.aAr());
                    this.em(";");
                }
                arrayList.add((cr)akE2);
            }
        }
        arrayList.addAll(this.Ra());
        this.em("}");
        return new acc_0(lc_02, string, s, anb_1Array, atu_0Array, xa2, arrayList);
    }

    public kc_0 a(String string, short s, atu_0 atu_02, String string2) {
        List list;
        atu_0[] atu_0Array;
        lc_0 lc_02 = this.RR();
        this.f(string2, lc_02);
        anb_1[] anb_1Array = this.QV();
        for (int j = this.QX(); j > 0; --j) {
            atu_02 = new ahe_1(atu_02);
        }
        if (this.ej("throws")) {
            this.RS();
            atu_0Array = this.Rw();
        } else {
            atu_0Array = new ft[]{};
        }
        if (this.el(";")) {
            if ((s & 0x500) == 0) {
                this.j("Non-abstract, non-native method must have a body");
            }
            this.RS();
            list = null;
        } else {
            if ((s & 0x500) != 0) {
                this.j("Abstract or native method must not have a body");
            }
            this.em("{");
            list = this.Ra();
            this.em("}");
        }
        return new kc_0(lc_02, string, s, atu_02, string2, anb_1Array, atu_0Array, list);
    }

    public fd_2 QT() {
        if (this.el("{")) {
            return this.QU();
        }
        return this.Rx().aAr();
    }

    public ln_2 QU() {
        lc_0 lc_02 = this.RR();
        this.em("{");
        ArrayList<fd_2> arrayList = new ArrayList<fd_2>();
        while (!this.el("}")) {
            arrayList.add(this.QT());
            if (this.el("}")) break;
            if (!this.el(",")) {
                this.j("\",\" or \"}\" expected");
            }
            this.RS();
        }
        this.RS();
        return new ln_2(lc_02, arrayList.toArray(new fd_2[arrayList.size()]));
    }

    public anb_1[] QV() {
        this.em("(");
        if (this.el(")")) {
            this.RS();
            return new anb_1[0];
        }
        ArrayList<anb_1> arrayList = new ArrayList<anb_1>();
        while (true) {
            arrayList.add(this.QW());
            if (!this.el(",")) break;
            this.RS();
        }
        this.em(")");
        return arrayList.toArray(new anb_1[arrayList.size()]);
    }

    public anb_1 QW() {
        boolean bl2 = this.ej("final");
        if (bl2) {
            this.RS();
        }
        atu_0 atu_02 = this.Ru();
        lc_0 lc_02 = this.RR();
        String string = this.RW();
        this.h(string, lc_02);
        for (int j = this.QX(); j > 0; --j) {
            atu_02 = new ahe_1(atu_02);
        }
        return new anb_1(lc_02, bl2, atu_02, string);
    }

    int QX() {
        int n2 = 0;
        while (this.bcq.awY().hE("[") && this.bcq.awZ().hE("]")) {
            this.RS();
            this.RS();
            ++n2;
        }
        return n2;
    }

    public lo_2 QY() {
        return this.QZ();
    }

    public lo_2 QZ() {
        lo_2 lo_22 = new lo_2(this.RR());
        this.em("{");
        lo_22.i(this.Ra());
        this.em("}");
        return lo_22;
    }

    public List Ra() {
        ArrayList<TK> arrayList = new ArrayList<TK>();
        while (!(this.el("}") || this.ej("case") || this.ej("default"))) {
            arrayList.add(this.Rb());
        }
        return arrayList;
    }

    public TK Rb() {
        if (this.bcq.awY().lz() && this.bcq.awZ().hE(":") || this.j(new String[]{"if", "for", "while", "do", "try", "switch", "synchronized", "return", "throw", "break", "continue"}) || this.k(new String[]{"{", ";"})) {
            return this.Re();
        }
        if (this.ej("class")) {
            String string = this.bcq.axa();
            if (string == null) {
                this.a("LCDCM", "Local class doc comment missing", this.RR());
            }
            this.RS();
            abh_1 abh_12 = (abh_1)this.a(string, (short)0, ale_2.cFu);
            return new ail_1(abh_12);
        }
        if (this.ej("final")) {
            lc_0 lc_02 = this.RR();
            this.RS();
            atu_0 atu_02 = this.Ru();
            lG lG2 = new lG(lc_02, 16, atu_02, this.Rc());
            this.em(";");
            return lG2;
        }
        alb_0 alb_02 = this.Rx();
        if (this.el(";")) {
            this.RS();
            return new cr(alb_02.aAr());
        }
        atu_0 atu_03 = alb_02.aAq();
        lG lG3 = new lG(alb_02.aP(), 0, atu_03, this.Rc());
        this.em(";");
        return lG3;
    }

    public jk_2[] Rc() {
        ArrayList<jk_2> arrayList = new ArrayList<jk_2>();
        while (true) {
            jk_2 jk_22 = this.Rd();
            this.h(jk_22.name, jk_22.aP());
            arrayList.add(jk_22);
            if (!this.el(",")) break;
            this.RS();
        }
        return arrayList.toArray(new jk_2[arrayList.size()]);
    }

    public jk_2[] eh(String string) {
        ArrayList<jk_2> arrayList = new ArrayList<jk_2>();
        jk_2 jk_22 = this.ei(string);
        this.g(jk_22.name, jk_22.aP());
        arrayList.add(jk_22);
        while (this.el(",")) {
            this.RS();
            jk_22 = this.Rd();
            this.g(jk_22.name, jk_22.aP());
            arrayList.add(jk_22);
        }
        return arrayList.toArray(new jk_2[arrayList.size()]);
    }

    public jk_2 Rd() {
        return this.ei(this.RW());
    }

    public jk_2 ei(String string) {
        lc_0 lc_02 = this.RR();
        int n2 = this.QX();
        fd_2 fd_22 = null;
        if (this.el("=")) {
            this.RS();
            fd_22 = this.QT();
        }
        return new jk_2(lc_02, string, n2, fd_22);
    }

    public akE Re() {
        lo_2 lo_22;
        if (this.bcq.awY().lz() && this.bcq.awZ().hE(":")) {
            return this.Rf();
        }
        aFA aFA2 = this.bcq.awY();
        akE akE2 = aFA2.hE("{") ? this.QZ() : (aFA2.dN("if") ? this.Rg() : (aFA2.dN("for") ? this.Rh() : (aFA2.dN("while") ? this.Rj() : (aFA2.dN("do") ? this.Rk() : (aFA2.dN("try") ? this.Rl() : (aFA2.dN("switch") ? this.Rm() : (aFA2.dN("synchronized") ? this.Rn() : (aFA2.dN("return") ? this.Ro() : (aFA2.dN("throw") ? this.Rp() : (aFA2.dN("break") ? this.Rq() : (aFA2.dN("continue") ? this.Rr() : (lo_22 = aFA2.hE(";") ? this.Rs() : this.RX()))))))))))));
        if (lo_22 == null) {
            this.j("\"" + aFA2.NV() + "\" NYI");
        }
        return lo_22;
    }

    public akE Rf() {
        String string = this.RW();
        this.em(":");
        return new akj_0(this.RR(), string, this.Re());
    }

    public akE Rg() {
        lc_0 lc_02 = this.RR();
        this.ek("if");
        this.em("(");
        jy_2 jy_22 = this.Rx().aAr();
        this.em(")");
        akE akE2 = this.Re();
        akE akE3 = null;
        if (this.ej("else")) {
            this.RS();
            akE3 = this.Re();
        }
        return new aia_1(lc_02, jy_22, akE2, akE3);
    }

    public akE Rh() {
        lc_0 lc_02 = this.RR();
        this.ek("for");
        this.em("(");
        TK tK = null;
        if (!this.el(";")) {
            tK = this.Ri();
        }
        this.em(";");
        jy_2 jy_22 = null;
        if (!this.el(";")) {
            jy_22 = this.Rx().aAr();
        }
        this.em(";");
        jy_2[] jy_2Array = null;
        if (!this.el(")")) {
            jy_2Array = this.Rt();
        }
        this.em(")");
        return new no_1(lc_02, tK, jy_22, jy_2Array, this.Re());
    }

    private TK Ri() {
        if (this.j(new String[]{"final", "byte", "short", "char", "int", "long", "float", "double", "boolean"})) {
            short s = this.QS();
            atu_0 atu_02 = this.Ru();
            return new lG(this.RR(), s, atu_02, this.Rc());
        }
        alb_0 alb_02 = this.Rx();
        if (this.bcq.awY().lz()) {
            atu_0 atu_03 = alb_02.aAq();
            return new lG(alb_02.aP(), 0, atu_03, this.Rc());
        }
        if (!this.el(",")) {
            return new cr(alb_02.aAr());
        }
        this.RS();
        ArrayList<cr> arrayList = new ArrayList<cr>();
        arrayList.add(new cr(alb_02.aAr()));
        while (true) {
            arrayList.add(new cr(this.Rx().aAr()));
            if (!this.el(",")) break;
            this.RS();
        }
        lo_2 lo_22 = new lo_2(alb_02.aP());
        lo_22.i(arrayList);
        return lo_22;
    }

    public akE Rj() {
        lc_0 lc_02 = this.RR();
        this.ek("while");
        this.em("(");
        jy_2 jy_22 = this.Rx().aAr();
        this.em(")");
        return new adh_0(lc_02, jy_22, this.Re());
    }

    public akE Rk() {
        lc_0 lc_02 = this.RR();
        this.ek("do");
        akE akE2 = this.Re();
        this.ek("while");
        this.em("(");
        jy_2 jy_22 = this.Rx().aAr();
        this.em(")");
        this.em(";");
        return new tb_1(lc_02, akE2, jy_22);
    }

    public akE Rl() {
        Object object;
        lc_0 lc_02 = this.RR();
        this.ek("try");
        lo_2 lo_22 = this.QZ();
        ArrayList<xp_1> arrayList = new ArrayList<xp_1>();
        while (this.ej("catch")) {
            object = this.RR();
            this.RS();
            this.em("(");
            anb_1 anb_12 = this.QW();
            this.em(")");
            arrayList.add(new xp_1((lc_0)object, anb_12, this.QZ()));
        }
        object = null;
        if (this.ej("finally")) {
            this.RS();
            object = this.QZ();
        }
        if (arrayList.size() == 0 && object == null) {
            this.j("\"try\" statement must have at least one \"catch\" clause or a \"finally\" clause");
        }
        return new aqt(lc_02, lo_22, arrayList, (lo_2)object);
    }

    public akE Rm() {
        lc_0 lc_02 = this.RR();
        this.ek("switch");
        this.em("(");
        jy_2 jy_22 = this.Rx().aAr();
        this.em(")");
        this.em("{");
        ArrayList<jt_1> arrayList = new ArrayList<jt_1>();
        while (!this.el("}")) {
            lc_0 lc_03 = this.RR();
            boolean bl2 = false;
            ArrayList<jy_2> arrayList2 = new ArrayList<jy_2>();
            do {
                if (this.ej("case")) {
                    this.RS();
                    arrayList2.add(this.Rx().aAr());
                } else if (this.ej("default")) {
                    this.RS();
                    if (bl2) {
                        this.j("Duplicate \"default\" label");
                    }
                    bl2 = true;
                } else {
                    this.j("\"case\" or \"default\" expected");
                }
                this.em(":");
            } while (this.j(new String[]{"case", "default"}));
            jt_1 jt_12 = new jt_1(lc_03, arrayList2, bl2, this.Ra());
            arrayList.add(jt_12);
        }
        this.RS();
        return new asD(lc_02, jy_22, arrayList);
    }

    public akE Rn() {
        lc_0 lc_02 = this.RR();
        this.ek("synchronized");
        this.em("(");
        jy_2 jy_22 = this.Rx().aAr();
        this.em(")");
        return new vu_2(lc_02, jy_22, this.QZ());
    }

    public akE Ro() {
        lc_0 lc_02 = this.RR();
        this.ek("return");
        jy_2 jy_22 = this.el(";") ? null : this.Rx().aAr();
        this.em(";");
        return new jr_1(lc_02, jy_22);
    }

    public akE Rp() {
        lc_0 lc_02 = this.RR();
        this.ek("throw");
        jy_2 jy_22 = this.Rx().aAr();
        this.em(";");
        return new v_0(lc_02, jy_22);
    }

    public akE Rq() {
        lc_0 lc_02 = this.RR();
        this.ek("break");
        String string = null;
        if (this.bcq.awY().lz()) {
            string = this.RW();
        }
        this.em(";");
        return new gl_1(lc_02, string);
    }

    public akE Rr() {
        lc_0 lc_02 = this.RR();
        this.ek("continue");
        String string = null;
        if (this.bcq.awY().lz()) {
            string = this.RW();
        }
        this.em(";");
        return new Ms(lc_02, string);
    }

    public akE Rs() {
        lc_0 lc_02 = this.RR();
        this.em(";");
        return new ek_0(lc_02);
    }

    public jy_2[] Rt() {
        ArrayList<jy_2> arrayList = new ArrayList<jy_2>();
        while (true) {
            arrayList.add(this.Rx().aAr());
            if (!this.el(",")) break;
            this.RS();
        }
        return arrayList.toArray(new jy_2[arrayList.size()]);
    }

    public atu_0 Ru() {
        atu_0 atu_02;
        aFA aFA2 = this.bcq.awY();
        int n2 = -1;
        if (aFA2.dN("byte")) {
            n2 = 1;
        } else if (aFA2.dN("short")) {
            n2 = 2;
        } else if (aFA2.dN("char")) {
            n2 = 3;
        } else if (aFA2.dN("int")) {
            n2 = 4;
        } else if (aFA2.dN("long")) {
            n2 = 5;
        } else if (aFA2.dN("float")) {
            n2 = 6;
        } else if (aFA2.dN("double")) {
            n2 = 7;
        } else if (aFA2.dN("boolean")) {
            n2 = 8;
        }
        if (n2 != -1) {
            atu_02 = new gw_1(aFA2.aP(), n2);
            this.RS();
        } else {
            atu_02 = this.Rv();
        }
        for (int j = this.QX(); j > 0; --j) {
            atu_02 = new ahe_1(atu_02);
        }
        return atu_02;
    }

    public ft Rv() {
        return new ft(this.RR(), this.QQ());
    }

    public ft[] Rw() {
        ArrayList<ft> arrayList = new ArrayList<ft>();
        arrayList.add(this.Rv());
        while (this.el(",")) {
            this.RS();
            arrayList.add(this.Rv());
        }
        return arrayList.toArray(new ft[arrayList.size()]);
    }

    public alb_0 Rx() {
        return this.Ry();
    }

    public alb_0 Ry() {
        alb_0 alb_02 = this.Rz();
        if (this.k(new String[]{"=", "+=", "-=", "*=", "/=", "&=", "|=", "^=", "%=", "<<=", ">>=", ">>>="})) {
            lc_0 lc_02 = this.RR();
            String string = this.RU();
            anw anw2 = alb_02.aAs();
            jy_2 jy_22 = this.Ry().aAr();
            return new ayN(lc_02, anw2, string, jy_22);
        }
        return alb_02;
    }

    public alb_0 Rz() {
        alb_0 alb_02 = this.RA();
        if (!this.el("?")) {
            return alb_02;
        }
        lc_0 lc_02 = this.RR();
        this.RS();
        jy_2 jy_22 = alb_02.aAr();
        jy_2 jy_23 = this.Rx().aAr();
        this.em(":");
        jy_2 jy_24 = this.Rz().aAr();
        return new acq_0(lc_02, jy_22, jy_23, jy_24);
    }

    public alb_0 RA() {
        alb_0 alb_02 = this.RB();
        while (this.el("||")) {
            lc_0 lc_02 = this.RR();
            this.RS();
            alb_02 = new rr_2(lc_02, alb_02.aAr(), "||", this.RB().aAr());
        }
        return alb_02;
    }

    public alb_0 RB() {
        alb_0 alb_02 = this.RC();
        while (this.el("&&")) {
            lc_0 lc_02 = this.RR();
            this.RS();
            alb_02 = new rr_2(lc_02, alb_02.aAr(), "&&", this.RC().aAr());
        }
        return alb_02;
    }

    public alb_0 RC() {
        alb_0 alb_02 = this.RD();
        while (this.el("|")) {
            lc_0 lc_02 = this.RR();
            this.RS();
            alb_02 = new rr_2(lc_02, alb_02.aAr(), "|", this.RD().aAr());
        }
        return alb_02;
    }

    public alb_0 RD() {
        alb_0 alb_02 = this.RE();
        while (this.el("^")) {
            lc_0 lc_02 = this.RR();
            this.RS();
            alb_02 = new rr_2(lc_02, alb_02.aAr(), "^", this.RE().aAr());
        }
        return alb_02;
    }

    public alb_0 RE() {
        alb_0 alb_02 = this.RF();
        while (this.el("&")) {
            lc_0 lc_02 = this.RR();
            this.RS();
            alb_02 = new rr_2(lc_02, alb_02.aAr(), "&", this.RF().aAr());
        }
        return alb_02;
    }

    public alb_0 RF() {
        alb_0 alb_02 = this.RG();
        while (this.k(new String[]{"==", "!="})) {
            alb_02 = new rr_2(this.RR(), alb_02.aAr(), this.RU(), this.RG().aAr());
        }
        return alb_02;
    }

    public alb_0 RG() {
        alb_0 alb_02 = this.RH();
        while (true) {
            if (this.ej("instanceof")) {
                lc_0 lc_02 = this.RR();
                this.RS();
                alb_02 = new p_0(lc_02, alb_02.aAr(), this.Ru());
                continue;
            }
            if (!this.k(new String[]{"<", ">", "<=", ">="})) break;
            alb_02 = new rr_2(this.RR(), alb_02.aAr(), this.RU(), this.RH().aAr());
        }
        return alb_02;
    }

    public alb_0 RH() {
        alb_0 alb_02 = this.RI();
        while (this.k(new String[]{"<<", ">>", ">>>"})) {
            alb_02 = new rr_2(this.RR(), alb_02.aAr(), this.RU(), this.RI().aAr());
        }
        return alb_02;
    }

    public alb_0 RI() {
        alb_0 alb_02 = this.RJ();
        while (this.k(new String[]{"+", "-"})) {
            alb_02 = new rr_2(this.RR(), alb_02.aAr(), this.RU(), this.RJ().aAr());
        }
        return alb_02;
    }

    public alb_0 RJ() {
        alb_0 alb_02 = this.RK();
        while (this.k(new String[]{"*", "/", "%"})) {
            alb_02 = new rr_2(this.RR(), alb_02.aAr(), this.RU(), this.RK().aAr());
        }
        return alb_02;
    }

    public alb_0 RK() {
        if (this.k(new String[]{"++", "--"})) {
            return new afa_1(this.RR(), this.RU(), this.RK().aAs());
        }
        if (this.k(new String[]{"+", "-", "~", "!"})) {
            return new afk_2(this.RR(), this.RU(), this.RK().aAr());
        }
        alb_0 alb_02 = this.RL();
        while (this.k(new String[]{".", "["})) {
            alb_02 = this.g(alb_02);
        }
        while (this.k(new String[]{"++", "--"})) {
            alb_02 = new afa_1(this.RR(), alb_02.aAs(), this.RU());
        }
        return alb_02;
    }

    public alb_0 RL() {
        if (this.el("(")) {
            this.RS();
            if (this.j(new String[]{"boolean", "char", "byte", "short", "int", "long", "float", "double"})) {
                atu_0 atu_02 = this.Ru();
                int n2 = this.QX();
                this.em(")");
                for (int j = 0; j < n2; ++j) {
                    atu_02 = new ahe_1(atu_02);
                }
                return new agz_2(this.RR(), atu_02, this.RK().aAr());
            }
            alb_0 alb_02 = this.Rx();
            this.em(")");
            if (this.bcq.awY().isLiteral() || this.bcq.awY().lz() || this.k(new String[]{"(", "~", "!"}) || this.j(new String[]{"this", "super", "new"})) {
                return new agz_2(this.RR(), alb_02.aAq(), this.RK().aAr());
            }
            return new zS(alb_02.aP(), alb_02.aAr());
        }
        if (this.bcq.awY().isLiteral()) {
            return this.RQ();
        }
        if (this.bcq.awY().lz()) {
            lc_0 lc_02 = this.RR();
            String[] stringArray = this.QQ();
            if (this.el("(")) {
                return new La(this.RR(), stringArray.length == 1 ? null : new anM(lc_02, stringArray, stringArray.length - 1), stringArray[stringArray.length - 1], this.RO());
            }
            if (this.el("[") && this.bcq.awZ().hE("]")) {
                atu_0 atu_03 = new ft(lc_02, stringArray);
                int n3 = this.QX();
                for (int j = 0; j < n3; ++j) {
                    atu_03 = new ahe_1(atu_03);
                }
                if (this.el(".") && this.bcq.awZ().dN("class")) {
                    this.RS();
                    lc_0 lc_03 = this.RR();
                    this.RS();
                    return new agx_0(lc_03, atu_03);
                }
                return atu_03;
            }
            return new anM(this.RR(), stringArray);
        }
        if (this.ej("this")) {
            lc_0 lc_04 = this.RR();
            this.RS();
            if (this.el("(")) {
                return new yn_1(lc_04, this.RO());
            }
            return new aLs(lc_04);
        }
        if (this.ej("super")) {
            this.RS();
            if (this.el("(")) {
                return new akl_0(this.RR(), null, this.RO());
            }
            this.em(".");
            String string = this.RW();
            if (this.el("(")) {
                return new ajs_2(this.RR(), string, this.RO());
            }
            return new GT(this.RR(), null, string);
        }
        if (this.ej("new")) {
            lc_0 lc_05 = this.RR();
            this.RS();
            atu_0 atu_04 = this.Ru();
            if (atu_04 instanceof ahe_1) {
                return new aFz(lc_05, (ahe_1)atu_04, this.QU());
            }
            if (atu_04 instanceof ft && this.el("(")) {
                jy_2[] jy_2Array = this.RO();
                if (this.el("{")) {
                    uy_1 uy_12 = new uy_1(this.RR(), atu_04);
                    this.b(uy_12);
                    return new afi_2(lc_05, null, uy_12, jy_2Array);
                }
                return new Nl(lc_05, (jy_2)null, atu_04, jy_2Array);
            }
            return new zj(lc_05, atu_04, this.RM(), this.QX());
        }
        if (this.j(new String[]{"boolean", "char", "byte", "short", "int", "long", "float", "double"})) {
            atu_0 atu_05 = this.Ru();
            int n4 = this.QX();
            for (int j = 0; j < n4; ++j) {
                atu_05 = new ahe_1(atu_05);
            }
            if (this.el(".") && this.bcq.awZ().dN("class")) {
                this.RS();
                lc_0 lc_06 = this.RR();
                this.RS();
                return new agx_0(lc_06, atu_05);
            }
            return atu_05;
        }
        if (this.ej("void")) {
            this.RS();
            if (this.el(".") && this.bcq.awZ().dN("class")) {
                this.RS();
                lc_0 lc_07 = this.RR();
                this.RS();
                return new agx_0(lc_07, new gw_1(lc_07, 0));
            }
            this.j("\"void\" encountered in wrong context");
        }
        this.j("Unexpected token \"" + this.bcq.awY() + "\" in primary");
        return null;
    }

    public alb_0 g(alb_0 alb_02) {
        Object object;
        Object object2;
        if (this.el(".")) {
            this.RS();
            if (this.bcq.awY().lz()) {
                String string = this.RW();
                if (this.el("(")) {
                    return new La(this.RR(), alb_02.aAr(), string, this.RO());
                }
                return new aai_2(this.RR(), alb_02.aAr(), string);
            }
            if (this.ej("this")) {
                lc_0 lc_02 = this.RR();
                this.RS();
                return new xj_1(lc_02, alb_02.aAq());
            }
            if (this.ej("super")) {
                object2 = this.RR();
                this.RS();
                if (this.el("(")) {
                    return new akl_0((lc_0)object2, alb_02.aAr(), this.RO());
                }
                this.em(".");
                object = this.RW();
                if (this.el("(")) {
                    this.j("Qualified superclass method invocation NYI");
                } else {
                    return new GT((lc_0)object2, alb_02.aAq(), (String)object);
                }
            }
            if (this.ej("new")) {
                object2 = alb_02.oj();
                object = this.RR();
                this.RS();
                String string = this.RW();
                cg_1 cg_12 = new cg_1((lc_0)object, (jy_2)object2, string);
                jy_2[] jy_2Array = this.RO();
                if (this.el("{")) {
                    uy_1 uy_12 = new uy_1(this.RR(), cg_12);
                    this.b(uy_12);
                    return new afi_2((lc_0)object, (jy_2)object2, uy_12, jy_2Array);
                }
                return new Nl((lc_0)object, (jy_2)object2, cg_12, jy_2Array);
            }
            if (this.ej("class")) {
                object2 = this.RR();
                this.RS();
                return new agx_0((lc_0)object2, alb_02.aAq());
            }
            this.j("Unexpected selector \"" + this.bcq.awY() + "\" after \".\"");
        }
        if (this.el("[")) {
            object2 = this.RR();
            this.RS();
            object = this.Rx().aAr();
            this.em("]");
            return new Wh((lc_0)object2, alb_02.aAr(), (jy_2)object);
        }
        this.j("Unexpected token \"" + this.bcq.awY() + "\" in selector");
        return null;
    }

    public jy_2[] RM() {
        ArrayList<jy_2> arrayList = new ArrayList<jy_2>();
        arrayList.add(this.RN());
        while (this.el("[") && !this.bcq.awZ().hE("]")) {
            arrayList.add(this.RN());
        }
        return arrayList.toArray(new jy_2[arrayList.size()]);
    }

    public jy_2 RN() {
        this.em("[");
        jy_2 jy_22 = this.Rx().aAr();
        this.em("]");
        return jy_22;
    }

    public jy_2[] RO() {
        this.em("(");
        if (this.el(")")) {
            this.RS();
            return new jy_2[0];
        }
        jy_2[] jy_2Array = this.RP();
        this.em(")");
        return jy_2Array;
    }

    public jy_2[] RP() {
        ArrayList<jy_2> arrayList = new ArrayList<jy_2>();
        while (true) {
            arrayList.add(this.Rx().aAr());
            if (!this.el(",")) break;
            this.RS();
        }
        return arrayList.toArray(new jy_2[arrayList.size()]);
    }

    public alb_0 RQ() {
        aFA aFA2 = this.bcq.awX();
        if (!aFA2.isLiteral()) {
            this.j("Literal expected");
        }
        return new aow_0(aFA2.aP(), aFA2.sA());
    }

    public lc_0 RR() {
        return this.bcq.RR();
    }

    public void RS() {
        this.bcq.awX();
    }

    public boolean RT() {
        return this.bcq.awY().NU();
    }

    public boolean ej(String string) {
        return this.bcq.awY().dN(string);
    }

    public boolean j(String[] stringArray) {
        return this.bcq.awY().i(stringArray);
    }

    public void ek(String string) {
        if (!this.bcq.awX().dN(string)) {
            this.j("\"" + string + "\" expected");
        }
    }

    public boolean el(String string) {
        return this.bcq.awY().hE(string);
    }

    public boolean k(String[] stringArray) {
        return this.bcq.awY().r(stringArray);
    }

    public String RU() {
        aFA aFA2 = this.bcq.awX();
        if (!aFA2.aub()) {
            this.j("Operator expected");
        }
        return aFA2.auc();
    }

    public void em(String string) {
        if (!this.bcq.awX().hE(string)) {
            this.j("Operator \"" + string + "\" expected");
        }
    }

    public boolean RV() {
        return this.bcq.awY().lz();
    }

    public String RW() {
        aFA aFA2 = this.bcq.awX();
        if (!aFA2.lz()) {
            this.j("Identifier expected");
        }
        return aFA2.getIdentifier();
    }

    public akE RX() {
        jy_2 jy_22 = this.Rx().aAr();
        this.em(";");
        return new cr(jy_22);
    }

    private void d(String string, lc_0 lc_02) {
        if (!Character.isLowerCase(string.charAt(0))) {
            this.a("UPN", "Package name \"" + string + "\" does not begin with a lower-case letter (see JLS2 6.8.1)", lc_02);
            return;
        }
        for (int j = 0; j < string.length(); ++j) {
            char c = string.charAt(j);
            if (Character.isLowerCase(c) || c == '_' || c == '.') continue;
            this.a("PPN", "Poorly chosen package name \"" + string + "\" contains bad character '" + c + "'", lc_02);
            return;
        }
    }

    private void e(String string, lc_0 lc_02) {
        if (!Character.isUpperCase(string.charAt(0))) {
            this.a("UCOIN1", "Class or interface name \"" + string + "\" does not begin with an upper-case letter (see JLS2 6.8.2)", lc_02);
            return;
        }
        for (int j = 0; j < string.length(); ++j) {
            char c = string.charAt(j);
            if (Character.isLetter(c) || Character.isDigit(c)) continue;
            this.a("UCOIN", "Class or interface name \"" + string + "\" contains unconventional character \"" + c + "\" (see JLS2 6.8.2)", lc_02);
            return;
        }
    }

    private void f(String string, lc_0 lc_02) {
        if (!Character.isLowerCase(string.charAt(0))) {
            this.a("UMN1", "Method name \"" + string + "\" does not begin with a lower-case letter (see JLS2 6.8.3)", lc_02);
            return;
        }
        for (int j = 0; j < string.length(); ++j) {
            char c = string.charAt(j);
            if (Character.isLetter(c) || Character.isDigit(c)) continue;
            this.a("UMN", "Method name \"" + string + "\" contains unconventional character \"" + c + "\" (see JLS2 6.8.3)", lc_02);
            return;
        }
    }

    private void g(String string, lc_0 lc_02) {
        if (Character.isUpperCase(string.charAt(0))) {
            for (int j = 0; j < string.length(); ++j) {
                char c = string.charAt(j);
                if (Character.isUpperCase(c) || Character.isDigit(c) || c == '_') continue;
                this.a("UCN", "Constant name \"" + string + "\" contains unconventional character \"" + c + "\" (see JLS2 6.8.5)", lc_02);
                return;
            }
        } else if (Character.isLowerCase(string.charAt(0))) {
            for (int j = 0; j < string.length(); ++j) {
                char c = string.charAt(j);
                if (Character.isLetter(c) || Character.isDigit(c)) continue;
                this.a("UFN", "Field name \"" + string + "\" contains unconventional character \"" + c + "\" (see JLS2 6.8.4)", lc_02);
                return;
            }
        } else {
            this.a("UFN1", "\"" + string + "\" is neither a conventional field name (JLS2 6.8.4) nor a conventional constant name (JLS2 6.8.5)", lc_02);
        }
    }

    private void h(String string, lc_0 lc_02) {
        if (!Character.isLowerCase(string.charAt(0))) {
            this.a("ULVN1", "Local variable name \"" + string + "\" does not begin with a lower-case letter (see JLS2 6.8.6)", lc_02);
            return;
        }
        for (int j = 0; j < string.length(); ++j) {
            char c = string.charAt(j);
            if (Character.isLetter(c) || Character.isDigit(c)) continue;
            this.a("ULVN", "Local variable name \"" + string + "\" contains unconventional character \"" + c + "\" (see JLS2 6.8.6)", lc_02);
            return;
        }
    }

    public void a(aeo_1 aeo_12) {
        this.aEE = aeo_12;
    }

    private void a(String string, String string2, lc_0 lc_02) {
        if (this.aEE != null) {
            this.aEE.b(string, string2, lc_02);
        }
    }

    protected final void j(String string) {
        throw new ajy_2(string, this.RR());
    }

    private static String join(String[] stringArray, String string) {
        if (stringArray == null) {
            return "(null)";
        }
        if (stringArray.length == 0) {
            return "(zero length array)";
        }
        StringBuffer stringBuffer = new StringBuffer(stringArray[0]);
        for (int j = 1; j < stringArray.length; ++j) {
            stringBuffer.append(string).append(stringArray[j]);
        }
        return stringBuffer.toString();
    }
}

