/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from nu
 */
public class nu_2
extends ael_2 {
    private boolean Oy;

    public boolean a(byte[] byArray) {
        if (!this.a(byArray.length, 1, false)) {
            return false;
        }
        this.Oy = byArray[0] == 1;
        return true;
    }

    public int getId() {
        return 4;
    }

    public void f(int n2) {
    }

    public boolean ss() {
        return this.Oy;
    }
}

