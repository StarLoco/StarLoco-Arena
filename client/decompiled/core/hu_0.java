/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
 * Renamed from hu
 */
public class hu_0
extends dd_2 {
    int vB;
    List vC = null;
    final int vD = 4;
    int vE = 0;

    public void start() {
        String string = this.aqI();
        if (string == null) {
            this.vB = Integer.MAX_VALUE;
        } else if ("full".equals(string = string.toLowerCase())) {
            this.vB = Integer.MAX_VALUE;
        } else if ("short".equals(string)) {
            this.vB = 2;
        } else {
            try {
                this.vB = Integer.parseInt(string) + 1;
            }
            catch (NumberFormatException numberFormatException) {
                this.eg("Could not parser [" + string + " as an integer");
                this.vB = Integer.MAX_VALUE;
            }
        }
        List list = this.aqJ();
        if (list != null && list.size() > 1) {
            int n2 = list.size();
            for (int j = 1; j < n2; ++j) {
                String string2 = (String)list.get(j);
                vU vU2 = this.QK();
                Map map = (Map)vU2.getObject("EVALUATOR_MAP");
                ayx ayx2 = (ayx)map.get(string2);
                this.a(ayx2);
            }
        }
        super.start();
    }

    private void a(ayx ayx2) {
        if (this.vC == null) {
            this.vC = new ArrayList();
        }
        this.vC.add(ayx2);
    }

    public void stop() {
        this.vC = null;
        super.stop();
    }

    protected void a(StringBuilder stringBuilder, un_1 un_12) {
    }

    protected void c(tz_0 tz_02) {
    }

    public String b(tz_0 tz_02) {
        int n2;
        int n3;
        StringBuilder stringBuilder = new StringBuilder(32);
        ik_2 ik_22 = tz_02.ags();
        if (ik_22 == null) {
            return "";
        }
        un_1[] un_1Array = ik_22.UL();
        int n4 = n3 = this.vB > un_1Array.length ? un_1Array.length : this.vB;
        if (this.vC != null) {
            n2 = 1;
            for (int j = 0; j < this.vC.size(); ++j) {
                ayx ayx2 = (ayx)this.vC.get(j);
                try {
                    if (!ayx2.w(tz_02)) continue;
                    n2 = 0;
                    break;
                }
                catch (Gp gp) {
                    ++this.vE;
                    if (this.vE < 4) {
                        this.e("Exception thrown for evaluator named [" + ayx2.getName() + "]", gp);
                        continue;
                    }
                    if (this.vE != 4) continue;
                    aIX aIX2 = new aIX("Exception thrown for evaluator named [" + ayx2.getName() + "].", this, gp);
                    aIX2.c(new aIX("This was the last warning about this evaluator's errors.We don't want the StatusManager to get flooded.", this));
                    this.b(aIX2);
                }
            }
            if (n2 == 0) {
                return "";
            }
        }
        this.c(tz_02);
        stringBuilder.append(un_1Array[0]).append(kJ.sy);
        for (n2 = 1; n2 < n3; ++n2) {
            String string = un_1Array[n2].toString();
            stringBuilder.append(string);
            this.a(stringBuilder, un_1Array[n2]);
            stringBuilder.append(kJ.sy);
        }
        return stringBuilder.toString();
    }
}

