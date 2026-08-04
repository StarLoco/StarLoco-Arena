/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/*
 * Renamed from aIr
 */
public class air_2
extends ael_2 {
    private final List dPN = new ArrayList();
    private final mm_0 dPO = new mm_0();
    private final List dPP = new ArrayList();
    private final jg_0 dPQ = new jg_0();

    public boolean a(byte[] byArray) {
        short s;
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        for (s = byteBuffer.getShort(); s > 0; s = (short)(s - 1)) {
            short s2 = byteBuffer.getShort();
            wy_2 wy_22 = new wy_2();
            if (!wy_22.b(byteBuffer)) {
                a.error((Object)"Impossible de d\u00e9s\u00e9rialiser une carte pour une modification d'inventaire");
                continue;
            }
            this.dPN.add(new pf_0(s2, wy_22));
        }
        for (s = byteBuffer.getShort(); s > 0; s = (short)(s - 1)) {
            this.dPO.add(byteBuffer.getShort());
        }
        for (s = byteBuffer.getShort(); s > 0; s = (short)(s - 1)) {
            wy_2 wy_23 = new wy_2();
            if (!wy_23.b(byteBuffer)) {
                a.error((Object)"Impossible de d\u00e9s\u00e9rialiser une carte pour une modification d'inventaire");
                byteBuffer.getShort();
                continue;
            }
            wy_23.q(byteBuffer.getShort());
            this.dPP.add(wy_23);
        }
        for (s = byteBuffer.getShort(); s > 0; s = (short)(s - 1)) {
            this.dPQ.add(byteBuffer.getInt());
        }
        return true;
    }

    public int getId() {
        return 5200;
    }

    public List aUU() {
        return this.dPN;
    }

    public mm_0 aUV() {
        return this.dPO;
    }

    public List aUW() {
        return this.dPP;
    }

    public jg_0 aUX() {
        return this.dPQ;
    }
}

