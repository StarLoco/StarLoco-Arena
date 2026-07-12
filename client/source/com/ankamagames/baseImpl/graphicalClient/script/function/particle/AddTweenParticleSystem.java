/*    */ package com.ankamagames.baseImpl.graphicalClient.script.function.particle;
/*    */ 
/*    */ import com.ankamagames.framework.script.JavaFunctionEx;
/*    */ import com.ankamagames.framework.script.LuaScriptParameterDescriptor;
/*    */ import com.ankamagames.framework.script.LuaScriptParameterType;
/*    */ import com.ankamagames.graphics.isometric.IsoWorldTarget;
/*    */ import com.ankamagames.graphics.isometric.particles.FreeParticleSystem;
/*    */ import com.ankamagames.graphics.isometric.particles.IsoParticleSystem;
/*    */ import com.ankamagames.graphics.isometric.particles.IsoParticleSystemFactory;
/*    */ import com.ankamagames.graphics.isometric.particles.IsoParticleSystemManager;
/*    */ import com.ankamagames.graphics.isometric.tween.ParabolicTween;
/*    */ import com.ankamagames.graphics.isometric.tween.Tween;
/*    */ import com.ankamagames.graphics.isometric.tween.TweenManager;
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
/*    */ public class AddTweenParticleSystem
/*    */   extends JavaFunctionEx
/*    */ {
/*    */   public AddTweenParticleSystem(LuaState luaState) {
/* 28 */     super(luaState);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 37 */     return "addTweenParticleSystem";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public LuaScriptParameterDescriptor[] getParameterDescriptors() {
/* 46 */     return new LuaScriptParameterDescriptor[] { new LuaScriptParameterDescriptor("particleFileId", LuaScriptParameterType.INTEGER, false), 
/* 47 */         new LuaScriptParameterDescriptor("startX", LuaScriptParameterType.INTEGER, false), 
/* 48 */         new LuaScriptParameterDescriptor("startY", LuaScriptParameterType.INTEGER, false), 
/* 49 */         new LuaScriptParameterDescriptor("startZ", LuaScriptParameterType.INTEGER, false), 
/* 50 */         new LuaScriptParameterDescriptor("destX", LuaScriptParameterType.INTEGER, false), 
/* 51 */         new LuaScriptParameterDescriptor("destY", LuaScriptParameterType.INTEGER, false), 
/* 52 */         new LuaScriptParameterDescriptor("destZ", LuaScriptParameterType.INTEGER, false), 
/* 53 */         new LuaScriptParameterDescriptor("angle", LuaScriptParameterType.INTEGER, false), 
/* 54 */         new LuaScriptParameterDescriptor("type", LuaScriptParameterType.INTEGER, false), 
/* 55 */         new LuaScriptParameterDescriptor("timeCoef", LuaScriptParameterType.NUMBER, true) };
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void run(int paramCount) throws LuaException {
/*    */     ParabolicTween tween;
/* 64 */     int particleFileId = getParamInt(0);
/* 65 */     int startX = getParamInt(1);
/* 66 */     int startY = getParamInt(2);
/* 67 */     int startZ = getParamInt(3);
/* 68 */     int destX = getParamInt(4);
/* 69 */     int destY = getParamInt(5);
/* 70 */     int destZ = getParamInt(6);
/* 71 */     int angle = getParamInt(7);
/* 72 */     getParamInt(8);
/* 73 */     double timeCoef = (paramCount >= 10) ? getParamDouble(9) : -1.0D;
/*    */     
/* 75 */     FreeParticleSystem system = IsoParticleSystemFactory.getInstance().getFreeParticleSystem(particleFileId);
/* 76 */     system.setX(startX);
/* 77 */     system.setY(startY);
/* 78 */     system.setZ(startZ);
/*    */ 
/*    */     
/* 81 */     if (timeCoef < 0.0D) {
/* 82 */       tween = new ParabolicTween((IsoWorldTarget)system, destX, destY, destZ, angle);
/*    */     } else {
/* 84 */       tween = new ParabolicTween((IsoWorldTarget)system, destX, destY, destZ, angle, timeCoef);
/*    */     } 
/* 86 */     IsoParticleSystemManager.getInstance().addParticleSystem((IsoParticleSystem)system);
/* 87 */     TweenManager.getInstance().addTween((Tween)tween);
/*    */     
/* 89 */     addReturnValue(system.getId());
/* 90 */     addReturnValue((int)tween.getTweenDuration());
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphicalClient\script\function\particle\AddTweenParticleSystem.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */