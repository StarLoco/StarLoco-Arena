/*    */ package gnu.trove;
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
/*    */ public final class HashFunctions
/*    */ {
/*    */   public static final int hash(double value) {
/* 26 */     long bits = Double.doubleToLongBits(value);
/* 27 */     return (int)(bits ^ bits >>> 32L);
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
/*    */   public static int hash(float value) {
/* 39 */     return Float.floatToIntBits(value * 6.6360896E8F);
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
/*    */   public static int hash(int value) {
/* 51 */     return value * 31;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static int hash(long value) {
/* 61 */     return (int)(value ^ value >> 32L) * 31;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static int hash(Object object) {
/* 70 */     return (object == null) ? 0 : object.hashCode();
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\gnu\trove\HashFunctions.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */