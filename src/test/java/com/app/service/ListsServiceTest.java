package com.app.service;

import com.app.file_operator.impl.ListOperator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class ListsServiceTest {
    static ListsService listsService;

    @BeforeAll
    static void beforeAll() {
        listsService = new ListsService("src/test/resources/testValues.csv", new ListOperator());
    }

    @Test
    @DisplayName("When getting idxs of lowest difference between lists")
    void test1() {
        assertThat(listsService.getIdxOfLowestDiff()).containsExactlyInAnyOrderElementsOf(List.of(0));
    }

    @Test
    @DisplayName("When getting idxs of highest difference between lists")
    void test2() {
        assertThat(listsService.getIdxOfHighestDiff()).containsExactlyInAnyOrderElementsOf(List.of(2));
    }

    @Test
    @DisplayName("When getting highest sequence")
    void test3() {
        assertThat(listsService.getHighestSequence()).isEqualTo("PIERWSZA, DRUGA I TRZECIA");
    }

    @Test
    @DisplayName("When getting difference between highest values of lowest and middle arrays")
    void test4() {
        assertThat(listsService.getDiffHighestValBetweenLowAndMiddleArrs()).isEqualTo(16);
    }

    @Test
    @DisplayName("When getting divied values from highest array by given value")
    void test5() {
        assertThat(listsService.getDividedValuesFromHighestArr(45)).containsExactly(90);
    }

    @Test
    @DisplayName("When perfect file condition is not throwing any exception")
    void test6() {
        assertThatCode(() -> listsService.perfectFileCondition()).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("When perfect file condition is false")
    void test7() {
        assertThat(listsService.fileIsPerfect()).isFalse();
    }
}
