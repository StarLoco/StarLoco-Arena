/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;
import java.util.ArrayList;

/*
 * Renamed from Qr
 */
public class qr_0
extends th_2 {
    public static String bGg = "registrationEnabled";
    public static String bGh = "tournamentDescription";
    public static String bGi = "tournamentIllustration";
    public static String bGj = "tournamentStyle";
    public static String bGk = "tournamentSchedule";
    public static String bGl = "tournamentPhaseSchedule";
    public static String bGm = "tournamentRules";
    public static String bGn = "tournamentInscriptionCard";
    public static String bGo = "tournamentRegistrationPeriod";
    public static String bGp = "tournamentRewards";
    public static String bGq = "tournamentFightParameter";
    public static String bGr = "isRegistered";
    public static String bGs = "hasSpecialRules";
    public static String bGt = "hasTree";
    private String BF;
    private long lc;
    private int bGu;
    private String bGv;
    private String bGw;
    private ArrayList bGx = new ArrayList();
    private ArrayList bGy = new ArrayList();
    private boolean bGz = false;

    public qr_0() {
    }

    public qr_0(rd_1 rd_12, rd_1 rd_13, jx_0 jx_02, int n2, String string, int n3) {
        super(rd_12, rd_13, jx_02, n2);
        this.BF = string;
        this.bGu = n3;
    }

    public byte[] cd() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(this.nj());
        this.B(byteBuffer);
        byte[] byArray = aey_0.hH(this.BF);
        byteBuffer.put((byte)byArray.length);
        byteBuffer.put(byArray);
        byteBuffer.putInt(this.bGu);
        return byteBuffer.array();
    }

    public int nj() {
        byte[] byArray = aey_0.hH(this.BF);
        return this.UD() + 1 + byArray.length + 4;
    }

    public iz_0 h(ByteBuffer byteBuffer) {
        int n2;
        qr_0 qr_02 = new qr_0();
        qr_02.C(byteBuffer);
        qr_02.lc = byteBuffer.getLong();
        byte[] byArray = new byte[byteBuffer.get()];
        byteBuffer.get(byArray);
        qr_02.BF = aey_0.V(byArray);
        byArray = new byte[byteBuffer.getShort()];
        byteBuffer.get(byArray);
        qr_02.bGv = aey_0.V(byArray);
        byArray = new byte[byteBuffer.get()];
        byteBuffer.get(byArray);
        qr_02.bGw = aey_0.V(byArray);
        int n3 = byteBuffer.get();
        for (n2 = 0; n2 < n3; ++n2) {
            qr_02.bGy.add(new rd_1[]{rd_1.aF(byteBuffer.getLong()), rd_1.aF(byteBuffer.getLong())});
        }
        n3 = byteBuffer.get();
        for (n2 = 0; n2 < n3; ++n2) {
            qr_02.bGx.add(new rd_1[]{rd_1.aF(byteBuffer.getLong()), rd_1.aF(byteBuffer.getLong())});
        }
        return qr_02;
    }

    public iz_0 nk() {
        qr_0 qr_02 = new qr_0();
        this.c(qr_02);
        qr_02.lc = this.lc;
        qr_02.BF = this.BF;
        qr_02.bGu = this.bGu;
        qr_02.bGz = true;
        qr_02.bGv = this.bGv;
        qr_02.bGw = this.bGw;
        return qr_02;
    }

    public Object getFieldValue(String string) {
        aub aub2;
        Object object;
        if (string.equals(vp) && (object = vk_1.BZ().aQ(this.lc)) != null) {
            return ((vg)object).BC();
        }
        if (string.equals(bGh) && (object = vk_1.BZ().aQ(this.lc)) != null) {
            return ((vg)object).BD();
        }
        if (string.equals(bGi) && (object = vk_1.BZ().aQ(this.lc)) != null) {
            try {
                return String.format(mu_1.rM().getString("tournamentIllustrationsPath"), ((vg)object).Bw());
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        if (string.equals(bGj) && (object = vk_1.BZ().aQ(this.lc)) != null) {
            aub aub3 = LS.Yf().gG(((vg)object).Bw());
            if (aub3 != null && aub3.aHh() == aql_0.cOF) {
                return "eventTournamentEvolution";
            }
            return "LadderOdd";
        }
        if (string.equals(bGk) && this.bGy.size() > 0) {
            object = (rd_1[])this.bGy.get(0);
            String string2 = ((rd_1)object[0]).getHours() + "H";
            if (((rd_1)object[0]).getMinutes() > 0) {
                string2 = string2 + ((rd_1)object[0]).getMinutes();
            }
            string2 = string2 + " - " + ((rd_1)object[1]).getHours() + "H";
            if (((rd_1)object[1]).getMinutes() > 0) {
                string2 = string2 + ((rd_1)object[1]).getMinutes();
            }
            return string2;
        }
        if (string.equals(bGl)) {
            object = new String[this.bGy.size()];
            for (int j = this.bGy.size() - 1; j >= 0; --j) {
                rd_1 rd_12 = ((rd_1[])this.bGy.get(j))[0];
                object[j] = rd_12.getHours() + "H" + rd_12.getMinutes() + ", " + rd_12.getDay() + " " + aon_0.aYc().getString("month" + rd_12.getMonth());
            }
            return object;
        }
        if (string.equals(bGo)) {
            object = (rd_1[])this.bGx.get(0);
            return ((rd_1)object[0]).getHours() + "H" + ((rd_1)object[0]).getMinutes() + ", " + ((rd_1)object[0]).getDay() + " " + aon_0.aYc().getString("month" + ((rd_1)object[0]).getMonth()) + " - " + ((rd_1)object[1]).getHours() + "H" + ((rd_1)object[1]).getMinutes() + ", " + ((rd_1)object[1]).getDay() + " " + aon_0.aYc().getString("month" + ((rd_1)object[1]).getMonth());
        }
        if (string.equals(bGm) && (object = vk_1.BZ().aQ(this.lc)) != null) {
            String string3 = ((vg)object).BA();
            String string4 = aon_0.aYc().a(53, ((vg)object).Bw(), new Object[0]);
            if (string3.length() > 0) {
                return string3;
            }
            return string4;
        }
        if (string.equals(bGn) && (object = vk_1.BZ().aQ(this.lc)) != null && (aub2 = LS.Yf().gG(((vg)object).Bw())).qo() != 0) {
            return new wy_2(aub2.qo());
        }
        if (string.equals(bGp) && (object = vk_1.BZ().aQ(this.lc)) != null && (aub2 = LS.Yf().gG(((vg)object).Bw())).aHi() != 0) {
            return new wy_2(aub2.aHi());
        }
        if (string.equals(bGq) && (object = vk_1.BZ().aQ(this.lc)) != null) {
            return WN.A(((vg)object).By());
        }
        if (string.equals(sU)) {
            return aon_0.aYc().a(36, this.Bo(), new Object[0]);
        }
        if (string.equals(bhE)) {
            object = vk_1.BZ().aQ(this.lc);
            if (object != null) {
                return ((vg)object).Bv() == -128 && this.add();
            }
            return false;
        }
        if (string.equals(bGr)) {
            object = vk_1.BZ().aQ(this.lc);
            if (object != null) {
                return ((vg)object).Bv() != -128;
            }
            return false;
        }
        if (string.equals(bGg)) {
            return this.add();
        }
        if (string.equals(nN)) {
            return "eventTournamentRegistered";
        }
        if (string.equals(bGs)) {
            object = vk_1.BZ().aQ(this.lc);
            if (object != null) {
                return ((vg)object).By().length > 0;
            }
            return false;
        }
        if (string.equals(bGt)) {
            object = vk_1.BZ().aQ(this.lc);
            if (object != null) {
                return ((vg)object).BE() == ks_1.bnI;
            }
            return false;
        }
        return super.getFieldValue(string);
    }

    public long fx() {
        return this.lc;
    }

    public boolean add() {
        vg vg2 = vk_1.BZ().aQ(this.lc);
        if (vg2 != null) {
            return !this.bGz && vg2.Bx();
        }
        return !this.bGz;
    }

    public int getType() {
        return 4;
    }
}

