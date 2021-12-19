(ns com.adventofcode.2021.day14
  (:require
   [clojure.string :as str]))

(defn parse-polymer
  [lines]
  (let [[template-lines rule-lines] (remove #{[""]} (partition-by empty? lines))]
    {:template (first template-lines)
     :rules (into {}
                  (map (fn [line]
                         (let [[_ x y z] (re-matches #"(.)(.) -> (.)" line)]
                           [[(first x) (first y)] (first z)])))
                  rule-lines)}))

(defn step
  [n polymer]
  (let [{:keys [template rules]} polymer
        apply-rule #(if-let [insert (rules %)]
                      [(first %) insert]
                      [(first %)])]
    (loop [i n
           state template]
      (if (zero? i)
        state
        (recur (dec i)
               (str (->> (map vector state (next state))
                         (mapcat apply-rule)
                         str/join)
                    (last state)))))))

(defn frequency-score
  [freq]
  (let [freqs (-> freq vals)
        most-common (reduce max 0 freqs)
        least-common (reduce min most-common freqs)]
    (- most-common least-common)))

(defn score
  [template]
  (-> template frequencies frequency-score))

(def add (fnil + 0))

(defn step-freq
  [n polymer]
  (let [{:keys [template rules]} polymer
        last-char (last template)]
    (loop [i n
           state (frequencies (map vector template (next template)))]
      (if (zero? i)
        (-> (reduce-kv (fn [acc k v]
                         (update acc (first k) add v))
                       {}
                       state)
            (update last-char inc))
        (recur (dec i)
               (reduce-kv (fn [acc k v]
                            (if-let [c (rules k)]
                              (-> acc
                                  (update [(first k) c] add v)
                                  (update [c (second k)] add v))
                              (update k add v)))
                          {}
                          state))))))

