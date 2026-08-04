/*
 * Decompiled with CFR 0.152.
 */
public class OH
implements wX {
    private static final OH bCm = new OH();
    private static final int bCn = 80;
    private final ayd bCo = new ayd(80);

    public static OH abC() {
        return bCm;
    }

    public void fy(String string) {
        acf acf2 = acf.T(vq_2.readFile(string));
        int n2 = acf2.readShort() & 0xFFFF;
        if (n2 == 0) {
            acf2.close();
            return;
        }
        bp_0 bp_02 = new bp_0();
        for (int j = 0; j < n2; ++j) {
            int n3;
            long l2 = acf2.readLong();
            short s = acf2.readShort();
            int n4 = acf2.readByte() & 0xFF;
            int[] nArray = new int[n4];
            for (n3 = 0; n3 < n4; ++n3) {
                nArray[n3] = acf2.readInt();
            }
            n3 = acf2.readShort() & 0xFFFF;
            byte[] byArray = acf2.jE(n3);
            bp_02.a(l2, s, byArray, nArray);
        }
        acf2.close();
        me_2.qR().a(bp_02);
    }

    public void aB(int n2, int n3) {
    }

    public void aC(int n2, int n3) {
    }

    public void d(ru_2 ru_22) {
        if (ru_22 == null) {
            return;
        }
        aEG[] aEGArray = ru_22.xS();
        if (aEGArray == null) {
            return;
        }
        assert (aEGArray.length > 0) : "le tableau des \u00e9l\u00e9ments interactifs devrait \u00eatre nul apr\u00e8s lecture du fichier";
        bp_0 bp_02 = new bp_0();
        for (int j = 0; j < aEGArray.length; ++j) {
            aEG aEG2 = aEGArray[j];
            bp_02.a(aEG2.nD, aEG2.Gp, aEG2.Fe, aEG2.dBG);
        }
        me_2.qR().a(bp_02);
        long l2 = ej_0.o(ru_22.pi(), ru_22.pj());
        this.bCo.put(l2, bp_02);
    }

    public void e(ru_2 ru_22) {
        long l2 = ej_0.o(ru_22.pi(), ru_22.pj());
        bp_0 bp_02 = (bp_0)this.bCo.get(l2);
        if (bp_02 != null) {
            me_2.qR().b(bp_02);
        }
    }
}

