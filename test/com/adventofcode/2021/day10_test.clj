(ns com.adventofcode.2021.day10-test
  (:require
   [clojure.string :as str]
   [clojure.test :refer [deftest is]]
   [com.adventofcode.util :as util]
   [com.adventofcode.2021.day10 :as sut]))

(def input
  (util/resources-lines "com/adventofcode/2021/day10.txt"))

(def sample
  (str/split-lines
   "[({(<(())[]>[[{[]{<()<>>
[(()[<>])]({[<{<<[]>>(
{([(<{}[<>[]}>{[]{[(<()>
(((({<>}<{<{<>}{[]{[]{}
[[<[([]))<([[{}[[()]]]
[{[{({}]{}}([{[{{{}}([]
{<[[]]>}<{[{[{[]{()[[[]
[<(<(<(<{}))><([]([]()
<{([([[(<>()){}]>(<<{{
<{([{{}}[<[[[<>{}]]]>[]]"))

(deftest syntax-scoring-test
  (is (= 26397 (sut/check-score sample)))
  (is (= 168417 (sut/check-score input)))
  (is (= 294 (sut/complete-line-score (seq "])}>"))))
  (is (= 288957 (sut/complete-score sample)))
  (is (= 2802519786 (sut/complete-score input))))
