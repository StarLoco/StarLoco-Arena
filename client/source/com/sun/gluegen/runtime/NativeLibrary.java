/*     */ package com.sun.gluegen.runtime;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.lang.reflect.Method;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NativeLibrary
/*     */ {
/*     */   private static final int WINDOWS = 1;
/*     */   private static final int UNIX = 2;
/*     */   private static final int MACOSX = 3;
/*     */   private static boolean DEBUG;
/*     */   private static int platform;
/*     */   private static DynamicLinker dynLink;
/*     */   private static String[] prefixes;
/*     */   private static String[] suffixes;
/*     */   private long libraryHandle;
/*     */   private String libraryPath;
/*     */   
/*     */   static {
/*  72 */     AccessController.doPrivileged(new PrivilegedAction() {
/*     */           public Object run() {
/*  74 */             String str = System.getProperty("os.name").toLowerCase();
/*  75 */             if (str.startsWith("wind")) {
/*  76 */               NativeLibrary.platform = 1;
/*  77 */             } else if (str.startsWith("mac os x")) {
/*  78 */               NativeLibrary.platform = 3;
/*     */             } else {
/*  80 */               NativeLibrary.platform = 2;
/*     */             } 
/*     */             
/*  83 */             NativeLibrary.DEBUG = (System.getProperty("gluegen.debug.NativeLibrary") != null);
/*     */             
/*  85 */             return null;
/*     */           }
/*     */         });
/*     */     
/*  89 */     switch (platform) {
/*     */       case 1:
/*  91 */         dynLink = new WindowsDynamicLinkerImpl();
/*  92 */         prefixes = new String[] { "" };
/*  93 */         suffixes = new String[] { ".dll" };
/*     */         break;
/*     */       case 2:
/*  96 */         dynLink = new UnixDynamicLinkerImpl();
/*  97 */         prefixes = new String[] { "lib" };
/*  98 */         suffixes = new String[] { ".so" };
/*     */         break;
/*     */       case 3:
/* 101 */         dynLink = new MacOSXDynamicLinkerImpl();
/* 102 */         prefixes = new String[] { "lib", "" };
/* 103 */         suffixes = new String[] { ".dylib", ".jnilib", "" };
/*     */         break;
/*     */       default:
/* 106 */         throw new InternalError("Platform not initialized properly");
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
/*     */   private NativeLibrary(long paramLong, String paramString) {
/* 120 */     this.libraryHandle = paramLong;
/* 121 */     this.libraryPath = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NativeLibrary open(String paramString, ClassLoader paramClassLoader) {
/* 129 */     return open(paramString, paramString, paramString, true, paramClassLoader);
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
/*     */   public static NativeLibrary open(String paramString1, String paramString2, String paramString3, boolean paramBoolean, ClassLoader paramClassLoader) {
/* 152 */     List list = enumerateLibraryPaths(paramString1, paramString2, paramString3, paramBoolean, paramClassLoader);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 158 */     for (String str : list) {
/*     */       
/* 160 */       if (DEBUG) {
/* 161 */         System.out.println("Trying to load " + str);
/*     */       }
/* 163 */       ensureNativeLibLoaded();
/* 164 */       long l = dynLink.openLibrary(str);
/* 165 */       if (l != 0L) {
/* 166 */         if (DEBUG) {
/* 167 */           System.out.println("Successfully loaded " + str + ": res = 0x" + Long.toHexString(l));
/*     */         }
/* 169 */         return new NativeLibrary(l, str);
/*     */       } 
/*     */     } 
/*     */     
/* 173 */     if (DEBUG) {
/* 174 */       System.out.println("Did not succeed in loading (" + paramString1 + ", " + paramString2 + ", " + paramString3 + ")");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 180 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public long lookupFunction(String paramString) {
/* 185 */     if (this.libraryHandle == 0L)
/* 186 */       throw new RuntimeException("Library is not open"); 
/* 187 */     return dynLink.lookupSymbol(this.libraryHandle, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getLibraryHandle() {
/* 194 */     return this.libraryHandle;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getLibraryPath() {
/* 199 */     return this.libraryPath;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() {
/* 205 */     if (this.libraryHandle == 0L)
/* 206 */       throw new RuntimeException("Library already closed"); 
/* 207 */     long l = this.libraryHandle;
/* 208 */     this.libraryHandle = 0L;
/* 209 */     dynLink.closeLibrary(l);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static List enumerateLibraryPaths(String paramString1, String paramString2, String paramString3, boolean paramBoolean, ClassLoader paramClassLoader) {
/* 220 */     ArrayList arrayList = new ArrayList();
/* 221 */     String str1 = selectName(paramString1, paramString2, paramString3);
/* 222 */     if (str1 == null)
/* 223 */       return arrayList; 
/* 224 */     String[] arrayOfString = buildNames(str1);
/*     */     
/* 226 */     if (paramBoolean)
/*     */     {
/* 228 */       for (byte b = 0; b < arrayOfString.length; b++) {
/* 229 */         arrayList.add(arrayOfString[b]);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 235 */     String str2 = getPathFromClassLoader(str1, paramClassLoader);
/* 236 */     if (str2 != null) {
/* 237 */       arrayList.add(str2);
/*     */     }
/*     */ 
/*     */     
/* 241 */     String str3 = AccessController.<String>doPrivileged(new PrivilegedAction()
/*     */         {
/*     */           public Object run() {
/* 244 */             return System.getProperty("java.library.path");
/*     */           }
/*     */         });
/* 247 */     if (str3 != null) {
/* 248 */       StringTokenizer stringTokenizer = new StringTokenizer(str3, File.pathSeparator);
/* 249 */       while (stringTokenizer.hasMoreTokens()) {
/* 250 */         addPaths(stringTokenizer.nextToken(), arrayOfString, arrayList);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 255 */     String str4 = AccessController.<String>doPrivileged(new PrivilegedAction()
/*     */         {
/*     */           public Object run() {
/* 258 */             return System.getProperty("user.dir");
/*     */           }
/*     */         });
/* 261 */     addPaths(str4, arrayOfString, arrayList);
/*     */ 
/*     */     
/* 264 */     if (platform == 3) {
/*     */       
/* 266 */       addPaths("/Library/Frameworks/" + str1 + ".Framework", arrayOfString, arrayList);
/*     */       
/* 268 */       addPaths("/System/Library/Frameworks/" + str1 + ".Framework", arrayOfString, arrayList);
/*     */     } 
/*     */     
/* 271 */     if (!paramBoolean)
/*     */     {
/* 273 */       for (byte b = 0; b < arrayOfString.length; b++) {
/* 274 */         arrayList.add(arrayOfString[b]);
/*     */       }
/*     */     }
/*     */     
/* 278 */     return arrayList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String selectName(String paramString1, String paramString2, String paramString3) {
/* 285 */     switch (platform) {
/*     */       case 1:
/* 287 */         return paramString1;
/*     */       case 2:
/* 289 */         return paramString2;
/*     */       case 3:
/* 291 */         return paramString3;
/*     */     } 
/* 293 */     throw new InternalError();
/*     */   }
/*     */ 
/*     */   
/*     */   private static String[] buildNames(String paramString) {
/* 298 */     String[] arrayOfString = new String[prefixes.length * suffixes.length];
/* 299 */     byte b1 = 0;
/* 300 */     for (byte b2 = 0; b2 < prefixes.length; b2++) {
/* 301 */       for (byte b = 0; b < suffixes.length; b++) {
/* 302 */         arrayOfString[b1++] = prefixes[b2] + paramString + suffixes[b];
/*     */       }
/*     */     } 
/* 305 */     return arrayOfString;
/*     */   }
/*     */   
/*     */   private static void addPaths(String paramString, String[] paramArrayOfString, List paramList) {
/* 309 */     for (byte b = 0; b < paramArrayOfString.length; b++) {
/* 310 */       paramList.add(paramString + File.separator + paramArrayOfString[b]);
/*     */     }
/*     */   }
/*     */   
/*     */   private static boolean initializedFindLibraryMethod = false;
/* 315 */   private static Method findLibraryMethod = null; private static volatile boolean loadedDynLinkNativeLib;
/*     */   private static String getPathFromClassLoader(String paramString, ClassLoader paramClassLoader) {
/* 317 */     if (paramClassLoader == null)
/* 318 */       return null; 
/* 319 */     if (!initializedFindLibraryMethod) {
/* 320 */       AccessController.doPrivileged(new PrivilegedAction() {
/*     */             public Object run() {
/*     */               try {
/* 323 */                 NativeLibrary.findLibraryMethod = ((NativeLibrary.class$java$lang$ClassLoader == null) ? (NativeLibrary.class$java$lang$ClassLoader = NativeLibrary.class$("java.lang.ClassLoader")) : NativeLibrary.class$java$lang$ClassLoader).getDeclaredMethod("findLibrary", new Class[] { (NativeLibrary.class$java$lang$String == null) ? (NativeLibrary.class$java$lang$String = NativeLibrary.class$("java.lang.String")) : NativeLibrary.class$java$lang$String });
/*     */                 
/* 325 */                 NativeLibrary.findLibraryMethod.setAccessible(true);
/* 326 */               } catch (Exception exception) {}
/*     */ 
/*     */               
/* 329 */               NativeLibrary.initializedFindLibraryMethod = true;
/* 330 */               return null;
/*     */             }
/*     */           });
/*     */     }
/* 334 */     if (findLibraryMethod != null) {
/*     */       try {
/* 336 */         return (String)findLibraryMethod.invoke(paramClassLoader, new Object[] { paramString });
/* 337 */       } catch (Exception exception) {}
/*     */     }
/*     */ 
/*     */     
/* 341 */     return null;
/*     */   }
/*     */   static Class class$java$lang$String; static Class class$java$lang$ClassLoader;
/*     */   
/*     */   private static void ensureNativeLibLoaded() {
/* 346 */     if (!loadedDynLinkNativeLib)
/* 347 */       synchronized (NativeLibrary.class) {
/* 348 */         if (!loadedDynLinkNativeLib) {
/* 349 */           loadedDynLinkNativeLib = true;
/* 350 */           NativeLibLoader.loadGlueGenRT();
/*     */         } 
/*     */       }  
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\sun\gluegen\runtime\NativeLibrary.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */