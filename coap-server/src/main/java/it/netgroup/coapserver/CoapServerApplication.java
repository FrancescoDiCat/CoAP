package it.netgroup.coapserver;

import it.unipr.netsec.mjcoap.coap.message.CoapMessageFactory;
import it.unipr.netsec.mjcoap.coap.message.CoapRequest;
import it.unipr.netsec.mjcoap.coap.message.CoapResponse;
import it.unipr.netsec.mjcoap.coap.message.CoapResponseCode;
import it.unipr.netsec.mjcoap.coap.server.AbstractCoapServer;
import it.unipr.netsec.mjcoap.coap.server.CoapResource;
import it.unipr.netsec.mjcoap.coap.server.CoapServer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Scheduled;

import java.lang.annotation.Repeatable;
import java.net.SocketException;
import java.util.Scanner;

@SpringBootApplication
public class CoapServerApplication implements CommandLineRunner{

    CoapRequest request = null;
    CoapServer server = null;


    public static void main(String[] args) throws SocketException {
        SpringApplication.run(CoapServerApplication.class, args);

    }

    void initServer() throws SocketException {
        this.server = new CoapServer() {
            @Override
            protected void handleGetRequest(CoapRequest req) {
                if (req.getRequestUriPath().equals("/test")) {
                    request = req;
                    System.out.println(new String(req.getPayload()));
                    CoapResponse resp = CoapMessageFactory.createResponse(req,CoapResponseCode._2_05_Content);
                    resp.setPayload(CoapResource.FORMAT_TEXT_PLAIN_UTF8,"Ricevuto".getBytes());
                    respond(req,resp);
                }

            }
        };
        server.setWriteMode(true);
    }

    @Override
    public void run(String... args) throws Exception {
        initServer();
    }
}
