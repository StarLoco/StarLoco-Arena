/*    */ package com.ankamagames.graphics.isometric.tween;
/*    */ 
/*    */ import com.ankamagames.graphics.isometric.IsoWorldTarget;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
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
/*    */ public abstract class Tween
/*    */ {
/* 19 */   private boolean m_removable = false;
/* 20 */   private List<TweenListener> m_listeners = null;
/*    */   protected IsoWorldTarget m_target;
/*    */   
/*    */   public Tween(IsoWorldTarget target)
/*    */   {
/* 25 */     this.m_target = target;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void addListener(TweenListener listener)
/*    */   {
/* 34 */     if (this.m_listeners == null) {
/* 35 */       this.m_listeners = new ArrayList();
/*    */     }
/* 37 */     this.m_listeners.add(listener);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void removeListener(TweenListener listener)
/*    */   {
/* 46 */     if (this.m_listeners == null) {
/* 47 */       return;
/*    */     }
/* 49 */     this.m_listeners.remove(listener);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public boolean isRemovable()
/*    */   {
/* 56 */     return this.m_removable;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void endTween()
/*    */   {
/* 63 */     this.m_removable = true;
/*    */     
/* 65 */     if (this.m_listeners != null) {
/* 66 */       for (TweenListener listener : this.m_listeners) {
/* 67 */         listener.onTweenEnd(this);
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   public abstract double getTweenDuration();
/*    */   
/*    */   public abstract void process(long paramLong, int paramInt);
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\graphics\isometric\tween\Tween.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */