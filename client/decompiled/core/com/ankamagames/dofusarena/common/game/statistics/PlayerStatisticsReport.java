/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.common.game.statistics;

public class PlayerStatisticsReport
extends rs_2 {
    public static final PlayerStatisticsReport hv = null;

    public rs_2 S() {
        return new PlayerStatisticsReport();
    }

    public void initialize() {
    }

    public void a(sk_0 sk_02, short s, boolean bl2) {
        long l2;
        long l3 = sk_02.W((short)12);
        long l4 = sk_02.W((short)13);
        long l5 = l2 = 0L < l3 && l3 < l4 ? l4 - l3 : 0L;
        if (bl2 && 0L < l2) {
            this.b((short)17, l4);
        }
        this.b((short)2, this.W((short)2) + l2);
        if (s == 1) {
            this.b((short)3, this.V((short)3) + 1);
            if (sk_02.V((short)14) > 0) {
                this.b((short)4, this.V((short)4) + 1);
                this.b((short)7, this.V((short)7) + 1);
                this.b((short)8, 0);
            } else {
                this.b((short)5, this.V((short)5) + 1);
                this.b((short)8, this.V((short)8) + 1);
                this.b((short)7, 0);
            }
        }
    }

    public void C(int n2) {
        this.b((short)3, n2);
    }

    public void D(int n2) {
        this.b((short)5, n2);
    }

    public void s(long l2) {
        this.b((short)1, l2);
    }

    public int dI() {
        return this.V((short)3);
    }

    public int dJ() {
        return this.V((short)4);
    }

    public int dK() {
        return this.V((short)5);
    }

    public long dL() {
        return this.W((short)2);
    }

    public long dM() {
        return this.W((short)1);
    }

    public int dN() {
        return this.V((short)6);
    }

    public int dO() {
        return this.V((short)8);
    }

    public int dP() {
        return this.V((short)7);
    }
}

