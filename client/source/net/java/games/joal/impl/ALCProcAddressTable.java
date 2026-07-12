/*    */ package net.java.games.joal.impl;
/*    */ 
/*    */ import java.lang.reflect.Field;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ALCProcAddressTable
/*    */ {
/*    */   public long _addressof_alcCaptureCloseDevice;
/*    */   public long _addressof_alcCaptureOpenDevice;
/*    */   public long _addressof_alcCaptureSamples;
/*    */   public long _addressof_alcCaptureStart;
/*    */   public long _addressof_alcCaptureStop;
/*    */   public long _addressof_alcCloseDevice;
/*    */   public long _addressof_alcCreateContext;
/*    */   public long _addressof_alcDestroyContext;
/*    */   public long _addressof_alcGetContextsDevice;
/*    */   public long _addressof_alcGetCurrentContext;
/*    */   public long _addressof_alcGetEnumValue;
/*    */   public long _addressof_alcGetError;
/*    */   public long _addressof_alcGetIntegerv;
/*    */   public long _addressof_alcGetString;
/*    */   public long _addressof_alcIsExtensionPresent;
/*    */   public long _addressof_alcMakeContextCurrent;
/*    */   public long _addressof_alcOpenDevice;
/*    */   public long _addressof_alcProcessContext;
/*    */   public long _addressof_alcSuspendContext;
/*    */   
/*    */   public long getAddressFor(String paramString) {
/* 61 */     String str = "_addressof_" + paramString;
/*    */     try {
/* 63 */       Field field = getClass().getField(str);
/* 64 */       return field.getLong(this);
/* 65 */     } catch (Exception exception) {
/*    */ 
/*    */       
/* 68 */       throw new RuntimeException("WARNING: Address query failed for \"" + paramString + "\"; it's either statically linked or is not a known " + "function", exception);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\net\java\games\joal\impl\ALCProcAddressTable.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */