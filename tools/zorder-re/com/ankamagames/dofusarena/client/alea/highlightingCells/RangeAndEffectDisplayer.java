/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.alea.highlightingCells;

import com.ankamagames.baseImpl.common.clientAndServer.game.effect.Effect;
import com.ankamagames.baseImpl.common.clientAndServer.game.effect.EffectContainer;
import com.ankamagames.baseImpl.graphics.alea.WorldCell;
import com.ankamagames.baseImpl.graphics.alea.WorldManager;
import com.ankamagames.baseImpl.graphics.alea.WorldMap;
import com.ankamagames.baseImpl.graphics.alea.cellSelector.CellSelector;
import com.ankamagames.baseImpl.graphics.alea.cellSelector.ElementSelection;
import com.ankamagames.baseImpl.graphics.alea.cellSelector.ElementSelector;
import com.ankamagames.baseImpl.graphics.alea.cellSelector.elementSelector.ElementSelectorGround;
import com.ankamagames.baseImpl.graphics.alea.display.AleaWorldScene;
import com.ankamagames.baseImpl.graphics.alea.worldElement.WorldElement;
import com.ankamagames.dofusarena.client.DofusArenaClientInstance;
import com.ankamagames.dofusarena.client.alea.DofusArenaWorldScene;
import com.ankamagames.dofusarena.client.core.game.fighter.Fighter;
import com.ankamagames.dofusarena.common.game.fight.AbstractFight;
import com.ankamagames.framework.ai.targetfinder.aoe.AreaOfEffect;
import com.ankamagames.framework.ai.targetfinder.aoe.AreaOfEffectEnum;
import com.ankamagames.framework.kernel.core.maths.Direction8;
import com.ankamagames.framework.kernel.core.maths.Point3;
import com.ankamagames.framework.kernel.core.maths.Vector3i;
import java.util.List;

public abstract class RangeAndEffectDisplayer {
    protected Fighter m_fighter;
    protected AbstractFight m_fight;
    protected DofusArenaWorldScene m_scene;
    protected boolean m_prepared;
    protected ElementSelection m_zoneEffect;
    protected ElementSelection m_range;
    protected ElementSelection m_rangeWithConstraint;
    protected ElementSelector m_elementSelector = new ElementSelectorGround();

    public RangeAndEffectDisplayer(String rangeName, float[] rangeColor, String zoneEffectName, float[] zoneEffectColor, String rangeWithConstraintName, float[] rangeWithConstraintColor) {
        this.m_range = new ElementSelection(rangeName, rangeColor);
        this.m_rangeWithConstraint = new ElementSelection(rangeWithConstraintName, rangeWithConstraintColor);
        this.m_zoneEffect = new ElementSelection(zoneEffectName, zoneEffectColor);
    }

    public void clearRange() {
        this.clearZoneEffect();
        this.m_range.clear();
        this.m_rangeWithConstraint.clear();
        this.m_range.refreshDisplay(DofusArenaClientInstance.getInstance().getWorldScene());
        this.m_rangeWithConstraint.refreshDisplay(DofusArenaClientInstance.getInstance().getWorldScene());
    }

    public void clearZoneEffect() {
        this.m_zoneEffect.clear();
        this.m_zoneEffect.refreshDisplay(DofusArenaClientInstance.getInstance().getWorldScene());
    }

    public void selectZoneEffect(EffectContainer effects, Fighter fighter, Point3 target, AleaWorldScene scene) {
        this.m_zoneEffect.clear();
        if (effects != null) {
            WorldCell centerCell = (WorldCell)scene.getWorldCell(target.getX(), target.getY());
            Direction8 direction = Vector3i.getDirection4FromVector(target.getX() - fighter.getPosition().getX(), target.getY() - fighter.getPosition().getY());
            for (Effect effect : effects) {
                AreaOfEffect aoe = effect.getAreaOfEffect();
                if (aoe.getType() == AreaOfEffectEnum.EMPTY) {
                    if (this.m_zoneEffect.contains(target)) continue;
                    this.m_zoneEffect.add(target);
                    continue;
                }
                List<int[]> pattern = aoe.getPattern();
                List<WorldElement> elements = CellSelector.getWorldElements(centerCell, scene, direction, this.m_elementSelector, pattern);
                for (WorldElement elt : elements) {
                    if (this.m_zoneEffect.contains(elt.getCoordinates())) continue;
                    this.m_zoneEffect.add(elt.getCoordinates());
                }
            }
        } else {
            this.m_zoneEffect.add(target);
        }
        this.m_zoneEffect.refreshDisplay(scene);
    }

    public boolean rangeContains(Point3 target) {
        return this.m_range.contains(target) || this.m_rangeWithConstraint.contains(target);
    }

    protected void selectRange(Fighter fighter, DofusArenaWorldScene scene) {
        Object[] worldMaps;
        this.clearRange();
        this.m_fighter = fighter;
        this.m_fight = fighter.getCurrentFight();
        this.m_scene = scene;
        Object[] objectArray = worldMaps = WorldManager.getInstance().getWorldMaps().getValues();
        int n = worldMaps.length;
        int n2 = 0;
        while (n2 < n) {
            Object objectMap = objectArray[n2];
            WorldMap map = (WorldMap)objectMap;
            WorldCell[][] cells = map.getCells();
            if (cells != null) {
                int x = 0;
                while (x < cells.length) {
                    int y = 0;
                    while (y < cells.length) {
                        WorldCell cell = cells[x][y];
                        if (cell != null) {
                            List<WorldElement> elements = cell.getElementsAtTop();
                            for (WorldElement element : elements) {
                                switch (this.checkValidity(element)) {
                                    case OK: {
                                        this.m_range.add(element.getCoordinates());
                                        break;
                                    }
                                    case OK_WITH_CONSTRAINTS: {
                                        this.m_rangeWithConstraint.add(element.getCoordinates());
                                    }
                                }
                            }
                        }
                        ++y;
                    }
                    ++x;
                }
            }
            ++n2;
        }
        this.m_range.refreshDisplay(scene);
        this.m_rangeWithConstraint.refreshDisplay(scene);
        this.m_fighter = null;
        this.m_fight = null;
        this.m_scene = null;
    }

    protected abstract RangeValidity checkValidity(WorldElement var1);

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static enum RangeValidity {
        OK,
        OK_WITH_CONSTRAINTS,
        INVALID;

    }
}

