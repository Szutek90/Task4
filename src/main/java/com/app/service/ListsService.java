package com.app.service;

import com.app.file_operator.FileOperator;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@ToString
@Slf4j
public class ListsService {
    private final List<Integer> lowestNumsRow;
    private final List<Integer> middleValueNumsRow;
    private final List<Integer> highestNumsRow;
    private final FileOperator<List<List<Integer>>> fileOperator;

    public ListsService(String filename, FileOperator<List<List<Integer>>> fileOperator) {
        this.fileOperator = fileOperator;
        var readed = fileOperator.readFile(filename);
        lowestNumsRow = new ArrayList<>(readed.getFirst());
        middleValueNumsRow = new ArrayList<>(readed.get(1));
        highestNumsRow = new ArrayList<>(readed.get(2));
    }


    public List<Integer> getIdxOfLowestDiff() {
        var idxs = new ArrayList<Integer>();
        var minDiff = Integer.MAX_VALUE;
        for (int i = 0; i < lowestNumsRow.size(); i++) {
            var currentDiff = Math.abs(lowestNumsRow.get(i) - middleValueNumsRow.get(i) - highestNumsRow.get(i));
            if (currentDiff < minDiff) {
                idxs.clear();
                idxs.add(i);
                minDiff = currentDiff;
            }
            else if (currentDiff == minDiff) {
                idxs.add(i);
            }
        }
        return idxs;
    }

    public List<Integer> getIdxOfHighestDiff() {
        var idxs = new ArrayList<Integer>();
        var maxDiff = 0;
        for (int i = 0; i < lowestNumsRow.size(); i++) {
            var currentDiff = Math.abs(lowestNumsRow.get(i) - middleValueNumsRow.get(i) - highestNumsRow.get(i));
            if (currentDiff > maxDiff) {
                idxs.clear();
                idxs.add(i);
                maxDiff = currentDiff;
            }
            else if (currentDiff == maxDiff) {
                idxs.add(i);
            }
        }
        return idxs;
    }

    public String getHighestSequence() {
        var minSeq = getHighestSequenceFromArr(lowestNumsRow);
        var midSeq = getHighestSequenceFromArr(middleValueNumsRow);
        var highSeq = getHighestSequenceFromArr(highestNumsRow);
        if (minSeq == midSeq && highSeq == midSeq) {
            return "PIERWSZA, DRUGA I TRZECIA";
        } else if (minSeq == midSeq) {
            return "PIERWSZA I DRUGA";
        } else if (minSeq == highSeq) {
            return "PIERWSZA I TRZECIA";
        } else if (midSeq == highSeq) {
            return "DRUGA I PIERWSZA";
        }
        var max = Math.max(minSeq, Math.max(midSeq, highSeq));
        if (max == minSeq) {
            return "PIERWSZA";
        } else if (max == midSeq) {
            return "DRUGA";
        }
        return "TRZECIA";
    }

    public int getDiffHighestValBetweenLowAndMiddleArrs() {
        return getDiffHighestValBetweenTwoLists(lowestNumsRow, middleValueNumsRow);
    }

    public List<Integer> getDividedValuesFromHighestArr(int divisor) {
        return getDividedValues(highestNumsRow, divisor);
    }


    public void perfectFileCondition() {
        if (fileIsPerfect()) {
            log.info("String is perfect");
        } else {
            var lowestToDelete = new ArrayList<>();
            var middleToDelete = new ArrayList<>();
            var tempListLowest = new ArrayList<>(lowestNumsRow);
            var tempListMiddle = new ArrayList<>(middleValueNumsRow);
            var tempListHighest = new ArrayList<>(highestNumsRow);
            var maxFromLowest = getMaxValueFromList(lowestNumsRow);
            var minFromMiddle = getMinValueFromList(tempListMiddle);
            var maxFromMiddle = getMaxValueFromList(tempListMiddle);
            var minFromHighest = getMinValueFromList(tempListHighest);
            while (maxFromLowest >= minFromMiddle) {
                lowestToDelete.add(maxFromLowest);
                tempListLowest.remove(Integer.valueOf(maxFromLowest));
                maxFromLowest = getMinValueFromList(tempListLowest);

            }
            while (maxFromMiddle >= minFromHighest) {
                middleToDelete.add(maxFromMiddle);
                tempListMiddle.remove(Integer.valueOf(maxFromMiddle));
                maxFromMiddle = getMinValueFromList(tempListMiddle);
            }
            log.info("To obtain perfect file its necesarry to delete:");
            log.info("From First, lowest nums row: " + lowestToDelete);
            log.info("From Second, middle nums row: " + middleToDelete);
        }
    }

    public boolean fileIsPerfect() {
        if (lowestNumsRow.size() != highestNumsRow.size() || highestNumsRow.size() != middleValueNumsRow.size()) {
            throw new IllegalStateException("Size of lists are not equal");
        }
        var maxFromLowest = getMaxValueFromList(lowestNumsRow);
        var minFromMiddle = getMinValueFromList(middleValueNumsRow);
        var maxFromMiddle = getMaxValueFromList(middleValueNumsRow);
        var minFromHighest = getMinValueFromList(highestNumsRow);
        return maxFromLowest < minFromMiddle && maxFromMiddle < minFromHighest;
    }

    private List<Integer> getDividedValues(List<Integer> arr, int divisor) {
        var dividedValues = new ArrayList<Integer>();
        if (divisor == 0 || arr.isEmpty()) {
            throw new IllegalStateException();
        }
        for (var value : arr) {
            if (value % divisor == 0) {
                dividedValues.add(value);
            }
        }
        return dividedValues;
    }

    private int getHighestSequenceFromArr(List<Integer> arr) {
        var highestSeq = 0;
        var counter = 0;
        arr.sort(Comparator.naturalOrder());
        for (int i = 0; i < arr.size() - 1; i++) {
            if (arr.get(i) > arr.get(i + 1)) {
                counter++;
                highestSeq = Math.max(counter, highestSeq);
            } else {
                counter = 0;
            }
        }
        return highestSeq;
    }

    private int getDiffHighestValBetweenTwoLists(List<Integer> arr1, List<Integer> arr2) {
        return Math.abs(getMaxValueFromList(arr1) - Math.abs(getMaxValueFromList(arr2)));
    }

    private int getMinValueFromList(List<Integer> list) {
        return list.stream().min(Comparator.naturalOrder()).orElseThrow(IllegalStateException::new);
    }

    private int getMaxValueFromList(List<Integer> list) {
        return list.stream().max(Comparator.naturalOrder()).orElseThrow(IllegalStateException::new);
    }

}
