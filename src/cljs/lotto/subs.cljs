(ns lotto.subs
    (:require-macros [reagent.ratom :refer [reaction]])
    (:require [re-frame.core :as re-frame]))

(re-frame/reg-sub :app-state (fn [db] db))

(re-frame/reg-sub :current-player
  (fn [db]
    (get db :current-player)
  )
)

(re-frame/reg-sub :current-player
  (fn [db]
    (get db :current-player)
  )
)

(re-frame/reg-sub :height
  (fn [db]
    (get db :height)
  )
)

(re-frame/reg-sub :width
  (fn [db]
    (get db :width)
  )
)

(re-frame/reg-sub :card-at
  (fn [db [_ row column]]
    (get-in db [:cards row column])
  )
)
