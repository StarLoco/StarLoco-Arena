/*     */ package org.fenggui.io;
/*     */ 
/*     */ import java.io.IOException;
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
/*     */ public abstract class InputOutputStream
/*     */ {
/*  59 */   private final List<String> warnings = new ArrayList<String>();
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
/*     */   public abstract int processAttribute(String paramString, int paramInt) throws IOException, MissingAttributeException, MalformedElementException;
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
/*     */   public abstract double processAttribute(String paramString, double paramDouble) throws IOException, MissingAttributeException, MalformedElementException;
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
/*     */   public float processAttribute(String name, float value) throws IOException, MissingAttributeException, MalformedElementException {
/* 104 */     return (float)processAttribute(name, value);
/*     */   }
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
/*     */   public abstract boolean processAttribute(String paramString, boolean paramBoolean) throws IOException, MissingAttributeException, MalformedElementException;
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
/*     */   public abstract String processAttribute(String paramString1, String paramString2) throws IOException, MissingAttributeException, MalformedElementException;
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
/*     */   public abstract <T extends IOStreamSaveable> T processChild(String paramString, T paramT, Class<T> paramClass) throws IOException, IOStreamException;
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
/*     */   public float processAttribute(String name, float value, float defaultValue) throws IOException, MalformedElementException {
/* 166 */     return (float)processAttribute(name, value, defaultValue);
/*     */   }
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
/*     */   public int processAttribute(String name, int value, int defaultValue) throws IOException, MalformedElementException {
/*     */     try {
/* 185 */       return processAttribute(name, value);
/*     */     }
/* 187 */     catch (MissingAttributeException e) {
/*     */       
/* 189 */       return defaultValue;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double processAttribute(String name, double value, double defaultValue) throws IOException, MalformedElementException {
/*     */     try {
/* 209 */       return processAttribute(name, value);
/*     */     }
/* 211 */     catch (MissingAttributeException e) {
/*     */       
/* 213 */       return defaultValue;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean processAttribute(String name, boolean value, boolean defaultValue) throws IOException, MalformedElementException {
/*     */     try {
/* 233 */       return processAttribute(name, value);
/*     */     }
/* 235 */     catch (MissingAttributeException e) {
/*     */       
/* 237 */       return defaultValue;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String processAttribute(String name, String value, String defaultValue) throws IOException, MalformedElementException {
/*     */     try {
/* 257 */       return processAttribute(name, value);
/*     */     }
/* 259 */     catch (MissingAttributeException e) {
/*     */       
/* 261 */       return defaultValue;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T extends IOStreamSaveable> T processChild(String name, T value, T defaultValue, Class<T> objectClass) throws IOException, IOStreamException {
/*     */     try {
/* 283 */       return processChild(name, value, objectClass);
/*     */     }
/* 285 */     catch (MissingElementException e) {
/*     */       
/* 287 */       return defaultValue;
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
/*     */   public abstract <T extends IOStreamSaveable> void processChildren(String paramString, List paramList, Class<T> paramClass) throws IOException, IOStreamException;
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
/*     */ 
/*     */   
/*     */   public abstract void processChildren(List paramList, TypeRegister paramTypeRegister) throws IOException, IOStreamException;
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
/*     */ 
/*     */   
/*     */   public abstract <T extends IOStreamSaveable> T processChild(T paramT, TypeRegister paramTypeRegister) throws IOException, IOStreamException;
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
/*     */ 
/*     */   
/*     */   public abstract <T extends IOStreamSaveable> void processInherentChild(String paramString, T paramT) throws IOException, IOStreamException;
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
/*     */ 
/*     */   
/*     */   public <T extends Enum> T processEnum(String name, T obj, T defaultValue, Class<T> objClass, StorageFormat<T, String> storageFormat) throws MalformedElementException, IOException {
/* 570 */     String enumName = storageFormat.encode(obj);
/*     */     
/* 572 */     enumName = processAttribute(name, enumName, (String)null);
/*     */     
/* 574 */     if (enumName == null) return defaultValue;
/*     */     
/* 576 */     Enum enum_ = (Enum)storageFormat.decode(enumName);
/*     */     
/* 578 */     if (enum_ == null) {
/*     */ 
/*     */ 
/*     */       
/* 582 */       StringBuffer buf = new StringBuffer();
/*     */       
/* 584 */       Enum[] values = (Enum[])objClass.getEnumConstants();
/*     */       
/* 586 */       for (int i = 0; i < values.length; i++) {
/*     */         
/* 588 */         Enum enum_1 = values[i];
/* 589 */         buf.append(enum_1.name());
/*     */         
/* 591 */         if (i < values.length - 1) {
/*     */           
/* 593 */           buf.append(", ");
/*     */         }
/* 595 */         else if (i == values.length - 1) {
/*     */           
/* 597 */           buf.append(" or ");
/*     */         } 
/*     */       } 
/*     */       
/* 601 */       throw MalformedElementException.createDefaultMalformedAttributeException(name, buf.toString());
/*     */     } 
/*     */     
/* 604 */     return (T)enum_;
/*     */   }
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
/*     */   public abstract boolean isInputStream();
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
/*     */   protected abstract String getParsingContext();
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
/*     */   protected String getNameList(Iterable<?> strings) {
/* 691 */     StringBuffer nameBuf = new StringBuffer();
/* 692 */     nameBuf.append('[');
/*     */     
/* 694 */     for (Object name : strings) {
/*     */       
/* 696 */       if (nameBuf.length() > 1)
/*     */       {
/* 698 */         nameBuf.append('|');
/*     */       }
/*     */       
/* 701 */       nameBuf.append(name.toString());
/*     */     } 
/*     */     
/* 704 */     nameBuf.append(']');
/* 705 */     return nameBuf.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract boolean startSubcontext(String paramString) throws IOStreamException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void endSubcontext();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void close() throws IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> getWarnings() {
/* 740 */     return this.warnings;
/*     */   }
/*     */   
/*     */   public String getWarningsAsString() {
/* 744 */     StringBuilder buf = new StringBuilder();
/*     */     
/* 746 */     for (String warning : this.warnings) {
/* 747 */       buf.append("WARNING: ");
/* 748 */       buf.append(warning);
/* 749 */       buf.append('\n');
/*     */     } 
/*     */     
/* 752 */     return buf.toString();
/*     */   }
/*     */   
/*     */   protected void addWarning(String warning) {
/* 756 */     this.warnings.add(warning);
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\io\InputOutputStream.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */