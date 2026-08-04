/*
 * Decompiled with CFR 0.152.
 */
import java.io.DataOutputStream;

/*
 * Renamed from Ow
 */
public class ow_0
extends anv {
    private final short aao;
    private final short bBZ;

    public ow_0(short s, short s2) {
        this.aao = s;
        this.bBZ = s2;
    }

    public short abx() {
        return this.bBZ;
    }

    public boolean isWide() {
        return false;
    }

    public void a(DataOutputStream dataOutputStream) {
        dataOutputStream.writeByte(12);
        dataOutputStream.writeShort(this.aao);
        dataOutputStream.writeShort(this.bBZ);
    }

    public boolean equals(Object object) {
        return object instanceof ow_0 && ((ow_0)object).aao == this.aao && ((ow_0)object).bBZ == this.bBZ;
    }

    public int hashCode() {
        return this.aao + (this.bBZ << 16);
    }
}

