/*     */ package com.ankamagames.framework.graphics.opengl.base.animation;
/*     */ 
/*     */ import com.ankamagames.framework.graphics.animation.instances.AnimatedObjectControler;
/*     */ import com.ankamagames.framework.graphics.opengl.base.Mesh;
/*     */ import com.ankamagames.framework.graphics.opengl.base.Scene;
/*     */ import com.ankamagames.framework.graphics.opengl.base.render.GLObject;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AnimationManager
/*     */ {
/*  22 */   protected static final Logger m_logger = Logger.getLogger(AnimationManager.class);
/*     */   
/*     */   public enum ProcessType {
/*  25 */     AUTO_PROCESS,
/*  26 */     USER_PROCESS;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class AnimatedSceneData
/*     */     extends TimedObject
/*     */   {
/*  35 */     protected ArrayList<AnimatedObject> m_animatedObjects = new ArrayList<AnimatedObject>();
/*     */     
/*     */     private Scene m_scene;
/*     */     
/*     */     private AnimationManager.ProcessType m_typeProcess;
/*     */     
/*     */     private AnimatedObjectControler m_controler;
/*     */     
/*     */     public AnimatedSceneData(Scene scene, AnimatedObjectControler controler, AnimationManager.ProcessType type) {
/*  44 */       this.m_scene = scene;
/*  45 */       this.m_controler = controler;
/*  46 */       this.m_typeProcess = type;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void uninitialize() {
/*  55 */       this.m_scene.uninitialize();
/*     */       
/*  57 */       for (AnimatedObject aObjects : this.m_animatedObjects) {
/*  58 */         aObjects.getMesh().uninitialize();
/*     */       }
/*  60 */       this.m_animatedObjects.clear();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void addAnimatedObject(AnimatedObject animatedObject) {
/*  68 */       this.m_animatedObjects.add(animatedObject);
/*  69 */       if (this.m_controler != null) {
/*  70 */         animatedObject.addControler(this.m_controler);
/*     */       }
/*     */     }
/*     */     
/*     */     public void removeAnimatedObject(AnimatedObject animatedObject) {
/*  75 */       this.m_animatedObjects.remove(animatedObject);
/*     */     }
/*     */     
/*     */     public void removeAllAnimatedObjects() {
/*  79 */       this.m_animatedObjects.clear();
/*     */     }
/*     */     
/*     */     public void invalidateAllAnimatedObjects() {
/*  83 */       for (AnimatedObject animatedObject : this.m_animatedObjects) {
/*  84 */         animatedObject.invalidate();
/*     */       }
/*     */     }
/*     */     
/*     */     public void process(long realTime, int frameCount) {
/*  89 */       if (!this.m_pause) {
/*  90 */         Iterator<AnimatedObject> iterator = this.m_animatedObjects.iterator();
/*     */ 
/*     */         
/*  93 */         int deltaTime = (this.m_currentTime == 0L) ? 0 : (int)(this.m_timeSpeed * (float)(realTime - this.m_currentTime));
/*     */         
/*  95 */         while (iterator.hasNext()) {
/*  96 */           AnimatedObject animatedObject = iterator.next();
/*  97 */           if (animatedObject.isInvalidate()) {
/*     */ 
/*     */             
/* 100 */             iterator.remove();
/*     */ 
/*     */             
/* 103 */             Mesh mesh = animatedObject.getMesh();
/* 104 */             if (mesh != null) {
/* 105 */               this.m_scene.removeChild((GLObject)mesh);
/*     */             }
/*     */ 
/*     */             
/* 109 */             animatedObject.release();
/*     */             continue;
/*     */           } 
/* 112 */           animatedObject.process(null, deltaTime, 0);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 117 */       this.m_currentTime = realTime;
/*     */     }
/*     */     
/*     */     public Scene getScene() {
/* 121 */       return this.m_scene;
/*     */     }
/*     */     
/*     */     public List<AnimatedObject> getAnimatedObject() {
/* 125 */       return this.m_animatedObjects;
/*     */     }
/*     */     
/*     */     public AnimationManager.ProcessType getType() {
/* 129 */       return this.m_typeProcess;
/*     */     }
/*     */   }
/*     */   
/* 133 */   protected static AnimationManager m_instance = new AnimationManager();
/*     */   
/*     */   protected HashMap<Scene, AnimatedSceneData> m_animatedSceneDatas;
/*     */   
/* 137 */   protected List<AnimatedSceneData> m_animatedSceneDatasToAdd = new ArrayList<AnimatedSceneData>();
/* 138 */   protected List<Scene> m_animatedSceneDatasToRemove = new ArrayList<Scene>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AnimationManager() {
/* 144 */     this.m_animatedSceneDatas = new HashMap<Scene, AnimatedSceneData>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AnimationManager getInstance() {
/* 151 */     return m_instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerScene(Scene scene, AnimatedObjectControler controler) {
/* 160 */     this.m_animatedSceneDatasToAdd.add(new AnimatedSceneData(scene, controler, ProcessType.AUTO_PROCESS));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerScene(Scene scene, AnimatedObjectControler controler, ProcessType processType) {
/* 169 */     this.m_animatedSceneDatasToAdd.add(new AnimatedSceneData(scene, controler, processType));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unregisterScene(Scene scene) {
/* 177 */     this.m_animatedSceneDatasToRemove.add(scene);
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
/*     */   public void process(long realTime, int frameCount) {
/* 189 */     for (AnimatedSceneData animatedSceneData : this.m_animatedSceneDatas.values()) {
/* 190 */       if (animatedSceneData.getType() == ProcessType.AUTO_PROCESS) {
/* 191 */         animatedSceneData.process(realTime, frameCount);
/*     */       }
/*     */     } 
/*     */     int i;
/* 195 */     for (i = 0; i < this.m_animatedSceneDatasToRemove.size(); i++) {
/* 196 */       this.m_animatedSceneDatas.remove(this.m_animatedSceneDatasToRemove.get(i));
/*     */     }
/* 198 */     this.m_animatedSceneDatasToRemove.clear();
/*     */     
/* 200 */     for (i = 0; i < this.m_animatedSceneDatasToAdd.size(); i++) {
/* 201 */       this.m_animatedSceneDatas.put(((AnimatedSceneData)this.m_animatedSceneDatasToAdd.get(i)).getScene(), this.m_animatedSceneDatasToAdd.get(i));
/*     */     }
/* 203 */     this.m_animatedSceneDatasToAdd.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void process(Scene scene, long realTime, int frameCount) {
/* 209 */     AnimatedSceneData sceneData = this.m_animatedSceneDatas.get(scene);
/* 210 */     if (sceneData != null) {
/* 211 */       sceneData.process(realTime, frameCount);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeAnimatedObject(Scene scene, AnimatedObject animatedObject) {
/* 221 */     AnimatedSceneData sceneData = this.m_animatedSceneDatas.get(scene);
/* 222 */     if (sceneData != null) {
/* 223 */       sceneData.removeAnimatedObject(animatedObject);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addAnimatedObject(Scene scene, AnimatedObject animatedObject) {
/* 233 */     AnimatedSceneData sceneData = this.m_animatedSceneDatas.get(scene);
/* 234 */     if (sceneData != null) {
/* 235 */       sceneData.addAnimatedObject(animatedObject);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void invalidateAllDisplayObjects(Scene scene) {
/* 243 */     AnimatedSceneData sceneData = this.m_animatedSceneDatas.get(scene);
/* 244 */     if (sceneData != null) {
/* 245 */       sceneData.invalidateAllAnimatedObjects();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPause(Scene scene, boolean pause) {
/* 254 */     AnimatedSceneData sceneData = this.m_animatedSceneDatas.get(scene);
/* 255 */     if (sceneData != null) {
/* 256 */       sceneData.setPause(pause);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<AnimatedObject> getAnimatedObject(Scene scene) {
/* 263 */     AnimatedSceneData sceneData = this.m_animatedSceneDatas.get(scene);
/* 264 */     if (sceneData != null) {
/* 265 */       return sceneData.getAnimatedObject();
/*     */     }
/* 267 */     return null;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\opengl\base\animation\AnimationManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */