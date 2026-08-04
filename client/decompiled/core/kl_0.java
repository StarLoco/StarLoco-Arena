/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from kl
 */
class kl_0
implements Runnable {
    kl_0() {
    }

    public void run() {
        if (nW.sM() < System.currentTimeMillis()) {
            if (nW.sN() < nW.sO()) {
                nW.sP().error((Object)("Too high ping detected : Server reply number is low, " + nW.sN() + " != " + nW.sO() + "."));
                nW.bF(nW.sO());
            } else {
                axp_0 axp_02 = apN.aDK().vJ();
                if (axp_02 != null) {
                    nW.bF(0);
                    for (int j = nW.sQ().length - 1; 0 <= j; --j) {
                        axp_02.b(new asg_0(nW.sQ()[j], 0));
                    }
                }
            }
        }
    }
}

