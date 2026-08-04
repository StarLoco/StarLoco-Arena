/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.framework.graphics.engine;

import java.nio.Buffer;
import java.nio.BufferOverflowException;
import java.nio.FloatBuffer;

public final class VertexBuffer
extends ams_2 {
    private zf_1 kw;
    private FloatBuffer kx;
    private yb_1 ky;
    private int kz;
    private int kA;

    public VertexBuffer() {
        this.kx = null;
        this.kz = 0;
        this.kA = 0;
    }

    public VertexBuffer(int n2, yb_1 yb_12) {
        this.a(n2, yb_12);
    }

    public VertexBuffer(VertexBuffer vertexBuffer) {
        this.a(vertexBuffer);
    }

    public void a(int n2, yb_1 yb_12) {
        this.kz = 0;
        this.kA = 0;
        this.ky = yb_12;
        this.setSize(n2);
    }

    public final void a(VertexBuffer vertexBuffer) {
        this.a(vertexBuffer.kz, vertexBuffer.ky);
        this.kA = vertexBuffer.kA;
        this.kx.put(vertexBuffer.fs());
    }

    public final void clear() {
        this.kA = 0;
    }

    public final void a(aij_1 aij_12) {
        this.ky.a(aij_12);
        aij_12.writeInt(this.kA);
        aij_12.writeInt(this.kz);
        this.kx.rewind();
        float[] fArray = new float[this.kx.limit()];
        this.kx.get(fArray);
        aij_12.writeInt(fArray.length);
        for (float f : fArray) {
            aij_12.writeFloat(f);
        }
    }

    public final void b(acf acf2) {
        this.ky = new yb_1();
        this.ky.b(acf2);
        this.kA = acf2.readInt();
        this.kz = acf2.readInt();
        this.setSize(this.kz);
        int n2 = acf2.readInt();
        for (int j = 0; j < n2; ++j) {
            this.kx.put(acf2.readFloat());
        }
    }

    public final void setSize(int n2) {
        assert (this.ky != null);
        if (this.kw != null) {
            this.kw.release();
        }
        this.kz = n2;
        this.kw = aoj_1.aXZ().pI(this.fp() * this.fr());
        this.kx = (FloatBuffer)this.kw.getBuffer();
    }

    public final int fo() {
        return this.ky.getSize();
    }

    public final int fp() {
        return this.kz;
    }

    public final int fq() {
        return this.kA;
    }

    public final int fr() {
        return this.fo() * 4;
    }

    public final FloatBuffer fs() {
        return (FloatBuffer)this.kx.position(0);
    }

    public final int U(int n2) {
        return this.ky.W(n2).getPosition();
    }

    public final Buffer V(int n2) {
        return this.kx.position(this.U(n2));
    }

    public final FloatBuffer h(int n2, int n3) {
        return (FloatBuffer)this.kx.position(this.fo() * n2 + this.U(n3));
    }

    public final agt_2 W(int n2) {
        return this.ky.W(n2);
    }

    public final void a(float[] fArray, int n2, int n3) {
        assert (n3 <= this.kz * this.ky.getSize());
        assert (n3 % this.ky.getSize() == 0);
        this.kx.rewind();
        this.kx.put(fArray, n2, n3);
        this.kA = n3 / this.ky.getSize();
    }

    public final void a(int n2, int n3, float[] fArray) {
        this.kx.position(this.fo() * n2 + this.U(n3));
        this.kx.put(fArray);
        this.X(n2);
    }

    public final void a(int n2, int n3, float f) {
        assert (this.ky.W(n3).getSize() == 1);
        this.kx.position(this.fo() * n2 + this.U(n3));
        this.kx.put(f);
        this.X(n2);
    }

    public final void a(int n2, int n3, float f, float f2) {
        assert (this.ky.W(n3).getSize() == 2);
        this.kx.position(this.fo() * n2 + this.U(n3));
        this.kx.put(f);
        this.kx.put(f2);
        this.X(n2);
    }

    public final void a(int n2, int n3, float f, float f2, float f3) {
        block3: {
            assert (this.ky.W(n3).getSize() == 3);
            try {
                this.kx.position(this.fo() * n2 + this.U(n3));
                this.kx.put(f);
                this.kx.put(f2);
                this.kx.put(f3);
            }
            catch (BufferOverflowException bufferOverflowException) {
                if (bb) break block3;
                throw new AssertionError();
            }
        }
        this.X(n2);
    }

    public final void a(int n2, int n3, float f, float f2, float f3, float f4) {
        assert (this.ky.W(n3).getSize() == 4);
        this.kx.position(this.fo() * n2 + this.U(n3));
        this.kx.put(f);
        this.kx.put(f2);
        this.kx.put(f3);
        this.kx.put(f4);
        this.X(n2);
    }

    public final yb_1 ft() {
        return new yb_1(this.ky);
    }

    public final int a(float[] fArray, int n2) {
        assert (fArray.length - n2 >= this.fo()) : "Float array size is less than a vertex size";
        int n3 = this.fq();
        this.kx.put(fArray, n2, this.fo());
        this.X(n3);
        return n3;
    }

    public final void b(VertexBuffer vertexBuffer) {
        assert (vertexBuffer.fq() + this.fq() <= this.fp());
        assert (vertexBuffer.ky.getSize() == this.ky.getSize());
        this.kx.position(this.fq() * this.fo());
        this.kx.put(vertexBuffer.fs());
        this.kA += vertexBuffer.fq();
    }

    public final void b(float[] fArray) {
        assert (fArray.length <= (this.fp() - this.fq()) * this.ky.getSize());
        assert (fArray.length % this.ky.getSize() == 0);
        this.kx.position(this.fq() * this.fo());
        this.kx.put(fArray);
        this.kA += fArray.length / this.ky.getSize();
    }

    public final void b(float[] fArray, int n2, int n3) {
        assert (n2 + n3 <= (this.fp() - this.fq()) * this.ky.getSize());
        assert (n3 % this.ky.getSize() == 0);
        this.kx.position(this.fq() * this.fo());
        this.kx.put(fArray, n2, n3);
        this.kA += n3 / this.ky.getSize();
    }

    public final void fu() {
        this.kx.limit(this.kA * this.fo());
        this.kz = this.kA;
    }

    protected void delete() {
        if (this.kw != null) {
            this.kw.release();
            this.kw = null;
        }
    }

    protected void af() {
        this.kz = 0;
        this.kA = 0;
        this.kx = null;
        this.ky = null;
    }

    protected void ag() {
        this.delete();
    }

    private void X(int n2) {
        if (this.kA <= n2) {
            this.kA = n2 + 1;
        }
    }
}

