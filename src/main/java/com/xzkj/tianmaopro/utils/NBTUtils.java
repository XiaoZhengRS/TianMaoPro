package com.xzkj.tianmaopro.utils;


import java.io.*;

public class NBTUtils {

    //加密

    public static String NBTtoBase54(Object data) {
        return new sun.misc.BASE64Encoder().encode(toByteArray(data));
    }
    //解密

    public static Object NBTtoObject(String Base64) throws IOException {
        sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
        return toObject(decoder.decodeBuffer(Base64));
    }

    public static Object toObject(byte[] bytes) {
        Object obj = null;

        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);

            ObjectInputStream ois = new ObjectInputStream(bis);
            obj = ois.readObject();
            ois.close();
            bis.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return obj;
    }

    public static byte[] toByteArray(Object obj) {
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray();
            oos.close();
            bos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return bytes;
    }

}
