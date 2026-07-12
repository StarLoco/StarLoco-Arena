/*    */ package org.keplerproject.luajava;
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
/*    */ 
/*    */ 
/*    */ public abstract class JavaFunction
/*    */ {
/*    */   protected LuaState L;
/*    */   
/*    */   public abstract int execute() throws LuaException;
/*    */   
/*    */   public JavaFunction(LuaState paramLuaState) {
/* 57 */     this.L = paramLuaState;
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
/*    */   
/*    */   public LuaObject getParam(int paramInt) {
/* 70 */     return this.L.getLuaObject(paramInt);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void register(String paramString) throws LuaException {
/* 80 */     synchronized (this.L) {
/*    */       
/* 82 */       this.L.pushJavaFunction(this);
/* 83 */       this.L.setGlobal(paramString);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\keplerproject\luajava\JavaFunction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */