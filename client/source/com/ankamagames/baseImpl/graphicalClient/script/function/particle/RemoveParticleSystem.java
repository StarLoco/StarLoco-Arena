/*    */ package com.ankamagames.baseImpl.graphicalClient.script.function.particle;
/*    */ 
/*    */ import com.ankamagames.framework.script.JavaFunctionEx;
/*    */ import com.ankamagames.framework.script.LuaScriptParameterDescriptor;
/*    */ import com.ankamagames.framework.script.LuaScriptParameterType;
/*    */ import com.ankamagames.graphics.isometric.particles.IsoParticleSystemManager;
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
/*    */ 
/*    */ public class RemoveParticleSystem
/*    */   extends JavaFunctionEx
/*    */ {
/*    */   public RemoveParticleSystem(LuaState luaState) {
/* 23 */     super(luaState);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 32 */     return "removeParticleSystem";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public LuaScriptParameterDescriptor[] getParameterDescriptors() {
/* 41 */     return new LuaScriptParameterDescriptor[] { new LuaScriptParameterDescriptor("particleUniqueId", LuaScriptParameterType.INTEGER, false) };
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void run(int paramCount) throws LuaException {
/* 50 */     int particleUniqueId = getParamInt(0);
/*    */     
/* 52 */     IsoParticleSystemManager.getInstance().removeParticleSystem(particleUniqueId);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphicalClient\script\function\particle\RemoveParticleSystem.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */