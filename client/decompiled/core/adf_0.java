/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from adF
 */
public abstract class adf_0 {
    private final lb_0 cnf = new lb_0();

    protected adf_0() {
        this.fill();
    }

    public final aGx[] atc() {
        Object[] objectArray = new aGx[this.cnf.size()];
        this.cnf.a(objectArray);
        return objectArray;
    }

    public final aGx jS(int n2) {
        return (aGx)this.cnf.get(n2);
    }

    public final Ts a(int n2, ByteBuffer byteBuffer) {
        aGx aGx2 = this.jS(n2);
        if (aGx2 == null) {
            return null;
        }
        Ts ts = aGx2.Dv();
        aGx2.a(ts, byteBuffer);
        return ts;
    }

    protected abstract void fill();

    protected final void a(aGx aGx2) {
        this.cnf.c(aGx2.ao(), aGx2);
    }
}

