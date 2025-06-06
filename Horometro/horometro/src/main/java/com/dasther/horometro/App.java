package com.dasther.horometro;

import com.dasther.horometro.communication.Moka7Plc;
import com.dasther.horometro.communication.PlcCommunication;
import com.dasther.horometro.service.PlcScannerService;

public class App {
    public static void main(String[] args) {
        PlcCommunication plc = new Moka7Plc();
        PlcScannerService polling = new PlcScannerService(plc, 1000); // lee cada 1 segundo
        Thread pollingThread = new Thread(polling);
        pollingThread.start();
    }
}
