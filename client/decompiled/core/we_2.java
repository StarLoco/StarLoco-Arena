/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from we
 */
public class we_2 {
    private final qa_2 auh = new qa_2();
    private final qa_2 aui = new qa_2();
    final ahl_1 auj;

    public we_2(ahl_1 ahl_12) {
        this.auj = ahl_12;
    }

    void aR(long l2) {
        this.auj.a(this, l2);
    }

    void d(long l2, int n2) {
        this.aui.d(Math.min(n2, this.aui.size()), l2);
    }

    void l(long l2) {
        int n2;
        while ((n2 = this.aui.cw(l2)) != -1) {
            this.aui.remove(n2);
        }
        this.auj.b(this, l2);
    }

    void dl() {
        this.auj.b(this);
    }

    void Cy() {
        this.auj.c(this);
    }

    void Cz() {
        this.auj.d(this);
    }

    public qa_2 CA() {
        return this.aui;
    }

    public qa_2 CB() {
        return this.auh;
    }

    public int w() {
        return 1 + 8 * this.aui.size() + 1 + 8 * this.auh.size();
    }

    public void c(ByteBuffer byteBuffer) {
        int n2;
        byteBuffer.put((byte)this.aui.size());
        for (n2 = 0; n2 < this.aui.size(); ++n2) {
            byteBuffer.putLong(this.aui.get(n2));
        }
        byteBuffer.put((byte)this.auh.size());
        for (n2 = 0; n2 < this.auh.size(); ++n2) {
            byteBuffer.putLong(this.auh.get(n2));
        }
    }

    public void y(ByteBuffer byteBuffer) {
        int n2;
        int n3 = byteBuffer.get();
        for (n2 = 0; n2 < n3; ++n2) {
            long l2 = byteBuffer.getLong();
            this.aui.ct(l2);
        }
        n2 = byteBuffer.get();
        for (int j = 0; j < n2; ++j) {
            long l3 = byteBuffer.getLong();
            this.auh.ct(l3);
        }
    }

    public void clear() {
        this.aui.clear();
        this.auh.clear();
    }
}

