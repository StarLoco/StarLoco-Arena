/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.nio.ByteBuffer;
import java.util.Comparator;
import org.apache.log4j.Logger;

/*
 * Renamed from Iz
 */
public abstract class iz_0
implements aho_0 {
    private static final Logger a = Logger.getLogger(iz_0.class);
    public static String vp = "title";
    public static String sU = "description";
    public static String bhD = "typeIcon";
    public static String bhE = "registrationButton";
    public static String nN = "style";
    public static String[] ce = new String[]{vp, sU, bhD, bhE, nN};
    protected rd_1 OV;
    protected rd_1 bhF;
    protected jx_0 bhG;
    private long nD = 0L;
    protected int asa;
    public static Comparator OU = new ajn_2();

    public iz_0() {
        this.OV = null;
        this.bhF = null;
        this.bhG = jx_0.blQ;
        this.asa = -1;
    }

    public iz_0(rd_1 rd_12, rd_1 rd_13, jx_0 jx_02, int n2) {
        this.OV = rd_12;
        this.bhF = rd_13;
        this.bhG = jx_02;
        this.asa = n2;
    }

    public void UC() {
        this.OV.b(this.bhG);
    }

    public abstract byte[] cd();

    public abstract int nj();

    public abstract iz_0 h(ByteBuffer var1);

    public abstract iz_0 nk();

    public int UD() {
        return 32;
    }

    public void B(ByteBuffer byteBuffer) {
        byteBuffer.putInt(this.getType());
        byteBuffer.putLong(this.OV.uJ());
        byteBuffer.putLong(this.bhF.uJ());
        byteBuffer.putLong(this.bhG.uJ());
        byteBuffer.putInt(this.asa);
    }

    public void C(ByteBuffer byteBuffer) {
        this.nD = byteBuffer.getLong();
        this.OV = rd_1.aF(byteBuffer.getLong());
        this.bhF = rd_1.aF(byteBuffer.getLong());
        long l2 = byteBuffer.getLong();
        this.bhG = l2 == jx_0.blQ.uJ() ? jx_0.blQ : jx_0.bT(l2);
        this.asa = byteBuffer.getInt();
    }

    public void c(iz_0 iz_02) {
        iz_02.a(new rd_1(this.OV));
        iz_02.b(new rd_1(this.bhF));
        if (this.bhG == jx_0.blQ) {
            iz_02.d(jx_0.blQ);
        } else {
            iz_02.d(new jx_0(this.bhG));
        }
        iz_02.gi(this.asa);
    }

    public acx_1 sz() {
        return this.OV;
    }

    public void a(rd_1 rd_12) {
        this.OV = rd_12;
    }

    public acx_1 UE() {
        return this.bhF;
    }

    public void b(rd_1 rd_12) {
        this.bhF = rd_12;
    }

    public jx_0 UF() {
        return this.bhG;
    }

    public void d(jx_0 jx_02) {
        this.bhG = jx_02;
    }

    public long getId() {
        return this.nD;
    }

    public int Bo() {
        return this.asa;
    }

    public void gi(int n2) {
        this.asa = n2;
    }

    public abstract int getType();

    public String[] getFields() {
        return ce;
    }

    public Object getFieldValue(String string) {
        if (string.equals(vp)) {
            return aon_0.aYc().a(35, this.Bo(), new Object[0]);
        }
        if (string.equals(sU)) {
            return aon_0.aYc().a(36, this.Bo(), new Object[0]);
        }
        if (string.equals(bhD)) {
            try {
                return String.format(mu_1.rM().getString("eventTypeIconsPath"), amu.lu(this.getType()));
            }
            catch (Exception exception) {
                a.warn((Object)"", (Throwable)exception);
            }
        }
        if (string.equals(bhE)) {
            return false;
        }
        if (string.equals(nN)) {
            return "eventDefault";
        }
        return null;
    }

    public void a(String string, Object object) {
    }

    public void c(String string, Object object) {
    }

    public void b(String string, Object object) {
    }

    public boolean l(String string) {
        return false;
    }
}

