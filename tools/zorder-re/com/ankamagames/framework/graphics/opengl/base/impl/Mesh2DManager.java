/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.framework.graphics.opengl.base.impl;

import com.ankamagames.framework.graphics.opengl.base.impl.Mesh2D;
import com.ankamagames.framework.kernel.core.resource.ContextFactory;
import com.ankamagames.framework.kernel.core.resource.ResourceContext;
import com.ankamagames.framework.kernel.core.resource.ResourceFactory;
import com.ankamagames.framework.kernel.core.resource.SingleResourceManager;

public class Mesh2DManager
extends SingleResourceManager {
    private static final Mesh2DManager m_instance = new Mesh2DManager();

    private Mesh2DManager() {
        super(new ResourceFactory<Mesh2D>(){

            @Override
            public Mesh2D makeObject() {
                return new Mesh2D();
            }
        }, new ContextFactory<Mesh2D.Mesh2DResourceContext>(){

            @Override
            public Mesh2D.Mesh2DResourceContext makeObject() {
                return new Mesh2D.Mesh2DResourceContext();
            }
        }, true);
    }

    public static Mesh2DManager getInstance() {
        return m_instance;
    }

    public Mesh2D getNewMesh() {
        ResourceContext context = this.getNewResource();
        if (context != null) {
            return (Mesh2D)context.getResource();
        }
        return null;
    }
}

