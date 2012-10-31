/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.internet2.middleware.authzStandardApiServerExt.org.apache.commons.jexl2.parser;

import edu.internet2.middleware.authzStandardApiServerExt.org.apache.commons.jexl2.parser.JexlNode;
import edu.internet2.middleware.authzStandardApiServerExt.org.apache.commons.jexl2.parser.Node;
import edu.internet2.middleware.authzStandardApiServerExt.org.apache.commons.jexl2.parser.Parser;
import edu.internet2.middleware.authzStandardApiServerExt.org.apache.commons.jexl2.parser.ParserTreeConstants;
import edu.internet2.middleware.authzStandardApiServerExt.org.apache.commons.jexl2.parser.ParserVisitor;
import edu.internet2.middleware.authzStandardApiServerExt.org.apache.commons.jexl2.parser.SimpleNode;

/**
 * A class originally generated by JJTree with the following JavaCCOptions:
 * MULTI=true,NODE_USES_PARSER=true,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=
 *
 * Worksaround issue https://javacc.dev.java.net/issues/show_bug.cgi?id=227
 * As soon as this issue if fixed and the maven plugin uses the correct version of Javacc, this
 * class can go away.
 *
 * The technical goal is to ensure every reference made in the parser was to a JexlNode; unfortunately,
 * as in javacc 4.1, it still uses a SimpleNode reference in the generated ParserVisitor.
 * Besides, there is no need to keep the parser around in the node.
 *
 * The functional goal is to a allow a <em>volatile</em> value in the node
 * so it can serve as a last evaluation cache even in multi-threaded executions.
 */
public class SimpleNode implements Node {
    /** The parent node. */
    protected JexlNode parent;
    /** The array of children nodes. */
    protected JexlNode[] children;
    /** The node type id. */
    protected final int id;
    /** volatile value so it can be used as a last evaluation cache. */
    protected volatile Object value;

    /**
     * Creates a SimpleNode instance.
     * @param i the node type identifier
     */
    public SimpleNode(int i) {
        id = i;
    }

    /**
     * Creates a SimpleNode instance.
     * @param p the parser instance
     * @param i the node type identifier
     */
    public SimpleNode(Parser p, int i) {
        this(i);
    }

    /** {@inheritDoc} */
    public void jjtOpen() {
    }

    /** {@inheritDoc} */
    public void jjtClose() {
    }

    /**
     * Sets this node's parent.
     * @param n the parent
     */
    public void jjtSetParent(Node n) {
        parent = (JexlNode) n;
    }

    /**
     * Gets this node's parent.
     * @return the parent node
     */
    public JexlNode jjtGetParent() {
        return parent;
    }

    /** Adds a child node.
     * @param n the child node
     * @param i the child offset
     */
    public void jjtAddChild(Node n, int i) {
        if (children == null) {
            children = new JexlNode[i + 1];
        } else if (i >= children.length) {
            JexlNode[] c = new JexlNode[i + 1];
            System.arraycopy(children, 0, c, 0, children.length);
            children = c;
        }
        children[i] = (JexlNode) n;
    }

    /**
     * Gets a child of this node.
     * @param i the child offset
     * @return the child node
     */
    public JexlNode jjtGetChild(int i) {
        return children[i];
    }

    /**
     * Gets this node number of children.
     * @return the number of children
     */
    public int jjtGetNumChildren() {
        return (children == null) ? 0 : children.length;
    }

    /** Sets this node value.
     * @param value
     */
    public void jjtSetValue(Object value) {
        this.value = value;
    }

    /** Gets this node value.
     * @return value
     */
    public Object jjtGetValue() {
        return value;
    }

    /**
     * Accept the visitor.
     * @param visitor the visitor
     * @param data contextual data
     * @return result of visit
     **/
    public Object jjtAccept(ParserVisitor visitor, Object data) {
        return visitor.visit(this, data);
    }

    /**
     * Accept the visitor on all this node's children.
     * @param visitor the visitor
     * @param data contextual data
     * @return result of visit
     **/
    public Object childrenAccept(ParserVisitor visitor, Object data) {
        if (children != null) {
            for (int i = 0; i < children.length; ++i) {
                children[i].jjtAccept(visitor, data);
            }
        }
        return data;
    }

    /* You can override these two methods in subclasses of SimpleNode to
    customize the way the JexlNode appears when the tree is dumped.  If
    your output uses more than one line you should override
    toString(String), otherwise overriding toString() is probably all
    you need to do. */
    @Override
    public String toString() {
        return ParserTreeConstants.jjtNodeName[id];
    }

    public String toString(String prefix) {
        return prefix + toString();
    }

    /* Override this method if you want to customize how the JexlNode dumps
    out its children. */
    public void dump(String prefix) {
        System.out.println(toString(prefix));
        if (children != null) {
            for (int i = 0; i < children.length; ++i) {
                SimpleNode n = children[i];
                if (n != null) {
                    n.dump(prefix + " ");
                }
            }
        }
    }
}

/* JavaCC - OriginalChecksum=7dff880883d088a37c1e3197e4b455a0 (do not edit this line) */
