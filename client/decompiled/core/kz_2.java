/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.kernel.core.maths.Matrix44;

/*
 * Renamed from kz
 */
public final class kz_2
extends ams_0 {
    public kz_2() {
    }

    public kz_2(kz_2 kz_22) {
        this.a(kz_22);
    }

    public kz_2(Matrix44 matrix44) {
        this.a(matrix44);
    }

    public final void a(kz_2 kz_22) {
        this.a(kz_22.ki());
    }

    public final void a(Matrix44 matrix44) {
        this.uz.d(matrix44);
    }

    public final Matrix44 ki() {
        return new Matrix44(this.uz);
    }

    public final aba_1 pc() {
        return aba_1.dqV;
    }

    public final ams_0 pd() {
        return new kz_2(this);
    }
}

