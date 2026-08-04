/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.Iterator;
import org.apache.log4j.Logger;

public class ajO
implements Iterable {
    protected static final Logger a = Logger.getLogger(ajO.class);
    protected ajv_2 aTs;

    public ajO(azk azk2, short s) {
        this.aTs = new ajv_2(s, azk2, null, true, false, false);
    }

    public void b(byte[] byArray) {
        this.aTs.d(byArray);
    }

    public byte[] cd() {
        return this.aTs.cd();
    }

    public boolean f(fv fv2) {
        try {
            this.aTs.a(fv2);
            return true;
        }
        catch (gg gg2) {
            a.error((Object)"impossible d'ajouter ce sort : inventaire plein");
        }
        catch (xR xR2) {
            a.error((Object)"impossible d'ajouter ce sort : on l'a d\u00e9j\u00e0");
        }
        return false;
    }

    public void dI(long l2) {
        this.aTs.C(l2);
    }

    public ajv_2 Oh() {
        return this.aTs;
    }

    public Iterator iterator() {
        return this.aTs.iterator();
    }

    public int size() {
        return this.aTs.size();
    }
}

