(ns spreadsheet.core
    (:require [reagent.core :as reagent :refer [atom]]
              [reagent.session :as session]
              [secretary.core :as secretary :include-macros true]
              [goog.events :as events]
              [goog.history.EventType :as EventType]
              [cljsjs.react :as react])
    (:import goog.History))

(def rows 51)

(defn a-to-z []
  (map char (range 65 91)))

(defn cell [i v]
  (let [editing (atom false)]
    [:td {:id i} v]
    )
  )

(defn spreadsheet []
  [:table {:class "bordered"}
   (for [t (cons "#" (a-to-z))]
     (cell t t))
   (for [x (range 1 rows)]
     [:tr
      (cell x x)
      (for [y (a-to-z)]
            (cell "2" ""))])])

(defn mount-root []
  (reagent/render [spreadsheet] (.getElementById js/document "app")))

(defn init! []
  (mount-root))
