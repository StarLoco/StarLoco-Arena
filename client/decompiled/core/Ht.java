/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;
import java.sql.ResultSet;

public final class Ht
extends lJ {
    public static final Ht bet = null;
    private static final short fn = 1;
    private int beu;
    private int aQV;
    private int Om;
    private String bev;
    private int bew;
    private short bex;
    private boolean bey;
    private boolean bez;
    private boolean beA;
    private boolean beB;
    private boolean beC;
    private float[] beD;
    private int[] beE;
    private int[] beF;
    private int[] beG;
    private int[] beH;
    private int[] beI;
    private int[] beJ;
    private long[] beK;
    private boolean beL;
    private boolean beM;

    public Ht() {
        super((short)1);
    }

    public int cq() {
        return atr_0.cUJ.getId();
    }

    public int nj() {
        byte[] byArray = aey_0.hH(this.bev);
        return 16 + byArray.length + 4 + 2 + 1 + 1 + 1 + 1 + 1 + 32 + (this.beD == null ? 0 : this.beD.length * 4) + (this.beE == null ? 0 : this.beE.length * 4) + (this.beF == null ? 0 : this.beF.length * 4) + (this.beG == null ? 0 : this.beG.length * 4) + (this.beH == null ? 0 : this.beH.length * 4) + (this.beI == null ? 0 : this.beI.length * 4) + (this.beJ == null ? 0 : this.beJ.length * 4) + (this.beK == null ? 0 : this.beK.length * 8) + 1 + 1;
    }

    public byte[] cr() {
        byte[] byArray = aey_0.hH(this.bev);
        ByteBuffer byteBuffer = ByteBuffer.allocate(this.nj());
        byteBuffer.putInt(this.beu);
        byteBuffer.putInt(this.aQV);
        byteBuffer.putInt(this.Om);
        byteBuffer.putInt(byArray.length);
        byteBuffer.put(byArray);
        byteBuffer.putInt(this.bew);
        byteBuffer.putShort(this.bex);
        byteBuffer.put(this.bey ? (byte)1 : 0);
        byteBuffer.put(this.bez ? (byte)1 : 0);
        byteBuffer.put(this.beA ? (byte)1 : 0);
        byteBuffer.put(this.beB ? (byte)1 : 0);
        byteBuffer.put(this.beC ? (byte)1 : 0);
        if (this.beD != null) {
            byteBuffer.putInt(this.beD.length);
            for (float f : this.beD) {
                byteBuffer.putFloat(f);
            }
        } else {
            byteBuffer.putInt(0);
        }
        if (this.beE != null) {
            byteBuffer.putInt(this.beE.length);
            for (int n2 : this.beE) {
                byteBuffer.putInt(n2);
            }
        } else {
            byteBuffer.putInt(0);
        }
        if (this.beF != null) {
            byteBuffer.putInt(this.beF.length);
            for (int n3 : this.beF) {
                byteBuffer.putInt(n3);
            }
        } else {
            byteBuffer.putInt(0);
        }
        if (this.beG != null) {
            byteBuffer.putInt(this.beG.length);
            for (int n4 : this.beG) {
                byteBuffer.putInt(n4);
            }
        } else {
            byteBuffer.putInt(0);
        }
        if (this.beH != null) {
            byteBuffer.putInt(this.beH.length);
            for (int n5 : this.beH) {
                byteBuffer.putInt(n5);
            }
        } else {
            byteBuffer.putInt(0);
        }
        if (this.beI != null) {
            byteBuffer.putInt(this.beI.length);
            for (int n6 : this.beI) {
                byteBuffer.putInt(n6);
            }
        } else {
            byteBuffer.putInt(0);
        }
        if (this.beJ != null) {
            byteBuffer.putInt(this.beJ.length);
            for (int n7 : this.beJ) {
                byteBuffer.putInt(n7);
            }
        } else {
            byteBuffer.putInt(0);
        }
        if (this.beK != null) {
            byteBuffer.putInt(this.beK.length);
            for (long l2 : this.beK) {
                byteBuffer.putLong(l2);
            }
        } else {
            byteBuffer.putInt(0);
        }
        byteBuffer.put((byte)(this.beL ? 1 : 0));
        byteBuffer.put((byte)(this.beM ? 1 : 0));
        return byteBuffer.array();
    }

    public void a(ByteBuffer byteBuffer, int n2, short s) {
        this.cd(n2);
        if (s == 1) {
            int n3;
            this.beu = byteBuffer.getInt();
            this.aQV = byteBuffer.getInt();
            this.Om = byteBuffer.getInt();
            byte[] byArray = new byte[byteBuffer.getInt()];
            byteBuffer.get(byArray);
            this.bev = aey_0.V(byArray);
            this.bew = byteBuffer.getInt();
            this.bex = byteBuffer.getShort();
            this.bey = byteBuffer.get() == 1;
            this.bez = byteBuffer.get() == 1;
            this.beA = byteBuffer.get() == 1;
            this.beB = byteBuffer.get() == 1;
            this.beC = byteBuffer.get() == 1;
            int n4 = byteBuffer.getInt();
            this.beD = new float[n4];
            for (n3 = 0; n3 < n4; ++n3) {
                this.beD[n3] = byteBuffer.getFloat();
            }
            n4 = byteBuffer.getInt();
            this.beE = new int[n4];
            for (n3 = 0; n3 < n4; ++n3) {
                this.beE[n3] = byteBuffer.getInt();
            }
            n4 = byteBuffer.getInt();
            this.beF = new int[n4];
            for (n3 = 0; n3 < n4; ++n3) {
                this.beF[n3] = byteBuffer.getInt();
            }
            n4 = byteBuffer.getInt();
            this.beG = new int[n4];
            for (n3 = 0; n3 < n4; ++n3) {
                this.beG[n3] = byteBuffer.getInt();
            }
            n4 = byteBuffer.getInt();
            this.beH = new int[n4];
            for (n3 = 0; n3 < n4; ++n3) {
                this.beH[n3] = byteBuffer.getInt();
            }
            n4 = byteBuffer.getInt();
            this.beI = new int[n4];
            for (n3 = 0; n3 < n4; ++n3) {
                this.beI[n3] = byteBuffer.getInt();
            }
            n4 = byteBuffer.getInt();
            this.beJ = new int[n4];
            for (n3 = 0; n3 < n4; ++n3) {
                this.beJ[n3] = byteBuffer.getInt();
            }
            n4 = byteBuffer.getInt();
            this.beK = new long[n4];
            for (n3 = 0; n3 < n4; ++n3) {
                this.beK[n3] = byteBuffer.getLong();
            }
            this.beL = byteBuffer.get() == 1;
            this.beM = byteBuffer.get() == 1;
        } else {
            a.error((Object)"Tentative de d\u00e9s\u00e9rialisation d'un objet avec une version non prise en charge");
        }
    }

    public lJ cs() {
        return new Ht();
    }

    public int ST() {
        return this.beu;
    }

    public void ge(int n2) {
        this.beu = n2;
    }

    public int M() {
        return this.aQV;
    }

    public void fr(int n2) {
        this.aQV = n2;
    }

    public String SU() {
        return this.bev;
    }

    public void eB(String string) {
        this.bev = string;
    }

    public int SV() {
        return this.bew;
    }

    public void gf(int n2) {
        this.bew = n2;
    }

    public short SW() {
        return this.bex;
    }

    public void aD(short s) {
        this.bex = s;
    }

    public boolean SX() {
        return this.bey;
    }

    public void bw(boolean bl2) {
        this.bey = bl2;
    }

    public boolean SY() {
        return this.bez;
    }

    public void bx(boolean bl2) {
        this.bez = bl2;
    }

    public boolean SZ() {
        return this.beA;
    }

    public void by(boolean bl2) {
        this.beA = bl2;
    }

    public boolean Ta() {
        return this.beB;
    }

    public void bz(boolean bl2) {
        this.beB = bl2;
    }

    public boolean isCritical() {
        return this.beC;
    }

    public void bA(boolean bl2) {
        this.beC = bl2;
    }

    public float[] Tb() {
        return this.beD;
    }

    public void n(float[] fArray) {
        this.beD = fArray;
    }

    public int[] Tc() {
        return this.beE;
    }

    public void s(int[] nArray) {
        this.beE = nArray;
    }

    public int[] Td() {
        return this.beF;
    }

    public void t(int[] nArray) {
        this.beF = nArray;
    }

    public int[] Te() {
        return this.beG;
    }

    public void u(int[] nArray) {
        this.beG = nArray;
    }

    public int[] Tf() {
        return this.beH;
    }

    public void v(int[] nArray) {
        this.beH = nArray;
    }

    public int[] Tg() {
        return this.beI;
    }

    public void w(int[] nArray) {
        this.beI = nArray;
    }

    public int[] Th() {
        return this.beJ;
    }

    public void x(int[] nArray) {
        this.beJ = nArray;
    }

    public long[] Ti() {
        return this.beK;
    }

    public void g(long[] lArray) {
        this.beK = lArray;
    }

    public int sn() {
        return this.Om;
    }

    public void cn(int n2) {
        this.Om = n2;
    }

    public boolean Tj() {
        return this.beL;
    }

    public void bB(boolean bl2) {
        this.beL = bl2;
    }

    public boolean Tk() {
        return this.beM;
    }

    public void bC(boolean bl2) {
        this.beM = bl2;
    }

    public static Ht c(ResultSet resultSet) {
        Ht ht = bet;
        if (resultSet.getInt("effect_id") != 0) {
            ht = new Ht();
            ht.cd(resultSet.getInt("effect_id"));
            ht.ge(resultSet.getInt("effect_id"));
            ht.fr(resultSet.getInt("effect_action_id"));
            ht.cn(resultSet.getInt("effect_parent_id"));
            if (resultSet.getArray("effect_duration") != null) {
                ht.x((int[])resultSet.getArray("effect_duration").getArray());
            }
            ht.eB(resultSet.getString("effect_parent_type"));
            ht.gf(resultSet.getInt("effect_area_shape"));
            ht.aD(resultSet.getShort("effect_area_ordering_method"));
            if (resultSet.getArray("effect_area_size") != null) {
                ht.w((int[])resultSet.getArray("effect_area_size").getArray());
            }
            ht.bw(resultSet.getBoolean("effect_affected_by_localisation"));
            ht.bx(resultSet.getBoolean("effect_target_trigger_is_self"));
            ht.by(resultSet.getBoolean("effect_is_personal"));
            ht.bz(resultSet.getBoolean("effect_has_single_target"));
            ht.bA(resultSet.getBoolean("effect_is_critical"));
            if (resultSet.getArray("effect_params") != null) {
                ht.n((float[])resultSet.getArray("effect_params").getArray());
            }
            if (resultSet.getArray("effect_triggers_before") != null) {
                ht.s((int[])resultSet.getArray("effect_triggers_before").getArray());
            }
            if (resultSet.getArray("effect_triggers_after") != null) {
                ht.t((int[])resultSet.getArray("effect_triggers_after").getArray());
            }
            if (resultSet.getArray("effect_end_triggers") != null) {
                ht.u((int[])resultSet.getArray("effect_end_triggers").getArray());
            }
            if (resultSet.getArray("effect_targets") != null) {
                ht.g((long[])resultSet.getArray("effect_targets").getArray());
            }
            ht.bB(resultSet.getBoolean("effect_triggered_with_duration"));
            ht.bC(resultSet.getBoolean("effect_applied_if_target_valid"));
        }
        return ht;
    }
}

