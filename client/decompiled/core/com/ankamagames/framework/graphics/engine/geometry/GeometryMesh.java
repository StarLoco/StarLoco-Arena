/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.framework.graphics.engine.geometry;

import com.ankamagames.framework.graphics.engine.VertexBufferPCT;
import com.ankamagames.framework.graphics.engine.geometry.Geometry;
import java.nio.FloatBuffer;
import java.util.Arrays;

public abstract class GeometryMesh
extends Geometry {
    protected ams_1 az;
    protected VertexBufferPCT aA;
    protected jB aB;
    protected float aC;
    protected boolean aD;
    protected float[] aE;
    protected int aF;

    public GeometryMesh() {
        this.az = null;
        this.aA = null;
        this.aB = jB.AX;
        this.aC = 1.0f;
        this.aD = false;
    }

    public GeometryMesh(GeometryMesh geometryMesh) {
        this.a(geometryMesh.aB, geometryMesh.aA, geometryMesh.az, true);
    }

    public void a(jB jB2, int n2, int n3) {
        this.aB = jB2;
        this.az = new ams_1(n3);
        this.aA = new VertexBufferPCT(n2);
        this.a(this.aA);
    }

    public void a(jB jB2, VertexBufferPCT vertexBufferPCT, ams_1 ams_12, boolean bl2) {
        this.aB = jB2;
        if (bl2) {
            this.aA = new VertexBufferPCT();
            this.aA.b(vertexBufferPCT);
            this.az = new ams_1(ams_12);
        } else {
            this.aA = vertexBufferPCT;
            this.az = ams_12;
        }
        this.a(this.aA);
    }

    public void a(jB jB2, VertexBufferPCT vertexBufferPCT, ams_1 ams_12, boolean bl2, float[] fArray) {
        this.aB = jB2;
        if (bl2) {
            this.aA = new VertexBufferPCT();
            this.aA.b(vertexBufferPCT);
            this.az = new ams_1(ams_12);
        } else {
            this.aA = vertexBufferPCT;
            this.az = ams_12;
        }
        int n2 = vertexBufferPCT.fq();
        if (n2 == 0) {
            this.ah();
            return;
        }
        this.ah();
        this.e(n2);
        System.arraycopy(fArray, 0, this.aE, 0, n2 * 4);
    }

    public final VertexBufferPCT ab() {
        return this.aA;
    }

    public final ams_1 ac() {
        return this.az;
    }

    public final jB ad() {
        return this.aB;
    }

    public final void d(int n2) {
        this.az.add(n2);
    }

    public void setColor(float f, float f2, float f3, float f4) {
        if (this.aA == null) {
            return;
        }
        if (this.aA.yt() == null) {
            return;
        }
        for (int j = 0; j < this.aA.fq(); ++j) {
            this.aA.a(j, f, f2, f3, f4);
        }
        this.a(this.aA);
    }

    public void a(aPb aPb2) {
        int n2 = this.aA.fq();
        if (this.aF != n2) {
            return;
        }
        for (int j = 0; j < n2; ++j) {
            float[] fArray = aPb2.aYK();
            float[] fArray2 = aPb2.aYL();
            float f = this.aE[j * 4] * fArray[0] + fArray2[0];
            float f2 = this.aE[j * 4 + 1] * fArray[1] + fArray2[1];
            float f3 = this.aE[j * 4 + 2] * fArray[2] + fArray2[2];
            float f4 = this.aE[j * 4 + 3] * fArray[3] + fArray2[3];
            this.aA.a(j, f, f2, f3, f4);
        }
    }

    public void a(float f) {
    }

    public abstract void a(db_2 var1);

    public static int a(jB jB2, int n2) {
        switch (jB2) {
            case AV: {
                return n2 * 2;
            }
            case AW: {
                return 2 + (n2 - 1);
            }
            case AU: {
                return n2;
            }
            case Ba: {
                return n2 * 4;
            }
            case AX: {
                return n2 * 3;
            }
            case AZ: 
            case AY: {
                return 3 + (n2 - 1);
            }
        }
        return 0;
    }

    public float getLineWidth() {
        return this.aC;
    }

    public void b(float f) {
        this.aC = f;
    }

    public boolean ae() {
        return this.aD;
    }

    public void a(boolean bl2) {
        this.aD = bl2;
    }

    protected void delete() {
        super.delete();
        if (this.aA != null) {
            this.aA.HF();
            this.aA = null;
        }
        if (this.az != null) {
            this.az.HF();
            this.az = null;
        }
        this.ah();
    }

    public void af() {
        this.az = null;
        this.aA = null;
        this.aB = jB.AX;
    }

    public void ag() {
        this.delete();
    }

    private void a(VertexBufferPCT vertexBufferPCT) {
        int n2 = vertexBufferPCT.fq();
        if (n2 == 0) {
            this.ah();
            return;
        }
        this.ah();
        FloatBuffer floatBuffer = vertexBufferPCT.yt();
        this.e(n2);
        floatBuffer.get(this.aE);
    }

    private void ah() {
        this.aF = 0;
    }

    protected void e(int n2) {
        int n3 = n2 * 4;
        if (this.aE == null || this.aE.length != n3) {
            float[] fArray = new float[n3];
            if (this.aE != null) {
                System.arraycopy(this.aE, 0, fArray, 0, Math.min(this.aE.length, fArray.length));
                for (int j = 0; j < n3 - this.aE.length; ++j) {
                    fArray[j + this.aE.length] = 0.0f;
                }
            } else {
                Arrays.fill(fArray, 0.0f);
            }
            this.aE = fArray;
        }
        this.aF = n2;
    }
}

