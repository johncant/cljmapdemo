(ns org.cljmapdemo.main
    (:require [neko.activity :refer [defactivity set-content-view! get-decor-view]]
              [neko.debug :refer [*a]]
              [neko.threading :refer [on-ui]]
              [neko.ui.mapping :refer [defelement]]
              [neko.notify :refer [toast]]
              [neko.ui :refer [make-ui-element]])
    (:import [com.google.android.gms.maps MapView]
             [com.google.android.gms.maps.OnMapReadyCallback]
             [com.google.android.gms.common GoogleApiAvailability]
             [android.content.Intent]
             [android.support.v4.app.Fragment]
             ))

(defactivity org.cljmapdemo.MainActivity
  :key :main

  (onCreate [this bundle]
    (.superOnCreate this bundle)
    (on-ui
      (set-content-view! (*a)
        [:linear-layout {}
          [:text-view {:text "Hello from CljMapDemo."}]
          [:button {:text "Pollution map"
                    :on-click (fn [_] (.startActivity this (android.content.Intent. "org.cljmapdemo.MAP")))
                   }]
          ]))))

(defelement :map-view
  :classname com.google.android.gms.maps.MapView
  :inherits :frame-layout
  :on-create (fn [^com.google.android.gms.maps.MapView this bundle]
               (.getMapAsync this (reify com.google.android.gms.maps.OnMapReadyCallback
                                    (onMapReady [this gm] (toast "map loaded"))))))

(defn make-map-ready-callback
  [func]
  (reify com.google.android.gms.maps.OnMapReadyCallback
    (onMapReady [this gm] (func gm))))

;(defn mapView [this]
;  ((memoize
;    (fn [] (make-ui-element this
;      [:map-view {:layout-width :fill
;                  :layout-height :fill} ]
;      {}
;      )))))

(defn mapView [this]
  ((memoize (fn [] (MapView. this)))))

(defn googleMap [this]
  ((memoize (fn [] (atom 1))))
  )

(defactivity org.cljmapdemo.MapActivity
  :key :map
  (onCreate [this bundle]
    (.superOnCreate this bundle)
      (set-content-view! (*a) (mapView (*a)))
      (toast (str (.isGooglePlayServicesAvailable (GoogleApiAvailability/getInstance) this)))
      (.getMapAsync (mapView (*a)) (make-map-ready-callback (fn [gm] (toast "map loaded")
                                                                     (swap! (googleMap this) #(gm)))))
  )
  (onResume [this]
            (.superOnResume this)
            (if @(googleMap this) (.onResume (mapView this))))
  (onPause [this]
            (.superOnPause this)
            (if @(googleMap this) (.onPause (mapView this))))

  (onDestroy [this]
            (.superOnDestroy this)
            (if @(googleMap this) (.onDestroy (mapView this))))

  (onSaveInstanceState [this bundle]
            (.superOnSaveInstanceState this bundle)
            (if @(googleMap this) (.onSaveInstanceState (mapView this) bundle)))
  (onLowMemory [this]
            (.superOnLowMemory this)
            (if @(googleMap this) (.onLowMemory (mapView this))))
)

