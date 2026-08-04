/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

public class CN {
    protected static Logger a = Logger.getLogger(CN.class);
    private static final cp_2 aMO = new cp_2();

    public static void a(abe_1 abe_12) {
        long l2 = abe_12.getId();
        if (aMO.v(l2)) {
            a.error((Object)("Impossible d'ajouter la d\u00e9finition de laboratoire de fusion " + abe_12 + " : Equivalent d\u00e9j\u00e0 trouv\u00e9 " + aMO.t(l2) + "."));
        } else {
            aMO.a(l2, abe_12);
        }
    }

    public static abe_1 by(long l2) {
        return (abe_1)aMO.t(l2);
    }
}

