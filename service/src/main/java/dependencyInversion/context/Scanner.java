package dependencyInversion.context;

import java.io.IOException;

/**
 * Created by anakasimova on 03/08/2018.
 */
public interface Scanner {

    void scanFrom(ClassLoader classloader) throws IOException;
}
