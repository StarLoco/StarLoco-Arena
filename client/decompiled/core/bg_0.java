/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from bg
 */
public final class bg_0
extends lJ {
    private static final short fn = 1;
    private int aW;
    private int fo;
    private byte fp;
    private int fq;
    private jg_0 fr = new jg_0();
    private short fs;
    private short ft;

    public bg_0() {
        super((short)1);
    }

    public int cq() {
        return atr_0.cVa.getId();
    }

    public byte[] cr() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(18 + this.fr.size() * 4);
        byteBuffer.putInt(this.aW);
        byteBuffer.putInt(this.fo);
        byteBuffer.put(this.fp);
        byteBuffer.putInt(this.fq);
        byteBuffer.putShort(this.fs);
        byteBuffer.putShort(this.ft);
        byteBuffer.put((byte)this.fr.size());
        for (int j = 0; j < this.fr.size(); ++j) {
            byteBuffer.putInt(this.fr.bu(j));
        }
        return byteBuffer.array();
    }

    public void a(ByteBuffer byteBuffer, int n2, short s) {
        this.cd(n2);
        if (s == 1) {
            this.aW = byteBuffer.getInt();
            this.fo = byteBuffer.getInt();
            this.fp = byteBuffer.get();
            this.fq = byteBuffer.getInt();
            this.fs = byteBuffer.getShort();
            this.ft = byteBuffer.getShort();
            int n3 = byteBuffer.get();
            for (int j = 0; j < n3; ++j) {
                this.fr.add(byteBuffer.getInt());
            }
        } else {
            a.error((Object)"Tentative de d\u00e9s\u00e9rialisation d'un objet avec une version non prise en charge");
        }
    }

    public lJ cs() {
        return new bg_0();
    }

    public int getId() {
        return this.aW;
    }

    public void f(int n2) {
        this.aW = n2;
    }

    public int ct() {
        return this.fo;
    }

    public void w(int n2) {
        this.fo = n2;
    }

    public byte cu() {
        return this.fp;
    }

    public void c(byte by) {
        this.fp = by;
    }

    public int cv() {
        return this.fq;
    }

    public void x(int n2) {
        this.fq = n2;
    }

    public jg_0 cw() {
        return this.fr;
    }

    public void y(int n2) {
        this.fr.add(n2);
    }

    public short cx() {
        return this.fs;
    }

    public void c(short s) {
        this.fs = s;
    }

    public short cy() {
        return this.ft;
    }

    public void d(short s) {
        this.ft = s;
    }
}

