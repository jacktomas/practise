package com.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by root on 17-3-23.
 */
public class TableCreator {
    public static void main(String[] args) throws ClassNotFoundException {
        Class<?> cl = Class.forName("com.annotation.Member");
        DBTable dbTable = cl.getAnnotation(DBTable.class);


        String tablename = dbTable.name();

        if (tablename.length() < 1) {
            tablename = cl.getName().toUpperCase();
        }

        ArrayList<String> columnDefs = new ArrayList<String>();
        for (Field field : cl.getDeclaredFields()) {
            String columnName = null;
            Annotation[] anns = field.getDeclaredAnnotations();
            if (anns.length < 1)
                continue;

            if (anns[0] instanceof SQLInteger) {
                SQLInteger sint = (SQLInteger) anns[0];

                if (sint.name().length() < 1) {
                    columnName = field.getName().toUpperCase();
                } else
                    columnName = sint.name();
                columnDefs.add(columnName + " INT" + getContraints(sint.constraints()));
            }

            if (anns[0] instanceof SQLString) {
                SQLString sString = (SQLString) anns[0];
                if (sString.name().length() < 1) {
                    columnName = field.getName().toUpperCase();
                } else
                    columnName = sString.name();
                columnDefs.add(columnName + " VARCHAR(" + sString.value() + ")" + getContraints(sString.constraints()));
            }
            StringBuilder createCommand = new StringBuilder("create table " + tablename + "(");
            for (String columnDef : columnDefs) {
                createCommand.append("\n    " + columnDef + ",");
            }
            String tableCreate = createCommand.substring(0, Integer.parseInt(String.valueOf(createCommand.length() - 1))) + ");";

            System.out.println("Table creation sql for " + " com.annotation.Member" + " is  :\n " + tableCreate);


        }
    }

    private static String getContraints(Constraints con) {
        String constraints = "";
        if (!con.allowNull()) {
            constraints += " NOT NULL";
        }
        if (con.primaryKey()) {
            constraints += " primary key";
        }
        if (con.unique()) {
            constraints += " UNIQUE";
        }
        return constraints;
    }
}
