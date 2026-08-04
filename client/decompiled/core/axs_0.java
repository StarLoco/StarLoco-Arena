/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from axs
 */
public class axs_0
extends alz {
    private static final Logger a = Logger.getLogger(axs_0.class);
    or_1 dju;
    int cgU;

    public axs_0() {
    }

    public axs_0(byte by, byte by2, short s, int n2) {
        super(by, by2, s);
        this.cgU = n2;
    }

    public void b(acf acf2) {
        if (acf2 == null) {
            throw new IllegalArgumentException("Argument 0 for @NotNull parameter of com/ankamagames/baseImpl/graphics/alea/environment/SoundDef.load must not be null");
        }
        super.b(acf2);
        this.cgU = acf2.readInt();
    }

    public void a(aij_1 aij_12) {
        if (aij_12 == null) {
            throw new IllegalArgumentException("Argument 0 for @NotNull parameter of com/ankamagames/baseImpl/graphics/alea/environment/SoundDef.save must not be null");
        }
        super.a(aij_12);
        aij_12.writeInt(this.cgU);
    }

    public String toString() {
        return "SoundDef{m_x=" + this.cFs + ", m_y=" + this.cFt + ", m_z=" + this.wp + "m_soundId=" + this.cgU + '}';
    }
}

