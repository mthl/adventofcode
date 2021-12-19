(ns com.adventofcode.2021.day15
  (:require
   [clojure.data.priority-map :refer [priority-map]]
   [clojure.spec.alpha :as s]
   [clojure.string :as str]
   [com.adventofcode.util :as util]))

(s/def ::node
  (s/cat :x int? :y int?))

(defn risk
  [cavern [x y]]
  (-> cavern (nth y) (nth x)))

(defn parse-cavern
  [lines]
  (mapv (fn [line]
          (mapv util/char->int line))
        lines))

(def conj-set (fnil conj #{}))

(defn graph
  [cavern]
  (let [width (count (first cavern))
        heigh (count cavern)
        xs (range width)
        ys (range heigh)
        horizontal-edges (for [y ys]
                           (map (fn [x1 x2]
                                  [[x1 y] [x2 y]])
                                xs
                                (next xs)))
        vertical-edges (for [x xs]
                         (map (fn [y1 y2]
                                [[x y1] [x y2]])
                              ys
                              (next ys)))
        edges (concat horizontal-edges vertical-edges)]
    (reduce (fn [acc [from to]]
              (-> acc
                  (update from conj-set to)
                  (update to conj-set from)))
            {}
            (into [] cat edges))))

(defn find-min
  [P d]
  (:node (reduce-kv (fn [acc node dist]
                      (if (and (contains? P node)
                               (< dist (:dist acc)))
                        {:node node
                         :dist dist}
                        acc))
                    {:node nil
                     :dist Long/MAX_VALUE}
                    d)))

(defn init-priority-map
  [g]
  (-> (priority-map)
      (into (zipmap (keys g) (repeat Long/MAX_VALUE)))
      (assoc [0 0] 0)))

(defn minimum-risk
  ([cavern]
   (let [width (count (first cavern))
         heigth (count cavern)]
     (minimum-risk cavern [0 0] [(dec width) (dec heigth)])))
  ([cavern start end]
   (let [g (graph cavern)]
     (loop [Q (init-priority-map g)]
       (let [[pos acc-risk] (first Q)
             Q* (dissoc Q pos)]
         (if (= pos end)
           acc-risk
           (recur (reduce (fn [acc neighbour]
                            (let [r (risk cavern neighbour)]
                              (update acc neighbour min (+ acc-risk r))))
                          Q*
                          (filter Q* (g pos))))))))))

(defn inc*
  [n]
  (if (> n 8) 1 (inc n)))

(defn add1
  [cavern]
  (mapv (fn [row]
          (mapv inc* row))
        cavern))

(defn repeat-5
  [cavern]
  (let [row (->> (iterate add1 cavern)
                 (take 5)
                 (apply mapv (comp vec concat)))]
    (->> (iterate add1 row)
         (take 5)
         (mapcat identity))))
