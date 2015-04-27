package org.hope6537.note.design.mix.chain_command;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
public class ClassUtils {

    /**
     * 根据父类获取子类
     */
    @SuppressWarnings("unchecked")
    public static List<Class> getSonClass(Class fatherClass) {
        List<Class> returnClassList = new ArrayList<>();
        String packageName = fatherClass.getPackage().getName();
        List<Class> classes = getClasses(packageName);
        returnClassList.addAll(classes.stream().filter(c -> fatherClass.isAssignableFrom(c) && !fatherClass.equals(c)).collect(Collectors.toList()));
        return returnClassList;
    }

    /**
     * 从一个包中查找所有的类
     */
    public static List<Class> getClasses(String packageName) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace(".", "/");
        Enumeration<URL> resources = null;
        try {
            resources = classLoader.getResources(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<File> dirs = new ArrayList<>();
        if (resources != null) {
            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                dirs.add(new File(resource.getFile()));
            }
        }
        ArrayList<Class> classes = new ArrayList<>();
        for (File directory : dirs) {
            classes.addAll(findClass(directory, packageName));
        }
        return classes;
    }

    /**
     * 通过类名获取类对象
     */
    private static List<Class> findClass(File directory, String packageName) {
        List<Class> classes = new ArrayList<>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (!file.isDirectory()) {
                    assert !file.getName().contains(".");
                    classes.addAll(findClass(file, packageName + "." + file.getName()));
                } else if (file.getName().endsWith(".class")) {
                    try {
                        classes.add(Class.forName(packageName + "." + file.getName().substring(0, file.getName().length() - 6)));
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return classes;
    }


}
