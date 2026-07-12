/*     */ package org.xml.sax.helpers;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.XMLReader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class XMLReaderFactory
/*     */ {
/*     */   private static final String property = "org.xml.sax.driver";
/*     */   
/*     */   public static XMLReader createXMLReader() throws SAXException {
/* 107 */     String str = null;
/* 108 */     ClassLoader classLoader = NewInstance.getClassLoader();
/*     */     
/*     */     try {
/* 111 */       str = System.getProperty("org.xml.sax.driver");
/* 112 */     } catch (RuntimeException runtimeException) {}
/*     */ 
/*     */     
/* 115 */     if (str == null) {
/*     */       try {
/* 117 */         InputStream inputStream; String str1 = "META-INF/services/org.xml.sax.driver";
/*     */ 
/*     */ 
/*     */         
/* 121 */         if (classLoader == null) {
/* 122 */           inputStream = ClassLoader.getSystemResourceAsStream(str1);
/*     */         } else {
/* 124 */           inputStream = classLoader.getResourceAsStream(str1);
/*     */         } 
/* 126 */         if (inputStream != null) {
/* 127 */           BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF8"));
/*     */           
/* 129 */           str = bufferedReader.readLine();
/* 130 */           inputStream.close();
/*     */         } 
/* 132 */       } catch (Exception exception) {}
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 137 */     if (str == null)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 143 */       str = "com.sun.org.apache.xerces.internal.parsers.SAXParser";
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 149 */     if (str != null) {
/* 150 */       return loadClass(classLoader, str);
/*     */     }
/*     */     
/*     */     try {
/* 154 */       return new ParserAdapter(ParserFactory.makeParser());
/* 155 */     } catch (Exception exception) {
/* 156 */       throw new SAXException("Can't create default XMLReader; is system property org.xml.sax.driver set?");
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
/*     */   public static XMLReader createXMLReader(String paramString) throws SAXException {
/* 180 */     return loadClass(NewInstance.getClassLoader(), paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static XMLReader loadClass(ClassLoader paramClassLoader, String paramString) throws SAXException {
/*     */     try {
/* 187 */       return (XMLReader)NewInstance.newInstance(paramClassLoader, paramString);
/* 188 */     } catch (ClassNotFoundException classNotFoundException) {
/* 189 */       throw new SAXException("SAX2 driver class " + paramString + " not found", classNotFoundException);
/*     */     }
/* 191 */     catch (IllegalAccessException illegalAccessException) {
/* 192 */       throw new SAXException("SAX2 driver class " + paramString + " found but cannot be loaded", illegalAccessException);
/*     */     }
/* 194 */     catch (InstantiationException instantiationException) {
/* 195 */       throw new SAXException("SAX2 driver class " + paramString + " loaded but cannot be instantiated (no empty public constructor?)", instantiationException);
/*     */     
/*     */     }
/* 198 */     catch (ClassCastException classCastException) {
/* 199 */       throw new SAXException("SAX2 driver class " + paramString + " does not implement XMLReader", classCastException);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\xml\sax\helpers\XMLReaderFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */