(ns com.adventofcode.2021.day6-test
  (:require
   [clojure.string :as str]
   [clojure.test :refer [deftest is]]
   [com.adventofcode.util :as util]
   [com.adventofcode.2021.day6 :as sut]))

(def input
  (first (util/resources-lines "com/adventofcode/2021/day6.txt")))

(def sample
   "3,4,3,1,2")

(def parsed
  {0 0
   7 0
   1 1
   4 1
   6 0
   3 2
   2 1
   5 0
   8 0})

(deftest overlap-points-test
  (is (= parsed (sut/parse-fishs sample)))
  (is (= 5934 (sut/count-fishs (sut/n-state 80 parsed))))
  (is (= 391888 (sut/count-fishs (sut/n-state 80 (sut/parse-fishs input)))))
  (is (= 26984457539 (sut/count-fishs (sut/n-state 256 parsed))))
  (is (= 1754597645339 (sut/count-fishs (sut/n-state 256 (sut/parse-fishs input))))))
