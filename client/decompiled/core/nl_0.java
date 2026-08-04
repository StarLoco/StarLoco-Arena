/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.io.BufferedInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import org.apache.log4j.Logger;

/*
 * Renamed from NL
 */
public class nl_0
implements aho_0 {
    protected static final Logger a = Logger.getLogger(nl_0.class);
    private static nl_0 bAj = new nl_0();
    private boolean aK = false;
    private short bud = 0;
    private int bAk = 0;
    private int bAl = 0;
    private int bAm = -1;
    private int bAn = -1;
    public static final int bAo = 960;
    public static final int bAp = 480;
    public static final String bAq = "mapSize";
    public static final String bAr = "miniMapX";
    public static final String bAs = "miniMapY";
    public static final String bAt = "eliteBonus";
    public static final String bAu = "evolutionBonus";
    public static final String xX = "name";

    public static nl_0 aaQ() {
        return bAj;
    }

    public String[] getFields() {
        return new String[]{bAr, bAs, bAq, bAt, bAu, xX};
    }

    public Object getFieldValue(String string) {
        if (string.equalsIgnoreCase(bAr)) {
            if (this.bud != xx_1.Em()) {
                this.bud = xx_1.Em();
                this.aK = this.aaR();
            }
            if (!this.aK) {
                return -1;
            }
            sj_1 sj_12 = apN.aDK().Ln();
            int n2 = sj_12.gn();
            int n3 = sj_12.go();
            return (int)(((double)this.bAk + (double)((n2 - n3) / 2) * 86.0) * 960.0 / (double)this.bAm + 480.0);
        }
        if (string.equalsIgnoreCase(bAs)) {
            if (this.bud != xx_1.Em()) {
                this.bud = xx_1.Em();
                this.aK = this.aaR();
            }
            if (!this.aK) {
                return -1;
            }
            sj_1 sj_13 = apN.aDK().Ln();
            int n4 = sj_13.gn();
            int n5 = sj_13.go();
            short s = sj_13.gp();
            return 480 - (int)(((double)(-this.bAl) + (double)((n4 + n5) / 2) * 43.0 - (double)s * 10.0) * 480.0 / (double)this.bAn + 240.0);
        }
        if (string.equalsIgnoreCase(bAq)) {
            return "960,480";
        }
        if (string.equals(bAt)) {
            return aon_0.aYc().getString("eliteDropBonus", afh_1.aRG().cn(this.bud));
        }
        if (string.equals(bAu)) {
            akw_0[] akw_0Array = afh_1.aRG().co(this.bud);
            return aon_0.aYc().getString("evolutionMapBonus", asf_0.a(akw_0Array));
        }
        if (string.equals(xX)) {
            return aon_0.aYc().a(61, this.bud, new Object[0]);
        }
        return null;
    }

    public boolean aaR() {
        URL uRL;
        String string;
        try {
            string = String.format(mu_1.rM().getString("fullMapPath"), this.bud);
        }
        catch (aih_2 aih_22) {
            a.error((Object)"Probl\u00e8me lors de la lecture de fullMapPath");
            return false;
        }
        if (string == null) {
            return false;
        }
        try {
            uRL = new URL(string);
        }
        catch (MalformedURLException malformedURLException) {
            a.error((Object)("URL invalide : " + string));
            return false;
        }
        aAN aAN2 = new aAN();
        aNe aNe2 = new aNe();
        try {
            aAN2.q(new BufferedInputStream(uRL.openStream()));
            aAN2.a(aNe2, new tf_2[0]);
            aAN2.close();
        }
        catch (Exception exception) {
            a.error((Object)("Probl\u00e8me lors de la lecture du fichier de map d'url : " + uRL));
            return false;
        }
        boolean bl2 = false;
        ArrayList arrayList = aNe2.aXo().getChildren();
        int n2 = arrayList.size();
        for (int j = 0; j < n2; ++j) {
            int n3;
            k_0 k_02 = (k_0)arrayList.get(j);
            if (k_02.getName().equals("#text") || k_02.getName().equals("#comment")) continue;
            this.bAm = -1;
            this.bAn = -1;
            k_0 k_03 = k_02.f("isoX");
            if (k_03 != null) {
                this.bAk = k_03.getIntValue();
            }
            if ((k_03 = k_02.f("isoY")) != null) {
                this.bAl = k_03.getIntValue();
            }
            if ((k_03 = k_02.f("isoWidth")) != null) {
                this.bAm = k_03.getIntValue();
            }
            if ((k_03 = k_02.f("isoHeight")) != null) {
                this.bAn = k_03.getIntValue();
            }
            if (this.bAm / 960 > this.bAn / 480) {
                n3 = (int)((float)this.bAm / 960.0f * 480.0f) - this.bAn;
                this.bAn += n3;
                this.bAl += n3 / 2;
            } else {
                n3 = (int)((float)this.bAn / 480.0f * 960.0f) - this.bAm;
                this.bAm += n3;
                this.bAk += n3 / 2;
            }
            if (this.bAm == -1 || this.bAn == -1) continue;
            bl2 = true;
        }
        return bl2;
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

