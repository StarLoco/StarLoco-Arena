/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from aLF
 */
public final class alf_2
extends lJ {
    private static final short fn = 1;
    private int aW;
    private jg_0 dVU = new jg_0();

    public alf_2() {
        super((short)1);
    }

    public int cq() {
        return atr_0.cUQ.getId();
    }

    public byte[] cr() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(6 + this.dVU.size() * 4);
        byteBuffer.putInt(this.aW);
        byteBuffer.putShort((short)this.dVU.size());
        for (int j = 0; j < this.dVU.size(); ++j) {
            byteBuffer.putInt(this.dVU.bu(j));
        }
        return byteBuffer.array();
    }

    public void a(ByteBuffer byteBuffer, int n2, short s) {
        this.cd(n2);
        if (s == 1) {
            this.aW = byteBuffer.getInt();
            int n3 = byteBuffer.getShort();
            for (int j = 0; j < n3; ++j) {
                this.dVU.add(byteBuffer.getInt());
            }
        } else {
            a.error((Object)"Tentative de d\u00e9s\u00e9rialisation d'un objet avec une version non prise en charge");
        }
    }

    public lJ cs() {
        return new alf_2();
    }

    public jg_0 aWt() {
        return this.dVU;
    }

    public void pm(int n2) {
        this.dVU.add(n2);
    }

    public void f(int n2) {
        this.aW = n2;
    }

    public int getId() {
        return this.aW;
    }
}

