/*
 * Decompiled with CFR 0.152.
 */
public abstract class jv
implements sq_1 {
    public abstract AW fV();

    public void s(Object object) {
        ((JG)object).b();
    }

    public void t(Object object) {
        try {
            ((JG)object).j();
        }
        catch (Exception exception) {
            throw new RuntimeException("passivateObject exception");
        }
    }

    public void u(Object object) {
    }

    public boolean v(Object object) {
        return true;
    }
}

