/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.baseImpl.graphics.alea;

import com.ankamagames.alea.AleaWorldCell;
import com.ankamagames.baseImpl.graphics.alea.element.GraphicalElement;
import com.ankamagames.baseImpl.graphics.alea.element.properties.GraphicalElementProperties;
import com.ankamagames.baseImpl.graphics.alea.worldElement.GraphicalWorldElement;
import com.ankamagames.baseImpl.graphics.alea.worldElement.WorldElement;
import com.ankamagames.framework.ai.pathfinder.PathFindMover;
import com.ankamagames.framework.ai.pathfinder.PathFindParameters;
import com.ankamagames.framework.kernel.core.maths.Direction8;
import com.ankamagames.framework.struct.space.Partition;
import java.util.ArrayList;
import java.util.List;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class WorldCell
implements AleaWorldCell {
    private int m_worldX;
    private int m_worldY;
    private ArrayList<GraphicalWorldElement> m_visualElements = new ArrayList();
    private ArrayList<WorldElement> m_customElements = new ArrayList();

    public WorldCell(int worldX, int worldY) {
        this.m_worldX = worldX;
        this.m_worldY = worldY;
    }

    public void setWorldX(int worldX) {
        this.m_worldX = worldX;
    }

    public void setWorldY(int worldY) {
        this.m_worldY = worldY;
    }

    public void addVisualElement(GraphicalWorldElement element) {
        if (this.m_visualElements.size() >= 512) {
            System.err.println("Coordonn\u00e9es de cellules d\u00e9passe la capacit\u00e9 d'un int");
        }
        long handle = this.getHandle() | (long)this.m_visualElements.size();
        element.SetHandle(handle);
        this.m_visualElements.add(element);
    }

    public long getHandle() {
        long ux = (long)this.m_worldX & 0xFFFFFFFL;
        long uy = (long)this.m_worldY & 0xFFFFFFFL;
        if (ux >= 0x20000000L || uy >= 0x20000000L) {
            System.err.println("Coordonn\u00e9es de cellules d\u00e9passe la capacit\u00e9 d'un int");
        }
        return ux << 36 | uy << 8;
    }

    public ArrayList<GraphicalWorldElement> getVisualElements() {
        return this.m_visualElements;
    }

    public void addCustomElement(WorldElement element) {
        this.m_customElements.add(element);
    }

    public ArrayList<WorldElement> getCustomElement() {
        return this.m_customElements;
    }

    public WorldElement getHighestElement() {
        int n = Integer.MIN_VALUE;
        WorldElement highest = null;
        int i = this.m_visualElements.size() - 1;
        while (i >= 0) {
            short s;
            WorldElement elt = this.m_visualElements.get(i);
            if (elt.getCoordinates().getZ() > s) {
                s = elt.getCoordinates().getZ();
                highest = elt;
            }
            --i;
        }
        return highest;
    }

    public List<WorldElement> getElementsAtTop() {
        int altitude = Integer.MIN_VALUE;
        int lastLevel = -1;
        ArrayList<WorldElement> elements = new ArrayList<WorldElement>();
        WorldElement element = this.getHighestElement();
        if (element != null) {
            elements.add(element);
        }
        return elements;
    }

    @Override
    public Partition getPartitionFromPoint(float x, float y, float z) {
        return null;
    }

    @Override
    public void removeAllPartitions() {
    }

    @Override
    public void addPartition(Partition subPartition) {
    }

    @Override
    public void removePartition(Partition subPartition) {
    }

    @Override
    public int getX() {
        return this.m_worldX;
    }

    @Override
    public int getY() {
        return this.m_worldY;
    }

    @Override
    public boolean isLineOfSightValid(short height, Direction8 direction) {
        if (this.m_visualElements == null) {
            return true;
        }
        block12: for (GraphicalWorldElement element : this.getVisualElements()) {
            if (!(element.getHeight() > 0.0) || element.getAltitude() > height || element.getCoordinates().getZ() <= height) continue;
            GraphicalElementProperties properties = element.getElement().getStateProperties(element.getState());
            switch (direction) {
                case SOUTH_EAST: {
                    if (properties.isLineOfSight1()) break;
                    return false;
                }
                case SOUTH_WEST: {
                    if (properties.isLineOfSight3()) break;
                    return false;
                }
                case NORTH_WEST: {
                    if (properties.isLineOfSight5()) break;
                    return false;
                }
                case NORTH_EAST: {
                    if (properties.isLineOfSight7()) break;
                    return false;
                }
                case TOP: {
                    if (properties.isLineOfSightTop()) break;
                    return false;
                }
                case BOTTOM: {
                    if (properties.isLineOfSightBottom()) break;
                    return false;
                }
                case NORTH: {
                    if (properties.isLineOfSight5() && properties.isLineOfSight7()) continue block12;
                    return false;
                }
                case SOUTH: {
                    if (properties.isLineOfSight1() && properties.isLineOfSight3()) continue block12;
                    return false;
                }
                case EAST: {
                    if (properties.isLineOfSight1() && properties.isLineOfSight7()) continue block12;
                    return false;
                }
                case WEST: {
                    if (properties.isLineOfSight3() && properties.isLineOfSight5()) continue block12;
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean isLineOfSightEndValid(short height) {
        if (this.m_visualElements == null) {
            return false;
        }
        ArrayList<GraphicalWorldElement> visualElement = this.getVisualElements();
        int i = visualElement.size() - 1;
        while (i >= 0) {
            GraphicalWorldElement element = (GraphicalWorldElement)visualElement.get(i);
            if (element.getCoordinates().getZ() == height) {
                GraphicalElementProperties properties = element.getElement().getStateProperties(element.getState());
                if (properties.isWalkable()) break;
                return false;
            }
            --i;
        }
        return true;
    }

    @Override
    public short getMaximumAltitude() {
        if (this.m_visualElements != null && this.m_visualElements.size() > 0) {
            GraphicalWorldElement element = this.m_visualElements.get(this.m_visualElements.size() - 1);
            return (short)((double)element.getCoordinates().getZ() + element.getHeight());
        }
        return Short.MIN_VALUE;
    }

    @Override
    public short getArrivalAltitude(PathFindMover mover, short z, Direction8 inputDir, PathFindParameters params) {
        short candidateHeight = 0;
        int n = Integer.MIN_VALUE;
        boolean foundValidCandidate = false;
        for (GraphicalWorldElement element : this.getVisualElements()) {
            short s;
            GraphicalElement graphicalElement = element.getElement();
            short topAltitude = element.getCoordinates().getZ();
            boolean sideBlocking = false;
            switch (inputDir) {
                case NORTH_EAST: {
                    if (graphicalElement.getStateProperties(element.getState()).isMove3()) break;
                    sideBlocking = true;
                    break;
                }
                case NORTH_WEST: {
                    if (graphicalElement.getStateProperties(element.getState()).isMove1()) break;
                    sideBlocking = true;
                    break;
                }
                case SOUTH_EAST: {
                    if (graphicalElement.getStateProperties(element.getState()).isMove5()) break;
                    sideBlocking = true;
                    break;
                }
                case SOUTH_WEST: {
                    if (graphicalElement.getStateProperties(element.getState()).isMove7()) break;
                    sideBlocking = true;
                    break;
                }
                case SOUTH: {
                    if (graphicalElement.getStateProperties(element.getState()).isMove5() && graphicalElement.getStateProperties(element.getState()).isMove7()) break;
                    sideBlocking = true;
                    break;
                }
                case NORTH: {
                    if (graphicalElement.getStateProperties(element.getState()).isMove3() && graphicalElement.getStateProperties(element.getState()).isMove1()) break;
                    sideBlocking = true;
                    break;
                }
                case EAST: {
                    if (graphicalElement.getStateProperties(element.getState()).isMove3() && graphicalElement.getStateProperties(element.getState()).isMove5()) break;
                    sideBlocking = true;
                    break;
                }
                case WEST: {
                    if (graphicalElement.getStateProperties(element.getState()).isMove1() && graphicalElement.getStateProperties(element.getState()).isMove7()) break;
                    sideBlocking = true;
                }
            }
            if (foundValidCandidate && ((!graphicalElement.getStateProperties(element.getState()).isMoveBottom() || sideBlocking) && element.getAltitude() >= candidateHeight && element.getAltitude() - candidateHeight < mover.getHeight() || (!graphicalElement.getStateProperties(element.getState()).isMoveTop() || sideBlocking) && (double)element.getAltitude() + element.getHeight() >= (double)candidateHeight && (double)element.getAltitude() + element.getHeight() - (double)candidateHeight < (double)mover.getHeight())) {
                foundValidCandidate = false;
            }
            if (!foundValidCandidate) {
                short maxZMovement;
                if (!graphicalElement.getStateProperties(element.getState()).isMoveTop() && graphicalElement.getStateProperties(element.getState()).isWalkable() && topAltitude >= s) {
                    maxZMovement = topAltitude < z ? mover.getJumpMaxDescendingHeight() : mover.getJumpMaxAscendingHeight();
                    boolean limitHeightWithJumpCapacity = true;
                    if (params != null) {
                        limitHeightWithJumpCapacity = params.m_limitHeightWithJumpCapacity;
                    }
                    if (Math.abs(topAltitude - z) <= maxZMovement || !limitHeightWithJumpCapacity) {
                        foundValidCandidate = true;
                        candidateHeight = topAltitude;
                        continue;
                    }
                }
                if (!graphicalElement.getStateProperties(element.getState()).isMoveBottom() && !sideBlocking && element.getAltitude() >= s) {
                    maxZMovement = element.getAltitude() < z ? mover.getJumpMaxDescendingHeight() : mover.getJumpMaxAscendingHeight();
                    if (Math.abs(element.getAltitude()) - z <= maxZMovement) {
                        foundValidCandidate = true;
                        candidateHeight = element.getAltitude();
                    }
                }
            }
            if (!sideBlocking) continue;
            s = topAltitude;
        }
        if (!foundValidCandidate) {
            return Short.MIN_VALUE;
        }
        return candidateHeight;
    }

    @Override
    public boolean isWalkable(short z) {
        if (this.m_visualElements != null && this.m_visualElements.size() > 0) {
            for (GraphicalWorldElement element : this.getVisualElements()) {
                if (element.getAltitude() != z) continue;
                return element.getElement().getStateProperties(element.getState()).isWalkable();
            }
        }
        return false;
    }

    @Override
    public boolean getMovementValidity(PathFindMover mover, short z, Direction8 direction) {
        if (this.m_visualElements == null) {
            return false;
        }
        block12: for (GraphicalWorldElement element : this.getVisualElements()) {
            if (!(element.getHeight() > 0.0) || element.getAltitude() < z || !((double)element.getAltitude() + element.getHeight() <= (double)(z + mover.getHeight()))) continue;
            GraphicalElementProperties properties = element.getElement().getStateProperties(element.getState());
            switch (direction) {
                case SOUTH_EAST: {
                    if (properties.isMove1()) break;
                    return false;
                }
                case SOUTH_WEST: {
                    if (properties.isMove3()) break;
                    return false;
                }
                case NORTH_WEST: {
                    if (properties.isMove5()) break;
                    return false;
                }
                case NORTH_EAST: {
                    if (properties.isMove7()) break;
                    return false;
                }
                case TOP: {
                    if (properties.isMoveTop()) break;
                    return false;
                }
                case BOTTOM: {
                    if (properties.isMoveBottom()) break;
                    return false;
                }
                case NORTH: {
                    if (properties.isMove5() && properties.isMove7()) continue block12;
                    return false;
                }
                case SOUTH: {
                    if (properties.isMove1() && properties.isMove3()) continue block12;
                    return false;
                }
                case EAST: {
                    if (properties.isMove1() && properties.isMove7()) continue block12;
                    return false;
                }
                case WEST: {
                    if (properties.isMove3() && properties.isMove5()) continue block12;
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean getMovementAcrossValidity(PathFindMover mover, short incomingZ, Direction8 incomingDir, short outgoingZ, Direction8 outgoingDir, PathFindParameters params) {
        if (this.m_visualElements == null) {
            return true;
        }
        for (GraphicalWorldElement element : this.getVisualElements()) {
            GraphicalElementProperties properties = element.getElement().getStateProperties(element.getState());
            if (element.getHeight() > 0.0 && element.getAltitude() >= incomingZ) {
                switch (incomingDir) {
                    case SOUTH_EAST: {
                        if (properties.isMove1()) break;
                        return false;
                    }
                    case SOUTH_WEST: {
                        if (properties.isMove3()) break;
                        return false;
                    }
                    case NORTH_WEST: {
                        if (properties.isMove5()) break;
                        return false;
                    }
                    case NORTH_EAST: {
                        if (properties.isMove7()) break;
                        return false;
                    }
                }
            }
            if (!(element.getHeight() > 0.0) || element.getAltitude() < outgoingZ) continue;
            switch (outgoingDir) {
                case SOUTH_EAST: {
                    if (properties.isMove5()) break;
                    return false;
                }
                case SOUTH_WEST: {
                    if (properties.isMove7()) break;
                    return false;
                }
                case NORTH_WEST: {
                    if (properties.isMove1()) break;
                    return false;
                }
                case NORTH_EAST: {
                    if (properties.isMove3()) break;
                    return false;
                }
            }
        }
        return true;
    }

    public WorldElement getHighestElement(int levelId) {
        double hh = -1.7976931348623157E308;
        GraphicalWorldElement e = null;
        if (this.m_visualElements != null) {
            for (GraphicalWorldElement element : this.m_visualElements) {
                if (element.getLevel() != levelId || !(element.getWeight() >= hh)) continue;
                hh = element.getWeight() + element.getHeight();
                e = element;
            }
        }
        return e;
    }

    public int getHighestNotEmptyLevel() {
        int level = 0;
        for (GraphicalWorldElement element : this.m_visualElements) {
            if (element.getLevel() <= level) continue;
            level = element.getLevel();
        }
        return level;
    }

    public WorldElement getElementWithTopAtAltitude(short altitude) {
        int i = this.m_visualElements.size() - 1;
        while (i >= 0) {
            WorldElement element = this.m_visualElements.get(i);
            if (element.getCoordinates().getZ() == altitude) {
                return element;
            }
            --i;
        }
        return null;
    }

    public String toString() {
        return "{WorldCell : (" + this.m_worldX + ", " + this.m_worldY + ") @" + Integer.toHexString(this.hashCode()) + "}";
    }
}

