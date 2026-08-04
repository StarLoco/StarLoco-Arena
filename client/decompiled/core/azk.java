/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.nio.ByteBuffer;
import java.util.ArrayList;
import org.apache.log4j.Logger;

public abstract class azk
implements ut_0 {
    protected static final Logger a = Logger.getLogger(azk.class);
    private final cp_2 dns = new cp_2();
    private final ArrayList dnt = new ArrayList();
    private final ArrayList dnu = new ArrayList();
    private static azk dnv;

    protected azk() {
    }

    public static void a(azk azk2) {
        dnv = azk2;
    }

    public static azk aLN() {
        return dnv;
    }

    public void g(fv fv2) {
        this.dns.a(fv2.getId(), fv2);
    }

    public void h(fv fv2) {
        if (fv2.iQ() == xq.axU.lV()) {
            this.dnt.add(fv2);
        }
    }

    public void i(fv fv2) {
        if (fv2.iQ() == xq.axT.lV()) {
            this.dnu.add(fv2);
        }
    }

    public ajv_2 aLO() {
        int n2 = this.dnt.size();
        short s = (short)Math.min(ou_1.he(3), n2);
        ajv_2 ajv_22 = new ajv_2(s, this, null, false, false, false);
        try {
            while (ajv_22.size() < s) {
                fv fv2 = (fv)this.dnt.get(ou_1.he(n2) - 1);
                if (ajv_22.ab(fv2.jf())) continue;
                ajv_22.a(fv2);
            }
        }
        catch (Exception exception) {
            a.error((Object)"impossible d'ajouter un sort \u00e0 l'inventaire des sorts");
        }
        return ajv_22;
    }

    public ajv_2 D(int[] nArray) {
        ajv_2 ajv_22 = new ajv_2(3, this, null, false, false, false);
        while (nArray.length > 3) {
            int[] nArray2 = new int[nArray.length - 1];
            int n2 = jr_0.VF().nextInt(nArray.length);
            System.arraycopy(nArray, 0, nArray2, 0, n2);
            System.arraycopy(nArray, n2 + 1, nArray2, n2, nArray.length - n2 - 1);
            nArray = nArray2;
        }
        try {
            for (int j = 0; j < nArray.length; ++j) {
                ajv_22.a((akU)this.dns.t(nArray[j]));
            }
        }
        catch (Exception exception) {
            a.error((Object)"impossible d'ajouter un sort \u00e0 l'inventaire des sorts");
        }
        return ajv_22;
    }

    public ArrayList aLP() {
        return this.dnu;
    }

    public cp_2 aLQ() {
        return this.dns;
    }

    public fv el(long l2) {
        return (fv)this.dns.t(l2);
    }

    public fv E(ByteBuffer byteBuffer) {
        return (fv)this.dns.t(byteBuffer.getInt());
    }
}

