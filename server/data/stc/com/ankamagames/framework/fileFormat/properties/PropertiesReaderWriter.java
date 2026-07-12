/*     */ package com.ankamagames.framework.fileFormat.properties;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Properties;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.Vector;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PropertiesReaderWriter
/*     */ {
/*  33 */   private static Logger m_logger = Logger.getLogger(PropertiesReaderWriter.class);
/*     */   
/*     */   private static final String STRING_ARRAY_DELIM = ",";
/*     */   
/*     */   private static final String MULTI_KEY_SIMPLE_FORMAT = "%s_";
/*     */   private static final String MULTI_KEY_FORMAT = "%s_%d";
/*  39 */   private final Properties m_properties = new Properties();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getString(String key)
/*     */     throws PropertyException
/*     */   {
/*  47 */     String s = this.m_properties.getProperty(key);
/*  48 */     if (s == null) {
/*  49 */       throw new PropertyException("Il n'existe pas de propriété: " + key);
/*     */     }
/*  51 */     return s;
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
/*     */   public ArrayList<String> getMultiString(String key)
/*     */     throws PropertyException
/*     */   {
/*  69 */     int index = 1;
/*  70 */     ArrayList<String> strings = new ArrayList();
/*  71 */     String multiKey = String.format("%s_%d", new Object[] { key, Integer.valueOf(index) });
/*  72 */     while (this.m_properties.containsKey(multiKey)) {
/*  73 */       strings.add(getString(multiKey));
/*  74 */       multiKey = String.format("%s_%d", new Object[] { key, Integer.valueOf(++index) });
/*     */     }
/*  76 */     return strings;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String[] getStringArray(String key)
/*     */     throws PropertyException
/*     */   {
/*  85 */     String s = getString(key);
/*  86 */     return getSplitedString(s);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ArrayList<String[]> getMultiStringArray(String key)
/*     */     throws PropertyException
/*     */   {
/*  95 */     ArrayList<String> values = getMultiString(key);
/*  96 */     ArrayList<String[]> strings = new ArrayList();
/*  97 */     for (String value : values) {
/*  98 */       strings.add(getSplitedString(value));
/*     */     }
/* 100 */     return strings;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getInteger(String key)
/*     */     throws PropertyException
/*     */   {
/* 109 */     String s = getString(key);
/*     */     try {
/* 111 */       return Integer.parseInt(s);
/*     */     } catch (NumberFormatException e) {
/* 113 */       throw new PropertyException("La propriété " + key + " n'est pas un int.");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public float getFloat(String key)
/*     */     throws PropertyException
/*     */   {
/* 123 */     String s = getString(key);
/*     */     try {
/* 125 */       return Float.valueOf(s).floatValue();
/*     */     } catch (NumberFormatException e) {
/* 127 */       throw new PropertyException("La propriété " + key + " n'est pas un float.");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getDouble(String key)
/*     */     throws PropertyException
/*     */   {
/* 137 */     String s = getString(key);
/*     */     try {
/* 139 */       return Double.valueOf(s).doubleValue();
/*     */     } catch (NumberFormatException e) {
/* 141 */       throw new PropertyException("La propriété " + key + " n'est pas un double.");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean getBoolean(String key)
/*     */     throws PropertyException
/*     */   {
/* 151 */     String s = getString(key);
/* 152 */     return Boolean.valueOf(s).booleanValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setString(String key, String value)
/*     */   {
/* 162 */     if (this.m_properties != null) {
/* 163 */       this.m_properties.setProperty(key, value);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setMultiString(String key, ArrayList<String> values)
/*     */   {
/* 174 */     int index = 1;
/* 175 */     for (String value : values) {
/* 176 */       String multiKey = String.format("%s_%d", new Object[] { key, Integer.valueOf(index++) });
/* 177 */       setString(multiKey, value);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setMultiStringArray(String key, ArrayList<String[]> values)
/*     */   {
/* 188 */     int index = 1;
/* 189 */     for (String[] value : values) {
/* 190 */       String multiKey = String.format("%s_%d", new Object[] { key, Integer.valueOf(index++) });
/* 191 */       setStringArray(multiKey, value);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setStringArray(String key, String[] value)
/*     */   {
/* 202 */     StringBuilder sb = new StringBuilder();
/* 203 */     for (int i = 0; i < value.length; i++) {
/* 204 */       sb.append(value[i]);
/* 205 */       if (i < value.length - 1) {
/* 206 */         sb.append(",");
/*     */       }
/*     */     }
/* 209 */     setString(key, sb.toString());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setInteger(String key, int value)
/*     */   {
/* 219 */     setString(key, Integer.toString(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setFloat(String key, float value)
/*     */   {
/* 229 */     setString(key, Float.toString(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setDouble(String key, double value)
/*     */   {
/* 239 */     setString(key, Double.toString(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setBoolean(String key, boolean value)
/*     */   {
/* 249 */     setString(key, Boolean.toString(value));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean load(InputStream inputStream)
/*     */   {
/* 259 */     this.m_properties.clear();
/*     */     try {
/* 261 */       this.m_properties.load(inputStream);
/*     */     } catch (IOException e) {
/* 263 */       return false;
/*     */     }
/* 265 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean load(URL url)
/*     */   {
/*     */     try
/*     */     {
/* 277 */       if (url != null) {
/* 278 */         return load(url.openStream());
/*     */       }
/* 280 */       m_logger.error("url nulle au load.");
/* 281 */       return false;
/*     */     }
/*     */     catch (IOException e) {
/* 284 */       e.printStackTrace();
/*     */     }
/* 286 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean load(String fileName)
/*     */   {
/*     */     try
/*     */     {
/* 298 */       File file = new File(fileName);
/* 299 */       if (file.exists()) {
/* 300 */         return load(new FileInputStream(file));
/*     */       }
/* 302 */       URL url = getClass().getClassLoader().getResource(fileName);
/* 303 */       if (url != null) {
/* 304 */         return load(url);
/*     */       }
/* 306 */       m_logger.error("Impossible de trouver le fichier de propriété " + fileName);
/* 307 */       return false;
/*     */     }
/*     */     catch (FileNotFoundException localFileNotFoundException) {}
/*     */     
/*     */ 
/* 312 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean save(String file)
/*     */   {
/*     */     try
/*     */     {
/* 324 */       OutputStream propertiesFile = new FileOutputStream(file);
/* 325 */       this.m_properties.store(propertiesFile, null);
/* 326 */       propertiesFile.close();
/*     */     } catch (FileNotFoundException e) {
/* 328 */       return false;
/*     */     } catch (IOException e) {
/* 330 */       return false;
/*     */     }
/* 332 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void clearMultiKey(String key)
/*     */   {
/* 341 */     String multiKey = String.format("%s_", new Object[] { key });
/* 342 */     Enumeration<Object> keys = this.m_properties.keys();
/* 343 */     while (keys.hasMoreElements()) {
/* 344 */       Object obj = keys.nextElement();
/* 345 */       if ((obj instanceof String)) {
/* 346 */         String currentKey = (String)obj;
/* 347 */         if (currentKey.startsWith(multiKey)) {
/* 348 */           this.m_properties.remove(currentKey);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private String[] getSplitedString(String s) {
/* 355 */     StringTokenizer st = new StringTokenizer(s, ",");
/* 356 */     Vector<String> v = new Vector();
/* 357 */     while (st.hasMoreTokens()) {
/* 358 */       v.addElement(st.nextToken());
/*     */     }
/* 360 */     String[] strings = new String[v.size()];
/* 361 */     v.copyInto(strings);
/* 362 */     return strings;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\fileFormat\properties\PropertiesReaderWriter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */