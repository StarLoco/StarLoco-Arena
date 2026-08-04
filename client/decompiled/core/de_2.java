/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.GregorianCalendar;

/*
 * Renamed from DE
 */
public class de_2
implements aho_0 {
    public static final String aNX = "calendar";
    public static final String aNY = "currentMonth";
    public static final String[] ce = new String[]{"calendar", "currentMonth"};
    private static final de_2 aNZ = new de_2();
    protected final sv_1 aOa = new sv_1(iz_0.OU);
    private final GregorianCalendar aOb = new GregorianCalendar(aon_0.aYc().Fd().getLocale());
    private final tx_1 aOc = new tx_1();

    public static de_2 Mc() {
        return aNZ;
    }

    public de_2() {
        azs_0.aLV().g("calendar.eventFilter", this.aOc);
    }

    public ArrayList a(rd_1 rd_12, rd_1 rd_13) {
        ArrayList<sv_1> arrayList = new ArrayList<sv_1>();
        int n2 = rd_12.h(rd_13) + 1;
        arrayList.ensureCapacity(n2);
        for (int j = 0; j < n2; ++j) {
            arrayList.add(new sv_1(iz_0.OU));
        }
        arrayList.trimToSize();
        for (int j = 0; j < this.aOa.size(); ++j) {
            iz_0 iz_02 = (iz_0)this.aOa.get(j);
            boolean bl2 = true;
            do {
                if (iz_02.sz().f(rd_12) >= 0 && iz_02.sz().f(rd_13) < 0 || iz_02 instanceof th_2 && ((th_2)iz_02).agh().f(rd_13) < 0 && iz_02.sz().f(rd_13) >= 0) {
                    if (iz_02 instanceof th_2) {
                        rd_1 rd_14 = rd_12;
                        acx_1 acx_12 = rd_13;
                        if (((th_2)iz_02).agh().f(rd_12) >= 0) {
                            rd_14 = ((th_2)iz_02).agh();
                        }
                        if (iz_02.sz().f(rd_13) < 0) {
                            acx_12 = iz_02.sz();
                        }
                        for (int i2 = rd_12.h(rd_14); i2 <= rd_12.h(acx_12); ++i2) {
                            ((sv_1)arrayList.get(i2)).add(iz_02);
                        }
                    } else {
                        ((sv_1)arrayList.get(rd_12.h(iz_02.sz()))).add(iz_02);
                    }
                    bl2 = true;
                }
                if (iz_02.UF() == jx_0.blQ) continue;
                if (bl2) {
                    iz_02 = iz_02.nk();
                    bl2 = false;
                }
                if (iz_02 instanceof th_2) {
                    ((th_2)iz_02).agh().b(iz_02.UF());
                }
                iz_02.UC();
            } while (iz_02.UF() != jx_0.blQ && (iz_02.sz().f(iz_02.UE()) <= 0 && iz_02.sz().f(rd_13) < 0 || iz_02 instanceof th_2 && ((th_2)iz_02).agh().f(rd_13) < 0 && ((th_2)iz_02).agh().f(iz_02.UE()) < 0));
        }
        return arrayList;
    }

    public void clear() {
        this.aOa.clear();
    }

    public void a(iz_0 iz_02) {
        this.aOa.add(iz_02);
    }

    public iz_0 Md() {
        return (iz_0)this.aOa.getFirst();
    }

    public void b(iz_0 iz_02) {
        this.aOa.remove(iz_02);
    }

    public sv_1 Me() {
        return this.aOa;
    }

    public GregorianCalendar Mf() {
        return this.aOb;
    }

    public tx_1 Mg() {
        return this.aOc;
    }

    public String[] getFields() {
        return ce;
    }

    public Object getFieldValue(String string) {
        if (string.equals(aNX)) {
            this.aOb.set(5, 1);
            int n2 = this.aOb.get(7) - 1;
            if (this.aOb.getFirstDayOfWeek() == 2) {
                n2 = (n2 + 5) % 7 + 1;
            }
            n2 %= 7;
            int n3 = this.aOb.getActualMaximum(5);
            int n4 = this.aOb.get(2);
            int n5 = this.aOb.get(1);
            this.aOb.set(2, n4 - 1);
            int n6 = this.aOb.get(1);
            int n7 = this.aOb.getActualMaximum(5);
            int n8 = n7 - n2;
            ArrayList<avF> arrayList = new ArrayList<avF>();
            int n9 = (int)Math.ceil((double)(n2 + n3) / 7.0);
            if (n4 == 0) {
                n4 = 12;
            }
            rd_1 rd_12 = new rd_1(0, 0, 0, n8 + 1, n4, n6);
            ArrayList arrayList2 = this.a(rd_12, new rd_1(rd_12).a(59, 59, 23, n9 * 7 - 1, 0, 0));
            for (int j = 1; j <= n9 * 7; ++j) {
                int n10;
                if (j <= n2 && n2 != 0) {
                    n10 = (n8 + j) % (1 + n7);
                    arrayList.add(new avF((byte)n10, (byte)n4, (ArrayList)arrayList2.get(j - 1), false));
                    continue;
                }
                if (j <= n2 + n3) {
                    n10 = (j - n2) % (1 + n3);
                    arrayList.add(new avF((byte)n10, (byte)(n4 + 1), (ArrayList)arrayList2.get(j - 1), true));
                    continue;
                }
                n10 = (j - n2 - n3) % (1 + n7);
                arrayList.add(new avF((byte)n10, (byte)(n4 + 2), (ArrayList)arrayList2.get(j - 1), false));
            }
            this.aOb.set(2, this.aOb.get(2) + 1);
            return arrayList;
        }
        if (string.equals(aNY)) {
            return aon_0.aYc().getString("month" + this.aOb.get(2)) + " " + this.aOb.get(1);
        }
        return null;
    }

    public void a(String string, Object object) {
    }

    public void c(String string, Object object) {
    }

    public void b(String string, Object object) {
    }

    public boolean l(String string) {
        return false;
    }
}

