package com.dasther.horometro.communication;

public interface PlcCommunication {

    boolean connect(String ip, int rack, int slot);
    
    void disconnect();
    
    boolean isConnected();

    Boolean readBit(int dbNumber, int byteIndex, int bitIndex);
    
    boolean writeBit(int dbNumber, int byteIndex, int bitIndex, boolean value);

    byte[] readBytes(int dbNumber, int start, int count);
    
    boolean writeBytes(int dbNumber, int start, byte[] data);

}
