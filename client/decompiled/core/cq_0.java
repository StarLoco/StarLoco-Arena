/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Cq
 */
public class cq_0
extends hu_0 {
    protected void a(StringBuilder stringBuilder, un_1 un_12) {
        abl_0 abl_02;
        pj_1 pj_12 = un_12.AT();
        if (pj_12 != null && (abl_02 = pj_12.up()) != null) {
            stringBuilder.append(" [").append(abl_02.aNk()).append(':').append(abl_02.getVersion()).append(']');
        }
    }

    protected void c(tz_0 tz_02) {
        ik_2 ik_22 = tz_02.ags();
        ik_22.UK();
    }
}

