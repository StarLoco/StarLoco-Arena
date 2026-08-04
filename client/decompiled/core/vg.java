/*
 * Decompiled with CFR 0.152.
 */
public class vg
implements aho_0 {
    public static final byte ase = -128;
    private final long lc;
    private final byte asf;
    private byte asg;
    private final short ash;
    private final byte asi;
    private final int[] asj;
    private final String ask;
    private final String asl;
    private final String asm;
    private final byte asn;
    public static final String NAME = "name";
    public static final String sU = "description";
    public static final String aso = "rules";
    public static final String asp = "openedSearch";
    public static final String asq = "coachLevel";
    public static final String asr = "illustration";
    public static final String ass = "tournamentLevelDescription";
    public static final String[] ce = new String[]{"name", "description", "openedSearch", "coachLevel", "illustration", "tournamentLevelDescription"};

    public vg(long l2, byte by, byte by2, short s, byte by3, int[] nArray, String string, String string2, String string3, byte by4) {
        this.lc = l2;
        this.asf = by;
        this.asg = by2;
        this.ash = s;
        this.asi = by3;
        this.asj = nArray;
        this.ask = string;
        this.asl = string2;
        this.asm = string3;
        this.asn = by4;
    }

    public long fx() {
        return this.lc;
    }

    public byte Bu() {
        return this.asf;
    }

    public byte Bv() {
        return this.asg;
    }

    public short Bw() {
        return this.ash;
    }

    public boolean Bx() {
        return this.asi != 0;
    }

    public int[] By() {
        return this.asj;
    }

    public String Bz() {
        return this.ask;
    }

    public String BA() {
        return this.asl;
    }

    public String BB() {
        return this.asm;
    }

    public String BC() {
        String string = this.Bz();
        if (string != null && string.length() > 0) {
            return string;
        }
        return aon_0.aYc().a(41, this.ash, new Object[0]);
    }

    public String BD() {
        String string = this.BA();
        if (string != null && string.length() > 0) {
            return string;
        }
        return aon_0.aYc().a(42, this.ash, new Object[0]);
    }

    public void C(byte by) {
        this.asg = by;
    }

    public byte BE() {
        return this.asn;
    }

    public String[] getFields() {
        return ce;
    }

    public Object getFieldValue(String string) {
        if (string.equals(NAME)) {
            return this.BC();
        }
        if (string.equals(sU)) {
            return this.BD();
        }
        if (string.equals(aso)) {
            String string2 = this.BA();
            String string3 = aon_0.aYc().a(53, this.Bw(), new Object[0]);
            if (string2.length() > 0) {
                return string2;
            }
            return string3;
        }
        if (string.equals(asp)) {
            if (this.asf != 0) {
                return aon_0.aYc().getString("tournaments.tournamentSearchTimePeriod") + "open";
            }
            return aon_0.aYc().getString("tournaments.tournamentSearchTimePeriod") + "close";
        }
        if (string.equals(asq)) {
            if (this.asg == -128) {
                return "";
            }
            return this.asg;
        }
        if (string.equals(asr)) {
            try {
                return String.format(mu_1.rM().getString("tournamentIllustrationsPath"), this.ash);
            }
            catch (Exception exception) {
            }
        } else if (string.equals(ass)) {
            String string4 = "";
            vg vg2 = vk_1.BZ().aQ(this.lc);
            if (vg2 != null) {
                switch (vg2.Bv()) {
                    case -128: {
                        string4 = aon_0.aYc().getString("coachNotRegistered");
                        break;
                    }
                    case 0: {
                        string4 = aon_0.aYc().getString("coachInTournamentFinal");
                        break;
                    }
                    case 1: {
                        string4 = aon_0.aYc().getString("coachInTournamentSemiFinal");
                        break;
                    }
                    case 2: {
                        string4 = aon_0.aYc().getString("coachInTournamentQuarterFinal");
                        break;
                    }
                    case 3: {
                        string4 = aon_0.aYc().getString("coachInTournamentFirstRound");
                    }
                }
            }
            return string4;
        }
        return null;
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
}

