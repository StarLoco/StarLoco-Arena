/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

public abstract class aPk {
    public static final int eoJ = -1;
    protected ys amA;
    protected int IP;
    protected int wg;
    protected int eoK = 1;
    protected boolean eoL = true;
    protected int eoM = 0;
    protected int HX = 0;
    protected Object eoN;
    protected Object eoO;
    protected boolean bKE = false;
    protected acw_1 eoP;
    private ArrayList G = null;

    public void a(yx_0 yx_02) {
        if (this.G == null) {
            this.G = new ArrayList(3);
        }
        this.G.add(yx_02);
    }

    public void b(yx_0 yx_02) {
        if (this.G != null) {
            this.G.remove(yx_02);
        }
    }

    public boolean isPaused() {
        return this.bKE;
    }

    public void setPaused(boolean bl2) {
        this.bKE = bl2;
    }

    public long getDuration() {
        return this.wg;
    }

    public void setDuration(int n2) {
        this.wg = n2;
    }

    public int getDelay() {
        return this.HX;
    }

    public void setDelay(int n2) {
        this.HX = n2;
    }

    public boolean aYQ() {
        return this.eoL;
    }

    public void fv(boolean bl2) {
        this.eoL = bl2;
    }

    public Object aYR() {
        return this.eoN;
    }

    public void aJ(Object object) {
        this.eoN = object;
    }

    public Object aYS() {
        return this.eoO;
    }

    public void aK(Object object) {
        this.eoO = object;
    }

    public int aYT() {
        return this.eoK;
    }

    public void pT(int n2) {
        assert (n2 == -1 || n2 > 0) : "La valeur de repeat d\u00e9finie n'est pas valide ( inf\u00e9rieure ou \u00e9gale \u00e0 0)";
        this.eoK = n2;
    }

    public void a(ys ys2) {
        this.amA = ys2;
    }

    public void a(acw_1 acw_12) {
        this.eoP = acw_12;
    }

    public acw_1 aYU() {
        return this.eoP;
    }

    public boolean aS(int n2) {
        if (this.bKE) {
            return true;
        }
        if (this.HX > 0) {
            this.HX -= n2;
        }
        if (this.HX > 0) {
            return false;
        }
        if (this.HX < 0) {
            this.IP -= this.HX;
            this.HX = 0;
        } else {
            this.IP += n2;
        }
        if (this.IP >= this.wg) {
            ++this.eoM;
            if (this.eoM != this.eoK) {
                if (this.eoL) {
                    Object object = this.eoN;
                    this.eoN = this.eoO;
                    this.eoO = object;
                }
                this.IP = this.wg != 0 ? (this.IP %= this.wg) : 0;
            } else {
                this.IP = this.wg;
            }
        }
        if (this.eoK != -1 && this.eoM >= this.eoK) {
            this.eoP.b(this);
            return false;
        }
        return true;
    }

    public void ly() {
        this.cleanUp();
        if (this.G != null) {
            for (int j = this.G.size() - 1; j >= 0; --j) {
                ((yx_0)this.G.get(j)).a(this, d_0.f);
            }
        }
    }

    public void cleanUp() {
    }
}

