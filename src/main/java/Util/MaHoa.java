/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;

import java.security.MessageDigest;
import java.util.Base64;

/**
 *
 * @author DELL
 */
public class MaHoa {

    public static String MaHoaMK(String str) {
        String salt = "fjndjfndjnfjbfhbhbf;ebfhdbdcbjfbdj";
        String result = null;
        str = str + salt;
        try {
            byte[] dt = str.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            result = Base64.getEncoder().encodeToString(md.digest(dt));
        } catch (Exception e) {
        }
        return result;
    }
}
