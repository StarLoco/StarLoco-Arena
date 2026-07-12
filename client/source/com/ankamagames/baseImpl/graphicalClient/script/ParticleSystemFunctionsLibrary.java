/*    */ package com.ankamagames.baseImpl.graphicalClient.script;
/*    */ 
/*    */ import com.ankamagames.baseImpl.graphicalClient.script.function.particle.AddParticleSystem;
/*    */ import com.ankamagames.baseImpl.graphicalClient.script.function.particle.AddTweenParticleSystem;
/*    */ import com.ankamagames.baseImpl.graphicalClient.script.function.particle.RemoveParticleSystem;
/*    */ import com.ankamagames.framework.script.JavaFunctionsLibrary;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ParticleSystemFunctionsLibrary
/*    */   extends JavaFunctionsLibrary
/*    */ {
/* 19 */   private static ParticleSystemFunctionsLibrary m_instance = new ParticleSystemFunctionsLibrary();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ParticleSystemFunctionsLibrary() {
/* 25 */     super("Particle");
/* 26 */     registerFunctionClass(AddTweenParticleSystem.class);
/* 27 */     registerFunctionClass(AddParticleSystem.class);
/* 28 */     registerFunctionClass(RemoveParticleSystem.class);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ParticleSystemFunctionsLibrary getInstance() {
/* 35 */     return m_instance;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphicalClient\script\ParticleSystemFunctionsLibrary.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */