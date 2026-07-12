package com.ankamagames.framework.kernel.core.sql;

import com.ankamagames.framework.kernel.core.common.Validable;
import com.ankamagames.framework.kernel.core.common.message.MessageHandler;

public interface SqlRequestRecipient extends MessageHandler, Validable {
  void onExceptionRaised(SqlRequest paramSqlRequest, Exception paramException);
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\core\sql\SqlRequestRecipient.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */