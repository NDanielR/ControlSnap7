package com.dasther.horometro.communication;

import com.dasther.horometro.communication.moka7.S7;
import com.dasther.horometro.communication.moka7.S7Client;


public class Moka7Plc implements PlcCommunication {

    private S7Client client = new S7Client();

    @Override
    public boolean connect(String ip, int rack, int slot) {
        int result = client.ConnectTo(ip, rack, slot);
        return result == 0;
    }

    @Override
    public void disconnect() {
        client.Disconnect();
    }

    @Override
    public boolean isConnected() {
        return client.Connected == true;
    }

    @Override
    public Boolean readBit(int dbNumber, int byteIndex, int bitIndex) {
        byte[] buffer = new byte[1];
        int result = client.ReadArea(S7.S7AreaDB,dbNumber,byteIndex,1,buffer);
        if (result == 0) {
            int mask = 1 << bitIndex;
            return (buffer[0] & mask) != 0;
        }
        return null;
    }

    @Override
    public boolean writeBit(int dbNumber, int byteIndex, int bitIndex, boolean value) {
        byte[] buffer = new byte[1];
        // Leer el byte actual del DB usando ReadArea
        int result = client.ReadArea(S7.S7AreaDB, dbNumber, byteIndex, 1, buffer);
        if (result != 0)
            return false;

        int mask = 1 << bitIndex;
        if (value) {
            buffer[0] = (byte) (buffer[0] | mask); // Setear bit
        } else {
            buffer[0] = (byte) (buffer[0] & ~mask); // Limpiar bit
        }

        // Escribir el byte modificado usando WriteArea
        result = client.WriteArea(S7.S7AreaDB, dbNumber, byteIndex, 1, buffer);
        return result == 0;
    }

    @Override
    public byte[] readBytes(int dbNumber, int start, int count) {
        byte[] buffer = new byte[count];
        int result = client.ReadArea(S7.S7AreaDB, dbNumber, start, count, buffer);
        return (result == 0) ? buffer : null;
    }

    @Override
    public boolean writeBytes(int dbNumber, int start, byte[] data) {
        int result = client.WriteArea(S7.S7AreaDB,dbNumber,start,data.length,data);
        return result == 0;
    }

}
