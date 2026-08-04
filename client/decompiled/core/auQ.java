/*
 * Decompiled with CFR 0.152.
 */
public abstract class auQ
extends pr_0 {
    private byte cWU;
    private String cWV;
    private int cWW;
    public static final byte cWX = 0;
    public static final byte cWY = 1;
    public static final byte cWZ = 2;
    public static final byte cXa = 3;
    public static final byte cXb = 4;
    public static final byte cXc = 5;
    public static final byte cXd = 6;
    public static final int cXe = 0;
    public static final int cXf = 1;
    public static final int cXg = 2;
    public static final int cXh = 3;
    public static final int cXi = 4;
    public static final int cXj = 5;
    public static final int cXk = 6;
    public static final int cXl = 7;
    public static final int cXm = 8;
    public static final int cXn = 9;
    public static final int cXo = 10;
    public static final int cXp = 11;
    public static final int ID = 1024;

    public byte[] encode() {
        return new byte[0];
    }

    public boolean a(byte[] byArray) {
        return true;
    }

    public int getId() {
        return 1024;
    }

    public void f(int n2) {
    }

    public void b() {
        this.cWU = 0;
        this.cWV = "";
    }

    public void j() {
    }

    public byte aHG() {
        return this.cWU;
    }

    public void aX(byte by) {
        this.cWU = by;
    }

    public String getErrorMessage() {
        return this.cWV;
    }

    public void jE(String string) {
        this.cWV = string;
    }

    public int getErrorCode() {
        return this.cWW;
    }

    public void mr(int n2) {
        this.cWW = n2;
    }
}

