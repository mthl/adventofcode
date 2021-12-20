(ns com.adventofcode.2021.day17-test
  (:require
   [clojure.test :refer [deftest is]]
   [com.adventofcode.util :as util]
   [com.adventofcode.2021.day17 :as sut]))

(def input
  (first (util/resources-lines "com/adventofcode/2021/day17.txt")))

(def sample
  "target area: x=20..30, y=-10..-5")

(def parsed
  (sut/map->Area
   {:x-min 20, :x-max 30, :y-min -10, :y-max -5}))

(deftest velocity-test
  (is (= parsed (sut/parse-target-area sample)))
  (is (= 45 (sut/max-y parsed)))
  (is (= 112 (sut/count-valid-velocities parsed)))
  (let [target (sut/parse-target-area input)]
    (is (= 9180 (sut/max-y target)))
    (is (= 3767 (sut/count-valid-velocities target)))))
