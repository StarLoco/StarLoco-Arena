/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.List;
import org.apache.log4j.Logger;

/*
 * Renamed from Rz
 */
public class rz_1 {
    protected static final Logger a = Logger.getLogger(rz_1.class);
    protected boolean[][] bJk;
    protected int bJl;
    protected int bJm;
    protected int bJn;
    protected int bJo;
    protected boolean bJp;
    static final char bJq = 'X';
    static final char bJr = '-';
    static final char bJs = ' ';

    protected rz_1() {
    }

    protected rz_1(int n2) {
        this.hx(n2);
    }

    protected rz_1(int n2, int n3) {
        this.aQ(n2, n3);
    }

    protected rz_1(boolean[][] blArray) {
        this.bJl = blArray.length;
        if (this.bJl > 0) {
            this.bJm = blArray[0].length;
            this.bJk = blArray;
        }
    }

    public rz_1(int n2, int n3, List list) {
        this.aQ(n2, n3);
        this.j(list);
    }

    protected void hx(int n2) {
        this.bJl = 2 * n2 + 1;
        this.bJm = 2 * n2 + 1;
        this.bJn = n2;
        this.bJo = n2;
        this.bJk = new boolean[this.bJl][this.bJm];
    }

    protected void aQ(int n2, int n3) {
        this.bJl = 2 * n2 + 1;
        this.bJm = 2 * n3 + 1;
        this.bJn = n2;
        this.bJo = n3;
        this.bJk = new boolean[this.bJl][this.bJm];
    }

    public void a(rz_1 rz_12) {
        this.bJk = (boolean[][])rz_12.bJk.clone();
        for (int j = 0; j < this.bJl; ++j) {
            this.bJk[j] = (boolean[])rz_12.bJk[j].clone();
        }
        this.bJl = rz_12.bJl;
        this.bJm = rz_12.bJm;
        this.bJn = rz_12.bJn;
        this.bJo = rz_12.bJo;
        this.bJp = rz_12.bJp;
    }

    public int aea() {
        return this.bJn;
    }

    public int aeb() {
        return this.bJo;
    }

    public void aec() {
        this.bJn = this.bJl / 2;
        this.bJo = this.bJm / 2;
    }

    public void aR(int n2, int n3) {
        this.bJn = n2;
        this.bJo = n3;
    }

    public boolean aS(int n2, int n3) {
        return this.bJk[n2][n3];
    }

    public void a(boolean[][] blArray) {
        this.bJk = blArray;
        this.bJp = false;
    }

    public void j(List list) {
        for (int[] nArray : list) {
            this.bJk[nArray[0] + this.bJl][nArray[1] + this.bJm] = true;
        }
    }

    public int aed() {
        return this.bJl;
    }

    public int aee() {
        return this.bJm;
    }

    public void a(int n2, int n3, ol_0 ol_02, ol_0 ol_03) {
        block44: {
            block46: {
                float f;
                boolean[] blArray;
                int n4;
                block45: {
                    boolean[][] blArray2 = null;
                    n4 = 0;
                    if (n2 != this.bJl) {
                        float f2 = (float)this.bJl / (float)n2;
                        blArray2 = this.bJk;
                        this.bJk = new boolean[n2][];
                        if (n2 < this.bJl) {
                            switch (ol_02) {
                                case aaK: 
                                case aaL: 
                                case aaM: {
                                    for (n4 = 0; n4 < n2; ++n4) {
                                        this.bJk[n4] = blArray2[n4];
                                    }
                                    break;
                                }
                                case aaN: {
                                    for (n4 = 0; n4 < n2; ++n4) {
                                        this.bJk[n4] = blArray2[Math.round((float)n4 * f2)];
                                    }
                                    break;
                                }
                            }
                        } else {
                            switch (ol_02) {
                                case aaK: {
                                    for (n4 = 0; n4 < this.bJl; ++n4) {
                                        this.bJk[n4] = blArray2[n4];
                                    }
                                    for (n4 = this.bJl; n4 < n2; ++n4) {
                                        this.bJk[n4] = new boolean[n3];
                                    }
                                    break;
                                }
                                case aaL: {
                                    for (n4 = 0; n4 < n2; ++n4) {
                                        this.bJk[n4] = blArray2[n4 % this.bJl];
                                    }
                                    break;
                                }
                                case aaN: {
                                    for (n4 = 0; n4 < n2; ++n4) {
                                        this.bJk[n4] = blArray2[(int)((float)n4 * f2)];
                                    }
                                    break;
                                }
                                case aaM: {
                                    for (n4 = 0; n4 < n2; ++n4) {
                                        this.bJk[n4] = n4 / this.bJl % 2 == 0 ? blArray2[n4 % this.bJl] : blArray2[this.bJl - n4 % this.bJl - 1];
                                    }
                                    break;
                                }
                            }
                        }
                        this.bJl = n2;
                    }
                    if (n3 == this.bJm) break block44;
                    blArray = null;
                    f = (float)this.bJm / (float)n3;
                    if (n3 >= this.bJm) break block45;
                    switch (ol_03) {
                        case aaK: 
                        case aaL: 
                        case aaM: {
                            for (int j = 0; j < this.bJl; ++j) {
                                blArray = this.bJk[j];
                                this.bJk[j] = new boolean[n3];
                                for (n4 = 0; n4 < n3; ++n4) {
                                    this.bJk[j][n4] = blArray[n4];
                                }
                            }
                            break block46;
                        }
                        case aaN: {
                            for (int j = 0; j < this.bJl; ++j) {
                                blArray = this.bJk[j];
                                this.bJk[j] = new boolean[n3];
                                for (n4 = 0; n4 < n3; ++n4) {
                                    this.bJk[j][n4] = blArray[(int)((float)n4 * f)];
                                }
                            }
                            break;
                        }
                    }
                    break block46;
                }
                switch (ol_03) {
                    case aaK: {
                        for (int j = 0; j < this.bJl; ++j) {
                            blArray = this.bJk[j];
                            this.bJk[j] = new boolean[n3];
                            for (n4 = 0; n4 < n3; ++n4) {
                                this.bJk[j][n4] = blArray[n4];
                            }
                        }
                        break;
                    }
                    case aaL: {
                        for (int j = 0; j < this.bJl; ++j) {
                            blArray = this.bJk[j];
                            this.bJk[j] = new boolean[n3];
                            for (n4 = 0; n4 < n3; ++n4) {
                                this.bJk[j][n4] = blArray[n4 % this.bJm];
                            }
                        }
                        break;
                    }
                    case aaN: {
                        for (int j = 0; j < this.bJl; ++j) {
                            blArray = this.bJk[j];
                            this.bJk[j] = new boolean[n3];
                            try {
                                for (n4 = 0; n4 < n3; ++n4) {
                                    this.bJk[j][n4] = blArray[(int)((float)n4 * f)];
                                }
                                continue;
                            }
                            catch (Exception exception) {
                                a.info((Object)((int)((float)n4 * f)));
                            }
                        }
                        break;
                    }
                    case aaM: {
                        for (int j = 0; j < this.bJl; ++j) {
                            blArray = this.bJk[j];
                            this.bJk[j] = new boolean[n3];
                            for (n4 = 0; n4 < n3; ++n4) {
                                this.bJk[j][n4] = n4 / this.bJl % 2 == 0 ? blArray[n4 % this.bJm] : blArray[this.bJm - n4 % this.bJm - 1];
                            }
                        }
                        break;
                    }
                }
            }
            this.bJm = n3;
        }
        this.bJp = false;
    }

    public void fu() {
        if (!this.bJp) {
            int n2;
            int n3;
            int n4;
            this.bJp = true;
            int n5 = 0;
            int n6 = this.bJl;
            int n7 = 0;
            int n8 = this.bJm;
            boolean bl2 = false;
            for (n4 = 0; n4 < this.bJl; ++n4) {
            }
            for (n4 = 0; n4 < this.bJl; ++n4) {
                bl2 = true;
                for (n3 = 0; n3 < this.bJm; ++n3) {
                    if (!this.bJk[n4][n3]) continue;
                    bl2 = false;
                }
            }
            this.bJn += n5;
            this.bJo += n7;
            n3 = n6 - n5;
            int n9 = n8 - n7;
            boolean[][] blArray = this.bJk;
            for (n2 = 0; n2 < this.bJl; ++n2) {
                blArray[n2] = this.bJk[n2];
            }
            this.bJl = n3;
            this.bJm = n9;
            this.bJk = new boolean[this.bJl][];
            for (n2 = 0; n2 < this.bJl; ++n2) {
                this.bJk[n2] = new boolean[this.bJm];
                for (int j = 0; j < this.bJm; ++j) {
                    this.bJk[n2][j] = blArray[n2 + n5][j + n7];
                }
            }
        }
    }

    public void a(rz_1 rz_12, int n2, int n3) {
        int n4 = 0;
        int n5 = 0;
        n2 += this.bJn - rz_12.bJn;
        n3 += this.bJo - rz_12.bJo;
        for (int j = 0; j < rz_12.bJl; ++j) {
            n4 = j + n2;
            if (n4 < 0 || n4 >= this.bJl) continue;
            for (int i2 = 0; i2 < rz_12.bJm; ++i2) {
                n5 = i2 + n3;
                if (n5 < 0 || n5 >= this.bJm) continue;
                boolean[] blArray = this.bJk[n4];
                int n6 = n5;
                blArray[n6] = blArray[n6] | rz_12.bJk[j][i2];
            }
        }
    }

    public void b(rz_1 rz_12) {
        this.a(rz_12, 0, 0);
    }

    public void b(rz_1 rz_12, int n2, int n3) {
        int n4 = 0;
        int n5 = 0;
        n2 += this.bJn - rz_12.bJn;
        n3 += this.bJo - rz_12.bJo;
        for (int j = 0; j < rz_12.bJl; ++j) {
            n4 = j + n2;
            if (n4 < 0 || n4 >= this.bJl) continue;
            for (int i2 = 0; i2 < rz_12.bJm; ++i2) {
                n5 = i2 + n3;
                if (n5 < 0 || n5 >= this.bJm) continue;
                boolean[] blArray = this.bJk[n4];
                int n6 = n5;
                blArray[n6] = blArray[n6] | rz_12.bJk[j][i2];
            }
        }
    }

    public void c(rz_1 rz_12) {
        this.b(rz_12, 0, 0);
    }

    public void c(rz_1 rz_12, int n2, int n3) {
        int n4 = 0;
        int n5 = 0;
        n2 += this.bJn - rz_12.bJn;
        n3 += this.bJo - rz_12.bJo;
        for (int j = 0; j < rz_12.bJl; ++j) {
            n4 = j + n2;
            if (n4 < 0 || n4 >= this.bJl) continue;
            for (int i2 = 0; i2 < rz_12.bJm; ++i2) {
                n5 = i2 + n3;
                if (n5 < 0 || n5 >= this.bJm) continue;
                boolean[] blArray = this.bJk[n4];
                int n6 = n5;
                blArray[n6] = blArray[n6] & rz_12.bJk[j][i2];
            }
        }
        this.bJp = false;
    }

    public void d(rz_1 rz_12) {
        this.c(rz_12, 0, 0);
    }

    public void d(rz_1 rz_12, int n2, int n3) {
        int n4 = 0;
        int n5 = 0;
        n2 += this.bJn - rz_12.bJn;
        n3 += this.bJo - rz_12.bJo;
        for (int j = 0; j < rz_12.bJl; ++j) {
            n4 = j + n2;
            if (n4 < 0 || n4 >= this.bJl) continue;
            for (int i2 = 0; i2 < rz_12.bJm; ++i2) {
                n5 = i2 + n3;
                if (n5 < 0 || n5 >= this.bJm) continue;
                boolean[] blArray = this.bJk[n4];
                int n6 = n5;
                blArray[n6] = blArray[n6] & !rz_12.bJk[j][i2];
            }
        }
        this.bJp = false;
    }

    public void e(rz_1 rz_12) {
        this.d(rz_12, 0, 0);
    }

    public void invert() {
        for (int j = 0; j < this.bJl; ++j) {
            for (int i2 = 0; i2 < this.bJm; ++i2) {
                this.bJk[j][i2] = !this.bJk[j][i2];
            }
        }
    }

    public void clear() {
        for (int j = 0; j < this.bJl; ++j) {
            for (int i2 = 0; i2 < this.bJm; ++i2) {
                this.bJk[j][i2] = false;
            }
        }
        this.bJp = false;
    }

    public void aef() {
        this.bJn = this.bJl - this.bJn - 1;
        for (int j = 0; j < this.bJl / 2; ++j) {
            boolean[] blArray = this.bJk[j];
            this.bJk[j] = this.bJk[this.bJl - j - 1];
            this.bJk[this.bJl - j - 1] = blArray;
        }
    }

    public void aeg() {
        this.bJo = this.bJm - this.bJo - 1;
        for (int j = 0; j < this.bJl; ++j) {
            for (int i2 = 0; i2 < this.bJm / 2; ++i2) {
                boolean bl2 = this.bJk[j][i2];
                this.bJk[j][i2] = this.bJk[j][this.bJm - 1 - i2];
                this.bJk[j][this.bJm - 1 - i2] = bl2;
            }
        }
    }

    public void aeh() {
        this.aef();
        this.aeg();
    }

    public void aei() {
        boolean[][] blArray = this.bJk;
        int n2 = this.bJl;
        this.bJl = this.bJm;
        this.bJm = n2;
        int n3 = this.bJm - 1;
        n2 = this.bJn;
        this.bJn = this.bJo;
        this.bJo = n3 - n2;
        this.bJk = new boolean[this.bJl][];
        for (int j = 0; j < this.bJl; ++j) {
            this.bJk[j] = new boolean[this.bJm];
            for (int i2 = 0; i2 < this.bJm; ++i2) {
                this.bJk[j][i2] = blArray[n3 - i2][j];
            }
        }
    }

    public void aej() {
        this.aeh();
    }

    public void aek() {
        boolean[][] blArray = this.bJk;
        int n2 = this.bJl;
        this.bJl = this.bJm;
        this.bJm = n2;
        int n3 = this.bJl - 1;
        n2 = this.bJn;
        this.bJn = n3 - this.bJo;
        this.bJo = n2;
        this.bJk = new boolean[this.bJl][];
        for (int j = 0; j < this.bJl; ++j) {
            this.bJk[j] = new boolean[this.bJm];
            for (int i2 = 0; i2 < this.bJm; ++i2) {
                this.bJk[j][i2] = blArray[i2][n3 - j];
            }
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        int n2 = this.bJn < 0 ? this.bJn : 0;
        int n3 = this.bJn >= this.bJl ? this.bJn + 1 : this.bJl;
        int n4 = this.bJo < 0 ? this.bJo : 0;
        int n5 = this.bJo >= this.bJm ? this.bJo + 1 : this.bJm;
        for (int j = n4; j < n5; ++j) {
            for (int i2 = n2; i2 < n3; ++i2) {
                if (i2 == this.bJn && j == this.bJo) {
                    stringBuilder.append("(");
                    if (i2 < 0 || i2 >= this.bJl || j < 0 || j >= this.bJm) {
                        stringBuilder.append(' ');
                    } else {
                        stringBuilder.append(this.bJk[i2][j] ? (char)'X' : '-');
                    }
                    stringBuilder.append(")");
                    continue;
                }
                stringBuilder.append(" ");
                if (i2 < 0 || i2 >= this.bJl || j < 0 || j >= this.bJm) {
                    stringBuilder.append(' ');
                } else {
                    stringBuilder.append(this.bJk[i2][j] ? (char)'X' : '-');
                }
                stringBuilder.append(" ");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}

