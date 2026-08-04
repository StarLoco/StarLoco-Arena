/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from ant
 */
public class ant_0
extends dz_2 {
    public static final String TAG = "SpringLayoutData";
    public static final String aTJ = "spld";
    private boolean cIY = false;
    private xs_1 cIZ;
    private xs_1 cJa;
    private xs_1 cJb;
    private xs_1 cJc;
    private xs_1 cJd;
    private xs_1 cJe;
    private xs_1 cJf;
    private xs_1 cJg;
    private att_0 aGK;

    public ant_0() {
    }

    public ant_0(xs_1 xs_12, xs_1 xs_13, xs_1 xs_14, xs_1 xs_15) {
        this.cIZ = xs_12;
        this.cJa = xs_13;
        this.cJb = xs_14;
        this.cJc = xs_15;
    }

    public void a(na_1 na_12) {
        if (na_12 instanceof xs_1 && this.isInTree()) {
            xs_1 xs_12 = (xs_1)na_12;
            this.a(xs_12.getEdge(), xs_12.getValue(), xs_12.getReferentId(), xs_12.getReferentEdge());
            this.cIY = true;
            this.setNeedsToPreProcess();
        }
        super.a(na_12);
    }

    private void aCr() {
        if (this.cIZ != null && this.cJd != null && this.cJf != null) {
            this.cJd = null;
        }
    }

    private void aCs() {
        if (this.cJa != null && this.cJe != null && this.cJg != null) {
            this.cJe = null;
        }
    }

    public String getTag() {
        return TAG;
    }

    public xs_1 getEast() {
        if (this.cJb != null) {
            return this.cJb;
        }
        if (this.cJf == null) {
            if (this.cIZ != null && this.cJd != null) {
                this.cJf = xs_1.a(this.cIZ, this.cJd);
            } else {
                return xs_1.iU(0);
            }
        }
        return this.cJf;
    }

    public void setEast(xs_1 xs_12) {
        this.cJb = xs_12;
    }

    public xs_1 getNorth() {
        if (this.cJc != null) {
            return this.cJc;
        }
        if (this.cJg == null) {
            if (this.cJa != null && this.cJe != null) {
                this.cJg = xs_1.a(this.cJa, this.cJe);
            } else {
                return xs_1.iU(0);
            }
        }
        return this.cJg;
    }

    public void setNorth(xs_1 xs_12) {
        this.cJc = xs_12;
    }

    public xs_1 getX() {
        if (this.cIZ != null) {
            return this.cIZ;
        }
        if (this.cJf == null) {
            if (this.cJb != null && this.cJd != null) {
                this.cJf = xs_1.b(this.cJb, this.cJd);
            } else {
                return xs_1.iU(0);
            }
        }
        return this.cJf;
    }

    public void setX(xs_1 xs_12) {
        this.cIZ = xs_12;
    }

    public xs_1 getY() {
        if (this.cJa != null) {
            return this.cJa;
        }
        if (this.cJg == null) {
            if (this.cJc != null && this.cJe != null) {
                this.cJg = xs_1.b(this.cJc, this.cJe);
            } else {
                return xs_1.iU(0);
            }
        }
        return this.cJg;
    }

    public void setY(xs_1 xs_12) {
        this.cJa = xs_12;
    }

    public xs_1 getWidth() {
        if (this.cJd != null) {
            return this.cJd;
        }
        if (this.cJf == null) {
            if (this.cJb != null && this.cIZ != null) {
                this.cJf = xs_1.b(this.cJb, this.cIZ);
            } else {
                return xs_1.iU(0);
            }
        }
        return this.cJf;
    }

    public void setWidth(xs_1 xs_12) {
        this.cJd = xs_12;
        this.aCr();
    }

    public xs_1 getHeight() {
        if (this.cJe != null) {
            return this.cJe;
        }
        if (this.cJg == null) {
            if (this.cJc != null && this.cJa != null) {
                this.cJg = xs_1.b(this.cJc, this.cJa);
            } else {
                return xs_1.iU(0);
            }
        }
        return this.cJg;
    }

    public void setHeight(xs_1 xs_12) {
        this.cJe = xs_12;
        this.aCs();
    }

    public att_0 getLayout() {
        return this.aGK;
    }

    public void setLayout(att_0 att_02) {
        this.aGK = att_02;
    }

    public xs_1 getConstraint(String string) {
        if (string.equalsIgnoreCase("North")) {
            return this.getNorth();
        }
        if (string.equalsIgnoreCase("East")) {
            return this.getEast();
        }
        if (string.equalsIgnoreCase("West")) {
            return this.getX();
        }
        if (string.equalsIgnoreCase("South")) {
            return this.getY();
        }
        return null;
    }

    public void a(String string, int n2, String string2, String string3) {
        xs_1 xs_12 = xs_1.a(xs_1.iU(n2), new zx_1(this.aGK, string2, string3));
        this.a(string, xs_12);
    }

    public void a(String string, int n2, adg_2 adg_22, String string2) {
        xs_1 xs_12 = xs_1.a(xs_1.iU(n2), new zx_1(this.aGK, adg_22, string2));
        this.a(string, xs_12);
    }

    public void a(String string, xs_1 xs_12) {
        if (string.equalsIgnoreCase("North")) {
            this.setNorth(xs_12);
        } else if (string.equalsIgnoreCase("East")) {
            this.setEast(xs_12);
        } else if (string.equalsIgnoreCase("West")) {
            this.setX(xs_12);
        } else if (string.equalsIgnoreCase("South")) {
            this.setY(xs_12);
        }
    }

    public static ant_0 a(att_0 att_02, adg_2 adg_22) {
        ant_0 ant_02 = new ant_0();
        ant_02.setLayout(att_02);
        ant_02.setX(xs_1.k(adg_22));
        ant_02.setY(xs_1.l(adg_22));
        ant_02.setWidth(xs_1.i(adg_22));
        ant_02.setHeight(xs_1.j(adg_22));
        return ant_02;
    }

    public void aCt() {
    }

    public void a(air_1 air_12) {
        ant_0 ant_02 = (ant_0)air_12;
        super.a((air_1)ant_02);
    }

    public void aaf() {
        aht_1 aht_12;
        super.aaf();
        aiD aiD2 = null;
        adg_2 adg_22 = (adg_2)this.getParentOfType(adg_2.class);
        if (adg_22 != null && (aht_12 = adg_22.getContainer()) != null) {
            aiD2 = aht_12.getLayoutManager();
        }
        if (aiD2 instanceof att_0) {
            this.setLayout((att_0)aiD2);
            if (this.adf instanceof adg_2) {
                ((att_0)aiD2).a((adg_2)this.adf, this);
            }
        }
        int n2 = this.getChildren().size();
        for (int j = 0; j < n2; ++j) {
            na_1 na_12 = (na_1)this.getChildren().get(j);
            if (!(na_12 instanceof xs_1)) continue;
            xs_1 xs_12 = (xs_1)na_12;
            this.a(xs_12.getEdge(), xs_12.getValue(), xs_12.getReferentId(), xs_12.getReferentEdge());
            this.cIY = true;
            this.setNeedsToPreProcess();
        }
    }

    public boolean cc(int n2) {
        boolean bl2 = super.cc(n2);
        if (this.cIY) {
            aht_1 aht_12 = (aht_1)this.getParentOfType(aht_1.class);
            aht_12.Am();
            this.cIY = false;
        }
        return bl2;
    }

    public String toString() {
        return "[x=" + this.getX() + ", y=" + this.getY() + ", width=" + this.getWidth() + ", height=" + this.getHeight() + "]";
    }
}

