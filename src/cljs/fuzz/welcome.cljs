(ns fuzz.welcome)

(defn handler [target params]
  (set! (.-innerHTML target) "<div class=\"juxt-div\"><p>Hello, let's get rocking</p></div>")

  (println "welcome"))
