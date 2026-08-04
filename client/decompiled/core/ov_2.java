/*
 * Decompiled with CFR 0.152.
 */
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

/*
 * Renamed from ov
 */
public abstract class ov_2 {
    private final short aao;

    public ov_2(short s) {
        this.aao = s;
    }

    public void a(DataOutputStream dataOutputStream) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        this.b(new DataOutputStream(byteArrayOutputStream));
        dataOutputStream.writeShort(this.aao);
        dataOutputStream.writeInt(byteArrayOutputStream.size());
        byteArrayOutputStream.writeTo(dataOutputStream);
    }

    protected abstract void b(DataOutputStream var1);

    static short a(ov_2 ov_22) {
        return ov_22.aao;
    }
}

