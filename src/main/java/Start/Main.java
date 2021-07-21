package Start;


import View.Panel;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        JFrame okno = new JFrame("Statusy e-mail");
        okno.add(new Panel(okno));
        okno.setLocationByPlatform(true);
        okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        okno.setVisible(true);
        okno.pack();
    }
}
