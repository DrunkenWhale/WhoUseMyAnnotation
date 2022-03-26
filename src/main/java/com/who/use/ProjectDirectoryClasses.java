package com.who.use;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

public class ProjectDirectoryClasses {

    public static final List<Class<?>> classes = initClasses();

    public static List<Class<?>> getAllClassesUseSpecialAnnotation(Class<? extends Annotation> clazz) {
        return ProjectDirectoryClasses.classes
                .stream()
                .filter(cls -> cls.getDeclaredAnnotation(clazz) != null)
                .collect(Collectors.toList());
    }


    private ProjectDirectoryClasses() {
    }


    private static List<Class<?>> initClasses() {

        ClassLoader classLoader =
                ProjectDirectoryClasses.class.getClassLoader();

        try {
            return getAllClassesFullNameInProjectDirectory().stream().map(x -> {
                try {
                    return classLoader.loadClass(x);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                return null;
            }).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return new LinkedList<>();
        }
    }

    private static List<String> getAllClassesFullNameInProjectDirectory() {

        List<String> list = new LinkedList<>();
        Arrays.stream(System.getProperty("java.class.path").split(";")).forEach(path -> {
            try {
                if (path.endsWith(".jar")) {
                    list.addAll(getAllClassesInSpecialJarPath(path));
                } else {
                    list.addAll(getAllClassesInSpecialDirectoryPath(path));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return list;

    }

    private static List<String> getAllClassesInSpecialDirectoryPath(String path) throws IOException {
        List<String> list = new LinkedList<>();
        int classesPathLength = path.length();
        Files.walkFileTree(Path.of(new File(path).toURI()), new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                String absPath = file.toString();
                if (absPath.endsWith(".class")) {
                    list.add(absPath.substring(0, absPath.length() - 6).substring(classesPathLength + 1).replace(System.getProperty("file.separator"), "."));
                }
                return super.visitFile(file, attrs);
            }
        });
        return list;
    }

    private static List<String> getAllClassesInSpecialJarPath(String jarPath) throws IOException {
        List<String> list = new LinkedList<>();
        if (jarPath.endsWith(".jar")) {
            JarFile jarFile = new JarFile(jarPath);
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry jarEntry = entries.nextElement();
                String jarClassPath = jarEntry.getName();
                if (!jarEntry.isDirectory() && jarClassPath.endsWith(".class") && !jarClassPath.contains("module-info")) {
                    String classPath = jarClassPath.substring(0, jarClassPath.length() - 6).replace("/", ".");
                    list.add(classPath);
                }
            }
        }
        return list;
    }
}
