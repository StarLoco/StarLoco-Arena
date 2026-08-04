/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.BitSet;
import java.util.Collection;
import java.util.HashSet;
import org.apache.log4j.Logger;

/*
 * Renamed from afR
 */
public class afr_2
implements JG {
    private final BitSet csy = new BitSet();
    private final HashSet csz = new HashSet();
    private int csA = 0;
    private static Logger a = Logger.getLogger(afr_2.class);
    private static final ym_0 cfT = new ym_0(new hi_1());

    public static afr_2 avH() {
        afr_2 afr_22;
        try {
            afr_22 = (afr_2)cfT.adr();
        }
        catch (Exception exception) {
            afr_22 = new afr_2();
            a.error((Object)("Erreur de checkout : " + exception.getMessage()));
        }
        return afr_22;
    }

    public void b() {
        this.csA = 0;
    }

    public void j() {
        this.csy.clear();
        this.csz.clear();
    }

    public void a(BitSet bitSet) {
        this.csy.or(bitSet);
    }

    public void f(Collection collection) {
        this.csz.addAll(collection);
    }

    public void j(kc_2 kc_22) {
        this.csz.add(kc_22);
    }

    public void avI() {
        ++this.csA;
    }

    public BitSet avJ() {
        return this.csy;
    }

    public HashSet avK() {
        return this.csz;
    }

    public void a(afr_2 afr_22) {
        if (afr_22 == null) {
            return;
        }
        this.a(afr_22.avJ());
        this.f(afr_22.avK());
        this.csA += afr_22.avL();
    }

    public int avL() {
        return this.csA;
    }

    public void clear() {
        this.csA = 0;
        this.csz.clear();
        this.csy.clear();
    }

    public void release() {
        if (cfT != null) {
            try {
                cfT.af(this);
            }
            catch (Exception exception) {
                a.error((Object)("Impossible de retourner l'\u00e9v\u00e9nement " + this + " au pool"), (Throwable)exception);
            }
        } else {
            a.error((Object)("Aucun pool n'a \u00e9t\u00e9 d\u00e9fini pour l'\u00e9v\u00e9nement " + this));
        }
    }
}

