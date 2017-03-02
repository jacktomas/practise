package waitnotify.testnotify3114;

/**
 * Created by root on 17-3-2.
 */
public class BackupB extends Thread {
    private DBTools dbTools;

    public BackupB(DBTools dbTools) {
        this.dbTools = dbTools;
    }

    @Override
    public void run() {
        dbTools.backupB();
    }
}
