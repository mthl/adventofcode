(ns com.adventofcode.2021.day12-test
  (:require
   [clojure.string :as str]
   [clojure.test :refer [deftest is]]
   [com.adventofcode.util :as util]
   [com.adventofcode.2021.day12 :as sut]))

(def input
  (util/resources-lines "com/adventofcode/2021/day12.txt"))

(def sample
  (str/split-lines
   "dc-end
HN-start
start-kj
dc-start
dc-HN
LN-dc
HN-end
kj-sa
kj-HN
kj-dc"))

(def small
  {:start #{:A :b},
   :A #{:start :c :b :end},
   :b #{:A :start :d :end},
   :c #{:A},
   :d #{:b},
   :end #{:A :b}})

(def parsed
  {:dc #{:kj :HN :start :end :LN}
   :end #{:dc :HN}
   :HN #{:dc :kj :start :end}
   :start #{:dc :kj :HN}
   :kj #{:dc :HN :start :sa}
   :LN #{:dc}
   :sa #{:kj}})

(deftest paths-test
  (is (= parsed (sut/parse-graph sample)))
  (is (= 19 (sut/count-paths parsed)))
  (is (= 36 (sut/count-paths* small)))
  (is (= 103 (sut/count-paths* parsed)))
  (let [g (sut/parse-graph input)]
    (is (= 3708 (sut/count-paths g)))
    (is (= 93858 (sut/count-paths* g)))))
