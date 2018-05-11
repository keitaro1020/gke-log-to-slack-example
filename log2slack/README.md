# 設定方法

1. GCP Console -> STACKDRIVER Logging
	1. 通知対象ログの条件を絞り込む
	2. エクスポートを作成 を選択
	3. エクスポートの編集画面で下記のように入力・選択してシンクを作成する
		- シンク名 : 適当な名前を入力
		- シンクサービス : Cloud Pub/Sub
		- シンクのエクスポート先 : 新しいPub/Subトピックを作成 -> 適当な名前を入力
2. Slack
	1. Webhook URLを発行
		- https://get.slack.help/hc/ja/articles/115005265063-Slack-%E3%81%A7%E3%81%AE%E7%9D%80%E4%BF%A1-WebHook-%E3%81%AE%E5%88%A9%E7%94%A8
3. GCP Console -> Cloud Functions
	1. 関数を作成 を選択
	2. 関数の作成画面で下記のように入力・選択して関数を作成する
		- 名前 : log2slack
		- メモリ : 128MB
		- トリガー : Cloud Pub/Subトピック
		- トピック : 1.3.で作成したPub/Subトピックを選択
		- ソースコード : インラインエディタ
		- index.js : 本ドキュメントと同じディレクトリにある index.js
			- 2.で取得したWebhook URLを置き換える
		- package.json : 本ドキュメントと同じディレクトリにある package.json
		- 実行する関数 : log2slack

## 参考
https://www.topgate.co.jp/notify-slack-by-cloud-functions

