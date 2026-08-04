/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.nio.ByteBuffer;
import org.apache.log4j.Logger;

/*
 * Renamed from vd
 */
public class vd_2
extends aen_1
implements aho_0 {
    private static final Logger a = Logger.getLogger(vd_2.class);
    public static final String NAME = "name";
    public static final String arL = "rankLevel";
    public static final String arM = "rankIconUrl";
    public static final String arN = "canInvite";
    public static final String arO = "canRemove";
    public static final String arP = "canPromote";
    public static final String arQ = "canDepromote";
    public static final String[] ce = new String[]{"name", "rankLevel", "rankIconUrl", "canInvite", "canRemove", "canPromote", "canDepromote"};

    public String[] getFields() {
        return ce;
    }

    public static vd_2 A(byte[] byArray) {
        vd_2 vd_22 = new vd_2();
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        vd_22.bny = byteBuffer.getShort();
        vd_22.bzu = byteBuffer.getInt();
        byte[] byArray2 = new byte[byteBuffer.get()];
        byteBuffer.get(byArray2);
        vd_22.dBT = aey_0.V(byArray2);
        return vd_22;
    }

    public Object getFieldValue(String string) {
        if (string.equals(NAME)) {
            return this.dBT;
        }
        if (string.equals(arL)) {
            return this.bny;
        }
        if (string.equals(arM)) {
            try {
                return String.format(mu_1.rM().getString("guildRankIconsPath"), this.bny);
            }
            catch (Exception exception) {
                a.warn((Object)"", (Throwable)exception);
            }
        }
        if (string.equals(arN)) {
            return this.aQY();
        }
        if (string.equals(arO)) {
            return this.aQZ();
        }
        if (string.equals(arP)) {
            return this.aRa();
        }
        if (string.equals(arQ)) {
            return this.aRb();
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

    public Object clone() {
        vd_2 vd_22 = new vd_2();
        vd_22.bzu = this.bzu;
        vd_22.bny = this.bny;
        vd_22.dBT = this.dBT;
        return vd_22;
    }
}

