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
/* 23 */   DEPTH_COMPARATOR(new Comparator<DisplayedElement>()
/*    */     {
/*    */       public int compare(DisplayedElement o1, DisplayedElement o2) {
/* 26 */         float z1 = o1.getZOrder();
/* 27 */         float z2 = o2.getZOrder();
/*    */         
/* 29 */         if (z1 > z2)
/* 30 */           return -1; 
/* 31 */         if (z1 < z2) {
/* 32 */           return 1;
/*    */         }
/* 34 */         return 0;
/*    */       }
/*    */     }),
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 43 */   MOUSE_DISTANCE_COMPARATOR(new Comparator<DisplayedElement>()
/*    */     {
/*    */       public int compare(DisplayedElement o1, DisplayedElement o2) {
/* 46 */         double d1 = o1.getDistanceFromTopToMouse();
/* 47 */         double d2 = o2.getDistanceFromTopToMouse();
/*    */         
/* 49 */         if (d1 > d2)
/* 50 */           return 1; 
/* 51 */         if (d1 < d2) {
/* 52 */           return -1;
/*    */         }
/* 54 */         return 0;
/*    */       }
/*    */     });
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private Comparator<DisplayedElement> m_comparator;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   DisplayedElementComparator(Comparator<DisplayedElement> comparator) {
/* 67 */     this.m_comparator = comparator;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Comparator<DisplayedElement> getComparator() {
/* 74 */     return this.m_comparator;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphics\alea\display\DisplayedElementComparator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */