(ns com.adventofcode.2021.day21)

(defn- parse-player
  [line]
  (let [[_ id pos] (re-matches #"Player (1|2) starting position: (\d)" line)]
    [(keyword (str "player-" id))
     {:pos (Long/parseLong pos)
      :score 0}]))

(defn parse-board
  [lines]
  (into {:next-player :player-1
         :rolled 0}
        (map parse-player)
        lines))

(defn deterministic-die
  []
  (cycle (range 1 101 1)))

(defn next-player
  [player]
  (case player
    :player-1 :player-2
    :player-2 :player-1))

(defn compute-pos
  [pos move]
  (inc (mod (dec (+ pos move)) 10)))

(defn step
  [{pX :next-player :as state} rolls]
  (let [{:keys [pos score]} (get state pX)
        move (reduce + 0 rolls)
        new-pos (compute-pos pos move)
        new-score (+ score new-pos)
        won? (>= new-score 1000)]
    (-> state
        (assoc :previous-rolls (vec rolls)
               :won? won?
               pX {:pos new-pos
                   :score new-score})
        (update :next-player next-player)
        (update :rolled + 3))))

(defn deterministic-play
  [board die]
  (reduce (fn [state rolls]
            (let [state* (step state rolls)]
              (if (:won? state*) (reduced state*) state*)))
          board
          (partition 3 die)))

(defn deterministic-play-score
  [board]
  (let [state (deterministic-play board (deterministic-die))
        {loser :next-player rolled-count :rolled} state]
    (-> state loser :score (* rolled-count))))
