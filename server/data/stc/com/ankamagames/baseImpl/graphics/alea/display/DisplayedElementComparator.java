/*    */ package com.ankamagames.baseImpl.graphics.alea.display;
/*    */ 
/*    */ import java.util.Comparator;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum DisplayedElementComparator
/*    */ {
/* 23 */   DEPTH_COMPARATOR(new Comparator()), 
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 43 */   MOUSE_DISTANCE_COMPARATOR(new Comparator());
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
/*    */   private Comparator<DisplayedElement> m_comparator;
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
/*    */   private DisplayedElementComparator(Comparator<DisplayedElement> comparator)
/*    */   {
/* 67 */     this.m_comparator = comparator;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public Comparator<DisplayedElement> getComparator()
/*    */   {
/* 74 */     return this.m_comparator;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphics\alea\display\DisplayedElementComparator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */