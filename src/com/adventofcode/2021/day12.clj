(ns com.adventofcode.2021.day12
  (:require
   [clojure.string :as str]))

(defn parse-edge
  [line]
  (->> (str/split line #"-")
       (map keyword)))

(def conj-set (fnil conj #{}))

(defn parse-graph
  [lines]
  (reduce (fn [acc [from to]]
            (-> acc
                (update from conj-set to)
                (update to conj-set from)))
          {}
          (map parse-edge lines)))

(defn small-cave?
  [cave]
  (and (= (name cave)
           (-> cave name str/lower-case))
       (not= cave :end)))

(defn explore
  [g]
  (loop [paths #{}
         stack [[:start]]]
    (if (empty? stack)
      paths
      (let [path (peek stack)
            node (peek path)]
        (if (= :end node)
          (recur (conj paths path)
                 (pop stack))
          (let [small-caves (filter small-cave? path)]
            (recur paths
                   (into (pop stack)
                         (map #(conj path %))
                         (remove (set small-caves) (g node))))))))))

(defn count-paths
  [g]
  (count (explore g)))

(defn explore2
  [g]
  (loop [paths #{}
         stack [[:start]]]
    (if (empty? stack)
      paths
      (let [path (peek stack)
            node (peek path)]
        (if (= :end node)
          (recur (conj paths path)
                 (pop stack))
          (let [small-caves (filter small-cave? path)
                twice? (->> small-caves frequencies vals (some #{2}))]
            (recur paths
                   (into (pop stack)
                         (map #(conj path %))
                         (cond->> (disj (g node) :start)
                           twice? (remove (set small-caves)))))))))))

(defn count-paths*
  [g]
  (count (explore2 g)))
