/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from aet
 */
public final class aet_1
extends lJ {
    private static final short fn = 1;
    private np_1[] UE = jn_1.bkb;

    public aet_1() {
        super((short)1);
    }

    public int cq() {
        return atr_0.cUF.getId();
    }

    public byte[] cr() {
        int n2 = 1;
        for (np_1 np_12 : this.UE) {
            n2 += np_12.nj();
        }
        ByteBuffer byteBuffer = ByteBuffer.allocate(n2);
        byteBuffer.put((byte)this.UE.length);
        for (np_1 np_13 : this.UE) {
            byteBuffer.put(np_13.cd());
        }
        return byteBuffer.array();
    }

    public void a(ByteBuffer byteBuffer, int n2, short s) {
        this.cd(n2);
        if (s == 1) {
            byte by = byteBuffer.get();
            if (by == 0) {
                this.UE = jn_1.bkb;
            } else {
                this.UE = new np_1[by];
                for (int j = 0; j < this.UE.length; ++j) {
                    this.UE[j] = np_1.j(byteBuffer);
                }
            }
        } else {
            a.error((Object)"Tentative de d\u00e9s\u00e9rialisation d'un objet avec une version non prise en charge");
        }
    }

    public lJ cs() {
        return new aet_1();
    }

    public void a(np_1 np_12) {
        np_1[] np_1Array = this.UE;
        this.UE = new np_1[this.UE.length + 1];
        System.arraycopy(np_1Array, 0, this.UE, 0, np_1Array.length);
        this.UE[this.UE.length - 1] = np_12;
    }

    public boolean fR(int n2) {
        for (np_1 np_12 : this.UE) {
            if (np_12.getId() != n2) continue;
            return true;
        }
        return false;
    }

    public np_1[] tv() {
        return this.UE;
    }
}

