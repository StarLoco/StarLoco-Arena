/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class lf {
    private final List Gj = new ArrayList(4);
    private final List Gk = new ArrayList(4);

    public void a(atD atD2, boolean bl2) {
        List list = bl2 ? this.Gk : this.Gj;
        list.add(atD2);
    }

    public Iterable pS() {
        return this.Gk;
    }

    public Iterable pT() {
        return this.Gj;
    }

    public int w() {
        int n2 = 4;
        n2 += this.b(this.Gk);
        return n2 += this.b(this.Gj);
    }

    private int b(List list) {
        int n2 = 0;
        for (atD atD2 : list) {
            n2 += atD2.w();
        }
        return n2;
    }

    public void c(ByteBuffer byteBuffer) {
        this.a(byteBuffer, this.Gj);
        this.a(byteBuffer, this.Gk);
    }

    private void a(ByteBuffer byteBuffer, List list) {
        byteBuffer.putShort((short)list.size());
        for (atD atD2 : list) {
            atD2.c(byteBuffer);
        }
    }

    protected void a(ahh_0 ahh_02, ByteBuffer byteBuffer) {
        this.a(ahh_02, byteBuffer, this.Gj);
        this.a(ahh_02, byteBuffer, this.Gk);
    }

    private void a(ahh_0 ahh_02, ByteBuffer byteBuffer, List list) {
        short s = byteBuffer.getShort();
        for (short s2 = 0; s2 < s; s2 = (short)(s2 + 1)) {
            list.add(atD.e(ahh_02, byteBuffer));
        }
    }

    public static lf b(ahh_0 ahh_02, ByteBuffer byteBuffer) {
        lf lf2 = new lf();
        lf2.a(ahh_02, byteBuffer);
        return lf2;
    }

    public void clear() {
        atD atD2;
        int n2;
        for (n2 = 0; n2 < this.Gj.size(); ++n2) {
            atD2 = (atD)this.Gj.get(n2);
            atD2.clean();
        }
        this.Gj.clear();
        for (n2 = 0; n2 < this.Gk.size(); ++n2) {
            atD2 = (atD)this.Gk.get(n2);
            atD2.clean();
        }
        this.Gk.clear();
    }
}

