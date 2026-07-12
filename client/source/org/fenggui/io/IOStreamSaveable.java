package org.fenggui.io;

import java.io.IOException;

public interface IOStreamSaveable {
  public static final String GENERATE_NAME = "--generate-name--";
  
  void process(InputOutputStream paramInputOutputStream) throws IOException, IOStreamException;
  
  String getUniqueName();
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\io\IOStreamSaveable.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */