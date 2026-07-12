/*     */ package com.ankamagames.framework.preferences;
/*     */ 
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Properties;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PreferenceStore
/*     */ {
/*     */   public static final boolean BOOLEAN_DEFAULT_DEFAULT = false;
/*     */   public static final double DOUBLE_DEFAULT_DEFAULT = 0.0D;
/*     */   public static final float FLOAT_DEFAULT_DEFAULT = 0.0F;
/*     */   public static final int INT_DEFAULT_DEFAULT = 0;
/*     */   public static final long LONG_DEFAULT_DEFAULT = 0L;
/*     */   public static final String STRING_DEFAULT_DEFAULT = "";
/*     */   private final Properties m_properties;
/*     */   private final Properties m_defaultProperties;
/*     */   private boolean m_dirty = false;
/*     */   private boolean m_autoSave = false;
/*     */   private String m_fileName;
/*  62 */   private final ArrayList<PreferencePropertyChangeListener> m_listeners = new ArrayList<PreferencePropertyChangeListener>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PreferenceStore() {
/*  68 */     this.m_defaultProperties = new Properties();
/*  69 */     this.m_properties = new Properties(this.m_defaultProperties);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PreferenceStore(String fileName) {
/*  78 */     this();
/*  79 */     setFileName(fileName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFileName() {
/*  86 */     return this.m_fileName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFileName(String fileName) {
/*  95 */     this.m_fileName = fileName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAutoSave() {
/* 102 */     return this.m_autoSave;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAutoSave(boolean autoSave) {
/* 109 */     this.m_autoSave = autoSave;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void load() throws IOException {
/* 117 */     if (this.m_fileName != null) {
/* 118 */       FileInputStream inputStream = new FileInputStream(this.m_fileName);
/* 119 */       load(inputStream);
/* 120 */       inputStream.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void load(InputStream inputStrean) throws IOException {
/* 130 */     this.m_properties.load(inputStrean);
/* 131 */     this.m_dirty = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void save() throws IOException {
/* 139 */     if (this.m_fileName == null) {
/* 140 */       throw new IOException("File name not specified");
/*     */     }
/* 142 */     FileOutputStream out = null;
/*     */     try {
/* 144 */       out = new FileOutputStream(this.m_fileName);
/* 145 */       save(out);
/*     */     } finally {
/* 147 */       if (out != null) {
/* 148 */         out.close();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void save(OutputStream outputStream) throws IOException {
/* 157 */     this.m_properties.store(outputStream, (String)null);
/* 158 */     this.m_dirty = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getPropertyList() {
/* 165 */     ArrayList<Object> propertyList = new ArrayList();
/* 166 */     Enumeration<?> it = this.m_defaultProperties.propertyNames();
/* 167 */     while (it.hasMoreElements()) {
/* 168 */       propertyList.add(it.nextElement());
/*     */     }
/* 170 */     it = this.m_properties.propertyNames();
/* 171 */     while (it.hasMoreElements()) {
/* 172 */       Object property = it.nextElement();
/* 173 */       if (!propertyList.contains(property)) {
/* 174 */         propertyList.add(property);
/*     */       }
/*     */     } 
/* 177 */     return propertyList.<String>toArray(new String[propertyList.size()]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean needsSaving() {
/* 187 */     return this.m_dirty;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addPreferencePropertyChangedListener(PreferencePropertyChangeListener listener) {
/* 197 */     this.m_listeners.add(listener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removePreferencePropertyChangedListener(PreferencePropertyChangeListener listener) {
/* 207 */     this.m_listeners.remove(listener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeAllPreferencePropertyChangedListeners() {
/* 214 */     this.m_listeners.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(String name) {
/* 223 */     return !(!this.m_properties.containsKey(name) && !this.m_defaultProperties.containsKey(name));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getDefaultBoolean(String name) {
/* 232 */     return getBoolean(this.m_defaultProperties, name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getDefaultDouble(String name) {
/* 241 */     return getDouble(this.m_defaultProperties, name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getDefaultFloat(String name) {
/* 250 */     return getFloat(this.m_defaultProperties, name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDefaultInt(String name) {
/* 259 */     return getInt(this.m_defaultProperties, name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getDefaultLong(String name) {
/* 268 */     return getLong(this.m_defaultProperties, name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDefaultString(String name) {
/* 277 */     return getString(this.m_defaultProperties, name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDefault(String name) {
/* 286 */     return (!this.m_properties.containsKey(name) && this.m_defaultProperties.containsKey(name));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getValue(String name) {
/* 294 */     if (this.m_properties.containsKey(name)) {
/* 295 */       return this.m_properties.getProperty(name);
/*     */     }
/* 297 */     if (this.m_defaultProperties.containsKey(name)) {
/* 298 */       return this.m_defaultProperties.getProperty(name);
/*     */     }
/* 300 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getBoolean(String name) {
/* 309 */     return getBoolean(this.m_properties, name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getDouble(String name) {
/* 318 */     return getDouble(this.m_properties, name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getFloat(String name) {
/* 327 */     return getFloat(this.m_properties, name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInt(String name) {
/* 336 */     return getInt(this.m_properties, name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getLong(String name) {
/* 345 */     return getLong(this.m_properties, name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getString(String name) {
/* 354 */     return getString(this.m_properties, name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDefault(String name, boolean value) {
/* 365 */     setValue(this.m_defaultProperties, name, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDefault(String name, double value) {
/* 376 */     setValue(this.m_defaultProperties, name, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDefault(String name, float value) {
/* 387 */     setValue(this.m_defaultProperties, name, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDefault(String name, int value) {
/* 398 */     setValue(this.m_defaultProperties, name, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDefault(String name, long value) {
/* 409 */     setValue(this.m_defaultProperties, name, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDefault(String name, String value) {
/* 420 */     setValue(this.m_defaultProperties, name, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValue(String name, boolean value) {
/* 431 */     boolean oldValue = getBoolean(name);
/* 432 */     if (oldValue != value) {
/* 433 */       setValue(this.m_properties, name, value);
/* 434 */       this.m_dirty = true;
/* 435 */       firePropertyChangeEvent(name, Boolean.valueOf(oldValue), Boolean.valueOf(value));
/* 436 */       if (this.m_autoSave) {
/*     */         try {
/* 438 */           save();
/* 439 */         } catch (IOException iOException) {}
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
/*     */   
/*     */   public void setValue(String name, double value) {
/* 453 */     double oldValue = getDouble(name);
/* 454 */     if (oldValue != value) {
/* 455 */       setValue(this.m_properties, name, value);
/* 456 */       this.m_dirty = true;
/* 457 */       firePropertyChangeEvent(name, Double.valueOf(oldValue), Double.valueOf(value));
/* 458 */       if (this.m_autoSave) {
/*     */         try {
/* 460 */           save();
/* 461 */         } catch (IOException iOException) {}
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
/*     */   
/*     */   public void setValue(String name, float value) {
/* 475 */     float oldValue = getFloat(name);
/* 476 */     if (oldValue != value) {
/* 477 */       setValue(this.m_properties, name, value);
/* 478 */       this.m_dirty = true;
/* 479 */       firePropertyChangeEvent(name, Float.valueOf(oldValue), Float.valueOf(value));
/* 480 */       if (this.m_autoSave) {
/*     */         try {
/* 482 */           save();
/* 483 */         } catch (IOException iOException) {}
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
/*     */   
/*     */   public void setValue(String name, int value) {
/* 497 */     int oldValue = getInt(name);
/* 498 */     if (oldValue != value) {
/* 499 */       setValue(this.m_properties, name, value);
/* 500 */       this.m_dirty = true;
/* 501 */       firePropertyChangeEvent(name, Integer.valueOf(oldValue), Integer.valueOf(value));
/* 502 */       if (this.m_autoSave) {
/*     */         try {
/* 504 */           save();
/* 505 */         } catch (IOException iOException) {}
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
/*     */   
/*     */   public void setValue(String name, long value) {
/* 519 */     long oldValue = getLong(name);
/* 520 */     if (oldValue != value) {
/* 521 */       setValue(this.m_properties, name, value);
/* 522 */       this.m_dirty = true;
/* 523 */       firePropertyChangeEvent(name, Long.valueOf(oldValue), Long.valueOf(value));
/* 524 */       if (this.m_autoSave) {
/*     */         try {
/* 526 */           save();
/* 527 */         } catch (IOException iOException) {}
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
/*     */   
/*     */   public void setValue(String name, String value) {
/* 541 */     String oldValue = getString(name);
/* 542 */     if (oldValue == null || !oldValue.equals(value)) {
/* 543 */       setValue(this.m_properties, name, value);
/* 544 */       this.m_dirty = true;
/* 545 */       firePropertyChangeEvent(name, oldValue, value);
/* 546 */       if (this.m_autoSave) {
/*     */         try {
/* 548 */           save();
/* 549 */         } catch (IOException iOException) {}
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
/*     */   
/*     */   public void firePropertyChangeEvent(String name, Object oldValue, Object newValue) {
/* 563 */     if (this.m_listeners.size() > 0 && (oldValue == null || !oldValue.equals(newValue))) {
/* 564 */       PreferencePropertyChangeEvent event = new PreferencePropertyChangeEvent(this, name, oldValue, newValue);
/* 565 */       for (int i = 0; i < this.m_listeners.size(); i++) {
/* 566 */         PreferencePropertyChangeListener listener = this.m_listeners.get(i);
/* 567 */         listener.propertyChange(event);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean getBoolean(Properties properties, String name) {
/* 578 */     String value = (properties != null) ? properties.getProperty(name) : null;
/* 579 */     if (value == null) {
/* 580 */       return false;
/*     */     }
/* 582 */     return Boolean.valueOf(value).booleanValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double getDouble(Properties properties, String name) {
/* 591 */     String value = (properties != null) ? properties.getProperty(name) : null;
/* 592 */     if (value == null) {
/* 593 */       return 0.0D;
/*     */     }
/* 595 */     double ival = 0.0D;
/*     */     try {
/* 597 */       ival = Double.valueOf(value).doubleValue();
/* 598 */     } catch (NumberFormatException numberFormatException) {}
/*     */     
/* 600 */     return ival;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float getFloat(Properties properties, String name) {
/* 609 */     String value = (properties != null) ? properties.getProperty(name) : null;
/* 610 */     if (value == null) {
/* 611 */       return 0.0F;
/*     */     }
/* 613 */     float ival = 0.0F;
/*     */     try {
/* 615 */       ival = Float.valueOf(value).floatValue();
/* 616 */     } catch (NumberFormatException numberFormatException) {}
/*     */     
/* 618 */     return ival;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getInt(Properties properties, String name) {
/* 627 */     String value = (properties != null) ? properties.getProperty(name) : null;
/* 628 */     if (value == null) {
/* 629 */       return 0;
/*     */     }
/* 631 */     int ival = 0;
/*     */     try {
/* 633 */       ival = Integer.valueOf(value).intValue();
/* 634 */     } catch (NumberFormatException numberFormatException) {}
/*     */     
/* 636 */     return ival;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private long getLong(Properties properties, String name) {
/* 645 */     String value = (properties != null) ? properties.getProperty(name) : null;
/* 646 */     if (value == null) {
/* 647 */       return 0L;
/*     */     }
/* 649 */     long ival = 0L;
/*     */     try {
/* 651 */       ival = Long.valueOf(value).longValue();
/* 652 */     } catch (NumberFormatException numberFormatException) {}
/*     */     
/* 654 */     return ival;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getString(Properties properties, String name) {
/* 663 */     String value = (properties != null) ? properties.getProperty(name) : null;
/* 664 */     if (value == null) {
/* 665 */       return "";
/*     */     }
/* 667 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setValue(Properties properties, String name, boolean value) {
/* 676 */     if (properties != null) {
/* 677 */       properties.put(name, Boolean.toString(value));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setValue(Properties properties, String name, double value) {
/* 687 */     if (properties != null) {
/* 688 */       properties.put(name, Double.toString(value));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setValue(Properties properties, String name, float value) {
/* 698 */     if (properties != null) {
/* 699 */       properties.put(name, Float.toString(value));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setValue(Properties properties, String name, int value) {
/* 709 */     if (properties != null) {
/* 710 */       properties.put(name, Integer.toString(value));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setValue(Properties properties, String name, long value) {
/* 720 */     if (properties != null) {
/* 721 */       properties.put(name, Long.toString(value));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setValue(Properties properties, String name, String value) {
/* 731 */     if (properties != null)
/* 732 */       properties.put(name, value); 
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\preferences\PreferenceStore.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */