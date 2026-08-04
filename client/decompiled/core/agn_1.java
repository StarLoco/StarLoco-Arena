/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aGn
 */
public class agn_1
implements aaa_1 {
    private int bhQ = 0;
    protected vU Pb;
    final Object dIj;

    public agn_1(Object object) {
        this.dIj = object;
    }

    protected Object aBg() {
        return this.dIj;
    }

    public void a(vU vU2) {
        if (this.Pb == null) {
            this.Pb = vU2;
        } else if (this.Pb != vU2) {
            throw new IllegalStateException("Context has been already set");
        }
    }

    public vU QK() {
        return this.Pb;
    }

    public Ju ea() {
        if (this.Pb == null) {
            return null;
        }
        return this.Pb.ea();
    }

    public void b(amb amb2) {
        if (this.Pb == null) {
            if (this.bhQ++ == 0) {
                System.out.println("LOGBACK: No context given for " + this);
            }
            return;
        }
        Ju ju = this.Pb.ea();
        if (ju != null) {
            ju.c(amb2);
        }
    }

    public void ee(String string) {
        this.b(new jP(string, this.aBg()));
    }

    public void c(String string, Throwable throwable) {
        this.b(new jP(string, this.aBg(), throwable));
    }

    public void ef(String string) {
        this.b(new apQ(string, this.aBg()));
    }

    public void d(String string, Throwable throwable) {
        this.b(new apQ(string, this.aBg(), throwable));
    }

    public void eg(String string) {
        this.b(new aIX(string, this.aBg()));
    }

    public void e(String string, Throwable throwable) {
        this.b(new aIX(string, this.aBg(), throwable));
    }
}

