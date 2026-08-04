/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.framework.graphics.engine;

import java.nio.FloatBuffer;

public final class VertexBufferPCT
extends ams_2 {
    private zf_1 ajg;
    private zf_1 ajh;
    private zf_1 aji;
    private FloatBuffer ajj;
    private FloatBuffer ajk;
    private FloatBuffer ajl;
    private int kA;
    private int kz;
    public static final int ajm = 2;
    public static final int ajn = 4;
    public static final int ajo = 2;
    public static final int ajp = 8;

    public VertexBufferPCT() {
        this.ajk = null;
        this.ajj = null;
        this.ajl = null;
        this.kA = 0;
        this.kz = 0;
    }

    public VertexBufferPCT(int n2) {
        this.init(n2);
    }

    public VertexBufferPCT(VertexBufferPCT vertexBufferPCT) {
        this.b(vertexBufferPCT);
    }

    public final void init(int n2) {
        this.kz = 0;
        this.kA = 0;
        this.setSize(n2);
    }

    public final void setSize(int n2) {
        if (this.ajg != null) {
            this.ajg.release();
        }
        if (this.ajh != null) {
            this.ajh.release();
        }
        if (this.aji != null) {
            this.aji.release();
        }
        this.kz = n2;
        this.ajg = aoj_1.aXZ().pI(this.kz * 2 * 4);
        this.ajh = aoj_1.aXZ().pI(this.kz * 4 * 4);
        this.aji = aoj_1.aXZ().pI(this.kz * 2 * 4);
        this.ajj = (FloatBuffer)this.ajg.getBuffer();
        this.ajk = (FloatBuffer)this.ajh.getBuffer();
        this.ajl = (FloatBuffer)this.aji.getBuffer();
    }

    public final void b(VertexBufferPCT vertexBufferPCT) {
        this.setSize(vertexBufferPCT.kz);
        this.kA = vertexBufferPCT.kA;
        this.ajj.rewind();
        this.ajj.put(vertexBufferPCT.ys());
        this.ajk.rewind();
        this.ajk.put(vertexBufferPCT.yt());
        this.ajl.rewind();
        this.ajl.put(vertexBufferPCT.yu());
    }

    public final void clear() {
        this.kA = 0;
    }

    public final FloatBuffer ys() {
        this.ajj.rewind();
        return this.ajj;
    }

    public final FloatBuffer yt() {
        this.ajk.rewind();
        return this.ajk;
    }

    public final FloatBuffer yu() {
        this.ajl.rewind();
        return this.ajl;
    }

    public final void f(float[] fArray) {
        this.ajj.rewind();
        this.ajj.put(fArray);
    }

    public final void g(float[] fArray) {
        this.ajj.position(this.kA * 2);
        this.ajj.put(fArray);
    }

    public final void b(float[] fArray, int n2) {
        assert (n2 <= fArray.length);
        this.ajj.position(this.kA * 2);
        this.ajj.put(fArray, 0, n2);
    }

    public final void c(float[] fArray, int n2) {
        this.ajj.rewind();
        this.ajj.put(fArray, 0, n2);
    }

    public final void h(float[] fArray) {
        this.ajk.position(this.kA * 4);
        this.ajk.put(fArray);
    }

    public final void d(float[] fArray, int n2) {
        assert (n2 <= fArray.length);
        this.ajk.position(this.kA * 4);
        this.ajk.put(fArray, 0, n2);
    }

    public final void i(float[] fArray) {
        this.ajk.rewind();
        this.ajk.put(fArray);
    }

    public final void e(float[] fArray, int n2) {
        this.ajk.rewind();
        this.ajk.put(fArray, 0, n2);
    }

    public final void j(float[] fArray) {
        this.ajl.position(this.kA * 2);
        this.ajl.put(fArray);
    }

    public final void f(float[] fArray, int n2) {
        assert (n2 <= fArray.length);
        this.ajl.position(this.kA * 2);
        this.ajl.put(fArray, 0, n2);
    }

    public final void k(float[] fArray) {
        this.ajl.rewind();
        this.ajl.put(fArray);
    }

    public final void g(float[] fArray, int n2) {
        this.ajl.rewind();
        this.ajl.put(fArray, 0, n2);
    }

    public final void a(int n2, float f, float f2, float f3, float f4) {
        this.ajk.position(n2 * 4);
        this.ajk.put(f);
        this.ajk.put(f2);
        this.ajk.put(f3);
        this.ajk.put(f4);
    }

    public final void l(float[] fArray) {
        this.ajk.rewind();
        this.ajk.put(fArray);
    }

    public final void a(int n2, float f, float f2) {
        this.ajl.position(n2 * 2);
        this.ajl.put(f);
        this.ajl.put(f2);
    }

    public final void b(int n2, float f, float f2) {
        this.ajj.position(n2 * 2);
        this.ajj.put(f);
        this.ajj.put(f2);
    }

    public final void f(int n2, float[] fArray) {
        this.ajj.position(n2 * 2);
        this.ajj.put(fArray);
    }

    public final void fu() {
        this.ajj.limit(this.kA * 2);
        this.ajk.limit(this.kA * 4);
        this.ajl.limit(this.kA * 2);
        this.kz = this.kA;
    }

    public final void dz(int n2) {
        this.kA = n2;
    }

    public final int fq() {
        return this.kA;
    }

    public final int fp() {
        return this.kz;
    }

    protected void delete() {
        if (this.ajg != null) {
            this.ajg.release();
            this.ajg = null;
        }
        if (this.ajh != null) {
            this.ajh.release();
            this.ajh = null;
        }
        if (this.aji != null) {
            this.aji.release();
            this.aji = null;
        }
    }

    protected void af() {
        this.kz = 0;
        this.kA = 0;
        this.ajk = null;
        this.ajj = null;
        this.ajl = null;
    }

    protected void ag() {
        this.delete();
    }
}

