package com.dasther.horometro.service;

import com.dasther.horometro.communication.PlcCommunication;

public class PlcScannerService implements Runnable {

    private final PlcCommunication plc;
    private volatile boolean running = true;
    private final int pollIntervalMs;

    public PlcScannerService(PlcCommunication plc, int pollIntervalMs) {
        this.plc = plc;
        this.pollIntervalMs = pollIntervalMs;
    }

    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            try {
                if (!plc.isConnected()) {

                    plc.connect("192.168.0.1", 0, 2);
                }
                
                byte[] dbData = plc.readBytes(1, 0, 10);

                // Aquí actualizas un objeto observable, logueas, o actualizas la UI (ver nota
                // abajo)
                // Por ejemplo:
                System.out.println("DB1[0..9]: " + java.util.Arrays.toString(dbData));

                Thread.sleep(pollIntervalMs);
            } catch (Exception ex) {
                ex.printStackTrace();
                // Si quieres: loguea el error
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    /* Ignore */ }
            }
        }
        plc.disconnect();
    }
}


