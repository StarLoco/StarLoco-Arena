/*    */ package com.ankamagames.baseImpl.graphicalClient.script.function.sound;
/*    */ 
/*    */ import com.ankamagames.framework.script.JavaFunctionEx;
/*    */ import com.ankamagames.framework.script.LuaScriptParameterDescriptor;
/*    */ import com.ankamagames.framework.script.LuaScriptParameterType;
/*    */ import com.ankamagames.framework.sounds.SoundManager;
/*    */ import com.ankamagames.framework.sounds.group.DefaultSourceGroup;
/*    */ import java.io.PrintStream;
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
/*    */ public class PlaySound
/*    */   extends JavaFunctionEx
/*    */ {
/*    */   public PlaySound(LuaState luaState)
/*    */   {
/* 25 */     super(luaState);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public String getName()
/*    */   {
/* 34 */     return "playSound";
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public LuaScriptParameterDescriptor[] getParameterDescriptors()
/*    */   {
/* 43 */     return new LuaScriptParameterDescriptor[] {
/* 44 */       new LuaScriptParameterDescriptor("soundFileId", LuaScriptParameterType.INTEGER, false), 
/* 45 */       new LuaScriptParameterDescriptor("isStereo", LuaScriptParameterType.BOOLEAN, true) };
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void run(int paramCount)
/*    */     throws LuaException
/*    */   {
/* 54 */     int soundFileId = getParamInt(0);
/* 55 */     boolean isStereo = (paramCount >= 2) && (getParamBool(1));
/* 56 */     DefaultSourceGroup group = (DefaultSourceGroup)SoundManager.getInstance().getGroupByName("SoundScriptGroup");
/* 57 */     if (group != null) {
/*    */       try {
/* 59 */         group.playSound(soundFileId, true, isStereo, false);
/*    */       } catch (Exception e) {
/* 61 */         System.err.println("soundExtension or soundPath not initialized");
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphicalClient\script\function\sound\PlaySound.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */