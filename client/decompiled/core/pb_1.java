/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.Map;
import org.apache.log4j.Logger;

/*
 * Renamed from Pb
 */
public class pb_1
extends jw_1 {
    private static final Logger a = Logger.getLogger(pb_1.class);
    private static final String abe = "anm/";
    private long bDe;

    public boolean a(arp_0 arp_02) {
        if (this.bDe == -1L) {
            return false;
        }
        ano_0 ano_02 = new ano_0(1);
        ano_02.put("fightId", (Object)arp_02.aEZ());
        Ky.WG().a(this.av(this.bDe), arp_02.aEY(), (Map)ano_02, null, false);
        return false;
    }

    private String av(long l2) {
        assert (Ky.WG().getPath() != null);
        return String.format("%s%d%s", abe, l2, Ky.WG().getExtension());
    }

    public void a(byte by, acf acf2) {
        String string = acf2.readString();
        try {
            this.bDe = Long.parseLong(string);
        }
        catch (NumberFormatException numberFormatException) {
            a.error((Object)("Impossible d'interpr\u00e9ter le parametre pour runScript param=" + string));
            this.bDe = -1L;
        }
    }

    public void a(aij_1 aij_12) {
    }

    public int getSize() {
        return 10 + super.getSize();
    }

    public aro ek() {
        return aro.cPu;
    }
}

