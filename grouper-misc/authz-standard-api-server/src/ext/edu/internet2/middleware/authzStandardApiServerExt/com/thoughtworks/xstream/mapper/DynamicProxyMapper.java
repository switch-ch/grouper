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
 * Copyright (C) 2005 Joe Walnes.
 * Copyright (C) 2006, 2007 XStream Committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 * 
 * Created on 22. January 2005 by Joe Walnes
 */
package edu.internet2.middleware.authzStandardApiServerExt.com.thoughtworks.xstream.mapper;

import edu.internet2.middleware.authzStandardApiServerExt.com.thoughtworks.xstream.mapper.Mapper;
import edu.internet2.middleware.authzStandardApiServerExt.com.thoughtworks.xstream.mapper.MapperWrapper;
import edu.internet2.middleware.authzStandardApiServerExt.com.thoughtworks.xstream.alias.ClassMapper;

import java.lang.reflect.Proxy;

/**
 * Mapper for handling special cases of aliasing dynamic proxies. The alias property specifies the name an instance
 * of a dynamic proxy should be serialized with.
 *
 * @author Joe Walnes
 */
public class DynamicProxyMapper extends MapperWrapper {

    private String alias;

    public DynamicProxyMapper(Mapper wrapped) {
        this(wrapped, "dynamic-proxy");
    }

    public DynamicProxyMapper(Mapper wrapped, String alias) {
        super(wrapped);
        this.alias = alias;
    }

    /**
     * @deprecated As of 1.2, use {@link #DynamicProxyMapper(Mapper)}
     */
    public DynamicProxyMapper(ClassMapper wrapped) {
        this((Mapper)wrapped);
    }

    /**
     * @deprecated As of 1.2, use {@link #DynamicProxyMapper(Mapper, String)}
     */
    public DynamicProxyMapper(ClassMapper wrapped, String alias) {
        this((Mapper)wrapped, alias);
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String serializedClass(Class type) {
        if (Proxy.isProxyClass(type)) {
            return alias;
        } else {
            return super.serializedClass(type);
        }
    }

    public Class realClass(String elementName) {
        if (elementName.equals(alias)) {
            return DynamicProxy.class;
        } else {
            return super.realClass(elementName);
        }
    }

    /**
     * Place holder type used for dynamic proxies.
     */
    public static class DynamicProxy {}

}
