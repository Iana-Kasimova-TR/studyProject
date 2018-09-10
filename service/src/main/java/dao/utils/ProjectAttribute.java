package dao.utils;

import java.sql.Types;

/**
 * Created by anakasimova on 09/09/2018.
 */
public enum ProjectAttribute {
    ID(Types.INTEGER),
    TITLE(Types.CHAR),
    DESCRIPTION(Types.CHAR),
    IS_DELETED(Types.BOOLEAN);

    public int typeOfAttribute;


    ProjectAttribute(int typeOfAttribute) {
        this.typeOfAttribute = typeOfAttribute;
    }

}
