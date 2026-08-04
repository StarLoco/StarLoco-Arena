/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;

/*
 * Renamed from MI
 */
public abstract class mi_2
implements Iterable {
    protected static final Logger a = Logger.getLogger(mi_2.class);
    public static final short bxy = -1;
    protected short bxz;
    protected final List bxA = new ArrayList(1);
    protected final boolean bxB;
    private boolean bxC = false;

    protected mi_2(boolean bl2, short s) {
        this.bxB = bl2;
        this.bxz = (short)Math.max(-1, s);
    }

    public boolean YK() {
        return this.bxB;
    }

    public void e(afJ afJ2) {
        if (afJ2 == null) {
            return;
        }
        if (!this.bxA.contains(afJ2)) {
            this.bxA.add(afJ2);
        }
    }

    public void f(afJ afJ2) {
        if (afJ2 == null) {
            return;
        }
        this.bxA.remove(afJ2);
    }

    public void YL() {
        this.bxA.clear();
    }

    public void cleanup() {
        this.ho();
        this.YL();
    }

    protected void b(wl_1 wl_12) {
        this.a(wl_12, true);
    }

    protected void a(wl_1 wl_12, boolean bl2) {
        afJ[] afJArray;
        for (afJ afJ2 : afJArray = this.bxA.toArray(new afJ[this.bxA.size()])) {
            afJ2.a(wl_12);
        }
        if (bl2) {
            try {
                wl_12.release();
            }
            catch (Exception exception) {
                a.error((Object)bl_0.b(exception));
            }
        }
    }

    public boolean l(short s) {
        if (this.bxz > 0 && s < this.size()) {
            a.error((Object)("Can't change the size of the inventory to " + s + " : current size is " + this.size()));
            return false;
        }
        this.bxz = (short)Math.max(-1, s);
        return true;
    }

    public short YM() {
        return this.bxz;
    }

    public boolean isFull() {
        return this.bxz != -1 && this.size() >= this.bxz;
    }

    public boolean isEmpty() {
        return this.size() == 0;
    }

    public boolean isLocked() {
        return this.bxC;
    }

    public void bR(boolean bl2) {
        this.bxC = bl2;
    }

    public abstract boolean d(uh_1 var1);

    public abstract boolean d(long var1, short var3);

    public abstract short A(long var1);

    public abstract boolean a(uh_1 var1, uh_1 var2);

    public abstract boolean c(uh_1 var1);

    public abstract boolean b(uh_1 var1);

    public abstract uh_1 H(long var1);

    public abstract boolean D(long var1);

    public abstract void aa(int var1);

    public abstract int l(int var1, int var2);

    public abstract boolean a(uh_1 var1);

    public abstract boolean E(long var1);

    public abstract boolean ab(int var1);

    public abstract uh_1 G(long var1);

    public abstract uh_1 ae(int var1);

    public abstract ArrayList ad(int var1);

    public abstract int size();

    public abstract int hn();

    public abstract int ho();

    public abstract Iterator iterator();

    public abstract zc_2 hp();
}

