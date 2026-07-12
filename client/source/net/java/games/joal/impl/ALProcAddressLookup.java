/*     */ package net.java.games.joal.impl;
/*     */ 
/*     */ import com.sun.gluegen.runtime.DynamicLookupHelper;
/*     */ import com.sun.gluegen.runtime.NativeLibrary;
/*     */ import com.sun.gluegen.runtime.ProcAddressHelper;
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
/*     */ public class ALProcAddressLookup
/*     */ {
/*  42 */   private static final ALProcAddressTable alTable = new ALProcAddressTable();
/*     */   private static volatile boolean alTableInitialized = false;
/*  44 */   private static final ALCProcAddressTable alcTable = new ALCProcAddressTable();
/*     */   private static volatile boolean alcTableInitialized = false;
/*  46 */   private static final DynamicLookup lookup = new DynamicLookup();
/*  47 */   private static volatile NativeLibrary openAL = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class DynamicLookup
/*     */     implements DynamicLookupHelper
/*     */   {
/*     */     public long dynamicLookupFunction(String param1String) {
/*  56 */       if (ALProcAddressLookup.openAL == null) {
/*     */ 
/*     */ 
/*     */         
/*  60 */         ALProcAddressLookup.openAL = NativeLibrary.open("OpenAL32", "openal", "OpenAL", false, ((ALProcAddressLookup.class$net$java$games$joal$impl$ALProcAddressLookup == null) ? (ALProcAddressLookup.class$net$java$games$joal$impl$ALProcAddressLookup = ALProcAddressLookup.class$("net.java.games.joal.impl.ALProcAddressLookup")) : ALProcAddressLookup.class$net$java$games$joal$impl$ALProcAddressLookup).getClassLoader());
/*     */ 
/*     */         
/*  63 */         if (ALProcAddressLookup.openAL == null) {
/*  64 */           throw new RuntimeException("Unable to find and load OpenAL library");
/*     */         }
/*     */       } 
/*  67 */       return ALProcAddressLookup.openAL.lookupFunction(param1String);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void resetALProcAddressTable() {
/*  72 */     if (!alTableInitialized) {
/*  73 */       synchronized (ALProcAddressLookup.class) {
/*  74 */         if (!alTableInitialized) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*  82 */           ProcAddressHelper.resetProcAddressTable(alTable, lookup);
/*  83 */           alTableInitialized = true;
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public static void resetALCProcAddressTable() {
/*  90 */     if (!alcTableInitialized) {
/*  91 */       synchronized (ALProcAddressLookup.class) {
/*  92 */         if (!alcTableInitialized) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 100 */           ProcAddressHelper.resetProcAddressTable(alcTable, lookup);
/* 101 */           alcTableInitialized = true;
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public static ALProcAddressTable getALProcAddressTable() {
/* 108 */     return alTable;
/*     */   }
/*     */   
/*     */   public static ALCProcAddressTable getALCProcAddressTable() {
/* 112 */     return alcTable;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\net\java\games\joal\impl\ALProcAddressLookup.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */