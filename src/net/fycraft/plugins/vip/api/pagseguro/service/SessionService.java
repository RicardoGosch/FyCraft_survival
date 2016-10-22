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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import net.fycraft.plugins.vip.api.pagseguro.domain.Credentials;
import net.fycraft.plugins.vip.api.pagseguro.domain.Error;
import net.fycraft.plugins.vip.api.pagseguro.enums.HttpStatus;
import net.fycraft.plugins.vip.api.pagseguro.exception.PagSeguroServiceException;
import net.fycraft.plugins.vip.api.pagseguro.logs.Log;
import net.fycraft.plugins.vip.api.pagseguro.utils.HttpConnection;
import net.fycraft.plugins.vip.api.pagseguro.xmlparser.ErrorsParser;
import net.fycraft.plugins.vip.api.pagseguro.xmlparser.XMLParserUtils;

/**
 * Class Session Service
 */
public class SessionService {

    /**
     * @var Log
     */
    private static Log log = new Log(SessionService.class);

    private static String buildSessionsRequestUrl(ConnectionData connectionData) //
            throws PagSeguroServiceException {
        return connectionData.getSessionsUrl();
    }

    public static String createSession(Credentials credentials) //
            throws PagSeguroServiceException {
        log.info("SessionService.createSession() - begin");

        ConnectionData connectionData = new ConnectionData(credentials);

        String url = SessionService.buildSessionsRequestUrl(connectionData);

        HttpConnection connection = new HttpConnection();
        HttpStatus httpCodeStatus = null;

        HttpURLConnection response = connection.post(url, //
                credentials.getAttributes(), //
                connectionData.getServiceTimeout(), //
                connectionData.getCharset(),
                null);

        try {
            httpCodeStatus = HttpStatus.fromCode(response.getResponseCode());

            if (httpCodeStatus == null) {
                throw new PagSeguroServiceException("Connection Timeout");
            } else if (HttpURLConnection.HTTP_OK == httpCodeStatus.getCode().intValue()) {
                DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

                InputSource inputSource = new InputSource(response.getInputStream());
                Document document = documentBuilder.parse(inputSource);

                Element element = document.getDocumentElement();

                // setting <session><id>
                String sessionId = XMLParserUtils.getTagValue("id", element);

                log.info("SessionService.createSession() - end");

                return sessionId;
            } else if (HttpURLConnection.HTTP_BAD_REQUEST == httpCodeStatus.getCode().intValue()) {
                List<Error> errors = ErrorsParser.readErrosXml(response.getErrorStream());

                PagSeguroServiceException exception = new PagSeguroServiceException(httpCodeStatus, errors);

                log.error(String.format("SessionService.createSession() - error %s", //
                        exception.getMessage()));

                throw exception;
            } else if (HttpURLConnection.HTTP_UNAUTHORIZED == httpCodeStatus.getCode().intValue()) {
                PagSeguroServiceException exception = new PagSeguroServiceException(httpCodeStatus);

                log.error(String.format("SessionService.createSession() - error %s", //
                        exception.getMessage()));

                throw exception;
            } else {
                throw new PagSeguroServiceException(httpCodeStatus);
            }
        } catch (PagSeguroServiceException e) {
            throw e;
        } catch (Exception e) {
            log.error(String.format("SessionService.createSession() - error %s", //
                    e.getMessage()));

            throw new PagSeguroServiceException(httpCodeStatus, e);
        } finally {
            response.disconnect();
        }
    }

}
