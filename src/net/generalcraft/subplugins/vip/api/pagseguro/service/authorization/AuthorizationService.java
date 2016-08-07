/*
 ************************************************************************
 Copyright [2014] [PagSeguro Internet Ltda.]

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

package net.generalcraft.subplugins.vip.api.pagseguro.service.authorization;

import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

import net.generalcraft.subplugins.vip.api.pagseguro.domain.Credentials;
import net.generalcraft.subplugins.vip.api.pagseguro.domain.Error;
import net.generalcraft.subplugins.vip.api.pagseguro.enums.HttpStatus;
import net.generalcraft.subplugins.vip.api.pagseguro.exception.PagSeguroServiceException;
import net.generalcraft.subplugins.vip.api.pagseguro.logs.Log;
import net.generalcraft.subplugins.vip.api.pagseguro.parser.authorization.AuthorizationRequestParser;
import net.generalcraft.subplugins.vip.api.pagseguro.service.ConnectionData;
import net.generalcraft.subplugins.vip.api.pagseguro.utils.HttpConnection;
import net.generalcraft.subplugins.vip.api.pagseguro.xmlparser.ErrorsParser;

/**
 * 
 * Class AuthorizationService
 */
public class AuthorizationService {
	
	/**
     * @var Log
     */
    private static Log log = new Log(AuthorizationService.class);

    /**
     * 
     * @param ConnectionData
     *            connectionData
     * @return string
     * @throws PagSeguroServiceException
     */
    public static String buildAuthorizationRequestUrl(ConnectionData connectionData) throws PagSeguroServiceException {
        return connectionData.getWSAuthorizationUrl() + "?" + connectionData.getCredentialsUrlQuery();
    }

    /**
     * Build authorization url
     * 
     * @param connection
     * @param code
     * @return string
     */
    private static String buildAuthorizationUrl(ConnectionData connection, String code) {
        return connection.getAuthorizationUrl() + "?code=" + code;
    }

    /**
     * 
     * @param credentials
     * @param authorizationRequest
     * @param onlyCheckoutCode
     * @return string
     * @throws Exception
     */
    public static String createAuthorizationRequest(Credentials credentials, net.generalcraft.subplugins.vip.api.pagseguro.domain.authorization.AuthorizationRequest authorizationRequest, Boolean onlyCheckoutCode)
            throws PagSeguroServiceException {

        AuthorizationService.log.info(String.format("AuthorizationService.Register( %s ) - begin", authorizationRequest.toString()));

        ConnectionData connectionData = new ConnectionData(credentials);

        Map<Object, Object> data = AuthorizationRequestParser.getData(authorizationRequest);
        
        String url = AuthorizationService.buildAuthorizationRequestUrl(connectionData);

        HttpConnection connection = new HttpConnection();
        HttpStatus httpCodeStatus = null;

        HttpURLConnection response = connection.post(url, data, connectionData.getServiceTimeout(),
                connectionData.getCharset(), null);

        try {

            httpCodeStatus = HttpStatus.fromCode(response.getResponseCode());
            if (httpCodeStatus == null) {
                throw new PagSeguroServiceException("Connection Timeout");
            } else if (HttpURLConnection.HTTP_OK == httpCodeStatus.getCode().intValue()) {

                String authorizationReturn = null;
                String code = AuthorizationRequestParser.readSuccessXml(response);

                if (onlyCheckoutCode) {
                	authorizationReturn = code;
                } else {
                	authorizationReturn = AuthorizationService.buildAuthorizationUrl(connectionData, code);
                }

                AuthorizationService.log.info(String.format("AuthorizationService.Register( %1s ) - end  %2s )",
                        authorizationRequest.toString(), code));

                return authorizationReturn;

            } else if (HttpURLConnection.HTTP_BAD_REQUEST == httpCodeStatus.getCode().intValue()) {

                List<Error> errors = ErrorsParser.readErrosXml(response.getErrorStream());

                PagSeguroServiceException exception = new PagSeguroServiceException(httpCodeStatus, errors);

                AuthorizationService.log.error(String.format("AuthorizationService.Register( %1s ) - error %2s",
                        authorizationRequest.toString(), exception.getMessage()));

                throw exception;

            } else {

                throw new PagSeguroServiceException(httpCodeStatus);
            }

        } catch (PagSeguroServiceException e) {
            throw e;
        } catch (Exception e) {

        	AuthorizationService.log.error(String.format("AuthorizationService.Register( %1s ) - error %2s", authorizationRequest.toString(),
                    e.getMessage()));

            throw new PagSeguroServiceException(httpCodeStatus, e);

        } finally {
            response.disconnect();
        }
    }

}
