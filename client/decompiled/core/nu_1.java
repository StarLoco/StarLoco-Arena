/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import org.apache.log4j.Logger;

/*
 * Renamed from NU
 */
public abstract class nu_1
implements nz {
    private static final boolean DEBUG = false;
    protected static final Logger a = Logger.getLogger(nu_1.class);
    private byte bAL = 0;
    private byte bAM = 0;
    private float bAN = 1.0f;
    private float Ov = 1.0f;
    private float bAO = 1.0f;
    private float bAP;
    private float bAQ;
    private boolean bAR = false;
    private boolean bAS = false;
    private final String m_name;
    protected aCZ dj;
    protected aL bAT;
    protected azy_0 bAU;
    protected int bAV = -1;
    protected boolean bAW;
    protected byte bAX = 0;
    private boolean OD = true;
    private final ArrayList bAY = new ArrayList();
    private final ArrayList bAZ = new ArrayList();
    private final ArrayList bBa = new ArrayList();

    protected nu_1(String string) {
        this(string, 0);
    }

    protected nu_1(String string, byte by) {
        this.m_name = string;
        this.dj = null;
        this.bAP = 0.0f;
        this.bAX = by;
    }

    public boolean k(int n2) {
        if (n2 > this.bAL - this.bAM) {
            return false;
        }
        this.bAM = (byte)(this.bAM + n2);
        return true;
    }

    public boolean l(int n2) {
        if (this.bAM < n2) {
            return false;
        }
        this.bAM = (byte)(this.bAM - n2);
        return true;
    }

    public void a(pl_0 pl_02) {
        this.bAZ.add(pl_02);
    }

    public void b(pl_0 pl_02) {
        this.bBa.add(pl_02);
    }

    public byte abb() {
        return this.bAL;
    }

    public boolean ac(byte by) {
        assert (by >= 0) : "Nombre de voix invalide";
        if (by == this.bAL) {
            return false;
        }
        if (by > this.bAL ? !this.bAT.k(by - this.bAL) : !this.bAT.l(this.bAL - by)) {
            return false;
        }
        this.bAL = by;
        return true;
    }

    public byte abc() {
        return this.bAX;
    }

    public void ad(byte by) {
        this.bAX = by;
    }

    public boolean isEnabled() {
        return this.OD;
    }

    public void setEnabled(boolean bl2) {
        this.OD = bl2;
    }

    public aCZ bK() {
        return this.dj;
    }

    public void a(aCZ aCZ2) {
        this.dj = aCZ2;
    }

    public void c(aL aL2) {
        this.bAT = aL2;
    }

    public aL abd() {
        return this.bAT;
    }

    public final void a(azy_0 azy_02) {
        this.bAU = azy_02;
    }

    public final azy_0 abe() {
        assert (this.bAU != null) : "Il faut d'abord initialiser en  appelant setHelper";
        return this.bAU;
    }

    public float abf() {
        return this.bAN;
    }

    public void aj(float f) {
        this.bAN = f;
        this.setGain(this.Ov);
    }

    public final float getGain() {
        return this.Ov * this.bAN;
    }

    public final void setGain(float f) {
        if (this.Ov != f) {
            float f2 = this.Ov;
            this.Ov = f;
            this.n(f2, this.Ov);
            this.G(this.abf());
        }
    }

    public final void setMaxGain(float f) {
        if (this.bAO != f) {
            this.o(this.bAO, f);
            this.G(this.abf());
            this.bAO = f;
        }
    }

    public final float getMaxGain() {
        return this.bAO;
    }

    public final boolean abg() {
        return this.bAR;
    }

    public final void setMute(boolean bl2) {
        this.c(this.bAR, bl2);
        this.bAR = bl2;
    }

    public boolean abh() {
        return this.OD && (!this.bAR || this.bAS);
    }

    public boolean abi() {
        return this.bAS;
    }

    public void bW(boolean bl2) {
        this.bAS = bl2;
    }

    public String getName() {
        return this.m_name;
    }

    public abstract Collection uZ();

    public abstract avE a(auk var1, boolean var2, boolean var3, boolean var4, long var5);

    public avE a(long l2, boolean bl2, boolean bl3, boolean bl4, long l3) {
        if (this.bAU != null && this.abh()) {
            auk auk2;
            try {
                auk2 = this.bAU.aJ(l2);
            }
            catch (IOException iOException) {
                a.error((Object)("Impossible de charger le son d'id " + l2));
                return null;
            }
            if (auk2 == null) {
                a.error((Object)("Impossible de charger le son d'id " + l2));
                return null;
            }
            return this.a(auk2, bl2, bl3, bl4, l3);
        }
        return null;
    }

    public abstract void b(avE var1);

    public abstract void n(float var1, float var2);

    public abstract void G(float var1);

    public abstract void o(float var1, float var2);

    public abstract void c(boolean var1, boolean var2);

    public avE i(long l2, long l3) {
        auk auk2;
        try {
            auk2 = this.bAU.aJ(l2);
        }
        catch (IOException iOException) {
            a.error((Object)("Impossible de pr\u00e9parer le son d'id " + l2));
            return null;
        }
        if (auk2 == null) {
            a.error((Object)("Impossible de pr\u00e9parer le son d'id " + l2));
            return null;
        }
        return this.a(auk2, l3);
    }

    public avE a(auk auk2, long l2) {
        if (this.abh()) {
            return this.bAT.a(auk2, this, l2);
        }
        return null;
    }

    public abstract void bM();

    public void ay(long l2) {
        int n2;
        for (n2 = this.bAZ.size() - 1; n2 >= 0; --n2) {
            this.bAY.add(this.bAZ.remove(n2));
        }
        for (n2 = this.bBa.size() - 1; n2 >= 0; --n2) {
            this.bAY.remove(this.bBa.remove(n2));
        }
        int n3 = this.bAY.size();
        for (n2 = 0; n2 < n3; ++n2) {
            ((pl_0)this.bAY.get(n2)).a(this, l2);
        }
        float f = 0.0f;
        if (this.bAP > 0.0f) {
            f = Math.min(this.bAQ, this.Ov + this.bAP);
        } else if (this.bAP < 0.0f) {
            f = Math.max(this.bAQ, this.Ov + this.bAP);
        } else {
            return;
        }
        if (f == this.bAQ) {
            this.bAP = 0.0f;
            this.bAQ = 0.0f;
        }
        this.setGain(f);
    }

    public void j(float f, float f2) {
        if (f2 > 0.0f) {
            if (f < 0.0f) {
                f = 0.0f;
            } else if (f > this.getMaxGain()) {
                this.setMaxGain(f);
            }
            this.bAQ = f;
            this.bAP = (f - this.getGain()) * 10.0f / f2;
        }
    }

    public abstract void stop();

    public abstract void c(avE var1);

    public void pause() {
    }

    public void restart() {
    }

    public boolean cL(int n2) {
        if (this.bAV == n2) {
            return false;
        }
        this.bAV = n2;
        return true;
    }

    public boolean abj() {
        return this.bAW;
    }

    public void bX(boolean bl2) {
        this.bAW = bl2;
    }
}

