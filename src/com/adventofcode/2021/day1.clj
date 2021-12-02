(ns com.adventofcode.2021.day1)

(defn increase-count
  [measurements]
  (->> (map - measurements (next measurements))
       (filter neg?)
       count))

(defn increase-window-count
  [measurements]
  (->> (map + measurements (next measurements) (nnext measurements))
       increase-count))
