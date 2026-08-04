/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from pd
 */
class pd_1 {
    private static final float abg = 0.002f;
    private final lb_0 abh = new lb_0();
    private final aBp abi = new aBp();
    private final aBp abj = new aBp();
    static final /* synthetic */ boolean bb;
    final /* synthetic */ qi_1 abk;

    private pd_1(qi_1 qi_12) {
        this.abk = qi_12;
    }

    final void clear() {
        this.abh.clear();
        this.abi.clear();
        this.abj.clear();
    }

    final float[] cC(int n2) {
        if (!bb && !this.abh.contains(n2)) {
            throw new AssertionError((Object)"Le groupe n'est pas en cache");
        }
        return (float[])this.abh.get(n2);
    }

    final float[] cD(int n2) {
        return (float[])this.abh.get(n2);
    }

    final void c(int n2, float[] fArray) {
        if (!bb && this.abh.bY(n2)) {
            throw new AssertionError((Object)"Le groupe est d\u00e9j\u00e0 en cache");
        }
        this.abh.c(n2, fArray);
    }

    private boolean a(float[] fArray, float f) {
        if (fArray[0] > 0.0f) {
            fArray[0] = fArray[0] - f;
            fArray[1] = fArray[1] - f;
            fArray[2] = fArray[2] - f;
            if (fArray[0] < 0.0f) {
                fArray[2] = 0.0f;
                fArray[1] = 0.0f;
                fArray[0] = 0.0f;
            }
            return false;
        }
        if (fArray[3] > 0.0f) {
            fArray[3] = fArray[3] - f;
            if (fArray[3] < 0.0f) {
                fArray[3] = 0.0f;
            }
            return false;
        }
        return true;
    }

    private boolean b(float[] fArray, float f) {
        if (fArray[0] > 0.0f) {
            fArray[0] = fArray[0] - f;
            fArray[1] = fArray[1] - f;
            fArray[2] = fArray[2] - f;
            fArray[3] = fArray[3] - f;
            if (fArray[0] < 0.0f) {
                fArray[3] = 0.0f;
                fArray[2] = 0.0f;
                fArray[1] = 0.0f;
                fArray[0] = 0.0f;
            }
            return false;
        }
        return true;
    }

    private boolean c(float[] fArray, float f) {
        if (fArray[3] < 1.0f) {
            fArray[3] = fArray[3] + f;
            if (fArray[3] > 1.0f) {
                fArray[3] = 1.0f;
            }
            return false;
        }
        if (fArray[0] < 1.0f) {
            fArray[0] = fArray[0] + f;
            fArray[1] = fArray[1] + f;
            fArray[2] = fArray[2] + f;
            if (fArray[0] > 1.0f) {
                fArray[2] = 1.0f;
                fArray[1] = 1.0f;
                fArray[0] = 1.0f;
            }
            return false;
        }
        return true;
    }

    private boolean d(float[] fArray, float f) {
        if (fArray[0] < 1.0f) {
            fArray[0] = fArray[0] + f;
            fArray[1] = fArray[1] + f;
            fArray[2] = fArray[2] + f;
            fArray[3] = fArray[3] + f;
            if (fArray[0] > 1.0f) {
                fArray[3] = 1.0f;
                fArray[2] = 1.0f;
                fArray[1] = 1.0f;
                fArray[0] = 1.0f;
            }
            return false;
        }
        return true;
    }

    void a(int n2, boolean bl2) {
        float f = 0.002f * (float)n2;
        if (f > 1.0f) {
            f = 1.0f;
        }
        ll_0 ll_02 = this.abh.pK();
        for (int j = this.abh.size(); j > 0; --j) {
            ll_02.fK();
            int n3 = ll_02.kR();
            if (this.abj.contains(n3)) continue;
            if (bl2) {
                this.abi.nk(n3);
            }
            float[] fArray = (float[])ll_02.value();
            if (qi_1.cW(n3) || this.abi.contains(n3)) {
                if (!this.a(n3, fArray, f)) continue;
                ll_02.remove();
                this.abi.remove(n3);
                continue;
            }
            if (!this.b(n3, fArray, f)) continue;
            ll_02.remove();
            this.abi.remove(n3);
        }
    }

    private boolean a(int n2, float[] fArray, float f) {
        if (this.abk.R(qi_1.a(this.abk), n2)) {
            if (this.c(fArray, f)) {
                this.abj.nk(n2);
            }
            return false;
        }
        if (this.a(fArray, f)) {
            this.abj.nk(n2);
            return true;
        }
        return false;
    }

    private boolean b(int n2, float[] fArray, float f) {
        if (this.abk.R(qi_1.a(this.abk), n2)) {
            if (this.d(fArray, f)) {
                this.abj.nk(n2);
            }
            return false;
        }
        if (this.b(fArray, f)) {
            this.abj.nk(n2);
            return true;
        }
        return false;
    }

    /* synthetic */ pd_1(qi_1 qi_12, adu_1 adu_12) {
        this(qi_12);
    }

    static /* synthetic */ aBp a(pd_1 pd_12) {
        return pd_12.abi;
    }

    static /* synthetic */ aBp b(pd_1 pd_12) {
        return pd_12.abj;
    }

    static {
        bb = !qi_1.class.desiredAssertionStatus();
    }
}

