package dependencyInversion.context;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by anakasimova on 13/07/2018.
 */
public class ClassPathAnnotationScanner implements Scanner{

    private List<String> classes = new ArrayList<>();

    public List<String> getClasses() {
        return classes;
    }

    @Override
    public void scanFrom(ClassLoader classloader) throws IOException {
        for (Map.Entry<File, ClassLoader> entry : getClasspathEntries(classloader).entrySet()) {
            if(entry.getKey().isDirectory()) {
                scan(entry.getKey(), entry.getValue(), "");
            }
        }
    }

    private  Map<File, ClassLoader> getClasspathEntries(ClassLoader classloader){
        Map<File, ClassLoader> entries = new HashMap<>();
        // Search parent first, since it's the order ClassLoader#loadClass() uses.
        ClassLoader parent = classloader.getParent();
        if (parent != null) {
            entries.putAll(getClasspathEntries(parent));
        }
        if (classloader instanceof URLClassLoader) {
            URLClassLoader urlClassLoader = (URLClassLoader) classloader;
            for (URL entry : urlClassLoader.getURLs()) {
                if (entry.getProtocol().equals("file")) {
                    File file = new File(entry.getFile());
                    if (!entries.containsKey(file)) {
                        entries.put(file, classloader);
                    }
                }
            }
        }
        return entries;
    }

    private void scan(
            File directory, ClassLoader classloader, String packagePrefix) throws IOException {
        File[] files = directory.listFiles();
        if (files == null) {
            System.out.println("cannot find directory");
            return;
        }
        for (File f : files) {
            String name = f.getName();
            if (f.isDirectory()) {  //if files which we found are also package
                scan(f, classloader, packagePrefix + name + "/");
            } else {
                classes.add((packagePrefix + name.substring(0, name.length()-6)).replace("/", "."));

            }
        }
        return;
    }
}
