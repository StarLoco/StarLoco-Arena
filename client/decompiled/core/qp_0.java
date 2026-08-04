/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from QP
 */
public abstract class qp_0
implements acl_0 {
    private volatile boolean closed = false;

    public abstract Object adr();

    public abstract void af(Object var1);

    public abstract void ag(Object var1);

    public int jx() {
        throw new UnsupportedOperationException();
    }

    public int jy() {
        throw new UnsupportedOperationException();
    }

    public void clear() {
        throw new UnsupportedOperationException();
    }

    public void ads() {
        throw new UnsupportedOperationException();
    }

    public void close() {
        this.adt();
        this.closed = true;
    }

    public void a(sq_1 sq_12) {
        throw new UnsupportedOperationException();
    }

    protected final boolean isClosed() {
        return this.closed;
    }

    protected final void adt() {
        if (this.isClosed()) {
            throw new IllegalStateException("Pool not open");
        }
    }
}

