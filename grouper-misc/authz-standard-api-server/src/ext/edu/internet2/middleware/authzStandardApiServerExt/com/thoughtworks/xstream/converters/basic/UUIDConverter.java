/*******************************************************************************
 * Copyright 2012 Internet2
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
/*
 * Copyright (C) 2008 XStream Committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 * 
 * Created on 04. January 2008 by Joerg Schaible
 */
package edu.internet2.middleware.authzStandardApiServerExt.com.thoughtworks.xstream.converters.basic;

import java.util.UUID;

import edu.internet2.middleware.authzStandardApiServerExt.com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter;
import edu.internet2.middleware.authzStandardApiServerExt.com.thoughtworks.xstream.converters.ConversionException;


/**
 * Converts a java.util.UUID to a string.
 * 
 * @author J&ouml;rg Schaible
 */
public class UUIDConverter extends AbstractSingleValueConverter {

    public boolean canConvert(Class type) {
        return type.equals(UUID.class);
    }

    public Object fromString(String str) {
        try {
            return UUID.fromString(str);
        } catch(IllegalArgumentException e) {
            throw new ConversionException("Cannot create UUID instance", e);
        }
    }

}
