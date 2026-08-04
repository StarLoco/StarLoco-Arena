/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

/*
 * Renamed from aJa
 */
public class aja_1 {
    protected final ArrayList ari = new ArrayList();
    protected int EN;
    protected int EO;
    protected int EP;
    protected int EQ;
    protected int fb;
    protected int fc;

    public aja_1() {
        this.reset();
    }

    public acm_1 ch(int n2, int n3) {
        if (n2 < this.EN || n2 >= this.EN + this.fb) {
            return null;
        }
        if (n3 < this.EO || n3 >= this.EO + this.fc) {
            return null;
        }
        int n4 = this.bO(n2, n3);
        if (n4 < 0) {
            return null;
        }
        dc_0 dc_02 = (dc_0)this.ari.get(n4);
        if (dc_02 != null) {
            return dc_02.Ls();
        }
        return null;
    }

    public boolean F(int n2, int n3) {
        return n2 >= this.EN && n2 < this.EN + this.fb && n3 >= this.EO && n3 < this.EO + this.fc;
    }

    public boolean bD(int n2, int n3) {
        int n4 = this.bO(n2, n3);
        dc_0 dc_02 = (dc_0)this.ari.get(n4);
        return dc_02 == null || dc_02.ak(n2, n3);
    }

    public boolean bE(int n2, int n3) {
        int n4 = this.bO(n2, n3);
        dc_0 dc_02 = (dc_0)this.ari.get(n4);
        return dc_02 == null || dc_02.ak(n2, n3);
    }

    public int getMinX() {
        return this.EN;
    }

    public int getMinY() {
        return this.EO;
    }

    public int getWidth() {
        return this.fb;
    }

    public int getHeight() {
        return this.fc;
    }

    public void reset() {
        this.ari.clear();
        this.EN = Integer.MAX_VALUE;
        this.EO = Integer.MAX_VALUE;
        this.EP = Integer.MIN_VALUE;
        this.EQ = Integer.MIN_VALUE;
        this.fb = 0;
        this.fc = 0;
    }

    public void a(dc_0 dc_02, int n2, int n3) {
        this.ari.add(dc_02);
        n3 *= 18;
        if ((n2 *= 18) < this.EN) {
            this.EN = n2;
        }
        if (n2 > this.EP) {
            this.EP = n2;
        }
        if (n3 < this.EO) {
            this.EO = n3;
        }
        if (n3 > this.EQ) {
            this.EQ = n3;
        }
        this.fb = 18 + this.EP - this.EN;
        this.fc = 18 + this.EQ - this.EO;
        assert (this.ari.size() < 100) : "C'est pas un peu abus\u00e9, comme taille : " + this.ari.size() + " ?????";
        assert (this.bO(n2, n3) == this.ari.size() - 1) : " Map ajout\u00e9e non valide. index : " + this.bO(n2, n3) + " attendu : " + (this.ari.size() - 1);
    }

    protected int bO(int n2, int n3) {
        if (n2 < this.EN) {
            return -1;
        }
        if (n3 < this.EO) {
            return -1;
        }
        int n4 = (n2 - this.EN) / 18;
        int n5 = (n3 - this.EO) / 18;
        assert (n4 >= 0);
        assert (n5 >= 0);
        return n5 * (this.fb / 18) + n4;
    }
}

