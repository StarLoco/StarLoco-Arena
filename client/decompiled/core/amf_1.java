/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import org.apache.log4j.Logger;

/*
 * Renamed from aMf
 */
public class amf_1
implements aEe {
    protected static Logger a = Logger.getLogger(amf_1.class);
    public static final amf_1 dXt = new amf_1();
    private final ArrayList dXu = new ArrayList();

    private amf_1() {
    }

    public void a(aEe aEe2) {
        if (!this.dXu.contains(aEe2)) {
            this.dXu.add(aEe2);
        }
    }

    public void initialize() {
        a.info((Object)"Initializing hardware tests...");
        int n2 = this.dXu.size();
        for (int j = 0; j < n2; ++j) {
            aEe aEe2 = (aEe)this.dXu.get(j);
            try {
                aEe2.initialize();
                continue;
            }
            catch (Exception exception) {
                a.error((Object)("Erreur \u00e0 l'initialisation du test hardware " + aEe2.GP().aBM()), (Throwable)exception);
            }
        }
    }

    public boolean GO() {
        a.info((Object)"Testing supported features...");
        int n2 = this.dXu.size();
        for (int j = 0; j < n2; ++j) {
            aEe aEe2 = (aEe)this.dXu.get(j);
            amA amA2 = aEe2.GP();
            boolean bl2 = false;
            try {
                bl2 = aEe2.GO();
            }
            catch (Exception exception) {
                a.error((Object)("Erreur pendant le test hardware " + amA2.aBM()), (Throwable)exception);
            }
            a.info((Object)("\t* " + amA2.aBM() + "..." + (bl2 ? " supported !" : " not supported !")));
            Mf.btd.a(amA2, bl2);
        }
        return true;
    }

    public void cleanUp() {
        a.info((Object)"Cleaning up hardware tests...");
        int n2 = this.dXu.size();
        for (int j = 0; j < n2; ++j) {
            aEe aEe2 = (aEe)this.dXu.get(j);
            try {
                aEe2.cleanUp();
                continue;
            }
            catch (Exception exception) {
                a.error((Object)("Erreur pendant le cleanup du test hardware " + aEe2.GP().aBM()), (Throwable)exception);
            }
        }
    }

    public amA GP() {
        return null;
    }
}

