/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.framework.graphics.engine.transformer;

import com.ankamagames.framework.kernel.core.maths.Matrix44;
import java.util.ArrayList;

public class BatchTransformer
extends ams_2 {
    private ArrayList uy;
    private Matrix44 uz;
    private boolean tY;
    private final ArrayList uA = new ArrayList(1);
    private BatchTransformer uB = null;
    private boolean uC;
    private static final Matrix44 uD = (Matrix44)yW.FL().a(Matrix44.it(), Matrix44.class);
    private static final int qL = BatchTransformer.L(BatchTransformer.class);

    public BatchTransformer() {
        this.uy = new ArrayList(3);
        this.uz = (Matrix44)yW.FL().a(Matrix44.it(), Matrix44.class);
        this.kj();
    }

    public final void a(BatchTransformer batchTransformer) {
        this.a(this.uA.size(), batchTransformer);
    }

    public final void a(int n2, BatchTransformer batchTransformer) {
        assert (batchTransformer != null) : "Child can't be null";
        batchTransformer.c(this);
        this.uA.add(n2, batchTransformer);
    }

    public final void b(BatchTransformer batchTransformer) {
        assert (batchTransformer != null) : "Child can't be null";
        batchTransformer.c(null);
        this.uA.remove(batchTransformer);
    }

    public final void aC(int n2) {
        assert (n2 > 0 && n2 < this.uA.size()) : "Index out of bound";
        this.b((BatchTransformer)this.uA.get(n2));
    }

    public final void removeAllChildren() {
        int n2 = this.uA.size();
        for (int j = 0; j < n2; ++j) {
            ((BatchTransformer)this.uA.get(j)).c(null);
        }
        this.uA.clear();
    }

    public final void a(ams_0 ams_02) {
        this.uy.add(ams_02);
        this.kj();
    }

    public final void a(int n2, ams_0 ams_02) {
        this.uy.add(n2, ams_02);
        this.kj();
    }

    public final ams_0 aI(int n2) {
        return (ams_0)this.uy.get(n2);
    }

    public final void b(int n2, ams_0 ams_02) {
        this.uy.set(n2, ams_02);
        this.kj();
    }

    public final void clear() {
        this.uB = null;
        this.removeAllChildren();
        this.uy.clear();
        this.kj();
    }

    public final Matrix44 ki() {
        if (!this.tY) {
            return this.uz;
        }
        if (this.uB == null && this.uy.size() == 0) {
            this.uz.OH();
            this.jV();
            return this.uz;
        }
        int n2 = this.uy.size();
        if (n2 == 0) {
            this.uz.OH();
        } else if (n2 > 4) {
            this.uz.a(((ams_0)this.uy.get(n2 - 2)).ki(), ((ams_0)this.uy.get(n2 - 1)).ki());
            for (int j = n2 - 3; j >= 0; --j) {
                uD.d(this.uz);
                this.uz.a(((ams_0)this.uy.get(j)).ki(), uD);
            }
        } else {
            switch (n2) {
                case 1: {
                    this.uz.d(((ams_0)this.uy.get(0)).ki());
                    break;
                }
                case 2: {
                    this.uz.a(((ams_0)this.uy.get(0)).ki(), ((ams_0)this.uy.get(1)).ki());
                    break;
                }
                case 3: {
                    this.uz.a(((ams_0)this.uy.get(0)).ki(), ((ams_0)this.uy.get(1)).ki(), ((ams_0)this.uy.get(2)).ki());
                    break;
                }
                case 4: {
                    this.uz.a(((ams_0)this.uy.get(0)).ki(), ((ams_0)this.uy.get(1)).ki(), ((ams_0)this.uy.get(2)).ki(), ((ams_0)this.uy.get(3)).ki());
                }
            }
        }
        if (this.uB != null) {
            if (this.uz.isIdentity()) {
                this.uz.d(this.uB.ki());
            } else {
                this.uz.f(this.uB.ki());
            }
        }
        this.jV();
        return this.uz;
    }

    public static int it() {
        return qL;
    }

    protected void delete() {
        super.delete();
        this.uA.clear();
        this.uB = null;
        this.uy.clear();
        this.uz.HF();
    }

    protected void af() {
        this.uz = (Matrix44)yW.FL().a(Matrix44.it(), Matrix44.class);
        this.kj();
    }

    protected void ag() {
        this.uA.clear();
        this.uB = null;
        this.uy.clear();
        this.uz.HF();
    }

    private void c(BatchTransformer batchTransformer) {
        this.uB = batchTransformer;
        this.kj();
    }

    private void kj() {
        this.tY = true;
        int n2 = this.uA.size();
        for (int j = 0; j < n2; ++j) {
            ((BatchTransformer)this.uA.get(j)).kj();
        }
    }

    private void jV() {
        this.tY = false;
        int n2 = this.uA.size();
        for (int j = 0; j < n2; ++j) {
            ((BatchTransformer)this.uA.get(j)).kj();
        }
    }
}

