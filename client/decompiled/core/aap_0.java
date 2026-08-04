/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from aaP
 */
public abstract class aap_0 {
    protected static final Logger a = Logger.getLogger(aap_0.class);
    private final zm_1 cgJ = new zm_1();

    public final void a(ic_2[] ic_2Array) {
        for (ic_2 ic_22 : ic_2Array) {
            this.cgJ.b(ic_22.lC(), ic_22.lD());
        }
    }

    public final Cs bw(short s) {
        return (Cs)this.cgJ.an(s);
    }
}

