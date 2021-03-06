/*
 * Copyright 2016-2017 ZTE Corporation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openo.sdnhub.sptndriver.enums.ac;

import junit.framework.Assert;

import org.junit.Test;
import org.openo.sdnhub.sptndriver.exception.ParamErrorException;

import static org.junit.Assert.*;

/**
 * The UT class of AccessActionEnum.
 */
public class AccessActionEnumTest {

    @Test
    public void testConvertNbiToSbi_legal() throws Exception {
        Assert.assertEquals(AccessActionEnum.KEEP.getSouth(),
            AccessActionEnum.convertNbiToSbi(AccessActionEnum.KEEP.getNorth()));
    }

    @Test
    public void testConvertNbiToSbi_Illegal() throws Exception {
        try {
            AccessActionEnum.convertNbiToSbi("KEEP");
        } catch (ParamErrorException ex) {
            return;
        }
        fail("Except ParamErrorException when convert KEEP to SBI");
    }

    @Test
    public void testConvertNbiToSbi_null() throws Exception {
        Assert.assertEquals(null, AccessActionEnum.convertNbiToSbi(null));
    }

    @Test
    public void testConvertSbiToNbi_legal() throws Exception {
        Assert.assertEquals(AccessActionEnum.KEEP.getNorth(),
            AccessActionEnum.convertSbiToNbi(AccessActionEnum.KEEP.getSouth()));
    }

    @Test
    public void testConvertSbiToNbi_null() throws Exception {
        Assert.assertEquals(null, AccessActionEnum.convertSbiToNbi(null));
    }

    @Test
    public void testConvertSbiToNbi_Illegal() throws Exception {
       // Currently, there is no way to input illegal parameter.
    }
}