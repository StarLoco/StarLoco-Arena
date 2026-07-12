/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.alea;

import com.ankamagames.baseImpl.graphicalClient.AbstractGameClientInstance;
import com.ankamagames.baseImpl.graphicalClient.alea.GameWorldScene;
import com.ankamagames.baseImpl.graphicalClient.script.MobileFunctionsLibrary;
import com.ankamagames.baseImpl.graphicalClient.script.SoundFunctionsLibrary;
import com.ankamagames.dofusarena.client.alea.highlightingCells.StartPointManager;
import com.ankamagames.dofusarena.client.alea.highlightingCells.StaticEffectAreaDisplayer;
import com.ankamagames.dofusarena.client.core.DofusArenaConfiguration;
import com.ankamagames.dofusarena.client.ui.protocol.message.worldScene.UIWorldSceneMouseMovedMessage;
import com.ankamagames.dofusarena.client.ui.protocol.message.worldScene.UIWorldSceneMouseReleasedMessage;
import com.ankamagames.framework.fileFormat.properties.PropertyException;
import com.ankamagames.framework.graphics.opengl.base.effects.EffectManager;
import com.ankamagames.framework.graphics.opengl.base.impl.Mesh2D;
import com.ankamagames.framework.kernel.core.common.message.Worker;
import com.ankamagames.framework.script.JavaFunctionsLibrary;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class DofusArenaWorldScene
extends GameWorldScene {
    private boolean m_dispatchMouseMovedMessage = false;

    public DofusArenaWorldScene(AbstractGameClientInstance gameClientInstance) {
        super(gameClientInstance);
        this.setAnimatedObjectActionsFunctionLibraries(new JavaFunctionsLibrary[]{SoundFunctionsLibrary.getInstance(), MobileFunctionsLibrary.getInstance()});
    }

    public boolean isDispatchMouseMovedMessage() {
        return this.m_dispatchMouseMovedMessage;
    }

    public void setDispatchMouseMovedMessage(boolean dispatchMouseMovedMessage) {
        this.m_dispatchMouseMovedMessage = dispatchMouseMovedMessage;
    }

    public void clean(boolean forceUpdate) {
        super.clean(forceUpdate);
        StartPointManager.getInstance().desactivate();
        StaticEffectAreaDisplayer.getInstance().deactivate();
    }

    public boolean keyPressed(KeyEvent keyEvent) {
        return false;
    }

    public boolean keyReleased(KeyEvent keyEvent) {
        return false;
    }

    public boolean keyTyped(KeyEvent keyEvent) {
        return false;
    }

    public boolean mouseClicked(MouseEvent mouseEvent) {
        return false;
    }

    public boolean mouseDragged(MouseEvent mouseEvent) {
        return this.mouseMoved(mouseEvent);
    }

    public boolean mouseEntered(MouseEvent mouseEvent) {
        return false;
    }

    public boolean mouseMoved(MouseEvent mouseEvent) {
        super.mouseMoved(mouseEvent);
        this.selectMobilesUnderMousePoint(mouseEvent.getX(), mouseEvent.getY());
        if (this.m_dispatchMouseMovedMessage) {
            UIWorldSceneMouseMovedMessage message = UIWorldSceneMouseMovedMessage.checkOut();
            message.setMouseX(mouseEvent.getX());
            message.setMouseY(mouseEvent.getY());
            Worker.getInstance().pushMessage(message);
        }
        return false;
    }

    public boolean mouseExited(MouseEvent mouseEvent) {
        return false;
    }

    public boolean mousePressed(MouseEvent mouseEvent) {
        return false;
    }

    public boolean mouseReleased(MouseEvent mouseEvent) {
        UIWorldSceneMouseReleasedMessage message = UIWorldSceneMouseReleasedMessage.checkOut();
        message.setMouseButton(mouseEvent.getButton());
        message.setMouseX(mouseEvent.getX());
        message.setMouseY(mouseEvent.getY());
        Worker.getInstance().pushMessage(message);
        return true;
    }

    public boolean mouseWheelMoved(MouseWheelEvent mouseEvent) {
        this.setDesiredZoomFactor(this.getDesiredZoomFactor() - (double)((float)mouseEvent.getWheelRotation() * 0.1f));
        return false;
    }

    public void addSpecialEffectToMesh(int gfxId, Mesh2D mesh) {
        try {
            if (DofusArenaConfiguration.getInstance().getBoolean("activateMapVisualEffect")) {
                switch (gfxId) {
                    case 461: 
                    case 462: 
                    case 463: {
                        mesh.setEffect(EffectManager.getInstance().getEffect("sea"), false);
                        break;
                    }
                    default: {
                        mesh.setEffect(null, false);
                        break;
                    }
                }
            }
        }
        catch (PropertyException e) {
            e.printStackTrace();
        }
    }
}

