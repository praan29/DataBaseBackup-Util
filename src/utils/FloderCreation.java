package utils;

import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;
/*@author Administrator*/

public class FloderCreation {

    private int ch;
    private int wo = 0;
    private Runtime rt = null;
    private File mainDir = null;
    private File subDir1 = null;
    private Process child = null;
    private PrintStream ps = null;
    private File sqlDumpFile = null;
    private InputStream inStream = null;
    private Date sysDate = new Date();
    utillsTo uTo = new utillsTo();
    private SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy");
    private String project_path = new java.io.File("").getAbsolutePath();

    public void createFloder() {
        try {
            //System.out.println(project_path);
            mainDir = new File(uTo.getLocalDir());
            if (!(mainDir.exists())) {
                if (mainDir.mkdir()) {
                    genSubDirectories();
                } else {
                    System.out.println("Oops!,Failed To Create DumpFloder..");
                }
            } else {
                genSubDirectories();
                String dumpFile2 = sdf.format(sysDate) + "Dump" + "." + "sql";
                for (File subDirList : mainDir.listFiles()) {
                    if (subDirList.isDirectory()) {
                        if (subDirList.list().length > 0) {
                            for (File subDirFiles : subDirList.listFiles()) {
                                if (subDirFiles.isFile()) {
                                    if (subDirFiles.getName().equals(dumpFile2)) {
                                        subDirFiles.delete();
                                        wo = 1;
                                        genDumpFile();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println("Exception  :  " + ex);
        }
    }

    public void genSubDirectories() {
        String subDirectoryPath = mainDir.getPath() + "\\" + sdf.format(sysDate);
        subDir1 = new File(subDirectoryPath);
        if (!(subDir1.exists())) {
            if (subDir1.mkdir()) {
                genDumpFile();
            } else {
                System.out.println("Oops!,Failed To Create SubFloder..");
            }
        }
    }

    public void genDumpFile() {
        try {
            String dumpFile = null;
            mainDir = new File(uTo.getLocalDir());
            String subDirectoryPath = mainDir.getPath() + "\\" + sdf.format(sysDate);
            String dumpCommand = "mysqldump " + uTo.getDatabaseName() + " -h " + uTo.getDatabaseHost() + " -u " + uTo.getDatabaseUserName() + " -p" + uTo.getDatabasePassword();
            rt = Runtime.getRuntime();
            if (wo != 1) {
                dumpFile = subDir1.getPath() + "\\" + sdf.format(sysDate) + "Dump" + "." + "sql";
            } else if (wo == 1) {
                dumpFile = subDirectoryPath + "\\" + sdf.format(sysDate) + "Dump" + "." + "sql";
            }
            sqlDumpFile = new File(dumpFile);
            if (!(sqlDumpFile.exists())) {
                sqlDumpFile.createNewFile();
                child = rt.exec(dumpCommand);
                ps = new PrintStream(dumpFile);
                inStream = child.getInputStream();
                while ((ch = inStream.read()) != -1) {
                    ps.write(ch);
                }
                InputStream err = child.getErrorStream();
                while ((ch = err.read()) != -1) {

                }
            }
        } catch (Exception ex) {
            System.out.println("Exception in Creating Dump File");
        }
    }
}
