/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from PC
 */
public class pc_2
extends gi_2 {
    public String b(tz_0 tz_02) {
        qw_0[] qw_0Array = tz_02.agv();
        if (qw_0Array != null && qw_0Array.length > 0) {
            return qw_0Array[0].getFileName();
        }
        return "?";
    }
}

