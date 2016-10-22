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

package net.fycraft.plugins.vip.api.pagseguro.service;

import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

import net.fycraft.plugins.vip.api.pagseguro.domain.Credentials;
import net.fycraft.plugins.vip.api.pagseguro.domain.Error;
import net.fycraft.plugins.vip.api.pagseguro.domain.PaymentRequest;
import net.fycraft.plugins.vip.api.pagseguro.domain.checkout.Checkout;
import net.fycraft.plugins.vip.api.pagseguro.enums.HttpStatus;
import net.fycraft.plugins.vip.api.pagseguro.exception.PagSeguroServiceException;
import net.fycraft.plugins.vip.api.pagseguro.logs.Log;
import net.fycraft.plugins.vip.api.pagseguro.parser.PaymentParser;
import net.fycraft.plugins.vip.api.pagseguro.service.checkout.CheckoutService;
import net.fycraft.plugins.vip.api.pagseguro.utils.HttpConnection;
import net.fycraft.plugins.vip.api.pagseguro.xmlparser.ErrorsParser;

/**
 * 
 * Class Payment Service
 * 
 * @deprecated use {@link CheckoutService} instead.
 */
@Deprecated
public class PaymentService {

    private PaymentService() {
    }

    /**
     * @var Log
     */
    private static Log log = new Log(PaymentService.class);

    /**
     * 
     * @param ConnectionData
     *            connectionData
     * @return string
     * @throws PagSeguroServiceException
     */
    public static String buildCheckoutRequestUrl(ConnectionData connectionData) throws PagSeguroServiceException {
        return CheckoutService.buildCheckoutRequestUrl(connectionData);
    }

    /**
     * Build checkout url
     * 
     * @param conn
     * @param code
     * @return string
     */
    private static String buildCheckoutUrl(ConnectionData connection, String code) {
        return connection.getCheckoutUrl() + "?code=" + code;
    }

    /**
     * 
     * @param credentials
     * @param checkout
     * @param onlyCheckoutCode
     * @return string
     * @throws Exception
     */
    public static String createCheckoutRequest(Credentials credentials, Checkout checkout, Boolean onlyCheckoutCode)
            throws PagSeguroServiceException {
        return CheckoutService.createCheckoutRequest(credentials, checkout, onlyCheckoutCode);
    }

    /**
     * 
     * @param credentials
     * @param paymentRequest
     * @param onlyCheckoutCode
     * @return string
     * @throws Exception
     * 
     * @deprecated use {@link #createCheckoutRequest(Credentials, Checkout, Boolean)} instead.
     */
    @Deprecated
    public static String createCheckoutRequest(Credentials credentials, PaymentRequest paymentRequest,
            Boolean onlyCheckoutCode) throws PagSeguroServiceException {

        PaymentService.log.info(String.format("PaymentService.Register( %s ) - begin", paymentRequest.toString()));

        ConnectionData connectionData = new ConnectionData(credentials);

        Map<Object, Object> data = PaymentParser.getData(paymentRequest);

        String url = PaymentService.buildCheckoutRequestUrl(connectionData);

        HttpConnection connection = new HttpConnection();
        HttpStatus httpCodeStatus = null;

        HttpURLConnection response = connection.post(url, data, connectionData.getServiceTimeout(),
                connectionData.getCharset());

        try {

            httpCodeStatus = HttpStatus.fromCode(response.getResponseCode());
            if (httpCodeStatus == null) {
                throw new PagSeguroServiceException("Connection Timeout");
            } else if (HttpURLConnection.HTTP_OK == httpCodeStatus.getCode().intValue()) {

                String paymentReturn = null;
                String code = PaymentParser.readSuccessXml(response);

                if (onlyCheckoutCode) {
                    paymentReturn = code;
                } else {
                    paymentReturn = PaymentService.buildCheckoutUrl(connectionData, code);
                }

                PaymentService.log.info(String.format("PaymentService.Register( %1s ) - end  %2s )",
                        paymentRequest.toString(), code));

                return paymentReturn;

            } else if (HttpURLConnection.HTTP_BAD_REQUEST == httpCodeStatus.getCode().intValue()) {

                List<Error> errors = ErrorsParser.readErrosXml(response.getErrorStream());

                PagSeguroServiceException exception = new PagSeguroServiceException(httpCodeStatus, errors);

                PaymentService.log.error(String.format("PaymentService.Register( %1s ) - error %2s",
                        paymentRequest.toString(), exception.getMessage()));

                throw exception;

            } else {

                throw new PagSeguroServiceException(httpCodeStatus);
            }

        } catch (PagSeguroServiceException e) {
            throw e;
        } catch (Exception e) {

            PaymentService.log.error(String.format("PaymentService.Register( %1s ) - error %2s",
                    paymentRequest.toString(), e.getMessage()));

            throw new PagSeguroServiceException(httpCodeStatus, e);

        } finally {
            response.disconnect();
        }
    }

}
