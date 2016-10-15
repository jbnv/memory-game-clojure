(ns lotto.handlers
    (:require [re-frame.core :as re-frame]
              [lotto.db :as db]
              [lotto.cards :as cards]))

(re-frame/reg-event-db
 :initialize-db
 (fn  [_ _]
   db/default-db))

; card-names: list of names (symbols) of the cards
(defn deal-cards [card-names width height]
  (let
    [
      card-count (* width height)
      taken-card-names (take (/ card-count 2) (shuffle card-names))
      name-deck (shuffle (concat taken-card-names taken-card-names))
      deck (for [name name-deck] (cards/card name :front))
      grid-of-cards (vec (for [row (partition width deck)] (vec row)))
    ]
    grid-of-cards
  )
)

(re-frame/reg-event-db
  :deal-cards
  (fn [db [_ card-names]]
    (assoc
      db
      :cards (deal-cards card-names (get db :width) (get db :height))
      :current-player (get db :current-player)
    )
  )
)
