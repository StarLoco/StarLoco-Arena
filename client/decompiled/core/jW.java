/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class jW
implements awG {
    public final ArrayList Df = new ArrayList(0);

    public boolean a(ByteBuffer byteBuffer) {
        if (this.Df.size() > 65535) {
            return false;
        }
        byteBuffer.putShort((short)this.Df.size());
        for (int j = 0; j < this.Df.size(); ++j) {
            ayk_0 ayk_02 = (ayk_0)this.Df.get(j);
            boolean bl2 = ayk_02.a(byteBuffer);
            if (bl2) continue;
            return false;
        }
        return true;
    }

    public boolean b(ByteBuffer byteBuffer) {
        int n2 = byteBuffer.getShort() & 0xFFFF;
        this.Df.clear();
        this.Df.ensureCapacity(n2);
        for (int j = 0; j < n2; ++j) {
            ayk_0 ayk_02 = new ayk_0();
            boolean bl2 = ayk_02.b(byteBuffer);
            if (!bl2) {
                return false;
            }
            this.Df.add(ayk_02);
        }
        return true;
    }

    public void clear() {
        this.Df.clear();
    }

    public boolean a(ByteBuffer byteBuffer, int n2) {
        return this.b(byteBuffer);
    }

    public int w() {
        int n2 = 0;
        n2 += 2;
        for (int j = 0; j < this.Df.size(); ++j) {
            ayk_0 ayk_02 = (ayk_0)this.Df.get(j);
            n2 += ayk_02.w();
        }
        return n2;
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        this.a(stringBuilder, "");
        return stringBuilder.toString();
    }

    public final void a(StringBuilder stringBuilder, String string) {
        stringBuilder.append(string).append("properties=");
        if (this.Df.isEmpty()) {
            stringBuilder.append("{}").append('\n');
        } else {
            stringBuilder.append("(").append(this.Df.size()).append(" elements)...\n");
            for (int j = 0; j < this.Df.size(); ++j) {
                ayk_0 ayk_02 = (ayk_0)this.Df.get(j);
                ayk_02.a(stringBuilder, string + j + "/ ");
            }
        }
    }
}

