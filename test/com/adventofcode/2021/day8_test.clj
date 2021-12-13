(ns com.adventofcode.2021.day8-test
  (:require
   [clojure.string :as str]
   [clojure.test :refer [deftest is]]
   [com.adventofcode.util :as util]
   [com.adventofcode.2021.day8 :as sut]))

(def input
  (util/resources-lines "com/adventofcode/2021/day8.txt"))

(def simple
  "acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf")

(def sample
  (str/split-lines
   "be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe
edbfga begcd cbg gc gcadebf fbgde acbgfd abcde gfcbed gfec | fcgedb cgb dgebacf gc
fgaebd cg bdaec gdafb agbcfd gdcbef bgcad gfac gcb cdgabef | cg cg fdcagb cbg
fbegcd cbd adcefb dageb afcb bc aefdc ecdab fgdeca fcdbega | efabcd cedba gadfec cb
aecbfdg fbg gf bafeg dbefa fcge gcbea fcaegb dgceab fcbdga | gecf egdcabf bgf bfgea
fgeab ca afcebg bdacfeg cfaedg gcfdb baec bfadeg bafgc acf | gebdcfa ecba ca fadegcb
dbcfg fgd bdegcaf fgec aegbdf ecdfab fbedc dacgb gdcebf gf | cefg dcbef fcge gbcadfe
bdfegc cbegaf gecbf dfcage bdacg ed bedf ced adcbefg gebcd | ed bcgafe cdgba cbgef
egadfb cdbfeg cegd fecab cgb gbdefca cg fgcdab egfdb bfceg | gbdfcae bgc cg cgb
gcafb gcf dcaebfg ecagb gf abcdeg gaef cafbge fdbac fegbdc | fgae cfgab fg bagce"))

(def entry
  {:signals ["acedgfb" "cdfbe" "gcdfa" "fbcad" "dab" "cefabd" "cdfgeb" "eafb" "cagedb" "ab"]
   :output ["cdfeb" "fcadb" "cdfeb" "cdbaf"]})

(def parsed
  [{:signals ["be" "cfbegad" "cbdgef" "fgaecd" "cgeb" "fdcge" "agebfd" "fecdb" "fabcd" "edb"]
    :output ["fdgacbe" "cefdb" "cefbgd" "gcbe"]}
   {:signals ["edbfga" "begcd" "cbg" "gc" "gcadebf" "fbgde" "acbgfd" "abcde" "gfcbed" "gfec"]
    :output ["fcgedb" "cgb" "dgebacf" "gc"]}
   {:signals ["fgaebd" "cg" "bdaec" "gdafb" "agbcfd" "gdcbef" "bgcad" "gfac" "gcb" "cdgabef"]
    :output ["cg" "cg" "fdcagb" "cbg"]}
   {:signals ["fbegcd" "cbd" "adcefb" "dageb" "afcb" "bc" "aefdc" "ecdab" "fgdeca" "fcdbega"]
    :output ["efabcd" "cedba" "gadfec" "cb"]}
   {:signals ["aecbfdg" "fbg" "gf" "bafeg" "dbefa" "fcge" "gcbea" "fcaegb" "dgceab" "fcbdga"]
    :output ["gecf" "egdcabf" "bgf" "bfgea"]}
   {:signals ["fgeab" "ca" "afcebg" "bdacfeg" "cfaedg" "gcfdb" "baec" "bfadeg" "bafgc" "acf"]
    :output ["gebdcfa" "ecba" "ca" "fadegcb"]}
   {:signals ["dbcfg" "fgd" "bdegcaf" "fgec" "aegbdf" "ecdfab" "fbedc" "dacgb" "gdcebf" "gf"]
    :output ["cefg" "dcbef" "fcge" "gbcadfe"]}
   {:signals ["bdfegc" "cbegaf" "gecbf" "dfcage" "bdacg" "ed" "bedf" "ced" "adcbefg" "gebcd"]
    :output ["ed" "bcgafe" "cdgba" "cbgef"]}
   {:signals ["egadfb" "cdbfeg" "cegd" "fecab" "cgb" "gbdefca" "cg" "fgcdab" "egfdb" "bfceg"]
    :output ["gbdfcae" "bgc" "cg" "cgb"]}
   {:signals ["gcafb" "gcf" "dcaebfg" "ecagb" "gf" "abcdeg" "gaef" "cafbge" "fdbac" "fegbdc"]
    :output ["fgae" "cfgab" "fg" "bagce"]}])


(deftest overlap-points-test
  (let [entries (sut/parse-entries input)]
    (is (= entry (sut/parse-line simple)))
    (is (= parsed (sut/parse-entries sample)))
    (is (= 26 (sut/count-1478 parsed)))
    (is (= 237 (sut/count-1478 entries)))

    (is (= {8 (set "acedgfb")
            5 (set "cdfbe")
            2 (set "gcdfa")
            3 (set "fbcad")
            7 (set "dab")
            9 (set "cefabd")
            6 (set "cdfgeb")
            4 (set "eafb")
            0 (set "cagedb")
            1 (set "ab")}
           (sut/find-mapping (:signals entry))))

    (is (= 5353 (sut/decode entry)))
    (is (= 61229 (sut/sum-entries parsed)))
    (is (= 1009098 (sut/sum-entries entries)))))
