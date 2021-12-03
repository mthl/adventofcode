(ns com.adventofcode.2021.day3-test
  (:require
   [clojure.string :as str]
   [clojure.test :refer [deftest is]]
   [com.adventofcode.util :as util]
   [com.adventofcode.2021.day3 :as sut]))

(def input
  (->> (util/resources-lines "com/adventofcode/2021/day3.txt")
       (map sut/parse-row)))

(def sample
  [[0 0 1 0 0]
   [1 1 1 1 0]
   [1 0 1 1 0]
   [1 0 1 1 1]
   [1 0 1 0 1]
   [0 1 1 1 1]
   [0 0 1 1 1]
   [1 1 1 0 0]
   [1 0 0 0 0]
   [1 1 0 0 1]
   [0 0 0 1 0]
   [0 1 0 1 0]])

(deftest power-consumption-test
  (is (= 22 (-> sample sut/gamma-rate sut/bits->decimal)))
  (is (= 9 (-> sample sut/epsilon-rate sut/bits->decimal)))
  (is (= 198 (sut/power-consumption sample)))
  (is (= 3895776 (sut/power-consumption input))))

(deftest life-support-rate-test
  (is (= 23 (-> sample sut/oxygen-generator-rate sut/bits->decimal)))
  (is (= 10 (-> sample sut/co2-scrubber-rate sut/bits->decimal)))
  (is (= 230 (sut/life-support-rate sample)))
  (is (= 7928162 (sut/life-support-rate input))))
