/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.entity.Entity3D;

/*
 * Renamed from fA
 */
public abstract class fa_0
extends Enum {
    public static final /* enum */ fa_0 rx = new ib_0();
    public static final /* enum */ fa_0 ry = new IA();
    public static final /* enum */ fa_0 rz = new ic_0();
    private static final /* synthetic */ fa_0[] rA;

    public static final fa_0[] values() {
        return (fa_0[])rA.clone();
    }

    public static fa_0 valueOf(String string) {
        return Enum.valueOf(fa_0.class, string);
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private fa_0() {
        void var2_-1;
        void var1_-1;
    }

    public abstract void a(Entity3D var1, byte var2, float var3, float var4, float var5, float var6);

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    /* synthetic */ fa_0(ib_0 ib_02) {
        this((String)var1_-1, (int)var2_1);
        void var2_1;
        void var1_-1;
    }

    static {
        rA = new fa_0[]{rx, ry, rz};
    }
}

