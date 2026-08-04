/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Un
 */
public class un_2
extends amp_1 {
    protected void a(apn_0 apn_02, ee ee2) {
        int[] nArray = ee2.z(apn_02.getId());
        assert (nArray != null) : "element interactif " + apn_02.getId() + " n'a pas de vue";
        for (int j = 0; j < nArray.length; ++j) {
            axu_0 axu_02;
            int n2 = nArray[j];
            if (a.isTraceEnabled()) {
                a.trace((Object)("Adding view " + n2 + " to element " + apn_02.getId()));
            }
            if ((axu_02 = ((vx_1)this.dXK).iw(n2)) == null) continue;
            apn_02.b(axu_02);
        }
    }
}

