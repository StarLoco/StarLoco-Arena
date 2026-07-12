package org.fenggui.render;

import org.fenggui.io.IOStreamSaveable;

public interface ITexture extends IOStreamSaveable {
  void bind();
  
  void delete();
  
  int getTextureWidth();
  
  int getTextureHeight();
  
  int getImageWidth();
  
  int getImageHeight();
  
  void setAlpha(boolean paramBoolean);
  
  boolean hasAlpha();
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\render\ITexture.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */