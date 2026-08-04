/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.GregorianCalendar;
import java.util.TimeZone;
import org.apache.log4j.Logger;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 * Renamed from rD
 */
public class rd_1
implements acx_1 {
    private static final Logger a = Logger.getLogger(rd_1.class);
    private static final TimeZone ahy = TimeZone.getDefault();
    private static final GregorianCalendar ahz = new GregorianCalendar(ahy);
    public static final long ahA = 0L;
    public static final acx_1 ahB;
    private int acM;
    private int acN;
    private int acO;
    private int ahC;
    private int ahD;
    private int ahE;
    private long ahF;

    private rd_1() {
    }

    public rd_1(acx_1 acx_12) {
        if (acx_12 != null) {
            this.a(acx_12);
        } else {
            this.a(ahB);
        }
    }

    public rd_1(int n2, int n3, int n4, int n5, int n6, int n7) {
        this.set(n2, n3, n4, n5, n6, n7);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void set(long l2) {
        if (l2 == 0L) {
            this.xh();
            return;
        }
        GregorianCalendar gregorianCalendar = ahz;
        synchronized (gregorianCalendar) {
            ahz.clear();
            ahz.setTimeZone(ahy);
            ahz.setTimeInMillis(l2);
            this.acM = ahz.get(13);
            this.acN = ahz.get(12);
            this.acO = ahz.get(11);
            this.ahC = ahz.get(5);
            this.ahD = ahz.get(2) + 1;
            this.ahE = ahz.get(0) == 1 ? ahz.get(1) : 1 - ahz.get(1);
            this.ahF = ahz.getTimeInMillis();
        }
    }

    public void a(acx_1 acx_12) {
        if (acx_12 == null) {
            throw new IllegalArgumentException("Argument 0 for @NotNull parameter of com/ankamagames/baseImpl/common/clientAndServer/game/time/calendar/GameDate.set must not be null");
        }
        if (acx_12.xg()) {
            this.xh();
        } else {
            this.set(acx_12.getSeconds(), acx_12.getMinutes(), acx_12.getHours(), acx_12.getDay(), acx_12.getMonth(), acx_12.getYear());
        }
    }

    public void set(int n2, int n3, int n4, int n5, int n6, int n7) {
        this.ahE = n7;
        this.ahD = n6;
        this.ahC = n5;
        this.acO = n4;
        this.acN = n3;
        this.acM = n2;
        this.normalize();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void normalize() {
        GregorianCalendar gregorianCalendar = ahz;
        synchronized (gregorianCalendar) {
            ahz.clear();
            ahz.setTimeZone(ahy);
            ahz.set(this.ahE, this.ahD - 1, this.ahC, this.acO, this.acN, this.acM);
            this.ahF = ahz.getTimeInMillis();
            this.acM = ahz.get(13);
            this.acN = ahz.get(12);
            this.acO = ahz.get(11);
            this.ahC = ahz.get(5);
            this.ahD = ahz.get(2) + 1;
            this.ahE = ahz.get(0) == 1 ? ahz.get(1) : 1 - ahz.get(1);
        }
    }

    public static rd_1 xf() {
        rd_1 rd_12 = new rd_1();
        rd_12.xh();
        return rd_12;
    }

    public static void setTimeZone(TimeZone timeZone) {
        ahz.setTimeZone(timeZone);
    }

    public boolean xg() {
        return this.ahF == 0L;
    }

    private void xh() {
        this.ahF = 0L;
        this.ahE = ahB.getYear();
        this.ahD = ahB.getMonth();
        this.ahC = ahB.getDay();
        this.acO = ahB.getHours();
        this.acN = ahB.getMinutes();
        this.acM = ahB.getSeconds();
    }

    public boolean b(acx_1 acx_12) {
        if (acx_12 == null) {
            throw new IllegalArgumentException("Argument 0 for @NotNull parameter of com/ankamagames/baseImpl/common/clientAndServer/game/time/calendar/GameDate.before must not be null");
        }
        return this.xg() || this.f(acx_12) < 0;
    }

    public boolean c(acx_1 acx_12) {
        if (acx_12 == null) {
            throw new IllegalArgumentException("Argument 0 for @NotNull parameter of com/ankamagames/baseImpl/common/clientAndServer/game/time/calendar/GameDate.beforeOrEquals must not be null");
        }
        return this.xg() || this.f(acx_12) <= 0;
    }

    public boolean d(acx_1 acx_12) {
        if (acx_12 == null) {
            throw new IllegalArgumentException("Argument 0 for @NotNull parameter of com/ankamagames/baseImpl/common/clientAndServer/game/time/calendar/GameDate.after must not be null");
        }
        return !this.xg() && this.f(acx_12) > 0;
    }

    public boolean e(acx_1 acx_12) {
        if (acx_12 == null) {
            throw new IllegalArgumentException("Argument 0 for @NotNull parameter of com/ankamagames/baseImpl/common/clientAndServer/game/time/calendar/GameDate.afterOrEquals must not be null");
        }
        return !this.xg() && this.f(acx_12) >= 0;
    }

    public int f(acx_1 acx_12) {
        if (this.xg()) {
            return acx_12.xg() ? 0 : -1;
        }
        if (this.ahE > acx_12.getYear()) {
            return 1;
        }
        if (this.ahE < acx_12.getYear()) {
            return -1;
        }
        if (this.ahD > acx_12.getMonth()) {
            return 1;
        }
        if (this.ahD < acx_12.getMonth()) {
            return -1;
        }
        if (this.ahC > acx_12.getDay()) {
            return 1;
        }
        if (this.ahC < acx_12.getDay()) {
            return -1;
        }
        if (this.acO > acx_12.getHours()) {
            return 1;
        }
        if (this.acO < acx_12.getHours()) {
            return -1;
        }
        if (this.acN > acx_12.getMinutes()) {
            return 1;
        }
        if (this.acN < acx_12.getMinutes()) {
            return -1;
        }
        if (this.acM > acx_12.getSeconds()) {
            return 1;
        }
        if (this.acM < acx_12.getSeconds()) {
            return -1;
        }
        return 0;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        rd_1 rd_12 = (rd_1)object;
        if (this.xg()) {
            return rd_12.xg();
        }
        if (this.ahC != rd_12.ahC) {
            return false;
        }
        if (this.acO != rd_12.acO) {
            return false;
        }
        if (this.acN != rd_12.acN) {
            return false;
        }
        if (this.ahD != rd_12.ahD) {
            return false;
        }
        if (this.acM != rd_12.acM) {
            return false;
        }
        return this.ahE == rd_12.ahE;
    }

    public void e(sl_0 sl_02) {
        if (sl_02 == null) {
            throw new IllegalArgumentException("Argument 0 for @NotNull parameter of com/ankamagames/baseImpl/common/clientAndServer/game/time/calendar/GameDate.add must not be null");
        }
        if (this.xg()) {
            return;
        }
        this.m(sl_02.getSeconds(), sl_02.getMinutes(), sl_02.getHours(), sl_02.getDays());
    }

    public void f(sl_0 sl_02) {
        if (sl_02 == null) {
            throw new IllegalArgumentException("Argument 0 for @NotNull parameter of com/ankamagames/baseImpl/common/clientAndServer/game/time/calendar/GameDate.sub must not be null");
        }
        if (this.xg()) {
            return;
        }
        this.n(sl_02.getSeconds(), sl_02.getMinutes(), sl_02.getHours(), sl_02.getDays());
    }

    public acx_1 b(jx_0 jx_02) {
        if (jx_02 == null) {
            throw new IllegalArgumentException("Argument 0 for @NotNull parameter of com/ankamagames/baseImpl/common/clientAndServer/game/time/calendar/GameDate.add must not be null");
        }
        return this.a(jx_02.getSeconds(), jx_02.getMinutes(), jx_02.getHours(), jx_02.getDays(), jx_02.getMonths(), jx_02.getYears());
    }

    public acx_1 c(jx_0 jx_02) {
        if (jx_02 == null) {
            throw new IllegalArgumentException("Argument 0 for @NotNull parameter of com/ankamagames/baseImpl/common/clientAndServer/game/time/calendar/GameDate.sub must not be null");
        }
        return this.b(jx_02.getSeconds(), jx_02.getMinutes(), jx_02.getHours(), jx_02.getDays(), jx_02.getMonths(), jx_02.getYears());
    }

    public acx_1 dm(int n2) {
        rd_1 rd_12 = new rd_1(this);
        rd_12.n(0, 0, 0, n2);
        return rd_12;
    }

    public acx_1 xi() {
        return this.dm(1);
    }

    public acx_1 dn(int n2) {
        rd_1 rd_12 = new rd_1(this);
        rd_12.m(0, 0, 0, n2);
        return rd_12;
    }

    public acx_1 xj() {
        return this.dn(1);
    }

    public pt_1 g(acx_1 acx_12) {
        assert (!this.xg()) : "Onessaye de calculer une diff\u00e9rence \u00e0 partir d'une date nulle.";
        assert (!acx_12.xg()) : "On essaye de calculer une diff\u00e9rence avec une date nulle";
        this.normalize();
        acx_12.normalize();
        long l2 = (acx_12.uJ() - this.uJ()) / 1000L;
        return new pt_1(l2);
    }

    public int h(acx_1 acx_12) {
        rd_1 rd_12 = new rd_1(0, 0, 0, this.ahC, this.ahD, this.ahE);
        rd_1 rd_13 = new rd_1(0, 0, 0, acx_12.getDay(), acx_12.getMonth(), acx_12.getYear());
        pt_1 pt_12 = new pt_1((rd_13.uJ() - rd_12.uJ()) / 1000L);
        int n2 = pt_12.getDays();
        if (pt_12.getHours() > 12) {
            ++n2;
        }
        return n2;
    }

    public rd_1 a(int n2, int n3, int n4, int n5, int n6, int n7) {
        if (this.xg()) {
            return this;
        }
        this.acM += n2;
        this.acN += n3;
        this.acO += n4;
        this.ahC += n5;
        this.ahD += n6;
        this.ahE += n7;
        this.normalize();
        return this;
    }

    public rd_1 m(int n2, int n3, int n4, int n5) {
        return this.a(n2, n3, n4, n5, 0, 0);
    }

    public rd_1 do(int n2) {
        this.ahD += n2;
        this.normalize();
        return this;
    }

    public rd_1 dp(int n2) {
        this.ahE += n2;
        this.normalize();
        return this;
    }

    public rd_1 n(int n2, int n3, int n4, int n5) {
        if (this.xg()) {
            return this;
        }
        this.acM -= n2;
        this.acN -= n3;
        this.acO -= n4;
        this.ahC -= n5;
        this.normalize();
        return this;
    }

    public rd_1 b(int n2, int n3, int n4, int n5, int n6, int n7) {
        if (this.xg()) {
            return this;
        }
        this.acM -= n2;
        this.acN -= n3;
        this.acO -= n4;
        this.ahC -= n5;
        this.ahD -= n6;
        this.ahE -= n7;
        this.normalize();
        return this;
    }

    public void xk() {
        if (this.xg()) {
            return;
        }
        this.acO = 0;
        this.acN = 0;
        this.acM = 0;
        this.normalize();
    }

    public void xl() {
        if (this.xg()) {
            return;
        }
        this.acN = 0;
        this.acM = 0;
        this.normalize();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int get(int n2) {
        GregorianCalendar gregorianCalendar = ahz;
        synchronized (gregorianCalendar) {
            ahz.setTimeInMillis(this.ahF);
            return ahz.get(n2);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static rd_1 aF(long l2) {
        GregorianCalendar gregorianCalendar = ahz;
        synchronized (gregorianCalendar) {
            ahz.clear();
            ahz.setTimeZone(ahy);
            ahz.setTimeInMillis(l2);
            int n2 = ahz.get(0) == 1 ? ahz.get(1) : 1 - ahz.get(1);
            return new rd_1(ahz.get(13), ahz.get(12), ahz.get(11), ahz.get(5), ahz.get(2) + 1, n2);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public long xm() {
        assert (!this.xg()) : "On essaye de calculer les heurs d'une date nulle";
        GregorianCalendar gregorianCalendar = ahz;
        synchronized (gregorianCalendar) {
            ahz.clear();
            ahz.setTimeZone(ahy);
            ahz.set(this.ahE, this.ahD - 1, this.ahC, this.acO, 0, 0);
            return ahz.getTimeInMillis();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public long xn() {
        assert (!this.xg()) : "On essaye de calculer les jours d'une date nulle";
        GregorianCalendar gregorianCalendar = ahz;
        synchronized (gregorianCalendar) {
            ahz.clear();
            ahz.setTimeZone(ahy);
            ahz.set(this.ahE, this.ahD - 1, this.ahC, 0, 0, 0);
            return ahz.getTimeInMillis();
        }
    }

    public long uJ() {
        return this.ahF;
    }

    public int getDay() {
        return this.ahC;
    }

    public int getHours() {
        return this.acO;
    }

    public int getMinutes() {
        return this.acN;
    }

    public int getMonth() {
        return this.ahD;
    }

    public int getSeconds() {
        return this.acM;
    }

    public int getYear() {
        return this.ahE;
    }

    public void setSeconds(int n2) {
        this.acM = n2;
        this.normalize();
    }

    public void setMinutes(int n2) {
        this.acN = n2;
        this.normalize();
    }

    public void setHours(int n2) {
        this.acO = n2;
        this.normalize();
    }

    public void setDay(int n2) {
        this.ahC = n2;
        this.normalize();
    }

    public void setMonth(int n2) {
        this.ahD = n2;
        this.normalize();
    }

    public void setYear(int n2) {
        this.ahE = n2;
        this.normalize();
    }

    public String toString() {
        if (this.xg()) {
            return "Date{ nulle }";
        }
        int n2 = ahz.getTimeZone().getRawOffset() / 3600000;
        if (n2 >= 0) {
            return az_1.a("{Date : %d/%M/%Y %h:%m:%s UTC+" + n2 + "}", this);
        }
        return az_1.a("{Date : %d/%M/%Y %h:%m:%s UTC-" + n2 + "}", this);
    }

    public String xo() {
        if (this.xg()) {
            return "null date";
        }
        return az_1.a("%d/%M/%Y %hH%mmin", this);
    }

    static {
        a.info((Object)("GameDate initialized. Timezone : " + ahz.getTimeZone()));
        ahB = rd_1.aF(0L);
    }
}

