/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from bF
 */
public class bf_1 {
    protected static final Logger a = Logger.getLogger(bf_1.class);
    public static final bf_1 ha = new bf_1();
    private final zm_1 hb = new zm_1();
    private final zm_1 hc = new zm_1();

    public static bf_1 df() {
        return ha;
    }

    public aiz_2 g(short s) {
        return (aiz_2)this.hb.an(s);
    }

    public void a(aiz_2 aiz_22) {
        this.hb.b(aiz_22.tI(), aiz_22);
        mm_0 mm_02 = (mm_0)this.hc.an(aiz_22.getType());
        if (mm_02 == null) {
            mm_02 = new mm_0();
            this.hc.b(aiz_22.getType(), mm_02);
        }
        mm_02.add(aiz_22.tI());
    }

    public aiz_2 b(et_2 et_22) {
        int n2;
        vy_1 vy_12 = et_22.kh();
        boolean bl2 = false;
        boolean bl3 = false;
        boolean bl4 = false;
        boolean bl5 = false;
        int n3 = ((mm_0)this.hc.an((short)1)).size() + ((mm_0)this.hc.an((short)2)).size() + ((mm_0)this.hc.an((short)4)).size() + ((mm_0)this.hc.an((short)3)).size();
        short[] sArray = vy_12.Gj();
        mm_0 mm_02 = new mm_0();
        int n4 = 0;
        block12: for (n2 = 0; n2 < sArray.length; ++n2) {
            switch (bf_1.df().g(sArray[n2]).getType()) {
                case 1: {
                    mm_02.add(sArray[n2]);
                }
                case 11: {
                    bl2 = true;
                    n3 -= ((mm_0)this.hc.an((short)1)).size();
                    ++n4;
                    continue block12;
                }
                case 2: {
                    mm_02.add(sArray[n2]);
                }
                case 12: {
                    bl3 = true;
                    n3 -= ((mm_0)this.hc.an((short)2)).size();
                    ++n4;
                    continue block12;
                }
                case 3: {
                    mm_02.add(sArray[n2]);
                }
                case 13: {
                    bl4 = true;
                    n3 -= ((mm_0)this.hc.an((short)3)).size();
                    ++n4;
                    continue block12;
                }
                case 4: {
                    mm_02.add(sArray[n2]);
                }
                case 14: {
                    bl5 = true;
                    n3 -= ((mm_0)this.hc.an((short)4)).size();
                    ++n4;
                    continue block12;
                }
                case 5: {
                    mm_02.add(sArray[n2]);
                }
                case 15: {
                    ++n4;
                }
            }
        }
        n2 = 0;
        if (mm_02.size() >= 3 || n4 == 5 || jr_0.VF().nextInt(100) + 1 <= mm_02.size() * mm_02.size() * 10) {
            n2 = 1;
        }
        if (n2 != 0 && n4 - mm_02.size() >= 3) {
            et_22.V((byte)2);
            return null;
        }
        if (n2 != 0) {
            return this.a(et_22, mm_02);
        }
        int n5 = jr_0.VF().nextInt(n3);
        short s = 0;
        if (!bl2) {
            if (n5 < ((mm_0)this.hc.an((short)1)).size()) {
                s = ((mm_0)this.hc.an((short)1)).get(n5);
            } else {
                n5 -= ((mm_0)this.hc.an((short)1)).size();
            }
        }
        if (s == 0 && !bl3) {
            if (n5 < ((mm_0)this.hc.an((short)2)).size()) {
                s = ((mm_0)this.hc.an((short)2)).get(n5);
            } else {
                n5 -= ((mm_0)this.hc.an((short)2)).size();
            }
        }
        if (s == 0 && !bl4) {
            if (n5 < ((mm_0)this.hc.an((short)3)).size()) {
                s = ((mm_0)this.hc.an((short)3)).get(n5);
            } else {
                n5 -= ((mm_0)this.hc.an((short)3)).size();
            }
        }
        if (s == 0 && !bl5) {
            if (n5 < ((mm_0)this.hc.an((short)4)).size()) {
                s = ((mm_0)this.hc.an((short)4)).get(n5);
            } else {
                n5 -= ((mm_0)this.hc.an((short)4)).size();
            }
        }
        if (s == 0) {
            if (n5 < ((mm_0)this.hc.an((short)5)).size()) {
                s = ((mm_0)this.hc.an((short)5)).get(n5);
            } else {
                n5 -= ((mm_0)this.hc.an((short)5)).size();
            }
        }
        aiz_2 aiz_22 = bf_1.df().g(s);
        et_22.kh().b(s, aiz_22.ayW());
        et_22.NF().jV(s);
        return aiz_22;
    }

    public aiz_2 a(et_2 et_22, mm_0 mm_02) {
        int n2 = jr_0.VF().nextInt(mm_02.size());
        aiz_2 aiz_22 = bf_1.df().g(mm_02.get(n2));
        et_22.kh().bq(aiz_22.tI());
        short s = 0;
        switch (aiz_22.getType()) {
            case 1: {
                s = ((mm_0)this.hc.an((short)11)).get(jr_0.VF().nextInt(((mm_0)this.hc.an((short)11)).size()));
                break;
            }
            case 2: {
                s = ((mm_0)this.hc.an((short)12)).get(jr_0.VF().nextInt(((mm_0)this.hc.an((short)12)).size()));
                break;
            }
            case 3: {
                s = ((mm_0)this.hc.an((short)13)).get(jr_0.VF().nextInt(((mm_0)this.hc.an((short)13)).size()));
                break;
            }
            case 4: {
                s = ((mm_0)this.hc.an((short)14)).get(jr_0.VF().nextInt(((mm_0)this.hc.an((short)14)).size()));
                break;
            }
            case 5: {
                s = ((mm_0)this.hc.an((short)15)).get(jr_0.VF().nextInt(((mm_0)this.hc.an((short)15)).size()));
            }
        }
        aiz_2 aiz_23 = bf_1.df().g(s);
        et_22.kh().b(s, aiz_23.ayW());
        et_22.NF().jV(s);
        return aiz_23;
    }
}

