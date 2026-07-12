/*      */ package com.ankamagames.framework.graphics.opengl.base;
/*      */ 
/*      */ import com.ankamagames.framework.graphics.opengl.base.effects.Effect;
/*      */ import com.ankamagames.framework.graphics.opengl.base.effects.EffectContext;
/*      */ import com.ankamagames.framework.graphics.opengl.base.material.BaseMaterial;
/*      */ import com.ankamagames.framework.graphics.opengl.base.material.Material;
/*      */ import com.ankamagames.framework.graphics.opengl.base.matrices.GLMatrix;
/*      */ import com.ankamagames.framework.graphics.opengl.base.render.GLObject;
/*      */ import com.ankamagames.framework.graphics.opengl.base.render.Transformable;
/*      */ import com.ankamagames.framework.graphics.opengl.base.render.ViewPort;
/*      */ import com.ankamagames.framework.graphics.opengl.base.states.GLRenderStates;
/*      */ import com.ankamagames.framework.kernel.core.resource.ManageableResource;
/*      */ import com.ankamagames.framework.kernel.core.resource.ResourceContext;
/*      */ import java.io.PrintStream;
/*      */ import java.nio.FloatBuffer;
/*      */ import java.nio.ShortBuffer;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import javax.media.opengl.GL;
/*      */ import javax.media.opengl.GLAutoDrawable;
/*      */ import org.apache.log4j.Logger;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class Mesh
/*      */   implements GLObject, Transformable, ManageableResource
/*      */ {
/*   33 */   protected static final Logger m_logger = Logger.getLogger(Mesh.class);
/*      */   
/*      */   protected int m_glType;
/*      */   
/*      */   protected BaseMaterial m_material;
/*      */   
/*      */   protected boolean m_materialChanged;
/*      */   
/*      */   protected boolean m_visible;
/*      */   
/*      */   protected boolean m_inheritVisibility;
/*      */   
/*      */   protected GLObject m_parent;
/*      */   
/*      */   protected FloatBuffer m_vertexBuffer;
/*      */   
/*      */   protected FloatBuffer m_vertexColorBuffer;
/*      */   
/*      */   protected FloatBuffer m_texCoordBuffer;
/*      */   
/*      */   protected ShortBuffer m_vertexIndexBuffer;
/*      */   
/*      */   protected int m_currentObjectIndex;
/*      */   
/*      */   protected boolean m_effectEnabled;
/*      */   
/*      */   private boolean m_initialized;
/*      */   private GLRenderStates m_preRenderStates;
/*      */   private GLRenderStates m_postRenderStates;
/*      */   private ArrayList<BaseTexture> m_textures;
/*      */   private ArrayList<BaseMaterial> m_materials;
/*      */   private ArrayList<GLMatrix> m_projectionMatrices;
/*      */   private ArrayList<GLMatrix> m_modelViewMatrices;
/*      */   private ArrayList<GLMatrix> m_textureMatrices;
/*      */   private boolean m_projectionMatrixSaved;
/*      */   private boolean m_modelviewMatrixSaved;
/*      */   private boolean m_textureMatrixSaved;
/*      */   protected ArrayList<GLObject> m_children;
/*      */   private int m_childSelection;
/*      */   private ViewPort m_viewport;
/*      */   private Effect m_effect;
/*      */   private int m_effectResult;
/*      */   private EffectContext m_effectContext;
/*      */   
/*      */   public Mesh()
/*      */   {
/*   79 */     this.m_glType = 4;
/*   80 */     this.m_material = new Material();
/*   81 */     this.m_visible = true;
/*   82 */     this.m_inheritVisibility = true;
/*   83 */     this.m_effectEnabled = true;
/*      */     
/*   85 */     this.m_textures = new ArrayList();
/*   86 */     this.m_materials = new ArrayList();
/*   87 */     this.m_projectionMatrices = new ArrayList();
/*   88 */     this.m_modelViewMatrices = new ArrayList();
/*   89 */     this.m_textureMatrices = new ArrayList();
/*   90 */     this.m_children = new ArrayList();
/*   91 */     this.m_childSelection = 0;
/*      */     
/*   93 */     this.m_parent = null;
/*      */   }
/*      */   
/*      */   public BaseMaterial getMaterial() {
/*   97 */     return this.m_material;
/*      */   }
/*      */   
/*      */   public void setMaterial(Material material) {
/*  101 */     if (this.m_material != material) {
/*  102 */       this.m_material = material;
/*  103 */       this.m_materialChanged = true;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void initialize()
/*      */   {
/*  112 */     setInitialized(true);
/*      */     
/*  114 */     this.m_material = new Material();
/*  115 */     this.m_visible = true;
/*  116 */     this.m_inheritVisibility = true;
/*  117 */     this.m_effectEnabled = true;
/*      */     
/*  119 */     this.m_textures = new ArrayList();
/*  120 */     this.m_materials = new ArrayList();
/*  121 */     this.m_projectionMatrices = new ArrayList();
/*  122 */     this.m_modelViewMatrices = new ArrayList();
/*  123 */     this.m_textureMatrices = new ArrayList();
/*  124 */     this.m_children = new ArrayList();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void uninitialize()
/*      */   {
/*  131 */     setInitialized(false);
/*      */     
/*  133 */     setEffect(null, true);
/*      */     
/*  135 */     for (GLObject child : this.m_children) {
/*  136 */       child.uninitialize();
/*      */     }
/*  138 */     this.m_children.clear();
/*      */     
/*  140 */     this.m_material = null;
/*  141 */     this.m_glType = 0;
/*  142 */     this.m_materialChanged = true;
/*      */     
/*  144 */     this.m_visible = true;
/*  145 */     this.m_inheritVisibility = true;
/*  146 */     this.m_parent = null;
/*      */     
/*  148 */     this.m_preRenderStates = null;
/*  149 */     this.m_postRenderStates = null;
/*  150 */     this.m_textures.clear();
/*  151 */     this.m_materials.clear();
/*  152 */     this.m_projectionMatrices.clear();
/*  153 */     this.m_modelViewMatrices.clear();
/*  154 */     this.m_textureMatrices.clear();
/*      */     
/*  156 */     this.m_projectionMatrixSaved = false;
/*  157 */     this.m_modelviewMatrixSaved = false;
/*  158 */     this.m_textureMatrixSaved = false;
/*      */     
/*  160 */     this.m_viewport = null;
/*      */     
/*  162 */     this.m_effectContext = null;
/*  163 */     this.m_effectResult = 0;
/*      */   }
/*      */   
/*      */   public boolean isInitialized() {
/*  167 */     return this.m_initialized;
/*      */   }
/*      */   
/*      */   public void setInitialized(boolean initialized) {
/*  171 */     this.m_initialized = initialized;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setVisible(boolean visible)
/*      */   {
/*  180 */     this.m_visible = visible;
/*  181 */     if (this.m_inheritVisibility) {
/*  182 */       for (GLObject object : this.m_children)
/*  183 */         object.setVisible(visible);
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean isVisible() {
/*  188 */     return this.m_visible;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setVisibilityInheritance(boolean inherit)
/*      */   {
/*  197 */     this.m_inheritVisibility = inherit;
/*      */   }
/*      */   
/*      */   public FloatBuffer getVertexBuffer() {
/*  201 */     return this.m_vertexBuffer;
/*      */   }
/*      */   
/*      */   public FloatBuffer getVertexColorBuffer() {
/*  205 */     return this.m_vertexColorBuffer;
/*      */   }
/*      */   
/*      */   public FloatBuffer getTexCoordBuffer() {
/*  209 */     return this.m_texCoordBuffer;
/*      */   }
/*      */   
/*      */   public ShortBuffer getVertexIndexBuffer() {
/*  213 */     return this.m_vertexIndexBuffer;
/*      */   }
/*      */   
/*      */   public BaseTexture getTexture() {
/*  217 */     if (!this.m_textures.isEmpty()) {
/*  218 */       return (BaseTexture)this.m_textures.get(0);
/*      */     }
/*  220 */     return null;
/*      */   }
/*      */   
/*      */   public void setTexture(BaseTexture texture) {
/*  224 */     clearTextures();
/*  225 */     pushTextureBack(texture);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setEffectContext(EffectContext context)
/*      */   {
/*  234 */     this.m_effectContext = context;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public EffectContext getEffectContext()
/*      */   {
/*  243 */     return this.m_effectContext;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setEffect(Effect effect, boolean applyToChilds)
/*      */   {
/*  254 */     if (effect != this.m_effect) {
/*  255 */       if (this.m_effect != null) {
/*  256 */         this.m_effect.unbind(this, this.m_effectContext);
/*      */       }
/*  258 */       this.m_effect = effect;
/*      */       
/*  260 */       if (this.m_effect != null) {
/*  261 */         this.m_effectContext = this.m_effect.getNewContext();
/*  262 */         this.m_effect.bind(this, this.m_effectContext);
/*      */       }
/*      */       else {
/*  265 */         this.m_effectContext = null;
/*      */       }
/*      */     }
/*  268 */     if (applyToChilds) {
/*  269 */       for (GLObject child : this.m_children) {
/*  270 */         child.setEffect(effect, applyToChilds);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Effect getEffect()
/*      */   {
/*  281 */     return this.m_effect;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void process(long realTime, int frameCount)
/*      */   {
/*  292 */     if (this.m_effect != null) {
/*  293 */       this.m_effectResult = this.m_effect.process(this, realTime, frameCount);
/*      */     }
/*  295 */     for (int i = 0; i < this.m_children.size(); i++) {
/*  296 */       ((GLObject)this.m_children.get(i)).process(realTime, frameCount);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void processGeometry(GL gl)
/*      */   {
/*  305 */     for (int i = 0; i < this.m_children.size(); i++) {
/*  306 */       ((GLObject)this.m_children.get(i)).processGeometry(gl);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void display(GL gl)
/*      */   {
/*  316 */     applyPreRenderStates(gl);
/*      */     
/*  318 */     applyMaterial();
/*      */     
/*  320 */     for (int i = 0; i < this.m_textures.size(); i++) {
/*  321 */       ((BaseTexture)this.m_textures.get(i)).bind();
/*      */     }
/*      */     
/*  324 */     applyTransformations(gl, 5889);
/*  325 */     applyTransformations(gl, 5888);
/*  326 */     applyTransformations(gl, 5890);
/*      */     
/*  328 */     calculateOnScreenCoordinates(gl);
/*      */     
/*  330 */     if ((this.m_effect != null) && (this.m_effectEnabled)) {}
/*  331 */     switch (this.m_effectResult)
/*      */     {
/*      */     case 0: 
/*  334 */       drawPrimitives(gl);
/*  335 */       for (GLObject child : this.m_children)
/*  336 */         child.display(gl);
/*  337 */       this.m_effect.display(gl, this.m_effectContext);
/*      */       
/*  339 */       break;
/*      */     
/*      */     case 1: 
/*  342 */       this.m_effect.display(gl, this.m_effectContext);
/*  343 */       drawPrimitives(gl);
/*  344 */       for (GLObject child : this.m_children) {
/*  345 */         child.display(gl);
/*      */       }
/*  347 */       break;
/*      */     
/*      */ 
/*      */     case 2: 
/*  351 */       this.m_effect.display(gl, this.m_effectContext);
/*      */       
/*  353 */       break;
/*      */     
/*      */ 
/*      */     case 3: 
/*  357 */       drawPrimitives(gl);
/*  358 */       for (GLObject child : this.m_children) {
/*  359 */         child.display(gl);
/*      */       }
/*  361 */       break;
/*      */     
/*      */ 
/*      */     case 4: 
/*  365 */       this.m_effect.display(gl, this.m_effectContext);
/*  366 */       for (GLObject child : this.m_children) {
/*  367 */         child.display(gl);
/*      */       }
/*      */     
/*      */ 
/*      */     default: 
/*  372 */       break;drawPrimitives(gl);
/*  373 */       for (int i = 0; i < this.m_children.size(); i++) {
/*  374 */         ((GLObject)this.m_children.get(i)).display(gl);
/*      */       }
/*      */     }
/*  377 */     restoreMatrices(gl, 5890);
/*  378 */     restoreMatrices(gl, 5888);
/*  379 */     restoreMatrices(gl, 5889);
/*      */     
/*  381 */     applyPostRenderStates(gl);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void drawPrimitives(GL gl)
/*      */   {
/*  394 */     if (!this.m_visible) {
/*  395 */       return;
/*      */     }
/*  397 */     gl.glVertexPointer(4, 5126, 0, this.m_vertexBuffer);
/*  398 */     gl.glColorPointer(4, 5126, 0, this.m_vertexColorBuffer);
/*  399 */     gl.glTexCoordPointer(2, 5126, 0, this.m_texCoordBuffer);
/*  400 */     gl.glDrawElements(4, this.m_vertexIndexBuffer.limit(), 5123, this.m_vertexIndexBuffer);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setPreRenderStates(GLRenderStates states)
/*      */   {
/*  410 */     this.m_preRenderStates = states;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void applyPreRenderStates(GL gl)
/*      */   {
/*  419 */     if (this.m_preRenderStates != null) {
/*  420 */       this.m_preRenderStates.setup(gl);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setPostRenderStates(GLRenderStates states)
/*      */   {
/*  430 */     this.m_postRenderStates = states;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void applyPostRenderStates(GL gl)
/*      */   {
/*  439 */     if (this.m_postRenderStates != null) {
/*  440 */       this.m_postRenderStates.setup(gl);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void setViewPort(ViewPort viewport)
/*      */   {
/*  448 */     this.m_viewport = viewport;
/*      */   }
/*      */   
/*      */   public ViewPort getViewPort() {
/*  452 */     return this.m_viewport;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void pushMaterialFront(BaseMaterial material)
/*      */   {
/*  462 */     if (material != null) {
/*  463 */       this.m_materials.add(0, material);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void pushMaterialBack(BaseMaterial material)
/*      */   {
/*  473 */     if (material != null) {
/*  474 */       this.m_materials.add(material);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public BaseMaterial popMaterialFront()
/*      */   {
/*  483 */     if (!this.m_materials.isEmpty()) {
/*  484 */       return (BaseMaterial)this.m_materials.remove(0);
/*      */     }
/*  486 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public BaseMaterial popMaterialBack()
/*      */   {
/*  495 */     if (!this.m_materials.isEmpty()) {
/*  496 */       return (BaseMaterial)this.m_materials.remove(this.m_materials.size() - 1);
/*      */     }
/*  498 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void removeMaterial(BaseMaterial material)
/*      */   {
/*  508 */     this.m_materials.remove(material);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void clearMaterials()
/*      */   {
/*  515 */     this.m_materials.clear();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void pushTextureFront(BaseTexture texture)
/*      */   {
/*  524 */     if (texture != null) {
/*  525 */       this.m_textures.add(0, texture);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void pushTextureBack(BaseTexture texture)
/*      */   {
/*  535 */     if (texture != null) {
/*  536 */       this.m_textures.add(texture);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public BaseTexture popTextureFront()
/*      */   {
/*  546 */     if (!this.m_textures.isEmpty()) {
/*  547 */       return (BaseTexture)this.m_textures.remove(0);
/*      */     }
/*  549 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public BaseTexture popTextureBack()
/*      */   {
/*  558 */     if (!this.m_textures.isEmpty()) {
/*  559 */       return (BaseTexture)this.m_textures.remove(this.m_textures.size() - 1);
/*      */     }
/*  561 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void removeTexture(ManagedTexture texture)
/*      */   {
/*  571 */     this.m_textures.remove(texture);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void clearTextures()
/*      */   {
/*  578 */     this.m_textures.clear();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int compareTo(GLObject o)
/*      */   {
/*      */     try
/*      */     {
/*  620 */       float z = o.getSortPosition();
/*  621 */       if (getSortPosition() > z)
/*  622 */         return 1;
/*  623 */       if (getSortPosition() < z)
/*  624 */         return -1;
/*      */     } catch (Exception e) {
/*  626 */       System.err.println("Exception : " + e.getMessage());
/*      */     }
/*  628 */     return 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public float getSortPosition()
/*      */   {
/*  637 */     return 0.0F;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void sort()
/*      */   {
/*  645 */     for (GLObject object : this.m_children) {
/*  646 */       object.sort();
/*      */     }
/*  648 */     Collections.sort(this.m_children);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void addChild(GLObject composable)
/*      */   {
/*  658 */     this.m_children.add(composable);
/*  659 */     composable.setParent(this);
/*  660 */     if (this.m_inheritVisibility) {
/*  661 */       composable.setVisible(this.m_visible);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void removeChild(GLObject composable)
/*      */   {
/*  671 */     if ((this.m_children != null) && (composable != null)) {
/*  672 */       this.m_children.remove(composable);
/*  673 */       composable.setParent(null);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void removeAllChilds()
/*      */   {
/*  682 */     this.m_children.clear();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean hasChild()
/*      */   {
/*  691 */     return !this.m_children.isEmpty();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public GLObject getFirstChild()
/*      */   {
/*  701 */     this.m_childSelection = 0;
/*  702 */     if (this.m_children.isEmpty()) {
/*  703 */       return null;
/*      */     }
/*  705 */     return (GLObject)this.m_children.get(this.m_childSelection);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public GLObject getNextChild()
/*      */   {
/*  714 */     this.m_childSelection += 1;
/*      */     
/*  716 */     if (this.m_childSelection >= this.m_children.size()) {
/*  717 */       return null;
/*      */     }
/*  719 */     return (GLObject)this.m_children.get(this.m_childSelection);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public GLObject getPreviousChild()
/*      */   {
/*  729 */     this.m_childSelection -= 1;
/*  730 */     if (this.m_childSelection < 0) {
/*  731 */       return null;
/*      */     }
/*  733 */     return (GLObject)this.m_children.get(this.m_childSelection);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getChildCount()
/*      */   {
/*  742 */     return this.m_children.size();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public int getTotalChildrenCount()
/*      */   {
/*  749 */     int count = 0;
/*  750 */     for (GLObject mesh : this.m_children) {
/*  751 */       if ((mesh instanceof Mesh)) {
/*  752 */         count += ((Mesh)mesh).getTotalChildrenCount();
/*      */       }
/*      */     }
/*  755 */     return count + getChildCount();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public GLObject getChildAt(int index)
/*      */   {
/*  766 */     if ((index >= 0) && (index < this.m_children.size())) {
/*  767 */       return (GLObject)this.m_children.get(index);
/*      */     }
/*  769 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ArrayList<GLObject> getChildren()
/*      */   {
/*  779 */     return this.m_children;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public GLObject getParent()
/*      */   {
/*  788 */     return this.m_parent;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setParent(GLObject parent)
/*      */   {
/*  798 */     this.m_parent = parent;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void pushMatrixFront(GLMatrix matrix, int transformationModel)
/*      */   {
/*  809 */     switch (transformationModel) {
/*      */     case 5888: 
/*  811 */       this.m_modelViewMatrices.add(0, matrix);
/*  812 */       break;
/*      */     case 5889: 
/*  814 */       this.m_projectionMatrices.add(0, matrix);
/*  815 */       break;
/*      */     case 5890: 
/*  817 */       this.m_textureMatrices.add(0, matrix);
/*      */     }
/*      */     
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void pushMatrixBack(GLMatrix matrix, int transformationModel)
/*      */   {
/*  830 */     switch (transformationModel) {
/*      */     case 5888: 
/*  832 */       this.m_modelViewMatrices.add(matrix);
/*  833 */       break;
/*      */     case 5889: 
/*  835 */       this.m_projectionMatrices.add(matrix);
/*  836 */       break;
/*      */     case 5890: 
/*  838 */       this.m_textureMatrices.add(matrix);
/*      */     }
/*      */     
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public GLMatrix popMatrixFront(int transformationModel)
/*      */   {
/*  850 */     switch (transformationModel) {
/*      */     case 5888: 
/*  852 */       if (this.m_modelViewMatrices.isEmpty()) return null;
/*  853 */       this.m_modelViewMatrices.remove(0);
/*  854 */       break;
/*      */     case 5889: 
/*  856 */       if (this.m_projectionMatrices.isEmpty()) return null;
/*  857 */       this.m_projectionMatrices.remove(0);
/*  858 */       break;
/*      */     case 5890: 
/*  860 */       if (this.m_textureMatrices.isEmpty()) return null;
/*  861 */       this.m_textureMatrices.remove(0);
/*      */     }
/*      */     
/*  864 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public GLMatrix popMatrixBack(int transformationModel)
/*      */   {
/*  874 */     switch (transformationModel) {
/*      */     case 5888: 
/*  876 */       if (this.m_modelViewMatrices.isEmpty()) return null;
/*  877 */       this.m_modelViewMatrices.remove(this.m_modelViewMatrices.size() - 1);
/*  878 */       break;
/*      */     case 5889: 
/*  880 */       if (this.m_projectionMatrices.isEmpty()) return null;
/*  881 */       this.m_projectionMatrices.remove(this.m_projectionMatrices.size() - 1);
/*  882 */       break;
/*      */     case 5890: 
/*  884 */       if (this.m_textureMatrices.isEmpty()) return null;
/*  885 */       this.m_textureMatrices.remove(this.m_textureMatrices.size() - 1);
/*      */     }
/*      */     
/*  888 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void clearMatrices(int transformationModel)
/*      */   {
/*  897 */     switch (transformationModel) {
/*      */     case 5888: 
/*  899 */       this.m_modelViewMatrices.clear();
/*  900 */       break;
/*      */     case 5889: 
/*  902 */       this.m_projectionMatrices.clear();
/*  903 */       break;
/*      */     case 5890: 
/*  905 */       this.m_textureMatrices.clear();
/*      */     }
/*      */     
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void removeMatrix(GLMatrix matrix, int transformationModel)
/*      */   {
/*  918 */     switch (transformationModel) {
/*      */     case 5888: 
/*  920 */       this.m_modelViewMatrices.remove(matrix);
/*  921 */       break;
/*      */     case 5889: 
/*  923 */       this.m_projectionMatrices.remove(matrix);
/*  924 */       break;
/*      */     case 5890: 
/*  926 */       this.m_textureMatrices.remove(matrix);
/*      */     }
/*      */     
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void applyTransformations(GL gl, int transformationModel)
/*      */   {
/*  939 */     switch (transformationModel) {
/*      */     case 5889: 
/*  941 */       if (!this.m_projectionMatrices.isEmpty()) {
/*  942 */         gl.glMatrixMode(5889);
/*  943 */         saveMatrix(gl, 5889);
/*  944 */         for (int i = 0; i < this.m_projectionMatrices.size(); i++)
/*  945 */           ((GLMatrix)this.m_projectionMatrices.get(i)).setup(gl);
/*      */       }
/*      */       break; case 5888:  if ((goto 223) && 
/*      */       
/*  949 */         (!this.m_modelViewMatrices.isEmpty())) {
/*  950 */         gl.glMatrixMode(5888);
/*  951 */         saveMatrix(gl, 5888);
/*  952 */         for (int i = 0; i < this.m_modelViewMatrices.size(); i++)
/*  953 */           ((GLMatrix)this.m_modelViewMatrices.get(i)).setup(gl);
/*      */       }
/*      */       break; case 5890:  if ((goto 223) && 
/*      */       
/*  957 */         (!this.m_textureMatrices.isEmpty())) {
/*  958 */         gl.glMatrixMode(5890);
/*  959 */         saveMatrix(gl, 5890);
/*  960 */         for (int i = 0; i < this.m_textureMatrices.size(); i++) {
/*  961 */           ((GLMatrix)this.m_textureMatrices.get(i)).setup(gl);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */       break;
/*      */     }
/*      */     
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void applyMaterial() {}
/*      */   
/*      */ 
/*      */ 
/*      */   public void saveMatrix(GL gl, int transformationModel)
/*      */   {
/*  981 */     gl.glMatrixMode(transformationModel);
/*  982 */     switch (transformationModel) {
/*      */     case 5888: 
/*  984 */       gl.glPushMatrix();
/*  985 */       this.m_modelviewMatrixSaved = true;
/*  986 */       break;
/*      */     case 5889: 
/*  988 */       gl.glPushMatrix();
/*  989 */       this.m_projectionMatrixSaved = true;
/*  990 */       break;
/*      */     case 5890: 
/*  992 */       gl.glPushMatrix();
/*  993 */       this.m_textureMatrixSaved = true;
/*      */     }
/*      */     
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void restoreMatrices(GL gl, int transformationModel)
/*      */   {
/* 1005 */     gl.glMatrixMode(transformationModel);
/* 1006 */     switch (transformationModel) {
/*      */     case 5888: 
/* 1008 */       if (this.m_modelviewMatrixSaved) {
/* 1009 */         gl.glPopMatrix();
/* 1010 */         this.m_modelviewMatrixSaved = false;
/*      */       }
/* 1012 */       break;
/*      */     case 5889: 
/* 1014 */       if (this.m_projectionMatrixSaved) {
/* 1015 */         gl.glPopMatrix();
/* 1016 */         this.m_projectionMatrixSaved = false;
/*      */       }
/* 1018 */       break;
/*      */     case 5890: 
/* 1020 */       if (this.m_textureMatrixSaved) {
/* 1021 */         gl.glPopMatrix();
/* 1022 */         this.m_textureMatrixSaved = false;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */       break;
/*      */     }
/*      */     
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   protected void calculateOnScreenCoordinates(GL gl) {}
/*      */   
/*      */ 
/*      */ 
/*      */   public void setEffectEnabled(boolean enable)
/*      */   {
/* 1040 */     this.m_effectEnabled = enable;
/* 1041 */     for (GLObject child : this.m_children) {
/* 1042 */       child.setEffectEnabled(enable);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String toString()
/*      */   {
/* 1081 */     return "<<abstract Mesh>>";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void reloadResource(ResourceContext resourceContext) {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void unloadResource(ResourceContext resourceContext) {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public long estimateMemoryUsageInBytes()
/*      */   {
/* 1114 */     int total = 0;
/*      */     
/*      */ 
/*      */ 
/* 1118 */     return total;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void onCheckOut()
/*      */   {
/* 1125 */     if (!isInitialized()) {
/* 1126 */       initialize();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public void onCheckIn()
/*      */   {
/* 1133 */     if (isInitialized()) {
/* 1134 */       uninitialize();
/*      */     }
/*      */   }
/*      */   
/*      */   public void init(GLAutoDrawable glAutoDrawable) {}
/*      */   
/*      */   public void setFrustumSize(float frustumWidth, float frustumHeight) {}
/*      */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\opengl\base\Mesh.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */