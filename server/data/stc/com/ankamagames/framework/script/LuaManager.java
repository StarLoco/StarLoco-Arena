/*     */ package com.ankamagames.framework.script;
/*     */ 
/*     */ import com.ankamagames.framework.kernel.core.common.message.ProcessScheduler;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.keplerproject.luajava.LuaState;
/*     */ import org.keplerproject.luajava.LuaStateFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LuaManager
/*     */   implements Runnable
/*     */ {
/*  24 */   private static Logger m_logger = Logger.getLogger(LuaManager.class);
/*     */   
/*     */   public static final String SCRIPT_VAR_NAME = "script";
/*     */   
/*     */   public static final String SCRIPT_FILE_EXTENTION = ".lua";
/*     */   
/*     */   private static final int CLOCK_DELAY = 10;
/*  31 */   private static LuaManager m_instance = new LuaManager();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  36 */   private ArrayList<LuaManagerEventListener> m_listeners = new ArrayList();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  41 */   private String m_path = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  47 */   private long m_lastClockTime = 0L;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private ConcurrentHashMap<Integer, LuaScript> m_scripts;
/*     */   
/*     */ 
/*     */ 
/*     */   private JavaFunctionsLibrary[] m_defaultLibraries;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public LuaManager()
/*     */   {
/*  63 */     this.m_scripts = new ConcurrentHashMap();
/*  64 */     this.m_defaultLibraries = new JavaFunctionsLibrary[] { DefaultFunctionsLibrary.getInstance() };
/*     */     
/*  66 */     ProcessScheduler.getInstance().schedule(this, 10L);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static LuaManager getInstance()
/*     */   {
/*  73 */     return m_instance;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setPath(String path)
/*     */   {
/*  80 */     if (!path.endsWith("/")) {
/*  81 */       path = path + "/";
/*     */     }
/*  83 */     this.m_path = path;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addEventListener(LuaManagerEventListener listener)
/*     */   {
/*  92 */     if (!this.m_listeners.contains(listener)) {
/*  93 */       this.m_listeners.add(listener);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void removeEventListener(LuaManagerEventListener listener)
/*     */   {
/* 103 */     if (this.m_listeners.contains(listener)) {
/* 104 */       this.m_listeners.remove(listener);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addDefaultLibraries(JavaFunctionsLibrary... libraries)
/*     */   {
/* 115 */     if ((libraries != null) && (libraries.length != 0)) {
/* 116 */       JavaFunctionsLibrary[] tmp = new JavaFunctionsLibrary[this.m_defaultLibraries.length + libraries.length];
/* 117 */       System.arraycopy(this.m_defaultLibraries, 0, tmp, 0, this.m_defaultLibraries.length);
/* 118 */       System.arraycopy(libraries, 0, tmp, tmp.length - 1, libraries.length);
/* 119 */       this.m_defaultLibraries = tmp;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public LuaScript getScript(int id)
/*     */   {
/* 128 */     return (LuaScript)this.m_scripts.get(Integer.valueOf(id));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Iterable<LuaScript> getScripts()
/*     */   {
/* 135 */     return (Iterable)this.m_scripts.values();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int loadScript(int fileId, JavaFunctionsLibrary[] libraries, boolean silentError)
/*     */   {
/* 146 */     if (this.m_path != null) {
/* 147 */       String fileName = this.m_path + fileId + ".lua";
/* 148 */       return loadScript(fileName, libraries, silentError);
/*     */     }
/* 150 */     return -1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int loadScript(String fileName, JavaFunctionsLibrary[] libraries, boolean silentError)
/*     */   {
/* 161 */     LuaScript s = createScript(libraries);
/*     */     try {
/* 163 */       s.loadFile(fileName);
/* 164 */       s.setSilentError(silentError);
/*     */     } catch (Exception e) {
/* 166 */       m_logger.error("Impossible de charger le fichier " + fileName, e);
/* 167 */       return -1;
/*     */     }
/*     */     
/* 170 */     if (s.getState() == LuaScript.State.LOADED) {
/* 171 */       int id = s.getId();
/*     */       
/* 173 */       this.m_scripts.put(Integer.valueOf(id), s);
/* 174 */       return id;
/*     */     }
/* 176 */     return -1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int loadCommand(String command, JavaFunctionsLibrary[] libraries, boolean silentError)
/*     */   {
/* 188 */     LuaScript s = createScript(libraries);
/* 189 */     s.loadCommand(command);
/* 190 */     s.setSilentError(silentError);
/*     */     
/* 192 */     if (s.getState() == LuaScript.State.LOADED) {
/* 193 */       int id = s.getId();
/*     */       
/* 195 */       this.m_scripts.put(Integer.valueOf(id), s);
/* 196 */       return id;
/*     */     }
/* 198 */     return -1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int runScript(String fileName)
/*     */   {
/* 209 */     return runScript(fileName, null, false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int runScript(String fileName, boolean silentError)
/*     */   {
/* 220 */     return runScript(fileName, null, silentError);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int runScript(int fileId)
/*     */   {
/* 230 */     return runScript(fileId, null, false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int runScript(int fileId, boolean silentError)
/*     */   {
/* 241 */     return runScript(fileId, null, silentError);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int runScript(int fileId, JavaFunctionsLibrary[] libraries)
/*     */   {
/* 251 */     return runScript(fileId, libraries, false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int runScript(int fileId, JavaFunctionsLibrary[] libraries, boolean silentError)
/*     */   {
/* 261 */     int id = loadScript(fileId, libraries, silentError);
/* 262 */     start(id);
/* 263 */     return id;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int runScript(String fileName, JavaFunctionsLibrary[] libraries)
/*     */   {
/* 273 */     return runScript(fileName, libraries, false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int runScript(String fileName, JavaFunctionsLibrary[] libraries, boolean silentError)
/*     */   {
/* 284 */     int id = loadScript(fileName, libraries, silentError);
/* 285 */     start(id);
/* 286 */     return id;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int runCommand(String command)
/*     */   {
/* 296 */     return runCommand(command, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int runCommand(String command, boolean silentError)
/*     */   {
/* 307 */     return runCommand(command, null, false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int runCommand(String command, JavaFunctionsLibrary[] libraries)
/*     */   {
/* 317 */     return runCommand(command, libraries, false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int runCommand(String command, JavaFunctionsLibrary[] libraries, boolean silentError)
/*     */   {
/* 327 */     int id = loadCommand(command, libraries, silentError);
/* 328 */     start(id);
/* 329 */     return id;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void start(int id)
/*     */   {
/* 338 */     LuaScript s = getScript(id);
/* 339 */     if (s != null) {
/* 340 */       s.start();
/*     */     } else {
/* 342 */       m_logger.error("Le Script " + id + " n'existe pas");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void interruptScript(int id)
/*     */   {
/* 352 */     LuaScript s = getScript(id);
/* 353 */     if (s != null) {
/* 354 */       s.interrupt();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getNextFreeId()
/*     */   {
/* 364 */     int i = 0;
/* 365 */     while (this.m_scripts.containsKey(Integer.valueOf(i))) {
/* 366 */       i++;
/*     */     }
/* 368 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void update(int deltaTime)
/*     */   {
/* 378 */     Iterator<LuaScript> iter = this.m_scripts.values().iterator();
/* 379 */     while (iter.hasNext()) {
/* 380 */       LuaScript script = (LuaScript)iter.next();
/* 381 */       script.update(deltaTime);
/*     */       
/* 383 */       if (script.getState() == LuaScript.State.DONE) {
/* 384 */         iter.remove();
/* 385 */         fireScriptFinishedEvent(script);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void fireScriptErrorEvent(LuaScript script, LuaScriptErrorType errorType, String message)
/*     */   {
/*     */     LuaManagerEventListener[] arrayOfLuaManagerEventListener;
/*     */     
/*     */ 
/* 398 */     int j = (arrayOfLuaManagerEventListener = (LuaManagerEventListener[])this.m_listeners.toArray(new LuaManagerEventListener[0])).length; for (int i = 0; i < j; i++) { LuaManagerEventListener listener = arrayOfLuaManagerEventListener[i];
/* 399 */       listener.onLuaScriptError(script, errorType, message);
/*     */     }
/*     */     
/* 402 */     m_logger.error("Erreur dans un script : " + script.getId() + " " + errorType + " " + message, new Exception());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void fireScriptFinishedEvent(LuaScript script)
/*     */   {
/*     */     LuaManagerEventListener[] arrayOfLuaManagerEventListener;
/*     */     
/* 411 */     int j = (arrayOfLuaManagerEventListener = (LuaManagerEventListener[])this.m_listeners.toArray(new LuaManagerEventListener[0])).length; for (int i = 0; i < j; i++) { LuaManagerEventListener listener = arrayOfLuaManagerEventListener[i];
/* 412 */       listener.onScriptFinished(script);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void onScriptError(LuaScript script, LuaScriptErrorType errorType, String message)
/*     */   {
/* 423 */     fireScriptErrorEvent(script, errorType, message);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private LuaScript createScript(JavaFunctionsLibrary[] libraries)
/*     */   {
/*     */     JavaFunctionsLibrary[] scriptLibraries;
/*     */     
/*     */ 
/*     */ 
/* 435 */     if (libraries != null) {
/* 436 */       JavaFunctionsLibrary[] scriptLibraries = new JavaFunctionsLibrary[this.m_defaultLibraries.length + libraries.length];
/* 437 */       System.arraycopy(this.m_defaultLibraries, 0, scriptLibraries, 0, this.m_defaultLibraries.length);
/* 438 */       System.arraycopy(libraries, 0, scriptLibraries, this.m_defaultLibraries.length, libraries.length);
/*     */     } else {
/* 440 */       scriptLibraries = this.m_defaultLibraries;
/*     */     }
/*     */     
/*     */ 
/* 444 */     LuaState luaState = LuaStateFactory.newLuaState();
/*     */     
/*     */ 
/* 447 */     int id = getNextFreeId();
/* 448 */     LuaScript script = new LuaScript(id, luaState, this, scriptLibraries);
/*     */     
/*     */ 
/* 451 */     luaState.pushJavaObject(script);
/* 452 */     luaState.setGlobal("script");
/*     */     
/* 454 */     return script;
/*     */   }
/*     */   
/*     */   public void interrupt() {
/* 458 */     ProcessScheduler.getInstance().remove(this);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getId()
/*     */   {
/* 467 */     return 1L;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setId(long id) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void run()
/*     */   {
/* 484 */     long clockTime = System.currentTimeMillis();
/* 485 */     int deltaTime = (int)(clockTime - this.m_lastClockTime);
/* 486 */     update(deltaTime);
/* 487 */     this.m_lastClockTime = clockTime;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\script\LuaManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */