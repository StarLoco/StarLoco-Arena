/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import com.ankamagames.dofusarena.client.DofusArenaClientInstance;
import com.ankamagames.dofusarena.client.DofusArenaReplayPlayerInstance;
import java.util.Iterator;
import java.util.StringTokenizer;
import org.apache.log4j.Logger;

/*
 * Renamed from tu
 */
public class tu_1 {
    private afT amS;
    private int amT;
    private static boolean amU = false;
    private boolean amV = false;
    private final String eA;
    private String fZ;
    private static Logger a = Logger.getLogger(tu_1.class);

    public tu_1(String string) {
        this.amS = new afT(string);
        this.eA = string;
        this.fZ = "";
    }

    public void zt() {
        String string;
        if (amU) {
            add_1.aOG().f(aon_0.aYc().getString("error.loading"), 102, 1);
            return;
        }
        amU = true;
        while ((string = this.amS.readLine()) != null) {
            StringTokenizer stringTokenizer = new StringTokenizer(string, "|");
            while (stringTokenizer.hasMoreElements()) {
                int n2 = Integer.parseInt(stringTokenizer.nextToken());
                switch (n2) {
                    case -1: {
                        this.fZ = stringTokenizer.nextToken();
                        gj_1.a(this.eA, gj_1.aj(this.fZ), gj_1.ak(this.fZ));
                        break;
                    }
                    case 0: {
                        this.a(stringTokenizer);
                        break;
                    }
                    case 100: {
                        this.cp(stringTokenizer.nextToken());
                        break;
                    }
                    case 102: {
                        this.b(stringTokenizer);
                        break;
                    }
                    case 200: {
                        this.a(Integer.parseInt(stringTokenizer.nextToken()), Float.parseFloat(stringTokenizer.nextToken()), Float.parseFloat(stringTokenizer.nextToken()), Short.parseShort(stringTokenizer.nextToken()));
                        break;
                    }
                    case 300: {
                        int n3 = Integer.parseInt(stringTokenizer.nextToken());
                        String string2 = stringTokenizer.nextToken();
                        long l2 = this.cq(string2);
                        int n4 = Integer.parseInt(stringTokenizer.nextToken());
                        int n5 = Integer.parseInt(stringTokenizer.nextToken());
                        double d = Double.parseDouble(stringTokenizer.nextToken());
                        qc_0 qc_02 = qc_0.valueOf(stringTokenizer.nextToken());
                        gw_0 gw_02 = new gw_0(0, 0, 0, l2, n4, n5, d, qc_02);
                        RO.aer().a(gw_02, n3);
                        break;
                    }
                    case 401: {
                        this.j(stringTokenizer);
                        break;
                    }
                    case 402: {
                        this.k(stringTokenizer);
                        break;
                    }
                    case 407: {
                        this.n(stringTokenizer);
                        break;
                    }
                    case 400: {
                        this.f(stringTokenizer);
                        break;
                    }
                    case 403: {
                        this.c(stringTokenizer);
                        break;
                    }
                    case 405: {
                        this.e(stringTokenizer);
                        break;
                    }
                    case 406: {
                        this.d(stringTokenizer);
                        break;
                    }
                    case 408: {
                        this.g(stringTokenizer);
                        break;
                    }
                    case 409: {
                        this.h(stringTokenizer);
                        break;
                    }
                    case 412: {
                        this.l(stringTokenizer);
                        break;
                    }
                    case 411: {
                        this.m(stringTokenizer);
                        break;
                    }
                    case 410: {
                        this.i(stringTokenizer);
                        break;
                    }
                    case 500: {
                        this.o(stringTokenizer);
                        this.amV = true;
                        break;
                    }
                    case 413: {
                        this.p(stringTokenizer);
                        break;
                    }
                    case 414: {
                        this.q(stringTokenizer);
                        break;
                    }
                    case 415: {
                        this.r(stringTokenizer);
                        break;
                    }
                    case 416: {
                        this.s(stringTokenizer);
                        break;
                    }
                    case 417: {
                        this.t(stringTokenizer);
                        break;
                    }
                    case 418: {
                        this.u(stringTokenizer);
                        break;
                    }
                }
            }
        }
        boolean bl2 = gj_1.al(this.fZ);
        azs_0.aLV().g("replayIdentificationCertificateIcon", bl2);
        azs_0.aLV().g("replayIdentificationCertificateText", bl2);
        if (!this.amV && apN.aDK().aDL() != null) {
            aaa aaa2 = new aaa(0, 0, 0, ug_2.bQg, ug_2.bQg, apN.aDK().aDL().getDuration());
            RO.aer().a(aaa2, RO.aer().aev() + ny_2.Qp);
        }
    }

    private void a(StringTokenizer stringTokenizer) {
        stringTokenizer.nextToken();
        this.amT = Integer.parseInt(stringTokenizer.nextToken());
        int n2 = Integer.parseInt(stringTokenizer.nextToken());
        for (int j = 0; j < n2 * 3 + 1; ++j) {
            stringTokenizer.nextToken();
        }
    }

    private void cp(String string) {
        StringTokenizer stringTokenizer = new StringTokenizer(string, "/");
        byte[] byArray = new byte[stringTokenizer.countTokens()];
        for (int j = 0; j < byArray.length; ++j) {
            Byte by = Byte.parseByte(stringTokenizer.nextToken());
            byArray[j] = by;
        }
        adu_0 adu_02 = aat_2.ac(byArray);
        apN.aDK().a(adu_02);
        apN.aDK().a(hg_1.Tr());
    }

    private void b(StringTokenizer stringTokenizer) {
        int n2 = Integer.parseInt(stringTokenizer.nextToken());
        for (int j = 0; j < n2; ++j) {
            stringTokenizer.nextToken();
        }
    }

    private void c(StringTokenizer stringTokenizer) {
        int n2 = Integer.parseInt(stringTokenizer.nextToken());
        int n3 = Integer.parseInt(stringTokenizer.nextToken());
        byte by = Byte.parseByte(stringTokenizer.nextToken());
        int n4 = Integer.parseInt(stringTokenizer.nextToken());
        long l2 = Long.parseLong(stringTokenizer.nextToken());
        lC lC2 = new lC(n3, by, n4);
        lC2.bC(l2);
        RO.aer().a(lC2, n2);
    }

    private void d(StringTokenizer stringTokenizer) {
        int n2 = Integer.parseInt(stringTokenizer.nextToken());
        int n3 = Integer.parseInt(stringTokenizer.nextToken());
        byte by = Byte.parseByte(stringTokenizer.nextToken());
        int n4 = Integer.parseInt(stringTokenizer.nextToken());
        int n5 = Integer.parseInt(stringTokenizer.nextToken());
        boolean bl2 = Boolean.parseBoolean(stringTokenizer.nextToken());
        boolean bl3 = Boolean.parseBoolean(stringTokenizer.nextToken());
        long l2 = Long.parseLong(stringTokenizer.nextToken());
        int n6 = Integer.parseInt(stringTokenizer.nextToken());
        int n7 = Integer.parseInt(stringTokenizer.nextToken());
        short s = Short.parseShort(stringTokenizer.nextToken());
        boolean bl4 = Boolean.parseBoolean(stringTokenizer.nextToken());
        abk_1 abk_12 = new abk_1(n3, by, n4, bl2, bl3, l2, n5, n6, n7, s, bl4);
        RO.aer().a(abk_12, n2);
    }

    private void e(StringTokenizer stringTokenizer) {
        int n2 = Integer.parseInt(stringTokenizer.nextToken());
        int n3 = Integer.parseInt(stringTokenizer.nextToken());
        byte by = Byte.parseByte(stringTokenizer.nextToken());
        int n4 = Integer.parseInt(stringTokenizer.nextToken());
        boolean bl2 = Boolean.parseBoolean(stringTokenizer.nextToken());
        boolean bl3 = Boolean.parseBoolean(stringTokenizer.nextToken());
        long l2 = Long.parseLong(stringTokenizer.nextToken());
        int n5 = Integer.parseInt(stringTokenizer.nextToken());
        int n6 = Integer.parseInt(stringTokenizer.nextToken());
        short s = Short.parseShort(stringTokenizer.nextToken());
        akg_2 akg_22 = new akg_2(n3, by, n4, bl2, bl3, l2, n5, n6, s);
        RO.aer().a(akg_22, n2);
    }

    private void f(StringTokenizer stringTokenizer) {
        int n2 = Integer.parseInt(stringTokenizer.nextToken());
        int n3 = Integer.parseInt(stringTokenizer.nextToken());
        byte by = Byte.parseByte(stringTokenizer.nextToken());
        int n4 = Integer.parseInt(stringTokenizer.nextToken());
        long l2 = Long.parseLong(stringTokenizer.nextToken());
        arh_0 arh_02 = new arh_0(Integer.parseInt(stringTokenizer.nextToken()));
        for (int j = 0; j < arh_02.aEF(); ++j) {
            StringTokenizer stringTokenizer2 = new StringTokenizer(stringTokenizer.nextToken(), ",");
            arh_02.b(j, Integer.parseInt(stringTokenizer2.nextToken()), Integer.parseInt(stringTokenizer2.nextToken()), Short.parseShort(stringTokenizer2.nextToken()));
        }
        HB hB = new HB(n3, by, n4, l2, arh_02);
        hB.bC(l2);
        RO.aer().a(hB, n2);
    }

    private void g(StringTokenizer stringTokenizer) {
        boolean bl2;
        int n2 = Integer.parseInt(stringTokenizer.nextToken());
        int n3 = Integer.parseInt(stringTokenizer.nextToken());
        byte by = Byte.parseByte(stringTokenizer.nextToken());
        int n4 = Integer.parseInt(stringTokenizer.nextToken());
        int n5 = Integer.parseInt(stringTokenizer.nextToken());
        StringTokenizer stringTokenizer2 = new StringTokenizer(stringTokenizer.nextToken(), "/");
        byte[] byArray = new byte[stringTokenizer2.countTokens()];
        for (bl2 = false; bl2 < byArray.length; bl2 += 1) {
            byArray[bl2] = Byte.parseByte(stringTokenizer2.nextToken());
        }
        bl2 = Boolean.parseBoolean(stringTokenizer.nextToken());
        int n6 = Integer.parseInt(stringTokenizer.nextToken());
        int n7 = Integer.parseInt(stringTokenizer.nextToken());
        el_2 el_22 = (el_2)mh_2.YJ().cr(n5);
        mv_0 mv_02 = new mv_0(n3, by, n4, el_22, byArray, bl2, 0);
        mv_02.fs(n6);
        mv_02.cj(n7);
        RO.aer().a(mv_02, n2);
    }

    private void h(StringTokenizer stringTokenizer) {
        int n2 = Integer.parseInt(stringTokenizer.nextToken());
        int n3 = Integer.parseInt(stringTokenizer.nextToken());
        byte by = Byte.parseByte(stringTokenizer.nextToken());
        int n4 = Integer.parseInt(stringTokenizer.nextToken());
        int n5 = Integer.parseInt(stringTokenizer.nextToken());
        boolean bl2 = Boolean.parseBoolean(stringTokenizer.nextToken());
        boolean bl3 = Boolean.parseBoolean(stringTokenizer.nextToken());
        long l2 = Long.parseLong(stringTokenizer.nextToken());
        int n6 = Integer.parseInt(stringTokenizer.nextToken());
        int n7 = Integer.parseInt(stringTokenizer.nextToken());
        short s = Short.parseShort(stringTokenizer.nextToken());
        ve_0 ve_02 = (ve_0)aca_0.aOq().E(n5);
        tz_1 tz_12 = new tz_1(n3, by, n4, ve_02, bl2, bl3, l2, n6, n7, s);
        RO.aer().a(tz_12, n2);
    }

    private void i(StringTokenizer stringTokenizer) {
        int n2 = Integer.parseInt(stringTokenizer.nextToken());
        long l2 = Long.parseLong(stringTokenizer.nextToken());
        int n3 = Integer.parseInt(stringTokenizer.nextToken());
        int n4 = Integer.parseInt(stringTokenizer.nextToken());
        el_2 el_22 = (el_2)mh_2.YJ().cr(n4);
        StringTokenizer stringTokenizer2 = new StringTokenizer(stringTokenizer.nextToken(), "/");
        byte[] byArray = new byte[stringTokenizer2.countTokens()];
        for (int j = 0; j < byArray.length; ++j) {
            byArray[j] = Byte.parseByte(stringTokenizer2.nextToken());
        }
        akq_0 akq_02 = new akq_0(0, 0, 0, l2, n3, byArray, el_22);
        RO.aer().a(akq_02, n2);
    }

    private void j(StringTokenizer stringTokenizer) {
        int n2 = Integer.parseInt(stringTokenizer.nextToken());
        int n3 = Integer.parseInt(stringTokenizer.nextToken());
        byte by = Byte.parseByte(stringTokenizer.nextToken());
        int n4 = Integer.parseInt(stringTokenizer.nextToken());
        long l2 = Long.parseLong(stringTokenizer.nextToken());
        tO tO2 = (tO)cw_1.eO().w(l2);
        yb yb2 = new yb(n3, by, n4, tO2);
        RO.aer().a(yb2, n2);
    }

    private void k(StringTokenizer stringTokenizer) {
        int n2 = Integer.parseInt(stringTokenizer.nextToken());
        int n3 = Integer.parseInt(stringTokenizer.nextToken());
        byte by = Byte.parseByte(stringTokenizer.nextToken());
        int n4 = Integer.parseInt(stringTokenizer.nextToken());
        long l2 = Long.parseLong(stringTokenizer.nextToken());
        vu_0 vu_02 = new vu_0(n3, by, n4, l2);
        RO.aer().a(vu_02, n2);
    }

    private void l(StringTokenizer stringTokenizer) {
        int n2 = Integer.parseInt(stringTokenizer.nextToken());
        int n3 = Integer.parseInt(stringTokenizer.nextToken());
        byte by = Byte.parseByte(stringTokenizer.nextToken());
        int n4 = Integer.parseInt(stringTokenizer.nextToken());
        qc_0 qc_02 = qc_0.valueOf(stringTokenizer.nextToken());
        long l2 = Long.parseLong(stringTokenizer.nextToken());
        wl_2 wl_22 = new wl_2(n3, by, n4, qc_02);
        wl_22.bB(l2);
        RO.aer().a(wl_22, n2);
    }

    private void m(StringTokenizer stringTokenizer) {
        int n2 = Integer.parseInt(stringTokenizer.nextToken());
        int n3 = Integer.parseInt(stringTokenizer.nextToken());
        byte by = Byte.parseByte(stringTokenizer.nextToken());
        int n4 = Integer.parseInt(stringTokenizer.nextToken());
        long l2 = Long.parseLong(stringTokenizer.nextToken());
        long l3 = Long.parseLong(stringTokenizer.nextToken());
        xo_1 xo_12 = new xo_1(n3, by, n4);
        xo_12.bB(l2);
        xo_12.bC(l3);
        RO.aer().a(xo_12, n2);
    }

    private void n(StringTokenizer stringTokenizer) {
        int n2 = Integer.parseInt(stringTokenizer.nextToken());
        int n3 = Integer.parseInt(stringTokenizer.nextToken());
        byte by = Byte.parseByte(stringTokenizer.nextToken());
        int n4 = Integer.parseInt(stringTokenizer.nextToken());
        long l2 = Long.parseLong(stringTokenizer.nextToken());
        act act2 = new act(n3, by, n4, l2);
        RO.aer().a(act2, n2);
    }

    private void o(StringTokenizer stringTokenizer) {
        int n2;
        int n3 = Integer.parseInt(stringTokenizer.nextToken());
        long[] lArray = new long[Integer.parseInt(stringTokenizer.nextToken())];
        for (int j = 0; j < lArray.length; ++j) {
            lArray[j] = this.cq(stringTokenizer.nextToken());
        }
        long[] lArray2 = new long[Integer.parseInt(stringTokenizer.nextToken())];
        for (n2 = 0; n2 < lArray2.length; ++n2) {
            lArray2[n2] = this.cq(stringTokenizer.nextToken());
        }
        n2 = Integer.parseInt(stringTokenizer.nextToken());
        aaa aaa2 = new aaa(0, 0, 0, lArray, lArray2, n2);
        RO.aer().a(aaa2, n3);
    }

    private void p(StringTokenizer stringTokenizer) {
        int n2 = Integer.parseInt(stringTokenizer.nextToken());
        int n3 = Integer.parseInt(stringTokenizer.nextToken());
        long[] lArray = new long[n3];
        String[] stringArray = new String[n3];
        for (int j = 0; j < n3; ++j) {
            String string;
            lArray[j] = Long.parseLong(stringTokenizer.nextToken());
            stringArray[j] = string = stringTokenizer.nextToken();
        }
        gy_0 gy_02 = new gy_0(0, 0, 0, stringArray, lArray);
        RO.aer().a(gy_02, n2);
    }

    private void q(StringTokenizer stringTokenizer) {
        int n2 = Integer.parseInt(stringTokenizer.nextToken());
        long l2 = Long.parseLong(stringTokenizer.nextToken());
        arh_0 arh_02 = new arh_0(Integer.parseInt(stringTokenizer.nextToken()));
        for (int j = 0; j < arh_02.aEF(); ++j) {
            StringTokenizer stringTokenizer2 = new StringTokenizer(stringTokenizer.nextToken(), ",");
            arh_02.b(j, Integer.parseInt(stringTokenizer2.nextToken()), Integer.parseInt(stringTokenizer2.nextToken()), Short.parseShort(stringTokenizer2.nextToken()));
        }
        abg_1 abg_12 = new abg_1(0, 0, 0, l2, arh_02);
        RO.aer().a(abg_12, n2);
    }

    private void r(StringTokenizer stringTokenizer) {
        int n2 = Integer.parseInt(stringTokenizer.nextToken());
        long l2 = Long.parseLong(stringTokenizer.nextToken());
        int n3 = Integer.parseInt(stringTokenizer.nextToken());
        ahv_1 ahv_12 = new ahv_1(0, 0, 0, l2, n3);
        RO.aer().a(ahv_12, n2);
    }

    private void s(StringTokenizer stringTokenizer) {
        int n2 = Integer.parseInt(stringTokenizer.nextToken());
        int n3 = Integer.parseInt(stringTokenizer.nextToken());
        int n4 = Integer.parseInt(stringTokenizer.nextToken());
        int n5 = Integer.parseInt(stringTokenizer.nextToken());
        short s = Short.parseShort(stringTokenizer.nextToken());
        gq_1 gq_12 = new gq_1(0, 0, 0, n3, n4, n5, s);
        RO.aer().a(gq_12, n2);
    }

    private void t(StringTokenizer stringTokenizer) {
        int n2 = Integer.parseInt(stringTokenizer.nextToken());
        int n3 = Integer.parseInt(stringTokenizer.nextToken());
        int n4 = Integer.parseInt(stringTokenizer.nextToken());
        int n5 = Integer.parseInt(stringTokenizer.nextToken());
        short s = Short.parseShort(stringTokenizer.nextToken());
        dt_1 dt_12 = new dt_1(0, 0, 0, n3, n4, n5, s);
        RO.aer().a(dt_12, n2);
    }

    private void u(StringTokenizer stringTokenizer) {
        int n2 = Integer.parseInt(stringTokenizer.nextToken());
        int n3 = Integer.parseInt(stringTokenizer.nextToken());
        byte by = Byte.parseByte(stringTokenizer.nextToken());
        int n4 = Integer.parseInt(stringTokenizer.nextToken());
        long l2 = Long.parseLong(stringTokenizer.nextToken());
        StringTokenizer stringTokenizer2 = new StringTokenizer(stringTokenizer.nextToken(), ",");
        ry ry2 = new ry(Integer.parseInt(stringTokenizer2.nextToken()), Integer.parseInt(stringTokenizer2.nextToken()), Short.parseShort(stringTokenizer2.nextToken()));
        qc_0 qc_02 = qc_0.valueOf(stringTokenizer.nextToken());
        avJ avJ2 = new avJ(n3, by, n4, l2, ry2, qc_02);
        RO.aer().a(avJ2, n2);
    }

    private void a(int n2, float f, float f2, short s) {
        qs_2 qs_22 = DofusArenaClientInstance.yl().YP();
        if (qs_22 == null) {
            qs_22 = DofusArenaReplayPlayerInstance.XY().YP();
        }
        qs_22.ao(false);
        nk_0.aaq().ce(2L);
        xx_1.ai((short)n2);
        et_0 et_02 = new et_0(f, f2, s);
        qs_22.d(et_02);
        qs_22.vs();
        hc_2.kI().k("common", false);
        hc_2.kI().k("world", false);
        hc_2.kI().k("fight", true);
        hc_2.kI().k("tutorial", true);
        qs_22.k(1.0);
        xx_1.ai((short)n2);
        qs_22.an(true);
        qs_22.bk(true);
    }

    public long cq(String string) {
        long l2 = 0L;
        try {
            l2 = Long.parseLong(string);
        }
        catch (NumberFormatException numberFormatException) {
            Iterator iterator = apN.aDK().aDL().aKj();
            while (iterator.hasNext()) {
                cl_1 cl_12 = (cl_1)iterator.next();
                if (!string.equalsIgnoreCase(cl_12.Ld())) continue;
                l2 = cl_12.Lb();
            }
        }
        return l2;
    }
}

