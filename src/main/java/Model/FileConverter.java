package Model;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;

public class FileConverter {
    HashMap<String, String> output;
    Emails all, otwarto, kliknieto, wyslano;
    Tasks task;
    String path;

    public FileConverter(Emails all, Emails otwarto, Emails wyslano, Emails kliknieto, Tasks task) {
        this.all = all;
        this.otwarto = otwarto;
        this.kliknieto = kliknieto;
        this.wyslano = wyslano;
        this.task = task;
    }

    public void prepareFiles() {
        HashMap<String, String> hm = new HashMap<>();

        setStatus("Nie wys³ano");
        setStatus(all, "Brak kontaktu");
        setStatus(wyslano, "Wys³ano");
        setStatus(otwarto, "Otwarto");
        setStatus(kliknieto, "Klikniêto");
    }

    public void setStatus(String status) {
        for (Task t : task.getTasks())
            output.put(t.taskBusiId, status);
    }

    public void setStatus(Emails m, String status) {
        for (Task t : task.getTasks())
            for (String s : m.getMails())
                if (t.getEmail().equals(s)) output.put(t.getTaskBusiId(), status);
    }

    public void exportFile() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        try {
            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(new File(this.path)), "windows-1250");
            PrintWriter pw = new PrintWriter(osw);
            for (HashMap.Entry<String, String> entry : output.entrySet()) {
                if (entry.getValue().equals("Klikniêto") || entry.getValue().equals("Wys³ano")) {
                    String s = entry.getKey() + ";Pozytywny;;" + entry.getValue() + timestamp + ";;;chacinr;;;;;;;;;;";
                    pw.write(s);
                } else if (entry.getValue().equals("Wys³ano")) {
                    String s = entry.getKey() + ";Negatywny;Wys³ano;;;" + timestamp + ";;;chacinr;;;;;;;;;;";
                    pw.write(s);
                } else {
                    String s = entry.getKey() + ";" + entry.getValue() + ";;;;" + timestamp + ";;;chacinr;;;;;;;;;;";
                    pw.write(s);
                }
                pw.close();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setPath(String path) {
        this.path = path;
    }

}
