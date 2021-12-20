(ns com.adventofcode.2021.day16-test
  (:require
   [clojure.test :refer [deftest is]]
   [com.adventofcode.util :as util]
   [com.adventofcode.2021.day16 :as sut]))

(def input
  (first (util/resources-lines "com/adventofcode/2021/day16.txt")))

(deftest decoder-test
  (is (= [{:version 6
           :type :literal
           :number 2021}]
         (sut/parse-packets "D2FE28")))
  (is (= [{:version 1
           :type :less-than
           :total-length-in-bits 27
           :subpackets
           [{:version 6
             :type :literal
             :number 10}
            {:version 2
             :type :literal
             :number 20}]}]
         (sut/parse-packets "38006F45291200")))
  (is (= [{:version 7
           :type :maximum
           :number-of-subpackets 3
           :subpackets
           [{:version 2
             :type :literal
             :number 1}
            {:version 4
             :type :literal
             :number 2}
            {:version 1
             :type :literal
             :number 3}]}]
         (sut/parse-packets "EE00D40C823060")))

  (is (= 16 (-> "8A004A801A8002F478" sut/parse-packets sut/version-sum)))
  (is (= 12 (-> "620080001611562C8802118E34" sut/parse-packets sut/version-sum)))
  (is (= 23 (-> "C0015000016115A2E0802F182340" sut/parse-packets sut/version-sum)))
  (is (= 31 (-> "A0016C880162017C3686B18A3D4780" sut/parse-packets sut/version-sum)))
  (is (= 866 (-> input sut/parse-packets sut/version-sum)))
  (is (= 3 (-> "C200B40A82" sut/parse-packets sut/eval-packets)))
  (is (= 54 (-> "04005AC33890" sut/parse-packets sut/eval-packets)))
  (is (= 7 (-> "880086C3E88112" sut/parse-packets sut/eval-packets)))
  (is (= 9 (-> "CE00C43D881120" sut/parse-packets sut/eval-packets)))
  (is (= 1 (-> "D8005AC2A8F0" sut/parse-packets sut/eval-packets)))
  (is (= 0 (-> "F600BC2D8F" sut/parse-packets sut/eval-packets)))
  (is (= 0 (-> "9C005AC2F8F0" sut/parse-packets sut/eval-packets)))
  (is (= 1 (-> "9C0141080250320F1802104A08" sut/parse-packets sut/eval-packets)))
  (is (= 1392637195518 (-> input sut/parse-packets sut/eval-packets))))
