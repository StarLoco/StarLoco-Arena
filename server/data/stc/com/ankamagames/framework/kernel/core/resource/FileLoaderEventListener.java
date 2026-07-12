package com.ankamagames.framework.kernel.core.resource;

public abstract interface FileLoaderEventListener
{
  public abstract void onLoadStart(String paramString);
  
  public abstract void onLoadComplete(String paramString);
  
  public abstract void onLoadError(String paramString1, String paramString2);
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\core\resource\FileLoaderEventListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */