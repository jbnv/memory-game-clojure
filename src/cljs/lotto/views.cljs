(ns lotto.views
  (:require [lotto.utils :refer [state-viewer]]
            [re-frame.core :as re-frame]
            [lotto.cards :as cards]))

(defn card-face [card]
  [:div.front
    [:div.card {:style {:font-size "240px"}}
      (get cards/mahjong (cards/face card))
    ]
  ]
)

(defn card-back []
  [:div.back
    [:div.card]
  ]
)

(defn oriented-card [card]
  [(if (cards/front? card) :div.flip-container :div.flip-container.back-of-card)
    [:div.flipper [card-back] [card-face card]]
  ]
)

(defn height [] 4)
(defn width [] 5)

(defn grid []
  (let
    [
      card (fn [is-front]
        [oriented-card (cards/card :white-dragon (if is-front :front :back))]
      )
      xor (fn [x y]
        (= (mod (+ x y) 2) 1)
      )
    ]
    [:table [:tbody
      (for [y (range (height))]
        [:tr (for [x (range (width))]
          [:td (card (xor x y))]
        )]
      )
    ]]
  )
)

(defn on-click-deal-new-game []
  (re-frame/dispatch [:deal-cards
    [:a :b :c :d :e :f :g :h :i :j]
    (width)
    (height)
  ])
)

(defn main-panel []
  (let
    [
      subtitle [:h2
        "Current Player: " @(re-frame/subscribe [:current-player])
      ]
    ]
    [:div
      subtitle
      [grid]
      [:button {:on-click on-click-deal-new-game} "Deal New Game"]
      [state-viewer]
    ]
  )
)
