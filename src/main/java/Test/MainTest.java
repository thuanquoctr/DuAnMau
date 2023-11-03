/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Test;

import javax.swing.UIManager;
import view.JDialogXinChao;
import view.Login;

/**
 *
 * @author DELL
 */
public class MainTest {

    public static void main(String[] args) {
        openChao();
        openLogin();
    }

    static void openChao() {
        new JDialogXinChao(null, true).setVisible(true);
    }

    static void openLogin() {
        try {
            UIManager.setLookAndFeel(UIManager.getLookAndFeel());
            new Login().setVisible(true);
        } catch (Exception e) {
        }
    }
}
