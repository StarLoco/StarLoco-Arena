/*    */ package com.ankamagames.graphics.opengl;
/*    */ 
/*    */ import com.ankamagames.framework.graphics.effects.ColorationEffect;
/*    */ import com.ankamagames.framework.graphics.opengl.RendererEventsHandler;
/*    */ import com.ankamagames.framework.graphics.opengl.base.effects.Effect;
/*    */ import com.ankamagames.framework.graphics.opengl.base.effects.EffectManager;
/*    */ import com.ankamagames.framework.graphics.opengl.base.effects.ShaderManager;
/*    */ import com.ankamagames.graphics.effects.BlurEffect;
/*    */ import com.ankamagames.graphics.effects.GreyScaleEffect;
/*    */ import com.ankamagames.graphics.effects.OutlineEffect;
/*    */ import com.ankamagames.graphics.effects.RippleEffect;
/*    */ import com.ankamagames.graphics.effects.SceneMirrorEffect;
/*    */ import com.ankamagames.graphics.effects.SeaEffect;
/*    */ import com.ankamagames.graphics.effects.WindEffect;
/*    */ import javax.media.opengl.GLAutoDrawable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DefaultEngineConfigurator
/*    */   implements RendererEventsHandler
/*    */ {
/*    */   private boolean m_initialized;
/* 25 */   private String m_shadersPath = "";
/*    */   
/*    */   public DefaultEngineConfigurator() {
/* 28 */     this.m_initialized = false;
/*    */   }
/*    */   
/*    */   public String getShadersPath() {
/* 32 */     return this.m_shadersPath;
/*    */   }
/*    */   
/*    */   public void setShadersPath(String shadersPath) {
/* 36 */     this.m_shadersPath = shadersPath;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onInit(GLAutoDrawable glAutoDrawable) {
/* 46 */     if (this.m_initialized) {
/*    */       return;
/*    */     }
/* 49 */     if (this.m_shadersPath.equals("")) {
/*    */       return;
/*    */     }
/* 52 */     ShaderManager.getInstance().setShadersBaseDirectory(this.m_shadersPath);
/*    */     
/* 54 */     Effect[] effects = {
/* 55 */         (Effect)new BlurEffect(), 
/* 56 */         (Effect)new GreyScaleEffect(), 
/* 57 */         (Effect)new OutlineEffect(), 
/* 58 */         (Effect)new RippleEffect(), 
/* 59 */         (Effect)new WindEffect(), 
/* 60 */         (Effect)new SeaEffect(), 
/* 61 */         (Effect)new SceneMirrorEffect(), 
/* 62 */         (Effect)new ColorationEffect()
/*    */       };
/*    */     
/* 65 */     EffectManager.getInstance().registerEffects(effects);
/*    */     
/* 67 */     this.m_initialized = true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onDisplay(GLAutoDrawable glAutoDrawable) {
/* 78 */     if (!this.m_initialized)
/* 79 */       onInit(glAutoDrawable); 
/*    */   }
/*    */   
/*    */   public void onReshape(GLAutoDrawable glAutoDrawable, int x, int y, int width, int height) {}
/*    */   
/*    */   public void onDisplayChanged(GLAutoDrawable glAutoDrawable, boolean modeChanged, boolean deviceChanged) {}
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\graphics\opengl\DefaultEngineConfigurator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */