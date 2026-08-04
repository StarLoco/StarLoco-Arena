/*
 * Decompiled with CFR 0.152.
 */
public class asT
extends ii_2
implements aio_2 {
    public void e(ahu_0 ahu_02) {
        super.a(ahu_02);
    }

    public void a(vU vU2) {
        if (!(vU2 instanceof ahu_0) && vU2 != null) {
            throw new IllegalArgumentException("LoggerContextAwareBase only accepts contexts of type c.l.classic.LoggerContext");
        }
        super.a(vU2);
    }

    public ahu_0 ON() {
        return (ahu_0)this.Pb;
    }
}

