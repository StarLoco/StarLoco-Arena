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
/*    */ 
/*    */ public class EffectManager
/*    */ {
/* 18 */   private static final EffectManager m_instance = new EffectManager();
/* 19 */   private static final Logger m_logger = Logger.getLogger(EffectManager.class);
/*    */ 
/*    */   
/* 22 */   private HashMap<String, Effect> m_effects = new HashMap<String, Effect>();
/*    */ 
/*    */   
/*    */   public static EffectManager getInstance() {
/* 26 */     return m_instance;
/*    */   }
/*    */ 
/*    */   
/*    */   public void registerEffects(Effect[] effects) {
/* 31 */     GL gl = GLU.getCurrentGL();
/*    */     
/* 33 */     if (effects != null) {
/* 34 */       byte b; int i; Effect[] arrayOfEffect; for (i = (arrayOfEffect = effects).length, b = 0; b < i; ) { Effect effect = arrayOfEffect[b];
/* 35 */         if (effect.isConfigurationMatchRequirements(gl)) {
/* 36 */           m_logger.info("Effet initialisé : " + effect.getName());
/* 37 */           registerEffect(effect.getName(), effect);
/*    */         } else {
/* 39 */           m_logger.warn("Effet incompatible avec le matériel : " + effect.getName());
/*    */         } 
/*    */         b++; }
/*    */     
/*    */     } 
/*    */   } public void registerEffect(String name, Effect effect) {
/* 45 */     this.m_effects.put(name, effect);
/*    */   }
/*    */   
/*    */   public Effect getEffect(String name) {
/* 49 */     return this.m_effects.get(name);
/*    */   }
/*    */   
/*    */   public void unregisterEffect(String name) {
/* 53 */     this.m_effects.remove(name);
/*    */   }
/*    */   
/*    */   public void preProcessEffects(long realTime, int frameCount) {
/* 57 */     for (Effect effect : this.m_effects.values())
/* 58 */       effect.preProcess(realTime, frameCount); 
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\opengl\base\effects\EffectManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */