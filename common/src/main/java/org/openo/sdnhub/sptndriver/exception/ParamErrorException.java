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

package org.openo.sdnhub.sptndriver.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * The exception class for wrong parameter.
 */
public class ParamErrorException extends ServerException {
    private final String errorInfo;

    public ParamErrorException(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    /**
     * Parameter exception of invalid input.
     *
     * @param validValues Expected valid input array
     * @param inputValue  Actual input
     */
    public ParamErrorException(final Object[] validValues, final String inputValue)
        throws ParamErrorException {
        errorInfo = getErrorInfo(validValues,inputValue);
    }

    private static String getErrorInfo(final Object[] validValues, final String inputValue) {
        StringBuilder tempErrorInfo = new StringBuilder("Valid values are: {");
        for (Object para : validValues) {
            tempErrorInfo.append(para.toString()).append(",");
        }
        tempErrorInfo.append("}, input value is: ");
        if (inputValue == null) {
            tempErrorInfo.append("null. ");
        } else {
            tempErrorInfo.append(inputValue).append(". ");
        }
        return tempErrorInfo.toString();
    }

    @Override
    public String toString() {
        return errorInfo;
    }

    @Override
    public Response getResponse() {
        return Response
            .status(Response.Status.BAD_REQUEST)
            .type(MediaType.TEXT_PLAIN_TYPE)
            .entity(toString())
            .build();
    }
}
