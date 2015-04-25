(ns spreadsheet.core
    (:require [reagent.core :as reagent :refer [atom]]
              [reagent.session :as session]
              [secretary.core :as secretary :include-macros true]
              [goog.events :as events]
              [goog.history.EventType :as EventType]
              [cljsjs.react :as react])
    (:import goog.History))

(defn a-to-z []
  (map char (range 65 91)))

(def rows 51)

(defn init-spreadsheet []
  (let [s {}]
    (into s (for [x (range 1 rows)]
              (for [y (a-to-z)]
                [(str y x) ""])))))

(def spreadsheet
  (atom {:cells (init-spreadsheet)})
  )

(defn header-cell [i]
  [:th {:id i :width "100px"} i]
  )

(defn handle-input [i e]
  (swap! spreadsheet assoc i (.-target.value e))
  )

(defn cell [i v]
  [:td {:id i :width "100px" :contentEditable "true" :onInput (fn [e] (handle-input i e))} v]
  )

(defn table-spreadsheet []
  [:table {:id "spreadsheet" :class "bordered" :style {:table-layout "fixed"}}
   (for [t (cons "#" (a-to-z))]
     (header-cell t))
   (for [x (range 1 rows)]
     [:tr
      (header-cell x)
      (for [y (a-to-z)]
            (cell (str y x) ""))])])

(defn mount-root []
  (reagent/render [table-spreadsheet] (.getElementById js/document "app")))

(defn init! []
  (mount-root))
