package com.ankamagames.graphics.isometric;

public interface RenderProcessHandler<HandlerScene extends IsoWorldScene> {
  void process(HandlerScene paramHandlerScene, long paramLong, int paramInt);
  
  void prepareBeforeRendering(HandlerScene paramHandlerScene, int paramInt1, int paramInt2);
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\graphics\isometric\RenderProcessHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */