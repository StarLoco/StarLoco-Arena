/*     */ package com.sun.gluegen.runtime;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.PrintStream;
/*     */ import java.lang.reflect.Method;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
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
/*     */   static
/*     */   {
/*  72 */     AccessController.doPrivileged(new PrivilegedAction() {
/*     */       public Object run() {
/*  74 */         String str = System.getProperty("os.name").toLowerCase();
/*  75 */         if (str.startsWith("wind")) {
/*  76 */           NativeLibrary.access$002(1);
/*  77 */         } else if (str.startsWith("mac os x")) {
/*  78 */           NativeLibrary.access$002(3);
/*     */         } else {
/*  80 */           NativeLibrary.access$002(2);
/*     */         }
/*     */         
/*  83 */         NativeLibrary.access$102(System.getProperty("gluegen.debug.NativeLibrary") != null);
/*     */         
/*  85 */         return null;
/*     */       }
/*     */     });
/*     */     
/*  89 */     switch (platform) {
/*     */     case 1: 
/*  91 */       dynLink = new WindowsDynamicLinkerImpl();
/*  92 */       prefixes = new String[] { "" };
/*  93 */       suffixes = new String[] { ".dll" };
/*  94 */       break;
/*     */     case 2: 
/*  96 */       dynLink = new UnixDynamicLinkerImpl();
/*  97 */       prefixes = new String[] { "lib" };
/*  98 */       suffixes = new String[] { ".so" };
/*  99 */       break;
/*     */     case 3: 
/* 101 */       dynLink = new MacOSXDynamicLinkerImpl();
/* 102 */       prefixes = new String[] { "lib", "" };
/* 103 */       suffixes = new String[] { ".dylib", ".jnilib", "" };
/* 104 */       break;
/*     */     default: 
/* 106 */       throw new InternalError("Platform not initialized properly");
/*     */     }
/*     */     
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private NativeLibrary(long paramLong, String paramString)
/*     */   {
/* 120 */     this.libraryHandle = paramLong;
/* 121 */     this.libraryPath = paramString;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static NativeLibrary open(String paramString, ClassLoader paramClassLoader)
/*     */   {
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
/*     */   public static NativeLibrary open(String paramString1, String paramString2, String paramString3, boolean paramBoolean, ClassLoader paramClassLoader)
/*     */   {
/* 152 */     List localList = enumerateLibraryPaths(paramString1, paramString2, paramString3, paramBoolean, paramClassLoader);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 158 */     for (Iterator localIterator = localList.iterator(); localIterator.hasNext();) {
/* 159 */       String str = (String)localIterator.next();
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
/*     */   public long lookupFunction(String paramString)
/*     */   {
/* 185 */     if (this.libraryHandle == 0L)
/* 186 */       throw new RuntimeException("Library is not open");
/* 187 */     return dynLink.lookupSymbol(this.libraryHandle, paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public long getLibraryHandle()
/*     */   {
/* 194 */     return this.libraryHandle;
/*     */   }
/*     */   
/*     */   public String getLibraryPath()
/*     */   {
/* 199 */     return this.libraryPath;
/*     */   }
/*     */   
/*     */ 
/*     */   public void close()
/*     */   {
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
/*     */   private static List enumerateLibraryPaths(String paramString1, String paramString2, String paramString3, boolean paramBoolean, ClassLoader paramClassLoader)
/*     */   {
/* 220 */     ArrayList localArrayList = new ArrayList();
/* 221 */     String str1 = selectName(paramString1, paramString2, paramString3);
/* 222 */     if (str1 == null)
/* 223 */       return localArrayList;
/* 224 */     String[] arrayOfString = buildNames(str1);
/*     */     
/* 226 */     if (paramBoolean)
/*     */     {
/* 228 */       for (int i = 0; i < arrayOfString.length; i++) {
/* 229 */         localArrayList.add(arrayOfString[i]);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 235 */     String str2 = getPathFromClassLoader(str1, paramClassLoader);
/* 236 */     if (str2 != null) {
/* 237 */       localArrayList.add(str2);
/*     */     }
/*     */     
/*     */ 
/* 241 */     String str3 = (String)AccessController.doPrivileged(new PrivilegedAction()
/*     */     {
/*     */       public Object run() {
/* 244 */         return System.getProperty("java.library.path");
/*     */       }
/*     */     });
/* 247 */     if (str3 != null) {
/* 248 */       localObject = new StringTokenizer(str3, File.pathSeparator);
/* 249 */       while (((StringTokenizer)localObject).hasMoreTokens()) {
/* 250 */         addPaths(((StringTokenizer)localObject).nextToken(), arrayOfString, localArrayList);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 255 */     Object localObject = (String)AccessController.doPrivileged(new PrivilegedAction()
/*     */     {
/*     */       public Object run() {
/* 258 */         return System.getProperty("user.dir");
/*     */       }
/* 260 */     });
/* 261 */     addPaths((String)localObject, arrayOfString, localArrayList);
/*     */     
/*     */ 
/* 264 */     if (platform == 3)
/*     */     {
/* 266 */       addPaths("/Library/Frameworks/" + str1 + ".Framework", arrayOfString, localArrayList);
/*     */       
/* 268 */       addPaths("/System/Library/Frameworks/" + str1 + ".Framework", arrayOfString, localArrayList);
/*     */     }
/*     */     
/* 271 */     if (!paramBoolean)
/*     */     {
/* 273 */       for (int j = 0; j < arrayOfString.length; j++) {
/* 274 */         localArrayList.add(arrayOfString[j]);
/*     */       }
/*     */     }
/*     */     
/* 278 */     return localArrayList;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static String selectName(String paramString1, String paramString2, String paramString3)
/*     */   {
/* 285 */     switch (platform) {
/*     */     case 1: 
/* 287 */       return paramString1;
/*     */     case 2: 
/* 289 */       return paramString2;
/*     */     case 3: 
/* 291 */       return paramString3;
/*     */     }
/* 293 */     throw new InternalError();
/*     */   }
/*     */   
/*     */   private static String[] buildNames(String paramString)
/*     */   {
/* 298 */     String[] arrayOfString = new String[prefixes.length * suffixes.length];
/* 299 */     int i = 0;
/* 300 */     for (int j = 0; j < prefixes.length; j++) {
/* 301 */       for (int k = 0; k < suffixes.length; k++) {
/* 302 */         arrayOfString[(i++)] = (prefixes[j] + paramString + suffixes[k]);
/*     */       }
/*     */     }
/* 305 */     return arrayOfString;
/*     */   }
/*     */   
/*     */   private static void addPaths(String paramString, String[] paramArrayOfString, List paramList) {
/* 309 */     for (int i = 0; i < paramArrayOfString.length; i++) {
/* 310 */       paramList.add(paramString + File.separator + paramArrayOfString[i]);
/*     */     }
/*     */   }
/*     */   
/* 314 */   private static boolean initializedFindLibraryMethod = false;
/* 315 */   private static Method findLibraryMethod = null;
/*     */   
/* 317 */   private static String getPathFromClassLoader(String paramString, ClassLoader paramClassLoader) { if (paramClassLoader == null)
/* 318 */       return null;
/* 319 */     if (!initializedFindLibraryMethod) {
/* 320 */       AccessController.doPrivileged(new PrivilegedAction() {
/*     */         public Object run() {
/*     */           try {
/* 323 */             NativeLibrary.access$202(NativeLibrary.class$java$lang$ClassLoader.getDeclaredMethod("findLibrary", new Class[] { String.class }));
/*     */             
/* 325 */             NativeLibrary.findLibraryMethod.setAccessible(true);
/*     */           }
/*     */           catch (Exception localException) {}
/*     */           
/* 329 */           NativeLibrary.access$302(true);
/* 330 */           return null;
/*     */         }
/*     */       });
/*     */     }
/* 334 */     if (findLibraryMethod != null) {
/*     */       try {
/* 336 */         return (String)findLibraryMethod.invoke(paramClassLoader, new Object[] { paramString });
/*     */       }
/*     */       catch (Exception localException) {}
/*     */     }
/*     */     
/* 341 */     return null;
/*     */   }
/*     */   
/*     */   private static volatile boolean loadedDynLinkNativeLib;
/*     */   private static void ensureNativeLibLoaded() {
/* 346 */     if (!loadedDynLinkNativeLib) {
/* 347 */       synchronized (NativeLibrary.class) {
/* 348 */         if (!loadedDynLinkNativeLib) {
/* 349 */           loadedDynLinkNativeLib = true;
/* 350 */           NativeLibLoader.loadGlueGenRT();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\sun\gluegen\runtime\NativeLibrary.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       0.7.1
 */