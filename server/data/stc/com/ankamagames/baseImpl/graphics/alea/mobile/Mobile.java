/*      */ package com.ankamagames.baseImpl.graphics.alea.mobile;
/*      */ 
/*      */ import com.ankamagames.baseImpl.graphics.alea.display.AleaWorldScene;
/*      */ import com.ankamagames.baseImpl.graphics.alea.mobile.states.DefaultMobilePostRenderState;
/*      */ import com.ankamagames.baseImpl.graphics.alea.mobile.states.DefaultMobilePreRenderState;
/*      */ import com.ankamagames.framework.graphics.animation.descriptors.DisplayObjectDescriptor;
/*      */ import com.ankamagames.framework.graphics.animation.descriptors.library.AbstractDescriptorLibrary;
/*      */ import com.ankamagames.framework.graphics.animation.descriptors.library.BaseDescriptorLibrary;
/*      */ import com.ankamagames.framework.graphics.animation.descriptors.library.DescriptorLibraryManager;
/*      */ import com.ankamagames.framework.graphics.animation.descriptors.library.DisplayObjectListener;
/*      */ import com.ankamagames.framework.graphics.animation.descriptors.library.ModifiableDescriptorLibrary;
/*      */ import com.ankamagames.framework.graphics.animation.instances.AnimatedObjectControler;
/*      */ import com.ankamagames.framework.graphics.animation.instances.DisplayObject;
/*      */ import com.ankamagames.framework.graphics.opengl.base.animation.AnimationManager;
/*      */ import com.ankamagames.framework.graphics.opengl.base.effects.Effect;
/*      */ import com.ankamagames.framework.graphics.opengl.base.impl.HitTestableMesh2D;
/*      */ import com.ankamagames.framework.graphics.opengl.base.material.Material;
/*      */ import com.ankamagames.framework.graphics.opengl.base.states.GLRenderStates;
/*      */ import com.ankamagames.framework.graphics.sba.IndexedDefinitionTagBuffer;
/*      */ import com.ankamagames.framework.kernel.core.maths.Direction8;
/*      */ import com.ankamagames.framework.kernel.core.maths.Point3;
/*      */ import com.ankamagames.framework.script.JavaFunctionEx;
/*      */ import com.ankamagames.framework.script.JavaFunctionsLibrary;
/*      */ import com.ankamagames.framework.script.LuaManager;
/*      */ import com.ankamagames.framework.script.LuaScriptParameterDescriptor;
/*      */ import com.ankamagames.graphics.effects.OutlineEffect;
/*      */ import com.ankamagames.graphics.isometric.IsoWorldTarget;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.PrintStream;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map.Entry;
/*      */ import java.util.Set;
/*      */ import org.apache.log4j.Logger;
/*      */ import org.keplerproject.luajava.LuaException;
/*      */ import org.keplerproject.luajava.LuaState;
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
/*      */ public class Mobile
/*      */   implements IsoWorldTarget, AnimatedObjectControler
/*      */ {
/*      */   public static final short DEFAULT_MOBILE_HEIGHT = 8;
/*      */   public static final String ANIMATION_PREFIX = "Anim";
/*      */   public static final String DEFAULT_ANIMATION_STATIC = "AnimStatique";
/*      */   public static final String DEFAULT_CARRY_ANNIMATION = "Porteur";
/*   74 */   public static final GLRenderStates DEFAULT_PRE_RENDERSTATE = new DefaultMobilePreRenderState();
/*   75 */   public static final GLRenderStates DEFAULT_POST_RENDERSTATE = new DefaultMobilePostRenderState();
/*      */   
/*   77 */   protected static final Logger m_logger = Logger.getLogger(Mobile.class);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private long m_id;
/*      */   
/*      */ 
/*      */ 
/*      */   private ModifiableDescriptorLibrary m_descriptorLibrary;
/*      */   
/*      */ 
/*      */ 
/*   90 */   private boolean m_descriptorLibraryChanged = false;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected DisplayObject m_displayObject;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private int m_lastLibraryChangeRevision;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  106 */   protected boolean m_visible = true;
/*  107 */   protected boolean m_visibleChanged = true;
/*  108 */   protected float m_alpha = 1.0F;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  113 */   private GLRenderStates m_preRenderState = DEFAULT_PRE_RENDERSTATE;
/*  114 */   private GLRenderStates m_postRenderState = DEFAULT_POST_RENDERSTATE;
/*      */   
/*      */ 
/*      */   private Effect m_effect;
/*      */   
/*      */ 
/*  120 */   private boolean m_applyEffectTochild = false;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  125 */   private String m_animation = "AnimStatique";
/*  126 */   private boolean m_animationChanged = false;
/*  127 */   private float m_animationSpeed = 1.0F;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  132 */   private String m_animationSuffix = null;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  137 */   private String m_staticAnimationKey = "AnimStatique";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  142 */   private Direction8 m_direction = Direction8.SOUTH_EAST;
/*  143 */   private boolean m_directionChanged = false;
/*      */   
/*      */ 
/*      */   protected double m_worldX;
/*      */   
/*      */   protected double m_worldY;
/*      */   
/*  150 */   protected double m_altitude = 0.0D;
/*      */   
/*  152 */   private short m_visualHeight = 8;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  157 */   protected boolean m_selected = false;
/*  158 */   protected boolean m_selectedChanged = false;
/*  159 */   protected ArrayList<MobileSelectionChangeListener> m_selectionChangedListeners = new ArrayList();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  164 */   private float m_scale = 1.0F;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  170 */   protected HashMap<String, Material> m_customLinkageMaterials = new HashMap();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  175 */   private String m_carryAnimation = "Porteur";
/*      */   
/*      */ 
/*      */ 
/*      */   private Mobile m_carriedMobile;
/*      */   
/*      */ 
/*      */   private Mobile m_carrierMobile;
/*      */   
/*      */ 
/*      */   private JavaFunctionsLibrary[] m_functionsLibraries;
/*      */   
/*      */ 
/*      */ 
/*      */   public Mobile(long id)
/*      */   {
/*  191 */     this(id, 0.0D, 0.0D);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Mobile(long id, double worldX, double worldY, double altitude)
/*      */   {
/*  203 */     this.m_id = id;
/*  204 */     this.m_worldX = worldX;
/*  205 */     this.m_worldY = worldY;
/*  206 */     this.m_altitude = altitude;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Mobile(long id, double worldX, double worldY)
/*      */   {
/*  217 */     this(id, worldX, worldY, 0.0D);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void setId(long id)
/*      */   {
/*  224 */     this.m_id = id;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public long getId()
/*      */   {
/*  231 */     return this.m_id;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setDescriptorLibrary(ModifiableDescriptorLibrary descriptorLibrary)
/*      */   {
/*  242 */     this.m_descriptorLibrary = descriptorLibrary;
/*  243 */     this.m_descriptorLibraryChanged = true;
/*  244 */     applyAllCustomMaterials();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public ModifiableDescriptorLibrary getDescriptorLibrary()
/*      */   {
/*  251 */     return this.m_descriptorLibrary;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setDescriptorLibraryFromFile(String fileName)
/*      */     throws Exception
/*      */   {
/*  262 */     BaseDescriptorLibrary baseLibrary = DescriptorLibraryManager.getInstance().getDescriptorLibrary(fileName);
/*  263 */     if (baseLibrary != null) {
/*  264 */       setDescriptorLibrary(new ModifiableDescriptorLibrary(baseLibrary));
/*  265 */       setAnimation(this.m_staticAnimationKey);
/*      */     } else {
/*  267 */       throw new FileNotFoundException("Le fichier " + fileName + " est introuvable !");
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setAnimation(String animation)
/*      */   {
/*  277 */     if (!this.m_animation.equals(animation)) {
/*  278 */       this.m_animation = animation;
/*  279 */       forceReloadAnimation();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getAnimation()
/*      */   {
/*  287 */     return this.m_animation;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getAnimationSuffix()
/*      */   {
/*  294 */     return this.m_animationSuffix;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void setAnimationSuffix(String animationSuffix)
/*      */   {
/*  301 */     this.m_animationSuffix = animationSuffix;
/*  302 */     forceReloadAnimation();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void forceReloadAnimation()
/*      */   {
/*  309 */     this.m_animationChanged = true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getStaticAnimationKey()
/*      */   {
/*  316 */     return this.m_staticAnimationKey;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void setStaticAnimationKey(String animationStatic)
/*      */   {
/*  323 */     this.m_staticAnimationKey = animationStatic;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setDirection(Direction8 direction)
/*      */   {
/*  332 */     if (this.m_direction != direction) {
/*  333 */       int delta = direction.getIndex() - this.m_direction.getIndex();
/*  334 */       this.m_direction = direction;
/*  335 */       this.m_directionChanged = true;
/*  336 */       if (this.m_carriedMobile != null) {
/*  337 */         this.m_carriedMobile.setDirection(this.m_carriedMobile.getDirection().getNextDirection8(delta));
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public Direction8 getDirection()
/*      */   {
/*  346 */     return this.m_direction;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public DisplayObject getDisplayObject()
/*      */   {
/*  353 */     return this.m_displayObject;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void setPostRenderState(GLRenderStates postRenderState)
/*      */   {
/*  360 */     this.m_postRenderState = postRenderState;
/*  361 */     if ((this.m_displayObject != null) && (this.m_displayObject.getMesh() != null)) {
/*  362 */       this.m_displayObject.getMesh().setPostRenderStates(postRenderState);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void setPreRenderState(GLRenderStates preRenderState)
/*      */   {
/*  370 */     this.m_preRenderState = preRenderState;
/*  371 */     if ((this.m_displayObject != null) && (this.m_displayObject.getMesh() != null)) {
/*  372 */       this.m_displayObject.getMesh().setPreRenderStates(preRenderState);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void setEffect(Effect effect)
/*      */   {
/*  380 */     this.m_effect = effect;
/*  381 */     forceReloadAnimation();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void setApplyEffectTochild(boolean applyEffectTochild)
/*      */   {
/*  388 */     this.m_applyEffectTochild = applyEffectTochild;
/*  389 */     forceReloadAnimation();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public double getAltitude()
/*      */   {
/*  398 */     return this.m_altitude;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setAltitude(double altitude)
/*      */   {
/*  407 */     this.m_altitude = altitude;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public double getWorldX()
/*      */   {
/*  416 */     return this.m_worldX;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setWorldX(double worldX)
/*      */   {
/*  425 */     this.m_worldX = worldX;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public double getWorldY()
/*      */   {
/*  434 */     return this.m_worldY;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setWorldY(double worldY)
/*      */   {
/*  443 */     this.m_worldY = worldY;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getWorldCellX()
/*      */   {
/*  452 */     return (int)Math.floor(this.m_worldX);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getWorldCellY()
/*      */   {
/*  461 */     return (int)Math.floor(this.m_worldY);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public Point3 getWorldCoordinates()
/*      */   {
/*  468 */     return new Point3(getWorldCellX(), getWorldCellY(), (short)(int)this.m_altitude);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setWorldPosition(double worldX, double worldY, double altitude)
/*      */   {
/*  479 */     setWorldX(worldX);
/*  480 */     setWorldY(worldY);
/*  481 */     setAltitude(altitude);
/*  482 */     if (this.m_carriedMobile != null) {
/*  483 */       this.m_carriedMobile.setWorldPosition(worldX, worldY, altitude + getHeight());
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setVisible(boolean visible)
/*      */   {
/*  494 */     if (visible != this.m_visible) {
/*  495 */       this.m_visible = visible;
/*  496 */       this.m_visibleChanged = true;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public boolean isVisible()
/*      */   {
/*  504 */     return this.m_visible;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public boolean isSelected()
/*      */   {
/*  511 */     return this.m_selected;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void setSelected(boolean selected)
/*      */   {
/*  518 */     if (selected != this.m_selected) {
/*  519 */       this.m_selected = selected;
/*  520 */       this.m_selectedChanged = true;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setAnimationSpeed(float animationSpeed)
/*      */   {
/*  530 */     this.m_animationSpeed = animationSpeed;
/*  531 */     if (this.m_displayObject != null) {
/*  532 */       this.m_displayObject.setAnimationSpeed(this.m_animationSpeed);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public float getScale()
/*      */   {
/*  539 */     return this.m_scale;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void setScale(float scale)
/*      */   {
/*  546 */     this.m_scale = scale;
/*  547 */     if (this.m_displayObject != null) {
/*  548 */       this.m_displayObject.setScale(this.m_scale, this.m_scale);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public float getAlpha()
/*      */   {
/*  556 */     return this.m_alpha;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void setAlpha(float alpha)
/*      */   {
/*  563 */     if (this.m_displayObject != null) {
/*  564 */       this.m_alpha = alpha;
/*  565 */       forceReloadAnimation();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public short getHeight()
/*      */   {
/*  575 */     return this.m_visualHeight;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void setVisualHeight(short visualHeight)
/*      */   {
/*  582 */     this.m_visualHeight = visualHeight;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getCarryAnimation()
/*      */   {
/*  589 */     return this.m_carryAnimation;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void setCarryAnimation(String carryAnimation)
/*      */   {
/*  596 */     this.m_carryAnimation = carryAnimation;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void setCarriedMobile(Mobile carriedMobile)
/*      */   {
/*  605 */     this.m_carriedMobile = carriedMobile;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public Mobile getCarriedMobile()
/*      */   {
/*  612 */     return this.m_carriedMobile;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void setCarrierMobile(Mobile carrierMobile)
/*      */   {
/*  621 */     this.m_carrierMobile = carrierMobile;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public Mobile getCarrierMobile()
/*      */   {
/*  628 */     return this.m_carrierMobile;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public boolean isCarried()
/*      */   {
/*  635 */     return this.m_carrierMobile != null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public boolean isCarrier()
/*      */   {
/*  642 */     return this.m_carriedMobile != null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void carry(Mobile carriedMobile)
/*      */   {
/*  651 */     carriedMobile.setCarrierMobile(this);
/*  652 */     setCarriedMobile(carriedMobile);
/*  653 */     forceReloadAnimation();
/*  654 */     carriedMobile.forceReloadAnimation();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   protected void setCreationListener()
/*      */   {
/*  661 */     getDescriptorLibrary().setDisplayObjectListener(this.m_carryAnimation, new DisplayObjectListener()
/*      */     {
/*      */       public void onCreated(DisplayObject carryDisplayObject) {}
/*      */       
/*      */       public void onProcessed(DisplayObject processed) {
/*  666 */         Mobile.this.m_carriedMobile.getDisplayObject().transformBy(processed);
/*  667 */         Mobile.this.m_carriedMobile.getDisplayObject().removeListener();
/*      */       }
/*      */     });
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Mobile uncarry()
/*      */   {
/*  679 */     if (this.m_carriedMobile != null)
/*      */     {
/*  681 */       getDescriptorLibrary().removeDisplayObjectListener(this.m_carryAnimation);
/*  682 */       this.m_carriedMobile.setCarrierMobile(null);
/*      */       
/*  684 */       Mobile carriedMobile = this.m_carriedMobile;
/*  685 */       setCarriedMobile(null);
/*  686 */       carriedMobile.forceReloadAnimation();
/*      */       
/*  688 */       return carriedMobile;
/*      */     }
/*  690 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public DisplayObject getCarryDisplayObject()
/*      */   {
/*  699 */     if (this.m_displayObject != null) {
/*  700 */       return this.m_displayObject.getDisplayObject(this.m_carryAnimation);
/*      */     }
/*  702 */     System.out.println("pas de display objct");
/*  703 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void addSelectionChangedListener(MobileSelectionChangeListener listener)
/*      */   {
/*  712 */     this.m_selectionChangedListeners.add(listener);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void removeSelectionChangedListener(MobileSelectionChangeListener listener)
/*      */   {
/*  721 */     if (this.m_selectionChangedListeners.contains(listener)) {
/*  722 */       this.m_selectionChangedListeners.remove(listener);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void removeAllSelectionChangedListener()
/*      */   {
/*  731 */     this.m_selectionChangedListeners.clear();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setPartDescriptor(BaseDescriptorLibrary srcLibrary, String[] linkageNames)
/*      */   {
/*  741 */     if (this.m_descriptorLibrary != null) { String[] arrayOfString;
/*  742 */       int j = (arrayOfString = linkageNames).length; for (int i = 0; i < j; i++) { String linkageName = arrayOfString[i];
/*  743 */         setPartDescriptor(srcLibrary, linkageName);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setPartDescriptor(BaseDescriptorLibrary srcLibrary, String linkageName)
/*      */   {
/*  755 */     setPartDescriptor(srcLibrary, linkageName, linkageName);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setPartDescriptor(AbstractDescriptorLibrary srcLibrary, String srcLinkageName, String destLinkageName)
/*      */   {
/*  767 */     if ((this.m_descriptorLibrary != null) && (srcLibrary != null)) {
/*  768 */       for (int i = 0; i < 8; i++) {
/*  769 */         String destLinkage = createLinkage(i, destLinkageName);
/*  770 */         String srcLinkage = createLinkage(i, srcLinkageName);
/*  771 */         DisplayObjectDescriptor descriptor = srcLibrary.getDescriptor(srcLinkage);
/*  772 */         if (descriptor != null) {
/*  773 */           this.m_descriptorLibrary.setDescriptor(destLinkage, descriptor);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void unsetPartDescriptor(String[] linkageNames)
/*      */   {
/*  785 */     if (this.m_descriptorLibrary != null) { String[] arrayOfString;
/*  786 */       int j = (arrayOfString = linkageNames).length; for (int i = 0; i < j; i++) { String linkageName = arrayOfString[i];
/*  787 */         unsetPartDescriptor(linkageName);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void unsetPartDescriptor(String linkageName)
/*      */   {
/*  798 */     if (this.m_descriptorLibrary != null) {
/*  799 */       for (int i = 0; i < 8; i++) {
/*  800 */         String linkage = createLinkage(i, linkageName);
/*  801 */         this.m_descriptorLibrary.setDescriptor(linkage, null);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setCustomMaterialToLinkages(String[] linkageNames, Material material)
/*      */   {
/*      */     String[] arrayOfString;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  820 */     int j = (arrayOfString = linkageNames).length; for (int i = 0; i < j; i++) { String linkageName = arrayOfString[i];
/*  821 */       setCustomMaterialToLinkage(linkageName, material);
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
/*      */   public void setCustomMaterialToLinkage(String linkageName, Material material)
/*      */   {
/*  839 */     if (this.m_customLinkageMaterials.containsKey(linkageName)) {
/*  840 */       applyCustomMaterial(linkageName, null);
/*  841 */       this.m_customLinkageMaterials.remove(linkageName);
/*      */     }
/*  843 */     if (material != null) {
/*  844 */       this.m_customLinkageMaterials.put(linkageName, material);
/*      */     }
/*  846 */     applyCustomMaterial(linkageName, material);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void applyAllCustomMaterials()
/*      */   {
/*  855 */     Set<Map.Entry<String, Material>> entries = this.m_customLinkageMaterials.entrySet();
/*  856 */     for (Map.Entry<String, Material> entry : entries) {
/*  857 */       applyCustomMaterial((String)entry.getKey(), (Material)entry.getValue());
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
/*      */   private void applyCustomMaterial(String linkageName, Material material)
/*      */   {
/*  870 */     if ((this.m_descriptorLibrary != null) && (linkageName != null) && (linkageName.length() != 0))
/*      */     {
/*      */ 
/*  873 */       Set<String> linkages = this.m_descriptorLibrary.getParent().getIndexedBuffer().getLinkages();
/*  874 */       for (String linkage : linkages)
/*      */       {
/*      */ 
/*  877 */         if (linkage.contains(linkageName)) {
/*  878 */           DisplayObjectDescriptor descriptor = this.m_descriptorLibrary.getDescriptor(linkage);
/*  879 */           if (descriptor != null)
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/*  884 */             if (!descriptor.isVirtual()) {
/*  885 */               descriptor = this.m_descriptorLibrary.setDescriptor(descriptor.getId(), descriptor);
/*      */             }
/*      */             
/*      */ 
/*  889 */             if (descriptor != null) {
/*  890 */               this.m_descriptorLibrary.setMaterial(linkage, material);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
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
/*      */   public boolean hitTest(float x, float y)
/*      */   {
/*  910 */     if (this.m_displayObject != null) {
/*  911 */       return this.m_displayObject.hitTest(x, y);
/*      */     }
/*  913 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected DisplayObject createDisplayObject()
/*      */   {
/*  923 */     if (this.m_descriptorLibrary != null) {
/*  924 */       DisplayObjectDescriptor descriptor = this.m_descriptorLibrary.getDescriptor(getDisplayObjectLinkage());
/*  925 */       if (descriptor == null) {
/*  926 */         m_logger.error("L'animation " + getDisplayObjectLinkage() + " n'existe pas dans la bibliothèque de ce mobile !");
/*  927 */         descriptor = this.m_descriptorLibrary.getDescriptor(createLinkage(getDirection().getIndex(), "AnimStatique"));
/*      */       }
/*  929 */       if (descriptor != null) {
/*  930 */         return descriptor.createInstance(this.m_descriptorLibrary);
/*      */       }
/*      */     }
/*  933 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void setDisplayObject(DisplayObject displayObject)
/*      */   {
/*  942 */     this.m_displayObject = displayObject;
/*  943 */     if (isCarried()) {
/*  944 */       this.m_carrierMobile.setCreationListener();
/*      */     }
/*  946 */     if (isCarrier()) {
/*  947 */       setCreationListener();
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
/*      */   public void process(AleaWorldScene scene, long realTime, int frameCount)
/*      */   {
/*  962 */     if (this.m_descriptorLibrary != null) {
/*  963 */       DescriptorLibraryManager.getInstance().tagResourceInUse(this.m_descriptorLibrary.getParent());
/*      */     }
/*      */     
/*  966 */     if (!this.m_visible) {
/*  967 */       if ((this.m_visibleChanged) && (this.m_displayObject != null)) {
/*  968 */         this.m_displayObject.invalidate();
/*  969 */         setDisplayObject(null);
/*      */       }
/*      */     }
/*      */     else {
/*  973 */       if ((this.m_descriptorLibrary != null) && (this.m_descriptorLibrary.getChangeRevision() != this.m_lastLibraryChangeRevision) && (this.m_displayObject != null)) {
/*  974 */         this.m_displayObject.refresh();
/*  975 */         this.m_lastLibraryChangeRevision = this.m_descriptorLibrary.getChangeRevision();
/*      */       }
/*      */       
/*  978 */       if ((this.m_animationChanged) || (this.m_directionChanged) || (this.m_descriptorLibraryChanged) || (this.m_visibleChanged))
/*      */       {
/*      */ 
/*  981 */         if (this.m_displayObject != null) {
/*  982 */           this.m_displayObject.invalidate();
/*      */         }
/*      */         
/*  985 */         if (this.m_descriptorLibrary != null)
/*      */         {
/*  987 */           setDisplayObject(createDisplayObject());
/*      */           
/*  989 */           if (this.m_displayObject != null)
/*      */           {
/*  991 */             this.m_displayObject.addControler(this);
/*      */             
/*      */ 
/*  994 */             setAnimationSpeed(this.m_animationSpeed);
/*      */             
/*      */ 
/*  997 */             setScale(this.m_scale);
/*      */             
/*      */ 
/* 1000 */             HitTestableMesh2D mesh = this.m_displayObject.getMesh();
/* 1001 */             if (mesh != null) {
/* 1002 */               if (this.m_preRenderState != null) {
/* 1003 */                 mesh.setPreRenderStates(this.m_preRenderState);
/*      */               }
/* 1005 */               if (this.m_postRenderState != null) {
/* 1006 */                 mesh.setPostRenderStates(this.m_postRenderState);
/*      */               }
/* 1008 */               if (this.m_effect != null) {
/* 1009 */                 mesh.setEffect(this.m_effect, this.m_applyEffectTochild);
/*      */               }
/*      */               
/* 1012 */               if (this.m_alpha != 1.0F) {
/* 1013 */                 if (!(mesh.getEffect() instanceof OutlineEffect)) {
/* 1014 */                   mesh.getMaterial().setUseDiffuse(true);
/*      */                 }
/*      */                 
/*      */ 
/* 1018 */                 mesh.getMaterial().getDiffuse()[0] *= this.m_alpha;
/* 1019 */                 mesh.getMaterial().getDiffuse()[1] *= this.m_alpha;
/* 1020 */                 mesh.getMaterial().getDiffuse()[2] *= this.m_alpha;
/*      */                 
/* 1022 */                 mesh.getMaterial().getDiffuse()[3] *= this.m_alpha;
/*      */               }
/*      */             }
/*      */             
/*      */ 
/* 1027 */             AnimationManager.getInstance().addAnimatedObject(scene, this.m_displayObject);
/*      */           }
/*      */         } else {
/* 1030 */           setDisplayObject(null);
/*      */         }
/*      */         
/*      */ 
/* 1034 */         this.m_descriptorLibraryChanged = false;
/* 1035 */         this.m_animationChanged = false;
/* 1036 */         this.m_directionChanged = false;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1041 */     if (this.m_selectedChanged) {
/* 1042 */       for (MobileSelectionChangeListener listener : this.m_selectionChangedListeners) {
/* 1043 */         listener.selectionChanged(this, this.m_selected);
/*      */       }
/* 1045 */       this.m_selectedChanged = false;
/*      */     }
/*      */     
/* 1048 */     this.m_visibleChanged = false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getDisplayObjectLinkage()
/*      */   {
/* 1055 */     String linkageName = this.m_animation;
/* 1056 */     if (this.m_animationSuffix != null) {
/* 1057 */       linkageName = linkageName + this.m_animationSuffix;
/*      */     }
/* 1059 */     return createLinkage(this.m_direction.getIndex(), linkageName);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String createLinkage(int directionIndex, String linkageName)
/*      */   {
/* 1071 */     return directionIndex + "_" + linkageName;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void onAnimatedObjectActionFlag(List<String> actions)
/*      */   {
/* 1081 */     for (String action : actions) {
/* 1082 */       LuaManager.getInstance().runCommand(action, getFunctionsLibraries(), true);
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
/*      */   private JavaFunctionsLibrary[] getFunctionsLibraries()
/*      */   {
/* 1095 */     if (this.m_functionsLibraries == null) {
/* 1096 */       this.m_functionsLibraries = new JavaFunctionsLibrary[] { new InnerMobileFunctionsLibrary() };
/*      */     }
/* 1098 */     return this.m_functionsLibraries;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public class InnerMobileFunctionsLibrary
/*      */     extends JavaFunctionsLibrary
/*      */   {
/*      */     private class GotoStaticAnimation
/*      */       extends JavaFunctionEx
/*      */     {
/*      */       public GotoStaticAnimation(LuaState luaState)
/*      */       {
/* 1115 */         super();
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       public String getName()
/*      */       {
/* 1125 */         return "gotoStaticAnimation";
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       public LuaScriptParameterDescriptor[] getParameterDescriptors()
/*      */       {
/* 1135 */         return null;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       protected void run(int paramCount)
/*      */         throws LuaException
/*      */       {
/* 1145 */         Mobile.this.setAnimation(Mobile.this.getStaticAnimationKey());
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     protected InnerMobileFunctionsLibrary()
/*      */     {
/* 1154 */       super();
/* 1155 */       registerGlobalFunctionClass(GotoStaticAnimation.class);
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphics\alea\mobile\Mobile.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */