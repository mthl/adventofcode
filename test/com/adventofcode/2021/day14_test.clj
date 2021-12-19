(ns com.adventofcode.2021.day14-test
  (:require
   [clojure.string :as str]
   [clojure.test :refer [deftest is]]
   [com.adventofcode.util :as util]
   [com.adventofcode.2021.day14 :as sut]))

(def input
  (util/resources-lines "com/adventofcode/2021/day14.txt"))

(def sample
  (str/split-lines
   "NNCB

CH -> B
HH -> N
CB -> H
NH -> C
HB -> C
HC -> B
HN -> C
NN -> C
BH -> H
NC -> B
NB -> B
BN -> B
BB -> N
BC -> B
CC -> N
CN -> C"))

(def parsed
  {:template "NNCB"
   :rules {[\C \H] \B
           [\H \H] \N
           [\C \B] \H
           [\N \H] \C
           [\H \B] \C
           [\H \C] \B
           [\H \N] \C
           [\N \N] \C
           [\B \H] \H
           [\N \C] \B
           [\N \B] \B
           [\B \N] \B
           [\B \B] \N
           [\B \C] \B
           [\C \C] \N
           [\C \N] \C}})

(deftest folds-test
  (is (= parsed (sut/parse-polymer sample)))
  (is (= "NCNBCHB" (sut/step 1 parsed)))
  (is (= "NBCCNBBBCBHCB" (sut/step 2 parsed)))
  (is (= "NBBBCNCCNBBNBNBBCHBHHBCHB" (sut/step 3 parsed)))
  (is (= "NBBNBNBBCCNBCNCCNBBNBBNBBBNBBNBBCBHCBHHNHCBBCBHCB" (sut/step 4 parsed)))
  (is (= 1588 (sut/score (sut/step 10 parsed))))
  (is (= 1588 (sut/frequency-score (sut/step-freq 10 parsed))))
  (is (= 2188189693529 (sut/frequency-score (sut/step-freq 40 parsed))))

  (let [polymer (sut/parse-polymer input)]
    (is (= 4244 (sut/score (sut/step 10 polymer))))
    (is (= 4807056953866 (sut/frequency-score (sut/step-freq 40 polymer))))))
