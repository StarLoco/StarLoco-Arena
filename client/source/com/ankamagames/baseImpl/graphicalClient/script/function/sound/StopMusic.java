/*    */ package com.ankamagames.baseImpl.graphicalClient.script.function.sound;
/*    */ 
/*    */ import com.ankamagames.framework.script.JavaFunctionEx;
/*    */ import com.ankamagames.framework.script.LuaScriptParameterDescriptor;
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
/*    */ public class StopMusic
/*    */   extends JavaFunctionEx
/*    */ {
/*    */   public StopMusic(LuaState luaState) {
/* 20 */     super(luaState);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 29 */     return "stopMusic";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public LuaScriptParameterDescriptor[] getParameterDescriptors() {
/* 38 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void run(int paramCount) throws LuaException {
/* 47 */     System.err.println("stop music not yet implemented");
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphicalClient\script\function\sound\StopMusic.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */