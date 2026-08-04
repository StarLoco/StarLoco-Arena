/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from kP
 */
public class kp_1 {
    private ams_2[] FG;
    private final aua_0 FH;
    private final Class FI;
    private final int qL;
    private static final boolean DEBUG = false;
    private static final Logger a = Logger.getLogger(kp_1.class);

    public kp_1(int n2, Class clazz) {
        try {
            if (!(clazz.newInstance() instanceof ams_2)) {
                throw new IllegalArgumentException("Type de classe invalide : n'h\u00e9rite pas de MemoryObject");
            }
        }
        catch (InstantiationException instantiationException) {
            throw new IllegalArgumentException("Type de classe invalide : impossible d'instancier", instantiationException);
        }
        catch (IllegalAccessException illegalAccessException) {
            throw new IllegalArgumentException("Type de classe invalide : impossible d'instancier", illegalAccessException);
        }
        this.FI = clazz;
        this.qL = ams_2.L(this.FI);
        this.FG = new ams_2[n2];
        this.FH = new aua_0(n2);
        for (int j = 0; j < this.FG.length; ++j) {
            this.FG[j] = this.bR(j);
        }
        yW.FL().b(this);
    }

    public final ams_2 pw() {
        int n2 = this.FH.aHc();
        if (n2 == this.FH.cVC) {
            this.resize(this.pB());
            n2 = this.FH.aHc();
        }
        ams_2 ams_22 = this.FG[n2];
        ams_22.avg();
        ams_22.ave();
        try {
            ams_22.af();
            return ams_22;
        }
        catch (Exception exception) {
            this.FH.mm(n2);
            throw new RuntimeException("Exception lev\u00e9e lors de l'extraction d'un \u00e9l\u00e9ment du pool", exception);
        }
    }

    public final void a(ams_2 ams_22) {
        this.FH.mm(ams_22.aXl());
    }

    protected void px() {
        this.FH.aHd();
    }

    public final int it() {
        return this.qL;
    }

    public final Class py() {
        return this.FI;
    }

    public final int pz() {
        return this.FH.pz();
    }

    public final int pA() {
        return this.FH.pA();
    }

    public final int getSize() {
        return this.FH.getSize();
    }

    private int pB() {
        int n2 = this.FG.length;
        if (n2 < 4096) {
            return n2 * 2;
        }
        return n2 + 4096;
    }

    private void resize(int n2) {
        assert (n2 > this.FG.length);
        ams_2[] ams_2Array = new ams_2[n2];
        System.arraycopy(this.FG, 0, ams_2Array, 0, this.FG.length);
        for (int j = this.FG.length; j < n2; ++j) {
            ams_2Array[j] = this.bR(j);
        }
        this.FH.resize(n2);
        this.FG = ams_2Array;
    }

    private ams_2 bR(int n2) {
        try {
            ams_2 ams_22 = (ams_2)this.FI.newInstance();
            ams_22.a(n2, this);
            return ams_22;
        }
        catch (Exception exception) {
            a.error((Object)"Failed to create object", (Throwable)exception);
            return null;
        }
    }
}

