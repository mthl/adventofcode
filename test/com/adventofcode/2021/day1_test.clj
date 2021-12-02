(ns com.adventofcode.2021.day1-test
  (:require
   [clojure.test :refer [deftest is]]
   [com.adventofcode.util :as util]
   [com.adventofcode.2021.day1 :as sut]))

(def input
  (->> (util/resources-lines "com/adventofcode/2021/day1.txt")
       (map #(Long/parseLong %))))

(def sample
  [199
   200
   208
   210
   200
   207
   240
   269
   260
   263])

(deftest increase-count-test
  (is (= 7 (sut/increase-count sample)))
  (is (= 1482 (sut/increase-count input))))

(deftest increase-window-count-test
  (is (= 5 (sut/increase-window-count sample)))
  (is (= 1518 (sut/increase-window-count input))))
