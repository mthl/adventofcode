(ns com.adventofcode.2021.day4-test
  (:require
   [clojure.string :as str]
   [clojure.test :refer [deftest is]]
   [com.adventofcode.util :as util]
   [com.adventofcode.2021.day4 :as sut]))

(def input
  (util/resources-lines "com/adventofcode/2021/day4.txt"))

(def sample
  (str/split-lines
   "7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1

22 13 17 11  0
 8  2 23  4 24
21  9 14 16  7
 6 10  3 18  5
 1 12 20 15 19

 3 15  0  2 22
 9 18 13 17  5
19  8  7 25 23
20 11 10 24  4
14 21 16 12  6

14 21 17 24  4
10 16 15  9 19
18  8 23 26 20
22 11 13  6  5
 2  0 12  3  7"))

(def game
  {:inputs [7 4 9 5 11 17 23 2 0 14 21 24 10 16 13 6 15 25 12 22 18 20 8 19 3 26 1]
   :drawn []
   :scores {}
   :boards [[22 13 17 11  0
              8  2 23  4 24
             21  9 14 16  7
              6 10  3 18  5
              1 12 20 15 19]

            [ 3 15  0  2 22
              9 18 13 17  5
             19  8  7 25 23
             20 11 10 24  4
             14 21 16 12  6]

            [14 21 17 24  4
             10 16 15  9 19
             18  8 23 26 20
             22 11 13  6  5
              2  0 12  3  7]]})

(deftest bingo-test
  (is (= game (sut/parse-raw sample)))
  (is (true? (sut/bingo? (-> game :boards (nth 2))
                         [14 21 17 24 4])))
  (is (true? (sut/bingo? (-> game :boards (nth 2))
                         [17 15 23 13 12])))
  (is (false? (sut/bingo? (-> game :boards (nth 2))
                          [21 17 24 4])))
  (is (= 4512 (-> game sut/play sut/first-winner sut/score)))
  (is (= 6592 (-> input sut/parse-raw sut/play sut/first-winner sut/score)))

  (is (= 1924 (-> game sut/play sut/last-winner sut/score)))
  (is (= 31755 (->  input sut/parse-raw sut/play sut/last-winner sut/score))))
