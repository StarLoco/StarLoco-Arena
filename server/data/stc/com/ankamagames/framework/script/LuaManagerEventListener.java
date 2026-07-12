package com.ankamagames.framework.script;

public abstract interface LuaManagerEventListener
{
  public abstract void onLuaScriptError(LuaScript paramLuaScript, LuaScriptErrorType paramLuaScriptErrorType, String paramString);
  
  public abstract void onScriptFinished(LuaScript paramLuaScript);
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\script\LuaManagerEventListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */