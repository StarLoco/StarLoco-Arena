/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from DY
 */
public class dy_2
extends ael_2 {
    private String m_name;
    private String ju;
    private long yk;
    private long yl;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        byte[] byArray2 = new byte[byteBuffer.get()];
        byteBuffer.get(byArray2);
        this.m_name = new String(byArray2);
        byArray2 = new byte[byteBuffer.get()];
        byteBuffer.get(byArray2);
        this.ju = new String(byArray2);
        this.yk = byteBuffer.getLong();
        this.yl = byteBuffer.getLong();
        return true;
    }

    public int getId() {
        return 6025;
    }

    public String getName() {
        return this.m_name;
    }

    public long ME() {
        return this.yk;
    }

    public long MF() {
        return this.yl;
    }

    public String MG() {
        return this.ju;
    }
}

