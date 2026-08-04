/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public class Ve
extends ael_2 {
    public static final byte biW = 0;
    public static final byte biX = 1;
    public static final byte biY = 2;
    public static final byte bSj = 3;
    private byte biZ;
    private String sI = null;
    private int abb = 0xFFFFFF;

    public boolean a(byte[] byArray) {
        if (!this.a(byArray.length, 3, false)) {
            return false;
        }
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.biZ = byteBuffer.get();
        if (this.biZ == 3) {
            this.abb = byteBuffer.getInt();
        }
        Short s = byteBuffer.getShort();
        byte[] byArray2 = new byte[s & 0xFFFF];
        byteBuffer.get(byArray2);
        this.sI = aey_0.V(byArray2);
        return true;
    }

    public int getId() {
        return 105;
    }

    public byte Vj() {
        return this.biZ;
    }

    public String getMessage() {
        return this.sI;
    }

    public int adA() {
        return this.abb;
    }
}

