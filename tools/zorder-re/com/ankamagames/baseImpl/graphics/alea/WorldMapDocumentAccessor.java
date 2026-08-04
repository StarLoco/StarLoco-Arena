/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.baseImpl.graphics.alea;

import com.ankamagames.alea.AleaDocumentAccessor;
import com.ankamagames.alea.AleaWorldMap;
import com.ankamagames.baseImpl.graphics.alea.CustomElementProcessor;
import com.ankamagames.baseImpl.graphics.alea.WorldCell;
import com.ankamagames.baseImpl.graphics.alea.WorldElementManager;
import com.ankamagames.baseImpl.graphics.alea.WorldGroupManager;
import com.ankamagames.baseImpl.graphics.alea.WorldMap;
import com.ankamagames.baseImpl.graphics.alea.element.BasicElement;
import com.ankamagames.baseImpl.graphics.alea.element.BrightnessElement;
import com.ankamagames.baseImpl.graphics.alea.element.GraphicalElement;
import com.ankamagames.baseImpl.graphics.alea.element.OffsetElement;
import com.ankamagames.baseImpl.graphics.alea.element.ParticleElement;
import com.ankamagames.baseImpl.graphics.alea.element.TeintElement;
import com.ankamagames.baseImpl.graphics.alea.worldElement.GraphicalWorldElement;
import com.ankamagames.baseImpl.graphics.alea.worldElement.WorldElement;
import com.ankamagames.baseImpl.graphics.alea.worldElement.WorldElementFactory;
import com.ankamagames.framework.fileFormat.document.DocumentContainer;
import com.ankamagames.graphics.isometric.particles.CellParticleSystem;
import com.ankamagames.graphics.isometric.particles.IsoParticleSystemFactory;
import com.ankamagames.graphics.isometric.particles.IsoParticleSystemManager;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;

public class WorldMapDocumentAccessor
extends AleaDocumentAccessor {
    private boolean m_particleActivated;
    private CustomElementProcessor m_customElementHandler;

    public boolean getParticleActivated() {
        return this.m_particleActivated;
    }

    public void setParticleActivated(boolean particleActivated) {
        this.m_particleActivated = particleActivated;
    }

    public WorldMapDocumentAccessor() {
        this.setBasePath("contents/data/maps");
        this.setDocumentExtension(".amw");
        this.setAleaDocumentTypeCode((byte)77);
        this.setAleaDocumentVersion((byte)1);
    }

    public void open(String fileName) throws Exception {
        try {
            super.open(fileName);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void read(DocumentContainer container) {
        super.read(container);
        if (this.m_streamBuffer == null || container == null) {
            return;
        }
        AleaWorldMap map = (AleaWorldMap)container;
        int coordX = this.m_streamBuffer.getInt();
        int coordY = this.m_streamBuffer.getInt();
        byte size = this.m_streamBuffer.get();
        int cellsCount = size * size;
        int coordCellX = coordX * size;
        int coordCellY = coordY * size;
        map.allocateGeometry(coordX, coordY, size, size);
        int j = 0;
        while (j < cellsCount) {
            int worldCellX = coordCellX + j % size;
            int worldCellY = coordCellY + j / size;
            WorldCell cell = (WorldCell)map.getPartitionFromPoint(worldCellX, worldCellY, 0.0f);
            if (cell == null) {
                map.notifyOnLoadError("Bad cell coordinates (" + worldCellX + "; " + worldCellY + ")");
                return;
            }
            this.readCellDatas(cell);
            ++j;
        }
        map.notifyOnLoadComplete();
    }

    private void readCellDatas(WorldCell cell) {
        int levelCount = this.m_streamBuffer.get();
        ArrayList[] levels = new ArrayList[levelCount];
        int levelIndex = 0;
        while (levelIndex < levelCount) {
            int elementCount = this.m_streamBuffer.get();
            if (elementCount > 0) {
                levels[levelIndex] = new ArrayList();
            }
            int elementIndex = 0;
            while (elementIndex < elementCount) {
                byte[] savedParam;
                int elementId = this.m_streamBuffer.getInt();
                byte state = this.m_streamBuffer.get();
                int cellInstanceGroupId = this.m_streamBuffer.getInt();
                int paramCount = this.m_streamBuffer.get();
                if (paramCount > 0) {
                    ByteBuffer byteParam = ByteBuffer.allocate(32);
                    int m = 0;
                    while (m < paramCount) {
                        byte type = this.m_streamBuffer.get();
                        byteParam.put(type);
                        int typeSize = m_typesSize[type];
                        int t = 0;
                        while (t < typeSize) {
                            byte param = this.m_streamBuffer.get();
                            byteParam.put(param);
                            ++t;
                        }
                        ++m;
                    }
                    savedParam = new byte[byteParam.position()];
                    System.arraycopy(byteParam.array(), 0, savedParam, 0, byteParam.position());
                } else {
                    savedParam = new byte[]{};
                }
                BasicElement element = WorldElementManager.getInstance().getElement(elementId);
                if (element != null) {
                    if (element.getType() == 6) {
                        WorldGroupManager.getInstance().registerInstanceGroupInformation(cellInstanceGroupId, savedParam, levelIndex);
                    } else {
                        levels[levelIndex].add(WorldElementFactory.create(element, paramCount, savedParam, state, cellInstanceGroupId));
                    }
                }
                ++elementIndex;
            }
            ++levelIndex;
        }
        if (cell == null || levels == null) {
            return;
        }
        float minOrderPossibleAltitude = 0.0f;
        byte currentAltitude = 0;
        byte oldLevelAltitude = 0;
        float possibleAltitudePad = 1.0E-4f;
        int level = 0;
        while (level < levels.length) {
            byte levelAltitude = currentAltitude;
            ArrayList levelElements = levels[level];
            if (levelElements == null) {
                oldLevelAltitude = levelAltitude;
            } else {
                boolean isLevelPiled = true;
                for (WorldElement element : levelElements) {
                    if (element.getElement().getType() != 8) continue;
                    isLevelPiled = false;
                    break;
                }
                if (!isLevelPiled) {
                    levelAltitude = currentAltitude = oldLevelAltitude;
                }
                float currentBrightness = 0.0f;
                float[] currentTeint = null;
                block16: for (WorldElement element : levelElements) {
                    switch (element.getElement().getType()) {
                        case 4: {
                            currentAltitude = OffsetElement.isAbsolute(element) ? OffsetElement.getOffset(element) : (byte)(currentAltitude + OffsetElement.getOffset(element));
                            levelAltitude = currentAltitude;
                            break;
                        }
                        case 3: {
                            currentTeint = TeintElement.getTeint(element);
                            break;
                        }
                        case 10: {
                            currentBrightness = (float)BrightnessElement.getBrightness(element);
                            break;
                        }
                        case 2: {
                            GraphicalElement graphicalElement = (GraphicalElement)element.getElement();
                            boolean elementPiled = graphicalElement.getStateProperties(element.getState()).isPiled();
                            byte elementAltitude = elementPiled ? currentAltitude : levelAltitude;
                            float elementHeight = graphicalElement.getStateProperties(element.getState()).getHeight();
                            float elementTopAltitude = (float)elementAltitude + elementHeight;
                            float zCellOrderAltitude = minOrderPossibleAltitude > elementTopAltitude ? minOrderPossibleAltitude + possibleAltitudePad : elementTopAltitude;
                            element.addPrecalculatedInformations(level, elementAltitude, currentBrightness, currentTeint, zCellOrderAltitude);
                            element.setCoordinates(cell.getX(), cell.getY(), elementAltitude);
                            cell.addVisualElement((GraphicalWorldElement)element);
                            if (elementPiled) {
                                currentAltitude = (byte)((float)currentAltitude + elementHeight);
                            }
                            minOrderPossibleAltitude = zCellOrderAltitude;
                            break;
                        }
                        case 6: {
                            break;
                        }
                        case 8: {
                            break;
                        }
                        case 1: {
                            break;
                        }
                        case 9: {
                            if (!this.m_particleActivated) continue block16;
                            int id = ParticleElement.getParticleFileId(element);
                            CellParticleSystem particleSystem = IsoParticleSystemFactory.getInstance().getCellParticleSystem(id);
                            if (particleSystem == null) continue block16;
                            particleSystem.setX(cell.getX());
                            particleSystem.setY(cell.getY());
                            particleSystem.setZ(currentAltitude);
                            particleSystem.setLevel(level);
                            IsoParticleSystemManager.getInstance().addCellParticleSystem(particleSystem);
                            break;
                        }
                        default: {
                            float elementTopAltitude = (float)currentAltitude + (float)element.getHeight();
                            float zCellOrderAltitude = minOrderPossibleAltitude > elementTopAltitude ? minOrderPossibleAltitude + possibleAltitudePad : elementTopAltitude;
                            element.addPrecalculatedInformations(level, currentAltitude, currentBrightness, currentTeint, zCellOrderAltitude);
                            element.setCoordinates(cell.getX(), cell.getY(), currentAltitude);
                            cell.addCustomElement(element);
                            if (this.m_customElementHandler != null) {
                                this.m_customElementHandler.onReadCell(cell, element, levels);
                            }
                            minOrderPossibleAltitude = zCellOrderAltitude;
                        }
                    }
                }
                if (isLevelPiled) {
                    oldLevelAltitude = levelAltitude;
                }
            }
            ++level;
        }
        Collections.sort(cell.getVisualElements());
    }

    public DocumentContainer getNewDocumentContainer() {
        return new WorldMap();
    }

    public CustomElementProcessor getCustomElementHandler() {
        return this.m_customElementHandler;
    }

    public void setCustomElementHandler(CustomElementProcessor customElementHandler) {
        this.m_customElementHandler = customElementHandler;
    }
}

