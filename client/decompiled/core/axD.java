/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public class axD
extends yg_0 {
    protected final cp_2 djU = new cp_2();
    protected String m_name;

    public String getName() {
        return this.m_name;
    }

    public void jY(String string) {
        this.m_name = this.m_name + string;
    }

    public void setName(String string) {
        this.m_name = string;
    }

    public cp_2 aKB() {
        return this.djU;
    }

    public byte[] cd() {
        byte[] byArray = this.getName().getBytes();
        ByteBuffer byteBuffer = ByteBuffer.allocate(2 + byArray.length + 1 + this.djU.size() * (8 + akv_0.w()));
        byteBuffer.put(this.axW);
        byteBuffer.put((byte)byArray.length);
        byteBuffer.put(byArray);
        byteBuffer.put((byte)this.djU.size());
        long[] lArray = this.djU.eJ();
        for (int j = 0; j < lArray.length; ++j) {
            byteBuffer.putLong(lArray[j]);
            ((akv_0)this.djU.t(lArray[j])).c(byteBuffer);
        }
        return byteBuffer.array();
    }

    public void f(ByteBuffer byteBuffer) {
        this.axW = byteBuffer.get();
        byte[] byArray = new byte[byteBuffer.get()];
        byteBuffer.get(byArray);
        this.setName(new String(byArray));
        int n2 = byteBuffer.get();
        for (int j = 0; j < n2; ++j) {
            this.djU.a(byteBuffer.getLong(), akv_0.ab(byteBuffer));
        }
    }
}

