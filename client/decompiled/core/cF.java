/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

public class cF {
    protected static Logger a = Logger.getLogger(cF.class);
    public static final int jo = 0;
    private static final jr_0 jp = jr_0.VF();
    private static final lb_0 jq = new lb_0();

    public static int size() {
        return jq.size();
    }

    public static void a(xj xj2) {
        if (xj2 == null) {
            a.error((Object)"Impossible d'ajouter une carte : ReferenceCoachCard \u00e9gal \u00e0 null.");
        } else if (xj2.tj() != aMK.dYz) {
            a.error((Object)("Impossible d'ajouter la referenceCoachCard d'id " + xj2.getId() + " : Id de type \u00e9gal \u00e0 " + xj2.tj() + " invalide."));
        } else {
            int n2 = xj2.getId();
            if (jq.contains(n2)) {
                a.error((Object)("Impossible d'ajouter la referenceCoachCard d'id " + n2 + " : D\u00e9j\u00e0 pr\u00e9sente."));
            } else {
                jq.c(n2, xj2);
            }
        }
    }

    public static xj Q(int n2) {
        xj xj2 = (xj)jq.get(n2);
        if (xj2 == null) {
            a.error((Object)("Impossible d'obtenir la carte d'id " + n2 + " : ReferenceCoachCard \u00e9gal \u00e0 null."));
        }
        return xj2;
    }

    public static int eV() {
        int n2 = 0;
        int[] nArray = jq.pL();
        if (nArray.length == 0) {
            a.error((Object)"Impossible d'obtenir une id de carte : Manager vide !");
        } else {
            n2 = nArray[jp.nextInt(nArray.length)];
        }
        return n2;
    }
}

