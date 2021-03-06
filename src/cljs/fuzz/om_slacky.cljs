(ns fuzz.om-slacky
  (:require-macros [cljs.core.async.macros :refer [go go-loop]])
  (:require [sablono.core :as html :refer-macros [html]]
            [om-tools.core :refer-macros [defcomponent]]
            [fuzz.slacky :as slacky]
            [om.core :as om :include-macros true]))

(def app-state (atom {:messages []}))

;; Rendering ---------

(defcomponent OmSlacky [{:keys [messages]} owner]
  (render [this]
    (html
     [:div.juxt-div
      (for [m (reverse messages)]
        [:p m])])))

;; Application -------

(defn handler [target opts]
  (om/root OmSlacky app-state {:target target})

  (let [slacky-msg-chan (slacky/poll-slacky)]
    (go-loop []
      (when-let [msg (<! slacky-msg-chan)]
        (swap! app-state update-in [:messages] conj msg)
        (recur)))))


;; See notes (atom in fig)
