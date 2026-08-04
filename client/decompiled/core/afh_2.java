/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from afh
 */
public class afh_2
extends gi_2 {
    long cqy = -1L;
    String cqz = null;

    public String b(tz_0 tz_02) {
        long l2 = tz_02.getTimeStamp();
        if (l2 == this.cqy) {
            return this.cqz;
        }
        this.cqy = l2;
        this.cqz = Long.toString(l2 - tz_0.getStartTime());
        return this.cqz;
    }
}

