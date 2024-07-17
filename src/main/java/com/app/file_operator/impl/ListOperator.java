package com.app.file_operator.impl;


import com.app.file_operator.FileOperator;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class ListOperator implements FileOperator<List<List<Integer>>> {

    @Override
    public List<List<Integer>> readFile(String fileName) {
        var highestNum = new ArrayList<Integer>();
        var secondHighestNum = new ArrayList<Integer>();
        var lowestNum = new ArrayList<Integer>();
        try (var lines = Files.lines(Paths.get(fileName))) {
            lines.forEach(line -> {
                var splitted = new ArrayList<>(Arrays.stream(line.split(";"))
                        .map(Integer::parseInt).toList());
                Collections.sort(splitted);
                lowestNum.add(splitted.get(0));
                secondHighestNum.add(splitted.get(1));
                highestNum.add(splitted.get(2));

            });
            return new LinkedList<>(List.of(lowestNum, secondHighestNum, highestNum));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
