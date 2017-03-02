package waitnotify.testnotify3114;

/**
 * Created by root on 17-3-2.
 */
public class BackupA extends Thread {
    private DBTools dbTools;

    public BackupA(DBTools dbTools) {
        this.dbTools = dbTools;
    }

    @Override
    public void run() {
        dbTools.backupA();
    }
}
