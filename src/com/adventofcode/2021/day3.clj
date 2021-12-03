(ns com.adventofcode.2021.day3
  (:require [clojure.string :as str]))

(defn column-nth
  [report i]
  (map #(nth % i) report))

(defn most-common-bit
  [^String bits]
  (max-key (frequencies bits) \0 \1))

(defn least-common-bit
  [^String bits]
  (min-key (frequencies bits) \1 \0))

(defn- columns
  [report]
  (->> (range 0 (-> report first count))
       (map #(str/join (column-nth report %)))))

(defn gamma-rate
  [report]
  (->> report columns (map most-common-bit) str/join))

(defn epsilon-rate
  [report]
  (->> report columns (map least-common-bit) str/join))

(defn bits->decimal
  "Convert a bit string into an integer."
  [^String bits]
  (Long/parseLong bits 2))

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
