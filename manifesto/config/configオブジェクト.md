# configオブジェクト
```
環境変数や設定ファイルなどの設定情報、パスワードなどの機密情報をコンテアに渡すためのオブジェクト
```

## configオブジェクトの種類
### ConfigMap
```
コンテナから利用する環境情報（環境変数や設定ファイルなど）を管理するオブジェクト
```
### Secret
```
パスワードなどの機密情報を管理するための機能を備えたオブジェクト
設定するデータは必ずBase64エンコードしなければならない
```

## SecretにおけるBase64エンコードコマンド
### コマンド
```
echo -n "<データ>" | base64
```
#### 例）
```
echo -n "password" | base64
⇒cGFzc3dvcmQ=
```

#### 環境変数を渡す場合の設定例
```
data:
    env: cGFzc3dvcmQ=
```

#### コンテナ内にマウントする場合⇒プロパティファイルのキーを含めすべてエンコード
```
# コマンド
echo -n "password=password" | base64

# 設定例
data:
    secret.properties: |
      cGFzc3dvcmQ9cGFzc3dvcmQ=
```
