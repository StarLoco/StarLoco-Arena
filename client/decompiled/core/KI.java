/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;

public class KI
implements aho_0 {
    public static final String nM = "guild.name";
    public static final String bpb = "guild.members";
    public static final String bpc = "guild.canManage";
    public static final String bpd = "guild.editableRanks";
    public static final String bpe = "guild.demon";
    public static final String[] ce = new String[]{"guild.members", "guild.name", "guild.canManage", "guild.editableRanks", "guild.demon"};
    private long nD;
    private String nR;
    private short atZ;
    private ca_0[] bpf;
    private ArrayList bpg = new ArrayList();
    private ArrayList bph = new ArrayList();

    public String[] getFields() {
        return ce;
    }

    public String hd() {
        if (this.atZ != 0) {
            String string = "";
            try {
                string = afg_1.kn(this.atZ);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                string = "" + this.atZ;
            }
            return this.nR + " (" + aon_0.aYc().getString("ladderInformation.demon") + " " + string + ")";
        }
        return this.nR;
    }

    public short WU() {
        return this.atZ;
    }

    public void T(String string) {
        this.nR = string;
    }

    public ca_0[] WV() {
        return this.bpf;
    }

    public void a(ca_0[] ca_0Array) {
        this.bpf = ca_0Array;
    }

    public void bY(long l2) {
        ArrayList<ca_0> arrayList = new ArrayList<ca_0>();
        for (int j = 0; j < this.bpf.length; ++j) {
            if (this.bpf[j].Ke() == l2) continue;
            arrayList.add(this.bpf[j]);
        }
        this.bpf = new ca_0[this.bpf.length - 1];
        arrayList.toArray(this.bpf);
    }

    public ArrayList WW() {
        return this.bpg;
    }

    public ArrayList WX() {
        return this.bph;
    }

    public long getId() {
        return this.nD;
    }

    public void a(vd_2 vd_22) {
        this.bph.add(vd_22);
        this.bpg.add((vd_2)vd_22.clone());
        azs_0.aLV().a((aho_0)this, bpd);
    }

    public void b(vd_2 vd_22) {
        this.bph.remove(vd_22);
        vd_2 vd_23 = null;
        for (vd_2 vd_24 : this.bpg) {
            if (vd_24.aRe() != vd_22.aRe()) continue;
            vd_23 = vd_24;
            break;
        }
        if (vd_23 != null) {
            this.bpg.remove(vd_23);
        }
        azs_0.aLV().a((aho_0)this, bpd);
    }

    public void b(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.nD = byteBuffer.getLong();
        byte[] byArray2 = new byte[byteBuffer.get()];
        byteBuffer.get(byArray2);
        this.nR = aey_0.V(byArray2);
        short s = byteBuffer.getShort();
        short s2 = byteBuffer.getShort();
        int n2 = byteBuffer.getInt();
        int n3 = byteBuffer.getInt();
        this.atZ = byteBuffer.getShort();
        int n4 = byteBuffer.getInt();
        int n5 = byteBuffer.get();
        this.bpg.clear();
        for (int j = 0; j < n5; ++j) {
            byte[] byArray3 = new byte[byteBuffer.getShort()];
            byteBuffer.get(byArray3);
            vd_2 vd_22 = vd_2.A(byArray3);
            this.bpg.add(vd_22);
        }
    }

    public boolean WY() {
        ca_0 ca_02 = apN.aDK().Ln().aPY();
        return ca_02 != null && ca_02.Kg().aQQ();
    }

    public Object getFieldValue(String string) {
        if (string.equals(bpb)) {
            ca_0[] ca_0Array = this.WV();
            ArrayList<ca_0> arrayList = new ArrayList<ca_0>();
            if (ca_0Array != null) {
                for (int j = 0; j < ca_0Array.length; ++j) {
                    arrayList.add(ca_0Array[j]);
                }
                Collections.sort(arrayList, new Wt(this));
            }
            return arrayList.toArray();
        }
        if (string.equals(nM)) {
            return this.hd();
        }
        if (string.equals(bpc)) {
            return this.WY();
        }
        if (string.equals(bpd)) {
            Collections.sort(this.bph, new wu_0(this));
            return this.bph.toArray();
        }
        if (string.equals(bpe)) {
            String string2 = "";
            try {
                string2 = afg_1.kn(this.atZ);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                string2 = "" + this.atZ;
            }
            return aon_0.aYc().getString("ladderInformation.demon") + " " + string2;
        }
        return null;
    }

    public void a(String string, Object object) {
        if (string.equals(nM)) {
            this.T((String)object);
        }
        if (string.equals(bpb)) {
            this.a((ca_0[])object);
        }
    }

    public void c(String string, Object object) {
    }

    public void b(String string, Object object) {
    }

    public boolean l(String string) {
        return false;
    }

    public void clean() {
        this.bph = null;
        this.bpg.clear();
        this.bpf = null;
        this.nR = null;
        this.nD = 0L;
    }
}

