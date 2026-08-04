/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.List;

/*
 * Renamed from aDn
 */
public class adn_1
implements aho_0 {
    public static adn_1 dwR = new adn_1();
    public static final byte dwS = 10;
    public static final byte dwT = 12;
    public static final String dwU = "list1vs1";
    public static final String dwV = "list2vs2";
    public static final String dwW = "listGuild";
    public static final String dwX = "listTournamentInTheMonth";
    public static final String dwY = "listTournamentInTheTrimester";
    public static final String dwZ = "listTournamentInTheYear";
    public static final String dxa = "listReputation";
    public static final String dxb = "listGuildDemon";
    public static final String dxc = "listDemon";
    public static final String dxd = "listGlickoRating";
    private List dxe = new ArrayList(10);
    private List dxf = new ArrayList(10);
    private List dxg = new ArrayList(10);
    private List dxh = new ArrayList(10);
    private List dxi = new ArrayList(10);
    private List dxj = new ArrayList(10);
    private List dxk = new ArrayList(10);
    private List dxl = new ArrayList(10);
    private List dxm = new ArrayList(12);
    private List dxn = new ArrayList(10);
    public static final String[] ce = new String[]{"list1vs1", "list2vs2", "listGuild", "listTournamentInTheMonth", "listTournamentInTheTrimester", "listTournamentInTheYear", "listReputation", "listGuildDemon", "listDemon", "listGlickoRating"};

    public static adn_1 aPi() {
        return dwR;
    }

    public String[] getFields() {
        return ce;
    }

    public adn_1() {
        int n2;
        for (n2 = 0; n2 < 10; ++n2) {
            this.dxe.add(new vv_2());
            this.dxf.add(new vv_2());
            this.dxg.add(new axb_0());
            this.dxh.add(new ayb_0());
            this.dxi.add(new ayb_0());
            this.dxj.add(new ayb_0());
            this.dxk.add(new hl_2());
            this.dxl.add(new awj());
            this.dxn.add(new atu());
        }
        for (n2 = 0; n2 < 12; ++n2) {
            this.dxm.add(new ec());
        }
    }

    public List aPj() {
        return this.dxe;
    }

    public List aPk() {
        return this.dxf;
    }

    public List aPl() {
        return this.dxg;
    }

    public List aPm() {
        return this.dxh;
    }

    public List aPn() {
        return this.dxi;
    }

    public List aPo() {
        return this.dxj;
    }

    public List aPp() {
        return this.dxk;
    }

    public List aPq() {
        return this.dxl;
    }

    public List aPr() {
        return this.dxm;
    }

    public List aPs() {
        return this.dxn;
    }

    public Object getFieldValue(String string) {
        if (string.equals(dwU)) {
            return this.dxe.toArray();
        }
        if (string.equals(dwV)) {
            return this.dxf.toArray();
        }
        if (string.equals(dwW)) {
            return this.dxg.toArray();
        }
        if (string.equals(dwX)) {
            return this.dxh.toArray();
        }
        if (string.equals(dwY)) {
            return this.dxi.toArray();
        }
        if (string.equals(dwZ)) {
            return this.dxj.toArray();
        }
        if (string.equals(dxa)) {
            return this.dxk.toArray();
        }
        if (string.equals(dxb)) {
            return this.dxl.toArray();
        }
        if (string.equals(dxc)) {
            return this.dxm.toArray();
        }
        if (string.equals(dxd)) {
            return this.dxn.toArray();
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

