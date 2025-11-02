/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.prakpbo.main;

/**
 *
 * @author asus
 */
public class Main 
{
    public static void main(String[] args) {
        MainJFrame frame = new MainJFrame();
        frame.changeMainPanel(new TambahDataPanel());
        frame.setVisible(true);
    }
}