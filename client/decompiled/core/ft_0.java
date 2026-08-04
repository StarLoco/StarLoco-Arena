/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from FT
 */
public class ft_0
extends ael_2 {
    private String bak;
    private String aca;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        byte[] byArray2 = new byte[byteBuffer.get() & 0xFF];
        byteBuffer.get(byArray2);
        this.bak = aey_0.V(byArray2);
        byte[] byArray3 = new byte[byteBuffer.get() & 0xFF];
        byteBuffer.get(byArray3);
        this.aca = aey_0.V(byArray3);
        return true;
    }

    public int getId() {
        return 3158;
    }

    public String nn() {
        return this.bak;
    }

    public String uj() {
        return this.aca;
    }
}

