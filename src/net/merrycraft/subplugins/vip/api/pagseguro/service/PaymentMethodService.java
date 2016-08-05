/*
 ************************************************************************
 Copyright [2011] [PagSeguro Internet Ltda.]

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 ************************************************************************
 */

package net.merrycraft.subplugins.vip.api.pagseguro.service;

import java.net.HttpURLConnection;
import java.util.List;

import net.merrycraft.subplugins.vip.api.pagseguro.domain.Credentials;
import net.merrycraft.subplugins.vip.api.pagseguro.domain.Error;
import net.merrycraft.subplugins.vip.api.pagseguro.domain.paymentmethod.PaymentMethods;
import net.merrycraft.subplugins.vip.api.pagseguro.enums.HttpStatus;
import net.merrycraft.subplugins.vip.api.pagseguro.exception.PagSeguroServiceException;
import net.merrycraft.subplugins.vip.api.pagseguro.logs.Log;
import net.merrycraft.subplugins.vip.api.pagseguro.parser.PaymentMethodsParser;
import net.merrycraft.subplugins.vip.api.pagseguro.utils.HttpConnection;
import net.merrycraft.subplugins.vip.api.pagseguro.xmlparser.ErrorsParser;

/**
 * 
 * Class Payment Method Service
 */
public class PaymentMethodService {

    /**
     * @var Log
     */
    private static Log log = new Log(PaymentMethodService.class);

    private static String buildPaymentMethodsUrl(ConnectionData connectionData) {
        return connectionData.getPaymentMethodsUrl();
    }

    public static PaymentMethods getPaymentMethods(Credentials credentials, //
            String publicKey) //
            throws PagSeguroServiceException {
        log.info("PaymentMethodService.getPaymentMethods() - begin");

        ConnectionData connectionData = new ConnectionData(credentials);

        StringBuilder url = new StringBuilder(PaymentMethodService.buildPaymentMethodsUrl(connectionData));
        url.append("?publicKey=" + publicKey);

        HttpConnection connection = new HttpConnection();
        HttpStatus httpCodeStatus = null;

        HttpURLConnection response = connection.get(url.toString(), //
                connectionData.getServiceTimeout(), //
                connectionData.getCharset(),
                null);

        try {
            httpCodeStatus = HttpStatus.fromCode(response.getResponseCode());

            if (httpCodeStatus == null) {
                throw new PagSeguroServiceException("Connection Timeout");
            } else if (HttpURLConnection.HTTP_OK == httpCodeStatus.getCode().intValue()) {
                PaymentMethods paymentMethods = PaymentMethodsParser.readPaymentMethods(response.getInputStream());

                log.info("PaymentMethodService.getPaymentMethods() - end");

                return paymentMethods;
            } else if (HttpURLConnection.HTTP_BAD_REQUEST == httpCodeStatus.getCode().intValue()) {
                List<Error> errors = ErrorsParser.readErrosXml(response.getErrorStream());

                PagSeguroServiceException exception = new PagSeguroServiceException(httpCodeStatus, errors);

                log.error(String.format("PaymentMethodService.getPaymentMethods() - error %s", //
                        exception.getMessage()));

                throw exception;
            } else if (HttpURLConnection.HTTP_UNAUTHORIZED == httpCodeStatus.getCode().intValue()) {
                PagSeguroServiceException exception = new PagSeguroServiceException(httpCodeStatus);

                log.error(String.format("PaymentMethodService.getPaymentMethods() - error %s", //
                        exception.getMessage()));

                throw exception;
            } else {
                throw new PagSeguroServiceException(httpCodeStatus);
            }
        } catch (PagSeguroServiceException e) {
            throw e;
        } catch (Exception e) {
            log.error(String.format("PaymentMethodService.getPaymentMethods() - error %s", //
                    e.getMessage()));

            throw new PagSeguroServiceException(httpCodeStatus, e);
        } finally {
            response.disconnect();
        }

    }
}
