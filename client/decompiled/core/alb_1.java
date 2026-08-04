/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.Random;
import org.apache.log4j.Logger;

/*
 * Renamed from aLB
 */
public abstract class alb_1 {
    protected static final Logger a = Logger.getLogger(alb_1.class);
    protected final lb_0 hE = new lb_0();
    private final jg_0[] dVP = new jg_0[51];
    private final Random Ey = new Random(System.currentTimeMillis());

    protected alb_1() {
        for (int j = 0; j < 51; ++j) {
            this.dVP[j] = new jg_0();
        }
    }

    public void a(oj_0 oj_02) {
        this.hE.c(oj_02.getId(), oj_02);
        if (oj_02.to()) {
            this.dVP[ka_1.bK(oj_02.getValue())].add(oj_02.getId());
        }
    }

    public oj_0 pj(int n2) {
        return (oj_0)this.hE.get(n2);
    }

    public oj_0 pk(int n2) {
        oj_0 oj_02 = null;
        while (oj_02 == null) {
            while (this.dVP[n2].isEmpty()) {
                --n2;
            }
            oj_02 = (oj_0)this.hE.get(this.dVP[n2].get(this.Ey.nextInt(this.dVP[n2].size())));
        }
        return oj_02;
    }

    public oj_0 pl(int n2) {
        return this.cl(n2, 0);
    }

    public oj_0 cl(int n2, int n3) {
        oj_0 oj_02 = null;
        while (oj_02 == null) {
            oj_02 = this.pk(n2);
            if (oj_02.ts() + n3 >= 100 || this.Ey.nextInt(100) - n3 <= oj_02.ts()) continue;
            oj_02 = null;
        }
        return oj_02;
    }
}

