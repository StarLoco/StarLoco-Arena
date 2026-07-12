/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.alea.highlightingCells;

import com.ankamagames.baseImpl.graphics.alea.cellSelector.ElementSelection;
import com.ankamagames.dofusarena.client.DofusArenaClientConstants;
import com.ankamagames.framework.ai.pathfinder.PathFindResult;
import com.ankamagames.framework.kernel.core.maths.Point3;

public class PathDisplaySelection
extends ElementSelection {
    private static final String LAYER_NAME = "pathDisplayer";

    public PathDisplaySelection() {
        super(LAYER_NAME, DofusArenaClientConstants.PATH_COLOR);
    }

    public void setPath(PathFindResult path) {
        this.clear();
        int numCells = path.getPathLength();
        int i = 0;
        while (i < numCells) {
            int[] step = path.getPathStep(i);
            Point3 p = new Point3(step[0], step[1], (short)step[2]);
            this.add(p);
            ++i;
        }
    }
}

