(defproject cljmapdemo/cljmapdemo "0.0.1-SNAPSHOT"
  :description "FIXME: Android project description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :global-vars {*warn-on-reflection* true}

  :source-paths ["src/clojure" "src"]
  :java-source-paths ["src/java"]
  :javac-options ["-target" "1.6" "-source" "1.6" "-Xlint:-options"]
  :plugins [[lein-droid "0.4.0-alpha4"]]

  :dependencies [[org.clojure-android/clojure "1.7.0-RC1" :use-resources true]
                 [neko/neko "4.0.0-alpha1"]
                 ]
  :profiles {:default [:dev]

             :dev
             [:android-common :android-user
              {:dependencies [[org.clojure/tools.nrepl "0.2.10"]
                             ;[com.google.android/support-v4 "r6"]
                              ]
               :target-path "target/debug"
               :android {:aot :all-with-unused
                         :rename-manifest-package "org.cljmapdemo"
                         :manifest-options {:app-name "cljmapdemo - debug"}
                         :project-dependencies  ["/opt/android-sdk/extras/google/google_play_services/libproject/google-play-services_lib"
                                                ;,"/opt/android-sdk/extras/android/support/v4/src"
                                                ]
                         :external-classes-paths  ["/opt/android-sdk/extras/google/google_play_services/libproject/google-play-services_lib/libs/google-play-services.jar"
                                                  ;,"/opt/android-sdk/extras/android/support/v4/android-support-v4.jar"
                                                  ]
                         }}]
             :release
             [:android-common
              {:target-path "target/release"
               :android
               { ;; Specify the path to your private keystore
                ;; and the the alias of the key you want to
                ;; sign APKs with.
                ;; :keystore-path "/home/user/.android/private.keystore"
                ;; :key-alias "mykeyalias"

                :ignore-log-priority [:debug :verbose]
                :aot :all
                :build-type :release}}]}

  :android {;; Specify the path to the Android SDK directory.
            ;; :sdk-path "/home/user/path/to/android-sdk/"

            ;; Try increasing this value if dexer fails with
            ;; OutOfMemoryException. Set the value according to your
            ;; available RAM.
            :dex-opts ["-JXmx4096M" "--incremental"]

            ;; If previous option didn't work, uncomment this as well.
            ;; :force-dex-optimize true

            :target-version "17"
            :support-libraries ["v4"]
            :use-google-api true
            :aot-exclude-ns ["clojure.parallel" "clojure.core.reducers"
                             "cljs-tooling.complete" "cljs-tooling.info"
                             "cljs-tooling.util.analysis" "cljs-tooling.util.misc"
                             "cider.nrepl" "cider-nrepl.plugin"
                             "cider.nrepl.middleware.util.java.parser"]})

