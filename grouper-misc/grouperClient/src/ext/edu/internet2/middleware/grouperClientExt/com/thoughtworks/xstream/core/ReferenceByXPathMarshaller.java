/*
 * Copyright (C) 2004, 2006 Joe Walnes.
 * Copyright (C) 2006, 2007 XStream Committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 * 
 * Created on 03. April 2004 by Joe Walnes
 */
package edu.internet2.middleware.grouperClientExt.com.thoughtworks.xstream.core;

import edu.internet2.middleware.grouperClientExt.com.thoughtworks.xstream.alias.ClassMapper;
import edu.internet2.middleware.grouperClientExt.com.thoughtworks.xstream.converters.ConverterLookup;
import edu.internet2.middleware.grouperClientExt.com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import edu.internet2.middleware.grouperClientExt.com.thoughtworks.xstream.io.path.Path;
import edu.internet2.middleware.grouperClientExt.com.thoughtworks.xstream.mapper.Mapper;

public class ReferenceByXPathMarshaller extends AbstractReferenceMarshaller {

    private final int mode;

    public ReferenceByXPathMarshaller(HierarchicalStreamWriter writer, ConverterLookup converterLookup, Mapper mapper, int mode) {
        super(writer, converterLookup, mapper);
        this.mode = mode;
    }

    /**
     * @deprecated As of 1.2, use {@link #ReferenceByXPathMarshaller(HierarchicalStreamWriter, ConverterLookup, Mapper, int)}
     */
    public ReferenceByXPathMarshaller(HierarchicalStreamWriter writer, ConverterLookup converterLookup, ClassMapper classMapper) {
        this(writer, converterLookup, classMapper, ReferenceByXPathMarshallingStrategy.RELATIVE);
    }

    protected String createReference(Path currentPath, Object existingReferenceKey) {
        return (mode == ReferenceByXPathMarshallingStrategy.RELATIVE ? currentPath.relativeTo((Path)existingReferenceKey) : existingReferenceKey).toString();
    }

    protected Object createReferenceKey(Path currentPath) {
        return currentPath;
    }

    protected void fireValidReference(Object referenceKey) {
        // nothing to do
    }
}