/*
 * Decompiled with CFR 0.152.
 */
import java.io.DataOutputStream;

/*
 * Renamed from aCp
 */
public class acp_2
extends anv {
    private final short aao;

    public acp_2(short s) {
        this.aao = s;
    }

    public boolean isWide() {
        return false;
    }

    public void a(DataOutputStream dataOutputStream) {
        dataOutputStream.writeByte(7);
        dataOutputStream.writeShort(this.aao);
    }

    public boolean equals(Object object) {
        return object instanceof acp_2 && ((acp_2)object).aao == this.aao;
    }

    public int hashCode() {
        return this.aao;
    }

    static short a(acp_2 acp_22) {
        return acp_22.aao;
    }
}

