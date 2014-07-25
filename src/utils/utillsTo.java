package utils;

import java.util.ResourceBundle;

public class utillsTo {

    ResourceBundle rb = ResourceBundle.getBundle("com.properties/utilParameter");

    public String getDatabaseHost() {
        return rb.getString("dataBase.ip");
    }

    public String getDatabasePortNo() {
        return rb.getString("dataBase.portNo");
    }

    public String getDatabaseUserName() {
        return rb.getString("dataBase.userName");
    }

    public String getDatabasePassword() {
        return rb.getString("dataBase.password");
    }

    public String getDatabaseName() {
        return rb.getString("dataBase.dbName");
    }

    public String getLocalDir() {
        return rb.getString("dataBase.dumpDir");
    }
}
