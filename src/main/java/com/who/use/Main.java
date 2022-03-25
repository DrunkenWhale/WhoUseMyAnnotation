package com.who.use;

import java.io.File;
import java.io.IOException;
import java.net.*;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        getAllClassesInProjectDirectory().forEach(System.out::println);
    }


    public static List<String> getAllClassesInProjectDirectory() throws IOException {
        List<String> list = new LinkedList<>();
        String classesPath = System.getProperty("java.class.path");
        int classesPathLength = classesPath.length();
        Files.walkFileTree(Path.of(new File(classesPath).toURI()), new SimpleFileVisitor<>() {
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

}
