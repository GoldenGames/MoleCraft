package me.mani.molecraft.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class NickFetcher {

    public static String getRandomName() {
        Socket socket = null;
        DataOutputStream dos = null;
        DataInputStream dis = null;

        Object name;
        try {
            socket = new Socket("89.163.225.13", 1919);
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
            dos.writeUTF("4jfnfc9h>>/(>");
            dos.writeUTF("nicknames");
            dos.writeUTF("getNickname");
            dos.flush();
            return dis.readUTF();
        } catch (Exception var14) {
            var14.printStackTrace();
            name = null;
        } finally {
            try {
                socket.close();
                dos.close();
                dis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return (String) name;
    }

    public static boolean hasAutoNick(String name) {
        Socket socket = null;
        DataOutputStream dos = null;
        DataInputStream dis = null;

        boolean ex;
        try {
            socket = new Socket("89.163.225.13", 1919);
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
            dos.writeUTF("4jfnfc9h>>/(>");
            dos.writeUTF("nicknames");
            dos.writeUTF("hasAutoNick");
            dos.writeUTF(name);
            dos.flush();
            boolean e = Boolean.parseBoolean(dis.readUTF());
            return e;
        } catch (Exception var15) {
            var15.printStackTrace();
            ex = false;
        } finally {
            try {
                socket.close();
                dos.close();
                dis.close();
            } catch (Exception var14) {
                ;
            }

        }

        return ex;
    }

    public static void updateLog(String from, String to, String action) {
        Socket socket = null;
        DataOutputStream dos = null;

        label83: {
            try {
                socket = new Socket("89.163.225.13", 1919);
                dos = new DataOutputStream(socket.getOutputStream());
                dos.writeUTF("4jfnfc9h>>/(>");
                dos.writeUTF("nicknames");
                dos.writeUTF("updateLog");
                dos.writeUTF(from);
                dos.writeUTF(to);
                dos.writeUTF(action);
                dos.flush();
                break label83;
            } catch (Exception var19) {
                var19.printStackTrace();

                try {
                    socket.close();
                    dos.close();
                } catch (IOException var17) {
                    var17.printStackTrace();
                }
            } finally {
                try {
                    socket.close();
                    dos.close();
                } catch (IOException var16) {
                    var16.printStackTrace();
                }

            }

            return;
        }

        try {
            socket.close();
            dos.close();
        } catch (IOException var18) {
            var18.printStackTrace();
        }

    }
}