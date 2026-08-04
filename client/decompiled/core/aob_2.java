/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/*
 * Renamed from aOb
 */
public class aob_2
implements aho_0 {
    private static final String eah = "noPeriod";
    private static final String dfF = "day";
    private static final String eai = "week";
    private static final String dfG = "month";
    private static final String eaj = "year";
    public static final String eak = "calendar";
    public static final String eal = "monthesList";
    public static final String eam = "selectedMonth";
    public static final String ean = "selectedYear";
    public static final String eao = "selectedDay";
    public static final String eap = "selectedEndHour";
    public static final String eaq = "selectedHour";
    public static final String ear = "selectedEndMinute";
    public static final String eas = "selectedMinute";
    public static final String eat = "selectedMonth2";
    public static final String eau = "selectedYear2";
    public static final String eav = "selectedDay2";
    public static final String eaw = "selectedEndHour2";
    public static final String eax = "selectedHour2";
    public static final String eay = "selectedEndMinute2";
    public static final String eaz = "selectedMinute2";
    public static final String eaA = "selectedMonth3";
    public static final String eaB = "selectedYear3";
    public static final String eaC = "selectedDay3";
    public static final String eaD = "selectedEndHour3";
    public static final String eaE = "selectedHour3";
    public static final String eaF = "selectedEndMinute3";
    public static final String eaG = "selectedMinute3";
    public static final String eaH = "selectedMonth4";
    public static final String eaI = "selectedYear4";
    public static final String eaJ = "selectedDay4";
    public static final String eaK = "selectedEndHour4";
    public static final String eaL = "selectedHour4";
    public static final String eaM = "selectedEndMinute4";
    public static final String eaN = "selectedMinute4";
    public static final String eaO = "registrationMonth";
    public static final String eaP = "registrationYear";
    public static final String eaQ = "registrationDay";
    public static final String eaR = "registrationHour";
    public static final String eaS = "registrationMinute";
    public static final String eaT = "registrationMonth2";
    public static final String eaU = "registrationYear2";
    public static final String eaV = "registrationDay2";
    public static final String eaW = "registrationHour2";
    public static final String eaX = "registrationMinute2";
    public static final String eaY = "yearsList";
    public static final String eaZ = "hoursList";
    public static final String eba = "daysList";
    public static final String ebb = "minutesList";
    public static final String ebc = "minute";
    public static final String ebd = "selectedTournamentDefinition";
    public static final String ebe = "eventPeriod";
    public static final String ebf = "repetition";
    public static final String ebg = "useGamePeriod2";
    public static final String ebh = "useGamePeriod3";
    public static final String ebi = "useGamePeriod4";
    private static final String[] ce;
    private static final int ebj;
    private static final ArrayList ebk;
    private static final ArrayList ebl;
    private static final ArrayList ebm;
    private static final ArrayList ebn;
    private static final ArrayList ebo;
    private Calendar bfM = new GregorianCalendar(aon_0.aYc().Fd().getLocale());
    private Integer[] ebp = new Integer[6];
    private zw_0[] ebq = new zw_0[6];
    private Integer[] ebr = new Integer[6];
    private Integer[] ebs = new Integer[6];
    private Integer[] ebt = new Integer[6];
    private Integer[] ebu = new Integer[4];
    private Integer[] ebv = new Integer[4];
    private boolean[] ebw = new boolean[3];
    private Integer ebx = 1;
    private final jg_0 eby = new jg_0();
    private String ebz = "noPeriod";
    private int ebA = 0;

    public aob_2() {
        this.aXR();
    }

    public Calendar getCalendar() {
        return this.bfM;
    }

    public void aXR() {
        int n2;
        int n3;
        int n4;
        this.bfM.setTime(new Date());
        for (n4 = 0; n4 < this.ebp.length; ++n4) {
            this.ebp[n4] = (Integer)ebn.get(this.bfM.get(5) - 1);
        }
        for (n4 = 0; n4 < this.ebq.length; ++n4) {
            this.ebq[n4] = (zw_0)ebo.get(this.bfM.get(2) + ebo.size() - 12);
        }
        n4 = ebk.indexOf(this.bfM.get(1));
        for (n3 = 0; n3 < this.ebr.length; ++n3) {
            this.ebr[n3] = (Integer)ebk.get(n4);
        }
        n3 = ebl.indexOf(this.bfM.get(11));
        for (n2 = 0; n2 < this.ebs.length; ++n2) {
            this.ebs[n2] = (Integer)ebl.get(n3 + 1 % ebl.size());
        }
        this.ebs[4] = (Integer)ebl.get(n3 % ebl.size());
        this.ebs[5] = (Integer)ebl.get((n3 + 4) % ebl.size());
        for (n2 = 0; n2 < this.ebu.length; ++n2) {
            this.ebu[n2] = (Integer)ebl.get((n3 + 5) % ebl.size());
        }
        for (n2 = 0; n2 < this.ebt.length; ++n2) {
            this.ebt[n2] = 0;
        }
        this.ebt[4] = 55;
        this.ebt[5] = 55;
        for (n2 = 0; n2 < this.ebv.length; ++n2) {
            this.ebv[n2] = 0;
        }
        for (n2 = 0; n2 < this.ebw.length; ++n2) {
            this.ebw[n2] = false;
        }
    }

    public void a(acx_1 acx_12, int n2) {
        this.g((acx_12.getHours() + 1) % ebl.size(), n2, false);
        this.cr((acx_12.getHours() + 5) % ebl.size(), n2);
        this.cs(0, n2);
        this.ct(0, n2);
        this.co(acx_12.getDay(), n2);
        this.cq(acx_12.getYear(), n2);
        this.cp(acx_12.getMonth() - 1, n2);
    }

    public void co(int n2, int n3) {
        if (n3 == 0) {
            this.bfM.set(5, n2);
        }
        this.ebp[n3] = n2;
        switch (n3) {
            case 0: {
                azs_0.aLV().a((aho_0)this, eao);
                break;
            }
            case 1: {
                azs_0.aLV().a((aho_0)this, eav);
                break;
            }
            case 2: {
                azs_0.aLV().a((aho_0)this, eaC);
                break;
            }
            case 3: {
                azs_0.aLV().a((aho_0)this, eaJ);
                break;
            }
            case 4: {
                azs_0.aLV().a((aho_0)this, eaQ);
                break;
            }
            case 5: {
                azs_0.aLV().a((aho_0)this, eaV);
            }
        }
        azs_0.aLV().a((aho_0)this, eaZ);
    }

    public int pw(int n2) {
        return this.ebp[n2];
    }

    public void cp(int n2, int n3) {
        if (n3 == 0) {
            this.bfM.set(2, n2);
        }
        this.ebq[n3] = (zw_0)ebo.get(Math.max(n2 + ebo.size() - 12, 0));
        switch (n3) {
            case 0: {
                azs_0.aLV().a((aho_0)this, eaZ, eal, eam, eak);
                break;
            }
            case 1: {
                azs_0.aLV().a((aho_0)this, eaZ, eal, eat, eak);
                break;
            }
            case 2: {
                azs_0.aLV().a((aho_0)this, eaZ, eal, eaA, eak);
                break;
            }
            case 3: {
                azs_0.aLV().a((aho_0)this, eaZ, eal, eaH, eak);
                break;
            }
            case 4: {
                azs_0.aLV().a((aho_0)this, eaZ, eal, eaO, eak);
                break;
            }
            case 5: {
                azs_0.aLV().a((aho_0)this, eaZ, eal, eaT, eak);
            }
        }
    }

    public int px(int n2) {
        return this.ebq[n2].Hb();
    }

    public void cq(int n2, int n3) {
        if (n3 == 0) {
            this.bfM.set(1, n2);
        }
        int n4 = ebk.indexOf(n2);
        this.ebr[n3] = (Integer)ebk.get(n4);
        switch (n3) {
            case 0: {
                azs_0.aLV().a((aho_0)this, eaZ, eal, ean, eak);
                break;
            }
            case 1: {
                azs_0.aLV().a((aho_0)this, eaZ, eal, eau, eak);
                break;
            }
            case 2: {
                azs_0.aLV().a((aho_0)this, eaZ, eal, eaB, eak);
                break;
            }
            case 3: {
                azs_0.aLV().a((aho_0)this, eaZ, eal, eaI, eak);
                break;
            }
            case 4: {
                azs_0.aLV().a((aho_0)this, eaZ, eal, eaP, eak);
                break;
            }
            case 5: {
                azs_0.aLV().a((aho_0)this, eaZ, eal, eaU, eak);
            }
        }
    }

    public int py(int n2) {
        return this.ebr[n2];
    }

    public void g(int n2, int n3, boolean bl2) {
        if (n3 == 0) {
            this.bfM.set(11, n2);
        }
        int n4 = ebl.indexOf(n2);
        this.ebs[n3] = (Integer)ebl.get(n4);
        if (bl2) {
            this.ebu[n3] = (Integer)ebl.get((n4 + 4) % ebl.size());
            this.ebs[4] = (Integer)ebl.get(n4);
            this.ebs[5] = (Integer)ebl.get((n4 + 4) % ebl.size());
            if (this.ebt[n3] >= 5) {
                this.ebt[5] = this.ebt[n3] - 5;
            } else {
                this.ebt[5] = 55;
                this.ebs[5] = Math.max(0, this.ebu[n3] - 1);
            }
        }
        switch (n3) {
            case 0: {
                azs_0.aLV().a((aho_0)this, eaq);
                break;
            }
            case 1: {
                azs_0.aLV().a((aho_0)this, eax);
                break;
            }
            case 2: {
                azs_0.aLV().a((aho_0)this, eaE);
                break;
            }
            case 3: {
                azs_0.aLV().a((aho_0)this, eaL);
                break;
            }
            case 4: {
                azs_0.aLV().a((aho_0)this, eaR);
                break;
            }
            case 5: {
                azs_0.aLV().a((aho_0)this, eaW);
            }
        }
    }

    public int pz(int n2) {
        return this.ebs[n2];
    }

    public void cr(int n2, int n3) {
        int n4 = ebl.indexOf(n2);
        this.ebu[n3] = (Integer)ebl.get(n4);
        switch (n3) {
            case 0: {
                azs_0.aLV().a((aho_0)this, eap);
                break;
            }
            case 1: {
                azs_0.aLV().a((aho_0)this, eaw);
                break;
            }
            case 2: {
                azs_0.aLV().a((aho_0)this, eaD);
                break;
            }
            case 3: {
                azs_0.aLV().a((aho_0)this, eaK);
            }
        }
    }

    public int pA(int n2) {
        return this.ebu[n2];
    }

    public void pB(int n2) {
        this.ebw[n2] = !this.ebw[n2];
    }

    public boolean pC(int n2) {
        return this.ebw[n2];
    }

    public void cs(int n2, int n3) {
        if (n3 == 0) {
            this.bfM.set(12, n2);
        }
        this.ebt[n3] = n2;
        switch (n3) {
            case 0: {
                azs_0.aLV().a((aho_0)this, eas);
                break;
            }
            case 1: {
                azs_0.aLV().a((aho_0)this, eaz);
                break;
            }
            case 2: {
                azs_0.aLV().a((aho_0)this, eaG);
                break;
            }
            case 3: {
                azs_0.aLV().a((aho_0)this, eaN);
                break;
            }
            case 4: {
                azs_0.aLV().a((aho_0)this, eaS);
                break;
            }
            case 5: {
                azs_0.aLV().a((aho_0)this, eaX);
            }
        }
        azs_0.aLV().a((aho_0)this, ebc);
    }

    public int pD(int n2) {
        return this.ebt[n2];
    }

    public void ct(int n2, int n3) {
        this.ebv[n3] = n2;
        switch (n3) {
            case 0: {
                azs_0.aLV().a((aho_0)this, ear);
                break;
            }
            case 1: {
                azs_0.aLV().a((aho_0)this, eay);
                break;
            }
            case 2: {
                azs_0.aLV().a((aho_0)this, eaF);
                break;
            }
            case 3: {
                azs_0.aLV().a((aho_0)this, eaM);
            }
        }
    }

    public int pE(int n2) {
        return this.ebv[n2];
    }

    public int aXS() {
        return this.ebx;
    }

    public void a(Integer n2) {
        this.ebx = n2;
    }

    public jg_0 aXT() {
        return this.eby;
    }

    public void lN(String string) {
        this.ebz = string;
    }

    public jx_0 aXU() {
        if (this.ebz.equals(eah)) {
            return jx_0.blQ;
        }
        if (this.ebz.equals(dfF)) {
            return jx_0.blS;
        }
        if (this.ebz.equals(eai)) {
            return jx_0.blT;
        }
        if (this.ebz.equals(dfG)) {
            return jx_0.blU;
        }
        if (this.ebz.equals(eaj)) {
            return jx_0.blV;
        }
        return null;
    }

    public int aXV() {
        return this.ebA;
    }

    public void pF(int n2) {
        this.ebA = n2;
    }

    public Object getFieldValue(String string) {
        if (string.equals(eak)) {
            return this.bfM;
        }
        if (string.equals(eal)) {
            return ebo;
        }
        if (string.equals(eaY)) {
            return ebk;
        }
        if (string.equals(ebb)) {
            return ebm;
        }
        if (string.equals(eam)) {
            return this.ebq[0];
        }
        if (string.equals(ean)) {
            return this.ebr[0];
        }
        if (string.equals(eao)) {
            return this.ebp[0];
        }
        if (string.equals(eaq)) {
            return this.ebs[0];
        }
        if (string.equals(eap)) {
            return this.ebu[0];
        }
        if (string.equals(eas)) {
            return this.ebt[0];
        }
        if (string.equals(ear)) {
            return this.ebv[0];
        }
        if (string.equals(eat)) {
            return this.ebq[1];
        }
        if (string.equals(eau)) {
            return this.ebr[1];
        }
        if (string.equals(eav)) {
            return this.ebp[1];
        }
        if (string.equals(eax)) {
            return this.ebs[1];
        }
        if (string.equals(eaw)) {
            return this.ebu[1];
        }
        if (string.equals(eaz)) {
            return this.ebt[1];
        }
        if (string.equals(eay)) {
            return this.ebv[1];
        }
        if (string.equals(eaA)) {
            return this.ebq[2];
        }
        if (string.equals(eaB)) {
            return this.ebr[2];
        }
        if (string.equals(eaC)) {
            return this.ebp[2];
        }
        if (string.equals(eaE)) {
            return this.ebs[2];
        }
        if (string.equals(eaD)) {
            return this.ebu[2];
        }
        if (string.equals(eaG)) {
            return this.ebt[2];
        }
        if (string.equals(eaF)) {
            return this.ebv[2];
        }
        if (string.equals(eaH)) {
            return this.ebq[3];
        }
        if (string.equals(eaI)) {
            return this.ebr[3];
        }
        if (string.equals(eaJ)) {
            return this.ebp[3];
        }
        if (string.equals(eaL)) {
            return this.ebs[3];
        }
        if (string.equals(eaK)) {
            return this.ebu[3];
        }
        if (string.equals(eaN)) {
            return this.ebt[3];
        }
        if (string.equals(eaM)) {
            return this.ebv[3];
        }
        if (string.equals(eaO)) {
            return this.ebq[4];
        }
        if (string.equals(eaP)) {
            return this.ebr[4];
        }
        if (string.equals(eaQ)) {
            return this.ebp[4];
        }
        if (string.equals(eaR)) {
            return this.ebs[4];
        }
        if (string.equals(eaS)) {
            return this.ebt[4];
        }
        if (string.equals(eaT)) {
            return this.ebq[5];
        }
        if (string.equals(eaU)) {
            return this.ebr[5];
        }
        if (string.equals(eaV)) {
            return this.ebp[5];
        }
        if (string.equals(eaW)) {
            return this.ebs[5];
        }
        if (string.equals(eaX)) {
            return this.ebt[5];
        }
        if (string.equals(ebc)) {
            return this.bfM.get(12);
        }
        if (string.equals(eaZ)) {
            return ebl;
        }
        if (string.equals(eba)) {
            return ebn;
        }
        if (string.equals(ebd)) {
            return this.ebx;
        }
        if (string.equals(ebe)) {
            return this.ebz;
        }
        if (string.equals(ebf)) {
            return this.ebA;
        }
        if (string.equals(ebg)) {
            return this.ebw[0];
        }
        if (string.equals(ebh)) {
            return this.ebw[1];
        }
        if (string.equals(ebi)) {
            return this.ebw[2];
        }
        return null;
    }

    public String[] getFields() {
        return ce;
    }

    public void a(String string, Object object) {
    }

    public void c(String string, Object object) {
    }

    public void b(String string, Object object) {
    }

    public boolean l(String string) {
        return false;
    }

    static {
        int n2;
        ce = new String[]{eak, eal, eam, ean, eao, eaq, eap, eas, ear, eat, eau, eav, eax, eaw, eaz, eay, eaA, eaB, eaC, eaE, eaD, eaG, eaF, eaH, eaI, eaJ, eaL, eaK, eaN, eaM, eaO, eaP, eaQ, eaR, eaS, eaT, eaU, eaV, eaW, eaX, eaY, eaZ, eba, ebc, ebd, ebe, ebg, ebh, ebi};
        ebk = new ArrayList(3);
        ebl = new ArrayList(24);
        ebm = new ArrayList(60);
        ebn = new ArrayList(31);
        ebo = new ArrayList(12);
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(new Date());
        ebj = gregorianCalendar.get(1);
        for (n2 = 0; n2 < 2; ++n2) {
            ebk.add(ebj + n2);
        }
        for (n2 = 0; n2 < 24; ++n2) {
            ebl.add(n2);
        }
        for (n2 = 0; n2 < 60; ++n2) {
            ebm.add(n2);
        }
        for (n2 = 0; n2 < 12; ++n2) {
            ebo.add(new zw_0(n2));
        }
        for (n2 = 1; n2 < 32; ++n2) {
            ebn.add(n2);
        }
    }
}

