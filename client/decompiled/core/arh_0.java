/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;

/*
 * Renamed from arH
 */
public final class arh_0
implements Iterable {
    public static int cQk = 0;
    public static int cQl = 1;
    public static int cQm = 2;
    protected static final Logger a = Logger.getLogger(arh_0.class);
    private boolean cQn;
    private int[][] cQo;
    private static final agv_0 cQp = new agv_0();
    private static final agv_0 cQq = new agv_0();
    private static final agv_0 cQr = new agv_0();
    private static final agv_0 cQs = new agv_0();
    private static final agv_0 cQt = new agv_0();

    public arh_0() {
        this.cQn = false;
    }

    public arh_0(int n2) {
        this.cQo = new int[n2][3];
        this.cQn = true;
    }

    public arh_0(int[][] nArray) {
        this.cQo = nArray;
        this.cQn = true;
    }

    public arh_0(int[] nArray, List list) {
        this.cQo = new int[1 + list.size()][3];
        this.cQo[0][arh_0.cQk] = nArray[cQk];
        this.cQo[0][arh_0.cQl] = nArray[cQl];
        this.cQo[0][arh_0.cQm] = nArray[cQm];
        int n2 = list.size();
        for (int j = 0; j < n2; ++j) {
            int[] nArray2 = (int[])list.get(j);
            assert (nArray2.length == 3) : "Cellule de longueur invalide : " + nArray2.length;
            this.cQo[j + 1][arh_0.cQk] = nArray2[cQk];
            this.cQo[j + 1][arh_0.cQl] = nArray2[cQl];
            this.cQo[j + 1][arh_0.cQm] = nArray2[cQm];
        }
        this.cQn = true;
    }

    public arh_0(List list) {
        this.cQo = new int[list.size()][3];
        int n2 = list.size();
        for (int j = 0; j < n2; ++j) {
            int[] nArray = (int[])list.get(j);
            assert (nArray.length == 3) : "Cellule de longueur invalide : " + nArray.length;
            this.cQo[j][arh_0.cQk] = nArray[cQk];
            this.cQo[j][arh_0.cQl] = nArray[cQl];
            this.cQo[j][arh_0.cQm] = nArray[cQm];
        }
        this.cQn = true;
    }

    public arh_0(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        if (byteBuffer.remaining() > 0 && byteBuffer.remaining() < 65536) {
            this.cQo = new int[byteBuffer.remaining() / 10][3];
            int n2 = 0;
            while (byteBuffer.remaining() >= 10) {
                this.cQo[n2][arh_0.cQk] = byteBuffer.getInt();
                this.cQo[n2][arh_0.cQl] = byteBuffer.getInt();
                this.cQo[n2][arh_0.cQm] = byteBuffer.getShort();
                ++n2;
            }
            this.cQn = true;
        } else {
            a.error((Object)("PathFindResult s\u00e9rialis\u00e9 de longueur louche : " + byArray.length + " @ " + bl_0.dH()));
        }
    }

    public void b(int n2, int n3, int n4, short s) {
        this.cQo[n2][arh_0.cQk] = n3;
        this.cQo[n2][arh_0.cQl] = n4;
        this.cQo[n2][arh_0.cQm] = s;
    }

    public void c(int n2, int[] nArray) {
        this.cQo[n2] = nArray;
    }

    public void x(int n2, int n3, int n4, int n5) {
        ArrayList<int[]> arrayList = new ArrayList<int[]>(this.cQo.length);
        for (int j = 0; j < this.cQo.length; ++j) {
            int[] nArray = this.cQo[j];
            if (nArray[0] < n2 || nArray[0] > n4 || nArray[1] < n3 || nArray[1] > n5) continue;
            arrayList.add(nArray);
        }
        if (arrayList.size() < this.cQo.length) {
            if (!arrayList.isEmpty()) {
                int[][] nArray = new int[arrayList.size()][3];
                this.cQo = (int[][])arrayList.toArray((T[])nArray);
            } else {
                this.cQo = null;
                this.cQn = false;
            }
        }
    }

    public int aEF() {
        if (!this.cQn || this.cQo == null) {
            return 0;
        }
        return this.cQo.length;
    }

    public boolean aEG() {
        return this.cQn;
    }

    public int[] lU(int n2) {
        assert (n2 >= 0 && n2 < this.cQo.length) : "Trying to get a path step not within the bounds length = " + this.cQo.length + " stepIndex = " + n2;
        if (n2 < this.cQo.length) {
            return this.cQo[n2];
        }
        return null;
    }

    public int[] aEH() {
        if (this.cQo != null && this.cQo.length != 0) {
            return this.cQo[0];
        }
        return null;
    }

    public int[] aEI() {
        if (this.cQo != null && this.cQo.length != 0) {
            return this.cQo[this.cQo.length - 1];
        }
        return null;
    }

    public qc_0 lV(int n2) {
        if (!this.cQn || n2 < 0 || n2 >= this.aEF()) {
            return null;
        }
        int n3 = this.cQo[n2][cQk] - this.cQo[n2 - 1][cQk];
        int n4 = this.cQo[n2][cQl] - this.cQo[n2 - 1][cQl];
        return qc_0.aG(n3, n4);
    }

    public Iterator iterator() {
        if (this.cQo == null) {
            return new aHr();
        }
        return new dl_2((Object[])this.cQo, true);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("{ ");
        if (this.cQn) {
            for (int[] nArray : this.cQo) {
                stringBuffer.append("[").append(nArray[0]).append(";").append(nArray[1]).append(";").append(nArray[2]).append("] ");
            }
        } else {
            stringBuffer.append("not found");
        }
        return stringBuffer.append("}").toString();
    }

    public ArrayList d(long l2, boolean bl2) {
        ArrayList<awf_0> arrayList = new ArrayList<awf_0>();
        if (this.cQo.length < 2) {
            return null;
        }
        cQp.l(this.cQo[0]);
        cQq.l(this.cQo[1]);
        cQq.l(cQp);
        long l3 = 0L;
        int n2 = 1;
        awf_0 awf_02 = new awf_0();
        awf_0 awf_03 = null;
        awf_02.f(cQp);
        awf_02.eb(l3);
        do {
            cQr.l(this.cQo[n2]);
            cQs.j(cQr);
            cQs.l(cQp);
            if (cQs.getX() != cQq.getX() || cQs.getY() != cQq.getY() || bl2 && cQs.id() != cQq.id()) {
                awf_02.h(cQp);
                awf_02.ec(l3 += (long)awf_02.v().n(awf_02.u()).aSz() * l2);
                cQt.j(awf_02.v());
                cQt.l(awf_02.u());
                awf_02.g(cQt);
                if (awf_03 != null) {
                    awf_03.i(cQq);
                }
                arrayList.add(awf_02);
                cQq.j(cQs);
                awf_03 = awf_02;
                awf_02 = new awf_0();
                awf_02.f(cQp);
                awf_02.eb(l3);
            }
            cQp.j(cQr);
        } while (++n2 < this.cQo.length);
        awf_02.h(cQp);
        awf_02.ec(l3 += (long)awf_02.v().n(awf_02.u()).aSz() * l2);
        awf_02.g(awf_02.v().n(awf_02.u()));
        arrayList.add(awf_02);
        return arrayList;
    }

    public boolean B(int[] nArray) {
        if (!this.aEG()) {
            return false;
        }
        for (int[] nArray2 : this) {
            if (nArray2[cQk] != nArray[cQk] || nArray2[cQl] != nArray[cQl] || nArray2[cQm] != nArray[cQm]) continue;
            return true;
        }
        return false;
    }

    public arh_0 bS(int n2, int n3) {
        if (n2 < 0 || n2 >= n3 || n3 > this.aEF()) {
            throw new IllegalArgumentException("0 <= startIndex < endIndex <= getPathLength() non-respect\u00e9");
        }
        arh_0 arh_02 = new arh_0(n3 - n2);
        for (int j = n2; j < n3; ++j) {
            arh_02.c(j - n2, this.lU(j));
        }
        return arh_02;
    }

    public static pf_0 a(int[] nArray, arh_0 arh_02, arh_0 arh_03) {
        int n2;
        arh_0 arh_04;
        int n3;
        if (arh_02 == null) {
            throw new IllegalArgumentException("Argument 1 for @NotNull parameter of com/ankamagames/framework/ai/pathfinder/PathFindResult.fusionPaths must not be null");
        }
        if (arh_03 == null) {
            throw new IllegalArgumentException("Argument 2 for @NotNull parameter of com/ankamagames/framework/ai/pathfinder/PathFindResult.fusionPaths must not be null");
        }
        int[] nArray2 = arh_03.aEH();
        if (nArray2 == null) {
            a.error((Object)("Le point de d\u00e9part du 2e chemin est null lors d'une fusion de chemin: firstPath=" + arh_02 + ", secondPath=" + arh_03));
            return null;
        }
        int n4 = -1;
        int n5 = -1;
        for (n3 = 0; n3 < arh_02.aEF(); ++n3) {
            int[] nArray3 = arh_02.lU(n3);
            if (nArray3[cQk] == nArray[cQk] && nArray3[cQl] == nArray[cQl] && nArray3[cQm] == nArray[cQm] && n4 == -1) {
                n4 = n3;
                if (n5 != -1) break;
            }
            if (nArray3[cQk] != nArray2[cQk] || nArray3[cQl] != nArray2[cQl] || nArray3[cQm] != nArray2[cQm] || n5 != -1) continue;
            n5 = n3;
            if (n4 != -1) break;
        }
        if (n4 == -1) {
            a.error((Object)("La position de d\u00e9part " + nArray[cQk] + ":" + nArray[cQl] + ":" + nArray[cQm] + " n'a pas \u00e9t\u00e9 trouv\u00e9e sur le premier chemin. (path: " + arh_02 + ")"));
            return null;
        }
        if (n5 == -1) {
            a.error((Object)("Le point de jonction " + nArray2[cQk] + ":" + nArray2[cQl] + ":" + nArray2[cQm] + " n'a pas \u00e9t\u00e9 trouv\u00e9 sur le premier chemin. (path: " + arh_02 + ")"));
            return null;
        }
        n3 = 0;
        int n6 = 0;
        if (n4 <= n5) {
            arh_04 = new arh_0(n5 - n4 + arh_03.aEF());
            for (n2 = n4; n2 < n5; ++n2) {
                arh_04.c(n3++, arh_02.lU(n2));
            }
        } else {
            arh_04 = new arh_0(n4 - n5 + arh_03.aEF());
            for (n2 = n4; n2 > n5; --n2) {
                arh_04.c(n3++, arh_02.lU(n2));
            }
            n6 = n4 - n5;
        }
        for (n2 = 0; n2 < arh_03.aEF(); ++n2) {
            arh_04.c(n3++, arh_03.lU(n2));
        }
        return new pf_0(arh_04, n6);
    }

    public byte[] cd() {
        int n2 = this.aEF();
        ByteBuffer byteBuffer = ByteBuffer.allocate(n2 * 10);
        for (int j = 0; j < n2; ++j) {
            int[] nArray = this.lU(j);
            byteBuffer.putInt(nArray[cQk]);
            byteBuffer.putInt(nArray[cQl]);
            byteBuffer.putShort((short)nArray[cQm]);
        }
        return byteBuffer.array();
    }

    public List aEJ() {
        return Arrays.asList(this.cQo);
    }

    public boolean equals(Object object) {
        if (object == null || !(object instanceof arh_0)) {
            return false;
        }
        arh_0 arh_02 = (arh_0)object;
        if (arh_02.cQn != this.cQn || arh_02.cQo.length != this.cQo.length) {
            return false;
        }
        int n2 = this.cQo.length;
        for (int j = 0; j < n2; ++j) {
            int[] nArray = this.cQo[j];
            int[] nArray2 = arh_02.cQo[j];
            if (nArray.length == nArray2.length && nArray[cQk] == nArray2[cQk] && nArray[cQl] == nArray2[cQl] && nArray[cQm] == nArray2[cQm]) continue;
            return false;
        }
        return true;
    }
}

