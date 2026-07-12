/*    */ package com.ankamagames.baseImpl.graphicalClient.script;
/*    */ 
/*    */ import com.ankamagames.framework.script.JavaFunctionsLibrary;
/*    */ import org.keplerproject.luajava.LuaException;
/*    */ import org.keplerproject.luajava.LuaState;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VisualEffectFunctionsLibrary
/*    */   extends JavaFunctionsLibrary
/*    */ {
/* 18 */   private static VisualEffectFunctionsLibrary m_instance = new VisualEffectFunctionsLibrary();
/*    */   
/*    */ 
/*    */ 
/*    */   protected VisualEffectFunctionsLibrary()
/*    */   {
/* 24 */     super("VisualEffect");
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public static VisualEffectFunctionsLibrary getInstance()
/*    */   {
/* 31 */     return m_instance;
/*    */   }
/*    */   
/*    */   public void importLibs(LuaState L)
/*    */     throws LuaException
/*    */   {}
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphicalClient\script\VisualEffectFunctionsLibrary.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */