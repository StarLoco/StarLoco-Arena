/*    */ package com.ankamagames.graphics.isometric.tween;
/*    */ 
/*    */ import com.ankamagames.graphics.isometric.IsoWorldScene;
/*    */ import com.ankamagames.graphics.isometric.RenderProcessHandler;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
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
/*    */ public class TweenManager
/*    */   implements RenderProcessHandler
/*    */ {
/* 20 */   private static TweenManager m_instance = new TweenManager();
/*    */   
/*    */   public static TweenManager getInstance() {
/* 23 */     return m_instance;
/*    */   }
/*    */   
/* 26 */   private ArrayList<Tween> m_tweens = new ArrayList();
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void addTween(Tween tween)
/*    */   {
/* 33 */     this.m_tweens.add(tween);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void process(IsoWorldScene scene, long realTime, int frameCount)
/*    */   {
/* 44 */     for (Tween tween : this.m_tweens) {
/* 45 */       tween.process(realTime, frameCount);
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void prepareBeforeRendering(IsoWorldScene scene, int centerScreenIsoWorldX, int centerScreenIsoWorldY)
/*    */   {
/* 59 */     for (Iterator<Tween> iterator = this.m_tweens.iterator(); iterator.hasNext();) {
/* 60 */       Tween tween = (Tween)iterator.next();
/*    */       
/* 62 */       if (tween.isRemovable()) {
/* 63 */         iterator.remove();
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\graphics\isometric\tween\TweenManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */