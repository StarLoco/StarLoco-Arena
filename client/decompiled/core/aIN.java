/*
 * Decompiled with CFR 0.152.
 */
class aIN
implements alx_0 {
    int dQH = 0;

    private aIN() {
    }

    public long getId() {
        return 1L;
    }

    public void c(long l2) {
    }

    public boolean a(pr_0 pr_02) {
        fp_2 fp_22 = (fp_2)pr_02;
        if (fp_2.a(fp_22) - this.dQH != 1) {
            System.err.println("msg.pos(" + fp_2.a(fp_22) + ") / lastpos(" + this.dQH + ") / delta(" + (fp_2.a(fp_22) - this.dQH) + ")");
        }
        if (fp_2.a(fp_22) % 100000 == 0) {
            System.out.println("checkpoint(" + fp_2.a(fp_22) + ")");
        }
        this.dQH = fp_2.a(fp_22);
        try {
            Thread.sleep(10 + (int)(Math.random() * 50.0));
        }
        catch (InterruptedException interruptedException) {
            acu_1.sP().error((Object)"Exception", (Throwable)interruptedException);
        }
        return false;
    }

    /* synthetic */ aIN(mc_0 mc_02) {
        this();
    }
}

