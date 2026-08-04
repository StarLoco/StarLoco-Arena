/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import com.ankamagames.baseImpl.graphics.alea.display.ScreenElement;
import java.io.FileOutputStream;
import java.util.ArrayList;
import org.apache.log4j.Logger;

public class kC {
    private static final Logger a = Logger.getLogger(kC.class);
    public static final int vI = 1024;
    public static final int EH = 576;
    private static final qa_2 EI = new qa_2(2048);
    private static final ArrayList EJ = new ArrayList();
    ArrayList EK;
    short EL;
    short EM;
    int EN;
    int EO;
    int EP;
    int EQ;
    private int ER = Integer.MAX_VALUE;
    private int ES = Integer.MAX_VALUE;
    private short ET = Short.MAX_VALUE;
    private int EU = Integer.MIN_VALUE;
    private int EV = Integer.MIN_VALUE;
    private short EW = Short.MIN_VALUE;

    public kC() {
        this.EK = new ArrayList(1024);
        this.EN = Integer.MAX_VALUE;
        this.EP = Integer.MIN_VALUE;
        this.EO = Integer.MAX_VALUE;
        this.EQ = Integer.MIN_VALUE;
    }

    public kC(short s, short s2) {
        this.EK = new ArrayList(1024);
        this.EL = s;
        this.EM = s2;
        this.EN = Integer.MAX_VALUE;
        this.EP = Integer.MIN_VALUE;
        this.EO = Integer.MAX_VALUE;
        this.EQ = Integer.MIN_VALUE;
    }

    public final void clear() {
        ahn_0.dNL.b(this);
        int n2 = this.EK.size();
        for (int j = 0; j < n2; ++j) {
            ScreenElement screenElement = (ScreenElement)this.EK.get(j);
            screenElement.HF();
        }
        this.EK.clear();
    }

    public final int q(byte by) {
        int n2 = 0;
        int n3 = this.EK.size();
        for (int j = 0; j < n3; ++j) {
            ScreenElement screenElement = (ScreenElement)this.EK.get(j);
            byte by2 = screenElement.avY().aoq();
            if ((by2 & by) != by2) continue;
            ++n2;
        }
        return n2;
    }

    public final ArrayList ph() {
        return this.EK;
    }

    public final short pi() {
        return this.EL;
    }

    public final short pj() {
        return this.EM;
    }

    public void load(String string) {
        int n2;
        int n3;
        int n4;
        String string2 = this.aN(string);
        acf acf2 = acf.T(vq_2.readFile(string2));
        this.ER = acf2.readInt();
        this.ES = acf2.readInt();
        this.ET = acf2.readShort();
        this.EU = acf2.readInt();
        this.EV = acf2.readInt();
        this.EW = acf2.readShort();
        int n5 = acf2.readInt();
        int n6 = acf2.readInt();
        int n7 = acf2.readShort() & 0xFFFF;
        for (n4 = 0; n4 < n7; ++n4) {
            n3 = n5 + (acf2.readByte() & 0xFF);
            int n8 = n5 + (acf2.readByte() & 0xFF);
            int n9 = n6 + (acf2.readByte() & 0xFF);
            int n10 = n6 + (acf2.readByte() & 0xFF);
            for (n2 = n3; n2 < n8; ++n2) {
                for (int j = n9; j < n10; ++j) {
                    int n11 = acf2.readByte() & 0xFF;
                    for (int i2 = 0; i2 < n11; ++i2) {
                        byte by = acf2.readByte();
                        ScreenElement screenElement = cv_0.m(by);
                        screenElement.ctp = n2;
                        screenElement.ctq = j;
                        screenElement.b(acf2);
                        zl_1 zl_12 = screenElement.ctr;
                        screenElement.NS = anx_0.bB(n2, j) - zl_12.aog();
                        screenElement.NQ = anx_0.J(n2, j, screenElement.cto - screenElement.aba) + zl_12.aoh();
                        long l2 = n2;
                        long l3 = j;
                        float f = screenElement.cts;
                        l3 += 131071L;
                        int n12 = (int)(f * 16.0f) + 8191;
                        assert ((l2 += 131071L) < 262144L);
                        assert (l3 < 262144L);
                        assert (n12 < 16384);
                        screenElement.ctu = (l3 & 0x3FFFFL) << 32 | (l2 & 0x3FFFFL) << 14 | (long)(n12 & 0x3FFF);
                        this.EK.add(screenElement);
                        if (screenElement.NS < this.EN) {
                            this.EN = screenElement.NS;
                        }
                        if (screenElement.NS + screenElement.ctr.aoi() > this.EP) {
                            this.EP = screenElement.NS + screenElement.ctr.aoi();
                        }
                        if (screenElement.NQ > this.EQ) {
                            this.EQ = screenElement.NQ;
                        }
                        if (screenElement.NQ - screenElement.ctr.aoj() >= this.EO) continue;
                        this.EO = screenElement.NQ - screenElement.ctr.aoj();
                    }
                }
            }
        }
        acf2.close();
        EI.reset();
        n4 = this.EK.size();
        for (n3 = 0; n3 < n4; ++n3) {
            ScreenElement screenElement = (ScreenElement)this.EK.get(n3);
            EI.ct((screenElement.ctu << 14) + (long)n3);
        }
        EI.sort();
        EJ.clear();
        EJ.ensureCapacity(n4);
        n3 = 0;
        for (int j = 0; j < n4; ++j) {
            long l4 = EI.hn(j);
            n2 = (int)(l4 & 0x3FFFL);
            if (l4 < 0L) {
                EJ.add(this.EK.get(n2));
                continue;
            }
            EJ.add(n3, this.EK.get(n2));
            ++n3;
        }
        this.EK.clear();
        this.EK.addAll(EJ);
    }

    public final String toString() {
        return "ScreenMap {" + this.EL + ", " + this.EM + "}";
    }

    void aM(String string) {
        int n2;
        int n3;
        int n4;
        int n5;
        String string2 = this.aN(string);
        aij_1 aij_12 = new aij_1();
        int n6 = this.EK.size();
        for (n5 = 0; n5 < n6; ++n5) {
            ScreenElement screenElement = (ScreenElement)this.EK.get(n5);
            if (screenElement.ctp < this.ER) {
                this.ER = screenElement.ctp;
            }
            if (screenElement.ctp > this.EU) {
                this.EU = screenElement.ctp;
            }
            if (screenElement.ctq < this.ES) {
                this.ES = screenElement.ctq;
            }
            if (screenElement.ctq > this.EV) {
                this.EV = screenElement.ctq;
            }
            if (screenElement.cto < this.ET) {
                this.ET = screenElement.cto;
            }
            if (screenElement.cto <= this.EW) continue;
            this.EW = screenElement.cto;
        }
        aij_12.writeInt(this.ER);
        aij_12.writeInt(this.ES);
        aij_12.writeShort(this.ET);
        aij_12.writeInt(this.EU);
        aij_12.writeInt(this.EV);
        aij_12.writeShort(this.EW);
        n5 = Integer.MAX_VALUE;
        int n7 = Integer.MAX_VALUE;
        int n8 = Integer.MIN_VALUE;
        int n9 = Integer.MIN_VALUE;
        cp_2 cp_22 = new cp_2(512);
        for (n4 = 0; n4 < n6; ++n4) {
            ScreenElement screenElement = (ScreenElement)this.EK.get(n4);
            long l2 = screenElement.ctp;
            long l3 = screenElement.ctq;
            if (l2 < (long)n5) {
                n5 = (int)l2;
            }
            if (l2 > (long)n8) {
                n8 = (int)l2;
            }
            if (l3 < (long)n7) {
                n7 = (int)l3;
            }
            if (l3 > (long)n9) {
                n9 = (int)l3;
            }
            float f = 0.0f;
            l3 += 131071L;
            int n10 = (int)(f * 16.0f) + 8191;
            assert ((l2 += 131071L) < 262144L);
            assert (l3 < 262144L);
            assert (n10 < 16384);
            long l4 = (l3 & 0x3FFFFL) << 32 | (l2 & 0x3FFFFL) << 14 | (long)(n10 & 0x3FFF);
            ArrayList<ScreenElement> arrayList = (ArrayList<ScreenElement>)cp_22.t(l4);
            if (arrayList == null) {
                arrayList = new ArrayList<ScreenElement>(4);
                cp_22.a(l4, arrayList);
            }
            arrayList.add(screenElement);
        }
        assert (n8 - n5 <= 255);
        assert (n9 - n7 <= 255);
        aij_12.writeInt(n5);
        aij_12.writeInt(n7);
        n4 = n8 - n5 + 1;
        int n11 = n9 - n7 + 1;
        ArrayList<agf_0> arrayList = new ArrayList<agf_0>();
        byte[][] byArray = new byte[n11][n4];
        akz_0 akz_02 = cp_22.eI();
        while (akz_02.hasNext()) {
            akz_02.fK();
            ArrayList arrayList2 = (ArrayList)akz_02.value();
            int n12 = arrayList2.size();
            long l5 = akz_02.TO();
            n3 = (int)(l5 >>> 32 & 0x3FFFFL) - 131071;
            int n13 = (int)(l5 >>> 14 & 0x3FFFFL) - 131071;
            byArray[n3 - n7][n13 - n5] = (byte)n12;
        }
        for (n2 = 0; n2 < n11; ++n2) {
            for (int j = 0; j < n4; ++j) {
                int n14;
                if (byArray[n2][j] == 0) continue;
                int n15 = 1;
                int n16 = 1;
                for (n3 = n2 + 1; n3 < n11 && byArray[n3][j] != 0; ++n3) {
                    ++n16;
                }
                for (n3 = j + 1; n3 < n4; ++n3) {
                    n14 = 1;
                    for (int i2 = n2; i2 < n2 + n16; ++i2) {
                        if (byArray[i2][n3] != 0) continue;
                        n14 = 0;
                        break;
                    }
                    if (n14 == 0) break;
                    ++n15;
                }
                for (n3 = n2; n3 < n2 + n16; ++n3) {
                    for (n14 = j; n14 < j + n15; ++n14) {
                        byArray[n3][n14] = 0;
                    }
                }
                arrayList.add(new agf_0(j, j + n15, n2, n2 + n16));
            }
        }
        n2 = arrayList.size();
        aij_12.writeShort((short)n2);
        for (int j = 0; j < n2; ++j) {
            agf_0 agf_02 = (agf_0)arrayList.get(j);
            aij_12.writeByte((byte)agf_02.bAB);
            aij_12.writeByte((byte)agf_02.bAC);
            aij_12.writeByte((byte)agf_02.bAD);
            aij_12.writeByte((byte)agf_02.bAE);
            for (int i3 = agf_02.bAB; i3 < agf_02.bAC; ++i3) {
                for (n3 = agf_02.bAD; n3 < agf_02.bAE; ++n3) {
                    long l6 = i3 + n5;
                    long l7 = n3 + n7;
                    float f = 0.0f;
                    l7 += 131071L;
                    int n17 = (int)(f * 16.0f) + 8191;
                    assert ((l6 += 131071L) < 262144L);
                    assert (l7 < 262144L);
                    assert (n17 < 16384);
                    long l8 = (l7 & 0x3FFFFL) << 32 | (l6 & 0x3FFFFL) << 14 | (long)(n17 & 0x3FFF);
                    ArrayList arrayList3 = (ArrayList)cp_22.t(l8);
                    int n18 = arrayList3.size();
                    aij_12.writeByte((byte)n18);
                    for (int i4 = 0; i4 < n18; ++i4) {
                        ScreenElement screenElement = (ScreenElement)arrayList3.get(i4);
                        screenElement.a(aij_12);
                    }
                }
            }
        }
        FileOutputStream fileOutputStream = vq_2.gw(string2);
        fileOutputStream.write(aij_12.getData());
        aij_12.close();
        fileOutputStream.close();
    }

    void a(ScreenElement screenElement) {
        this.EK.add(screenElement);
    }

    boolean F(int n2, int n3) {
        return n2 >= this.ER && n2 <= this.EU && n3 >= this.ES && n3 <= this.EV;
    }

    boolean g(int n2, int n3, short s) {
        return n2 >= this.ER && n2 <= this.EU && n3 >= this.ES && n3 <= this.EV && s >= this.ET && s <= this.EW;
    }

    private String aN(String string) {
        return string + this.EL + "_" + this.EM;
    }
}

