(ns com.adventofcode.2021.day4
  (:require
   [clojure.string :as str]
   [clojure.set :as set]))

(defn numbers
  [strs]
  (mapv #(Long/parseLong %) strs))

(defn- parse-board
  [raw-board]
  (-> (str/trim raw-board)
      (str/replace #" +" ",")
      (str/split #",")))

(defn parse-raw
  [lines]
  (let [groups (remove #{[""]} (partition-by empty? lines))
        raw-inputs (str/split (ffirst groups) #",")
        raw-boards (map #(str/join \space %) (rest groups))]
    {:inputs (numbers raw-inputs)
     :drawn []
     :scores {}
     :boards (mapv (comp numbers parse-board) raw-boards)}))

(defn board?
  [x]
  (and (seq x)
       (every? int? x)
       (= (count x) 25)))

(defn bingo?
  [board drawn]
  (let [rows (partition 5 board)
        columns (apply map vector rows)]
    (boolean
     (or (some #(set/subset? % (set drawn)) (map set rows))
         (some #(set/subset? % (set drawn)) (map set columns))))))

(defn score
  [{:keys [board drawn]}]
  (let [called (last drawn)
        unmarked (remove (set drawn) board)]
    (* (reduce + 0 unmarked)
       called)))

(defn play
  [game]
  (let [called (-> game :inputs first)]
    (if (nil? called)
      game
      (let [drawn (conj (:drawn game) called)
            new-winners (->> (:boards game)
                             (filter #(bingo? % drawn))
                             (remove (:scores game)))]
        (recur
         (-> game
             (assoc :drawn drawn)
             (update :inputs rest)
             (update :scores merge (zipmap new-winners
                                           (repeat drawn)))))))))

(defn- choose-score
  [game f]
  (when-let [scores (:scores game)]
    (let [[board drawn] (f scores)]
      {:board board
       :drawn drawn})))

(defn first-winner
  [game]
  (choose-score game #(apply min-key (comp count val) %)))

(defn last-winner
  [game]
  (choose-score game #(apply max-key (comp count val) %)))
