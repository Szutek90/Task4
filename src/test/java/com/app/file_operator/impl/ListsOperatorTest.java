package com.app.file_operator.impl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

class ListsOperatorTest {

    @Test
    @DisplayName("When reading file")
    void test(){
        var fileOperator = new ListOperator();
        var expectedLowest = List.of(10,30,5);
        var expectedSecondHighest = List.of(20,21,46);
        var expectedHighest = List.of(56,90,98);
        var result = fileOperator.readFile("src/test/resources/testValues.csv");

        Assertions.assertThat(result.getFirst()).containsExactlyInAnyOrderElementsOf(expectedLowest);
        Assertions.assertThat(result.get(1)).containsExactlyInAnyOrderElementsOf(expectedSecondHighest);
        Assertions.assertThat(result.getLast()).containsExactlyInAnyOrderElementsOf(expectedHighest);

    }
}
