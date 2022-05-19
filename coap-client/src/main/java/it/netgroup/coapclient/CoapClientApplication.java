package it.netgroup.coapclient;

import it.unipr.netsec.mjcoap.coap.client.CoapClient;
import it.unipr.netsec.mjcoap.coap.client.CoapResponseHandler;
import it.unipr.netsec.mjcoap.coap.message.CoapMessage;
import it.unipr.netsec.mjcoap.coap.message.CoapRequest;
import it.unipr.netsec.mjcoap.coap.message.CoapRequestMethod;
import it.unipr.netsec.mjcoap.coap.message.CoapResponse;
import it.unipr.netsec.mjcoap.coap.provider.CoapURI;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.zoolu.util.SystemUtils;

import java.net.SocketException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

@SpringBootApplication
public class CoapClientApplication implements CommandLineRunner{

    public static void main(String[] args){
        SpringApplication.run(CoapClientApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        CoapClient client = new CoapClient();
        CoapURI resource_uri = new CoapURI(args.length > 0 ? args[0] : "coap://127.0.0.1/test");

        Scanner s = new Scanner(System.in);
        System.out.println("Invia un messaggio al server");
        String message = s.nextLine();

        while(!message.equalsIgnoreCase("exit")){
            client.request(CoapRequestMethod.GET, resource_uri, 1, message.getBytes(), new CoapResponseHandler() {
                @Override
                public void onResponse(CoapRequest coapRequest, CoapResponse coapResponse) {
                    System.out.println("Response: "+new String(coapResponse.getPayload()));
                }

                @Override
                public void onRequestFailure(CoapRequest coapRequest) {

                }
            });
            System.out.println("Invia un messaggio al server");
            message = s.nextLine();
        }

        client.halt();
    }


}
