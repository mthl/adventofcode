(ns com.adventofcode.2021.day15-test
  (:require
   [clojure.string :as str]
   [clojure.test :refer [deftest is]]
   [com.adventofcode.util :as util]
   [com.adventofcode.2021.day15 :as sut]))

(def input
  (util/resources-lines "com/adventofcode/2021/day15.txt"))

(def sample
  (str/split-lines
   "1163751742
1381373672
2136511328
3694931569
7463417111
1319128137
1359912421
3125421639
1293138521
2311944581"))

(def parsed
  [[1 1 6 3 7 5 1 7 4 2]
   [1 3 8 1 3 7 3 6 7 2]
   [2 1 3 6 5 1 1 3 2 8]
   [3 6 9 4 9 3 1 5 6 9]
   [7 4 6 3 4 1 7 1 1 1]
   [1 3 1 9 1 2 8 1 3 7]
   [1 3 5 9 9 1 2 4 2 1]
   [3 1 2 5 4 2 1 6 3 9]
   [1 2 9 3 1 3 8 5 2 1]
   [2 3 1 1 9 4 4 5 8 1]])

(def extended-parsed
  (sut/repeat-5 parsed))

(def cavern (sut/parse-cavern input))

(deftest cavern-test
  (is (= parsed (sut/parse-cavern sample)))
  (is (= 40 (sut/minimum-risk parsed)))
  (is (= [[8 9 1 2 3]
          [9 1 2 3 4]
          [1 2 3 4 5]
          [2 3 4 5 6]
          [3 4 5 6 7]]
         (sut/repeat-5 [[8]])))
  (is (= 315 (sut/minimum-risk extended-parsed)))
  (is (= 745 (sut/minimum-risk cavern)))
  (is (= 3002 (sut/minimum-risk (sut/repeat-5 cavern)))))
