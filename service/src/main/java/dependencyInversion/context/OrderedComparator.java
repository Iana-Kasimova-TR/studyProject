package dependencyInversion.context;

import dependencyInversion.definition.Definition;

/**
 * Created by anakasimova on 03/08/2018.
 */
public class OrderedComparator implements java.util.Comparator<Definition> {


    @Override
    public int compare(Definition o1, Definition o2) {
        return Integer.compare(o1.getOrder(), o2.getOrder());
    }
}
