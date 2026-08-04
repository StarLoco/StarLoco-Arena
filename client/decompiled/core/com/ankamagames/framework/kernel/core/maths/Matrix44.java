/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
package com.ankamagames.framework.kernel.core.maths;

import org.apache.log4j.Logger;

public final class Matrix44
extends ams_2 {
    protected static final Logger a = Logger.getLogger(Matrix44.class);
    private static final float[] bEm = new float[16];
    public static final Matrix44 bEn;
    private float[] aaB;
    private static final int qL;
    private boolean bEo;

    public Matrix44() {
        this.createBuffer();
    }

    public Matrix44(Matrix44 matrix44) {
        this.createBuffer();
        this.d(matrix44);
    }

    public Matrix44(float[] fArray) {
        this.o(fArray);
    }

    public Matrix44(eu_2 eu_22) {
        this.createBuffer();
        this.e(eu_22);
    }

    public Matrix44(eu_2 eu_22, agu_0 agu_02) {
        this.createBuffer();
        this.a(eu_22, agu_02);
    }

    public Matrix44(agu_0 agu_02, agu_0 agu_03, agu_0 agu_04, agu_0 agu_05) {
        this.createBuffer();
        this.a(agu_02, agu_03, agu_04, agu_05);
    }

    public static Matrix44 acr() {
        Matrix44 matrix44 = new Matrix44();
        matrix44.OH();
        return matrix44;
    }

    public final void b(acf acf2) {
        for (int j = 0; j < this.aaB.length; ++j) {
            this.aaB[j] = acf2.readFloat();
        }
        this.bEo = false;
    }

    public final void a(aij_1 aij_12) {
        for (int j = 0; j < this.aaB.length; ++j) {
            aij_12.writeFloat(this.aaB[j]);
        }
    }

    public final void d(Matrix44 matrix44) {
        this.bEo = matrix44.bEo;
        System.arraycopy(matrix44.aaB, 0, this.aaB, 0, this.aaB.length);
    }

    public final void o(float[] fArray) {
        assert (fArray.length >= 16) : "Buffer length must be at least of 16 float";
        this.createBuffer();
        System.arraycopy(fArray, 0, this.aaB, 0, 16);
        this.bEo = false;
    }

    public final void e(eu_2 eu_22) {
        float f = eu_22.getX() + eu_22.getX();
        float f2 = eu_22.getY() + eu_22.getY();
        float f3 = eu_22.id() + eu_22.id();
        float f4 = eu_22.getX() * f;
        float f5 = eu_22.getX() * f2;
        float f6 = eu_22.getX() * f3;
        float f7 = eu_22.getY() * f2;
        float f8 = eu_22.getY() * f3;
        float f9 = eu_22.id() * f3;
        float f10 = eu_22.Fe() * f;
        float f11 = eu_22.Fe() * f2;
        float f12 = eu_22.Fe() * f3;
        this.aaB[0] = 1.0f - (f7 + f9);
        this.aaB[1] = f5 + f12;
        this.aaB[2] = f6 - f11;
        this.aaB[3] = 0.0f;
        this.aaB[4] = f5 - f12;
        this.aaB[5] = 1.0f - (f4 + f9);
        this.aaB[6] = f8 + f10;
        this.aaB[7] = 0.0f;
        this.aaB[8] = f6 + f11;
        this.aaB[9] = f8 - f10;
        this.aaB[10] = 1.0f - (f4 + f7);
        this.aaB[11] = 0.0f;
        this.aaB[12] = 0.0f;
        this.aaB[13] = 0.0f;
        this.aaB[14] = 0.0f;
        this.aaB[15] = 1.0f;
        this.bEo = false;
    }

    public final void a(eu_2 eu_22, agu_0 agu_02) {
        this.e(eu_22);
        this.e(agu_02);
        this.act();
        this.bEo = false;
    }

    public final void a(eu_2 eu_22, agu_0 agu_02, agu_0 agu_03) {
        this.e(eu_22);
        this.e(agu_02);
        this.act();
        float f = agu_03.getX();
        float f2 = agu_03.getY();
        float f3 = agu_03.id();
        if (f != 1.0f || f2 != 1.0f || f3 != 1.0f) {
            this.aaB[0] = this.aaB[0] * f;
            this.aaB[1] = this.aaB[1] * f;
            this.aaB[2] = this.aaB[2] * f;
            this.aaB[4] = this.aaB[4] * f2;
            this.aaB[5] = this.aaB[5] * f2;
            this.aaB[6] = this.aaB[6] * f2;
            this.aaB[8] = this.aaB[8] * f3;
            this.aaB[9] = this.aaB[9] * f3;
            this.aaB[10] = this.aaB[10] * f3;
        }
        this.bEo = false;
    }

    public final void a(agu_0 agu_02, agu_0 agu_03, agu_0 agu_04, agu_0 agu_05) {
        this.b(agu_02);
        this.c(agu_03);
        this.d(agu_04);
        this.e(agu_05);
        this.bEo = false;
    }

    public final void b(agu_0 agu_02) {
        this.aaB[0] = agu_02.getX();
        this.aaB[1] = agu_02.getY();
        this.aaB[2] = agu_02.id();
        this.bEo = false;
    }

    public final void c(agu_0 agu_02) {
        this.aaB[4] = agu_02.getX();
        this.aaB[5] = agu_02.getY();
        this.aaB[6] = agu_02.id();
        this.bEo = false;
    }

    public final void d(agu_0 agu_02) {
        this.aaB[8] = agu_02.getX();
        this.aaB[9] = agu_02.getY();
        this.aaB[10] = agu_02.id();
        this.bEo = false;
    }

    public final void e(agu_0 agu_02) {
        this.aaB[12] = agu_02.getX();
        this.aaB[13] = agu_02.getY();
        this.aaB[14] = agu_02.id();
        this.bEo = false;
    }

    public final void e(float f, float f2, float f3) {
        this.aaB[12] = f;
        this.aaB[13] = f2;
        this.aaB[14] = f3;
        this.bEo = false;
    }

    public final void f(agu_0 agu_02) {
        this.act();
        float f = agu_02.getX();
        float f2 = agu_02.getY();
        float f3 = agu_02.id();
        if (f != 1.0f || f2 != 1.0f || f3 != 1.0f) {
            this.aaB[0] = this.aaB[0] * f;
            this.aaB[1] = this.aaB[1] * f;
            this.aaB[2] = this.aaB[2] * f;
            this.aaB[4] = this.aaB[4] * f2;
            this.aaB[5] = this.aaB[5] * f2;
            this.aaB[6] = this.aaB[6] * f2;
            this.aaB[8] = this.aaB[8] * f3;
            this.aaB[9] = this.aaB[9] * f3;
            this.aaB[10] = this.aaB[10] * f3;
        }
    }

    public final void OH() {
        try {
            System.arraycopy(bEm, 0, this.aaB, 0, 16);
        }
        catch (Exception exception) {
            a.error((Object)"Exception", (Throwable)exception);
        }
        this.aaB[15] = 1.0f;
        this.aaB[10] = 1.0f;
        this.aaB[5] = 1.0f;
        this.aaB[0] = 1.0f;
        this.bEo = true;
    }

    public final void a(agu_0 agu_02, agu_0 agu_03) {
        agu_03.d(agu_02.getX() * this.aaB[0] + agu_02.getY() * this.aaB[4] + agu_02.id() * this.aaB[8], agu_02.getX() * this.aaB[1] + agu_02.getY() * this.aaB[5] + agu_02.id() * this.aaB[9], agu_02.getX() * this.aaB[2] + agu_02.getY() * this.aaB[6] + agu_02.id() * this.aaB[10], 0.0f);
    }

    public final void b(agu_0 agu_02, agu_0 agu_03) {
        agu_03.d(agu_02.getX() * this.aaB[0] + agu_02.getY() * this.aaB[4] + agu_02.id() * this.aaB[8] + agu_02.Fe() * this.aaB[12], agu_02.getX() * this.aaB[1] + agu_02.getY() * this.aaB[5] + agu_02.id() * this.aaB[9] + agu_02.Fe() * this.aaB[13], agu_02.getX() * this.aaB[2] + agu_02.getY() * this.aaB[6] + agu_02.id() * this.aaB[10] + agu_02.Fe() * this.aaB[14], agu_02.getX() * this.aaB[3] + agu_02.getY() * this.aaB[7] + agu_02.id() * this.aaB[11] + agu_02.Fe() * this.aaB[15]);
    }

    public final void am(float f) {
        this.bEo = false;
        this.aaB[0] = this.aaB[0] * f;
        this.aaB[1] = this.aaB[1] * f;
        this.aaB[2] = this.aaB[2] * f;
        this.aaB[3] = this.aaB[3] * f;
        this.aaB[4] = this.aaB[4] * f;
        this.aaB[5] = this.aaB[5] * f;
        this.aaB[6] = this.aaB[6] * f;
        this.aaB[7] = this.aaB[7] * f;
        this.aaB[8] = this.aaB[8] * f;
        this.aaB[9] = this.aaB[9] * f;
        this.aaB[10] = this.aaB[10] * f;
        this.aaB[11] = this.aaB[11] * f;
        this.aaB[12] = this.aaB[12] * f;
        this.aaB[13] = this.aaB[13] * f;
        this.aaB[14] = this.aaB[14] * f;
        this.aaB[15] = this.aaB[15] * f;
    }

    public final void e(Matrix44 matrix44) {
        if (this.bEo) {
            matrix44.d(this);
        } else {
            matrix44.aaB[0] = this.aaB[0];
            matrix44.aaB[1] = this.aaB[4];
            matrix44.aaB[2] = this.aaB[8];
            matrix44.aaB[3] = this.aaB[12];
            matrix44.aaB[4] = this.aaB[1];
            matrix44.aaB[5] = this.aaB[5];
            matrix44.aaB[6] = this.aaB[9];
            matrix44.aaB[7] = this.aaB[13];
            matrix44.aaB[8] = this.aaB[2];
            matrix44.aaB[9] = this.aaB[6];
            matrix44.aaB[10] = this.aaB[10];
            matrix44.aaB[11] = this.aaB[14];
            matrix44.aaB[12] = this.aaB[3];
            matrix44.aaB[13] = this.aaB[7];
            matrix44.aaB[14] = this.aaB[11];
            matrix44.aaB[15] = this.aaB[15];
            matrix44.bEo = false;
        }
    }

    public final void f(Matrix44 matrix44) {
        this.bEo = false;
        float f = this.aaB[0] * matrix44.aaB[0] + this.aaB[1] * matrix44.aaB[4] + this.aaB[2] * matrix44.aaB[8] + this.aaB[3] * matrix44.aaB[12];
        float f2 = this.aaB[0] * matrix44.aaB[1] + this.aaB[1] * matrix44.aaB[5] + this.aaB[2] * matrix44.aaB[9] + this.aaB[3] * matrix44.aaB[13];
        float f3 = this.aaB[0] * matrix44.aaB[2] + this.aaB[1] * matrix44.aaB[6] + this.aaB[2] * matrix44.aaB[10] + this.aaB[3] * matrix44.aaB[14];
        float f4 = this.aaB[0] * matrix44.aaB[3] + this.aaB[1] * matrix44.aaB[7] + this.aaB[2] * matrix44.aaB[11] + this.aaB[3] * matrix44.aaB[15];
        float f5 = this.aaB[4] * matrix44.aaB[0] + this.aaB[5] * matrix44.aaB[4] + this.aaB[6] * matrix44.aaB[8] + this.aaB[7] * matrix44.aaB[12];
        float f6 = this.aaB[4] * matrix44.aaB[1] + this.aaB[5] * matrix44.aaB[5] + this.aaB[6] * matrix44.aaB[9] + this.aaB[7] * matrix44.aaB[13];
        float f7 = this.aaB[4] * matrix44.aaB[2] + this.aaB[5] * matrix44.aaB[6] + this.aaB[6] * matrix44.aaB[10] + this.aaB[7] * matrix44.aaB[14];
        float f8 = this.aaB[4] * matrix44.aaB[3] + this.aaB[5] * matrix44.aaB[7] + this.aaB[6] * matrix44.aaB[11] + this.aaB[7] * matrix44.aaB[15];
        float f9 = this.aaB[8] * matrix44.aaB[0] + this.aaB[9] * matrix44.aaB[4] + this.aaB[10] * matrix44.aaB[8] + this.aaB[11] * matrix44.aaB[12];
        float f10 = this.aaB[8] * matrix44.aaB[1] + this.aaB[9] * matrix44.aaB[5] + this.aaB[10] * matrix44.aaB[9] + this.aaB[11] * matrix44.aaB[13];
        float f11 = this.aaB[8] * matrix44.aaB[2] + this.aaB[9] * matrix44.aaB[6] + this.aaB[10] * matrix44.aaB[10] + this.aaB[11] * matrix44.aaB[14];
        float f12 = this.aaB[8] * matrix44.aaB[3] + this.aaB[9] * matrix44.aaB[7] + this.aaB[10] * matrix44.aaB[11] + this.aaB[11] * matrix44.aaB[15];
        float f13 = this.aaB[12] * matrix44.aaB[0] + this.aaB[13] * matrix44.aaB[4] + this.aaB[14] * matrix44.aaB[8] + this.aaB[15] * matrix44.aaB[12];
        float f14 = this.aaB[12] * matrix44.aaB[1] + this.aaB[13] * matrix44.aaB[5] + this.aaB[14] * matrix44.aaB[9] + this.aaB[15] * matrix44.aaB[13];
        float f15 = this.aaB[12] * matrix44.aaB[2] + this.aaB[13] * matrix44.aaB[6] + this.aaB[14] * matrix44.aaB[10] + this.aaB[15] * matrix44.aaB[14];
        float f16 = this.aaB[12] * matrix44.aaB[3] + this.aaB[13] * matrix44.aaB[7] + this.aaB[14] * matrix44.aaB[11] + this.aaB[15] * matrix44.aaB[15];
        this.aaB[0] = f;
        this.aaB[1] = f2;
        this.aaB[2] = f3;
        this.aaB[3] = f4;
        this.aaB[4] = f5;
        this.aaB[5] = f6;
        this.aaB[6] = f7;
        this.aaB[7] = f8;
        this.aaB[8] = f9;
        this.aaB[9] = f10;
        this.aaB[10] = f11;
        this.aaB[11] = f12;
        this.aaB[12] = f13;
        this.aaB[13] = f14;
        this.aaB[14] = f15;
        this.aaB[15] = f16;
    }

    public final void a(Matrix44 matrix44, Matrix44 matrix442) {
        this.bEo = false;
        this.aaB[0] = matrix44.aaB[0] * matrix442.aaB[0] + matrix44.aaB[1] * matrix442.aaB[4] + matrix44.aaB[2] * matrix442.aaB[8] + matrix44.aaB[3] * matrix442.aaB[12];
        this.aaB[1] = matrix44.aaB[0] * matrix442.aaB[1] + matrix44.aaB[1] * matrix442.aaB[5] + matrix44.aaB[2] * matrix442.aaB[9] + matrix44.aaB[3] * matrix442.aaB[13];
        this.aaB[2] = matrix44.aaB[0] * matrix442.aaB[2] + matrix44.aaB[1] * matrix442.aaB[6] + matrix44.aaB[2] * matrix442.aaB[10] + matrix44.aaB[3] * matrix442.aaB[14];
        this.aaB[3] = matrix44.aaB[0] * matrix442.aaB[3] + matrix44.aaB[1] * matrix442.aaB[7] + matrix44.aaB[2] * matrix442.aaB[11] + matrix44.aaB[3] * matrix442.aaB[15];
        this.aaB[4] = matrix44.aaB[4] * matrix442.aaB[0] + matrix44.aaB[5] * matrix442.aaB[4] + matrix44.aaB[6] * matrix442.aaB[8] + matrix44.aaB[7] * matrix442.aaB[12];
        this.aaB[5] = matrix44.aaB[4] * matrix442.aaB[1] + matrix44.aaB[5] * matrix442.aaB[5] + matrix44.aaB[6] * matrix442.aaB[9] + matrix44.aaB[7] * matrix442.aaB[13];
        this.aaB[6] = matrix44.aaB[4] * matrix442.aaB[2] + matrix44.aaB[5] * matrix442.aaB[6] + matrix44.aaB[6] * matrix442.aaB[10] + matrix44.aaB[7] * matrix442.aaB[14];
        this.aaB[7] = matrix44.aaB[4] * matrix442.aaB[3] + matrix44.aaB[5] * matrix442.aaB[7] + matrix44.aaB[6] * matrix442.aaB[11] + matrix44.aaB[7] * matrix442.aaB[15];
        this.aaB[8] = matrix44.aaB[8] * matrix442.aaB[0] + matrix44.aaB[9] * matrix442.aaB[4] + matrix44.aaB[10] * matrix442.aaB[8] + matrix44.aaB[11] * matrix442.aaB[12];
        this.aaB[9] = matrix44.aaB[8] * matrix442.aaB[1] + matrix44.aaB[9] * matrix442.aaB[5] + matrix44.aaB[10] * matrix442.aaB[9] + matrix44.aaB[11] * matrix442.aaB[13];
        this.aaB[10] = matrix44.aaB[8] * matrix442.aaB[2] + matrix44.aaB[9] * matrix442.aaB[6] + matrix44.aaB[10] * matrix442.aaB[10] + matrix44.aaB[11] * matrix442.aaB[14];
        this.aaB[11] = matrix44.aaB[8] * matrix442.aaB[3] + matrix44.aaB[9] * matrix442.aaB[7] + matrix44.aaB[10] * matrix442.aaB[11] + matrix44.aaB[11] * matrix442.aaB[15];
        this.aaB[12] = matrix44.aaB[12] * matrix442.aaB[0] + matrix44.aaB[13] * matrix442.aaB[4] + matrix44.aaB[14] * matrix442.aaB[8] + matrix44.aaB[15] * matrix442.aaB[12];
        this.aaB[13] = matrix44.aaB[12] * matrix442.aaB[1] + matrix44.aaB[13] * matrix442.aaB[5] + matrix44.aaB[14] * matrix442.aaB[9] + matrix44.aaB[15] * matrix442.aaB[13];
        this.aaB[14] = matrix44.aaB[12] * matrix442.aaB[2] + matrix44.aaB[13] * matrix442.aaB[6] + matrix44.aaB[14] * matrix442.aaB[10] + matrix44.aaB[15] * matrix442.aaB[14];
        this.aaB[15] = matrix44.aaB[12] * matrix442.aaB[3] + matrix44.aaB[13] * matrix442.aaB[7] + matrix44.aaB[14] * matrix442.aaB[11] + matrix44.aaB[15] * matrix442.aaB[15];
    }

    public final void a(Matrix44 matrix44, Matrix44 matrix442, Matrix44 matrix443) {
        Matrix44 matrix444 = new Matrix44();
        matrix444.a(matrix442, matrix443);
        this.a(matrix44, matrix444);
        this.bEo = false;
    }

    public final void a(Matrix44 matrix44, Matrix44 matrix442, Matrix44 matrix443, Matrix44 matrix444) {
        Matrix44 matrix445 = new Matrix44();
        matrix445.a(matrix442, matrix443, matrix444);
        this.a(matrix44, matrix445);
        this.bEo = false;
    }

    public final float acs() {
        if (this.bEo) {
            return 1.0f;
        }
        float f = this.aaB[0] * this.aaB[5] - this.aaB[1] * this.aaB[4];
        float f2 = this.aaB[0] * this.aaB[6] - this.aaB[2] * this.aaB[4];
        float f3 = this.aaB[0] * this.aaB[7] - this.aaB[3] * this.aaB[4];
        float f4 = this.aaB[1] * this.aaB[6] - this.aaB[2] * this.aaB[5];
        float f5 = this.aaB[1] * this.aaB[7] - this.aaB[3] * this.aaB[5];
        float f6 = this.aaB[2] * this.aaB[7] - this.aaB[3] * this.aaB[6];
        float f7 = this.aaB[8] * this.aaB[13] - this.aaB[9] * this.aaB[12];
        float f8 = this.aaB[8] * this.aaB[14] - this.aaB[10] * this.aaB[12];
        float f9 = this.aaB[8] * this.aaB[15] - this.aaB[11] * this.aaB[12];
        float f10 = this.aaB[9] * this.aaB[14] - this.aaB[10] * this.aaB[13];
        float f11 = this.aaB[9] * this.aaB[15] - this.aaB[11] * this.aaB[13];
        float f12 = this.aaB[10] * this.aaB[15] - this.aaB[11] * this.aaB[14];
        return f * f12 - f2 * f11 + f3 * f10 + f4 * f9 - f5 * f8 + f6 * f7;
    }

    public final boolean g(Matrix44 matrix44) {
        if (this.bEo) {
            matrix44.OH();
            return true;
        }
        float f = this.aaB[0] * this.aaB[5] - this.aaB[1] * this.aaB[4];
        float f2 = this.aaB[10] * this.aaB[15] - this.aaB[11] * this.aaB[14];
        float f3 = this.aaB[0] * this.aaB[6] - this.aaB[2] * this.aaB[4];
        float f4 = this.aaB[9] * this.aaB[15] - this.aaB[11] * this.aaB[13];
        float f5 = this.aaB[0] * this.aaB[7] - this.aaB[3] * this.aaB[4];
        float f6 = this.aaB[9] * this.aaB[14] - this.aaB[10] * this.aaB[13];
        float f7 = this.aaB[1] * this.aaB[6] - this.aaB[2] * this.aaB[5];
        float f8 = this.aaB[8] * this.aaB[15] - this.aaB[11] * this.aaB[12];
        float f9 = this.aaB[1] * this.aaB[7] - this.aaB[3] * this.aaB[5];
        float f10 = this.aaB[8] * this.aaB[14] - this.aaB[10] * this.aaB[12];
        float f11 = this.aaB[2] * this.aaB[7] - this.aaB[3] * this.aaB[6];
        float f12 = this.aaB[8] * this.aaB[13] - this.aaB[9] * this.aaB[12];
        float f13 = f * f2 - f3 * f4 + f5 * f6 + f7 * f8 - f9 * f10 + f11 * f12;
        if (Math.abs(f13) < 1.0E-5f) {
            return false;
        }
        matrix44.aaB[0] = this.aaB[5] * f2 - this.aaB[6] * f4 + this.aaB[7] * f6;
        matrix44.aaB[4] = -this.aaB[4] * f2 + this.aaB[6] * f8 - this.aaB[7] * f10;
        matrix44.aaB[8] = this.aaB[4] * f4 - this.aaB[5] * f8 + this.aaB[7] * f12;
        matrix44.aaB[12] = -this.aaB[4] * f6 + this.aaB[5] * f10 - this.aaB[6] * f12;
        matrix44.aaB[1] = -this.aaB[1] * f2 + this.aaB[2] * f4 - this.aaB[3] * f6;
        matrix44.aaB[5] = this.aaB[0] * f2 - this.aaB[2] * f8 + this.aaB[3] * f10;
        matrix44.aaB[9] = -this.aaB[0] * f4 + this.aaB[1] * f8 - this.aaB[3] * f12;
        matrix44.aaB[13] = this.aaB[0] * f6 - this.aaB[1] * f10 + this.aaB[2] * f12;
        matrix44.aaB[2] = this.aaB[13] * f11 - this.aaB[14] * f9 + this.aaB[15] * f7;
        matrix44.aaB[6] = -this.aaB[12] * f11 + this.aaB[14] * f5 - this.aaB[15] * f3;
        matrix44.aaB[10] = this.aaB[12] * f9 - this.aaB[13] * f5 + this.aaB[15] * f;
        matrix44.aaB[14] = -this.aaB[12] * f7 + this.aaB[13] * f3 - this.aaB[14] * f;
        matrix44.aaB[3] = -this.aaB[9] * f11 + this.aaB[10] * f9 - this.aaB[11] * f7;
        matrix44.aaB[7] = this.aaB[8] * f11 - this.aaB[10] * f5 + this.aaB[11] * f3;
        matrix44.aaB[11] = -this.aaB[8] * f9 + this.aaB[9] * f5 - this.aaB[11] * f;
        matrix44.aaB[15] = this.aaB[8] * f7 - this.aaB[9] * f3 + this.aaB[10] * f;
        matrix44.am(1.0f / f13);
        return true;
    }

    public final float[] Pn() {
        return this.aaB;
    }

    public final void d(int n2, float f) {
        this.bEo = false;
        this.aaB[n2] = f;
    }

    public final boolean isIdentity() {
        return this.bEo;
    }

    public final boolean h(Matrix44 matrix44) {
        if (matrix44 == this) {
            return true;
        }
        for (int j = 0; j < this.aaB.length; ++j) {
            if (this.aaB[j] == matrix44.aaB[j]) continue;
            return false;
        }
        return true;
    }

    public static int it() {
        return qL;
    }

    protected final void af() {
        this.bEo = false;
    }

    protected final void ag() {
        this.delete();
    }

    private void createBuffer() {
        if (this.aaB != null) {
            return;
        }
        this.bEo = false;
        this.aaB = new float[16];
    }

    private void act() {
        this.aaB[3] = 0.0f;
        this.aaB[7] = 0.0f;
        this.aaB[11] = 0.0f;
        this.aaB[15] = 1.0f;
    }

    static {
        for (int j = 0; j < 16; ++j) {
            Matrix44.bEm[j] = 0.0f;
        }
        bEn = Matrix44.acr();
        qL = Matrix44.L(Matrix44.class);
    }
}

