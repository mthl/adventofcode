(ns com.adventofcode.2021.day7-test
  (:require
   [clojure.test :refer [deftest is]]
   [com.adventofcode.util :as util]
   [com.adventofcode.2021.day7 :as sut]))

(def input
  (first (util/resources-lines "com/adventofcode/2021/day7.txt")))

(def sample
   "16,1,2,0,4,2,7,1,2,14")

(def parsed
  [16 1 2 0 4 2 7 1 2 14])

(deftest overlap-points-test
  (let [crabs (sut/parse-crabs input)]
    (is (= parsed (sut/parse-crabs sample)))
    (is (= 37 (sut/best-score parsed sut/score1)))
    (is (= 337488 (sut/best-score crabs sut/score1)))

    (is (= 168 (sut/score2 5 parsed)))
    (is (= 206 (sut/score2 2 parsed)))

    (is (= 89647695 (sut/best-score crabs sut/score2)))))
