/* Generated By:JJTree: Do not edit this line. ASTReference.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package edu.internet2.middleware.authzStandardApiServerExt.org.apache.commons.jexl2.parser;

import edu.internet2.middleware.authzStandardApiServerExt.org.apache.commons.jexl2.parser.JexlNode;
import edu.internet2.middleware.authzStandardApiServerExt.org.apache.commons.jexl2.parser.Parser;
import edu.internet2.middleware.authzStandardApiServerExt.org.apache.commons.jexl2.parser.ParserVisitor;

public
class ASTReference extends JexlNode {
  public ASTReference(int id) {
    super(id);
  }

  public ASTReference(Parser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(ParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=67e4a104c50277a2b611412d6f72e403 (do not edit this line) */
