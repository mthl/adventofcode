(ns com.adventofcode.2021.day11
  (:require
   [clojure.set :as set]
   [clojure.string :as str]
   [com.adventofcode.util :as util]))

(defn- parse-line
  [line]
  (map util/char->int line))

(defn parse-octopuses
  [lines]
  (map parse-line lines))

(defn width
  [octopuses]
  (-> octopuses first count))

(defn height
  [octopuses]
  (count octopuses))

(defn edges
  [octopuses]
  (->> (for [x (range (dec (width octopuses)))
             y (range (dec (height octopuses)))]
         (let [xy [x y]]
           (cond-> [[xy [x (inc y)]]
                    [xy [(inc x) y]]
                    [xy [(inc x) (inc y)]]]
             (and (> x 0) (> y 0))
             (conj [xy [(dec x) (dec y)]])

             (> x 0)
             (conj [xy [(dec x) y]]
                   [xy [(dec x) (inc y)]])

             (> y 0)
             (conj [xy [x (dec y)]]
                   [xy [(inc x) (dec y)]]))))
       (mapcat identity)
       (concat (map (fn [x y y*]
                      [[x y] [x y*]])
                    (repeat (dec (width octopuses)))
                    (range (height octopuses))
                    (next (range (height octopuses))))
               (map (fn [y x x*]
                      [[x y] [x* y]])
                    (repeat (dec (height octopuses)))
                    (range (width octopuses))
                    (next (range (width octopuses))))
               [[[(-> octopuses height dec) (-> octopuses width dec dec)]
                 [(-> octopuses height dec dec) (-> octopuses width dec)]]])))

(def conj-set (fnil conj #{}))

(defn graph
  [octopuses]
  (reduce (fn [g [a b]]
            (-> g
                (update a conj-set b)
                (update b conj-set a)))
          {}
          (edges octopuses)))

(defn energy-level
  [octopuses [x y]]
  (-> octopuses
      (nth y)
      (nth x)))

(defn inc-energy-level
  [octopuses [x y]]
  (update octopuses y update x inc))

(defn step1
  [octopuses]
  (vec (for [row octopuses]
         (mapv inc row))))

(defn find-flashing-nodes
  [octopuses]
  (for [x (range (width octopuses))
        y (range (height octopuses))
        :when (> (energy-level octopuses [x y]) 9)]
    [x y]))

(defn propagate-flash
  [octopuses g flashing-nodes]
  (if (empty? flashing-nodes)
    octopuses
    (let [flashing (first flashing-nodes)
          adjacents (g flashing)]
      (recur (reduce inc-energy-level octopuses adjacents)
             g
             (rest flashing-nodes)))))

(defn add-zeros
  [octopuses]
  (vec (for [row octopuses]
         (mapv #(if (> % 9) 0 %) row))))

(defn step
  [octopuses]
  (let [g (graph octopuses)]
    (loop [flashing #{}
           state (step1 octopuses)]
      (let [flashing* (-> state find-flashing-nodes set)
            new-flashing (set/difference flashing* flashing)]
        (if (empty? new-flashing)
          (add-zeros state)
          (recur flashing* (propagate-flash state g new-flashing)))))))

(defn n-step
  [n octopuses]
  (loop [i n
         state octopuses]
    (if (zero? i)
      state
      (recur (dec i) (step state)))))

(defn count-flashes
  [n octopuses]
  (loop [i n
         state octopuses
         flashes 0]
    (if (zero? i)
      flashes
      (let [new-state (step state)]
        (recur (dec i)
               new-state
               (->> new-state
                    (mapcat identity)
                    (filter zero?)
                    count
                    (+ flashes)))))))

(defn simultaneous-step
  [octopuses]
  (loop [i 0
         state octopuses]
    (if (->> state (mapcat identity) (every? zero?))
      i
      (recur (inc i) (step state)))))
