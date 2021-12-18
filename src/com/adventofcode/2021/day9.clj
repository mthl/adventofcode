(ns com.adventofcode.2021.day9
  (:require
   [clojure.set :as set]
   [clojure.string :as str]
   [clojure.java.io :as io]
   [com.adventofcode.util :as util]))

(defrecord Point [x y height])

(defn parse-height-map
  [line]
  (map util/char->int (seq line)))

(defn parse-heights
  [lines]
  (map parse-height-map lines))

(defn insert-sentinels
  [heights]
  (let [length (-> heights first count)]
    (map (fn [height-map]
           (concat [9] height-map [9]))
         (concat [(repeat length 9)]
                 heights
                 [(repeat length 9)]))))

(defn window3
  [coll]
  (map vector
       coll
       (next coll)
       (next (next coll))))

(defn window-low-points
  [y x previous-window current-window next-window]
  (let [height (get current-window 1)
        neighbours [(nth current-window 0)
                    (nth current-window 2)
                    (nth previous-window 1)
                    (nth next-window 1)]]
    (when (every? #(< height %) neighbours)
      (->Point x y height))))

(defn row-low-points
  [y previous-row current-row next-row]
  (map (partial window-low-points y)
       (range)
       (window3 previous-row)
       (window3 current-row)
       (window3 next-row)))

(defn low-points*
  [heights]
  (let [heights* (insert-sentinels heights)]
    (->> (map row-low-points
              (range)
              heights*
              (next heights*)
              (next (next heights*)))
         (mapcat #(filter some? %)))))

(defn low-points
  [heights]
  (->> (low-points* heights)
       (map :height)))

(defn risk-sum
  [heights]
  (->> (low-points heights)
       (map inc)
       (reduce + 0)))

(defn horizontal-edges
  [y row]
  (map (fn [x a b]
         (when-not (or (= a 9) (= b 9))
           [(->Point x y a)
            (->Point (inc x) y b)]))
       (range)
       row
       (next row)))

(defn vertical-edges
  [y previous-row current-row]
  (map (fn [x a b]
         (when-not (or (= a 9) (= b 9))
           [(->Point x (dec y) a)
            (->Point x y b)]))
       (range)
       previous-row
       current-row))

(defn row-edges
  [y previous-row current-row]
  (->> (horizontal-edges y current-row)
       (concat (vertical-edges y previous-row current-row))
       (filter some?)))

(def conj-set (fnil conj #{}))

(defn make-graph
  [heights]
  (let [row-size (-> heights first count)]
    (reduce (fn [g [a b]]
              (-> g
                  (update a conj-set b)
                  (update b conj-set a)))
            {}
            (mapcat row-edges
                    (range)
                    (cons (vec (repeat row-size 9)) heights)
                    heights))))

(defn explore-dfs
  [g node]
  (loop [adjacents [node]
         visited #{}]
    (if (empty? adjacents)
      visited
      (let [node (peek adjacents)]
        (recur (into (pop adjacents) (remove visited) (g node))
               (conj visited node))))))

(defn basins
  [heights]
  (let [g (make-graph heights)
        start-points (low-points* heights)]
    (map (partial explore-dfs g) start-points)))

(defn top-basins-score
  [heights]
  (->> (basins heights)
       (map count)
       (sort >)
       (take 3)
       (reduce * 1)))
