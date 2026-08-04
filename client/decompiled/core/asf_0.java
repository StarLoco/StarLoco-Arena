/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

/*
 * Renamed from asf
 */
public class asf_0 {
    private static final String aGd = "333333";

    public static String a(int n2, boolean bl2, Iterable iterable, Iterable iterable2, int n3) {
        rw_2 rw_22 = new rw_2();
        if (bl2) {
            Object object;
            rw_2 rw_23;
            if (iterable != null) {
                rw_23 = new rw_2();
                rw_2 rw_24 = new rw_2();
                boolean bl3 = true;
                boolean bl4 = true;
                for (Object[] objectArray : iterable) {
                    object = rw_23;
                    if (objectArray.di(1L)) {
                        object = rw_24;
                        if (!bl4) {
                            ((rw_2)object).bJ(", ");
                        } else {
                            bl4 = false;
                        }
                    } else if (!bl3) {
                        ((rw_2)object).bJ(", ");
                    } else {
                        bl3 = false;
                    }
                    int n4 = objectArray.Tb().length;
                    Object[] objectArray2 = new Object[n4];
                    for (int j = 0; j < n4; ++j) {
                        objectArray2[j] = (int)objectArray.Tb()[j];
                    }
                    String string = aon_0.aYc().a(22, objectArray.M(), objectArray2);
                    ((rw_2)object).bJ(string);
                    if (objectArray.aln() == null || objectArray.aln().length <= 0) continue;
                    if (objectArray.aln()[0] >= 63) {
                        ((rw_2)object).bJ(" ").bJ(aon_0.aYc().getString("cast.infiniteDuration"));
                        continue;
                    }
                    if (objectArray.aln()[0] <= 0) continue;
                    ((rw_2)object).bJ(" ").bJ(aon_0.aYc().getString("cast.durationDescription", objectArray.aln()[0]));
                }
                if (!bl3) {
                    rw_22.bJ(aon_0.aYc().getString("cast.effectDescription", rw_23.wR()));
                }
                if (!bl4) {
                    rw_22.bJ(aon_0.aYc().getString("cast.criticalEffectDescription", rw_24.wR()));
                }
            }
            if (iterable2 != null) {
                rw_23 = new rw_2();
                boolean bl5 = true;
                for (xj_0 xj_02 : iterable2) {
                    Object[] objectArray;
                    int n5 = xj_02.Tb().length;
                    objectArray = new Object[n5];
                    for (int j = 0; j < n5; ++j) {
                        objectArray[j] = (int)xj_02.Tb()[j];
                    }
                    object = aon_0.aYc().a(22, xj_02.M(), objectArray);
                    if (!bl5) {
                        rw_23.bJ(", ");
                    } else {
                        bl5 = false;
                    }
                    rw_23.bJ((String)object);
                }
                if (!bl5) {
                    rw_22.bJ(are_0.format(aon_0.aYc().getString("cast.bonusDescription"), rw_23.wR()));
                }
            }
        }
        if (aon_0.aYc().g(n3, n2)) {
            rw_22.bJ(aon_0.aYc().a(n3, n2, new Object[0]));
        }
        return rw_22.wR();
    }

    public static String a(int n2, boolean bl2, Iterable iterable, Iterable iterable2, int n3, int n4, boolean bl3, boolean bl4, byte by, byte by2, byte by3, int n5, int n6) {
        rw_2 rw_22 = new rw_2();
        if (bl2) {
            if (iterable != null) {
                rw_2 rw_23 = new rw_2();
                rw_2 rw_24 = new rw_2();
                boolean bl5 = true;
                boolean bl6 = true;
                boolean bl7 = false;
                for (xj_0 xj_02 : iterable) {
                    rw_2 rw_25 = rw_23;
                    if (xj_02.alo()) {
                        bl7 = true;
                    }
                    if (xj_02.di(1L)) {
                        rw_25 = rw_24;
                        if (!bl6) {
                            rw_25.bJ(", ");
                        } else {
                            bl6 = false;
                        }
                    } else if (!bl5) {
                        rw_25.bJ(", ");
                    } else {
                        bl5 = false;
                    }
                    int n7 = xj_02.Tb().length;
                    Object[] objectArray = new Object[n7];
                    for (int j = 0; j < n7; ++j) {
                        objectArray[j] = (int)xj_02.Tb()[j];
                    }
                    String string = aon_0.aYc().a(22, xj_02.M(), objectArray);
                    rw_25.bJ(string);
                    int[] nArray = xj_02.aln();
                    if (nArray == null || nArray.length <= 0) continue;
                    if (nArray[0] == 63) {
                        rw_25.bJ(aon_0.aYc().getString("cast.infiniteDuration", nArray[0]));
                        continue;
                    }
                    if (nArray[0] <= 0) continue;
                    rw_25.bJ(aon_0.aYc().getString("cast.durationDescription", nArray[0]));
                }
                if (!bl5) {
                    rw_22.bJ(aon_0.aYc().getString("cast.effectDescription", rw_23.wR()));
                }
                if (!bl6) {
                    rw_22.bJ(aon_0.aYc().getString("cast.criticalEffectDescription", rw_24.wR()));
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(aon_0.aYc().getString("cast.use"));
                boolean bl8 = false;
                if (by >= 63) {
                    stringBuilder.append(aon_0.aYc().getString("cast.useInfiniteInterval"));
                    bl8 = true;
                } else if (by > 0) {
                    stringBuilder.append(aon_0.aYc().getString("cast.useInterval", by));
                    bl8 = true;
                } else if (by3 > 0) {
                    stringBuilder.append(aon_0.aYc().getString("cast.useMaxPerTurn", by3));
                    bl8 = true;
                } else if (by2 > 0) {
                    stringBuilder.append(aon_0.aYc().getString("cast.useMaxPerTarget", by2));
                    bl8 = true;
                }
                if (bl7) {
                    if (bl8) {
                        stringBuilder.append(", ");
                    }
                    stringBuilder.append(aon_0.aYc().getString("cast.usePersonalEffect"));
                    bl8 = true;
                }
                if (bl8) {
                    rw_22.D(stringBuilder);
                }
            }
        } else if (aon_0.aYc().g(n5, n2)) {
            rw_22.bJ(aon_0.aYc().a(n5, n2, new Object[0]));
        }
        return rw_22.wR();
    }

    public static String c(xj_0 xj_02) {
        rw_2 rw_22 = new rw_2();
        int n2 = xj_02.Tb().length;
        Object[] objectArray = new Object[n2];
        for (int j = 0; j < n2; ++j) {
            objectArray[j] = (int)xj_02.Tb()[j];
        }
        String string = aon_0.aYc().a(22, xj_02.M(), objectArray);
        rw_22.bJ(string);
        return rw_22.wR();
    }

    public static String bU(int n2, int n3) {
        rw_2 rw_22 = new rw_2();
        rw_22.wI().bM(aGd);
        rw_22.bJ(aon_0.aYc().a(n3, n2, new Object[0])).wJ();
        return rw_22.wR();
    }

    public static String a(akw_0[] akw_0Array) {
        return asf_0.a(akw_0Array, false, true);
    }

    public static String b(akw_0[] akw_0Array) {
        return asf_0.a(akw_0Array, false, false);
    }

    public static String c(akw_0[] akw_0Array) {
        return asf_0.a(akw_0Array, true, false);
    }

    public static String a(akw_0[] akw_0Array, boolean bl2, boolean bl3) {
        int n2;
        rw_2 rw_22 = new rw_2();
        int n3 = n2 = bl2 ? 50 : 47;
        if (akw_0Array != null) {
            rw_2 rw_23 = new rw_2();
            boolean bl4 = true;
            for (int j = 0; j < akw_0Array.length; ++j) {
                if (!bl4) {
                    rw_23.bJ(", ");
                } else {
                    bl4 = false;
                }
                akw_0 akw_02 = akw_0Array[j];
                rw_23.bJ(asf_0.a(akw_02, n2));
                if (akw_02.getType() == AI.aHK.tI()) {
                    aiz_2 aiz_22 = bf_1.df().g((short)akw_02.rg()[0]);
                    rw_23.bJ(asf_0.b(aiz_22));
                }
                if (akw_02.aAl() == 0L) continue;
                rw_23.bJ(asf_0.b(akw_02));
            }
            if (!bl4) {
                if (!bl3) {
                    rw_22.bJ(are_0.format(aon_0.aYc().getString("effects"), rw_23.wR()));
                } else {
                    rw_22.bJ(rw_23.wR());
                }
            }
        }
        return rw_22.wR();
    }

    public static String b(aiz_2 aiz_22) {
        rw_2 rw_22 = new rw_2();
        akw_0[] akw_0Array = aiz_22.ayX();
        boolean bl2 = true;
        for (int j = 0; j < akw_0Array.length; ++j) {
            if (!bl2) {
                rw_22.bJ(", ");
            } else {
                bl2 = false;
            }
            akw_0 akw_02 = akw_0Array[j];
            rw_22.bJ(asf_0.a(akw_02, 47));
        }
        ArrayList arrayList = aiz_22.eC();
        for (int j = 0; j < arrayList.size(); ++j) {
            if (!bl2) {
                rw_22.bJ(", ");
            } else {
                bl2 = false;
            }
            xj_0 xj_02 = (xj_0)arrayList.get(j);
            rw_22.bJ(asf_0.c(xj_02));
        }
        return rw_22.wR();
    }

    public static String b(akw_0 akw_02) {
        rw_2 rw_22 = new rw_2();
        boolean bl2 = true;
        long l2 = akw_02.aAl();
        for (int j = 0; j < 64; ++j) {
            if ((l2 & 1L << j) == 0L) continue;
            if (!bl2) {
                rw_22.bJ(", ");
            } else {
                rw_22.bJ("(");
                bl2 = false;
            }
            rw_22.bJ(aon_0.aYc().a(63, j + 1, new Object[0]));
        }
        if (!bl2) {
            rw_22.bJ(")");
        }
        return rw_22.wR();
    }

    public static String a(akw_0 akw_02, int n2) {
        return aon_0.aYc().a(n2, akw_02.getType(), akw_02.aAk());
    }

    public static String b(aau_1 aau_12) {
        rw_2 rw_22 = new rw_2();
        if (aau_12.adW()) {
            rw_2 rw_23 = new rw_2();
            boolean bl2 = true;
            if (aau_12.aoW().size() > 0) {
                short[] sArray = aau_12.aoW().Gj();
                for (int j = 0; j < sArray.length; ++j) {
                    Object[] objectArray = new Object[]{aau_12.aoW().cp(sArray[j])};
                    String string = aon_0.aYc().a(48, sArray[j], objectArray);
                    if (!bl2) {
                        rw_23.bJ(", ");
                    } else {
                        bl2 = false;
                    }
                    rw_23.bJ(string);
                }
                if (!bl2) {
                    rw_22.bJ(rw_23.wR());
                }
            } else {
                jg_0 jg_02 = aau_12.adX();
                for (int j = 0; j < jg_02.size(); ++j) {
                    String string = aon_0.aYc().a(23, jg_02.get(j), new Object[0]);
                    if (!bl2) {
                        rw_23.bJ(", ");
                    } else {
                        bl2 = false;
                    }
                    rw_23.bJ(string);
                }
                if (!bl2) {
                    rw_22.bJ(aon_0.aYc().getString("tomeCondition", rw_23.wR()));
                }
            }
        } else if (aon_0.aYc().g(38, aau_12.tI())) {
            rw_22.bJ(aon_0.aYc().a(38, aau_12.tI(), new Object[0]));
        }
        return rw_22.wR();
    }

    public static String d(xj_0 xj_02) {
        int n2 = xj_02.M();
        if (n2 == mh_2.bwo.getId()) {
            return aon_0.aYc().getString("help.tackle");
        }
        if (n2 == mh_2.bwq.getId()) {
            return aon_0.aYc().getString("help.dodge");
        }
        if (n2 == mh_2.bvE.getId()) {
            return aon_0.aYc().getString("help.criticalHit");
        }
        if (n2 == mh_2.bvk.getId() || n2 == mh_2.bvl.getId()) {
            return aon_0.aYc().getString("help.earth");
        }
        if (n2 == mh_2.bvi.getId() || n2 == mh_2.bvj.getId()) {
            return aon_0.aYc().getString("help.fire");
        }
        if (n2 == mh_2.bvm.getId() || n2 == mh_2.bvn.getId()) {
            return aon_0.aYc().getString("help.water");
        }
        if (n2 == mh_2.bvo.getId() || n2 == mh_2.bvp.getId()) {
            return aon_0.aYc().getString("help.wind");
        }
        if (n2 == mh_2.bvQ.getId() || n2 == mh_2.bvR.getId()) {
            return aon_0.aYc().getString("help.dmg");
        }
        if (n2 == mh_2.buR.getId() || n2 == mh_2.buS.getId()) {
            return aon_0.aYc().getString("help.earthRes");
        }
        if (n2 == mh_2.buP.getId() || n2 == mh_2.buQ.getId()) {
            return aon_0.aYc().getString("help.fireRes");
        }
        if (n2 == mh_2.buT.getId() || n2 == mh_2.buU.getId()) {
            return aon_0.aYc().getString("help.waterRes");
        }
        if (n2 == mh_2.buV.getId() || n2 == mh_2.buW.getId()) {
            return aon_0.aYc().getString("help.windRes");
        }
        if (n2 == mh_2.bvO.getId() || n2 == mh_2.bvP.getId()) {
            return aon_0.aYc().getString("help.res");
        }
        if (n2 == mh_2.bvI.getId()) {
            return aon_0.aYc().getString("help.nbSummons");
        }
        if (n2 == mh_2.buz.getId()) {
            return aon_0.aYc().getString("help.fighterActionPoints");
        }
        if (n2 == mh_2.buD.getId()) {
            return aon_0.aYc().getString("help.movePoints");
        }
        if (n2 == mh_2.bux.getId()) {
            return aon_0.aYc().getString("help.healthPoints");
        }
        if (n2 == mh_2.bvG.getId()) {
            return aon_0.aYc().getString("help.fighterRange");
        }
        if (n2 == mh_2.bvX.getId()) {
            return aon_0.aYc().getString("help.damageRebound");
        }
        if (n2 == mh_2.bvM.getId()) {
            return aon_0.aYc().getString("help.healBonus");
        }
        if (n2 == mh_2.bwN.getId()) {
            return aon_0.aYc().getString("help.summonTackle");
        }
        if (n2 == mh_2.bwM.getId()) {
            return aon_0.aYc().getString("help.summonCC");
        }
        if (n2 == mh_2.bwK.getId()) {
            return aon_0.aYc().getString("help.summonDmg");
        }
        if (n2 == mh_2.bwO.getId()) {
            return aon_0.aYc().getString("help.summonHp");
        }
        if (n2 == mh_2.bwL.getId()) {
            return aon_0.aYc().getString("help.summonRes");
        }
        return null;
    }

    public static String c(np_1 ... np_1Array) {
        rw_2 rw_22 = new rw_2();
        boolean bl2 = true;
        for (int j = 0; j < np_1Array.length; ++j) {
            if (!bl2) {
                rw_22.bJ(", ");
            } else {
                bl2 = false;
            }
            np_1 np_12 = np_1Array[j];
            Object[] objectArray = new Object[np_12.rg().length];
            if (np_12 instanceof hv_1 || np_12 instanceof Zi) {
                objectArray[0] = ((ve_0)aca_0.aOq().E(np_12.rg()[0])).getName();
            } else if (np_12 instanceof gm_2 || np_12 instanceof alu_2) {
                objectArray[0] = ((yp_2)je_1.Wa().el(np_12.rg()[0])).getName();
            } else if (np_12 instanceof we_0) {
                objectArray[0] = aon_0.aYc().a(5, np_12.rg()[0], new Object[0]);
            } else if (np_12 instanceof al_0) {
                objectArray[0] = np_12.rg()[0];
                objectArray[1] = ((yp_2)je_1.Wa().el(np_12.rg()[1])).getName();
            } else {
                for (int i2 = 0; i2 < objectArray.length; ++i2) {
                    objectArray[i2] = np_12.rg()[i2];
                }
            }
            rw_22.bJ(aon_0.aYc().a(54, np_12.getType(), objectArray));
            if (np_12 instanceof agp) {
                rw_22.bJ(asf_0.c(((agp)np_12).awq()));
                continue;
            }
            if (!(np_12 instanceof wi_0)) continue;
            rw_22.bJ(asf_0.a(((wi_0)np_12).CD()));
        }
        return rw_22.wR();
    }

    public static String a(mp_2 ... mp_2Array) {
        rw_2 rw_22 = new rw_2();
        boolean bl2 = true;
        for (int j = 0; j < mp_2Array.length; ++j) {
            if (!bl2) {
                rw_22.bJ(", ");
            } else {
                bl2 = false;
            }
            mp_2 mp_22 = mp_2Array[j];
            Object[] objectArray = new Object[mp_22.rg().length];
            for (int i2 = 0; i2 < objectArray.length; ++i2) {
                objectArray[i2] = mp_22.rg()[i2];
            }
            rw_22.bJ(aon_0.aYc().a(55, mp_22.getType(), objectArray));
        }
        return rw_22.wR();
    }
}

