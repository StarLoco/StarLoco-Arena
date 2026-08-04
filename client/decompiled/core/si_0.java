/*
 * Decompiled with CFR 0.152.
 */
import com.xuggle.ferry.FerryJNI;

/*
 * Renamed from sI
 */
public class si_0 {
    private volatile long hf;
    protected boolean hg;
    private jr_2 hh;

    protected si_0(long l2, boolean bl2) {
        this.hf = l2;
        this.hg = bl2;
    }

    public static long a(si_0 si_02) {
        if (si_02 == null) {
            return 0L;
        }
        return si_02.dv();
    }

    public long dv() {
        if (this.hf == 0L) {
            throw new IllegalStateException("underlying native object already deleted");
        }
        return this.hf;
    }

    public boolean equals(Object object) {
        boolean bl2 = false;
        if (object instanceof si_0) {
            bl2 = ((si_0)object).hf == this.hf;
        }
        return bl2;
    }

    public int hashCode() {
        return (int)this.hf;
    }

    protected void finalize() {
        this.delete();
    }

    public synchronized void delete() {
        if (this.hf != 0L && this.hg) {
            this.hg = false;
            FerryJNI.delete_Logger(this.hf);
        }
        this.hf = 0L;
    }

    public static si_0 ch(String string) {
        long l2 = FerryJNI.Logger_getLogger(string);
        return l2 == 0L ? null : new si_0(l2, false);
    }

    public static si_0 ci(String string) {
        long l2 = FerryJNI.Logger_getStaticLogger(string);
        return l2 == 0L ? null : new si_0(l2, false);
    }

    public boolean a(String string, int n2, bX bX2, String string2) {
        return FerryJNI.Logger_log(this.hf, this, string, n2, bX2.dZ(), string2);
    }

    public boolean a(String string, int n2, String string2) {
        return FerryJNI.Logger_error(this.hf, this, string, n2, string2);
    }

    public boolean b(String string, int n2, String string2) {
        return FerryJNI.Logger_warn(this.hf, this, string, n2, string2);
    }

    public boolean c(String string, int n2, String string2) {
        return FerryJNI.Logger_info(this.hf, this, string, n2, string2);
    }

    public boolean d(String string, int n2, String string2) {
        return FerryJNI.Logger_debug(this.hf, this, string, n2, string2);
    }

    public boolean e(String string, int n2, String string2) {
        return FerryJNI.Logger_trace(this.hf, this, string, n2, string2);
    }

    public boolean a(bX bX2) {
        return FerryJNI.Logger_isLogging(this.hf, this, bX2.dZ());
    }

    public void a(bX bX2, boolean bl2) {
        FerryJNI.Logger_setIsLogging(this.hf, this, bX2.dZ(), bl2);
    }

    public static boolean b(bX bX2) {
        return FerryJNI.Logger_isGlobalLogging(bX2.dZ());
    }

    public static void b(bX bX2, boolean bl2) {
        FerryJNI.Logger_setGlobalIsLogging(bX2.dZ(), bl2);
    }

    public String getName() {
        return FerryJNI.Logger_getName(this.hf, this);
    }
}

