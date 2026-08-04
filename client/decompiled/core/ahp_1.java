/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/*
 * Renamed from ahP
 */
public final class ahp_1 {
    protected static final Logger a = Logger.getLogger(ahp_1.class);
    private static String cwM = "";

    private ahp_1() {
    }

    public static String axu() {
        return cwM;
    }

    public static List a(atS atS2, String string) {
        String[] stringArray;
        if (string == null || string.length() == 0) {
            return null;
        }
        ArrayList<hx> arrayList = new ArrayList<hx>();
        for (String string2 : stringArray = string.split(";")) {
            if ("canSummon".equalsIgnoreCase(string2)) {
                arrayList.add(new ahG());
                continue;
            }
            if ("canCastWhenCarrying".equalsIgnoreCase(string2)) {
                arrayList.add(new anv_2(true));
                continue;
            }
            if ("cantCastWhenCarrying".equalsIgnoreCase(string2)) {
                arrayList.add(new anv_2(false));
                continue;
            }
            if ("cantCastWhenCarried".equalsIgnoreCase(string2)) {
                arrayList.add(new ada_0());
                continue;
            }
            if ("canCastWhenDying".equalsIgnoreCase(string2)) {
                arrayList.add(new aHw(25));
                continue;
            }
            if ("canCastWhenDrunk".equalsIgnoreCase(string2)) {
                arrayList.add(new acr_2(true, avx_0.dez.lV()));
                continue;
            }
            if ("canCastWhenMaskClass".equalsIgnoreCase(string2)) {
                arrayList.add(new acr_2(true, avx_0.deH.lV()));
                continue;
            }
            if ("canCastWhenMaskBerzerk".equalsIgnoreCase(string2)) {
                arrayList.add(new acr_2(true, avx_0.deJ.lV()));
                continue;
            }
            if ("canCastWhenMaskCoward".equalsIgnoreCase(string2)) {
                arrayList.add(new acr_2(true, avx_0.deI.lV()));
                continue;
            }
            if ("cannotCastWhenMaskClass".equalsIgnoreCase(string2)) {
                arrayList.add(new acr_2(false, avx_0.deH.lV()));
                continue;
            }
            if ("cannotCastWhenMaskBerzerk".equalsIgnoreCase(string2)) {
                arrayList.add(new acr_2(false, avx_0.deJ.lV()));
                continue;
            }
            if ("cannotCastWhenMaskCoward".equalsIgnoreCase(string2)) {
                arrayList.add(new acr_2(false, avx_0.deI.lV()));
                continue;
            }
            if ("canCastWhenInjured".equalsIgnoreCase(string2)) {
                arrayList.add(new aHw(99));
                continue;
            }
            if ("canCastWhenCarryAlly".equalsIgnoreCase(string2)) {
                arrayList.add(new rW(true));
                continue;
            }
            if ("canCastWhenCarryEnnemy".equalsIgnoreCase(string2)) {
                arrayList.add(new rW(false));
                continue;
            }
            a.error((Object)("Crit\u00e8re invalide : '" + string2 + "'"));
        }
        return arrayList;
    }
}

