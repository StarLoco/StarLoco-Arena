/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
package com.ankamagames.baseImpl.graphics.alea.display;

import com.ankamagames.baseImpl.graphics.alea.display.AleaTextureManager;
import com.ankamagames.baseImpl.graphics.alea.element.GraphicalElement;
import com.ankamagames.framework.graphics.opengl.base.impl.Mesh2D;
import com.ankamagames.framework.graphics.opengl.base.impl.Mesh2DManager;
import org.apache.log4j.Logger;

class CellMeshFactory {
    protected static Logger m_logger = Logger.getLogger(CellMeshFactory.class);
    private static final CellMeshFactory m_instance = new CellMeshFactory();
    private Mesh2D[] m_cellsMesh = new Mesh2D[3000];
    private int meshCount = 0;

    private CellMeshFactory() {
        int i = 0;
        while (i < this.m_cellsMesh.length) {
            this.m_cellsMesh[i] = Mesh2DManager.getInstance().getNewMesh();
            ++i;
        }
    }

    public static CellMeshFactory getInstance() {
        return m_instance;
    }

    public Mesh2D getMeshFromGraphicalElement(GraphicalElement graphicalElement, int state) {
        Mesh2D mesh;
        if (!(mesh = this.m_cellsMesh[this.meshCount++]).isInitialized()) {
            mesh.initialize();
        }
        Mesh2DManager.getInstance().tagResourceInUse(mesh);
        float x = graphicalElement.getStateProperties(state).getOriginX();
        float y = graphicalElement.getStateProperties(state).getOriginY();
        mesh.setHotPoint(x, y);
        mesh.setTexture(AleaTextureManager.getInstance().getTextureForCell(graphicalElement.getStateProperties(state).getGfxId()));
        if (mesh.getTexture() == null) {
            m_logger.error((Object)("Impossible de charger la texture " + graphicalElement.getStateProperties(state).getGfxId()));
        }
        mesh.setFlip(graphicalElement.getStateProperties(state).isFlip());
        mesh.computeTextureCoordinate();
        return mesh;
    }

    public void rewind() {
        this.meshCount = 0;
    }
}

