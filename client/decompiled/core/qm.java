/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public class qm {
    public String name;
    public String adM;
    public String adN;
    public boolean adO;
    public long adP;
    public short adQ;
    public byte adR;
    public short adS;
    final /* synthetic */ aaf_1 adT;

    public qm(aaf_1 aaf_12) {
        this.adT = aaf_12;
    }

    public void b(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        byte[] byArray2 = new byte[byteBuffer.get() & 0xFF];
        byteBuffer.get(byArray2);
        this.name = aey_0.V(byArray2);
        byte[] byArray3 = new byte[byteBuffer.get() & 0xFF];
        byteBuffer.get(byArray3);
        this.adM = aey_0.V(byArray3);
        byte[] byArray4 = new byte[byteBuffer.get() & 0xFF];
        byteBuffer.get(byArray4);
        this.adN = aey_0.V(byArray4);
        this.adO = byteBuffer.get() == 1;
        this.adP = byteBuffer.getLong();
        this.adQ = byteBuffer.getShort();
        this.adR = byteBuffer.get();
        this.adS = byteBuffer.getShort();
    }
}

