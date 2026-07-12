package com.ankamagames.framework.script;

public interface LuaManagerEventListener {
  void onLuaScriptError(LuaScript paramLuaScript, LuaScriptErrorType paramLuaScriptErrorType, String paramString);
  
  void onScriptFinished(LuaScript paramLuaScript);
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\script\LuaManagerEventListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */