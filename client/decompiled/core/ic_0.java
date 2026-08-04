/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.VertexBufferPCT;
import com.ankamagames.framework.graphics.engine.entity.Entity3D;
import com.ankamagames.framework.graphics.engine.geometry.GeometryMesh;

/*
 * Renamed from IC
 */
final class ic_0
extends fa_0 {
    static final /* synthetic */ boolean bb;

    public void a(Entity3D entity3D, byte by, float f, float f2, float f3, float f4) {
        if (!bb && entity3D.aFz() != 1) {
            throw new AssertionError();
        }
        GeometryMesh geometryMesh = (GeometryMesh)entity3D.ma(0);
        VertexBufferPCT vertexBufferPCT = geometryMesh.ab();
        vertexBufferPCT.b(0, -f3, -f4);
        vertexBufferPCT.b(1, -f3, f4);
        vertexBufferPCT.b(2, f3, -f4);
        vertexBufferPCT.b(3, f3, f4);
    }

    static {
        bb = !fa_0.class.desiredAssertionStatus();
    }
}

