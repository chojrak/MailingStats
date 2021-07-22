package Model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;

public class Emails {
    HashSet<String> mails;
    String path;

    public Emails(String path) {
        this.path = path;
        importMailsSM();
    }

    public void importMailsSM() {
        try {
            FileInputStream fis = new FileInputStream(new File(this.path));
            Scanner scn = new Scanner(fis, "UTF-8");
            HashSet<String> mails = new HashSet<>();
            while (scn.hasNextLine()) {
                LinkedList<String> line = new LinkedList<>(Arrays.asList(scn.nextLine().split(";")));
                mails.add(line.get(0));
            }
            this.mails = mails;
        } catch (FileNotFoundException e) {
        }
    }

    public HashSet<String> getMails() {
        return mails;
    }

    public String getPath() {
        return path;
    }

}


