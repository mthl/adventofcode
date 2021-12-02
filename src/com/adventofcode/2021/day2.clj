(ns com.adventofcode.2021.day2)

(defn position [instructions]
  (reduce (fn [pos [action amount]]
            (case action
              :up (update pos :depth - amount)
              :down (update pos :depth + amount)
              :forward (update pos :horizontal-position + amount)))
          {:horizontal-position 0
           :depth 0}
          instructions))

(defn position-with-aim [instructions]
  (reduce (fn [pos [action X]]
            (case action
              :up (update pos :aim - X)
              :down (update pos :aim + X)
              :forward (-> pos
                           (update :horizontal-position + X)
                           (update :depth + (* X (:aim pos))))))
          {:horizontal-position 0
           :depth 0
           :aim 0}
          instructions))
