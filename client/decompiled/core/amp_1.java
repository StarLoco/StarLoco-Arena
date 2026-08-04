/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import org.apache.log4j.Logger;

/*
 * Renamed from aMp
 */
public abstract class amp_1 {
    protected static final Logger a = Logger.getLogger(amp_1.class);
    protected aap_0 dXK;
    protected final ArrayList bZJ = new ArrayList();

    public final void a(aap_0 aap_02) {
        this.dXK = aap_02;
    }

    public final void a(alk_2 alk_22) {
        this.bZJ.add(alk_22);
    }

    public final void b(alk_2 alk_22) {
        this.bZJ.remove(alk_22);
    }

    public void aWW() {
        this.bZJ.clear();
    }

    public adw_0 eP(long l2) {
        try {
            for (alk_2 alk_22 : this.bZJ) {
                if (!alk_22.eI(l2)) continue;
                short s = alk_22.eJ(l2);
                Cs cs = this.dXK.bw(s);
                if (cs == null) {
                    a.error((Object)("Aucune factory d'enregistr\u00e9e pour un \u00e9l\u00e9ment interactif de type " + s));
                    continue;
                }
                adw_0 adw_02 = (adw_0)cs.h();
                adw_02.c(l2);
                adw_02.y(s);
                adw_02.ad(alk_22.eK(adw_02.getId()));
                this.a(adw_02, alk_22);
                return adw_02;
            }
            a.error((Object)("Aucune d\u00e9finition trouv\u00e9e pour l'instance d'\u00e9lement interactif " + l2));
        }
        catch (Exception exception) {
            a.error((Object)("Exception lors de InteractiveElementFactory.createInteractiveElement(" + l2 + ")"), (Throwable)exception);
        }
        return null;
    }

    public adw_0 b(long l2, short s, byte[] byArray) {
        Cs cs = this.dXK.bw(s);
        if (cs == null) {
            a.error((Object)("Aucune factory d'enregistr\u00e9e pour un \u00e9l\u00e9ment interactif de type " + s));
            return null;
        }
        adw_0 adw_02 = (adw_0)cs.h();
        adw_02.c(l2);
        adw_02.ad(byArray);
        return adw_02;
    }

    protected abstract void a(adw_0 var1, alk_2 var2);
}

