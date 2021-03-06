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

package org.openo.sdnhub.sptndriver.services;

import org.openo.sdnhub.sptndriver.utils.ServiceUtil;
import org.openo.sdnhub.sptndriver.config.AppConfig;
import org.openo.sdnhub.sptndriver.exception.ServerException;
import org.openo.sdnhub.sptndriver.exception.ServerIoException;
import org.openo.sdnhub.sptndriver.models.esr.SdnController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * The ESR service class.
 */
public class EsrService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EsrService.class);

    /**
     * Get SDN-O controller.
     *
     * @param controllerId SDN-O controller id.
     * @return SDN-O controller.
     */
    public SdnController getSdnoController(String controllerId)
        throws ServerException {
        String printText = "Get controller " + controllerId;
        LOGGER.debug(printText + " begin. ");
        Retrofit retrofit = ServiceUtil.initRetrofit(AppConfig.getConfig().getMsbUrl());
        EsrServiceInterface service = retrofit.create(EsrServiceInterface.class);
        Call<SdnController> cmdCall = service.getSdnoController(controllerId);
        Response<SdnController> response;
        try {
            response = cmdCall.execute();
        } catch (IOException ex) {
            throw new ServerIoException(ex);
        }

        SdnController sdnController = ServiceUtil.parseResponse(response, LOGGER, printText);

        LOGGER.debug(printText + " end. ");
        return sdnController;
    }
}
