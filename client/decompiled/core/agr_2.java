/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from aGr
 */
public class agr_2
extends ael_2 {
    private byte aV;
    private int dIy;
    private int dIz;
    private int dIA;

    public byte an() {
        return this.aV;
    }

    public int aSo() {
        return this.dIy;
    }

    public int aSp() {
        return this.dIz;
    }

    public int aSq() {
        return this.dIA;
    }

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.aV = byteBuffer.get();
        this.dIy = byteBuffer.getInt();
        this.dIz = byteBuffer.getInt();
        this.dIA = byteBuffer.getInt();
        return true;
    }

    public int getId() {
        return 5491;
    }
}

