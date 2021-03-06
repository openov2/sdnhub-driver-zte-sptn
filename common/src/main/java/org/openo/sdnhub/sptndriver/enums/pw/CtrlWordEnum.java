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

package org.openo.sdnhub.sptndriver.enums.pw;

import org.openo.sdnhub.sptndriver.exception.ParamErrorException;

import java.util.ArrayList;
import java.util.List;

/**
 * Enumerator of PW control word.
 */
public enum CtrlWordEnum {
    DISABLE("disable", Integer.valueOf(0)),
    ENABLE("enable", Integer.valueOf(1));
    private String name;
    private Integer index;

    CtrlWordEnum(String name, Integer index) {
        this.name = name;
        this.index = index;
    }

    /**
     * Convert SBI integer control word to NBI string control word.
     *
     * @param index Integer value of NBI control word.
     * @return String value of SBI control word.
     */
    public static String getName(Integer index)
        throws ParamErrorException {
        if (index == null) {
            return null;
        }
        for (CtrlWordEnum e : CtrlWordEnum.values()) {
            if (e.getIndex().equals(index)) {
                return e.name;
            }
        }
        List<String> validValues = new ArrayList<>();
        for (CtrlWordEnum ctrlWordEnum : CtrlWordEnum.values()) {
            validValues.add(ctrlWordEnum.getIndex().toString());
        }
        throw new ParamErrorException(validValues.toArray(), index.toString());
    }

    /**
     * Convert NBI string control word to NBI integer control word.
     *
     * @param name String value of SBI control word.
     * @return Integer value of NBI control word.
     */
    public static Integer getIndex(String name)
        throws ParamErrorException {
        if (name == null) {
            return null;
        }
        for (CtrlWordEnum e : CtrlWordEnum.values()) {
            if (e.getName().equals(name)) {
                return e.index;
            }
        }
        List<String> validValues = new ArrayList<>();
        for (CtrlWordEnum ctrlWordEnum : CtrlWordEnum.values()) {
            validValues.add(ctrlWordEnum.getName());
        }
        throw new ParamErrorException(validValues.toArray(), name);
    }

    /**
     * Get NBI control word.
     */
    public String getName() {
        return name;
    }

    /**
     * Get SBI control word.
     */
    public Integer getIndex() {
        return index;
    }
}
