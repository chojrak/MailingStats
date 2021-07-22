package Model;

import View.Panel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class Tasks {
    HashMap<String, Task> tasks;
    HashMap<String, Mail> emails;
    String cade, creaDt;
    Panel panel;

    public Tasks(String cade, String creaDt, Panel panel) {
        this.cade = cade;
        this.creaDt = creaDt;
        tasks = new HashMap<String, Task>();
        emails = new HashMap<String, Mail>();
        this.panel = panel;
        importTasks();
    }

    public void importTasks() {
        StringBuilder sb = new StringBuilder();
        for (int i = 2; i <= 5; i++) {
            sb.append("select task.task_busi_id, task.task_sour_att");
            sb.append(i);
            sb.append("_tx task_emai_tx \n");
            sb.append("from dss_acr.odm_tasks task where task.cade_id in (");
            sb.append(cade);
            sb.append(") and task.task_clos_dt is null \n");
            sb.append("and trunc(task.task_crea_dt, 'dd') = '");
            sb.append(creaDt);
            sb.append("'\n and task.task_sour_att");
            sb.append(i);
            sb.append("_tx is not null \n");
            if (i < 5) sb.append("union ");
        }
        try {
            ResultSet rs = DBConn.Execute(sb.toString());
            while (rs.next()) {
                if (tasks.containsKey(rs.getString("task_busi_id")))
                    tasks.get(rs.getString("task_busi_id")).addEmail(rs.getString("task_emai_tx"));
                else
                    tasks.put(rs.getString("task_busi_id"), new Task(rs.getString("task_busi_id"), rs.getString("task_emai_tx")));

                if (emails.containsKey(rs.getString("task_emai_tx")))
                    emails.get(rs.getString("task_emai_tx")).addTaskBusiId(rs.getString("task_busi_id"));
                else
                    emails.put(rs.getString("task_emai_tx"), new Mail(rs.getString("task_emai_tx"), rs.getString("task_busi_id")));
            }
        } catch (SQLException e) {
            panel.setMsg(e.getMessage());
        } catch (NullPointerException e) {
            panel.setMsg("Brak danych z SQL");
        }

    }

    public HashMap<String, Task> getTasks() {
        return tasks;
    }

    public HashMap<String, Mail> getMails() {
        return emails;
    }

}
