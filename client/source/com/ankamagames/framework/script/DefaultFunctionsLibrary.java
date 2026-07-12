/*     */ package com.ankamagames.framework.script;
/*     */ 
/*     */ import org.keplerproject.luajava.LuaException;
/*     */ import org.keplerproject.luajava.LuaState;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultFunctionsLibrary
/*     */   extends JavaFunctionsLibrary
/*     */ {
/*     */   private class GetTimer
/*     */     extends JavaFunctionEx
/*     */   {
/*     */     public GetTimer(LuaState luaState) {
/*  24 */       super(luaState);
/*     */     }
/*     */     
/*     */     public String getName() {
/*  28 */       return "getTimer";
/*     */     }
/*     */     
/*     */     public LuaScriptParameterDescriptor[] getParameterDescriptors() {
/*  32 */       return null;
/*     */     }
/*     */     
/*     */     public void run(int paramCount) throws LuaException {
/*  36 */       LuaScript script = getScriptObject(this.L);
/*  37 */       if (script != null) {
/*  38 */         addReturnValue(script.getTime());
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class Invoke
/*     */     extends JavaFunctionEx
/*     */   {
/*     */     public Invoke(LuaState luaState) {
/*  51 */       super(luaState);
/*     */     }
/*     */     
/*     */     public String getName() {
/*  55 */       return "invoke";
/*     */     }
/*     */     
/*     */     public LuaScriptParameterDescriptor[] getParameterDescriptors() {
/*  59 */       return new LuaScriptParameterDescriptor[] {
/*  60 */           new LuaScriptParameterDescriptor("time", LuaScriptParameterType.INTEGER, false), 
/*  61 */           new LuaScriptParameterDescriptor("loopCount", LuaScriptParameterType.INTEGER, false), 
/*  62 */           new LuaScriptParameterDescriptor("funcName", LuaScriptParameterType.STRING, false), 
/*  63 */           new LuaScriptParameterDescriptor("funcParams", LuaScriptParameterType.BLOOPS, true) };
/*     */     }
/*     */     
/*     */     public void run(int paramCount) throws LuaException {
/*  67 */       LuaScript script = getScriptObject(this.L);
/*     */       
/*  69 */       int time = getParamInt(0);
/*  70 */       int loopCount = getParamInt(1);
/*  71 */       String funcName = getParamString(2);
/*     */       
/*  73 */       Object[] args = getParams(3, paramCount);
/*     */       
/*  75 */       script.registerTask(time, loopCount, funcName, args);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class SetInterval
/*     */     extends JavaFunctionEx
/*     */   {
/*     */     public SetInterval(LuaState luaState) {
/*  87 */       super(luaState);
/*     */     }
/*     */     
/*     */     public String getName() {
/*  91 */       return "setInterval";
/*     */     }
/*     */     
/*     */     public LuaScriptParameterDescriptor[] getParameterDescriptors() {
/*  95 */       return new LuaScriptParameterDescriptor[] {
/*  96 */           new LuaScriptParameterDescriptor("time", LuaScriptParameterType.INTEGER, false), 
/*  97 */           new LuaScriptParameterDescriptor("funcName", LuaScriptParameterType.STRING, false), 
/*  98 */           new LuaScriptParameterDescriptor("funcParams", LuaScriptParameterType.BLOOPS, true) };
/*     */     }
/*     */     
/*     */     public void run(int paramCount) throws LuaException {
/* 102 */       LuaScript script = getScriptObject(this.L);
/*     */       
/* 104 */       int time = getParamInt(0);
/* 105 */       String funcName = getParamString(1);
/*     */       
/* 107 */       Object[] args = getParams(2, paramCount);
/*     */       
/* 109 */       script.registerTask(time, -1, funcName, args);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private class Interrupt
/*     */     extends JavaFunctionEx
/*     */   {
/*     */     public Interrupt(LuaState luaState) {
/* 119 */       super(luaState);
/*     */     }
/*     */     
/*     */     public String getName() {
/* 123 */       return "interrupt";
/*     */     }
/*     */     
/*     */     public LuaScriptParameterDescriptor[] getParameterDescriptors() {
/* 127 */       return null;
/*     */     }
/*     */     
/*     */     public void run(int paramCount) throws LuaException {
/* 131 */       LuaScript script = getScriptObject(this.L);
/* 132 */       if (script != null) {
/* 133 */         script.interrupt();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private class Trace
/*     */     extends JavaFunctionEx
/*     */   {
/*     */     public Trace(LuaState luaState) {
/* 144 */       super(luaState);
/*     */     }
/*     */     
/*     */     public String getName() {
/* 148 */       return "trace";
/*     */     }
/*     */     
/*     */     public LuaScriptParameterDescriptor[] getParameterDescriptors() {
/* 152 */       return new LuaScriptParameterDescriptor[] {
/* 153 */           new LuaScriptParameterDescriptor("message", LuaScriptParameterType.STRING, false), 
/* 154 */           new LuaScriptParameterDescriptor("message", LuaScriptParameterType.BLOOPS, true)
/*     */         };
/*     */     }
/*     */     
/*     */     public void run(int paramCount) throws LuaException {
/* 159 */       StringBuilder builder = new StringBuilder(getParamString(0));
/* 160 */       for (int i = 1; i < paramCount; i++) {
/* 161 */         builder.append(", ").append(getParamString(i));
/*     */       }
/* 163 */       System.out.println(builder.toString());
/*     */     }
/*     */   }
/*     */   
/* 167 */   private static final DefaultFunctionsLibrary m_instance = new DefaultFunctionsLibrary();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultFunctionsLibrary() {
/* 173 */     super(null);
/* 174 */     registerGlobalFunctionClass((Class)GetTimer.class);
/* 175 */     registerGlobalFunctionClass((Class)Invoke.class);
/* 176 */     registerGlobalFunctionClass((Class)SetInterval.class);
/* 177 */     registerGlobalFunctionClass((Class)Trace.class);
/* 178 */     registerGlobalFunctionClass((Class)Interrupt.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DefaultFunctionsLibrary getInstance() {
/* 185 */     return m_instance;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\script\DefaultFunctionsLibrary.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */