/*
 * Decompiled with CFR 0.152.
 */
import java.util.Arrays;

public class aCP
extends aNk {
    private static final String[] yA = new String[]{"equal", "greater", "less", "ne", "ge", "le", "eq", "gt", "lt", "more"};
    public static final aCP duH = new aCP("equal");
    public static final aCP duI = new aCP("ne");
    public static final aCP duJ = new aCP("greater");
    public static final aCP duK = new aCP("less");
    public static final aCP duL = new aCP("ge");
    public static final aCP duM = new aCP("le");
    private static final int[] duN = new int[]{0, 4, 5, 6};
    private static final int[] duO = new int[]{2, 3, 5, 8};
    private static final int[] duP = new int[]{1, 3, 4, 7, 9};

    public aCP() {
    }

    public aCP(String string) {
        this.setValue(string);
    }

    public String[] getValues() {
        return yA;
    }

    public boolean nn(int n2) {
        if (this.getIndex() == -1) {
            throw new eq_2("Comparison value not set.");
        }
        int[] nArray = n2 < 0 ? duO : (n2 > 0 ? duP : duN);
        return Arrays.binarySearch(nArray, this.getIndex()) >= 0;
    }
}

