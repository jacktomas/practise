package waitnotify.testnotify3114;

/**
 * Created by root on 17-3-2.
 */
public class Run {
    public static void main(String[] args) {
        DBTools dbTools = new DBTools();
        for (int i = 0; i < 20; i++) {
            BackupB backupB = new BackupB(dbTools);
            backupB.start();
            BackupA backupA = new BackupA(dbTools);
            backupA.start();
        }
    }
}
