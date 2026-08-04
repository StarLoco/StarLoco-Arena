/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from wY
 */
public class wy_0
extends aos {
    public wy_0(int n2) {
        super(n2);
    }

    public te_0 Du() {
        return new te_0(this, null);
    }

    public void a(te_0 te_02, ByteBuffer byteBuffer) {
        te_0.a(te_02, byteBuffer.getInt());
        te_0.b(te_02, byteBuffer.getInt());
    }

    public void a(aij_1 aij_12, String string) {
        aeW[] aeWArray = wy_0.iQ(string);
        aij_12.writeInt(aeWArray[0].auL());
        aij_12.writeInt(aeWArray[1].auL());
    }
}

