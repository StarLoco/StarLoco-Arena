package org.fenggui.io;

public interface ContextHandler {
  IOStreamSaveable get(String paramString);
  
  String getName(IOStreamSaveable paramIOStreamSaveable);
  
  void add(String paramString, IOStreamSaveable paramIOStreamSaveable) throws NameShadowingException;
  
  void startSubcontext(String paramString);
  
  void endSubcontext();
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\io\ContextHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */