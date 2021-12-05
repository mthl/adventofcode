(ns com.adventofcode.2021.day5-test
  (:require
   [clojure.string :as str]
   [clojure.test :refer [deftest is]]
   [com.adventofcode.util :as util]
   [com.adventofcode.2021.day5 :as sut]))

(def input
  (util/resources-lines "com/adventofcode/2021/day5.txt"))

(def sample
  (str/split-lines
   "0,9 -> 5,9
8,0 -> 0,8
9,4 -> 3,4
2,2 -> 2,1
7,0 -> 7,4
6,4 -> 2,0
0,9 -> 2,9
3,4 -> 1,4
0,0 -> 8,8
5,5 -> 8,2"))

(def parsed
  [[0 9 5 9]
   [8 0 0 8]
   [9 4 3 4]
   [2 2 2 1]
   [7 0 7 4]
   [6 4 2 0]
   [0 9 2 9]
   [3 4 1 4]
   [0 0 8 8]
   [5 5 8 2]])

(deftest overlap-points-test
  (let [lines (sut/parse input)]
    (is (= parsed (sut/parse sample)))
    (is (= 5 (->> parsed (mapcat sut/points) sut/overlap-points count)))
    (is (= 5084 (->> lines (mapcat sut/points) sut/overlap-points count)))

    (is (= 12 (->> parsed (mapcat sut/points*) sut/overlap-points count)))
    (is (= 17882 (->> lines (mapcat sut/points*) sut/overlap-points count)))))

