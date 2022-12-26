package com.epam.mjc.nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


public class FileReader {

    private Profile tryToInstantiateProfile(String[] info){
        try {
            return new Profile(info[0], Integer.parseInt(info[1]), info[2], Long.parseLong(info[3]));
        }
        catch (NumberFormatException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    private String read(File file) throws FileNotFoundException {
        StringBuilder result = new StringBuilder();
        try (RandomAccessFile accessFile = new RandomAccessFile(file, "rw"); FileChannel inChannel = accessFile.getChannel())
        {
            long size = inChannel.size();
            ByteBuffer buffer = ByteBuffer.allocate((int)size);
            inChannel.read(buffer);
            buffer.flip();
            for (byte bits : buffer.array()) {
                result.append((char)bits);
            }
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }

        return result.toString();
    }

    public Profile getDataFromFile(File file) {
        try {
            String text = read(file);
        String[] lines = text.split("\n");
        String[] info = new String[4];
        int ind = 0;
        for (String line : lines) {
            for (int i = 0; i < line.length(); i++) {
                if (line.charAt(i) == ':') {
                    info[ind++] = line.substring(i+1).trim();
                }
            }
        }
        return tryToInstantiateProfile(info);
        }
        catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
}
