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
 
package edu.internet2.middleware.authzStandardApiServerExt.org.apache.commons.beanutils.locale;

import edu.internet2.middleware.authzStandardApiServerExt.org.apache.commons.beanutils.Converter;


/**
 * <p>General purpose locale-sensitive data type converter that can be registered and used
 * within the BeanUtils package to manage the conversion of objects from
 * one type to another.
 *
 * @author Yauheny Mikulski
 */

public interface LocaleConverter extends Converter {


    /**
     * Convert the specified locale-sensitive input object into an output object of the
     * specified type.
     *
     * @param type Data type to which this value should be converted
     * @param value The input value to be converted
     * @param pattern The user-defined pattern is used for the input object formatting.
     * @return The converted value
     *
     * @exception edu.internet2.middleware.authzStandardApiServerExt.org.apache.commons.beanutils.ConversionException if conversion
     * cannot be performed successfully
     */
    public Object convert(Class type, Object value, String pattern);
}
