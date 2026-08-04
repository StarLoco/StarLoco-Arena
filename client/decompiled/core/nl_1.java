/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from nL
 */
public class nl_1
extends ael_2 {
    private byte aV;
    private long aj;
    private byte[] Pc;
    private byte[] Pd;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.aj = byteBuffer.getLong();
        this.aV = byteBuffer.get();
        if (this.aV == 0) {
            short s = byteBuffer.getShort();
            this.Pc = new byte[s];
            byteBuffer.get(this.Pc);
            short s2 = byteBuffer.getShort();
            this.Pd = new byte[s2];
            byteBuffer.get(this.Pd);
        }
        return true;
    }

    public int getId() {
        return 6010;
    }

    public byte an() {
        return this.aV;
    }

    public long K() {
        return this.aj;
    }

    public byte[] sJ() {
        return this.Pd;
    }

    public byte[] sK() {
        return this.Pc;
    }
}

