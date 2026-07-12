/*    */ package org.keplerproject.luajava;
/*    */ 
/*    */ import java.lang.reflect.InvocationHandler;
/*    */ import java.lang.reflect.Method;
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
/*    */ public class LuaInvocationHandler
/*    */   implements InvocationHandler
/*    */ {
/*    */   private LuaObject obj;
/*    */   
/*    */   public LuaInvocationHandler(LuaObject paramLuaObject) {
/* 45 */     this.obj = paramLuaObject;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object invoke(Object paramObject, Method paramMethod, Object[] paramArrayOfObject) throws LuaException {
/* 53 */     synchronized (this.obj.L) {
/*    */       Object object;
/* 55 */       String str = paramMethod.getName();
/* 56 */       LuaObject luaObject = this.obj.getField(str);
/*    */       
/* 58 */       if (luaObject.isNil())
/*    */       {
/* 60 */         return null;
/*    */       }
/*    */       
/* 63 */       Class<?> clazz = paramMethod.getReturnType();
/*    */ 
/*    */ 
/*    */       
/* 67 */       if (clazz.equals(Void.class) || clazz.equals(void.class)) {
/*    */         
/* 69 */         luaObject.call(paramArrayOfObject, 0);
/* 70 */         object = null;
/*    */       }
/*    */       else {
/*    */         
/* 74 */         object = luaObject.call(paramArrayOfObject, 1)[0];
/* 75 */         if (object != null && object instanceof Double)
/*    */         {
/* 77 */           object = LuaState.convertLuaNumber((Double)object, clazz);
/*    */         }
/*    */       } 
/*    */       
/* 81 */       return object;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\keplerproject\luajava\LuaInvocationHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */