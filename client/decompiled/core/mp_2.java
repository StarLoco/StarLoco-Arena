/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;
import java.sql.ResultSet;

/*
 * Renamed from mp
 */
public abstract class mp_2 {
    public static final mp_2 JH = null;
    private int aW;
    protected int[] JI;
    private boolean JJ;
    private int JK;
    private byte JL;

    public static mp_2 a(short s, int n2, int[] nArray, boolean bl2, int n3, byte by) {
        mp_2 mp_22 = null;
        if (s == qk_1.bHA.tI()) {
            mp_22 = new cy_1();
        }
        if (s == qk_1.bHB.tI()) {
            mp_22 = new fp_1();
        }
        if (s == qk_1.bHC.tI()) {
            mp_22 = new ct_1();
        }
        if (s == qk_1.bHD.tI()) {
            mp_22 = new ajm_0();
        }
        if (mp_22 != null) {
            mp_22.aW = n2;
            mp_22.JI = nArray;
            mp_22.JJ = bl2;
            mp_22.JK = n3;
            mp_22.JL = by;
        }
        return mp_22;
    }

    public abstract boolean a(mv_1 var1, yg_0 var2, yg_0 var3);

    public int nj() {
        return 7 + 4 * this.JI.length + 1 + 4 + 1;
    }

    public byte[] cd() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(this.nj());
        byteBuffer.putShort(this.getType());
        byteBuffer.putInt(this.aW);
        byteBuffer.put((byte)this.JI.length);
        for (int n2 : this.JI) {
            byteBuffer.putInt(n2);
        }
        byteBuffer.put(this.JJ ? (byte)1 : 0);
        byteBuffer.putInt(this.JK);
        byteBuffer.put(this.JL);
        return byteBuffer.array();
    }

    public static mp_2 i(ByteBuffer byteBuffer) {
        boolean bl2;
        short s = byteBuffer.getShort();
        int n2 = byteBuffer.getInt();
        int[] nArray = new int[byteBuffer.get()];
        for (bl2 = false; bl2 < nArray.length; bl2 += 1) {
            nArray[bl2] = byteBuffer.getInt();
        }
        bl2 = byteBuffer.get() == 1;
        int n3 = byteBuffer.getInt();
        byte by = byteBuffer.get();
        return mp_2.a(s, n2, nArray, bl2, n3, by);
    }

    public int getId() {
        return this.aW;
    }

    public int[] rg() {
        return this.JI;
    }

    public boolean rh() {
        return this.JJ;
    }

    public int ri() {
        return this.JK;
    }

    public byte rj() {
        return this.JL;
    }

    public void s(byte by) {
        this.JL = by;
    }

    public abstract short getType();

    public static mp_2 a(ResultSet resultSet) {
        mp_2 mp_22 = JH;
        int n2 = resultSet.getInt("condition_id");
        if (n2 != 0) {
            float[] fArray = (float[])resultSet.getArray("condition_parameters").getArray();
            int[] nArray = new int[fArray.length];
            for (int j = 0; j < fArray.length; ++j) {
                nArray[j] = (int)fArray[j];
            }
            mp_22 = mp_2.a(resultSet.getShort("condition_type"), n2, nArray, resultSet.getBoolean("condition_is_necessary"), resultSet.getInt("condition_victory_points"), resultSet.getByte("condition_affected_team"));
        }
        return mp_22;
    }
}

