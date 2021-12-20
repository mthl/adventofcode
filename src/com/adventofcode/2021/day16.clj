(ns com.adventofcode.2021.day16
  (:require
   [clojure.string :as str]))

(def hexa-bits
  {\0 [0 0 0 0]
   \1 [0 0 0 1]
   \2 [0 0 1 0]
   \3 [0 0 1 1]
   \4 [0 1 0 0]
   \5 [0 1 0 1]
   \6 [0 1 1 0]
   \7 [0 1 1 1]
   \8 [1 0 0 0]
   \9 [1 0 0 1]
   \A [1 0 1 0]
   \B [1 0 1 1]
   \C [1 1 0 0]
   \D [1 1 0 1]
   \E [1 1 1 0]
   \F [1 1 1 1]})

(defn bits->num
  [bits]
  (Long/parseLong (str/join bits) 2))

(defn- bit-seq
  [s]
  (mapcat hexa-bits s))

(defn packet-type
  [type-id]
  (case type-id
    0 :sum
    1 :product
    2 :minimum
    3 :maximum
    4 :literal
    5 :greater-than
    6 :less-than
    7 :equal-to))

(defn parse-packets
  ([s]
   (parse-packets (bit-seq s) 1))
  ([bits n]
   (loop [bits bits
          i n
          acc []]
     (if (or (= i 0) (every? zero? bits))
       (with-meta acc {:bits bits})
       (let [[version bits] (split-at 3 bits)
             [type-id bits] (split-at 3 bits)
             packet {:version (bits->num version)
                     :type (-> type-id bits->num packet-type)}]
         (if (-> packet :type (= :literal))
           (let [butlast-groups (->> bits
                                     (partition 5)
                                     (take-while #(= 1 (first %))))
                 bits (drop (* 5 (count butlast-groups)) bits)
                 [last-group bits] (split-at 5 bits)
                 groups (map #(vec (drop 1 %))
                             (concat butlast-groups [last-group]))
                 data (mapcat identity groups)
                 packet* (assoc packet :number (bits->num data))]
             (recur bits (some-> i dec) (conj acc packet*)))
           (let [[[length-type-id] bits] (split-at 1 bits)]
             (case length-type-id
               0 (let [[data bits] (split-at 15 bits)
                       total-length (bits->num data)
                       [data bits] (split-at total-length bits)
                       subpackets (parse-packets data nil)
                       packet* (assoc packet
                                      :total-length-in-bits total-length
                                      :subpackets subpackets)]
                   (recur bits (some-> i dec) (conj acc packet*)))
               1 (let [[data bits] (split-at 11 bits)
                       number-of-subpackets (bits->num data)
                       subpackets (parse-packets bits number-of-subpackets)
                       packet* (assoc packet
                                      :number-of-subpackets number-of-subpackets
                                      :subpackets subpackets)]
                   (recur (-> subpackets meta :bits)
                          (some-> i dec)
                          (conj acc packet*)))))))))))

(defn version-sum
  [packets]
  (let [sum (reduce + 0 (map :version packets))
        subpackets (mapcat :subpackets packets)]
    (if (empty? subpackets)
      sum
      (+ sum (version-sum subpackets)))))

(defn binop-subpackets
  [packet]
  (let [{:keys [subpackets]} packet]
    (assert (= 2 (count subpackets)))
    subpackets))

(defn eval-packets
  [packet]
  (if-not (map? packet)
    (recur (first packet))
    (case (:type packet)
      :sum (reduce + 0 (map eval-packets (:subpackets packet)))
      :product (reduce * 1 (map eval-packets (:subpackets packet)))
      :minimum (reduce min Long/MAX_VALUE (map eval-packets (:subpackets packet)))
      :maximum (reduce max Long/MIN_VALUE (map eval-packets (:subpackets packet)))
      :literal (:number packet)
      :greater-than (let [[x y] (binop-subpackets packet)]
                      (if (> (eval-packets x) (eval-packets y)) 1 0))
      :less-than (let [[x y] (binop-subpackets packet)]
                   (if (< (eval-packets x) (eval-packets y)) 1 0))
      :equal-to (let [[x y] (binop-subpackets packet)]
                  (if (= (eval-packets x) (eval-packets y)) 1 0)))))
