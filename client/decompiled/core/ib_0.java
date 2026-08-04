/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.VertexBufferPCT;
import com.ankamagames.framework.graphics.engine.entity.Entity3D;
import com.ankamagames.framework.graphics.engine.geometry.GeometryMesh;

/*
 * Renamed from IB
 */
final class ib_0
extends fa_0 {
    static final /* synthetic */ boolean bb;

    public void a(Entity3D entity3D, byte by, float f, float f2, float f3, float f4) {
        if (!bb && entity3D.aFz() != 1) {
            throw new AssertionError();
        }
        GeometryMesh geometryMesh = (GeometryMesh)entity3D.ma(0);
        VertexBufferPCT vertexBufferPCT = geometryMesh.ab();
        vertexBufferPCT.b(0, -f2, (by & 1) == 1 ? f : 0.0f);
        vertexBufferPCT.b(1, 0.0f, f2 * 0.5f + ((by & 2) == 2 ? f : 0.0f));
        vertexBufferPCT.b(2, f2, (by & 4) == 4 ? f : 0.0f);
        vertexBufferPCT.b(3, 0.0f, -f2 * 0.5f + ((by & 8) == 8 ? f : 0.0f));
    }

    static {
        bb = !fa_0.class.desiredAssertionStatus();
    }
}

