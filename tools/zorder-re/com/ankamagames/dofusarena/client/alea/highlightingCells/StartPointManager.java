/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
package com.ankamagames.dofusarena.client.alea.highlightingCells;

import com.ankamagames.baseImpl.graphics.alea.WorldCell;
import com.ankamagames.baseImpl.graphics.alea.WorldManager;
import com.ankamagames.baseImpl.graphics.alea.WorldMap;
import com.ankamagames.baseImpl.graphics.alea.cellSelector.ElementSelection;
import com.ankamagames.baseImpl.graphics.alea.display.AleaWorldScene;
import com.ankamagames.baseImpl.graphics.alea.worldElement.WorldElement;
import com.ankamagames.dofusarena.client.DofusArenaClientConstants;
import com.ankamagames.dofusarena.client.DofusArenaClientInstance;
import com.ankamagames.dofusarena.client.alea.element.FightStartPointElement;
import com.ankamagames.framework.graphics.opengl.base.BaseTexture;
import com.ankamagames.framework.kernel.core.maths.Point3;
import com.ankamagames.framework.kernel.core.resource.ResourceContext;
import com.ankamagames.framework.kernel.core.resource.ResourceListener;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class StartPointManager
implements ResourceListener {
    private static final Logger m_logger = Logger.getLogger(StartPointManager.class);
    private static final BaseTexture TEXTURE = null;
    private boolean m_activated;
    private List<ElementSelection> m_teamStartPoints;
    private static StartPointManager m_instance = new StartPointManager();

    public StartPointManager() {
        int TEAM_COUNT = 2;
        this.m_teamStartPoints = new ArrayList<ElementSelection>(2);
        int i = 0;
        while (i < 2) {
            this.m_teamStartPoints.add(new ElementSelection(this.getLayerName(i), DofusArenaClientConstants.TEAM_COLOR[i], TEXTURE));
            ++i;
        }
    }

    public static StartPointManager getInstance() {
        return m_instance;
    }

    public void activate(AleaWorldScene scene) {
        if (!this.m_activated) {
            this.m_activated = true;
            WorldManager.getInstance().addListener(this);
            Object[] objectMaps = WorldManager.getInstance().getWorldMaps().getValues();
            int count = 0;
            Object[] objectArray = objectMaps;
            int n = objectMaps.length;
            int n2 = 0;
            while (n2 < n) {
                Object map = objectArray[n2];
                WorldMap worldMap = (WorldMap)map;
                if (map != null) {
                    this.addCells(worldMap.getCells());
                    ++count;
                }
                ++n2;
            }
        }
    }

    public void desactivate() {
        if (this.m_activated) {
            this.m_activated = false;
            WorldManager.getInstance().removeListener(this);
            for (ElementSelection teamStartPointDisplayer : this.m_teamStartPoints) {
                teamStartPointDisplayer.clear();
            }
        }
    }

    public boolean containsTarget(byte teamId, Point3 target) {
        if (this.m_teamStartPoints.size() > teamId && teamId >= 0) {
            return this.m_teamStartPoints.get(teamId).contains(target);
        }
        m_logger.trace((Object)("teamId invalid " + teamId));
        return false;
    }

    public void add(byte teamId, Point3 target) {
        if (this.m_teamStartPoints.size() > teamId && teamId >= 0) {
            this.m_teamStartPoints.get(teamId).add(target);
        } else {
            m_logger.trace((Object)("teamId invalid " + teamId));
        }
    }

    public void addCells(WorldCell[][] cells) {
        if (cells != null) {
            int i = 0;
            while (i < cells.length) {
                int j = 0;
                while (j < cells[i].length) {
                    ArrayList<WorldElement> customCellElements = cells[i][j].getCustomElement();
                    int cellX = cells[i][j].getX();
                    int cellY = cells[i][j].getY();
                    for (ElementSelection teamStartPointDisplayer : this.m_teamStartPoints) {
                        teamStartPointDisplayer.removeAt(cellX, cellY);
                    }
                    for (WorldElement element : customCellElements) {
                        if (element.getElement().getType() != 1000) continue;
                        byte teamId = FightStartPointElement.getTeamId(element);
                        this.add(teamId, new Point3(cellX, cellY, (short)((double)element.getAltitude() + element.getHeight())));
                    }
                    ++j;
                }
                ++i;
            }
            this.refreshHighlight();
        }
    }

    public void removeCells(WorldCell[][] cells) {
        if (cells != null) {
            for (ElementSelection teamStartPointDisplayer : this.m_teamStartPoints) {
                int i = 0;
                while (i < cells.length) {
                    int j = 0;
                    while (j < cells[i].length) {
                        WorldCell cell = cells[i][j];
                        if (cell != null) {
                            teamStartPointDisplayer.removeAt(cell.getX(), cell.getY());
                        }
                        ++j;
                    }
                    ++i;
                }
            }
            this.refreshHighlight();
        }
    }

    public void onResourceContextReloaded(ResourceContext resourceContexts) {
        WorldMap map = (WorldMap)resourceContexts.getResource();
        this.addCells(map.getCells());
    }

    public void onUnloadResourceContext(ResourceContext resourceContexts) {
        WorldMap map = (WorldMap)resourceContexts.getResource();
        this.removeCells(map.getCells());
    }

    private void refreshHighlight() {
        AleaWorldScene scene = DofusArenaClientInstance.getInstance().getWorldScene();
        for (ElementSelection teamStartPointDisplayer : this.m_teamStartPoints) {
            teamStartPointDisplayer.refreshDisplay(scene);
        }
    }

    protected String getLayerName(int id) {
        return "startPoint" + id;
    }
}

