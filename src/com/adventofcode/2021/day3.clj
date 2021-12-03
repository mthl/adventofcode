(ns com.adventofcode.2021.day3)

(defn parse-row
  [row]
  (map #(Long/parseLong (str %)) row))

(defn column-nth
  [report i]
  (map #(nth % i) report))

(defn most-common-bit
  [bits]
  (max-key (frequencies bits) 0 1))

(defn least-common-bit
  [bits]
  (min-key (frequencies bits) 1 0))

(defn- columns
  [report]
  (->> (range 0 (-> report first count))
       (map #(column-nth report %))))

(defn gamma-rate
  [report]
  (->> (columns report)
       (map most-common-bit)))

(defn epsilon-rate
  [report]
  (->> (columns report)
       (map least-common-bit)))

(defn exp
  "Compute x^n"
  [x n]
  (loop [res 1
         i n]
    (if (= i 0)
      res
      (recur (* res x) (dec i)))))

(defn bits->decimal
  "Convert a sequence of bits into an integer."
  [bits]
  (->> bits
       reverse
       (map-indexed (fn [n bit]
                      (case bit
                        0 0
                        1 (exp 2 n))))
       (reduce + 0)))

(defn power-consumption
  [report]
  (* (bits->decimal (gamma-rate report))
     (bits->decimal (epsilon-rate report))))

(defn- rate
  [report criteria]
  (loop [numbers report
         index 0]
    (if (= (count numbers) 1)
      (first numbers)
      (let [column (column-nth numbers index)
            bit (criteria column)]
        (recur (filter #(= (nth % index) bit) numbers)
               (inc index))))))

(defn oxygen-generator-rate
  [report]
  (rate report most-common-bit))

(defn co2-scrubber-rate
  [report]
  (rate report least-common-bit))

(defn life-support-rate
  [report]
  (* (bits->decimal (oxygen-generator-rate report))
     (bits->decimal (co2-scrubber-rate report))))
