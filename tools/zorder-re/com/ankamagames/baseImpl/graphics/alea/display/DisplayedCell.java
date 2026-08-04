/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.baseImpl.graphics.alea.display;

import com.ankamagames.baseImpl.graphics.alea.WorldCell;
import com.ankamagames.baseImpl.graphics.alea.WorldGroup;
import com.ankamagames.baseImpl.graphics.alea.WorldGroupManager;
import com.ankamagames.baseImpl.graphics.alea.display.AleaWorldScene;
import com.ankamagames.baseImpl.graphics.alea.display.CellMeshFactory;
import com.ankamagames.baseImpl.graphics.alea.display.DisplayedElement;
import com.ankamagames.baseImpl.graphics.alea.display.FadeManager;
import com.ankamagames.baseImpl.graphics.alea.element.GraphicalElement;
import com.ankamagames.baseImpl.graphics.alea.worldElement.GraphicalWorldElement;
import com.ankamagames.framework.graphics.opengl.base.impl.Mesh2D;
import com.ankamagames.framework.kernel.core.common.MonitoredPool;
import com.ankamagames.framework.kernel.core.common.ObjectFactory;
import com.ankamagames.framework.kernel.core.common.Poolable;
import com.ankamagames.graphics.isometric.highlight.HighLightManager;
import com.ankamagames.graphics.isometric.lights.LightManager;
import com.ankamagames.graphics.isometric.lines.LinesManager;
import gnu.trove.TIntArrayList;
import java.util.ArrayList;
import org.apache.commons.pool.ObjectPool;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class DisplayedCell
implements Poolable {
    private static final ObjectPool m_staticPool = new MonitoredPool(new ObjectFactory<DisplayedCell>(){

        @Override
        public DisplayedCell makeObject() {
            return new DisplayedCell();
        }
    });
    private ArrayList<DisplayedElement> m_hitElements;
    private WorldCell m_worldCell;
    private double m_screenX;
    private double m_screenY;
    private ArrayList<DisplayedElement> m_displayedElements = new ArrayList();

    public DisplayedCell() {
        this.m_hitElements = new ArrayList();
    }

    public void setWorldCell(WorldCell worldCell) {
        this.m_worldCell = worldCell;
    }

    public WorldCell getWorldCell() {
        return this.m_worldCell;
    }

    public double getScreenX() {
        return this.m_screenX;
    }

    public double getScreenY() {
        return this.m_screenY;
    }

    public void setScreenX(double screenX) {
        this.m_screenX = screenX;
    }

    public void setScreenY(double screenY) {
        this.m_screenY = screenY;
    }

    public ArrayList<DisplayedElement> getDisplayedElements() {
        return this.m_displayedElements;
    }

    public void updateDisplayedElements(AleaWorldScene scene, int row, int heightCellCount, int centerScreenIsoWorldX, int centerScreenIsoWorldY) {
        int elevationUnit = (int)Math.floor(scene.getElevationUnit());
        for (DisplayedElement displayedElement : this.m_displayedElements) {
            displayedElement.release();
        }
        this.m_displayedElements.clear();
        if (this.m_worldCell == null) {
            return;
        }
        try {
            for (GraphicalWorldElement element : this.m_worldCell.getVisualElements()) {
                float elementHeightPx;
                GraphicalElement graphicalElement = element.getElement();
                float elementAltitudePx = element.getAltitude() * elevationUnit;
                if (!this.isElementVisibleOnScreen(scene, (int)(elementAltitudePx + (elementHeightPx = graphicalElement.getStateProperties(element.getState()).getHeight() * (float)elevationUnit)), heightCellCount, row)) continue;
                float displayedElementScreenY = (float)this.m_screenY + elementAltitudePx;
                float displayedElementScreenTopY = displayedElementScreenY + elementHeightPx;
                double zValue = (-this.m_screenY + (double)element.getAltitudeOrder()) / (double)scene.getFrustumHeight();
                DisplayedElement displayedElement = DisplayedElement.checkOut();
                displayedElement.setDisplayedCell(this);
                displayedElement.setWorldElement(element);
                displayedElement.setScreenPosition((float)this.m_screenX, displayedElementScreenY, displayedElementScreenTopY);
                displayedElement.setZOrder((float)zValue);
                displayedElement.setAltitude(elementAltitudePx / (float)elevationUnit);
                displayedElement.setBrightness(element.getBrightness());
                displayedElement.setTeint(element.getTeint());
                displayedElement.setState(element.getState());
                displayedElement.setLevel(element.getLevel());
                Mesh2D mesh = CellMeshFactory.getInstance().getMeshFromGraphicalElement(graphicalElement, displayedElement.getState());
                mesh.setScreenPosition(displayedElement.getScreenX(), displayedElement.getScreenY());
                mesh.setZOrder(displayedElement.getZOrder());
                int gfxId = graphicalElement.getStateProperties(element.getState()).getGfxId();
                scene.addSpecialEffectToMesh(gfxId, mesh);
                displayedElement.setMesh(mesh);
                this.m_displayedElements.add(displayedElement);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isElementVisibleOnScreen(AleaWorldScene scene, int elementTopAltitudePx, int heightCellCount, int screenRow) {
        int screenBottomRow = heightCellCount * 2 + scene.getIsoCamera().getTopScreenCellHeightBonus();
        return screenRow <= screenBottomRow || !((double)elementTopAltitudePx < (double)(screenRow - screenBottomRow - 3) * 21.5);
    }

    public void prepareCellBeforeRendering(AleaWorldScene scene) {
        if (this.m_worldCell == null) {
            return;
        }
        for (DisplayedElement displayedElement : this.m_displayedElements) {
            Mesh2D elementMesh = displayedElement.getMesh();
            float brightnessValue = 0.5f + displayedElement.getBrightness() * LightManager.getInstance().getLightContrast();
            float[] teint = displayedElement.getTeint();
            FadeManager.getInstance().apply(displayedElement, brightnessValue, teint);
            int groupInstanceId = displayedElement.getWorldElement().getGroupInstanceId();
            if (groupInstanceId > 0) {
                WorldGroup worldGroup = WorldGroupManager.getInstance().getGroupFromInstance(groupInstanceId);
                int instanceLevel = WorldGroupManager.getInstance().getLevelFromInstance(groupInstanceId);
                if (worldGroup != null) {
                    TIntArrayList maskedLayers = null;
                    if (scene.getIsoCamera().getCameraGroupInstanceId() == 0) {
                        maskedLayers = worldGroup.getMaskedLayers(-1);
                    } else {
                        WorldGroup cameraWorldGroup = WorldGroupManager.getInstance().getGroupFromInstance(scene.getIsoCamera().getCameraGroupInstanceId());
                        TIntArrayList groupMaskedLayers = cameraWorldGroup.getMaskedLayers(displayedElement.getLevel() - instanceLevel);
                        if (groupMaskedLayers == null) {
                            maskedLayers = worldGroup.getMaskedLayers(-1);
                        }
                    }
                    if (maskedLayers != null && maskedLayers.contains(displayedElement.getLevel() - instanceLevel)) {
                        displayedElement.setVisible(false);
                    }
                }
            }
            if (!displayedElement.isVisible()) continue;
            HighLightManager.getInstance().prepareElementBeforeRendering(scene, displayedElement);
            LinesManager.getInstance().prepareElementBeforeRendering(scene, displayedElement);
            LightManager.getInstance().applyLightToMesh(elementMesh, displayedElement.getDisplayedCell().getWorldCell().getX(), displayedElement.getDisplayedCell().getWorldCell().getY(), displayedElement.getAltitude());
            scene.addChild(elementMesh);
        }
    }

    public ArrayList<DisplayedElement> getDisplayedElementsUnderPoint(double x, double y) {
        this.m_hitElements.clear();
        for (DisplayedElement displayedElement : this.m_displayedElements) {
            if (!displayedElement.isVisible() || !displayedElement.rectHitTest(x, y) || !displayedElement.fineHitTest(x, y, 0.2)) continue;
            this.m_hitElements.add(displayedElement);
        }
        return this.m_hitElements;
    }

    public static DisplayedCell checkOut() {
        try {
            return (DisplayedCell)m_staticPool.borrowObject();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void release() {
        try {
            m_staticPool.returnObject(this);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCheckOut() {
    }

    @Override
    public void onCheckIn() {
    }
}

