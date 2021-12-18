(ns com.adventofcode.2021.day10
  (:require
   [clojure.set :as set]))

(def closing-char
  {\( \), \[ \], \{ \}, \< \>})

(def opening-char
  (set/map-invert closing-char))

(def corrupted-score
  {\) 3
   \] 57
   \} 1197
   \> 25137})

(defn missing-score
  [chars]
  (reduce (fn [acc c]
            (-> acc
                (* 5)
                (+ (case c
                     \) 1
                     \] 2
                     \} 3
                     \> 4))))
          0
          chars))

(defn check-line
  [line]
  (loop [input (seq line)
         stack []]
    (if (empty? input)
      {:error :missing
       :score (->> (reverse stack)
                   (map closing-char)
                   missing-score)}
      (let [c (first input)]
        (cond
          (contains? closing-char c)
          (recur (next input) (conj stack c))

          (= (opening-char c) (peek stack))
          (recur (next input) (pop stack))

          :else {:error :corrupted
                 :score (corrupted-score c)})))))

(defn check-score
  [lines]
  (->> lines
       (map check-line)
       (filter #(= :corrupted (:error %)))
       (map :score)
       (reduce + 0)))

(defn complete-score
  [lines]
  (let [scores (->> lines
                    (map check-line)
                    (filter #(= :missing (:error %)))
                    (map :score))
        size (count scores)]
    (nth (sort scores) (/ (dec size) 2))))
