/*     */ package com.ankamagames.framework.preferences;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StackedPreferenceStore
/*     */   extends PreferenceStore
/*     */ {
/*  16 */   private final ArrayList<PreferenceStore> m_children = new ArrayList();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public StackedPreferenceStore(String fileName)
/*     */   {
/*  24 */     super(fileName);
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
/*     */   public void pushChild(PreferenceStore preferenceStore)
/*     */   {
/*  37 */     this.m_children.remove(preferenceStore);
/*  38 */     this.m_children.add(preferenceStore);
/*     */     
/*     */ 
/*  41 */     String[] propertyList = preferenceStore.getPropertyList();
/*  42 */     String[] arrayOfString1; int j = (arrayOfString1 = propertyList).length; for (int i = 0; i < j; i++) { String property = arrayOfString1[i];
/*  43 */       firePropertyChangeEvent(property, null, preferenceStore.getValue(property));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void removeChild(PreferenceStore preferenceStore)
/*     */   {
/*  55 */     this.m_children.remove(preferenceStore);
/*     */     
/*     */ 
/*  58 */     String[] propertyList = preferenceStore.getPropertyList();
/*  59 */     String[] arrayOfString1; int j = (arrayOfString1 = propertyList).length; for (int i = 0; i < j; i++) { String property = arrayOfString1[i];
/*  60 */       firePropertyChangeEvent(property, null, getValue(property));
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
/*     */   private PreferenceStore getLastChildContains(String name)
/*     */   {
/*  74 */     if (!this.m_children.isEmpty()) {
/*  75 */       for (int i = this.m_children.size() - 1; i >= 0; i--) {
/*  76 */         PreferenceStore preferenceStore = (PreferenceStore)this.m_children.get(i);
/*  77 */         if ((preferenceStore != null) && (preferenceStore.contains(name))) {
/*  78 */           return preferenceStore;
/*     */         }
/*     */       }
/*     */     }
/*  82 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean getDefaultBoolean(String name)
/*     */   {
/*  92 */     PreferenceStore preferenceStore = getLastChildContains(name);
/*  93 */     if (preferenceStore != null) {
/*  94 */       return preferenceStore.getDefaultBoolean(name);
/*     */     }
/*  96 */     return super.getDefaultBoolean(name);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getDefaultDouble(String name)
/*     */   {
/* 106 */     PreferenceStore preferenceStore = getLastChildContains(name);
/* 107 */     if (preferenceStore != null) {
/* 108 */       return preferenceStore.getDefaultDouble(name);
/*     */     }
/* 110 */     return super.getDefaultDouble(name);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public float getDefaultFloat(String name)
/*     */   {
/* 120 */     PreferenceStore preferenceStore = getLastChildContains(name);
/* 121 */     if (preferenceStore != null) {
/* 122 */       return preferenceStore.getDefaultFloat(name);
/*     */     }
/* 124 */     return super.getDefaultFloat(name);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getDefaultInt(String name)
/*     */   {
/* 134 */     PreferenceStore preferenceStore = getLastChildContains(name);
/* 135 */     if (preferenceStore != null) {
/* 136 */       return preferenceStore.getDefaultInt(name);
/*     */     }
/* 138 */     return super.getDefaultInt(name);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getDefaultLong(String name)
/*     */   {
/* 148 */     PreferenceStore preferenceStore = getLastChildContains(name);
/* 149 */     if (preferenceStore != null) {
/* 150 */       return preferenceStore.getDefaultLong(name);
/*     */     }
/* 152 */     return super.getDefaultLong(name);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getDefaultString(String name)
/*     */   {
/* 162 */     PreferenceStore preferenceStore = getLastChildContains(name);
/* 163 */     if (preferenceStore != null) {
/* 164 */       return preferenceStore.getDefaultString(name);
/*     */     }
/* 166 */     return super.getDefaultString(name);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean getBoolean(String name)
/*     */   {
/* 176 */     PreferenceStore preferenceStore = getLastChildContains(name);
/* 177 */     if (preferenceStore != null) {
/* 178 */       return preferenceStore.getBoolean(name);
/*     */     }
/* 180 */     return super.getBoolean(name);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getDouble(String name)
/*     */   {
/* 190 */     PreferenceStore preferenceStore = getLastChildContains(name);
/* 191 */     if (preferenceStore != null) {
/* 192 */       return preferenceStore.getDouble(name);
/*     */     }
/* 194 */     return super.getDouble(name);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public float getFloat(String name)
/*     */   {
/* 204 */     PreferenceStore preferenceStore = getLastChildContains(name);
/* 205 */     if (preferenceStore != null) {
/* 206 */       return preferenceStore.getFloat(name);
/*     */     }
/* 208 */     return super.getFloat(name);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getInt(String name)
/*     */   {
/* 218 */     PreferenceStore preferenceStore = getLastChildContains(name);
/* 219 */     if (preferenceStore != null) {
/* 220 */       return preferenceStore.getInt(name);
/*     */     }
/* 222 */     return super.getInt(name);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getLong(String name)
/*     */   {
/* 232 */     PreferenceStore preferenceStore = getLastChildContains(name);
/* 233 */     if (preferenceStore != null) {
/* 234 */       return preferenceStore.getLong(name);
/*     */     }
/* 236 */     return super.getLong(name);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getString(String name)
/*     */   {
/* 246 */     PreferenceStore preferenceStore = getLastChildContains(name);
/* 247 */     if (preferenceStore != null) {
/* 248 */       return preferenceStore.getString(name);
/*     */     }
/* 250 */     return super.getString(name);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setValue(String name, boolean value)
/*     */   {
/* 261 */     PreferenceStore preferenceStore = getLastChildContains(name);
/* 262 */     if (preferenceStore != null) {
/* 263 */       preferenceStore.setValue(name, value);
/*     */     }
/* 265 */     super.setValue(name, value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setValue(String name, double value)
/*     */   {
/* 276 */     PreferenceStore preferenceStore = getLastChildContains(name);
/* 277 */     if (preferenceStore != null) {
/* 278 */       preferenceStore.setValue(name, value);
/*     */     }
/* 280 */     super.setValue(name, value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setValue(String name, float value)
/*     */   {
/* 291 */     PreferenceStore preferenceStore = getLastChildContains(name);
/* 292 */     if (preferenceStore != null) {
/* 293 */       preferenceStore.setValue(name, value);
/*     */     }
/* 295 */     super.setValue(name, value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setValue(String name, int value)
/*     */   {
/* 306 */     PreferenceStore preferenceStore = getLastChildContains(name);
/* 307 */     if (preferenceStore != null) {
/* 308 */       preferenceStore.setValue(name, value);
/*     */     }
/* 310 */     super.setValue(name, value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setValue(String name, long value)
/*     */   {
/* 321 */     PreferenceStore preferenceStore = getLastChildContains(name);
/* 322 */     if (preferenceStore != null) {
/* 323 */       preferenceStore.setValue(name, value);
/*     */     }
/* 325 */     super.setValue(name, value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setValue(String name, String value)
/*     */   {
/* 336 */     PreferenceStore preferenceStore = getLastChildContains(name);
/* 337 */     if (preferenceStore != null) {
/* 338 */       preferenceStore.setValue(name, value);
/*     */     }
/* 340 */     super.setValue(name, value);
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\preferences\StackedPreferenceStore.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */