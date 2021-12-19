(ns com.adventofcode.2021.day13
  (:require
   [clojure.string :as str]))

(defn parse-origami
  [lines]
  (let [[dot-lines fold-lines] (remove #{[""]} (partition-by empty? lines))]
    {:dots (into #{}
                 (map (fn [s]
                        (let [[x y] (str/split s #",")]
                          {:x (Long/parseLong x)
                           :y (Long/parseLong y)})))
                 dot-lines)
     :folds (mapv (fn [line]
                    (let [[_ k v] (re-matches #"fold along (x|y)=(\d+)" line)]
                      [(keyword k) (Long/parseLong v)]))
                  fold-lines)}))

(defn fold1
  [origami]
  (let [{:keys [dots folds]} origami
        [k v] (first folds)
        base (remove #(> (k %) v) dots)
        to-fold (filter #(> (k %) v) dots)
        folded (case k
                 :y (map (fn [{:keys [x y]}]
                           {:x x
                            :y (- v (- y v))})
                         to-fold)
                 :x (map (fn [{:keys [x y]}]
                           {:x (- v (- x v))
                            :y y})
                         to-fold))]
    (-> origami
        (assoc :dots (into (set base) folded))
        (update :folds next))))

(defn fold
  [origami]
  (loop [state origami]
    (if (-> state :folds empty?)
      state
      (recur (fold1 state)))))

(defn visible-dots
  [origami]
  (-> origami :dots count))

(defn render
  [origami]
  (let [{:keys [dots]} origami
        max-x (reduce max 0 (map :x dots))
        max-y (reduce max 0 (map :y dots))]
    (for [y (range (inc max-y))]
      (->> (for [x (range (inc max-x))]
             (if (contains? dots {:x x :y y}) \# \.))
           str/join))))
