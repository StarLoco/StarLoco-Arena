/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.Collections;

public class bd {
    private static bd eB = new bd();
    private static final int eC = 100;
    private boolean eD = false;
    private ArrayList eE = new ArrayList(100);
    private ArrayList eF = new ArrayList(100);
    private ArrayList eG = new ArrayList(100);
    private ArrayList eH = new ArrayList(100);
    private ArrayList eI = new ArrayList(100);
    private ArrayList eJ = new ArrayList(100);
    private ArrayList eK = new ArrayList(100);
    private ArrayList eL = new ArrayList(100);
    private ArrayList eM = new ArrayList(100);
    private int eN = 4;
    public static final int eO = 0;
    public static final int eP = 1;
    public static final int eQ = 2;
    public static final int eR = 3;
    public static final int eS = 4;
    private int eT = 0;
    private boolean eU = false;

    private bd() {
    }

    public static bd ce() {
        return eB;
    }

    public void cf() {
        this.eD = true;
    }

    public boolean cg() {
        return this.eN != 4;
    }

    private void a(air_1 air_12, ArrayList arrayList, ArrayList arrayList2, int n2) {
        if (!air_12.isUnloading() && !air_12.isInTreeDepthManager()) {
            if (this.eN == n2) {
                if (!arrayList.contains(air_12)) {
                    arrayList.add(air_12);
                }
            } else if (!arrayList2.contains(air_12)) {
                arrayList2.add(air_12);
            }
        }
    }

    private void a(air_1 air_12, ArrayList arrayList) {
        if (!(air_12.isUnloading() || air_12.isATemplate() || air_12.isAddedNextInTreeDepthManager() || arrayList.contains(air_12))) {
            arrayList.add(air_12);
        }
    }

    public void b(air_1 air_12) {
        this.a(air_12, this.eF);
    }

    public void c(air_1 air_12) {
        this.a(air_12, this.eI);
    }

    public void d(air_1 air_12) {
        this.a(air_12, this.eL);
    }

    public void e(air_1 air_12) {
        if (!air_12.isATemplate()) {
            if (this.eN == 4 || this.eN <= 1 && air_12.getLastPreProcessFrame() != this.eT) {
                this.a(air_12, this.eG, this.eE, 1);
            } else {
                this.b(air_12);
            }
        }
    }

    public void f(air_1 air_12) {
        if (!air_12.isATemplate()) {
            if (this.eN == 4 || this.eN <= 2 && air_12.getLastMiddleProcessFrame() != this.eT) {
                this.a(air_12, this.eJ, this.eH, 2);
            } else {
                this.c(air_12);
            }
        }
    }

    public void g(air_1 air_12) {
        if (!air_12.isATemplate()) {
            if (this.eN == 4 || this.eN <= 3 && air_12.getLastPostProcessFrame() != this.eT) {
                this.a(air_12, this.eM, this.eK, 3);
            } else {
                this.d(air_12);
            }
        }
    }

    private int a(na_1 na_12, int n2) {
        na_12.setTreePosition(n2);
        if (na_12.uA != null) {
            int n3 = na_12.uA.size();
            for (int j = 0; j < n3; ++j) {
                n2 = this.a((na_1)na_12.uA.get(j), n2 + 1);
            }
        }
        return n2;
    }

    public void ch() {
        this.eU = true;
    }

    public void v(int n2) {
        this.eU = true;
        while (this.eU) {
            int n3;
            int n4;
            this.eU = false;
            this.eN = 0;
            this.eT = (this.eT + 1) % Integer.MAX_VALUE;
            if (this.eD) {
                this.a(add_1.aOG().aON().getMasterRootContainer(), 0);
                this.eD = false;
            }
            agV.awJ();
            this.eN = 1;
            while (this.eE.size() != 0) {
                n4 = this.eE.size();
                Collections.sort(this.eE, lm_2.GU);
                for (n3 = 0; n3 < n4; ++n3) {
                    air_1 air_12 = (air_1)this.eE.get(n3);
                    air_12.setLastPreProcessFrame(this.eT);
                    air_12.kW(n2);
                }
                this.eE.clear();
                n3 = this.eG.size();
                for (int j = 0; j < n3; ++j) {
                    this.eE.add(this.eG.get(j));
                }
                this.eG.clear();
            }
            this.eN = 2;
            while (this.eH.size() != 0) {
                n4 = this.eH.size();
                Collections.sort(this.eH, or_2.aaU);
                for (n3 = 0; n3 < n4; ++n3) {
                    air_1 air_13 = (air_1)this.eH.get(n3);
                    air_13.setLastMiddleProcessFrame(this.eT);
                    air_13.kX(n2);
                }
                this.eH.clear();
                n3 = this.eJ.size();
                for (int j = 0; j < n3; ++j) {
                    this.eH.add(this.eJ.get(j));
                }
                this.eJ.clear();
            }
            this.eN = 3;
            while (this.eK.size() != 0) {
                n4 = this.eK.size();
                Collections.sort(this.eK, or_2.aaU);
                for (n3 = 0; n3 < n4; ++n3) {
                    air_1 air_14 = (air_1)this.eK.get(n3);
                    air_14.setLastPostProcessFrame(this.eT);
                    air_14.kY(n2);
                }
                this.eK.clear();
                n3 = this.eM.size();
                for (int j = 0; j < n3; ++j) {
                    this.eK.add(this.eM.get(j));
                }
                this.eM.clear();
            }
            this.eN = 4;
            n4 = this.eF.size();
            for (n3 = 0; n3 < n4; ++n3) {
                air_1 air_15 = (air_1)this.eF.get(n3);
                this.eE.add(air_15);
            }
            this.eF.clear();
            n4 = this.eI.size();
            for (n3 = 0; n3 < n4; ++n3) {
                air_1 air_16 = (air_1)this.eI.get(n3);
                this.eH.add(air_16);
            }
            this.eI.clear();
            n4 = this.eL.size();
            for (n3 = 0; n3 < n4; ++n3) {
                air_1 air_17 = (air_1)this.eL.get(n3);
                this.eK.add(air_17);
            }
            this.eL.clear();
        }
    }
}

