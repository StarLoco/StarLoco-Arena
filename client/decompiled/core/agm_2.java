/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

/*
 * Renamed from agm
 */
public enum agm_2 implements rk_0
{
    ctO(0, 0),
    ctP(1, 0),
    ctQ(2, 4),
    ctR(4, 6),
    ctS(8, 2),
    ctT(16, 1),
    ctU(32, 7),
    ctV(64, 3),
    ctW(128, 5),
    ctX(15, 0),
    ctY(240, 0),
    ctZ(256, 0);

    private final short cua;
    private final byte cub;

    /*
     * WARNING - void declaration
     */
    private agm_2() {
        void var4_2;
        void var3_1;
        this.cua = var3_1;
        this.cub = var4_2;
    }

    public static agm_2 bH(short s) {
        for (agm_2 agm_22 : agm_2.values()) {
            if (agm_22.awm() != s) continue;
            return agm_22;
        }
        return null;
    }

    public short awm() {
        return this.cua;
    }

    public byte awn() {
        return this.cub;
    }

    public String cC() {
        return Short.toString(this.cua);
    }

    public String cD() {
        return this.toString();
    }

    public String cE() {
        return null;
    }

    public static ArrayList bI(short s) {
        ArrayList<agm_2> arrayList = new ArrayList<agm_2>();
        for (agm_2 agm_22 : agm_2.values()) {
            if (agm_22 == ctX || agm_22 == ctY || agm_22 == ctO || (agm_22.awm() & s) != agm_22.awm()) continue;
            arrayList.add(agm_22);
        }
        return arrayList;
    }
}

