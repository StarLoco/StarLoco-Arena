/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from wT
 */
public abstract class wt_0
extends Ik {
    protected all_1 avL = new pp_2();
    nk_2 avM;
    Gf avN;

    public void a(nk_2 nk_22) {
        this.avM = nk_22;
    }

    public void start() {
        int n2 = 0;
        if (this.avN == null) {
            this.eg("Missing discriminator. Aborting");
            ++n2;
        }
        if (!this.avN.isStarted()) {
            this.eg("Discriminator has not started successfully. Aborting");
            ++n2;
        }
        if (n2 == 0) {
            super.start();
        }
    }

    public void stop() {
        for (adr_0 adr_02 : this.avL.acx()) {
            adr_02.stop();
        }
    }

    protected abstract long y(Object var1);

    protected void z(Object object) {
        long l2;
        if (!this.isStarted()) {
            return;
        }
        String string = this.avN.Q(object);
        adr_0 adr_02 = this.avL.d(string, l2 = this.y(object));
        if (adr_02 == null) {
            try {
                adr_02 = this.avM.a(this.Pb, string);
                if (adr_02 != null) {
                    this.avL.a(string, adr_02, l2);
                }
            }
            catch (azG azG2) {
                this.e("Failed to build appender for [" + string + "]", azG2);
                return;
            }
        }
        this.avL.cp(l2);
        adr_02.T(object);
    }

    public Gf Dq() {
        return this.avN;
    }

    public void a(Gf gf) {
        this.avN = gf;
    }

    public String Dr() {
        if (this.avN != null) {
            return this.avN.getKey();
        }
        return null;
    }
}

