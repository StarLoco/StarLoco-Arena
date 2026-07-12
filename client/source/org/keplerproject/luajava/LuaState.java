/*      */ package org.keplerproject.luajava;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class LuaState
/*      */ {
/*      */   private static final String LUAJAVA_LIB = "luajava-1.1";
/*   38 */   public static final Integer LUA_GLOBALSINDEX = new Integer(-10002);
/*   39 */   public static final Integer LUA_REGISTRYINDEX = new Integer(-10000);
/*      */   
/*   41 */   public static final Integer LUA_TNONE = new Integer(-1);
/*   42 */   public static final Integer LUA_TNIL = new Integer(0);
/*   43 */   public static final Integer LUA_TBOOLEAN = new Integer(1);
/*   44 */   public static final Integer LUA_TLIGHTUSERDATA = new Integer(2);
/*   45 */   public static final Integer LUA_TNUMBER = new Integer(3);
/*   46 */   public static final Integer LUA_TSTRING = new Integer(4);
/*   47 */   public static final Integer LUA_TTABLE = new Integer(5);
/*   48 */   public static final Integer LUA_TFUNCTION = new Integer(6);
/*   49 */   public static final Integer LUA_TUSERDATA = new Integer(7);
/*   50 */   public static final Integer LUA_TTHREAD = new Integer(8);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   56 */   public static final Integer LUA_MULTRET = new Integer(-1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   64 */   public static final Integer LUA_ERRRUN = new Integer(1);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   69 */   public static final Integer LUA_YIELD = new Integer(2);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   74 */   public static final Integer LUA_ERRSYNTAX = new Integer(3);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   80 */   public static final Integer LUA_ERRMEM = new Integer(4);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   85 */   public static final Integer LUA_ERRERR = new Integer(5);
/*      */   
/*      */   private CPtr luaState;
/*      */   
/*      */   private int stateId;
/*      */   
/*      */   static {
/*   92 */     System.loadLibrary("luajava-1.1");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected LuaState(int paramInt) {
/*  105 */     this.luaState = _open();
/*  106 */     luajava_open(this.luaState, paramInt);
/*  107 */     this.stateId = paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected LuaState(CPtr paramCPtr) {
/*  116 */     this.luaState = paramCPtr;
/*  117 */     this.stateId = LuaStateFactory.insertLuaState(this);
/*  118 */     luajava_open(paramCPtr, this.stateId);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void close() {
/*  126 */     LuaStateFactory.removeLuaState(this.stateId);
/*  127 */     _close(this.luaState);
/*  128 */     this.luaState = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized boolean isClosed() {
/*  136 */     return (this.luaState == null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getCPtrPeer() {
/*  145 */     return (this.luaState != null) ? this.luaState.getPeer() : 0L;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  219 */   public static final Integer LUA_GCSTOP = new Integer(0);
/*  220 */   public static final Integer LUA_GCRESTART = new Integer(1);
/*  221 */   public static final Integer LUA_GCCOLLECT = new Integer(2);
/*  222 */   public static final Integer LUA_GCCOUNT = new Integer(3);
/*  223 */   public static final Integer LUA_GCCOUNTB = new Integer(4);
/*  224 */   public static final Integer LUA_GCSTEP = new Integer(5);
/*  225 */   public static final Integer LUA_GCSETPAUSE = new Integer(6);
/*  226 */   public static final Integer LUA_GCSETSTEPMUL = new Integer(7);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LuaState newThread() {
/*  306 */     LuaState luaState = new LuaState(_newthread(this.luaState));
/*      */     
/*  308 */     LuaStateFactory.insertLuaState(luaState);
/*      */     
/*  310 */     return luaState;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getTop() {
/*  317 */     return _getTop(this.luaState);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setTop(int paramInt) {
/*  322 */     _setTop(this.luaState, paramInt);
/*      */   }
/*      */ 
/*      */   
/*      */   public void pushValue(int paramInt) {
/*  327 */     _pushValue(this.luaState, paramInt);
/*      */   }
/*      */ 
/*      */   
/*      */   public void remove(int paramInt) {
/*  332 */     _remove(this.luaState, paramInt);
/*      */   }
/*      */ 
/*      */   
/*      */   public void insert(int paramInt) {
/*  337 */     _insert(this.luaState, paramInt);
/*      */   }
/*      */ 
/*      */   
/*      */   public void replace(int paramInt) {
/*  342 */     _replace(this.luaState, paramInt);
/*      */   }
/*      */ 
/*      */   
/*      */   public int checkStack(int paramInt) {
/*  347 */     return _checkStack(this.luaState, paramInt);
/*      */   }
/*      */ 
/*      */   
/*      */   public void xmove(LuaState paramLuaState, int paramInt) {
/*  352 */     _xmove(this.luaState, paramLuaState.luaState, paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isNumber(int paramInt) {
/*  359 */     return (_isNumber(this.luaState, paramInt) != 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isString(int paramInt) {
/*  364 */     return (_isString(this.luaState, paramInt) != 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isFunction(int paramInt) {
/*  369 */     return (_isFunction(this.luaState, paramInt) != 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isCFunction(int paramInt) {
/*  374 */     return (_isCFunction(this.luaState, paramInt) != 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isUserdata(int paramInt) {
/*  379 */     return (_isUserdata(this.luaState, paramInt) != 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isTable(int paramInt) {
/*  384 */     return (_isTable(this.luaState, paramInt) != 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isBoolean(int paramInt) {
/*  389 */     return (_isBoolean(this.luaState, paramInt) != 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isNil(int paramInt) {
/*  394 */     return (_isNil(this.luaState, paramInt) != 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isThread(int paramInt) {
/*  399 */     return (_isThread(this.luaState, paramInt) != 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isNone(int paramInt) {
/*  404 */     return (_isNone(this.luaState, paramInt) != 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isNoneOrNil(int paramInt) {
/*  409 */     return (_isNoneOrNil(this.luaState, paramInt) != 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public int type(int paramInt) {
/*  414 */     return _type(this.luaState, paramInt);
/*      */   }
/*      */ 
/*      */   
/*      */   public String typeName(int paramInt) {
/*  419 */     return _typeName(this.luaState, paramInt);
/*      */   }
/*      */ 
/*      */   
/*      */   public int equal(int paramInt1, int paramInt2) {
/*  424 */     return _equal(this.luaState, paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */   
/*      */   public int rawequal(int paramInt1, int paramInt2) {
/*  429 */     return _rawequal(this.luaState, paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */   
/*      */   public int lessthan(int paramInt1, int paramInt2) {
/*  434 */     return _lessthan(this.luaState, paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */   
/*      */   public double toNumber(int paramInt) {
/*  439 */     return _toNumber(this.luaState, paramInt);
/*      */   }
/*      */ 
/*      */   
/*      */   public int toInteger(int paramInt) {
/*  444 */     return _toInteger(this.luaState, paramInt);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean toBoolean(int paramInt) {
/*  449 */     return (_toBoolean(this.luaState, paramInt) != 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString(int paramInt) {
/*  454 */     return _toString(this.luaState, paramInt);
/*      */   }
/*      */ 
/*      */   
/*      */   public int strLen(int paramInt) {
/*  459 */     return _strlen(this.luaState, paramInt);
/*      */   }
/*      */ 
/*      */   
/*      */   public int objLen(int paramInt) {
/*  464 */     return _objlen(this.luaState, paramInt);
/*      */   }
/*      */ 
/*      */   
/*      */   public LuaState toThread(int paramInt) {
/*  469 */     return new LuaState(_toThread(this.luaState, paramInt));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void pushNil() {
/*  476 */     _pushNil(this.luaState);
/*      */   }
/*      */ 
/*      */   
/*      */   public void pushNumber(double paramDouble) {
/*  481 */     _pushNumber(this.luaState, paramDouble);
/*      */   }
/*      */ 
/*      */   
/*      */   public void pushInteger(int paramInt) {
/*  486 */     _pushInteger(this.luaState, paramInt);
/*      */   }
/*      */ 
/*      */   
/*      */   public void pushString(String paramString) {
/*  491 */     if (paramString == null) {
/*  492 */       _pushNil(this.luaState);
/*      */     } else {
/*  494 */       _pushString(this.luaState, paramString);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void pushString(byte[] paramArrayOfbyte) {
/*  499 */     if (paramArrayOfbyte == null) {
/*  500 */       _pushNil(this.luaState);
/*      */     } else {
/*  502 */       _pushString(this.luaState, paramArrayOfbyte, paramArrayOfbyte.length);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void pushBoolean(boolean paramBoolean) {
/*  507 */     _pushBoolean(this.luaState, paramBoolean ? 1 : 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void getTable(int paramInt) {
/*  514 */     _getTable(this.luaState, paramInt);
/*      */   }
/*      */ 
/*      */   
/*      */   public void getField(int paramInt, String paramString) {
/*  519 */     _getField(this.luaState, paramInt, paramString);
/*      */   }
/*      */ 
/*      */   
/*      */   public void rawGet(int paramInt) {
/*  524 */     _rawGet(this.luaState, paramInt);
/*      */   }
/*      */ 
/*      */   
/*      */   public void rawGetI(int paramInt1, int paramInt2) {
/*  529 */     _rawGetI(this.luaState, paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */   
/*      */   public void createTable(int paramInt1, int paramInt2) {
/*  534 */     _createTable(this.luaState, paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */   
/*      */   public void newTable() {
/*  539 */     _newTable(this.luaState);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMetaTable(int paramInt) {
/*  545 */     return _getMetaTable(this.luaState, paramInt);
/*      */   }
/*      */ 
/*      */   
/*      */   public void getFEnv(int paramInt) {
/*  550 */     _getFEnv(this.luaState, paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTable(int paramInt) {
/*  557 */     _setTable(this.luaState, paramInt);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setField(int paramInt, String paramString) {
/*  562 */     _setField(this.luaState, paramInt, paramString);
/*      */   }
/*      */ 
/*      */   
/*      */   public void rawSet(int paramInt) {
/*  567 */     _rawSet(this.luaState, paramInt);
/*      */   }
/*      */ 
/*      */   
/*      */   public void rawSetI(int paramInt1, int paramInt2) {
/*  572 */     _rawSetI(this.luaState, paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int setMetaTable(int paramInt) {
/*  578 */     return _setMetaTable(this.luaState, paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int setFEnv(int paramInt) {
/*  584 */     return _setFEnv(this.luaState, paramInt);
/*      */   }
/*      */ 
/*      */   
/*      */   public void call(int paramInt1, int paramInt2) {
/*  589 */     _call(this.luaState, paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int pcall(int paramInt1, int paramInt2, int paramInt3) {
/*  595 */     return _pcall(this.luaState, paramInt1, paramInt2, paramInt3);
/*      */   }
/*      */ 
/*      */   
/*      */   public int yield(int paramInt) {
/*  600 */     return _yield(this.luaState, paramInt);
/*      */   }
/*      */ 
/*      */   
/*      */   public int resume(int paramInt) {
/*  605 */     return _resume(this.luaState, paramInt);
/*      */   }
/*      */ 
/*      */   
/*      */   public int status() {
/*  610 */     return _status(this.luaState);
/*      */   }
/*      */ 
/*      */   
/*      */   public int gc(int paramInt1, int paramInt2) {
/*  615 */     return _gc(this.luaState, paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getGcCount() {
/*  620 */     return _getGcCount(this.luaState);
/*      */   }
/*      */ 
/*      */   
/*      */   public int next(int paramInt) {
/*  625 */     return _next(this.luaState, paramInt);
/*      */   }
/*      */ 
/*      */   
/*      */   public int error() {
/*  630 */     return _error(this.luaState);
/*      */   }
/*      */ 
/*      */   
/*      */   public void concat(int paramInt) {
/*  635 */     _concat(this.luaState, paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int LdoFile(String paramString) {
/*  643 */     return _LdoFile(this.luaState, paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int LdoString(String paramString) {
/*  649 */     return _LdoString(this.luaState, paramString);
/*      */   }
/*      */ 
/*      */   
/*      */   public int LgetMetaField(int paramInt, String paramString) {
/*  654 */     return _LgetMetaField(this.luaState, paramInt, paramString);
/*      */   }
/*      */ 
/*      */   
/*      */   public int LcallMeta(int paramInt, String paramString) {
/*  659 */     return _LcallMeta(this.luaState, paramInt, paramString);
/*      */   }
/*      */ 
/*      */   
/*      */   public int Ltyperror(int paramInt, String paramString) {
/*  664 */     return _Ltyperror(this.luaState, paramInt, paramString);
/*      */   }
/*      */ 
/*      */   
/*      */   public int LargError(int paramInt, String paramString) {
/*  669 */     return _LargError(this.luaState, paramInt, paramString);
/*      */   }
/*      */ 
/*      */   
/*      */   public String LcheckString(int paramInt) {
/*  674 */     return _LcheckString(this.luaState, paramInt);
/*      */   }
/*      */ 
/*      */   
/*      */   public String LoptString(int paramInt, String paramString) {
/*  679 */     return _LoptString(this.luaState, paramInt, paramString);
/*      */   }
/*      */ 
/*      */   
/*      */   public double LcheckNumber(int paramInt) {
/*  684 */     return _LcheckNumber(this.luaState, paramInt);
/*      */   }
/*      */ 
/*      */   
/*      */   public double LoptNumber(int paramInt, double paramDouble) {
/*  689 */     return _LoptNumber(this.luaState, paramInt, paramDouble);
/*      */   }
/*      */ 
/*      */   
/*      */   public int LcheckInteger(int paramInt) {
/*  694 */     return _LcheckInteger(this.luaState, paramInt);
/*      */   }
/*      */ 
/*      */   
/*      */   public int LoptInteger(int paramInt1, int paramInt2) {
/*  699 */     return _LoptInteger(this.luaState, paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */   
/*      */   public void LcheckStack(int paramInt, String paramString) {
/*  704 */     _LcheckStack(this.luaState, paramInt, paramString);
/*      */   }
/*      */ 
/*      */   
/*      */   public void LcheckType(int paramInt1, int paramInt2) {
/*  709 */     _LcheckType(this.luaState, paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */   
/*      */   public void LcheckAny(int paramInt) {
/*  714 */     _LcheckAny(this.luaState, paramInt);
/*      */   }
/*      */ 
/*      */   
/*      */   public int LnewMetatable(String paramString) {
/*  719 */     return _LnewMetatable(this.luaState, paramString);
/*      */   }
/*      */ 
/*      */   
/*      */   public void LgetMetatable(String paramString) {
/*  724 */     _LgetMetatable(this.luaState, paramString);
/*      */   }
/*      */ 
/*      */   
/*      */   public void Lwhere(int paramInt) {
/*  729 */     _Lwhere(this.luaState, paramInt);
/*      */   }
/*      */ 
/*      */   
/*      */   public int Lref(int paramInt) {
/*  734 */     return _Lref(this.luaState, paramInt);
/*      */   }
/*      */ 
/*      */   
/*      */   public void LunRef(int paramInt1, int paramInt2) {
/*  739 */     _LunRef(this.luaState, paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */   
/*      */   public int LgetN(int paramInt) {
/*  744 */     return _LgetN(this.luaState, paramInt);
/*      */   }
/*      */ 
/*      */   
/*      */   public void LsetN(int paramInt1, int paramInt2) {
/*  749 */     _LsetN(this.luaState, paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */   
/*      */   public int LloadFile(String paramString) {
/*  754 */     return _LloadFile(this.luaState, paramString);
/*      */   }
/*      */ 
/*      */   
/*      */   public int LloadString(String paramString) {
/*  759 */     return _LloadString(this.luaState, paramString);
/*      */   }
/*      */ 
/*      */   
/*      */   public int LloadBuffer(byte[] paramArrayOfbyte, String paramString) {
/*  764 */     return _LloadBuffer(this.luaState, paramArrayOfbyte, paramArrayOfbyte.length, paramString);
/*      */   }
/*      */ 
/*      */   
/*      */   public String Lgsub(String paramString1, String paramString2, String paramString3) {
/*  769 */     return _Lgsub(this.luaState, paramString1, paramString2, paramString3);
/*      */   }
/*      */ 
/*      */   
/*      */   public String LfindTable(int paramInt1, String paramString, int paramInt2) {
/*  774 */     return _LfindTable(this.luaState, paramInt1, paramString, paramInt2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void pop(int paramInt) {
/*  782 */     _pop(this.luaState, paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void getGlobal(String paramString) {
/*  789 */     _getGlobal(this.luaState, paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void setGlobal(String paramString) {
/*  797 */     _setGlobal(this.luaState, paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void openBase() {
/*  803 */     _openBase(this.luaState);
/*      */   }
/*      */   
/*      */   public void openTable() {
/*  807 */     _openTable(this.luaState);
/*      */   }
/*      */   
/*      */   public void openIo() {
/*  811 */     _openIo(this.luaState);
/*      */   }
/*      */   
/*      */   public void openOs() {
/*  815 */     _openOs(this.luaState);
/*      */   }
/*      */   
/*      */   public void openString() {
/*  819 */     _openString(this.luaState);
/*      */   }
/*      */   
/*      */   public void openMath() {
/*  823 */     _openMath(this.luaState);
/*      */   }
/*      */   
/*      */   public void openDebug() {
/*  827 */     _openDebug(this.luaState);
/*      */   }
/*      */   
/*      */   public void openPackage() {
/*  831 */     _openPackage(this.luaState);
/*      */   }
/*      */   
/*      */   public void openLibs() {
/*  835 */     _openLibs(this.luaState);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getObjectFromUserdata(int paramInt) throws LuaException {
/*  893 */     return _getObjectFromUserdata(this.luaState, paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isObject(int paramInt) {
/*  903 */     return _isObject(this.luaState, paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void pushJavaObject(Object paramObject) {
/*  914 */     _pushJavaObject(this.luaState, paramObject);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void pushJavaFunction(JavaFunction paramJavaFunction) throws LuaException {
/*  923 */     _pushJavaFunction(this.luaState, paramJavaFunction);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isJavaFunction(int paramInt) {
/*  933 */     return _isJavaFunction(this.luaState, paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void pushObjectValue(Object paramObject) throws LuaException {
/*  944 */     if (paramObject == null) {
/*      */       
/*  946 */       pushNil();
/*      */     }
/*  948 */     else if (paramObject instanceof Boolean) {
/*      */       
/*  950 */       Boolean bool = (Boolean)paramObject;
/*  951 */       pushBoolean(bool.booleanValue());
/*      */     }
/*  953 */     else if (paramObject instanceof Number) {
/*      */       
/*  955 */       pushNumber(((Number)paramObject).doubleValue());
/*      */     }
/*  957 */     else if (paramObject instanceof String) {
/*      */       
/*  959 */       pushString((String)paramObject);
/*      */     }
/*  961 */     else if (paramObject instanceof JavaFunction) {
/*      */       
/*  963 */       JavaFunction javaFunction = (JavaFunction)paramObject;
/*  964 */       pushJavaFunction(javaFunction);
/*      */     }
/*  966 */     else if (paramObject instanceof LuaObject) {
/*      */       
/*  968 */       LuaObject luaObject = (LuaObject)paramObject;
/*  969 */       luaObject.push();
/*      */     }
/*  971 */     else if (paramObject instanceof byte[]) {
/*      */       
/*  973 */       pushString((byte[])paramObject);
/*      */     }
/*      */     else {
/*      */       
/*  977 */       pushJavaObject(paramObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized Object toJavaObject(int paramInt) throws LuaException {
/*  989 */     Boolean bool = null;
/*      */     
/*  991 */     if (isBoolean(paramInt)) {
/*      */       
/*  993 */       bool = new Boolean(toBoolean(paramInt));
/*      */     }
/*  995 */     else if (type(paramInt) == LUA_TSTRING.intValue()) {
/*      */       
/*  997 */       String str = toString(paramInt);
/*      */     }
/*  999 */     else if (isFunction(paramInt)) {
/*      */       
/* 1001 */       LuaObject luaObject = getLuaObject(paramInt);
/*      */     }
/* 1003 */     else if (isTable(paramInt)) {
/*      */       
/* 1005 */       LuaObject luaObject = getLuaObject(paramInt);
/*      */     }
/* 1007 */     else if (type(paramInt) == LUA_TNUMBER.intValue()) {
/*      */       
/* 1009 */       Double double_ = new Double(toNumber(paramInt));
/*      */     }
/* 1011 */     else if (isUserdata(paramInt)) {
/*      */       
/* 1013 */       if (isObject(paramInt))
/*      */       {
/* 1015 */         Object object = getObjectFromUserdata(paramInt);
/*      */       }
/*      */       else
/*      */       {
/* 1019 */         LuaObject luaObject = getLuaObject(paramInt);
/*      */       }
/*      */     
/* 1022 */     } else if (isNil(paramInt)) {
/*      */       
/* 1024 */       bool = null;
/*      */     } 
/*      */     
/* 1027 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LuaObject getLuaObject(String paramString) {
/* 1037 */     return new LuaObject(this, paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LuaObject getLuaObject(LuaObject paramLuaObject, String paramString) throws LuaException {
/* 1050 */     if (paramLuaObject.L.getCPtrPeer() != this.luaState.getPeer()) {
/* 1051 */       throw new LuaException("Object must have the same LuaState as the parent!");
/*      */     }
/* 1053 */     return new LuaObject(paramLuaObject, paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LuaObject getLuaObject(LuaObject paramLuaObject, Number paramNumber) throws LuaException {
/* 1066 */     if (paramLuaObject.L.getCPtrPeer() != this.luaState.getPeer()) {
/* 1067 */       throw new LuaException("Object must have the same LuaState as the parent!");
/*      */     }
/* 1069 */     return new LuaObject(paramLuaObject, paramNumber);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LuaObject getLuaObject(LuaObject paramLuaObject1, LuaObject paramLuaObject2) throws LuaException {
/* 1082 */     if (paramLuaObject1.getLuaState().getCPtrPeer() != this.luaState.getPeer() || paramLuaObject1.getLuaState().getCPtrPeer() != paramLuaObject2.getLuaState().getCPtrPeer())
/*      */     {
/* 1084 */       throw new LuaException("Object must have the same LuaState as the parent!");
/*      */     }
/* 1086 */     return new LuaObject(paramLuaObject1, paramLuaObject2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LuaObject getLuaObject(int paramInt) {
/* 1097 */     return new LuaObject(this, paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Number convertLuaNumber(Double paramDouble, Class<int> paramClass) {
/* 1112 */     if (paramClass.isPrimitive()) {
/*      */       
/* 1114 */       if (paramClass == int.class)
/*      */       {
/* 1116 */         return new Integer(paramDouble.intValue());
/*      */       }
/* 1118 */       if (paramClass == long.class)
/*      */       {
/* 1120 */         return new Long(paramDouble.longValue());
/*      */       }
/* 1122 */       if (paramClass == float.class)
/*      */       {
/* 1124 */         return new Float(paramDouble.floatValue());
/*      */       }
/* 1126 */       if (paramClass == double.class)
/*      */       {
/* 1128 */         return paramDouble;
/*      */       }
/* 1130 */       if (paramClass == byte.class)
/*      */       {
/* 1132 */         return new Byte(paramDouble.byteValue());
/*      */       }
/* 1134 */       if (paramClass == short.class)
/*      */       {
/* 1136 */         return new Short(paramDouble.shortValue());
/*      */       }
/*      */     }
/* 1139 */     else if (paramClass.isAssignableFrom(Number.class)) {
/*      */ 
/*      */       
/* 1142 */       if (paramClass.isAssignableFrom(Integer.class))
/*      */       {
/* 1144 */         return new Integer(paramDouble.intValue());
/*      */       }
/* 1146 */       if (paramClass.isAssignableFrom(Long.class))
/*      */       {
/* 1148 */         return new Long(paramDouble.longValue());
/*      */       }
/* 1150 */       if (paramClass.isAssignableFrom(Float.class))
/*      */       {
/* 1152 */         return new Float(paramDouble.floatValue());
/*      */       }
/* 1154 */       if (paramClass.isAssignableFrom(Double.class))
/*      */       {
/* 1156 */         return paramDouble;
/*      */       }
/* 1158 */       if (paramClass.isAssignableFrom(Byte.class))
/*      */       {
/* 1160 */         return new Byte(paramDouble.byteValue());
/*      */       }
/* 1162 */       if (paramClass.isAssignableFrom(Short.class))
/*      */       {
/* 1164 */         return new Short(paramDouble.shortValue());
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1169 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void openJavaLib() {
/* 1184 */     _openJavaLib(this.luaState, this.stateId);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int Lerror(String paramString) {
/* 1192 */     return _Lerror(this.luaState, paramString);
/*      */   }
/*      */   
/*      */   private synchronized native CPtr _open();
/*      */   
/*      */   private synchronized native void _close(CPtr paramCPtr);
/*      */   
/*      */   private synchronized native CPtr _newthread(CPtr paramCPtr);
/*      */   
/*      */   private synchronized native int _getTop(CPtr paramCPtr);
/*      */   
/*      */   private synchronized native void _setTop(CPtr paramCPtr, int paramInt);
/*      */   
/*      */   private synchronized native void _pushValue(CPtr paramCPtr, int paramInt);
/*      */   
/*      */   private synchronized native void _remove(CPtr paramCPtr, int paramInt);
/*      */   
/*      */   private synchronized native void _insert(CPtr paramCPtr, int paramInt);
/*      */   
/*      */   private synchronized native void _replace(CPtr paramCPtr, int paramInt);
/*      */   
/*      */   private synchronized native int _checkStack(CPtr paramCPtr, int paramInt);
/*      */   
/*      */   private synchronized native void _xmove(CPtr paramCPtr1, CPtr paramCPtr2, int paramInt);
/*      */   
/*      */   private synchronized native int _isNumber(CPtr paramCPtr, int paramInt);
/*      */   
/*      */   private synchronized native int _isString(CPtr paramCPtr, int paramInt);
/*      */   
/*      */   private synchronized native int _isCFunction(CPtr paramCPtr, int paramInt);
/*      */   
/*      */   private synchronized native int _isUserdata(CPtr paramCPtr, int paramInt);
/*      */   
/*      */   private synchronized native int _type(CPtr paramCPtr, int paramInt);
/*      */   
/*      */   private synchronized native String _typeName(CPtr paramCPtr, int paramInt);
/*      */   
/*      */   private synchronized native int _equal(CPtr paramCPtr, int paramInt1, int paramInt2);
/*      */   
/*      */   private synchronized native int _rawequal(CPtr paramCPtr, int paramInt1, int paramInt2);
/*      */   
/*      */   private synchronized native int _lessthan(CPtr paramCPtr, int paramInt1, int paramInt2);
/*      */   
/*      */   private synchronized native double _toNumber(CPtr paramCPtr, int paramInt);
/*      */   
/*      */   private synchronized native int _toInteger(CPtr paramCPtr, int paramInt);
/*      */   
/*      */   private synchronized native int _toBoolean(CPtr paramCPtr, int paramInt);
/*      */   
/*      */   private synchronized native String _toString(CPtr paramCPtr, int paramInt);
/*      */   
/*      */   private synchronized native int _objlen(CPtr paramCPtr, int paramInt);
/*      */   
/*      */   private synchronized native CPtr _toThread(CPtr paramCPtr, int paramInt);
/*      */   
/*      */   private synchronized native void _pushNil(CPtr paramCPtr);
/*      */   
/*      */   private synchronized native void _pushNumber(CPtr paramCPtr, double paramDouble);
/*      */   
/*      */   private synchronized native void _pushInteger(CPtr paramCPtr, int paramInt);
/*      */   
/*      */   private synchronized native void _pushString(CPtr paramCPtr, String paramString);
/*      */   
/*      */   private synchronized native void _pushString(CPtr paramCPtr, byte[] paramArrayOfbyte, int paramInt);
/*      */   
/*      */   private synchronized native void _pushBoolean(CPtr paramCPtr, int paramInt);
/*      */   
/*      */   private synchronized native void _getTable(CPtr paramCPtr, int paramInt);
/*      */   
/*      */   private synchronized native void _getField(CPtr paramCPtr, int paramInt, String paramString);
/*      */   
/*      */   private synchronized native void _rawGet(CPtr paramCPtr, int paramInt);
/*      */   
/*      */   private synchronized native void _rawGetI(CPtr paramCPtr, int paramInt1, int paramInt2);
/*      */   
/*      */   private synchronized native void _createTable(CPtr paramCPtr, int paramInt1, int paramInt2);
/*      */   
/*      */   private synchronized native int _getMetaTable(CPtr paramCPtr, int paramInt);
/*      */   
/*      */   private synchronized native void _getFEnv(CPtr paramCPtr, int paramInt);
/*      */   
/*      */   private synchronized native void _setTable(CPtr paramCPtr, int paramInt);
/*      */   
/*      */   private synchronized native void _setField(CPtr paramCPtr, int paramInt, String paramString);
/*      */   
/*      */   private synchronized native void _rawSet(CPtr paramCPtr, int paramInt);
/*      */   
/*      */   private synchronized native void _rawSetI(CPtr paramCPtr, int paramInt1, int paramInt2);
/*      */   
/*      */   private synchronized native int _setMetaTable(CPtr paramCPtr, int paramInt);
/*      */   
/*      */   private synchronized native int _setFEnv(CPtr paramCPtr, int paramInt);
/*      */   
/*      */   private synchronized native void _call(CPtr paramCPtr, int paramInt1, int paramInt2);
/*      */   
/*      */   private synchronized native int _pcall(CPtr paramCPtr, int paramInt1, int paramInt2, int paramInt3);
/*      */   
/*      */   private synchronized native int _yield(CPtr paramCPtr, int paramInt);
/*      */   
/*      */   private synchronized native int _resume(CPtr paramCPtr, int paramInt);
/*      */   
/*      */   private synchronized native int _status(CPtr paramCPtr);
/*      */   
/*      */   private synchronized native int _gc(CPtr paramCPtr, int paramInt1, int paramInt2);
/*      */   
/*      */   private synchronized native int _error(CPtr paramCPtr);
/*      */   
/*      */   private synchronized native int _next(CPtr paramCPtr, int paramInt);
/*      */   
/*      */   private synchronized native void _concat(CPtr paramCPtr, int paramInt);
/*      */   
/*      */   private synchronized native void _pop(CPtr paramCPtr, int paramInt);
/*      */   
/*      */   private synchronized native void _newTable(CPtr paramCPtr);
/*      */   
/*      */   private synchronized native int _strlen(CPtr paramCPtr, int paramInt);
/*      */   
/*      */   private synchronized native int _isFunction(CPtr paramCPtr, int paramInt);
/*      */   
/*      */   private synchronized native int _isTable(CPtr paramCPtr, int paramInt);
/*      */   
/*      */   private synchronized native int _isNil(CPtr paramCPtr, int paramInt);
/*      */   
/*      */   private synchronized native int _isBoolean(CPtr paramCPtr, int paramInt);
/*      */   
/*      */   private synchronized native int _isThread(CPtr paramCPtr, int paramInt);
/*      */   
/*      */   private synchronized native int _isNone(CPtr paramCPtr, int paramInt);
/*      */   
/*      */   private synchronized native int _isNoneOrNil(CPtr paramCPtr, int paramInt);
/*      */   
/*      */   private synchronized native void _setGlobal(CPtr paramCPtr, String paramString);
/*      */   
/*      */   private synchronized native void _getGlobal(CPtr paramCPtr, String paramString);
/*      */   
/*      */   private synchronized native int _getGcCount(CPtr paramCPtr);
/*      */   
/*      */   private synchronized native int _LdoFile(CPtr paramCPtr, String paramString);
/*      */   
/*      */   private synchronized native int _LdoString(CPtr paramCPtr, String paramString);
/*      */   
/*      */   private synchronized native int _LgetMetaField(CPtr paramCPtr, int paramInt, String paramString);
/*      */   
/*      */   private synchronized native int _LcallMeta(CPtr paramCPtr, int paramInt, String paramString);
/*      */   
/*      */   private synchronized native int _Ltyperror(CPtr paramCPtr, int paramInt, String paramString);
/*      */   
/*      */   private synchronized native int _LargError(CPtr paramCPtr, int paramInt, String paramString);
/*      */   
/*      */   private synchronized native String _LcheckString(CPtr paramCPtr, int paramInt);
/*      */   
/*      */   private synchronized native String _LoptString(CPtr paramCPtr, int paramInt, String paramString);
/*      */   
/*      */   private synchronized native double _LcheckNumber(CPtr paramCPtr, int paramInt);
/*      */   
/*      */   private synchronized native double _LoptNumber(CPtr paramCPtr, int paramInt, double paramDouble);
/*      */   
/*      */   private synchronized native int _LcheckInteger(CPtr paramCPtr, int paramInt);
/*      */   
/*      */   private synchronized native int _LoptInteger(CPtr paramCPtr, int paramInt1, int paramInt2);
/*      */   
/*      */   private synchronized native void _LcheckStack(CPtr paramCPtr, int paramInt, String paramString);
/*      */   
/*      */   private synchronized native void _LcheckType(CPtr paramCPtr, int paramInt1, int paramInt2);
/*      */   
/*      */   private synchronized native void _LcheckAny(CPtr paramCPtr, int paramInt);
/*      */   
/*      */   private synchronized native int _LnewMetatable(CPtr paramCPtr, String paramString);
/*      */   
/*      */   private synchronized native void _LgetMetatable(CPtr paramCPtr, String paramString);
/*      */   
/*      */   private synchronized native void _Lwhere(CPtr paramCPtr, int paramInt);
/*      */   
/*      */   private synchronized native int _Lref(CPtr paramCPtr, int paramInt);
/*      */   
/*      */   private synchronized native void _LunRef(CPtr paramCPtr, int paramInt1, int paramInt2);
/*      */   
/*      */   private synchronized native int _LgetN(CPtr paramCPtr, int paramInt);
/*      */   
/*      */   private synchronized native void _LsetN(CPtr paramCPtr, int paramInt1, int paramInt2);
/*      */   
/*      */   private synchronized native int _LloadFile(CPtr paramCPtr, String paramString);
/*      */   
/*      */   private synchronized native int _LloadBuffer(CPtr paramCPtr, byte[] paramArrayOfbyte, long paramLong, String paramString);
/*      */   
/*      */   private synchronized native int _LloadString(CPtr paramCPtr, String paramString);
/*      */   
/*      */   private synchronized native String _Lgsub(CPtr paramCPtr, String paramString1, String paramString2, String paramString3);
/*      */   
/*      */   private synchronized native String _LfindTable(CPtr paramCPtr, int paramInt1, String paramString, int paramInt2);
/*      */   
/*      */   private synchronized native void _openBase(CPtr paramCPtr);
/*      */   
/*      */   private synchronized native void _openTable(CPtr paramCPtr);
/*      */   
/*      */   private synchronized native void _openIo(CPtr paramCPtr);
/*      */   
/*      */   private synchronized native void _openOs(CPtr paramCPtr);
/*      */   
/*      */   private synchronized native void _openString(CPtr paramCPtr);
/*      */   
/*      */   private synchronized native void _openMath(CPtr paramCPtr);
/*      */   
/*      */   private synchronized native void _openDebug(CPtr paramCPtr);
/*      */   
/*      */   private synchronized native void _openPackage(CPtr paramCPtr);
/*      */   
/*      */   private synchronized native void _openLibs(CPtr paramCPtr);
/*      */   
/*      */   private synchronized native void luajava_open(CPtr paramCPtr, int paramInt);
/*      */   
/*      */   private synchronized native Object _getObjectFromUserdata(CPtr paramCPtr, int paramInt) throws LuaException;
/*      */   
/*      */   private synchronized native boolean _isObject(CPtr paramCPtr, int paramInt);
/*      */   
/*      */   private synchronized native void _pushJavaObject(CPtr paramCPtr, Object paramObject);
/*      */   
/*      */   private synchronized native void _pushJavaFunction(CPtr paramCPtr, JavaFunction paramJavaFunction) throws LuaException;
/*      */   
/*      */   private synchronized native boolean _isJavaFunction(CPtr paramCPtr, int paramInt);
/*      */   
/*      */   private synchronized native void _openJavaLib(CPtr paramCPtr, int paramInt);
/*      */   
/*      */   private synchronized native int _Lerror(CPtr paramCPtr, String paramString);
/*      */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\keplerproject\luajava\LuaState.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */