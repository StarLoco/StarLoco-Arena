/*    */ package com.ankamagames.dofusarena.client.core.game.actor;
/*    */ 
/*    */ import com.ankamagames.baseImpl.graphics.alea.mobile.MobileSelectionChangeListener;
/*    */ import com.ankamagames.baseImpl.graphics.alea.mobile.PathMobile;
/*    */ import com.ankamagames.dofusarena.client.core.DofusArenaConfiguration;
/*    */ import com.ankamagames.framework.graphics.animation.descriptors.library.BaseDescriptorLibrary;
/*    */ import com.ankamagames.framework.graphics.animation.descriptors.library.DescriptorLibraryManager;
/*    */ import com.ankamagames.framework.graphics.animation.descriptors.library.ModifiableDescriptorLibrary;
/*    */ import com.ankamagames.framework.graphics.opengl.base.effects.EffectManager;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class Actor
/*    */   extends PathMobile
/*    */ {
/*    */   public Actor(long id)
/*    */   {
/* 30 */     super(id);
/*    */     
/* 32 */     setEffect(EffectManager.getInstance().getEffect("outline"));
/* 33 */     setApplyEffectTochild(false);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setGfx(String gfx)
/*    */   {
/* 42 */     setDescriptorLibrary(createGfxDescriptorLibrary(gfx));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   protected static ModifiableDescriptorLibrary createGfxDescriptorLibrary(String gfx)
/*    */   {
/*    */     try
/*    */     {
/* 53 */       String gfxFile = DofusArenaConfiguration.getInstance().getString("mobileGfxPath");
/* 54 */       gfxFile = String.format(gfxFile, new Object[] { gfx });
/* 55 */       BaseDescriptorLibrary baseLibrary = DescriptorLibraryManager.getInstance().getDescriptorLibrary(gfxFile);
/* 56 */       return new ModifiableDescriptorLibrary(baseLibrary);
/*    */     } catch (Exception e) {
/* 58 */       m_logger.error("Erreur dans createGfxDescriptorLibrary : ", e);
/*    */     }
/* 60 */     return null;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void addSelectionChangedListener(MobileSelectionChangeListener listener)
/*    */   {
/* 70 */     super.addSelectionChangedListener(listener);
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\game\actor\Actor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */