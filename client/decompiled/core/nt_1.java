/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from Nt
 */
public abstract class nt_1
extends ayp {
    private boolean bzz = false;
    protected static final Logger a = Logger.getLogger(nt_1.class);

    public nt_1 aay() {
        if (!this.eM()) {
            this.bzz = !this.bzz;
            return this;
        }
        return new cv(!this.b(null, null, null, null));
    }

    public abstract boolean eM();

    public abstract int a(Object var1, Object var2, Object var3, Object var4);

    public boolean b(Object object, Object object2, Object object3, Object object4) {
        boolean bl2;
        try {
            boolean bl3 = bl2 = this.a(object, object2, object3, object4) == 0;
            if (this.bzz) {
                return !bl2;
            }
        }
        catch (en_0 en_02) {
            bl2 = false;
            a.error((Object)("Exception sur crit\u00e8re (expected) !! User : " + object + " Target : " + object2 + " Cnotext : " + object4), (Throwable)en_02);
        }
        catch (Exception exception) {
            bl2 = false;
            a.error((Object)"Exception inatendue !!", (Throwable)exception);
        }
        return bl2;
    }

    public aij_2 Ce() {
        return aij_2.cxG;
    }

    public boolean oN() {
        return this.bzz;
    }
}

