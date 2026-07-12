/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.framework.graphics.opengl.base.matrices.transformation3D;

import com.ankamagames.framework.graphics.opengl.base.matrices.transformation3D.Position3D;
import javax.media.opengl.GL;

public class HotSpot3D
extends Position3D {
    public void setup(GL gl) {
        if (this.m_x != 0.0f || this.m_y != 0.0f || this.m_z != 0.0f) {
            gl.glTranslatef(-this.m_x, this.m_y, this.m_z);
        }
    }
}

