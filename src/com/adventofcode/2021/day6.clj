(ns com.adventofcode.2021.day6
  (:require [clojure.string :as str]))

(defn parse-fishs
  [line]
  (->> (str/split line #",")
       (map #(Long/parseLong %))
       frequencies
       (merge (zipmap (range 9) (repeat 0)))))

(defn step
  [fishs]
  {0 (fishs 1)
   1 (fishs 2)
   2 (fishs 3)
   3 (fishs 4)
   4 (fishs 5)
   5 (fishs 6)
   6 (+ (fishs 7) (fishs 0))
   7 (fishs 8)
   8 (fishs 0)})

(defn n-state
  [n state]
  (loop [x n
         s state]
    (if (= x 0)
      s
      (recur (dec x) (step s)))))

(defn count-fishs
  [state]
  (reduce + 0 (vals state)))
