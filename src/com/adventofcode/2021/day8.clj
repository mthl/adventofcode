(ns com.adventofcode.2021.day8
  (:require
   [clojure.set :as set]
   [clojure.string :as str]
   [clojure.java.io :as io]))

(defn parse-line
  [line]
  (let [[signals output] (str/split line #" \| ")]
    {:signals (str/split signals #" ")
     :output (str/split output #" ")}))

(defn parse-entries
  [lines]
  (map parse-line lines))

(defn count-1478
  [entries]
  (->> entries
       (mapcat :output)
       (filter (comp #{2 3 4 7} count))
       count))

(defn matching
  [pattern]
  (fn [candidate]
    (set/subset? pattern candidate)))

(defn select
  [candidates & xforms]
  (->> candidates
       (eduction (apply comp xforms))
       first))

(defn find-mapping
  [signals]
  (let [length (->> signals (map set) (group-by count))
        repr {1 (select (length 2))
              4 (select (length 4))
              7 (select (length 3))
              8 (select (length 7))}]
    (assoc repr
           0 (select (length 6)
                     (remove (matching (repr 4)))
                     (filter (matching (repr 7))))
           2 (select (length 5)
                     (remove (matching (repr 7)))
                     (remove (matching (set/difference (repr 4) (repr 1)))))
           3 (select (length 5) (filter (matching (repr 7))))
           5 (select (length 5)
                     (filter (matching (set/difference (repr 4) (repr 1)))))
           6 (select (length 6)
                     (remove (matching (repr 4)))
                     (remove (matching (repr 7))))
           9 (select (length 6)
                     (filter (matching (repr 4)))
                     (filter (matching (repr 7)))))))

(defn decode
  [entry]
  (let [mapping (-> entry :signals find-mapping)
        translate (set/map-invert mapping)]
    (->> (:output entry)
         (map (comp translate set))
         str/join
         Long/parseLong)))

(defn sum-entries
  [entries]
  (->> entries
       (map decode)
       (reduce + 0)))
