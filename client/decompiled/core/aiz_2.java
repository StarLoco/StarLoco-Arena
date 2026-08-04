/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.Iterator;

/*
 * Renamed from aiZ
 */
public class aiz_2
implements Pi {
    public static final short czD = 1;
    public static final short czE = 11;
    public static final short czF = 2;
    public static final short czG = 12;
    public static final short czH = 3;
    public static final short czI = 13;
    public static final short czJ = 4;
    public static final short czK = 14;
    public static final short czL = 5;
    public static final short czM = 15;
    public static final short czN = 20;
    public static final short czO = 21;
    public static final short czP = 30;
    public static final short czQ = 40;
    public static final short czR = 50;
    public static final short czS = 60;
    public static final short czT = 61;
    public static final short czU = 62;
    public static final short czV = 63;
    public static final short czW = 70;
    public static final short czX = 100;
    protected short fL;
    protected byte czY;
    protected short Gp;
    protected akw_0[] czZ;
    protected final ArrayList iM;

    public aiz_2(short s, byte by, short s2, akw_0[] akw_0Array, ArrayList arrayList) {
        this.fL = s;
        this.czY = by;
        this.Gp = s2;
        this.czZ = akw_0Array;
        this.iM = arrayList;
    }

    public boolean ayT() {
        return this.Gp == 1 || this.Gp == 2 || this.Gp == 3 || this.Gp == 4 || this.Gp == 5;
    }

    public boolean ayU() {
        return this.Gp == 11 || this.Gp == 12 || this.Gp == 13 || this.Gp == 14 || this.Gp == 15;
    }

    public boolean ayV() {
        return this.ayT() || this.ayU();
    }

    public short tI() {
        return this.fL;
    }

    public byte ayW() {
        return this.czY;
    }

    public short getType() {
        return this.Gp;
    }

    public akw_0[] ayX() {
        return this.czZ;
    }

    public ArrayList eC() {
        return this.iM;
    }

    public int iP() {
        return 16;
    }

    public long iO() {
        return this.fL;
    }

    public Iterator iterator() {
        return this.iM.iterator();
    }
}

