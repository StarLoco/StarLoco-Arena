/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from ayK
 */
public class ayk_0
implements awG {
    public byte dmF = 0;
    public byte dmG = 0;
    public static final int dmH = 2;

    public boolean a(ByteBuffer byteBuffer) {
        byteBuffer.put(this.dmF);
        byteBuffer.put(this.dmG);
        return true;
    }

    public boolean b(ByteBuffer byteBuffer) {
        this.dmF = byteBuffer.get();
        this.dmG = byteBuffer.get();
        return true;
    }

    public void clear() {
        this.dmF = 0;
        this.dmG = 0;
    }

    public boolean a(ByteBuffer byteBuffer, int n2) {
        return this.b(byteBuffer);
    }

    public int w() {
        return 2;
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        this.a(stringBuilder, "");
        return stringBuilder.toString();
    }

    public final void a(StringBuilder stringBuilder, String string) {
        stringBuilder.append(string).append("id=").append(this.dmF).append('\n');
        stringBuilder.append(string).append("count=").append(this.dmG).append('\n');
    }
}

