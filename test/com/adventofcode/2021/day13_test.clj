(ns com.adventofcode.2021.day13-test
  (:require
   [clojure.string :as str]
   [clojure.test :refer [deftest is]]
   [com.adventofcode.util :as util]
   [com.adventofcode.2021.day13 :as sut]))

(def input
  (util/resources-lines "com/adventofcode/2021/day13.txt"))

(def sample
  (str/split-lines
   "6,10
0,14
9,10
0,3
10,4
4,11
6,0
6,12
4,1
0,13
10,12
3,4
3,0
8,4
1,10
2,14
8,10
9,0

fold along y=7
fold along x=5"))

(def parsed
  {:dots #{{:x 8 :y 4}
           {:x 8 :y 10}
           {:x 4 :y 11}
           {:x 3 :y 4}
           {:x 1 :y 10}
           {:x 6 :y 10}
           {:x 0 :y 14}
           {:x 3 :y 0}
           {:x 10 :y 4}
           {:x 10 :y 12}
           {:x 4 :y 1}
           {:x 6 :y 0}
           {:x 9 :y 10}
           {:x 0 :y 13}
           {:x 6 :y 12}
           {:x 0 :y 3}
           {:x 9 :y 0}
           {:x 2 :y 14}}
   :folds [[:y 7]
           [:x 5]]})

(deftest folds-test
  (is (= parsed (sut/parse-origami sample)))
  (is (= 17 (sut/visible-dots (sut/fold1 parsed))))
  (let [origami (sut/parse-origami input)]
    (is (= 837 (sut/visible-dots (sut/fold1 origami))))
    (is (= ["####.###..####..##..#..#..##..#..#.#..#"
            "#....#..#....#.#..#.#.#..#..#.#..#.#..#"
            "###..#..#...#..#....##...#....####.#..#"
            "#....###...#...#.##.#.#..#....#..#.#..#"
            "#....#....#....#..#.#.#..#..#.#..#.#..#"
            "####.#....####..###.#..#..##..#..#..##."]
           (sut/render (sut/fold origami))))))
