(ns com.adventofcode.2021.day9-test
  (:require
   [clojure.string :as str]
   [clojure.test :refer [deftest is]]
   [com.adventofcode.util :as util]
   [com.adventofcode.2021.day9 :as sut]))

(def input
  (util/resources-lines "com/adventofcode/2021/day9.txt"))

(def sample
  (str/split-lines
   "2199943210
3987894921
9856789892
8767896789
9899965678"))

(def parsed
  [[2 1 9 9 9 4 3 2 1 0]
   [3 9 8 7 8 9 4 9 2 1]
   [9 8 5 6 7 8 9 8 9 2]
   [8 7 6 7 8 9 6 7 8 9]
   [9 8 9 9 9 6 5 6 7 8]])

(deftest overlap-points-test
  (let [heights (sut/parse-heights input)]
    (is (= parsed (sut/parse-heights sample)))
    (is (= [1 0 5 5] (sut/low-points parsed)))
    (is (= 15 (sut/risk-sum parsed)))
    (is (= 570 (sut/risk-sum heights)))
    (is (= 1134 (sut/top-basins-score parsed)))
    (is (= 899392 (sut/top-basins-score heights)))))
