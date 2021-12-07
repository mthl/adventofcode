(ns com.adventofcode.2021.day7
  (:require
   [clojure.string :as str]
   [clojure.java.math :as math]))

(defn sum
  [nums]
  (reduce + 0 nums))

(defn parse-crabs
  [line]
  (->> (str/split line #",")
       (map #(Long/parseLong %))))

(defn dist
  [crab pos]
  (- (max crab pos)
     (min crab pos)))

(defn score1
  [pos crabs]
  (sum (map #(dist % pos) crabs)))

(defn int-sum
  [n]
  (/ (* n (inc n))
     2))

(defn score2
  [pos crabs]
  (sum (map #(int-sum (dist % pos)) crabs)))

(defn best-score
  [crabs score-fn]
  (loop [acc (-> crabs first (score-fn crabs))
         candidates (rest crabs)]
    (if (empty? candidates)
      acc
      (recur (min acc (-> candidates first (score-fn crabs)))
             (next candidates)))))
