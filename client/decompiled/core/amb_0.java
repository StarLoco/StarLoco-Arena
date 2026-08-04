/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from amB
 */
public class amb_0
extends ue_0 {
    private int bKK;
    byte[] Nw;
    private boolean cHL;
    private boolean cHM = false;
    private int Nx;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.o(byteBuffer);
        this.cHM = byteBuffer.get() == 1;
        this.cHL = byteBuffer.get() == 1;
        this.Nx = byteBuffer.getInt();
        this.bKK = byteBuffer.getInt();
        this.Nw = new byte[byteBuffer.getShort()];
        byteBuffer.get(this.Nw);
        return true;
    }

    public void j() {
        super.j();
        this.Nw = null;
    }

    public void b() {
        super.b();
    }

    public int getId() {
        return 8120;
    }

    public byte[] aew() {
        return this.Nw;
    }

    public int aey() {
        return this.bKK;
    }

    public int M() {
        return this.bKK;
    }

    public jl_0 N() {
        return jl_0.bjF;
    }

    public boolean aBN() {
        return this.cHL;
    }

    public boolean gI() {
        return this.cHM;
    }

    public int aBO() {
        return this.Nx;
    }
}

