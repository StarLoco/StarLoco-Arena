/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from ve
 */
public class ve_0
extends jb_2
implements aho_0,
Comparable {
    public static final String arR = "id";
    public static final String xX = "name";
    public static final String aog = "description";
    public static final String arS = "backgroundDescription";
    public static final String arT = "itemIconUrl";
    public static final String aoi = "illustrationUrl";
    public static final String aoh = "iconUrl";
    public static final String aoj = "cardType";
    public static final String arU = "equipmentType";
    public static final String Oj = "value";
    public static final String arV = "actionPoints";
    public static final String arW = "aoe";
    public static final String arX = "target";
    public static final String arY = "range";
    public static final String arZ = "duration";
    public static final String[] ce = new String[]{"id", "name", "description", "backgroundDescription", "itemIconUrl", "illustrationUrl", "iconUrl", "cardType", "equipmentType", "value", "actionPoints", "aoe", "target", "range", "duration"};
    private final int asa;
    private final int it;
    private boolean apG;
    private String fM = null;
    private String asb = null;

    public ve_0(int n2, vi_1 vi_12, int n3, int n4, boolean bl2, int n5, int n6, boolean bl3, boolean bl4, int n7, boolean bl5, boolean bl6, int n8, boolean bl7) {
        super(n2, vi_12, n4, bl2, n5, n6, bl3, bl4, n7, bl5, bl6);
        this.asa = n3;
        this.it = n8;
        this.apG = bl7;
    }

    public String getName() {
        return aon_0.aYc().a(1, this.getId(), new Object[0]);
    }

    public void setDescription(String string) {
        this.fM = string;
    }

    public void cA(String string) {
        this.asb = string;
    }

    public String getDescription() {
        if (this.fM == null) {
            this.setDescription(asf_0.a(this.getId(), this.AD(), this.Vt(), this.Vu(), 21));
        }
        return this.fM;
    }

    public String Bn() {
        if (this.asb == null) {
            this.cA(asf_0.bU(this.getId(), 2));
        }
        return this.asb;
    }

    public int eA() {
        return this.it;
    }

    public boolean AD() {
        return this.apG;
    }

    public int Bo() {
        return this.asa;
    }

    public int getValue() {
        je_2 je_22 = jk_1.mf().mg();
        int n2 = je_22.na().get(this.getId());
        return super.getValue() + n2;
    }

    public void b(String string, Object object) {
    }

    public Object getFieldValue(String string) {
        Object object;
        if (string.equals(arR)) {
            return this.getId();
        }
        if (string.equals(xX)) {
            return this.getName();
        }
        if (string.equals(aog)) {
            return this.getDescription();
        }
        if (string.equals(arS)) {
            return this.Bn();
        }
        if (string.equals(arT)) {
            try {
                return String.format(mu_1.rM().getString("fighterEquipmentIconsPath"), this.getId());
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        if (string.equals(aoi)) {
            try {
                return String.format(mu_1.rM().getString("fighterEquipmentIllustrationsPath"), this.getId());
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        if (string.equals(aoh)) {
            try {
                return String.format(mu_1.rM().getString("fighterEquipmentTypeIconPath"), this.Vk().aiK(), this.Bo());
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        if (string.equals(aoj)) {
            if (this.Vk() == vi_1.bSW) {
                return "weapon";
            }
            return "equipment";
        }
        if (string.equals(arU)) {
            return " (" + aon_0.aYc().getString(this.Vk().cD()) + ")";
        }
        if (string.equals(Oj)) {
            return this.getValue();
        }
        if (string.equals(arV)) {
            return this.Vo();
        }
        if (string.equals(arW) && (object = this.Vt()) != null && ((acy)object).size() > 0) {
            zg_1 zg_12 = ((xj_0)((acy)object).get(0)).alM().fj();
            if (zg_12 == zg_1.cdF || zg_12 == zg_1.cdv) {
                return "aoeIconPOINT";
            }
            if (zg_12 == zg_1.cdw || zg_12 == zg_1.cdx || zg_12 == zg_1.cdz) {
                return "aoeIconCROSS";
            }
            if (zg_12 == zg_1.cdy || zg_12 == zg_1.cdD) {
                return "aoeIconLINE";
            }
            return "aoeIcon" + zg_12;
        }
        if (string.equals(arX)) {
            object = "cast.targetFree";
            for (xj_0 xj_02 : this.Vt()) {
                long[] lArray;
                if (this.Az() == 0) {
                    object = "cast.targetCaster";
                    continue;
                }
                for (long l2 : lArray = ((aLc)xj_02.alI()).aWk()) {
                    if ((4L & l2) != 0L) {
                        object = "cast.targetAlly";
                        continue;
                    }
                    if ((8L & l2) != 0L) {
                        object = "cast.targetEnemy";
                        continue;
                    }
                    if ((0x20L & l2) == 0L) continue;
                    object = "cast.targetSummon";
                }
            }
            return aon_0.aYc().getString((String)object);
        }
        if (string.equals(arY)) {
            if (this.AA() != this.Az()) {
                return this.AA() + "-" + this.Az();
            }
            return this.Az();
        }
        if (string.equals(arZ)) {
            return 0;
        }
        return null;
    }

    public String[] getFields() {
        return ce;
    }

    public boolean l(String string) {
        return false;
    }

    public void c(String string, Object object) {
    }

    public void a(String string, Object object) {
    }

    public int compareTo(Object object) {
        if (object instanceof ve_0) {
            ve_0 ve_02 = (ve_0)object;
            if (this.Bo() > ve_02.Bo()) {
                return 1;
            }
            if (this.Bo() < ve_02.Bo()) {
                return -1;
            }
            return this.getName().compareTo(ve_02.getName());
        }
        throw new RuntimeException("attempting to compare a " + object.getClass().getName() + " to a " + this.getClass().getName());
    }

    public byte[] cd() {
        byte[] byArray = new byte[4];
        ByteBuffer.wrap(byArray).putInt(this.aW);
        return byArray;
    }

    public int Bp() {
        return 4;
    }
}

