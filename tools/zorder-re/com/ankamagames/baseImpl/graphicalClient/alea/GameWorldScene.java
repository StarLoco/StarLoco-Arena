/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.baseImpl.graphicalClient.alea;

import com.ankamagames.baseImpl.graphicalClient.AbstractGameClientInstance;
import com.ankamagames.baseImpl.graphics.alea.display.AleaWorldScene;
import javax.media.opengl.GLAutoDrawable;

public abstract class GameWorldScene
extends AleaWorldScene {
    protected AbstractGameClientInstance m_gameClientInstance;

    public GameWorldScene(AbstractGameClientInstance gameClientInstance) {
        this.m_gameClientInstance = gameClientInstance;
    }

    public void init(GLAutoDrawable glAutoDrawable) {
        super.init(glAutoDrawable);
        this.m_gameClientInstance.onWorldSceneInitialized();
    }
}

