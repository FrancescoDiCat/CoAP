package it.netgroup.coapclient;

import it.unipr.netsec.mjcoap.coap.client.CoapClient;
import it.unipr.netsec.mjcoap.coap.client.CoapResponseHandler;
import it.unipr.netsec.mjcoap.coap.message.CoapRequest;
import it.unipr.netsec.mjcoap.coap.message.CoapRequestMethod;
import it.unipr.netsec.mjcoap.coap.message.CoapResponse;
import it.unipr.netsec.mjcoap.coap.provider.CoapURI;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.SocketException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class ServiceCoapClient {
    CoapClient client = new CoapClient();
    CoapURI resource_uri = new CoapURI("coap://127.0.0.1/test");

    public ServiceCoapClient() throws SocketException, URISyntaxException {
    }

    @Scheduled(fixedRate = 3000)
    public void SendRequest(){
        Date date = new Date();
        SimpleDateFormat DateFor = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String stringDate= DateFor.format(date);
        client.request(CoapRequestMethod.GET, resource_uri, 1, stringDate.getBytes(), new CoapResponseHandler() {
            @Override
            public void onResponse(CoapRequest coapRequest, CoapResponse coapResponse) {
                System.out.println(coapResponse);
            }

            @Override
            public void onRequestFailure(CoapRequest coapRequest) {

            }
        });
    }
}
