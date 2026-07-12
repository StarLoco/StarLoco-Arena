/*     */ package org.keplerproject.luajava;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public final class LuaStateFactory
/*     */ {
/*  44 */   private static final List states = new ArrayList();
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
/*     */   public static synchronized LuaState newLuaState() {
/*  58 */     int i = getNextStateIndex();
/*  59 */     LuaState luaState = new LuaState(i);
/*     */     
/*  61 */     states.add(i, luaState);
/*     */     
/*  63 */     return luaState;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized LuaState getExistingState(int paramInt) {
/*  73 */     return states.get(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized int insertLuaState(LuaState paramLuaState) {
/*     */     int i;
/*  85 */     for (i = 0; i < states.size(); i++) {
/*     */       
/*  87 */       LuaState luaState = states.get(i);
/*     */       
/*  89 */       if (luaState != null)
/*     */       {
/*  91 */         if (luaState.getCPtrPeer() == paramLuaState.getCPtrPeer()) {
/*  92 */           return i;
/*     */         }
/*     */       }
/*     */     } 
/*  96 */     i = getNextStateIndex();
/*     */     
/*  98 */     while (states.size() <= i)
/*     */     {
/* 100 */       states.add(null);
/*     */     }
/* 102 */     states.set(i, paramLuaState);
/*     */     
/* 104 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized void removeLuaState(int paramInt) {
/* 113 */     states.add(paramInt, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static synchronized int getNextStateIndex() {
/*     */     byte b;
/* 123 */     for (b = 0; b < states.size() && states.get(b) != null; b++);
/* 124 */     return b;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\keplerproject\luajava\LuaStateFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */