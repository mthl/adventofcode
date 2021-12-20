(ns com.adventofcode.2021.day17
  (:refer-clojure :exclude [test]))

(defprotocol Target
  (missed? [this obj])
  (within? [this obj]))

(defrecord Probe [x y])

(defrecord Area [x-min y-min x-max y-max]
  Target
  (missed? [_ probe]
    (or (> (:x probe) x-max)
        (< (:y probe) y-min)))

  (within? [_ probe]
    (and (<= x-min (:x probe) x-max)
         (<= y-min (:y probe) y-max))))

(defrecord Velocity [x y])

(def ^:private line-regexp
  #"target area: x=(-?\d+)..(-?\d+), y=(-?\d+)..(-?\d+)")

(defn parse-target-area
  [line]
  (let [matches (re-matches line-regexp line)
        [x1 x2 y1 y2] (map #(Long/parseLong %) (next matches))]
    (map->Area
     {:x-min (min x1 x2)
      :x-max (max x1 x2)
      :y-min (min y1 y2)
      :y-max (max y1 y2)})))

(defn step
  [state]
  (let [{:keys [probe velocity y-max]} state
        {px :x py :y} probe
        {vx :x vy :y} velocity]
    (assoc state
           :y-max (max y-max (+ py vy))
           :probe (->Probe (+ px vx) (+ py vy))
           :velocity (map->Velocity
                      {:x (case (compare vx 0)
                            0 0
                            -1 (inc vx)
                            +1 (dec vx))
                       :y (dec vy)}))))

(defn test
  [target velocity]
  (loop [state {:probe (->Probe 0 0)
                :velocity velocity
                :init-velocity velocity
                :y-max Long/MIN_VALUE}]
    (let [{:keys [probe]} state]
      (cond
        (within? target probe) (assoc state :result :success)
        (missed? target probe) (assoc state :result :failure)
        :else (recur (step state))))))

(defn max-y
  [target]
  (->> (for [x (range 500)
             y (range 500)]
         (->Velocity x y))
       (map (partial test target))
       (filter (comp #{:success} :result))
       (sort-by :y-max >)
       first
       :y-max))

(defn count-valid-velocities
  [target]
  (->> (for [x (range 500)
             y (range -250 250 1)]
         (->Velocity x y))
       (map (partial test target))
       (filter (comp #{:success} :result))
       count))
