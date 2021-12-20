(ns com.adventofcode.2021.day20
  (:require
   [clojure.string :as str]))

(defn parse-image-enhancement
  [lines]
  {:algo (first lines)
   :image (vec (next (next lines)))})

(defn window3
  [coll]
  (map vector
       coll
       (next coll)
       (next (next coll))))

(defn pixels->num
  [pixels]
  (let [bits (map {\# 1, \. 0} pixels)]
    (Long/parseLong (str/join bits) 2)))

(defn transform-window
  [algo previous-window current-window next-window]
  (let [pixels (concat previous-window current-window next-window)
        num (pixels->num pixels)
        pixel (nth algo num)]
    pixel))

(defn transform-row
  [algo previous-row current-row next-row]
  (map (partial transform-window algo)
       (window3 previous-row)
       (window3 current-row)
       (window3 next-row)))

(defn insert-sentinels
  [sentinel points]
  (let [width (-> points first count)
        extra-row (repeat width sentinel)
        extra-rows [extra-row extra-row]
        extra-columns [sentinel sentinel]]
    (map (fn [row]
           (concat extra-columns row extra-columns))
         (concat extra-rows points extra-rows))))

(defn transform
  ([image-enhancement]
   (transform image-enhancement 1))
  ([{:keys [image algo]} n]
   (loop [i n
          state image]
     (if (zero? i)
       state
       (let [zero (get algo 0)
             sentinel (cond
                        (= zero \.) \.
                        (even? i) \.
                        :else \#)
             pixels (insert-sentinels sentinel state)]
         (recur (dec i)
                (->> (map (partial transform-row algo)
                          pixels
                          (next pixels)
                          (next (next pixels)))
                     (map str/join))))))))

(defn count-lits
  [image]
  (reduce (fn [acc row]
            (+ acc (count (filter #{\#} row))))
          0
          image))
