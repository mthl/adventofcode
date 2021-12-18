(ns com.adventofcode.2021.day11-test
  (:require
   [clojure.string :as str]
   [clojure.test :refer [deftest is]]
   [com.adventofcode.util :as util]
   [com.adventofcode.2021.day11 :as sut]))

(def input
  (util/resources-lines "com/adventofcode/2021/day11.txt"))

(def sample
  (str/split-lines
   "5483143223
2745854711
5264556173
6141336146
6357385478
4167524645
2176841721
6882881134
4846848554
5283751526"))

(def small0
  [[1 1 1 1 1]
   [1 9 9 9 1]
   [1 9 1 9 1]
   [1 9 9 9 1]
   [1 1 1 1 1]])

(def small1
  [[3 4 5 4 3]
   [4 0 0 0 4]
   [5 0 0 0 5]
   [4 0 0 0 4]
   [3 4 5 4 3]])

(def small2
  [[4 5 6 5 4]
   [5 1 1 1 5]
   [6 1 1 1 6]
   [5 1 1 1 5]
   [4 5 6 5 4]])

(def parsed
  [[5 4 8 3 1 4 3 2 2 3]
   [2 7 4 5 8 5 4 7 1 1]
   [5 2 6 4 5 5 6 1 7 3]
   [6 1 4 1 3 3 6 1 4 6]
   [6 3 5 7 3 8 5 4 7 8]
   [4 1 6 7 5 2 4 6 4 5]
   [2 1 7 6 8 4 1 7 2 1]
   [6 8 8 2 8 8 1 1 3 4]
   [4 8 4 6 8 4 8 5 5 4]
   [5 2 8 3 7 5 1 5 2 6]])

(deftest flashes-test
  (is (= parsed (sut/parse-octopuses sample)))
  (is (= [[6 5 9 4 2 5 4 3 3 4]
          [3 8 5 6 9 6 5 8 2 2]
          [6 3 7 5 6 6 7 2 8 4]
          [7 2 5 2 4 4 7 2 5 7]
          [7 4 6 8 4 9 6 5 8 9]
          [5 2 7 8 6 3 5 7 5 6]
          [3 2 8 7 9 5 2 8 3 2]
          [7 9 9 3 9 9 2 2 4 5]
          [5 9 5 7 9 5 9 6 6 5]
          [6 3 9 4 8 6 2 6 3 7]]
         (sut/step parsed)))

  (is (= small1 (sut/step small0)))
  (is (= small2 (sut/step small1)))

  (is (= [[0 0 5 0 9 0 0 8 6 6]
          [8 5 0 0 8 0 0 5 7 5]
          [9 9 0 0 0 0 0 0 3 9]
          [9 7 0 0 0 0 0 0 4 1]
          [9 9 3 5 0 8 0 0 6 3]
          [7 7 1 2 3 0 0 0 0 0]
          [7 9 1 1 2 5 0 0 0 9]
          [2 2 1 1 1 3 0 0 0 0]
          [0 4 2 1 1 2 5 0 0 0]
          [0 0 2 1 1 1 9 0 0 0]]
         (sut/n-step 3 parsed)))

  (is (= [[0 4 8 1 1 1 2 9 7 6]
          [0 0 3 1 1 1 2 0 0 9]
          [0 0 4 1 1 1 2 5 0 4]
          [0 0 8 1 1 1 1 4 0 6]
          [0 0 9 9 1 1 1 3 0 6]
          [0 0 9 3 5 1 1 2 3 3]
          [0 4 4 2 3 6 1 1 3 0]
          [5 5 3 2 2 5 2 3 5 0]
          [0 5 3 2 2 5 0 6 0 0]
          [0 0 3 2 2 4 0 0 0 0]]
         (sut/n-step 10 parsed)))

  (is (= 1656 (sut/count-flashes 100 parsed)))
  (is (= 195 (sut/simultaneous-step parsed)))

  (let [octopuses (sut/parse-octopuses input)]
    (is (= 1683 (sut/count-flashes 100 octopuses)))
    (is (= 788 (sut/simultaneous-step octopuses)))))
