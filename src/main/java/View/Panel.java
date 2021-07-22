package View;

import Model.Emails;
import Model.FileConverter;
import Model.Tasks;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Panel extends JPanel {

    JFrame ramka;
    JLabel all;
    JLabel kliknieto;
    JLabel otwarto;
    JLabel wyslano;
    JLabel kliknietoOferta;
    JLabel kliknietoPos;
    JTextField cade;
    JTextField creaDt;
    JTextArea msg;

    public Panel(JFrame ramka) {
        this.ramka = ramka;
        setLayout(null);


        all = addJLabel(45, 20, 535, 20, "Maile ALL TAG");
        add(all);
        add(addFileChooser(20, 20, 20, 20, all));

        otwarto = addJLabel(45, 50, 535, 20, "Maile Otwarto");
        add(otwarto);
        add(addFileChooser(20, 50, 20, 20, otwarto));

        wyslano = addJLabel(45, 80, 535, 20, "Maile Wyslano");
        add(wyslano);
        add(addFileChooser(20, 80, 20, 20, wyslano));

        kliknieto = addJLabel(45, 110, 535, 20, "Maile Kliknieto");
        add(kliknieto);
        add(addFileChooser(20, 110, 20, 20, kliknieto));

        kliknietoOferta = addJLabel(45, 140, 535, 20, "Maile Kliknieto - Oferta (opcjonalnie)");
        add(kliknietoOferta);
        add(addFileChooser(20, 140, 20, 20, kliknietoOferta));

        kliknietoPos = addJLabel(45, 170, 535, 20, "Maile Kliknieto POS (opcjonalnie)");
        add(kliknietoPos);
        add(addFileChooser(20, 170, 20, 20, kliknietoPos));

        add(addJLabel(20, 200, 200, 30, "cade_id rozdzielone przecinkami:"));
        cade = new JTextField();
        cade.setBounds(225, 200, 355, 30);
        add(cade);

        add(addJLabel(20, 230, 200, 30, "task_crea_dt w formacie YYYY-MM-DD:"));
        creaDt = new JTextField();
        creaDt.setBounds(225, 230, 355, 30);
        add(creaDt);

        JButton magic = new JButton("Do the magic");
        magic.setBounds(20, 275, 560, 60);
        magic.setBackground(Color.PINK);
        magic.addActionListener(new Parse(this));
        add(magic);

        msg = new JTextArea();
        msg.setBounds(20, 338, 560, 60);
        msg.setLineWrap(true);
        add(msg);

    }

    public JButton addFileChooser(int x, int y, int width, int height, JLabel jl) {
        JButton jb = new JButton(UIManager.getIcon("FileView.directoryIcon"));
        jb.setBounds(x, y, width, height);
        jb.addActionListener(new ChooseFile(jl));
        return jb;
    }

    public JLabel addJLabel(int x, int y, int width, int height, String text) {
        JLabel jl = new JLabel(text);
        jl.setBounds(x, y, width, height);
        jl.setFont(new Font("Calibri", Font.PLAIN, 12));
        return jl;
    }

    public void setMsg(String s) {
        this.msg.setText(s);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(600, 400);
    }

    class ChooseFile implements ActionListener {
        JLabel jl;

        ChooseFile(JLabel jl) {
            this.jl = jl;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser jf = new JFileChooser();
            jf.setCurrentDirectory(new File(System.getProperty("user.dir")));
            int r = jf.showOpenDialog(null);
            if (r == JFileChooser.APPROVE_OPTION) {
                jl.setText(jf.getSelectedFile().getAbsolutePath());
                // msg.setText("gotów");
            }
        }
    }

    class Parse implements ActionListener {
        Panel panel;

        Parse(Panel panel) {
            this.panel = panel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            if (all.getText().equals("Maile ALL TAG"))
                msg.setText("Dodaj plik ze wszystkimi mailami załączonymi do Sales Manago pod danym TAGiem");
            else if (otwarto.getText().equals("Maile Otwarto"))
                msg.setText("Dodaj plik z mailami, które otworzyły wiadomość");
            else if (wyslano.getText().equals("Maile Wyslano"))
                msg.setText("Dodaj plik z mailami, do których została wysłana wiadomość");
            else if (kliknieto.getText().equals("Maile Kliknieto"))
                msg.setText("Dodaj plik z mailami, które cośtam poklikały");
            else if (!cade.getText().isEmpty() && !creaDt.getText().isEmpty() &&
                    !kliknietoOferta.getText().equals("Maile Kliknieto - Oferta (opcjonalnie)") &&
                    !kliknietoPos.getText().equals("Maile Kliknieto POS (opcjonalnie)")) {
                panel.setMsg("pobieram dane z SQL");
                FileConverter fc = new FileConverter(new Emails(all.getText()),
                        new Emails(otwarto.getText()),
                        new Emails(wyslano.getText()),
                        new Emails(kliknieto.getText()),
                        new Emails(kliknietoOferta.getText()),
                        new Emails(kliknietoPos.getText()),
                        new Tasks(cade.getText(), creaDt.getText(), panel), panel);
                fc.prepareFiles();
                fc.exportFile();
                panel.setMsg("done");
            } else if (!cade.getText().isEmpty() && !creaDt.getText().isEmpty()) {
                panel.setMsg("pobieram dane z SQL");
                FileConverter fc = new FileConverter(new Emails(all.getText()),
                        new Emails(otwarto.getText()),
                        new Emails(wyslano.getText()),
                        new Emails(kliknieto.getText()),
                        new Tasks(cade.getText(), creaDt.getText(), panel), panel);
                fc.prepareFiles();
                fc.exportFile();
                panel.setMsg("done");
            }


        }
    }
}
