/*    */ package org.fenggui.layout;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BorderLayoutData
/*    */   implements ILayoutData
/*    */ {
/*    */   protected static final int NORTH_VALUE = 0;
/*    */   protected static final int WEST_VALUE = 1;
/*    */   protected static final int EAST_VALUE = 2;
/*    */   protected static final int SOUTH_VALUE = 3;
/*    */   protected static final int CENTER_VALUE = 4;
/*    */   
/*    */   public BorderLayoutData(int v) {
/* 58 */     this.value = 0;
/*    */     this.value = v;
/*    */   } public int getValue() {
/* 61 */     return this.value;
/*    */   }
/*    */   
/*    */   public static final BorderLayoutData NORTH = new BorderLayoutData(0);
/*    */   public static final BorderLayoutData WEST = new BorderLayoutData(1);
/*    */   public static final BorderLayoutData SOUTH = new BorderLayoutData(3);
/*    */   public static final BorderLayoutData CENTER = new BorderLayoutData(4);
/*    */   public static final BorderLayoutData EAST = new BorderLayoutData(2);
/*    */   private int value;
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\layout\BorderLayoutData.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */