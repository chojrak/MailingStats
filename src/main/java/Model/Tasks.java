package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class Tasks {
    LinkedList<Task> tasks;
    String cade, creaDt;

    public Tasks(String cade, String creaDt) {
        this.cade = cade;
        this.creaDt = creaDt;
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
            sb.append("'\n");
            if (i < 5) sb.append("union ");
        }

        ResultSet rs = DBConn.Execute(sb.toString());
        try {
            while (rs.next()) tasks.add(new Task(rs.getString("task_busi_id"), rs.getString("task_emai_tx")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public LinkedList<Task> getTasks() {
        return tasks;
    }
}
