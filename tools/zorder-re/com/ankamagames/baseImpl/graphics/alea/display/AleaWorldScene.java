/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.baseImpl.graphics.alea.display;

import com.ankamagames.alea.AleaWorldCell;
import com.ankamagames.alea.AleaWorldMap;
import com.ankamagames.baseImpl.graphics.alea.WorldCell;
import com.ankamagames.baseImpl.graphics.alea.WorldGroup;
import com.ankamagames.baseImpl.graphics.alea.WorldGroupManager;
import com.ankamagames.baseImpl.graphics.alea.WorldManager;
import com.ankamagames.baseImpl.graphics.alea.adviser.AdviserManager;
import com.ankamagames.baseImpl.graphics.alea.display.AleaIsoCamera;
import com.ankamagames.baseImpl.graphics.alea.display.AleaTextureManager;
import com.ankamagames.baseImpl.graphics.alea.display.CellMeshFactory;
import com.ankamagames.baseImpl.graphics.alea.display.DisplayedCell;
import com.ankamagames.baseImpl.graphics.alea.display.DisplayedElement;
import com.ankamagames.baseImpl.graphics.alea.display.DisplayedElementComparator;
import com.ankamagames.baseImpl.graphics.alea.display.FadeManager;
import com.ankamagames.baseImpl.graphics.alea.display.MaterialFactory;
import com.ankamagames.baseImpl.graphics.alea.element.GraphicalElement;
import com.ankamagames.baseImpl.graphics.alea.mobile.Mobile;
import com.ankamagames.baseImpl.graphics.alea.mobile.MobileManager;
import com.ankamagames.baseImpl.graphics.alea.worldElement.GraphicalWorldElement;
import com.ankamagames.framework.graphics.animation.instances.AnimatedObjectControler;
import com.ankamagames.framework.graphics.opengl.base.animation.AnimatedObject;
import com.ankamagames.framework.graphics.opengl.base.animation.AnimationManager;
import com.ankamagames.framework.graphics.opengl.base.impl.Mesh2D;
import com.ankamagames.framework.graphics.opengl.base.impl.Mesh2DManager;
import com.ankamagames.framework.graphics.opengl.base.render.GLObject;
import com.ankamagames.framework.graphics.opengl.base.states.DefaultScenePostRenderStates;
import com.ankamagames.framework.graphics.opengl.base.states.DefaultScenePreRenderStates;
import com.ankamagames.framework.kernel.core.controllers.KeyboardController;
import com.ankamagames.framework.kernel.core.controllers.MouseController;
import com.ankamagames.framework.kernel.core.maths.Point3;
import com.ankamagames.framework.script.JavaFunctionsLibrary;
import com.ankamagames.framework.script.LuaManager;
import com.ankamagames.framework.sounds.SoundManager;
import com.ankamagames.framework.sounds.group.field.FieldGroup;
import com.ankamagames.framework.sounds.group.field.FieldSourceController;
import com.ankamagames.framework.struct.space.Partition;
import com.ankamagames.graphics.isometric.IsoWorldScene;
import com.ankamagames.graphics.isometric.RenderProcessHandler;
import com.ankamagames.graphics.isometric.highlight.HighLightManager;
import com.ankamagames.graphics.isometric.lights.LightManager;
import com.ankamagames.graphics.isometric.lines.LinesManager;
import com.ankamagames.graphics.isometric.particles.IsoParticleSystemManager;
import com.ankamagames.graphics.isometric.tween.TweenManager;
import gnu.trove.TIntArrayList;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public abstract class AleaWorldScene
extends IsoWorldScene
implements MouseController,
KeyboardController,
Partition,
AnimatedObjectControler {
    public static final String DEFAULT_GFX_PATH = "contents/gfx";
    public static final String DEFAULT_SND_PATH = "contents/snd";
    protected String m_gfxPath = "contents/gfx";
    protected String m_sndPath = "contents/snd";
    public DisplayedCell[] m_displayedCells;
    private int m_mouseX;
    private int m_mouseY;
    private ArrayList<RenderProcessHandler> m_renderProcessHandlers = new ArrayList();
    private boolean m_forceUpdateDisplayCell = false;
    private JavaFunctionsLibrary[] m_animatedObjectActionsFunctionLibraries = null;
    private static float DEFAULT_OUTDOOR_ZOOM_FACTOR = 1.1f;
    protected static float DEFAULT_INDOOR_ZOOM_FACTOR = 1.4f;
    private float m_outdoorZoomFactor = DEFAULT_OUTDOOR_ZOOM_FACTOR;
    private float m_indoorZoomFactor = DEFAULT_INDOOR_ZOOM_FACTOR;

    public AleaWorldScene() {
        this.addRenderProcessHandler(MobileManager.getInstance());
        this.addRenderProcessHandler(IsoParticleSystemManager.getInstance());
        this.addRenderProcessHandler(LightManager.getInstance());
        this.addRenderProcessHandler(HighLightManager.getInstance());
        this.addRenderProcessHandler(LinesManager.getInstance());
        this.addRenderProcessHandler(FadeManager.getInstance());
        this.addRenderProcessHandler(TweenManager.getInstance());
        this.addRenderProcessHandler(AdviserManager.getInstance());
    }

    @Override
    protected void initializeCamera() {
        this.m_isoCamera = new AleaIsoCamera(0.0, 0.0, 0.0, this);
        this.setCamera(this.m_isoCamera);
    }

    @Override
    public AleaIsoCamera getIsoCamera() {
        return (AleaIsoCamera)super.getIsoCamera();
    }

    public void setForceUpdateDisplayCell(boolean forceUpdateDisplayCell) {
        this.m_forceUpdateDisplayCell = forceUpdateDisplayCell;
    }

    public DisplayedCell[] getDisplayedCells() {
        return this.m_displayedCells;
    }

    public DisplayedElement getDisplayedElementAt(Point3 pos) {
        DisplayedElement element = null;
        DisplayedCell[] displayedCellArray = this.m_displayedCells;
        int n = this.m_displayedCells.length;
        int n2 = 0;
        while (n2 < n) {
            DisplayedCell cell = displayedCellArray[n2];
            if (cell != null && cell.getWorldCell() != null && cell.getWorldCell().getX() == pos.getX() && cell.getWorldCell().getY() == pos.getY()) {
                for (DisplayedElement displayedElement : cell.getDisplayedElements()) {
                    if (displayedElement.getWorldElement().getCoordinates().getZ() != pos.getZ()) continue;
                    element = displayedElement;
                }
            }
            ++n2;
        }
        return element;
    }

    public void setAnimatedObjectActionsFunctionLibraries(JavaFunctionsLibrary[] animatedObjectActionsFunctionLibraries) {
        this.m_animatedObjectActionsFunctionLibraries = animatedObjectActionsFunctionLibraries;
    }

    public void setCellCharacteristics(double cellWidth, double cellHeight, int elevationUnit, int screenCellHeightBonus) {
        this.m_cellWidth = cellWidth;
        this.m_cellHeight = cellHeight;
        this.m_elevationUnit = elevationUnit;
        if (this.m_isoCamera != null) {
            this.m_isoCamera.setBottomScreenCellHeightBonus(screenCellHeightBonus);
        }
    }

    public void setGfxPath(String gfxPath) {
        this.m_gfxPath = gfxPath;
        AleaTextureManager.getInstance().setGfxPath(gfxPath);
    }

    public void setSndPath(String sndPath) {
        this.m_sndPath = sndPath;
    }

    public int getMouseX() {
        return this.m_mouseX;
    }

    public int getMouseY() {
        return this.m_mouseY;
    }

    public void addSound(String fileName, float x, float y, float range, float rolloff) {
        FieldGroup field = (FieldGroup)SoundManager.getInstance().getGroupByName(fileName);
        if (field == null) {
            field = new FieldGroup(fileName);
            SoundManager.getInstance().addGroup(field);
            field.setMaxGain(0.4f);
            field.createReferences(String.valueOf(this.m_sndPath) + fileName, true, false, range, rolloff);
        }
        FieldSourceController controller = new FieldSourceController(x, y, 0.0);
        field.addEmitter(controller);
    }

    public void addRenderProcessHandler(RenderProcessHandler renderProcessHandler) {
        this.m_renderProcessHandlers.add(renderProcessHandler);
    }

    public void removeRenderProcessHandler(RenderProcessHandler renderProcessHandler) {
        this.m_renderProcessHandlers.remove(renderProcessHandler);
    }

    @Override
    public void uninitialize() {
        this.clean(true);
        super.uninitialize();
    }

    public void clean(boolean forceUpdate) {
        MobileManager.getInstance().removeAllMobiles();
        IsoParticleSystemManager.getInstance().clearParticleSystems();
        AnimationManager.getInstance().invalidateAllDisplayObjects(this);
        AdviserManager.getInstance().clear();
        WorldManager.getInstance().releaseAllResources();
        WorldManager.getInstance().update();
        WorldManager.getInstance().getDocumentAccessor().setBasePath("");
        Mesh2DManager.getInstance().releaseAllResources();
        Mesh2DManager.getInstance().update();
        this.uninitializeDisplayedCell();
        this.setForceUpdateDisplayCell(forceUpdate);
    }

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        super.init(glAutoDrawable);
        this.setPreRenderStates(new DefaultScenePreRenderStates());
        this.setPostRenderStates(new DefaultScenePostRenderStates());
        MaterialFactory.getInstance().setFileExtension(".png");
        MaterialFactory.getInstance().setGfxPath(this.m_gfxPath);
        if (this.m_isoCamera != null) {
            this.m_isoCamera.setWidthCellCount((int)Math.ceil((double)this.m_frustumWidth / this.getCellWidth()) + 2);
            this.m_isoCamera.setHeightCellCount((int)Math.ceil((double)this.m_frustumHeight / this.getCellHeight()));
            this.initializeDisplayedCell(this.m_isoCamera.getCellCountInView());
        }
        AnimationManager.getInstance().registerScene(this, this, AnimationManager.ProcessType.AUTO_PROCESS);
    }

    @Override
    public void process(long realTime, int frameCount) {
        if (this.m_isoCamera == null) {
            return;
        }
        for (RenderProcessHandler processHandler : this.m_renderProcessHandlers) {
            processHandler.process(this, realTime, frameCount);
        }
        this.m_isoCamera.process(realTime, frameCount);
        int centerScreenIsoWorldX = this.m_isoCamera.getCenterScreenIsoWorldX();
        int centerScreenIsoWorldY = this.m_isoCamera.getCenterScreenIsoWorldY();
        this.updateDisplayedCell(centerScreenIsoWorldX, centerScreenIsoWorldY, this.m_forceUpdateDisplayCell);
        this.removeAllChilds();
        this.prepareSceneBeforeRendering(centerScreenIsoWorldX, centerScreenIsoWorldY);
        this.sort();
        super.process(realTime, frameCount);
    }

    @Override
    public void display(GL gl) {
        super.display(gl);
        CellMeshFactory.getInstance().rewind();
    }

    private void initializeDisplayedCell(int numCellsInView) {
        if (numCellsInView > 0) {
            if (this.m_displayedCells != null) {
                DisplayedCell[] displayedCellArray = this.m_displayedCells;
                int n = this.m_displayedCells.length;
                int n2 = 0;
                while (n2 < n) {
                    DisplayedCell m_displayedCell = displayedCellArray[n2];
                    if (m_displayedCell != null) {
                        m_displayedCell.release();
                    }
                    ++n2;
                }
            }
            this.m_displayedCells = new DisplayedCell[numCellsInView];
            int i = 0;
            while (i < this.m_displayedCells.length) {
                this.m_displayedCells[i] = DisplayedCell.checkOut();
                ++i;
            }
        }
    }

    public void uninitializeDisplayedCell() {
        if (this.m_displayedCells != null) {
            DisplayedCell[] displayedCellArray = this.m_displayedCells;
            int n = this.m_displayedCells.length;
            int n2 = 0;
            while (n2 < n) {
                DisplayedCell m_displayedCell = displayedCellArray[n2];
                if (m_displayedCell != null) {
                    m_displayedCell.release();
                }
                ++n2;
            }
        }
        this.m_displayedCells = null;
    }

    protected void updateDisplayedCell(int centerScreenIsoWorldX, int centerScreenIsoWorldY, boolean bForceUpdate) {
        AleaIsoCamera isoCamera = this.getIsoCamera();
        if (isoCamera.isCameraParametersChanged() || bForceUpdate) {
            AleaWorldMap cameraMap;
            WorldCell cameraCell;
            this.m_forceUpdateDisplayCell = false;
            int cameraX = 0;
            int cameraY = 0;
            if (this.getCameraTarget() != null) {
                cameraX = (int)Math.round(this.getCameraTarget().getWorldX());
                cameraY = (int)Math.round(this.getCameraTarget().getWorldY());
            }
            if ((cameraCell = (WorldCell)(cameraMap = this.getWorldMapFromCellCoordinates(cameraX, cameraY)).getPartitionFromPoint(cameraX, cameraY, 0.0f)) != null) {
                WorldGroup group;
                isoCamera.setCameraGroupInstanceId(0);
                for (GraphicalWorldElement element : cameraCell.getVisualElements()) {
                    GraphicalElement graphicalElement = element.getElement();
                    if (graphicalElement.getStateProperties(element.getState()).isMoveBottom() || element.getGroupInstanceId() == 0 || !(this.getCameraTarget().getAltitude() >= (double)element.getAltitude())) continue;
                    isoCamera.setCameraGroupInstanceId(element.getGroupInstanceId());
                    isoCamera.setCameraGroupLevel(element.getLevel());
                }
                FadeManager.getInstance().clearMaskedLayers();
                if (isoCamera.getCameraGroupInstanceId() > 0 && (group = WorldGroupManager.getInstance().getGroupFromInstance(isoCamera.getCameraGroupInstanceId())) != null) {
                    int instanceLevel = WorldGroupManager.getInstance().getLevelFromInstance(isoCamera.getCameraGroupInstanceId());
                    TIntArrayList maskedLayers = group.getMaskedLayers(isoCamera.getCameraGroupLevel() - instanceLevel);
                    if (maskedLayers != null) {
                        if (maskedLayers.contains(-1)) {
                            this.resetToDefaultIndoorZoomFactor();
                        }
                        int i = 0;
                        while (i < maskedLayers.size()) {
                            int layer = maskedLayers.get(i);
                            FadeManager.getInstance().addMaskedLayers(layer, isoCamera.getCameraGroupInstanceId());
                            ++i;
                        }
                    }
                }
            }
            int isoLocalX = this.m_isoCamera.getIsoLocalX();
            int isoLocalY = this.m_isoCamera.getIsoLocalY();
            int isoOffsetX = this.m_isoCamera.getIsoOffsetX();
            int isoOffsetY = this.m_isoCamera.getIsoOffsetY();
            int heightCellCount = this.m_isoCamera.getHeightCellCount();
            int widthCellCount = this.m_isoCamera.getWidthCellCount();
            int bottomScreenCellHeightBonus = this.m_isoCamera.getBottomScreenCellHeightBonus();
            int topScreenCellHeightBonus = this.m_isoCamera.getTopScreenCellHeightBonus();
            this.initializeDisplayedCell(this.m_isoCamera.getCellCountInView());
            int cellCount = 0;
            int rowCount = 0;
            int rowPair = 0;
            WorldManager.getInstance().beginUpdate();
            int row = -topScreenCellHeightBonus;
            while (row < heightCellCount * 2 + bottomScreenCellHeightBonus) {
                int col = 0;
                while (col < widthCellCount) {
                    int wx = centerScreenIsoWorldX + isoLocalX;
                    int wy = centerScreenIsoWorldY + isoLocalY;
                    DisplayedCell displayedCell = this.m_displayedCells[cellCount];
                    AleaWorldMap map = this.getWorldMapFromCellCoordinates(wx, wy);
                    displayedCell.setWorldCell((WorldCell)map.getPartitionFromPoint(wx, wy, 0.0f));
                    displayedCell.setScreenX(this.isoToScreenX(isoLocalX, isoLocalY));
                    displayedCell.setScreenY(this.isoToScreenY(isoLocalX, isoLocalY));
                    displayedCell.updateDisplayedElements(this, row, heightCellCount, centerScreenIsoWorldX, centerScreenIsoWorldY);
                    ++isoLocalX;
                    --isoLocalY;
                    ++cellCount;
                    ++col;
                }
                rowPair = rowPair == 0 ? 1 : 0;
                isoLocalY = isoOffsetY + ++rowCount / 2;
                isoLocalX = isoOffsetX + rowCount / 2 + rowPair;
                ++row;
            }
            WorldManager.getInstance().endUpdate();
        }
        WorldManager.getInstance().tagLoadedMapsInUse();
    }

    public abstract void addSpecialEffectToMesh(int var1, Mesh2D var2);

    protected void prepareSceneBeforeRendering(int centerScreenIsoWorldX, int centerScreenIsoWorldY) {
        for (RenderProcessHandler processHandler : this.m_renderProcessHandlers) {
            processHandler.prepareBeforeRendering(this, centerScreenIsoWorldX, centerScreenIsoWorldY);
        }
        if (this.m_displayedCells != null) {
            DisplayedCell[] displayedCellArray = this.m_displayedCells;
            int n = this.m_displayedCells.length;
            int n2 = 0;
            while (n2 < n) {
                DisplayedCell cell = displayedCellArray[n2];
                cell.prepareCellBeforeRendering(this);
                ++n2;
            }
        }
    }

    public ArrayList<DisplayedElement> getDisplayedElementsUnderMousePoint(double mouseX, double mouseY, DisplayedElementComparator comparator) {
        if (this.m_isoCamera == null || this.m_displayedCells == null) {
            return null;
        }
        double adjustedMouseX = (mouseX - (double)this.m_frustumWidth / 2.0) / this.m_isoCamera.getZoomFactor() + this.m_isoCamera.getCameraDeltaScreenX();
        double adjustedMouseY = ((double)this.m_frustumHeight / 2.0 - mouseY) / this.m_isoCamera.getZoomFactor() + this.m_isoCamera.getCameraDeltaScreenY();
        ArrayList<DisplayedElement> hitElements = new ArrayList<DisplayedElement>();
        DisplayedCell[] displayedCellArray = this.m_displayedCells;
        int n = this.m_displayedCells.length;
        int n2 = 0;
        while (n2 < n) {
            DisplayedCell cell = displayedCellArray[n2];
            ArrayList<DisplayedElement> cellHitElements = cell.getDisplayedElementsUnderPoint(adjustedMouseX, adjustedMouseY);
            if (cellHitElements.size() != 0) {
                for (DisplayedElement displayedElement : cellHitElements) {
                    displayedElement.calculateDistanceFromTopToMouse(adjustedMouseX, adjustedMouseY);
                    hitElements.add(displayedElement);
                }
            }
            ++n2;
        }
        if (comparator.getComparator() != null) {
            Collections.sort(hitElements, comparator.getComparator());
        }
        return hitElements;
    }

    public ArrayList<Mobile> getMobilesUnderMousePoint(double mouseX, double mouseY) {
        if (this.m_isoCamera == null) {
            return null;
        }
        float adjustedMouseX = (float)(mouseX - (double)(this.m_frustumWidth / 2.0f)) / (float)this.m_isoCamera.getZoomFactor();
        float adjustedMouseY = (float)((double)(this.m_frustumHeight / 2.0f) - mouseY) / (float)this.m_isoCamera.getZoomFactor();
        return MobileManager.getInstance().getMobilesUnderPoint(adjustedMouseX, adjustedMouseY);
    }

    public int selectMobilesUnderMousePoint(double mouseX, double mouseY) {
        if (this.m_isoCamera == null) {
            return 0;
        }
        float adjustedMouseX = (float)(mouseX - (double)(this.m_frustumWidth / 2.0f)) / (float)this.m_isoCamera.getZoomFactor();
        float adjustedMouseY = (float)((double)(this.m_frustumHeight / 2.0f) - mouseY) / (float)this.m_isoCamera.getZoomFactor();
        return MobileManager.getInstance().selectMobilesUnderPoint(adjustedMouseX, adjustedMouseY);
    }

    public float getOutdoorZoomFactor() {
        return this.m_outdoorZoomFactor;
    }

    public float getIndoorZoomFactor() {
        return this.m_indoorZoomFactor;
    }

    public void setOutdoorZoomFactor(float outdoorZoomFactor) {
        this.m_outdoorZoomFactor = outdoorZoomFactor;
    }

    public void setIndoorZoomFactor(float indoorZoomFactor) {
        this.m_indoorZoomFactor = indoorZoomFactor;
    }

    public void resetToDefaultOutdoorZoomFactor() {
        if (this.m_isoCamera != null) {
            this.m_isoCamera.setDesiredZoomFactor(this.m_outdoorZoomFactor);
        }
    }

    public void resetToDefaultIndoorZoomFactor() {
        if (this.m_isoCamera != null) {
            this.m_isoCamera.setDesiredZoomFactor(this.m_indoorZoomFactor);
        }
    }

    @Override
    public void onAnimatedObjectActionFlag(List<String> actions) {
        for (String action : actions) {
            LuaManager.getInstance().runCommand(action, this.m_animatedObjectActionsFunctionLibraries, true);
        }
    }

    public void onAnimatedObjectInvalidate(AnimatedObject displayObject) {
    }

    @Override
    public abstract boolean mouseClicked(MouseEvent var1);

    @Override
    public abstract boolean mousePressed(MouseEvent var1);

    @Override
    public abstract boolean mouseReleased(MouseEvent var1);

    @Override
    public abstract boolean mouseEntered(MouseEvent var1);

    @Override
    public abstract boolean mouseExited(MouseEvent var1);

    @Override
    public abstract boolean mouseDragged(MouseEvent var1);

    @Override
    public boolean mouseMoved(MouseEvent mouseEvent) {
        this.m_mouseX = mouseEvent.getX();
        this.m_mouseY = mouseEvent.getY();
        return false;
    }

    @Override
    public abstract boolean mouseWheelMoved(MouseWheelEvent var1);

    @Override
    public abstract boolean keyTyped(KeyEvent var1);

    @Override
    public abstract boolean keyPressed(KeyEvent var1);

    @Override
    public abstract boolean keyReleased(KeyEvent var1);

    public AleaWorldMap getWorldMapFromCellCoordinates(int x, int y) {
        return WorldManager.getInstance().getMapFromCellCoordinates(x, y);
    }

    public AleaWorldCell getWorldCell(int x, int y) {
        AleaWorldMap map = this.getWorldMapFromCellCoordinates(x, y);
        return (AleaWorldCell)map.getPartitionFromPoint(x, y, 0.0f);
    }

    @Override
    public Partition getPartitionFromPoint(float x, float y, float z) {
        return this.getWorldCell((int)x, (int)y);
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

    public Partition getWorld() {
        return WorldManager.getInstance();
    }

    @Override
    public String toString() {
        int surface = 0;
        for (GLObject mesh : this.m_children) {
            if (!(mesh instanceof Mesh2D)) continue;
            surface = (int)((float)surface + ((Mesh2D)mesh).getWidth() * ((Mesh2D)mesh).getHeight());
        }
        float screenCount = (float)surface / 786432.0f;
        return String.valueOf(surface) + " px, " + screenCount + " \u00e9crans, " + "zoom=" + this.m_isoCamera.getZoomFactor() + ", " + super.toString();
    }
}

