package Model;

import View.Panel;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class FileConverter {
    HashMap<String, String> output;
    HashMap<String, String> statuses;
    Emails all, otwarto, kliknieto, wyslano, kliknietoOferta, kliknietoPOS;
    Tasks task;
    String path;
    Panel p;

    public FileConverter(Emails all, Emails otwarto, Emails wyslano, Emails kliknieto, Tasks task, Panel p) {
        this(all, otwarto, wyslano, kliknieto, null, null, task, p);
    }

    public FileConverter(Emails all, Emails otwarto, Emails wyslano, Emails kliknieto, Emails kliknietoOferta, Emails kliknietoPOS, Tasks task, Panel p) {
        this.all = all;
        this.otwarto = otwarto;
        this.kliknieto = kliknieto;
        this.wyslano = wyslano;
        this.task = task;
        this.kliknietoOferta = kliknietoOferta;
        this.kliknietoPOS = kliknietoPOS;
        this.p = p;
        this.path = all.getPath().substring(0, all.getPath().lastIndexOf('\\')) + "\\CVMM_" + getTimestamp() + "0";
        output = new HashMap<String, String>();
        statuses = new HashMap<String, String>();
    }


    public void prepareFiles() {
        HashMap<String, String> hm = new HashMap<>();

        setStatus("Nie wys³ano");
        setStatus(all, "Brak kontaktu");
        setStatus(wyslano, "Wys³ano");
        setStatus(otwarto, "Otwarto");
        setStatus(kliknieto, "Klikniêto");
        if (kliknietoPOS != null) setStatus(kliknietoPOS, "Klikniêto - POS");
        if (kliknietoOferta != null) setStatus(kliknietoOferta, "Klikniêto - Oferta");

    }

    public void setStatus(String status) {
        for (String t : task.getTasks().keySet())
            output.put(t, status);
    }

    public void setStatus(Emails m, String status) {
        for (String s : m.getMails())
            if (task.getMails().containsKey(s))
                for (String t : task.getMails().get(s).getTaskBusiIds()) output.put(t, status);
    }

    public void exportFile() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        try {

            int i = 0;
            int j = 0;
            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(new File(this.path + i + ".txt")), "windows-1250");
            PrintWriter pw = new PrintWriter(osw);

            for (HashMap.Entry<String, String> entry : output.entrySet()) {
                j++;
                if (entry.getValue().equals("Klikniêto") || entry.getValue().equals("Otwarto") ||
                        entry.getValue().equals("Klikniêto - Oferta") || entry.getValue().equals("Klikniêto - POS")) {
                    String s = entry.getKey() + ";Pozytywny;;" + entry.getValue() + ";;" + timestamp + ";;;chacinr;;;;;;;;;;\r\n";
                    pw.write(s);
                } else if (entry.getValue().equals("Wys³ano")) {
                    String s = entry.getKey() + ";Negatywny;Wys³ano;;;" + timestamp + ";;;chacinr;;;;;;;;;;\r\n";
                    pw.write(s);
                } else {
                    String s = entry.getKey() + ";" + entry.getValue() + ";;;;" + timestamp + ";;;chacinr;;;;;;;;;;\r\n";
                    pw.write(s);
                }
                if (j == 75000) {
                    j = 0;
                    i++;
                    pw.close();
                    osw = new OutputStreamWriter(new FileOutputStream(new File(this.path + i + ".txt")), "windows-1250");
                    pw = new PrintWriter(osw);
                }
            }
            pw.close();

        } catch (UnsupportedEncodingException e) {
            p.setMsg(e.getMessage());
        } catch (FileNotFoundException e) {
            p.setMsg(e.getMessage());
        }
    }


    public static String getTimestamp() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmm"));
        return timestamp;
    }


}

