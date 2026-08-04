/*
 * Decompiled with CFR 0.152.
 */
public abstract class Ts {
    protected final aGx bOo;

    protected Ts(aGx aGx2) {
        this.bOo = aGx2;
    }

    public final int ao() {
        return this.bOo.ao();
    }

    public final AV afY() {
        return this.bOo.afY();
    }

    public final int afZ() {
        return this.bOo.afZ();
    }

    public void a(abx_2[] abx_2Array) {
        assert (abx_2Array.length == this.bOo.afZ());
    }

    public abstract void update(int var1);
}

