/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from pT
 */
public class pt_1
implements sl_0 {
    private int acM;
    private int acN;
    private int acO;
    private int acP;
    private long acQ;

    public pt_1(long l2) {
        this.acQ = l2;
        this.uG();
    }

    public pt_1(sl_0 sl_02) {
        this.acM = sl_02.getSeconds();
        this.acN = sl_02.getMinutes();
        this.acO = sl_02.getHours();
        this.acP = sl_02.getDays();
        this.uH();
    }

    public pt_1(int n2, int n3, int n4, int n5) {
        this.acM = n2;
        this.acN = n3;
        this.acO = n4;
        this.acP = n5;
        this.uH();
    }

    public void a(sl_0 sl_02) {
        if (sl_02 == null) {
            this.a(bLM);
            return;
        }
        this.acM = sl_02.getSeconds();
        this.acN = sl_02.getMinutes();
        this.acO = sl_02.getHours();
        this.acP = sl_02.getDays();
        this.acQ = sl_02.uJ();
    }

    private void uG() {
        long l2 = this.acQ;
        this.acP = (int)(l2 / 86400L);
        this.acO = (int)((l2 -= (long)(this.acP * 3600 * 24)) / 3600L);
        this.acN = (int)((l2 -= (long)(this.acO * 3600)) / 60L);
        this.acM = (int)(l2 -= (long)(this.acN * 60));
    }

    private void uH() {
        this.acQ = this.acM + this.acN * 60 + this.acO * 3600 + this.acP * 3600 * 24;
    }

    public int getSeconds() {
        return this.acM;
    }

    public int getMinutes() {
        return this.acN;
    }

    public int getHours() {
        return this.acO;
    }

    public int getDays() {
        return this.acP;
    }

    public boolean uI() {
        return this.acQ > 0L;
    }

    public boolean isEmpty() {
        return this.acQ == 0L;
    }

    public boolean equals(Object object) {
        if (object == this || object instanceof sl_0) {
            sl_0 sl_02 = (sl_0)object;
            return this.uJ() == sl_02.uJ();
        }
        return false;
    }

    public boolean b(sl_0 sl_02) {
        if (sl_02 == null) {
            throw new IllegalArgumentException("Argument 0 for @NotNull parameter of com/ankamagames/baseImpl/common/clientAndServer/game/time/calendar/GameInterval.greaterThan must not be null");
        }
        return this.acQ > sl_02.uJ();
    }

    public boolean c(sl_0 sl_02) {
        if (sl_02 == null) {
            throw new IllegalArgumentException("Argument 0 for @NotNull parameter of com/ankamagames/baseImpl/common/clientAndServer/game/time/calendar/GameInterval.lowerThan must not be null");
        }
        return this.acQ < sl_02.uJ();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{Interval: ");
        stringBuilder.append(this.acP).append("d ");
        stringBuilder.append(this.acO).append(":").append(this.acN).append(';').append(this.acM);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public long uJ() {
        return this.acQ;
    }

    public static pt_1 ax(long l2) {
        return new pt_1(l2);
    }

    public int d(sl_0 sl_02) {
        if (this.isEmpty()) {
            return 0;
        }
        if (sl_02.isEmpty()) {
            throw new ArithmeticException("/ by zero");
        }
        long l2 = this.acQ;
        long l3 = sl_02.uJ();
        if (l3 == 0L) {
            throw new ArithmeticException("/ by zero");
        }
        return (int)(l2 / l3);
    }

    public void e(sl_0 sl_02) {
        if (sl_02 == null) {
            return;
        }
        this.acQ += sl_02.uJ();
        this.uG();
    }

    public void cI(int n2) {
        this.acQ *= (long)n2;
    }
}

