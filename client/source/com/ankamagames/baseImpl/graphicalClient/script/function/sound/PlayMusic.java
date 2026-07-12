/*    */ package com.ankamagames.baseImpl.graphicalClient.script.function.sound;
/*    */ 
/*    */ import com.ankamagames.framework.script.JavaFunctionEx;
/*    */ import com.ankamagames.framework.script.LuaScriptParameterDescriptor;
/*    */ import com.ankamagames.framework.script.LuaScriptParameterType;
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
/*    */ public class PlayMusic
/*    */   extends JavaFunctionEx
/*    */ {
/*    */   public PlayMusic(LuaState luaState) {
/* 21 */     super(luaState);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 31 */     return "playMusic";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public LuaScriptParameterDescriptor[] getParameterDescriptors() {
/* 41 */     return new LuaScriptParameterDescriptor[] {
/* 42 */         new LuaScriptParameterDescriptor("musicFileId", LuaScriptParameterType.INTEGER, false)
/*    */       };
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void run(int paramCount) throws LuaException {
/* 53 */     int id = getParamInt(0);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphicalClient\script\function\sound\PlayMusic.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */