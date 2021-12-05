(ns com.adventofcode.2021.day5
  (:require [clojure.set :as set]))

(defn parse
  [lines]
  (->> lines
       (map #(re-matches #"^(\d+),(\d+) -> (\d+),(\d+)$" %))
       (map (fn [[_ x1 y1 x2 y2]]
              [(Long/parseLong x1)
               (Long/parseLong y1)
               (Long/parseLong x2)
               (Long/parseLong y2)]))))

(defn int-seq
  "Returns a lazy seq of integers from start (inclusive) to end
  (inclusive). When start is equal to end, returns an infinite sequence
  of start."
  [start end]
  (let [step (compare end start)]
    (if (= step 0)
      (repeat start)
      (range start (+ end step) step))))

(defn points*
  "Return the points contained in a vertical, horizontal or diagonal line."
  [line]
  (let [[x1 y1 x2 y2] line
        xs (int-seq x1 x2)
        ys (int-seq y1 y2)]
    (map vector xs ys)))

(defn points
  "Return the points contained in a vertical or horizontal line."
  [line]
  (let [[x1 y1 x2 y2] line]
    (if (or (= x1 x2) (= y1 y2))
      (line-points* line)
      [])))

(defn overlap-points
  [points]
  (->> points
       frequencies
       (filter (fn [[p n]]
                 (> n 1)))
       keys))
