/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class aMx
extends gi_2 {
    int dXV = 5;
    List vC = null;
    final int vD = 4;
    int vE = 0;

    public void start() {
        String string = this.aqI();
        if (string == null) {
            return;
        }
        try {
            this.dXV = Integer.parseInt(string);
        }
        catch (NumberFormatException numberFormatException) {
            this.eg("");
        }
        List list = this.aqJ();
        if (list != null && list.size() > 1) {
            int n2 = list.size();
            for (int j = 1; j < n2; ++j) {
                Map map;
                ayx ayx2;
                String string2 = (String)list.get(j);
                vU vU2 = this.QK();
                if (vU2 == null || (ayx2 = (ayx)(map = (Map)vU2.getObject("EVALUATOR_MAP")).get(string2)) == null) continue;
                this.a(ayx2);
            }
        }
    }

    private void a(ayx ayx2) {
        if (this.vC == null) {
            this.vC = new ArrayList();
        }
        this.vC.add(ayx2);
    }

    public String b(tz_0 tz_02) {
        qw_0[] qw_0Array;
        int n2;
        StringBuffer stringBuffer = new StringBuffer();
        if (this.vC != null) {
            boolean bl2 = false;
            for (n2 = 0; n2 < this.vC.size(); ++n2) {
                ayx ayx2 = (ayx)this.vC.get(n2);
                try {
                    if (!ayx2.w(tz_02)) continue;
                    bl2 = true;
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
            if (!bl2) {
                return "";
            }
        }
        if ((qw_0Array = tz_02.agv()) != null && qw_0Array.length > 0) {
            n2 = this.dXV < qw_0Array.length ? this.dXV : qw_0Array.length;
            for (int j = 0; j < n2; ++j) {
                stringBuffer.append("Caller+");
                stringBuffer.append(j);
                stringBuffer.append("\t at ");
                stringBuffer.append(qw_0Array[j]);
                stringBuffer.append(kJ.sy);
            }
            return stringBuffer.toString();
        }
        return qw_0.bId;
    }
}

