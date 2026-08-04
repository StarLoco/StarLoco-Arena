/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;
import java.util.ArrayList;

/*
 * Renamed from aHM
 */
public final class ahm_1
extends lJ {
    private static final short fn = 1;
    private short dNK;
    private byte czY;
    private short Gp;
    private akw_0[] czZ = new akw_0[0];
    private final ArrayList iM = new ArrayList();

    public ahm_1() {
        super((short)1);
    }

    public int cq() {
        return atr_0.cVc.getId();
    }

    public byte[] cr() {
        int n2;
        int n3 = 0;
        for (n2 = 0; n2 < this.czZ.length; ++n2) {
            n3 += this.czZ[n2].nj();
        }
        n2 = 4;
        for (Ht ht : this.iM) {
            n2 += ht.cr().length + 4 + 4 + 2;
        }
        ByteBuffer byteBuffer = ByteBuffer.allocate(6 + n3 + n2);
        byteBuffer.putShort(this.dNK);
        byteBuffer.put(this.czY);
        byteBuffer.putShort(this.Gp);
        byteBuffer.put((byte)this.czZ.length);
        for (int j = 0; j < this.czZ.length; ++j) {
            this.czZ[j].c(byteBuffer);
        }
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
            int n3;
            this.dNK = byteBuffer.getShort();
            this.czY = byteBuffer.get();
            this.Gp = byteBuffer.getShort();
            this.czZ = new akw_0[byteBuffer.get()];
            for (n3 = 0; n3 < this.czZ.length; ++n3) {
                this.czZ[n3] = akw_0.J(byteBuffer);
            }
            n3 = byteBuffer.getInt();
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
        return new ahm_1();
    }

    public short aUf() {
        return this.dNK;
    }

    public void cr(short s) {
        this.dNK = s;
    }

    public byte ayW() {
        return this.czY;
    }

    public void bp(byte by) {
        this.czY = by;
    }

    public short getType() {
        return this.Gp;
    }

    public void setType(short s) {
        this.Gp = s;
    }

    public akw_0[] ayX() {
        return this.czZ;
    }

    public void c(akw_0 akw_02) {
        if (this.czZ == null) {
            this.czZ = new akw_0[]{akw_02};
        } else {
            akw_0[] akw_0Array = this.czZ;
            this.czZ = new akw_0[this.czZ.length + 1];
            System.arraycopy(akw_0Array, 0, this.czZ, 1, akw_0Array.length);
            this.czZ[0] = akw_02;
        }
    }

    public void a(Ht ht) {
        this.iM.add(ht);
    }

    public ArrayList eC() {
        return this.iM;
    }
}

