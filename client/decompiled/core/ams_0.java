/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.kernel.core.maths.Matrix44;

/*
 * Renamed from amS
 */
public abstract class ams_0 {
    protected Matrix44 uz;

    public ams_0() {
        this.uz = new Matrix44();
    }

    public ams_0(ams_0 ams_02) {
        this.uz = new Matrix44(ams_02.ki());
    }

    public abstract Matrix44 ki();

    public abstract aba_1 pc();

    public abstract ams_0 pd();
}

