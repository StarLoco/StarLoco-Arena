/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.VertexBufferPCT;
import com.ankamagames.framework.graphics.engine.entity.Entity3D;
import com.ankamagames.framework.graphics.engine.geometry.GeometryMesh;

final class IA
extends fa_0 {
    static final /* synthetic */ boolean bb;

    public void a(Entity3D entity3D, byte by, float f, float f2, float f3, float f4) {
        if (!bb && entity3D.aFz() != 1) {
            throw new AssertionError();
        }
        GeometryMesh geometryMesh = (GeometryMesh)entity3D.ma(0);
        VertexBufferPCT vertexBufferPCT = geometryMesh.ab();
        if (by != 0) {
            float f5 = ej_0.aq((int)(f3 * 2.0f));
            float f6 = ej_0.aq((int)(f4 * 2.0f));
            float f7 = f3 * 2.0f / f5;
            float f8 = f4 * 2.0f / f6;
            float f9 = 1.0f;
            float f10 = -1.0f * f4 + ((by & 1) == 1 ? f : 0.0f);
            float f11 = -1.0f * f4 + f2 * 0.5f + ((by & 2) == 2 ? f : 0.0f);
            float f12 = -1.0f * f4 + ((by & 4) == 4 ? f : 0.0f);
            float f13 = -1.0f * f4 + -f2 * 0.5f + ((by & 8) == 8 ? f : 0.0f);
            vertexBufferPCT.b(0, -f2, f10);
            vertexBufferPCT.b(1, 0.0f, f11);
            vertexBufferPCT.b(2, f2, f12);
            vertexBufferPCT.b(3, 0.0f, f13);
            vertexBufferPCT.a(0, 0.5f * f7, 0.0f);
            vertexBufferPCT.a(1, 1.0f * f7, 0.5f * f8);
            vertexBufferPCT.a(2, 0.5f * f7, 1.0f * f8);
            vertexBufferPCT.a(3, 0.0f, 0.5f * f8);
        } else {
            vertexBufferPCT.b(0, -f3, f4);
            vertexBufferPCT.b(1, -f3, -f4);
            vertexBufferPCT.b(2, f3, -f4);
            vertexBufferPCT.b(3, f3, f4);
        }
    }

    static {
        bb = !fa_0.class.desiredAssertionStatus();
    }
}

