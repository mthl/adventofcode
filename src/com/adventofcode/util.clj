(ns com.adventofcode.util
  (:require
   [clojure.java.io :as io]))

(defn resources-lines
  "Construct a sequence of the lines contained in a resource file."
  [n]
  (with-open [f (-> n io/resource io/file io/reader)]
    (doall (line-seq f))))

(def char->int
  {\0 0
   \1 1
   \2 2
   \3 3
   \4 4
   \5 5
   \6 6
   \7 7
   \8 8
   \9 9})
