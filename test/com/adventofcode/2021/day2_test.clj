(ns com.adventofcode.2021.day2-test
  (:require
   [clojure.string :as str]
   [clojure.test :refer [deftest is]]
   [com.adventofcode.util :as util]
   [com.adventofcode.2021.day2 :as sut]))

(def input
  (->> (util/resources-lines "com/adventofcode/2021/day2.txt")
       (map #(re-matches #"^(forward|down|up) (\d+)$" %))
       (map (fn [[_ action amount]]
              [(keyword action) (Long/parseLong amount)]))))

(def sample
  [[:forward 5]
   [:down 5]
   [:forward 8]
   [:up 3]
   [:down 8]
   [:forward 2]])

(defn result
  [pos]
  (* (:horizontal-position pos)
     (:depth pos)))

(deftest position-test
  (is (= 150 (-> sample sut/position result)))
  (is (= 1383564 (-> input sut/position result))))

(deftest position-with-aim-test
  (is (= 900 (-> sample sut/position-with-aim result)))
  (is (= 1488311643 (-> input sut/position-with-aim result))))
