/*    */ package com.ankamagames.framework.graphics.opengl.base.effects;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import javax.media.opengl.GL;
/*    */ import javax.media.opengl.glu.GLU;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EffectManager
/*    */ {
/*    */   private HashMap<String, Effect> m_effects;
/* 18 */   private static final EffectManager m_instance = new EffectManager();
/* 19 */   private static final Logger m_logger = Logger.getLogger(EffectManager.class);
/*    */   
/*    */   private EffectManager() {
/* 22 */     this.m_effects = new HashMap();
/*    */   }
/*    */   
/*    */   public static EffectManager getInstance() {
/* 26 */     return m_instance;
/*    */   }
/*    */   
/*    */   public void registerEffects(Effect[] effects)
/*    */   {
/* 31 */     GL gl = GLU.getCurrentGL();
/*    */     
/* 33 */     if (effects != null) { Effect[] arrayOfEffect;
/* 34 */       int j = (arrayOfEffect = effects).length; for (int i = 0; i < j; i++) { Effect effect = arrayOfEffect[i];
/* 35 */         if (effect.isConfigurationMatchRequirements(gl)) {
/* 36 */           m_logger.info("Effet initialisé : " + effect.getName());
/* 37 */           registerEffect(effect.getName(), effect);
/*    */         } else {
/* 39 */           m_logger.warn("Effet incompatible avec le matériel : " + effect.getName());
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */   
/* 45 */   public void registerEffect(String name, Effect effect) { this.m_effects.put(name, effect); }
/*    */   
/*    */   public Effect getEffect(String name)
/*    */   {
/* 49 */     return (Effect)this.m_effects.get(name);
/*    */   }
/*    */   
/*    */   public void unregisterEffect(String name) {
/* 53 */     this.m_effects.remove(name);
/*    */   }
/*    */   
/*    */   public void preProcessEffects(long realTime, int frameCount) {
/* 57 */     for (Effect effect : this.m_effects.values()) {
/* 58 */       effect.preProcess(realTime, frameCount);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\opengl\base\effects\EffectManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */