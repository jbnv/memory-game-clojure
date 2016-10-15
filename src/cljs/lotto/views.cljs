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
  [
    (if
      (cards/front? card)
      :div.flip-container
      :div.flip-container.back-of-card
    )
    [:div.flipper
      [card-back]
      [card-face card]
    ]
  ]
)

(defn height [] (re-frame/subscribe [:height]))
(defn width [] (re-frame/subscribe [:width]))

(defn flipper [row column]
  (let
    [
      card (re-frame/subscribe [:card-at row column])
    ]
    [:div 
      {
        :on-click (fn [] (re-frame/dispatch [:flip-up row column]))
      }
      [oriented-card @card]
    ]
  )
)

(defn grid []
  (let
    [
    ]
    [:table [:tbody
      (for [y (range @(height))]
        [:tr (for [x (range @(width))]
          [:td [flipper x y]]
        )]
      )
    ]]
  )
)

(defn on-click-deal-new-game []
  (re-frame/dispatch [:deal-cards
    (keys cards/mahjong)
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
