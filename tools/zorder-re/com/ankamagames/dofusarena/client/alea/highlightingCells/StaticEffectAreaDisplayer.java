/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.alea.highlightingCells;

import com.ankamagames.baseImpl.common.clientAndServer.game.effectArea.BasicEffectArea;
import com.ankamagames.baseImpl.graphics.alea.WorldCell;
import com.ankamagames.baseImpl.graphics.alea.cellSelector.CellSelector;
import com.ankamagames.baseImpl.graphics.alea.cellSelector.ElementSelection;
import com.ankamagames.baseImpl.graphics.alea.cellSelector.ElementSelector;
import com.ankamagames.baseImpl.graphics.alea.cellSelector.elementSelector.ElementSelectorGround;
import com.ankamagames.baseImpl.graphics.alea.display.AleaWorldScene;
import com.ankamagames.baseImpl.graphics.alea.display.DisplayedElement;
import com.ankamagames.baseImpl.graphics.alea.worldElement.WorldElement;
import com.ankamagames.dofusarena.client.DofusArenaClientConstants;
import com.ankamagames.framework.graphics.opengl.base.material.Material;
import com.ankamagames.framework.kernel.core.maths.Direction8;
import com.ankamagames.framework.kernel.core.maths.Point3;
import com.ankamagames.graphics.isometric.highlight.HighLightLayer;
import com.ankamagames.graphics.isometric.highlight.HighLightManager;
import java.util.HashMap;
import java.util.List;

public class StaticEffectAreaDisplayer {
    private static int NEXT_SELECTION_ID = 1;
    private static String PREFIX_SELECTION_NAME = "STATIC_EFFECT";
    private static final int TEAM_COUNT = 2;
    private static final String PREFIX_ACTIVATE_LAYER = "ACTIVATE_LAYER";
    private boolean m_activated;
    private static final float[] STATIC_ZONE_EFFECT_COLOR = new float[]{0.0f, 0.0f, 0.0f, 0.4f};
    private static StaticEffectAreaDisplayer m_instance = new StaticEffectAreaDisplayer();
    protected ElementSelector m_elementSelector = new ElementSelectorGround();
    private HashMap<BasicEffectArea, ElementSelection> m_areaSelections = new HashMap();

    public static StaticEffectAreaDisplayer getInstance() {
        return m_instance;
    }

    private static int getNextSelectionId() {
        if (NEXT_SELECTION_ID == Integer.MAX_VALUE) {
            return 1;
        }
        return NEXT_SELECTION_ID++;
    }

    public boolean isActivated() {
        return this.m_activated;
    }

    public void activate() {
        if (!this.m_activated) {
            try {
                int i = 0;
                while (i < 2) {
                    HighLightLayer layer = HighLightManager.getInstance().createLayer(this.getActivateLayerName(i));
                    Material material = new Material();
                    float[] color = DofusArenaClientConstants.TEAM_COLOR[i];
                    material.setDiffuse(color[0], color[1], color[2], 0.5f);
                    material.setUseDiffuse(true);
                    layer.setMaterial(material);
                    ++i;
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            this.m_activated = true;
        }
    }

    public void deactivate() {
        if (this.m_activated) {
            this.clear();
            int i = 0;
            while (i < 2) {
                HighLightManager.getInstance().removeLayer(this.getActivateLayerName(i));
                ++i;
            }
            this.m_activated = false;
        }
    }

    public void clear() {
        for (ElementSelection selection : this.m_areaSelections.values()) {
            selection.clear();
        }
        this.m_areaSelections.clear();
    }

    public void addStaticEffectArea(BasicEffectArea area, AleaWorldScene scene) {
        if (this.isActivated()) {
            ElementSelection selection = new ElementSelection(String.valueOf(PREFIX_SELECTION_NAME) + StaticEffectAreaDisplayer.getNextSelectionId(), STATIC_ZONE_EFFECT_COLOR);
            selection.add(area.getPosition());
            List<int[]> pattern = area.getArea().getPattern();
            Point3 p = area.getPosition();
            WorldCell centerCell = (WorldCell)scene.getWorldCell(p.getX(), p.getY());
            if (centerCell != null) {
                List<WorldElement> elements = CellSelector.getWorldElements(centerCell, scene, Direction8.NONE, this.m_elementSelector, pattern);
                for (WorldElement element : elements) {
                    selection.add(element.getCoordinates());
                }
            }
            selection.refreshDisplay(scene);
            this.m_areaSelections.put(area, selection);
        }
    }

    public void removeStaticEffectArea(BasicEffectArea area, AleaWorldScene scene) {
        ElementSelection selection;
        if (this.isActivated() && (selection = this.m_areaSelections.get(area)) != null) {
            selection.clear();
            this.m_areaSelections.remove(area);
        }
    }

    private String getActivateLayerName(int teamId) {
        return PREFIX_ACTIVATE_LAYER + teamId;
    }

    public void markElementUse(Point3 target, AleaWorldScene scene) {
        int i = 0;
        while (i < 2) {
            this.markElementUse(i, target, scene);
            ++i;
        }
    }

    public void unmarkElement(Point3 target, AleaWorldScene scene) {
        int i = 0;
        while (i < 2) {
            this.unmarkElement(i, target, scene);
            ++i;
        }
    }

    public void markElementUse(int teamId, Point3 target, AleaWorldScene scene) {
        DisplayedElement element;
        if (this.isActivated() && (element = scene.getDisplayedElementAt(target)) != null) {
            HighLightManager.getInstance().add(element.getLayerReference(), this.getActivateLayerName(teamId));
        }
    }

    public void unmarkElement(int teamId, Point3 target, AleaWorldScene scene) {
        DisplayedElement element;
        if (this.isActivated() && (element = scene.getDisplayedElementAt(target)) != null) {
            HighLightManager.getInstance().remove(element.getLayerReference(), this.getActivateLayerName(teamId));
        }
    }
}

