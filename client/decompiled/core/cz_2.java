/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import org.apache.log4j.Logger;

/*
 * Renamed from CZ
 */
public class cz_2 {
    private static Logger a = Logger.getLogger(cz_2.class);
    private final ArrayList aMX;
    private final int aMY;
    private final String aMZ;
    private final URL aHf;
    private final aGm aNa;
    private static int aGl = 0;

    protected cz_2(String string, int n2) {
        URL uRL = null;
        Class<?> clazz = null;
        try {
            clazz = Class.forName(string);
        }
        catch (ClassNotFoundException classNotFoundException) {
            // empty catch block
        }
        if (clazz != null) {
            aGm aGm2 = null;
            try {
                aGm2 = (aGm)clazz.newInstance();
            }
            catch (Exception exception) {
                // empty catch block
            }
            this.aHf = null;
            this.aNa = aGm2;
        } else {
            try {
                uRL = new URL(string);
            }
            catch (MalformedURLException malformedURLException) {
                // empty catch block
            }
            this.aNa = null;
            this.aHf = uRL;
        }
        if (this.aHf == null && this.aNa == null) {
            throw new Exception("Ressource invalide : " + string);
        }
        this.aMY = n2;
        this.aMZ = string;
        this.aMX = new ArrayList(n2);
    }

    private boolean i(na_1 na_12) {
        if (this.aMX.size() < this.aMY) {
            this.aMX.add(na_12);
            return true;
        }
        return false;
    }

    public na_1 Lp() {
        if (this.aMX.size() > 0) {
            return (na_1)this.aMX.remove(0);
        }
        return null;
    }

    public int getMaxSize() {
        return this.aMY;
    }

    public String fH() {
        return this.aMZ;
    }

    public boolean Lq() {
        return this.aMY > this.aMX.size();
    }

    public void Lr() {
        afq_1 afq_12 = add_1.aOG().azj();
        while (this.Lq()) {
            na_1 na_12 = null;
            aji_1 aji_12 = afq_12.lf(this.aMZ + aGl++);
            if (this.aNa != null) {
                na_12 = (na_1)this.aNa.a(afq_12, aji_12);
            } else if (this.aHf != null) {
                try {
                    na_12 = add_1.aOG().a(this.aHf, afq_12, aji_12, false, null, null, null);
                }
                catch (Exception exception) {
                    a.error((Object)("Probl\u00e8me lors du changement d'un xml : " + this.aHf), (Throwable)exception);
                }
            }
            if (na_12 == null) continue;
            if (!this.i(na_12)) {
                na_12.release();
                continue;
            }
            a.info((Object)("Chargement d'une ressource " + this.aMZ));
        }
    }

    public void clean() {
        for (int j = this.aMX.size() - 1; j >= 0; --j) {
            ((na_1)this.aMX.get(j)).release();
        }
        this.aMX.clear();
    }
}

