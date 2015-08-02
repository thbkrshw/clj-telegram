(ns clj-telegram.client
  (:require  [clj-http.client :as client]
             [clojure.data.json :as json]))


(def api-url (str "https://api.telegram.org/bot"))

(defn- request [verb token data]
  (let [response (client/post (str api-url bot-token "/" verb)
                              {:form-params data
                               :as :json})
        json     (json/read-str (:body response) :key-fn keyword)]
    (clojure.pprint/pprint json)
    json))


(defn get-me [token]
  (request "getMe" token nil))

(defn send-message [token chat-id text & optionals]
  (request "sendMessage" token {:chat_id chat-id
                                :text text
                                :reply_markup (json/write-str {:keyboard [["0" "1"]["Foo" "Bar"]]
                                                               :one_time_keyboard true
                                                               :resize_keyboard true})}))

(defn setup-hook [token url]
  (request "setWebhook" token {:url url}))

(defn remove-hook [token]
  (setup-hook token ""))

(defn process-updates [req]
  ;; (println req)
  (let [update (json/read-str (slurp (:body req)) :key-fn keyword)
        m (:message update)
        chat-id (-> m :chat :id)
        text (-> m :text)]
    ;;(debug m chat-id text)
    ;;(message chat-id (str "Sent: " text))

    )
  "OK")
