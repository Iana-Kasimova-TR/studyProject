package dao.utils;

import java.sql.Types;


/**
 * Created by anakasimova on 09/09/2018.
 */
public enum TaskAttribute {
    ID(Types.INTEGER),
    TITLE(Types.CHAR),
    DEADLINE(Types.DATE),
    DESCRIPTION(Types.CHAR),
    IS_DONE(Types.BOOLEAN),
    REMIND_DATE(Types.DATE),
    PRIORITY(Types.CHAR),
    PERCENT_OF_READINESS(Types.DOUBLE),
    PARENT_TASK_ID(Types.INTEGER),
    PROJECT_ID(Types.INTEGER),
    IS_DELETED_FROM_PROJECT(Types.BOOLEAN),
    IS_DELETED(Types.BOOLEAN);



    public int typeOfAttribute;


    TaskAttribute(int typeOfAttribute) {
        this.typeOfAttribute = typeOfAttribute;
    }





}
