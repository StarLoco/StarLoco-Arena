/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;
import java.sql.ResultSet;

/*
 * Renamed from np
 */
public abstract class np_1 {
    protected int[] JI;
    protected int aW;
    protected int Om;
    protected Ht On = null;

    public abstract int getType();

    public void a(je_2 je_22) {
    }

    public void a(mv_1 mv_12) {
    }

    public abstract int T();

    public static np_1 b(np_1 ... np_1Array) {
        if (np_1Array[0] == null) {
            if (np_1Array.length == 2) {
                return np_1Array[1];
            }
            np_1[] np_1Array2 = new np_1[np_1Array.length - 1];
            System.arraycopy(np_1Array, 1, np_1Array2, 0, np_1Array2.length);
            return np_1.b(np_1Array2);
        }
        if (np_1Array[0].rg().length >= np_1Array[0].T()) {
            return np_1Array[0];
        }
        np_1 np_12 = np_1.co(np_1Array[0].getType());
        np_12.f(np_1Array[0].getId());
        int[] nArray = new int[np_1Array[0].rg().length + np_1Array[1].rg().length];
        System.arraycopy(np_1Array[0].rg(), 0, nArray, 0, np_1Array[0].rg().length);
        System.arraycopy(np_1Array[1].rg(), 0, nArray, np_1Array[0].rg().length, np_1Array[1].rg().length);
        np_12.i(nArray);
        if (np_1Array.length == 2) {
            return np_12;
        }
        np_1[] np_1Array3 = new np_1[np_1Array.length - 1];
        System.arraycopy(np_1Array, 2, np_1Array3, 1, np_1Array3.length - 1);
        np_1Array3[0] = np_12;
        return np_1.b(np_1Array3);
    }

    public byte[] cd() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(this.nj());
        byteBuffer.putInt(this.getType());
        byteBuffer.putInt(this.aW);
        byteBuffer.putInt(this.Om);
        byteBuffer.put((byte)this.JI.length);
        for (int n2 : this.JI) {
            byteBuffer.putInt(n2);
        }
        if (this.On != null) {
            byteBuffer.putShort(this.On.qx());
            byteBuffer.putInt(this.On.qw());
            byteBuffer.put(this.On.cr());
        } else {
            byteBuffer.putShort((short)0);
        }
        return byteBuffer.array();
    }

    public static np_1 j(ByteBuffer byteBuffer) {
        np_1 np_12 = np_1.co(byteBuffer.getInt());
        np_12.k(byteBuffer);
        return np_12;
    }

    public void k(ByteBuffer byteBuffer) {
        short s;
        this.aW = byteBuffer.getInt();
        this.Om = byteBuffer.getInt();
        this.JI = new int[byteBuffer.get()];
        for (s = 0; s < this.JI.length; ++s) {
            this.JI[s] = byteBuffer.getInt();
        }
        s = byteBuffer.getShort();
        if (s != 0) {
            int n2 = byteBuffer.getInt();
            this.On = new Ht();
            this.On.a(byteBuffer, n2, s);
        }
    }

    public int nj() {
        return 13 + 4 * this.JI.length + (this.On != null ? this.On.nj() + 2 + 4 : 2);
    }

    public void i(int[] nArray) {
        this.JI = nArray;
    }

    public int[] rg() {
        return this.JI;
    }

    public int getId() {
        return this.aW;
    }

    public void f(int n2) {
        this.aW = n2;
    }

    public int sn() {
        return this.Om;
    }

    public void cn(int n2) {
        this.Om = n2;
    }

    public Ht so() {
        return this.On;
    }

    public void b(Ht ht) {
        this.On = ht;
    }

    public boolean sp() {
        return false;
    }

    public boolean jq() {
        return false;
    }

    public static np_1 co(int n2) {
        if (n2 == ajr_2.cCl.tI()) {
            return new Rx();
        }
        if (n2 == ajr_2.cBa.tI()) {
            return new wb_1();
        }
        if (n2 == ajr_2.cBc.tI()) {
            return new qt_2();
        }
        if (n2 == ajr_2.cBb.tI()) {
            return new adr_1();
        }
        if (n2 == ajr_2.cBg.tI()) {
            return new hv_1();
        }
        if (n2 == ajr_2.cBf.tI()) {
            return new gm_2();
        }
        if (n2 == ajr_2.cBi.tI()) {
            return new zr_0();
        }
        if (n2 == ajr_2.cBh.tI()) {
            return new fS();
        }
        if (n2 == ajr_2.cBe.tI()) {
            return new Zi();
        }
        if (n2 == ajr_2.cBd.tI()) {
            return new alu_2();
        }
        if (n2 == ajr_2.cBj.tI()) {
            return new aIo();
        }
        if (n2 == ajr_2.cBk.tI()) {
            return new ata_0();
        }
        if (n2 == ajr_2.cBl.tI()) {
            return new agp();
        }
        if (n2 == ajr_2.cBm.tI()) {
            return new Uz();
        }
        if (n2 == ajr_2.cBn.tI()) {
            return new wi_0();
        }
        if (n2 == ajr_2.cBr.tI()) {
            return new uy_0();
        }
        if (n2 == ajr_2.cBq.tI()) {
            return new we_0();
        }
        if (n2 == ajr_2.cBo.tI()) {
            return new pz_2();
        }
        if (n2 == ajr_2.cBp.tI()) {
            return new ii_1();
        }
        if (n2 == ajr_2.cBs.tI()) {
            return new aej_2();
        }
        if (n2 == ajr_2.cBt.tI()) {
            return new z_0();
        }
        if (n2 == ajr_2.cBu.tI()) {
            return new axw_0();
        }
        if (n2 == ajr_2.cBx.tI()) {
            return new al_0();
        }
        if (n2 == ajr_2.cBy.tI()) {
            return new if_0();
        }
        if (n2 == ajr_2.cBw.tI()) {
            return new uw_0();
        }
        if (n2 == ajr_2.cBv.tI()) {
            return new dn_1();
        }
        if (n2 == ajr_2.cBA.tI()) {
            return new fi_1();
        }
        if (n2 == ajr_2.cBz.tI()) {
            return new afo_0();
        }
        if (n2 == ajr_2.cBB.tI()) {
            return new fd_1();
        }
        if (n2 == ajr_2.cBC.tI()) {
            return new GZ();
        }
        if (n2 == ajr_2.cBE.tI()) {
            return new alk();
        }
        if (n2 == ajr_2.cBF.tI()) {
            return new ih_0();
        }
        return new aIE(n2);
    }

    public static np_1 b(ResultSet resultSet) {
        np_1 np_12 = np_1.co(resultSet.getInt("parameter_type"));
        np_12.f(resultSet.getInt("parameter_id"));
        np_12.cn(resultSet.getInt("parameter_parent_id"));
        float[] fArray = (float[])resultSet.getArray("parameter_params").getArray();
        int[] nArray = new int[fArray.length];
        for (int j = 0; j < fArray.length; ++j) {
            nArray[j] = (int)fArray[j];
        }
        np_12.i(nArray);
        return np_12;
    }
}

