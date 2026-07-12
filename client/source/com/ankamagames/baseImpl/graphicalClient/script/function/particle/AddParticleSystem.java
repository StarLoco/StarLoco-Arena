/*    */ package com.ankamagames.baseImpl.graphicalClient.script.function.particle;
/*    */ 
/*    */ import com.ankamagames.framework.script.JavaFunctionEx;
/*    */ import com.ankamagames.framework.script.LuaScriptParameterDescriptor;
/*    */ import com.ankamagames.framework.script.LuaScriptParameterType;
/*    */ import com.ankamagames.graphics.isometric.particles.FreeParticleSystem;
/*    */ import com.ankamagames.graphics.isometric.particles.IsoParticleSystem;
/*    */ import com.ankamagames.graphics.isometric.particles.IsoParticleSystemFactory;
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
/*    */ public class AddParticleSystem
/*    */   extends JavaFunctionEx
/*    */ {
/*    */   public AddParticleSystem(LuaState luaState) {
/* 26 */     super(luaState);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 35 */     return "addParticleSystem";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public LuaScriptParameterDescriptor[] getParameterDescriptors() {
/* 44 */     return new LuaScriptParameterDescriptor[] { new LuaScriptParameterDescriptor("particleFileId", LuaScriptParameterType.INTEGER, false), 
/* 45 */         new LuaScriptParameterDescriptor("x", LuaScriptParameterType.INTEGER, false), new LuaScriptParameterDescriptor("y", LuaScriptParameterType.INTEGER, false), 
/* 46 */         new LuaScriptParameterDescriptor("z", LuaScriptParameterType.INTEGER, false) };
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void run(int paramCount) throws LuaException {
/* 55 */     int particleId = getParamInt(0);
/* 56 */     int worldX = getParamInt(1);
/* 57 */     int worldY = getParamInt(2);
/* 58 */     int altitude = getParamInt(3);
/*    */     
/* 60 */     FreeParticleSystem system = IsoParticleSystemFactory.getInstance().getFreeParticleSystem(particleId);
/* 61 */     system.setX(worldX);
/* 62 */     system.setY(worldY);
/* 63 */     system.setZ(altitude);
/*    */     
/* 65 */     IsoParticleSystemManager.getInstance().addParticleSystem((IsoParticleSystem)system);
/*    */     
/* 67 */     addReturnValue(system.getId());
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphicalClient\script\function\particle\AddParticleSystem.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */