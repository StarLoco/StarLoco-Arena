/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

/*
 * Renamed from td
 */
public class td_2
extends aaH {
    private final ArrayList Hy = new ArrayList();
    private final float[] amz = new float[4];
    private int IP;
    private int wg;
    private final ys amA = ys.aCq;

    public void b(vP vP2) {
        this.Hy.add(vP2);
    }

    public void setDuration(int n2) {
        this.wg = n2;
    }

    public int getDuration() {
        return this.wg;
    }

    public void bI(int n2) {
        if (this.Hy.size() < 2) {
            return;
        }
        this.IP += n2;
        if (this.IP >= this.wg) {
            this.IP = 0;
            this.Hy.add(this.Hy.remove(0));
        }
        vP vP2 = (vP)this.Hy.get(0);
        vP vP3 = (vP)this.Hy.get(1);
        this.amz[0] = this.amA.b(vP2.Cp(), vP3.Cp(), this.IP, this.wg);
        this.amz[1] = this.amA.b(vP2.Cq(), vP3.Cq(), this.IP, this.wg);
        this.amz[2] = this.amA.b(vP2.Cr(), vP3.Cr(), this.IP, this.wg);
        this.amz[3] = this.amA.b(vP2.getAlpha(), vP3.getAlpha(), this.IP, this.wg);
        this.q(this.amz);
    }

    public void j() {
        super.j();
        this.Hy.clear();
    }
}

