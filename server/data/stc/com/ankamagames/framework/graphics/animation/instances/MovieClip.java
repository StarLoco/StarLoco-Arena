/*     */ package com.ankamagames.framework.graphics.animation.instances;
/*     */ 
/*     */ import com.ankamagames.framework.graphics.animation.descriptors.BitmapDescriptor;
/*     */ import com.ankamagames.framework.graphics.animation.descriptors.BitmapSequenceDescriptor;
/*     */ import com.ankamagames.framework.graphics.animation.descriptors.DisplayObjectDescriptor;
/*     */ import com.ankamagames.framework.graphics.animation.descriptors.DisplayObjectDescriptor.DescriptorType;
/*     */ import com.ankamagames.framework.graphics.animation.descriptors.FrameDataDescriptor;
/*     */ import com.ankamagames.framework.graphics.animation.descriptors.FrameDescriptor;
/*     */ import com.ankamagames.framework.graphics.animation.descriptors.MovieClipDescriptor;
/*     */ import com.ankamagames.framework.graphics.animation.descriptors.library.AbstractDescriptorLibrary;
/*     */ import com.ankamagames.framework.graphics.effects.ColorationEffect.ColorationEffectContext;
/*     */ import com.ankamagames.framework.graphics.opengl.base.Mesh;
/*     */ import com.ankamagames.framework.graphics.opengl.base.effects.Effect;
/*     */ import com.ankamagames.framework.graphics.opengl.base.effects.EffectManager;
/*     */ import com.ankamagames.framework.graphics.opengl.base.impl.HitTestableMesh2D;
/*     */ import com.ankamagames.framework.graphics.opengl.base.impl.Mesh2D;
/*     */ import com.ankamagames.framework.graphics.opengl.base.material.Material;
/*     */ import gnu.trove.TIntObjectIterator;
/*     */ import java.awt.geom.Rectangle2D.Float;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MovieClip
/*     */   extends SequenceObject
/*     */   implements AnimatedObjectControler
/*     */ {
/*     */   protected static final int MISSED_FRAME_COUNT_MAX = 3;
/*  34 */   protected static final Logger m_logger = Logger.getLogger(MovieClip.class);
/*     */   
/*  36 */   private boolean m_initialized = false;
/*     */   
/*  38 */   private static float DEFAULT_LEFT_BOUND = -100.0F;
/*  39 */   private static float DEFAULT_TOP_BOUND = -100.0F;
/*  40 */   private static float DEFAULT_WIDTH = 200.0F;
/*  41 */   private static float DEFAULT_HEIGHT = 200.0F;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected Frame[] m_frames;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected List<DisplayObject> m_children;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public MovieClip()
/*     */   {
/*  60 */     this.m_children = new ArrayList();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public MovieClip(AbstractDescriptorLibrary descriptorLibrary, int descriptorId)
/*     */   {
/*  69 */     super(descriptorLibrary, descriptorId);
/*     */     
/*  71 */     this.m_children = new ArrayList();
/*     */     
/*  73 */     this.m_mesh.setVisibilityInheritance(false);
/*  74 */     this.m_mesh.setVisible(false);
/*     */     
/*  76 */     initialize();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void release()
/*     */   {
/*  86 */     if (!isReleased()) {
/*  87 */       releaseFrames();
/*  88 */       this.m_mesh.removeAllChilds();
/*  89 */       super.release();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void onCheckIn()
/*     */   {
/*  96 */     super.onCheckIn();
/*     */     
/*  98 */     this.m_initialized = false;
/*  99 */     this.m_children = null;
/* 100 */     this.m_frames = null;
/*     */   }
/*     */   
/*     */ 
/*     */   public void onCheckOut()
/*     */   {
/* 106 */     super.onCheckOut();
/*     */     
/* 108 */     this.m_children = new ArrayList();
/* 109 */     this.m_mesh.setVisibilityInheritance(false);
/* 110 */     this.m_mesh.setVisible(false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public MovieClipDescriptor getDescriptor()
/*     */   {
/* 120 */     DisplayObjectDescriptor descriptor = this.m_descriptorLibrary.getDescriptor(this.m_descriptorId);
/* 121 */     if ((descriptor != null) && (descriptor.getType() == DisplayObjectDescriptor.DescriptorType.MOVIE_CLIP)) {
/* 122 */       return (MovieClipDescriptor)descriptor;
/*     */     }
/*     */     
/* 125 */     m_logger.trace("getMovieClipDescritpor ne devrait pas arriver " + descriptor);
/* 126 */     invalidate();
/* 127 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void process(Mesh parentMesh, int deltaTime, int recurs)
/*     */   {
/* 140 */     if (!isReleased()) {
/* 141 */       if (!this.m_initialized) {
/* 142 */         initialize();
/*     */       }
/*     */       
/* 145 */       this.m_children.clear();
/* 146 */       this.m_mesh.removeAllChilds();
/*     */       
/* 148 */       MovieClipDescriptor descriptor = getDescriptor();
/*     */       
/* 150 */       if ((!this.m_terminated) && (descriptor != null) && (this.m_frames.length >= this.m_currentFrameIndex) && (this.m_frames.length > 0))
/*     */       {
/*     */ 
/* 153 */         if (this.m_frames[this.m_currentFrameIndex] == null) {
/* 154 */           createFrame(this.m_currentFrameIndex, descriptor);
/*     */         }
/*     */         
/*     */ 
/* 158 */         processCurrentFrame(descriptor, deltaTime, recurs);
/*     */         
/* 160 */         this.m_mesh.getMaterial().setDiffuseChanged(false);
/* 161 */         this.m_mesh.getMaterial().setSpecularChanged(false);
/*     */         
/* 163 */         attachMeshTo(parentMesh);
/*     */       }
/*     */       else {
/* 166 */         removeMeshFrom(parentMesh);
/*     */       }
/*     */       
/* 169 */       super.process(parentMesh, deltaTime, recurs);
/*     */     }
/*     */     else {
/* 172 */       m_logger.error("utilisation d'un movieClip checkin (parent=" + parentMesh + "profondeur=" + recurs + ")");
/*     */     }
/*     */     
/* 175 */     notifyProcessed();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private DisplayObject createDisplayObject(AbstractDescriptorLibrary descriptorLibrary, int id)
/*     */   {
/* 183 */     DisplayObject displayObject = descriptorLibrary.createInstanceOf(id);
/*     */     
/* 185 */     if (displayObject != null) {
/* 186 */       this.m_children.add(displayObject);
/*     */       
/*     */ 
/* 189 */       displayObject.setParent(this);
/*     */       
/*     */ 
/* 192 */       displayObject.addControler(this);
/*     */     } else {
/* 194 */       m_logger.error("childDescriptor introuvable id=" + id + " dans la librairie : " + descriptorLibrary);
/*     */     }
/*     */     
/* 197 */     return displayObject;
/*     */   }
/*     */   
/*     */ 
/*     */   private AbstractDescriptorLibrary getLibrary(MovieClipDescriptor descriptor)
/*     */   {
/*     */     AbstractDescriptorLibrary library;
/*     */     
/*     */     AbstractDescriptorLibrary library;
/*     */     
/* 207 */     if (descriptor.isVirtual()) {
/* 208 */       library = descriptor.getLibrary();
/*     */     } else {
/* 210 */       library = this.m_descriptorLibrary;
/*     */     }
/* 212 */     return library;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void createFrame(int frameIndex, MovieClipDescriptor movieClipDescriptor)
/*     */   {
/* 223 */     FrameDescriptor frameDescr = movieClipDescriptor.getFrameAtIndex(frameIndex);
/*     */     
/* 225 */     if (frameDescr != null)
/*     */     {
/* 227 */       this.m_frames[frameIndex] = Frame.getNewFrame();
/*     */       
/*     */ 
/*     */ 
/* 231 */       AbstractDescriptorLibrary descriptorLibrary = getLibrary(movieClipDescriptor);
/* 232 */       TIntObjectIterator<FrameDataDescriptor> iterator = frameDescr.iterator();
/*     */       
/* 234 */       int i = frameDescr.size();
/* 235 */       do { iterator.advance();
/* 236 */         FrameDataDescriptor data = (FrameDataDescriptor)iterator.value();
/*     */         DisplayObject displayObject;
/* 238 */         DisplayObject displayObject; if (data.hasCharacterId())
/*     */         {
/* 240 */           displayObject = createDisplayObject(descriptorLibrary, data.getCharacterId());
/*     */         } else {
/* 242 */           if (this.m_frames[(frameIndex - 1)] == null) {
/* 243 */             createFrame(frameIndex - 1, movieClipDescriptor);
/*     */           }
/*     */           
/* 246 */           int frame = frameIndex - 1;
/* 247 */           displayObject = this.m_frames[frame].getDisplayObject(data.getDepth());
/*     */         }
/*     */         
/* 250 */         this.m_frames[frameIndex].put(data.getDepth(), displayObject);i--;
/* 234 */       } while (i >= 0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 253 */       m_logger.error("framedescriptor == null frameIndex" + frameIndex);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void processCurrentFrame(MovieClipDescriptor descriptor, int deltaTime, int recurs)
/*     */   {
/* 264 */     if (this.m_pause) {
/* 265 */       deltaTime = 0;
/*     */     } else {
/* 267 */       deltaTime = (int)(deltaTime * this.m_timeSpeed);
/*     */     }
/*     */     try {
/* 270 */       FrameDescriptor frameDesc = descriptor.getFrameAtIndex(this.m_currentFrameIndex);
/* 271 */       Material meshMaterial = this.m_mesh.getMaterial();
/*     */       
/*     */ 
/* 274 */       Frame currentFrame = this.m_frames[this.m_currentFrameIndex];
/*     */       
/* 276 */       if (currentFrame == null) {
/* 277 */         return;
/*     */       }
/*     */       
/* 280 */       int[] depths = currentFrame.getDepths();
/* 281 */       Arrays.sort(depths);
/* 282 */       for (int i = 0; i < depths.length; i++) {
/* 283 */         int depth = depths[i];
/*     */         
/* 285 */         DisplayObject currentChild = currentFrame.getDisplayObject(depth);
/* 286 */         if (currentChild != null)
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/* 291 */           currentChild.removeMeshFrom(this.m_mesh);
/*     */           
/* 293 */           FrameDataDescriptor frameDataDesc = frameDesc.getDataAt(depth);
/*     */           
/* 295 */           if (frameDataDesc != null)
/*     */           {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 301 */             if (currentChild.getType() == DisplayObject.DisplayObjectType.BITMAP) {
/* 302 */               BitmapDescriptor childDescriptor = (BitmapDescriptor)currentChild.getDescriptor();
/* 303 */               if (childDescriptor != null) {
/* 304 */                 float inverScalingValue = childDescriptor.getInvertScalingValue();
/* 305 */                 currentChild.setScale(inverScalingValue, inverScalingValue);
/*     */               }
/*     */             }
/* 308 */             else if (currentChild.getType() == DisplayObject.DisplayObjectType.BITMAP_SEQUENCE) {
/* 309 */               BitmapSequenceDescriptor childDescriptor = (BitmapSequenceDescriptor)currentChild.getDescriptor();
/* 310 */               if (childDescriptor != null) {
/* 311 */                 float inverScalingValue = childDescriptor.getInvertScalingValue();
/* 312 */                 currentChild.setScale(inverScalingValue, inverScalingValue);
/*     */               }
/*     */             }
/*     */             
/* 316 */             currentChild.setMatrix(frameDataDesc.getMatrix());
/*     */             
/*     */ 
/* 319 */             currentChild.applyDescriptorColor();
/*     */             
/*     */ 
/* 322 */             Material frameMaterial = frameDataDesc.getMaterial();
/* 323 */             if (frameMaterial != null) {
/* 324 */               currentChild.colorize(frameMaterial);
/*     */             }
/* 326 */             this.m_children.add(currentChild);
/*     */             
/*     */ 
/* 329 */             currentChild.colorize(meshMaterial);
/* 330 */             currentChild.process(this.m_mesh, deltaTime, recurs + 1);
/* 331 */             currentChild.attachMeshTo(this.m_mesh);
/*     */             
/* 333 */             Mesh2D childMesh = currentChild.getMesh();
/* 334 */             if ((childMesh.getMaterial().useSpecular()) && (childMesh.getTexture() != null)) {
/* 335 */               Effect colorationEffect = EffectManager.getInstance().getEffect("coloration");
/* 336 */               childMesh.setEffect(colorationEffect, false);
/* 337 */               if (colorationEffect != null)
/*     */               {
/*     */ 
/* 340 */                 ((ColorationEffect.ColorationEffectContext)childMesh.getEffectContext()).setMesh(childMesh);
/*     */               }
/*     */             }
/* 343 */             else if (childMesh.getEffect() != null) {
/* 344 */               childMesh.setEffect(null, false);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 350 */       if ((hasFrameChanged()) && (descriptor.hasActions())) {
/* 351 */         int lastFrame = getLastFrameIndex();
/*     */         
/*     */ 
/* 354 */         int frameMissedCount = this.m_currentFrameIndex - lastFrame;
/*     */         
/* 356 */         if (frameMissedCount > 0) {
/* 357 */           if (frameMissedCount > 3) {
/* 358 */             lastFrame = this.m_currentFrameIndex - 3;
/*     */           }
/* 360 */           for (int i = lastFrame + 1; i <= this.m_currentFrameIndex; i++) {
/* 361 */             List<String> actions = descriptor.getActionsAt(i);
/* 362 */             if ((actions != null) && (actions.size() > 0)) {
/* 363 */               doAction(actions);
/*     */             }
/*     */           }
/*     */         } else {
/* 367 */           if (frameMissedCount < -3) {
/* 368 */             lastFrame = (this.m_currentFrameIndex + descriptor.getFrameCount() - 3) % descriptor.getFrameCount();
/*     */           }
/*     */           
/* 371 */           int frameCount = descriptor.getFrameCount();
/* 372 */           for (int i = lastFrame + 1; i < frameCount; i++) {
/* 373 */             List<String> actions = descriptor.getActionsAt(i);
/* 374 */             if ((actions != null) && (actions.size() > 0)) {
/* 375 */               doAction(actions);
/*     */             }
/*     */           }
/* 378 */           for (int i = 0; i <= this.m_currentFrameIndex; i++) {
/* 379 */             List<String> actions = descriptor.getActionsAt(i);
/* 380 */             if ((actions != null) && (actions.size() > 0)) {
/* 381 */               doAction(actions);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception ex) {
/* 388 */       ex.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DisplayObject.DisplayObjectType getType()
/*     */   {
/* 399 */     return DisplayObject.DisplayObjectType.MOVIE_CLIP;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean hitTest(float x, float y)
/*     */   {
/* 411 */     Rectangle2D.Float rect = getBoundRectangle();
/* 412 */     if ((rect != null) && (rect.contains(x, y))) {
/* 413 */       for (DisplayObject child : this.m_children)
/*     */       {
/* 415 */         HitTestableMesh2D mesh = child.getMesh();
/* 416 */         if ((mesh != null) && (mesh.hitTest(x, y))) {
/* 417 */           return true;
/*     */         }
/*     */       }
/*     */     }
/* 421 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private Rectangle2D.Float getBoundRectangle()
/*     */   {
/* 428 */     if (this.m_mesh == null) {
/* 429 */       return null;
/*     */     }
/* 431 */     this.m_boundRect.setRect(getLeftBound(), getBottomBound(), getWidth(), getHeight());
/* 432 */     return this.m_boundRect;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onAnimatedObjectActionFlag(List<String> actions)
/*     */   {
/* 443 */     doAction(actions);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void initialize()
/*     */   {
/* 450 */     refresh();
/* 451 */     this.m_initialized = true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public float getLeftBound()
/*     */   {
/* 461 */     return this.m_mesh.getPosX() + DEFAULT_LEFT_BOUND;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public float getBottomBound()
/*     */   {
/* 471 */     return this.m_mesh.getPosY() + DEFAULT_TOP_BOUND;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public float getHeight()
/*     */   {
/* 481 */     return DEFAULT_HEIGHT;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public float getWidth()
/*     */   {
/* 491 */     return DEFAULT_WIDTH;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void refresh()
/*     */   {
/* 501 */     if (this.m_descriptorLibrary != null) {
/* 502 */       this.m_children.clear();
/*     */       
/* 504 */       MovieClipDescriptor descriptor = getDescriptor();
/* 505 */       if (descriptor != null) {
/* 506 */         releaseFrames();
/*     */         
/* 508 */         int frameCount = descriptor.getFrameCount();
/* 509 */         this.m_frames = new Frame[frameCount];
/*     */       } else {
/* 511 */         this.m_frames = null;
/*     */       }
/* 513 */     } else { releaseFrames();
/* 514 */       this.m_frames = null;
/*     */     }
/*     */   }
/*     */   
/*     */   private void releaseFrames()
/*     */   {
/* 520 */     if (this.m_frames != null) { Frame[] arrayOfFrame;
/* 521 */       int j = (arrayOfFrame = this.m_frames).length; for (int i = 0; i < j; i++) { Frame frame = arrayOfFrame[i];
/* 522 */         if (frame != null) {
/* 523 */           frame.release();
/*     */         }
/*     */       }
/* 526 */       this.m_frames = null;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public DisplayObject getDisplayObject(int id)
/*     */   {
/* 533 */     DisplayObject result = null;
/* 534 */     if (this.m_children != null) {
/* 535 */       for (DisplayObject displayObject : this.m_children) {
/* 536 */         if ((displayObject.getDescriptor() != null) && (displayObject.getDescriptor().getId() == id)) {
/* 537 */           result = displayObject;
/* 538 */           break;
/*     */         }
/*     */       }
/*     */       
/* 542 */       if (result == null) {
/* 543 */         for (DisplayObject displayObject : this.m_children) {
/* 544 */           result = displayObject.getDisplayObject(id);
/*     */         }
/*     */       }
/*     */     }
/* 548 */     return result;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\animation\instances\MovieClip.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */