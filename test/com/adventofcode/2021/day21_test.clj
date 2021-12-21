(ns com.adventofcode.2021.day21-test
  (:require
   [clojure.string :as str]
   [clojure.test :refer [deftest is]]
   [com.adventofcode.util :as util]
   [com.adventofcode.2021.day21 :as sut]))

(def input
  (util/resources-lines "com/adventofcode/2021/day21.txt"))

(def sample
  (str/split-lines
   "Player 1 starting position: 4
Player 2 starting position: 8"))

(def parsed
  {:player-1 {:pos 4
              :score 0}
   :player-2 {:pos 8
              :score 0}
   :next-player :player-1
   :rolled 0})

(def board
  (sut/parse-board input))

(deftest image-enhancement-test
  (is (= parsed (sut/parse-board sample)))
  (is (= {:player-1 {:pos 10, :score 1000},
          :player-2 {:pos 3, :score 745},
          :next-player :player-2,
          :rolled 993,
          :previous-rolls [91 92 93],
          :won? true}
         (sut/deterministic-play parsed (sut/deterministic-die))))
  (is (= 739785 (sut/deterministic-play-score parsed)))
  (is (= 929625 (sut/deterministic-play-score board))))
