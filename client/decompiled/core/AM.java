/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.HashMap;
import org.apache.log4j.Logger;

public class AM {
    protected static final Logger a = Logger.getLogger(AM.class);
    private static final AM aHZ = new AM();
    protected final HashMap aIa = new HashMap();

    public static AM Ht() {
        return aHZ;
    }

    private AM() {
    }

    public nk aZ(long l2) {
        return (nk)this.aIa.get(l2);
    }

    public boolean a(nk nk2) {
        if (this.aIa.containsKey(nk2.getId())) {
            a.info((Object)("Impossible d'ajouter l'\u00e9change " + nk2.getClass().getName() + " : un \u00e9change avec le m\u00eame ID (" + nk2.getId() + ") existe d\u00e9j\u00e0."));
            return false;
        }
        this.aIa.put(nk2.getId(), nk2);
        return true;
    }

    public boolean b(nk nk2) {
        return this.aIa.remove(nk2.getId()) != null;
    }
}

