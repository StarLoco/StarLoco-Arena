/*
 * Decompiled with CFR 0.152.
 */
import java.util.Iterator;

/*
 * Renamed from aHH
 */
public class ahh_0 {
    private final ahl_1 auj;
    private final ea_0 dMT;

    public ahl_1 aUe() {
        return this.auj;
    }

    public ahh_0(ea_0 ea_02, ahl_1 ahl_12) {
        if (ea_02 == null || ahl_12 == null) {
            throw new IllegalArgumentException("aucun argument du constructeur de " + this.getClass().getSimpleName() + " ne doit \u00eatre null");
        }
        this.dMT = ea_02;
        this.auj = ahl_12;
    }

    public ack_1 ey(long l2) {
        he_1 he_12 = this.dMT.gX();
        return he_12 == null ? null : he_12.bG(l2);
    }

    public xb_2 ez(long l2) {
        if (this.dMT.gW() == null) {
            return null;
        }
        Iterator iterator = this.dMT.gT().agn();
        while (iterator.hasNext()) {
            xb_2 xb_22;
            kc_2 kc_22 = (kc_2)iterator.next();
            if (kc_22.PJ() == null || (xb_22 = kc_22.PJ().dK(l2)) == null) continue;
            return xb_22;
        }
        return null;
    }
}

