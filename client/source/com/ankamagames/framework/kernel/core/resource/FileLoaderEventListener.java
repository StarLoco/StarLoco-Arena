package com.ankamagames.framework.kernel.core.resource;

public interface FileLoaderEventListener {
  void onLoadStart(String paramString);
  
  void onLoadComplete(String paramString);
  
  void onLoadError(String paramString1, String paramString2);
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\core\resource\FileLoaderEventListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */