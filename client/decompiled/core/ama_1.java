/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;
import java.util.ArrayList;

/*
 * Renamed from aMA
 */
public final class ama_1
extends lJ {
    private static final short fn = 1;
    private int dXW;
    private short dXX;
    private boolean iG;
    private final ArrayList iM = new ArrayList();

    public ama_1() {
        super((short)1);
    }

    public int cq() {
        return atr_0.cUM.getId();
    }

    public byte[] cr() {
        int n2 = 4;
        for (Object object : this.iM) {
            n2 += ((Ht)object).cr().length + 4 + 4 + 2;
        }
        ByteBuffer byteBuffer = ByteBuffer.allocate(7 + n2);
        byteBuffer.putInt(this.dXW);
        byteBuffer.putShort(this.dXX);
        byteBuffer.put(this.iG ? (byte)1 : 0);
        byteBuffer.putInt(this.iM.size());
        for (Ht ht : this.iM) {
            byteBuffer.putInt(ht.qw());
            byteBuffer.putShort(ht.qx());
            byte[] byArray = ht.cr();
            byteBuffer.putInt(byArray.length);
            byteBuffer.put(byArray);
        }
        return byteBuffer.array();
    }

    public void a(ByteBuffer byteBuffer, int n2, short s) {
        this.cd(n2);
        if (s == 1) {
            this.dXW = byteBuffer.getInt();
            this.dXX = byteBuffer.getShort();
            this.iG = byteBuffer.get() == 1;
            int n3 = byteBuffer.getInt();
            for (int j = 0; j < n3; ++j) {
                int n4 = byteBuffer.getInt();
                short s2 = byteBuffer.getShort();
                byte[] byArray = new byte[byteBuffer.getInt()];
                byteBuffer.get(byArray);
                Ht ht = new Ht();
                ht.a(ByteBuffer.wrap(byArray), n4, s2);
                this.a(ht);
            }
        } else {
            a.error((Object)"Tentative de d\u00e9s\u00e9rialisation d'un objet avec une version non prise en charge");
        }
    }

    public lJ cs() {
        return new ama_1();
    }

    public int aXe() {
        return this.dXW;
    }

    public void po(int n2) {
        this.dXW = n2;
    }

    public short aXf() {
        return this.dXX;
    }

    public void cx(short s) {
        this.dXX = s;
    }

    public boolean eB() {
        return this.iG;
    }

    public void q(boolean bl2) {
        this.iG = bl2;
    }

    public ArrayList eC() {
        return this.iM;
    }

    public void a(Ht ht) {
        this.iM.add(ht);
    }
}

