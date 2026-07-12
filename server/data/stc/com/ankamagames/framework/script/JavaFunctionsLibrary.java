/*     */ package com.ankamagames.framework.script;
/*     */ 
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.util.ArrayList;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class JavaFunctionsLibrary
/*     */ {
/*     */   private String m_name;
/*     */   private ArrayList<Class<? extends JavaFunctionEx>> m_globalFunctionClasses;
/*     */   private ArrayList<Class<? extends JavaFunctionEx>> m_functionClasses;
/*     */   
/*     */   public String getName()
/*     */   {
/*  69 */     return this.m_name;
/*     */   }
/*     */   
/*     */   protected JavaFunctionsLibrary(String name) {
/*  73 */     this.m_name = name;
/*  74 */     this.m_functionClasses = new ArrayList();
/*  75 */     this.m_globalFunctionClasses = new ArrayList();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Iterable<Class<? extends JavaFunctionEx>> getFunctionClasses()
/*     */   {
/*  82 */     return this.m_functionClasses;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Iterable<Class<? extends JavaFunctionEx>> getGlobalFunctionClasses()
/*     */   {
/*  89 */     return this.m_globalFunctionClasses;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void importLibs(LuaState L)
/*     */     throws LuaException
/*     */   {
/*  99 */     L.newTable();
/* 100 */     for (Class<? extends JavaFunctionEx> functionClass : this.m_functionClasses) {
/* 101 */       JavaFunctionEx function = createFunctionFromClass(functionClass, L);
/* 102 */       if (function != null) {
/* 103 */         L.pushString(function.getName());
/* 104 */         L.pushJavaFunction(function);
/* 105 */         L.setTable(-3);
/*     */       }
/*     */     }
/* 108 */     if (getName() != null) {
/* 109 */       L.setGlobal(getName());
/*     */     }
/*     */     
/*     */ 
/* 113 */     for (Class<? extends JavaFunctionEx> functionClass : this.m_globalFunctionClasses) {
/* 114 */       JavaFunctionEx function = createFunctionFromClass(functionClass, L);
/* 115 */       if (function != null) {
/* 116 */         function.register();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JavaFunctionEx createFunctionFromClass(Class<? extends JavaFunctionEx> functionClass, LuaState L)
/*     */   {
/* 130 */     JavaFunctionEx function = null;
/*     */     try
/*     */     {
/* 133 */       if (functionClass.getEnclosingClass() != null) {
/* 134 */         Constructor<? extends JavaFunctionEx> contructor = functionClass.getDeclaredConstructor(new Class[] { getClass(), LuaState.class });
/* 135 */         contructor.setAccessible(true);
/* 136 */         function = (JavaFunctionEx)contructor.newInstance(new Object[] { this, L });
/*     */       } else {
/* 138 */         function = (JavaFunctionEx)functionClass.getConstructor(new Class[] { LuaState.class }).newInstance(new Object[] { L });
/*     */       }
/*     */     } catch (NoSuchMethodException e) {
/* 141 */       e.printStackTrace();
/*     */     } catch (InstantiationException e) {
/* 143 */       e.printStackTrace();
/*     */     } catch (IllegalAccessException e) {
/* 145 */       e.printStackTrace();
/*     */     } catch (InvocationTargetException e) {
/* 147 */       e.printStackTrace();
/*     */     }
/* 149 */     return function;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void registerFunctionClass(Class<? extends JavaFunctionEx> functionClass)
/*     */   {
/* 158 */     this.m_functionClasses.add(functionClass);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void registerGlobalFunctionClass(Class<? extends JavaFunctionEx> functionClass)
/*     */   {
/* 168 */     this.m_globalFunctionClasses.add(functionClass);
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\script\JavaFunctionsLibrary.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */