/*
 * Decompiled with CFR 0.152.
 */
import java.util.List;
import java.util.Map;

public class zx
extends ii_2 {
    final List aFt;
    int Ip = 0;
    ahs_2 Io;

    zx(lq_0 lq_02) {
        this.aFt = lq_02.qH();
    }

    public zx(String string) {
        this(string, new afe_0());
    }

    public zx(String string, ahs_2 ahs_22) {
        this.Io = ahs_22;
        try {
            lq_0 lq_02 = new lq_0(string, ahs_22);
            this.aFt = lq_02.qH();
        }
        catch (NullPointerException nullPointerException) {
            throw new fe("Failed to initialize Parser", nullPointerException);
        }
    }

    public ki_1 Gt() {
        return this.Gu();
    }

    public akn_1 a(ki_1 ki_12, Map map) {
        aNi aNi2 = new aNi(ki_12, map);
        aNi2.a(this.Pb);
        return aNi2.aXq();
    }

    ki_1 Gu() {
        ki_1 ki_12 = this.Gv();
        if (ki_12 == null) {
            return null;
        }
        ki_1 ki_13 = this.Gw();
        if (ki_13 != null) {
            ki_12.a(ki_13);
        }
        return ki_12;
    }

    ki_1 Gv() {
        ln_0 ln_02 = this.GB();
        if (ln_02 == null) {
            throw new IllegalStateException("a LITERAL or '%'");
        }
        switch (ln_02.getType()) {
            case 1000: {
                this.GC();
                return new ki_1(0, ln_02.getValue());
            }
            case 37: {
                anz_1 anz_12;
                this.GC();
                ln_0 ln_03 = this.GB();
                this.a(ln_03, "a FORMAT_MODIFIER, KEYWORD or LEFT_PARENTHESIS");
                if (ln_03.getType() == 1002) {
                    acd_1 acd_12 = acd_1.kx((String)ln_03.getValue());
                    this.GC();
                    anz_12 = this.Gx();
                    anz_12.a(acd_12);
                } else {
                    anz_12 = this.Gx();
                }
                return anz_12;
            }
        }
        return null;
    }

    ki_1 Gw() {
        ln_0 ln_02 = this.GB();
        if (ln_02 == null) {
            return null;
        }
        return this.Gu();
    }

    anz_1 Gx() {
        ln_0 ln_02 = this.GB();
        this.a(ln_02, "a LEFT_PARENTHESIS or KEYWORD");
        int n2 = ln_02.getType();
        switch (n2) {
            case 1004: {
                return this.Gy();
            }
            case 40: {
                this.GC();
                return this.Gz();
            }
        }
        throw new IllegalStateException("Unexpected token " + ln_02);
    }

    anz_1 Gy() {
        ln_0 ln_02 = this.GA();
        awg_0 awg_02 = new awg_0(ln_02.getValue());
        ln_0 ln_03 = this.GB();
        if (ln_03 != null && ln_03.getType() == 1006) {
            List list = new mm_1((String)ln_03.getValue()).qH();
            awg_02.r(list);
            this.GC();
        }
        return awg_02;
    }

    anz_1 Gz() {
        qS qS2 = new qS();
        ki_1 ki_12 = this.Gu();
        qS2.b(ki_12);
        ln_0 ln_02 = this.GA();
        if (ln_02.getType() != 41) {
            throw new IllegalStateException("Expecting RIGHT_PARENTHESIS token but got " + ln_02);
        }
        return qS2;
    }

    ln_0 GA() {
        if (this.Ip < this.aFt.size()) {
            return (ln_0)this.aFt.get(this.Ip++);
        }
        return null;
    }

    ln_0 GB() {
        if (this.Ip < this.aFt.size()) {
            return (ln_0)this.aFt.get(this.Ip);
        }
        return null;
    }

    void GC() {
        ++this.Ip;
    }

    void a(ln_0 ln_02, String string) {
        if (ln_02 == null) {
            throw new IllegalStateException("All tokens consumed but was expecting " + string);
        }
    }
}

